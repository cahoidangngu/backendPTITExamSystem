package com.quanchun.backendexamsystem.error;

public class QuizzNotFoundException extends Exception{
    public QuizzNotFoundException(){super();}

    public QuizzNotFoundException(String message){super(message);}
    public QuizzNotFoundException(String message, Throwable cause){super(message,cause);}
    public QuizzNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace){super(message, cause, enableSuppression, writableStackTrace);}
}
