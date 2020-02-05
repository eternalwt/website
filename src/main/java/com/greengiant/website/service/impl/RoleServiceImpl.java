package com.greengiant.website.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.greengiant.website.dao.RoleMapper;
import com.greengiant.website.dao.UserMapper;
import com.greengiant.website.dao.UserRoleMapper;
import com.greengiant.website.pojo.model.Role;
import com.greengiant.website.pojo.model.User;
import com.greengiant.website.pojo.model.UserRole;
import com.greengiant.website.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;


    @Override
    public List<Role> getRoleListByUserName(String userName) {
        List<Role> roleList = new ArrayList<>();
        // 1.根据userName得到userId
        User user = userMapper.selectByName(userName);
        if (user != null) {
            List<UserRole> userRoleList = userRoleMapper.selectByUserId(user.getId());
            if (userRoleList != null && !userRoleList.isEmpty()) {
                QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
                queryWrapper.in("id", userRoleList.stream().map(UserRole::getRoleId).collect(Collectors.toList()));
                roleList = roleMapper.selectList(queryWrapper);
            }
        }
        return roleList;
    }

}
