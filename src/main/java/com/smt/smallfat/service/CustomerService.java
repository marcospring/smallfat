package com.smt.smallfat.service;

import com.csyy.core.obj.Pagination;
import com.smt.smallfat.po.FatCustomer;

import java.util.Map;

public interface CustomerService {
    FatCustomer addCustomer(Map<String,Object> param);
    void deleteCustomer(int id);
    FatCustomer updateCustomer(Map<String,Object> param);
    Pagination<FatCustomer> pageCustomer(Map<String,Object > param);
    FatCustomer getCustomerById(int id);
    FatCustomer getCustomerByUUID(String uuid);
    FatCustomer customerLogin(Map<String,Object> param);
}
