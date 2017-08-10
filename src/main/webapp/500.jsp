<%@ page import="com.csyy.common.JSONUtils" %>
<%@ page import="com.csyy.core.constant.Constants" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    response.getWriter().print(JSONUtils.toJson(Constants.ERROR));
%>