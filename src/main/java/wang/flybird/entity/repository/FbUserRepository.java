package wang.flybird.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import wang.flybird.entity.FbUser;

/**
 * Created by stephan on 20.03.16.
 */
public interface FbUserRepository extends JpaRepository<FbUser, Long> {
    FbUser findById(String id);
    FbUser findByUsername(String username);
	FbUser findByWxunionid(String wxUnionId);
    
}
