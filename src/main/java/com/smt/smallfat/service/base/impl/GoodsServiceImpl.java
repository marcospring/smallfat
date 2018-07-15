package com.smt.smallfat.service.base.impl;

import com.csyy.common.DateUtils;
import com.csyy.common.JSONUtils;
import com.csyy.common.StringDefaultValue;
import com.csyy.core.apisupport.impl.BaseServiceImpl;
import com.csyy.core.datasource.param.*;
import com.csyy.core.exception.CommonException;
import com.csyy.core.obj.Pagination;
import com.csyy.core.utils.CommonBeanUtils;
import com.csyy.core.utils.SQLUtil;
import com.smt.smallfat.constant.Constant;
import com.smt.smallfat.constant.ResultConstant;
import com.smt.smallfat.exception.GetGoodsDetailLockException;
import com.smt.smallfat.exception.UnLockGoodsDetailException;
import com.smt.smallfat.po.*;
import com.smt.smallfat.service.base.FavoriteService;
import com.smt.smallfat.service.base.GoodsService;
import com.smt.smallfat.service.system.SysDicItemService;
import com.smt.smallfat.vo.GoodsCategoryVO;
import com.smt.smallfat.vo.GoodsVO;
import com.smt.smallfat.vo.SysDicItemVo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Collections.sort;


@Service
public class GoodsServiceImpl extends BaseServiceImpl implements GoodsService {


    @Autowired
    private SysDicItemService dicItemService;

    Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public FatGoods addGoods(Map<String, Object> param) {
        String title = StringDefaultValue.StringValue(param.get(FatGoods.FIELD_TITLE));
        String themeIds = StringDefaultValue.StringValue(param.get("themeIds"));
        String[] themeIdArr = StringDefaultValue.isEmpty(themeIds) ? new String[0] : themeIds.split(",");
        //重复性验证，如果存在标题相同且库存不为0的情况则提示已存在产品
        CustomSQL where = SQLCreator.where().cloumn(FatGoods.FIELD_DISABLED).operator(ESQLOperator.EQ).v(Constant
                .WrapperExtend.ZERO).operator(ESQLOperator.AND).cloumn(FatGoods.FIELD_TITLE).operator(ESQLOperator
                .EQ).v(title);
        List<FatGoods> goodsList = factory.getCacheReadDataSession().queryListResultByWhere(FatGoods.class, where);
        if (goodsList.size() > 0)
            throw new CommonException(ResultConstant.Goods.GOODS_IS_NOT_NULL);
        FatGoods goods = CommonBeanUtils.transMap2BasePO(param, FatGoods.class);
        goods = factory.getCacheWriteDataSession().save(FatGoods.class, goods);
        for (String id : themeIdArr) {
            FatGoodsThemeRelation relation = FatGoodsThemeRelation.getInstance(FatGoodsThemeRelation.class);
            relation.setGoodsId(goods.getId());
            relation.setThemeId(StringDefaultValue.intValue(id));
            factory.getCacheWriteDataSession().save(FatGoodsThemeRelation.class, relation);
        }

        return goods;
    }

    @Override
    public FatGoods getGoodsById(int id) {
        FatGoods goods = factory.getCacheReadDataSession().querySingleResultById(FatGoods.class, id);
        if (goods == null)
            throw new CommonException(ResultConstant.Goods.GOODS_IS_NULL);
        return goods;
    }

