package com.greengiant.website.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.greengiant.website.dao.FileConfigMapper;
import com.greengiant.website.pojo.model.FileConfig;
import com.greengiant.website.service.FileConfigService;
import org.springframework.stereotype.Service;

@Service
public class FileConfigServiceImpl extends ServiceImpl<FileConfigMapper, FileConfig> implements FileConfigService {
}
