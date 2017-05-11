package wang.flybird.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import wang.flybird.entity.FbOptLog;

public interface SysLogRepository extends JpaRepository<FbOptLog, Long>  {

}
