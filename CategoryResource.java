package md.library.isd.category;

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
 *
 * @author danul
 */
@Path("categs")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CategoryResource {

    static Logger log = Logger.getLogger(CategoryResource.class.getName());

    @GET
    public Response getCategs() throws Exception {
        BasicConfigurator.configure();

        String allCategs = Helper.toJson(CategoryDao.getAllCateg());

        log.info("You got the category ");
        return Response.ok(allCategs).build();
    }

    @POST
    public Response insertCateg(String categData) throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException {
        BasicConfigurator.configure();

        CategoryService.createCategory(categData);

        log.info("You inserted the category");
        return Response.status(Response.Status.CREATED)
                .entity(new RespionseMessage("201", "resource inserted successfully"))
                .build();
    }
    
    
    @PUT
    @Path("/{id_category}")
    public Response updateCategory(@PathParam("id_category") String id_category, String categData) throws SQLException {
        BasicConfigurator.configure();

        int zid = Integer.parseInt(id_category);
        Category category = CategoryService.instantiateCategory(categData);
        category.setIDcategory(zid);
        CategoryDao.updateCategory(category);
        log.info("You updated the category");
        return Response.status(Response.Status.CREATED).build();
    }

    @DELETE
    @Path("/{id_category}")
    public Response deleteCateg(@PathParam("id_category") String id_category) throws SQLException {
        BasicConfigurator.configure();

        int zid = Integer.parseInt(id_category);
        CategoryDao.deleteCategory(zid);
        log.info("You deleted the category");
       return Response.status(Response.Status.CREATED)
                .entity(new RespionseMessage("200", "resource deleted successfully"))
                .build();
    }
    
   
}
