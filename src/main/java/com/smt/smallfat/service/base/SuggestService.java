package com.smt.smallfat.service.base;

import com.csyy.core.obj.Pagination;
import com.smt.smallfat.po.FatSuggest;

import java.util.Map;

public interface SuggestService {
    FatSuggest addSuggest(Map<String, Object> param);

    void deleteSuggest(int id);

    FatSuggest updateSuggest(Map<String, Object> param);

    FatSuggest getSuggestById(int id);

    FatSuggest getSuggestByUUID(String uuid);

    Pagination<FatSuggest> paginationSuggest(Map<String, Object> param);
}
