package com.greengiant.website.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.greengiant.infrastructure.utils.PasswordUtil;
import com.greengiant.website.dao.UserMapper;
import com.greengiant.website.dao.UserRoleMapper;
import com.greengiant.website.pojo.model.User;
import com.greengiant.website.pojo.model.UserRole;
import com.greengiant.website.pojo.query.UserQuery;
import com.greengiant.website.service.UserRoleService;
import com.greengiant.website.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    UserRoleService userRoleService;

    // todo 事务放在这里，思考一下我在威盛电子服务划分过多遇到的问题
    // todo 注意：看些这些事务相关的东西能否用上：PlatformTransactionManager、DefaultTransactionDefinition、TransactionStatus

    @Override
    @Transactional
    public boolean addUser(UserQuery userQuery) {
        try {
            User user = new User();
            user.setUserName(userQuery.getUserName());
            String salt = PasswordUtil.getSalt();
            user.setPasswordSalt(salt);
            // 密码加盐加密
            user.setPassword(PasswordUtil.encrypt(userQuery.getPassword(), salt));
            userMapper.insert(user);

            // 批量插入
            List<Long> roleIdList = userQuery.getRoleIdList();
            List<UserRole> userRoleList = new ArrayList<>();
            if (roleIdList != null && !roleIdList.isEmpty()) {
                for (Long roleId : roleIdList) {
                    UserRole userRole = new UserRole();
                    userRole.setRoleId(roleId);
                    userRole.setUserId(user.getId());
                    userRoleMapper.insert(userRole);
                }
                userRoleService.saveBatch(userRoleList);
            }
            return true;
        } catch (Exception ex) {
            log.error("addUser failed...", ex);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
    }

    @Override
    @Transactional
    public boolean delUser(Long userId) {
        try {
            this.removeById(userId);

            Map<String, Object> map = new HashMap<>(16);
            map.put("user_id", userId);
            userRoleMapper.deleteByMap(map);

            return true;
        } catch (Exception ex) {
            log.error("delUser failed...", ex);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
    }

    @Override
    public User getByName(String userName) {
        User user = userMapper.selectByName(userName);

        return user;
    }

    @Override
    public int changePassword(User user, String rawPassword) {
        String newPassword = PasswordUtil.encrypt(rawPassword, user.getPasswordSalt());
        user.setPassword(newPassword);

        UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<>();
        userUpdateWrapper.eq("user_name", user.getUserName());
        int update = userMapper.update(user, userUpdateWrapper);

        return update;
    }
}
