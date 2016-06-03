package com.excilys.computer_database.core.dto;

import com.excilys.computer_database.core.entity.Company;

public class CompanyDTO {
    private long id;
    private String name;

    /**
     * Constructor.
     * @param id The company's id
     * @param name The company's name
     */
    public CompanyDTO(int id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

    /**
     * Constructor.
     * @param company The company
     */
    public CompanyDTO(Company company) {
        this.id = company.getId();
        this.name = company.getName();
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

}
