package com.shaff.carshop.exceptions;

public class ApplicationException extends Exception {

    public ApplicationException(){
        super();
    }

    public ApplicationException(String message, Throwable ex){
        super(message, ex);
    }

    public ApplicationException(String message){
        super(message);
    }
}
