package com.excilys.tests.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.excilys.computer_database.core.entity.Computer;
import com.excilys.computer_database.core.entity.Computer.ComputerBuilder;
import com.excilys.computer_database.persistence.DAOException;
import com.excilys.computer_database.service.ComputerService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/applicationContext.xml" })
public class TestComputerService {
    @Autowired
    ComputerService computerService;

    @Before
    public void beforeTest() {
    }

    @Test
    public void testGetComputerById() {
        // Illegal call
        try {
            computerService.getComputerById(-1L);
            fail("Le service ne doit pas trouver un id négatif");
        } catch (DAOException e) {
            // Ok!
        }

        // Legal call
        try {
            assertEquals(new Long(1), computerService.getComputerById(1L).getId());
        } catch (DAOException e) {
            e.printStackTrace();
        }

        // Legal call with no values
        try {
            computerService.getComputerById(Long.MAX_VALUE);
            fail("The computer of maximum id is not supposed to exist");
        } catch (DAOException e) {
            // Ok !
        }
    }

    @Test
    public void testCreateAndDelete() {
        Long createdId;

        // Legal insertion
        try {
            Computer computer = (new ComputerBuilder("ComputerTest")).build();
            computerService.createComputer(computer);
            createdId = computer.getId();
        } catch (DAOException e) {
            fail("The insert is a failure.");
            e.printStackTrace();
            return;
        }

        // Legal deletion
        if (createdId != null) {
            try {
                computerService.delete(createdId);
            } catch (DAOException e) {
                fail("The deletion is a failure");
                e.printStackTrace();
                return;
            }
            // Verify that the deletion is effective
            try {
                computerService.getComputerById(createdId);
            } catch (DAOException e) {
                // Ok !
            }
        }

        // Illegal call
        // Create a computer with a negative id
        try {
            Computer computer = (new ComputerBuilder("test")).id(-1).build();
            computerService.createComputer(computer);
            fail("An id must be positive");
        } catch (DAOException e) {
            // ok !
        }

        // Create a computer with an existing id
        try {
            Computer computer = (new ComputerBuilder("test")).id(1).build();
            computerService.createComputer(computer);
            fail("Not supposed to permit the creation with an already existing id.");
        } catch (DAOException e) {
            // ok !
        }
    }
}
