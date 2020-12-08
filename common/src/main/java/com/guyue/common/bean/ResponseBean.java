package com.guyue.common.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.stereotype.Component;
@ApiModel
@Component
public class ResponseBean {
    @ApiModelProperty(value = "是否成功",example = "1")
    private Integer code;//

    @ApiModelProperty("error message")
    private String message;//是否成功

    @ApiModelProperty("请求数据")
    private Object data;//是否成功

    public ResponseBean() {
        super();
        // TODO Auto-generated constructor stub
    }
    public ResponseBean(Integer code, String message, Object data) {
        super();

        this.code = code;
        this.message = message;
        this.data = data;
    }
    public Integer getCode() {
        return code;
    }
    public void setCode(Integer code) {
        this.code = code;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public Object getData() {
        return data;
    }
    public void setData(Object data) {
        this.data = data;
    }
    @Override
    public String toString() {
        return "Result [code=" + code + ", message=" + message + ", data=" + data + "]";
    }


    public ResponseBean getSuccess(Object data){
        this.code = 0;
        this.message="success";
        this.data = data;
        return this;
    }
    public ResponseBean getError(String errorMessage){
        this.code = -1;
        this.message=errorMessage;
        this.data = null;
        return this;
    }

    public ResponseBean returnSuccess(Object data){
        this.code = 0;
        this.message="success";
        this.data = (data==null)?true:data;
        return this;
    }

    public ResponseBean returnError(String errorMessage){
        this.code = -1;
        this.message=errorMessage;
        this.data = false;
        return this;
    }


    public ResponseBean getError(String errorMessage,Object... args){
        errorMessage = String.format(errorMessage,args);
        this.code = -1;
        this.message=errorMessage;
        this.data = null;
        return this;
    }



    public ResponseBean returnError(String errorMessage,Object... args){
        errorMessage = String.format(errorMessage,args);
        this.code = -1;
        this.message=errorMessage;
        this.data = false;
        return this;
    }

}
