package com.zhixie.catalog.model.es;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/7/20.
 */
public class SourceSet {
    private int matchCount = 0;
    private List<Object> datas = new ArrayList<>();
    private List<Object> aggList = new ArrayList<>();

    public void add(Object prd){
        datas.add(prd);
    }

    public int getMatchCount() {
        return matchCount;
    }

    public void setMatchCount(int matchCount) {
        this.matchCount = matchCount;
    }

    public List<Object> getDatas() {
        return datas;
    }

    public void setDatas(List<Object> datas) {
        this.datas = datas;
    }

    public List<Object> getAggList() {
        return aggList;
    }

    public void setAggList(List<Object> aggList) {
        this.aggList = aggList;
    }
}
