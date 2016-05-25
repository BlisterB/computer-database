package com.excilys.computer_database.controller;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/** Bootstrap class to obtain the ApplicationContext */
public class AppContextManager {
    private static ApplicationContext context;

    public static ApplicationContext getContext(){
        if(context == null){
            synchronized (AppContextManager.class) {
                if(context == null) {
                    context = new ClassPathXmlApplicationContext("applicationContext.xml");
                }
            }
        }
        return context;
    }
}
