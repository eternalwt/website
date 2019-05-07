package com.greengiant.website.dao.user;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ShiroUserMapper implements RowMapper<ShiroUser>{
	public ShiroUser mapRow(ResultSet rs, int rowNum) throws SQLException
	{
		ShiroUser shiroUser = new ShiroUser();
		shiroUser.setId(rs.getInt("id"));
		shiroUser.setUsername(rs.getString("username"));
		shiroUser.setPassword(rs.getString("password"));
		shiroUser.setPassword_salt(rs.getString("password_salt"));
		
		return shiroUser;
	}
}
