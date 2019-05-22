package com.greengiant.website.service.impl;

import com.greengiant.website.dao.UserDao;
import com.greengiant.website.dao.UserRoleDao;
import com.greengiant.website.pojo.model.User;
import com.greengiant.website.pojo.model.UserRole;
import com.greengiant.website.pojo.vo.AddUserVo;
import com.greengiant.website.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserRoleDao userRoleDao;

    //todo 事务放在这里，思考一下我在威盛电子服务划分过多遇到的问题

    @Override
    @Transactional(rollbackFor = Exception.class)//todo 把几个选项都看一下
    public void addUser(AddUserVo userVo) {
        //todo 考虑返回值
        //todo 考虑是否链式赋值

        //todo 事务
        User user = new User();
        user.setUserName(userVo.getUserName());
        user.setPassword(userVo.getPassword());
        //todo 生成随机数，看是用shiro带的，还是用JDK 8安全随机数生成器
        user.setPasswordSalt("123456");
        int userId = userDao.insert(user);//todo 确认这里是不是返回id

        // todo 1.搞懂Transactional注解；2.测试抛异常的情况

        UserRole userRole = new UserRole();
        userRole.setRoleId(userVo.getRoleId());
        userRole.setUserId((long)userId);
        userRoleDao.insert(userRole);
    }

    @Override
    public User getByName(String userName) {
        //todo 判空在哪里做？用java8那种很简洁的语法，想明白
        User user = userDao.selectByName(userName);

        return user;
    }
}
