package com.quanchun.backendexamsystem.error;

public class UserExistsException extends Exception{
    public UserExistsException(){super();}

    public UserExistsException(String message){super(message);}
    public UserExistsException(String message, Throwable cause){super(message,cause);}
    public UserExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace){super(message, cause, enableSuppression, writableStackTrace);}
}
