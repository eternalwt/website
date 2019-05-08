package com.greengiant.website.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.greengiant.website.model.Role;
import com.greengiant.website.dao.RoleDao;
import com.greengiant.website.model.ShiroUser;
import com.greengiant.website.dao.user.UserDao;
import com.greengiant.website.model.UserRole;
import com.greengiant.website.dao.UserRoleDao;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserDao shiroUserDao;
	
	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private UserRoleDao userRoleDao;
	
//	@Autowired
//	private PlatformTransactionManager transactionManager;
	
	@RequiresRoles("role1")
	//@RequiresPermissions("+user1+100")//TODO 改一下
	@RequestMapping("/adduser")
	public ModelAndView adduser()
	{
		List<Role> rolesLst = roleDao.getRoleLst();
		List<String> roleLst = new ArrayList<String>();
		for (int i = 0; i < rolesLst.size(); i++)
		{
			roleLst.add(rolesLst.get(i).getRolename());
		}
		
		ModelAndView modelAndView = new ModelAndView("user/adduser");  
        modelAndView.addObject("roleLst", roleLst);
        //TODO 比较modelAndView的传法和VO的传法
        return modelAndView;
	}
	
	//@RequiresRoles("role1")//TODO 改一下
	@RequestMapping("/add")
	@ResponseBody
	public String add(HttpServletRequest request)
	{
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String rolename = request.getParameter("role");
		
		//TODO 这里也要思考返回值是否用枚举的问题
		if (shiroUserDao.findUserByName(username))
		{
			return "duplicatedUser";
		}
		
		ShiroUser user = new ShiroUser();
		user.setUsername(username);
		String salt = new SecureRandomNumberGenerator().nextBytes().toHex();
		String md5Psw = new Md5Hash(password, salt, 2).toString();
		user.setPassword(md5Psw);
		user.setPasswordSalt(salt);
		
		UserRole userRole = new UserRole();
		userRole.setUsername(username);
		userRole.setRolename(rolename);
		
//		//TODO 这里是直接用的transactionManager，后面再看看声明式和配置式事务管理，还有抽服务层的问题
//		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
//		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
//		TransactionStatus status = transactionManager.getTransaction(def);
//		try
//        {
//            shiroUserDao.addUser(user);
//            userRoleDao.addUserRole(userRole);
//            transactionManager.commit(status);//TODO 深入分析放这里有没有问题，commit抛异常了怎么办
//
//            return "success";
//
//        }
//		catch (DataAccessException ex)
//        {
//        	transactionManager.rollback(status);
//        	return "fail";
//        }
//		catch (TransactionException ex)
//        {
//        	transactionManager.rollback(status);
//        	return "fail";
//        }

		return null;
		//TODO 显示用户列表（并能删、改）
	}

}
