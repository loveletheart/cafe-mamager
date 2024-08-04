package myapp.controller;

import myapp.entity.Menu;
import myapp.service.MenuService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Controller
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping
    public ModelAndView getMenu() {
    	 List<Menu> menus = menuService.getMenuByValue("coffee");//데이터베이스에서 coffee라는 조건으로 검색
         ModelAndView modelAndView = new ModelAndView("coffee");
         System.out.println("Fetched menus: " + menus);
         modelAndView.addObject("menus", menus);
         return modelAndView;
    }

    @GetMapping("/coffee")
    public ModelAndView getCoffee() {
        List<Menu> menus = menuService.getMenuByValue("coffee");
        ModelAndView modelAndView = new ModelAndView("coffee");
        modelAndView.addObject("menus", menus);
        return modelAndView;
    }
    
    @GetMapping("/aid")
    public ModelAndView getAid() {
        List<Menu> menus = menuService.getMenuByValue("aid");
        ModelAndView modelAndView = new ModelAndView("aid");
        modelAndView.addObject("menus", menus);
        return modelAndView;
    }
    
    @GetMapping("/cookie")
    public ModelAndView getCookie() {
        List<Menu> menus = menuService.getMenuByValue("cookie");
        ModelAndView modelAndView = new ModelAndView("cookie");
        modelAndView.addObject("menus", menus);
        return modelAndView;
    }
    
    @GetMapping("/bread")
    public ModelAndView getBread() {
        List<Menu> menus = menuService.getMenuByValue("bread");
        ModelAndView modelAndView = new ModelAndView("bread");
        modelAndView.addObject("menus", menus);
        return modelAndView;
    }
    
    @GetMapping("/cake")
    public ModelAndView getCake() {
        List<Menu> menus = menuService.getMenuByValue("cake");
        ModelAndView modelAndView = new ModelAndView("cake");
        modelAndView.addObject("menus", menus);
        return modelAndView;
    }
}
