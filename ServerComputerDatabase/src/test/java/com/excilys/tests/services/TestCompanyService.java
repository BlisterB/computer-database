package com.excilys.tests.services;


import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.persistence.PersistenceContext;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import com.excilys.computer_database.core.dto.CompanyDTO;
import com.excilys.computer_database.service.CompanyService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/applicationContext.xml"}, classes = {PersistenceContext.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
    TransactionalTestExecutionListener.class})
public class TestCompanyService{
    @Autowired
    CompanyService companyService;

    @Before
    public void beforeTest(){
    }

    @After
    public void afterTest(){
    }

    public void list(){
        List<CompanyDTO> list = companyService.getDTOList();
        assertTrue(list.size() > 30);
    }

    public void testCreation(){
        // No company creation feature in the spec for the moment
    }

    public void testDelete(){
        // No company deletion feature in the spec for the moment
    }

}
