package com.codebene.board.exception.post;

import com.codebene.board.exception.ClientErrorException;
import org.springframework.http.HttpStatus;

public class PostNotFoundException extends ClientErrorException {

    public PostNotFoundException() {
        super(HttpStatus.NOT_FOUND, "Post not found.");
    }

    public PostNotFoundException(Long postId) {
        super(HttpStatus.NOT_FOUND, "Post with postId" + postId + "not found.");
    }

    public PostNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
