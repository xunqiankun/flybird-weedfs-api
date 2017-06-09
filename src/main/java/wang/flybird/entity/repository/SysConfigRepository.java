package wang.flybird.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import wang.flybird.entity.SysConfig;

public interface SysConfigRepository  extends JpaRepository<SysConfig, Long>  {

	SysConfig findByKey(String wxAppId);

}
