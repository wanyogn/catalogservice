package com.zhixie.catalog.service.impl;

import com.zhixie.catalog.mapper.ClinicalTrialFacilityMapper;
import com.zhixie.catalog.service.ClinicalTrialFacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

@Service
public class ClinicalTrialFacilityServiceImpl implements ClinicalTrialFacilityService {

    @Autowired
    private ClinicalTrialFacilityMapper clinicalTrialFacilityMapper;

    @Override
    public ArrayList<Map<String, Object>> selectClinicalInstitutionListByMap(Map<String,Object> map) {
        return clinicalTrialFacilityMapper.selectClinicalInstitutionListByMap(map);
    }

    @Override
    public int selectClinicalInstitutionListCountByMap(Map<String, Object> map) {
        return clinicalTrialFacilityMapper.selectClinicalInstitutionListCountByMap(map);
    }

    @Override
    public Map<String, Object> selectClinicalInstitutionListById(String id) {
        return clinicalTrialFacilityMapper.selectClinicalInstitutionListById(id);
    }

    @Override
    public ArrayList<Map<String, Object>> selectClinicalInstitutionListByInfoMap(Map<String, Object> map) {
        return clinicalTrialFacilityMapper.selectClinicalInstitutionListByInfoMap(map);
    }

    @Override
    public ArrayList<Map<String, Object>> selectClinicalInstitutionInfoByMap(Map<String,Object> map) {
        return clinicalTrialFacilityMapper.selectClinicalInstitutionInfoByMap(map);
    }

    @Override
    public int selectClinicalInstitutionInfoCountByMap(Map<String, Object> map) {
        return clinicalTrialFacilityMapper.selectClinicalInstitutionInfoCountByMap(map);
    }

    @Override
    public ArrayList<Map<String, Object>> selectClinicalInstitutionInfoByPid(String pid) {
        return clinicalTrialFacilityMapper.selectClinicalInstitutionInfoByPid(pid);
    }

    @Override
    public ArrayList<Map<String, Object>> selectClinicalInstitutionListGroupByProvinceByName(String name) {
        return clinicalTrialFacilityMapper.selectClinicalInstitutionListGroupByProvinceByName(name);
    }

    @Override
    public ArrayList<Map<String,Object>> selectInstitutionEthicalInfoByName(String name) {
        return clinicalTrialFacilityMapper.selectInstitutionEthicalInfoByName(name);
    }
}
