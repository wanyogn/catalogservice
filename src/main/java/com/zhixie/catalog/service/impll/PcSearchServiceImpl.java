package com.zhixie.catalog.service.impll;

import com.zhixie.catalog.mapper.PcSearchMapper;
import com.zhixie.catalog.service.PcSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
@Service
public class PcSearchServiceImpl implements PcSearchService {
    @Autowired
    private PcSearchMapper pcSearchMapper;
    @Override
    public int insertUserSearchInfo(Map<String, Object> map) {
        return pcSearchMapper.insertUserSearchInfo(map);
    }
}
