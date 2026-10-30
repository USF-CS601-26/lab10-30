package multithreading.countdownlatch;

import java.util.concurrent.CountDownLatch;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Demonstrates basic thread coordination using CountDownLatch.
 * Customers (threads) must wait until the main thread "opens" the library.
 *
 * Fill in code and discuss what happens at each step.
 */
public class LibraryDemo {
    private static final Logger logger =
            LogManager.getLogger(LibraryDemo.class);

    public static void runLibrary() throws InterruptedException {
        System.out.println("Main thread: preparing to open the library...");

        LibraryCatalog catalog = new ThreadSafeLibraryCatalog();

        System.out.println("Main thread: Stocking shelves with new books...");
        Thread.sleep(400);

        catalog.addBook("Dune", "Herbert");
        catalog.addBook("1984", "Orwell");
        catalog.addBook("Foundation", "Asimov");

        // Count starts at 1 because the library is closed.
        // When the count reaches 0, waiting customers may enter.
        CountDownLatch openLatch = new CountDownLatch(1);

        int numCustomers = 3;

        // Create customer threads that wait for the library to open.
        for (int i = 1; i <= numCustomers; i++) {

            // TODO: Create a Thread named "Customer-" + i.
            // The thread should:
            // 1. Print that it is waiting for the library to open.
            // 2. Call the await() on the count down latch
            // 3. Print that it has entered the library.
            // 4. Print available books from the catlog
            // 5. Attempt to check out "1984".
            // 6. Print whether the checkout was successful.
            // 7. Handle InterruptedException

            // TODO: Start the thread.
        }

        // Main thread prepares to open the library.
        System.out.println("Main thread: unlocking doors...");
        Thread.sleep(400);

        System.out.println("Main thread: turning on lights...");
        Thread.sleep(400);

        // TODO: Print "Library is now open! Customers may enter."

        // TODO: Call countDown() on openLatch to release waiting customers.
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
