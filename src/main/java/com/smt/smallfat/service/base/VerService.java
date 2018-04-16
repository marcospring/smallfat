package com.smt.smallfat.service.base;

import com.csyy.core.exception.BusinessException;
import com.csyy.core.obj.Pagination;
import com.smt.smallfat.po.SysAppVersion;

import java.util.Map;

/**
 * Created by hanyangyang on 16/8/1.
 */
public interface VerService {
    /**
     * 添加App_version
     * @param param
     * @return
     * @throws BusinessException
     */
    SysAppVersion addSysVer(Map<String, Object> param) throws BusinessException;

    /**
     * 删除App_version
     * @param id
     * @throws BusinessException
     */
    void delSysVer(int id) throws BusinessException;

    /**
     * 修改App_version
     * @param id
     * @param param
     * @return
     * @throws BusinessException
     */
    SysAppVersion updateSysVer(int id, Map<String, Object> param) throws BusinessException;

    /**
     * 通过uuid查询App_version
     * @param uuid
     * @return
     * @throws BusinessException
     */
    SysAppVersion getSysVer(String uuid) throws BusinessException;

    /**
     * 通过ID查询App_version
     * @param id
     * @return
     * @throws BusinessException
     */
    SysAppVersion getSysVerByID(int id) throws BusinessException;

    /**
     * 通过版本号查询App_version
     * @param param
     * @return
     * @throws BusinessException
     */
    SysAppVersion getSysVerByVerNo(Map<String, Object> param) throws BusinessException;

    /**
     * 通过系统类型查询最新App_versionNo
     * @param param
     * @return
     * @throws BusinessException
     */
    Map<String,Object> getSysVerBySysType(Map<String, Object> param) throws BusinessException;

    /**
     * 分页获得App_version
     * @param param
     * @param pageNo
     * @param pageSize
     * @return
     * @throws BusinessException
     */
    Pagination<SysAppVersion> getSysVerByPage(Map<String, Object> param, int pageNo, int pageSize)  throws BusinessException;
}
