package com.smt.smallfat.web.backend.system;

/**
 * Created by hanyangyang on 16/8/1.
 */

import com.csyy.common.StringDefaultValue;
import com.csyy.core.exception.CommonException;
import com.csyy.core.obj.Pagination;
import com.smt.smallfat.constant.Constant;
import com.smt.smallfat.constant.ResultConstant;
import com.smt.smallfat.exception.InfoEmptyException;
import com.smt.smallfat.po.SysAppVersion;
import com.smt.smallfat.service.base.VerService;
import com.smt.smallfat.web.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * App_version Controller
 */
@Controller
@RequestMapping("/backend/version")
public class VerController extends BaseController {
    @Autowired
    private VerService verService;
    /**
     * 增加APP_version
     * @param request
     * @param response
     */
//  @RequestMapping(value ="addSysVer",method = RequestMethod.POST)
    @RequestMapping("addSysVer")
    public void addSysVer(HttpServletRequest request, HttpServletResponse response){
//      Map<String,Object> param = nullAbleValidation(request,"","",);
        Map<String,Object> param = (Map<String,Object>) validation(request, new Validator() {
            @Override
            public Object valid(Map<String, Object> map) {
                if (StringUtils.isEmpty(map.get("sysType"))||StringUtils.isEmpty(map.get("versionNo"))||StringUtils.isEmpty(map.get("appType"))
                   ||StringUtils.isEmpty(map.get("title"))||StringUtils.isEmpty(map.get("status"))||StringUtils.isEmpty(map.get("memo"))
                   ||StringUtils.isEmpty(map.get("isForce"))) {
                    throw new InfoEmptyException();
                }
                //校验是否强制更新
                int isForce =   Integer.parseInt(String.valueOf(map.get("isForce")));
                if (isForce== Constant.FORCE&&StringUtils.isEmpty(map.get("leftButton"))){
                    throw new InfoEmptyException();
                }
                if (isForce==Constant.NOT_FORCE&&(StringUtils.isEmpty(map.get("leftButton"))||StringUtils.isEmpty(map.get("rightButton")))){
                    throw new InfoEmptyException();
                }
                //如果是安卓系统,则添加下载地址
                 int sysType =   Integer.parseInt(String.valueOf(map.get("sysType")));
                    if (sysType == Constant.ANDROID && StringUtils.isEmpty(map.get(SysAppVersion.FIELD_DOWNLOAD_ADDRESS))){
                        throw new CommonException(ResultConstant.SysAppVersion.DOWNADDRESS_IS_NOT_FOUND);}
//            try{ int sysType =   Integer.parseInt(String.valueOf(map.get("sysType")));
//                if (sysType == Constant.ANDROID && StringUtils.isEmpty(map.get(SysAppVersion.FIELD_DOWNLOAD_ADDRESS))){
//                    throw new InfoEmptyException();}}
//            catch(Exception e){throw new CommonException(ResultConstant.SysAppVersion.DOWNADDRESS_IS_NOT_FOUND);}
                return map;
            }
        });
//      Map<String,Object> param1 = getRequestParams(request);
        SysAppVersion sysAppVersion = verService.addSysVer(param);
        printWriter(response,successResultJSON(sysAppVersion));
    }
    /**
     * 删除APP_version
     * @param request
     * @param response
     */
    @RequestMapping("delSysVer")
    public void delSysVer(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> param = getRequestParams(request);
        int id = new IdValid().getValidId(param);
        verService.delSysVer(id);
        printWriter(response,successResultJSON("删除成功"));
    }
    /**
     * 修改APP_version
     * @param request
     * @param response
     */
    @RequestMapping("updateSysVer")
    public void updateSysVer(HttpServletRequest request,HttpServletResponse response){
        Map<String,Object> param = getRequestParams(request);
        int id = new IdValid().getValidId(param);
        SysAppVersion sysAppVersion = verService.updateSysVer(id,param);
        printWriter(response,successResultJSON(sysAppVersion));
    }
    /**
     * 根据uuid查询APP_version
     * @param request
     * @param response
     */
    @RequestMapping("getSysVerByUUID")
    public void getSysVer(HttpServletRequest request,HttpServletResponse response){
        Map<String,Object> param = getRequestParams(request);
        String uuid = new UUIDValid().getValidUUID(param);
        SysAppVersion sysAppVersion = verService.getSysVer(uuid);
        printWriter(response,successResultJSON(sysAppVersion));
    }
    /**
     * 根据id查询APP_version
     * @param request
     * @param response
     */
    @RequestMapping("getSysVerByID")
    public void getSysVerByID(HttpServletRequest request,HttpServletResponse response){
        Map<String,Object> param = getRequestParams(request);
        int id = new IdValid().getValidId(param);
        SysAppVersion sysAppVersion = verService.getSysVerByID(id);
        printWriter(response,successResultJSON(sysAppVersion));
    }

    /**
     * 根据版本号查询APP_version
     * @param request
     * @param response
     */
    @RequestMapping("getSysVerByVerNo")
    public void getSysVerByVerNo(HttpServletRequest request,HttpServletResponse response){
        Map<String,Object> param = (Map<String,Object>) validation(request, new Validator() {
            @Override
            public Object valid(Map<String, Object> map) {
                if (StringUtils.isEmpty(map.get("versionNo"))||StringUtils.isEmpty(map.get("sysType"))){
                    throw new InfoEmptyException();
                }
                return map;
            }
        });
//      校验版本号非空的另一种方法
//      Map<String, Object> param = getRequestParams(request);
//      int versionNo = StringDefaultValue.intValue(param.get("versionNo"));
        SysAppVersion sysAppVersion = verService.getSysVerByVerNo(param);
        printWriter(response,successResultJSON(sysAppVersion));
    }
    /**
     * 分页获得App_version
     * @param request
     * @param response
     */
    @RequestMapping("getSysVerByPage")
    public void getSysVerByPage(HttpServletRequest request,HttpServletResponse response){
        Map<String, Object> param = getRequestParams(request);
        int pageNo = StringDefaultValue.intValue(param.get(PAGE_NO));
        if (pageNo<1) {
            pageNo = 1;
        }
        int pageSize = StringDefaultValue.intValue(param.get(PAGE_SIZE));
        Pagination<SysAppVersion> ver = verService.getSysVerByPage(param,pageNo,pageSize);
        printWriter(response,successResultJSON(ver));
    }
}
