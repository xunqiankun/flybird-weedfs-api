package wang.flybird.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import wang.flybird.entity.FbUser;

/**
 * Created by stephan on 20.03.16.
 */
public interface SysUserRepository extends JpaRepository<FbUser, Long> {
    FbUser findByUsername(String username);
}
