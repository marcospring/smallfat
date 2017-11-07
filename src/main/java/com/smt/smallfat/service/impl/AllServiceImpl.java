package com.smt.smallfat.service.impl;

import com.csyy.common.StringDefaultValue;
import com.csyy.core.apisupport.impl.BaseServiceImpl;
import com.csyy.core.datasource.param.Param;
import com.csyy.core.datasource.param.ParamBuilder;
import com.csyy.core.exception.CommonException;
import com.csyy.core.obj.Pagination;
import com.csyy.core.utils.CommonBeanUtils;
import com.smt.smallfat.constant.Constant;
import com.smt.smallfat.constant.ResultConstant;
import com.smt.smallfat.po.FatAll;
import com.smt.smallfat.service.AllService;
import com.smt.smallfat.service.system.SysDicItemService;
import com.smt.smallfat.service.system.SysDicService;
import com.smt.smallfat.vo.AllCountVO;
import com.smt.smallfat.vo.SysDicItemVo;
import com.smt.smallfat.vo.SysDicVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AllServiceImpl extends BaseServiceImpl implements AllService {

    @Autowired
    private SysDicService dicService;
    @Autowired
    private SysDicItemService dicItemService;

    @Override
    public FatAll addAll(Map<String, Object> param) {
        String subject = StringDefaultValue.StringValue(param.get(FatAll.FIELD_SUBJECT));
        String category = StringDefaultValue.StringValue(param.get(FatAll.FIELD_CATEGORY));
        String littleTitle = StringDefaultValue.StringValue(param.get(FatAll.FIELD_LITTLE_TITLE));
        Param params = ParamBuilder.getInstance().getParam().add(ParamBuilder.nv(FatAll.FIELD_SUBJECT, subject)).add
                (ParamBuilder.nv(FatAll.FIELD_CATEGORY, category)).add(ParamBuilder.nv(FatAll.FIELD_LITTLE_TITLE,
                littleTitle));
        FatAll all = factory.getCacheReadDataSession().querySingleResultByParams(FatAll.class, params);
        if (all != null)
            throw new CommonException(ResultConstant.All.ALL_IS_NOT_NULL);
        all = CommonBeanUtils.transMap2BasePO(param, FatAll.class);
        all = factory.getCacheWriteDataSession().save(FatAll.class, all);
        return all;
    }

    @Override
    public void deleteAll(int id) {
        factory.getCacheWriteDataSession().physicalDelete(FatAll.class, id);
    }

    @Override
    public FatAll updateAll(Map<String, Object> param) {
        int id = StringDefaultValue.intValue(param.get(FatAll.FIELD_ID));
        FatAll all = getAllById(id);
        all = CommonBeanUtils.transMap2BasePO(param, all);
        factory.getCacheWriteDataSession().update(FatAll.class, all);
        return all;
    }

    @Override
    public FatAll getAllById(int id) {

        FatAll all = factory.getReadDataSession().querySingleResultById(FatAll.class, id);
        if (all == null)
            throw new CommonException(ResultConstant.All.ALL_IS_NULL);
        return all;
    }

    @Override
    public FatAll getAllByUUID(String uuid) {
        FatAll all = factory.getReadDataSession().querySingleResultByUUID(FatAll.class, uuid);
        if (all == null)
            throw new CommonException(ResultConstant.All.ALL_IS_NULL);
        return all;
    }

    @Override
    public Pagination<FatAll> pageAll(Map<String, Object> param) {
        int pageNo = StringDefaultValue.intValue(param.get(Constant.PAGE_NO));
        int pageSize = StringDefaultValue.intValue(param.get(Constant.PAGE_SIZE));
        Param params = ParamBuilder.getInstance().getParam().add(param);
        Pagination<FatAll> page = queryClassPagination(FatAll.class, params, pageNo, pageSize);
        return page;
    }

    @Override
    public List<AllCountVO> getAllSubjectCount() {
        List<AllCountVO> voList = factory.getCacheReadDataSession().queryVOList(AllCountVO.class, new
                        Throwable(),
                ParamBuilder.getInstance().getParam());
        List<AllCountVO> temp = new ArrayList<>(0);
        for (AllCountVO vo : voList) {
            String code = vo.getCode();
            try {
                SysDicVo dic = dicService.getDicByCode(code);
                vo.setName(dic.getDicName());
                vo.setType(AllCountVO.SUBJECT);
                List<AllCountVO> categoryVOList = getCategoriesBySubject(code);
                if(categoryVOList == null)
                    categoryVOList = temp;
                vo.setSons(categoryVOList);
            } catch (CommonException e) {
                if (ResultConstant.SysDicResult.SYSDIC_IS_NULL.equals(e.getExceptionKey()))
                    vo.setName("");
            }
        }
        return voList;
    }

    @Override
    public List<AllCountVO> getCategoriesBySubject(String subject) {
        Param param = ParamBuilder.getInstance().getParam().add(ParamBuilder.nv(FatAll.FIELD_SUBJECT, subject));
        List<AllCountVO> voList = factory.getCacheReadDataSession().queryVOList(AllCountVO.class, new
                        Throwable(),
                param);
        for (AllCountVO vo : voList) {
            String value = vo.getCode();
            try {
                SysDicItemVo dicItem = dicItemService.getDicItemByDicCodeAndItemValue(subject, value);
                vo.setName(dicItem.getDicItemName());
                vo.setType(AllCountVO.CATEGORY);
                vo.setParentCode(subject);
            } catch (CommonException e) {
                if (ResultConstant.SysDicResult.SYSDIC_IS_NULL.equals(e.getExceptionKey()))
                    vo.setName("");
            }
        }
        return voList;
    }
}
