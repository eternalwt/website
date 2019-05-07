package com.greengiant.website.dao.userrole;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.gao.dao.user.ShiroUser;

@Repository
public class UserRoleJdbcTemplate {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public List<UserRole> getUserRoleLst() {
		String SQL = "select * from shiro_user_roles";
		List<UserRole> userRoleLst = jdbcTemplate.query(SQL, new UserRoleMapper());
		return userRoleLst;
	}
	
	public int addUserRole(UserRole userRole){
		int rowCount = jdbcTemplate.update("INSERT INTO shiro_user_roles(`username`,`role_name`) VALUES(?, ?)",
				new Object[] {userRole.getUsername(), userRole.getRolename()});
		
		return rowCount;
	}
	
}
