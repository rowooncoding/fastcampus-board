package com.codebene.board.model.user;

public record UserSignUpRequestBody(
        String username,
        String password
) {
}
