package ru.skypro.homework.dto.comment;

import lombok.Data;

import java.util.List;
@Data
public class CommentsDTO {
    private int count;
    private List<CommentDTO> results;

//    public CommentsDTO(int count, List<CommentDTO> results){
//        this.count = results.size();
//        this.results = results;
//    }
}

