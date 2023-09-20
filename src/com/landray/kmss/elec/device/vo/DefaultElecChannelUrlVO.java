package com.landray.kmss.elec.device.vo;

public class DefaultElecChannelUrlVO extends AbstractElecChannelUrlVO {
	private static final long serialVersionUID = 1L;
	/**
	 * 签署链接
	 */
	private String signUrl;
	/**
	 * 签署人姓名
	 */
	private String signUserName;
	/**
	 * 签署人证件号码
	 */
	private String signUserLicenseNO;
	/**
	 * 签署人唯一标志
	 */
	private String signUserKey;
	/**
	 * 签署链接
	 * @return
	 */
	@Override
	public String getSignUrl() {
		return signUrl;
	}
	/**
	 * 签署链接
	 * @param signUrl
	 */
	@Override
	public void setSignUrl(String signUrl) {
		this.signUrl = signUrl;
	}
	/**
	 * 签署人姓名
	 * @return
	 */
	@Override
	public String getSignUserName() {
		return signUserName;
	}
	/**
	 * 签署人姓名
	 * @param signUserName
	 */
	@Override
	public void setSignUserName(String signUserName) {
		this.signUserName = signUserName;
	}
	/**
	 * 签署人证件号码
	 * @return
	 */
	@Override
	public String getSignUserLicenseNO() {
		return signUserLicenseNO;
	}
	/**
	 * 签署人证件号码
	 * @param signUserLicenseNO
	 */
	@Override
	public void setSignUserLicenseNO(String signUserLicenseNO) {
		this.signUserLicenseNO = signUserLicenseNO;
	}
	/**
	 * 签署人唯一标志
	 * @return
	 */
	@Override
	public String getSignUserKey() {
		return signUserKey;
	}
	/**
	 * 签署人唯一标志
	 * @param signUserKey
	 */
	@Override
	public void setSignUserKey(String signUserKey) {
		this.signUserKey = signUserKey;
	}

	@Override
	public String toString() {
		return "DefaultElecChannelUrlVO{" +
				"signUrl='" + signUrl + '\'' +
				", signUserName='" + signUserName + '\'' +
				", signUserLicenseNO='" + signUserLicenseNO + '\'' +
				", signUserKey='" + signUserKey + '\'' +
				'}';
	}
}
