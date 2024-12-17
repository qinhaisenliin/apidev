/**
 * Copyright 2024-2025 琴海森林(qinhaisenlin@163.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
 * @author 琴海森林
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
