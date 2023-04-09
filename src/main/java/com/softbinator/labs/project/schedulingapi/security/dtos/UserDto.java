package com.softbinator.labs.project.schedulingapi.security.dtos;

import com.softbinator.labs.project.schedulingapi.security.enums.Role;
import com.softbinator.labs.project.schedulingapi.security.models.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;

    private String username;

    private Role role;

    private Long roleId;

    public UserDto(User user) {
        id = user.getId();
        username = user.getUsername();
        role = user.getRole();
        roleId = user.getRoleId();
    }
}
