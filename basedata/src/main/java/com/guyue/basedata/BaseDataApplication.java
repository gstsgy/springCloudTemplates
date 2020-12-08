package com.guyue.basedata;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
@ServletComponentScan
@EnableSwagger2
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"com.sr.suray.wms.basedata","com.sr.suray.wms.common"})
@MapperScan(value = {"com.sr.suray.wms.common.mapper","com.sr.suray.wms.basedata.mapper"})
public class BaseDataApplication {
    public static void main(String[] args) {
        SpringApplication.run(BaseDataApplication.class);
    }
}
