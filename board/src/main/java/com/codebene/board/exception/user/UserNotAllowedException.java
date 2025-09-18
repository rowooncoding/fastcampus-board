package com.codebene.board.exception.user;

import com.codebene.board.exception.ClientErrorException;
import org.springframework.http.HttpStatus;

public class UserNotAllowedException extends ClientErrorException {

    public UserNotAllowedException() {
super(HttpStatus.FORBIDDEN, "User not allowed.");
    }
}
