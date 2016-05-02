package com.excilys.computer_database.entity;

import java.sql.Timestamp;

import org.joda.time.LocalDate;

import com.excilys.computer_database.database.dao.DAOException;

public class Computer extends Entity {
    // TODO : implémenter serializable

    private Long id;
    private String name;
    private LocalDate introduced, discontinued;
    private Company company;

    /** Implementation of the Builder pattern. */
    public static class ComputerBuilder {
        private Long id;
        private String name;
        private LocalDate introduced, discontinued;
        private Company company;

        /** Initialise a ComputerBuild to customize and build.
         *  @param name The computer name
         */
        public ComputerBuilder(String name) {
            this.name = name;
        }

        /** Build a computer instance.
         *  @return the built computer
         *  @throws DAOException */
        public Computer build() throws DAOException {
            // TODO : Put the verification in the service?
            if (introduced != null && discontinued != null) {
                if (introduced.isAfter(discontinued)) {
                    throw new DAOException("Introduced date cannot be greater than Discontinued date");
                }
            }

            return new Computer(this);
        }

        /** Customize the computer id.
         * @param id The computer's id
         * @return The instance of ComputerBuilder
         */
        public ComputerBuilder id(long id) {
            this.id = id;
            return this;
        }

        /** Customize the computer's name.
         * @param name The computer's name
         * @return The instance of ComputerBuilder
         */
        public ComputerBuilder name(String name) {
            this.name = name;
            return this;
        }

        /** Customize the computer's introduced date field.
         * @param introduced The introduction date
         * @return The instance of ComputerBuilder
         */
        public ComputerBuilder introduced(LocalDate introduced) {
            if (introduced != null) {
                this.introduced = introduced;
            }
            return this;
        }

        /** Customize the computer's introduced date field.
         * @param introduced The introduction date
         * @return The instance of ComputerBuilder
         */
        public ComputerBuilder introduced(Timestamp introduced) {
            if (introduced != null) {
                this.introduced = new LocalDate(introduced);
            }
            return this;
        }

        /** Customize the computer's discontinued date field.
         * @param discontinued The discontinued date
         * @return The instance of ComputerBuilder
         */
        public ComputerBuilder discontinued(LocalDate discontinued) {
            this.discontinued = discontinued;
            return this;
        }

        /** Customize the computer's discontinued date field.
         * @param discontinued The discontinued date
         * @return The instance of ComputerBuilder
         */
        public ComputerBuilder discontinued(Timestamp discontinued) {
            if (discontinued != null) {
                this.discontinued = new LocalDate(discontinued);
            }
            return this;
        }

        /** Customize the company field.
         * @param company The company
         * @return The instance of ComputerBuilder
         */
        public ComputerBuilder company(Company company) {
            this.company = company;
            return this;
        }
    }

    /** Constructor.
     * @param builder The builder
     */
    private Computer(ComputerBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.introduced = builder.introduced;
        this.discontinued = builder.discontinued;
        this.company = builder.company;
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
