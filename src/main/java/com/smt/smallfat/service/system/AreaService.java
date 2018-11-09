package com.smt.smallfat.service.system;

import com.smt.smallfat.po.FatArea;

import java.util.List;

public interface AreaService {
    List<FatArea> getAreaListByParentId(int parentId);

    List<FatArea> getAreaListByFirstLetter(String letter);

    FatArea getAreaByCode(String code);

    List<FatArea> areaListByAreaCode(String code);

    String getProvinceCode(String areaCode);
}
