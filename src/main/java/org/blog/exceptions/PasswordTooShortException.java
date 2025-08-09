package org.blog.exceptions;

public class PasswordTooShortException extends RegistrationExceptions {
    public PasswordTooShortException(String message) {
        super(message);
    }
}
