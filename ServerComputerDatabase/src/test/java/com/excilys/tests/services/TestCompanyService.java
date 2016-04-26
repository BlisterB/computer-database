package com.excilys.tests.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.excilys.computer_database.database.dao.DAOException;
import com.excilys.computer_database.database.services.CompaniesService;
import com.excilys.computer_database.entity.Company;
import com.excilys.computer_database.ui.Page;

public class TestCompanyService{
    CompaniesService service;

    @Before
    public void beforeTest(){
        service = new CompaniesService();
    }

    @After
    public void afterTest(){

    }

    @Test
    public void testListAllCompanies(){
        // TODO : Enlever ce test en cas de table volumineuse (ou de possibilité d'ajouter/suppression des company)
        List<Company> list;
        try {
            list = service.listAllCompanies();
            assertEquals(58, list.size());
        } catch (DAOException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testListByPage(){
        // On vérifie le nombre d'elements par page
        int nbCompanies = 58;
        int nbPerPage = 10;

        Page<Company> page;
        for(int i = 0; i < nbCompanies; i += nbPerPage){
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
