package com.landray.kmss.km.imeeting.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.km.imeeting.ImeetingConstant;
import com.landray.kmss.km.imeeting.model.KmImeetingBook;
import com.landray.kmss.km.imeeting.model.KmImeetingRes;
import com.landray.kmss.sys.authorization.interfaces.ISysAuthAreaForm;
import com.landray.kmss.sys.authorization.model.SysAuthArea;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.web.action.ActionMapping;


/**
 * 会议室预约 Form
 * 
 * @author 
 * @version 1.0 2014-07-21
 */
public class KmImeetingBookForm extends ExtendForm implements ISysAuthAreaForm {

	/**
	 * 会议名称
	 */
	protected String fdName = null;
	
	/**
	 * @return 会议名称
	 */
	public String getFdName() {
		return fdName;
	}
	
	/**
	 * @param fdName 会议名称
	 */
	public void setFdName(String fdName) {
		this.fdName = fdName;
	}
	
	/**
	 * 召开日期
	 */
	protected String fdHoldDate = null;
	
	/**
	 * @return 召开日期
	 */
	public String getFdHoldDate() {
		return fdHoldDate;
	}
	
	/**
	 * @param fdHoldDate 召开日期
	 */
	public void setFdHoldDate(String fdHoldDate) {
		this.fdHoldDate = fdHoldDate;
	}
	
	/**
	 * 结束日期
	 */
	protected String fdFinishDate = null;
	
	/**
	 * @return 结束日期
	 */
	public String getFdFinishDate() {
		return fdFinishDate;
	}
	
	/**
	 * @param fdFinishDate 结束日期
	 */
	public void setFdFinishDate(String fdFinishDate) {
		this.fdFinishDate = fdFinishDate;
	}
	
	/**
	 * 会议历时
	 */
	protected String fdHoldDuration = null;

	/**
	 * @return 会议历时
	 */
	public String getFdHoldDuration() {
		return fdHoldDuration;
	}

	/**
	 * @param fdHoldDuration
	 *            会议历时
	 */
	public void setFdHoldDuration(String fdHoldDuration) {
		this.fdHoldDuration = fdHoldDuration;
	}

	/**
	 * 备注
	 */
	protected String fdRemark = null;
	
	/**
	 * @return 备注
	 */
	public String getFdRemark() {
		return fdRemark;
	}
	
	/**
	 * @param fdRemark 备注
	 */
	public void setFdRemark(String fdRemark) {
		this.fdRemark = fdRemark;
	}
	
	/**
	 * 会议登记人的ID
	 */
	protected String docCreatorId = null;
	
	/**
	 * @return 会议登记人的ID
	 */
	public String getDocCreatorId() {
		return docCreatorId;
	}
	
	/**
	 * @param docCreatorId 会议登记人的ID
	 */
	public void setDocCreatorId(String docCreatorId) {
		this.docCreatorId = docCreatorId;
	}
	
	/**
	 * 会议登记人的名称
	 */
	protected String docCreatorName = null;
	
	/**
	 * @return 会议登记人的名称
	 */
	public String getDocCreatorName() {
		return docCreatorName;
	}
	
	/**
	 * @param docCreatorName 会议登记人的名称
	 */
	public void setDocCreatorName(String docCreatorName) {
		this.docCreatorName = docCreatorName;
	}
	
	/**
	 * 会议地点的ID
	 */
	protected String fdPlaceId = null;
	
	/**
	 * @return 会议地点的ID
	 */
	public String getFdPlaceId() {
		return fdPlaceId;
	}
	
	/**
	 * @param fdPlaceId 会议地点的ID
	 */
	public void setFdPlaceId(String fdPlaceId) {
		this.fdPlaceId = fdPlaceId;
	}
	/**
	 * 会议地点的保管者
	 */
	public String docKeeperName = null;
	public String getDocKeeperName() {
		return docKeeperName;
	}

	public void setDocKeeperName(String docKeeperName) {
		this.docKeeperName = docKeeperName;
	}


	/**
	 * 会议地点设备详情
	 */
	public String fdPlaceDetail = null;
	
	public String getFdPlaceDetail() {
		return fdPlaceDetail;
	}

	public void setFdPlaceDetail(String fdPlaceDetail) {
		this.fdPlaceDetail = fdPlaceDetail;
	}
	/**
	 * 会议地点的容纳人数
	 */
	public String fdPlaceSeats = null;
	
	public String getFdPlaceSeats() {
		return fdPlaceSeats;
	}

