package com.zhixie.catalog.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.Map;

/**
 * 医博会展会Mapper
 */
@Mapper
public interface ExhibitionMapper {
    /**
     * 根据展商名称查找
     * @param
     * @return
     */
    ArrayList<Map<String,Object>> selectExhibition(Map<String,Object> map);

    /**
     * 查询数量
     * @param map
     * @return
     */
    int selectCountByName(Map<String,Object> map);
}
