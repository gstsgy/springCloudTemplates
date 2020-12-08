package com.guyue.permission;

import com.guyue.common.utils.Encrypt;
import org.junit.jupiter.api.Test;

/**
 * @ClassName PassWordTest
 * @Description TODO
 * @Author guyue
 * @Date 2020/10/20 上午9:37
 **/
public class PassWordTest {
    @Test
    public void t1(){
        System.out.println(Encrypt.encryptToMD5("suray2020"));
    }
}
