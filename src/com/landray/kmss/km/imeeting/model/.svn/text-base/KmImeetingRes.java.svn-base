package com.landray.kmss.km.imeeting.model;

import java.util.List;

import com.landray.kmss.common.convertor.ModelConvertor_ModelListToFormList;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.km.imeeting.forms.KmImeetingResForm;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.model.IAttachment;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.right.interfaces.ExtendAuthModel;
import com.landray.kmss.util.AutoHashMap;

/**
 * 会议室信息
 */
public class KmImeetingRes extends ExtendAuthModel implements IAttachment {

	/**
	 * 会议室名称
	 */
	protected String fdName;

	/**
	 * @return 会议室名称
	 */
	public String getFdName() {
		// return fdName;
		return SysLangUtil.getPropValue(this, "fdName", this.fdName);
	}

	/**
	 * @param fdName
	 *            会议室名称
	 */
	public void setFdName(String fdName) {
		this.fdName = fdName;
		SysLangUtil.setPropValue(this, "fdName", fdName);
	}

	/**
	 * 详情
	 */
	protected String fdDetail;

	/**
	 * @return 详情
	 */
	public String getFdDetail() {
		return fdDetail;
	}

	/**
	 * @param fdDetail
	 *            详情
	 */
	public void setFdDetail(String fdDetail) {
		this.fdDetail = fdDetail;
	}

	/**
	 * 地点楼层
	 */
	protected String fdAddressFloor;

	/**
	 * @return 地点楼层
	 */
	public String getFdAddressFloor() {
		return fdAddressFloor;
	}

	/**
	 * @param fdAddressFloor
	 *            地点楼层
	 */
	public void setFdAddressFloor(String fdAddressFloor) {
		this.fdAddressFloor = fdAddressFloor;
	}

	/**
	 * 容纳人数
	 */
	protected String fdSeats;

	/**
	 * @return 容纳人数
	 */
	public String getFdSeats() {
		return fdSeats;
	}

	/**
	 * @param fdSeats
	 *            容纳人数
	 */
	public void setFdSeats(String fdSeats) {
		this.fdSeats = fdSeats;
	}

	/**
	 * 是否有效
	 */
	protected Boolean fdIsAvailable = new Boolean(true);

	/**
	 * @return 是否有效
	 */
	public Boolean getFdIsAvailable() {
		return fdIsAvailable;
	}

	/**
	 * @param fdIsAvailable
	 *            是否有效
	 */
	public void setFdIsAvailable(Boolean fdIsAvailable) {
		this.fdIsAvailable = fdIsAvailable;
	}

	/**
	 * 所属分类
	 */
	protected KmImeetingResCategory docCategory;

	/**
	 * @return 所属分类
	 */
	public KmImeetingResCategory getDocCategory() {
		return docCategory;
	}

	/**
	 * @param docCategory
	 *            所属分类
	 */
	public void setDocCategory(KmImeetingResCategory docCategory) {
		this.docCategory = docCategory;
	}

	/**
	 * 排序号
	 */
	protected Integer fdOrder;

	/**
	 * @return 排序号
	 */
	public Integer getFdOrder() {
		return fdOrder;
	}

	/**
	 * @param fdOrder
	 *            排序号
	 */
	public void setFdOrder(Integer fdOrder) {
		this.fdOrder = fdOrder;
	}

	/**
	 * 保管者
	 */
	protected SysOrgElement docKeeper;

	/**
	 * @return 保管者
	 */
	public SysOrgElement getDocKeeper() {
		return docKeeper;
	}

	/**
	 * @param docKeeper
	 *            保管者
	 */
	public void setDocKeeper(SysOrgElement docKeeper) {
		this.docKeeper = docKeeper;
	}

	/**
	 * 最大使用时长(小时)
	 */
	protected Double fdUserTime;

	public Double getFdUserTime() {
		return fdUserTime;
	}

	public void setFdUserTime(Double fdUserTime) {
		this.fdUserTime = fdUserTime;
	}

	private String fdSeatDetail;// 坐席明细

	private String fdSeatCount;// 座位数

	private String fdCols;// 列

	private String fdRows;// 行

	/**
	 * 坐席明细
	 * 
	 * @return
	 */
	public String getFdSeatDetail() {
		return (String) readLazyField("fdSeatDetail", fdSeatDetail);
	}

	/**
	 * 坐席明细
	 */
	public void setFdSeatDetail(String fdSeatDetail) {
		this.fdSeatDetail = (String) writeLazyField("fdSeatDetail",
				this.fdSeatDetail, fdSeatDetail);
	}

	/**
	 * 座位数
	 * 
	 * @return
	 */
	public String getFdSeatCount() {
		return fdSeatCount;
	}

	/**
	 * 座位数
	 */
	public void setFdSeatCount(String fdSeatCount) {
		this.fdSeatCount = fdSeatCount;
	}

