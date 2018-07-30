package com.zhixie.catalog.service;

import com.zhixie.catalog.model.wechat.WxspUserInfo;
import org.springframework.stereotype.Service;

import java.util.Map;

public interface WxSearchService {
    WxspUserInfo selectWxspUserByOpenid(String openid);
    WxspUserInfo selectWxspYXCUserByOpenid(String openid);
    int insertWxSearchByMap(Map<String,Object> map);
    int insertWxspUserByMap(Map<String, String> map);//插入用户信息
}
