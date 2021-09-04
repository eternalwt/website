package com.greengiant.website.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.greengiant.website.dao.PermissionMapper;
import com.greengiant.website.pojo.model.Perm;
import com.greengiant.website.service.PermService;
import org.springframework.stereotype.Service;

@Service
public class PermServiceImpl extends ServiceImpl<PermissionMapper, Perm> implements PermService {

}
