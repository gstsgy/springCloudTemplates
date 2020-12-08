package com.guyue.permission.service;

import com.guyue.permission.bean.db.OperatorDO;
import com.guyue.common.bean.ResponseBean;

import java.util.List;

/**
 * @ClassName UserService
 * @Description TODO
 * @Author guyue
 * @Date 2020/10/14 下午3:29
 **/
public interface UserService {
    ResponseBean addUser(OperatorDO operatorDO);

    ResponseBean deleteUser(List<OperatorDO> operatorDOs);

    ResponseBean queryUser(OperatorDO operatorDO,String operatorId,Integer pageNum,Integer pageSize);

    ResponseBean updateUser(OperatorDO operatorDO);

    ResponseBean getUserPosition();

    ResponseBean sendMail(String code);

    ResponseBean updateUserpw( OperatorDO user, Integer mailcode);

    ResponseBean queryOne(String code);

    ResponseBean queryUserEnum(Long deptId);

    ResponseBean restpassword( OperatorDO user);
}
