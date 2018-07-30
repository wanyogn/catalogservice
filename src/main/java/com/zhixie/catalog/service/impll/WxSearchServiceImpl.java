package com.zhixie.catalog.service.impll;

import com.zhixie.catalog.mapper.WxSearchMapper;
import com.zhixie.catalog.model.wechat.WxspUserInfo;
import com.zhixie.catalog.service.WxSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
@Service
public class WxSearchServiceImpl implements WxSearchService{

    @Autowired
    private WxSearchMapper wxSearchMapper;

    @Override
    public WxspUserInfo selectWxspUserByOpenid(String openid) {
        return wxSearchMapper.selectWxspUserByOpenid(openid);
    }

    @Override
    public WxspUserInfo selectWxspYXCUserByOpenid(String openid) {
        return wxSearchMapper.selectWxspYXCUserByOpenid(openid);
    }

    @Override
    public int insertWxSearchByMap(Map<String, Object> map) {
        return wxSearchMapper.insertWxSearchByMap(map);
    }

    @Override
    public int insertWxspUserByMap(Map<String, String> map) {
        return wxSearchMapper.insertWxspUserByMap(map);
    }
}
