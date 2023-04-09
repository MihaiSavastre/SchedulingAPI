package com.softbinator.labs.project.schedulingapi.security.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthentificationRequest {

    private String username;

    private String password;

}
