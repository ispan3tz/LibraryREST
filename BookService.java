/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package md.library.isd.books;

import com.google.gson.Gson;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import md.library.isd.user.UserService;

public class BookService  {

    public static Book instantiateBook(String json) {
        Gson g = new Gson();
        return g.fromJson(json, Book.class);
    }

    public static String toJson(Book book) {
        Gson g = new Gson();
        return g.toJson(book);
    }

    public static String showAllBooks() throws SQLException {
     Gson g = new Gson();
     List<Book> books = BookDao.getAllBooks();
     return g.toJson(books);}
    
     public static String showOneBook(String id_book) throws SQLException{
        int bid = Integer.parseInt(id_book);
        return toJson(BookDao.getBookById(bid));
    }
    
    public static boolean createBook (String bookData) throws SQLException{
        Book book = BookService.instantiateBook(bookData);
        BookDao.addBook(book);
        return true;
    }
    
    public Book getBookById(int id_book, String json){
        
        Gson g = new Gson();
        return g.fromJson(json, Book.class);
    
        }
    
    
    
    

}


