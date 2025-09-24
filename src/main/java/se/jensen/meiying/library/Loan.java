package se.jensen.meiying.library;

import java.time.LocalDateTime;

public class Loan {
    private LocalDateTime loanDate;
    private Borrower borrower;
    private Book book;

    public Loan(LocalDateTime loanDate) {
        this.loanDate = loanDate;
    }

    public void setBorrower(Borrower borrower) {
        this.borrower = borrower;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @Override
    public String toString() {
        return book.getTitle() + " (ISBN: " + book.getIsbn() + ") has been loaned by " +
                borrower.getName() + ".";
    }

}
