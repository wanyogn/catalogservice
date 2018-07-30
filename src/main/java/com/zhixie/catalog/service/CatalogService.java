package com.zhixie.catalog.service;

import com.zhixie.catalog.model.Catalogs;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


public interface CatalogService {
    List<Map<String,Object>> selectDirectory(int pid);

    Catalogs selectDirByCode(int pid, String code);
}
