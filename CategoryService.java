/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package md.library.isd.category;

import com.google.gson.Gson;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import md.library.isd.books.Book;

/**
 *
 * @author danul
 */
public class CategoryService {

    public static Category instantiateCategory(String json) {
        Gson g = new Gson();
        return g.fromJson(json, Category.class);
    }

    public static String toJson(Category category) {
        Gson g = new Gson();
        return g.toJson(category);
    }

    public static String showAllCateg() throws SQLException {
        Gson g = new Gson();
        List<Category> categs = CategoryDao.getAllCateg();
        return g.toJson(categs);
    }

    public static boolean createCategory(String categData) throws SQLException {
        Category category = CategoryService.instantiateCategory(categData);
        CategoryDao.addCategory(category);
        return true;
    }

}
