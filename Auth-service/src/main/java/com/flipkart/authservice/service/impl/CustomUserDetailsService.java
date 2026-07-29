package com.flipkart.authservice.service.impl;

import com.flipkart.authservice.entity.CustomUserDetails;
import com.flipkart.authservice.entity.User;
import com.flipkart.authservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String mobileNo) throws UsernameNotFoundException {

        Optional<User> optionalUser = userRepository.findByMobile(mobileNo);
        if(optionalUser.isPresent()) {
            return new CustomUserDetails(optionalUser.get());
        }else{
            throw new UsernameNotFoundException(mobileNo);
        }
    }
}
