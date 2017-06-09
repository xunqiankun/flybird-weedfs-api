package wang.flybird.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import wang.flybird.entity.FbUserAccount;

public interface FbUserAccountRepository  extends JpaRepository<FbUserAccount, Long>  {

	FbUserAccount findByClientTypeAndAccountTypeAndWxAppidAndWxOpenid(String clientType, String accountType,
			String wxAppId, String wxOpenid);

	FbUserAccount findById(String accountId);

	

}
