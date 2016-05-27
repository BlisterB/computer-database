package com.excilys.computer_database.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorController {

    @RequestMapping(value="/404")
    public String handle404(Exception ex) {
        return "404";
    }


    @RequestMapping(value="/403")
    public String handle403(Exception ex) {
        return "403";
    }

    /*
    @RequestMapping(value="/500")
    public String handle500(Exception ex) {
        return "500";
    }
     */
}
