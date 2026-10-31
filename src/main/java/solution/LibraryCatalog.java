package solution;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple library catalog.
 * This class is NOT thread-safe.
 */
public class LibraryCatalog {
    private final List<Book> books = new ArrayList<>();
    private final List<String> auditLog = new ArrayList<>();

    /** Adds a book to the catalog. */
    public void addBook(String title, String author) {
        books.add(new Book(title, author));
        logChange("Added: " + title);
    }

    /** Checks out a book if available. */
    public boolean checkoutBook(String title) {
        for (Book book : books) {
            if (book.getTitle().equals(title) && !book.isCheckedOut()) {
                book.checkout();
                logChange("Checked out: " + title);
                return true;
            }
        }
        return false;
    }

    /** Returns a checked-out book. */
    public boolean returnBook(String title) {
        for (Book book : books) {
            if (book.getTitle().equals(title) && book.isCheckedOut()) {
                book.returnToLibrary();
                logChange("Returned: " + title);
                return true;
            }
        }
        return false;
    }

    /** Prints all books available for checkout. */
    public void printAvailableBooks() {
        System.out.println(Thread.currentThread().getName()
                + " checking available books:");
        for (Book book : books) {
            if (!book.isCheckedOut()) {
                System.out.println("  " + book);
            }
        }
    }

    /** Records a change to the catalog. */
    protected void logChange(String message) {
        auditLog.add(message);
    }

    /** Prints all audit log entries. */
    public void printLog() {
        for (String message : auditLog) {
            System.out.println(message);
        }
    }
}
