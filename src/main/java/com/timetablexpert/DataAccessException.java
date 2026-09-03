package com.timetablexpert;

/**
 * Unchecked wrapper for a {@link java.sql.SQLException} that escaped the data layer.
 * Replaces the old "print the message, return null" pattern so failures surface
 * loudly instead of turning into a {@link NullPointerException} in the caller.
 */
public class DataAccessException extends RuntimeException {

    public DataAccessException(String message, Throwable cause) {
        super(message + (cause != null ? ": " + cause.getMessage() : ""), cause);
    }
}
