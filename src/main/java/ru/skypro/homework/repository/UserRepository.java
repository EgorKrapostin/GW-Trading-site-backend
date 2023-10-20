package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.skypro.homework.entity.Users;

public interface UserRepository extends JpaRepository<Users, Integer> {

}
