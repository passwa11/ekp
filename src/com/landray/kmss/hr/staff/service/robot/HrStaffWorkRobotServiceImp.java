package com.landray.kmss.hr.staff.service.robot;

import java.util.List;
import java.util.Map;

import com.landray.kmss.hr.staff.util.HrStaffPersonUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.landray.kmss.common.exception.KmssException;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.hr.staff.model.HrStaffPersonExperienceWork;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfo;
import com.landray.kmss.hr.staff.service.IHrStaffPersonExperienceWorkService;
import com.landray.kmss.hr.staff.service.IHrStaffPersonInfoService;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.lbpm.engine.manager.task.TaskExecutionContext;
import com.landray.kmss.sys.lbpmservice.node.robotnode.support.AbstractRobotNodeServiceImp;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataModel;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.ResourceUtil;

/**
 * 合同信息机器人
 * @author 邓超
 *
 */
public class HrStaffWorkRobotServiceImp extends
		AbstractRobotNodeServiceImp {
	// 工作经历
	private IHrStaffPersonExperienceWorkService hrStaffPersonExperienceWorkService;


	public void setHrStaffPersonExperienceWorkService(
			IHrStaffPersonExperienceWorkService hrStaffPersonExperienceWorkService) {
		this.hrStaffPersonExperienceWorkService = hrStaffPersonExperienceWorkService;
	}

	// 员工信息
	private IHrStaffPersonInfoService hrStaffPersonInfoService;
	public void setHrStaffPersonInfoService(
			IHrStaffPersonInfoService hrStaffPersonInfoService) {
		this.hrStaffPersonInfoService = hrStaffPersonInfoService;
	}

	@Override
	public void execute(TaskExecutionContext context) throws Exception {
		JSONObject json = (JSONObject) JSONValue
				.parse(getConfigContent(context));
		saveMainModel(context, json);
	}

	private void saveMainModel(TaskExecutionContext context, JSONObject json) throws Exception {
		JSONObject parameters = (JSONObject) json.get("params");
		IBaseModel mainModel = context.getMainModel();
		if (mainModel instanceof IExtendDataModel) {
			IExtendDataModel model = (IExtendDataModel) mainModel;
			Map<String, Object> modelData = model.getExtendDataModelInfo().getModelData();
			//定制开始，获取入职申请信息,这里为了方便，前端将身份证配置到fdApplicant字段 -- by liuyang
			//根据身份证获取人事档案员工信息
			String fdApplicant = (String) modelData.get(parameters.get("fdApplicant"));
			HrStaffPersonInfo fdPersonInfo = HrStaffPersonUtil.getPersonInfoByIdCard(fdApplicant);
			if (fdPersonInfo == null) {
				throw new KmssException(new KmssMessage("人事档案未找到身份证为:" + fdApplicant + "对应的记录"));
			}
			// 获取合同员工
			//HrStaffPersonInfo fdPersonInfo = getPersonInfo((String) parameters.get("fdApplicant"), modelData);
			//定制结束，获取入职申请信息,这里为了方便，前端将身份证配置到fdApplicant字段 -- by liuyang

			//获取工作经历明细表的公司名称字段
			String fieldValue = (String) parameters.get("fdCompany");
			String[] fieldValues = fieldValue.split("\\.");
			if (fieldValues.length == 2) {
				//先由map中由第一层key值获取最外层的有效数据
				List<Map<String, Object>> fileList = (List) modelData.get(fieldValues[0]);
				if (null != fileList && fileList.size() > 0) {
					for (Map<String, Object> map : fileList) {
						HrStaffPersonExperienceWork detailed = new HrStaffPersonExperienceWork();
						detailed.setFdPersonInfo(fdPersonInfo);
						// 公司
						BeanUtils.setProperty(detailed, "fdCompany", map
								.get(parameters.get("fdCompany").toString().replace(fieldValues[0] + ".", "")));
						// 开始日期
						BeanUtils.setProperty(detailed, "fdBeginDate", map
								.get(parameters.get("fdBeginDate").toString().replace(fieldValues[0] + ".", "")));
						// 结束日期
						BeanUtils.setProperty(detailed, "fdEndDate", map
								.get(parameters.get("fdEndDate").toString().replace(fieldValues[0] + ".", "")));
						// 职位
						BeanUtils.setProperty(detailed, "fdPosition", map
								.get(parameters.get("fdPosition").toString().replace(fieldValues[0] + ".", "")));
						// 工作描述
						BeanUtils.setProperty(detailed, "fdDescription", map
								.get(parameters.get("fdDescription").toString().replace(fieldValues[0] + ".", "")));
						// 离职原因
						BeanUtils.setProperty(detailed, "fdReasons", map
								.get(parameters.get("fdReasons").toString().replace(fieldValues[0] + ".", "")));
						// 流程URL
						detailed.setFdRelatedProcess(getUrl(model));

						hrStaffPersonExperienceWorkService.add(detailed);
					}
				}
			}
		}
	}

	/**
	 * 获取合同员工
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
	 * 获取流程URL
	 * 
	 * @param mainModel
	 * 
	 * @return
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

}
