package com.zhixie.catalog.controller;

import com.google.gson.GsonBuilder;
import com.zhixie.catalog.service.ExhibitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 医博会展商搜索Controller
 */
@RestController
public class ExhibitionController {

    @Autowired
    private ExhibitionService exhibitionService;
    /**
     * 根据关键词搜索展商
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/catalog/selectExhibitionByKeyword")
    public String selectExhibition(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setHeader("Access-Control-Allow-Origin", "*");
        String keyword = request.getParameter("keyword");
        String id_string = request.getParameter("id");//展馆id
        int num = Integer.valueOf(request.getParameter("num"));
        int size = Integer.parseInt(request.getParameter("size"));

        Map<String,Object> map = new HashMap<>();

        Map<String,Object> temp = new HashMap<>();
        if(id_string == null){
            temp.put("exhibitorname",keyword);
        }else if(keyword == null){
            temp.put("pavilionid",id_string);
        }
        temp.put("startnum",size*num);
        temp.put("size",size);

        ArrayList<Map<String,Object>> exhibitions = exhibitionService.selectExhibition(temp);
        map.put("data",exhibitions);

        if(num == 0){
            map.put("count",exhibitionService.selectCountByName(temp));
        }
        return new GsonBuilder().create().toJson(map);
    }
}
