package com.smt.smallfat.web.app;

import com.csyy.common.StringDefaultValue;
import com.csyy.core.obj.Pagination;
import com.smt.smallfat.constant.Constant;
import com.smt.smallfat.po.*;
import com.smt.smallfat.service.*;
import com.smt.smallfat.vo.FavoriteVO;
import com.smt.smallfat.web.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/app/customer")
public class AppCustomerController extends BaseController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private FavoriteService favoriteService;
    @Autowired
    private CustomerAddressService customerAddressService;

    @Autowired
    private SuggestService suggestService;

    @RequestMapping("/login")
    public void login(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = nullAbleValidation(request, FatCustomer.FIELD_USER_FROM, FatCustomer.FIELD_NICK_NAME,
                FatCustomer
                        .FIELD_OPEN_ID);
        FatCustomer customer = customerService.customerLogin(map);
        printWriter(response, successResultJSON(customer));
    }

    @RequestMapping("/favorite")
    public void addFavorite(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request, FatFavorite.FIELD_USER_ID, FatFavorite
                .FIELD_FAVORITE_TYPE, FatFavorite.FIELD_ARTICLE_ID, "operateType");
        favoriteService.operateFavorite(param);
        printWriter(response, successResultJSON());
    }

    @RequestMapping("/isFavorite")
    public void isFavorite(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> params = nullAbleValidation(request, FatFavorite.FIELD_USER_ID, FatFavorite
                .FIELD_FAVORITE_TYPE, FatFavorite.FIELD_ARTICLE_ID);
        int articleId = StringDefaultValue.intValue(params.get(FatFavorite.FIELD_ARTICLE_ID));
        int userId = StringDefaultValue.intValue(params.get(FatFavorite.FIELD_USER_ID));
        int favoriteType = StringDefaultValue.intValue(params.get(FatFavorite.FIELD_FAVORITE_TYPE));
        FatFavorite favorite = favoriteService.getIsFavorite(userId, articleId, favoriteType);
        if(favorite == null)
            printWriter(response,successResultJSON(FavoriteService.NOT_FAVORITE));
        else
            printWriter(response,successResultJSON(FavoriteService.IS_FAVORITE));
    }

    @RequestMapping("/myFavorite")
    public void favoriteArticles(HttpServletRequest request, HttpServletResponse response){
        Map<String ,Object> param = nullAbleValidation(request, Constant.USER_ID, FatFavorite.FIELD_FAVORITE_TYPE);
        Pagination<FavoriteVO> page = favoriteService.pageFavorite(param);
        printWriter(response,successResultJSON(page));
    }

    @RequestMapping("/addAddress")
    public void addAddress(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> param = nullAbleValidation(request, FatCustomerAddress.FIELD_RECEIVE_NAME,
                FatCustomerAddress.FIELD_MOBILE_NUMBER,FatCustomerAddress.FIELD_AREA_ADDRESS,FatCustomerAddress
                        .FIELD_ADDRESS,FatCustomerAddress.FIELD_USER_ID);
        FatCustomerAddress address = customerAddressService.addAddress(param);
        List<FatCustomerAddress> addressList = customerAddressService.getAllAddress(address.getUserId());
        printWriter(response,successResultJSON(addressList));
    }
    @RequestMapping("/deleteAddress")
    public void deleteAddress(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> param = nullAbleValidation(request,"ids",Constant.USER_ID);
        String ids = StringDefaultValue.StringValue(param.get("ids"));
        int userId = StringDefaultValue.intValue(param.get(Constant.USER_ID));
        customerAddressService.deleteAddress(ids);
        List<FatCustomerAddress> addressList = customerAddressService.getAllAddress(userId);
        printWriter(response,successResultJSON(addressList));
    }
    @RequestMapping("/updateAddress")
    public void updateAddress(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> param = nullAbleValidation(request, FatCustomerAddress.FIELD_ID);
        FatCustomerAddress address = customerAddressService.updateAddress(param);
        List<FatCustomerAddress> addressList = customerAddressService.getAllAddress(address.getUserId());
        printWriter(response,successResultJSON(addressList));
    }
    @RequestMapping("/getAddressById")
    public void getAddressById(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> param = nullAbleValidation(request, FatCustomerAddress.FIELD_ID);
        int id = StringDefaultValue.intValue(param.get(FatCustomerAddress.FIELD_ID));
        FatCustomerAddress address = customerAddressService.getAddressById(id);
        printWriter(response,successResultJSON(address));
    }
    @RequestMapping("/userAddressList")
    public void userAddressList(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> param = nullAbleValidation(request, FatCustomerAddress.FIELD_USER_ID);
        int userId = StringDefaultValue.intValue(param.get(FatCustomerAddress.FIELD_USER_ID));
        List<FatCustomerAddress> addressList = customerAddressService.getAllAddress(userId);
        printWriter(response,successResultJSON(addressList));
    }
    @RequestMapping("/setDefaultAddress")
    public void setDefaultAddress(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> param = nullAbleValidation(request, FatCustomerAddress.FIELD_ID);
        int id = StringDefaultValue.intValue(param.get(FatCustomerAddress.FIELD_ID));
        FatCustomerAddress address = customerAddressService.setDefaultAddress(id);
        List<FatCustomerAddress> addressList = customerAddressService.getAllAddress(address.getUserId());
        printWriter(response,successResultJSON(addressList));
    }

    @RequestMapping("/addSuggest")
    public void addSuggest(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> param = nullAbleValidation(request,FatSuggest.FIELD_CONTENT,FatSuggest
                .FIELD_NICK_NAME,FatSuggest.FIELD_USER_ID,FatSuggest.FIELD_MOBILE_TELEPHONE);
        FatSuggest suggest = suggestService.addSuggest(param);
        printWriter(response,successResultJSON(suggest));
    }
}
