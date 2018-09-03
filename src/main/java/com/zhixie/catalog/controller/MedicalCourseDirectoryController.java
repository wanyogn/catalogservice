package com.zhixie.catalog.controller;

import com.google.gson.GsonBuilder;
import com.zhixie.catalog.service.ClinicalTrialFacilityService;
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

    @Autowired
    private ClinicalTrialFacilityService clinicalTrialFacilityService;

    /**
     * 根据pid查询医疗机构诊疗科目录
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/catalog/queryWxTreatDirectoryByPid")
    public String queryWxTreatDirectory(HttpServletRequest request, HttpServletResponse response){
        //long startTime=System.currentTimeMillis();
        response.setHeader("Access-Control-Allow-Origin", "*");

        String id_string = request.getParameter("id");
        Map<String,Object> map = new HashMap<>();

        if(id_string == null) id_string = "0";
        int id = Integer.valueOf(id_string);

        ArrayList<Map<String,Object>> list = medicalCourseDirectoryService.selectTreatDirectoryByPid(id);
        if(id == 0){
            int num = 0;
            Map<String,Object> tmap = new HashMap<>();
            for(Map<String,Object> t : list){
                String name = (String) t.get("name");
                tmap.put("profession_name","^"+name);
                num = clinicalTrialFacilityService.selectClinicalInstitutionListCountByMap(tmap);
                t.put("institution_num",num);
                num = clinicalTrialFacilityService.selectClinicalProfessionCountByMap(tmap);
                t.put("profession_num",num);
            }
        }
        map.put("list",list);
        //long endTime=System.currentTimeMillis();
        //System.out.println("程序运行时间： "+(endTime-startTime)+"ms");

        return new GsonBuilder().create().toJson(map);
    }

}
