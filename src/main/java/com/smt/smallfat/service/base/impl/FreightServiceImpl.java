package com.smt.smallfat.service.base.impl;

import com.csyy.common.StringDefaultValue;
import com.csyy.core.apisupport.impl.BaseServiceImpl;
import com.csyy.core.datasource.param.*;
import com.csyy.core.exception.CommonException;
import com.csyy.core.utils.CommonBeanUtils;
import com.smt.smallfat.constant.Constant;
import com.smt.smallfat.constant.ResultConstant;
import com.smt.smallfat.po.FatArea;
import com.smt.smallfat.po.FatFreightBody;
import com.smt.smallfat.po.FatFreightBodyArea;
import com.smt.smallfat.po.FatFreightHead;
import com.smt.smallfat.service.base.FreightService;
import com.smt.smallfat.service.system.AreaService;
import com.smt.smallfat.service.system.SysDicItemService;
import com.smt.smallfat.vo.FreightTableItemVO;
import com.smt.smallfat.vo.FreightTableVO;
import com.smt.smallfat.vo.SysDicItemVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FreightServiceImpl extends BaseServiceImpl implements FreightService {

    @Autowired
    private SysDicItemService itemService;
    @Autowired
    private AreaService areaService;

    Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public FreightTableVO addFreightTable(Map<String, Object> param) {
        FatFreightHead head = CommonBeanUtils.transMap2BasePO(param, FatFreightHead.class);
        //查询传入快递公司是否在系统内存在
        itemService.getDicItemByDicCodeAndItemValue(FLEX_COMPANY, head.getCompanyCode());
        Param params = ParamBuilder.getInstance().getParam().add(ParamBuilder.nv(FatFreightHead.FIELD_SHOP_ID, head
                .getShopId())).add(ParamBuilder.nv(FatFreightHead.FIELD_COMPANY_CODE, head.getCompanyCode())).add
                (ParamBuilder.nv(FatFreightHead.FIELD_EXPRESS_TYPE, head.getExpressType()));
        FatFreightHead headSearch = factory.getWriteDataSession().querySingleResultByParams(FatFreightHead.class,
                params);
        if (headSearch != null)
            throw new CommonException(ResultConstant.Freight.TABLE_IS_NOT_NULL);
        head = factory.getCacheWriteDataSession().save(FatFreightHead.class, head);
        return fillFreightTableVO(head);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public FreightTableItemVO addFreightTableItem(Map<String, Object> param) {
        String[] areaCode = StringDefaultValue.StringValue(param.get(AREA_CODE)).split(Constant.Separator.COMMA);
        FatFreightBody body = CommonBeanUtils.transMap2BasePO(param, FatFreightBody.class);
        body = factory.getCacheWriteDataSession().save(FatFreightBody.class, body);
        Param params = ParamBuilder.getInstance().getParam();
        List<FatArea> areaList = new ArrayList<>(areaCode.length);
        for (String code : areaCode) {
            params.clean();
            params.add(ParamBuilder.nv(FatFreightBodyArea.FIELD_AREA_CODE, code)).add(ParamBuilder.nv
                    (FatFreightBodyArea.FIELD_BODY_ID, body.getId()));
            FatFreightBodyArea area = factory.getWriteDataSession().querySingleResultByParams(FatFreightBodyArea.class,
                    params);
            if (area != null) {
                throw new CommonException(ResultConstant.Freight.BODY_AREA_IS_NOT_NULL);
            }
            area = FatFreightBodyArea.getInstance(FatFreightBodyArea.class);
            area.setAreaCode(code);
            area.setBodyId(body.getId());
            factory.getCacheWriteDataSession().save(FatFreightBodyArea.class, area);
            areaList.add(areaService.getAreaByCode(code));
        }
        return fillFreightTableItemVO(body, areaList);
    }

    private FreightTableItemVO fillFreightTableItemVO(FatFreightBody body) {
        List<FatArea> areaList = getFatFreightBodyAreas(body.getId());
        return fillFreightTableItemVO(body, areaList);
    }

    private FreightTableItemVO fillFreightTableItemVO(FatFreightBody body, List<FatArea> areaList) {
        FreightTableItemVO vo = new FreightTableItemVO();
        vo.setAreas(areaList);
        vo.setTableId(body.getHeadId());
        vo.setItemId(body.getId());
        vo.setFirstCount(body.getFirstCount());
        vo.setFirstPrice(body.getFirstPrice());
        vo.setOtherCount(body.getOtherCount());
        vo.setOtherPrice(body.getOtherPrice());
        return vo;
    }

    @Override
    public FreightTableItemVO addFreightItemArea(int bodyId, String areaCode) {
        FatFreightBody body = factory.getCacheReadDataSession().querySingleResultById(FatFreightBody.class, bodyId);
        return fillFreightTableItemVO(body);
    }

    private FatFreightBodyArea addFatFreightBodyArea(int bodyId, String areaCode) {
        Param params = ParamBuilder.getInstance().getParam();
        params.add(ParamBuilder.nv(FatFreightBodyArea.FIELD_BODY_ID, bodyId));
        params.add(ParamBuilder.nv(FatFreightBodyArea.FIELD_AREA_CODE, areaCode));
        FatFreightBodyArea area = factory.getWriteDataSession().querySingleResultByParams(FatFreightBodyArea.class,
                params);
        if (area != null)
            throw new CommonException(ResultConstant.Freight.BODY_AREA_IS_NOT_NULL);
        area = FatFreightBodyArea.getInstance(FatFreightBodyArea.class);
        area.setAreaCode(areaCode);
        area.setBodyId(bodyId);
        return factory.getCacheWriteDataSession().save(FatFreightBodyArea.class, area);
    }

    private List<FatFreightBodyArea> getBodyAreas(int bodyId) {
        Param params = ParamBuilder.getInstance().getParam().add(ParamBuilder.nv(FatFreightBodyArea.FIELD_BODY_ID, bodyId));
        return factory.getCacheReadDataSession().queryListResult(FatFreightBodyArea
                .class, params);
    }

    @Override
    public List<FatArea> getFatFreightBodyAreas(int bodyId) {
        List<FatFreightBodyArea> areaList = getBodyAreas(bodyId);
        List<FatArea> fatAreaList = new ArrayList<>(areaList.size());
        for (FatFreightBodyArea areaItem : areaList) {
            fatAreaList.add(areaService.getAreaByCode(areaItem.getAreaCode()));
        }
        return fatAreaList;
    }

    @Override
    public FreightTableVO viewFreightTable(int id) {
        FatFreightHead head = factory.getCacheReadDataSession().querySingleResultById(FatFreightHead.class, id);
        if (head == null)
            throw new CommonException(ResultConstant.Freight.TABLE_IS_NULL);
        Param param = ParamBuilder.getInstance().getParam().add(ParamBuilder.nv(FatFreightBody.FIELD_HEAD_ID, head
                .getId()));
        List<FatFreightBody> bodies = factory.getCacheReadDataSession().queryListResult(FatFreightBody.class, param);
        FreightTableVO tableVO = fillFreightTableVO(head);
        for (FatFreightBody body : bodies) {
            param.clean();
            param.add(ParamBuilder.nv(FatFreightBodyArea.FIELD_BODY_ID, body.getId()));
            List<FatFreightBodyArea> areaList = factory.getCacheReadDataSession().queryListResult(FatFreightBodyArea
                    .class, param);
            List<FatArea> fatAreaList = new ArrayList<>(areaList.size());
            for (FatFreightBodyArea areaItem : areaList) {
                fatAreaList.add(areaService.getAreaByCode(areaItem.getAreaCode()));
            }
            tableVO.addItem(fillFreightTableItemVO(body, fatAreaList));
        }
        return tableVO;
    }

    private FreightTableVO fillFreightTableVO(FatFreightHead head) {
        FreightTableVO vo = new FreightTableVO();
        SysDicItemVo expressCompanyItem = itemService.getDicItemByDicCodeAndItemValue(FLEX_COMPANY, head.getCompanyCode
                ());
        SysDicItemVo expressType = itemService.getDicItemByDicCodeAndItemValue(EXPRESS_TYPE, StringDefaultValue
                .StringValue(head.getExpressType()));
        vo.setFexName(expressCompanyItem.getDicItemName());
        vo.setType(expressType.getDicItemName());
        vo.setUpdateTime(head.getUpdateTime());
        vo.setTableId(head.getId());
        return vo;
    }

    @Override
    public List<FreightTableVO> listFreightTable(int shopId) {
        Param param = ParamBuilder.getInstance().getParam().add(ParamBuilder.nv(FatFreightHead.FIELD_SHOP_ID, shopId));
        List<FatFreightHead> heads = factory.getCacheReadDataSession().queryListResult(FatFreightHead.class, param);
        List<FreightTableVO> voList = new ArrayList<>(heads.size());
        for (FatFreightHead head : heads)
            voList.add(viewFreightTable(head.getId()));
        return voList;
    }

    @Override
    public FreightTableVO updateFreightTable(int id, Map<String, Object> param) {
        FatFreightHead head = id(FatFreightHead.class, id, ResultConstant.Freight.TABLE_IS_NULL);
        head = CommonBeanUtils.transMap2BasePO(param, head);
        factory.getCacheWriteDataSession().update(FatFreightHead.class, head);
        return viewFreightTable(head.getId());
    }

    @Override
    public FreightTableItemVO updateFreightItem(int id, Map<String, Object> param) {
        FatFreightBody body = id(FatFreightBody.class, id, ResultConstant.Freight.BODY_IS_NULL);
        int headId = StringDefaultValue.intValue(param.get(FatFreightBody.FIELD_HEAD_ID));
        if (headId != body.getHeadId())
            throw new CommonException(ResultConstant.Freight.BODY_HEAD_CAN_NOT_MODIFY);
        //处理标体的城市列表，先全部删除，再添加前端传来的
        String areaCode = StringDefaultValue.StringValue(param.get(FatFreightBodyArea.FIELD_AREA_CODE));
        String[] areaList = areaCode.split(Constant.Separator.COMMA);
        deleteFreightItemArea(id);
        if (areaList.length > 0) {
            for (String code : areaList) {
                addFatFreightBodyArea(id, code);
            }
        }
        body = CommonBeanUtils.transMap2BasePO(param, body);
        factory.getCacheWriteDataSession().update(FatFreightBody.class, body);
        return fillFreightTableItemVO(body);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteFreightItem(int id) {
        deleteFreightItemArea(id);
        factory.getCacheWriteDataSession().physicalDelete(FatFreightBody.class, id);

    }

    @Override
    public void deleteFreightTable(int id) {
        List<FatFreightBody> bodyList = getBodiesByHeadId(id);
        for (FatFreightBody body : bodyList)
            deleteFreightItem(body.getId());
        factory.getCacheWriteDataSession().physicalDelete(FatFreightHead.class, id);
    }

    @Override
    public void deleteFreightItemArea(int bodyId) {
        CustomSQL where = SQLCreator.where().cloumn(FatFreightBodyArea.FIELD_BODY_ID).operator(ESQLOperator.EQ).v
                (bodyId);
        factory.getCacheWriteDataSession().physicalWhereDelete(FatFreightBodyArea.class, where);

    }

    @Override
    public List<FatFreightBody> getBodiesByHeadId(int headId) {
        Param param = ParamBuilder.getInstance().getParam();
        param.add(ParamBuilder.nv(FatFreightBody.FIELD_HEAD_ID, headId));
        return factory.getCacheReadDataSession().queryListResult(FatFreightBody.class, param);
    }

    @Override
    public BigDecimal getPostage(String areaCode, int headId, BigDecimal totalWeight) {
//        FatFreightHead head = factory.getCacheReadDataSession().querySingleResultById(FatFreightHead.class,headId);
////        Param param = ParamBuilder.getInstance().getParam().add(ParamBuilder.nv(FatFreightHead
////                .FIELD_COMPANY_CODE, flexCode)).add(ParamBuilder.nv(FatFreightHead.FIELD_SHOP_ID, shopId));
////        FatFreightHead head = factory.getCacheReadDataSession().querySingleResultByParams(FatFreightHead.class, param);
//        if (head == null)
//            return new BigDecimal(0);
        logger.info("========>计算邮费开始：headId:{}", headId);
        Param param = ParamBuilder.getInstance().getParam();
        param.clean().add(ParamBuilder.nv(FatFreightBody.FIELD_HEAD_ID, headId));
        List<FatFreightBody> bodyList = factory.getCacheReadDataSession().queryListResult(FatFreightBody.class, param);

        FatFreightBody selectBody = null;
        for (FatFreightBody body : bodyList) {
            param.clean().add(ParamBuilder.nv(FatFreightBodyArea.FIELD_BODY_ID, body.getId())).add(ParamBuilder.nv
                    (FatFreightBodyArea.FIELD_AREA_CODE, areaService.getProvinceCode(areaCode)));
            FatFreightBodyArea area = factory.getCacheReadDataSession().querySingleResultByParams(FatFreightBodyArea
                    .class, param);
            if (area != null) {
                selectBody = body;
                break;
            }
        }
        //logger.info("========>计算邮费开始：selectBody:{}", selectBody.getFirstPrice());

        if (selectBody == null)
            return new BigDecimal(0);
        BigDecimal a = getPrice(selectBody, totalWeight);
        logger.info("次底层：{}",a);
        return a;
    }

    private BigDecimal getPrice(FatFreightBody body, BigDecimal totalWeight) {
        totalWeight = totalWeight.divide(new BigDecimal(1000));
        logger.info("计算邮费：totalWeight:{}", totalWeight);
        logger.info("计算邮费：firstPrice:{},firstBody:{}", body.getFirstPrice(), body.getFirstCount());
        BigDecimal firstPrice = body.getFirstPrice().multiply(body.getFirstCount());
        if (totalWeight.compareTo(body.getFirstCount()) == -1 || totalWeight.compareTo(body.getFirstCount()) == 0) {
            return firstPrice;
        }
        BigDecimal otherWeight = totalWeight.subtract(body.getFirstPrice());
        logger.info("计算邮费：otherWeight:{}", otherWeight);
        BigDecimal otherPrice = body.getOtherPrice().multiply(otherWeight);
        logger.info("计算邮费：otherPrice:{}", otherPrice);
        BigDecimal a = firstPrice.add(otherPrice);
        logger.info("最底层：{}",a);
        return a;
    }

    @Override
    public List<Map<String, Object>> getShopFlexList(int shopId) {
        List<FatFreightHead> headList = factory.getCacheReadDataSession().queryListResult(FatFreightHead.class,
                ParamBuilder.getInstance().getParam().add(ParamBuilder.nv(FatFreightHead.FIELD_SHOP_ID, shopId)));
        List<Map<String, Object>> result = new ArrayList<>(headList.size());
        for (FatFreightHead head : headList) {
            Map<String, Object> flexCompany = new HashMap<>(2);
            SysDicItemVo expressCompanyItem = itemService.getDicItemByDicCodeAndItemValue(FLEX_COMPANY, head.getCompanyCode
                    ());
            flexCompany.put("key", head.getId());
            flexCompany.put("value", expressCompanyItem.getDicItemName());
            result.add(flexCompany);
        }
        return result;
    }

}
