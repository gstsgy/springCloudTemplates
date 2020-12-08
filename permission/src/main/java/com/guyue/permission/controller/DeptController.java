package com.guyue.permission.controller;

import com.guyue.permission.bean.db.DeptDO;
import com.guyue.permission.bean.view.TreeNodeVO;
import com.guyue.permission.service.DeptService;
import com.guyue.common.bean.ResponseBean;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName DeptController
 * @Description TODO
 * @Author guyue
 * @Date 2020/10/16 下午4:27
 **/
@RestController
@Api(tags = "部门")
@RequestMapping("/dept")
public class DeptController {
    @Autowired
    private DeptService deptService;

    @ApiOperation("新增一个部门")
    @PostMapping("dept")
    public ResponseBean addDept(@RequestBody DeptDO deptDO) {
        return deptService.addDept(deptDO);
    }
    @ApiOperation("删除部门")
    @DeleteMapping("depts")
    public ResponseBean deleteDepts(@RequestBody TreeNodeVO deptDO) {
        if(deptDO==null){
            return new ResponseBean(-1,"参数不能为空",null);
        }
        long peopleCount = deptService.getDeptPeopleCount(Long.parseLong(deptDO.getValue()));
        if(peopleCount>0){
            return new ResponseBean(-1,"该部门还存在人员，不允许删除",null);
        }
        deptService.deleteDepts(deptDO);
        return new ResponseBean(0,"",true);
    }
    @ApiOperation("查询部门")
    @GetMapping("depts")
    public ResponseBean queryDept() {

        return deptService.queryDept();
    }
    @ApiOperation("修改一个部门")
    @PutMapping("dept")
    public ResponseBean updateDept(@RequestBody TreeNodeVO deptDO) {
        return deptService.updateDept(deptDO);
    }

    @GetMapping("deptpeoplecount")
    @ApiOperation("获取该部门及下属部门的人员数量")
    public ResponseBean getDeptPeopleCount(@ApiParam("部门id") Long  id){
        // departmentService.deleteDept(treeNodeView);
        return new ResponseBean(0,"",deptService.getDeptPeopleCount(id));
    }


    @ApiOperation("查询部门枚举")
    @GetMapping("deptenum")
    public ResponseBean queryDeptEnum() {

        return deptService.queryDeptEnum();
    }

}
