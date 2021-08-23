package com.greengiant.website.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.greengiant.website.pojo.model.FileInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileInfoMapper extends BaseMapper<FileInfo> {
}
