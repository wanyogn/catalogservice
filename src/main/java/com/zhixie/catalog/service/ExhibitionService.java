package com.zhixie.catalog.service;

import java.util.ArrayList;
import java.util.Map;

/**
 * 医博会展商Service
 */
public interface ExhibitionService {
    /**
     * 根据展商名称查找
     * @param
     * @return
     */
    ArrayList<Map<String,Object>> selectExhibition(Map<String,Object> map);

    /**
     * 查找数量
     * @param map
     * @return
     */
    int selectCountByName(Map<String,Object> map);
}
