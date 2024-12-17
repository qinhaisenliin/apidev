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

package cn.apidev.kit;

import com.jfinal.aop.Interceptor;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinalFilter;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.CaseInsensitiveContainerFactory;
import com.jfinal.plugin.activerecord.dialect.H2Dialect;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.activerecord.dialect.OracleDialect;
import com.jfinal.plugin.activerecord.dialect.PostgreSqlDialect;
import com.jfinal.plugin.activerecord.dialect.SqlServerDialect;
import com.jfinal.plugin.activerecord.dialect.Sqlite3Dialect;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.template.Engine;
import cn.apidev.api.ctrl.ApidevController;
import cn.apidev.config.ApidevConfig;

/**
 * APIDev配置工具类
 * 
 * @author 琴海森林
 *
 */
public class ApidevKit {

	/**
	 * APIDev视图路径
	 */
	private static final String viewPath = "/apidev";
	
	private static final String basePackage = "cn.apidev";
	
	/**
	 * JFinal 项目的配置文件
	 */
	private static final String apidevConfig = "apidev-config.txt";

	/**
	 * APIDev version
	 */
	public static final String version = "1.0.3";
	
	/**
	 * 标记SpringBoot项目
	 */
	private static boolean isSpringBoot = false;
	
	/**
	 * 数据库字段是否转小写
	 */
	private static Boolean toLowerCase = null;
		
	private static Prop p;
	
	/**
	 * 数据库配置
	 */
	private static String url;
	private static String username;
	private static String password;
	
	/**
	 * 获取配置文件参数
	 * @return Prop
	 */
	private static Prop getProp() {
		if (p == null) {
			// 加载从左到右第一个被找到的配置文件
			p = PropKit.appendIfExists(apidevConfig);
		}
		return p;
	}
			
	/**
	 * 配置数据库信息
	 * @param url url
	 * @param username 用户名
	 * @param password 密码
	 */
	public static void configDb(String url,String username,String password) {
		ApidevKit.url = url;
		ApidevKit.username = username;
		ApidevKit.password = password;
	}
				
	/**
	 * APIDev控制器路由
	 * @return String
	 */
	public static String getActionKey() {
		String actionKey = getProp().get("apidev.actionKey", viewPath);
		if (StrKit.isBlank(actionKey))
			return viewPath;
		return actionKey;
	}
	
	/**
	 * APIDev服务是否关闭
	 * @return boolean
	 */
	public static boolean isStop() {
		return getProp().getBoolean("apidev.stop", false);
	}
	
	/**
	 * 是否SpringBoot项目
	 * @return boolean
	 */
	public static boolean isSpringBoot() {
		return isSpringBoot;
	}
		
	/**
	 * 配置APIDev路由（必须配置）
	 * @param me Routes
	 * @return Routes
	 */
	public static Routes configRoute(Routes me) {
		return configRoute(me,null);
	}
	
	/**
	 * 配置APIDev路由和路由拦截器
	 * @param me Routes
	 * @param interceptor 拦截器
	 * @return Routes
	 */
	public static Routes configRoute(Routes me,Interceptor interceptor) {
		// 添加Apidev前端路由
		return me.add(new Routes() {
			public void config() {
				// 配置视图的基础路径
				this.setBaseViewPath("/");

				// 扫描前台路由
				this.scan(basePackage);
				
				// 配置自定义拦截器
				if(interceptor!=null)
					this.addInterceptor(interceptor);
				
				// 配置路由
				this.add(getActionKey(), ApidevController.class, viewPath);
			}
		});
	}
	
	/**
	 * 配置Engine（必须配置）
	 * @param me Engine
	 * @return Engine
	 */
	public static Engine configEngine(Engine me) {
		// 配置jar资源目录
		me.setBaseTemplatePath("webapp");
	    me.setToClassPathSourceFactory();
	    return me;
	}
	
	/**
	 * 获取DruidPlugin,主要是SpringBoot项目中使用；
	 * 
	 * JFinal项目可以忽略该配置，默认就是项目中的主数据库。
	 * @return DruidPlugin
	 */
	private static DruidPlugin getDruidPlugin() {
		// 为空则从自定义配置文件apidev-config.txt配置文件读取
		if(url==null) {
			url=getProp().get("apidev.db.url");
			username = getProp().get("apidev.db.username");
			password = getProp().get("apidev.db.password");
		}
		
		if(url==null) {
			throw new IllegalArgumentException("没有配置数据库信息");
		}
		
		return new DruidPlugin(url, username, password);
	}
	
	/**
	 * 配置数据库插件
	 * SpringBoot项目中使用,
	 * JFinal可以忽略，默认就是项目中的主数据库
	 * @param me Plugins
	 * @return Plugins
	 */
	public static Plugins configPlugin(Plugins me) {
		// 配置 JDBC 连接池插件
		DruidPlugin druidPlugin = getDruidPlugin();
		me.add(druidPlugin);
		
		// 配置 ActiveRecordPlugin
		ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
		
		if(toLowerCase!=null) {
			// 设置大小不敏感,oracle、dameng登表字段是大写的需要设置为true
			arp.setContainerFactory(new CaseInsensitiveContainerFactory(toLowerCase));
		}
		// 配置数据库方言
		if(url.startsWith("jdbc:oracle")||url.startsWith("jdbc:dm")) {
			toLowerCase = true;
			arp.setDialect(new OracleDialect());
		}else if(url.startsWith("jdbc:sqlite")) {
			arp.setDialect(new Sqlite3Dialect());
		} else if(url.startsWith("jdbc:postgresql")) {
			arp.setDialect(new PostgreSqlDialect());
		} else if(url.startsWith("jdbc:microsoft")) {
			arp.setDialect(new SqlServerDialect());
		} else if(url.startsWith("jdbc:h2")) {
			arp.setDialect(new H2Dialect());
		} else if(url.startsWith("jdbc:mysql")) {
			arp.setDialect(new MysqlDialect());
		}
		// 显示sql
		arp.setShowSql(true);
		me.add(arp);
		return me;
	}
		
	/**
	 * 获取JFinalFilter
	 * @return JFinalFilter
	 */
	public static JFinalFilter getJFinalFilter() {
		isSpringBoot = true;
		return new JFinalFilter(new ApidevConfig());
	}
	
	public static JFinalFilter getJFinalFilter(Interceptor interceptor) {
		isSpringBoot = true;
		return new JFinalFilter(new ApidevConfig(interceptor));
	}
	
	/**
	 * 启动完成时调用
	 */
	public static void onStart() {
		printApidevInfo();
	}
			
	/**
	 * 打印APIDev配置信息
	 */
	private static void printApidevInfo() {
		String msg = "APIDev Server Info:\n";
		msg += " > Start:     " + !isStop();
		msg += "\n";
		msg += " > Version:   " + version;
		msg += "\n";
		msg += " > ActionKey: " + getActionKey();
		msg += "\n";
		System.out.println(msg);
	}
	
}
