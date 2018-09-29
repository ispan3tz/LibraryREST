package md.library.isd.loan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import md.library.isd.books.BookDao;
import md.library.isd.dbmanage.DBConnection;
import md.library.isd.dbmanage.DBManager;
import md.library.isd.test.Helper;
import md.library.isd.user.User;

public class LoanDao {
    final private static String SELECT = "loan.id_loan, loan.date_issued, "
            + "loan.date_for_return, loan.date_returned, loan.status, "
            + "loan.id_user, loan.id_book, book.title, book.author, "
            + "user.firstname, user.lastname";
    final private static String FROM = "loan JOIN book on loan.id_book = book.id_book "
            + "JOIN user on loan.id_user = user.id_user";
    private static Connection conn;
    private static DBConnection db;
    
    public static List<Loan> getAllLoans() throws SQLException {
        DBManager db = new DBManager();
        List<Loan> loanList = new ArrayList();
        ResultSet rs = db.select("SELECT " + SELECT + " FROM " + FROM);
        while (rs.next()) {
            loanList.add(instantiateLoan(rs));
        }
        if(db!=null) db.destroy();
        return loanList;
    }

    public Loan getLoanById(int id) throws SQLException {
        DBManager db = new DBManager();
        ResultSet rs = db.select("SELECT "+SELECT+" FROM "+ FROM +" WHERE id_loan = " + id);
        rs.next();
        Loan loan = instantiateLoan(rs);
        db.destroy();
        return loan;
    }

    public Loan getLoanByDateIssued(Date dateIssued) throws SQLException {
        DBManager db = new DBManager();
        ResultSet rs = db.select("SELECT * FROM "+ FROM +" WHERE date_issued=" + dateIssued);
        rs.next();
        Loan loan = instantiateLoan(rs);
        db.destroy();
        return loan;
    }

    public Loan getLoanByDateForReturn(Date dateForReturn) throws SQLException {
        DBManager db = new DBManager();
        ResultSet rs = db.select("SELECT "+SELECT+" FROM "+ FROM +" WHERE date_for_return=" + dateForReturn);
        Loan loan = instantiateLoan(rs);
        db.destroy();
        return loan;
    }

    public Loan getLoanByDateReturned(Date dateReturned) throws SQLException {
        DBManager db = new DBManager();
        ResultSet rs = db.select("SELECT "+SELECT+" FROM "+ FROM +" WHERE date_returned=" + dateReturned);
        rs.next();
        Loan loan = instantiateLoan(rs);
        db.destroy();
        return loan;
    }

    public static List<Loan> getLoanByIdBook(int idBook) throws SQLException {
        db = new DBConnection();
        conn = db.getConnection();
        List<Loan> loans = new ArrayList();
        String query = "SELECT "+SELECT+" FROM "+ FROM +" WHERE loan.id_book = ?";
        PreparedStatement getLoanByIdBook = conn.prepareStatement(query);
        getLoanByIdBook.setInt(1, idBook);
        ResultSet rs = getLoanByIdBook.executeQuery();
        while(rs.next()){
            loans.add(instantiateLoan(rs));
        }
        if(db != null) db.destroy();
        return loans;
    }

    public Loan getLoanByStatus(int status) throws SQLException {
        DBManager db = new DBManager();
        ResultSet rs = db.select("SELECT "+SELECT+" FROM "+ FROM +" WHERE status" + status);
        rs.next();
        Loan loan = instantiateLoan(rs);
        db.destroy();
        return loan;
    }
    
    public static List<Loan> getLoansByUserId(int loanUserId) throws SQLException {
        DBManager db = new DBManager();
        List<Loan> loanList = new ArrayList();
        ResultSet rs = db.select("SELECT " + SELECT + " FROM "+ FROM
                + " WHERE loan.id_user = " + loanUserId);
        while (rs.next()) {
            loanList.add(instantiateLoan(rs));
        }
        db.destroy();
        return loanList;
    }

    public static int addLoan(Loan loan) throws SQLException {
        db = new DBConnection();
        conn = db.getConnection();
        
        String addLoanQuery = "INSERT INTO loan "
                + "(date_issued, date_for_return, id_user, id_book, status) "
                + "VALUES(?,?,?,?,?)";
        int id = User.getCurrentUserId();
        PreparedStatement addLoan = conn.prepareStatement(addLoanQuery);
        addLoan.setDate(1, Helper.getDate());
        addLoan.setDate(2, Helper.getDateAfterMonth());
        if(loan.getIdUser() == 0){
            addLoan.setInt(3, User.getCurrentUserId());
        }else{
            addLoan.setInt(3, loan.getIdUser());
        }
        addLoan.setInt(4, loan.getIdBook());
        addLoan.setInt(5, loan.getStatus());
        int res1 = addLoan.executeUpdate();
        int res2 = 0;
        if(loan.getStatus() == 4)
            res2 = BookDao.updateBookStatus(loan.getIdBook(),0);
        if(db != null) db.destroy();
        return res1+res2;
    }

    public static int updateLoan(Loan loan) throws SQLException {
        db = new DBConnection();
        conn = db.getConnection();
        
        String query = "UPDATE loan SET `date_returned` = ? ,`status` = ? WHERE id_loan = ?";
        PreparedStatement updateLoan = conn.prepareStatement(query);
        if(loan.getStatus() == 3)updateLoan.setDate(1, Helper.getDate());
        else updateLoan.setNull(1, java.sql.Types.DATE);
        updateLoan.setInt(2, loan.getStatus());
        updateLoan.setInt(3, loan.getId());
        int res1 = updateLoan.executeUpdate();
        
        int res2 = 0;
        if(loan.getStatus() == 3)
            res2 = BookDao.updateBookStatus(loan.getIdBook(),1);
        
        db.destroy();
        return res1 + res2;
    }
    
    public static int deleteLoanById(int id) throws SQLException {
        db = new DBConnection();
        conn = db.getConnection();
        String query = "DELETE FROM loan WHERE id_loan = ? ";
        PreparedStatement deleteLoan = conn.prepareStatement(query);
        deleteLoan.setInt(1, id);
        int result = deleteLoan.executeUpdate();
        if(db!=null) db.destroy();
        return result;
    }

    private static Loan instantiateLoan(ResultSet rs) throws SQLException {
        Loan loan = new Loan();
        loan.setId(rs.getInt(1));
        loan.setDateIssued(rs.getDate(2));
        loan.setDateForReturn(rs.getDate(3));
        loan.setDateReturned(rs.getDate(4));
        loan.setStatus(rs.getInt(5));
        loan.setIdUser(rs.getInt(6));
        loan.setIdBook(rs.getInt(7));
        loan.setTitleBook(rs.getString(8));
        loan.setAuthorBook(rs.getString(9));
        loan.setUserFirstNm(rs.getString(10));
        loan.setUserLastNm(rs.getString(11));
        return loan;
    }
    
    public static void main(String[] args) {
        String str = "SELECT " + SELECT + " FROM " + FROM;
        System.out.println(str);
    }
}
