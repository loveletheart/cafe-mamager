package myapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import myapp.entity.Menu;
import myapp.repository.MenuRepository;

@Service
public class MenuService {

    @Autowired
    private MenuRepository menuRepository;
    
    public Page<Menu> getMenuByCategory(String category,int page, int size) {
    	PageRequest pageRequest = PageRequest.of(page, size);
        return menuRepository.findByPage(category, pageRequest);
    }
}
