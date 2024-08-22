package myapp.controller;

import myapp.entity.Menu;
import myapp.service.MenuService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Page;

import java.util.List;

@Controller
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping
    public ModelAndView getMenu(@RequestParam(defaultValue = "0") int page) {
    	return getMenu("coffee",page);
    }

    @GetMapping("/coffee")
    public ModelAndView getCoffee(@RequestParam(defaultValue = "0") int page) {
        return getMenu("coffee",page);
    }
    
    @GetMapping("/aid")
    public ModelAndView getAid(@RequestParam(defaultValue = "0") int page) {
        return getMenu("aid",page);
    }
    
    @GetMapping("/cookie")
    public ModelAndView getCookie(@RequestParam(defaultValue = "0") int page) {
        return getMenu("cookie",page);
    }
    
    @GetMapping("/bread")
    public ModelAndView getBread(@RequestParam(defaultValue = "0") int page) {
        return getMenu("bread",page);
    }
    
    @GetMapping("/cake")
    public ModelAndView getCake(@RequestParam(defaultValue = "0") int page) {
        return getMenu("cake",page);
    }
    
    private ModelAndView getMenu(String category,@RequestParam(defaultValue = "0") int page) {
    	
    	int pageSize = 12; // Number of items per page
        Page<Menu> menuPage = menuService.getMenuByCategory(category, page, pageSize);//데이터베이스에 메뉴의 개수 확인용
        
        List<Menu> menus = menuService.getMenuByValue(category);// 데이터베이스에서 category라는 조건으로 검색
        ModelAndView modelAndView = new ModelAndView(category);
        modelAndView.addObject("menus", menus);//menus에 맞는 데이터 저장
        modelAndView.addObject("activeCategory", category);//카테고리에 맞는 버튼 색상 뒷배경과 통일
        modelAndView.addObject("totalPages", menuPage.getTotalPages()); // 전체페이지 수
        modelAndView.addObject("currentPage", page); // 현재 페이지
        modelAndView.addObject("activeCategory", category); // Highlight the active category
        return modelAndView;
    }
}
