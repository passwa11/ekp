package com.landray.kmss.third.ding.service.spring;

import com.dingtalk.api.request.OapiProcessinstanceCreateRequest;
import com.dingtalk.api.request.OapiProcessinstanceCreateRequest.FormComponentValueVo;
import com.dingtalk.api.request.OapiProcessinstanceGetRequest;
import com.dingtalk.api.response.OapiProcessinstanceCreateResponse;
import com.dingtalk.api.response.OapiProcessinstanceGetResponse;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.lbpm.engine.manager.event.EventExecutionContext;
import com.landray.kmss.sys.lbpm.engine.manager.event.IEventListener;
import com.landray.kmss.sys.right.interfaces.BaseAuthModel;
import com.landray.kmss.third.ding.constant.DingConstant;
import com.landray.kmss.third.ding.model.DingConfig;
import com.landray.kmss.third.ding.model.ThirdDingFinstance;
import com.landray.kmss.third.ding.model.ThirdDingOrmDe;
import com.landray.kmss.third.ding.model.ThirdDingOrmTemp;
import com.landray.kmss.third.ding.oms.DingApiService;
import com.landray.kmss.third.ding.service.IOmsRelationService;
import com.landray.kmss.third.ding.service.IThirdDingFinstanceService;
import com.landray.kmss.third.ding.service.IThirdDingOrmTempService;
import com.landray.kmss.third.ding.util.DingUtil;
import com.landray.kmss.third.ding.util.DingUtils;
import com.landray.kmss.third.ding.util.ThirdDingTalkClient;
import com.landray.kmss.third.ding.webclient.IKmReviewWebserviceService;
import com.landray.kmss.third.ding.webclient.IKmReviewWebserviceServiceProxy;
import com.landray.kmss.third.ding.webclient.KmReviewParamterForm;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ThirdDingLBPMEventHandlerService implements IEventListener, ApplicationListener {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ThirdDingLBPMEventHandlerService.class);
	private IThirdDingOrmTempService thirdDingOrmTempService;

	private IThirdDingFinstanceService thirdDingFinstanceService;

	private IOmsRelationService omsRelationService;

	public void setThirdDingOrmTempService(IThirdDingOrmTempService thirdDingOrmTempService) {
		this.thirdDingOrmTempService = thirdDingOrmTempService;
	}

	public void setThirdDingFinstanceService(IThirdDingFinstanceService thirdDingFinstanceService) {
		this.thirdDingFinstanceService = thirdDingFinstanceService;
	}

	public void setOmsRelationService(IOmsRelationService omsRelationService) {
		this.omsRelationService = omsRelationService;
	}

	/**
	 * @param json
	 * @param temp
	 * @throws Exception
	 *             启动EKP的流程
	 */
	public void addEKPFlow(JSONObject json, ThirdDingOrmTemp temp,String time) throws Exception {
		logger.warn("------------启动EKP的流程-------------------------");
		// 保存流程数据，防止多次启动
		String pid = json.getString("processInstanceId");
		List<ThirdDingFinstance> list = thirdDingFinstanceService.findList("fdCreateTime='"+time+"'", null);		
		if(list==null||list.isEmpty()){
			KmReviewParamterForm param = createForm(json, temp);
			IKmReviewWebserviceServiceProxy service = new IKmReviewWebserviceServiceProxy();
			IKmReviewWebserviceService client = service.getIKmReviewWebserviceService();
			String id = client.addReview(param);
			ThirdDingFinstance finstance = new ThirdDingFinstance();
			finstance.setFdInstanceId(pid);
			finstance.setFdModelId(id);
			finstance.setFdModelName("com.landray.kmss.km.review.model.KmReviewMain");
			finstance.setFdProcessCode(temp.getFdProcessCode());
			finstance.setFdStartFlow(temp.getFdStartFlow());
			finstance.setFdTemplateId(temp.getFdTemplateId());
			finstance.setFdDingStatus(json.getString("type"));
			finstance.setFdCreateTime(time);
			thirdDingFinstanceService.add(finstance);
			thirdDingFinstanceService.flushHibernateSession();
		}
	}

	/**
	 * 创建文档及流程数据
	 */
	private KmReviewParamterForm createForm(JSONObject plainTextJson, ThirdDingOrmTemp temp) throws Exception {
		String pid = plainTextJson.getString("processInstanceId");
		String auth = plainTextJson.getString("staffId");
		String title = plainTextJson.getString("title");
		KmReviewParamterForm form = new KmReviewParamterForm();
		// 文档模板id
		form.setFdTemplateId(temp.getFdTemplateId());
		// 文档标题
		form.setDocSubject(title);
		DingApiService dingService = DingUtils.getDingApiService();
		String url = DingConstant.DING_PREFIX + "/topapi/processinstance/get"
				+ DingUtil.getDingAppKeyByEKPUserId("?",
						temp.getDocCreator() == null ? null
								: temp.getDocCreator().getFdId());
		logger.debug("钉钉接口：" + url);
		ThirdDingTalkClient client = new ThirdDingTalkClient(url);
		OapiProcessinstanceGetRequest request = new OapiProcessinstanceGetRequest();
		request.setProcessInstanceId(pid);
		OapiProcessinstanceGetResponse response = client.execute(request, dingService.getAccessToken());
		JSONObject json = JSONObject.fromObject(response.getBody());
		JSONArray ja = json.getJSONObject("process_instance").getJSONArray("form_component_values");
		JSONObject jotemp = null;
		String key = null;
		List<ThirdDingOrmDe> details = temp.getFdDetail();
		// 流程表单
		JSONObject jsonv = new JSONObject();
		for (int i = 0; i < ja.size(); i++) {
			jotemp = ja.getJSONObject(i);
			if (!jotemp.containsKey("name")) {
                continue;
            }
			key = jotemp.getString("name");
			for (ThirdDingOrmDe detail : details) {
				if (key.trim().equals(detail.getFdDingField().trim())) {
					if(StringUtil.isNull(jotemp.getString("value"))||"null".equals(jotemp.getString("value"))){
						jsonv.put(detail.getFdEkpField(), "");
					}else{
						jsonv.put(detail.getFdEkpField(), jotemp.getString("value"));
					}
				}
			}
			/*
			 * if("缺卡原因".equals(jotemp.getString("name"))){ jsonv.put("result",
			 * jotemp.getString("value")); }else
			 * if("repairCheckTime".equals(jotemp.getString("name"))){
			 * jsonv.put("time",
			 * JSONObject.fromObject(jotemp.getString("ext_value")).getString(
			 * "planText")); }
			 */
		}
		// 流程发起人
		form.setDocCreator("{\"Id\": \"" + getEKPUser(auth) + "\"}");
		form.setFormValues(jsonv.toString());
		logger.info("获取审批单信息:" + json.toString());
		return form;
	}

	@Override
	public void handleEvent(EventExecutionContext execution, String parameter) throws Exception {
		try {
			logger.debug("------------流程节点“工作项进入事件”，启动钉钉流程-------------------------");
			if ("true".equals(DingConfig.newInstance().getDingFlowEnabled())
					&& ("1".equals(DingConfig.newInstance().getDingFlowSynType())
							|| "3".equals(DingConfig.newInstance().getDingFlowSynType()))) {
				// 启动钉钉流程
				IBaseModel baseModel = execution.getMainModel();
				System.out.println(baseModel);
				String fdTemplateId = DingUtil.getModelTemplateProperyId(baseModel, "fdTemplate", null);
				if (StringUtil.isNull(fdTemplateId)) {
					return;
				}
				String where = "fdIsAvailable=1 and fdTemplateId='" + fdTemplateId + "'";
				ThirdDingOrmTemp ding = (ThirdDingOrmTemp) thirdDingOrmTempService.findFirstOne(where, null);
				if (ding != null) {
					String handler = null;
					if (execution.getWorkitem().getFdExpecter() == null) {
						handler = ((BaseAuthModel)baseModel).getDocCreator().getFdId();
					} else {
						handler = execution.getWorkitem().getFdExpecter().getFdId();
					}
					addDingFlow(baseModel, ((BaseAuthModel)baseModel).getDocCreator().getFdLoginName(), ding, getDingUser(handler));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * @param main
	 * @param auth
	 * @param temp
	 * @throws Exception
	 *             启动钉钉的流程
	 */
	public void addDingFlow(IBaseModel baseModel, String auth, ThirdDingOrmTemp temp, String handler) throws Exception {
		Thread.sleep(8000);
		thirdDingFinstanceService.flushHibernateSession();
		List list = thirdDingFinstanceService.findList("fdModelId='"+baseModel.getFdId()+"'", null);
		if(list==null||list.isEmpty()){
			DingApiService dingService = DingUtils.getDingApiService();
			Map map = DingUtil.getExtendDataModelInfo(baseModel);
			String url = DingConstant.DING_PREFIX
					+ "/topapi/processinstance/create"
					+ DingUtil.getDingAppKeyByEKPUserId("?",
							((BaseAuthModel) baseModel).getDocCreator()
									.getFdId());
			logger.debug("钉钉接口：" + url);
			ThirdDingTalkClient client = new ThirdDingTalkClient(url);
			OapiProcessinstanceCreateRequest request = new OapiProcessinstanceCreateRequest();
			// request.setProcessCode("PROC-FFYJRKOV-OCRYFGHMUXW4P2NNWYBI3-7DR9I7MJ-1");//自定义的请假调休
			// request.setProcessCode("PROC-3KYJ23FV-EI7L5QR2R14UG3IBMSCW3-ZLHW373J-V8");//原生的出差
			// request.setProcessCode("PROC-CFYJEB2V-DNDZAI0RTTI6A2MOVPQ73-UYDTX2NJ-H");//自定义的外出
			// request.setProcessCode("PROC-A8294A4D-722A-436E-A510-1D9B95B4EE1C");//自定义的加班
			// request.setProcessCode("PROC-C7E8A633-8853-4808-B514-6C3F173668F4");//自定义的出差
			request.setProcessCode(temp.getFdProcessCode());
			request.setOriginatorUserId(getDingUser(((BaseAuthModel)baseModel).getDocCreator().getFdId()));
			if (((BaseAuthModel) baseModel).getDocCreator().getFdParent() != null) {
                request.setDeptId(Long.parseLong(getDingUser(((BaseAuthModel)baseModel).getDocCreator().getFdParent().getFdId())));
            }
			// 获取流程第一个审批节点处理人
			request.setApprovers(handler);
			List<FormComponentValueVo> formComponentValues = new ArrayList<FormComponentValueVo>();
			// {"name":"SYS-ATC","value":"{\"startTime\":\"2018-01-16 上午\",\"finishTime\":\"2018-01-17	上午\”,\"unit\":\"halfDay\",\"attType\":\"事假\"}","ext_value":""}]
			List<ThirdDingOrmDe> des = temp.getFdDetail();
			StringBuffer md5Str = new StringBuffer();
			if (des != null && !des.isEmpty()) {
				FormComponentValueVo vo = null;
				if ("1".equals(temp.getFdDingTemplateType())) {
					JSONObject jo = new JSONObject();
					vo = new FormComponentValueVo();
					vo.setName("SYS-ATC");
					for (ThirdDingOrmDe de : des) {
						if(map.get(de.getFdEkpField()) instanceof Date){
							jo.put(de.getFdDingField(), DateUtil.convertDateToString((Date) map.get(de.getFdEkpField()), DateUtil.PATTERN_DATE));
						}else if(map.get(de.getFdEkpField()) instanceof String){
							jo.put(de.getFdDingField(), map.get(de.getFdEkpField()));
						}
					}
					jo.put("unit", "day");
					vo.setValue(jo.toString());
					formComponentValues.add(vo);
					logger.info("出勤套件表单参数："+jo);
				} else if ("2".equals(temp.getFdDingTemplateType())) {
					// TODO暂时未支持
				} else {
					for (ThirdDingOrmDe de : des) {
						Object fdEkpField = map.get(de.getFdEkpField());
						if (!(fdEkpField instanceof String) && !(fdEkpField instanceof Date)) {
							continue;
						}
						if (fdEkpField instanceof Date) {
							Date date = (Date) fdEkpField;
							fdEkpField = DateUtil.convertDateToString(date, DateUtil.PATTERN_DATETIME);
						}
						vo = new FormComponentValueVo();
						vo.setName(de.getFdDingField());
						vo.setValue((String) fdEkpField);
						md5Str.append(vo.getName()+";"+vo.getValue());
						formComponentValues.add(vo);
					}
				}
			}
			request.setFormComponentValues(formComponentValues);
			logger.debug("请求钉钉创建流程参数：" + BeanUtils.describe(request));
			System.out.println("请求钉钉创建流程参数：" + BeanUtils.describe(request));
			OapiProcessinstanceCreateResponse response = client.execute(request, dingService.getAccessToken());
			System.out.println("创建钉钉流程返回值：" + BeanUtils.describe(response));
			logger.debug("创建钉钉流程返回值：" + response.getBody());
			JSONObject jo = JSONObject.fromObject(response.getBody());
			// 创建成功后保存启动的流程信息
			if (jo.containsKey("errcode") && jo.getInt("errcode") == 0) {
				ThirdDingFinstance finstance = new ThirdDingFinstance();
				finstance.setFdInstanceId(jo.getString("process_instance_id"));
				finstance.setFdModelId(baseModel.getFdId());
				finstance.setFdModelName(ModelUtil.getModelClassName(baseModel));
				finstance.setFdProcessCode(temp.getFdProcessCode());
				finstance.setFdStartFlow(temp.getFdStartFlow());
				finstance.setFdTemplateId(temp.getFdTemplateId());
				finstance.setFdEkpStatus("20");
				finstance.setFdDingStatus("");
				thirdDingFinstanceService.add(finstance);
				url = DingConstant.DING_PREFIX + "/topapi/processinstance/get"
						+ DingUtil.getDingAppKeyByEKPUserId("?",
								((BaseAuthModel) baseModel).getDocCreator()
										.getFdId());
				logger.debug("钉钉接口：" + url);
				ThirdDingTalkClient dclient = new ThirdDingTalkClient(DingConstant.DING_PREFIX + "/topapi/processinstance/get");
				OapiProcessinstanceGetRequest drequest = new OapiProcessinstanceGetRequest();
				drequest.setProcessInstanceId(jo.getString("process_instance_id"));
				OapiProcessinstanceGetResponse dresponse = dclient.execute(drequest,DingUtils.getDingApiService().getAccessToken());
				if(dresponse.getErrcode()==0){					
					finstance.setFdCreateTime(dresponse.getProcessInstance().getCreateTime().getTime()+"");
					thirdDingFinstanceService.update(finstance);
				}else{
					logger.error("获取钉钉流程实例信息失败("+jo.getString("process_instance_id")+")");
				}
			}
		}
	}

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		/*if (event == null)
			return;
		Object obj = event.getSource();
		if (!(obj instanceof KmReviewMain))
			return;
		if (event instanceof Event_SysFlowFinish) {
			// 不自动更新钉钉流程
		}*/
	}

	private String getDingUser(String fdId) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("fdAppPkId");
		hqlInfo.setWhereBlock("fdEkpId='" + fdId + "'");
		String  fdAppPkId = (String) omsRelationService.findFirstOne(hqlInfo);
		if (StringUtils.isNotBlank(fdAppPkId)) {
			return fdAppPkId;
		}
		return "";
	}

	private String getEKPUser(String userId) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("fdEkpId");
		hqlInfo.setWhereBlock("fdAppPkId='" + userId + "'");
		String fdEkpId = (String) omsRelationService.findFirstOne(hqlInfo);
		if (StringUtils.isNotBlank(fdEkpId)) {
			return fdEkpId;
		}
		return "";
	}

}
