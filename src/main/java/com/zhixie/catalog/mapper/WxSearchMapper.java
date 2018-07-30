package com.zhixie.catalog.mapper;

import com.zhixie.catalog.model.wechat.WxspUserInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface WxSearchMapper {
    WxspUserInfo selectWxspUserByOpenid(String openid);

    WxspUserInfo selectWxspYXCUserByOpenid(String openid);
    int insertWxspUserByMap(Map<String, String> map);//插入用户信息
    int insertWxSearchByMap(Map<String,Object> map);//插入用户搜索记录
}
