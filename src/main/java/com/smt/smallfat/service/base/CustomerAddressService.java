package com.smt.smallfat.service.base;

import com.csyy.core.obj.Pagination;
import com.smt.smallfat.po.FatCustomerAddress;

import java.util.List;
import java.util.Map;

public interface CustomerAddressService {
    /**
     * 默认地址设置，1：默认地址；0：非默认地址
     */
    int DEFAULT = 1;
    int UN_DEFAULT = 0;

    FatCustomerAddress addAddress(Map<String, Object> param);

    void deleteAddress(String ids);

    FatCustomerAddress updateAddress(Map<String, Object> param);

    FatCustomerAddress getAddressById(int id);

    FatCustomerAddress getAddressByUUID(String uuid);

    List<FatCustomerAddress> getAllAddress(int userId);

    FatCustomerAddress setDefaultAddress(int id);

    Pagination<FatCustomerAddress> pageAddress(Map<String, Object> param);
}
