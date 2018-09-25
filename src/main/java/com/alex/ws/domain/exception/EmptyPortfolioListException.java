package com.alex.ws.domain.exception;

public class EmptyPortfolioListException extends Exception {

    public EmptyPortfolioListException() {
        super();
    }

    public EmptyPortfolioListException(String message) {
        super(message);
    }

    public EmptyPortfolioListException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyPortfolioListException(Throwable cause) {
        super(cause);
    }
}
