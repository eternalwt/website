package com.greengiant.website.dao;

import java.util.List;

import com.greengiant.website.model.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserRoleDao {
	String TABLE_NAME = "shiro_user_role";
	String ALL_FIELDS = "id, username, role_name";

	@Select("select " + ALL_FIELDS + " from " + TABLE_NAME)
	List<UserRole> getUserRoleLst();

	@Select("insert into " + TABLE_NAME +"set username= #{username}, role_name= #{rolename}")
	int addUserRole(UserRole userRole);

}
