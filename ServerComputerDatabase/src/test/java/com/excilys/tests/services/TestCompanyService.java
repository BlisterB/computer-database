package com.excilys.tests.services;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.excilys.computer_database.database.dao.DAOException;
import com.excilys.computer_database.database.services.CompanyService;
import com.excilys.computer_database.entity.Company;
import com.excilys.computer_database.ui.Page;

public class TestCompanyService{
    CompanyService service;

    @Before
    public void beforeTest(){
        service = new CompanyService();
    }

    @After
    public void afterTest(){

    }

    @Test
    public void testListByPage(){
        // On vérifie le nombre d'elements par page
        int nbCompanies = 58;
        int nbPerPage = 10;

        Page<Company> page;
        for(int i = 0; i < 1; i += nbPerPage){
            try {
                page = service.listSomeCompanies(i, nbPerPage);

                if(nbCompanies - i > nbPerPage){
                    assertEquals(nbPerPage, page.getList().size());
                } else {
                    assertEquals(nbCompanies - i, page.getList().size());
                }
            } catch (DAOException e) {
                e.printStackTrace();
            }
        }
    }

    public void testCreation(){
        // No company creation feature in the spec for the moment
    }

    public void testDelete(){
        // No company deletion feature in the spec for the moment
    }

}
