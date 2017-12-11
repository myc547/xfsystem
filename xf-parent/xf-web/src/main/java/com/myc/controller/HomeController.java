package com.myc.controller;

import com.ardsec.framework.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author myc
 * @version 1.0, 2017/12/5
 */
@Controller
public class HomeController extends BaseController {

    @RequestMapping("/home")
    public String home() {
        return "home/home";
    }
}
