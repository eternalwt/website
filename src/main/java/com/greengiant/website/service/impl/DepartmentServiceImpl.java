package com.greengiant.website.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.greengiant.website.dao.DepartmentMapper;
import com.greengiant.website.pojo.model.Department;
import com.greengiant.website.service.DepartmentService;
import org.springframework.stereotype.Service;

@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements DepartmentService {
}
