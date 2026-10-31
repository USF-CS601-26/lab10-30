package solution;

import java.util.concurrent.CountDownLatch;

/**
 * Demonstrates basic thread coordination using CountDownLatch.
 * Customers (threads) must wait until the main thread "opens" the library.
 *
 * Fill in code and discuss what happens at each step.
 */
public class LibraryDemo {

    public static void runLibrary() throws InterruptedException {
        System.out.println("Main thread: preparing to open the library...");
        ThreadSafeLibraryCatalog catalog = new ThreadSafeLibraryCatalog();
        System.out.println("Main thread: Stocking shelves with new books...");
        Thread.sleep(400);
        catalog.addBook("Dune", "Herbert");
        catalog.addBook("1984", "Orwell");
        catalog.addBook("Foundation", "Asimov");

        // Initially, count = 1 for CountDownLatch because the library is closed.
        // Once the main thread calls countDown(), the count reaches 0, and patrons can enter.
        CountDownLatch openLatch = new CountDownLatch(1);
        int numCustomers = 3;

        // Create customers (threads) that wait for the latch signal (to say that the library is open).
        for (int i = 1; i <= numCustomers; i++) {
            Thread t = new Thread(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + " waiting outside for library to open...");
                    openLatch.await(); // each customer thread waits until the library opens (until openLatch is 0)
                    System.out.println(Thread.currentThread().getName() + " entered the library.");
                    catalog.printAvailableBooks();
                    boolean success = catalog.checkoutBook("1984");
                    System.out.println(Thread.currentThread().getName()
                            + (success ? " successfully checked out 1984"
                            : " could not check out 1984"));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }, "Customer-" + i);
            t.start();
        }

        // Main thread prepares to open the library
        System.out.println("Main thread: unlocking doors...");
        Thread.sleep(400);
        System.out.println("Main thread: turning on lights...");
        Thread.sleep(400);

        // Open the library (signal all waiting patrons)
        System.out.println(System.lineSeparator() + "Library is now open! Customers may enter." + System.lineSeparator());
        openLatch.countDown(); // Open the library (signal all threads who were waiting)

    }


    static void main(String[] args) {
        try {
            runLibrary();
        }
        catch(InterruptedException e) {
            System.out.println(e);
        }
    }

}
