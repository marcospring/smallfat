package com.smt.smallfat.service.base.impl;

import com.csyy.common.JSONUtils;
import com.csyy.common.StringDefaultValue;
import com.csyy.core.apisupport.impl.BaseServiceImpl;
import com.csyy.core.datasource.param.*;
import com.csyy.core.exception.CommonException;
import com.csyy.core.obj.Pagination;
import com.csyy.core.utils.CommonBeanUtils;
import com.smt.smallfat.constant.Constant;
import com.smt.smallfat.constant.ResultConstant;
import com.smt.smallfat.po.SysAppVersion;
import com.smt.smallfat.service.base.VerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * APP_version接口实现
 * Created by hanyangyang on 16/8/1.
 */
@Service("VerService")
public class VerServiceImpl extends BaseServiceImpl implements VerService {

    Logger logger = LoggerFactory.getLogger(VerServiceImpl.class);

    @Override
    @Transactional
    public SysAppVersion addSysVer(Map<String,Object> param){
        SysAppVersion sysAppVersion;
        //校验相同app类型,系统类型和版本号的版本是否已存在
        Param param1 = ParamBuilder.getInstance().getParam()
                .add(ParamBuilder.nv(SysAppVersion.FIELD_SYS_TYPE, StringDefaultValue.StringValue(param.get("sysType"))))
                .add(ParamBuilder.nv(SysAppVersion.FIELD_VERSION_NO, StringDefaultValue.StringValue(param.get("versionNo"))))
                .add(ParamBuilder.nv(SysAppVersion.FIELD_APP_TYPE, StringDefaultValue.StringValue(param.get("appType"))));
        long l = factory.getCacheReadDataSession().queryListResultCount(SysAppVersion.class, param1);
        if (l>0){
            throw new CommonException(ResultConstant.SysAppVersion.SYSAPPVER_IS_EXISTS);//需要添加异常类型(小赵讲过)
        }
        //校验字数
        String title = param.get("title").toString();
        String memo = param.get("memo").toString();
        String leftButton = param.get("leftButton").toString();
        String rightButton = param.get("rightButton").toString();
        if (title.length()>10||memo.length()>120||leftButton.length()>6||rightButton.length()>6){
            throw new CommonException(ResultConstant.SysAppVersion.WORDS_IS_FULL);
        }
        sysAppVersion = CommonBeanUtils.transMap2BasePO(param,SysAppVersion.class);
//        //显示操作创建和更新用户
//        StringBuilder builder = new StringBuilder(
//                Constant.LOGIN_SESSION_STUFF);
//        String maiyaSid = builder.append(param.get("maiyaSid")).toString();
//        String json = factory.getRedisSessionFactory().getSession()
//                .getData(maiyaSid);
//        if (!StringUtils.isEmpty(json)) {
//            SessionSystemUserVo sessionSystemUserVo = JSONUtils.fromJson(json,SessionSystemUserVo.class);
//            SysUser sysUser=sessionSystemUserVo.getUser();
//            sysAppVersion.setCreateUser(sysUser.getId());
//            sysAppVersion.setUpdateUser(sysUser.getId());
//        }
        SysAppVersion save = factory.getCacheWriteDataSession().save(SysAppVersion.class, sysAppVersion);
        return save;
    }
    @Override
    public void delSysVer(int id){
        Param param = ParamBuilder.getInstance().getParam().add(ParamBuilder.nv(SysAppVersion.FIELD_ID,id));
        SysAppVersion sysAppVersion = factory.getCacheReadDataSession().querySingleResultById(SysAppVersion.class, id);
        if (sysAppVersion==null){
            throw new CommonException(ResultConstant.SysAppVersion.SYSAPPVER_IS_NOT_FOUND);
        }
        int i = factory.getCacheWriteDataSession().logicDelete(SysAppVersion.class, param);
        if (i<1){
            throw new CommonException(ResultConstant.SysAppVersion.DELETE_SYSAPPVER_IS_FAILD);
        }
    }
    @Override
    public SysAppVersion updateSysVer(int id,Map<String,Object> param){
        SysAppVersion sysAppVersion;
        sysAppVersion = factory.getCacheReadDataSession().querySingleResultById(SysAppVersion.class, id);
        if (sysAppVersion==null){
            throw new CommonException(ResultConstant.SysAppVersion.SYSAPPVER_IS_NOT_FOUND);
        }
        sysAppVersion = CommonBeanUtils.transMap2BasePO(param,sysAppVersion);
        //显示操作更新用户
//        StringBuilder builder = new StringBuilder(
//                Constant.LOGIN_SESSION_STUFF);
//        String maiyaSid = builder.append(param.get("maiyaSid")).toString();
//        String json = factory.getRedisSessionFactory().getSession()
//                .getData(maiyaSid);
//        if (!StringUtils.isEmpty(json)) {
//            SessionSystemUserVo sessionSystemUserVo = JSONUtils.fromJson(json,SessionSystemUserVo.class);
//            SysUser sysUser=sessionSystemUserVo.getUser();
//            sysAppVersion.setUpdateUser(sysUser.getId());
//        }
        int update = factory.getCacheWriteDataSession().update(SysAppVersion.class, sysAppVersion);
        if (update<1) {
            throw new CommonException(ResultConstant.SysAppVersion.UPDATE_SYSAPPVER_IS_FAILD);
        }
        return sysAppVersion;
    }
    @Override
    public SysAppVersion getSysVer(String uuid){
        SysAppVersion sysAppVersion = factory.getCacheReadDataSession().querySingleResultByUUID(SysAppVersion.class, uuid);
        if (sysAppVersion==null){
            throw new CommonException(ResultConstant.SysAppVersion.SYSAPPVER_IS_NOT_FOUND);
        }
        return sysAppVersion;
    }
    @Override
    public SysAppVersion getSysVerByID(int id){
        SysAppVersion sysAppVersion = factory.getCacheReadDataSession().querySingleResultById(SysAppVersion.class,id);
        if (sysAppVersion==null){
            throw new CommonException(ResultConstant.SysAppVersion.SYSAPPVER_IS_NOT_FOUND);
        }
        return sysAppVersion;
    }
    @Override
    public SysAppVersion getSysVerByVerNo(Map<String,Object> param){
        Param param1 = ParamBuilder.getInstance().getParam()
                .add(ParamBuilder.nv(SysAppVersion.FIELD_VERSION_NO, StringDefaultValue.StringValue(param.get("versionNo"))));
        SysAppVersion sysAppVersion = factory.getCacheReadDataSession().querySingleResultByParams(SysAppVersion.class, param1);
        if (sysAppVersion==null){
            throw new CommonException(ResultConstant.SysAppVersion.SYSAPPVER_IS_NOT_FOUND);
        }
        return sysAppVersion;
    }
    @Override
    public Map<String,Object> getSysVerBySysType(Map<String,Object> param) {
        SysAppVersion resultVersion = null;
        //拼接SQL查出版本库中相应系统状态为启用的最后添加版本
        CustomSQL where = SQLCreator.where();
        
        //-----------常先添加--------------
//        int appType = param.get(SysAppVersion.FIELD_APP_TYPE) != null ? Integer.parseInt(param.get(SysAppVersion.FIELD_APP_TYPE) + "") : 1;
//        where.cloumn(SysAppVersion.FIELD_APP_TYPE).operator(ESQLOperator.EQ).v(appType).operator(ESQLOperator.AND).
        //-----------常先添加--------------
        
        
       where.
                cloumn(SysAppVersion.FIELD_SYS_TYPE).operator(ESQLOperator.EQ).v(param.get(SysAppVersion.FIELD_SYS_TYPE))
                .operator(ESQLOperator.AND).cloumn(SysAppVersion.FIELD_STATUS).operator(ESQLOperator.EQ).v(1).operator(ESQLOperator.ORDER_BY)
                .cloumn(SysAppVersion.FIELD_CREATE_TIME).operator(ESQLOperator.DESC).operator(ESQLOperator.LIMIT).v(1);
        List<SysAppVersion> sysAppVersions = factory.getCacheReadDataSession().queryListResultByWhere(SysAppVersion.class,where);
        if (sysAppVersions==null||sysAppVersions.size()<1){
            throw new CommonException(ResultConstant.SysAppVersion.SYSAPPVER_IS_NOT_FOUND);
        }
        //获取版本库中该条对象
        SysAppVersion version = sysAppVersions.get(0);
        resultVersion = version;
        int isForce = version.getIsForce();
        String localVersion = version.getVersionNo();

        //获取线上版本号
        String onlineVersion = param.get(SysAppVersion.FIELD_VERSION_NO).toString();

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("version", resultVersion);
        //调用isAppNewVersion方法判断版本库和线上版本大小
        boolean result = isAppNewVersion(localVersion,onlineVersion);
        if (result){
            //result为true,则tag=0,不更新
            resultMap.put("tag", "0");
        }else{
            if (isForce == Constant.FORCE) {
                //2,强制更新
                resultMap.put("tag", "2");
            } else {
                //1,更新
                resultMap.put("tag", "1");
            }
        }
        return resultMap;
    }
    /**
    * 判断是否为最新版本方法 将版本号根据.切分为int数组 比较
    *
    * @param localVersion
    *            本地版本号
    * @param onlineVersion
    *            线上版本号
    * @return
    */
    private boolean isAppNewVersion(String localVersion, String onlineVersion)
    {
        //在转换为数组前,判断版本号是否有v(例如: v1.0.1),若有则去掉
        String regex1= "V";
        String regex= "v";
        String replacement = "";
        localVersion = localVersion.replaceAll(regex,replacement);
        onlineVersion = onlineVersion.replaceAll(regex,replacement);

        localVersion = localVersion.replaceAll(regex1,replacement);
        onlineVersion = onlineVersion.replaceAll(regex1,replacement);

        if (localVersion.equals(onlineVersion))
        {
            return true;
        }

        String[] localArray = localVersion.split("\\.");
        String[] onlineArray = onlineVersion.split("\\.");

        int length = localArray.length < onlineArray.length ? localArray.length : onlineArray.length;

        for (int i = 0; i < length; i++)
        {
            if (Integer.parseInt(onlineArray[i]) > Integer.parseInt(localArray[i]))
            {
                return true;
            }
            else if (Integer.parseInt(onlineArray[i]) < Integer.parseInt(localArray[i]))
            {
                return false;
            }
            // 相等 比较下一组值
        }
        return true;
    }
    @Override
    public Pagination<SysAppVersion> getSysVerByPage(Map<String,Object> param, int pageNo, int pageSize){
        Param param1 = ParamBuilder.getInstance().getParam().add(param);
        //根据更新时间逆序
        CustomSQL where = SQLCreator.where();
        if (!StringDefaultValue.isEmpty(param.get(SysAppVersion.FIELD_APP_TYPE))){
            int appType =   Integer.parseInt(String.valueOf(param.get("appType")));
//            if(appType==Constant.MAIYA_APP){
                where.cloumn(SysAppVersion.FIELD_APP_TYPE).operator(ESQLOperator.EQ).v(param.get(SysAppVersion.FIELD_APP_TYPE))
                        .operator(ESQLOperator.AND);
//            }
        }
        if (!StringDefaultValue.isEmpty(param.get(SysAppVersion.FIELD_STATUS))){
            int status =   Integer.parseInt(String.valueOf(param.get("status")));
            if(status==Constant.SHI||status==Constant.FOU){
               where .cloumn(SysAppVersion.FIELD_STATUS).operator(ESQLOperator.EQ).v(param.get(SysAppVersion.FIELD_STATUS))
                       .operator(ESQLOperator.AND);
          }
        }
        where.cloumn("1=1").operator(ESQLOperator.ORDER_BY).cloumn(SysAppVersion.FIELD_UPDATE_TIME).operator(ESQLOperator.DESC);
//        .operator(ESQLOperator.LIMIT).v((pageNo - 1) * pageSize).operator(ESQLOperator.COMMA).v(pageSize);
//        .cloumn("desc").cloumn("limit").v((pageNo - 1) * pageSize).operator(ESQLOperator.COMMA).v(pageSize);
//      分页显示
        Pagination<SysAppVersion> appVersionPag = queryClassPagination(SysAppVersion.class,where,pageNo,pageSize);
        if (appVersionPag==null||appVersionPag.getRecordsTotal()==0){
            throw new CommonException(ResultConstant.SysAppVersion.SYSAPPVER_IS_NULL);
        }
        return appVersionPag;
    }
}
