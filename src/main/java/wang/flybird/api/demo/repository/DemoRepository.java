package wang.flybird.api.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import wang.flybird.api.demo.viewmodel.DemoInfo;
import wang.flybird.entity.User;

public interface DemoRepository extends JpaRepository<User, Long>{
	@Query("select d.name as name, di.sex as sex  "
	        + "from Demo d ,DemoInfo di where d.id=di.id and d.id = ?1 group by d.id")
	Page<DemoInfo> findById(String id, Pageable pageable);
	
	//Page<User> findById(String id);
	
	
}
