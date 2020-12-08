package com.guyue.common.utils;

import java.lang.reflect.Field;

/**
 * @ClassName StrongParentSubclass
 * @Description TODO
 * @Author guyue
 * @Date 2020/10/27 下午2:26
 **/
public class StrongParentSubclass {
    public static Object parentSubclass(Object parent,Object subclass){
        Field[] parents = parent.getClass().getDeclaredFields();//获取所有属性
        Field[] children = subclass.getClass().getSuperclass().getDeclaredFields();//获取父类所有属性
        try {
            for (Field fieldParent : parents){
                fieldParent.setAccessible(true);
                String nameParent = fieldParent.getName(); //获取属性的名字
                Object valueParent = fieldParent.get(parent);//获取属性值
                for (Field fieldChild : children){
                    fieldChild.setAccessible(true);
                    String nameChild = fieldChild.getName(); //获取属性的名字
                    Object valueChild= fieldChild.get(subclass);
                    if (nameChild.equals(nameParent)){
                        fieldChild.set(subclass,valueParent);
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return subclass;
    }
}
