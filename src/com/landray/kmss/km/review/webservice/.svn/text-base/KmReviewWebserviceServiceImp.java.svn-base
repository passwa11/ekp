package com.landray.kmss.km.review.webservice;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.constant.SysDocConstant;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.km.review.forms.KmReviewMainForm;
import com.landray.kmss.km.review.model.KmReviewDocKeyword;
import com.landray.kmss.km.review.model.KmReviewMain;
import com.landray.kmss.km.review.model.KmReviewTemplate;
import com.landray.kmss.km.review.service.IKmReviewMainService;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.sys.authentication.background.IBackgroundAuthService;
import com.landray.kmss.sys.authorization.model.SysAuthArea;
import com.landray.kmss.sys.authorization.service.ISysAuthAreaService;
import com.landray.kmss.sys.category.model.SysCategoryProperty;
import com.landray.kmss.sys.config.dict.SysDictCommonProperty;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.lbpm.engine.builder.WorkitemInstance;
import com.landray.kmss.sys.lbpm.engine.service.ProcessExecuteService;
import com.landray.kmss.sys.lbpm.engine.service.ProcessInstanceInfo;
import com.landray.kmss.sys.metadata.interfaces.ISysMetadataParser;
import com.landray.kmss.sys.metadata.service.ISysMetadataService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.webservice2.forms.AttachmentForm;
import com.landray.kmss.sys.webservice2.interfaces.ISysWsAttService;
import com.landray.kmss.sys.webservice2.interfaces.ISysWsOrgService;
import com.landray.kmss.sys.webservice2.interfaces.imp.SysWsOrgServiceImp;
import com.landray.kmss.sys.webservice2.util.SysWsUtil;
import com.landray.kmss.sys.workflow.interfaces.ISysWfMainForm;
import com.landray.kmss.sys.workflow.interfaces.SysWfBusinessForm;
import com.landray.kmss.sys.workflow.webservice.DefaultStartParameter;
import com.landray.kmss.sys.workflow.webservice.WorkFlowParameterInitializer;
import com.landray.kmss.sys.xform.base.model.SysFormTemplate;
import com.landray.kmss.sys.xform.base.service.ISysFormTemplateService;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.FormCheckUtil;
import com.landray.kmss.util.Runner;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.annotation.RestApi;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "/api/km-review/kmReviewRestService", method = RequestMethod.POST)
@RestApi(docUrl = "/km/review/restservice/kmReviewRestServiceHelp.jsp", name = "kmReviewRestService", resourceKey = "km-review:kmReviewRestService.title")

public class KmReviewWebserviceServiceImp implements IKmReviewWebserviceService {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(KmReviewWebserviceServiceImp.class);

	private IKmReviewMainService kmReviewMainService;
	public void setKmReviewMainService(IKmReviewMainService kmReviewMainService) {
		this.kmReviewMainService = kmReviewMainService;
	}

	private ISysWsAttService sysWsAttService;
	public void setSysWsAttService(ISysWsAttService sysWsAttService) {
		this.sysWsAttService = sysWsAttService;
	}

	private ISysWsOrgService sysWsOrgService;
	public void setSysWsOrgService(ISysWsOrgService sysWsOrgService) {
		this.sysWsOrgService = sysWsOrgService;
	}

	private IBackgroundAuthService backgroundAuthService;
	public void setBackgroundAuthService(
			IBackgroundAuthService backgroundAuthService) {
		this.backgroundAuthService = backgroundAuthService;
	}
	
	protected ProcessExecuteService processExecuteService;
	public void setProcessExecuteService(ProcessExecuteService processExecuteService) {
		this.processExecuteService = processExecuteService;
	}
	
	private ISysMetadataService sysMetadataService;
	public void setSysMetadataService(ISysMetadataService sysMetadataService) {
		this.sysMetadataService = sysMetadataService;
	}
	
	private ISysAttMainCoreInnerService sysAttMainService;
	public void setSysAttMainService(ISysAttMainCoreInnerService sysAttMainService) {
		this.sysAttMainService = sysAttMainService;
	}
	
