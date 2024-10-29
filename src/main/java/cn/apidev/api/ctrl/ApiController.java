package cn.apidev.api.ctrl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.aop.Before;
import com.jfinal.aop.Inject;
import com.jfinal.core.Path;
import com.jfinal.kit.JsonKit;
import com.jfinal.kit.Kv;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

import cn.apidev.api.service.ApiService;
import cn.apidev.base.ApidevBaseController;
import cn.apidev.common.ShareInterceptor;

/**
 * Aipdev接口
 * @author http://apidev.cn
 *
 */
@Path("/apidev/api")
public class ApiController extends ApidevBaseController{
	
	@Inject
	ApiService apiService;
	
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
				redirect("/");
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
		String parentId = getPara("parentId","1");
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
				menu.set("parentName", parentMenu.getStr("title"));
		}
		set("isEditShare",getPara("isEditShare","0"));
		set("treeType",getPara("treeType","1"));
		set("menu",menu);
		render("menu.html");
	}
	
	/**
	 * 目录接口数据
	 */
	public void getMenuData() {
		String id=getPara("id");
		String keyword=getPara("keyword");
		String treeType = getPara("treeType","1");
		Record menuInfo=apiService.findById(id);
		if(menuInfo!=null) {
			Record parentMenu=apiService.findById(menuInfo.getStr("parent_id"));
			if(parentMenu!=null)
				menuInfo.set("parentName", parentMenu.getStr("title"));
		}

		List<Record> menuApiData=apiService.getMenuApiList(id,keyword,treeType);
		Record menuData=new Record();
		menuData.set("menuInfo", menuInfo);
		menuData.set("menuApiData", menuApiData);
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
		Record api=apiService.findById(getPara("id"));
		if(api!=null) {
			apiService.toApiJson(api);
			Record parent=apiService.findById(api.get("parent_id"));
			if(parent!=null) {
				String parentName = parent.get("title")==null?parent.get("action_key") : parent.get("title");
				api.set("parentName", parentName);
			}
			if(api.get("title")==null) {
				api.set("title", api.get("action_key"));
				api.set("request_url", api.get("action_key"));
			}
		}else {
			api = new Record();
			String title = getPara("title");
			api.set("parent_id", getPara("parentId"));
			api.set("id", getPara("id"));
			api.set("type", getPara("type"));
			api.set("title", title.substring(title.length()-4));
			api.set("isAdd", true);
			if(api.get("type").equals("demo")) {
				Record parent=apiService.findById(api.get("parent_id"));
				if(parent!=null) {
					api.set("request_mode", parent.getStr("request_mode"));
					String parentName = parent.get("title")==null?parent.get("action_key") : parent.get("title");
					api.set("parentName", parentName);
					api.set("action_key", parent.get("action_key"));
				}
			}
		}
		String treeType=api.getStr("type").equals("link") ? "2" : "1";
		api.set("treeType",treeType);
		
		set("api",api);
		render("detail.html");
	}
	
	/**
	 * 接口文档
	 */
	public void doc() {
		Record api=apiService.findById(getPara("id"));
		if(api!=null) {
			apiService.toApiJson(api);
		}else {
			api=new Record();
			api.set("id", getPara("id"));
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
		set("treeData",JsonKit.toJson(result));
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
		
		set("apiList", list);

		// 文档模板渲染
		String wordString = renderToString("download.html", Kv.by("apiList", list));

		// temp下载临时文件存放目录
		String fileName = "temp/Apidev";
		if (title != null)
			title += title.replace("/", "_");
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
		set("apiList", list);

		// 文档模板渲染
		String wordString = renderToString("download.html", Kv.by("apiList", list));

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
	 * 项目概况页面
	 */
	public void project(){
		Record record=apiService.getProjectData();
		set("projectData",record);
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
		ApiService.deleteByTask();
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
		
	/**
	 * 调试页面
	 */
	public void debug() {
		Record api = apiService.findById(getPara("id"));
		set("api",api);
		render("debug.html");
	}
	
	// 验证码
	public void captcha() {
		renderCaptcha();
	}
}
