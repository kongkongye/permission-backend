package com.kongkongye.backend.permission.common;

import com.alibaba.fastjson.JSON;
import com.kongkongye.backend.permission.common.util.StringUtil;
import com.kongkongye.backend.permission.common.util.Util;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * spring的拦截器：日志拦截器
 * 实例化后添加到spring的拦截器中生效
 */
@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogInterceptor implements HandlerInterceptor {
    public static final String KEY_REQUEST_USER_ID = "userId";
    private static final String REQUEST_KEY_REQ_ID = "log_reqId";
    private static final String REQUEST_KEY_REQ_TIME = "log_reqTime";

    /**
     * 默认为null
     */
    private String realIpHeader;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String reqId = StringUtil.getRandomUid();
        request.setAttribute(REQUEST_KEY_REQ_ID, reqId);
        request.setAttribute(REQUEST_KEY_REQ_TIME, System.currentTimeMillis());

        Object userId = request.getSession().getAttribute(KEY_REQUEST_USER_ID);

        log.info("[请求开始]请求ID:{}, 用户:{} ip:{}, uri:{}, 方法:{}, 内容类型:{}, 参数:{}",
                reqId, userId != null ? userId : "", Util.getRealIp(realIpHeader, request), request.getRequestURI(), request.getMethod(), request.getContentType(),
                JSON.toJSONString(request.getParameterMap()));

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String reqId = (String) request.getAttribute(REQUEST_KEY_REQ_ID);
        Long reqTime = (Long) request.getAttribute(REQUEST_KEY_REQ_TIME);
        long last = reqTime != null ? (System.currentTimeMillis() - reqTime) : -1;
        String lastStr = last != -1 ? ((double) last / 1000) + "s" : "";
        HttpSession session = request.getSession(false);

        log.info("[请求结束]请求ID:{}, 耗时:{}, 会话过期时间:{} ex:{}",
                reqId,
                lastStr,
                session != null ? session.getMaxInactiveInterval() + "s" : "",
                ex != null ? ex.getMessage() : "");
    }

}
