package com.guyue.common.bean;

/**
 * @ClassName BaseEnum
 * @Description TODO
 * @Author guyue
 * @Date 2020/10/10 上午9:46
 **/
public interface BaseEnum<E extends Enum<E>, T> {


    /**
     * 真正与数据库进行映射的值
     *
     * @return
     */
    T getValue();

    /**
     * 显示的信息
     *
     * @return
     */
    String getDisplayName();
}
