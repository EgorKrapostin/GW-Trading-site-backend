package ru.skypro.homework.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
}
