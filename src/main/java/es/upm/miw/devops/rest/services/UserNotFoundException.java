package es.upm.miw.devops.rest.services;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String id) {
        super("User with id " + id + " not found");
    }
}