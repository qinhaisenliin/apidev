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

package cn.apidev.base;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import javax.servlet.http.Cookie;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;
import com.jfinal.core.NotAction;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Record;

/**
 * APIDev 基础类
 * 
 * @author 琴海森林
 *
 */
public class ApidevBaseController extends Controller {
	/**
	 * 返回接口成功数据
	 * 
	 * @param data 接口数据
	 * @return Ret
	 */
	@NotAction
	public Ret ok(Object data) {
		return Ret.ok("成功").set("data", data);
	}

	@NotAction
	public Ret ok(String msg) {
		return Ret.ok(msg);
	}

	@NotAction
	public Ret ok() {
		return Ret.ok("成功");
	}

	@NotAction
	public Ret fail() {
		return Ret.fail("失败");
	}

	/**
	 * 返回接口失败信息
	 * 
	 * @param msg 提示信息
	 * @return Ret
	 */
	@NotAction
	public Ret fail(String msg) {
		return Ret.fail(msg);
	}

	/**
	 * 获取http请求body参数
	 * 
	 * @return JSONObject
	 */
	@NotAction
	public JSONObject getBodyJson() {
		String body = getRawData();
		JSONObject bodyJson = (JSONObject) JSONObject.parseObject(body);
		return bodyJson;
	}

	/**
	 * 获取请求参数,转化为JFinal的Record对象 , 本方法不接受body传参，body中的参数请使用getBodyJson()
	 * 
	 * @return Record
	 */
	@NotAction
	public Record getAllParamsToRecord() {
		@SuppressWarnings("unchecked")
		Record result = new Record().setColumns(getKv());
		return result;
	}

	/**
	 * 把内容写入文件
	 * 
	 * @param file 要写入的文件
	 * @param str  要写入的内容
	 */
	@NotAction
	public void writeToFile(File file, String str) {
		try {
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
			writer.write(str);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@NotAction
	public void writeToHtml(String fileName, String text) {
		File file = new File(PathKit.getWebRootPath() + getViewPath() + "/" + fileName);
		try {
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
			writer.write(text);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@NotAction
	public String getCookies() {
		String str = "";
		Cookie[] cookies = getRequest().getCookies();
		for (int i = 0; i < cookies.length; i++) {
			Cookie cookie = cookies[i];
			String s = cookie.getName() + "=" + cookie.getValue() + ";";
			str += s;
		}
		;
		return str;
	}
}
