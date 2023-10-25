package ru.skypro.homework.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Data
@Entity
@Table(name = "Ad")
public class Ad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private int id;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @OneToOne
    @JoinColumn(name = "image_id")
    private Image image;

    @Column(length = 32 ,nullable = false)
    private String title;

    @Column(length = 1000, nullable = false)
    private String description;

    @Column(nullable = false)
    private int price;

    @OneToMany(mappedBy = "ad", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

}
