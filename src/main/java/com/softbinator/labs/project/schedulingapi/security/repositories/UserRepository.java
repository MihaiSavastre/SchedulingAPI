package com.softbinator.labs.project.schedulingapi.security.repositories;

import com.softbinator.labs.project.schedulingapi.security.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
}
