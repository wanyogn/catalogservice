package com.zhixie.catalog.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;
@Mapper
public interface PcSearchMapper {
    int insertUserSearchInfo(Map<String, Object> map);
}
