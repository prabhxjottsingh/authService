package org.example.service;

import java.util.HashSet;
import java.util.Objects;
import java.util.UUID;

import org.example.entities.UserInfo;
import org.example.entities.UserRole;
import org.example.models.UserInfoDTO;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
@Component
public class UserDetailsServiceImplementation implements UserDetailsService {
    
    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;
    
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException{
        UserInfo user = userRepository.findByUsername(userName);
        if( user == null )
            throw new UsernameNotFoundException("Could not found the username....");
        return new CustomUserDetails(user);
    }

    public UserInfo checkIfUserAlreadyExists(UserInfoDTO userInfo){
        return userRepository.findByUsername(userInfo.getUsername());
    }

    public Boolean signupUser(UserInfoDTO userInfo) {
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        if(Objects.nonNull(checkIfUserAlreadyExists(userInfo))) return false;
        String userId = UUID.randomUUID().toString();
        userRepository.save(new UserInfo(userId, userInfo.getUsername(), userInfo.getPassword(), userInfo.getEmail(), new HashSet<>()));
        return true;
    }

    

}
