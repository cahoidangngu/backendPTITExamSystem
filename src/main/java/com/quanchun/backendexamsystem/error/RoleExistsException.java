package com.quanchun.backendexamsystem.error;

public class RoleExistsException extends Exception{
    public RoleExistsException(){super();}

    public RoleExistsException(String message){super(message);}
    public RoleExistsException(String message, Throwable cause){super(message,cause);}
    public RoleExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace){super(message, cause, enableSuppression, writableStackTrace);}
}
