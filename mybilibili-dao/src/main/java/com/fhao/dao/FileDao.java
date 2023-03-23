package com.fhao.dao;

import com.fhao.domain.File;
import org.apache.ibatis.annotations.Mapper;

/**
 * author: FHao
 * create time: 2023-03-23 15:54
 * description:
 */
@Mapper
public interface FileDao {
    Integer addFile(File file);

    File getFileByMD5(String md5);
}
