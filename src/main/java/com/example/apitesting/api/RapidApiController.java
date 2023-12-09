package com.example.apitesting.api;

import com.example.apitesting.util.AuthenticationUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;

@RestController
@RequestMapping("api")
public class RapidApiController {

    @Autowired
    AuthenticationUtil authUtil;

    @GetMapping("download")
    public ResponseEntity<Resource> downloadLogs(@RequestParam String startDate, @RequestParam String endDate) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate start = LocalDate.parse(startDate, formatter);
        LocalDate end = LocalDate.parse(endDate, formatter);

        StringBuilder logsContent = new StringBuilder();
        while (!start.isAfter(end)) {

            String filename;

            if(start.isEqual(LocalDate.now()))
                filename = "src/main/resources/logs/application.log";
            else
                filename = "src/main/resources/logs/" + "logfile-" + start.format(formatter) + ".log";

            logsContent.append(readFileContent(filename));
            start = start.plusDays(1);
        }

        InputStreamResource resource = new InputStreamResource(
                new ByteArrayInputStream(logsContent.toString().getBytes(StandardCharsets.UTF_8))
        );

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=logs.txt");

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(logsContent.length())
                .contentType(MediaType.TEXT_PLAIN)
                .body(resource);
    }

    private String readFileContent(String filename) throws IOException {
        File file = new File(filename);
        if (file.exists()) {
            try (FileInputStream fis = new FileInputStream(file)) {
                byte[] data = new byte[(int) file.length()];
                fis.read(data);
                return new String(data, StandardCharsets.UTF_8);
            }
        }
        return "";
    }

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
