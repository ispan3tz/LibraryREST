/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package md.library.isd.category;

import com.google.gson.Gson;
import md.library.isd.dbmanage.DBManager;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import md.library.isd.books.Book;
import md.library.isd.dbmanage.DBConnection;
import md.library.isd.test.Helper;

/**
 *
 * @author danul
 */
public class CategoryDao {

    private static Connection conn;
    private static DBConnection db;

    public static List<Category> getAllCateg() throws SQLException {
        db = new DBConnection();
        conn = db.getConnection();
        String query = "SELECT * FROM category";

        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        List<Category> categoryList = new ArrayList();
        while (resultSet.next()) {
            categoryList.add(instantiateCategory(resultSet));
        }
        statement.close();
        if (db != null) {
            db.destroy();
        }
        return categoryList;
    }

    public static List<Category> getCategorybyIDBook(int id_book) throws SQLException {
       Book book = new Book();
        db = new DBConnection();
        conn = db.getConnection();
        List<Category> Bookcategorylist = new ArrayList();
        String query = "SELECT * "
                + "FROM library.category b "
                + "JOIN library.relbookcategory r "
                + "ON b.id_category = r.id_category "
                + "WHERE r.id_book = ? ";
        PreparedStatement getCategorybyIDBook = conn.prepareStatement(query);
        getCategorybyIDBook.setInt(1, id_book);
        getCategorybyIDBook.execute();
        ResultSet resultSet = getCategorybyIDBook.getResultSet();
        while (resultSet.next()) { 
            Bookcategorylist.add(instantiateCategory(resultSet));
        }
        if (db != null) {
            db.destroy();
        }

        return Bookcategorylist;
    }

    public static int addCategory(Category category) throws SQLException {
        db = new DBConnection();
        conn = db.getConnection();

        String query = " INSERT INTO `category`(`name_category`) VALUES (?)";
        PreparedStatement addCategory = conn.prepareStatement(query);
        addCategory.setString(1, category.getNameCategory());
        int result = addCategory.executeUpdate();
        if (db != null) {
            db.destroy();
        }
        return result;

    }

    public static int updateCategory(Category category) throws SQLException {
        db = new DBConnection();
        conn = db.getConnection();
        String query = "UPDATE `category` SET `name_category`= ? WHERE id_category=?";
        PreparedStatement updateCategory = conn.prepareStatement(query);
        updateCategory.setString(1, category.getNameCategory());
        int result = updateCategory.executeUpdate();
        if (db != null) {
            db.destroy();
        }
        return result;
    }

    public static int deleteCategory(int id_category) throws SQLException {
        db = new DBConnection();
        conn = db.getConnection();
        conn.setAutoCommit(false);
        String queryRel = "DELETE FROM `relbookcategory` WHERE id_category= ?";
        PreparedStatement deleterel = conn.prepareStatement(queryRel);
        deleterel.setInt(1, id_category);
        int resultrel = deleterel.executeUpdate();

        String queryLoa = "DELETE FROM `category` WHERE id_category= ?";
        PreparedStatement deleteLoa = conn.prepareStatement(queryLoa);
        deleteLoa.setInt(1, id_category);
        int resultLoan = deleteLoa.executeUpdate();

        conn.commit();
        if (db != null) {
            db.destroy();
        }
        return resultrel + resultLoan;
    }

    private static Category instantiateCategory(ResultSet rs) throws SQLException {
        Category category = new Category();
        category.setIDcategory(rs.getInt(1));
        category.setNameCategory(rs.getString(2));
        return category;
    }
    
//    public static void main(String[] args) throws SQLException {
//        db = new DBConnection();
//        conn = db.getConnection();
//        String sql = "SELECT * FROM library.category b JOIN library.relbookcategory r ON b.id_category = r.id_category WHERE r.id_book = 116 ";
//        Statement statement = conn.createStatement();
//        ResultSet resultSet = statement.executeQuery(sql);
//        List<Category> Bookcategorylist = new ArrayList();
//        while(resultSet.next())
//            Bookcategorylist.add(instantiateCategory(resultSet));
//        db.destroy();
//        for(Category cat: Bookcategorylist)
//        System.out.println(cat.getNameCategory());
//    }
}
