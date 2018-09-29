package md.library.isd.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import md.library.isd.dbmanage.DBConnection;
import md.library.isd.loan.LoanDao;
import md.library.isd.loan.LoanService;
import md.library.isd.test.Helper;

/**
 *
 * @author 
 */
public class UserDao {

    private static Connection conn;
    private static DBConnection db;

    public static List<User> getAllUsers() throws SQLException {
        List<User> userList = new ArrayList();
        try {
            db = new DBConnection();
            conn = db.getConnection();
            String query = "SELECT * FROM user";
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                userList.add(instantiateUser(resultSet));
            }
            statement.close();
        } finally {
            if (db != null) {
                db.destroy();
            }
        }
        return userList;
    }

    public static User getUserById(int id) throws SQLException {
        User user = new User();
        try {
            db = new DBConnection();
            conn = db.getConnection();
            conn.setAutoCommit(false);
            
            String query = "SELECT * FROM user WHERE id_user = ?";
            PreparedStatement getUserById = conn.prepareStatement(query);
            getUserById.setInt(1, id);
            if (getUserById.execute()) {
                ResultSet resultSet = getUserById.getResultSet();
                while (resultSet.next()) {
                    user = instantiateUser(resultSet);
                }
            }
            
            user.setLoans(LoanDao.getLoansByUserId(id));
            
            conn.commit();
        } finally {
            if (db != null) {
                db.destroy();
            }
        }
        return user;
    }

    public static User getUserByUsername(String username) throws SQLException {
        User user = new User();
        try {
            db = new DBConnection();
            conn = db.getConnection();
            String query = "SELECT * FROM user WHERE username = ? LIMIT 1";
            PreparedStatement getUserByUsername = conn.prepareStatement(query);
            getUserByUsername.setString(1, username);
            if (getUserByUsername.execute()) {
                ResultSet resultSet = getUserByUsername.getResultSet();
                resultSet.next();
                user = instantiateUser(resultSet);
            }
        } finally {
            if (db != null) {
                db.destroy();
            }
        }
        return user;
    }

    public static User getUserByEmail(String email) throws SQLException {
        User user = new User();
        try {
            db = new DBConnection();
            conn = db.getConnection();
            String query = "SELECT * FROM user WHERE email = ? LIMIT 1";
            PreparedStatement getUserByEmail = conn.prepareStatement(query);
            getUserByEmail.setString(1, email);
            if (getUserByEmail.execute()) {
                ResultSet resultSet = getUserByEmail.getResultSet();
                resultSet.next();
                user = instantiateUser(resultSet);
            }
        } finally {
            if (db != null) {
                db.destroy();
            }
        }
        return user;
    }

    public static int addUser(User user) throws SQLException {
        int result = 0;
        try {
            db = new DBConnection();
            conn = db.getConnection();
            String query = "INSERT INTO `user`"
                    + "(`username`, `firstname`, `lastname`, `email`, `password`, `role`, `date_issued`, `salt`) "
                    + "VALUES (?,?,?,?,?,?,?,?)";
            PreparedStatement addUser = conn.prepareStatement(query);
            addUser.setString(1, user.getUsername());
            addUser.setString(2, user.getFirstname());
            addUser.setString(3, user.getLastname());
            addUser.setString(4, user.getEmail());
            addUser.setString(5, user.getPassword());
            addUser.setInt(6, user.getRole());
            addUser.setDate(7, Helper.getDate());
            addUser.setString(8, user.getSalt());
            result = addUser.executeUpdate();
        } finally {
            if (db != null) {
                db.destroy();
            }
        }
        return result;
    }

    public static int updateUser(User user) throws SQLException {
        int result = 0;
        try {
            db = new DBConnection();
            conn = db.getConnection();
            String query = "UPDATE `user` SET `username` = ?,  `firstname` = ?, `lastname` = ?, `email` = ?, "
                    + "`password` = ?, `role` = ?, `salt` = ? WHERE id_user = ?";
            PreparedStatement updateUser = conn.prepareStatement(query);
            updateUser.setString(1, user.getUsername());
            updateUser.setString(2, user.getFirstname());
            updateUser.setString(3, user.getLastname());
            updateUser.setString(4, user.getEmail());
            updateUser.setString(5, user.getPassword());
            updateUser.setInt(6, user.getRole());
            updateUser.setString(7, user.getSalt());
            updateUser.setInt(8, user.getUserID());
            result = updateUser.executeUpdate();
        } finally {
            if (db != null) {
                db.destroy();
            }
        }
        return result;
    }

    public static int updateUserLoginDate(int userId) throws SQLException {
        int result = 0;
        try {
            db = new DBConnection();
            conn = db.getConnection();
            String query = "UPDATE `user` SET date_last_login = ? WHERE id_user = ?";
            PreparedStatement updateUserLoginDate = conn.prepareStatement(query);
            updateUserLoginDate.setDate(1, Helper.getDate());
            updateUserLoginDate.setInt(2, userId);
            result = updateUserLoginDate.executeUpdate();
        } finally {
            if (db != null) {
                db.destroy();
            }
        }
        return result;
    }

    public static int deleteUserById(int id) throws SQLException {
        int resultUser = 0;
        int resultLoan = 0;
        try {
            db = new DBConnection();
            conn = db.getConnection();
            conn.setAutoCommit(false);

            String queryLoan = "DELETE FROM loan WHERE id_user = ?";
            PreparedStatement deleteLoan = conn.prepareStatement(queryLoan);
            deleteLoan.setInt(1, id);
            resultLoan = deleteLoan.executeUpdate();

            String queryUser = "DELETE FROM user WHERE id_user = ?";
            PreparedStatement deleteUser = conn.prepareStatement(queryUser);
            deleteUser.setInt(1, id);
            resultUser = deleteUser.executeUpdate();

            conn.commit();
        } finally {
            if (db != null) {
                db.destroy();
            }
        }
        return resultLoan + resultUser;
    }

    private static User instantiateUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUserID(rs.getInt(1));
        user.setUsername(rs.getString(2));
        user.setFirstname(rs.getString(3));
        user.setLastname(rs.getString(4));
        user.setEmail(rs.getString(5));
        user.setPassword(rs.getString(6));
        user.setRole(rs.getInt(7));
        user.setStatus(rs.getInt(8));
        user.setDateIssued(rs.getDate(9));
        user.setDateLastLogin(rs.getDate(10));
        user.setSalt(rs.getString(11));
        return user;
    }
}
