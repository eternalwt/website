package com.greengiant.website.service.impl;

import com.greengiant.website.dao.UserMapper;
import com.greengiant.website.dao.UserRoleMapper;
import com.greengiant.website.pojo.model.User;
import com.greengiant.website.pojo.model.UserRole;
import com.greengiant.website.pojo.vo.AddUserVo;
import com.greengiant.website.service.UserService;
import com.greengiant.website.utils.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    //todo 事务放在这里，思考一下我在威盛电子服务划分过多遇到的问题
    //3.注意：加密和事务。看些这些东西能否用上：PlatformTransactionManager、DefaultTransactionDefinition、TransactionStatus

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addUser(AddUserVo userVo) {
        //todo 考虑返回值
        //todo 考虑是否链式赋值

        User user = new User();
        user.setUserName(userVo.getUserName());
        String salt = PasswordUtil.getSalt();// todo 这里加密相关的代码怎么写的更加模块化？
        user.setPasswordSalt(salt);
        // 密码加盐加密
        user.setPassword(PasswordUtil.encrypt(userVo.getPassword(), salt));
        userMapper.insert(user);

        UserRole userRole = new UserRole();
        userRole.setRoleId(userVo.getRoleId());
        userRole.setUserId(user.getId());
        userRoleMapper.insert(userRole);
    }

    @Override
    public User getByName(String userName) {
        //todo 判空在哪里做？用java8那种很简洁的语法，想明白。
        //todo 想明白了：前端从业务角度判空，后端只在最下层操作数据库的时候用java8语法判断，然后用全局异常处理返回给前端
        User user = userMapper.selectByName(userName);

        return user;
    }
}
