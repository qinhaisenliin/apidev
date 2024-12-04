package cn.apidev.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 接口映射类
 */
public class MappingInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	private String className; 
    private String methodName;//方法
    private List<String> httpMethods;//同一个接口可以通过GET/POST等访问
    private List<String> classRequestMappings;//class定义的映射
    private List<String> requestMappings;//具体的方法映射，包含class类上的映射
    private String remark;//方法备注

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
