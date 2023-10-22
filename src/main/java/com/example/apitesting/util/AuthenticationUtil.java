package com.example.apitesting.util;

import com.example.apitesting.domain.RapidApiUser;
import com.example.apitesting.repository.RapidApiUserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
public class AuthenticationUtil {

    @Autowired
    RapidApiUserRepository repo;

    public boolean authenticate(HttpServletRequest request)
    {
        try{
            String username = request.getHeader("x-rapidapi-user");

            if (!request.getHeader("x-rapidapi-host").equalsIgnoreCase("ezyhire.p.rapidapi.com")  || ObjectUtils.isEmpty(username))
                return false;

            RapidApiUser user = repo.findByUsername(username).orElse(null);

            if (ObjectUtils.isEmpty(user))
                repo.save(new RapidApiUser(null, username));

            return true;
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }


}
