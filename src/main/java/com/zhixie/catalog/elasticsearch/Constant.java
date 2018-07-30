package com.zhixie.catalog.elasticsearch;


import com.zhixie.catalog.helper.ESClientFactory;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

public class Constant {
    //查找所有的目录
    public static String queryDirectoryText = "{\"size\":23,\"collapse\":{\"field\":\"directory_number\"}" +
            ",\"sort\":[{\"directory_number\":{\"order\":\"asc\"}}],\"_source\":[\"directory_number\",\"directory_name\",\"introduction\"]}";

    public static String queryFirst_dir(String num){
        String str = "{\"size\":23,\"collapse\":{\"field\":\"first_product_number\"},\"sort\":[{\"first_product_number\":{\"order\":\"asc\"}}]" +
                ",\"query\":{\"bool\":{\"must\":[{\"match\":{\"directory_number\":\""+num+"\"}}]}},\"_source\":[\"first_product_number\",\"first_product_name\"]}";
        return str;
    }
    /*通过NUM找所有的num编号下的数据，上传简介专用*/
    public static String queryDirByNum(String num){
        String str = "{\"size\":100,\"query\":{\"match\":{\"directory_number\":\""+num+"\"}}}";
        return str;
    }

    public static String querySecond_dir(String dir_num,String sec_num){
        String str = "{\"size\":120,\"sort\":[{\"second_product_number\":{\"order\":\"asc\"}}]" +
                ",\"query\":{\"bool\":{\"must\":[{\"match\":{\"directory_number\":\""+dir_num+"\"}}" +
                ",{\"match\":{\"first_product_number\":\""+sec_num+"\"}}]}}}";
        return str;
    }
}
