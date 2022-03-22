package com.mab.controller;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mab.format.ResponseFormat;
import com.mab.format.ResponseMessageFormat;
import com.mab.util.GenerateCredentialUtil;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class CredentialController {

    @Value("${apiKeyLength}")
    private Integer apiKeyLength;

    @Value("${jwtKeyLength}")
    private Integer jwtKeyLength;

    @Value("${jweIdLength}")
    private Integer jweIdLength;

    // Creating Object of ObjectMapper define in Jakson Api
    private ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("/")
    public String helloCredAPI() {
        return "Generate Credential API Service is Running";
    }

    @PostMapping("/apiCredential")
    public ResponseEntity<ResponseFormat> apiCredential(HttpServletRequest request) {
        log.info("Request Client : " + request.getRemoteAddr());

        /*
         * Pembuatan API ID, API KEY, JWT KEY, & JWE ID
         * select length('4fac7ae940fc46cbb80c1d7e4651b58b'); --32 API ID
         * select length('HWcQ1UYkOSYgBh10+WLMBCLEPw6R3549rAxV7LgfRk8='); --44 API KEY
         * select length('OKcQ1UYkOSYgBh10+WLMBCLEPw6R3549rAxV7LgfRk8='); --44 JWT key
         * select length('Fdh9u8rINxfivbria1JKVT1u232VQBZYKx1HGAGPABa'); --43 JWE ID
         */
        String apiId, apiKey, jwtKey, jweId;
        GenerateCredentialUtil generateCredentialUtil = new GenerateCredentialUtil();
        apiId = UUID.randomUUID().toString().replace("-", "");
        apiKey = generateCredentialUtil.randomString(apiKeyLength);
        jwtKey = generateCredentialUtil.randomString(jwtKeyLength);
        jweId = generateCredentialUtil.randomString(jweIdLength);

        log.info("API ID : " + apiId + " | " + apiId.length());
        log.info("API KEY : " + apiKey + " | " + apiKey.length());
        log.info("JWT KEY : " + jwtKey + " | " + jwtKey.length());
        log.info("JWE ID : " + jweId + " | " + jweId.length());

        /* Informasi Tentang Nama Method */
        String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName();

        /* Memanggil Class Response yang telah dibuat */
        ResponseMessageFormat responseMessageFormat = new ResponseMessageFormat();
        responseMessageFormat.setAPI_ID(apiId);
        responseMessageFormat.setAPI_KEY(apiKey);
        responseMessageFormat.setJWT_KEY(jwtKey);
        responseMessageFormat.setJWE_ID(jweId);

        ResponseFormat responseFormat = new ResponseFormat();
        responseFormat.setTimestamp(new Date());
        responseFormat.setStatus(HttpStatus.OK.value());
        responseFormat.setError("");
        responseFormat.setMessage(responseMessageFormat);
        responseFormat.setPath(request.getRequestURI() + " | " + nameofCurrMethod);

        ResponseEntity<ResponseFormat> responseEntity = null;
        responseEntity = ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON)
                .body(responseFormat);
        try {
            // get Oraganisation object as a json string
            String jsonString = objectMapper.writeValueAsString(responseEntity);

            // Displaying JSON String
            log.info("Respose Message : " + jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return responseEntity;
    }

}
