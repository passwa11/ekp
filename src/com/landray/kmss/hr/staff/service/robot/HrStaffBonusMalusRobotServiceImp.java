package com.landray.kmss.hr.staff.service.robot;

import java.util.List;
import java.util.Map;

import com.landray.kmss.hr.staff.util.HrStaffPersonUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.landray.kmss.common.exception.KmssException;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.hr.staff.model.HrStaffPersonExperienceBonusMalus;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfo;
import com.landray.kmss.hr.staff.service.IHrStaffPersonExperienceBonusMalusService;
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
 * 
 * @author 邓超
 *
 */
public class HrStaffBonusMalusRobotServiceImp extends
		AbstractRobotNodeServiceImp {
	// 合同信息
	private IHrStaffPersonExperienceBonusMalusService hrStaffPersonExperienceBonusMalusService;

	public void setHrStaffPersonExperienceBonusMalusService(
			IHrStaffPersonExperienceBonusMalusService hrStaffPersonExperienceBonusMalusService) {
		this.hrStaffPersonExperienceBonusMalusService = hrStaffPersonExperienceBonusMalusService;
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
			// 校验合同员工是否存在于人事档案中，并保存奖惩信息
			checkSavePersonInfo(parameters, mainModel);
		}
	}

	private HrStaffPersonInfo checkSavePersonInfo(JSONObject parameters,
			IBaseModel mainModel) throws Exception {
		String fieldValue = (String) parameters.get("fdBonusMalusName");
		IExtendDataModel model = (IExtendDataModel) mainModel;
		Map<String, Object> modelData = model.getExtendDataModelInfo().getModelData();

		//定制开始，获取入职申请信息,这里为了方便，前端将身份证配置到fdApplicant字段 -- by liuyang
		//根据身份证获取人事档案员工信息
		String fdApplicant = (String) modelData.get(parameters.get("fdApplicant"));
		HrStaffPersonInfo personInfo = HrStaffPersonUtil.getPersonInfoByIdCard(fdApplicant);
		if(personInfo == null){
			throw new KmssException(new KmssMessage("人事档案未找到身份证为:" + fdApplicant +"对应的记录"));
		}
		//定制结束，获取入职申请信息,这里为了方便，前端将身份证配置到fdApplicant字段 -- by liuyang
		
		// 如果是明细表里面的字段 获取两段key值 否则一段
		String[] fieldValues = fieldValue.split("\\.");
		if (fieldValues.length == 2) {
			// 先由map中由第一层key值获取最外层的有效数据
			List<Map<String, Object>> fileList = (List) modelData.get(fieldValues[0]);
			if (null != fileList && fileList.size() > 0) {
				for (Map<String, Object> map : fileList) {
					HrStaffPersonExperienceBonusMalus detailed = new HrStaffPersonExperienceBonusMalus();
					//定制开始，根据身份证获取人事档案信息，这里不需要，所以注释掉,by liuyang
					// 获取每一行记录选择的人员id和name
//					String fdApplicantId = BeanUtils
//							.getProperty(map.get(fieldValues[1]), "id");
//					String fdApplicantName = BeanUtils
//							.getProperty(map.get(fieldValues[1]), "name");
//					// 校验人员是否存在于人事档案中
//					HrStaffPersonInfo personInfo = (HrStaffPersonInfo) hrStaffPersonInfoService.findByPrimaryKey(fdApplicantId, null, true);
//					if (personInfo == null) {
//						throw new KmssException(
//								new KmssMessage(ResourceUtil.getString(
//										"hrStaffAttendanceManageDetailed.robot.fdApplicant.nofind",
//										"hr-staff", null, fdApplicantName)));
//					}
					//定制结束，根据身份证获取人事档案信息，这里不需要，所以注释掉,by liuyang
					detailed.setFdPersonInfo(personInfo);
					// 奖惩名称
					BeanUtils.setProperty(detailed, "fdBonusMalusName", map
							.get(parameters.get("fdBonusMalusName").toString()
									.replace(fieldValues[0] + ".", "")));
					// 奖惩类型
					BeanUtils.setProperty(detailed, "fdBonusMalusType", map
							.get(parameters.get("fdBonusMalusType").toString()
									.replace(fieldValues[0] + ".", "")));
					// 奖惩日期
					BeanUtils.setProperty(detailed, "fdBonusMalusDate", map
							.get(parameters.get("fdBonusMalusDate").toString()
									.replace(fieldValues[0] + ".", "")));
					// 备注
					BeanUtils.setProperty(detailed, "fdMemo", map
							.get(parameters.get("fdMemo").toString()
									.replace(fieldValues[0] + ".", "")));
					// 流程URL
					detailed.setFdRelatedProcess(getUrl(model));
					hrStaffPersonExperienceBonusMalusService.add(detailed);
				}
			}
		} else {
			HrStaffPersonExperienceBonusMalus detailed = new HrStaffPersonExperienceBonusMalus();
			// 获取人事档案员工
			HrStaffPersonInfo fdPersonInfo = getPersonInfo((String) parameters
					.get("fdApplicant"), modelData);
			detailed.setFdPersonInfo(fdPersonInfo);
			// 奖惩名称
			BeanUtils.setProperty(detailed, "fdBonusMalusName", modelData
					.get(parameters.get("fdBonusMalusName")));
			// 奖惩类型
			BeanUtils.setProperty(detailed, "fdBonusMalusType", modelData
					.get(parameters.get("fdBonusMalusType")));
			// 奖惩日期
			BeanUtils.setProperty(detailed, "fdBonusMalusDate", modelData
					.get(parameters.get("fdBonusMalusDate")));
			// 备注
			BeanUtils.setProperty(detailed, "fdMemo", modelData
					.get(parameters.get("fdMemo")));
			// 流程URL
			detailed.setFdRelatedProcess(getUrl(model));
			hrStaffPersonExperienceBonusMalusService.add(detailed);
		}
		return null;
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
