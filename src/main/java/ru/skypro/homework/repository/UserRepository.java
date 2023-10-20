package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;
import ru.skypro.homework.entity.Users;

public interface UserRepository extends JpaRepository<User, Integer> {
    Users findByUsername(String firstName);
}
