package com.smt.smallfat.service.base.impl;

import com.csyy.common.StringDefaultValue;
import com.csyy.core.apisupport.impl.BaseServiceImpl;
import com.csyy.core.datasource.param.*;
import com.csyy.core.exception.CommonException;
import com.csyy.core.obj.Pagination;
import com.csyy.core.utils.CommonBeanUtils;
import com.smt.smallfat.constant.Constant;
import com.smt.smallfat.constant.ResultConstant;
import com.smt.smallfat.po.FatCustomerAddress;
import com.smt.smallfat.service.base.CustomerAddressService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
@Service
public class CustomerAddressServiceImpl extends BaseServiceImpl implements CustomerAddressService {
    @Override
    public FatCustomerAddress addAddress(Map<String, Object> param) {
        int isDefault = StringDefaultValue.intValue(param.get(FatCustomerAddress.FIELD_IS_DEFAULT));
        int userId = StringDefaultValue.intValue(param.get(FatCustomerAddress.FIELD_USER_ID));
        if (isDefault == DEFAULT)
            updateAddressNonDefault(userId);
        FatCustomerAddress address = CommonBeanUtils.transMap2BasePO(param, FatCustomerAddress.class);
        return factory.getCacheWriteDataSession().save(FatCustomerAddress.class, address);
    }

    private void updateAddressNonDefault(int userId) {
        Param params = ParamBuilder.getInstance().getParam().add(ParamBuilder.nv(FatCustomerAddress
                .FIELD_IS_DEFAULT, UN_DEFAULT));
        CustomSQL where = SQLCreator.where().cloumn(FatCustomerAddress.FIELD_DISABLED).operator(ESQLOperator.EQ)
                .v(Constant.WrapperExtend.ZERO).operator(ESQLOperator.AND).cloumn(FatCustomerAddress
                        .FIELD_USER_ID).operator(ESQLOperator.EQ).v(userId).operator(ESQLOperator.AND).cloumn
                        (FatCustomerAddress.FIELD_IS_DEFAULT).operator(ESQLOperator.EQ).v(DEFAULT);
        factory.getCacheWriteDataSession().updateCustomColumnByWhere(FatCustomerAddress.class, params, where);
    }

    @Override
    public void deleteAddress(String ids) {
        String[] idArray = ids.split(Constant.Separator.COMMA);
        for (String idStr : idArray) {
            int id = StringDefaultValue.intValue(idStr);
            factory.getCacheWriteDataSession().physicalDelete(FatCustomerAddress.class, id);
        }
    }

    @Override
    public FatCustomerAddress updateAddress(Map<String, Object> param) {
        int id = StringDefaultValue.intValue(param.get(FatCustomerAddress.FIELD_ID));
        int userId = StringDefaultValue.intValue(param.get(FatCustomerAddress.FIELD_USER_ID));
        int isDefault = StringDefaultValue.intValue(param.get(FatCustomerAddress.FIELD_IS_DEFAULT));
        if (isDefault == DEFAULT)
            updateAddressNonDefault(userId);
        FatCustomerAddress address = getAddressById(id);
        address = CommonBeanUtils.transMap2BasePO(param, address);
        factory.getCacheWriteDataSession().update(FatCustomerAddress.class, address);
        return address;
    }

    @Override
    public FatCustomerAddress getAddressById(int id) {
        FatCustomerAddress address = factory.getCacheReadDataSession().querySingleResultById(FatCustomerAddress
                .class,id);
        if(address == null)
            throw new CommonException(ResultConstant.CustomerAddress.ADDRESS_IS_NULL);
        return address;
    }

    @Override
    public FatCustomerAddress getAddressByUUID(String uuid) {
        FatCustomerAddress address = factory.getCacheReadDataSession().querySingleResultByUUID(FatCustomerAddress
                .class,uuid);
        if(address == null)
            throw new CommonException(ResultConstant.CustomerAddress.ADDRESS_IS_NULL);
        return address;
    }

    @Override
    public List<FatCustomerAddress> getAllAddress(int userId) {
        Param param = ParamBuilder.getInstance().getParam().add(ParamBuilder.nv(FatCustomerAddress.FIELD_USER_ID,
                userId));
        List<FatCustomerAddress> list = factory.getCacheReadDataSession().queryListResult(FatCustomerAddress.class,
                param);
        return list;
    }

    @Override
    public FatCustomerAddress setDefaultAddress(int id) {
        FatCustomerAddress address = getAddressById(id);
        updateAddressNonDefault(address.getUserId());
        address.setIsDefault(DEFAULT);
        factory.getCacheWriteDataSession().update(FatCustomerAddress.class,address);
        return address;
    }

    @Override
    public Pagination<FatCustomerAddress> pageAddress(Map<String, Object> param) {
        int pageSize = StringDefaultValue.intValue(param.get(Constant.PAGE_SIZE));
        int pageNo = StringDefaultValue.intValue(param.get(Constant.PAGE_NO));
        Param params = ParamBuilder.getInstance().getParam().add(param);
        Pagination<FatCustomerAddress> page = queryClassPagination(FatCustomerAddress.class
                ,params,pageNo,pageSize);
        return page;
    }
}
