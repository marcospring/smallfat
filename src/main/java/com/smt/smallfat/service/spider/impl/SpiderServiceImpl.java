package com.smt.smallfat.service.spider.impl;

import com.csyy.common.StringDefaultValue;
import com.csyy.core.apisupport.impl.BaseServiceImpl;
import com.csyy.core.datasource.param.Param;
import com.csyy.core.datasource.param.ParamBuilder;
import com.csyy.core.exception.CommonException;
import com.csyy.core.obj.Pagination;
import com.csyy.core.utils.CommonBeanUtils;
import com.smt.smallfat.constant.Constant;
import com.smt.smallfat.constant.ResultConstant;
import com.smt.smallfat.po.FatArticle;
import com.smt.smallfat.po.FatSpider;
import com.smt.smallfat.service.base.ArticleService;
import com.smt.smallfat.service.spider.SpiderService;
import com.smt.smallfat.service.spider.utils.MessageObj;
import com.smt.smallfat.service.spider.utils.SpiderTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Service
public class SpiderServiceImpl extends BaseServiceImpl implements SpiderService {

    private SpiderTool tool = new SpiderTool();
    @Autowired
    private ArticleService articleService;
    Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public FatSpider findById(int id) {
        FatSpider spider = factory.getCacheReadDataSession().querySingleResultById(FatSpider.class, id);
        if (spider == null)
            throw new CommonException(ResultConstant.Spider.SPIDER_IS_NULL);
        return spider;
    }

    @Override
    public Pagination<FatSpider> find(Map<String, Object> param) {
        int pageNo = StringDefaultValue.intValue(param.get(Constant.PAGE_NO));
        int pageSize = StringDefaultValue.intValue(param.get(Constant.PAGE_SIZE));
        Param params = ParamBuilder.getInstance().getParam();
        params.add(param);
        params.add(ParamBuilder.nv(Constant.SQLConstants.ORDER_COLUMN, FatSpider.FIELD_UPDATE_TIME));
        params.add(ParamBuilder.nv(Constant.SQLConstants.ORDER_BY_TYPE, Constant.SQLConstants.DESC));
        Pagination<FatSpider> page = queryClassPagination(FatSpider.class, params, pageNo, pageSize);
        return page;
    }

    @Override
    public void addOne(FatSpider spider) {
        factory.getCacheWriteDataSession().save(FatSpider.class, spider);
    }

    @Override
    public boolean addAll(List<MessageObj> objs) {
        return false;
    }

    @Override
    public void updateOne(Map<String, Object> param) {
        int id = StringDefaultValue.intValue(param.get(FatSpider.FIELD_ID));
        FatSpider spider = findById(id);
        spider = CommonBeanUtils.transMap2BasePO(param, spider);
        factory.getCacheWriteDataSession().update(FatSpider.class, spider);

    }

    @Override
    public void deleteById(int pk) {
        factory.getCacheWriteDataSession().physicalDelete(FatSpider.class, pk);
    }

    @Override
    public void catchSpiderBest(InputStream inputStream) {
        List<FatSpider> list = tool.fromJsonToList(tool.fileReader(inputStream));
        for (FatSpider spider : list) {
            addOne(spider);
        }
    }

    @Override
    public void addBest(int spiderID) {
        FatSpider spider = findById(spiderID);
        FatArticle best = FatArticle.getInstance(FatArticle.class);
        best.setTitle(spider.getTitle());
        best.setContent(spider.getContent());
        best.setPic(spider.getCover());
        best.setAuthor(spider.getTag());
        best.setArticleType(163);
        articleService.addArticleObject(best);
    }
}
