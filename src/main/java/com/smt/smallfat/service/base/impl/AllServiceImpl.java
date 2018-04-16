package com.smt.smallfat.service.base.impl;

import com.csyy.common.StringDefaultValue;
import com.csyy.core.apisupport.impl.BaseServiceImpl;
import com.csyy.core.datasource.param.*;
import com.csyy.core.exception.CommonException;
import com.csyy.core.obj.Pagination;
import com.csyy.core.utils.CommonBeanUtils;
import com.csyy.core.utils.SQLUtil;
import com.smt.smallfat.constant.Constant;
import com.smt.smallfat.constant.ResultConstant;
import com.smt.smallfat.po.FatAll;
import com.smt.smallfat.po.FatCustomer;
import com.smt.smallfat.po.FatFavorite;
import com.smt.smallfat.service.base.AllService;
import com.smt.smallfat.service.base.CustomerService;
import com.smt.smallfat.service.base.FavoriteService;
import com.smt.smallfat.service.system.SysDicItemService;
import com.smt.smallfat.service.system.SysDicService;
import com.smt.smallfat.vo.*;
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
    @Autowired
    private FavoriteService favoriteService;

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
    public AllVO getAllVOById(int id,int userId) {
        StringBuilder builder = new StringBuilder(Constant.ALL_SHARE_URL);
        FatAll all = getAllById(id);
        FatFavorite favorite = favoriteService.getIsFavorite(userId, all.getId(), FavoriteService.ALL);
        int isLove = userId == 0 ? FavoriteService.NOT_FAVORITE : favorite == null ? FavoriteService
                .NOT_FAVORITE : FavoriteService.IS_FAVORITE;
        AllVO vo = new AllVO(all.getId(), all.getUuid(), all.getLittleImg(), all.getLittleTitle(), isLove, all
                .getSubject(), all.getCategory(), all.getBigImg(), all.getLatinName(), all.getSubjectDesc(), all
                .getDescription(), all.getClickCount(), builder.append(all.getId()).toString(), all.getOrderCombin());
        vo.setFavoriteCount(favoriteService.getFavoriteCount(all.getId(),FavoriteService.ALL));
        return vo;
    }

    @Override
    public FatAll getAllByUUID(String uuid) {
        FatAll all = factory.getReadDataSession().querySingleResultByUUID(FatAll.class, uuid);
        if (all == null)
            throw new CommonException(ResultConstant.All.ALL_IS_NULL);
        return all;
    }

    @Override
    public Pagination<AllVO> pageAll(Map<String, Object> param) {
        int pageNo = StringDefaultValue.intValue(param.get(Constant.PAGE_NO));
        int pageSize = StringDefaultValue.intValue(param.get(Constant.PAGE_SIZE));
        int userId = StringDefaultValue.intValue(param.get(Constant.USER_ID));
        Param params = ParamBuilder.getInstance().getParam().add(param);
        Pagination<FatAll> page = queryClassPagination(FatAll.class, params, pageNo, pageSize);

        List<FatAll> data = page.getData();
        List<AllVO> dataVo = new ArrayList<>(data.size());
        StringBuilder builder = new StringBuilder(Constant.ALL_SHARE_URL);
        for (FatAll all : data) {
            builder.replace(0, builder.toString().length(), Constant.ALL_SHARE_URL);
            FatFavorite favorite = favoriteService.getIsFavorite(userId, all.getId(), FavoriteService.ALL);
            int isLove = userId == 0 ? FavoriteService.NOT_FAVORITE : favorite == null ? FavoriteService
                    .NOT_FAVORITE : FavoriteService.IS_FAVORITE;
            AllVO vo = new AllVO(all.getId(), all.getUuid(), all.getLittleImg(), all.getLittleTitle(), isLove, all
                    .getSubject(), all.getCategory(), all.getBigImg(), all.getLatinName(), all.getSubjectDesc(), all
                    .getDescription(), all.getClickCount(), builder.append(all.getId()).toString(), all.getOrderCombin());
            dataVo.add(vo);
        }
        Pagination<AllVO> pageVO = new Pagination<>(dataVo, page.getPageNo(), page.getPageSize());
        pageVO.setRecordsTotal(page.getRecordsTotal());

        return pageVO;
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
                if (categoryVOList == null)
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

    @Override
    public Pagination<FatAll> search(String littleTitle, int pageNo, int pageSize) {
        CustomSQL where = SQLCreator.where();
        where.cloumn(FatAll.FIELD_LITTLE_TITLE).operator(ESQLOperator.LIKE).v(SQLUtil.likeValue(littleTitle, SQLUtil
                .ALL));
        Pagination<FatAll> list = queryClassPagination(FatAll.class, where, pageNo, pageSize);
        return list;
    }

    @Override
    public FavoriteUsersVO favoriteUsers(int id) {
        Param param = ParamBuilder.getInstance().getParam().add(ParamBuilder.nv(FatFavorite.FIELD_FAVORITE_TYPE,
                FavoriteService.ALL)).add(ParamBuilder.nv(FatFavorite.FIELD_ARTICLE_ID, id));
        List<FatFavorite> list = factory.getCacheReadDataSession().queryListResult(FatFavorite.class, param);
        List<FatCustomer> users = new ArrayList<>(list.size());
        for (FatFavorite favorite : list) {
            users.add(factory.getCacheReadDataSession().querySingleResultById(FatCustomer.class, favorite.getUserId()));
        }
        FavoriteUsersVO vo = new FavoriteUsersVO();
        vo.setFavoriteCount(list.size());
        vo.setUsers(users);
        return vo;
    }
}
