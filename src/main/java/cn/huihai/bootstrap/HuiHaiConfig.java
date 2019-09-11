package cn.huihai.bootstrap;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
* @ClassName: HuixConfig  
* @Description: TODO
* @author shikl
* @date 2018年4月24日 上午2:13:05  
*
 */
@Configuration
@ComponentScan(
        basePackages = {
                "cn.huihai.*",
                "cn.huihai.*.*",
                "cn.huihai.*.*.*"
        }
)
public class HuiHaiConfig {
}
