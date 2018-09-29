package md.library.isd.books;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import md.library.isd.category.Category;
import md.library.isd.category.CategoryDao;
import md.library.isd.dbmanage.DBConnection;
import md.library.isd.loan.LoanDao;

import md.library.isd.test.Helper;

//Overview, Read
public class BookDao {
    private static Connection conn;
    private static DBConnection db;

    public static List<Book> getAllBooks() throws SQLException {
        db = new DBConnection();
        conn = db.getConnection();
        String query = "SELECT * FROM book";
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        List<Book> bookList = new ArrayList();
        while (resultSet.next()) {
            bookList.add(instantiateBook(resultSet));
        }
        statement.close();
        if (db != null) {
            db.destroy();
        }
        return bookList;
    }

    public static Book getBookById(int id_book) throws SQLException {
        db = new DBConnection();
        conn = db.getConnection();
        Book book = new Book();
        String query = "SELECT * FROM book WHERE id_book = ?";
        PreparedStatement getBookById = conn.prepareStatement(query);
        getBookById.setInt(1, id_book);
        if (getBookById.execute()) {
            ResultSet resultSet = getBookById.getResultSet();
            resultSet.next();
            book = instantiateBook(resultSet);
        }
        book.setCategories(CategoryDao.getCategorybyIDBook(book.getIdbook()));
        book.setLoans(LoanDao.getLoanByIdBook(id_book));
        if (db != null) {
            db.destroy();
        }
        return book;
    }

    public static List<Book> getBookByTitle(String title) throws SQLException {
        db = new DBConnection();
        conn = db.getConnection();
        List<Book> listBook = new ArrayList();
        
        String query = "SELECT * FROM book WHERE title LIKE ?";
        PreparedStatement getBookByTitle = conn.prepareStatement(query);
        getBookByTitle.setString(1, "%"+title+"%");
       
        if(getBookByTitle.execute()){
            ResultSet resultSet = getBookByTitle.getResultSet(); 
            while(resultSet.next()){
                listBook.add(instantiateBook(resultSet));
            }
        }
        if(db != null) db.destroy();
        return listBook;
    }
    
    public static List<Book> getAllBooksByCat(int id_category) throws SQLException {
        db = new DBConnection();
        conn = db.getConnection();
        List<Book> categoryBookList = new ArrayList();
      
        String query = "SELECT * FROM book JOIN relbookcategory "
                + "ON book.id_book = relbookcategory.id_book "
                + "WHERE relbookcategory.id_category= ?";
        PreparedStatement getAllBooksbyCat = conn.prepareStatement(query);
        getAllBooksbyCat.setInt(1, id_category);
        getAllBooksbyCat.execute();
        ResultSet resultSet = getAllBooksbyCat.getResultSet();
        while (resultSet.next()) {
            categoryBookList.add(instantiateBook(resultSet));
        }
        db.destroy();
        return categoryBookList;
    }

    private static Book instantiateBook(ResultSet rs) throws SQLException {
        Book book = new Book();
        book.setIdbook(rs.getInt(1));
        book.setIsbn(rs.getString(2));
        book.setAuthor(rs.getString(3));
        book.setPublisher(rs.getString(4));
        book.setTitle(rs.getString(5));
        book.setYear(rs.getInt(6));
        book.setDescription(rs.getString(7));
        book.setDate(rs.getDate(8));
        book.setStatus(rs.getInt(9));
        book.setImage(rs.getString(10));
        return book;
    }

