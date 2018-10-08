package com.zhixie.catalog.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import com.zhixie.catalog.elasticsearch.Constant;
import com.zhixie.catalog.helper.*;
import com.zhixie.catalog.model.Catalog;
import com.zhixie.catalog.model.ExemptionDir;
import com.zhixie.catalog.model.common.IpGeograAddress;
import com.zhixie.catalog.model.es.ESCount;
import com.zhixie.catalog.model.es.ESResultRoot;
import com.zhixie.catalog.model.es.Hit;
import com.zhixie.catalog.model.es.SourceSet;
import com.zhixie.catalog.service.CatalogService;
import com.zhixie.catalog.service.PcSearchService;
import jdk.nashorn.internal.parser.JSONParser;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class CatalogController {

    @Autowired
    private CatalogService catalogService;
    @Autowired
    private PcSearchService pcSearchService;
    @RequestMapping("/upload")
    public ModelAndView upload() {
        ModelAndView modelAndView = new ModelAndView("upload");
        return modelAndView;
    }/*
    @RequestMapping("/search")
    public ModelAndView search() {
        ModelAndView modelAndView = new ModelAndView("search");

        return modelAndView;
    }*/
    @RequestMapping("/textupload")
    public ModelAndView search() {
        ModelAndView modelAndView = new ModelAndView("textupload");

        return modelAndView;
    }

    /**
     * 查看所有的目录编号
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/catalog/queryDirectory")
    public String queryDirectory(HttpServletRequest request, HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin", "*");
        List<Object> list = new ArrayList<>();
        try{
            String val = HttpHandler.httpPostCall("http://localhost:9200/catalogs/catalog/_search", Constant.queryDirectoryText);
            ESResultRoot retObj = new GsonBuilder().create().fromJson(val, ESResultRoot.class);
            for (Hit hit:retObj.hits.hits){
                list.add(hit._source);
            }
        }catch(Exception e){

        }
        return new GsonBuilder().create().toJson(list);
    }

    /**
     * 查找所有的一级菜单
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/catalog/queryFirst_Product")
    public String queryFirst_Product(HttpServletRequest request, HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin", "*");

        JSONArray res = new JSONArray();
        List<Map<String,Object>> catalogs = catalogService.selectDirectory(0);
        for (int i = 0;i < catalogs.size();i++){
            //System.out.println(catalogs.get(i).get("code")+catalogs.get(i).get("name"));
            JSONObject json = new JSONObject();
            json.put("number",catalogs.get(i).get("code"));
            json.put("name",catalogs.get(i).get("name"));
            json.put("content",catalogs.get(i).get("content"));
            List<Map<String,Object>> catas = catalogService.selectDirectory((Integer) catalogs.get(i).get("id"));
            List<Object> list = new ArrayList<>();
            for(int j = 0;j < catas.size();j++){
               list.add(catas.get(j));
            }
            json.put("second",list);
            res.add(json);
        }
        return res.toString();
    }
    /**
     * 查找二级的产品类别及详细信息
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/catalog/queryProduct")
    public String queryProduct(HttpServletRequest request, HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin", "*");
        String dir_num = request.getParameter("dir_num");
        String first_num = request.getParameter("first_num");
        if(dir_num == null || first_num == null){return "error!";}
        SourceSet sourceSet = new SourceSet();
        List<Object> list = new ArrayList<>();
        try{
            String val = HttpHandler.httpPostCall("http://localhost:9200/catalogs/catalog/_search", Constant.querySecond_dir(dir_num,first_num));
            ESResultRoot retObj = new GsonBuilder().create().fromJson(val, ESResultRoot.class);
            for (Hit hit:retObj.hits.hits){
                list.add(hit._source);
            }
        }catch(Exception e){

        }
       sourceSet.setDatas(scanIndexResult(list));

        return new GsonBuilder().create().toJson(sourceSet);
    }

    /**
     * 点击二级菜单查看详情
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/catalog/queryProductDetail")
    public String searchDetailByNumber(HttpServletRequest request, HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin", "*");
        String dir_num = request.getParameter("dir_num");
        String first_num = request.getParameter("first_num");
        String second_num = request.getParameter("second_num");
        String esRequest = StaticVariable.esRequest;
        String condition = "";
        SourceSet sourceSet = new SourceSet();
        condition = "directory_number:\\\\\""+dir_num+"\\\\\" AND first_product_number:\\\\\""+first_num+"\\\\\" " +
                "AND second_product_number:\\\\\""+second_num+"\\\\\"";
        esRequest = esRequest.replaceFirst("#query",condition);
        esRequest = esRequest.replaceFirst("#phrase_slop",String.valueOf(5));
        List<Object> list = new ArrayList<>();
        try {
            String val = HttpHandler.httpPostCall("http://localhost:9200/catalogs/catalog/_search", esRequest);
            ESResultRoot retObj = new GsonBuilder().create().fromJson(val, ESResultRoot.class);
            for (Hit hit:retObj.hits.hits){
                sourceSet.add(hit._source);
            }
        }catch (Exception e){

        }
        //sourceSet.setDatas(scanResult(list));
        return new GsonBuilder().create().toJson(sourceSet);
    }
    /**
     * 每个品名举例后面的数字，调用医械查接口
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping("/catalog/search_product_count")
    public String searchProductCountByName(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        String keyword = request.getParameter("keyword");
        if(keyword.equals("")) return "fail";
        String condition = "";
        SourceSet productSet = new SourceSet();
        if(keyword.indexOf(" ")>0 && !keyword.endsWith(" ")){
            String lkeyword = keyword.split(" ")[0];
            String rkeyword = keyword.split(" ")[1];
            condition = "(product_name_ch:(\\\\\""+lkeyword+"\\\\\" OR \\\\\""+rkeyword+"\\\\\")) AND (maker_name_ch:(\\\\\""+lkeyword+"\\\\\" OR \\\\\""+rkeyword+"\\\\\"))";
        }else{
            condition = "(product_name_ch:\\\\\""+keyword+"\\\\\") OR (product_mode:\\\\\""+keyword+"\\\\\")";
        }
        String esCount = StaticVariable.esCount;
        esCount = esCount.replaceFirst("#query",condition);
        esCount = esCount.replaceFirst("\"#filter\"","");
        String countRet = HttpHandler.httpPostCall("http://localhost:9200/product/_count", esCount);
        ESCount esCt = new GsonBuilder().create().fromJson(countRet, ESCount.class);
        productSet.setMatchCount(esCt.count);
        return new GsonBuilder().create().toJson(productSet);
    }
    /**
     * 调用医械查接口，根据产品名称查找产品信息
     * @param
     * @return
     * @throws IOException
     */
    @RequestMapping("/catalog/search_product_name")
    public String searchProductByName(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");

        String num = request.getParameter("num");
        String keyword = request.getParameter("keyword");
        if(num == null || keyword == null) return "fail";
        if(num.equals("") || keyword.equals("")) return "fail";

        String size = request.getParameter("size");
        String condition = "";
        String esRequest = StaticVariable.esYXC;
        SourceSet productSet = new SourceSet();
        if(keyword.indexOf(" ")>0 && !keyword.endsWith(" ")){
            String lkeyword = keyword.split(" ")[0];
            String rkeyword = keyword.split(" ")[1];
            condition = "(product_name_ch:(\\\\\""+lkeyword+"\\\\\" OR \\\\\""+rkeyword+"\\\\\")) AND (maker_name_ch:(\\\\\""+lkeyword+"\\\\\" OR \\\\\""+rkeyword+"\\\\\"))";
        }else{
            condition = "(product_name_ch:\\\\\""+keyword+"\\\\\") OR (product_mode:\\\\\""+keyword+"\\\\\")";
        }
        int from = Integer.valueOf(num);
        if(size != null) { from = from * Integer.valueOf(size); }
        else{ from = from * 10; }

        esRequest = esRequest.replaceFirst("\"#from\"",String.valueOf(from));
        if(size == null){ esRequest = esRequest.replaceFirst("\"#size\"","10"); }
        else{ esRequest = esRequest.replaceFirst("\"#size\"",size); }
        esRequest = esRequest.replaceFirst("\"#includes\"","\"product_name_ch\"");
        String postbody = esRequest.replaceFirst("#query",condition);
        String ret = HttpHandler.httpPostCall("http://localhost:9200/product/_search", postbody);
        ESResultRoot retObj = new GsonBuilder().create().fromJson(ret, ESResultRoot.class);
        for(Hit hit:retObj.hits.hits){
            productSet.add(hit._source);
        }
        String esCount = StaticVariable.esCount;
        esCount = esCount.replaceFirst("#query",condition);
        esCount = esCount.replaceFirst("\"#filter\"","");
        String countRet = HttpHandler.httpPostCall("http://localhost:9200/product/_count", esCount);
        ESCount esCt = new GsonBuilder().create().fromJson(countRet, ESCount.class);
        productSet.setMatchCount(esCt.count);
        return new GsonBuilder().create().toJson(productSet);
    }
    @RequestMapping("/catalog/searchProductByKeyword")
    public String searchProductByKeyword(HttpServletRequest request, HttpServletResponse response){
        long startTime=System.currentTimeMillis();
        response.setHeader("Access-Control-Allow-Origin", "*");
        String keyword = request.getParameter("keyword");
        String gllb = request.getParameter("gllb");
        String yxzh = request.getParameter("yxzh");
        String mlsx = request.getParameter("mlsx");
        String esRequest = StaticVariable.esRequest;
        String condition = "";
        SourceSet sourceSet = new SourceSet();
        if(keyword.indexOf(" ") > 0 && !keyword.endsWith(" ")){
            String[] arr = keyword.trim().split(" ");
            condition = "(second_product_name:\\\\\""+arr[0]+"\\\\\" OR product_description:\\\\\""+arr[0]+"\\\\\" " +
                    "OR expected_use:\\\\\""+arr[0]+"\\\\\" OR product_example:\\\\\""+arr[0]+"\\\\\")";
            for(int i = 1;i < arr.length;i++){
                if(!arr[i].equals("")){
                    condition += " AND (second_product_name:\\\\\""+arr[i]+"\\\\\" OR product_description:\\\\\""+arr[i]+"\\\\\" " +
                            "OR expected_use:\\\\\""+arr[i]+"\\\\\" OR product_example:\\\\\""+arr[i]+"\\\\\")";
                }
            }
            esRequest = esRequest.replaceFirst("#phrase_slop",String.valueOf(0));
        }else{
            condition = "(second_product_name:\\\\\""+keyword+"\\\\\" OR product_description:\\\\\""+keyword+"\\\\\" " +
                    "OR expected_use:\\\\\""+keyword+"\\\\\" OR product_example:\\\\\""+keyword+"\\\\\")";
            esRequest = esRequest.replaceFirst("#phrase_slop",String.valueOf(5));
        }
        if(gllb!=null && gllb != ""){
            condition += " AND (management_category:\\\\\""+gllb+"\\\\\")";
        }
        if(yxzh!=null && yxzh != ""){
            condition += " AND (composite_product:\\\\\""+yxzh+"\\\\\")";
        }
        if(mlsx!=null && mlsx != ""){
            condition +=" AND (catalog_property:\\\\\""+mlsx+"\\\\\")";
        }

        esRequest = esRequest.replaceFirst("#query",condition);
        List<Object> list = new ArrayList<>();
        try {
            String val = HttpHandler.httpPostCall("http://localhost:9200/catalogs/catalog/_search", esRequest);
            ESResultRoot retObj = new GsonBuilder().create().fromJson(val, ESResultRoot.class);
            sourceSet.setMatchCount(retObj.hits.total);
            for (Hit hit:retObj.hits.hits){
                list.add(hit._source);
            }
        }catch (Exception e){

        }

        long start=System.currentTimeMillis(); //获取结束时间
        sourceSet.setDatas(scanResult(list));
       // sourceSet.setDatas(list);
        long endTime=System.currentTimeMillis(); //获取结束时间
        System.out.println("scanResult程序运行时间： "+(endTime-start)+"ms");
        System.out.println("程序运行时间： "+(endTime-startTime)+"ms");
        return new GsonBuilder().create().toJson(sourceSet);
    }
    @RequestMapping("catalog/searchProductByNum")
    public String searchProductByNumber(HttpServletRequest request, HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin", "*");
        String number = request.getParameter("number");
        if(number.trim().length() != 6){return "error!";}
        String directory_number = number.substring(0,2);
        String first_number = number.substring(2,4);
        String second_number = number.substring(4,6);
        String esRequest = StaticVariable.esRequest;
        String condition = "";
        SourceSet sourceSet = new SourceSet();
        condition = "directory_number:\\\\\""+directory_number+"\\\\\" AND first_product_number:\\\\\""+first_number+"\\\\\" " +
                "AND second_product_number:\\\\\""+second_number+"\\\\\"";
        esRequest = esRequest.replaceFirst("#query",condition);
        esRequest = esRequest.replaceFirst("#phrase_slop",String.valueOf(5));
        List<Object> list = new ArrayList<>();
        try {
            String val = HttpHandler.httpPostCall("http://localhost:9200/catalogs/catalog/_search", esRequest);
            ESResultRoot retObj = new GsonBuilder().create().fromJson(val, ESResultRoot.class);
            sourceSet.setMatchCount(retObj.hits.total);
            for (Hit hit:retObj.hits.hits){
                list.add(hit._source);
            }
        }catch (Exception e){

        }
        sourceSet.setDatas(scanResult(list));
        return new GsonBuilder().create().toJson(sourceSet);
    }
    /**
     * 上传数据
     * @param file
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/upload/catalog", method = RequestMethod.POST)
    @ResponseBody
    public String upload(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/html; charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        ReadExcel excel = new ReadExcel();
        List<ExemptionDir> catalogs = excel.getExcelInfo(file);

        try{
            for (int i = 0;i <catalogs.size();i++) {
                ExemptionDir catalog = catalogs.get(i);
                Map<String, Object> json = new HashMap<>();
                json.put("id",getId());
                json.put("category",catalog.getCategory());
                json.put("classify_code",catalog.getClassify_code());
                json.put("product_name",catalog.getProduct_name());
                json.put("product_description",catalog.getProduct_description());
                json.put("management_category",catalog.getManagement_category());
                json.put("remark",catalog.getRemark());

                IndexRequest indexRequest = new IndexRequest("exemption_directory","directory").source(json);
                IndexResponse response1 = ESClientFactory.getClient().index(indexRequest);
            }
        }catch(Exception e){

        }
    return "success";
    }
    @RequestMapping(value = "/catalog/introductionUpload", method = RequestMethod.POST)
    @ResponseBody
    public String uploadIntroducton(HttpServletRequest request, HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin", "*");
        String selected = request.getParameter("selected");
        String content = request.getParameter("content");
        List<String> list = new ArrayList<>();
        try{
            String val = HttpHandler.httpPostCall("http://localhost:9200/catalogs/catalog/_search", Constant.queryDirByNum(selected));
            ESResultRoot retObj = new GsonBuilder().create().fromJson(val, ESResultRoot.class);
            for (Hit hit:retObj.hits.hits){
                list.add(hit._id);
            }
            Map<String,String> map = new HashMap<>();
            map.put("introduction",content);
            for(int i = 0;i <list.size();i++){
                UpdateRequest updateRequest = new UpdateRequest("catalogs", "catalog", list.get(i))
                        .doc(map);
                ESClientFactory.getClient().update(updateRequest);
            }

        }catch(Exception e){

        }
return "success";
    }
    //用于搜索筛选分组
    private  List<Object> scanResult(List list){
        List<Integer> indexList = new ArrayList<>();
        for (int i = 0;i < list.size();i++){
            if(i > 0){
                String str1 = JSONObject.fromObject(list.get(i)).get("directory_number").toString()+JSONObject.fromObject(list.get(i)).get("first_product_number").toString()
                        +JSONObject.fromObject(list.get(i)).get("second_product_number").toString();
                String str2 = JSONObject.fromObject(list.get(i-1)).get("directory_number").toString()+JSONObject.fromObject(list.get(i-1)).get("first_product_number").toString()
                        +JSONObject.fromObject(list.get(i-1)).get("second_product_number").toString();
                if(str1.equals(str2)){

                }else{
                    indexList.add(i);
                }
            }else{
                indexList.add(i);
            }
        }
        indexList.add(list.size());
        List<Object> ll = new ArrayList<>();
        for(int i = 0;i <indexList.size()-1;i++){
            JSONObject json = new JSONObject();
            List<Object> list2 = new ArrayList<>();
            for(int j = indexList.get(i);j < indexList.get(i+1);j++){
                if(j == indexList.get(i)){
                    JSONObject jsonObject = JSONObject.fromObject(list.get(j));
                    //json.put("number",jsonObject.get("directory_number").toString()+jsonObject.get("first_product_number")+jsonObject.get("second_product_number"));
                    json.put("dir_num",jsonObject.get("directory_number"));
                    json.put("name",jsonObject.get("second_product_name"));
                    json.put("first_number",jsonObject.get("first_product_number"));
                    json.put("first_name",jsonObject.get("directory_name"));
                    json.put("second_number",jsonObject.get("second_product_number"));
                    json.put("second_name",jsonObject.get("first_product_name"));

                }
                list2.add(list.get(j));
                json.put("data",list2);
            }
            ll.add(json);
        }
        return ll;
    }
    /*用于首页筛选*/
    private  List<Object> scanIndexResult(List list){
        List<Integer> indexList = new ArrayList<>();
        for (int i = 0;i < list.size();i++){
            if(i > 0){
                JSONObject json1 = JSONObject.fromObject(list.get(i));
                JSONObject json2 = JSONObject.fromObject(list.get(i-1));
                if(json1.get("second_product_number").toString().equals(json2.get("second_product_number").toString())){

                }else{
                    indexList.add(i);
                }
            }else{
                indexList.add(i);
            }
        }
        indexList.add(list.size());
        List<Object> ll = new ArrayList<>();
        for(int i = 0;i <indexList.size()-1;i++){
            JSONObject json = new JSONObject();
            for(int j = indexList.get(i);j < indexList.get(i+1);j++){
                if(j == indexList.get(i)){
                    JSONObject jsonObject = JSONObject.fromObject(list.get(j));
                    //json.put("number",jsonObject.get("directory_number").toString()+jsonObject.get("first_product_number")+jsonObject.get("second_product_number"));
                    json.put("dir_num",jsonObject.get("directory_number"));
                    json.put("first_num",jsonObject.get("first_product_number"));
                    json.put("second_num",jsonObject.get("second_product_number"));
                    json.put("name",jsonObject.get("second_product_name"));
                    json.put("first_name",jsonObject.get("directory_name"));
                    json.put("second_name",jsonObject.get("first_product_name"));
                    json.put("catalog_property",jsonObject.get("catalog_property"));
                }
            }
            ll.add(json);
        }
        return ll;
    }

    /**
     * 添加记录
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("catalog/insertSearchInfo")
    public int insertUserSearchInfo(HttpServletRequest request,HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin", "*");
        String keyword = request.getParameter("keyword");
        String searchtype = request.getParameter("searchtype");
        if(keyword == "" || searchtype == ""){return -1;}
        String ip = CusAccessObjectUtil.getIpAddress(request);
        String json_result = "";
        try {
            json_result = CusAccessObjectUtil.getAddresses("ip=" + ip, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        IpGeograAddress ipGeograAddress = new GsonBuilder().create().fromJson(json_result, IpGeograAddress.class);
        String region = String.valueOf(ipGeograAddress.data.get("region"));
        String city = String.valueOf(ipGeograAddress.data.get("city"));
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = df.format(new Date());
        Map<String, Object> map = new HashMap<>();

        map.put("userip", ip);
        map.put("userregion", region);
        map.put("usercity", city);
        map.put("searchtype",searchtype);
        map.put("keyword", keyword);
        map.put("createdate",date);
        return pcSearchService.insertUserSearchInfo(map);
    }
    private String getId(){
        String id = "";
        //获取当前时间戳
        SimpleDateFormat sf = new SimpleDateFormat("MMddHHmmssSSS");
        String temp = sf.format(new Date());
        //获取4位随机数
        int random = (int)((Math.random()+1)*1000);
        id = temp + random;
        return id;
    }
}