	@ResponseBody
	@RequestMapping(value = "addReview")
	@Override
	public String addReview(@ModelAttribute KmReviewParamterForm webParamForm)
			throws Exception {
		// 切换当前用户
		SysOrgElement creator = sysWsOrgService.findSysOrgElement(webParamForm
				.getDocCreator());
		if (creator == null) {
            return "user is not exist";
        }
		// 修改切换用户的方法 @作者：曹映辉 @日期：2012年11月15日
		return (String) backgroundAuthService.switchUserById(creator.getFdId(),
				new Runner() {

					@Override
					public Object run(Object parameter) throws Exception {
						//判断当前登录用户是否匿名，不允许匿名登录
						if (UserUtil.getKMSSUser().isAnonymous()) {
                            return null;
                        }
						KmReviewParamterForm webForm = (KmReviewParamterForm) parameter;

						// modify #4344 增加对组织架构类型json参数的支持 #曹映辉 2014.8.22
						// 根据模板id 加载数据字典
						// DictLoadService
						// loader=(DictLoadService)SpringBeanUtil.getBean("sysFormDictLoadService");
						ISysMetadataParser sysMetadataParser = (ISysMetadataParser) SpringBeanUtil
								.getBean("sysMetadataParser");

						ISysFormTemplateService sysFormTemplateService = (ISysFormTemplateService) SpringBeanUtil
								.getBean("sysFormTemplateService");
						
						HQLInfo hqlCommon = new HQLInfo();
						hqlCommon.setWhereBlock("fdModelId=:fdModelId and fdModelName=:fdModelName and fdParentForm is null");
						hqlCommon.setParameter("fdModelId", webForm.getFdTemplateId());
						hqlCommon.setParameter("fdModelName", KmReviewTemplate.class.getName());
						
						List<SysFormTemplate> sysFormTemplateValues = sysFormTemplateService
								.findValue(hqlCommon);
						
						KmReviewMain km = new KmReviewMain();
						
						if(!CollectionUtils.isEmpty(sysFormTemplateValues)) {
							SysFormTemplate sysFormTemplate=sysFormTemplateValues.get(0);
							//通用表单流程数据
							if(sysFormTemplate.getFdCommonTemplate()!=null) {
								km.setExtendFilePath(sysFormTemplateValues.get(0).getFdCommonTemplate().getFdFormFileName());
							}else {
								km.setExtendFilePath(sysFormTemplateValues.get(0).getFdFormFileName());
							}
						}
						

						SysDictModel dict = sysMetadataParser.getDictModel(km);
						

						// 初始化表单流程数据
						RequestContext requestContext = getContext(webForm,
								dict);
						DefaultStartParameter flowParam = getStartParameter(webForm);

						if (logger.isDebugEnabled()) {
							logger.debug("开始启动流程...");
						}

						String modelId = null;
						try {
							// 启动流程
							IExtendForm form = kmReviewMainService
									.initFormSetting(
											null, requestContext);
							// 设置流程API
							WorkFlowParameterInitializer.initialize(
									(ISysWfMainForm) form, flowParam);

							List<AttachmentForm> attForms = webForm
									.getAttachmentForms();
							sysWsAttService.validateAttSize(attForms); // 校验附件大小

							modelId = kmReviewMainService.add(form,
									requestContext);
							String modelName = form.getModelClass().getName();
							sysWsAttService.save(attForms, modelId, modelName);

							if (logger.isDebugEnabled()) {
								logger.debug("流程启动完毕！");
							}
						} catch (Exception e) {
							// 有异常时事物回滚
							throw new RuntimeException(e);
						}

						return modelId;
					}

				}, webParamForm);
	}

