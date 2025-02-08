package myapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class OdsController {

    @GetMapping("/ods/ODS")
    public ModelAndView showOdsPage() {
        ModelAndView mav = new ModelAndView("ods/ODS");  // templates/ods/ODS.html
        return mav;
    }
}
