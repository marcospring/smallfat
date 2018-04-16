package com.smt.smallfat.service.system.impl;

import com.csyy.common.StringDefaultValue;
import com.csyy.core.apisupport.impl.BaseServiceImpl;
import com.csyy.core.datasource.param.Param;
import com.csyy.core.datasource.param.ParamBuilder;
import com.csyy.core.exception.CommonException;
import com.csyy.core.utils.CommonBeanUtils;
import com.smt.smallfat.constant.ResultConstant;
import com.smt.smallfat.po.SysDic;
import com.smt.smallfat.po.SysDicItem;
import com.smt.smallfat.service.system.SysDicService;
import com.smt.smallfat.utils.TransUtil;
import com.smt.smallfat.vo.DicTreeVo;
import com.smt.smallfat.vo.SysDicVo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by xindongwang on 17/3/12.
 */
@Service
public class SysDicServiceImpl extends BaseServiceImpl implements SysDicService {

    @Override
    public SysDic addDic(Map<String, Object> param) {
        SysDic dic = CommonBeanUtils.transMap2BasePO(param, SysDic.class);
        if (dic.getParentId() != 0) {
            if (dic == null)
                throw new CommonException(ResultConstant.SysDicResult.SYSDIC_IS_NULL);
            SysDic sysDic = factory.getCacheReadDataSession().querySingleResultById(SysDic.class, dic.getParentId());
            if (StringDefaultValue.isEmpty(sysDic)) {
                throw new CommonException(ResultConstant.SysDicResult.SYSDIC_IS_NULL);
            }
        }
        dic = factory.getCacheWriteDataSession().save(SysDic.class, dic);
        return dic;
    }

    @Override
    public void deleteDic(int id) {
        Param params = ParamBuilder.getInstance().getParam();
        params.add(ParamBuilder.nv(SysDicItem.FIELD_DIC_ID, id));
        List<SysDicItem> list = factory.getCacheReadDataSession().queryListResult(SysDicItem.class, params);
        ;
        if (list != null && list.size() > 0)
            throw new CommonException(ResultConstant.SysDicResult.SYSDIC_HAS_ITEMS);
        params.clean();
        params = ParamBuilder.getInstance().getParam().add(ParamBuilder.nv(SysDic.FIELD_ID, id));
        factory.getCacheWriteDataSession().logicDelete(SysDic.class, params);
    }

    @Override
    public SysDicVo updateDic(Map<String, Object> param) {
        int dicId = StringDefaultValue.intValue(param.get(SysDic.FIELD_ID));
        SysDic dic = factory.getCacheReadDataSession().querySingleResultById(SysDic.class, dicId);
        if (dic == null)
            throw new CommonException(ResultConstant.SysDicResult.SYSDIC_IS_NULL);
        dic = CommonBeanUtils.transMap2BasePO(param, dic);
        factory.getCacheWriteDataSession().update(SysDic.class, dic);
        return CommonBeanUtils.getBeanBySameProperty(SysDicVo.class, dic);
    }

    @Override
    public SysDicVo getDicByUUID(String uuid) {
        SysDic sysDic = factory.getCacheReadDataSession().querySingleResultByUUID(SysDic.class, uuid);
        if (sysDic == null)
            throw new CommonException(ResultConstant.SysDicResult.SYSDIC_IS_NULL);
        SysDicVo sysDicVo = CommonBeanUtils.getBeanBySameProperty(SysDicVo.class, sysDic);
        return sysDicVo;
    }

    @Override
    public SysDicVo getDicById(int id) {
        SysDic sysDic = factory.getCacheReadDataSession().querySingleResultById(SysDic.class, id);
        if (sysDic == null)
            throw new CommonException(ResultConstant.SysDicResult.SYSDIC_IS_NULL);
        SysDicVo sysDicVo = CommonBeanUtils.getBeanBySameProperty(SysDicVo.class, sysDic);
        return sysDicVo;
    }

    @Override
    public SysDicVo getDicByCode(String code) {
        Param params = ParamBuilder.getInstance().getParam();
        params.add(ParamBuilder.nv(SysDic.FIELD_DIC_CODE, code));
        SysDic dic = factory.getCacheReadDataSession().querySingleResultByParams(SysDic.class, params);
        if (dic == null)
            throw new CommonException(ResultConstant.SysDicResult.SYSDIC_IS_NULL);

        SysDicVo sysDicVo = CommonBeanUtils.getBeanBySameProperty(SysDicVo.class, dic);
        return sysDicVo;
    }

    @Override
    public DicTreeVo getDicTree(Map<String, Object> param) {
        int id;
        DicTreeVo dicTreeVo;
        if (StringDefaultValue.isEmpty(param.get(SysDic.FIELD_ID))) {
            dicTreeVo = new DicTreeVo(0);
        } else {
            id = StringDefaultValue.intValue(param.get(SysDic.FIELD_ID));
            SysDic sysDic = factory.getCacheReadDataSession().querySingleResultById(SysDic.class, id);
            if (sysDic == null)
                throw new CommonException(ResultConstant.SysDicResult.SYSDIC_IS_NULL);
            dicTreeVo = new DicTreeVo(sysDic.getId(), sysDic.getDicCode(), sysDic.getDicName());
        }
        DicTreeVo dicTree = getDicTree(dicTreeVo);
        return dicTree;
    }

    private DicTreeVo getDicTree(DicTreeVo vo) {
        Param param = ParamBuilder.getInstance().getParam().add(ParamBuilder.nv(SysDic.FIELD_PARENT_ID, vo
                .getId()));
        List<SysDic> sysDicList = factory.getCacheReadDataSession().queryListResult(SysDic.class, param);
        if (sysDicList != null && sysDicList.size() > 0) {
            List<DicTreeVo> tree = new ArrayList<>();
            for (SysDic sysDic : sysDicList) {
                DicTreeVo treeVO = new DicTreeVo(sysDic.getId(), sysDic.getDicCode(), sysDic.getDicName());
                treeVO = getDicTree(treeVO);
                tree.add(treeVO);
                vo.setChildren(tree);
            }
            vo.setChildren(tree);
        }
        return vo;
    }


    @Override
    public List<SysDicVo> getDicListByParentId(String code) {
        Param param = ParamBuilder.getInstance().getParam();
        param.add(ParamBuilder.nv(SysDic.FIELD_DIC_CODE, code));
        SysDic dic = factory.getCacheReadDataSession().querySingleResultByParams(SysDic.class, param);
        if (dic == null)
            throw new CommonException(ResultConstant.SysDicResult.SYSDIC_IS_NULL);
        List<SysDic> dicListByParentId = getDicListByParentId(dic.getId());
        List<SysDicVo> sysDicVoList = TransUtil.transPoListToVoList(dicListByParentId, SysDicVo.class);
        return sysDicVoList;
    }

    private List<SysDic> getDicListByParentId(int parentId) {
        Param param = ParamBuilder.getInstance().getParam().add(ParamBuilder.nv(SysDic.FIELD_PARENT_ID, parentId));
        return factory.getCacheReadDataSession().queryListResult(SysDic.class, param);
    }
}
