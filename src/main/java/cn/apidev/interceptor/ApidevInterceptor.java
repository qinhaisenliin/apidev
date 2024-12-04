package cn.apidev.interceptor;

import com.jfinal.aop.Inject;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.core.JFinal;

import cn.apidev.api.service.ApidevService;
import cn.apidev.kit.ApidevKit;

/**
 * Apidev 全局拦截器
 * @author apidev.cn
 *
 */
public class ApidevInterceptor implements Interceptor{
	@Inject
	ApidevService apidevService;
	
	@Override
	public void intercept(Invocation inv) {
		Controller ctr = inv.getController();

		if(ApidevKit.isStop()) {
			ctr.render("404.html");
			return;
		}else {
			ctr.set("apiActionKey", ApidevKit.getActionKey());
			ctr.set("path", JFinal.me().getContextPath());
			inv.invoke();
		}
	}


}
