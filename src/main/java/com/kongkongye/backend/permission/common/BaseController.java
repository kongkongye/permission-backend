package com.kongkongye.backend.permission.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
public abstract class BaseController extends Base {
    //以下3个方法不应该在service里访问
    //因为这样的话对于service里的一个方法,就无法区分是否需要判断用户权限了

    public ServletRequestAttributes getServletRequestAttributes() {
        return (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    }

    public HttpServletRequest getHttpRequest() {
        return getServletRequestAttributes().getRequest();
    }

    public HttpSession getHttpSession() {
        return getHttpRequest().getSession();
    }

    /**
     * 获取语言（使用当前线程的请求里的locale）
     * @param code 语言编码
     */
//    public String get(String code, Object... args) {
//        ServletRequestAttributes requestAttributes = getServletRequestAttributes();
//        return I18nUtil.get(code, requestAttributes.getRequest().getLocale(), args);
//    }
}
