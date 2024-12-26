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

package cn.apidev.api.ctrl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.aop.Before;
import com.jfinal.aop.Inject;
import com.jfinal.kit.JsonKit;
import com.jfinal.kit.Kv;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import cn.apidev.api.service.ApidevService;
import cn.apidev.base.ApidevBaseController;
import cn.apidev.interceptor.ApidevInterceptor;
import cn.apidev.interceptor.ShareInterceptor;

/**
 * APIDev接口
 * 
 * @author 琴海森林
 *
 */
@Before(ApidevInterceptor.class)
public class ApidevController extends ApidevBaseController{
	
	@Inject
	private ApidevService apiService;
	
	/**
	 * 主页
	 */
	public void index() {
		String apiId=getPara(0);
		if(StrKit.notBlank(apiId)) {
			Record api=apiService.findById(apiId);
			if(api!=null && api.get("type").equals("api")) {
				set("api",api);
				render("api.html");
			}else {
				render("404.html");
			}
			return;
		}
		render("index.html");
	}
	
		
	/**
	 * 查询列表
	 */
	public void list() {
		Record record = getAllParamsToRecord();
		int pageNumber = getParaToInt("pageNumber", 1);
		int pageSize = getParaToInt("pageSize", 15);
		Page<Record> page=apiService.list(pageNumber,pageSize,record);
		renderJson(ok(page));
	}
	
	/**
	 * 树菜单
	 */
	public void getTreeList() {
		String parentId = getPara("parent_id","1");
		String type = getPara("type");
		List<Record> treeList=apiService.getTreeList(parentId,type,true);
		List<Record> result=new ArrayList<>();
		Record root=apiService.findById(parentId);
		if(root==null) {
			root=new Record();
			root.set("title", "根目录");
			root.set("id", parentId);
			root.set("type", "menu");
		}
		root.set("isopen", true);
		root.set("children", treeList);
		result.add(root);
		renderJson(Ret.ok("data", result));
	}

	
	/**
	 * 同步接口
	 */
	public void sync() {
		Ret ret = apiService.sync();
		renderJson(ret);
	}
	
	/**
	 * 目录信息
	 */
	public void menu() {
		String id=getPara("id");
		Record menu=apiService.findById(id);
		if(menu!=null) {
			Record parentMenu=apiService.findById(menu.getStr("parent_id"));
			if(parentMenu!=null)
				menu.set("parent_name", parentMenu.getStr("title"));
		}
		set("is_edit_share",getPara("is_edit_share","0"));
		set("tree_type",getPara("tree_type","1"));
		set("menu",menu);
		render("menu.html");
	}
	
	/**
	 * 目录接口数据
	 */
	public void getMenuData() {
		Record param=getAllParamsToRecord();
		Record menuData = apiService.getMenuData(param);
		renderJson(ok(menuData));
	}
	
	public void add() {
		Record api = new Record();
		api.set("id", getPara("id",createToken()));
		set("api",api);
		render("detail.html");
	}
		
	/**
	 * 接口详情
	 */
	public void detail() {
		Record param = getAllParamsToRecord();
		Record api = apiService.getDetail(getPara("id"), param);		
		set("api",api);
		render("detail.html");
	}
	
	/**
	 * 接口文档
	 */
	public void doc() {
		Record api=apiService.getDoc(getPara("id"));
		String method = getRequest().getMethod();
		if(api.get("id")==null && method.equals("GET")) {
			render("404.html");
			return;
		}
		set("api",api);
		render("doc.html");
	}

	/**
	 * 保存数据
	 */
	public void save() {
		Record api=getAllParamsToRecord();
		Ret ret=apiService.save(api);
		renderJson(ret);
	}
	
	/**
	 * 修改数据
	 */
	public void update() {
		Record api=getAllParamsToRecord();
		if(api.get("id").equals(api.get("parent_id"))) {
			renderJson(fail("父级目录不能选择自己"));
			return;
		}
		Ret ret=apiService.update(api);
		renderJson(ret);
	}
	
	/**
	 * 修改状态
	 */
	public void updateStatus() {
		Ret ret=apiService.updateStatus(getPara("id"),getPara("status"));
		renderJson(ret);
	}
	/**
	 * 显示隐藏接口
	 */
	public void updateVisible() {
		Ret ret=apiService.updateVisible(getPara("id"),getPara("visible"));
		renderJson(ret);
	}
	
	/**
	 * 移动到
	 */
	public void moveTo() {
		Ret ret=apiService.moveTo(getPara("id"),getPara("parentId"));
		renderJson(ret);
	}
	
	/**
	 * 排序
	 */
	public void sort() {
		Record param = getAllParamsToRecord();
		Record menuData=apiService.getMenuData(param);
		List<Record> childrenMenuList=apiService.getTreeList(param.get("id"), "menu", true);
		set("menu",menuData.get("menu"));
		set("menu_data",menuData.get("menu_data"));
		set("children_menu_list",childrenMenuList);
		render("sort.html");
	}
	
