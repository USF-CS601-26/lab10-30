# CS601 Lab: Thread Safety and CountDownLatch

## Overview

In this lab, you will practice using `ReentrantReadWriteLock` to make an existing class thread-safe and `CountDownLatch` to coordinate multiple threads.

You are given a simple library catalog that allows customers to view available books, check out books, and return books. The original `LibraryCatalog` class is **not thread-safe**.

Your tasks are to:
1. Implement `ThreadSafeLibraryCatalog`, a thread-safe subclass of `LibraryCatalog`.
2. Complete `LibraryDemo` to simulate customers waiting for the library to open before attempting to check out books.


## Part 1: Implement ThreadSafeLibraryCatalog

Complete the class `ThreadSafeLibraryCatalog`, which extends `LibraryCatalog`.

Use a single `ReentrantReadWriteLock` to protect access to the shared library catalog.

Override each method from the parent class, LibraryCatalog. For each overridden method:

1. Acquire the appropriate lock.
2. Call the corresponding superclass method using `super`.
3. Release the lock in a `finally` block.

**Important:**
- Do not duplicate the implementation of the superclass methods.
- Use the same `ReentrantReadWriteLock` object for all methods.
- Make sure locks are always released, even when an exception occurs.
- Do not modify `LibraryCatalog` or `Book`.

**Question to answer:** When `addBook()` calls `super.addBook()`, the superclass invokes `logChange()`. Why can the current thread acquire the write lock again in the overridden `logChange()` method?

## Part 2: Complete LibraryDemo

The `LibraryDemo` class simulates three customers waiting outside a library.

The main thread first stocks the library with books, creates the customer threads, and then opens the library.

Use a `CountDownLatch` initialized with a count of 1.

### Task 1: Create customer threads

Complete the TODOs inside the loop that creates customer threads.

Each customer thread should:

1. Print a message indicating that it is waiting for the library to open.
2. Call `await()` on the latch to wait until the library opens.
3. Once released, print a message indicating that it has entered.
4. Print available books from the catalog.
5. Attempt to check out `"1984"` from the catalog.
6. Print whether the checkout was successful.
7. Handle `InterruptedException`.

Name the threads `Customer-1`, `Customer-2`, and `Customer-3`.

Make sure to start each thread.

### Task 2: Open the library

After the main thread finishes preparing the library:

1. Print `"Library is now open! Customers may enter."`
2. Call `countDown()` on the latch to allow the waiting customers to proceed.

## Part 3: Run and Observe

Run `LibraryDemo` several times and examine the output.

You should observe that:
- Customers wait until the main thread opens the library.
- After `countDown()` is called, all three customer threads can proceed.
- The order in which customers enter and perform operations is not guaranteed.
- Multiple customers may see `"1984"` as available, but only one can successfully check it out.

## Questions

1. Why does `printAvailableBooks()` use a read lock while `checkoutBook()` uses a write lock?
2. Can two threads hold the read lock at the same time? Can two threads hold the write lock at the same time?
3. What happens when the main thread calls `openLatch.countDown()`?
4. Does calling `openLatch.await()` decrease the latch count?

### Logging with Log4j

Modify `LibraryDemo` to use **Log4j 2** instead of `System.out.println()` to display messages about the library and customer threads.

The starter code already includes the logger:

```java
private static final Logger logger =
        LogManager.getLogger(LibraryDemo.class);
```

**Requirements:**

1. Replace all `System.out.println()` statements in `LibraryDemo` with appropriate Log4j logging statements.
2. Use `logger.info()` for normal application events, such as preparing to open the library, customers waiting, customers entering, and checkout results.
3. Use `logger.error()` to report exceptions. Include the exception object so that the stack trace is recorded.
4. Make sure `log4j2.xml` configuration file in `src/main/resources` does the following:
    - Displays **INFO and higher** logging levels on the console.
    - Writes **DEBUG and higher** logging levels to a file named `debug.log`.
    - Includes the timestamp, thread name, logging level, and message in each log entry.
5. Add at least one `logger.debug()` statement to provide additional diagnostic information that appears in the log file but not on the console.

**Note:** Log4j is thread-safe, so multiple customer threads can use the same logger concurrently without additional synchronization.