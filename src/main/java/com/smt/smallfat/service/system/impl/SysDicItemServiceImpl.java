package com.smt.smallfat.service.system.impl;

import com.csyy.common.StringDefaultValue;
import com.csyy.core.apisupport.impl.BaseServiceImpl;
import com.csyy.core.datasource.param.Param;
import com.csyy.core.datasource.param.ParamBuilder;
import com.csyy.core.exception.CommonException;
import com.csyy.core.utils.CommonBeanUtils;
import com.smt.smallfat.constant.ResultConstant;
import com.smt.smallfat.exception.InfoEmptyException;
import com.smt.smallfat.po.SysDic;
import com.smt.smallfat.po.SysDicItem;
import com.smt.smallfat.service.system.SysDicItemService;
import com.smt.smallfat.utils.TransUtil;
import com.smt.smallfat.vo.SysDicItemVo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by xindongwang on 17/3/12.
 */
@Service
public class SysDicItemServiceImpl extends BaseServiceImpl implements SysDicItemService {

    @Override
    public SysDicItem addSysDicItem(Map<String, Object> param) {
        int id = StringDefaultValue.intValue(param.get(SysDicItem.FIELD_DIC_ID));
        String value = StringDefaultValue.StringValue(param.get(SysDicItem.FIELD_DIC_ITEM_VALUE));
        SysDicItem item;
        Param params = ParamBuilder.getInstance().getParam().add(ParamBuilder.nv(SysDicItem.FIELD_DIC_ID, StringDefaultValue.StringValue(param.get(SysDicItem.FIELD_DIC_ID))))
                .add(ParamBuilder.nv(SysDicItem.FIELD_DIC_ITEM_VALUE, StringDefaultValue.StringValue(param.get(SysDicItem.FIELD_DIC_ITEM_VALUE))));
        List<SysDicItem> dicItemByParam = factory.getCacheReadDataSession().queryListResult(SysDicItem.class,
                params);
        if (dicItemByParam.size() == 0) {
            item = CommonBeanUtils.transMap2BasePO(param, SysDicItem.class);
            item = factory.getCacheWriteDataSession().save(SysDicItem.class, item);
        } else {
            throw new CommonException(ResultConstant.SysDicResult.SYSDICITEM_IS_EXIST);
        }
        return item;
    }

    @Override
    public void deleteSysDicItem(int id) {
        Param param = ParamBuilder.getInstance().getParam().add(ParamBuilder.nv(SysDicItem.FIELD_ID, id));
        factory.getCacheWriteDataSession().logicDelete(SysDicItem.class, param);
    }

    @Override
    public SysDicItemVo updateSysDicItem(Map<String, Object> param) {
        int id = StringDefaultValue.intValue(param.get(SysDicItem.FIELD_ID));
        String value = StringDefaultValue.StringValue(param.get(SysDicItem.FIELD_DIC_ITEM_VALUE));
//        int dicItemid = StringDefaultValue.intValue(param.get(SysDicItem.FIELD_ID));
        SysDicItem sysDicItem = factory.getCacheReadDataSession().querySingleResultById(SysDicItem.class, id);
        if (sysDicItem == null)
            throw new CommonException(ResultConstant.SysDicResult.SYSDICITEM_IS_NULL);
        Param params = ParamBuilder.getInstance().getParam().add(ParamBuilder.nv(SysDicItem.FIELD_DIC_ID, sysDicItem.getDicId()))
                .add(ParamBuilder.nv(SysDicItem.FIELD_DIC_ITEM_VALUE, value));
        List<SysDicItem> dicItemByParam = factory.getCacheReadDataSession().queryListResult(SysDicItem.class, params);
        if (dicItemByParam.size() != 0) {
            if (dicItemByParam.stream().filter(p -> p.getId() != id).count() > 0) {
                throw new CommonException(ResultConstant.SysDicResult.SYSDICITEM_IS_EXIST);
            }
        }
        sysDicItem = CommonBeanUtils.transMap2BasePO(param, sysDicItem);
        factory.getCacheWriteDataSession().update(SysDicItem.class, sysDicItem);
        return CommonBeanUtils.getBeanBySameProperty(SysDicItemVo.class, sysDicItem);
    }

