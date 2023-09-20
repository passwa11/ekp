package com.landray.kmss.hr.staff.service.robot;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.landray.kmss.common.exception.KmssException;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.hr.staff.model.HrStaffAttendanceManage;
import com.landray.kmss.hr.staff.model.HrStaffAttendanceManageDetailed;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfo;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfoLog;
import com.landray.kmss.hr.staff.service.IHrStaffAttendanceManageDetailedService;
import com.landray.kmss.hr.staff.service.IHrStaffAttendanceManageService;
import com.landray.kmss.hr.staff.service.IHrStaffPersonInfoLogService;
import com.landray.kmss.hr.staff.service.IHrStaffPersonInfoService;
import com.landray.kmss.hr.staff.util.HrStaffPersonUtil;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.lbpm.engine.manager.task.TaskExecutionContext;
import com.landray.kmss.sys.lbpmservice.node.robotnode.support.AbstractRobotNodeServiceImp;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataModel;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.UserUtil;

/**
 * 考勤加班机器人
 * 
 * @author 潘永辉 2018年1月30日
 *
 */
public class HrStaffAttendanceManageOvertimeRobotServiceImp extends
		AbstractRobotNodeServiceImp {
	// 请假明细
	private IHrStaffAttendanceManageDetailedService hrStaffAttendanceManageDetailedService;
	// 考勤管理
	private IHrStaffAttendanceManageService hrStaffAttendanceManageService;
	// 员工信息
	private IHrStaffPersonInfoService hrStaffPersonInfoService;
	// 员工日志
	private IHrStaffPersonInfoLogService hrStaffPersonInfoLogService;

	public void setHrStaffAttendanceManageDetailedService(
			IHrStaffAttendanceManageDetailedService hrStaffAttendanceManageDetailedService) {
		this.hrStaffAttendanceManageDetailedService = hrStaffAttendanceManageDetailedService;
	}

	public void setHrStaffAttendanceManageService(
			IHrStaffAttendanceManageService hrStaffAttendanceManageService) {
		this.hrStaffAttendanceManageService = hrStaffAttendanceManageService;
	}

	public void setHrStaffPersonInfoService(
			IHrStaffPersonInfoService hrStaffPersonInfoService) {
		this.hrStaffPersonInfoService = hrStaffPersonInfoService;
	}

	public void setHrStaffPersonInfoLogService(
			IHrStaffPersonInfoLogService hrStaffPersonInfoLogService) {
		this.hrStaffPersonInfoLogService = hrStaffPersonInfoLogService;
	}

	@Override
	public void execute(TaskExecutionContext context) throws Exception {
		JSONObject json = (JSONObject) JSONValue.parse(getConfigContent(context));
		saveMainModel(context, json);
	}

	private void saveMainModel(TaskExecutionContext context, JSONObject json)
			throws Exception {
		JSONObject parameters = (JSONObject) json.get("params");
		IBaseModel mainModel = context.getMainModel();
		if (mainModel instanceof IExtendDataModel) {
			IExtendDataModel model = (IExtendDataModel) mainModel;
			Map<String, Object> modelData = model.getExtendDataModelInfo().getModelData();
			HrStaffAttendanceManageDetailed detailed = new HrStaffAttendanceManageDetailed();
			// 操作类型为：加班
			detailed.setFdType(HrStaffAttendanceManageDetailed.TYPE_OVERTIME);

			// 获取加班人
			HrStaffPersonInfo fdPersonInfo = getPersonInfo((String) parameters.get("fdApplicant"), modelData);
			detailed.setFdPersonInfo(fdPersonInfo);
			// 加班天数
			BeanUtils.setProperty(detailed, "fdLeaveDays", modelData.get(parameters.get("fdLeaveDays")));
			// 开始日期
			BeanUtils.setProperty(detailed, "fdBeginDate", modelData.get(parameters.get("fdBeginDate")));
			// 结束日期
			BeanUtils.setProperty(detailed, "fdEndDate", modelData.get(parameters.get("fdEndDate")));
			// 假期类型（takeWorking:调休假期，annualLeave:带薪年假，sickLeave:带薪病假）
			String fdLeaveType = (String) parameters.get("fdLeaveType");

			BeanUtils.setProperty(detailed, "fdLeaveType", fdLeaveType);
			// 流程URL
			detailed.setFdRelatedProcess(getUrl(model));
			//流程标题
			detailed.setFdSubject(getTitle(model));
			try {
				// 计算加班天数
				calculatedDays(fdPersonInfo, getLeaveType(fdLeaveType), detailed.getFdLeaveDays());
			} catch (Exception e) {
				// 保存异常日志
				detailed.setFdException(true);
				String msg = e.getMessage();
				if (msg.startsWith("{")) {
					msg = msg.substring(1);
				}
				if (msg.endsWith("}")) {
					msg = msg.substring(0, msg.length() - 1);
				}
				// 构建日志信息
				HrStaffPersonInfoLog log = hrStaffPersonInfoLogService.buildPersonInfoLog("attendanceManage", msg);
				log.setFdIp("-");
				log.setFdBrowser("-");
				log.setFdEquipment("-");
				log.getFdTargets().add(fdPersonInfo);
				hrStaffPersonInfoLogService.add(log);
			}

			hrStaffAttendanceManageDetailedService.add(detailed);
		}
	}

	/**
	 * 计算加班天数
	 * 
	 * @param fdPersonInfo
	 * @param leaveType
	 * @param leaveDays
	 * @throws Exception
	 */
	private void calculatedDays(HrStaffPersonInfo fdPersonInfo, Integer leaveType, Double leaveDays) throws Exception {
		if (leaveType == null) {
            return;
        }

		List<HrStaffAttendanceManage> list = hrStaffAttendanceManageService.findValidAttendanceManagesByPerson(fdPersonInfo.getFdId());
		HrStaffAttendanceManage manage;
		boolean isNew = false;
		if (list != null && !list.isEmpty()) {
			// 取最后一个，也是过期时间最长（或最近年份）
			manage = list.get(list.size() - 1);
		} else {
			isNew = true;
			manage = new HrStaffAttendanceManage();
			manage.setFdYear(Integer.valueOf(DateUtil.convertDateToString(new Date(), "yyyy")));
			manage.setFdPersonInfo(fdPersonInfo);
			manage.setFdCreateTime(new Date());
			manage.setFdCreator(UserUtil.getUser());
		}

		switch (leaveType) {
		case HrStaffPersonUtil.LEAVETYPE_TAKEWORKING: { // 调休
			manage.setFdDaysOfTakeWorking(manage.getFdDaysOfTakeWorking() + leaveDays);
			break;
		}
		case HrStaffPersonUtil.LEAVETYPE_ANNUALLEAVE: { // 年假
			manage.setFdDaysOfAnnualLeave(manage.getFdDaysOfAnnualLeave() + leaveDays);
			break;
		}
		case HrStaffPersonUtil.LEAVETYPE_SICKLEAVE: { // 病假
			manage.setFdDaysOfSickLeave(manage.getFdDaysOfSickLeave() + leaveDays);
			break;
		}
		}

		if (isNew) {
			hrStaffAttendanceManageService.add(manage);
		} else {
			hrStaffAttendanceManageService.update(manage);
		}
	}

	/**
	 * 获取请假人
	 * 
	 * @param fieldValue
	 * @param modelData
	 * @return
	 * @throws Exception
	 */
	private HrStaffPersonInfo getPersonInfo(String fieldValue,
			Map<String, Object> modelData) throws Exception {
		Object fdApplicant = modelData.get(fieldValue);
		String fdApplicantId = BeanUtils.getProperty(fdApplicant, "id");
		String fdApplicantName = BeanUtils.getProperty(fdApplicant, "name");
		HrStaffPersonInfo personInfo = (HrStaffPersonInfo) hrStaffPersonInfoService.findByPrimaryKey(fdApplicantId, null, true);
		if (personInfo == null) {
			throw new KmssException(new KmssMessage(ResourceUtil.getString(
					"hrStaffAttendanceManageDetailed.robot.fdApplicant.nofind",
					"hr-staff", null, fdApplicantName)));
		}
		return personInfo;
	}

	/**
	 * 获取请假类型
	 * 
	 * @param parameters
	 * @param modelData
	 * @return
	 * @throws Exception
	 */
	private Integer getLeaveType(String fdLeaveType) throws Exception {
		// （takeWorking:调休假期，annualLeave:带薪年假，sickLeave:带薪病假）
		if (HrStaffAttendanceManageDetailed.LEAVE_TYPE_TAKEWORKING.equals(fdLeaveType))// 调休
        {
            return HrStaffPersonUtil.LEAVETYPE_TAKEWORKING;
        } else if (HrStaffAttendanceManageDetailed.LEAVE_TYPE_ANNUALLEAVE.equals(fdLeaveType))// 年假
        {
            return HrStaffPersonUtil.LEAVETYPE_ANNUALLEAVE;
        } else if (HrStaffAttendanceManageDetailed.LEAVE_TYPE_SICKLEAVE.equals(fdLeaveType))// 病假
        {
            return HrStaffPersonUtil.LEAVETYPE_SICKLEAVE;
        }

		return null;
	}

	/**
	 * 获取流程URL
	 * 
	 * @param mainModel
	 * @return
	 * @throws Exception
	 */
	private String getUrl(IBaseModel mainModel) throws Exception {
		String modelName = mainModel.getClass().getName();
		if (modelName.contains("$$")) {
			modelName = modelName.substring(0, modelName.indexOf("$$"));
		}
		SysDictModel dictModel = SysDataDict.getInstance().getModel(modelName);
		String url = dictModel.getUrl();
		if (url != null) {
			url = url.replace("${fdId}", mainModel.getFdId());
		}
		return url;
	}

	/**
	 * <p>获取流程标题</p>
	 * @param mainModel
	 * @return
	 * @author 孙佳
	 * @throws Exception 
	 */
	private String getTitle(IBaseModel mainModel) throws Exception {
		String modelName = mainModel.getClass().getName();
		if (modelName.contains("$$")) {
			modelName = modelName.substring(0, modelName.indexOf("$$"));
		}
		SysDictModel sysDictModel = SysDataDict.getInstance().getModel(modelName);
		String fdName = sysDictModel.getDisplayProperty();
		String docValue = ModelUtil.getModelPropertyString(mainModel, fdName, null, null);
		return docValue;
	}

}
