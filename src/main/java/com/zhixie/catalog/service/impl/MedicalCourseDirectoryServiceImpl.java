package com.zhixie.catalog.service.impl;

import com.zhixie.catalog.mapper.MedicalCourseDirectoryMapper;
import com.zhixie.catalog.service.MedicalCourseDirectoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Map;

@Service
public class MedicalCourseDirectoryServiceImpl implements MedicalCourseDirectoryService{

    @Autowired
    private MedicalCourseDirectoryMapper medicalCourseDirectoryMapper;

    @Override
    public ArrayList<Map<String, Object>> selectTreatDirectoryByPid(int pid) {
        return medicalCourseDirectoryMapper.selectTreatDirectoryByPid(pid);
    }

    @Override
    public Map<String, Object> selectTreatDirectoryById(int id) {
        return medicalCourseDirectoryMapper.selectTreatDirectoryById(id);
    }

}
