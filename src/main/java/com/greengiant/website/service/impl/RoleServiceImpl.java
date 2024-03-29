package com.greengiant.website.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.greengiant.website.dao.RoleMapper;
import com.greengiant.website.dao.UserMapper;
import com.greengiant.website.dao.UserRoleMapper;
import com.greengiant.website.pojo.PageParam;
import com.greengiant.website.pojo.model.Role;
import com.greengiant.website.pojo.model.RolePermission;
import com.greengiant.website.pojo.model.User;
import com.greengiant.website.pojo.model.UserRole;
import com.greengiant.website.pojo.query.PageQuery;
import com.greengiant.website.service.RolePermissionService;
import com.greengiant.website.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

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

    @Autowired
    private RolePermissionService rolePermissionService;


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

    @Override
    public int editRole(Role role) {
        UpdateWrapper<Role> roleUpdateWrapper = new UpdateWrapper<>();
        roleUpdateWrapper.eq("id", role.getId());

        return roleMapper.update(role, roleUpdateWrapper);
    }

    @Override
    @Transactional
    public boolean delRole(Long roleId) {
        try {
            this.removeById(roleId);

            // todo 该role已经被赋予某些user的两种处理策略：1.把user-role删掉（也应该先单独判断提示一下）；2.不允许删除
            // 删除角色权限关联
            QueryWrapper<RolePermission> wrapper = new QueryWrapper<>();
            wrapper.eq("role_id", roleId);
            return rolePermissionService.remove(wrapper);
        } catch (Exception ex) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error("delRole failed...", ex);
            return false;
        }
    }

    @Override
    public Role selectByName(String roleName) {
        return roleMapper.selectByName(roleName);
    }

    @Override
    public IPage<Role> getPageList(PageQuery<Role> pageQuery) {
        Role role = null;
        if (pageQuery.getEntity() != null) {
            role = pageQuery.getEntity();
        }

        PageParam pageParam = new PageParam();
        if (pageQuery.getPageParam() != null) {
            pageParam = pageQuery.getPageParam();
        }

        IPage<Role> pg = new Page<>(pageParam.getPageNum(), pageParam.getPageSize());
        QueryWrapper<Role> wrapper = generateQueryWrapper(role);
        pg = this.page(pg, wrapper);// 底层是baseMapper.selectPage()

        return pg;
    }

    private QueryWrapper<Role> generateQueryWrapper(Role role) {
        QueryWrapper<Role> wrapper = new QueryWrapper<>();

        if (role == null) {
            return wrapper;
        }

        if (role.getRoleName() != null && !"".equals(role.getRoleName())) {
            wrapper.like("role_name", role.getRoleName());
        }
        // 根据需要添加更多字段

        return wrapper;
    }

    private String getRoleName(String roleId, List<Role> roleList) {
        String roleName = "";
        if (roleId != null) {
            for (Role role : roleList) {
                if (roleId.equals(role.getId().toString())) {
                    return role.getRoleName();
                }
            }
        }

        return roleName;
    }

}
