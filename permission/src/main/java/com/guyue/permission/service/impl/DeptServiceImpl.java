package com.guyue.permission.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.guyue.permission.bean.db.DeptDO;
import com.guyue.permission.bean.db.OperatorDO;
import com.guyue.permission.bean.view.TreeNodeVO;
import com.guyue.permission.service.DeptService;
import com.guyue.common.bean.ResponseBean;
import com.guyue.permission.mapper.DeptMapper;
import com.guyue.permission.mapper.OperatorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName DeptServiceImpl
 * @Description TODO
 * @Author guyue
 * @Date 2020/10/16 下午4:29
 **/
@Transactional
@Service
public class DeptServiceImpl implements DeptService {
    @Autowired
    private DeptMapper deptMapper;

    @Autowired
    private ResponseBean responseBean;

    @Override
    public ResponseBean addDept(DeptDO deptDO) {
        if (deptDO == null) {
            return responseBean.getError("参数不能为空");
        }
        List<DeptDO> lists = queryChildren(deptDO.getParentId());
        long count = lists.stream().filter(item -> item.getName().equals(deptDO.getName())).count();
        if (count > 0) {
            return responseBean.getError("同级部门不可重复");
        }
        return responseBean.getSuccess(deptMapper.insert(deptDO)>0);
    }

    @Override
    public void deleteDepts(TreeNodeVO deptDO) {
        if (deptDO == null) {
            return;
        }

        DeptDO tmp = deptMapper.selectById(deptDO.getValue());
        if (tmp != null) {
            deptMapper.deleteById(tmp.getId());
        }
        if (deptDO.getChildren() == null || deptDO.getChildren().size() == 0) {
            return;
        }
        deptDO.getChildren().forEach(this::deleteDepts);
    }

    @Override
    public ResponseBean queryDept() {
        List<DeptDO> root = queryChildren(null);
        List<TreeNodeVO> trees =
                root.stream().map(item -> new TreeNodeVO(0, "", item.getName(), item.getId() + "")).collect(Collectors.toList());
        trees.forEach(item -> getTree(item, 1));
        return responseBean.getSuccess(trees);
        //return null;
    }

    @Override
    public ResponseBean updateDept(TreeNodeVO deptDO) {
        if (deptDO == null) {
            return responseBean.getError("参数不能为空");
        }

        DeptDO tmp = deptMapper.selectById(deptDO.getValue());
        if (tmp != null) {
            tmp.setName(deptDO.getTitle());
            return responseBean.getSuccess(deptMapper.updateById(tmp)>0);
        } else {
            return responseBean.getError("部门不存在");
        }

    }

    @Autowired
    private OperatorMapper operatorMapper;

    @Override
    public Long getDeptPeopleCount(Long id) {
        QueryWrapper<OperatorDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(OperatorDO::getDeptId, id);
        List<OperatorDO> lsittmp = operatorMapper.selectList(queryWrapper);
        List<DeptDO> list = queryChildren(id);
        if (list == null || list.size() == 0) {
            return (long) lsittmp.size();
        }
        Long count1 = list.stream().map(item -> getDeptPeopleCount(item.getId())).reduce(Long::sum).get();
        return (long) lsittmp.size() + count1;
    }

    @Override
    public ResponseBean queryDeptEnum() {
        QueryWrapper<DeptDO> queryWrapper = new QueryWrapper<>();
        List<DeptDO> list = deptMapper.selectList(queryWrapper);
        List<Map> resultData = list.stream().map(item->{
            Map<String,Object> map = new HashMap();
            map.put("key",item.getId());
            map.put("value",item.getName());
            return map;
        }).collect(Collectors.toList());
        return responseBean.getSuccess(resultData);
    }

    private List<DeptDO> queryChildren(Long parentId) {
        QueryWrapper<DeptDO> queryWrapper = new QueryWrapper<>();
        if (parentId == null) {
            queryWrapper.lambda().isNull(DeptDO::getParentId);
        } else {
            queryWrapper.lambda().eq(DeptDO::getParentId, parentId);
        }
        return deptMapper.selectList(queryWrapper);
    }

    private void getTree(TreeNodeVO treeNodeView, int n) {
        List<DeptDO> depts = queryChildren(Long.parseLong(treeNodeView.getValue()));
        if (depts == null || depts.size() == 0) {
            return;
        }
        List<TreeNodeVO> trees = depts.stream().map(item -> new TreeNodeVO(n, treeNodeView.getValue(), item.getName(), item.getId() + "")).collect(Collectors.toList());
        treeNodeView.setChildren(trees);
        treeNodeView.getChildren().forEach(item -> getTree(item, n + 1));
    }
}
