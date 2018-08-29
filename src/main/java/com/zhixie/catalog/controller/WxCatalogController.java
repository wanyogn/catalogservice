package com.zhixie.catalog.controller;
import com.google.gson.GsonBuilder;
import com.zhixie.catalog.model.Catalogs;
import com.zhixie.catalog.model.wechat.WxspUserInfo;
import com.zhixie.catalog.service.CatalogService;
import com.zhixie.catalog.service.WxSearchService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class WxCatalogController {

    @Autowired
    private CatalogService catalogService;
    @Autowired
    private WxSearchService wxSearchService;

    /**
     * 查看分类目录所有的一级编号
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/catalog/queryWxDirectoryById")
    public String queryDirectory(HttpServletRequest request, HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin", "*");
        String code = request.getParameter("code");
        if(code == null){return "error!";}
        Catalogs catalog = catalogService.selectDirByCode(0,code);
        if(null == catalog){return "没有记录！";}
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code",catalog.getCode());
        jsonObject.put("name",catalog.getName());
        List<Map<String,Object>> catalogs = catalogService.selectDirectory(catalog.getId());
        jsonObject.put("second",catalogs);
        return JSONObject.fromObject(jsonObject).toString();
    }
    /*
        微信小程序--新增查询信息
     */
    @RequestMapping("/catalog/wxsp_insert_usersearch")
    public void wxspInsertUserSearch(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        String openid = request.getParameter("openid");
        String keyword = request.getParameter("keyword");
        String tag = request.getParameter("tag");
        WxspUserInfo user = null;
        if(tag.equals("FLML")){
            user = wxSearchService.selectWxspUserByOpenid(openid);
        }else if(tag.equals("YXC")){
            user = wxSearchService.selectWxspYXCUserByOpenid(openid);
        }

        if(user != null){
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = df.format(new Date());
            Map<String,Object> map = new HashMap<>();
            map.put("userid",user.getId());
            map.put("keyword",keyword);
            map.put("createdate",date);
            map.put("tag",tag);
            wxSearchService.insertWxSearchByMap(map);
        }

    }
}