    //Create
    public static int addBook(Book book) throws SQLException {
        db = new DBConnection();
        conn = db.getConnection();
        String query = "INSERT INTO `book`" + "(`isbn`, `author`, `publisher`, "
                + "`title`, `year`, `description`, `date_issued`, `status`, `image`)"
                + "VALUES (?,?,?,?,?,?,?,?,?)";
        PreparedStatement addBook = conn.prepareStatement(query);
        addBook.setString(1, book.getIsbn());
        addBook.setString(2, book.getAuthor());
        addBook.setString(3, book.getPublisher());
        addBook.setString(4, book.getTitle());
        addBook.setInt(5, book.getYear());
        addBook.setString(6, book.getDescription());
        addBook.setDate(7,  Helper.getDate());
        addBook.setInt(8, book.getStatus());
        addBook.setString(9, book.getImage());
        int result = addBook.executeUpdate();
    
        Statement statement = conn.createStatement();
        
        String query2 = "SELECT `id_book` FROM `book` WHERE `isbn` = ?";
        PreparedStatement selectBook = conn.prepareStatement(query2);
        selectBook.setString(1, book.getIsbn());
        ResultSet rs = selectBook.executeQuery();
        rs.next();
        int bookId = rs.getInt(1);
        
        int executeUpdate = 0;
        for (Category cat : book.getCategories()) {
            String query3 = "INSERT INTO `relbookcategory` (`id_book`, `id_category`) VALUES (?,?)";
            PreparedStatement insertCateg = conn.prepareStatement(query3);
            insertCateg.setInt(1, bookId);
            insertCateg.setInt(2, cat.getIDcategory());
            executeUpdate += insertCateg.executeUpdate();
        }
        if(db != null) db.destroy();
        return result;
    }

    //Delete
    public static int deleteBook(int id_book) throws SQLException {
       db = new DBConnection();
        conn = db.getConnection();
        conn.setAutoCommit(false);
        
        
        String queryRel = "DELETE FROM relbookcategory WHERE id_book = ?";
        PreparedStatement deleterel = conn.prepareStatement(queryRel);
        deleterel.setInt(1, id_book);
        int resultrel = deleterel.executeUpdate();
        
        String queryLoa ="DELETE FROM loan WHERE id_book = ?" ;
        PreparedStatement deleteLoa = conn.prepareStatement(queryLoa);
        deleteLoa.setInt(1, id_book);
        int resultLoan = deleteLoa.executeUpdate();
        
        
        
        String queryBoo = "DELETE FROM book WHERE id_book = ? ";
        PreparedStatement deleteBook = conn.prepareStatement(queryBoo);
        deleteBook.setInt(1, id_book);
        int resultBook = deleteBook.executeUpdate();
        
        conn.commit();
        if(db != null) db.destroy();
        return resultrel+resultLoan+resultBook;
       
    }

    //Update
    public static int updateBook(Book book) throws SQLException {
        db = new DBConnection();
        conn = db.getConnection();
        String query = "UPDATE `book` SET `isbn` = ?,  `author` = ?, `publisher` = ?, `title` = ?, "
                + "`year` = ?, `description` = ?, `status` = ?, `image` = ? WHERE id_book = ?";
        PreparedStatement updateBook = conn.prepareStatement(query);
        updateBook.setString(1, book.getIsbn());
        updateBook.setString(2, book.getAuthor());
        updateBook.setString(3, book.getPublisher());
        updateBook.setString(4, book.getTitle());
        updateBook.setInt(5, book.getYear());
        updateBook.setString(6, book.getDescription());
        updateBook.setInt(7, book.getStatus());
        updateBook.setString(8, book.getImage());
        updateBook.setInt(9, book.getIdbook());
        int result = updateBook.executeUpdate();
        if(db != null) db.destroy();
        return result;
        
    }

    public static int updateBookStatus(int id, int status) throws SQLException {
        db = new DBConnection();
        conn = db.getConnection();

        String updateBookStatusQ = "UPDATE book SET `status` = ? WHERE id_book = ?";
        PreparedStatement updateBookStatus = conn.prepareStatement(updateBookStatusQ);
        updateBookStatus.setInt(1, status);
        updateBookStatus.setInt(2, id);
        int result = updateBookStatus.executeUpdate();

        if (db != null) {
            db.destroy();
        }
        return result;
    }
}
