package com.landray.kmss.sys.attend.model;

import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.attend.util.AttendConstant;
import com.landray.kmss.sys.authorization.model.SysAuthArea;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyModel;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.quartz.interfaces.ISysQuartzModel;

import java.util.Date;
import java.util.List;

/**
 * 出差/请假/加班关联表
 * 
 * @author admin
 * 
 */
public class SysAttendBusiness extends BaseModel implements ISysQuartzModel, ISysNotifyModel {

	private static final long serialVersionUID = -1954712211826799128L;

	/**
	 * 流程ID
	 */
	private String fdProcessId;

	public String getFdProcessId() {
		return fdProcessId;
	}

	public void setFdProcessId(String fdProcessId) {
		this.fdProcessId = fdProcessId;
	}

	/**
	 * 流程主题
	 */
	private String fdProcessName;

	public String getFdProcessName() {
		return fdProcessName;
	}

	public void setFdProcessName(String fdProcessName) {
		this.fdProcessName = fdProcessName;
	}

	/**
	 * 链接
	 */
	private String docUrl;

	public String getDocUrl() {
		return docUrl;
	}

	public void setDocUrl(String docUrl) {
		this.docUrl = docUrl;
	}

	/**
	 * 开始时间/开始日期
	 */
	private Date fdBusStartTime;

	public Date getFdBusStartTime() {
		return fdBusStartTime;
	}

	public void setFdBusStartTime(Date fdBusStartTime) {
		this.fdBusStartTime = fdBusStartTime;
	}

	/**
	 * 结束时间/结束日期
	 */
	private Date fdBusEndTime;

	public Date getFdBusEndTime() {
		return fdBusEndTime;
	}

	public void setFdBusEndTime(Date fdBusEndTime) {
		this.fdBusEndTime = fdBusEndTime;
	}

	/**
	 * 开始日期上下午，1：上午，2：下午
	 */
	private Integer fdStartNoon;

	public Integer getFdStartNoon() {
		return fdStartNoon;
	}

	public void setFdStartNoon(Integer fdStartNoon) {
		this.fdStartNoon = fdStartNoon;
	}

	/**
	 * 结束日期上下午，1：上午，2：下午
	 */
	private Integer fdEndNoon;

	public Integer getFdEndNoon() {
		return fdEndNoon;
	}

	public void setFdEndNoon(Integer fdEndNoon) {
		this.fdEndNoon = fdEndNoon;
	}

	/**
	 * 人员，可多选
	 */
	private List<SysOrgElement> fdTargets;

	public List<SysOrgElement> getFdTargets() {
		return fdTargets;
	}

	public void setFdTargets(List<SysOrgElement> fdTargets) {
		this.fdTargets = fdTargets;
	}

	/**
	 * 假期类型
	 */
	private Integer fdBusType;

	public Integer getFdBusType() {
		return fdBusType;
	}

	public void setFdBusType(Integer fdBusType) {
		this.fdBusType = fdBusType;
	}

	/**
	 * 出差：4;请假：5;加班：6;外出：7;销假：8;9:销出差
	 */
	private Integer fdType;

	public Integer getFdType() {
		return fdType;
	}

	public void setFdType(Integer fdType) {
		this.fdType = fdType;
	}

	/**
	 * 共计多少小时
	 */
	private Float fdCountHour;


	public Float getFdCountHour() {
		return fdCountHour;
	}

	public void setFdCountHour(Float fdCountHour) {
		this.fdCountHour = fdCountHour;
	}

	/**
	 * 1：按天，2：按半天，3，按小时
	 */
	private Integer fdStatType;

	public Integer getFdStatType() {
		return fdStatType;
	}

	public void setFdStatType(Integer fdStatType) {
		this.fdStatType = fdStatType;
	}

	/**
	 * 假期名称
	 */
	private String fdLeaveName;

	/**
	 * 明细ID(如请假对应的明细ID)
	 */
	private String fdBusDetailId;

	/**
	 * 删除标识 1:表示已删除
	 */
	private Integer fdDelFlag = 0;

	/**
	 * 状态标识：0标识启动事件，1标识结束事件
	 */
	private Integer fdOverFlag = 0;

