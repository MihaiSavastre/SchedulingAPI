package com.softbinator.labs.project.schedulingapi.security.user_information;

import org.springframework.security.core.Authentication;

public interface IAuthenticationFacade {
    Authentication getAuthentication();

    String getUsername();
}
