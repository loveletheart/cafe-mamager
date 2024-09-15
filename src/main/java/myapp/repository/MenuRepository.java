package myapp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import myapp.entity.Menu;

@Repository
public interface MenuRepository extends JpaRepository<Menu, String> {
	 @Query("SELECT m FROM Menu m WHERE m.type = :menu")
	 Page<Menu> findByPage(String menu, Pageable pageable);
}
