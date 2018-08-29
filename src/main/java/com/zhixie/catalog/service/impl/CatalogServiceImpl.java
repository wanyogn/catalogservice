package com.zhixie.catalog.service.impl;

import com.zhixie.catalog.mapper.CatalogMapper;
import com.zhixie.catalog.model.Catalogs;
import com.zhixie.catalog.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CatalogServiceImpl implements CatalogService {

    @Autowired
    private CatalogMapper catalogMapper;
    @Override
    public List<Map<String, Object>> selectDirectory(int pid) {
        return catalogMapper.selectDirectory(pid);
    }

    @Override
    public Catalogs selectDirByCode(int pid, String code) {
        Map<String,Object> map = new HashMap<>();
        map.put("pid",pid);
        map.put("code",code);
        return catalogMapper.selectDirByCode(map);
    }
}
