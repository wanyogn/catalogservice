package com.zhixie.catalog.model.es;

import java.util.List;

/**
 * Created by admin on 2017/7/13.
 */

public class Hit
{
    public String _index;
    public String _type;
    public String _id;
    public int _version;
//    public object _score;
    public Object _source;
    public List<Object> sort;
}
