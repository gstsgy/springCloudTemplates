package com.guyue.permission.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.guyue.permission.bean.db.DeptDO;
import com.guyue.permission.bean.db.OperatorDO;
import com.guyue.permission.bean.db.UserRoleDO;
import com.guyue.permission.bean.view.OperatorVO;
import com.guyue.permission.service.UserService;
import com.guyue.common.bean.ResponseBean;
import com.guyue.common.utils.Encrypt;
import com.guyue.common.utils.StrongParentSubclass;
import com.guyue.permission.mapper.DeptMapper;
import com.guyue.permission.mapper.OperatorMapper;
import com.guyue.permission.mapper.UserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @ClassName UserServiceImpl
 * @Description TODO
 * @Author guyue
 * @Date 2020/10/14 下午3:36
 **/
@Transactional
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private ResponseBean responseBean;
    @Autowired
    private OperatorMapper operatorMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private DeptMapper deptMapper;
    @Value("${userdefaultpw}")
    private String defaultPassWord;


    //private String defaultPw;
    @Override
    public ResponseBean addUser(OperatorDO operatorDO) {
        if (operatorDO != null) {
            QueryWrapper<OperatorDO> queryWrapper = new QueryWrapper();
            queryWrapper.lambda().eq(OperatorDO::getCode, operatorDO.getCode());

            OperatorDO operator = operatorMapper.selectOne(queryWrapper);
            if (operator != null ) {
                return responseBean.getError("用户名重复");
            } else {
                operatorDO.setPasswd(Encrypt.encryptToMD5(defaultPassWord));
                return responseBean.getSuccess(operatorMapper.insert(operatorDO) > 0);
            }

        }

        return responseBean.getError("参数不能为空");
    }

    @Override
    public ResponseBean deleteUser(List<OperatorDO> operatorDOs) {
        if (operatorDOs != null && operatorDOs.size() > 0) {
            operatorDOs.forEach(item -> {
                if (item.getId() != 1) {
                    OperatorDO operatorDO = operatorMapper.selectById(item.getId());
                    operatorMapper.deleteById(operatorDO.getId());

                    // 删除用户角色
                    QueryWrapper<UserRoleDO> queryWrapper = new QueryWrapper();
                    queryWrapper.lambda().eq(UserRoleDO::getUserId, operatorDO.getId());
                    List<UserRoleDO> tmps = userRoleMapper.selectList(queryWrapper);
                    tmps.forEach(it -> {
                        userRoleMapper.deleteById(it.getId());
                    });
                }
            });
            return responseBean.getSuccess(true);
        }

        return responseBean.getError("参数不能为空");
    }

    @Override
    public ResponseBean queryUser(OperatorDO operatorDO, String operatorId, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<OperatorDO> queryWrapper = new QueryWrapper().lambda();
        queryWrapper.ne(OperatorDO::getId, 1);
        if (operatorDO != null && operatorDO.getDeptId() != null) {
            queryWrapper.eq(OperatorDO::getDeptId, operatorDO.getDeptId());
        }
       /* if(operatorDO!=null&&operatorDO.getDeptId()!=null){
            queryWrapper.eq("dept_Id", operatorDO.getDeptId());
        }*/

        if (operatorDO != null && operatorDO.getCode() != null) {
            queryWrapper.like(OperatorDO::getCode, operatorDO.getCode());
        }
        if (operatorDO != null && operatorDO.getPosition() != null) {
            queryWrapper.like(OperatorDO::getPosition, operatorDO.getPosition());
        }

        List<OperatorDO> operators = PageHelper.startPage(pageNum, pageSize).doSelectPage(() -> operatorMapper.selectList(queryWrapper));
        List<OperatorVO> result= operators.stream().map(item -> {
            OperatorVO tmp = new OperatorVO();
            StrongParentSubclass.parentSubclass(item, tmp);
            if (tmp.getDeptId() != null) {
                DeptDO tmp1 = deptMapper.selectById(tmp.getDeptId());
                if (tmp1 != null) {
                    tmp.setDeptName(tmp1.getName());
                }
            }
            return tmp;
        }).collect(Collectors.toList());
        return responseBean.getSuccess(new PageInfo(result));
    }

    @Override
    public ResponseBean updateUser(OperatorDO operatorDO) {
        if (operatorDO != null) {
            if (operatorDO.getId() == 1) {
                return responseBean.getError("管理员帐号不允许修改");
            }
            OperatorDO operatorDOt = operatorMapper.selectById(operatorDO.getId());
            if (operatorDOt == null || operatorDOt.getEffective() == 0) {
                return responseBean.getError("该用户不存在");
            }
            operatorDO.setPasswd(operatorDOt.getPasswd());
            return responseBean.getSuccess(operatorMapper.updateById(operatorDO) > 0);
        }

        return responseBean.getError("参数不能为空");
    }

    @Override
    public ResponseBean getUserPosition() {
        QueryWrapper<OperatorDO> queryWrapper = new QueryWrapper();
        queryWrapper.select("distinct position");
        return responseBean.getSuccess(operatorMapper.selectList(queryWrapper).stream().map(OperatorDO::getPosition).collect(Collectors.toList()));
    }

    private ConcurrentHashMap<String, Integer> mailCode = new ConcurrentHashMap();
    @Autowired
    private JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String mailuser;

    @Override
    public ResponseBean sendMail(String code) {
        QueryWrapper<OperatorDO> queryWrapper = new QueryWrapper();
        queryWrapper.lambda().eq(OperatorDO::getCode, code);
        OperatorDO operator = operatorMapper.selectOne(queryWrapper);
        if (operator.getId() == 1) {
            return responseBean.getError("管理员帐号不允许修改");
        }

        if (operator != null) {
            if (operator.getEmail() == null || "".equals(operator.getEmail())) {
                return responseBean.getError("此用户没有绑定邮箱");
            } else {
                try {
                    SimpleMailMessage message = new SimpleMailMessage();
                    message.setSubject("验证码");
                    int randNum = new Random().nextInt(999999);
                    if (randNum < 100000) {
                        randNum += 100000;
                    }
                    String codemessage = "您正在修改登录密码，验证码为：" + randNum + "，请在30分钟内完成!";
                    //     session.setAttribute("code", code);
                    mailCode.put(code, randNum);
                    message.setText(codemessage);
                    message.setTo(operator.getEmail());
                    message.setFrom(mailuser);
                    mailSender.send(message);
                    return responseBean.getSuccess("发送成功");

                } catch (Exception e) {
                    return responseBean.getError(e.getMessage());
                }
            }
        } else {
            return responseBean.getError("没有此用户信息");
        }
    }

    @Override
    public ResponseBean updateUserpw(OperatorDO user, Integer mailcode) {

        if (mailCode.get(user.getCode()) != null && mailCode.get(user.getCode()).equals(mailcode)) {
            mailCode.remove(user.getCode());
            QueryWrapper<OperatorDO> queryWrapper = new QueryWrapper();
            queryWrapper.lambda().eq(OperatorDO::getCode, user.getCode());
            OperatorDO operator = operatorMapper.selectOne(queryWrapper);

            if (operator != null) {
                operator.setPasswd(Encrypt.encryptToMD5(user.getPasswd()));
                operatorMapper.updateById(operator);
                return responseBean.getSuccess(true);
            } else {
                return responseBean.getError("用户名不正确");
            }
        } else {
            return responseBean.getError("验证码不正确");
        }
    }

    @Override
    public ResponseBean queryOne(String code) {
        QueryWrapper<OperatorDO> queryWrapper = new QueryWrapper();
        queryWrapper.lambda().eq(OperatorDO::getCode, code);
        return responseBean.getSuccess(operatorMapper.selectOne(queryWrapper));

    }

    @Override
    public ResponseBean queryUserEnum(Long deptId) {
        QueryWrapper<OperatorDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(OperatorDO::getDeptId, deptId).ne(OperatorDO::getId, 1);
        List<OperatorDO> list = operatorMapper.selectList(queryWrapper);
        List<Map> resultData = list.stream().map(item -> {
            Map<String, Object> map = new HashMap();
            map.put("key", item.getId());
            map.put("value", item.getNickName());
            return map;
        }).collect(Collectors.toList());
        return responseBean.getSuccess(resultData);
    }

    @Override
    public ResponseBean restpassword(OperatorDO user) {
        if (user == null) {
            return responseBean.getError("参数不允许为空");
        }
        OperatorDO tmp = operatorMapper.selectById(user.getId());
        tmp.setPasswd(Encrypt.encryptToMD5(defaultPassWord));
        operatorMapper.updateById(tmp);
        return responseBean.getSuccess(true);
    }
}
