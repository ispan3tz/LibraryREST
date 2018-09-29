package md.library.isd.loan;

import com.google.gson.Gson;
import java.io.*;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author 
 */
public class Loan{
    private int id;
    private Date dateIssued;
    private Date dateForReturn;
    private Date dateReturned;
    private int idUser;
    private int idBook;
    private String userNm;
    private String userFirstNm;
    private String userLastNm;
    private String titleBook;
    private String authorBook;
    private int status;
    

    public Loan() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDateIssued() {
        return dateIssued;
    }

    public void setDateIssued(Date dateIssued) {
        this.dateIssued = dateIssued;
    }

    public Date getDateForReturn() {
        return dateForReturn;
    }

    public void setDateForReturn(Date dateForReturn) {
        this.dateForReturn = dateForReturn;
    }

    public Date getDateReturned() {
        return dateReturned;
    }

    public void setDateReturned(Date dateReturned) {
        this.dateReturned = dateReturned;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdBook() {
        return idBook;
    }

    public void setIdBook(int idBook) {
        this.idBook = idBook;
    }

    public String getUserNm() {
        return userNm;
    }

    public void setUserNm(String userNm) {
        this.userNm = userNm;
    }

    public String getTitleBook() {
        return titleBook;
    }

    public void setTitleBook(String titleBook) {
        this.titleBook = titleBook;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUserFirstNm() {
        return userFirstNm;
    }

    public void setUserFirstNm(String userFirstNm) {
        this.userFirstNm = userFirstNm;
    }

    public String getUserLastNm() {
        return userLastNm;
    }

    public void setUserLastNm(String userLastNm) {
        this.userLastNm = userLastNm;
    }
    
    public String toJson(Loan loan) {
        Gson gson = new Gson();
        return gson.toJson(loan);
    }
    
    public String getAuthorBook() {
        return authorBook;
    }

    public void setAuthorBook(String authorBook) {
        this.authorBook = authorBook;
    }
}
