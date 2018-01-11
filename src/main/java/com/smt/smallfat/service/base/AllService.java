package com.smt.smallfat.service.base;

import com.csyy.core.obj.Pagination;
import com.smt.smallfat.po.FatAll;
import com.smt.smallfat.vo.AllCountVO;

import java.util.List;
import java.util.Map;

public interface AllService {
    int ALL_FAVORITE_TYPE = 1;


    FatAll addAll(Map<String, Object> param);

    void deleteAll(int id);

    FatAll updateAll(Map<String, Object> param);

    FatAll getAllById(int id);

    FatAll getAllByUUID(String uuid);

    Pagination<FatAll> pageAll(Map<String, Object> param);

    List<AllCountVO> getAllSubjectCount();

    List<AllCountVO> getCategoriesBySubject(String subject);

    List<FatAll> search(String littleTitle);
}
