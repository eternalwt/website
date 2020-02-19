package com.greengiant.website.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.greengiant.website.dao.UserMapper;
import com.greengiant.website.dao.UserRoleMapper;
import com.greengiant.website.pojo.model.User;
import com.greengiant.website.pojo.model.UserRole;
import com.greengiant.website.pojo.vo.AddUserQuery;
import com.greengiant.website.service.UserService;
import com.greengiant.website.utils.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    // todo 事务放在这里，思考一下我在威盛电子服务划分过多遇到的问题
    // todo 注意：看些这些事务相关的东西能否用上：PlatformTransactionManager、DefaultTransactionDefinition、TransactionStatus

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addUser(AddUserQuery userVo) {
        //todo 考虑返回值
        //todo 考虑是否链式赋值
        User user = new User();
        user.setUserName(userVo.getUserName());
        String salt = PasswordUtil.getSalt();
        user.setPasswordSalt(salt);
        // 密码加盐加密
        user.setPassword(PasswordUtil.encrypt(userVo.getPassword(), salt));
        userMapper.insert(user);

        // todo 如何批量插入
        List<Long> roleIdList = userVo.getRoleIdList();
        if (roleIdList != null && !roleIdList.isEmpty()) {
            for (Long roleId : roleIdList) {
                UserRole userRole = new UserRole();
                userRole.setRoleId(roleId);
                userRole.setUserId(user.getId());
                // todo 为啥没加@TableName("auth_user_role")的时候抛异常了没有回滚？再复现一把
                // todo 确认抛异常后返回值显示正确
                userRoleMapper.insert(userRole);
            }
        }
    }

    @Override
    public User getByName(String userName) {
        //todo 判空在哪里做？用java8那种很简洁的语法，想明白。
        //todo 想明白了：前端从业务角度判空，后端只在最下层操作数据库的时候用java8语法判断，然后用全局异常处理返回给前端
        User user = userMapper.selectByName(userName);

        return user;
    }
}
