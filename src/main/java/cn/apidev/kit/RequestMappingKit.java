package cn.apidev.kit;

import org.springframework.beans.BeansException;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import cn.apidev.config.spring.ApidevApplicationContext;
import cn.apidev.vo.MappingInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 获取RequestMapping映射路由信息
 */
public class RequestMappingKit {

    /**
     * 获取映射路由信息
     * @return List
     */
    public static List<MappingInfo> getMappingInfoList() throws BeansException {
        RequestMappingHandlerMapping mapping = (RequestMappingHandlerMapping) ApidevApplicationContext.getBean("requestMappingHandlerMapping");

        // 获取url与类和方法的对应信息
        Map<RequestMappingInfo, HandlerMethod> map = mapping.getHandlerMethods();

        List<MappingInfo> list = new ArrayList<>(map.size());

        for (Map.Entry<RequestMappingInfo, HandlerMethod> m : map.entrySet()) {
            MappingInfo mappingInfo = new MappingInfo();
            RequestMappingInfo info = m.getKey();
            HandlerMethod handlerMethod = m.getValue();
            //获取当前方法所在类名
            Class<?> bean = handlerMethod.getBeanType();
            //使用反射获取当前类注解内容
            RequestMapping requestMapping = bean.getAnnotation(RequestMapping.class);
            if (null != requestMapping) {
                String[] value = (String[]) AnnotationUtils.getAnnotationAttributes(requestMapping).get("value");
                mappingInfo.setClassRequestMappings(Arrays.asList(value));
            }
            //获取方法上注解以及注解值
            PatternsRequestCondition p = info.getPatternsCondition();
            List<String> urls = new ArrayList<>();
            urls.addAll(p.getPatterns());
            mappingInfo.setRequestMappings(urls);

//            ApiOperation apiOperation = handlerMethod.getMethodAnnotation(ApiOperation.class);
//            if (apiOperation != null) {
//                mappingInfo.setRemark(apiOperation.value());
//            } else {
                  mappingInfo.setRemark(info.getName());
//            }

            mappingInfo.setClassName(handlerMethod.getMethod().getDeclaringClass().getName());
            mappingInfo.setMethodName(handlerMethod.getMethod().getName());

            RequestMethodsRequestCondition methodsCondition = info.getMethodsCondition();
            List<String> httpMethods = new ArrayList<>();
            methodsCondition.getMethods().forEach(requestMethod -> httpMethods.add(requestMethod.toString()));
            mappingInfo.setHttpMethods(httpMethods);

            list.add(mappingInfo);
        }

        return list;
    }


}
