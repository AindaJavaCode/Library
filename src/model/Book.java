package model;

public class Book {

    private int bookID;
    private String bookName;
    private int num_pages;
    private String bookAuthor;
    private boolean isRare;
    private boolean isBorrowed;
    private int isBorrowedByCustomerId;

    public Book(int bookID, String bookName, int num_pages, String bookAuthor, boolean isRare, boolean isBorrowed, int isBorrowedByCustomerId){
        this.bookID = bookID;
        this.bookName = bookName;
        this.num_pages = num_pages;
        this.bookAuthor = bookAuthor;
        this.isRare = isRare;
        this.isBorrowed = isBorrowed;
        this.isBorrowedByCustomerId = isBorrowedByCustomerId;
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public int getNum_pages() {
        return num_pages;
    }

    public void setNum_pages(int num_pages) {
        this.num_pages = num_pages;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public boolean isRare() {
        return isRare;
    }

    public void setRare(boolean rare) {
        isRare = rare;
    }

    public boolean isBorrowed() {
        return isBorrowed;
    }

    public void setBorrowed(boolean borrowed) {
        isBorrowed = borrowed;
    }

    public int getIsBorrowedByCustomerId() {
        return isBorrowedByCustomerId;
    }

    public void setIsBorrowedByCustomerId(int isBorrowedByCustomerId) {
        this.isBorrowedByCustomerId = isBorrowedByCustomerId;
    }
}