	/**
	 * 获取处理节点用户
	 * 
	 * @param processInfo
	 * @param operationType
	 *            根据操作来判断
	 * @return
	 */
	private SysOrgElement getElement(ProcessInstanceInfo processInfo,
			String operationType) {
		List<WorkitemInstance> workitems = processInfo.getCurrentWorkitems();

		WorkitemInstance workitem = workitems.get(0);

		// 下面对回复沟通和退回加签做特殊处理
		if ("handler_returnCommunicate".equals(operationType)) {// 回复沟通 特殊处理
			// "activityType":"communicateWorkitem"
			for (int i = 0; i < workitems.size(); i++) {
				if ("communicateWorkitem"
						.equals(workitems.get(i).getFdActivityType())) {
					workitem = workitems.get(i);
					break;
				}
			}
		} else if ("handler_assignRefuse".equals(operationType)) {// 退回加签
			// "activityType":"assignWorkitem",
			for (int i = 0; i < workitems.size(); i++) {
				if ("assignWorkitem"
						.equals(workitems.get(i).getFdActivityType())) {
					workitem = workitems.get(i);
					break;
				}
			}
		}

		SysOrgElement element = workitem.getFdExpecter();
		if (SysOrgConstant.ORG_TYPE_POST == element.getFdOrgType()) {
			element = (SysOrgElement) element.getFdPersons().get(0);
		}

		return element;
	}

