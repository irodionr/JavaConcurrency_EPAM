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
        philosophers[4] = new Philosopher("Philosopher 4", forks[4], forks[0]);

        for (int i = 0; i < philosophers.length; i++) {
            System.out.println("Starting Thread " + i);
            Thread t = new Thread(philosophers[i]);
            t.start();
        }
    }

}

class Philosopher extends Thread {

    private Fork leftFork;
    private Fork rightFork;
    private String name;

    public Philosopher(String name, Fork lFork, Fork rFork) {
        this.name = name;
        leftFork = lFork;
        rightFork = rFork;
    }

    public void eat() {
        if (!leftFork.inUse) {
            if (!rightFork.inUse) {
                leftFork.take();
                rightFork.take();

                System.out.println(name + " is eating");
                try {
                    Thread.sleep(1000);
                }
                catch (InterruptedException exc) {
                    System.err.println(exc.getMessage());
                }

                leftFork.release();
                rightFork.release();
            }
        }

        think();
    }

    public void think() {
        System.out.println(name + " is thinking");
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException exc) {
            System.err.println(exc.getMessage());
        }
    }

    public void run() {
        for (int i = 0; i < 10; i++) {
            eat();
        }
    }

}

class Fork {

    private String name;
    public boolean inUse;

    public Fork(String name) {
        this.name = name;
    }

    public void take() {
        System.out.println("Take " + name);
        this.inUse = true;
    }

    public void release() {
        System.out.println("Release " + name);
        this.inUse = false;
    }

}
