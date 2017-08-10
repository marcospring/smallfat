package com.smt.smallfat.web.backend.dic;

import com.csyy.common.JSONUtils;
import com.csyy.common.StringDefaultValue;
import com.csyy.core.result.Result;
import com.smt.smallfat.po.SysDicItem;
import com.smt.smallfat.service.system.SysDicItemService;
import com.smt.smallfat.vo.SysDicItemVo;
import com.smt.smallfat.vo.SysDicVo;
import com.smt.smallfat.web.common.BaseController;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by xindongwang on 17/3/12.
 */

@Controller
@RequestMapping("/dicItem")
public class DicItemController extends BaseController{

    private Logger logger = Logger.getLogger(getClass());

    @Autowired
    private SysDicItemService dicItemService;

    /**
     * 添加字典子类
     * @param request
     * @param response
     */
    @RequestMapping("/addDicItem")
    public void addDicItem(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request, SysDicItemVo.FIELD_DIC_ID,SysDicItemVo.FIELD_DIC_ITEM_NAME,SysDicItemVo.FIELD_DIC_ITEM_VALUE);
        SysDicItem result = dicItemService.addSysDicItem(param);
        printWriter(response, successResultJSON(result));
    }

    /**
     * 删除字典子类
     * @param request
     * @param response
     */
    @RequestMapping("/deleteDicItem")
    public void deleteDicItem(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request, SysDicItemVo.FIELD_ID);
        int id = StringDefaultValue.intValue(param.get(SysDicItemVo.FIELD_DIC_ID));
        dicItemService.deleteSysDicItem(id);
        printWriter(response, successResultJSON());
    }

    /**
     * 更新字典子类
     * @param request
     * @param response
     */
    @RequestMapping("/updateDicItem")
    public void updateDicItem(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request, SysDicItemVo.FIELD_ID);
        SysDicItemVo result = dicItemService.updateSysDicItem(param);
        printWriter(response, successResultJSON(result));
    }

    /**
     * 根据字典子类id获取字典子类
     * @param request
     * @param response
     */
    @RequestMapping("/getDicItemById")
    public void getDicItemById(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request, SysDicItemVo.FIELD_ID);
        int id = StringDefaultValue.intValue(param.get(SysDicItemVo.FIELD_DIC_ID));
        SysDicItemVo item = dicItemService.getDicItemById(id);
        printWriter(response, successResultJSON(item));
    }

    /**
     * 根据字典子类uuid获取字典子类
     * @param request
     * @param response
     */
    @RequestMapping("/getDicItemByUUID")
    public void getDicItemByUUID(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request, SysDicItemVo.FIELD_UUID);
        String uuid = StringDefaultValue.StringValue(param.get(SysDicItemVo.FIELD_UUID));
        SysDicItemVo item = dicItemService.getDicItemByUUID(uuid);
        printWriter(response, successResultJSON(item));
    }

    /**
     * 根据父字典code获取子字典列表
     * @param request
     * @param response
     */
    @RequestMapping(value = "/getDicItemsByDicCode",method = RequestMethod.POST)
    public void getDicItemsByDicCode(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request, SysDicVo.FIELD_DIC_CODE);
        String code = StringDefaultValue.StringValue(param.get(SysDicVo.FIELD_DIC_CODE));
        List<SysDicItemVo> list = dicItemService.getDicItemByDicCode(code);
        printWriter(response,successResultJSON(list));
    }

    /**
     * 根据父字典id获取子字典列表
     * @param request
     * @param response
     */
    @RequestMapping("/getDicItemByDicId")
    public void getDicItemByDicId(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request,SysDicVo.FIELD_ID);
        int id = StringDefaultValue.intValue(param.get(SysDicVo.FIELD_ID));
        List<SysDicItemVo> list = dicItemService.getDicItemByDicId(id);
        printWriter(response,successResultJSON(list));
    }

    /**
     * 根据父字典uuid获取子字典列表
     * @param request
     * @param response
     */
    @RequestMapping("/getDicItemByDicUUID")
    public void getDicItemByDicUUID(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request, SysDicVo.FIELD_UUID);
        String code = StringDefaultValue.StringValue(param.get(SysDicVo.FIELD_UUID));
        List<SysDicItemVo> list = dicItemService.getDicItemByDicUUID(code);
        printWriter(response,successResultJSON(list));
    }

}
