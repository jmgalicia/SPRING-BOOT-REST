package com.jmgalicia.exception;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class RestErrorResponse {
    public final String detail;
    public final String message;

    public RestErrorResponse(Exception ex, String detail) {
        this.message = ex.getLocalizedMessage();
        this.detail = detail;
    }
}
