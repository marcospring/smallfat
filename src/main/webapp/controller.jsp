<%@ page import="com.smt.smallfat.utils.ueditor.ActionEnter" %>
<%--
  Created by IntelliJ IDEA.
  User: zhangkui
  Date: 2017/10/9
  Time: 下午5:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    request.setCharacterEncoding( "utf-8" );
    response.setHeader("Content-Type" , "text/html");
    String rootPath = application.getRealPath( "/" );
    out.write( new ActionEnter( request, rootPath ).exec() );
%>