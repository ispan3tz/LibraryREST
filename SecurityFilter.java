/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package md.library.isd.security;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import md.library.isd.exception.RespionseMessage;
import org.glassfish.jersey.internal.util.Base64;

@Provider
public class SecurityFilter implements ContainerRequestFilter {
    private final static String AUTHORIZATION_HEADER_KEY = "authorization";
    private final static String AUTHORIZATION_HEADER_PREFIX = "Basic ";
    @Override
    public void filter(ContainerRequestContext crc) throws IOException {
        List<String> authHearde = crc.getHeaders().get(AUTHORIZATION_HEADER_KEY);
        if (authHearde != null && authHearde.size() > 0) {
            try {
                StringTokenizer tokenizer = getBasicAuth(authHearde);
                String username = tokenizer.nextToken();
                String password = tokenizer.nextToken();
                if (Authorizator.authorize(username, password, crc.getMethod(), crc.getUriInfo().getPath())){
                    return;
                }else{
                    Response response = Response.status(Response.Status.UNAUTHORIZED)
                        .entity(new RespionseMessage("Unauthorized", "401"))
                        .build();
                    crc.abortWith(response);
                }
            } catch (SQLException ex) {
                Logger.getLogger(SecurityFilter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        Response response = Response.status(Response.Status.OK)
                .entity(new RespionseMessage("Unauthorized", "401"))
                .build();
                                    
        crc.abortWith(response);
    }
    
    private StringTokenizer getBasicAuth(List<String> authHearde){
        String authToken = authHearde.get(0);
        authToken = authToken.replaceFirst(AUTHORIZATION_HEADER_PREFIX, "");
        String decodedString = Base64.decodeAsString(authToken);
        return new StringTokenizer(decodedString, ":");
    }
}
