package com.excilys.computer_database.database.dao;

public class NotFoundException extends Exception {
    public NotFoundException(Long id){
        super("No entry found with the id : " + id);
    }
}
