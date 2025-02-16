package org.example.controller;

import org.example.entities.RefreshToken;
import org.example.models.UserInfoDTO;
import org.example.responses.JWTResponseDTO;
import org.example.service.JWTService;
import org.example.service.RefreshTokenService;
import org.example.service.UserDetailsServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@RestController
public class AuthController {
    
    @Autowired
    private JWTService jwtService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private UserDetailsServiceImplementation userDetailsServiceImpl;

    @PostMapping("auth/v1/signup")
    public ResponseEntity signup(@RequestBody UserInfoDTO userInfo){
        try{
            Boolean isSignedUp = userDetailsServiceImpl.signupUser(userInfo);
            if(  Boolean.FALSE.equals(isSignedUp) ){
                return new ResponseEntity<>("Already exists", HttpStatus.BAD_REQUEST);
            }
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(userInfo.getUsername());
            String jwtToken = jwtService.generateToken(userInfo.getUsername());
            return new ResponseEntity<>(
                    JWTResponseDTO
                    .builder()
                    .accessToken(jwtToken)
                    .token(refreshToken.getToken())
                    .build(), 
                HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>("Exception in user service", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
