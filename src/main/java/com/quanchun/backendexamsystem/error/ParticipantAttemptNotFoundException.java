package com.quanchun.backendexamsystem.error;

public class ParticipantAttemptNotFoundException extends Exception{
    public ParticipantAttemptNotFoundException(){super();}

    public ParticipantAttemptNotFoundException(String message){super(message);}
    public ParticipantAttemptNotFoundException(String message, Throwable cause){super(message, cause);}
    public ParticipantAttemptNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace){super(message, cause, enableSuppression, writableStackTrace);}
}