	/**
	 * 审批流程接口
	 */
	@ResponseBody
	@RequestMapping(value = "approveProcess")
	@Override
	public String
			approveProcess(@ModelAttribute KmReviewParamterForm webParamForm)
					throws Exception {
		// 正中流程集成拦截
		new ReiewHandler(webParamForm).process();
		//获取流程
		ProcessInstanceInfo processInfo = processExecuteService.load(webParamForm.getFdId());
		//检验流程
		String result = inspect(webParamForm.getFdId(), processInfo);
		if(StringUtil.isNotNull(result)) {
			return result;
		}

		// 获取请求参数flowParam
		JSONObject flowParamterJsonTemp = JSONObject
				.fromObject(webParamForm.getFlowParam());

		// 设置审操作类型
		String operationTypeTemp = flowParamterJsonTemp
				.getString("operationType");

		// 操作者
		SysOrgElement handler = null;
		if (flowParamterJsonTemp.has("handler")
				&& StringUtil.isNotNull(flowParamterJsonTemp
				.getString("handler"))) {
			handler = sysWsOrgService.findSysOrgElement(flowParamterJsonTemp
					.getString("handler"));
		}
		else {
			handler = getElement(processInfo, operationTypeTemp);
		}
		if (handler == null || (handler.getFdIsAbandon() != null
				&& handler.getFdIsAbandon())) {
            return "user is not exist";
        }
		// 修改切换用户的方法 @作者：曹映辉 @日期：2012年11月15日
		return (String) backgroundAuthService.switchUserById(handler.getFdId(),
				new Runner() {

					@Override
					public Object run(Object parameter) throws Exception {
						//判断当前登录用户是否匿名，不允许匿名登录
						if (UserUtil.getKMSSUser().isAnonymous()) {
                            return null;
                        }
						KmReviewParamterForm webForm = (KmReviewParamterForm) parameter;

						ISysMetadataParser sysMetadataParser = (ISysMetadataParser) SpringBeanUtil
								.getBean("sysMetadataParser");

						ISysFormTemplateService sysFormTemplateService = (ISysFormTemplateService) SpringBeanUtil
								.getBean("sysFormTemplateService");
						

						KmReviewMain mainModel = (KmReviewMain) kmReviewMainService.findByPrimaryKey(webForm.getFdId());
						//获取模板id
						HQLInfo hql = new HQLInfo();
						hql.setSelectBlock("fdId");
						hql.setWhereBlock(
								"fdModelId=:fdModelId and fdModelName=:fdModelName");
						hql.setParameter("fdModelId",
								mainModel.getFdTemplate().getFdId());
						hql.setParameter("fdModelName",
								KmReviewTemplate.class.getName());

						List<String> values = sysFormTemplateService
								.findValue(hql);
						SysDictModel dict = sysMetadataParser.getDictModel(mainModel);
						if (StringUtil.isNull(
								webForm.getDocSubject())) {
							webForm.setDocSubject(mainModel.getDocSubject());
						}
						// 初始化表单流程数据
						RequestContext requestContext = getContext(webForm,
								dict);
						Map<String, Object> formValues = (Map<String, Object>) requestContext
								.getAttribute(
										ISysMetadataService.INIT_MODELDATA_KEY);
						formValues.put("docCreator", mainModel.getDocCreator());
						// 检测表单key是否存在
						String checkJson = FormCheckUtil.checkFieldIsExist(
								requestContext, values.get(0));
						if(StringUtil.isNotNull(checkJson)){
							return checkJson;
						}

						//读取表单流程数据，初始化model
						sysMetadataService.readModelSetting(mainModel,requestContext);
						//初始化流程参数
						DefaultStartParameter flowParam = getStartParameter(webForm);

						if (logger.isDebugEnabled()) {
							logger.debug("开始审批流程...");
						}

						try {
							//建立流程form
							KmReviewMainForm mainForm = new KmReviewMainForm();
							mainForm.setFdId(webForm.getFdId());
							IExtendForm form = kmReviewMainService.convertModelToForm(mainForm, mainModel, requestContext);
							
							if(StringUtil.isNull(requestContext.getParameter("fdTemplateId"))){
								requestContext.setParameter("fdTemplateId",
										mainModel.getFdTemplate().getFdId());
							}
							KmReviewMainForm requestForm = (KmReviewMainForm)kmReviewMainService
									.initFormSetting(
											null, requestContext);
							Map<String, Object> requestMap=null;
							Map<String, Object> requestOldMap=null;
							if(requestForm!=null&&form!=null) {
								requestMap=requestForm.getExtendDataFormInfo().getFormData();
								requestOldMap=((KmReviewMainForm)form).getExtendDataFormInfo().getFormData();
							}

							Map<String, SysDictCommonProperty> propertyMap = dict.getPropertyMap();
							if(requestMap!=null&&requestOldMap!=null){
								for (String key : requestMap.keySet()) {
									SysDictCommonProperty sysDictCommonProperty=propertyMap.get(key);
									if(sysDictCommonProperty!=null&&sysDictCommonProperty.getBusinessType()!=null&& "detailsTable".equals(sysDictCommonProperty.getBusinessType())){
										List oldList=(List)requestOldMap.get(key);
										List newList=(List)requestMap.get(key);
										List tempList=null;
										if(CollectionUtils.isNotEmpty(oldList)&&CollectionUtils.isNotEmpty(newList)&&oldList.size()>=newList.size()){
											tempList=oldList.subList(0,newList.size());
										}

										if(CollectionUtils.isNotEmpty(tempList)){
											requestOldMap.put(key,tempList);
										}
									}
								}
							}
							
							
							// 设置流程API
							WorkFlowParameterInitializer.initialize(
									(ISysWfMainForm) form, flowParam);
							
							/*更新审批操作*/
							mainForm = (KmReviewMainForm)form;
							
							SysWfBusinessForm sysWfBusinessForm = mainForm.getSysWfBusinessForm();
							//获取sysWfBusinessForm的fdParameterJson对象
							JSONObject fdParamterJson = JSONObject.fromObject(sysWfBusinessForm.getFdParameterJson());
							//获取请求参数flowParam
							JSONObject flowParamterJson =JSONObject.fromObject(webForm.getFlowParam());
							
							//设置审操作类型
							String operationType = flowParamterJson.getString("operationType");
							fdParamterJson.put("operationType", operationType);
							//设置参数
							if(flowParamterJson.has("operParam")) {
								JSONObject operParamJson = flowParamterJson.getJSONObject("operParam");
								//修改
								operParamJson.put("auditNote", flowParamterJson.getString("auditNote"));
								if(flowParamterJson.has("futureNodeId")) {
									String futureNodeId = flowParamterJson.getString("futureNodeId");
									if(StringUtil.isNotNull(futureNodeId)) {
										//人工决策
										operParamJson.put("futureNodeId", futureNodeId);
									}
								}
								
								if(operParamJson.has("otherHandlers")) {
									//其他处理人，只获取id
									String otherHandlers = operParamJson.getString("otherHandlers");
									String otherHandlerIds = getHandlerIds(otherHandlers);
									operParamJson.remove("otherHandlers");
									operParamJson.put("toOtherHandlerIds", otherHandlerIds);
								}
								fdParamterJson.put("param", operParamJson);
							}
							
							sysWfBusinessForm.setFdParameterJson(fdParamterJson.toString());
							form = mainForm;
							
							List<AttachmentForm> attForms = webForm
									.getAttachmentForms();
							sysWsAttService.validateAttSize(attForms); // 校验附件大小

							//更新
							kmReviewMainService.update(form, requestContext);
							
							String modelName = form.getModelClass().getName();
							sysWsAttService.save(attForms, webForm.getFdId(), modelName);

							if (logger.isDebugEnabled()) {
								logger.debug("审批流程完毕！");
							}
						} catch (Exception e) {
							// 有异常时事物回滚
							throw new RuntimeException(e);
						}
						return webForm.getFdId();
					}

				}, webParamForm);
	}

