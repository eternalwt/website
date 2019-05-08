package com.greengiant.website.dao.role;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.greengiant.website.model.Role;
import org.springframework.jdbc.core.RowMapper;

public class RoleMapper implements RowMapper<Role>{
	public Role mapRow(ResultSet rs, int rowNum) throws SQLException
	{
		Role role = new Role();
		role.setId(rs.getInt("id"));
		role.setRolename(rs.getString("role_name"));
		role.setPermissions(rs.getString("permission"));
		
		return role;
	}
}
