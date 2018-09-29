package md.library.isd.user;

import md.library.isd.security.PasswordGenerator;
import com.google.gson.Gson;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;

/**
 * @author 
 */
public class UserService {
    
    public static User createUser(String userData) throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException{
        User user = instantiateUser(userData);
        String salt = PasswordGenerator.getSalt();
        user.setSalt(salt);
        String password = PasswordGenerator.generatePassword(user.getPassword(), salt);
        user.setPassword(password);
        return user;
    }
    
    public static User instantiateUser(String json){
        Gson g = new Gson();
        return g.fromJson(json, User.class);
    }
}
