package com.bisnode.bhc.domain.exception;

/**
 * Created by sahm on 28.08.17.
 */
public class InvalidDataProfileException extends Throwable {

    public InvalidDataProfileException(String id, String data_profile) {
        super("Invalid data_profile value " + data_profile + " for company-id: " + id);
    }
}