	@ResponseBody
	@RequestMapping(value = "updateReviewInfo")
	@Override
	public String
			updateReviewInfo(@ModelAttribute KmReviewParamterForm webParamForm)
					throws Exception {
		// 正中流程集成拦截
		new ReiewHandler(webParamForm).process();
		//获取流程
		ProcessInstanceInfo processInfo = processExecuteService.load(webParamForm.getFdId());
		//检验流程
		String result = inspect(webParamForm.getFdId(), processInfo);
		if(StringUtil.isNotNull(result)) {
			return result;
		}
		//获取处理节点用户
		SysOrgElement element = getElement(processInfo);
		if (element == null || (element.getFdIsAbandon()!=null&&element.getFdIsAbandon())) {
            return "user is not exist";
        }
		
		// 修改切换用户的方法 @作者：曹映辉 @日期：2012年11月15日
		return (String) backgroundAuthService.switchUserById(element.getFdId(),
				new Runner() {

					@Override
					public Object run(Object parameter) throws Exception {
						//判断当前登录用户是否匿名，不允许匿名登录
						if (UserUtil.getKMSSUser().isAnonymous()) {
                            return null;
                        }
						KmReviewParamterForm webForm = (KmReviewParamterForm) parameter;

						ISysMetadataParser sysMetadataParser = (ISysMetadataParser) SpringBeanUtil
								.getBean("sysMetadataParser");

						ISysFormTemplateService sysFormTemplateService = (ISysFormTemplateService) SpringBeanUtil
								.getBean("sysFormTemplateService");

						KmReviewMain mainModel = (KmReviewMain) kmReviewMainService
								.findByPrimaryKey(webForm.getFdId());
						SysDictModel dict = sysMetadataParser
								.getDictModel(mainModel);
						if (StringUtil.isNull(
								webForm.getDocSubject())) {
							webForm.setDocSubject(mainModel.getDocSubject());
						}
						//获取模板id
						HQLInfo hql = new HQLInfo();
						hql.setSelectBlock("fdId");
						hql.setWhereBlock("fdModelId=:fdModelId and fdModelName=:fdModelName");
						hql.setParameter("fdModelId",
								mainModel.getFdTemplate().getFdId());
						hql.setParameter("fdModelName", KmReviewTemplate.class.getName());

						List<String> values = sysFormTemplateService
								.findValue(hql);


						// 初始化表单流程数据
						RequestContext requestContext = getContext(webForm,
								dict);
						Map<String, Object> formValues = (Map<String, Object>) requestContext
								.getAttribute(
										ISysMetadataService.INIT_MODELDATA_KEY);
						formValues.put("docCreator", mainModel.getDocCreator());
						//检查表单key是否存在
						String checkJson=FormCheckUtil.checkFieldIsExist(requestContext,values.get(0));
						if(StringUtil.isNotNull(checkJson)){
							return checkJson;
						}
						//读取表单流程数据，初始化model
						sysMetadataService.readModelSetting(mainModel,requestContext);
						DefaultStartParameter flowParam = getStartParameter(webForm);

						if (logger.isDebugEnabled()) {
							logger.debug("开始更新流程...");
						}

						try {
							// 建立流程form
							KmReviewMainForm mainForm = new KmReviewMainForm();
							mainForm.setFdId(webForm.getFdId());
							IExtendForm form = kmReviewMainService.convertModelToForm(mainForm, mainModel, requestContext);
							
							if(StringUtil.isNull(requestContext.getParameter("fdTemplateId"))){
								requestContext.setParameter("fdTemplateId",
										mainModel.getFdTemplate().getFdId());
							}
							KmReviewMainForm requestForm = (KmReviewMainForm)kmReviewMainService
									.initFormSetting(
											null, requestContext);
							Map<String, Object> requestMap=null;
							Map<String, Object> requestOldMap=null;
							if(requestForm!=null&&form!=null) {
								requestMap=requestForm.getExtendDataFormInfo().getFormData();
								requestOldMap=((KmReviewMainForm)form).getExtendDataFormInfo().getFormData();
							}

							Map<String, SysDictCommonProperty> propertyMap = dict.getPropertyMap();
							if(requestMap!=null&&requestOldMap!=null){
								for (String key : requestMap.keySet()) {
									SysDictCommonProperty sysDictCommonProperty=propertyMap.get(key);
									if(sysDictCommonProperty!=null&&sysDictCommonProperty.getBusinessType()!=null&& "detailsTable".equals(sysDictCommonProperty.getBusinessType())){
										List oldList=(List)requestOldMap.get(key);
										List newList=(List)requestMap.get(key);
										List tempList=null;
										if(CollectionUtils.isNotEmpty(oldList)&&CollectionUtils.isNotEmpty(newList)&&oldList.size()>=newList.size()){
											tempList=oldList.subList(0,newList.size());
										}

										if(CollectionUtils.isNotEmpty(tempList)){
											requestOldMap.put(key,tempList);
										}
									}
								}
							}
							
							
							// 设置流程API
							WorkFlowParameterInitializer.initialize(
									(ISysWfMainForm) form, flowParam);

							List<AttachmentForm> attForms = webForm
									.getAttachmentForms();
							sysWsAttService.validateAttSize(attForms); // 校验附件大小

							//只更新不驱动
							((KmReviewMainForm) form).getSysWfBusinessForm().setCanStartProcess("false");
							//更新
							kmReviewMainService.update(form, requestContext);
							
							Set<String> fdKeySet=new HashSet<String>();
							if(!CollectionUtils.isEmpty(attForms)) {
								for(int i=0;i<attForms.size();i++) {
									AttachmentForm attachmentForm=attForms.get(i);
									fdKeySet.add(attachmentForm.getFdKey());
								}
							}
							
							if(CollectionUtils.isNotEmpty(fdKeySet)) {
								Iterator<String> iterator = fdKeySet.iterator();
								while(iterator.hasNext()){
									String fdkey=iterator.next();
									//先删除附件，然后在新增
									HQLInfo hqlInfo=new HQLInfo();
									hqlInfo.setModelName(SysAttMain.class.getName());
									String whereBlock = "fdModelId='" + webForm.getFdId() + "' and fdKey='"+fdkey+"'";
									hqlInfo.setWhereBlock(whereBlock);
									List<SysAttMain> sysAttMains=sysWsAttService.findList(hqlInfo);
									if(CollectionUtils.isNotEmpty(sysAttMains)) {
										String[] ids=new String[sysAttMains.size()];
										for(int i=0;i<sysAttMains.size();i++) {
											ids[i]=sysAttMains.get(i).getFdId();
										}
										if(ids.length>0) {
											sysWsAttService.deleteByIds(ids);
										}
									}
								}
							}
							
							
							String modelName = form.getModelClass().getName();
							sysWsAttService.save(attForms, webForm.getFdId(), modelName);
							
							

							if (logger.isDebugEnabled()) {
								logger.debug("更新流程完毕！");
							}
						} catch (Exception e) {
							// 有异常时事物回滚
							throw new RuntimeException(e);
						}

						return webForm.getFdId();
					}
						
				}, webParamForm);
	}
	
