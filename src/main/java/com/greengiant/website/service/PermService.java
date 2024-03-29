package com.greengiant.website.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.greengiant.website.pojo.model.Perm;

import java.util.List;

public interface PermService extends IService<Perm> {

    List<Perm> getPermList(Perm perm);

}
