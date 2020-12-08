package com.guyue.common.bean;

/**
 * @ClassName SearchBaseView
 * @Description TODO
 * @Author guyue
 * @Date 2020/10/10 上午9:47
 **/
public abstract class SearchBaseView {

    private Integer pageNum;


    private Integer pageSize;


    private String orderby;


    private String operatorId;

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getOrderby() {
        return orderby;
    }

    public void setOrderby(String orderby) {
        this.orderby = orderby;
    }
}
