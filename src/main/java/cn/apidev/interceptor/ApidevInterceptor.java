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

import com.jfinal.aop.Inject;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.core.JFinal;

import cn.apidev.api.service.ApidevService;
import cn.apidev.kit.ApidevKit;

/**
 * APIDev 全局拦截器
 * 
 * @author 琴海森林
 *
 */
public class ApidevInterceptor implements Interceptor {
	@Inject
	ApidevService apidevService;

	@Override
	public void intercept(Invocation inv) {
		Controller ctr = inv.getController();

		if (ApidevKit.isStop()) {
			ctr.render("404.html");
			return;
		} else {
			ctr.set("apiActionKey", ApidevKit.getActionKey());
			ctr.set("path", JFinal.me().getContextPath());
			inv.invoke();
		}
	}

}
