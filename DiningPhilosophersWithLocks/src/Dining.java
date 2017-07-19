import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Dining {

    public static void main(String[] args) {

        Fork[] forks = new Fork[5];
        for (int i = 0; i < forks.length; i++) {
            forks[i] = new Fork("Fork " + i);
        }

        Philosopher[] philosophers = new Philosopher[5];
        for (int i = 0; i < philosophers.length - 1; i++) {
            philosophers[i] = new Philosopher("Philosopher " + i, forks[i], forks[i + 1]);
        }
        philosophers[4] = new Philosopher("Philosopher " + 4, forks[4], forks[0]);

        for (int i = 0; i < philosophers.length; i++) {
            System.out.println("Starting Thread " + i);
            Thread t = new Thread(philosophers[i]);
            t.start();
        }

    }

}

class Philosopher implements Runnable {

    private String name;
    private final Fork leftFork, rightFork;

    public Philosopher(String _name, Fork _leftFork, Fork _rightFork) {
        name = _name;
        leftFork = _leftFork;
        rightFork = _rightFork;
    }

    public void eat() {

        if (!leftFork.isTaken() && !rightFork.isTaken()) {
            leftFork.take();
            rightFork.take();

            System.out.println(name + " is eating");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            leftFork.release();
            rightFork.release();
        }

        think();

    }

    public void think() {

        System.out.println(name + " is thinking");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {

        for (int i = 0; i < 5; i++) {
            if (i != 1) {
                System.out.println(name + " has eaten " + i + " times");
            } else {
                System.out.println(name + " has eaten " + i + " time");
            }

            eat();
        }

    }

}

class Fork {

    private String name;
    private Lock lock;
    private boolean isTaken;

    public Fork(String _name) {

        name = _name;
        lock = new ReentrantLock();
        isTaken = false;

    }

    public void take() {

        lock.lock();
        isTaken = true;
        System.out.println("Take " + name);

    }

    public void release() {

        lock.unlock();
        isTaken = false;
        System.out.println("Release " + name);

    }

    public boolean isTaken() {

        return isTaken;

    }

}
