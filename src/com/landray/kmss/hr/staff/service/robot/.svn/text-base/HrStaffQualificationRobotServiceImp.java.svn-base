package com.landray.kmss.hr.staff.service.robot;

import java.util.Map;

import com.landray.kmss.hr.staff.util.HrStaffPersonUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.landray.kmss.common.exception.KmssException;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.hr.staff.model.HrStaffPersonExperienceQualification;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfo;
import com.landray.kmss.hr.staff.service.IHrStaffPersonExperienceQualificationService;
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
public class HrStaffQualificationRobotServiceImp extends
		AbstractRobotNodeServiceImp {
	// 合同信息
	private IHrStaffPersonExperienceQualificationService hrStaffPersonExperienceQualificationService;


	public void setHrStaffPersonExperienceQualificationService(
			IHrStaffPersonExperienceQualificationService hrStaffPersonExperienceQualificationService) {
		this.hrStaffPersonExperienceQualificationService = hrStaffPersonExperienceQualificationService;
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

	private void saveMainModel(TaskExecutionContext context, JSONObject json)
			throws Exception {
		JSONObject parameters = (JSONObject) json.get("params");
		IBaseModel mainModel = context.getMainModel();
		if (mainModel instanceof IExtendDataModel) {
			IExtendDataModel model = (IExtendDataModel) mainModel;
			Map<String, Object> modelData = model.getExtendDataModelInfo()
					.getModelData();
			HrStaffPersonExperienceQualification detailed = new HrStaffPersonExperienceQualification();
			//定制开始，获取入职申请信息,这里为了方便，前端将身份证配置到fdApplicant字段 -- by liuyang
			//根据身份证获取人事档案员工信息
			String fdApplicant = (String) modelData.get(parameters.get("fdApplicant"));
			HrStaffPersonInfo fdPersonInfo = HrStaffPersonUtil.getPersonInfoByIdCard(fdApplicant);
			if(fdPersonInfo == null){
				throw new KmssException(new KmssMessage("人事档案未找到身份证为:" + fdApplicant +"对应的记录"));
			}
			// 获取合同员工
			//HrStaffPersonInfo fdPersonInfo = getPersonInfo((String) parameters.get("fdApplicant"), modelData);
			//定制结束，获取入职申请信息,这里为了方便，前端将身份证配置到fdApplicant字段 -- by liuyang

			detailed.setFdPersonInfo(fdPersonInfo);
			// 证书名称
			BeanUtils.setProperty(detailed, "fdCertificateName", modelData
					.get(parameters.get("fdCertificateName")));
			// 颁发日期
			BeanUtils.setProperty(detailed, "fdBeginDate", modelData
					.get(parameters.get("fdBeginDate")));
			// 失效日期
			BeanUtils.setProperty(detailed, "fdEndDate", modelData
					.get(parameters.get("fdEndDate")));
			// 颁发单位
			BeanUtils.setProperty(detailed, "fdAwardUnit", modelData
					.get(parameters.get("fdAwardUnit")));
			// 流程URL
			detailed.setFdRelatedProcess(getUrl(model));

			hrStaffPersonExperienceQualificationService.add(detailed);
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
