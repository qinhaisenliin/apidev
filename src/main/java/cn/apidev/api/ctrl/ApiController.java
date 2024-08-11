package cn.apidev.api.ctrl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.jfinal.aop.Inject;
import com.jfinal.core.Path;
import com.jfinal.kit.JsonKit;
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
	 * 新建接口、快捷请求、接口用例
	 */
	public void add() {
		set("type",getPara("type","api"));
		set("parentId",getPara("parentId","-1"));
		render("add.html");
	}
	
	/**
	 * 新建目录
	 */
	public void addMenu() {
		set("parentId",getPara("parentId","-1"));
		render("menu.html");
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
		String parentId = getPara("parentId","-1");
		String type = getPara("type");
		List<Record> treeList=apiService.getTreeList(parentId,type);
		List<Record> result=new ArrayList<>();
		Record root=new Record();
		root.set("id", parentId);
		root.set("title", "根目录");
		root.set("type", "menu");
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
	 * 接口信息
	 */
	public void info() {
		set("apiId",getPara(0));
		render("info.html");
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
		String id=getPara(0);
		if(id==null) {
			renderJson(fail("id is null"));
			return;
		}
		Record api=apiService.findById(id);
		renderJson(api !=null ? ok(api) : fail("没有找到数据"));
	}
	
	
	/**
	 * 保存接口
	 */
	public void save() {
		Record api=getAllParamsToRecord();
		Ret ret=apiService.save(api);
		renderJson(ret);
	}
	
	public void update() {
		Record api=getAllParamsToRecord();
		Ret ret=apiService.update(api);
		renderJson(ret);
	}
	
	public void copy() {
		Ret ret = apiService.copy(getPara("id"), null);
		renderJson(ret);
	}
	
	public void delete() {
		Ret ret = apiService.updateDel(getPara("id"));
		renderJson(ret);
	}
	

	public void download() {
		String id = getPara("id");
		String title = getPara("title");
		List<Record> list = apiService.getExportApiList(id);
		
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
	public void getProjectData(){
		Record record=apiService.getProjectData();
		renderJson(ok(record));
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
	
	public void debug() {
		Record api = apiService.findById(getPara("id"));
		set("api",api);
		render("debug.html");
	}
}
