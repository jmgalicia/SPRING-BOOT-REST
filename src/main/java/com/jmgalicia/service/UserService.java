package com.jmgalicia.service;

import com.jmgalicia.model.User;
import com.jmgalicia.dao.UserDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/*
 * Sample service to demonstrate what the API would use to get things done
 */
@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserDAO userDAO;

    @Autowired
    CounterService counterService;

    @Autowired
    GaugeService gaugeService;

    public UserService() {
    }

    public User addUser(User user) {
        return userDAO.save(user);
    }

    public User getUser(long id) {

        return userDAO.findOne(id);
    }

    public void updateUser(User user) {
        userDAO.save(user);
    }

    public void deleteUser(Long id) {
        userDAO.delete(id);
    }

    public Page<User> getAllUsers(Integer page, Integer size) {
        Page pageOfUsers = userDAO.findAll(new PageRequest(page, size));
        return pageOfUsers;
    }
}
