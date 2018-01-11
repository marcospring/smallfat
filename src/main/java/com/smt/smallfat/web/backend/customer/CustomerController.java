package com.smt.smallfat.web.backend.customer;

import com.csyy.common.StringDefaultValue;
import com.csyy.core.obj.Pagination;
import com.smt.smallfat.po.FatCustomer;
import com.smt.smallfat.service.base.CustomerService;
import com.smt.smallfat.web.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
@RequestMapping("/backend/customer")
public class CustomerController extends BaseController{

    @Autowired
    private CustomerService customerService;

    @RequestMapping("/list")
    public void list(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> param = getRequestParams(request);
        Pagination<FatCustomer> page  = customerService.pageCustomer(param);
        printWriter(response,successResultJSON(page));

    }

    @RequestMapping("/delete")
    public void delete(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> param = nullAbleValidation(request,FatCustomer.FIELD_ID);
        int id = StringDefaultValue.intValue(param.get(FatCustomer.FIELD_ID));
        customerService.deleteCustomer(id);
        printWriter(response,successResultJSON());

    }

    @RequestMapping("/getCustomerById")
    public void getCustomerById(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> param = nullAbleValidation(request,FatCustomer.FIELD_ID);
        int id = StringDefaultValue.intValue(param.get(FatCustomer.FIELD_ID));
        FatCustomer customer = customerService.getCustomerById(id);
        printWriter(response,successResultJSON(customer));

    }

}
