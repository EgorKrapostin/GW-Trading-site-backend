package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.skypro.homework.entity.Users;

public interface UserRepository extends JpaRepository<Users, Integer> {

    @Query(value = "SELECT * FROM user_auth " +
            "WHERE email = :email",
            nativeQuery = true)
    Users findUserByEmail(@Param("email") String email);
}
