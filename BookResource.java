package md.library.isd.books;

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
 * @author Dan Tutunaru
 */
@Path("books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookResource {

    static Logger log = Logger.getLogger(BookResource.class.getName());

    @GET
    public Response getBooks(@QueryParam("title") String title, @QueryParam("categ") String categ) throws Exception {
        BasicConfigurator.configure();

        if (title != null) {
            String allBooksbytitle = Helper.toJson(BookDao.getBookByTitle(title));
            log.info("got all books by title!");
            return Response.ok(allBooksbytitle).build();
        }else if(categ != null){
            int idCategory = Integer.parseInt(categ);
            String allBooksByCategory = Helper.toJson(BookDao.getAllBooksByCat(idCategory));
            return Response.ok(allBooksByCategory).build();
        }
        String allBooks = Helper.toJson(BookDao.getAllBooks());
        log.info("You got all the books!");
        return Response.ok(allBooks).build();
    }
    
    @GET
    @Path("/{id_book}")
    public Response getBook(@PathParam("id_book") String id_book) throws Exception {
        BasicConfigurator.configure();

        int bookid = Integer.parseInt(id_book);
        String book = Helper.toJson(BookDao.getBookById(bookid));
        log.info("You got book id");
        return Response.ok(book).build();
    }

    /**
     * PUT method for creating an instance of BookRest
     *
     * @param content representation for the resource
     */
    @PUT
    @Path("/{id_book}")
    public static Response updateBook(@PathParam("id_book") String id_book, String bookData) throws SQLException {
        BasicConfigurator.configure();

        Book book = BookService.instantiateBook(bookData);
        book.setIdbook(Integer.parseInt(id_book));
        BookDao.updateBook(book);
        log.info("You updated a book");
        return Response.status(Response.Status.CREATED)
                .entity(new RespionseMessage("201", "resource updated successfully"))
                .build();
    }

    //{'id_book':'5','isbn':'20050','author':'No','publisher':'LUmina','title':'java','year':2004,'description':'good', 'status':0}
    /**
     * POST method for updating an instance of BookRest
     *
     * @param content representation for the resource
     */
    @POST
    public Response insertBook(String bookData) throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException {
        BasicConfigurator.configure();
        BookService.createBook(bookData);
        log.info("You added a book ");
        return Response.status(Response.Status.CREATED)
                .entity(new RespionseMessage("201", "resource inserted successfully"))
                .build();
    }

    //{'isbn':'20050','author':'No','publisher':'LUmina','title':'java','year':2004,'description':'good', 'status':1}
    /**
     * DELETE method for updating an instance of BookRest
     *
     * @param content representation for the resource
     */
    @DELETE
    @Path("/{id_book}")
    public Response deleteJson(@PathParam("id_book") String id_book) throws SQLException {
        BasicConfigurator.configure();

        int bid = Integer.parseInt(id_book);
        BookDao.deleteBook(bid);
        log.info("You deleted a book");

        return Response.status(Response.Status.CREATED)
                .entity(new RespionseMessage("200", "resource deleted successfully"))
                .build();
    }
}
