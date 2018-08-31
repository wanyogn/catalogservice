package com.zhixie.catalog.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.Map;

@Mapper
public interface ClinicalTrialFacilityMapper {

    ArrayList<Map<String,Object>> selectClinicalInstitutionListByMap(Map<String,Object> map);

    int selectClinicalInstitutionListCountByMap(Map<String,Object> map);

    Map<String,Object> selectClinicalInstitutionListById(String id);

    ArrayList<Map<String,Object>> selectClinicalInstitutionListByInfoMap(Map<String,Object> map);

    ArrayList<Map<String,Object>> selectClinicalInstitutionInfoByMap(Map<String,Object> map);

    int selectClinicalInstitutionInfoCountByMap(Map<String,Object> map);

    ArrayList<Map<String,Object>> selectClinicalInstitutionInfoByPid(String pid);

    ArrayList<Map<String,Object>> selectClinicalInstitutionListGroupByProvinceByName(String name);

    ArrayList<Map<String,Object>> selectInstitutionEthicalInfoByName(String name);
}
