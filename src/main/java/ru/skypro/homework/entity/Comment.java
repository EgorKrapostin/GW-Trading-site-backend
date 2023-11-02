package ru.skypro.homework.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Data
@Entity
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id", nullable = false)
    private int id;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User author;


    @ManyToOne
    @JoinColumn(name = "ad_id", nullable = false)
    private Ad ad;

    @CreationTimestamp
    @Column(name = "comment_date", length = 20, nullable = false)
    private LocalDateTime createdAt;

    @Column(length = 1000, nullable = false)
    private String text;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        if (id != 0) {
            return id == comment.id;
        } else {
            return Objects.equals(ad, comment.ad) && Objects.equals(author, comment.author)
                    && Objects.equals(createdAt, comment.createdAt) && Objects.equals(text, comment.text);
        }
    }

    @Override
    public int hashCode() {
        if (id != 0) {
            return Objects.hash(id);
        } else {
            return Objects.hash(ad, author, createdAt, text);
        }
    }
}
