package com.zhixie.catalog.service;

import java.util.ArrayList;
import java.util.Map;

public interface MedicalCourseDirectoryService {

    //根据pid查询诊疗科目名录数据
    ArrayList<Map<String,Object>> selectTreatDirectoryByPid(int pid);

    //根据id查询诊疗科目名录
    Map<String,Object> selectTreatDirectoryById(int id);

}
