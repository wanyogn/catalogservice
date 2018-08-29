package com.zhixie.catalog.service;

import java.util.ArrayList;
import java.util.Map;

public interface ClinicalTrialFacilityService {

    //根据条件查询临床试验机构备案列表信息
    ArrayList<Map<String,Object>> selectClinicalInstitutionListByMap(Map<String,Object> map);

    //根据条件查询临床试验机构备案列表信息数量
    int selectClinicalInstitutionListCountByMap(Map<String,Object> map);

    //根据id查询临床试验机构备案列表信息
    Map<String,Object> selectClinicalInstitutionListById(String id);

    //根据条件查询临床试验机构备案专业信息
    ArrayList<Map<String,Object>> selectClinicalInstitutionInfoByMap(Map<String,Object> map);

    //根据条件查询临床试验机构备案专业数量
    int selectClinicalInstitutionInfoCountByMap(Map<String,Object> map);

    //根据pid查询临床试验机构备案专业信息
    ArrayList<Map<String,Object>> selectClinicalInstitutionInfoByPid(String pid);

    //根据关键字查询临床试验机构备案列表聚合信息(聚合省份)
    ArrayList<Map<String,Object>> selectClinicalInstitutionListGroupByProvinceByName(String name);
}