	public void updateSort() {
		JSONObject body=getBodyJson();
		JSONArray ids = body.getJSONArray("ids");
		Ret ret=apiService.updateSort(ids);
		renderJson(ret);
	}
	
	/**
	 * 复制
	 */
	public void copy() {
		Ret ret = apiService.copy(getPara("id"), null);
		renderJson(ret);
	}
	
	/**
	 * 删除
	 */
	public void delete() {
		String id=getPara("id");
		Ret ret=fail();
		if(id!=null) {
			String[] str=id.split(",");
			for(String s:str) {
				ret=apiService.updateDel(s);
			}
		}
		renderJson(ret);
	}
	
	/**
	 * 分享页面
	 */
	@Before(ShareInterceptor.class)
	public void share() {
		String shareId = getPara(0);
		Record api=apiService.findByShareId(shareId);
		List<Record> treeList=apiService.getShareTreeList(api.get("id"), true);
		api.set("isopen", true);
		api.set("children", treeList);
		List<Record> result=new ArrayList<>();
		result.add(api);
		set("api",api);
		set("tree_data",JsonKit.toJson(result));
		render("share.html");
	}

	/**
	 * 下载文档
	 */
	public void download() {
		String id = getPara("id");
		String title = getPara("title","Apidev");
		String ids = getPara("selectedIds");
		List<Record> list;
		if(ids!=null) {
			list=apiService.getExportSelectApiList(ids);
		}else {
			list = apiService.getExportApiList(id);
		}
		
		set("api_list", list);

		// 文档模板渲染
		String wordString = renderToString("download.html", Kv.by("api_list", list));

		// temp下载临时文件存放目录
		String fileName = "temp";
		if (title != null)
			title = title.replace("/", "_");
		File path=new File(PathKit.getWebRootPath() + getViewPath() + fileName);
		if(!path.exists()){
			path.mkdirs();
			fileName+="/Apidev";
		}
		File file = new File(PathKit.getWebRootPath() + getViewPath() + fileName + ".html");
		writeToFile(file, wordString);

		renderFile(file,title+".html");
	}
	
	/**
	 * 下载分享接口
	 */
	public void downloadShareApi() {
		String id = getPara("id");
		String title = getPara("title","Apidev");

		List<Record> list=apiService.getExportShareApiList(id);
		set("api_list", list);

		// 文档模板渲染
		String wordString = renderToString("download.html", Kv.by("api_list", list));

		// temp下载临时文件存放目录
		String fileName = "temp/Apidev";
		if (title != null)
			title = title.replace("/", "_");
		File path=new File(PathKit.getWebRootPath() + getViewPath() + fileName);
		if(!path.exists()){
			path.mkdirs();
			fileName+="/Apidev";
		}
		File file = new File(PathKit.getWebRootPath() + getViewPath() + fileName + ".html");
		writeToFile(file, wordString);

		renderFile(file,title+".html");
	}
	
	/**
	 * 导出json
	 */
	public void exportJson() {  
		String parentId=getPara("parentId");
		Record record=apiService.findById(parentId);
		if(record==null || record.getInt("del")!=0) {
			renderJson(fail("无数据"));
			return;
		}

		List<Record> allList=apiService.getExportAllApiList(parentId);
		
        // 准备JSON数据  
        String jsonData =JsonKit.toJson(ok(allList));

        // temp下载临时文件存放目录
 		String fileName = "temp";
 		File path=new File(PathKit.getWebRootPath() + getViewPath() + fileName);
 		if(!path.exists()){
 			path.mkdirs();
 		}
 		File file = new File(PathKit.getWebRootPath() + getViewPath() + fileName + ".txt");
 		writeToFile(file, jsonData);
 		
 		String title = "apidev-"+record.getStr("title")+".txt";
 		renderFile(file,title);
    }  
	
	/**
	 * 项目概况页面
	 */
	public void project(){
		Record record=apiService.getProjectData();
		set("project_data",record);
		render("project.html");
	}
	

	/**
	 * 回收站页面
	 */
	public void recycle() {
		render("recycle.html");
	}
	
	/**
	 * 回收站表格数据
	 */
	public void getRecycleList() {
		Record record=apiService.getRecycleList(getAllParamsToRecord());
		renderJson(ok(record));
	}
	
	/**
	 * 恢复数据
	 */
	public void recover() {
		String id=getPara("id");
		Ret ret=fail();
		if(id!=null) {
			String[] str=id.split(",");
			for(String s:str) {
				ret=apiService.recover(s);
			}
		}
		renderJson(ret);
	}
	
	/**
	 * 发送请求
	 */
	public void sendRequest() {
		JSONObject body = getBodyJson();
		String str = body.getString("cookies");
		str += getCookies();
		body.put("cookies", str);
		String result = apiService.sendRequest(body);
		renderJson(result);
	}
	
	/**
	 * 保存接口数据
	 */
	public void saveApi() {
		JSONObject body = getBodyJson();
		Ret ret = apiService.saveApi(body);
		renderJson(ret);
	}
			
	// 验证码
	public void captcha() {
		renderCaptcha();
	}
	
	public void login() {
		render("login.html");
	}
}
