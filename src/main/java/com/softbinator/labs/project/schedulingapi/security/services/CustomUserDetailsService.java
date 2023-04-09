package com.softbinator.labs.project.schedulingapi.security.services;

import com.softbinator.labs.project.schedulingapi.security.dtos.CustomUserDetails;
import com.softbinator.labs.project.schedulingapi.security.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByUsername(username).orElseThrow();
        return new CustomUserDetails(user);
    }
}

