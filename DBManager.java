/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package md.library.isd.dbmanage;

import java.sql.*;

import org.apache.log4j.Logger;

/**
 * @author danul
 */
public class DBManager {

    static Logger log = Logger.getLogger(DBManager.class.getName());
    private Connection conn;
    private DBConnection db;

    public DBManager() {
        db = new DBConnection();
        conn = db.getConnection();
    }

    /**
     * Executing query statements on library database
     *
     * @param sql The query statement
     * @return Returns 0 if no statement was executed otherwise the number of
     * rows affected
     */
    public ResultSet select(String sql) throws SQLException {
        Statement stmt = conn.createStatement();
        boolean selected = stmt.execute(sql);
        ResultSet rs = stmt.getResultSet();
        if (selected) {
            return rs;
        } else {
            throw new SQLException();
        }
    }

    public int modify(String sql) throws SQLException {
        try {
            Statement stmt = conn.createStatement();
            int result = stmt.executeUpdate(sql);//int are val
            return result;
        } catch (SQLException ex) {
            log.error(ex.getMessage());
            throw new SQLException();
        }
    }

    public static Connection getConnection() {
        DBConnection db = new DBConnection();
        return db.getConnection();
    }

    /**
     * Destroy database manager
     */
    public void destroy() {
        if (db != null) {
            db.destroy();
        }
    }

}
