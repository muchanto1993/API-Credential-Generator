package com.mab.format;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonPropertyOrder({ "API_ID", "API_KEY", "JWT_KEY", "JWE_ID" })
@JsonInclude(Include.NON_NULL)
public class ResponseMessageFormat {

    private String API_ID;
    private String API_KEY;
    private String JWT_KEY;
    private String JWE_ID;

}
