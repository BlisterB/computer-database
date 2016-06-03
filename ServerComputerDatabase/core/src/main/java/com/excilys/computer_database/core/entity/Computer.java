package com.excilys.computer_database.core.entity;

import java.sql.Timestamp;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.excilys.computer_database.core.DateHelper;

@Entity
@Table(name = "computer", uniqueConstraints = { @UniqueConstraint(columnNames = "ID") })
public class Computer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Timestamp introduced, discontinued;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    /** Implementation of the Builder pattern. */
    public static class ComputerBuilder {
        private Long id;
        private String name;
        private Timestamp introduced, discontinued;
        private Company company;

        /**
         * Initialise a ComputerBuild to customize and build.
         * @param name The computer name
         */
        public ComputerBuilder(String name) {
            this.name = name;
        }

        /**
         * Build a computer instance.
         * @return the built computer
         */
        public Computer build() {
            return new Computer(this);
        }

        /**
         * Customize the computer id.
         * @param id The computer's id
         * @return The instance of ComputerBuilder
         */
        public ComputerBuilder id(long id) {
            this.id = id;
            return this;
        }

        /**
         * Customize the computer's name.
         * @param name The computer's name
         * @return The instance of ComputerBuilder
         */
        public ComputerBuilder name(String name) {
            this.name = name;
            return this;
        }

        /**
         * Customize the computer's introduced date field.
         * @param introduced The introduction date
         * @return The instance of ComputerBuilder
         */
        public ComputerBuilder introduced(Timestamp introduced) {
            if (introduced != null) {
                this.introduced = introduced;
            }
            return this;
        }

        /**
         * Customize the computer's introduced date field.
         * @param introduced The introduction date
         * @return The instance of ComputerBuilder
         */
        public ComputerBuilder introduced(LocalDate introduced) {
            if (introduced != null) {
                this.introduced = DateHelper.localDateToTimestamp(introduced);
            }
            return this;
        }

        /**
         * Customize the computer's discontinued date field.
         * @param discontinued The discontinued date
         * @return The instance of ComputerBuilder
         */
        public ComputerBuilder discontinued(Timestamp discontinued) {
            this.discontinued = discontinued;
            return this;
        }

        /**
         * Customize the computer's discontinued date field.
         * @param discontinued The discontinued date
         * @return The instance of ComputerBuilder
         */
        public ComputerBuilder discontinued(LocalDate discontinued) {
            if (discontinued != null) {
                this.discontinued = DateHelper.localDateToTimestamp(discontinued);
            }
            return this;
        }

        /**
         * Customize the company field.
         * @param company The company
         * @return The instance of ComputerBuilder
         */
        public ComputerBuilder company(Company company) {
            this.company = company;
            return this;
        }
    }

    /**
     * Constructor.
     * @param builder The builder
     */
    private Computer(ComputerBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.introduced = builder.introduced;
        this.discontinued = builder.discontinued;
        this.company = builder.company;
    }

    public Computer() {

    }

    @Override
    public String toString() {
        return this.getId() + "\t" + this.getName() + "\t" + this.getIntroduced() + "\t" + this.getDiscontinued() + "\t"
                + this.getCompany();
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
     * @return the introduced
     */
    public Timestamp getIntroduced() {
        return introduced;
    }

    /**
     * @param introduced the introduced to set
     */
    public void setIntroduced(Timestamp introduced) {
        this.introduced = introduced;
    }

    /**
     * @return the discontinued
     */
    public Timestamp getDiscontinued() {
        return discontinued;
    }

    /**
     * @param discontinued the discontinued to set
     */
    public void setDiscontinued(Timestamp discontinued) {
        this.discontinued = discontinued;
    }

    /**
     * @return the company
     */
    public Company getCompany() {
        return company;
    }

    /**
     * @param company the company to set
     */
    public void setCompany(Company company) {
        this.company = company;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Computer other = (Computer) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }
}
