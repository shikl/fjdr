package cn.huihai.bootstrap;

import javax.servlet.Filter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

/**
* @ClassName: MiddleWebApplicationInitializer  
* @Description: Web 启动类,整个应用启动入口.
* @author shikl
* @date 2018年4月23日 下午9:42:44  
*
 */
public class HuiHaiWebApplicationInitializer implements WebApplicationInitializer {

    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();

        //启动基础配置
        rootContext.register(HuiHaiConfig.class);

        //在此处加入其他配置类.

        servletContext.addListener(new ContextLoaderListener(rootContext));

        //加入编码过滤器
        servletContext.addFilter("encodingFilter", encodingFilter()).addMappingForUrlPatterns(null, false, "/*");
        //加入权限控制.
//        servletContext.addFilter("shiroFilter", shiroFilter()).addMappingForUrlPatterns(null, false, "/*");

        //Spring Requset 监听器
        servletContext.addListener(new RequestContextListener());

//        2、springmvc上下文
        rootContext.register(HuiHaiSpringMvcConfiguration.class);

        //3、DispatcherServlet
        DispatcherServlet dispatcherServlet = new DispatcherServlet(rootContext);
        ServletRegistration.Dynamic dynamic = servletContext.addServlet("dispatcherServlet", dispatcherServlet);
        dynamic.setLoadOnStartup(2);
        dynamic.addMapping("/");
        //4.添加

        System.out.println("Huix WebApplicationInitializer success.");
    }

    private Filter encodingFilter() {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        return filter;
    }
}
