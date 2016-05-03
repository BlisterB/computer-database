package com.excilys.tests.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import com.excilys.computer_database.database.dao.DAOException;
import com.excilys.computer_database.database.services.ComputerService;
import com.excilys.computer_database.entity.Computer;
import com.excilys.computer_database.entity.Computer.ComputerBuilder;

public class TestComputerService {
    ComputerService service;

    @Before
    public void beforeTest() {
        service = new ComputerService();
    }

    @Test
    public void testGetComputerById() {
        // Illegal call
        try {
            service.getComputerById(-1L);
            fail("Le service ne doit pas trouver un id négatif");
        } catch (DAOException e) {
            // Ok!
        }

        // Legal call
        try {
            assertEquals(new Long(1), service.getComputerById(1L).getId());
        } catch (DAOException e) {
            e.printStackTrace();
        }

        // Legal call with no values
        try {
            service.getComputerById(Long.MAX_VALUE);
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
            service.createComputer(computer);
            createdId = computer.getId();
        } catch (DAOException e) {
            fail("The insert is a failure.");
            e.printStackTrace();
            return;
        }

        // Legal deletion
        if (createdId != null) {
            try {
                service.delete(createdId);
            } catch (DAOException e) {
                fail("The deletion is a failure");
                e.printStackTrace();
                return;
            }
            // Verify that the deletion is effective
            try {
                Computer computer = service.getComputerById(createdId);
            } catch (DAOException e) {
                // Ok !
            }
        }

        // Illegal call
        // Create a computer with a negative id
        try {
            Computer computer = (new ComputerBuilder("test")).id(-1).build();
            service.createComputer(computer);
            fail("An id must be positive");
        } catch (DAOException e) {
            // ok !
        }

        // Create a computer with an existing id
        try {
            Computer computer = (new ComputerBuilder("test")).id(1).build();
            service.createComputer(computer);
            fail("Not supposed to permit the creation with an already existing id.");
        } catch (DAOException e) {
            // ok !
        }
    }
}
