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

package cn.apidev.interceptor;

import java.util.Date;

import com.jfinal.aop.Inject;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Record;

import cn.apidev.api.service.ApidevService;
import cn.apidev.kit.ApidevKit;

/**
 * APIDev分享地址拦截器
 * 
 * @author 琴海森林
 *
 */
public class ShareInterceptor implements Interceptor {

	@Inject
	ApidevService apiService;

	@Override
	public void intercept(Invocation inv) {
		Controller ctr = inv.getController();
		String shareId = ctr.getPara(0);
		String captcha = ctr.getPara("captcha");
		ctr.set("shareId", shareId);
		if (StrKit.isBlank(shareId)) {
			ctr.renderJson(Ret.fail("缺失参数！"));
			return;
		}
		
		Record api = apiService.findByShareId(shareId);
		
		if (api != null) {
			ctr.set("actionKey", ApidevKit.getActionKey() + "/share/" + shareId);
			// 验证有效期
			Date expireTime = api.get("expiret_time");
			if (expireTime != null) {
				long l = expireTime.getTime() - new Date().getTime();
				if (l <= 0) {
					ctr.renderJson(Ret.fail("URL授权时间已到期！"));
					return;
				}
			}
			// 验证密码
			String password = ctr.getSessionAttr(shareId);
			if (api.get("password") != null) {

				String pass = ctr.getPara("password");
				if (captcha != null && ctr.validateCaptcha("captcha") == false && password == null) {
					ctr.set("msg", "验证码不正确");
					ctr.set("password", pass);
					ctr.render("login.html");
					return;
				}
				if (pass != null) {
					if (api.get("password").equals(pass)) {
						ctr.setSessionAttr(shareId, pass);
						password = pass;
					} else {
						ctr.set("msg", "密码错误");
						ctr.set("password", pass);
						ctr.render("login.html");
						return;
					}
				}
				if (!api.get("password").equals(password)) {
					ctr.set("msg", "请输入访问密码");
					ctr.render("login.html");
					return;
				}
			}
		} else {
			ctr.renderJson(Ret.fail("分享链接无效"));
			return;
		}

		inv.invoke();

	}

}
