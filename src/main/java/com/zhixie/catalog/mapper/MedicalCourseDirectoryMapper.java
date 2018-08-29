package com.zhixie.catalog.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.Map;

@Mapper
public interface MedicalCourseDirectoryMapper {

    ArrayList<Map<String,Object>> selectTreatDirectoryByPid(int pid);

    Map<String,Object> selectTreatDirectoryById(int id);
}
