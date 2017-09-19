package com.bisnode.bhc.domain.exception;

public class InvalidPortfolioMessageException extends Exception {

    public InvalidPortfolioMessageException() {
        super();
    }

    public InvalidPortfolioMessageException(String message) {
        super(message);
    }

    public InvalidPortfolioMessageException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidPortfolioMessageException(Throwable cause) {
        super(cause);
    }
}
