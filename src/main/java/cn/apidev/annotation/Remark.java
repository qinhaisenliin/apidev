/**
 * 本项目采用《咖啡授权协议》，保护知识产权，就是在保护我们自己身处的行业。
 * 
 * Copyright (c) 2022-2023, jfinal.com
 */

package cn.apidev.annotation;

import java.lang.annotation.*;

/**
 * 接口一键同步功能时，自动向 run_api 表的 remark 字段中
 * 添加 @Remark 注解的内容，注意只在 remark 字段为空时才添加
 * 否则会覆盖掉用户自己添加的内容
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Remark {
	String value();
}