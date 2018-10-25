package com.zhixie.catalog.service.impl;

import com.zhixie.catalog.mapper.ExhibitionMapper;
import com.zhixie.catalog.service.ExhibitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

@Service
public class ExhibitionServiceImpl implements ExhibitionService{
    @Autowired
    private ExhibitionMapper exhibitionMapper;
    @Override
    public ArrayList<Map<String, Object>> selectExhibition(Map<String,Object> map) {
        return exhibitionMapper.selectExhibition(map);
    }

    @Override
    public int selectCountByName(Map<String, Object> map) {
        return exhibitionMapper.selectCountByName(map);
    }
}
