package com.guyue.permission;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.guyue.permission.bean.db.DeptDO;
import com.guyue.permission.mapper.DeptMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @ClassName DeptTest
 * @Description TODO
 * @Author guyue
 * @Date 2020/10/14 上午10:01
 **/
@SpringBootTest//(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class DeptTest {
    @Autowired
    private DeptMapper deptMapper;


    @Test
    public  void add(){
        DeptDO deptDO = new DeptDO();
        deptDO.setInsertId("admin");
        deptDO.setUpdateId("admin");
        deptDO.setName("测试2");
        //deptDO.setParentId();
        deptMapper.insert(deptDO);
        System.out.println(deptDO.getId());
    }

    @Test
    public  void update(){
        DeptDO deptDO = deptMapper.selectById(2);
        deptDO.setParentId(1L);

        deptMapper.updateById(deptDO);
        System.out.println(deptDO.getId());
    }

    @Test
    public  void query(){
        QueryWrapper<DeptDO> queryWrapper = new QueryWrapper();
        queryWrapper.isNull("parent_Id").eq("effective",1);
        List<DeptDO> depts = deptMapper.selectList(queryWrapper);
        System.out.println(depts);
    }

    /*@Test
    public  void add(){
        DeptDO deptDO = new DeptDO();
        deptDO.setInsertId("admin");
        deptDO.setUpdateId("admin");
        deptDO.setName("测试2");
        //deptDO.setParentId();
        deptMapper.insert(deptDO);
        System.out.println(deptDO.getId());
    }*/
}
