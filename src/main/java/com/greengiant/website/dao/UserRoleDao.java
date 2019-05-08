package com.greengiant.website.dao;

import java.util.List;

import com.greengiant.website.model.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserRoleDao {

	@Select("select * from shiro_user_roles")//todo
	List<UserRole> getUserRoleLst();

	@Select("insert into shiro_user_roles(`username`,`role_name`) values(?, ?)")
	int addUserRole(UserRole userRole);

}
