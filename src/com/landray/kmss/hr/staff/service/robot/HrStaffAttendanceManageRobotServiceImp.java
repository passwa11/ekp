package com.landray.kmss.hr.staff.service.robot;

import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.landray.kmss.common.exception.KmssException;
import com.landray.kmss.common.model.IBaseModel;
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
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;

/**
 * 考勤管理机器人
 * 
 * @author 潘永辉 2017-1-12
 * 
 */
public class HrStaffAttendanceManageRobotServiceImp extends
		AbstractRobotNodeServiceImp {
	// 请假明细
	private IHrStaffAttendanceManageDetailedService hrStaffAttendanceManageDetailedService;
	// 考勤管理
	private IHrStaffAttendanceManageService hrStaffAttendanceManageService;
	// 员工信息
	private IHrStaffPersonInfoService hrStaffPersonInfoService;
//	private DictLoadService sysFormDictLoadService;
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

//	public void
//			setSysFormDictLoadService(DictLoadService sysFormDictLoadService) {
//		this.sysFormDictLoadService = sysFormDictLoadService;
//	}

	public void setHrStaffPersonInfoLogService(
			IHrStaffPersonInfoLogService hrStaffPersonInfoLogService) {
		this.hrStaffPersonInfoLogService = hrStaffPersonInfoLogService;
	}

	@Override
	public void execute(TaskExecutionContext context) throws Exception {
		JSONObject json = (JSONObject) JSONValue
				.parse(getConfigContent(context));
		saveMainModel(context, json);
	}

	private void saveMainModel(TaskExecutionContext context, JSONObject json)
			throws Exception {
		JSONObject parameters = (JSONObject) json.get("params");
		IBaseModel mainModel = context.getMainModel();
		if (mainModel instanceof IExtendDataModel) {
			IExtendDataModel model = (IExtendDataModel) mainModel;
			Map<String, Object> modelData = model.getExtendDataModelInfo()
					.getModelData();
			HrStaffAttendanceManageDetailed detailed = new HrStaffAttendanceManageDetailed();
			// 操作类型为：请假
			detailed.setFdType(HrStaffAttendanceManageDetailed.TYPE_LEAVE);

			// 获取请假人
			HrStaffPersonInfo fdPersonInfo = getPersonInfo((String) parameters
					.get("fdApplicant"), modelData);
			detailed.setFdPersonInfo(fdPersonInfo);
			// 请假天数
			BeanUtils.setProperty(detailed, "fdLeaveDays", modelData
					.get(parameters.get("fdLeaveDays")));
			// 开始日期
			BeanUtils.setProperty(detailed, "fdBeginDate", modelData
					.get(parameters.get("fdBeginDate")));
			// 结束日期
			BeanUtils.setProperty(detailed, "fdEndDate", modelData
					.get(parameters.get("fdEndDate")));
			// 请假类型
//			Map<String, String> map = getLeaveTypes(model.getExtendFilePath(),
//					(String) parameters.get("fdLeaveType"));
//			String _fdLeaveType = map.get((String) modelData.get(parameters
//					.get("fdLeaveType")));

			// 获取请假类型
			Integer leaveType = getLeaveType(parameters, modelData);
			// 为了和加班类型统一，这里统一使用枚举
			if (leaveType != null) {
				String fdLeaveType = "";
				if (leaveType.equals(HrStaffPersonUtil.LEAVETYPE_TAKEWORKING)) {// 调休
					fdLeaveType = "takeWorking";
				}
				if (leaveType.equals(HrStaffPersonUtil.LEAVETYPE_ANNUALLEAVE)) {// 年假
					fdLeaveType = "annualLeave";
				}
				if (leaveType.equals(HrStaffPersonUtil.LEAVETYPE_SICKLEAVE)) {// 病假
					fdLeaveType = "sickLeave";
				}

				BeanUtils.setProperty(detailed, "fdLeaveType", fdLeaveType);
			}

			// 流程URL
			detailed.setFdRelatedProcess(getUrl(model));
			//流程标题
			detailed.setFdSubject(getTitle(model));
			try {
				// 计算请假天数
				calculatedDays(fdPersonInfo.getFdId(), leaveType,
						detailed.getFdLeaveDays());
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
				HrStaffPersonInfoLog log = hrStaffPersonInfoLogService
						.buildPersonInfoLog("attendanceManage", msg);
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
	 * 休假类型统一使用枚举
	 * 
	private Map<String, String> getLeaveTypes(String fileName,
			String fdLeaveTypeId) throws Exception {
		SysDictExtendModel extendModel = sysFormDictLoadService
				.loadDictByFileName(fileName);
		SysDictCommonProperty p = extendModel.getPropertyMap().get(
				fdLeaveTypeId);

		// 年假|年假;调休|TX;事假|事假;病假|病假;其它|其它
		String values = p.getEnumValues();
		Map<String, String> map = new HashMap<String, String>();
		if (StringUtil.isNotNull(values)) {
			for (String value : values.split(";")) {
				if (StringUtil.isNotNull(value)) {
					String[] val = value.split("\\|");
					if (val.length > 1) {
						map.put(val[1], val[0]);
					} else {
						map.put(val[0], val[0]);
					}
				}
			}
		}

		return map;
	}
	*/

	/**
	 * 计算请假天数
	 * 
	 * @param fdPersonInfoId
	 * @param leaveType
	 * @param leaveDays
	 * @throws Exception
	 */
	private void calculatedDays(String fdPersonInfoId, Integer leaveType,
			Double leaveDays) throws Exception {
		// 如果没有匹配的请假类型，这里就不扣除有效天数，因为系统只内置了3种请假类型，除此之外的类型不用扣除操作，只会记录请假明细
		if (leaveType == null) {
            return;
        }

		// 可用假期天数
		Double days = 0.0;
		String s_leaveType = "";
		switch (leaveType) {
		case HrStaffPersonUtil.LEAVETYPE_TAKEWORKING: { // 调休
			days = hrStaffAttendanceManageService
					.getDaysOfTakeWorkingByPerson(fdPersonInfoId);
			s_leaveType = ResourceUtil
					.getString("hr-staff:hrStaffAttendanceManage.fdDaysOfTakeWorking");
			break;
		}
		case HrStaffPersonUtil.LEAVETYPE_ANNUALLEAVE: { // 年假
			days = hrStaffAttendanceManageService
					.getDaysOfAnnualLeaveByPerson(fdPersonInfoId);
			s_leaveType = ResourceUtil
					.getString("hr-staff:hrStaffAttendanceManage.fdDaysOfAnnualLeave");
			break;
		}
		case HrStaffPersonUtil.LEAVETYPE_SICKLEAVE: { // 病假
			days = hrStaffAttendanceManageService
					.getDaysOfSickLeaveByPerson(fdPersonInfoId);
			s_leaveType = ResourceUtil
					.getString("hr-staff:hrStaffAttendanceManage.fdDaysOfSickLeave");
			break;
		}
		}

		// 如果申请的请假天数比可用天数多，则不扣除请假天数，在请假流程标题增加标注（假期扣减失败，具体见员工动态），同时增加一条员工动态日志
		if (leaveDays.doubleValue() > days.doubleValue()) {
			throw new KmssException(new KmssMessage(ResourceUtil.getString(
					"hrStaffAttendanceManageDetailed.robot.leaveDays.error",
					"hr-staff", null, new Object[] { s_leaveType,
							leaveDays.doubleValue(), days.doubleValue() })));
		}

		// 如果申请通过，则扣除可用天数
		switch (leaveType) {
		case HrStaffPersonUtil.LEAVETYPE_TAKEWORKING: { // 调休
			hrStaffAttendanceManageService.updateDaysOfTakeWorkingByPerson(
					fdPersonInfoId, leaveDays);
			break;
		}
		case HrStaffPersonUtil.LEAVETYPE_ANNUALLEAVE: { // 年假
			hrStaffAttendanceManageService.updateDaysOfAnnualLeaveByPerson(
					fdPersonInfoId, leaveDays);
			break;
		}
		case HrStaffPersonUtil.LEAVETYPE_SICKLEAVE: { // 病假
			hrStaffAttendanceManageService.updateDaysOfSickLeaveByPerson(
					fdPersonInfoId, leaveDays);
			break;
		}
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
		HrStaffPersonInfo personInfo = (HrStaffPersonInfo) hrStaffPersonInfoService
				.findByPrimaryKey(fdApplicantId, null, true);
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
	private Integer getLeaveType(JSONObject parameters,
			Map<String, Object> modelData) throws Exception {
		// 请假类型
		String fdLeaveType = (String) modelData.get(parameters
				.get("fdLeaveType"));
		// 调休
		String fdDaysOfTakeWorking = (String) parameters
				.get("fdDaysOfTakeWorking");
		if (StringUtil.isNotNull(fdDaysOfTakeWorking)
				&& getLeaveTypeValue(fdDaysOfTakeWorking).equals(fdLeaveType)) {
			return HrStaffPersonUtil.LEAVETYPE_TAKEWORKING;
		}
		// 年假
		String fdDaysOfAnnualLeave = (String) parameters
				.get("fdDaysOfAnnualLeave");
		if (StringUtil.isNotNull(fdDaysOfAnnualLeave)
				&& getLeaveTypeValue(fdDaysOfAnnualLeave).equals(fdLeaveType)) {
			return HrStaffPersonUtil.LEAVETYPE_ANNUALLEAVE;
		}
		// 病假
		String fdDaysOfSickLeave = (String) parameters.get("fdDaysOfSickLeave");
		if (StringUtil.isNotNull(fdDaysOfSickLeave)
				&& getLeaveTypeValue(fdDaysOfSickLeave).equals(fdLeaveType)) {
			return HrStaffPersonUtil.LEAVETYPE_SICKLEAVE;
		}

		return null;
	}

	private String getLeaveTypeValue(String leave) {
		String[] values = leave.split("\\|");
		String value = null;
		if (values.length > 1) {
            value = values[1];
        } else {
            value = values[0];
        }
		return value;
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
