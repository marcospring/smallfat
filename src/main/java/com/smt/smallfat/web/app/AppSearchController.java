package com.smt.smallfat.web.app;

import com.csyy.common.StringDefaultValue;
import com.csyy.core.obj.Pagination;
import com.smt.smallfat.constant.Constant;
import com.smt.smallfat.po.FatAll;
import com.smt.smallfat.service.base.AllService;
import com.smt.smallfat.service.base.ArticleService;
import com.smt.smallfat.service.base.GoodsService;
import com.smt.smallfat.vo.ArticleVO;
import com.smt.smallfat.vo.GoodsVO;
import com.smt.smallfat.web.common.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
@RequestMapping("/app")
@Api(tags = "搜索")
public class AppSearchController extends BaseController {

    private static final int ALL = 1;
    private static final int ARTICLE = 2;
    private static final int GOODS = 3;

    @Autowired
    private AllService allService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private GoodsService goodsService;

    @ApiOperation(value = "全局搜索接口", notes = "全局搜索接口")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "pageNo", value = "页码", dataType =
                    "int"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", value = "每页条数", dataType =
                    "int"),
            @ApiImplicitParam(paramType = "query", name = "title", value = "搜索文字", dataType =
                    "String"),
            @ApiImplicitParam(paramType = "query", name = "searchType", value = "搜索类型：1：大全；2：文章；3：商品", dataType =
                    "int", required = true),
    })
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public void search(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request, "searchType");
        String title = StringDefaultValue.StringValue(param.get("title"));
        int searchType = StringDefaultValue.intValue(param.get("searchType"));
//        int userId = StringDefaultValue.intValue(param.get(Constant.USER_ID));
        int pageNo = StringDefaultValue.intValue(param.get(Constant.PAGE_NO));
        int pageSize = StringDefaultValue.intValue(param.get(Constant.PAGE_SIZE));
        String successJson = successResultJSON();
        switch (searchType) {
            case ALL:
                Pagination<FatAll> allPage = allService.search(title, pageNo, pageSize);
                successJson = successResultJSON(allPage);
                break;
            case ARTICLE:
                Pagination<ArticleVO> articlePage = articleService.search(title, pageNo, pageSize);
                successJson = successResultJSON(articlePage);
                break;
            case GOODS:
                Pagination<GoodsVO> goodsPage = goodsService.search(title, pageNo, pageSize);
                successJson = successResultJSON(goodsPage);
                break;
        }
        printWriter(response, successJson);
    }
}
