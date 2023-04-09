package com.softbinator.labs.project.schedulingapi.security.services;

import com.softbinator.labs.project.schedulingapi.security.dtos.AuthentificationRequest;
import com.softbinator.labs.project.schedulingapi.security.dtos.CreateUserDto;
import com.softbinator.labs.project.schedulingapi.security.dtos.CustomUserDetails;
import com.softbinator.labs.project.schedulingapi.security.dtos.UserDto;
import com.softbinator.labs.project.schedulingapi.security.enums.Role;
import com.softbinator.labs.project.schedulingapi.security.models.User;
import com.softbinator.labs.project.schedulingapi.security.repositories.UserRepository;
import com.softbinator.labs.project.schedulingapi.security.user_information.IAuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final IAuthenticationFacade authenticationFacade;


    public UserDto createUser(CreateUserDto createUserDto, Role role, Long roleId) {
        userRepository.findByUsername(createUserDto.getUsername()).ifPresent(
                (u) -> {throw new RuntimeException("Username already exists!");}
        );

        var user = User.builder()
                        .username(createUserDto.getUsername())
                        .role(role)
                        .roleId(roleId).build();

        user.setPassword(passwordEncoder.encode(createUserDto.getPassword()));
        user = userRepository.save(user);
        return new UserDto(user);
    }

    public UserDto findById(Long id) {
        return userRepository.findById(id)
                .map(UserDto::new)
                .orElseThrow();
    }

    public String authenticate(AuthentificationRequest authRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getUsername(),
                        authRequest.getPassword()
                )
        );

        var user = userRepository.findByUsername(authRequest.getUsername()).orElseThrow(
                () -> {throw new RuntimeException("username not found");}
        );

        String token = jwtService.generateToken(
                Map.of(),
                new CustomUserDetails(user)
        );


        //DEBUG
        for (var a : authenticationFacade.getAuthentication().getAuthorities())
            System.out.println(a.getAuthority());

        return token;
    }
}

