package com.quanchun.backendexamsystem.error;

public class RegisterQuizzNotFoundException extends Exception{
    public RegisterQuizzNotFoundException(){
        super();
    }

    public RegisterQuizzNotFoundException(String message){
        super(message);
    }

    public RegisterQuizzNotFoundException(String message, Throwable cause){
        super(message, cause);
    }

    public RegisterQuizzNotFoundException(Throwable cause){
        super(cause);
    }

    protected RegisterQuizzNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace){
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