	public void setFdPlaceSeats(String fdPlaceSeats) {
		this.fdPlaceSeats = fdPlaceSeats;
	}
	/**
	 * 会议地点的地点楼层
	 */
	public String fdPlaceAddressFloor = null;
	
	public String getFdPlaceAddressFloor() {
		return fdPlaceAddressFloor;
	}

	public void setFdPlaceAddressFloor(String fdPlaceAddressFloor) {
		this.fdPlaceAddressFloor = fdPlaceAddressFloor;
	}
	
	/**
	 * 会议地点的名称
	 */
	protected String fdPlaceName = null;
	
	/**
	 * @return 会议地点的名称
	 */
	public String getFdPlaceName() {
		return fdPlaceName;
	}
	
	/**
	 * @param fdPlaceName 会议地点的名称
	 */
	public void setFdPlaceName(String fdPlaceName) {
		this.fdPlaceName = fdPlaceName;
	}
	
	private String fdUserTime;

	public String getFdUserTime() {
		return fdUserTime;
	}

	public void setFdUserTime(String fdUserTime) {
		this.fdUserTime = fdUserTime;
	}

	private String fdRecurrenceStr;

	public String getFdRecurrenceStr() {
		return fdRecurrenceStr;
	}

	public void setFdRecurrenceStr(String fdRecurrenceStr) {
		this.fdRecurrenceStr = fdRecurrenceStr;
	}

	private String fdRepeatType;// 重复类型

	private String fdRepeatFrequency;// 重复频率

	private String fdRepeatTime;// 重复时间

	private String fdRepeatUtil;// 结束条件

	public String getFdRepeatType() {
		return fdRepeatType;
	}

	public void setFdRepeatType(String fdRepeatType) {
		this.fdRepeatType = fdRepeatType;
	}

	public String getFdRepeatFrequency() {
		return fdRepeatFrequency;
	}

	public void setFdRepeatFrequency(String fdRepeatFrequency) {
		this.fdRepeatFrequency = fdRepeatFrequency;
	}

	public String getFdRepeatTime() {
		return fdRepeatTime;
	}

	public void setFdRepeatTime(String fdRepeatTime) {
		this.fdRepeatTime = fdRepeatTime;
	}

	public String getFdRepeatUtil() {
		return fdRepeatUtil;
	}

	public void setFdRepeatUtil(String fdRepeatUtil) {
		this.fdRepeatUtil = fdRepeatUtil;
	}

	/**
	 * 重复周期
	 */
	protected String RECURRENCE_FREQ = ImeetingConstant.RECURRENCE_FREQ_NO;

	/**
	 * 重复频率
	 */
	protected String RECURRENCE_INTERVAL = "1";

	/**
	 * 重复几次后结束
	 */
	protected String RECURRENCE_COUNT = "5";

	/**
	 * 重复到某天结束
	 */
	protected String RECURRENCE_UNTIL = null;

	protected String RECURRENCE_BYDAY = null;

	/**
	 * 重复提醒信息
	 */
	protected String RECURRENCE_SUMMARY = null;

	/**
	 * 重复结束类型
	 */
	protected String RECURRENCE_END_TYPE = "NEVER";

	/**
	 * 重复星期的值，比如每周周一、周三重复，则该值为: MO,WE
	 */
	protected String RECURRENCE_WEEKS = "SU";

	/**
	 * 重复周期为每月时，重复类型：一周的某天，一月的某天
	 */
	protected String RECURRENCE_MONTH_TYPE = "month";

	/**
	 * 重复开始时间
	 */
	protected String RECURRENCE_START = null;

	public String getRECURRENCE_FREQ() {
		return RECURRENCE_FREQ;
	}

	public void setRECURRENCE_FREQ(String recurrence_freq) {
		RECURRENCE_FREQ = recurrence_freq;
	}

	public String getRECURRENCE_INTERVAL() {
		return RECURRENCE_INTERVAL;
	}

	public void setRECURRENCE_INTERVAL(String recurrence_interval) {
		RECURRENCE_INTERVAL = recurrence_interval;
	}

	public String getRECURRENCE_COUNT() {
		return RECURRENCE_COUNT;
	}

	public void setRECURRENCE_COUNT(String recurrence_count) {
		RECURRENCE_COUNT = recurrence_count;
	}

	public String getRECURRENCE_UNTIL() {
		return RECURRENCE_UNTIL;
	}

	public void setRECURRENCE_UNTIL(String recurrence_until) {
		RECURRENCE_UNTIL = recurrence_until;
	}

