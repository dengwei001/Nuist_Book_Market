package com.nuist.bookmMarket.controller;

import org.apache.logging.log4j.core.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
public class ViewController {

    private org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("/bookList")
    public String toBookList(){

        logger.debug("##############");
        return "bookList";
    }


}
