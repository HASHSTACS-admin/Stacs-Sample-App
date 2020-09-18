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
 * @Description Swagger UI Config
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
                //select specific api request
                .apis(RequestHandlerSelectors.any())
                //configure not to display errors
                .paths(Predicates.not(PathSelectors.regex("/error.*")))
                //set path monitoring to root '/' 
                .paths(PathSelectors.regex("/.*"))
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Sample Document")
                .contact(new Contact("Support", "", "support@stacs.io"))
                .description("This is the sample API document using Swagger 2")
                .build();
    }
}
