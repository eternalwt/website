package com.greengiant.website.controller;

import com.greengiant.website.pojo.ResultBean;
import com.greengiant.website.service.MenuService;
import com.greengiant.website.service.PermService;
import com.greengiant.website.utils.ResultUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    private PermService permService;// todo 这个service有坏味道，看看要不要干掉

    @Autowired
    private MenuService menuService;

//    @RequiresRoles("admin")
//    @RequiresPermissions("aaa")
    @RequestMapping(value = "/isPermitted", method = RequestMethod.GET)
    public ResultBean checkPermission() {
        Subject subject = SecurityUtils.getSubject();
        subject.hasRole("admin");
        boolean result = subject.isPermitted("admin");
        // todo 这个方法测完可以干掉。这里为啥principal为空？是不是没传session等过来？
        return ResultUtils.success();
    }

    @RequestMapping(value = "/getAllPermissionList", method = RequestMethod.GET)
    public ResultBean getAllPermissionList() {
        // todo 把返回值的异常处理加一下
        return ResultUtils.success(menuService.list());
    }

    // todo restful改造
    @RequestMapping(value = "/getPermissionListByUserId", method = RequestMethod.GET)
    public ResultBean getPermissionListByUserId(Long roleId) {
        Map<String, Object> queryMap = new HashedMap();
        queryMap.put("role", roleId);
        return ResultUtils.success(menuService.listByMap(queryMap));
    }

    @RequestMapping(value = "/getRolePermissionListMap", method = RequestMethod.GET)
    public ResultBean getRolePermissionListMap() {
        return ResultUtils.success(permService.getRolePermissionListMap());
    }

}
