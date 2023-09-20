package com.landray.kmss.km.imeeting.forms;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_FormListToModelList;
import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.km.imeeting.model.KmImeetingRes;
import com.landray.kmss.km.imeeting.model.KmImeetingResCategory;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.forms.IAttachmentForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.right.interfaces.ExtendAuthForm;
import com.landray.kmss.util.AutoArrayList;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionMapping;

/**
 * 会议室信息 Form
 * 
 * @author
 * @version 1.0 2014-07-21
 */
public class KmImeetingResForm extends ExtendAuthForm implements
		IAttachmentForm {

	/**
	 * 会议室名称
	 */
	protected String fdName = null;

	/**
	 * @return 会议室名称
	 */
	public String getFdName() {
		return fdName;
	}

	/**
	 * @param fdName
	 *            会议室名称
	 */
	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	/**
	 * 排序号
	 */
	protected String fdOrder = null;

	/**
	 * @return 排序号
	 */
	public String getFdOrder() {
		return fdOrder;
	}

	/**
	 * @param fdOrder
	 *            排序号
	 */
	public void setFdOrder(String fdOrder) {
		this.fdOrder = fdOrder;
	}

	/**
	 * 详情
	 */
	protected String fdDetail = null;

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
	protected String fdAddressFloor = null;

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
	protected String fdSeats = null;

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
	protected String fdIsAvailable = null;

	/**
	 * @return 是否有效
	 */
	public String getFdIsAvailable() {
		return fdIsAvailable;
	}

	/**
	 * @param fdIsAvailable
	 *            是否有效
	 */
	public void setFdIsAvailable(String fdIsAvailable) {
		this.fdIsAvailable = fdIsAvailable;
	}

	/**
	 * 所属分类的ID
	 */
	protected String docCategoryId = null;

	/**
	 * @return 所属分类的ID
	 */
	public String getDocCategoryId() {
		return docCategoryId;
	}

	/**
	 * @param docCategoryId
	 *            所属分类的ID
	 */
	public void setDocCategoryId(String docCategoryId) {
		this.docCategoryId = docCategoryId;
	}

	/**
	 * 所属分类的名称
	 */
	protected String docCategoryName = null;

	/**
	 * @return 所属分类的名称
	 */
	public String getDocCategoryName() {
		return docCategoryName;
	}

	/**
	 * @param docCategoryName
	 *            所属分类的名称
	 */
	public void setDocCategoryName(String docCategoryName) {
		this.docCategoryName = docCategoryName;
	}

	/**
	 * 保管者的ID
	 */
	protected String docKeeperId = null;

	/**
	 * @return 保管者的ID
	 */
	public String getDocKeeperId() {
		return docKeeperId;
	}

	/**
	 * @param docKeeperId
	 *            保管者的ID
	 */
	public void setDocKeeperId(String docKeeperId) {
		this.docKeeperId = docKeeperId;
	}

	/**
	 * 保管者的名称
	 */
	protected String docKeeperName = null;

	/**
	 * @return 保管者的名称
	 */
	public String getDocKeeperName() {
		return docKeeperName;
	}

	/**
	 * @param docKeeperName
	 *            保管者的名称
	 */
	public void setDocKeeperName(String docKeeperName) {
		this.docKeeperName = docKeeperName;
	}

	/**
	 * 最大使用时长
	 */
	protected String fdUserTime = null;

	public String getFdUserTime() {
		return fdUserTime;
	}

	public void setFdUserTime(String fdUserTime) {
		this.fdUserTime = fdUserTime;
	}

	private String fdSeatDetail;// 坐席明细

	private String fdSeatCount;// 座位数

	/**
	 * 坐席明细
	 * 
	 * @return
	 */
	public String getFdSeatDetail() {
		return fdSeatDetail;
	}

	/**
	 * 坐席明细
	 */
	public void setFdSeatDetail(String fdSeatDetail) {
		this.fdSeatDetail = fdSeatDetail;
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
	
	private String fdCols;// 列数

	private String fdRows;// 行数

	public String getFdCols() {
		if (StringUtil.isNull(fdCols)) {
			setFdCols("22");
		}
		return fdCols;
	}

	public void setFdCols(String fdCols) {
		this.fdCols = fdCols;
	}

	public String getFdRows() {
		if (StringUtil.isNull(fdRows)) {
			setFdRows("15");
		}
		return fdRows;
	}

	public void setFdRows(String fdRows) {
		this.fdRows = fdRows;
	}

	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdName = null;
		fdOrder = null;
		fdDetail = null;
		fdAddressFloor = null;
		fdSeats = null;
		fdIsAvailable = null;
		docCategoryId = null;
		docCategoryName = null;
		docKeeperId = null;
		docKeeperName = null;
		fdUserTime = null;
		fdNeedExamFlag = null;
		fdSeatDetail = null;
		fdSeatCount = null;
		fdCols = null;
		fdRows = null;
		fdInnerScreenEnable = null;
		fdOuterScreenEnable = null;
		fdSignInEnable = null;
		fdSignInTypeCode = null;
		fdSignInIp = null;
		fdSignInPort = null;
		fdSignInUserName = null;
		fdSignInPassword = null;
		fdInnerScreenForms.clear();
		fdOuterScreenForms.clear();
		autoHashMap = new AutoHashMap(AttachmentDetailsForm.class);
		super.reset(mapping, request);
	}

	@Override
	public Class getModelClass() {
		return KmImeetingRes.class;
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
	public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("docCategoryId",
					new FormConvertor_IDToModel("docCategory",
							KmImeetingResCategory.class));
			toModelPropertyMap.put("docKeeperId", new FormConvertor_IDToModel(
					"docKeeper", SysOrgElement.class));
			toModelPropertyMap.put("fdInnerScreenForms",
					new FormConvertor_FormListToModelList("fdInnerScreens",
							""));
			toModelPropertyMap.put("fdOuterScreenForms",
					new FormConvertor_FormListToModelList("fdOuterScreens",
							""));

		}
		return toModelPropertyMap;
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
	protected String fdNeedExamFlag = null;

	public String getFdNeedExamFlag() {
		if (fdNeedExamFlag == null) {
            fdNeedExamFlag = "false";
        }
		return fdNeedExamFlag;
	}

	public void setFdNeedExamFlag(String fdNeedExamFlag) {
		this.fdNeedExamFlag = fdNeedExamFlag;
	}

	private String fdInnerScreenEnable;// 是否有内屏

	private String fdOuterScreenEnable;// 是否有外屏

	private String fdSignInEnable;// 是否开启人脸签到

	private String fdSignInTypeCode;// 签到设备类型

	private String fdSignInIp;// 签到设备ip

	private String fdSignInPort;// 签到设备端口

	private String fdSignInUserName;// 签到设备用户名

	private String fdSignInPassword;// 签到设备密码

	private List<KmImeetingInnerScreenForm> fdInnerScreenForms = new AutoArrayList(
			KmImeetingInnerScreenForm.class);// 内屏

	private List<KmImeetingOuterScreenForm> fdOuterScreenForms = new AutoArrayList(
			KmImeetingOuterScreenForm.class);// 外屏

	public String getFdInnerScreenEnable() {
		if (fdInnerScreenEnable == null) {
			fdInnerScreenEnable = Boolean.FALSE.toString();
		}
		return fdInnerScreenEnable;
	}

	public void setFdInnerScreenEnable(String fdInnerScreenEnable) {
		this.fdInnerScreenEnable = fdInnerScreenEnable;
	}

	public String getFdOuterScreenEnable() {
		if (fdOuterScreenEnable == null) {
			fdOuterScreenEnable = Boolean.FALSE.toString();
		}
		return fdOuterScreenEnable;
	}

	public void setFdOuterScreenEnable(String fdOuterScreenEnable) {
		this.fdOuterScreenEnable = fdOuterScreenEnable;
	}

	public List<KmImeetingInnerScreenForm> getFdInnerScreenForms() {
		return fdInnerScreenForms;
	}

	public void setFdInnerScreenForms(
			List<KmImeetingInnerScreenForm> fdInnerScreenForms) {
		this.fdInnerScreenForms = fdInnerScreenForms;
	}

	public List<KmImeetingOuterScreenForm> getFdOuterScreenForms() {
		return fdOuterScreenForms;
	}

	public void setFdOuterScreenForms(
			List<KmImeetingOuterScreenForm> fdOuterScreenForms) {
		this.fdOuterScreenForms = fdOuterScreenForms;
	}

	public String getFdSignInEnable() {
		return fdSignInEnable;
	}

	public void setFdSignInEnable(String fdSignInEnable) {
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
