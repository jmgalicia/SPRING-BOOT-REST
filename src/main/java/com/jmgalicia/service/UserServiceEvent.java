package com.jmgalicia.service;

import org.springframework.context.ApplicationEvent;

/**
 * This is an optional class used in publishing application events.
 * This can be used to inject events into the Spring Boot audit management endpoint.
 */
public class UserServiceEvent extends ApplicationEvent {

    public UserServiceEvent(Object source) {
        super(source);
    }

    public String toString() {
        return "My UserService Event";
    }
}