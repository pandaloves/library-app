package se.jensen.meiying.library;

// Övning: Modellera en biblioteksapplikation

import javax.swing.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static Borrower currentBorrower;
    private static boolean loggedIn;
    private static List<Loan> loans = new ArrayList<>();

    private static void showMenu(List<Book> books) {
        String[] options = {"List all the books", "Borrow a book", "List all loans", "Log out"};
        int choice = JOptionPane.showOptionDialog(
                null,
                "Make a choice:",
                "Menu",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]
        );

        if (choice == 0) {
            StringBuilder booksList = new StringBuilder("Available books:\n");
            for (Book book : books) {
                boolean borrowed = loans.stream().anyMatch(loan -> loan.getBook().equals(book));
                booksList.append(book).append(borrowed ? " (borrowed)" : " (available)").append("\n");
            }
            JOptionPane.showMessageDialog(null, booksList.toString());

        } else if (choice == 1) {
            String isbn = JOptionPane.showInputDialog(null, "Enter ISBN of the book to borrow:");
            if (isbn == null || isbn.isBlank()) {
                return;
            }

            Book selectedBook = null;
            for (Book book : books) {
                if (book.getIsbn().equals(isbn)) {
                    selectedBook = book;
                    break;
                }
            }

            if (selectedBook == null) {
                JOptionPane.showMessageDialog(null, "No book found with ISBN: " + isbn);
                return;
            }

            boolean alreadyBorrowed = false;
            for (Loan loan : loans) {
                if (loan.getBook().equals(selectedBook)) {
                    alreadyBorrowed = true;
                    System.out.println(selectedBook.getTitle() + " has already been borrowed.");
                    break;
                }
            }

            if (!alreadyBorrowed) {
                Loan loan = new Loan(LocalDateTime.now());
                loan.setBook(selectedBook);
                loan.setBorrower(currentBorrower);
                loans.add(loan);
                currentBorrower.getLoans().add(loan);

                JOptionPane.showMessageDialog(null, "You borrowed: " + selectedBook.getTitle());
            }
        } else if (choice == 2) {
            if (loans.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No active loans.");
            } else {
                StringBuilder loanList = new StringBuilder("Active loans:\n");
                for (Loan loan : loans) {
                    loanList.append(loan).append("\n");
                }
                JOptionPane.showMessageDialog(null, loanList.toString());
            }
        } else if (choice == 3) {
            loggedIn = false;
            currentBorrower = null;
            JOptionPane.showMessageDialog(null, "You have logged out.");
        }
    }

        /*
    private static List<Book> loadBooksFromFile(String filename) {
        List<Book> books = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                books.add(new Book(parts[0], parts[1], new Author(parts[2])));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return books;
    }

    private static void saveBooksToFile(List<Book> books, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Book b : books) {
                writer.write(b.getTitle() + ";" + b.getIsbn() + ";" + b.getAuthor().getName());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
*/

    public static void main(String[] args) {
        List<Borrower> borrowers = new ArrayList<>();
        borrowers.add(new Borrower("tom", "tom000", "Tom"));
        borrowers.add(new Borrower("mia", "mia000", "Mia"));
        borrowers.add(new Borrower("june", "june000", "June"));

        List<Book> books = new ArrayList<>();
        //  List<Book> books = loadBooksFromFile("books.txt");

        /*
        if (books.isEmpty()) {
            books.add(new Book("Hero", "0000", new Author("Peter")));
            books.add(new Book("West", "0001", new Author("Joe")));
            books.add(new Book("China", "0002", new Author("Mia")));

             saveBooksToFile(books, "books.txt");
         }
         */

        // System.out.println(borrowers);

        books.add(new Book("Hero", "0000", new Author("Peter")));
        books.add(new Book("West", "0001", new Author("Joe")));
        books.add(new Book("China", "0002", new Author("Mia")));
        // System.out.println(books);

        boolean running = true;
        while (running) {
            loggedIn = false;
            currentBorrower = null;

            while (!loggedIn) {
                String newUsername = JOptionPane.showInputDialog(null, "Enter username:");
                String newPassword = JOptionPane.showInputDialog(null, "Enter password:");

                if (newUsername == null || newPassword == null) {
                    System.out.println("Username or password cannot be empty.");
                    running = false;
                    break;
                }

                boolean foundUser = false;

                for (Borrower borrower : borrowers) {
                    if (borrower.getUsername().equals(newUsername) &&
                            borrower.getPassword().equals(newPassword)) {
                        currentBorrower = borrower;
                        loggedIn = true;
                        foundUser = true;
                        break;
                    }
                }

                if (foundUser) {
                    System.out.println("Logged in successfully! Welcome " + currentBorrower.getName());
                    while (loggedIn) {
                        showMenu(books);
                    }
                } else {
                    System.out.println("Invalid username or password!");
                }
            }
        }
    }
}