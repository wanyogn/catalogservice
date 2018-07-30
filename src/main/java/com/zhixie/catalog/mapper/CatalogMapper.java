package com.zhixie.catalog.mapper;

import com.zhixie.catalog.model.Catalogs;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CatalogMapper {
    List<Map<String,Object>> selectDirectory(int pid);

    Catalogs selectDirByCode(Map<String,Object> map);
}
