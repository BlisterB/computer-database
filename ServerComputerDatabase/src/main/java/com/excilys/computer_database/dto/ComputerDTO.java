package com.excilys.computer_database.dto;

import java.time.LocalDate;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import com.excilys.computer_database.entity.Computer;
import com.excilys.computer_database.helper.DateHelper;

public class ComputerDTO {
    private Long id, companyId;
    @NotEmpty
    @Size(min = 3, max = 40)
    private String name;
    private String companyName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate introduced, discontinued;

    public ComputerDTO() {
    }

    public ComputerDTO(Long id, String name, LocalDate introduced, LocalDate discontinued, Long companyId,
            String companyName) {
        this.id = id;
        this.companyId = companyId;
        this.name = name;
        this.companyName = companyName;
        this.introduced = introduced;
        this.discontinued = discontinued;
    }

    public ComputerDTO(Computer c) {
        this.id = c.getId();
        this.name = c.getName();
        if (c.getIntroduced() != null) {
            this.introduced = DateHelper.timestampToLocalDate(c.getIntroduced());
        }
        if (c.getDiscontinued() != null) {
            this.discontinued = DateHelper.timestampToLocalDate(c.getDiscontinued());
        }

        if (c.getCompany() != null) {
            this.companyId = c.getCompany().getId();
            this.companyName = c.getCompany().getName();
        }
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the companyId
     */
    public Long getCompanyId() {
        return companyId;
    }

    /**
     * @param companyId the companyId to set
     */
    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
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

    /**
     * @return the companyName
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * @param companyName the companyName to set
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * @return the introduced
     */
    public LocalDate getIntroduced() {
        return introduced;
    }

    /**
     * @param introduced the introduced to set
     */
    public void setIntroduced(LocalDate introduced) {
        this.introduced = introduced;
    }

    /**
     * @return the discontinued
     */
    public LocalDate getDiscontinued() {
        return discontinued;
    }

    /**
     * @param discontinued the discontinued to set
     */
    public void setDiscontinued(LocalDate discontinued) {
        this.discontinued = discontinued;
    }

}
