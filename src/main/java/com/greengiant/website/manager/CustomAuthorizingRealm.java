package com.greengiant.website.manager;

import java.util.List;

import com.greengiant.website.model.ShiroUser;
import com.greengiant.website.dao.user.UserDao;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.realm.Realm;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@Deprecated
public class CustomAuthorizingRealm implements Realm {

	private UserDao userDao;
	
	public CustomAuthorizingRealm()
	{
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("../applicationContext.xml");
		userDao = (UserDao) context.getBean("userDao");
	}
	
	public String getName() {
		return "MySQLRealm";
	}

	public boolean supports(AuthenticationToken token) {
		// 仅支持UsernamePasswordToken类型的Token
		return token instanceof UsernamePasswordToken;
	}

	@Override
	public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
    	String username = (String)token.getPrincipal();
        String password = new String((char[])token.getCredentials());
        
    	boolean isUserExists = false;
    	ShiroUser currUser = null;
    	List<ShiroUser> shiroUsers = userDao.getShiroUserList();
		for (ShiroUser shiroUser : shiroUsers) {
			if (shiroUser.getUsername().equals(username))
			{
				isUserExists = true;
				currUser = shiroUser;
				break;
			}
		}
		
		if (!isUserExists){
			throw new UnknownAccountException(); //用户名不存在
		}
		else if (!currUser.getPassword().equals(password)){
			throw new IncorrectCredentialsException(); //密码错误
		}
    	
        return new SimpleAuthenticationInfo(username, password, this.getName());
    }

}
