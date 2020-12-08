

package com.guyue.gateway.controller;

import com.guyue.gateway.conf.DocumentationConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger.web.*;

import java.util.List;


/**
 * @ClassName SwaggerResourceController
 * @Description TODO
 * @Author guyue
 * @Date 2020/10/10 上午11:35
 **/



@RestController
@RequestMapping("/swagger-resources")
public class SwaggerResourceController {
    private DocumentationConfig documentationConfig;

    @Autowired
    public SwaggerResourceController(DocumentationConfig documentationConfig) {
        this.documentationConfig = documentationConfig;
    }

    @RequestMapping(value = "/configuration/security")
    public ResponseEntity<SecurityConfiguration> securityConfiguration() {
        return new ResponseEntity<>(SecurityConfigurationBuilder.builder().build(), HttpStatus.OK);
    }

    @RequestMapping(value = "/configuration/ui")
    public ResponseEntity<UiConfiguration> uiConfiguration() {
        return new ResponseEntity<>(UiConfigurationBuilder.builder().build(), HttpStatus.OK);
    }

    @RequestMapping
    public ResponseEntity<List<SwaggerResource>> swaggerResources() {
        return new ResponseEntity<>(documentationConfig.get(), HttpStatus.OK);
    }
}
