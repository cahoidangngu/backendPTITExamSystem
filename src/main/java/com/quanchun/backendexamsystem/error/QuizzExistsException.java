package com.quanchun.backendexamsystem.error;

public class QuizzExistsException extends Exception{
    public QuizzExistsException(){super();}

    public QuizzExistsException(String message){super(message);}
    public QuizzExistsException(String message, Throwable cause){super(message,cause);}
    public QuizzExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace){super(message, cause, enableSuppression, writableStackTrace);}
}
