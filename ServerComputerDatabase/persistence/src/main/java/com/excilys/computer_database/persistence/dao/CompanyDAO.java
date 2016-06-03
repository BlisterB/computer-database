package com.excilys.computer_database.persistence.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.excilys.computer_database.core.entity.Company;

public interface CompanyDAO extends PagingAndSortingRepository<Company, Long>{ }
