package com.quanchun.backendexamsystem.error;

public class QuestionNotFoundException extends Exception{
    public QuestionNotFoundException(){super();}

    public QuestionNotFoundException(String message){super(message);}
    public QuestionNotFoundException(String message, Throwable cause){super(message,cause);}
    public QuestionNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace){super(message, cause, enableSuppression, writableStackTrace);}
}
