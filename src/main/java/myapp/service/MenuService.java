package myapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import myapp.entity.Menu;
import myapp.repository.MenuRepository;
import java.util.List;

@Service
public class MenuService {

    @Autowired
    private MenuRepository menuRepository;

    public List<Menu> getMenuByValue(String menuValue) {
    	List<Menu> menus = menuRepository.findByMenu(menuValue);
    	menus.forEach(menu -> System.out.println(menu.getMenuName()));
        
        return menus;
    }
}
