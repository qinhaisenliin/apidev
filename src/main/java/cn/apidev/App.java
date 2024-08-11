/**
 * 本项目采用《咖啡授权协议》，保护知识产权，就是在保护我们自己身处的行业。
 * 
 * Copyright (c) 2022-2023, jfinal.com
 */

package cn.apidev;

import com.jfinal.server.undertow.UndertowServer;

import cn.apidev.common.ApidevConfig;

/**
 * 启动入口
 */
public class App {
	public static void main(String[] args) {
		UndertowServer.start(ApidevConfig.class);
	}
}





