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

package cn.apidev.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 接口映射类
 * 
 * @author 琴海森林
 */
public class MappingInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	private String className;
	private String methodName;					// 方法
	private List<String> httpMethods;			// 同一个接口可以通过GET/POST等访问
	private List<String> classRequestMappings;	// class定义的映射
	private List<String> requestMappings;		// 具体的方法映射，包含class类上的映射
	private String remark;						// 方法备注

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public List<String> getHttpMethods() {
		return httpMethods;
	}

	public void setHttpMethods(List<String> httpMethods) {
		this.httpMethods = httpMethods;
	}

	public List<String> getClassRequestMappings() {
		return classRequestMappings;
	}

	public void setClassRequestMappings(List<String> classRequestMappings) {
		this.classRequestMappings = classRequestMappings;
	}

	public List<String> getRequestMappings() {
		return requestMappings;
	}

	public void setRequestMappings(List<String> requestMappings) {
		this.requestMappings = requestMappings;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
