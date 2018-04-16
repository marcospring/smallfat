package com.smt.smallfat.web.app;

import com.csyy.common.StringDefaultValue;
import com.csyy.core.obj.Pagination;
import com.smt.smallfat.constant.Constant;
import com.smt.smallfat.po.FatAll;
import com.smt.smallfat.service.base.AllService;
import com.smt.smallfat.vo.AllCountVO;
import com.smt.smallfat.vo.AllVO;
import com.smt.smallfat.web.common.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/app/all")
@Api(tags = "大全")
public class AppAllController extends BaseController {

    @Autowired
    private AllService allService;
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    @ApiOperation(value = "查询大全列表", notes = "查询大全的列表，可根据条件分页查询", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "littleTitle", value = "大全名称", required = false, dataType =
                    "String"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", value = "每页条数", required = false, dataType =
                    "int"),
            @ApiImplicitParam(paramType = "query", name = "pageNo", value = "页码", required = false, dataType =
                    "int"),
    })

    public void list(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = getRequestParams(request);
        Pagination<AllVO> page = allService.pageAll(param);
        printWriter(response, successResultJSON(page));
    }

    @RequestMapping("/allSubjectCount")
    public void allSubjectCount(HttpServletRequest request, HttpServletResponse response) {
        List<AllCountVO> list = allService.getAllSubjectCount();
        printWriter(response, successResultJSON(list));
    }

    @RequestMapping("/getAllById")
    public void getAllById(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request, FatAll.FIELD_ID);
        int id = StringDefaultValue.intValue(param.get(FatAll.FIELD_ID));
        int userId = StringDefaultValue.intValue(param.get(Constant.USER_ID));
        AllVO all = allService.getAllVOById(id,userId);
        all.setShareUrl(Constant.ALL_SHARE_URL + id);
        printWriter(response, successResultJSON(all));
    }

    @RequestMapping("/search")
    public void search(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request, FatAll.FIELD_LITTLE_TITLE);
        String name = StringDefaultValue.StringValue(param.get(FatAll.FIELD_LITTLE_TITLE));
        int pageNo = StringDefaultValue.intValue(param.get(Constant.PAGE_NO));
        int pageSize = StringDefaultValue.intValue(param.get(Constant.PAGE_SIZE));
        Pagination<FatAll> all = allService.search(name, pageNo, pageSize);
        printWriter(response, successResultJSON(all));
    }


}
