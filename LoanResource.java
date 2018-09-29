/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package md.library.isd.loan;

import java.sql.SQLException;
import javax.ws.rs.core.*;
import javax.ws.rs.*;
import md.library.isd.exception.RespionseMessage;
import md.library.isd.test.Helper;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/**
 * REST Web Service
 *
 * @author danul
 */
@Path("loans")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LoanResource {

    static Logger log = Logger.getLogger(LoanResource.class.getName());

    @GET
    public Response getLoans() throws SQLException {
        BasicConfigurator.configure();
        String allLoans = Helper.toJson(LoanDao.getAllLoans());
        log.info("You got all the loans!");
        return Response.ok(allLoans).build();
    }

    @GET
    @Path("/{userId}")
    public Response getLoansByIdUser(@PathParam("userId") String userId) throws SQLException {
        BasicConfigurator.configure();
        int id = Integer.parseInt(userId);
        String loans = Helper.toJson(LoanDao.getLoansByUserId(id));
        log.info("You got user loan by user id!");
        return Response.ok(loans).build();
    }

    @PUT
    @Path("/{loanId}")
    public Response updateLoan(@PathParam("loanId") String loanId, String loanData) throws SQLException {
        BasicConfigurator.configure();
        Loan loan = LoanService.instantiateLoan(loanData);
        loan.setId(Integer.parseInt(loanId));
        LoanDao.updateLoan(loan);
        log.info("You updated a loan!");
        return Response.status(Response.Status.CREATED)
                .entity(new RespionseMessage("201", "resource updated successfully"))
                .build();
    }

    @POST
    public Response insertLoan(String loanData) throws SQLException {
        BasicConfigurator.configure();
        LoanService.addLoan(loanData);
        log.info("You created a loan!");
        return Response.status(Response.Status.CREATED)
                .entity(new RespionseMessage("201", "resource updated successfully"))
                .build();
    }

    @DELETE
    @Path("/{loanId}")
    public Response deleteLoan(@PathParam("loanId") String loanId) throws SQLException {
        BasicConfigurator.configure();
        int loanid = Integer.parseInt(loanId);
        LoanDao.deleteLoanById(loanid);
        log.info("You deleted a loan!");
        return Response.status(Response.Status.CREATED)
                .entity(new RespionseMessage("200", "resource deleted successfully"))
                .build();
    }

}
