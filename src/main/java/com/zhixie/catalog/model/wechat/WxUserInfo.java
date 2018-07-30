package com.zhixie.catalog.model.wechat;

public class WxUserInfo {
    //用户的标识
    private String openid;
    //用户的昵称
    private String nickname;
    //用户性别1为男性2为女性
    private int sex;
    //用户个人资料填写的省份
    private String province;
    //用户个人资料填写的城市
    private String city;
    //国家
    private String country;
    //用户头像,最后一个数值代表正方形头像大学（0,46,64,96,132）
    private String headimgurl;
    //用户特权信息
    private String[] privilege;
    //用户统一标识
    private String unionid;

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public String[] getPrivilege() {
        return privilege;
    }

    public void setPrivilege(String[] privilege) {
        this.privilege = privilege;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }
}
