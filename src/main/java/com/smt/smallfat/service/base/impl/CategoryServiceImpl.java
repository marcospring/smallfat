package com.smt.smallfat.service.base.impl;

import com.csyy.core.apisupport.impl.BaseServiceImpl;
import com.csyy.core.datasource.param.Param;
import com.csyy.core.datasource.param.ParamBuilder;
import com.csyy.core.exception.CommonException;
import com.csyy.core.utils.CommonBeanUtils;
import com.smt.smallfat.constant.ResultConstant;
import com.smt.smallfat.po.FatGoodsCategory;
import com.smt.smallfat.service.base.CategoryService;
import com.smt.smallfat.service.base.GoodsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImpl extends BaseServiceImpl implements CategoryService {
    @Override
    public FatGoodsCategory addCategory(Map<String, Object> param) {
        Param params = ParamBuilder.getInstance().getParam().add(ParamBuilder.nv(FatGoodsCategory
                .FIELD_CATEGORY_NAME, param.get(FatGoodsCategory.FIELD_CATEGORY_NAME)));
        FatGoodsCategory category = factory.getCacheReadDataSession().querySingleResultByParams(FatGoodsCategory
                .class, params);
        if (category != null)
            throw new CommonException(ResultConstant.Category.CATEGORY_NOT_NULL);
        category = CommonBeanUtils.transMap2BasePO(param, FatGoodsCategory.class);
        return factory.getCacheWriteDataSession().save(FatGoodsCategory.class, category);
    }

    @Override
    public void delCategory(int id) {
        id(FatGoodsCategory.class, id, ResultConstant.Category.CATEGORY_IS_NULL);
        factory.getCacheWriteDataSession().physicalDelete(FatGoodsCategory.class, id);
    }

    @Override
    public FatGoodsCategory updateCategory(Map<String, Object> param, int id) {
        FatGoodsCategory category = id(FatGoodsCategory.class, id, ResultConstant.Category.CATEGORY_IS_NULL);
        category = CommonBeanUtils.transMap2BasePO(param, category);
        factory.getCacheWriteDataSession().update(FatGoodsCategory.class, category);
        return category;
    }

    @Override
    public List<FatGoodsCategory> categoryList() {
        Param param = ParamBuilder.getInstance().getParam();
        return factory.getCacheReadDataSession().queryListResult(FatGoodsCategory.class, param);
    }

    @Override
    public void publishCategory(int id) {
        changeStatus(GoodsService.CATEGORY_ENABLE, id);
    }

    @Override
    public void cancelCategory(int id) {
        changeStatus(GoodsService.CATEGORY_DISABLE, id);
    }

    private void changeStatus(int status, int id) {
        FatGoodsCategory category = id(FatGoodsCategory.class, id, ResultConstant.Category.CATEGORY_IS_NULL);
        category.setCategoryStatus(status);
        factory.getCacheWriteDataSession().update(FatGoodsCategory.class, category);
    }

    @Override
    public FatGoodsCategory getCategoryById(int id) {
        return id(FatGoodsCategory.class, id, ResultConstant.Category.CATEGORY_IS_NULL);
    }
}
