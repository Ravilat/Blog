package org.blog.exceptions;

public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException(String textError) {
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" + textError + "~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }
}
