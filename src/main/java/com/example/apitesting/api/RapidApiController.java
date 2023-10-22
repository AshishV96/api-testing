package com.example.apitesting.api;

import com.example.apitesting.util.AuthenticationUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Iterator;

@RestController
public class RapidApiController {

    @Autowired
    AuthenticationUtil authUtil;

    @GetMapping("getApiHeaders")
    public String getApiHeaders(HttpServletRequest request)
    {
        Iterator<String> headers = request.getHeaderNames().asIterator();
        System.out.println("----------------HEADERS----------------");
        while(headers.hasNext())
        {
            String headerName = headers.next();
            System.out.println(headerName+" -> "+request.getHeader(headerName));
        }
        System.out.println("---------------------------------------");
        String header = request.getHeader("X-RapidAPI-Key");
        return ObjectUtils.isEmpty(header)?"No header found":header;
    }

    @GetMapping("authenticate")
    public ResponseEntity<String> authenticate(HttpServletRequest request)
    {
        if(!authUtil.authenticate(request))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are not authorized to access this api");

        return ResponseEntity.ok("Authentication Successful");
    }

}
