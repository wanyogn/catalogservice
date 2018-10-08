package com.zhixie.catalog.controller;

import com.google.gson.GsonBuilder;
import com.zhixie.catalog.helper.HttpHandler;
import com.zhixie.catalog.helper.StaticVariable;
import com.zhixie.catalog.model.es.ESCount;
import com.zhixie.catalog.model.es.ESResultRoot;
import com.zhixie.catalog.model.es.Hit;
import com.zhixie.catalog.model.es.SourceSet;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 豁免目录控制器
 */
@RestController
public class ExemptionDirController {
    /**
     * 根据关键词查找豁免目录
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("catalog/searchHMMLByKeyword")
    public String searchProductByNumber(HttpServletRequest request, HttpServletResponse response) throws Exception{
        response.setHeader("Access-Control-Allow-Origin", "*");
        String keyword = request.getParameter("keyword");
        String num = request.getParameter("num");
        if(num == null || keyword == null) return "fail";
        if(num.equals("") || keyword.equals("")) return "fail";

        String size = request.getParameter("size");

        String esRequest = StaticVariable.esYXC;
        SourceSet productSet = new SourceSet();

        int from = Integer.valueOf(num);
        if(size != null) { from = from * Integer.valueOf(size); }
        else{ from = from * 10; }
        String condition = "(classify_code:\\\\\""+keyword+"\\\\\") OR (product_name:\\\\\""+keyword+"\\\\\") OR (product_description:\\\\\""+keyword+"\\\\\")";
        esRequest = esRequest.replaceFirst("\"#from\"",String.valueOf(from));

        if(size == null){ esRequest = esRequest.replaceFirst("\"#size\"","10"); }
        else{ esRequest = esRequest.replaceFirst("\"#size\"",size); }

        esRequest = esRequest.replaceFirst("\"#includes\"","\"id\",\"category\",\"classify_code\",\"product_name\",\"product_description\",\"management_category\",\"remark\"");
        String postbody = esRequest.replaceFirst("#query",condition);
        String ret = HttpHandler.httpPostCall("http://localhost:9200/exemption_directory/directory/_search", postbody);
        ESResultRoot retObj = new GsonBuilder().create().fromJson(ret, ESResultRoot.class);
        for(Hit hit:retObj.hits.hits){
            productSet.add(hit._source);
        }
        if(from == 0) {
            //计数
            String esCount = StaticVariable.esCount;
            esCount = esCount.replaceFirst("#query", condition);
            esCount = esCount.replaceFirst("\"#filter\"", "");
            String countRet = HttpHandler.httpPostCall("http://localhost:9200/exemption_directory/directory/_count", esCount);
            ESCount esCt = new GsonBuilder().create().fromJson(countRet, ESCount.class);
            productSet.setMatchCount(esCt.count);
        }
        return new GsonBuilder().create().toJson(productSet);
    }

}
