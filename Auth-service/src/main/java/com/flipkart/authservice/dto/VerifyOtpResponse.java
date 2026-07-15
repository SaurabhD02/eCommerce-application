package com.flipkart.authservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VerifyOtpResponse {

    @JsonProperty("bIsValid")
    private boolean isValid;

    @JsonProperty("sMessage")
    private String message;

    @JsonProperty("sToken")
    private String token;

}
