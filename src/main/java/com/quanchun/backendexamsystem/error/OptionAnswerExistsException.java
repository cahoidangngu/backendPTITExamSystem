package com.quanchun.backendexamsystem.error;

public class OptionAnswerExistsException extends Exception{
    public OptionAnswerExistsException(){super();}

    public OptionAnswerExistsException(String message){super(message);}
    public OptionAnswerExistsException(String message, Throwable cause){super(message,cause);}
    public OptionAnswerExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace){super(message, cause, enableSuppression, writableStackTrace);}
}
