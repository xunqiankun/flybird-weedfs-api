package wang.flybird.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import wang.flybird.entity.FbMessage;

public interface FbMessageRepository   extends JpaRepository<FbMessage, Long>  {
	
}
