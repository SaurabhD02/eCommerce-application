package com.flipkart.authservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SendOtpRequest {

    @JsonProperty("sMobile")
    private String mobile;
}
