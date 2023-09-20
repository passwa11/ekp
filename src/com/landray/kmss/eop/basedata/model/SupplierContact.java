package com.landray.kmss.eop.basedata.model;

/**
 * @author wangwh
 * @description:供应商列表展示VO
 * @date 2021/5/7
 */
public class SupplierContact extends EopBasedataSupplier {

	//第一联系人姓名
    private String contactName;
    
    //第一联系人电话
    private String contactPhone;

    //第一联系人邮箱
    private String contactEmail;

    /**
     * 第一联系人姓名
     */
	public String getContactName() {
		return contactName;
	}

	/**
     * 第一联系人姓名
     */
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	/**
     * 第一联系人电话
     */
	public String getContactPhone() {
		return contactPhone;
	}

	/**
     * 第一联系人电话
     */
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	/**
     * 第一联系人邮箱
     */
	public String getContactEmail() {
		return contactEmail;
	}

	/**
     * 第一联系人邮箱
     */
	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}
    
    

}
