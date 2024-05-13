package com.quanchun.backendexamsystem.error;

public class OptionAnswerNotFoundException extends Exception{
    public OptionAnswerNotFoundException(){super();}

    public OptionAnswerNotFoundException(String message){super(message);}
    public OptionAnswerNotFoundException(String message, Throwable cause){super(message,cause);}
    public OptionAnswerNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace){super(message, cause, enableSuppression, writableStackTrace);}
}
