package ru.skypro.homework.entity;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table(name = "image")
@Data
public class Image {
    @Id
    @Column(name = "image_id")
    private String id;

    @Lob
    @Column(name = "image")
    @Type(type = "org.hibernate.type.BinaryType")
    private byte[] bytes;

}
