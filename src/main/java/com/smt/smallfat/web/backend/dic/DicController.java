package com.smt.smallfat.web.backend.dic;


import com.csyy.common.StringDefaultValue;
import com.smt.smallfat.po.SysDic;
import com.smt.smallfat.service.system.SysDicService;
import com.smt.smallfat.vo.DicTreeVo;
import com.smt.smallfat.vo.SysDicVo;
import com.smt.smallfat.web.common.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by xindongwang on 17/3/12.
 */
@Controller
@RequestMapping("/backend/dic")
public class DicController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SysDicService dicService;

    /**
     * 添加字典父类
     * @param request
     * @param response
     */
    @RequestMapping("/addDic")
    public void addDic(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request, SysDicVo.FIELD_DIC_CODE,SysDicVo.FIELD_DIC_NAME);
        SysDic dic = dicService.addDic(param);
        printWriter(response, successResultJSON(dic));
    }

    /**
     * 删除字典父类
     * @param request
     * @param response
     */
    @RequestMapping("/deleteDic")
    public void deleteDic(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request, SysDicVo.FIELD_ID);
        dicService.addDic(param);
        printWriter(response, successResultJSON());
    }

    /**
     * 更新字典父类
     * @param request
     * @param response
     */
    @RequestMapping("/updateDic")
    public void updateDic(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = getRequestParams(request);
        SysDicVo sysDicVo = dicService.updateDic(param);
        printWriter(response, successResultJSON(sysDicVo));
    }

    /**
     * 根据字典父类id获取字典父类
     * @param request
     * @param response
     */
    @RequestMapping("/getDicById")
    public void getDicById(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request, SysDicVo.FIELD_ID);
        int id = StringDefaultValue.intValue(param.get(SysDicVo.FIELD_ID));
        SysDicVo sysDicVo = dicService.getDicById(id);
        printWriter(response, successResultJSON(sysDicVo));
    }

    /**
     * 根据字典父类uuid获取字典父类
     * @param request
     * @param response
     */
    @RequestMapping("/getDicByUUID")
    public void getDicByUUID(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request, SysDicVo.FIELD_UUID);
        String uuid = StringDefaultValue.StringValue(param.get(SysDicVo.FIELD_UUID));
        SysDicVo sysDicVo = dicService.getDicByUUID(uuid);
        printWriter(response, successResultJSON(sysDicVo));
    }

    /**
     * 获取父字典tree
     * @param request
     * @param response
     */
    @RequestMapping("/dicTree")
    public void dicTree(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = getRequestParams(request);
        DicTreeVo dicTreeVo = dicService.getDicTree(param);
        printWriter(response, successResultJSON(dicTreeVo));
    }

    /**
     * 根据爷爷获取父类集合
     * @param request
     * @param response
     */
    @RequestMapping("/getDicListByParentId")
    public void getDicListByParentId(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request,SysDicVo.FIELD_DIC_CODE);
        String code = StringDefaultValue.StringValue(param.get(SysDicVo.FIELD_DIC_CODE));
        List<SysDicVo> sysDicVoList = dicService.getDicListByParentId(code);
        printWriter(response, successResultJSON(sysDicVoList));
    }
}
