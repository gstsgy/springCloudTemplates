package com.guyue.common.bean;

import java.util.Map;

/**
 * @author guyue
 * @Description TODO()
 * @date 20-6-24 下午4:18
 */
public class UpdateModel {
    // 表名，字段名
    private String tableName;

    private Map<String,String> setdata;

    private Map<String,String> wheredata;

    public Map<String, String> getSetdata() {
        return setdata;
    }

    public void setSetdata(Map<String, String> setdata) {
        this.setdata = setdata;
    }

    public Map<String, String> getWheredata() {
        return wheredata;
    }

    public void setWheredata(Map<String, String> wheredata) {
        this.wheredata = wheredata;
    }

    public String getTableName() {
        return tableName;
    }
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public String toString() {
        return "UpdateModel{" +
                "tableName='" + tableName + '\'' +
                ", setdata=" + setdata +
                ", wheredata=" + wheredata +
                '}';
    }
}