	/**
	 * 获取处理节点用户
	 * @param processInfo
	 * @return
	 */
	private SysOrgElement getElement(ProcessInstanceInfo processInfo){
		WorkitemInstance workitem = processInfo.getCurrentWorkitems().get(0);
		SysOrgElement element = workitem.getFdExpecter();
		if(SysOrgConstant.ORG_TYPE_POST == element.getFdOrgType()){
			element = (SysOrgElement) element.getFdPersons().get(0);
		}
		
		return element;
	}
	
	/**
	 * 获取多个处理人id
	 * @param jsonArrStr
	 * @return
	 * @throws Exception
	 */
	private String getHandlerIds(String jsonArrStr) throws Exception {
		ISysWsOrgService sysWsOrgServiceImp = new SysWsOrgServiceImp();
		List<SysOrgElement> handlers = sysWsOrgServiceImp.findSysOrgList(jsonArrStr);
		String handlerIds = "";
		for(int i=0; i<handlers.size()-1; i++) {
			handlerIds += handlers.get(i).getFdId() + ";";
		}
		handlerIds += handlers.get(handlers.size()-1).getFdId();
		
		return handlerIds;
	}
	
	/**
	 * 初始化主文档及流程表单数据
	 */
	private RequestContext getContext(KmReviewParamterForm webForm,
			SysDictModel dict) throws Exception {
		RequestContext requestContext = new RequestContext();
		if (StringUtil.isNotNull(webForm.getFdTemplateId())) {
			requestContext.setParameter("fdTemplateId",
					webForm.getFdTemplateId());
		}

		Map<String, Object> values = new HashMap<String, Object>();
		requestContext.setAttribute(ISysMetadataService.INIT_MODELDATA_KEY,
				values);
		// 默认为待审状态
		if (StringUtil.isNull(webForm.getDocStatus())) {
			webForm.setDocStatus(SysDocConstant.DOC_STATUS_EXAMINE);
		}

		values.put("docStatus", webForm.getDocStatus());
		values.put("docSubject", webForm.getDocSubject());
		values.put("docContent", webForm.getDocContent());
		values.put("docCreator", UserUtil.getUser());

		ISysAuthAreaService sysAuthAreaService=(ISysAuthAreaService)SpringBeanUtil.getBean("sysAuthAreaService");
		SysAuthArea sysAuthArea=(SysAuthArea)sysAuthAreaService.findByPrimaryKey(webForm.getAuthAreaId(),SysAuthArea.class,true);
		if(sysAuthArea!=null&&sysAuthArea.getFdName()!=null) {
			values.put("authArea", sysAuthAreaService.findByPrimaryKey(webForm.getAuthAreaId()));
		}
		
		
		// 流程表单数据
		String formJson = webForm.getFormValues();
		if (StringUtil.isNotNull(formJson)) {
			Map<String, Object> formMap = SysWsUtil.json2map(formJson, dict);
			for (String key : formMap.keySet()) {// keySet获取map集合key的集合 然后在遍历key即可
				Object value = formMap.get(key);//
				if (value instanceof Date) {
					DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Object dateStr = df.format(value);
					formMap.put(key, dateStr);
				}
			}
			values.putAll(formMap);
		}
		// 文档关键字
		String keywordJsonStr = webForm.getFdKeyword();
		if (StringUtil.isNotNull(keywordJsonStr)) {
			values.put("docKeyword", parseDocKeywords(keywordJsonStr));
		}
		// 辅类别
		String docPropJsonStr = webForm.getDocProperty();
		if (StringUtil.isNotNull(docPropJsonStr)) {
			values.put("docProperties", parseDocProperties(docPropJsonStr));
		}

		return requestContext;
	}
	
