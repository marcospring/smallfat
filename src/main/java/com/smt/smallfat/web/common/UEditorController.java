package com.smt.smallfat.web.common;

import com.smt.smallfat.constant.Constant;
import com.smt.smallfat.utils.ueditor.ActionEnter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

@Controller
public class UEditorController extends BaseController{
    @RequestMapping("/controller")
    public void exec(HttpServletRequest request, HttpServletResponse response){
//        request.setCharacterEncoding("utf-8");
//        try {
//            request.setCharacterEncoding(Constant.Charset.UTF8);
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        response.setHeader("Content-Type" , "text/html");
//        String rootPath = request.getServletContext().getRealPath( "/" );
//        PrintWriter out = null;
//        try {
//            out = response.getWriter();
//            out.write( new ActionEnter( request, rootPath ).exec() );
//        } catch (IOException e) {
//            e.printStackTrace();
//        }finally {
//            out.flush();
//            out.close();
//        }

    }
}
