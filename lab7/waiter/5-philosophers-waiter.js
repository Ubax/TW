const EventEmitter = require('events');

class Lock extends EventEmitter { }

class Fork {
	constructor() {
		this.isAcquired = false;
		this.lock = new Lock();
	}

	waitOnLock(f) {
		if (this.isAcquired) {
			this.lock.on('unlocked', () => {
				this.waitOnLock(f)
			});
		}
		else {
			this.isAcquired = true;
			f();
		}
	}

	acquire() {
		if (this.isAcquired) return false;
		else {
			this.isAcquired = true;
			return true;
		}
	}
	release() {
		this.isAcquired = false;
		this.lock.emit('unlocked');
	}
}

class Waiter {
	constructor(numberOfPhilosopers) {
		this.currentlyEating = 0;
		this.numberOfPhilosopers = numberOfPhilosopers;
	}
	allow(leftFork, rightFork) {
		if (this.currentlyEating < this.numberOfPhilosopers) {
			this.currentlyEating++;
			return true;
		}
		return false;
	}
	release(){
		this.currentlyEating--;
	}
}

const acquiringLock = new Lock();

class Philosopher {
	constructor(name, leftFork, rightFork, waiter) {
		this.name = name
		this.leftFork = leftFork
		this.rightFork = rightFork
		this.running = true;
		this.waiter = waiter;
	}
	_log(msg) {
		console.log(`${this.name}: ${msg}`)
	}
	kill() {
		this.running = false;
	}
	waitForPermission(f) {
		if (this.waiter.allow(this.leftFork, this.rightFork)) {
			f();
		} else {
			setTimeout(() => this.waitForPermission(f), Math.floor(Math.random() * 100));
		}
	}
	acquireForks() {
		return new Promise((resolve, reject) => {
			this.waitForPermission(() => {
				this.leftFork.acquire();
				this.rightFork.acquire();
				resolve();
			})
		})
	}
	async run() {
		while (this.running) {
			await this.acquireForks();
			await this.think();
			this._log("Acquiring left fork");
			await this.leftFork.acquire();
			this._log("Acquiring right fork");
			await this.rightFork.acquire();
			await this.dine();
			this._log("Releasing forks");
			this.leftFork.release();
			this.rightFork.release();
			this.waiter.release();
		}
	}
	think() {
		this._log("Thinking")
		return new Promise((resolve, reject) => {
			setTimeout(() => {
				resolve();
			}, Math.floor(Math.random() * 100))
		})
	}
	dine() {
		this._log("Eating")
		return new Promise((resolve, reject) => {
			setTimeout(() => {
				resolve();
			}, Math.floor(Math.random() * 100 + 200))
		})
	}
}

const N = 3;

var forks = []
var philosophers = []
var waiter = new Waiter(N);
for (let i = 0; i < N; i++)forks.push(new Fork());
for (let i = 0; i < N - 1; i++)philosophers.push(new Philosopher(`Phil ${i}`, forks[i], forks[i + 1], waiter));
philosophers.push(new Philosopher(`Phil last`, forks[forks.length - 1], forks[0], waiter));
philosophers.forEach((x, i) => x.run())
setTimeout(() => { console.log("Finished!"); }, 10000);