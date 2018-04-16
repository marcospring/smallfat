package com.smt.smallfat.service.base;

import com.csyy.core.obj.Pagination;
import com.smt.smallfat.po.FatAll;
import com.smt.smallfat.vo.AllCountVO;
import com.smt.smallfat.vo.AllVO;
import com.smt.smallfat.vo.FavoriteUsersVO;

import java.util.List;
import java.util.Map;

public interface AllService {
    int ALL_FAVORITE_TYPE = 1;


    FatAll addAll(Map<String, Object> param);

    void deleteAll(int id);

    FatAll updateAll(Map<String, Object> param);

    FatAll getAllById(int id);

    AllVO getAllVOById(int id,int userId);

    FatAll getAllByUUID(String uuid);

    Pagination<AllVO> pageAll(Map<String, Object> param);

    List<AllCountVO> getAllSubjectCount();

    List<AllCountVO> getCategoriesBySubject(String subject);

    Pagination<FatAll> search(String littleTitle, int pageNo, int pageSize);

    FavoriteUsersVO favoriteUsers(int id);
}
