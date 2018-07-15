package com.smt.smallfat.service.base.impl;

import com.csyy.common.StringDefaultValue;
import com.csyy.core.apisupport.impl.BaseServiceImpl;
import com.csyy.core.datasource.param.*;
import com.csyy.core.exception.CommonException;
import com.csyy.core.obj.Pagination;
import com.csyy.core.utils.CommonBeanUtils;
import com.smt.smallfat.constant.Constant;
import com.smt.smallfat.constant.ResultConstant;
import com.smt.smallfat.po.FatGoodsTheme;
import com.smt.smallfat.po.FatGoodsThemeRelation;
import com.smt.smallfat.po.SysRole;
import com.smt.smallfat.service.base.GoodsService;
import com.smt.smallfat.service.base.ThemeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ThemeServiceImpl extends BaseServiceImpl implements ThemeService {

    @Override
    public FatGoodsTheme addTheme(Map<String, Object> param) {
        FatGoodsTheme theme;
        Param params = ParamBuilder.getInstance().getParam()
                .add(ParamBuilder.nv(FatGoodsTheme.FIELD_THEME_TITLE, param.get(FatGoodsTheme.FIELD_THEME_TITLE)));
        long count = factory.getCacheReadDataSession().queryListResultCount(FatGoodsTheme.class, params);
        if (count > 0) {
            throw new CommonException(ResultConstant.Theme.THEME_IS_NULL);
        }
        theme = CommonBeanUtils.transMap2BasePO(param, FatGoodsTheme.class);
        return factory.getCacheWriteDataSession().save(FatGoodsTheme.class, theme);
//        param.put(FatGoodsThemeRelation.FIELD_THEME_ID, theme.getId());
//
//        int themeId = StringDefaultValue.intValue(param.get(FatGoodsTheme.FIELD_ID));
//        if (StringDefaultValue.isEmpty(param.get("goodsIds"))) {
//            CustomSQL whereSql = SQLCreator.where().cloumn(FatGoodsThemeRelation.FIELD_THEME_ID).operator(ESQLOperator
//                    .EQ).v(themeId);
//            factory.getCacheWriteDataSession().logicWhereDelete(FatGoodsThemeRelation.class, whereSql);
//        } else {
//            String permissionIds = param.get("goodsIds").toString();
//            params.clean();
//            params.add(ParamBuilder.nv(FatGoodsThemeRelation.FIELD_THEME_ID, themeId));
//            List<FatGoodsThemeRelation> existsList = factory.getCacheReadDataSession().queryListResult
//                    (FatGoodsThemeRelation.class, params);
//            if (existsList == null) {
//                throw new CommonException(ResultConstant.Goods.GOODS_IS_NULL);
//            }
//            String[] rights = permissionIds.split(",");
//            List<String> inputIds = Arrays.asList(rights);
//            dealWithThemeGoods(themeId, existsList, inputIds);
//        }
    }

    @Override
    @Transactional
    public void delTheme(int id) {
        //删除主题含有的商品关系
        CustomSQL where = SQLCreator.where().cloumn(FatGoodsThemeRelation.FIELD_THEME_ID).operator(ESQLOperator.EQ).v
                (id);
        factory.getCacheWriteDataSession().physicalWhereDelete(FatGoodsThemeRelation.class, where);
        //删除主题
        factory.getCacheWriteDataSession().physicalDelete(FatGoodsTheme.class, id);
    }

    @Override
    public FatGoodsTheme updateTheme(Map<String, Object> param) {
        int id = StringDefaultValue.intValue(param.get(FatGoodsTheme.FIELD_ID));
        FatGoodsTheme theme;
        theme = factory.getCacheReadDataSession().querySingleResultById(FatGoodsTheme.class, id);
        if (theme == null) {
            throw new CommonException(ResultConstant.Theme.THEME_IS_NULL);
        }
        Param params = ParamBuilder.getInstance().getParam()
                .add(ParamBuilder.nv(FatGoodsTheme.FIELD_THEME_TITLE, StringDefaultValue.StringValue(param.get
                        (FatGoodsTheme.FIELD_THEME_TITLE)))).add(ParamBuilder.nv(SysRole.FIELD_DISABLED, 0));
        List<FatGoodsTheme> themes = factory.getCacheReadDataSession().queryListResult(FatGoodsTheme.class, params);

//        FatGoodsTheme finalTheme = theme;
//        try {
//            FatGoodsTheme self = null;
//            self = themes.stream().filter(p -> p.getId() == finalTheme.getId()).findFirst().get();
//            themes.remove(self);
//        } catch (NoSuchElementException e) {
//            e.printStackTrace();
//        }
        if (themes != null && themes.size() > 0) {
            throw new CommonException(ResultConstant.SysRoleResult.SYSROLE_IS_EXISTS);
        }
        theme = CommonBeanUtils.transMap2BasePO(param, theme);
        factory.getCacheWriteDataSession().update(FatGoodsTheme.class, theme);
        return theme;
//        param.put(SysRolePermission.FIELD_ROLE_ID, id);
//        int themeId = StringDefaultValue.intValue(param.get(FatGoodsTheme.FIELD_ID));
//        String goodsIds = param.get("goodsId").toString();
//        params.clean();
//        params.add(ParamBuilder.nv(FatGoodsThemeRelation.FIELD_THEME_ID, themeId));
//        List<FatGoodsThemeRelation> relations = factory.getCacheReadDataSession().queryListResult(FatGoodsThemeRelation
//                .class, params);
//        if (relations == null) {
//            throw new CommonException(ResultConstant.SysPermission.PERMISSION_IS_NULL);
//        }
//
//        String[] rights = goodsIds.split(",");
//        List<String> inputIds = Arrays.asList(rights);
//        dealWithThemeGoods(themeId, relations, inputIds);
    }

    @Override
    public Pagination<FatGoodsTheme> allTheme(Map<String, Object> params) {
        int pageNo = StringDefaultValue.intValue(params.get(Constant.PAGE_NO));
        int pageSize = StringDefaultValue.intValue(params.get(Constant.PAGE_SIZE));
        Param param = ParamBuilder.getInstance().getParam().add(params);
        Pagination<FatGoodsTheme> pageTheme = queryClassPagination(FatGoodsTheme.class, param, pageNo, pageSize);
        return pageTheme;
    }

    @Override
    public List<FatGoodsTheme> indexTheme() {
        Param param = ParamBuilder.getInstance().getParam().add(ParamBuilder.nv(FatGoodsTheme.FIELD_PUBLISH_STATUS,
                GoodsService.CATEGORY_ENABLE));
        return factory.getCacheReadDataSession().queryListResult(FatGoodsTheme.class, param);
    }


    private List<FatGoodsThemeRelation> dealWithThemeGoods(int themeId, List<FatGoodsThemeRelation> existsList,
                                                           List<String>
                                                                   inputIds) {
        List<FatGoodsThemeRelation> permissions = new ArrayList<>();
        if (existsList == null || existsList.size() == 0) {
            for (String goodsId : inputIds) {
                permissions.add(addThemeGoodsRelation(themeId, goodsId));
            }
        } else {
            //修改操作.
            Set<String> before = new HashSet<String>();
            Set<String> after = new HashSet<String>(inputIds);
            for (FatGoodsThemeRelation relation : existsList) {
                before.add(relation.getGoodsId() + "");
            }
            Set<String> temp = new HashSet<String>(before);
            temp.retainAll(after);
            before.removeAll(temp);

            for (String goodsId : before) {
                for (FatGoodsThemeRelation relation : existsList) {
                    if (goodsId.equals(relation.getGoodsId() + "")) {
                        factory.getCacheWriteDataSession().physicalDelete(FatGoodsThemeRelation.class, relation.getId());
                    }
                }
            }
            after.removeAll(temp);
            for (String goodsId : after) {
                permissions.add(addThemeGoodsRelation(themeId, goodsId));
            }
        }
        return permissions;
    }

    private FatGoodsThemeRelation addThemeGoodsRelation(int themeId, String goodsId) {
        FatGoodsThemeRelation relation = FatGoodsThemeRelation.getInstance(FatGoodsThemeRelation.class);
        relation.setThemeId(themeId);
        relation.setGoodsId(Integer.parseInt(goodsId));
        relation = factory.getCacheWriteDataSession().save(FatGoodsThemeRelation.class, relation);
        return relation;
    }

    @Override
    public void publishTheme(int id) {
        FatGoodsTheme theme = id(FatGoodsTheme.class, id, ResultConstant.Theme.THEME_IS_NULL);
        theme.setPublishStatus(GoodsService.CATEGORY_ENABLE);
        theme.setPublishTime(new Date());
        factory.getCacheWriteDataSession().update(FatGoodsTheme.class, theme);
    }

    @Override
    public void cancelTheme(int id) {
        FatGoodsTheme theme = id(FatGoodsTheme.class, id, ResultConstant.Theme.THEME_IS_NULL);
        theme.setPublishStatus(GoodsService.CATEGORY_DISABLE);
        factory.getCacheWriteDataSession().update(FatGoodsTheme.class, theme);
    }

    @Override
    public FatGoodsTheme getThemeById(int id) {
        return id(FatGoodsTheme.class, id, ResultConstant.Theme.THEME_IS_NULL);
    }

}
