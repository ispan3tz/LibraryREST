/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package md.library.isd.loan;

import com.google.gson.Gson;
import java.sql.SQLException;
import md.library.isd.user.User;

/**
 *
 * @author Dan
 */
public class LoanService {

    public static Loan instantiateLoan(String json) {
        Gson g = new Gson();
        return g.fromJson(json, Loan.class);
    }

    public static String toJson(Object obj) {
        Gson g = new Gson();
        return g.toJson(obj);
    }

    public static String showAllLoans() throws SQLException {
        return toJson(LoanDao.getAllLoans());
    }
    
    public static int addLoan(String loanData) throws SQLException{
        Loan loan = instantiateLoan(loanData);
        if(loan.getIdUser() == 0)
            loan.setIdUser(User.getCurrentUserId());
        return LoanDao.addLoan(loan);
    }
}
