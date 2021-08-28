package com.greengiant.website.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.greengiant.website.dao.FileInfoMapper;
import com.greengiant.website.pojo.model.FileInfo;
import com.greengiant.website.service.FileInfoService;
import org.springframework.stereotype.Service;

@Service
public class FileInfoServiceImpl extends ServiceImpl<FileInfoMapper, FileInfo> implements FileInfoService {
    @Override
    public boolean isExists(String md5) {
        if (md5 == null) {
            return false;
        }

        // todo 添加判断逻辑
        return false;
    }
}
