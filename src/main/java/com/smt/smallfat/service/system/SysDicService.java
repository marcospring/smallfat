package com.smt.smallfat.service.system;

import com.csyy.core.exception.BusinessException;
import com.csyy.core.result.Result;
import com.smt.smallfat.po.SysDic;
import com.smt.smallfat.vo.DicTreeVo;
import com.smt.smallfat.vo.SysDicVo;

import java.util.List;
import java.util.Map;

/**
 * Created by xindongwang on 17/3/11.
 */
public interface SysDicService {

    /**
     * 添加字典
     *
     * @param param
     * @return
     */
    SysDic addDic(Map<String, Object> param);

    /**
     * 删除字典
     */
    void deleteDic(int id);

    /**
     * 更新字典
     *
     * @param param
     * @param param
     * @return
     */
    SysDicVo updateDic(Map<String, Object> param) throws BusinessException;

    /**
     * 根据UUID查询字典
     *
     * @param uuid
     * @return
     */
    SysDicVo getDicByUUID(String uuid) throws BusinessException;

    /**
     * 根据ID查询字典
     *
     * @param id
     * @return
     */
    SysDicVo getDicById(int id) throws BusinessException;

    /**
     * 根据CODE获取字典数据
     *
     * @param code
     * @return
     * @throws BusinessException
     */
    SysDicVo getDicByCode(String code) throws BusinessException;

    /**
     * 获取dic的tree
     *
     * @return
     */
    DicTreeVo getDicTree(Map<String, Object> param);

    /**
     * 根据爷爷获取父类集合
     *
     * @param code
     */
    List<SysDicVo> getDicListByParentId(String code);

}
