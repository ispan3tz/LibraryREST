package md.library.isd.user;

import com.google.gson.Gson;
import java.util.Date;
import java.util.List;
import md.library.isd.loan.Loan;

/**
 * User class model
 *
 * @author danul
 */
public class User {
    private static int currentUserId;
    private int userID;
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private int role = 0;
    private int status = 0;
    private Date dateIssued = new Date();
    private Date dateLastLogin = new Date();
    private String salt;
    private List<Loan> loans;

    public User() {
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setDateIssued(Date dateIssued) {
        this.dateIssued = dateIssued;
    }

    public void setDateLastLogin(Date dateLastLogin) {
        this.dateLastLogin = dateLastLogin;
    }
    
    public void setLoans(List<Loan> loans) {
        this.loans = loans;
    }

    public int getUserID() {
        return userID;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public int getRole() {
        return role;
    }

    public int getStatus() {
        return status;
    }

    public Date getDateIssued() {
        return dateIssued;
    }

    public Date getDateLastLogin() {
        return dateLastLogin;
    }
    
    public String getSalt() {
        return salt;
    }
    
    public static void setCurrentUserId(int currentUserId) {
        User.currentUserId = currentUserId;
    }

    public static int getCurrentUserId() {
        return currentUserId;
    }
    
    public List<Loan> getLoans() {
        return loans;
    }
}
