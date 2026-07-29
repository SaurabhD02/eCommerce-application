package com.flipkart.apigateway.exception;

import java.io.Serial;

public class UnAuthorizedUserException  extends RuntimeException{

    @Serial
    private static final long serialVersionUID = -3952215105519401565L;

    private static final String DEFAULT_MESSAGE = """
            Invalid Credentials!
            """;

    public UnAuthorizedUserException() {
        super(DEFAULT_MESSAGE);
    }

    public UnAuthorizedUserException(final String message) {
        super(DEFAULT_MESSAGE + " " + message);
    }

}
