package edu.unicolombo.trustHotelAPI.service;

import edu.unicolombo.trustHotelAPI.domain.model.User;
import edu.unicolombo.trustHotelAPI.dto.auth.JwtTokenDTO;
import edu.unicolombo.trustHotelAPI.dto.auth.LoginDTO;
import edu.unicolombo.trustHotelAPI.infrastructure.security.JwtTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtTokenService jwtTokenService;

    public JwtTokenDTO loginAuth(LoginDTO data){
        try {
            Authentication auth =authManager.authenticate(new UsernamePasswordAuthenticationToken(data.email(), data.password()));

            UserDetails ud =(UserDetails) auth.getPrincipal();

            String token = jwtTokenService.generateToken((User) ud);

            return new JwtTokenDTO(token);
        } catch (BadCredentialsException ex) {
            throw new BadCredentialsException("Credenciales invalidas");
        }
    }
}
