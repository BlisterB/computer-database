package com.excilys.computer_database.persistence.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.excilys.computer_database.core.entity.Computer;

public interface ComputerDAO extends PagingAndSortingRepository<Computer, Long> {
    List<Computer> removeByCompany_Id(Long id);

    // TODO : Envisager le StartingWith ? Pertinence++ mais Performances--
    Page<Computer> findByNameOrCompany_Name(String name, String companyName, Pageable pageable);
}
