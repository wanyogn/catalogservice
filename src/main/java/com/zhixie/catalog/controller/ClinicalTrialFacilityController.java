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
        String num_string = request.getParameter("num");
        String province = request.getParameter("province");
        System.out.println(province);
        String profession_name = request.getParameter("profession_name");

        Map<String,Object> temp = new HashMap<>();
        if(name == null) name = "";
        if(num_string == null) num_string = "0";
        int num = Integer.valueOf(num_string);
        if(province != null && province.equals("")) province = null;
        if(profession_name != null && profession_name.equals("")) profession_name = null;

        if(province != null) province = province.replace(",","|");
        if(profession_name != null) profession_name = "^"+profession_name.replace(",","|^");

        temp.put("name","%"+name+"%");
        temp.put("startnum",10*num);
        temp.put("province",province);
        temp.put("profession_name",profession_name);

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
        //long startTime=System.currentTimeMillis();
        response.setHeader("Access-Control-Allow-Origin", "*");

        String name = request.getParameter("name");
        int num = Integer.valueOf(request.getParameter("num"));
        String province = request.getParameter("province");
        ArrayList<Map<String,Object>> dataList = new ArrayList<>();
        Map<String,Object> map = new HashMap<>();

        if(province != null && province.equals("")) province = null;
        if(province != null) province = province.replace(",","|");

        Map<String,Object> temp = new HashMap<>();
        temp.put("name","%"+name+"%");
        temp.put("startnum",10*num);
        temp.put("province",province);

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
        //long endTime=System.currentTimeMillis();
        //System.out.println("程序运行时间： "+(endTime-startTime)+"ms");

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

    /**
     *  根据条件查询折叠起来的专业信息
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/catalog/selectClinicalInstitutionHiddenInfoByMap")
    public String selectClinicalInstitutionHiddenInfoByMap(HttpServletRequest request, HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin", "*");

        String pid = request.getParameter("pid");
        String profession_name = request.getParameter("profession_name");
        Map<String,Object> temp = new HashMap<>();
        temp.put("pid",pid);
        temp.put("profession_name",profession_name);

        ArrayList<Map<String,Object>> list = clinicalTrialFacilityService.selectClinicalInstitutionHiddenInfoByMap(temp);
        Map<String,Object> map = new HashMap<>();
        map.put("data",list);

        return new GsonBuilder().create().toJson(map);
    }

    /**
     *  聚合省份
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/catalog/selectClinicalInstitutionGroup")
    public String selectClinicalInstitutionGroup(HttpServletRequest request, HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin", "*");

        String name = request.getParameter("name");
        if(name == null) name = "%%";

        ArrayList<Map<String,Object>> list = clinicalTrialFacilityService.selectClinicalInstitutionListGroupByProvinceByName(name);
        Map<String,Object> map = new HashMap<>();
        map.put("data",list);

        return new GsonBuilder().create().toJson(map);
    }

    /**
     *  根据机构名称查找机构伦理信息
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/catalog/selectInstitutionEthicalInfoByName")
    public String selectInstitutionEthicalInfoByName(HttpServletRequest request, HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin", "*");

        String name = request.getParameter("name");

        ArrayList<Map<String,Object>> list = clinicalTrialFacilityService.selectInstitutionEthicalInfoByName(name);
        Map<String,Object> map = new HashMap<>();
        map.put("data",list);

        return new GsonBuilder().create().toJson(map);
    }
}
