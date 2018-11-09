package com.smt.smallfat.service.system.impl;

import com.csyy.core.apisupport.impl.BaseServiceImpl;
import com.csyy.core.datasource.param.*;
import com.csyy.core.utils.SQLUtil;
import com.smt.smallfat.po.FatArea;
import com.smt.smallfat.service.system.AreaService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AreaServiceImpl extends BaseServiceImpl implements AreaService {
    @Override
    public List<FatArea> getAreaListByParentId(int parentId) {
        Param param = ParamBuilder.getInstance().getParam().add(ParamBuilder.nv(FatArea.FIELD_PARENT_ID, parentId));
        List<FatArea> result = factory.getCacheReadDataSession().queryListResult(FatArea.class, param);
        return result;
    }

    @Override
    public List<FatArea> getAreaListByFirstLetter(String letter) {
        Param param = ParamBuilder.getInstance().getParam().add(ParamBuilder.nv(FatArea.FIELD_FIRST_LETTER, letter));
        List<FatArea> result = factory.getCacheReadDataSession().queryListResult(FatArea.class, param);
        return result;
    }

    @Override
    public FatArea getAreaByCode(String code) {
        Param param = ParamBuilder.getInstance().getParam().add(ParamBuilder.nv(FatArea.FIELD_AREA_CODE, code));
        return factory.getCacheReadDataSession().querySingleResultByParams(FatArea.class, param);
    }

    @Override
    public List<FatArea> areaListByAreaCode(String code) {
        CustomSQL where = SQLCreator.where().cloumn(FatArea.FIELD_AREA_CODE).operator(ESQLOperator.LIKE).v(SQLUtil
                .likeValue(code, SQLUtil.BACK));
        return factory.getCacheReadDataSession().queryListResultByWhere(FatArea.class, where);
    }

    @Override
    public String getProvinceCode(String areaCode) {
        return areaCode == null || "".equals(areaCode) ? "000000" : areaCode.substring(0, 2) + "0000";
    }
}
