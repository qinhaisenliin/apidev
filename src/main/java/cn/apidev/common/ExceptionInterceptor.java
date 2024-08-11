/**
 * 本项目采用《咖啡授权协议》，保护知识产权，就是在保护我们自己身处的行业。
 * 
 * Copyright (c) 2022-2023, jfinal.com
 */
package cn.apidev.common;

import org.apache.log4j.Logger;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.kit.Ret;

/**
 * 异常处理
 */
public class ExceptionInterceptor implements Interceptor {
	private static final Logger LOG = Logger.getLogger(ExceptionInterceptor.class);
	private static final String MSG="msg";//提示信息
	private static final String E="e";//警告信息
	

	@Override
	public void intercept(Invocation inv) {
		Controller ctrl = inv.getController();
		String methodNAme=inv.getMethodName();
		
		try {
			inv.invoke();
			String msg=ctrl.getAttr("msg");
			if(methodNAme.equals("save")){
				ctrl.setAttr(MSG, msg == null ? "数据保存成功" : msg);
			}else if(methodNAme.equals("update")){
				ctrl.setAttr(MSG, msg == null ? "数据修改成功" : msg);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage(), e);
			boolean b="XMLHttpRequest".equalsIgnoreCase(ctrl.getHeader("X-Requested-With"));
			if (b) {
				ctrl.renderJson(Ret.fail(e.getMessage()==null?"系统异常":e.getMessage()));
			} else {
				ctrl.setAttr(E, e);
				ctrl.renderError(ctrl.getResponse().getStatus());
			}
		}
	}

}
