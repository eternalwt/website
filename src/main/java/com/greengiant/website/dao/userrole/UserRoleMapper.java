package com.greengiant.website.dao.userrole;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.greengiant.website.model.UserRole;
import org.springframework.jdbc.core.RowMapper;

public class UserRoleMapper implements RowMapper<UserRole>{
	public UserRole mapRow(ResultSet rs, int rowNum) throws SQLException
	{
		UserRole userRole = new UserRole();
		userRole.setId(rs.getInt("id"));
		userRole.setUsername(rs.getString("username"));
		userRole.setRolename(rs.getString("role_name"));
		
		return userRole;
	}
}
