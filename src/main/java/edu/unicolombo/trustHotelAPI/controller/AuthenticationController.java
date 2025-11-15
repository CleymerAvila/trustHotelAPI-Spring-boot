package edu.unicolombo.trustHotelAPI.controller;

import edu.unicolombo.trustHotelAPI.dto.auth.JwtTokenDTO;
import edu.unicolombo.trustHotelAPI.dto.auth.LoginDTO;
import edu.unicolombo.trustHotelAPI.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authService;


    @PostMapping("/login")
    public ResponseEntity<JwtTokenDTO>  login(@RequestBody LoginDTO data){
        return ResponseEntity.ok(authService.loginAuth(data));
    }


}
