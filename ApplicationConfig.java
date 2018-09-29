package md.library.isd.test;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author 
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(md.library.isd.books.BookResource.class);
        resources.add(md.library.isd.category.CategoryResource.class);
        resources.add(md.library.isd.exception.GenericExceptionMapper.class);
        resources.add(md.library.isd.loan.LoanResource.class);
        resources.add(md.library.isd.security.AuthResource.class);
        resources.add(md.library.isd.security.CORSFilter.class);
        resources.add(md.library.isd.security.SecurityFilter.class);
        resources.add(md.library.isd.test.LibraryResource.class);
        resources.add(md.library.isd.user.UserResource.class);
    }
}