package com.greengiant.website.dao.user;

import java.util.List;

import com.greengiant.website.model.ShiroUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ShiroUserDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<ShiroUser> getShiroUserLst() {
		String SQL = "select * from shiro_users";
		List<ShiroUser> shiroUserLst = jdbcTemplate.query(SQL, new ShiroUserMapper());
		return shiroUserLst;
	}
	
	public int addUser(ShiroUser user){
		int rowCount = jdbcTemplate.update("INSERT INTO shiro_users(`username`,`password`,`password_salt`) VALUES(?, ?, ?)",
				new Object[] {user.getUsername(), user.getPassword(), user.getPassword_salt()});
		
		return rowCount;
	}
	
	public boolean findUserByName(String name)
	{
		String sql = "select * from shiro_users where username=?";
		
		try
		{
			ShiroUser user = jdbcTemplate.queryForObject(sql, new Object[]{name}, new ShiroUserMapper());
			
			if (user != null)
			{
				return true;
			}
		}
		catch(EmptyResultDataAccessException ex)
		{
			return false;
		}		
		
		return false;
	}
	
}
