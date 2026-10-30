package multithreading.countdownlatch;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * A thread-safe version of LibraryCatalog.
 * Allows concurrent readers but only one writer at a time.
 */
public class ThreadSafeLibraryCatalog extends LibraryCatalog {
        private final ReentrantReadWriteLock rwLock =
                new ReentrantReadWriteLock(true);

        @Override
        public void addBook(String title, String author) {
           // FILL IN CODE:

        }

        @Override
        public boolean checkoutBook(String title) {
            // FILL IN CODE:

            return false;
        }

        @Override
        public boolean returnBook(String title) {
            // FILL IN CODE:

            return false;
        }

        @Override
        public void printAvailableBooks() {
           // FILL IN CODE:

        }

        @Override
        protected void logChange(String message) {
           // FILL IN CODE:

        }

        @Override
        public void printLog() {
            // FILL IN CODE:

        }
}
