package ru.skypro.homework.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.entity.Comment;

import java.util.List;
/**
 * Repository class for working with comments through the database
 */
@Repository
public interface CommentRepository extends CrudRepository<Comment, Integer> {

    List<Comment> getCommentsByAd_Id(int id);

}
