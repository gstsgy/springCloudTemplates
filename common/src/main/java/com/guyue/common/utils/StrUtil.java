package com.guyue.common.utils;

/**
 * @ClassName StrUtil
 * @Description TODO
 * @Author guyue
 * @Date 2020/11/2 下午3:20
 **/
public class StrUtil {

    /**
     * 判断字符串是否空
     * @param content 目标字符串
     * @return 为空返回true
     */
    public static boolean isEmpty(String content){
        if(content==null)return true;
        else return content.length() == 0;
    }

    public static String padLeft(String content,int len,char letter){
        return String.format("%"+len+"s", content).replace(' ', letter);
    }
    public static String padRight(String content,int len,char letter){
        return String.format("%1$-"+len+"s", content).replace(' ', letter);
    }
    public static void main(String[] args) {
        System.out.println(padRight("124241",4,'0'));

        /*String str = "gr";
        String res = String.format("%5s", str);
        res = res.replaceAll("\\s", "1");*/
       // System.out.println(String.format("%4s", "1"));
    }
}