	/**
	 * 检验流程
	 * @param fdId
	 * @param processInfo
	 * @return
	 */
	private String inspect(String fdId, ProcessInstanceInfo processInfo) {
		
		
		if(processInfo == null) {
			//不存在
			String errorInfo = String.format("没有找到该流程#%s！", fdId);
			if (logger.isWarnEnabled()) {
                logger.warn(errorInfo);
            }
			return FormCheckUtil.setJsonParam(false,FormCheckUtil.MESSAGE_KEY,errorInfo);
		}
		
		if (ArrayUtil.isEmpty(processInfo.getCurrentWorkitems())) {
			String errorInfo=String.format("当前流程中#%s不包含任何操作！", fdId);
			if (logger.isWarnEnabled()) {
                logger.warn(errorInfo);
            }
			return FormCheckUtil.setJsonParam(false,FormCheckUtil.MESSAGE_KEY,errorInfo);
		}
		
		return null;
	}

	/**
	 * 初始化流程参数
	 */
	private DefaultStartParameter getStartParameter(KmReviewParamterForm webForm) {
		DefaultStartParameter param = new DefaultStartParameter();

		param.setDocStatus(webForm.getDocStatus());
		param.setDrafterId(UserUtil.getUser().getFdId());
		setFlowParam(param, webForm);

		return param;
	}

