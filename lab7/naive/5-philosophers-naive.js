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
		return new Promise((resolve, reject) => {
			this.waitOnLock(resolve)
		})
	}
	release() {
		this.isAcquired = false;
		this.lock.emit('unlocked');
	}
}

class Philosopher {
	constructor(name, leftFork, rightFork) {
		this.name = name
		this.leftFork = leftFork
		this.rightFork = rightFork
		this.running = true;
	}
	_log(msg) {
		console.log(`${this.name}: ${msg}`)
	}
	kill() {
		this.running = false;
	}
	async run() {
		while (this.running) {
			await this.think();
			this._log("Acquiring left fork");
			await this.leftFork.acquire();
			this._log("Acquiring right fork");
			await this.rightFork.acquire();
			await this.dine();
			this._log("Releasing forks");
			this.leftFork.release();
			this.rightFork.release();
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

var forks = []
var philosophers = []
for (let i = 0; i < 3; i++)forks.push(new Fork());
for (let i = 0; i < 2; i++)philosophers.push(new Philosopher(`Phil ${i}`, forks[i], forks[i + 1]));
philosophers.push(new Philosopher(`Phil last`, forks[forks.length - 1], forks[0]));
philosophers.forEach((x, i) => x.run())
setTimeout(() => { console.log("Finished!"); }, 10000);