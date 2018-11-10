package com.shaff.carshop.exceptions;

public class DBException extends ApplicationException {

    public DBException(){
        super();
    }

    public DBException(String message, Throwable cause){
        super(message, cause);
    }

    public DBException(String message){
        super(message);
    }
}
