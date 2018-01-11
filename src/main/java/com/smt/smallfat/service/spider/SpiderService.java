package com.smt.smallfat.service.spider;

import com.csyy.core.obj.Pagination;
import com.smt.smallfat.po.FatSpider;
import com.smt.smallfat.service.spider.utils.MessageObj;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public interface SpiderService {

    /**
     * 通过主键查询
     * @return
     */
    FatSpider findById(int id);

    /**
     * 返回分页后的数据
     * @return
     */
    Pagination<FatSpider> find(Map<String,Object> param);

    /**
     * 添加一个
     */
    void addOne(FatSpider spider);

    /**
     * 添加多个
     */
    boolean addAll(List<MessageObj> objs);

    /**
     * 修改一个.
     */
    void updateOne(Map<String,Object> param);



    /**
     * 删除一个
     * @param pk
     */
    void deleteById(int pk);

    /**
     * 抓取入库
     * @return
     */
    void catchSpiderBest(InputStream inputStream);
    /**
     * 添加至精选
     * @param spiderID
     */
    void addBest(int spiderID);
}
