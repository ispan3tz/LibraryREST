/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package md.library.isd.category;

/**
 *
 * @author danul
 */
import com.google.gson.Gson;
import java.io.Serializable;
import java.util.Date;
import java.lang.*;
import java.util.*;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

public class Category {

    private int id_category;

    private String name_category;

    public Category() {
    }

    public int getIDcategory() {
        return id_category;
    }

    public void setIDcategory(int id_category) {
        this.id_category = id_category;

    }

    public String getNameCategory() {
        return name_category;
    }

    public void setNameCategory(String name_category) {
        this.name_category = name_category;

    }
    
    public String toJson(Category category) {
        Gson gson = new Gson();
        return gson.toJson(category);
    }

}
