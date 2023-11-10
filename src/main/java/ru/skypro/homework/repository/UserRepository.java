package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ru.skypro.homework.entity.User;

import java.util.Optional;
/**
 * Repository class for working with users through the database
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value = "SELECT * FROM user_auth " +
            "WHERE email = :email",
            nativeQuery = true)
    Optional<User> findUserByEmail(@Param("email") String email);

}
