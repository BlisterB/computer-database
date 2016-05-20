package com.excilys.computer_database.helpers;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.excilys.computer_database.entity.Company;

public class Test {
    public static void main(String[] arg) {
        ApplicationContext context = new ClassPathXmlApplicationContext("Bean.xml");
        Company o = (Company) context.getBean("company");
        System.out.println(o);
    }
}
