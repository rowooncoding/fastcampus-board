package com.codebene.board.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
public class Post {
    private Long postId;
    private String body;
    private ZonedDateTime createDateTime;
}
