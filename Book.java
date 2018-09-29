package md.library.isd.books;

import java.util.Date;
import java.util.List;
import md.library.isd.category.Category;
import md.library.isd.loan.Loan;

public class Book {

    private int id_book;

    private String isbn;

    private String author;

    private String publisher;

    private String title;

    private int year = 0;

    private String description;

    private Date date_issued = new Date();

    private int status = 0;
    
    private String image;

    private List<Category> categories;

    private List<Loan> loans;

    public Book() {
    }

    public int getIdbook() {
        return id_book;
    }

    public void setIdbook(int id_book) {
        this.id_book = id_book;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date_issued;
    }

    public void setDate(Date date_issued) {
        this.date_issued = date_issued;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public void setDate_issued(Date date_issued) {
        this.date_issued = date_issued;
    }

    public void setLoans(List<Loan> loans) {
        this.loans = loans;
    }

    public int getId_book() {
        return id_book;
    }

    public Date getDate_issued() {
        return date_issued;
    }

    public List<Loan> getLoans() {
        return loans;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
