package com.smt.smallfat.service.spider;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.smt.smallfat.po.FatSpider;
import com.smt.smallfat.service.spider.utils.MessageObj;
import com.smt.smallfat.vo.PaginationVo;

/**
 * service 的接口定义(实际使用中可以自行添加)
 * 
 * @author bruce
 *
 */
 
public interface DrzwSpiderService {

	 /**
     * 通过主键查询
     * @return
     */
     FatSpider findById(int id);

     /**
     * 返回分页后的数据
     * @return
     */
     PaginationVo<FatSpider> find(Map<String,Object> param);

	/**
	 * 添加一个
	 */
	 int addOne(FatSpider spider);
	
    /**
     * 添加多个
     */
     boolean addAll(List<MessageObj> objs);
    	
	/**
	 * 修改一个.
	 */
	 int updateOne(Map<String,Object> param);
	

	
	/**
	 * 删除一个
	 * @param pk
	 */
	 int deleteById(int pk);

    /**
     * 抓取入库
     * @return
     */
     void catchSpiderBest(InputStream inputStream);
    /**
     * 添加至精选
     * @param spiderID
     */
     void addBest(String spiderID);
    
}