    @Override
    public List<SysDicItemVo> getDicItemByDicCode(String code) {
        Param param = ParamBuilder.getInstance().getParam();
        param.add(ParamBuilder.nv(SysDic.FIELD_DIC_CODE, code));
        SysDic dic = factory.getCacheReadDataSession().querySingleResultByParams(SysDic.class, param);
        if (dic == null)
            throw new CommonException(ResultConstant.SysDicResult.SYSDIC_IS_NULL);
        Param params = ParamBuilder.getInstance().getParam();
        params.add(ParamBuilder.nv(SysDicItem.FIELD_DIC_ID, dic.getId()));
        List<SysDicItem> dicItems = factory.getCacheReadDataSession().queryListResult(SysDicItem.class, params);
        List<SysDicItemVo> sysDicItemVos = TransUtil.transPoListToVoList(dicItems, SysDicItemVo.class);
        return sysDicItemVos;
    }

    @Override
    public SysDicItemVo getDicItemById(int id) {
        SysDicItem sysDicItem = factory.getCacheReadDataSession().querySingleResultById(SysDicItem.class, id);
        if (sysDicItem == null)
            throw new CommonException(ResultConstant.SysDicResult.SYSDICITEM_IS_NULL);
        SysDicItemVo dicItemVo = CommonBeanUtils.getBeanBySameProperty(SysDicItemVo.class, sysDicItem);
        return dicItemVo;
    }

    @Override
    public SysDicItemVo getDicItemByUUID(String uuid) {
        SysDicItem dicItem = factory.getCacheReadDataSession().querySingleResultByUUID(SysDicItem.class, uuid);
        if (dicItem == null)
            throw new CommonException(ResultConstant.SysDicResult.SYSDICITEM_IS_NULL);
        SysDicItemVo dicItemVo = CommonBeanUtils.getBeanBySameProperty(SysDicItemVo.class, dicItem);
        return dicItemVo;
    }

    @Override
    public List<SysDicItemVo> getDicItemByDicId(int id) {
        Param params = ParamBuilder.getInstance().getParam();
        params.add(ParamBuilder.nv(SysDicItem.FIELD_DIC_ID, id));
        List<SysDicItem> dicItemList = factory.getCacheReadDataSession().queryListResult(SysDicItem.class, params);
        List sysDicItemVos = TransUtil.transPoListToVoList(dicItemList, SysDicItemVo.class);
        return sysDicItemVos;
    }

    @Override
    public List<SysDicItemVo> getDicItemByDicUUID(String uuid) {
        SysDic dic = factory.getCacheReadDataSession().querySingleResultByUUID(SysDic.class, uuid);
        if (dic == null)
            throw new CommonException(ResultConstant.SysDicResult.SYSDIC_IS_NULL);
        Param params = ParamBuilder.getInstance().getParam();
        params.add(ParamBuilder.nv(SysDicItem.FIELD_DIC_ID, dic.getId()));
        List<SysDicItem> dicItemList = factory.getCacheReadDataSession().queryListResult(SysDicItem.class, params);
        List sysDicItemVos = TransUtil.transPoListToVoList(dicItemList, SysDicItemVo.class);
        return sysDicItemVos;
    }

