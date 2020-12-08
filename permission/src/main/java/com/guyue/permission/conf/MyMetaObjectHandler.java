package com.guyue.permission.conf;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @ClassName MyMetaObjectHandler
 * @Description TODO
 * @Author guyue
 * @Date 2020/11/4 下午4:41
 **/
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "insertYmd", Date.class, new Date()); // 起始版本 3.3.0(推荐使用)
        this.strictInsertFill(metaObject, "updateYmd", Date.class, new Date());
        this.strictInsertFill(metaObject, "insertId", String.class, GetUserIdFilter.getUser(Thread.currentThread().getId()));
        this.strictInsertFill(metaObject, "updateId", String.class, GetUserIdFilter.getUser(Thread.currentThread().getId()));
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "updateYmd", Date.class, new Date());
        this.strictInsertFill(metaObject, "updateId", String.class, GetUserIdFilter.getUser(Thread.currentThread().getId()));
    }
}
