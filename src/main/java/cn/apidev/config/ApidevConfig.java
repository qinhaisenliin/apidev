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

package cn.apidev.config;

import com.jfinal.aop.Interceptor;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.json.FastJsonFactory;
import com.jfinal.template.Engine;

import cn.apidev.kit.ApidevKit;


/**
 * APIDev配置中心，该类主要是在SpringBoot项目中使用，JFinal项目可以
 * 
 * 单独使用ApidevKit中的ApidevKit.configRoute(me)和ApidevKit.configEngine(me);
 * 
 * @author 琴海森林
 */
public class ApidevConfig extends JFinalConfig {
	private Interceptor interceptor;
	
	public ApidevConfig() {

	}
	
	public ApidevConfig(Interceptor interceptor) {
		this.interceptor = interceptor;
	}
	
	
	@Override
	public void configConstant(Constants me) {
		me.setDevMode(true);
		me.setJsonFactory(FastJsonFactory.me());
		//开启依赖注入
		me.setInjectDependency(true);
	}

	@Override
	public void configRoute(Routes me) {
		// 添加前台路由
		if(interceptor!=null) {
			ApidevKit.configRoute(me,interceptor);
		}else {
			ApidevKit.configRoute(me);
		}
	}

	@Override
	public void configEngine(Engine me) {
		// 配置jar资源目录
		ApidevKit.configEngine(me);
	}

	
	@Override
	public void configPlugin(Plugins me) {
		// 配置 JDBC 连接池插件
		ApidevKit.configPlugin(me);
	}

	@Override
	public void configInterceptor(Interceptors me) {

	}

	@Override
	public void configHandler(Handlers me) {

	}

	// 服务启动时回调 onStart()
	public void onStart() {
		ApidevKit.onStart();
	}
	
	// 服务关闭时回调 onStop()
	public void onStop() {}
}
