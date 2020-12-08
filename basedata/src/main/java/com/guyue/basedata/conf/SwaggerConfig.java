package com.guyue.basedata.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .pathMapping("/")
                //.
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.sr.suray.wms"))
                .paths(PathSelectors.any())
                .build().apiInfo(new ApiInfoBuilder()
                        .title("基础数据相关接口文档")
                        .description("基础数据相关接口文档")
                        .version("Version: 1.0.0")
                       //.contact(new Contact("前端页面","http://192.168.1.106:9528/wms","hujj@suray.cn"))
                       //.license("The Apache License")
                       // .licenseUrl("192.168.1.159")
                        .build());
    }
}
