package cn.apidev.api.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import cn.apidev.base.ApidevBaseService;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Action;
import com.jfinal.core.JFinal;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

/**
 * apidev接口
 * 
 * @author apidev.cn
 *
 */
public class ApiService extends ApidevBaseService {

	/**
	 * 表名称
	 */
	private static final String tableName = "apidev_api";

	private static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

	static {
		scheduleDailyTask();
	}

	private static void scheduleDailyTask() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		Date now = new Date();
		if (now.after(calendar.getTime())) {
			calendar.add(Calendar.DAY_OF_MONTH, 1);
		}

		long initialDelay = calendar.getTimeInMillis() - now.getTime();
		long period = 1 * 60 * 60; // 每小时执行一次

		scheduler.scheduleAtFixedRate(() -> {
			// 这里执行需要定时执行的任务
			deleteByTask();
		}, initialDelay, period, TimeUnit.SECONDS);
	}

	/**
	 * 删除被标记超过30天的数据
	 */
	public static void deleteByTask() {
		String sql = "DELETE FROM " + tableName + " WHERE del > 0 and update_time < NOW() - INTERVAL 30 DAY";
		int n = Db.delete(sql);
		System.out.println("删除被标记超过30天的数据: " + n + " 条");
	}

	@Override
	protected String getTableName() {
		return tableName;
	}

	/**
	 * 同步接口
	 * 
	 * @return
	 */
	public Ret sync() {
		int counter = 0;
		List<Record> saveList = new ArrayList<>();
		List<Record> updateList = new ArrayList<>();
		List<String> allActionKeys = JFinal.me().getAllActionKeys();
		for (String actionKey : allActionKeys) {

			String[] urlPara = new String[1];
			Action action = JFinal.me().getAction(actionKey, urlPara);
			if (action == null || actionKey.equals("")) {
				continue;
			}

			String controller = action.getControllerClass().getName();

			String sql = getQueryFullSql("action_key", "controller");
			Record api = Db.findFirst(sql, actionKey, controller);

			if (api == null) {
				api = new Record();
				api.set("id", UUID.randomUUID().toString().replace("-", ""));
				api.set("action_key", actionKey);
				api.set("controller", controller);
				api.set("parent_id", "-1");
				api.set("type", "api");
				api.set("create_time", new Date());
				api.set("update_time", new Date());
				saveList.add(api);
				counter++;
			}
		}

		if (saveList.size() > 0) {
			Db.batchSave(tableName, saveList, 500);
		}
		if (updateList.size() > 0) {
			Db.batchUpdate(tableName, updateList, 500);
		}

		if (counter == 0) {
			return ok("接口已经是最新状态，无需更新");
		} else {
			return ok("接口更新成功，共更新接口 : " + counter);
		}
	}

	/**
	 * 查询api接口
	 * 
	 * @param parentId
	 * @param type
	 * @return
	 */
	public List<Record> getTreeList(String parentId, String type) {
		String sql = "select id,type,request_mode,parent_id,action_key,LOWER(COALESCE(title, CONCAT(action_key, '（未调试）'))) AS title  from "
				+ tableName + " where del = 0 and parent_id=?";
		List<Record> list;
		if (type != null) {
			sql += " and type = ?";
			list = Db.find(sql + " order by type desc", parentId, type);
		} else {
			list = Db.find(sql + " order by type desc", parentId);
		}

		for (Record rd : list) {
			String id = rd.get("id");
			if ("-1".equals(parentId) || "-2".equals(parentId)) {
				rd.set("isopen", true);
			} else {
				rd.set("isopen", false);
			}
			List<Record> children = getTreeList(id, type);
			if (children.size() > 0)
				rd.set("children", children);
		}
		return list;
	}

	public Record findById(String id) {
		return Db.findById(tableName, id);
	}

	/**
	 * 分页查询api
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @param record     查询key:parentId,title,type
	 * @return
	 */
	public Page<Record> list(int pageNumber, int pageSize, Record record) {
		Record params = new Record();
		String parentId = record.getStr("parentId");
		if (parentId != null && !"-1".equals(parentId) && !"-2".equals(parentId)) {
			params.set("parent_id=", parentId);
		}
		params.set("type=", record.getStr("type"));
		params.set("title like", record.getStr("keyword"));
		params.set("del=", record.getStr("del"));
		Page<Record> page = getPage(pageNumber, pageSize, params,"order by update_time desc");
		page.getList().forEach(rd -> {
			String pid = rd.getStr("parent_id");
			rd.set("parentNames", getParantNames(pid));
			rd.set("parentIds", getParentIds(pid));
		});
		return page;
	}

	/**
	 * 查询父级名称
	 * 
	 * @param id
	 * @return
	 */
	public String getParantNames(String id) {
		if ("-1".equals(id)) {
			return "根目录";
		} else if ("-2".equals(id)) {
			return "快捷请求";
		}

		Record api = findById(id);
		if (api != null) {
			StringBuilder titleBuilder = new StringBuilder();

			// 从当前节点获取标题或动作键
			String title = api.getStr("title");
			if (title == null) {
				title = api.getStr("action_key");
			}

			// 递归获取父级名称
			String parentId = api.getStr("parent_id");
			String parentName = getParantNames(parentId);

			// 如果有父级名称，进行拼接
			if (parentName != null) {
				titleBuilder.append(parentName).append(" / ").append(title);
			} else {
				titleBuilder.append(title);
			}

			return titleBuilder.toString();
		}

		return null;
	}

	public String getParentIds(String id) {
		if ("-1".equals(id) || "-2".equals(id)) {
			return id;
		}

		Record api = findById(id);
		if (api != null) {
			StringBuilder titleBuilder = new StringBuilder();

			// 递归获取父级名称
			String parentId = api.getStr("parent_id");
			String parentIds = getParentIds(parentId);

			// 如果有父级名称，进行拼接
			if (parentIds != null) {
				titleBuilder.append(parentIds).append(" / ").append(id);
			} else {
				titleBuilder.append(id);
			}

			return titleBuilder.toString();
		}

		return null;
	}

	public Ret save(Record api) {
		if (api.getStr("id") == null)
			api.set("id", UUID.randomUUID().toString().replace("-", ""));
		api.set("create_time", new Date());
		api.set("update_time", new Date());
		api.set("del", 0);
		boolean b = Db.save(tableName, api);
		return b ? ok("保存成功") : fail("保存失败");
	}

	public Ret update(Record api) {
		if (api.getStr("id") == null)
			return fail("没有找到数据");
		api.set("update_time", new Date());
		if (api.getStr("parent_id") == null)
			api.remove("parent_id");
		if (api.getStr("title") == null)
			api.remove("title");
		api.remove("isopen","children");
		
		boolean b = Db.update(tableName, api);
		return b ? ok("修改成功") : fail("修改失败");
	}

	/**
	 * 复制
	 * 
	 * @param id
	 * @param parentId
	 * @return
	 */
	public Ret copy(String id, String parentId) {
		Record api = findById(id);
		if (api != null) {
			String newId = UUID.randomUUID().toString().replace("-", "");
			api.set("id", newId);
			if (parentId == null) {
				api.set("title", api.getStr("title") == null ? api.getStr("action_key") + "（未调试） Copy"
						: api.getStr("title") + " Copy");
			}
			api.set("parent_id", parentId == null ? api.getStr("parent_id") : parentId);
			save(api);
			List<Record> childrenList = getTreeList(id, null);
			if (childrenList.size() > 0) {
				for (Record rd : childrenList) {
					copy(rd.getStr("id"), newId);
				}
			}
		}

		return ok("复制成功");
	}

	/**
	 * 标记删除
	 * 
	 * @param id
	 * @return
	 */
	public Ret updateDel(String id) {
		Record api = new Record();
		Date date = new Date();
		api.set("id", id);
		api.set("update_time", date);
		api.set("del", 1);
		boolean b = Db.tx(() -> {
			try {
				Db.update(tableName, api);
				List<Record> childrenTreeList = getTreeList(id, null);
				// 获取列表
				List<Record> childrenList = new ArrayList<>();
				flattenTree(childrenTreeList, childrenList, false);

				// 根据id生成数组
				String[] idArray = childrenList.stream().map(r -> r.getStr("id")).toArray(String[]::new);
				// 输出结果
				for (String str : idArray) {
					Record apiChil = new Record();
					apiChil.set("id", str);
					apiChil.set("update_time", date);
					apiChil.set("del", 2);
					Db.update(tableName, apiChil);
				}
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}

		});

		return b ? ok("已移到回收站") : fail("数据异常");
	}

	/**
	 *  treeList 转 list
	 * @param tree
	 * @param list
	 * @param isApi
	 */
	private void flattenTree(List<Record> tree, List<Record> list, boolean isApi) {
		for (Record record : tree) {
			if (isApi) {
				if ("api".equals(record.get("type"))) {
					list.add(record);
				}
			} else {
				list.add(record);
			}
			if (record.get("children") != null)
				flattenTree(record.get("children"), list, isApi);
		}
	}

	/**
	 * 导出接口
	 * @param id
	 * @return
	 */
	public List<Record> getExportApiList(String id) {
		// 获取列表
		List<Record> childrenList = new ArrayList<>();
		Record api = findById(id);
		List<Record> childrenTreeList = getTreeList(id, null);
		if (!"-1".equals(id) && "api".equals(api.getStr("type"))) {
			childrenList.add(api);
		}
		flattenTree(childrenTreeList, childrenList, true);
		childrenList.forEach(rd -> {
			toApiJson(rd);
		});
		return childrenList;
	}

	/**
	 * 添加接口文档参数
	 * @param api
	 */
	public void toApiJson(Record api) {
		api.put("headersJson", JSONObject.parseArray(api.get("request_headers")));
		api.put("queryJson", JSONObject.parseArray(api.get("request_param_explain")));
		api.put("formDataJson", JSONObject.parseArray(api.get("request_form_data")));
		api.put("bodyJson", JSONObject.parseArray(api.get("request_body_explain")));
		api.put("successDataExplain", JSONObject.parseArray(api.get("success_demo_explain")));
		api.put("failuerDataExplain", JSONObject.parseArray(api.get("failuer_demo_explain")));
	}

	/**
	 * 恢复
	 * 
	 * @param id
	 * @return
	 */
	public Ret recover(String id) {
		if (id == null)
			return fail("id is null");

		Record api = findById(id);

		if (api == null)
			return fail("数据不存在");
		// 恢复数据状态：0：正常
		api.set("del", 0);
		String msg="恢复成功";
		String parentId = api.get("parent_id");
		if (!"-1".equals(parentId) && !"-2".equals(parentId)) {
			Record parentApi = findById(parentId);
			if ("demo".equals(api.get("type")) && (parentApi == null || (parentApi != null && parentApi.getInt("del") != 0))) {
				return fail("所属接口文档已不存在");
			} else if(parentApi==null || (parentApi != null && parentApi.getInt("del") != 0)){
				msg="原目录已删除，文件还原到根目录";
				String parentIds=getParentIds(id);
				if(parentIds.contains("-1"))
					api.set("parent_id", "-1");
				else
					api.set("parent_id", "-2");
			}
			update(api);
			recoverChildrenApi(id);
		} else {
			update(api);
			recoverChildrenApi(id);
		}
		return ok(msg);
	}
	
	/**
	 * 恢复下级数据
	 * @param parentId
	 */
	public void recoverChildrenApi(String parentId) {
		List<Record> treeList=getTreeList(parentId, null);
		List<Record> childrenList = new ArrayList<>();
		flattenTree(treeList, childrenList, false);
		childrenList.forEach(api ->{
			api.set("del", 0);
			update(api);
		});
	}
	
	/**
	 * 项目统计数据
	 * @param record
	 * @return
	 */
	public Record getProjectData(){
		Record result=new Record();
		String sql="select count(id) count,type from "+tableName+" where del=0 group by type";
		List<Record> list=Db.find(sql);
		list.forEach(record -> {
			result.set(record.get("type"), record.get("count"));
		});
		
		return result;
	}
	
	// 查询回收站数据
	public Record getRecycleList(Record record) {
		record.set("del", 1);
		Page<Record> page=list(1, 10000, record);
		List<Record> apiData=new ArrayList<>();
		List<Record> shortcutData=new ArrayList<>();
		
		page.getList().forEach(rd -> {
			// 判断数据是否可以恢复
			String parentId=rd.get("parent_id");
			int status=0;
			if(!"-1".equals(parentId)&&!"-2".equals(parentId)) {
				Record parent=findById(parentId);
				if("demo".equals(rd.get("type"))){
					if(parent==null|parent.getInt("del")!=0) {
						status = 1;
					}
				}
			}
			rd.set("status", status);
						
			// 计算两个日期之间的差值（毫秒）  
			Date updateTime=rd.getDate("update_time");
			Date now=new Date();			
	        long diffInMillis = now.getTime() - updateTime.getTime();  
	        // 将差值转换为天数  
	        long diffInDays = TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);  
	        rd.set("diffInDays", (30-diffInDays)+" 天");
	        // 区分接口和快捷请求
	        String parentIds=rd.get("parentIds");
			if(parentIds!=null && parentIds.contains("-1")) {
				apiData.add(rd);
			} else if(parentIds!=null && parentIds.contains("-2")){
				shortcutData.add(rd);
			}else{
				apiData.add(rd);
			}
						
		});
		Record result=new Record();
		result.set("apiData", apiData);
		result.set("shortcutData", shortcutData);
		return result;
	}
	
}
