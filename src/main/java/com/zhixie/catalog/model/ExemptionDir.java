package com.zhixie.catalog.model;

/**
 * 豁免目录
 */
public class ExemptionDir {
    private String id;//id
    private String category;//类别
    private String classify_code;//分类编码
    private String product_name;//产品名称
    private String product_description;//产品描述
    private String management_category;//管理类别
    private String remark;//备注

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getClassify_code() {
        return classify_code;
    }

    public void setClassify_code(String classify_code) {
        this.classify_code = classify_code;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_description() {
        return product_description;
    }

    public void setProduct_description(String product_description) {
        this.product_description = product_description;
    }

    public String getManagement_category() {
        return management_category;
    }

    public void setManagement_category(String management_category) {
        this.management_category = management_category;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
