package com.smt.smallfat.service.system;


import com.csyy.core.result.Result;
import com.smt.smallfat.po.SysDicItem;
import com.smt.smallfat.vo.SysDicItemVo;

import java.util.List;
import java.util.Map;

/**
 * 字典项服务接口
 * Created by zhangkui on 16/5/14.
 */
public interface SysDicItemService {
    /**
     * 添加字典项
     *
     * @param param
     * @return
     */
    SysDicItem addSysDicItem(Map<String, Object> param);

    /**
     * 删除字典项
     *
     * @param id
     */
    void deleteSysDicItem(int id);

    /**
     * 更新字典项
     *
     * @param param
     * @return
     */
    SysDicItemVo updateSysDicItem(Map<String, Object> param);

    /**
     * 根据字典编码获取字典项列表
     *
     * @param code
     * @return
     */
    List<SysDicItemVo> getDicItemByDicCode(String code);

    /**
     * 根据ID获取字典项
     *
     * @param id
     * @return
     */
    SysDicItemVo getDicItemById(int id);

    /**
     * 根据UUID获取字典项
     *
     * @param uuid
     * @return
     */
    SysDicItemVo getDicItemByUUID(String uuid);

    /**
     * 根据字典ID获取字典项列表
     *
     * @param id
     * @return
     */
    List<SysDicItemVo> getDicItemByDicId(int id);

    /**
     * 根据字典UUID获取字典项列表
     *
     * @param uuid
     * @return
     */
    List<SysDicItemVo> getDicItemByDicUUID(String uuid);

    /**
     * 根据字典父类code添加该父类的子类
     *
     * @param param
     * @return
     */
    SysDicItemVo addDicItemByDicCode(Map<String, Object> param);

    /**
     * 根据父字典code和子字典id获取子字典
     * dicCode
     * id
     *
     * @param param
     * @return
     */
    SysDicItemVo getDicItemByDicCodeAndItemName(Map<String, Object> param);

    /**
     * 根据父字典code和dic_item_value获取子字典
     *
     * @param dicCode
     * @param dicItemValue
     * @return
     */
    SysDicItemVo getDicItemByDicCodeAndItemValue(String dicCode, String dicItemValue);

}
