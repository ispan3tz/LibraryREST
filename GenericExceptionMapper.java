/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package md.library.isd.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.apache.log4j.Logger;

/**
 *
 * @author danul
 */
@Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable>  {
static Logger log = Logger.getLogger(GenericExceptionMapper.class.getName());
    @Override
    public Response toResponse(Throwable ex) {
        String error = "\r\n\t\t";
        for(StackTraceElement s: ex.getStackTrace()){
           error += s.toString() + "\r\n\t\t";
        }
        log.error(ex.getMessage() + error);
        
         RespionseMessage errorMessage = new RespionseMessage(ex.getMessage(), "500");
                return Response.status(Status.INTERNAL_SERVER_ERROR)
                .entity(errorMessage)
                .build();
    }
    
}
