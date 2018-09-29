/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package md.library.isd.security;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import md.library.isd.dbmanage.DBConnection;
import md.library.isd.dbmanage.DBManager;

/**
 *
 * 
 */
public class AuthDao {
    private static Connection conn;
    private static DBConnection db;
    
    public static int getPermission(String method, String path) throws SQLException {
        db = new DBConnection();
        conn = db.getConnection();
        String query = "SELECT `permission` FROM `permission` WHERE `link` = ? AND `method` = ?";
        PreparedStatement getPermission = conn.prepareStatement(query);
        getPermission.setString(1, path);
        getPermission.setString(2, method);
        int permission = 2;
        if(getPermission.execute()){
            ResultSet resultSet = getPermission.getResultSet();
            resultSet.next();
            permission = resultSet.getInt(1);
        }
        if(db != null) db.destroy();
        return permission;
    }
}