	public String getRECURRENCE_BYDAY() {
		return RECURRENCE_BYDAY;
	}

	public void setRECURRENCE_BYDAY(String recurrence_byday) {
		RECURRENCE_BYDAY = recurrence_byday;
	}

	public String getRECURRENCE_SUMMARY() {
		return RECURRENCE_SUMMARY;
	}

	public void setRECURRENCE_SUMMARY(String recurrence_summary) {
		RECURRENCE_SUMMARY = recurrence_summary;
	}

	public String getRECURRENCE_END_TYPE() {
		return RECURRENCE_END_TYPE;
	}

	public void setRECURRENCE_END_TYPE(String recurrence_end_type) {
		RECURRENCE_END_TYPE = recurrence_end_type;
	}

	public String getRECURRENCE_WEEKS() {
		return RECURRENCE_WEEKS;
	}

	public void setRECURRENCE_WEEKS(String recurrence_weeks) {
		RECURRENCE_WEEKS = recurrence_weeks;
	}

	public String getRECURRENCE_MONTH_TYPE() {
		return RECURRENCE_MONTH_TYPE;
	}

	public void setRECURRENCE_MONTH_TYPE(String recurrence_month_type) {
		RECURRENCE_MONTH_TYPE = recurrence_month_type;
	}

	public String getRECURRENCE_START() {
		return RECURRENCE_START;
	}

	public void setRECURRENCE_START(String recurrence_start) {
		RECURRENCE_START = recurrence_start;
	}

	// 所属场所ID
	protected String authAreaId = null;

	@Override
    public String getAuthAreaId() {
		return authAreaId;
	}

	@Override
    public void setAuthAreaId(String authAreaId) {
		this.authAreaId = authAreaId;
	}

	// 所属场所名称
	protected String authAreaName = null;

	@Override
    public String getAuthAreaName() {
		return authAreaName;
	}

	@Override
    public void setAuthAreaName(String authAreaName) {
		this.authAreaName = authAreaName;
	}
	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdName = null;
		fdHoldDate = null;
		fdFinishDate = null;
		fdHoldDuration = null;
		fdRemark = null;
		docCreatorId = null;
		docCreatorName = null;
		fdPlaceId = null;
		fdPlaceName = null;
		fdUserTime = null;
		fdRecurrenceStr = null;
		authAreaId = null;
		authAreaName = null;
		fdPlaceAddressFloor = null;
		fdPlaceSeats = null;
		fdPlaceDetail = null;
		docKeeperName = null;
		super.reset(mapping, request);
	}

	@Override
    public Class getModelClass() {
		return KmImeetingBook.class;
	}
	
	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("authAreaId", new FormConvertor_IDToModel("authArea", SysAuthArea.class));
			toModelPropertyMap.put("docCreatorId",
					new FormConvertor_IDToModel("docCreator",
						SysOrgElement.class));
			toModelPropertyMap.put("fdPlaceId",
					new FormConvertor_IDToModel("fdPlace",
						KmImeetingRes.class));
		}
		return toModelPropertyMap;
	}

	private String fdHasExam;

	public String getFdHasExam() {
		return fdHasExam;
	}

	public void setFdHasExam(String fdHasExam) {
		this.fdHasExam = fdHasExam;
	}

	private String fdExamRemark;

	public String getFdExamRemark() {
		return fdExamRemark;
	}

	public void setFdExamRemark(String fdExamRemark) {
		this.fdExamRemark = fdExamRemark;
	}

	protected String isNotify = null;

	public String getIsNotify() {
		return isNotify;
	}

	public void setIsNotify(String isNotify) {
		this.isNotify = isNotify;
	}

	public String isBegin;

	public String getIsBegin() {
		return isBegin;
	}

	public void setIsBegin(String isBegin) {
		this.isBegin = isBegin;
	}

	public String isEnd;

	public String getIsEnd() {
		return isEnd;
	}

	public void setIsEnd(String isEnd) {
		this.isEnd = isEnd;
	}

	public String isCreator;
	
	public String getIsCreator() {
		return isCreator;
	}

	public void setIsCreator(String isCreator) {
		this.isCreator = isCreator;
	}

	protected String fdExamerId;

	protected String fdExamerName;

	public String getFdExamerId() {
		return fdExamerId;
	}

	public void setFdExamerId(String fdExamerId) {
		this.fdExamerId = fdExamerId;
	}

	public String getFdExamerName() {
		return fdExamerName;
	}

	public void setFdExamerName(String fdExamerName) {
		this.fdExamerName = fdExamerName;
	}

}
