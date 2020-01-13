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

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public String getRole(String username) {
        //根据权限，指定返回数据
        Role role = roleMapper.selectByName(username);
        if (role != null) {
            //todo 重构
            if ("user".equals(role.getRoleName())) {
                return "欢迎登陆";
            }
            if ("admin".equals(role.getRoleName())) {
                return "欢迎来到管理员页面";
            }
        }
        return "尚未分配角色";
    }

    @Override
    public void addRole(Role role) {
        roleMapper.insert(role);
    }

    @Override
    public List<Role> getRoleListByUserName(String userName) {
        List<Role> roleList = new ArrayList<>();
        // 1.根据userName得到userId
        User user = userMapper.selectByName(userName);
        if (user != null) {
            List<UserRole> userRoleList = userRoleMapper.selectByUserId(user.getId());
            if (userRoleList != null && !userRoleList.isEmpty()) {
                QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
                // todo
                //queryWrapper.in()
                //roleMapper.selectList()
            }
        }
        return roleList;
    }

}
