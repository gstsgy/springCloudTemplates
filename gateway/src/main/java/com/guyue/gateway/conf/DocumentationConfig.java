package com.guyue.gateway.conf;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName DocumentationConfig
 * @Description TODO
 * @Author guyue
 * @Date 2020/10/10 上午11:05
 **/
@Component
@Primary
public class DocumentationConfig implements SwaggerResourcesProvider {
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList<SwaggerResource>();
        resources.add(swaggerResource("permission", "/permission/v2/api-docs", "2.0"));
        resources.add(swaggerResource("basedata", "/basedata/v2/api-docs", "2.0"));
        return resources;
    }

    private SwaggerResource swaggerResource(String name, String location, String version) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setUrl(location);
        swaggerResource.setSwaggerVersion(version);
        return swaggerResource;
    }
}
