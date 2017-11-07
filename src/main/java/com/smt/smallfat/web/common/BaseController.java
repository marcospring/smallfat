
package com.smt.smallfat.web.common;

import com.csyy.common.ExceptionInfo;
import com.csyy.common.JSONUtils;
import com.csyy.common.PropertiesUtils;
import com.csyy.common.StringDefaultValue;
import com.csyy.core.apisupport.BaseResultSupport;
import com.csyy.core.exception.BusinessException;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.smt.smallfat.exception.InfoEmptyException;
import com.smt.smallfat.exception.ValideErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class BaseController extends BaseResultSupport {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final int DEFAULT_PAGE_SIZE = 10;
    protected String PAGE_NO = "pageNo";
    protected String PAGE_SIZE = "pageSize";

    public BaseController() {
    }

    protected void printWriter(HttpServletResponse response, String data) {
        this.setBasicResponseHeader(response);
        response.setContentType("application/json; charset=utf-8");

        try {
            this.write(data, response);
        } catch (IOException var4) {
            var4.printStackTrace();
        }

    }

    protected void setBasicResponseHeader(HttpServletResponse response) {
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "No-cache");
        response.setDateHeader("Expires", 0L);
    }

    private void write(String data, HttpServletResponse response) throws IOException {
        PrintWriter printWriter = response.getWriter();
        this.logger.debug("向前台写数据{}", data);
        printWriter.write(data);
        printWriter.flush();
        printWriter.close();
    }

    public Map<String, Object> getRequestParams(HttpServletRequest request) {
        Map<String, Object> paramsMap = new HashMap();
        String pageNoStr = request.getParameter("pageNo");
        String pageSizeStr = request.getParameter("pageSize");
        int pageNo = StringDefaultValue.intValue(pageNoStr) == 0 ? 1 : StringDefaultValue.intValue(pageNoStr);
        int pageSize = StringDefaultValue.intValue(pageSizeStr) == 0 ? 10 : StringDefaultValue.intValue(pageSizeStr);
        paramsMap.put("pageNo", pageNo);
        paramsMap.put("pageSize", pageSize);
        Enumeration parameterNames = request.getParameterNames();

        while (parameterNames.hasMoreElements()) {
            String name = StringDefaultValue.StringValue(parameterNames.nextElement());

            try {
                String value = null;
                String[] values = request.getParameterValues(name);
                if (values != null && values.length == 1) {
                    value = values[0];
                    paramsMap.put(name, value.trim());
                }
            } catch (Exception var11) {
                this.logger.error("获取页面参数时异常，参数名称【{}】异常信息【{}】", name, var11);
            }
        }

        return paramsMap;
    }

    @ExceptionHandler
    public void exception(HttpServletResponse response, Exception e) {
        if (!(e instanceof BusinessException)) {
            ((Exception) e).printStackTrace();
            ExceptionInfo.exceptionInfo((Exception) e, this.logger);
            e = new BusinessException((Exception) e);
        }

        this.printWriter(response, this.resultJSON((BusinessException) e));
    }

    protected Map<String, Object> nullAbleValidation(HttpServletRequest request, String... args) {
        Map<String, Object> params = this.getRequestParams(request);
        this.logger.debug("请求地址:" + request.getRequestURL() + ",请求参数:" + JSONUtils.toJson(params));
        if (args != null && args.length != 0) {
            String[] var4 = args;
            int var5 = args.length;

            for (int var6 = 0; var6 < var5; ++var6) {
                String key = var4[var6];
                if (StringUtils.isEmpty(params.get(key))) {
                    throw new InfoEmptyException();
                }
            }

            return params;
        } else {
            throw new InfoEmptyException();
        }
    }

    protected Map<String, Object> getAndCheckParams(HttpServletRequest request, String[] notEmptyKeys, String[] optionalKeys) {
        Map<String, Object> params = new HashMap();
        List<String> notEmptyKeysList = new ArrayList();
        String[] var6 = notEmptyKeys;
        int var7 = notEmptyKeys.length;

        int var8;
        for (var8 = 0; var8 < var7; ++var8) {
            String key = var6[var8];
            notEmptyKeysList.add(key);
        }

        List<String> optionalKeysList = new ArrayList();
        String[] var12 = optionalKeys;
        var8 = optionalKeys.length;

        for (int var15 = 0; var15 < var8; ++var15) {
            String key = var12[var15];
            optionalKeysList.add(key);
        }

        Enumeration parameterNames = request.getParameterNames();

        while (parameterNames.hasMoreElements()) {
            String name = StringDefaultValue.StringValue(parameterNames.nextElement());
            if (notEmptyKeysList.contains(name)) {
                this.putParams(request, params, name, true);
            } else if (optionalKeysList.contains(name)) {
                this.putParams(request, params, name, false);
            }
        }

        this.logger.debug("请求地址:" + request.getRequestURL() + ",请求参数:" + JSONUtils.toJson(params));
        return params;
    }

    private void putParams(HttpServletRequest request, Map<String, Object> params, String name, boolean mustKey) {
        try {
            String value = null;
            String[] values = request.getParameterValues(name);
            if (values != null && values.length >= 1) {
                value = values[0].trim();
                if (mustKey && StringUtils.isEmpty(value)) {
                    throw new InfoEmptyException();
                }

                params.put(name, value);
            } else if (mustKey && values == null) {
                throw new InfoEmptyException();
            }
        } catch (Exception var7) {
            this.logger.error("获取页面参数时异常，参数名称【{}】异常信息【{}】", name, var7);
        }

    }

    protected Object validation(HttpServletRequest request, BaseController.Validator validator) {
        Map<String, Object> param = this.getRequestParams(request);
        Map<String, Object> map = new HashMap();
        Iterator var5 = param.entrySet().iterator();

        while (var5.hasNext()) {
            Entry<String, Object> entry = (Entry) var5.next();
            if (!"".equals(entry.getValue())) {
                map.put(entry.getKey(), entry.getValue());
            }
        }

        this.logger.info("参数================:" + JSONUtils.toJson(map));
        return validator.valid(map);
    }

    public Object getMapVal(Map<String, Object> map, String key) {
        Object object = map.get(key);
        if (object instanceof String) {
            return object != null && !"".equals(object) ? ((String) object).trim() : "";
        } else {
            return object;
        }
    }

    protected boolean checkRole(Map<String, Object> sysLoginVO, PropertiesUtils properties) {
        try {
            Object roles = sysLoginVO.get("roles");
            String s = JSONUtils.toJson(roles);
            List<Integer> integers = (List) JSONUtils.fromJson(s, new TypeToken<List<Integer>>() {
            });
            String unsensitive = properties.get("unsensitive");
            String[] split = unsensitive.split(",");
            return !integers.stream().anyMatch((p) -> {
                return Arrays.stream(split).anyMatch((k) -> {
                    return String.valueOf(p).equals(k);
                });
            });
        } catch (Exception var8) {
            this.logger.error("脱敏校验角色错误================");
            ExceptionInfo.exceptionInfo(var8, this.logger);
            return false;
        }
    }

    public class SessionIdValid implements BaseController.Validator {
        public SessionIdValid() {
        }

        public Object valid(Map<String, Object> map) {
            if (StringUtils.isEmpty(map.get("sessionId"))) {
                throw new InfoEmptyException();
            } else {
                return StringDefaultValue.StringValue(map.get("sessionId"));
            }
        }

        public String getValidSessionId(Map<String, Object> map) {
            return (String) this.valid(map);
        }
    }

    public class UUIDValid implements BaseController.Validator {
        public UUIDValid() {
        }

        public Object valid(Map<String, Object> map) {
            if (StringUtils.isEmpty(map.get("uuid"))) {
                throw new InfoEmptyException();
            } else {
                return StringDefaultValue.StringValue(map.get("uuid"));
            }
        }

        public String getValidUUID(Map<String, Object> map) {
            return (String) this.valid(map);
        }
    }

    public class IdValid implements BaseController.Validator {
        public IdValid() {
        }

        public Object valid(Map<String, Object> map) {
            if (StringUtils.isEmpty(map.get("id"))) {
                throw new InfoEmptyException();
            } else {
                int id;
                try {
                    id = StringDefaultValue.intValue(map.get("id"));
                } catch (Exception var4) {
                    throw new ValideErrorException();
                }

                return id;
            }
        }

        public int getValidId(Map<String, Object> map) {
            return StringDefaultValue.intValue(this.valid(map));
        }
    }

    protected interface Validator {
        Object valid(Map<String, Object> var1);
    }
}
