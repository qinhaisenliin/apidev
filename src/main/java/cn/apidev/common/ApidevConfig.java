/**
 * 本项目采用《咖啡授权协议》，保护知识产权，就是在保护我们自己身处的行业。
 * 
 * Copyright (c) 2022-2023, jfinal.com
 */

package cn.apidev.common;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;
import com.jfinal.json.FastJsonFactory;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.jfinal.template.Engine;


/**
 * 配置中心
 *
 */
public class ApidevConfig extends JFinalConfig {
	
	static Prop p;
	
	static void loadConfig() {
		if (p == null) {
			// 加载从左到右第一个被找到的配置文件
			p = PropKit.useFirstFound("app-config-pro.txt", "app-config-dev.txt");
		}
	}
	
	@Override
	public void configConstant(Constants me) {
		loadConfig();
		me.setDevMode(p.getBoolean("devMode", false));
		me.setJsonFactory(FastJsonFactory.me());
		//开启依赖注入
		me.setInjectDependency(true);
	}

	@Override
	public void configRoute(Routes me) {
		// 添加前台路由
		me.add(new Routes() {
			public void config() {
				// 配置视图的基础路径
				this.setBaseViewPath("/");
				
				// 扫描前台路由
				this.scan("cn.apidev");			
			}
		});
	}

	@Override
	public void configEngine(Engine me) {
		// devMode 为 true 时支持模板文件热加载
		me.setDevMode(p.getBoolean("engineDevMode", false));
		me.addSharedObject("path", JFinal.me().getContextPath());
		me.addSharedFunction("/apidev/common/_layout.html");
	}
	/**
	 * 抽取成独立的方法，便于 _Generator 中重用该方法，减少代码冗余
	 */
	public static DruidPlugin getDruidPlugin() {
		loadConfig();
		return new DruidPlugin(p.get("jdbcUrl"), p.get("user"), p.get("password").trim());
	}
	
	@Override
	public void configPlugin(Plugins me) {
		// 配置 JDBC 连接池插件
		DruidPlugin druidPlugin = getDruidPlugin();
		me.add(druidPlugin);
		
		// 配置 ActiveRecordPlugin
		ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
		//处理SQL参数
		me.add(arp);
		
		// 配置缓存插件
		me.add(new EhCachePlugin());
	}

	@Override
	public void configInterceptor(Interceptors me) {
		me.addGlobalActionInterceptor(new ExceptionInterceptor());
	}

	@Override
	public void configHandler(Handlers me) {

	}

	// 服务启动时回调 onStart()
	public void onStart() {


	}
	
	// 服务关闭时回调 onStop()
	public void onStop() {}
}
