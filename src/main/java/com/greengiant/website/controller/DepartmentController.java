package com.greengiant.website.controller;

import com.greengiant.website.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dept")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

}
