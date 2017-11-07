package com.smt.smallfat.service;

import com.csyy.core.obj.Pagination;
import com.smt.smallfat.exception.GetGoodsDetailLockException;
import com.smt.smallfat.exception.UnLockGoodsDetailException;
import com.smt.smallfat.po.FatGoods;
import com.smt.smallfat.po.FatGoodsDetail;
import com.smt.smallfat.po.FatGoodsResource;
import com.smt.smallfat.vo.GoodsVO;

import java.util.List;
import java.util.Map;

public interface GoodsService {

    String FLEX_COMPANY = "FLEX_COMPANY";
    String GOODS_FLAG ="GOODS_FLAG";


    FatGoods addGoods(Map<String, Object> param);

    FatGoods getGoodsById(int id);

    FatGoods getGoodsByUUID(String uuid);

    void deleteGoods(int id);

    Pagination<FatGoods> pageGoods(Map<String, Object> param);

    List<FatGoodsResource> getGoodsResourceByGoodsId(int id);

    FatGoodsResource addGoodsResource(Map<String, Object> param);

    void deleteGoodsResourceById(int id);

    GoodsVO getGoodsVO(int id);

    GoodsVO getGoodsVOByUUID(String uuid);

    Pagination<GoodsVO> pageGoodsVO(Map<String, Object> param);

    List<FatGoodsDetail> getGoodsDetailsByGoodsId(int id);

    FatGoodsDetail addGoodsDetail(Map<String, Object> param);

    void deleteGoodsDetail(int detailId);

    FatGoods updateGoods(Map<String, Object> param);

    FatGoodsResource updateGoodsResource(Map<String, Object> param);

    FatGoodsDetail updateGoodsDetail(Map<String, Object> param);

    FatGoodsResource getGoodsResourceById(int id);

    FatGoodsDetail getGoodsDetailById(int id);

    GoodsVO getGoodsVoByGoodsDetailId(int detailId);

    /**
     * 获取商品数量
     *
     * @param goodsDetailId
     * @return
     */
    int getGoodsCount(int goodsDetailId);

    /**
     * 添加商品详情锁定
     *
     * @param goodsDetailId 锁定的商品详情id
     * @param customerUUID 获得所的用户uuid
     */
    void addGoodsDetailLock(int goodsDetailId, String customerUUID) throws GetGoodsDetailLockException;

    /**
     * 解锁商品详情
     * @param customerUUID 获得所的用户uuid
     * @param goodsDetailId 锁定的商品详情id
     */
    void cancelGoodsDetailLock(int goodsDetailId, String customerUUID) throws UnLockGoodsDetailException;

    /**
     * 退库存
     * @param goodsDetailId
     * @param returnCount
     */
    void returnInventory(int goodsDetailId,int returnCount);

    void addToApp(int id);
}
