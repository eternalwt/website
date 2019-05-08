package com.greengiant.website.dao.role;

import java.util.List;

import com.greengiant.website.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RoleJdbcDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<Role> getRoleLst() {
		String SQL = "select * from shiro_roles_permissions";
		List<Role> RoleLst = jdbcTemplate.query(SQL, new RoleMapper());
		return RoleLst;
	}
}
