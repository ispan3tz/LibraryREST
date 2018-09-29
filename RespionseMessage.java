/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package md.library.isd.exception;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author danul
 */
@XmlRootElement
public class RespionseMessage {

    private String message;
    private String code;

    public RespionseMessage() {
    }

    public RespionseMessage(String message, String code) {
        this.message = message;
        this.code = code;

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