    @Override
    public FatGoods getGoodsByUUID(String uuid) {
        FatGoods goods = factory.getCacheReadDataSession().querySingleResultByUUID(FatGoods.class, uuid);
        if (goods == null)
            throw new CommonException(ResultConstant.Goods.GOODS_IS_NULL);
        return goods;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteGoods(int id) {
        GoodsVO goodsVO = getGoodsVO(id);
        CustomSQL where;
        //判断用户收藏中是否包换
        where = SQLCreator.where().cloumn(FatFavorite.FIELD_FAVORITE_TYPE).operator(ESQLOperator.EQ).v
                (FavoriteService.GOODS).operator(ESQLOperator.AND).cloumn(FatFavorite.FIELD_ARTICLE_ID).operator
                (ESQLOperator.EQ).v(id);
        long count = factory.getCacheReadDataSession().queryListResultCountByWhere(FatFavorite.class, where);
        if (count != 0L)
            throw new CommonException(ResultConstant.Goods.FAVORITE_GOODS);

        List<FatGoodsDetail> details = goodsVO.getDetails();
        for (FatGoodsDetail detail : details) {
            //判断用户购物车中是否包含
            where = SQLCreator.where().cloumn(FatShoppingCart.FIELD_GOODS_DETAIL_ID).operator
                    (ESQLOperator.EQ).v(detail.getId());
            long cartCount = factory.getCacheReadDataSession().queryListResultCountByWhere(FatShoppingCart.class,
                    where);
            if (cartCount != 0)
                throw new CommonException(ResultConstant.Goods.SHOPPING_CART_GOODS);
            //判断用户订单中是否包含
            where = SQLCreator.where().cloumn(FatOrderItem.FIELD_GOODS_DETAIL_ID).operator
                    (ESQLOperator.EQ).v(detail.getId());
            long orderCount = factory.getCacheReadDataSession().queryListResultCountByWhere(FatOrderItem.class,
                    where);
            if (orderCount != 0)
                throw new CommonException(ResultConstant.Goods.ORDER_GOODS);
        }
        factory.getCacheWriteDataSession().physicalDelete(FatGoods.class, id);
        //删除goodsdetail
        where = SQLCreator.where().cloumn(FatGoodsDetail.FIELD_GOODS_ID).operator(ESQLOperator.EQ).v(id);
        factory.getCacheWriteDataSession().physicalWhereDelete(FatGoodsDetail.class, where);
        //删除goodsresource
        where = SQLCreator.where().cloumn(FatGoodsResource.FIELD_GOODS_ID).operator(ESQLOperator.EQ).v(id);
        factory.getCacheWriteDataSession().physicalWhereDelete(FatGoodsResource.class, where);
    }

    @Override
    public Pagination<FatGoods> pageGoods(Map<String, Object> param) {
        int pageNo = StringDefaultValue.intValue(param.get(Constant.PAGE_NO));
        int pageSize = StringDefaultValue.intValue(param.get(Constant.PAGE_SIZE));
        Param params = ParamBuilder.getInstance().getParam().add(param)
                .add(ParamBuilder.nv(Constant.SQLConstants.ORDER_COLUMN, FatGoods.FIELD_UPDATE_TIME))
                .add(ParamBuilder.nv(Constant.SQLConstants.ORDER_BY_TYPE, Constant.SQLConstants.DESC));
        return queryClassPagination(FatGoods.class, params, pageNo, pageSize);
    }

    @Override
    public List<FatGoodsResource> getGoodsResourceByGoodsId(int id) {
        Param param = ParamBuilder.getInstance().getParam().add(ParamBuilder.nv(FatGoodsResource.FIELD_GOODS_ID, id));
        return factory.getCacheReadDataSession().queryListResult(FatGoodsResource.class, param);
    }

    @Override
    public FatGoodsResource addGoodsResource(Map<String, Object> param) {
        FatGoodsResource resource = CommonBeanUtils.transMap2BasePO(param, FatGoodsResource.class);
        resource = factory.getCacheWriteDataSession().save(FatGoodsResource.class, resource);
        return resource;
    }

    @Override
    public void deleteGoodsResourceById(int id) {
        factory.getCacheWriteDataSession().physicalDelete(FatGoodsResource.class, id);
    }

    @Override
    public GoodsVO getGoodsVO(int id) {
        FatGoods goods = getGoodsById(id);
        List<FatGoodsResource> list = getGoodsResourceByGoodsId(id);
        List<FatGoodsDetail> details = getGoodsDetailsByGoodsId(id);
        GoodsVO vo = fillGoodsVO(goods, list, details);
        List<FatGoodsThemeRelation> relations = goodsThemeIds(vo.getId());
        List<Integer> themeIdList = new ArrayList<>(relations.size());
        themeIdList.addAll(relations.stream().map(FatGoodsThemeRelation::getThemeId).collect(Collectors.toList()));
        vo.setThemeIdList(StringUtils.join(themeIdList, ","));
        return vo;
    }

    @Override
    public GoodsVO getGoodsVOByUUID(String uuid) {
        FatGoods goods = getGoodsByUUID(uuid);
        List<FatGoodsResource> list = getGoodsResourceByGoodsId(goods.getId());
        List<FatGoodsDetail> details = getGoodsDetailsByGoodsId(goods.getId());
        return fillGoodsVO(goods, list, details);
    }

    @Override
    public Pagination<GoodsVO> pageGoodsVO(Map<String, Object> param) {
        param.put(FatGoods.FIELD_IS_APP, 1);
        Pagination<FatGoods> page = pageGoods(param);
        Pagination<GoodsVO> pageVO = fillGoodsVOPagination(page);
        return pageVO;
    }

    @Override
    public List<FatGoodsDetail> getGoodsDetailsByGoodsId(int id) {
        Param param = ParamBuilder.getInstance().getParam().add(ParamBuilder.nv(FatGoodsResource.FIELD_GOODS_ID, id));
        return factory.getReadDataSession().queryListResult(FatGoodsDetail.class, param);
    }

    @Override
    public FatGoodsDetail addGoodsDetail(Map<String, Object> param) {
        String size = StringDefaultValue.StringValue(param.get(FatGoodsDetail.FIELD_MODEL_SIZE));
        int goodsId = StringDefaultValue.intValue(param.get(FatGoodsDetail.FIELD_GOODS_ID));
        Param params = ParamBuilder.getInstance().getParam();
        params.add(ParamBuilder.nv(FatGoodsDetail.FIELD_MODEL_SIZE, size));
        params.add(ParamBuilder.nv(FatGoodsDetail.FIELD_GOODS_ID, goodsId));
        FatGoodsDetail details = factory.getCacheReadDataSession().querySingleResultByParams(FatGoodsDetail.class,
                params);
        if (details != null)
            throw new CommonException(ResultConstant.Goods.GOODS_DETAIL_IS_NOT_NULL);
        FatGoodsDetail detail = CommonBeanUtils.transMap2BasePO(param, FatGoodsDetail.class);
        detail.setDetailLock(StringDefaultValue.StringValue(Constant.WrapperExtend.ZERO));
        detail = factory.getCacheWriteDataSession().save(FatGoodsDetail.class, detail);
        return detail;
    }

    @Override
    public void deleteGoodsDetail(int detailId) {
        factory.getCacheWriteDataSession().physicalDelete(FatGoodsDetail.class, detailId);
    }

    @Override
    public FatGoods updateGoods(Map<String, Object> param) {
        int id = StringDefaultValue.intValue(param.get(FatGoods.FIELD_ID));
        String themeIds = StringDefaultValue.StringValue(param.get("themeIds"));
        String[] themeIdArr = StringDefaultValue.isEmpty(themeIds) ? new String[0] : themeIds.split(",");


        FatGoods goods = getGoodsById(id);
        goods = CommonBeanUtils.transMap2BasePO(param, goods);
        if (goods.getIsApp() == 1)
            goods.setPublishTime(new Date());
        factory.getCacheWriteDataSession().update(FatGoods.class, goods);
        //操作主题
        CustomSQL where = SQLCreator.where().cloumn(FatGoodsThemeRelation.FIELD_GOODS_ID).operator(ESQLOperator.EQ).v
                (id);
        //删除商品所有主题
        factory.getCacheWriteDataSession().physicalWhereDelete(FatGoodsThemeRelation.class, where);
        //更新用户主题
        for (String themeId : themeIdArr) {
            FatGoodsThemeRelation relation = FatGoodsThemeRelation.getInstance(FatGoodsThemeRelation.class);
            relation.setGoodsId(id);
            relation.setThemeId(StringDefaultValue.intValue(themeId));
            factory.getCacheWriteDataSession().save(FatGoodsThemeRelation.class, relation);
        }
        return goods;
    }

    @Override
    public FatGoodsResource updateGoodsResource(Map<String, Object> param) {
        int id = StringDefaultValue.intValue(param.get(FatGoodsResource.FIELD_ID));
        FatGoodsResource resource = getGoodsResourceById(id);
        resource = CommonBeanUtils.transMap2BasePO(param, resource);
        factory.getCacheWriteDataSession().update(FatGoodsResource.class, resource);
        return resource;
    }

    @Override
    public FatGoodsDetail updateGoodsDetail(Map<String, Object> param) {
        int id = StringDefaultValue.intValue(param.get(FatGoodsDetail.FIELD_ID));
        FatGoodsDetail detail = getGoodsDetailById(id);
        detail = CommonBeanUtils.transMap2BasePO(param, detail);
        factory.getCacheWriteDataSession().update(FatGoodsDetail.class, detail);
        return detail;
    }

    public FatGoodsDetail getGoodsDetailById(int id) {
        FatGoodsDetail detail = factory.getReadDataSession().querySingleResultById(FatGoodsDetail.class, id);
        if (detail == null)
            throw new CommonException(ResultConstant.Goods.GOODS_DETAIL_IS_NULL);
        return detail;
    }

    public FatGoodsResource getGoodsResourceById(int id) {
        FatGoodsResource resource = factory.getCacheReadDataSession().querySingleResultById(FatGoodsResource.class, id);
        if (resource == null)
            throw new CommonException(ResultConstant.Goods.GOODS_RESOURCE_IS_NULL);
        return resource;
    }

    @Override
    public int getGoodsCount(int goodsDetailId) {
        FatGoodsDetail detail = getGoodsDetailById(goodsDetailId);
        return detail.getStock();
    }

    @Override
    @Transactional()
    public void addGoodsDetailLock(int goodsDetailId, String customerUUID) {
        CustomSQL where = SQLCreator.where();
        where.cloumn(FatGoodsDetail.FIELD_DISABLED).operator(ESQLOperator.EQ).v(Constant.WrapperExtend.ZERO).operator
                (ESQLOperator.AND).cloumn(FatGoodsDetail.FIELD_DETAIL_LOCK).operator(ESQLOperator.EQ).v
                (StringDefaultValue.StringValue(Constant.WrapperExtend.ZERO))
                .operator(ESQLOperator.AND).cloumn(FatGoodsDetail.FIELD_ID).operator
                (ESQLOperator.EQ).v(goodsDetailId);
        Param param = ParamBuilder.getInstance().getParam().add(ParamBuilder.nv(FatGoodsDetail.FIELD_DETAIL_LOCK,
                customerUUID));
        int updateCount = factory.getCacheWriteDataSession().updateCustomColumnByWhere(FatGoodsDetail.class, param, where);
        if (updateCount == 0)
            throw new GetGoodsDetailLockException(goodsDetailId);
    }

    @Override
    public void cancelGoodsDetailLock(int goodsDetailId, String customerUUID) {
        CustomSQL where = SQLCreator.where();
        where.cloumn(FatGoodsDetail.FIELD_DISABLED).operator(ESQLOperator.EQ).v(Constant.WrapperExtend.ZERO).operator
                (ESQLOperator.AND).cloumn(FatGoodsDetail.FIELD_DETAIL_LOCK).operator(ESQLOperator.EQ).v(customerUUID)
                .operator(ESQLOperator.AND).cloumn(FatGoodsDetail.FIELD_ID).operator(ESQLOperator.EQ).v(goodsDetailId);
        Param param = ParamBuilder.getInstance().getParam().add(ParamBuilder.nv(FatGoodsDetail.FIELD_DETAIL_LOCK,
                StringDefaultValue.StringValue(Constant.WrapperExtend.ZERO)));
        int updateCount = factory.getCacheWriteDataSession().updateCustomColumnByWhere(FatGoodsDetail.class, param,
                where);
        if (updateCount == 0)
            throw new UnLockGoodsDetailException(goodsDetailId);
    }


    private GoodsVO fillGoodsVO(FatGoods goods, List<FatGoodsResource> resource, List<FatGoodsDetail> details) {
        GoodsVO goodsVO = new GoodsVO(goods, resource, details);
        SysDicItemVo dicItemVo = dicItemService.getDicItemByDicCodeAndItemValue(FLEX_COMPANY, goods.getExpCompany());
        goodsVO.setExpCompany(dicItemVo == null ? "" : dicItemVo.getDicItemName());
        String[] flags = goods.getFlag().split(Constant.Separator.COMMA);
        goodsVO.setFlagKeys(goods.getFlag());
        StringBuilder flagBuilder = new StringBuilder();
        for (String f : flags) {
            SysDicItemVo vo = dicItemService.getDicItemByDicCodeAndItemValue(GOODS_FLAG, f);
            if (vo != null)
                flagBuilder.append(vo.getDicItemName()).append(Constant.Separator.COMMA);
        }
        goodsVO.setFlag(flagBuilder.deleteCharAt(flagBuilder.lastIndexOf(Constant.Separator.COMMA)).toString());
        goodsVO.setPriceArea(buildPriceArea(details));
        goodsVO.setPublishTime(goods.getPublishTime());
        return goodsVO;
    }

    private String buildPriceArea(List<FatGoodsDetail> details) {
        StringBuilder priceArea = new StringBuilder("￥");
        if (details.size() == 0)
            return "0";
        if (details.size() == 1)
            return priceArea.append(details.get(0).getPrice().intValue()).toString();
        sort(details, new Comparator<FatGoodsDetail>() {
            @Override
            public int compare(FatGoodsDetail o1, FatGoodsDetail o2) {
                return o1.getPrice().compareTo(o2.getPrice());
            }
        });
        priceArea.append(details.get(0).getPrice().intValue()).append(Constant.Separator.CONNECTOR).append("￥")
                .append(details.get(details.size() - 1).getPrice().intValue());
        return priceArea.toString();
    }

    @Override
    public GoodsVO getGoodsVoByGoodsDetailId(int detailId) {
        FatGoodsDetail detail = getGoodsDetailById(detailId);
        return getGoodsVO(detail.getGoodsId());
    }

    @Override
    public void returnInventory(int goodsDetailId, int returnCount) {
        FatGoodsDetail detail = getGoodsDetailById(goodsDetailId);
        logger.info("原有库存为：{}，退还个数为：{}", detail.getStock(), returnCount);
        detail.setStock(detail.getStock() + returnCount);
        factory.getCacheWriteDataSession().update(FatGoodsDetail.class, detail);
        logger.info("退还库存后结果：{}", JSONUtils.toJson(detail));
    }

    @Override
    public void addToApp(int id) {
        FatGoods goods = getGoodsById(id);
        goods.setIsApp(1);
        goods.setPublishTime(new Date());
        factory.getCacheWriteDataSession().update(FatGoods.class, goods);
    }

    @Override
    public void orderGoods(int id) {
        FatGoods goods = getGoodsById(id);
        goods.setOrderBy(goods.getOrderBy() + 1);
        goods.setUpdateTime(new Date());
        factory.getCacheWriteDataSession().update(FatGoods.class, goods);
    }

    @Override
    public List<FatCustomer> shoppingCartUsers(int goodsId) {
        List<FatCustomer> customers = new ArrayList<>();
        List<FatGoodsDetail> details = getGoodsDetailsByGoodsId(goodsId);
        for (FatGoodsDetail detail : details) {
            Param param = ParamBuilder.getInstance().getParam().add(ParamBuilder.nv(FatShoppingCart
                    .FIELD_GOODS_DETAIL_ID, detail.getId()));
            List<FatShoppingCart> carts = factory.getCacheReadDataSession().queryListResult(FatShoppingCart.class, param);
            for (FatShoppingCart cart : carts) {
                customers.add(factory.getCacheReadDataSession().querySingleResultById(FatCustomer.class, cart.getUserId()));
            }
        }

        return customers;
    }

    @Override
    public Pagination<GoodsVO> search(String title, int pageNo, int pageSize) {
        CustomSQL where = SQLCreator.where();
        where.cloumn(FatGoods.FIELD_IS_APP).operator(ESQLOperator.EQ).v(1).operator(ESQLOperator.AND)
                .cloumn(FatGoods.FIELD_TITLE).operator(ESQLOperator.LIKE).v(SQLUtil.likeValue(title, SQLUtil.ALL))
                .operator(ESQLOperator.ORDER_BY).cloumn(FatGoods.FIELD_UPDATE_TIME).operator(ESQLOperator.DESC);
        Pagination<FatGoods> page = queryClassPagination(FatGoods.class, where, pageNo, pageSize);
        Pagination<GoodsVO> pageVO = fillGoodsVOPagination(page);
        return pageVO;
    }

    private Pagination<GoodsVO> fillGoodsVOPagination(Pagination<FatGoods> page) {
        List<FatGoods> list = page.getData();
        List<GoodsVO> voList = fillGoodsVOList(list);
        Pagination<GoodsVO> pageVO = new Pagination<>(voList, page.getPageNo(), page.getPageSize());
        pageVO.setRecordsTotal(page.getRecordsTotal());
        return pageVO;
    }

    private List<GoodsVO> fillGoodsVOList(List<FatGoods> list) {
        List<GoodsVO> voList = new ArrayList<>(list.size());
        for (FatGoods goods : list) {
            List<FatGoodsResource> resources = getGoodsResourceByGoodsId(goods.getId());
            List<FatGoodsDetail> details = getGoodsDetailsByGoodsId(goods.getId());
            GoodsVO vo = fillGoodsVO(goods, resources, details);
            voList.add(vo);
        }
        return voList;
    }

    @Override
    public GoodsCategoryVO listGoodsCategory() {
        Param param = ParamBuilder.getInstance().getParam().add(ParamBuilder.nv(FatGoodsCategory.FIELD_CATEGORY_STATUS, CATEGORY_ENABLE));
        List<FatGoodsCategory> categories = factory.getCacheReadDataSession().queryListResult(FatGoodsCategory.class,
                param);
        param.clean();
        param.add(ParamBuilder.nv(FatGoodsTheme.FIELD_PUBLISH_STATUS, CATEGORY_ENABLE));
        List<FatGoodsTheme> themes = factory.getCacheReadDataSession().queryListResult(FatGoodsTheme.class, param);
        return new GoodsCategoryVO(categories, themes);
    }

    @Override
    public List<GoodsVO> goodsThemeList(int themeId) {
        List<Integer> ids = getThemeIdArray(themeId);
        if (ids.size() == 0)
            return new ArrayList<>(0);
        CustomSQL where = SQLCreator.where().cloumn(FatGoods.FIELD_ID).operator(ESQLOperator.IN).v(ids);
        List<FatGoods> goods = factory.getCacheReadDataSession().queryListResultByWhere(FatGoods.class, where);
        return fillGoodsVOList(goods);
    }

    private List<Integer> getThemeIdArray(int themeId) {
        Param param = ParamBuilder.getInstance().getParam().add(ParamBuilder.nv(FatGoodsThemeRelation.FIELD_THEME_ID,
                themeId));
        List<FatGoodsThemeRelation> relations = factory.getCacheReadDataSession().queryListResult
                (FatGoodsThemeRelation.class, param);
        List<Integer> ids = new ArrayList<>(relations.size());
        ids.addAll(relations.stream().map(FatGoodsThemeRelation::getGoodsId).collect(Collectors.toList()));
        return ids;
    }

    @Override
    public List<GoodsVO> nearestGoodsList() {
        CustomSQL where = SQLCreator.where().cloumn(FatGoods.FIELD_IS_APP).operator(ESQLOperator.EQ).v(1).operator
                (ESQLOperator.ORDER_BY).cloumn(FatGoods.FIELD_PUBLISH_TIME).operator(ESQLOperator.DESC).operator
                (ESQLOperator.LIMIT).v(1);
        List<FatGoods> goods = factory.getCacheReadDataSession().queryListResultByWhere(FatGoods.class, where);
        Date begin = DateUtils.getCurrentDayBegin();
        Date end = DateUtils.getCurrentDayEnd();
        if (goods != null && goods.size() > 0) {
            begin = DateUtils.getDayBegin(goods.get(0).getPublishTime());
            end = DateUtils.getDayEnd(goods.get(0).getPublishTime());
        }
        where.clean();
        where.cloumn(FatGoods.FIELD_PUBLISH_TIME).operator(ESQLOperator.BETWEEN).v(begin).operator(ESQLOperator.AND)
                .v(end).operator(ESQLOperator.ORDER_BY).cloumn(FatGoods.FIELD_PUBLISH_TIME).operator(ESQLOperator.DESC);
        goods = factory.getCacheReadDataSession().queryListResultByWhere(FatGoods.class, where);
        return fillGoodsVOList(goods);
    }

    private List<FatGoodsThemeRelation> goodsThemeIds(int goodsId) {
        Param params = ParamBuilder.getInstance().getParam();
        params.add(ParamBuilder.nv(FatGoodsThemeRelation.FIELD_GOODS_ID, goodsId));
        return factory.getCacheReadDataSession().queryListResult
                (FatGoodsThemeRelation.class, params);

    }
    @Override
    public Pagination<GoodsVO> otherGoodsList(Map<String, Object> param) {
        CustomSQL where = SQLCreator.where().cloumn(FatGoods.FIELD_IS_APP).operator(ESQLOperator.EQ).v(1).operator
                (ESQLOperator.ORDER_BY).cloumn(FatGoods.FIELD_PUBLISH_TIME).operator(ESQLOperator.DESC).operator
                (ESQLOperator.LIMIT).v(1);
        List<FatGoods> goods = factory.getCacheReadDataSession().queryListResultByWhere(FatGoods.class, where);
        Date begin = DateUtils.getCurrentDayBegin();
        Date end = DateUtils.getCurrentDayEnd();
        if (goods != null && goods.size() > 0) {
            begin = DateUtils.getDayBegin(goods.get(0).getPublishTime());
            end = DateUtils.getDayEnd(goods.get(0).getPublishTime());
        }
        int pageNo = StringDefaultValue.intValue(param.get(Constant.PAGE_NO));
        int pageSize = StringDefaultValue.intValue(param.get(Constant.PAGE_SIZE));
        where.clean();
        where.cloumn(FatGoods.FIELD_PUBLISH_TIME).operator(ESQLOperator.LT).v(begin).operator(ESQLOperator.ORDER_BY).cloumn
                (FatGoods.FIELD_PUBLISH_TIME).operator(ESQLOperator.DESC);
        Pagination<FatGoods> page = queryClassPagination(FatGoods.class, where, pageNo, pageSize);
        Pagination<GoodsVO> pageVO = fillGoodsVOPagination(page);
        return pageVO;
    }
}
