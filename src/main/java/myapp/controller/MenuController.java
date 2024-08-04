package myapp.controller;

import myapp.entity.Menu;
import myapp.service.MenuService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping
    public ModelAndView getMenu() {
    	return getMenu("coffee");
    }

    @GetMapping("/coffee")
    public ModelAndView getCoffee() {
        return getMenu("coffee");
    }
    
    @GetMapping("/aid")
    public ModelAndView getAid() {
        return getMenu("aid");
    }
    
    @GetMapping("/cookie")
    public ModelAndView getCookie() {
        return getMenu("cookie");
    }
    
    @GetMapping("/bread")
    public ModelAndView getBread() {
        return getMenu("bread");
    }
    
    @GetMapping("/cake")
    public ModelAndView getCake() {
        return getMenu("cake");
    }
    
    private ModelAndView getMenu(String category) {
        List<Menu> menus = menuService.getMenuByValue(category);// 데이터베이스에서 category라는 조건으로 검색
        ModelAndView modelAndView = new ModelAndView(category);
        modelAndView.addObject("menus", menus);//menus에 맞는 데이터 저장
        modelAndView.addObject("activeCategory", category);//카테고리에 맞는 버튼 색상 뒷배경과 통일
        return modelAndView;
    }
}
