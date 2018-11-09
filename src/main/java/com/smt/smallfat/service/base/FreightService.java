package com.smt.smallfat.service.base;


import com.smt.smallfat.po.FatArea;
import com.smt.smallfat.po.FatFreightBody;
import com.smt.smallfat.vo.FreightTableItemVO;
import com.smt.smallfat.vo.FreightTableVO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface FreightService {

    String FLEX_COMPANY = "FLEX_COMPANY";
    String EXPRESS_TYPE = "EXPRESS_TYPE";
    String AREA_CODE= "areaCode";

    FreightTableVO addFreightTable(Map<String,Object> param);

    FreightTableItemVO addFreightTableItem(Map<String,Object> param);

    FreightTableItemVO addFreightItemArea(int bodyId,String areaCode);

    FreightTableVO viewFreightTable(int id);

    List<FreightTableVO> listFreightTable(int shopId);

    FreightTableVO updateFreightTable(int id,Map<String,Object> param);

    FreightTableItemVO updateFreightItem(int id,Map<String,Object> param);

    void deleteFreightItem(int id);

    void deleteFreightTable(int id);

    void deleteFreightItemArea(int bodyId);

    List<FatArea> getFatFreightBodyAreas(int bodyId);

    List<FatFreightBody> getBodiesByHeadId(int headId);

    BigDecimal getPostage(String areaCode, int headId, BigDecimal totalWeight);

    List<Map<String, Object>> getShopFlexList(int shopId);
}
