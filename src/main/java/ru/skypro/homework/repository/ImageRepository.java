package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.entity.Image;
/**
 * Repository class for working with images via a database
 */
public interface ImageRepository extends JpaRepository<Image, String> {
}