	public String getFdCols() {
		return fdCols;
	}

	public void setFdCols(String fdCols) {
		this.fdCols = fdCols;
	}

	public String getFdRows() {
		return fdRows;
	}

	public void setFdRows(String fdRows) {
		this.fdRows = fdRows;
	}

	@Override
    public Class getFormClass() {
		return KmImeetingResForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("docCategory.fdId", "docCategoryId");
			toFormPropertyMap.put("docCategory.fdName", "docCategoryName");
			toFormPropertyMap.put("docKeeper.fdId", "docKeeperId");
			toFormPropertyMap.put("docKeeper.deptLevelNames", "docKeeperName");
			toFormPropertyMap.put("fdInnerScreens",
					new ModelConvertor_ModelListToFormList(
							"fdInnerScreenForms"));
			toFormPropertyMap.put("fdOuterScreens",
					new ModelConvertor_ModelListToFormList(
							"fdOuterScreenForms"));
		}
		return toFormPropertyMap;
	}

	@Override
	public String getDocSubject() {
		return null;
	}

	/**
	 * 附件，用以实现会议室图片
	 */
	AutoHashMap autoHashMap = new AutoHashMap(AttachmentDetailsForm.class);

	@Override
	public AutoHashMap getAttachmentForms() {
		return autoHashMap;
	}

	/**
	 * 会议预定需保管者审批
	 */
	private Boolean fdNeedExamFlag = new Boolean(false);

	public Boolean getFdNeedExamFlag() {
		return fdNeedExamFlag;
	}

	public void setFdNeedExamFlag(Boolean fdNeedExamFlag) {
		this.fdNeedExamFlag = fdNeedExamFlag;
	}

	private Boolean fdInnerScreenEnable;// 是否有内屏

	private Boolean fdOuterScreenEnable;// 是否有外屏

	private List<KmImeetingInnerScreen> fdInnerScreens;// 内屏

	private List<KmImeetingOuterScreen> fdOuterScreens;// 外屏

	private Boolean fdSignInEnable;// 是否开启人脸签到

	private String fdSignInTypeCode;// 签到设备类型

	private String fdSignInIp;// 签到设备ip

	private String fdSignInPort;// 签到设备端口

	private String fdSignInUserName;// 签到设备用户名

	private String fdSignInPassword;// 签到设备密码

	public Boolean getFdInnerScreenEnable() {
		if (fdInnerScreenEnable == null) {
			return Boolean.FALSE;
		}
		return fdInnerScreenEnable;
	}

	public void setFdInnerScreenEnable(Boolean fdInnerScreenEnable) {
		this.fdInnerScreenEnable = fdInnerScreenEnable;
	}

	public Boolean getFdOuterScreenEnable() {
		if (fdOuterScreenEnable == null) {
			return Boolean.FALSE;
		}
		return fdOuterScreenEnable;
	}

	public void setFdOuterScreenEnable(Boolean fdOuterScreenEnable) {
		this.fdOuterScreenEnable = fdOuterScreenEnable;
	}

	public List<KmImeetingInnerScreen> getFdInnerScreens() {
		return fdInnerScreens;
	}

	public void setFdInnerScreens(List<KmImeetingInnerScreen> fdInnerScreens) {
		this.fdInnerScreens = fdInnerScreens;
	}

	public List<KmImeetingOuterScreen> getFdOuterScreens() {
		return fdOuterScreens;
	}

	public void setFdOuterScreens(List<KmImeetingOuterScreen> fdOuterScreens) {
		this.fdOuterScreens = fdOuterScreens;
	}

	public Boolean getFdSignInEnable() {
		if (fdSignInEnable == null) {
			return Boolean.FALSE;
		}
		return fdSignInEnable;
	}

	public void setFdSignInEnable(Boolean fdSignInEnable) {
		this.fdSignInEnable = fdSignInEnable;
	}

	public String getFdSignInTypeCode() {
		return fdSignInTypeCode;
	}

	public void setFdSignInTypeCode(String fdSignInTypeCode) {
		this.fdSignInTypeCode = fdSignInTypeCode;
	}

	public String getFdSignInIp() {
		return fdSignInIp;
	}

	public void setFdSignInIp(String fdSignInIp) {
		this.fdSignInIp = fdSignInIp;
	}

	public String getFdSignInPort() {
		return fdSignInPort;
	}

	public void setFdSignInPort(String fdSignInPort) {
		this.fdSignInPort = fdSignInPort;
	}

	public String getFdSignInUserName() {
		return fdSignInUserName;
	}

	public void setFdSignInUserName(String fdSignInUserName) {
		this.fdSignInUserName = fdSignInUserName;
	}

	public String getFdSignInPassword() {
		return fdSignInPassword;
	}

	public void setFdSignInPassword(String fdSignInPassword) {
		this.fdSignInPassword = fdSignInPassword;
	}

}
