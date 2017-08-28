package com.bisnode.bhc.domain.exception;

/**
 * Created by sahm on 28.08.17.
 */
public class InvalidSystemIdException extends Throwable {
    public InvalidSystemIdException(String id, String system_id) {
        super("Invalid system_id value " + system_id + " for company-id: " + id);
    }
}
