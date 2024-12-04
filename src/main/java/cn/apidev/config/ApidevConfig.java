
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
 * Apidev配置中心，该类主要是在SpringBoot项目中使用，JFinal项目可以
 * <br/>
 * 单独使用ApidevKit中的ApidevKit.configRoute(me)和ApidevKit.configEngine(me);
 *
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