	/**
	 * 解析文档关键字表达式
	 * 
	 * @param jsonArrStr
	 *            格式为["关键字1", "关键字2"...]
	 * @return
	 */
	private List<KmReviewDocKeyword> parseDocKeywords(String jsonArrStr) {
		JSONArray jsonArr = JSONArray.fromObject(jsonArrStr);
		List<KmReviewDocKeyword> keywordList = new ArrayList<KmReviewDocKeyword>();

		for (Object value : jsonArr) {
			KmReviewDocKeyword docKeyword = new KmReviewDocKeyword();
			docKeyword.setDocKeyword((String) value);
			keywordList.add(docKeyword);
		}

		return keywordList;
	}

	/**
	 * 解析辅类别表达式
	 * 
	 * @param jsonArrStr
	 *            格式为["fdId01", "fdId02"...]
	 * @return
	 */
	private List<SysCategoryProperty> parseDocProperties(String jsonArrStr) {
		JSONArray jsonArr = JSONArray.fromObject(jsonArrStr);
		List<SysCategoryProperty> categoryList = new ArrayList<SysCategoryProperty>();

		for (Object value : jsonArr) {
			SysCategoryProperty category = new SysCategoryProperty();
			category.setFdId((String) value);
			categoryList.add(category);
		}

		return categoryList;
	}

	/**
	 * 解析流程参数的表达式
	 * 
	 * @param jsonArrStr
	 *            格式为{auditNode:"", futureNodeId:"",
	 *            changeNodeHandlers:["节点名1：用户ID1; 用户ID2...",
	 *            "节点名2：用户ID1; 用户ID2..."...]}
	 * @return
	 */
	private void setFlowParam(DefaultStartParameter param,
			KmReviewParamterForm webForm) {
		JSONObject jsonObj = JSONObject.fromObject(webForm.getFlowParam());

		if (!jsonObj.isNullObject() && !jsonObj.isEmpty()) {
			Object auditNode = jsonObj.get("auditNode"); // 审批意见
			if (auditNode != null) {
				param.setAuditNode(auditNode.toString());
			} else {
				Object auditNote = jsonObj.get("auditNote"); // 审批意见
				if (auditNote != null) {
					param.setAuditNode(auditNote.toString());
				}
			}

			Object futureNodeId = jsonObj.get("futureNodeId"); // 人工决策节点
			if (futureNodeId != null) {
				param.setFutureNodeId(futureNodeId.toString());
			}

			Object handlers = jsonObj.get("changeNodeHandlers"); // “必须修改节点处理人”节点
			if (handlers != null) {
				JSONArray jsonArr = JSONArray.fromObject(handlers);
				param.setChangeNodeHandlers(jsonArr);
			}
		}
	}
}
