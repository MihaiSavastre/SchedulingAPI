package com.softbinator.labs.project.schedulingapi.security.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserDto {
    @NotBlank
    @Size(min = 4, max = 30)
    protected String username;

    @NotBlank
    @Size(min = 4, max = 100)
    protected String password;

}
