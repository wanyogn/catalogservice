package com.zhixie.catalog.controller;

import com.google.gson.GsonBuilder;
import com.zhixie.catalog.service.MedicalCourseDirectoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 诊疗科目名录控制器
 */

@RestController
public class MedicalCourseDirectoryController {

    @Autowired
    private MedicalCourseDirectoryService medicalCourseDirectoryService;

    /**
     * 根据pid查询医疗机构诊疗科目录
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/catalog/queryWxTreatDirectoryByPid")
    public String queryWxTreatDirectory(HttpServletRequest request, HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin", "*");

        String id_string = request.getParameter("id");
        Map<String,Object> map = new HashMap<>();

        if(id_string == null) id_string = "0";

        int id = Integer.valueOf(id_string);
        if(id != 0) {
            Map<String, Object> parentTemp = medicalCourseDirectoryService.selectTreatDirectoryById(id);
            map.put("parent", parentTemp);
        }

        ArrayList<Map<String,Object>> list = medicalCourseDirectoryService.selectTreatDirectoryByPid(id);
        map.put("list",list);

        return new GsonBuilder().create().toJson(map);
    }

}
