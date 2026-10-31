package solution;

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
            rwLock.writeLock().lock();
            try {
                super.addBook(title, author);
            } finally {
                rwLock.writeLock().unlock();
            }
        }

        @Override
        public boolean checkoutBook(String title) {
            rwLock.writeLock().lock();
            try {
                return super.checkoutBook(title);
            } finally {
                rwLock.writeLock().unlock();
            }
        }

        @Override
        public boolean returnBook(String title) {
            rwLock.writeLock().lock();
            try {
                return super.returnBook(title);
            } finally {
                rwLock.writeLock().unlock();
            }
        }

        @Override
        public void printAvailableBooks() {
            rwLock.readLock().lock();
            try {
                super.printAvailableBooks();
            } finally {
                rwLock.readLock().unlock();
            }
        }

        @Override
        protected void logChange(String message) {
            rwLock.writeLock().lock();
            try {
                super.logChange(message);
            } finally {
                rwLock.writeLock().unlock();
            }
        }

        @Override
        public void printLog() {
            rwLock.readLock().lock();
            try {
                super.printLog();
            } finally {
                rwLock.readLock().unlock();
            }
        }
}
