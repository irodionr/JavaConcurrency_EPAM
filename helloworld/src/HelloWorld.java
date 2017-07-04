public class HelloWorld {

    public static void main(String[] args) {

        Thread currentThread = Thread.currentThread();

        System.out.println("currentThread.getName(): " + currentThread.getName());
        System.out.println("currentThread.getThreadGroup().getName(): " + currentThread.getThreadGroup().getName());

        Thread thread = new Thread(() -> {
            System.out.println("Hello, ");
            System.out.println("Thread.currentThread().getName(): " + Thread.currentThread().getName());
            System.out.println("Thread.currentThread().getThreadGroup().getName(): " + Thread.currentThread().getThreadGroup().getName());
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("World!");
        });
        thread.start();

        System.out.println("main: finish");

    }

}
