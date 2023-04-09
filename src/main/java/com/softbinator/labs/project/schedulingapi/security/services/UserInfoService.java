package com.softbinator.labs.project.schedulingapi.security.services;

import com.softbinator.labs.project.schedulingapi.security.repositories.UserRepository;
import com.softbinator.labs.project.schedulingapi.security.user_information.IAuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserInfoService {
    private final UserRepository userRepository;
    private final IAuthenticationFacade authenticationFacade;

    public String getUsername() {
        return authenticationFacade.getUsername();
    }

    public Long getEntityId() {
        return userRepository.findByUsername(authenticationFacade.getUsername()).get().getRoleId();
    }
}
