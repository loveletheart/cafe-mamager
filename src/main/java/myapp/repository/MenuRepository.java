package myapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import myapp.entity.Menu;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu, String> {
	 @Query("SELECT m FROM Menu m WHERE m.type = :menu")
	  List<Menu> findByMenu(@Param("menu") String menu); // 수정된 쿼리
}
