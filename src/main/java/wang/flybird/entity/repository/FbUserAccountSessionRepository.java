package wang.flybird.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import wang.flybird.entity.FbUserAccountSession;

public interface FbUserAccountSessionRepository   extends JpaRepository<FbUserAccountSession, Long>  {

	FbUserAccountSession findByFbUserAccountId(String fbUserAccountId);

	@Modifying(clearAutomatically = true)
	@Query(value = "update fb_user_account_session t set t.token =?1 where t.fb_user_account_id = ?2",nativeQuery = true)
	int updateToken(String token, String fbUserAccountId);

}
