package com.codebene.board.model;

import java.time.ZonedDateTime;

public record Post(Long postId, String body, ZonedDateTime createDateTime) {
}
