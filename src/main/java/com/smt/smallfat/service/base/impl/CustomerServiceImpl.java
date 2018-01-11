package com.smt.smallfat.service.base.impl;

import com.csyy.common.StringDefaultValue;
import com.csyy.core.apisupport.impl.BaseServiceImpl;
import com.csyy.core.datasource.param.Param;
import com.csyy.core.datasource.param.ParamBuilder;
import com.csyy.core.exception.CommonException;
import com.csyy.core.obj.Pagination;
import com.csyy.core.utils.CommonBeanUtils;
import com.smt.smallfat.constant.Constant;
import com.smt.smallfat.constant.ResultConstant;
import com.smt.smallfat.po.FatCustomer;
import com.smt.smallfat.service.base.CustomerService;
import com.smt.smallfat.service.house.CircleService;
import com.smt.smallfat.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CustomerServiceImpl extends BaseServiceImpl implements CustomerService {


    @Autowired
    private CircleService circleService;

    @Override
    public FatCustomer addCustomer(Map<String, Object> param) {
        FatCustomer customer = CommonBeanUtils.transMap2BasePO(param, FatCustomer.class);
        customer = factory.getCacheWriteDataSession().save(FatCustomer.class, customer);
        return customer;
    }

    @Override
    public void deleteCustomer(int id) {
        getCustomerById(id);
        factory.getCacheWriteDataSession().physicalDelete(FatCustomer.class, id);
    }

    @Override
    public FatCustomer updateCustomer(Map<String, Object> param) {
        int id = StringDefaultValue.intValue(param.get(FatCustomer.FIELD_ID));
        FatCustomer customer = getCustomerById(id);
        customer = CommonBeanUtils.transMap2BasePO(param, customer);
        factory.getCacheWriteDataSession().update(FatCustomer.class, customer);
        return customer;
    }

    @Override
    public Pagination<FatCustomer> pageCustomer(Map<String, Object> param) {
        int pageNo = StringDefaultValue.intValue(param.get(Constant.PAGE_NO));
        int pageSize = StringDefaultValue.intValue(param.get(Constant.PAGE_SIZE));
        Param params = ParamBuilder.getInstance().getParam().add(param);
        Pagination<FatCustomer> page = queryClassPagination(FatCustomer.class, params, pageNo, pageSize);
        return page;
    }

    @Override
    public FatCustomer getCustomerById(int id) {
        FatCustomer customer = factory.getCacheReadDataSession().querySingleResultById(FatCustomer.class, id);
        if (customer == null)
            throw new CommonException(ResultConstant.Customer.CUSTOMER_IS_NULL);

        return customer;
    }

    @Override
    public FatCustomer getCustomerByUUID(String uuid) {
        FatCustomer customer = factory.getCacheReadDataSession().querySingleResultByUUID(FatCustomer.class, uuid);
        if (customer == null)
            throw new CommonException(ResultConstant.Customer.CUSTOMER_IS_NULL);

        return customer;
    }

    @Override
    public FatCustomer customerLogin(Map<String, Object> param) {
        String openId = StringDefaultValue.StringValue(param.get(FatCustomer.FIELD_OPEN_ID));
        Param params = ParamBuilder.getInstance().getParam().add(ParamBuilder.nv(FatCustomer.FIELD_OPEN_ID, openId));
        FatCustomer customer = factory.getCacheReadDataSession().querySingleResultByParams(FatCustomer.class, params);
        if (customer == null) {
            customer = CommonBeanUtils.transMap2BasePO(param, FatCustomer.class);
            customer = factory.getCacheWriteDataSession().save(FatCustomer.class, customer);
        } else {
            customer = CommonBeanUtils.transMap2BasePO(param, customer);
            factory.getCacheWriteDataSession().update(FatCustomer.class,customer);
        }

        return customer;
    }

    @Override
    public UserVO getUserVO(int userId) {
        UserVO vo = new UserVO();
        vo.setUser(getCustomerById(userId));
        vo.setBeFollowedCount(circleService.beFollowedCount(userId));
        vo.setFollowCount(circleService.followCount(userId));
        vo.setPraiseCount(circleService.userPraiseCount(userId));
        return vo;
    }
}
