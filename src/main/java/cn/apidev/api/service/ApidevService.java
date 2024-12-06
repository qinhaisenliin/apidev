package cn.apidev.api.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import cn.apidev.base.ApidevBaseService;
import cn.apidev.kit.ApidevKit;
import cn.apidev.kit.RequestMappingKit;
import cn.apidev.vo.MappingInfo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Action;
import com.jfinal.core.JFinal;
import com.jfinal.kit.HttpKit;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

/**
 * Apidev接口
 * 
 * @author apidev.cn
 *
 */
public class ApidevService extends ApidevBaseService {
	
	public static ApidevService me = new ApidevService();

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
		String sql = "delete from " + tableName + " where del > 0 and update_time <= ?";
		 LocalDateTime thirtyDaysAgo = LocalDateTime.now().minusDays(30);  
         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); 
         String date=thirtyDaysAgo.format(formatter);
		 int n = Db.delete(sql, date);
		 System.out.println("删除被标记超过30天的数据: " + n + " 条");
	}
	

	/**
	 * 数据库表
	 * @return String
	 */
	@Override
	protected String getTableName() {
		return tableName;
	}

	/**
	 * 同步接口
	 * 
	 * @return Ret
	 */
	public Ret sync() {
		int counter = 0;
		List<Record> saveList = new ArrayList<>();
		List<Record> updateList = new ArrayList<>();
		List<String> allActionKeys = JFinal.me().getAllActionKeys();
		Record root=findById("1");
		Record root2=findById("2");
		if(root==null) {
			root = new Record();
			root.set("id","1");
			root.set("title", "根目录");
			root.set("type", "menu");
			root.set("parent_id", "root");
			root.set("share_id", System.currentTimeMillis());
			root.set("create_time", new Date());
			root.set("update_time", new Date());
			save(root);
		}
		if(root2==null) {
			root2 = new Record();
			root2.set("id","2");
			root2.set("title", "快捷请求");
			root2.set("type", "menu");
			root2.set("parent_id", "root");
			root2.set("create_time", new Date());
			root2.set("update_time", new Date());
			save(root2);
		}
		int num = 0;
		for (String actionKey : allActionKeys) {
			
			String[] urlPara = new String[1];
			Action action = JFinal.me().getAction(actionKey, urlPara);
			if (action == null || actionKey.equals("")) {
				continue;
			}

			String controller = action.getControllerClass().getName();

			String sql = getQueryFullSql("action_key", "controller","type");
			Record api = Db.findFirst(sql, actionKey, controller,"api");

			if (api == null) {
				api = new Record();
				api.set("id", UUID.randomUUID().toString().replace("-", ""));
				api.set("action_key", actionKey);
				api.set("request_url", actionKey);
				api.set("controller", controller);
				api.set("title", actionKey);
				api.set("parent_id", "1");
				api.set("type", "api");
				api.set("request_mode", "GET");
				api.set("interface_status", "开发中");
				api.set("create_time", new Date());
				api.set("update_time", new Date());
				api.set("visible", 0);//默认不显示到文档页
				api.set("sort", num++);
				// 根据controller分组
				sql=getQueryFullSql("title","controller","type");
				Record parent = Db.findFirst(sql, controller, controller,"menu");
				if(parent==null) {
					parent=new Record();
					parent.set("id", UUID.randomUUID().toString().replace("-", ""));
					parent.set("controller", controller);
					parent.set("title", controller);
					parent.set("type", "menu");
					parent.set("sort", 0);
					parent.set("visible", 0);//默认不显示到文档页
					save(parent);
					num=0;
					api.set("sort", num++);
				}
				api.set("parent_id", parent.getStr("id"));
				saveList.add(api);
				counter++;
			}
		}
		
		if(ApidevKit.isSpringBoot()) {
			counter+=getRequestMapping();
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
     * 同步RequestMapping接口信息
     * @return int
     */
    public int getRequestMapping() {
    	 List<MappingInfo> mappingInfoList;
    	try {
    		mappingInfoList = RequestMappingKit.getMappingInfoList();
		} catch (Exception e) {
			System.out.println("requestMappingHandlerMapping bean is not find");
			mappingInfoList=new ArrayList<>();
		}
       
        List<JSONObject> runApiList = new ArrayList<>();
        int counter = 0;
		List<Record> saveList = new ArrayList<>();

        int num = 0;
        for (MappingInfo mappingInfo : mappingInfoList) {
            JSONObject json = new JSONObject();
            String actionKey = mappingInfo.getRequestMappings().get(0);
            String controller=mappingInfo.getClassName();
            json.put("action_key", actionKey);
            json.put("controller", mappingInfo.getClassName());
            json.put("remark", mappingInfo.getRemark());
            runApiList.add(json);
            String sql = getQueryFullSql("action_key", "controller","type");
			Record api = Db.findFirst(sql, actionKey, mappingInfo.getClassName(),"api");
			if(api==null) {
	            api = new Record();
				api.set("id", UUID.randomUUID().toString().replace("-", ""));
				api.set("action_key", actionKey);
				api.set("request_url", actionKey);
				api.set("controller", controller);
				api.set("title", mappingInfo.getRemark()==null?actionKey:mappingInfo.getRemark());
				api.set("parent_id", "1");
				api.set("type", "api");
				api.set("request_mode", "GET");
				api.set("interface_status", "开发中");
				api.set("create_time", new Date());
				api.set("update_time", new Date());
				api.set("visible", 0);//默认不显示到文档页
				api.set("sort", num++);
				// 根据controller分组
				sql=getQueryFullSql("title","controller","type");
				Record parent = Db.findFirst(sql, controller, controller,"menu");
				if(parent==null) {
					parent=new Record();
					parent.set("id", UUID.randomUUID().toString().replace("-", ""));
					parent.set("controller", controller);
					parent.set("title", controller);
					parent.set("type", "menu");
					parent.set("sort", 0);
					parent.set("visible", 0);//默认不显示到文档页
					save(parent);
					num=0;
					api.set("sort", num++);
				}
				api.set("parent_id", parent.getStr("id"));
				saveList.add(api);
				counter++;
			}
        }
        
        if (saveList.size() > 0) {
			Db.batchSave(tableName, saveList, 500);
		}
        
        return counter;
    }

	/**
	 * 查询api接口
	 * 
	 * @param parentId 父级id
	 * @param type 接口类型：menu、api、demo、link,null
	 * @param isFirst 是否第一次查询
	 * @return List
	 */
	public List<Record> getTreeList(String parentId, String type,boolean isFirst) {
		String sql = "select id,type,request_mode,parent_id,action_key,interface_status,controller,"
				+ " title,password,visible,share_id,create_time,update_time  "
				+ " from "+ tableName 
				+ " where del = 0 and parent_id=?";
		List<Record> list;
		if (type != null) {
			sql += " and type = ?";
			list = Db.find(sql + " order by sort asc,type desc", parentId, type);
		} else {
			list = Db.find(sql + " order by sort asc,type desc", parentId);
		}
		
		for (Record rd : list) {
			String id = rd.get("id");
			rd.set("isopen", isFirst);
			if(rd.get("title")==null) {
				rd.set("title", rd.getStr("action_key"));
			}
			List<Record> children = getTreeList(id, type, false);
			if (children.size() > 0)
				rd.set("children", children);
		}
		return list;
	}
	
	/**
	 * 查询被标记删除的数据
	 * @param parentId 父级id
	 * @param del 删除状态
	 * @return List
	 */
	public List<Record> getDelTreeList(String parentId, int del) {
		String sql = "select id,type,request_mode,parent_id,action_key,interface_status,controller,"
				+ " title,password,visible,create_time,update_time  "
				+ " from "+ tableName 
				+ " where del = ? and parent_id=? ";
		List<Record> list = Db.find(sql, del, parentId);

		for (Record rd : list) {
			String id = rd.get("id");
			List<Record> children = getDelTreeList(id,del);
			if (children.size() > 0)
				rd.set("children", children);
		}
		return list;
	}
	
	/**
	 * 查询api接口
	 * 
	 * @param parentId 父级id
	 * @param isFirst 是否第一次查询
	 * @return List
	 */
	public List<Record> getShareTreeList(String parentId,boolean isFirst) {
		String sql = "select id,type,request_mode,parent_id,action_key,interface_status,controller,"
				+ " title,password,visible,share_id,create_time,update_time  "
				+ " from "+ tableName 
				+ " where del = 0 and parent_id=? and visible = 1";
		List<Record> list = Db.find(sql + " order by type desc,sort asc", parentId);
		
		for (Record rd : list) {
			String id = rd.get("id");
			rd.set("isopen", isFirst);
			if(rd.get("title")==null) {
				rd.set("title", rd.getStr("action_key"));
			}
			List<Record> children = getShareTreeList(id, false);
			if (children.size() > 0)
				rd.set("children", children);
		}
		return list;
	}

	public Record findById(String id) {
		return Db.findById(tableName, id);
	}
	
	/**
	 * 查询分享id对应的数据
	 * @param shareId 分享id
	 * @return Record
	 */
	public Record findByShareId(String shareId) {
		String sql="select * from "+tableName+" where share_id=?";
		return Db.findFirst(sql, shareId);
	}

	/**
	 * 分页查询api
	 * 
	 * @param pageNumber 页码
	 * @param pageSize 分页大小
	 * @param record 查询key:parentId,title,type
	 * @return Page
	 */
	public Page<Record> list(int pageNumber, int pageSize, Record record) {
		Record params = new Record();
		String parentId = record.getStr("parent_id");
//		if (parentId != null && !"1".equals(parentId) && !"2".equals(parentId)) {
			params.set("parent_id=", parentId);
//		}
		params.set("type=", record.getStr("type"));
		params.set("title like", record.getStr("keyword"));
		params.set("del=", record.getStr("del"));
		Page<Record> page = getPage(pageNumber, pageSize, params,"order by sort asc");
		page.getList().forEach(rd -> {
			String pid = rd.getStr("parent_id");
			rd.set("parent_names", getParantNames(pid));
			rd.set("parent_ids", getParentIds(pid));
		});
		return page;
	}

	/**
	 * 查询父级名称
	 * 
	 * @param id 主键id
	 * @return String
	 */
	public String getParantNames(String id) {
		if ("1".equals(id)) {
			return "根目录";
		} else if ("2".equals(id)) {
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
				titleBuilder.append(parentName);
				titleBuilder.append(" / ").append(title);
			}

			return titleBuilder.toString();
		}

		return null;
	}

	public String getParentIds(String id) {
		if ("1".equals(id) || "2".equals(id)) {
			return id;
		}

		Record api = findById(id);
		if (api != null) {
			StringBuilder parentIdBuilder = new StringBuilder();

			// 递归获取父级名称
			String parentId = api.getStr("parent_id");
			String parentIds = getParentIds(parentId);

			// 如果有父级名称，进行拼接
			if (parentIds != null) {
				parentIdBuilder.append(parentIds);
				parentIdBuilder.append(" / ").append(id);
			}

			return parentIdBuilder.toString();
		}

		return null;
	}

	public Ret save(Record api) {
		if (api.getStr("id") == null)
			api.set("id", UUID.randomUUID().toString().replace("-", ""));
		api.set("create_time", new Date());
		api.set("update_time", new Date());
		if(api.get("parent_id")==null)
			api.set("parent_id", "1");
		api.set("del", 0);
		api.set("sort", 100);
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
	 * 修改状态
	 * @param ids 修改的id集
	 * @param status 状态
	 * @return Ret
	 */
	public Ret updateStatus(String ids,String status) {
		if(ids==null || status==null)
			return fail("id or status is null");
		String[] str=ids.split(",");
		for(String id:str) {
			Record api=new Record();
			api.set("id", id).set("interface_status", status);
			api.set("update_time", new Date());
			Db.update(tableName, api);
		}
		return ok("修改成功");
	}
	
	/**
	 * 显示隐藏接口
	 * @param ids ids
	 * @param visible 显示隐藏状态
	 * @return Ret
	 */
	public Ret updateVisible(String ids,String visible) {
		if(ids==null || visible==null)
			return fail("id or visible is null");
		String[] str=ids.split(",");
		for(String id:str) {
			Record api=new Record();
			api.set("id", id).set("visible", visible);
			api.set("update_time", new Date());
			Db.update(tableName, api);
		}
		return ok("修改成功");
	}
	
	/**
	 * 批量移动
	 * @param ids 修改的id集
	 * @param parentId 父级id
	 * @return Ret
	 */
	public Ret moveTo(String ids,String parentId) {
		if(ids==null || parentId==null)
			return fail("id or parentId is null");
		String[] str=ids.split(",");
		for(String id:str) {
			Record api=new Record();
			api.set("id", id).set("parent_id", parentId);
			api.set("update_time", new Date());
			Db.update(tableName, api);
		}
		return ok("移动成功");
	}

	/**
	 * 复制
	 * 
	 * @param id 复杂制对象主键
	 * @param parentId 父级id
	 * @return Ret
	 */
	public Ret copy(String id, String parentId) {
		Record api = findById(id);
		if (api != null) {
			String newId = UUID.randomUUID().toString().replace("-", "");
			api.set("id", newId);
			if (parentId == null) {
				String title =api.getStr("title") == null ? api.getStr("action_key") + " Copy"
						: api.getStr("title") + " Copy";
				api.set("title", title);
			}
			api.set("parent_id", parentId == null ? api.getStr("parent_id") : parentId);
			save(api);
			List<Record> childrenList = getTreeList(id, null,true);
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
	 * @param id 标记删除的id
	 * @return Ret
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
				List<Record> childrenTreeList = getTreeList(id, null, true);
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
	 * @param tree 树数据列表
	 * @param list 过滤结果列表
	 * @param isApi 是否api类型
	 */
	private void flattenTree(List<Record> tree, List<Record> list, boolean isApi) {
		for (Record record : tree) {
			if (isApi) {
				if ("api".equals(record.get("type")) || "link".equals(record.get("type"))) {
					String parentNames=getParantNames(record.getStr("id"));
					record.set("parent_names", parentNames);
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
	 * @param id 导出对象id
	 * @return List
	 */
	public List<Record> getExportApiList(String id) {
		// 获取列表
		List<Record> childrenList = new ArrayList<>();
		Record api = findById(id);
		List<Record> childrenTreeList = getTreeList(id, null, true);
		if (!"1".equals(id) && "api".equals(api.getStr("type"))) {
			childrenList.add(api);
		}
		flattenTree(childrenTreeList, childrenList, true);
		childrenList.forEach(rd -> {
			toApiJson(rd);
		});
		return childrenList;
	}
	
	public List<Record> getExportSelectApiList(String ids){
		List<Record> list = new ArrayList<>();
		if(ids!=null) {
			String[] str = ids.split(",");
			for(String id:str) {
				Record api=findById(id);
				toApiJson(api);
				if(api.get("title")==null)
					api.set("title",api.get("action_key"));
				
				list.add(api);
			}
		}
		return list;
	}
	
	/**
	 * 导出分享的接口
	 * @param id 导出对象id
	 * @return List
	 */
	public List<Record> getExportShareApiList(String id){
		// 获取列表
		List<Record> childrenList = new ArrayList<>();
		Record api = findById(id);
		List<Record> childrenTreeList = getShareTreeList(id, true);
		if (api.getInt("visible")==1 && ("api".equals(api.getStr("type"))||"demo".equals(api.getStr("type")))) {
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
	 * @param api api对象
	 */
	public void toApiJson(Record api) {
		api.put("headers_json", JSONObject.parseArray(api.get("request_headers")));
		api.put("query_json", JSONObject.parseArray(api.get("request_param_explain")));
		api.put("path_json", JSONObject.parseArray(api.get("request_path_explain")));
		api.put("form_data_json", JSONObject.parseArray(api.get("request_form_data")));
		api.put("body_json", JSONObject.parseArray(api.get("request_body_explain")));
		api.put("success_data_explain", JSONObject.parseArray(api.get("success_demo_explain")));
		api.put("failuer_data_explain", JSONObject.parseArray(api.get("failuer_demo_explain")));
		
		// 转译html字符，避免渲染html数据
		String successDemo = api.getStr("success_demo");
		successDemo = escapeHtml(successDemo);
		api.set("success_demo",successDemo);
		String failDemo = api.getStr("fail_demo");
		failDemo = escapeHtml(failDemo);
		api.set("fail_demo",failDemo);
		String requestResult = api.getStr("request_result");
		requestResult = escapeHtml(requestResult);
		api.set("request_result",requestResult);
		
	}
	
	/**
	 * 转义html字符
	 * @param html html文本
	 * @return String
	 */
	 public String escapeHtml(String html) {  
        if (html == null) {  
            return null;  
        }  
        return html  
            .replace("&", "&amp;")  
            .replace("<", "&lt;")  
            .replace(">", "&gt;")  
            .replace("\"", "&quot;")  
            .replace("'", "&apos;");  
	}  
	 
	/**
	 * 恢复
	 * 
	 * @param id 恢复对象id
	 * @return Ret
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
		if (!"1".equals(parentId) && !"2".equals(parentId)) {
			Record parentApi = findById(parentId);
			if ("demo".equals(api.get("type")) && (parentApi == null || (parentApi != null && parentApi.getInt("del") != 0))) {
				return fail("所属接口文档已不存在");
			} else if(parentApi==null || (parentApi != null && parentApi.getInt("del") != 0)){
				msg="原目录已删除，文件还原到根目录";
				String parentIds=getParentIds(id);
				if(parentIds.startsWith("2"))
					api.set("parent_id", "2");
				else
					api.set("parent_id", "1");
			}			
		}
		Db.tx(() -> {
			update(api);
			recoverChildrenApi(id);
			return true;
		});
		
		return ok(msg);
	}
	
	/**
	 * 恢复下级数据
	 * @param parentId 父级id
	 */
	public void recoverChildrenApi(String parentId) {
		List<Record> treeList=getDelTreeList(parentId,2);
		List<Record> childrenList = new ArrayList<>();
		flattenTree(treeList, childrenList, false);
		childrenList.forEach(api ->{
			api.set("del", 0);
			update(api);
		});
	}
	
	/**
	 * 目录数据
	 * @param id 目录id
	 * @param keyword 关键词查询
	 * @param treeType 树类型
	 * @return List
	 */
	public List<Record> getMenuApiList(String id,String keyword,String treeType) {
		String type="api";
		if("2".equals(treeType)) {
			type = "link";
		}
		List<Record> treeList=getTreeList(id, type, true);
		List<Record> apiList=new ArrayList<>();
		flattenTree(treeList, apiList, true);
		if(keyword!=null) {
			List<Record> filteredList = apiList.stream()  
	                .filter(record -> recordContainsKeyword(record, keyword))  
	                .collect(Collectors.toList()); 
			return filteredList;
		}
		return apiList;
	}
	
	private static boolean recordContainsKeyword(Record record, String keyword) {  
        // 在这里定义如何检查 Record 是否包含关键字，比如检查某个字段  
		String title=record.getStr("title");
		String actionKey=record.getStr("action_key");
		String controller=record.getStr("controller");
        return (title+actionKey+controller).contains(keyword); // 假设 Record 的 toString() 方法包含关键字  
    }  
	
	/**
	 * 项目统计数据
	 * @return Record
	 */
	public Record getProjectData(){
		Record result=new Record();
		String sql="select count(id) count,type from "+tableName+" where del=0 group by type";
		List<Record> list=Db.find(sql);
		list.forEach(record -> {
			if(record.get("type").equals("menu")) {
				int menuCount=record.getInt("count")-1;
				result.set("menu", menuCount);
			}else {
				result.set(record.get("type"), record.get("count"));
			}
		});
		String sql2="select id,title,password,expiret_time,share_id from "+tableName+" where del=0 and share_id is not null";
		List<Record> shareList = Db.find(sql2);
		shareList.forEach(rd->{
			rd.set("parent_names", getParantNames(rd.get("id")));
			
		});

		result.set("share_list",shareList);
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
			String type = rd.get("type");
			int status=0;
			if(!"1".equals(parentId)&&!"2".equals(parentId)) {
				Record parent=findById(parentId);
				if("demo".equals(type)){
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
	        if(diffInDays==29) {
	        	long diffInHours = TimeUnit.HOURS.convert(diffInMillis, TimeUnit.MILLISECONDS);  
	        	rd.set("diff_days", (720 - diffInHours) + " 小时");  
	        }else {   	
	        	rd.set("diff_days", (29-diffInDays)+" 天");
	        }
	        // 区分接口和快捷请求
	        String parentIds=rd.get("parent_ids");
	        if("api".equals(type)||"demo".equals(type)) {
	        	apiData.add(rd);
	        }else if("link".equals(type) || parentIds.startsWith("2")) {
	        	shortcutData.add(rd);
			}else{
				apiData.add(rd);
			}
						
		});
		Record result=new Record();
		result.set("api_data", apiData);
		result.set("shortcut_data", shortcutData);
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public String sendRequest(JSONObject json){
    	String url=json.getString("url");
    	String body=json.getString("body");
    	JSONObject queryParas=json.getJSONObject("query_para");
    	JSONObject headers=json.getJSONObject("header");
    	String method=json.getString("method");
    	String contentType=json.getString("content_type");
    	String cookies=json.getString("cookies");
    	if(StrKit.notBlank(cookies))
    		headers.put("Cookie", cookies);
    	String result="";
    	if("GET".equals(method)){
    		result=HttpKit.get(url, null, headers.toJavaObject(Map.class));
    	}else{
    		switch (contentType) { 
    			case "none":  
			    	result=HttpKit.post(url, null, headers.toJavaObject(Map.class));
			        break;  
    		    case "json":  
    		    	headers.put("Content-Type", "application/json; utf-8");  
    		    	result=HttpKit.post(url, body, headers.toJavaObject(Map.class));
    		        break;  
    		    case "raw":  
    		    	headers.put("Content-Type", "text/plain; charset=utf-8");  
    		    	result=HttpKit.post(url, body, headers.toJavaObject(Map.class));
    		        break;  
    		    case "xml":  
    		    	headers.put("Content-Type", "application/xml; charset=utf-8");  
    		    	result=HttpKit.post(url, body, headers.toJavaObject(Map.class));
    		        break;  
    		    case "form-data":  
    		    	headers.put("Content-Type", "multipart/form-data; boundary="+System.currentTimeMillis());  
    		    	result=HttpKit.post(url, queryParas.toJavaObject(Map.class), null, headers.toJavaObject(Map.class));
    		        break;  
    		    case "x-www-form-urlencoded":  
    		    	headers.put("Content-Type", "application/x-www-form-urlencoded; charset=utf-8"); 
    		    	result=HttpKit.post(url, queryParas.toJavaObject(Map.class), null, headers.toJavaObject(Map.class));
    		    	break;  
    		    default:  
    		    	headers.put("Content-Type", "application/"+contentType+"; charset=utf-8"); 
    		    	result=HttpKit.post(url, body, headers.toJavaObject(Map.class));
    		}  
    	
    	}
    	return result;
    }
	
	/**
	 * 保存接口文档
	 * @param body body参选
	 * @return Ret
	 */
	public Ret saveApi(JSONObject body) {
		Record api=new Record();      
    	//循环转换                
    	for (Map.Entry<String, Object> entry : body.entrySet()) {    
    		api.set(entry.getKey(), entry.getValue());            
    	}   
    	
    	// 新曾接口
    	boolean isAdd = api.getBoolean("is_add");
    	api.remove("is_add");
    	if(isAdd) {
    		api.set("sort", 100);
    		return save(api);
    	}else {
    		Ret ret=update(api);
    		
        	if(ret.isFail()) {
        		ret=save(api);
        	}
        	return ret;
    	}
	}
	
	/**
	 * 目录数据
	 * @param param 查询参数
	 * @return Record
	 */
	public Record getMenuData(Record param) {
		String id=param.get("id");
		String keyword=param.get("keyword");
		String treeType = param.get("tree_type","1");
		Record menuInfo=findById(id);
		if(menuInfo!=null) {
			Record parentMenu=findById(menuInfo.getStr("parent_id"));
			if(parentMenu!=null)
				menuInfo.set("parent_name", parentMenu.getStr("title"));
		}

		List<Record> menuApiData=getMenuApiList(id,keyword,treeType);
		Record menuData=new Record();
		menuData.set("menu", menuInfo);
		menuData.set("menu_data", menuApiData);
		return menuData;
	}
	
	/**
	 * 获取接口详细信息
	 * @param id 接口id
	 * @param param 查询参数
	 * @return Record
	 */
	public Record getDetail(String id,Record param) {
		Record api=findById(id);
		if(api!=null) {
			toApiJson(api);
			Record parent=findById(api.get("parent_id"));
			if(parent!=null) {
				String parentName = parent.get("title")==null?parent.get("action_key") : parent.get("title");
				api.set("parent_name", parentName);
			}
			if(api.get("title")==null) {
				api.set("title", api.get("action_key"));
				api.set("request_url", api.get("action_key"));
			}
		}else {
			api = new Record();
			String title = param.get("title");
			api.set("parent_id", param.get("parent_id"));
			api.set("id", param.get("id"));
			api.set("type", param.get("type"));
			api.set("title", title.substring(title.length()-4));
			api.set("is_add", true);
			if(api.get("type").equals("demo")) {
				Record parent=findById(api.get("parent_id"));
				if(parent!=null) {
					api.set("request_mode", parent.getStr("request_mode"));
					String parentName = parent.get("title")==null?parent.get("action_key") : parent.get("title");
					api.set("parent_name", parentName);
					api.set("action_key", parent.get("action_key"));
					api.set("request_url", parent.get("request_url"));
				}
			}
		}
		String treeType=api.getStr("type").equals("link") ? "2" : "1";
		api.set("tree_type",treeType);
		return api;
	}
	
	/**
	 * 文档预览
	 * @param id 文档id
	 * @return Record
	 */
	public Record getDoc(String id) {
		Record api=findById(id);
		if(api!=null) {
			toApiJson(api);
		}else {
			api=new Record();
			api.set("id", id);
		}
		return api;
	}
	
	/**
	 * 更新排序
	 * @param ids ids集
	 * @return Ret
	 */
	public Ret updateSort(JSONArray ids) {
		if(ids!=null) {
			for(int i=0;i<ids.size();i++) {
				Record api=new Record();
				api.set("id", ids.get(i));
				api.set("sort", i+1);
				Db.update(tableName, api);
			}
		}
		return ok("修改成功");
	}
}
