package model;

import cn.bmob.v3.BmobObject;

/**
 * Created by xiao on 17-2-22.
 */

public class TB_userInfo extends BmobObject {

    private String name;
    private String pwd;

    public TB_userInfo(String tableName, String name) {
        super(tableName);
        this.name = name;
    }

    public TB_userInfo(){

    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public TB_userInfo(String name) {
        this.name = name;

    }
}
