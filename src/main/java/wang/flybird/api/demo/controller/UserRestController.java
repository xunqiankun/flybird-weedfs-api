package wang.flybird.api.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.annotations.ApiIgnore;
import wang.flybird.api.security.JwtTokenUtil;
import wang.flybird.api.security.JwtUser;
import wang.flybird.api.security.service.JwtUserDetailsServiceImpl;

import javax.servlet.http.HttpServletRequest;

@RestController
@ApiIgnore
public class UserRestController {

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsServiceImpl userDetailsService;

    @RequestMapping(value = "user", method = RequestMethod.GET)
    public JwtUser getAuthenticatedUser(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        String userid = jwtTokenUtil.getUseridFromToken(token);
        JwtUser user = (JwtUser) userDetailsService.loadUserByUserid(userid);
        return user;
    }

}
