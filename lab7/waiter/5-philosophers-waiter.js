const EventEmitter = require('events');
const { performance } = require('perf_hooks');

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
	release() {
		this.currentlyEating--;
	}
}

class Philosopher {
	constructor(name, waiter, leftFork, rightFork, thinkingTime, eatingTime, waitingTimes) {
		this.name = name
		this.waiter = waiter;
		this.leftFork = leftFork
		this.rightFork = rightFork
		this.running = true;
		this.thinkingTime = thinkingTime;
		this.eatingTime = eatingTime;
		this.waitingTimes = waitingTimes;
	}
	_log(msg) {
		if (DEBUG) console.log(`${this.name}: ${msg}`)
	}
	kill() {
		this.running = false;
	}
	waitForPermission(f) {
		if (this.waiter.allow(this.leftFork, this.rightFork)) {
			f();
		} else {
			setTimeout(() => this.waitForPermission(f), getWaitingTime(this.thinkingTime));
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
			let start = performance.now();
			this._log("Acquiring left fork");
			await this.leftFork.acquire();
			this._log("Acquiring right fork");
			await this.rightFork.acquire();
			await this.dine();
			this.waitingTimes.push(performance.now() - start);
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
			}, getWaitingTime(this.thinkingTime));
		})
	}
	dine() {
		this._log("Eating")
		return new Promise((resolve, reject) => {
			setTimeout(() => {
				resolve();
			}, getWaitingTime(this.eatingTime))
		})
	}
}

const getWaitingTime = time => Math.floor(time.time + (Math.random() * 2 * time.delta) - time.delta);

const DEBUG = false;

function run(N, thinkingTime, eatingTime, cb) {
	var forks = [];
	var philosophers = [];
	var waitingTimes = [];
	var waiter = new Waiter(N)
	for (let i = 0; i < N; i++)forks.push(new Fork());
	for (let i = 0; i < N - 1; i++)philosophers.push(new Philosopher(`Phil ${i}`, waiter, forks[i], forks[i + 1], thinkingTime, eatingTime, waitingTimes));
	philosophers.push(new Philosopher(`Phil last`, waiter, forks[forks.length - 1], forks[0], thinkingTime, eatingTime, waitingTimes));
	philosophers.forEach((x, i) => x.run())
	setTimeout(() => {
		philosophers.forEach((x, i) => x.kill());
		console.log(`${N}\t${thinkingTime.time}\t${eatingTime.time}\t${waitingTimes.reduce((acc, cur) => acc + cur) / waitingTimes.length}`.replace(/\./g, ","))
		cb();
	}, 5000);
}

function runN(N, MAX_N, thinkingTime, eatingTime) {
	run(N, thinkingTime, eatingTime, () => {
		if (N < MAX_N) {
			runN(N + 1, MAX_N, thinkingTime, eatingTime);
		}
	})
}

function runTT(N, thinkingTime, thinkingTimeMax, eatingTime) {
	run(N, thinkingTime, eatingTime, () => {
		if (thinkingTime.time < thinkingTimeMax) {
			runTT(N,
				{
					time: thinkingTime.time + thinkingTime.time / 10,
					delta: thinkingTime.delta + thinkingTime.delta / 10
				}, thinkingTimeMax, eatingTime);
		}
	})
}

function runET(N, thinkingTime, eatingTime, eatingTimeMax) {
	run(N, thinkingTime, eatingTime, () => {
		if (eatingTime.time < eatingTimeMax) {
			runET(N, thinkingTime, eatingTimeMax, {
				time: eatingTime.time + eatingTime.time / 10,
				delta: eatingTime.delta + eatingTime.delta / 10
			});
		}
	})
}

runN(2, 30, { time: 15, delta: 5 }, { time: 15, delta: 5 });
// runTT(5, { time: 6, delta: 0.6 }, 500, { time: 50, delta: 5 });
// runTT(5, { time: 50, delta: 5 }, { time: 6, delta: 0.6 }, 500);