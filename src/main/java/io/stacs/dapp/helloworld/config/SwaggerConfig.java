package io.stacs.dapp.helloworld.config;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author HuangShengli
 * @ClassName SwaggerConfig
 * @Description TODO
 * @since 2020/9/12
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .pathMapping("/")
                .select()
                // 选择哪些路径和api会生成document
                .apis(RequestHandlerSelectors.any())
                //不显示错误的接口地址
                .paths(Predicates.not(PathSelectors.regex("/error.*")))
                // 对根下所有路径进行监控
                .paths(PathSelectors.regex("/.*"))
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("报文体验Helloworld示例文档")
                .contact(new Contact("Captain", "", "huangshengli@stacs.cn"))
                .description("这是SWAGGER_2生成的接口文档")
                .build();
    }
}
