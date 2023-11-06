package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Repository;
import ru.skypro.homework.entity.User;

import java.util.Optional;
/**
 * Repository class for working with users through the database
 */
@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    /*@Query(value = "SELECT * FROM user_auth " +
            "WHERE username = :username",
            nativeQuery = true)
    Optional<User> findUs(@Param("username") String username);*/
    /*@Query(value = "SELECT * FROM user_auth " +
            "WHERE username = :username",
            nativeQuery = true)*/
    Optional<User> findByUsername(String username);

}
