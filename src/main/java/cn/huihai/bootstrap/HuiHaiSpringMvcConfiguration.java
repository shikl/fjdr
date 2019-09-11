package cn.huihai.bootstrap;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import cn.huix.bootstrap.SpringMvcConfiguration;

/**
* @ClassName: HuixSpringMvcConfiguration  
* @Description: Spring MVC 类配置文件.
* @author shikl
* @date 2018年4月23日 下午9:44:26  
*
 */
@Configuration
public class HuiHaiSpringMvcConfiguration extends SpringMvcConfiguration {

//    @Autowired
//    private Environment env;
//    @Autowired
//    private UserInfoService userInfoService;
//    @Autowired
//    private HumanBasicService humanBasicService;
//    @Autowired
//    private RedisTemplate redisTemplate;

    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        return multipartResolver;
    }

    @Override
    protected void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
//        argumentResolvers.add(new LoginUserArgumentsResolver());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        UserInitHandleInterceptor interceptor = new UserInitHandleInterceptor();
//        interceptor.setEnv(env);
//        interceptor.setLoginUserInfoService( userInfoService );
//        interceptor.setHumanBasicService( humanBasicService );
//        interceptor.setRedisTemplate(redisTemplate);
//        registry.addInterceptor(interceptor);
    }
}