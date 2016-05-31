package com.excilys.computer_database.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.excilys.computer_database.entity.Company;

public interface CompanyDAO extends PagingAndSortingRepository<Company, Long>{ }
