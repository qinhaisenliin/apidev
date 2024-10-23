package cn.apidev.api.ctrl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.aop.Inject;
import com.jfinal.core.Path;
import com.jfinal.kit.Kv;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

import cn.apidev.api.service.ApiService;
import cn.apidev.base.ApidevBaseController;

/**
 * aipdev接口
 * @author apidev.cn
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
		List<Record> treeList=apiService.getTreeList(parentId,type);
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
		// -1和js方法名拼接有问题，因此此处转化一下根目录id
//		if("1".equals(id)) {
//			id="1";
//		}
		Record menu=apiService.findById(id);
		if(menu!=null) {
			Record parentMenu=apiService.findById(menu.getStr("parent_id"));
			if(parentMenu!=null)
				menu.set("parentName", parentMenu.getStr("title"));
		}
		// -1和js方法名拼接有问题，因此此处转化一下根目录id
//		if(menu.get("id").equals("1"))
//			menu.set("id", "1");
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
		
	}
	
	/**
	 * 运行接口
	 */
	public void run() {
		
	}
	
	/**
	 * 接口信息
	 */
	public void getById() {
		String id=getPara("id");
		if(id==null) {
			renderJson(fail("id is null"));
			return;
		}
		Record api=apiService.findById(id);
		renderJson(api !=null ? ok(api) : fail("没有找到数据"));
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
	
	public void moveTo() {
		Ret ret=apiService.moveTo(getPara("id"),getPara("parentId"));
		renderJson(ret);
	}
	
	public void copy() {
		Ret ret = apiService.copy(getPara("id"), null);
		renderJson(ret);
	}
	
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
	
	public void share() {
		String id = getPara(0);
		if("1".equals(id))
			id="-1";
		List<Record> list = apiService.getExportApiList(id);
		
		set("apiList", list);

		// 文档模板渲染
		String wordString = renderToString("download.html", Kv.by("apiList", list));

		writeToHtml("share.html", wordString);
		render("share.html");
	}

	/**
	 * 下载文档
	 */
	public void download() {
		String id = getPara("id");
		String title = getPara("title");
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
			fileName += "_" + title.replace("/", "_");
		File path=new File(PathKit.getWebRootPath() + getViewPath() + fileName);
		if(!path.exists()){
			path.mkdirs();
			fileName+="/Apidev";
		}
		File file = new File(PathKit.getWebRootPath() + getViewPath() + fileName + ".html");
		writeToFile(file, wordString);

		renderFile(file);
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
	 * 项目统计数据
	 */
//	public void getProjectData(){
//		Record record=apiService.getProjectData();
//		renderJson(ok(record));
//	}

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
}
