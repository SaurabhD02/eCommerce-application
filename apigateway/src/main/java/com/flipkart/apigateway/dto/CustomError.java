package com.flipkart.apigateway.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
public class CustomError {

    @Builder.Default
    private LocalDateTime time = LocalDateTime.now();

    private HttpStatus httpStatus;

    private String header;

    private String timeStamp;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;

    @Builder.Default
    private final Boolean isSuccess = false;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<CustomSubError> subErrors;

    /**
     * Represents a sub-error with specific details as {@link CustomSubError}.
     */
    @Getter
    @Builder
    public static class CustomSubError {

        private String message;

        private String field;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        private Object value;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String type;

    }

    /**
     * Enumerates common error headers for categorizing errors as {@link Header}.
     */
    @Getter
    @RequiredArgsConstructor
    public enum Header {

        AUTH_ERROR("AUTH ERROR"),
        INTERNAL_SERVER_ERROR("INTERNAL SERVER ERROR");

        private final String name;

    }

}
