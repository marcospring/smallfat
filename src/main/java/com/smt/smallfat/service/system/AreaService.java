package com.smt.smallfat.service.system;

import com.smt.smallfat.po.FatArea;

import java.util.List;

public interface AreaService {
    List<FatArea> getAreaListByParentId(int parentId);
    List<FatArea> getAreaListByFirstLetter(String letter);
}
