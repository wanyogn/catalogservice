package com.zhixie.catalog.model;

public class Catalog {

    private int id;//ID
    private String flow_number;//流水号
    private String directory_number;//目录编号
    private String directory_name;//目录名称
    private String first_product_number;//一级产品类别编号
    private String first_product_name;//一级产品类别名称
    private String second_product_number;//二级产品类别编号
    private String second_product_name;//二级产品类别名称
    private String product_description;//产品描述
    private String expected_use;//预期用途
    private String product_example;//品名举例
    private String management_category;//管理类别
    private String composite_product;//药械组合产品
    private String remark;//产品属性

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public String getFlow_number() {return flow_number;}

    public void setFlow_number(String flow_number) {this.flow_number = flow_number;}

    public String getDirectory_number() {return directory_number;}

    public void setDirectory_number(String directory_number) {this.directory_number = directory_number;}

    public String getDirectory_name() {return directory_name;}

    public void setDirectory_name(String directory_name) {this.directory_name = directory_name;}

    public String getFirst_product_number() {return first_product_number;}

    public void setFirst_product_number(String first_product_number) {this.first_product_number = first_product_number;}

    public String getFirst_product_name() {return first_product_name;}

    public void setFirst_product_name(String first_product_name) {this.first_product_name = first_product_name;}

    public String getSecond_product_number() {return second_product_number;}

    public void setSecond_product_number(String second_product_number) {this.second_product_number = second_product_number;}

    public String getSecond_product_name() {return second_product_name;}

    public void setSecond_product_name(String second_product_name) {this.second_product_name = second_product_name;}

    public String getProduct_description() {return product_description;}

    public void setProduct_description(String product_description) {this.product_description = product_description;}

    public String getExpected_use() {return expected_use;}

    public void setExpected_use(String expected_use) {this.expected_use = expected_use;}

    public String getProduct_example() {return product_example;}

    public void setProduct_example(String product_example) {this.product_example = product_example;}

    public String getManagement_category() {return management_category;}

    public void setManagement_category(String management_category) {this.management_category = management_category; }

    public String getComposite_product() {return composite_product;}

    public void setComposite_product(String composite_product) {this.composite_product = composite_product;}

    public String getRemark() {return remark;}

    public void setRemark(String remark) {this.remark = remark;}
}
