package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.entity.Image;
/**
 * Repository class for working with images via a database
 */
@Repository
public interface ImageRepository extends CrudRepository<Image, String> {
}
