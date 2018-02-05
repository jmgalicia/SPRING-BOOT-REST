package com.jmgalicia.dao;

import com.jmgalicia.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserDAO extends PagingAndSortingRepository<User, Long> {
    User findUserByAddress(String address);
    Page findAll(Pageable pageable);
}
