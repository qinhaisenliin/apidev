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
 * Apidev 基础类
 * @author apidev.cn
 *
 */
public class ApidevBaseController extends Controller{
	/**
	 * 返回接口成功数据
	 * @param data
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
	 */
	@NotAction
	public Ret fail(String msg) {
		return Ret.fail(msg);
	}
	
	/**
	 * 获取http请求body参数
	 * @return
	 */
	@NotAction
	public JSONObject getBodyJson(){
		String body = getRawData();      
        JSONObject bodyJson = (JSONObject)JSONObject.parseObject(body);
        return bodyJson;
	}
	
	/** 
	 * 获取请求参数,转化为JFinal的Record对象 ,<br/>
	 * 本方法不接受body传参，body中的参数请使用getBodyJson()
	 * @return
	 */
	@NotAction
    public Record getAllParamsToRecord(){
    	@SuppressWarnings("unchecked")
		Record result=new Record().setColumns(getKv());
        return result;
    }
	
	/**
	 * 把内容写入文件
	 * @param file
	 * @param str
	 */
	@NotAction
	public void writeToFile(File file,String str) {
		try {
			BufferedWriter writer = new BufferedWriter (new OutputStreamWriter(new FileOutputStream(file),"UTF-8"));		
			writer.write(str);				
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@NotAction
	public void writeToHtml(String fileName,String text) {
		File file = new File(PathKit.getWebRootPath() + getViewPath()+"/"+fileName);
		try {
			BufferedWriter writer = new BufferedWriter (new OutputStreamWriter(new FileOutputStream(file),"UTF-8"));		
			writer.write(text);				
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@NotAction
	public String getCookies(){
		String str="";
		Cookie[] cookies=getRequest().getCookies();
    	for(int i=0;i<cookies.length;i++) {
    		Cookie cookie=cookies[i];
    		String s=cookie.getName()+"="+cookie.getValue()+";";
    		str+=s;
    	};
    	return str;
	}
}
