package com.guyue.permission.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.guyue.permission.bean.db.MenuDO;
import com.guyue.permission.bean.db.RoleMenuDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName RoleMenuMapper
 * @Description TODO
 * @Author guyue
 * @Date 2020/10/14 下午3:17
 **/
@Mapper
@Repository
public interface RoleMenuMapper extends BaseMapper<RoleMenuDO> {
    List<MenuDO> selectMenuIdByRole(@Param("roleId") Long roleId, @Param("type") Integer type);
}
