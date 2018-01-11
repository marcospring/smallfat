package com.smt.smallfat.web.common;

import com.csyy.common.ExceptionInfo;
import com.csyy.common.JSONUtils;
import com.csyy.common.StringDefaultValue;
import com.csyy.core.exception.CommonException;
import com.csyy.core.result.Result;
import com.csyy.core.utils.SpringContextHolder;
import com.csyy.redis.factory.RedisSessionFactory;
import com.smt.smallfat.constant.Constant;
import com.smt.smallfat.constant.ResultConstant;
import com.smt.smallfat.utils.SystemSessionUtil;
import com.smt.smallfat.vo.LoginVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by gaohaiming on 2017/3/11.
 */
public class SessionFilter extends OncePerRequestFilter {

    private static final String AJAX_HEADER = "XMLHttpRequest";
    private static Logger logger = LoggerFactory.getLogger(SessionFilter.class);
    private static Properties properties = new Properties();
    private static final String SESSION_ID = "sessionId";

    public static int TIME_OUT = 999;
    public static Result SESSION_TIMEOUT = new Result(TIME_OUT,"会话不存在或已过期");
    public static Result LOGOIN_BY_OTHER_RESULT = new Result(10216,
            "您的账号已在其他位置登录");
    public static Result NO_PERMISSION_RESULT = new Result(10217,
            "无权限");
    // RedisSessionFactory factory = SpringContextHolder.getBean("redisFactory",
    // RedisSessionFactory.class);
    RedisSessionFactory factory = SpringContextHolder.getBean(
            "redisFactory",
            RedisSessionFactory.class);

    static {
        try {
            properties.load(Thread.currentThread().getContextClassLoader()
                    .getResourceAsStream("properties/config.properties"));
        } catch (IOException e) {
            ExceptionInfo.exceptionInfo(e, logger);
            e.printStackTrace();
        }
    }

    boolean isValid(String uri) {
        boolean doFilter = true;
        String noFilterStr = properties.getProperty("nologin");
        if (!StringUtils.isEmpty(noFilterStr)) {
            String[] notFilter = noFilterStr.split(Constant.Separator.COMMA);
            for (String s : notFilter) {
                if (uri.indexOf(s) != -1) {
                    doFilter = !doFilter;
                    break;
                }
            }
        }

        return doFilter;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "No-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding(Constant.Charset.UTF8);
        PrintWriter out = null;

        String uri = request.getRequestURI();
        this.paramLog(request);
        boolean doFilter = isValid(uri);

        if (doFilter) {
            String mailiSid = request.getParameter(SESSION_ID);
            boolean islogined = false;
            LoginVO loginVO = new LoginVO();
            try {
                // 后台用户
                if (!StringUtils.isEmpty(mailiSid)) {
                    loginVO = SystemSessionUtil.getAndRefashUserSession(mailiSid);
                    if (loginVO != null){
                        islogined = true;
                    }
                }
            } catch (CommonException e) {
                if (ResultConstant.SysUserResult.SESSION_LOST_ERROR.equals(e
                        .getExceptionKey())) {
                    out = response.getWriter();
                    out.print(JSONUtils.toJson(SessionFilter.SESSION_TIMEOUT));
                    return;

                } else if (ResultConstant.SysUserResult.LOGOIN_BY_OTHER.equals(e
                        .getExceptionKey())) {
                    out = response.getWriter();
                    out.print(JSONUtils.toJson(LOGOIN_BY_OTHER_RESULT));
                    return;
                } else {
                    throw e;
                }

            }
            // 未登录
            if (!islogined) {
                out = response.getWriter();
                out.print(JSONUtils.toJson(SESSION_TIMEOUT));
                return;
            } else {
//                List<PermissionListener> permissionListenerList = new ArrayList<>();
//                ChannelPermissionListener channelPermissionListener = new ChannelPermissionListener();
//                permissionListenerList.add(channelPermissionListener);
//                for (PermissionListener p: permissionListenerList) {
//                    if (!p.check(loginVO,uri)) {
//                        out = response.getWriter();
//                        out.print(JSONUtils.toJson(NO_PERMISSION_RESULT));
//                        return;
//                    }
//                }
                filterChain.doFilter(request, response);
            }

        } else {
            filterChain.doFilter(request, response);
        }
    }

    private boolean isAjaxRequest(HttpServletRequest request) {
        String header = request.getHeader("X-Requested-With");
        if (header != null && AJAX_HEADER.equals(header)) {
            return true;
        }
        return false;
    }
    /**
     *
     * @param request
     */
    public void paramLog(HttpServletRequest request) {
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        Enumeration<?> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String name = StringDefaultValue.StringValue(parameterNames
                    .nextElement());
            try {
                String value = null;
                String[] values = request.getParameterValues(name);
                if (values != null && values.length == 1) {
                    value = values[0];
                    paramsMap.put(name, value);
                }
            } catch (Exception e) {
                logger.error("获取页面参数时异常，参数名称【{}】异常信息【{}】", name, e);
            }
        }
        logger.debug("\r\n— — — 请求url：【" + request.getRequestURL()
                + "】 \r\n— — — 参数：【" + JSONUtils.toJson(paramsMap) + "】");
    }
}
