package com.quanchun.backendexamsystem.error;

public class QuestionExistsException extends Exception{
    public QuestionExistsException(){super();}

    public QuestionExistsException(String message){super(message);}
    public QuestionExistsException(String message, Throwable cause){super(message,cause);}
    public QuestionExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace){super(message, cause, enableSuppression, writableStackTrace);}
}
