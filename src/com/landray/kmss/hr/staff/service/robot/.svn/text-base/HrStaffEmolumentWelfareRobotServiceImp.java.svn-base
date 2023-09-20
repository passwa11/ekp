package com.landray.kmss.hr.staff.service.robot;

import com.landray.kmss.common.exception.KmssException;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.hr.staff.model.HrStaffEmolumentWelfareDetalied;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfo;
import com.landray.kmss.hr.staff.service.IHrStaffEmolumentWelfareDetaliedService;
import com.landray.kmss.hr.staff.service.IHrStaffPersonInfoService;
import com.landray.kmss.hr.staff.util.HrStaffPersonUtil;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.lbpm.engine.manager.task.TaskExecutionContext;
import com.landray.kmss.sys.lbpmservice.node.robotnode.support.AbstractRobotNodeServiceImp;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataModel;
import com.landray.kmss.util.KmssMessage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.util.HashMap;
import java.util.Map;

/**
 * 薪酬福利机器人
 * 
 * @author 潘永辉 2017-1-16
 * 
 */
public class HrStaffEmolumentWelfareRobotServiceImp extends
		AbstractRobotNodeServiceImp {
	private Log logger = LogFactory.getLog(this.getClass());

	// 请假明细
	private IHrStaffEmolumentWelfareDetaliedService hrStaffEmolumentWelfareDetaliedService;
	// 员工信息
	private IHrStaffPersonInfoService hrStaffPersonInfoService;

	public void setHrStaffEmolumentWelfareDetaliedService(
			IHrStaffEmolumentWelfareDetaliedService hrStaffEmolumentWelfareDetaliedService) {
		this.hrStaffEmolumentWelfareDetaliedService = hrStaffEmolumentWelfareDetaliedService;
	}

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
			HrStaffEmolumentWelfareDetalied detailed = new HrStaffEmolumentWelfareDetalied();
			// 如果是明细表，需要另外处理
			Map<String, Object> detailData = new HashMap<String, Object>();
			// 获取调整员工
			//定制开始，获取入职申请信息,这里为了方便，前端将身份证配置到fdApplicant字段 -- by liuyang
			//根据身份证获取人事档案员工信息
			String fdApplicant = (String) modelData.get(parameters.get("fdApplicant"));
			HrStaffPersonInfo fdPersonInfo = HrStaffPersonUtil.getPersonInfoByIdCard(fdApplicant);
			if(fdPersonInfo == null){
				throw new KmssException(new KmssMessage("人事档案未找到身份证为:" + fdApplicant +"对应的记录"));
			}
//			String fdApplicant = HrStaffPersonUtil.getDetailKey((String) parameters.get("fdApplicant"), modelData, detailData);
//			HrStaffPersonInfo fdPersonInfo = HrStaffPersonUtil.getPersonInfo(fdApplicant, modelData, detailData, hrStaffPersonInfoService);
			//定制结束，获取入职申请信息,这里为了方便，前端将身份证配置到fdApplicant字段 -- by liuyang

			detailed.setFdPersonInfo(fdPersonInfo);
			// 调整日期
			String fdAdjustDate = HrStaffPersonUtil.getDetailKey((String) parameters.get("fdAdjustDate"), modelData, detailData);
			Object adjustDate = HrStaffPersonUtil.processData(fdAdjustDate, "fdAdjustDate", modelData, detailData, detailed);
			// 调整前薪酬
			String fdBeforeEmolument = HrStaffPersonUtil.getDetailKey((String) parameters.get("fdBeforeEmolument"), modelData, detailData);
			Object beforeEmolument = HrStaffPersonUtil.processData(fdBeforeEmolument, "fdBeforeEmolument", modelData, detailData, detailed);

			// 调整金额
			String fdAdjustAmount = HrStaffPersonUtil.getDetailKey((String) parameters.get("fdAdjustAmount"), modelData, detailData);
			HrStaffPersonUtil.processData(fdAdjustAmount,"fdAdjustAmount",modelData,detailData,detailed);

			// 调整后薪酬
			String fdAfterEmolument = HrStaffPersonUtil.getDetailKey((String) parameters.get("fdAfterEmolument"), modelData, detailData);
			Object afterEmolument = HrStaffPersonUtil.processData(fdAfterEmolument,"fdAfterEmolument",modelData,detailData,detailed);

			// 流程URL
			detailed.setFdRelatedProcess(getUrl(model));

			if (logger.isDebugEnabled()) {
				logger.debug("薪酬福利机器人");
				logger.debug("员工：" + fdPersonInfo != null ? fdPersonInfo.getFdName() : "-");
				logger.debug("调整日期：" + adjustDate);
				logger.debug("调整前薪酬：" + beforeEmolument);
				logger.debug("调整金额：" + adjustDate);
				logger.debug("调整后薪酬：" + afterEmolument);
			}

			hrStaffEmolumentWelfareDetaliedService.add(detailed);
		}
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