	public Integer getFdOverFlag() {
		/**
		 * 如果没有找到则标识是已完成，兼容历史数据
		 */
		if(fdOverFlag ==null){
			fdOverFlag = AttendConstant.ATTEND_PROCESS_STATUS[1];
		}
		return fdOverFlag;
	}

	public void setFdOverFlag(Integer fdOverFlag) {
		this.fdOverFlag = fdOverFlag;
	}

	public Integer getFdDelFlag() {
		return fdDelFlag;
	}

	public void setFdDelFlag(Integer fdDelFlag) {
		this.fdDelFlag = fdDelFlag;
	}

	public String getFdLeaveName() {
		return fdLeaveName;
	}

	public void setFdLeaveName(String fdLeaveName) {
		this.fdLeaveName = fdLeaveName;
	}

	private Date docCreateTime;

	public Date getDocCreateTime() {
		return docCreateTime;
	}

	public void setDocCreateTime(Date docCreateTime) {
		this.docCreateTime = docCreateTime;
	}

	public String getFdBusDetailId() {
		return fdBusDetailId;
	}

	public void setFdBusDetailId(String fdBusDetailId) {
		this.fdBusDetailId = fdBusDetailId;
	}

	@Override
	public Class getFormClass() {
		return null;
	}

	/**
	 * 加班处理类型
	 */
	private String fdOverHandle;

	public String getFdOverHandle() {
		return fdOverHandle;
	}

	public void setFdOverHandle(String fdOverHandle) {
		this.fdOverHandle = fdOverHandle;
	}

	/**
	 * 用餐时间
	 */
	private String fdMealTimes;

	public String getFdMealTimes() {
		return fdMealTimes;
	}

	public void setFdMealTimes(String fdMealTimes) {
		this.fdMealTimes = fdMealTimes;
	}

	/**
	 * 加班转调休考勤时间
	 */
	private Date fdWorkTime;

	public Date getFdWorkTime() {
		return fdWorkTime;
	}

	public void setFdWorkTime(Date fdWorkTime) {
		this.fdWorkTime = fdWorkTime;
	}

	/**
	 * 所属场所
	 */
	protected SysAuthArea authArea;

	public SysAuthArea getAuthArea() {
		return authArea;
	}

	public void setAuthArea(SysAuthArea authArea) {
		this.authArea = authArea;
	}

	/**
	 * 非表结构字段，用于存储加班时长，用于下一个方法体判断使用
	 */
	private Integer overTime;

	public Integer getOverTime() {
		return overTime;
	}

	public void setOverTime(Integer overTime) {
		this.overTime = overTime;
	}

	@Override
	public boolean equals(Object obj){
		if(!(obj instanceof SysAttendBusiness)){
			return false;
		}
		SysAttendBusiness  bus =(SysAttendBusiness) obj;
		if(this==bus){
			return true;
		}
		if(bus.getFdId().equals(this.fdId)){
			return true;
		}
		return false;
	}

	@Override
	public int hashCode(){
		return this.fdId.hashCode();
	}

	//实际加班开始时间
	private Date fdActualOverBeginTime;
	//实际加班结束时间
	private Date fdActualOverEndTime;
	//加班计划时长
	private Double fdOverApplyTimes;
	//加班实际时长
	private Double fdOverTimes;

	public Date getFdActualOverBeginTime() {
		return fdActualOverBeginTime;
	}

	public void setFdActualOverBeginTime(Date fdActualOverBeginTime) {
		this.fdActualOverBeginTime = fdActualOverBeginTime;
	}

	public Date getFdActualOverEndTime() {
		return fdActualOverEndTime;
	}

	public void setFdActualOverEndTime(Date fdActualOverEndTime) {
		this.fdActualOverEndTime = fdActualOverEndTime;
	}

	public Double getFdOverApplyTimes() {
		return fdOverApplyTimes;
	}

	public void setFdOverApplyTimes(Double fdOverApplyTimes) {
		this.fdOverApplyTimes = fdOverApplyTimes;
	}

	public Double getFdOverTimes() {
		return fdOverTimes;
	}

	public void setFdOverTimes(Double fdOverTimes) {
		this.fdOverTimes = fdOverTimes;
	}
}
