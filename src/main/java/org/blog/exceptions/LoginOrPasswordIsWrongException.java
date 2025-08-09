package org.blog.exceptions;

public class LoginOrPasswordIsWrongException extends RuntimeException {
    public LoginOrPasswordIsWrongException(String message) {
        super(message);
    }
}
