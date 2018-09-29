/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package md.library.isd.security;

import java.sql.SQLException;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import md.library.isd.test.Helper;
import md.library.isd.user.UserDao;

/**
 * REST Web Service
 *
 * @author 
 */
@Path("auth")
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource {

    @GET
    @Path("/{usernmae}")
    public Response getUserRole(@PathParam("usernmae") String username) throws SQLException {
        int userRole = UserDao.getUserByUsername(username).getRole();
        return Response.ok()
                .entity("{\"Role\":\"" + userRole + "\"}")
                .build();
    }
    
}