    @Override
    public SysDicItemVo addDicItemByDicCode(Map<String, Object> param) {
        if (!param.containsKey(SysDic.FIELD_DIC_CODE) || !param.containsKey(SysDicItem.FIELD_DIC_ITEM_NAME) || !param.containsKey(SysDicItem.FIELD_DIC_ITEM_VALUE)) {
            throw new InfoEmptyException();
        }
        String code = StringDefaultValue.StringValue(param.get(SysDic.FIELD_DIC_CODE));
        String value = StringDefaultValue.StringValue(param.get(SysDicItem.FIELD_DIC_ITEM_VALUE));
        Param param1 = ParamBuilder.getInstance().getParam();
        param1.add(ParamBuilder.nv(SysDic.FIELD_DIC_CODE, code));
        SysDic sysDic = factory.getCacheReadDataSession().querySingleResultByParams(SysDic.class, param1);
        if (sysDic == null)
            throw new CommonException(ResultConstant.SysDicResult.SYSDIC_IS_NULL);
        String name = StringDefaultValue.StringValue(param.get(SysDicItem.FIELD_DIC_ITEM_NAME));
        Param params = ParamBuilder.getInstance().getParam().add(ParamBuilder.nv(SysDicItem.FIELD_DIC_ID, sysDic.getId()))
                .add(ParamBuilder.nv(SysDicItem.FIELD_DIC_ITEM_VALUE, value));
        List<SysDicItem> dicItemByParam = factory.getCacheReadDataSession().queryListResult(SysDicItem.class,
                params);
        if (dicItemByParam.size() != 0) {
            throw new CommonException(ResultConstant.SysDicResult.SYSDICITEM_IS_EXIST);
        }
        param.clear();
        param.put(SysDicItem.FIELD_DIC_ITEM_NAME, name);
        param.put(SysDicItem.FIELD_DIC_ITEM_VALUE, value);
        param.put(SysDicItem.FIELD_DIC_ID, sysDic.getId());
        SysDicItem sysDicItem = CommonBeanUtils.transMap2BasePO(param, SysDicItem.class);
        sysDicItem = factory.getCacheWriteDataSession().save(SysDicItem.class, sysDicItem);
        return CommonBeanUtils.getBeanBySameProperty(SysDicItemVo.class, sysDicItem);
    }

    @Override
    public SysDicItemVo getDicItemByDicCodeAndItemName(Map<String, Object> param) {
        String code = StringDefaultValue.StringValue(param.get(SysDic.FIELD_DIC_CODE));
        String name = StringDefaultValue.StringValue(param.get(SysDicItem.FIELD_DIC_ITEM_NAME));
        Param params = ParamBuilder.getInstance().getParam();
        params.add(ParamBuilder.nv(SysDic.FIELD_DIC_CODE, code));
        SysDic dic = factory.getCacheReadDataSession().querySingleResultByParams(SysDic.class, params);
        if (dic == null)
            throw new CommonException(ResultConstant.SysDicResult.SYSDIC_IS_NULL);

        params.clean();
        params.add(ParamBuilder.nv(SysDicItem.FIELD_DIC_ITEM_NAME, name));
        SysDicItem itemByParam = factory.getCacheReadDataSession().querySingleResultByParams(SysDicItem.class,
                params);
        return CommonBeanUtils.getBeanBySameProperty(SysDicItemVo.class, itemByParam);
    }

    @Override
    public SysDicItemVo getDicItemByDicCodeAndItemValue(String dicCode, String dicItemValue) {
        Param param = ParamBuilder.getInstance().getParam();
        param.add(ParamBuilder.nv(SysDic.FIELD_DIC_CODE, dicCode));
        SysDic sysDic = factory.getCacheReadDataSession().querySingleResultByParams(SysDic.class, param);
        if (sysDic == null)
            throw new CommonException(ResultConstant.SysDicResult.SYSDIC_IS_NULL);
        Param params = ParamBuilder.getInstance().getParam().add(ParamBuilder.nv(SysDicItem.FIELD_DIC_ID, sysDic.getId()))
                .add(ParamBuilder.nv(SysDicItem.FIELD_DIC_ITEM_VALUE, dicItemValue));
        SysDicItem itemByParam = factory.getCacheReadDataSession().querySingleResultByParams(SysDicItem.class,
                params);
        return CommonBeanUtils.getBeanBySameProperty(SysDicItemVo.class, itemByParam);
    }

}
