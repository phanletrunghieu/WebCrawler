/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Phan Hieu
 */
@Controller
@RequestMapping(value = "/hello")
public class UserController {
    
    @RequestMapping(method = RequestMethod.GET)
    public String userAction(){
        return "index";
    }
}
