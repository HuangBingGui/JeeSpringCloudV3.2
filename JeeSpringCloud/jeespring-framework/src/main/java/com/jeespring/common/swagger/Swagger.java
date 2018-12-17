package com.jeespring.common.swagger;

import com.google.common.base.Predicates;
import com.jeespring.common.web.AbstractBaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger {

    @Bean("JeeSpring云接口")
    public Docket createJeeSpringRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("JeeSpring云接口")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))                         //这里采用包含注解的方式来确定要显示的接口
                //.apis(RequestHandlerSelectors.basePackage("com.jeespring.modules"))    //这里采用包扫描的方式来确定要显示的接口
                //.apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                //.paths(PathSelectors.regex("/rest/.*"))
                //.paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Swagger2构建RESTful APIs")
                .description("更多JeeSpring相关文章")
                .termsOfServiceUrl("http://www.jeespring.com/")
                .contact("contact")
                .version("1.0")
                .build();
    }
    
    /*注解
    @ApiOperation(value="创建用户", notes="根据User对象创建用户")
    @ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "User")
    
    
    @ApiOperation("生成代码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "moduleName", value = "模块名称", required = true, dataType = "String"),
            @ApiImplicitParam(name = "bizChName", value = "业务名称", required = true, dataType = "String"),
            @ApiImplicitParam(name = "bizEnName", value = "业务英文名称", required = true, dataType = "String"),
            @ApiImplicitParam(name = "path", value = "项目生成类路径", required = true, dataType = "String")
    })
    */
}