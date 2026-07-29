package com.flipkart.authservice.exception;

import java.io.Serial;

public class OtpException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = -3952215105519401565L;

    private static final String DEFAULT_MESSAGE = """
            Exception while validating Otp!
            """;

    public OtpException() {
        super(DEFAULT_MESSAGE);
    }

    public OtpException(final String message) {
        super(DEFAULT_MESSAGE + " " + message);
    }
}
