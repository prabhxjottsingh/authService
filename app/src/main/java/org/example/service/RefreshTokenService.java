package org.example.service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.example.entities.RefreshToken;
import org.example.entities.UserInfo;
import org.example.repository.RefreshTokenRepository;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RefreshTokenService {
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired 
    private UserRepository userRepository;

    public RefreshToken createRefreshToken(String userName){
        UserInfo userInfo = userRepository.findByUsername(userName);
        RefreshToken refreshToken = RefreshToken.builder()
                                    .userInfo(userInfo)
                                    .token(UUID.randomUUID().toString())
                                    .expiryDate(Instant.now().plusMillis(60*1000))
                                    .build();
        return refreshTokenRepository.save(refreshToken);
    }
    
    public RefreshToken verifyExpiration(RefreshToken token){
        if( token.getExpiryDate().compareTo(Instant.now()) < 0 ){
            refreshTokenRepository.delete(token);
            throw new RuntimeException(token.getToken() + " refresh token is expired. Please login again");
        }
        return token;
    }

    public Optional<RefreshToken> findByToken(String token){
        return refreshTokenRepository.findByToken(token);
    }

}
