package md.library.isd.user;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
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
 * @author 
 */
@Path("users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    static Logger log = Logger.getLogger(UserResource.class.getName());

    /**
     * Get all the users form database
     * @return returns a JSON list of Users
     */
    @GET
    public static Response getAllUsers() throws SQLException {
        BasicConfigurator.configure();
        String allUsers = Helper.toJson(UserDao.getAllUsers());
        log.info("You got all the users!");
        return Response.ok(allUsers).build();
    }

    /**
     * Get user with a specific id
     * @return returns a JSON object of a User
     */
    @GET
    @Path("/{userId}")
    public static Response getUser(@PathParam("userId") String userId) throws SQLException {
        BasicConfigurator.configure();
        int id = Integer.parseInt(userId);
        String user = Helper.toJson(UserDao.getUserById(id));
        log.info("You got the id!");
        return Response.ok(user).build();
    }

    /**
     * PUT method for creating an instance of User
     * {'username':'Ciuccia','firstname':'Johan','lastname':'Miller','email':'mail12@mail.md','password':'1123','role':1}
     */
    @PUT
    @Path("/{userId}")
    public static Response updateUser(@PathParam("userId") String userId, String userData) throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException {
        BasicConfigurator.configure();
        User user = UserService.createUser(userData);
        user.setUserID(Integer.parseInt(userId));
        UserDao.updateUser(user);
        log.info("You updated the user!");
        return Response.status(Response.Status.CREATED)
                .entity(new RespionseMessage("201","resource updated successfully"))
                .build();
    }

    /**
     * POST method for inserting a new instance of User
     * {'username':'UserName','firstname':'Johan','lastname':'Miller','email':'mail2@mail.md','password':'1122','role':1}
     */
    @POST
    public static Response insertUser(String userData) throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException {
        BasicConfigurator.configure();
        User user = UserService.createUser(userData);
        UserDao.addUser(user);
        log.info("You inserted the user!");
        return Response.status(Response.Status.CREATED)
                .entity(new RespionseMessage("201","resource inserted successfully"))
                .build();
    }

    /**
     * DELETE method for deleting an instance of UserResource
     */
    @DELETE
    @Path("/{userId}")
    public static Response deleteJson(@PathParam("userId") String userId) throws SQLException {
        BasicConfigurator.configure();
        int uid = Integer.parseInt(userId);
        UserDao.deleteUserById(uid);
        log.info("You deleted the user!");
        return Response.status(Response.Status.CREATED)
                .entity(new RespionseMessage("200","resource deleted successfully"))
                .build();
    }
}
