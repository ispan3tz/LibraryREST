/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package md.library.isd.security;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import md.library.isd.user.User;
import md.library.isd.user.UserDao;

/**
 *
 * @author Saniok1122
 */
public class Authorizator {

    public static boolean authorize(String username, String pass, String method, String uri) throws SQLException {
        try {
            int count = uri.length() - uri.replace("/", "").length();
            if (count > 1) {
                uri = uri.substring(0, uri.lastIndexOf('/') + 1);
            }
            int permission = AuthDao.getPermission(method, uri);
            User user = UserDao.getUserByUsername(username);
            User.setCurrentUserId(user.getUserID());
            pass = PasswordGenerator.generatePassword(pass, user.getSalt());
            String upass = user.getPassword();
            int role = user.getRole();
            if (pass.equals(upass) && role >= permission) {
                UserDao.updateUserLoginDate(user.getUserID());
                return true;
            }
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            Logger.getLogger(Authorizator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
