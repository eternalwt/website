package com.greengiant.website.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.greengiant.website.pojo.model.FileInfo;

public interface FileInfoService extends IService<FileInfo> {

    boolean isExists(String md5);

}
