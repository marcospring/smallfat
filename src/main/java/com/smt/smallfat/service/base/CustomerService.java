package com.smt.smallfat.service.base;

import com.csyy.core.obj.Pagination;
import com.smt.smallfat.po.FatCustomer;
import com.smt.smallfat.vo.UserVO;

import java.util.Map;

public interface CustomerService {
    FatCustomer addCustomer(Map<String,Object> param);
    void deleteCustomer(int id);
    FatCustomer updateCustomer(Map<String,Object> param);
    Pagination<FatCustomer> pageCustomer(Map<String,Object > param);
    FatCustomer getCustomerById(int id);
    FatCustomer getCustomerByUUID(String uuid);
    FatCustomer customerLogin(Map<String,Object> param);
    UserVO getUserVO(int userId);
}
