package com.excilys.computer_database.persistence.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.excilys.computer_database.core.entity.User;

public interface UserDAO extends PagingAndSortingRepository<User, Long> {

    User findByUsername(String username);
}
