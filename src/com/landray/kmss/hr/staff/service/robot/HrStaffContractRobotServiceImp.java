package com.landray.kmss.hr.staff.service.robot;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.landray.kmss.hr.staff.model.HrStaffContractType;
import com.landray.kmss.hr.staff.service.IHrStaffContractTypeService;
import com.landray.kmss.hr.staff.util.HrStaffPersonUtil;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.sun.tools.example.debug.expr.ParseException;
import org.apache.commons.beanutils.BeanUtils;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.landray.kmss.common.exception.KmssException;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.hr.staff.model.HrStaffPersonExperienceContract;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfo;
import com.landray.kmss.hr.staff.service.IHrStaffPersonExperienceContractService;
import com.landray.kmss.hr.staff.service.IHrStaffPersonInfoService;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
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
public class HrStaffContractRobotServiceImp extends
		AbstractRobotNodeServiceImp {
	// 合同信息
	private IHrStaffPersonExperienceContractService hrStaffPersonExperienceContractService;
	public void setHrStaffPersonExperienceContractService(
			IHrStaffPersonExperienceContractService hrStaffPersonExperienceContractService) {
		this.hrStaffPersonExperienceContractService = hrStaffPersonExperienceContractService;
	}

	// 员工信息
	private IHrStaffPersonInfoService hrStaffPersonInfoService;
	public void setHrStaffPersonInfoService(
			IHrStaffPersonInfoService hrStaffPersonInfoService) {
		this.hrStaffPersonInfoService = hrStaffPersonInfoService;
	}

	// 附件
	private ISysAttMainCoreInnerService sysAttMainService;

	public void setSysAttMainService(
			ISysAttMainCoreInnerService sysAttMainService) {
		this.sysAttMainService = sysAttMainService;
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
			HrStaffPersonExperienceContract detailed = new HrStaffPersonExperienceContract();
			//定制开始，获取入职申请信息,这里为了方便，前端将身份证配置到fdApplicant字段 -- by liuyang
			//根据身份证获取人事档案员工信息
			String fdApplicant  = (String) modelData.get(parameters.get("fdApplicant"));
			HrStaffPersonInfo fdPersonInfo = HrStaffPersonUtil.getPersonInfoByIdCard(fdApplicant);
			if(fdPersonInfo == null){
				throw new KmssException(new KmssMessage("人事档案未找到身份证为:" + fdApplicant +"对应的记录"));
			}
			//定制结束，获取入职申请信息,这里为了方便，前端将身份证配置到fdApplicant字段 -- by liuyang
			detailed.setFdPersonInfo(fdPersonInfo);

			// 合同名称
			BeanUtils.setProperty(detailed, "fdName", modelData
					.get(parameters.get("fdName")));
			// 合同开始时间
			Date beginDate = (Date) modelData.get(parameters.get("fdBeginDate"));
			detailed.setFdBeginDate(beginDate);
//			BeanUtils.setProperty(detailed, "fdBeginDate", beginDate);
			// 合同结束时间
			Date endDate = (Date) modelData.get(parameters.get("fdEndDate"));
			detailed.setFdEndDate(endDate);
//			BeanUtils.setProperty(detailed, "fdEndDate", endDate);
			// 合同类型
			String fdContType = (String)modelData.get(parameters.get("fdContType"));

			/*BeanUtils.setProperty(detailed, "fdContType",
					parameters.get("fdContType") + "~" + fdContType);*/
			IHrStaffContractTypeService staffContractTypeService = (IHrStaffContractTypeService) SpringBeanUtil.getBean("hrStaffContractTypeService");
			HrStaffContractType staffContType = (HrStaffContractType)staffContractTypeService.findByPrimaryKey(fdContType, null, true);
			detailed.setFdStaffContType(staffContType);

			// 签订标识
			String fdSignType = (String)modelData.get(parameters.get("fdSignType"));
			/*BeanUtils.setProperty(detailed, "fdSignType",
					parameters.get("fdSignType") + "~" + fdSignType);*/
			detailed.setFdSignType(fdSignType);

			// 合同办理时间
//			BeanUtils.setProperty(detailed, "fdHandleDate",
//					modelData.get(parameters.get("fdHandleDate")));
			detailed.setFdHandleDate((Date) modelData.get(parameters.get("fdHandleDate")));
			// 合同附件
			String fdKey = (String) parameters.get("autoHashMap");
			String fdMainModelName = mainModel.getClass().getName();
			if (fdMainModelName.contains("$$")) {
				fdMainModelName = fdMainModelName.substring(0,
						fdMainModelName.indexOf("$$"));
			}
			//Temporal start = LocalDate.parse(DateUtil.convertDateToString(beginDate,"yyyy-MM-dd"));
			//Temporal end = LocalDate.parse(DateUtil.convertDateToString(endDate,"yyyy-MM-dd"));

			//Long year = ChronoUnit.YEARS.between(start,end);
			//Long month = ChronoUnit.YEARS.between(start,end);

			//计算两个时间相差月份
			if(beginDate!=null && endDate!=null){
			int monthSpace = getMonthSpace(beginDate, endDate);

			//设置年数
			BeanUtils.setProperty(detailed, "fdContractYear",  monthSpace/12);
			//设置月数
			BeanUtils.setProperty(detailed, "fdContractMonth",  monthSpace%12);
			}
			//合同所属单位
			BeanUtils.setProperty(detailed, "fdContractUnit", modelData.get(parameters.get("fdContractUnit")));
			String fdMainId = mainModel.getFdId();
			List<SysAttMain> attMainList = sysAttMainService.findByModelKey(fdMainModelName, fdMainId, fdKey);
			for (SysAttMain attMain : attMainList) {
				SysAttMain attMainClone = (SysAttMain) attMain.clone();
				attMainClone.setFdModelName(detailed.getClass().getName());
				attMainClone.setFdModelId(detailed.getFdId());
				attMainClone.setFdKey("attHrExpCont");
				sysAttMainService.add(attMainClone);
			}
			// 备注
			BeanUtils.setProperty(detailed, "fdMemo", modelData
					.get(parameters.get("fdMemo")));
			// 流程URL
			detailed.setFdRelatedProcess(getUrl(model));
			//默认是续签是正常合同
			detailed.setFdContStatus("1");

			hrStaffPersonExperienceContractService.add(detailed);
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

	private int getMonthSpace(Date date1, Date date2){
		int result = 0;
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(date1);
		c2.setTime(date2);
		int i = c2.get(Calendar.YEAR)-c1.get(Calendar.YEAR);
		int month = 0;
		if (i<0) {
			month = -i*12;
		}else if(i>0) {
			month =  i*12;
		}
		result = (c2.get(Calendar.MONDAY) - c1.get(Calendar.MONTH)) + month;
		return result == 0 ? 1 : Math.abs(result);
	}
}
