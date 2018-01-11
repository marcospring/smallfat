package com.smt.smallfat.service.base.impl;

import com.csyy.common.StringDefaultValue;
import com.csyy.core.apisupport.impl.BaseServiceImpl;
import com.csyy.core.datasource.param.Param;
import com.csyy.core.datasource.param.ParamBuilder;
import com.csyy.core.exception.CommonException;
import com.csyy.core.obj.Pagination;
import com.csyy.core.utils.CommonBeanUtils;
import com.smt.smallfat.constant.Constant;
import com.smt.smallfat.constant.ResultConstant;
import com.smt.smallfat.po.FatSuggest;
import com.smt.smallfat.service.base.SuggestService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SuggestServiceImpl extends BaseServiceImpl implements SuggestService{
    @Override
    public FatSuggest addSuggest(Map<String, Object> param) {
        FatSuggest suggest = CommonBeanUtils.transMap2BasePO(param,FatSuggest.class);
        suggest = factory.getCacheWriteDataSession().save(FatSuggest.class,suggest);
        return suggest;
    }

    @Override
    public void deleteSuggest(int id) {
        factory.getCacheWriteDataSession().physicalDelete(FatSuggest.class,id);
    }

    @Override
    public FatSuggest updateSuggest(Map<String, Object> param) {
        int id = StringDefaultValue.intValue(param.get(param.get(FatSuggest.FIELD_ID)));
        FatSuggest suggest = getSuggestById(id);
        suggest = CommonBeanUtils.transMap2BasePO(param,suggest);
        factory.getCacheWriteDataSession().update(FatSuggest.class,suggest);
        return suggest;
    }

    @Override
    public FatSuggest getSuggestById(int id) {
        FatSuggest suggest = factory.getCacheReadDataSession().querySingleResultById(FatSuggest.class,id);
        if(suggest == null)
            throw new CommonException(ResultConstant.Suggest.SUGGEST_IS_NULL);
        return suggest;
    }

    @Override
    public FatSuggest getSuggestByUUID(String uuid) {
        FatSuggest suggest = factory.getCacheReadDataSession().querySingleResultByUUID(FatSuggest.class,uuid);
        if(suggest == null)
            throw new CommonException(ResultConstant.Suggest.SUGGEST_IS_NULL);
        return suggest;
    }

    @Override
    public Pagination<FatSuggest> paginationSuggest(Map<String, Object> param) {
        int pageNo = StringDefaultValue.intValue(param.get(Constant.PAGE_NO));
        int pageSize = StringDefaultValue.intValue(param.get(Constant.PAGE_SIZE));
        Param params = ParamBuilder.getInstance().getParam().add(param);
        Pagination<FatSuggest> page = queryClassPagination(FatSuggest.class,params,pageNo,pageSize);
        return page;
    }
}
