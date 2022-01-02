package com.greengiant.website.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.greengiant.infrastructure.utils.PasswordUtil;
import com.greengiant.website.dao.UserMapper;
import com.greengiant.website.dao.UserRoleMapper;
import com.greengiant.website.pojo.PageParam;
import com.greengiant.website.pojo.model.User;
import com.greengiant.website.pojo.model.UserRole;
import com.greengiant.website.pojo.query.PageQuery;
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
        return userMapper.selectByName(userName);
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

    @Override
    public IPage<User> getPageList(PageQuery<User> pageQuery) {
        User user = null;
        if (pageQuery.getEntity() != null) {
            user = pageQuery.getEntity();
        }

        PageParam pageParam = new PageParam();
        if (pageQuery.getPageParam() != null) {
            pageParam = pageQuery.getPageParam();
        }

        IPage<User> pg = new Page<>(pageParam.getPageNum(), pageParam.getPageSize());
        QueryWrapper<User> wrapper = generateQueryWrapper(user);
        pg = this.page(pg, wrapper);// 底层是baseMapper.selectPage()

        return pg;
    }

    private QueryWrapper<User> generateQueryWrapper(User user) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();

        if (user == null) {
            return wrapper;
        }

        if (user.getUserName() != null && !"".equals(user.getUserName())) {
            wrapper.like("user_name", user.getUserName());
        }
        // 根据需要添加更多字段

        return wrapper;
    }

}
