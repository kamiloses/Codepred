package org.example.codepred.exception;

public class AnnouncementNotFoundException extends RuntimeException{
    public AnnouncementNotFoundException(String message) {
        super(message);
    }
}
