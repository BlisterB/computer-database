package com.excilys.computer_database.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.excilys.computer_database.entity.User;

public interface UserDAO extends PagingAndSortingRepository<User, Long> {

    User findByUsername(String username);
}
