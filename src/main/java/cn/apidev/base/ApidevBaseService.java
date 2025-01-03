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

import java.util.ArrayList;
import java.util.List;

import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

/**
 * APIDev 基础服务类
 * 
 * @author 琴海森林
 *
 */
public abstract class ApidevBaseService {
	
	/**
	 * 返回接口成功数据
	 * @param data 接口返回数据
	 * @return Ret
	 */
	protected Ret ok(Object data) {
		return Ret.ok("成功").set("data", data);
	}
	
	protected Ret ok(String msg) {
		return Ret.ok(msg);
	}
	
	protected Ret ok() {
		return Ret.ok("成功");
	}
	
	protected Ret fail() {
		return Ret.fail("失败");
	}

	/**
	 * 返回接口失败信息
	 * @param msg 提示信息
	 * @return Ret
	 */
	protected Ret fail(String msg) {
		return Ret.fail(msg);
	}

	/**
	 * 查询表名称
	 * 
	 * @return String
	 */
	protected abstract String getTableName();
	
	
	/**
	 * select * from getTable()
	 * @return String
	 */
	protected String getQuerySql() {			
		return "select * from "+getTableName()+" ";
	}
	
	/**
	 * 获取字段查询sql
	 * 
	 * columns 查询字段可以带逻辑字符,
	 * 
	 * 如：用户表sys_user,用name、state、sex查询：getQuerySql("name like","state=","sex");
	 * 
	 * 那么就会返回查询语句：select * from sys_user where name like ? and state = ? and sex = ?
	 * 
	 * @param columns name like,state=,sex
	 * @return String sql：select * from sys_user where name like ? and state = ? and sex = ?
	 */
	protected String getQueryFullSql(String...columns) {
		String sql=getQuerySql();
		for(int i=0;i<columns.length;i++) {
			if(i==0)
				sql+=" where ";
			if(i>0)
				sql+=" and ";
			
			sql+=columns[i];
			
			boolean b=columns[i].endsWith(" like")||columns[i].endsWith("=")||columns[i].endsWith(">")||columns[i].endsWith("<");
			if(b)
				sql+=" ? ";
			else
				sql+=" = ? ";
		}
		System.out.println(sql);
		return sql;
	}
	

	/**
	 * 单表分页条件查询
	 * 
	 * @param pageNumber 页码
	 * @param pageSize   分页大小
	 * @param params     查询条件 条件前置部分集合（字段+匹配符号，如 name like,id = ）
	 * @return Page
	 */
	protected Page<Record> getPage(int pageNumber, int pageSize, Record params) {
		return getPage( pageNumber, pageSize, params, null, null);
	}

	/**
	 * 单表分页条件查询
	 * 
	 * @param pageNumber 页码
	 * @param pageSize   分页大小
	 * @param params     查询条件 条件前置部分集合（字段+匹配符号，如 name like,id = ）
	 * @param orderBySql orderBy语句
	 * @return Page
	 */
	protected Page<Record> getPage(int pageNumber, int pageSize, Record params, String orderBySql) {
		return getPage(pageNumber, pageSize, params, orderBySql, null);
	}

	/**
	 * 单表分页条件查询，支持多数据源
	 * 
	 * @param pageNumber 页码
	 * @param pageSize   分页大小
	 * @param params     查询条件 条件前置部分集合（字段+匹配符号，如 name like,id = ）
	 * @param orderBySql orderBy语句
	 * @param groupBySql groupBy语句
	 * @return Page
	 */
	protected Page<Record> getPage(int pageNumber, int pageSize, Record params, String orderBySql,String groupBySql) {
		// 拼接sql中的from部分
		StringBuilder from = new StringBuilder(" from ");
		from.append(getTableName()).append(" where 1=1");
		// 这个用来存值不为空的value集合
		List<Object> notNullValues = new ArrayList<>();
		if (params != null && params.getColumnNames().length > 0) {
			// 查询条件前置部分集合（字段+匹配符号，如 name like,id = ）
			// 也可以直接写全查询条件，这时不需要value,如(id = 1 or parent_id = 1)
			String columnNames[] = params.getColumnNames();
			// 查询条件后置部分集合（每个查询条件匹配的值，如"张三",20）
			Object[] columnValues = params.getColumnValues();
			for (int i = 0; i < columnNames.length; i++) {
				String columnName = columnNames[i];
				Object columnValue = columnValues[i];
				if (columnValue != null && StrKit.notBlank(String.valueOf(columnValue))) {
					// 处理不带?号的查询条件，这类查询条件，value一律传"withoutValue"
					if ("withoutValue".equals(columnValue)) {
						from.append(" and ").append(columnName);
					} else {
						if (columnName.contains("like")) {
							columnValue = "%" + columnValue + "%";
						}
						from.append(" and ").append(columnName).append(" ?");
						notNullValues.add(columnValue);
					}
				}
			}
		}
		Object[] notNullValueArr = new Object[notNullValues.size()];
		notNullValues.toArray(notNullValueArr);
		// 计数语句
		String totalRowSql = "select count(*) " + from.toString();

		if (StrKit.notBlank(groupBySql)) {
			totalRowSql += " " + groupBySql;
		}
		// 查询语句
		String findSql = "select * " + from.toString();
		if (StrKit.notBlank(orderBySql)) {
			findSql += " " + orderBySql;
		}

		return Db.paginateByFullSql(pageNumber, pageSize, totalRowSql, findSql, notNullValueArr);
	}
}
