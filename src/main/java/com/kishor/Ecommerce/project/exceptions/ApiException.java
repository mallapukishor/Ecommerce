package com.kishor.Ecommerce.project.exceptions;

import java.io.Serial;

public class ApiException extends RuntimeException {
    @Serial
    private static final long serialVersionUID=1l;
    public ApiException(){

    }
    public ApiException(String message){
        super(message);
    }

}
