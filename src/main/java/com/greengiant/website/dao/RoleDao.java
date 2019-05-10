package com.greengiant.website.dao;

import java.util.List;

import com.greengiant.website.model.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface RoleDao {
	String TABLE_NAME = "shiro_role_permission";
	String ALL_FIELDS = "id, role_name, permission";


	//void addRole();//todo

	@Select("select " + ALL_FIELDS + " from " + TABLE_NAME)
	List<Role> getRoleLst();
}
