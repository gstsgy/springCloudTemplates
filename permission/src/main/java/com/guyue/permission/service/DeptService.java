package com.guyue.permission.service;

import com.guyue.permission.bean.db.DeptDO;
import com.guyue.permission.bean.view.TreeNodeVO;
import com.guyue.common.bean.ResponseBean;

/**
 * @ClassName DeptService
 * @Description TODO
 * @Author guyue
 * @Date 2020/10/16 下午4:29
 **/
public interface DeptService {

    ResponseBean addDept(DeptDO deptDO);

    void deleteDepts(TreeNodeVO deptDO);

    ResponseBean queryDept();


    ResponseBean updateDept(TreeNodeVO deptDO);

    //获取该部门及下属部门的人员数量
    Long getDeptPeopleCount(Long id);

    ResponseBean queryDeptEnum();
}
