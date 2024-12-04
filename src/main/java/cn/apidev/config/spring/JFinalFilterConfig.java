package cn.apidev.config.spring;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;  
import org.springframework.context.annotation.Bean;  
import org.springframework.context.annotation.Configuration;

import com.jfinal.core.JFinalFilter;

import cn.apidev.kit.ApidevKit; 

/**
 * 在SpringBoot项目中使用
 *
 */
@Configuration
public class JFinalFilterConfig {
	
	@Value("${spring.datasource.url}")
	private String url;
	@Value("${spring.datasource.username}")
	private String username;
	@Value("${spring.datasource.password}")
	private String password;
	
	 @Bean  
    public FilterRegistrationBean<JFinalFilter> jfinalFilter() {  
        FilterRegistrationBean<JFinalFilter> registrationBean = new FilterRegistrationBean<>();  
        ApidevKit.configDb(url, username, password);
        registrationBean.setFilter(ApidevKit.getJFinalFilter());  
        registrationBean.addUrlPatterns(ApidevKit.getActionKey()+"/*"); // 设置过滤路径  
        return registrationBean;  
    }  
}
