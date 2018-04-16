package com.smt.smallfat.service.system.impl;

import com.csyy.core.apisupport.impl.BaseServiceImpl;
import com.csyy.core.datasource.param.Param;
import com.csyy.core.datasource.param.ParamBuilder;
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
}
