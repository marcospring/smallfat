package com.smt.smallfat.po;

import com.csyy.core.obj.BasePo;
import  java.math.BigDecimal;
/**
 * FatOrder实体
 * 
 * @author 系统生成
 *
 */
public class FatOrder extends BasePo {
	private static final long serialVersionUID = 1L;
	/**表名*/
	public static final String tableName = "FAT_ORDER";
	/**订单状态*/
	private Integer  state = 0;
	/**订单状态 对应的静态变量值*/
	public static final String FIELD_STATE = "state";
	/**订单总价格*/
	private BigDecimal  totalPrice = new BigDecimal(0);
	/**订单总价格 对应的静态变量值*/
	public static final String FIELD_TOTAL_PRICE = "totalPrice";
	/**订单总重量*/
	private BigDecimal  totalWeight = new BigDecimal(0);
	/**订单总重量 对应的静态变量值*/
	public static final String FIELD_TOTAL_WEIGHT = "totalWeight";
	/**用户id*/
	private Integer  customerId = 0;
	/**用户id 对应的静态变量值*/
	public static final String FIELD_CUSTOMER_ID = "customerId";
	/**用户备注*/
	private String  memo = "";
	/**用户备注 对应的静态变量值*/
	public static final String FIELD_MEMO = "memo";
	/**订单号*/
	private String  orderNo = "";
	/**订单号 对应的静态变量值*/
	public static final String FIELD_ORDER_NO = "orderNo";
	/**乐观锁*/
	private String  luckLock = "";
	/**乐观锁 对应的静态变量值*/
	public static final String FIELD_LUCK_LOCK = "luckLock";
	/**快递单号*/
	private String  flexNo = "";
	/**快递单号 对应的静态变量值*/
	public static final String FIELD_FLEX_NO = "flexNo";
	/**地址ID*/
	private Integer  addressId = 0;
	/**地址ID 对应的静态变量值*/
	public static final String FIELD_ADDRESS_ID = "addressId";
	/**邮费*/
	private BigDecimal  postage = new BigDecimal(0);
	/**邮费 对应的静态变量值*/
	public static final String FIELD_POSTAGE = "postage";
	/**微信订单号*/
	private String  transactionId = "";
	/**微信订单号 对应的静态变量值*/
	public static final String FIELD_TRANSACTION_ID = "transactionId";
	/**退款订单号*/
	private String  refundNo = "";
	/**退款订单号 对应的静态变量值*/
	public static final String FIELD_REFUND_NO = "refundNo";
	/**快递公司*/
	private String  expCompany = "";
	/**快递公司 对应的静态变量值*/
	public static final String FIELD_EXP_COMPANY = "expCompany";
	/***/
	private String  userAddress = "";
	/** 对应的静态变量值*/
	public static final String FIELD_USER_ADDRESS = "userAddress";
	
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public BigDecimal getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}
	public BigDecimal getTotalWeight() {
		return totalWeight;
	}
	public void setTotalWeight(BigDecimal totalWeight) {
		this.totalWeight = totalWeight;
	}
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getLuckLock() {
		return luckLock;
	}
	public void setLuckLock(String luckLock) {
		this.luckLock = luckLock;
	}
	public String getFlexNo() {
		return flexNo;
	}
	public void setFlexNo(String flexNo) {
		this.flexNo = flexNo;
	}
	public Integer getAddressId() {
		return addressId;
	}
	public void setAddressId(Integer addressId) {
		this.addressId = addressId;
	}
	public BigDecimal getPostage() {
		return postage;
	}
	public void setPostage(BigDecimal postage) {
		this.postage = postage;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getRefundNo() {
		return refundNo;
	}
	public void setRefundNo(String refundNo) {
		this.refundNo = refundNo;
	}
	public String getExpCompany() {
		return expCompany;
	}
	public void setExpCompany(String expCompany) {
		this.expCompany = expCompany;
	}
	public String getUserAddress() {
		return userAddress;
	}
	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}
}
