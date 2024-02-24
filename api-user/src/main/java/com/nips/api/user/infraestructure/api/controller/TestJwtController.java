package com.nips.api.user.infraestructure.api.controller;

import com.nips.api.user.application.dto.AuthCredentialsRequestDto;
import com.nips.api.user.application.dto.AuthCredentialsResponseDto;
import com.nips.api.user.common.RouteMapping;
import jakarta.annotation.security.RolesAllowed;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(RouteMapping.AUTH_TEST_API_ROOT)
public class TestJwtController {

    @Autowired
    private HttpServletRequest request;


    @RolesAllowed("ROLE_USER")
    @GetMapping( value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> echoBack(@RequestBody(required = false) byte[] rawBody) throws IOException {

        Map<String, String> headers = new HashMap<>();
        for (String headerName : Collections.list(request.getHeaderNames())) {
            headers.put(headerName, request.getHeader(headerName));
        }

        Map<String, Object> responseMap = new HashMap<String,Object>();
        responseMap.put("protocol", request.getProtocol());
        responseMap.put("method", request.getMethod());
        responseMap.put("headers", headers);
        responseMap.put("cookies", request.getCookies());
        responseMap.put("parameters", request.getParameterMap());
        responseMap.put("path", request.getServletPath());
        responseMap.put("body", rawBody != null ? Base64.getEncoder().encodeToString(rawBody) : null);

        return ResponseEntity.status(HttpStatus.OK).body(responseMap);
    }
}
