package com.smt.smallfat.web.backend.customer;

import com.csyy.core.obj.Pagination;
import com.smt.smallfat.po.FatCustomerAddress;
import com.smt.smallfat.service.base.CustomerAddressService;
import com.smt.smallfat.web.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
@RequestMapping("/backend/address")
public class AddressController extends BaseController{
    @Autowired
    private CustomerAddressService addressService;

    @RequestMapping("/list")
    public void list(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> param = getRequestParams(request);
        Pagination<FatCustomerAddress> page = addressService.pageAddress(param);
        printWriter(response,successResultJSON(page));
    }
}
