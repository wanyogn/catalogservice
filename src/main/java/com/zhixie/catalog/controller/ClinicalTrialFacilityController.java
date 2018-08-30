package com.zhixie.catalog.controller;

import com.google.gson.GsonBuilder;
import com.zhixie.catalog.service.ClinicalTrialFacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 临床试验机构备案信息控制器
 */
@RestController
public class ClinicalTrialFacilityController {

    @Autowired
    private ClinicalTrialFacilityService clinicalTrialFacilityService;

    /**
     *  根据条件查询临床试验机构备案列表信息
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/catalog/selectClinicalInstitutionListByMap")
    public String selectClinicalInstitutionListByMap(HttpServletRequest request, HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin", "*");

        String name = request.getParameter("name");
        String province = request.getParameter("province");
        int num = Integer.valueOf(request.getParameter("num"));

        Map<String,Object> temp = new HashMap<>();
        temp.put("name","%"+name+"%");
        temp.put("province",province);
        temp.put("startnum",10*num);

        ArrayList<Map<String,Object>> list = clinicalTrialFacilityService.selectClinicalInstitutionListByMap(temp);
        Map<String,Object> map = new HashMap<>();
        map.put("data",list);
        if(num == 0) map.put("count",clinicalTrialFacilityService.selectClinicalInstitutionListCountByMap(temp));

        return new GsonBuilder().create().toJson(map);
    }

    /**
     *  根据id查询临床试验机构备案列表信息
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/catalog/selectClinicalInstitutionListById")
    public String selectClinicalInstitutionListById(HttpServletRequest request, HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin", "*");

        String id = request.getParameter("id");
        Map<String,Object> map = new HashMap<>();

        Map<String,Object> data = clinicalTrialFacilityService.selectClinicalInstitutionListById(id);
        map.put("data",data);

        return new GsonBuilder().create().toJson(map);
    }

    /**
     *  根据专业条件查询临床试验机构备案列表信息
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/catalog/selectClinicalInstitutionListByInfoMap")
    public String selectClinicalInstitutionListByInfoMap(HttpServletRequest request, HttpServletResponse response){
        long startTime=System.currentTimeMillis();
        response.setHeader("Access-Control-Allow-Origin", "*");

        String name = request.getParameter("name");
        int num = Integer.valueOf(request.getParameter("num"));
        ArrayList<Map<String,Object>> dataList = new ArrayList<>();
        Map<String,Object> map = new HashMap<>();

        Map<String,Object> temp = new HashMap<>();
        temp.put("name","%"+name+"%");
        temp.put("startnum",10*num);

        if(num == 0) map.put("count",clinicalTrialFacilityService.selectClinicalInstitutionInfoCountByMap(temp));

        ArrayList<Map<String,Object>> list = clinicalTrialFacilityService.selectClinicalInstitutionListByInfoMap(temp);
        for(Map<String,Object> t : list){
            String pid = (String) t.get("pid");
            Map<String,Object> data = clinicalTrialFacilityService.selectClinicalInstitutionListById(pid);
            temp.put("pid",pid);
            ArrayList<Map<String,Object>> secondList = clinicalTrialFacilityService.selectClinicalInstitutionInfoByMap(temp);
            data.put("secondList",secondList);
            dataList.add(data);
        }
        map.put("data",dataList);
        long endTime=System.currentTimeMillis();
        System.out.println("程序运行时间： "+(endTime-startTime)+"ms");

        return new GsonBuilder().create().toJson(map);
    }

    /**
     *  根据pid查询临床试验机构备案专业信息
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/catalog/selectClinicalInstitutionInfoByPid")
    public String selectClinicalInstitutionInfoByPid(HttpServletRequest request, HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin", "*");

        String pid = request.getParameter("pid");
        Map<String,Object> temp = new HashMap<>();
        temp.put("pid",pid);

        ArrayList<Map<String,Object>> list = clinicalTrialFacilityService.selectClinicalInstitutionInfoByPid(pid);
        Map<String,Object> map = new HashMap<>();
        map.put("data",list);
        map.put("count",clinicalTrialFacilityService.selectClinicalInstitutionInfoCountByMap(temp));

        return new GsonBuilder().create().toJson(map);
    }
}
