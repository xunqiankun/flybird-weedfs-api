package wang.flybird.api.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import wang.flybird.api.security.JwtUser;
import wang.flybird.api.security.JwtUserFactory;
import wang.flybird.entity.FbUser;
import wang.flybird.entity.FbUserAccount;
import wang.flybird.entity.repository.FbUserAccountRepository;
import wang.flybird.entity.repository.FbUserRepository;

/**
 * Created by stephan on 20.03.16.
 */
@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private FbUserRepository userRepository;
    
    @Autowired
    private FbUserAccountRepository fbUserAccountRepository;

    public JwtUser loadUserByUserid(String userid) throws UsernameNotFoundException {
        FbUser user = userRepository.findById(userid);

        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with userid '%s'.", userid));
        } else {
            return JwtUserFactory.create(user);
        }
    }

	@Override
	public JwtUser loadUserByUsername(String username) throws UsernameNotFoundException {
		FbUser user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        } else {
            return JwtUserFactory.create(user);
        }
	}

	public JwtUser loadUserByAccountId(String accountId) {
		
		FbUserAccount fbUserAccount = fbUserAccountRepository.findById(accountId);
		String fbUserId = fbUserAccount.getFbUserId();
		
		FbUser user = userRepository.findById(fbUserId);

        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with accountId '%s'.", accountId));
        } else {
            return JwtUserFactory.create(user);
        }
	}
	
	
}
