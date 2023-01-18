package shop.iamhyunjun.ostargram.swagger.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;



@Configuration
@EnableWebMvc
public class SwaggerConfig implements WebMvcConfigurer {


    // EnableWebMvc로 인하여 JSON 타입 포맷팅이 안나와서 수동으로 넣어줌
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
//        Remove the default MappingJackson2HttpMessageConverter
        converters.removeIf(converter -> {
            String converterName = converter.getClass().getSimpleName();
            return converterName.equals("MappingJackson2HttpMessageConverter");
        });
//        Add your custom MappingJackson2HttpMessageConverter
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        converter.setObjectMapper(objectMapper);
        converters.add(converter);
        WebMvcConfigurer.super.extendMessageConverters(converters);
    }

//// jwt  버전
//@Bean
//public Docket api() {
//    return new Docket(DocumentationType.SWAGGER_2)
//            .useDefaultResponseMessages(false)
//            .select()
//            .apis(RequestHandlerSelectors.any())
////            .paths(PathSelectors.ant("/api/**"))
//            .build()
//            .apiInfo(metaData())
//            .securityContexts(Arrays.asList(securityContext()))
//            .securitySchemes(Arrays.asList(apiKey()));
//
//}
//
//    private ApiInfo metaData() {
//        return new ApiInfoBuilder()
//                .title("Hanghae99 REST API")
//                .description("로그인 후 나오는 토큰 값을 Authorize에 입력하고 사용!")
//                .version("0.0.1")
//                .termsOfServiceUrl("Terms of service")
//                .contact(new Contact("Hyun Jun Hwang", "https://github.com/hyunjunhwang1994/spring_deeping_week_assignment", "hyunjunhwang1994@gmail.com"))
//                .license("Apache License Version 2.0")
//                .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
//                .build();
//    }
//
//    private ApiKey apiKey() {
//        return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
//    }
//
//
//    private SecurityContext securityContext() {
//        return springfox
//                .documentation
//                .spi.service
//                .contexts
//                .SecurityContext
//                .builder()
//                .securityReferences(defaultAuth()).forPaths(PathSelectors.any()).build();
//    }
//
//    List<SecurityReference> defaultAuth() {
//        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
//        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
//        authorizationScopes[0] = authorizationScope;
//        return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
//    }
//
//}
    // 세션 버전
    @Bean
    public OpenAPI api() {
        Info info = new Info()
                .title("제목")
                .version("V1.0")
                .contact(new Contact()
                        .name("Web Site")
                        .url("localhost:8080"))
                .license(new License()
                        .name("Apache License Version 2.0")
                        .url("http://www.apache.org/license/LICENSE-2.0"));

        //-------------------- 인가 방식 지정 ---------------------
        SecurityScheme auth = new SecurityScheme()
                .type(SecurityScheme.Type.APIKEY).in(SecurityScheme.In.COOKIE).name("JSESSIONID");
        SecurityRequirement securityRequirement = new SecurityRequirement().addList("basicAuth");

        return new OpenAPI()
                .components(new Components().addSecuritySchemes("basicAuth", auth))
                .addSecurityItem(securityRequirement)
                .info(info);
    }


}
