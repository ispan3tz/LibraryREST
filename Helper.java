/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package md.library.isd.test;

import com.google.gson.Gson;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

/**
 *
 * @author danul
 */
public class Helper {
    
    public static String getCurrentDate(){
        return new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
    }
    
    public static Date getDate(){
        return Date.valueOf(LocalDate.now());
    }
    
    public static Date getDateAfterMonth(){
        return Date.valueOf(LocalDate.now().plusMonths(1));
    }
    
    public static String toJson(Object obj){
        Gson g = new Gson();
        return g.toJson(obj);
    }
    
}
