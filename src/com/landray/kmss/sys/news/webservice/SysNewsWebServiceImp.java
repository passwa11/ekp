package com.landray.kmss.sys.news.webservice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.constant.SysDocConstant;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.authentication.background.IBackgroundAuthService;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.metadata.interfaces.ISysMetadataParser;
import com.landray.kmss.sys.metadata.service.ISysMetadataService;
import com.landray.kmss.sys.news.forms.SysNewsMainForm;
import com.landray.kmss.sys.news.model.SysNewsMain;
import com.landray.kmss.sys.news.service.ISysNewsMainService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.sys.tag.forms.SysTagMainForm;
import com.landray.kmss.sys.webservice2.forms.AttachmentForm;
import com.landray.kmss.sys.webservice2.interfaces.ISysWsAttService;
import com.landray.kmss.sys.webservice2.interfaces.ISysWsOrgService;
import com.landray.kmss.sys.workflow.interfaces.ISysWfMainForm;
import com.landray.kmss.sys.workflow.webservice.DefaultStartParameter;
import com.landray.kmss.sys.workflow.webservice.WorkFlowParameterInitializer;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.util.Runner;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.annotation.RestApi;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RestApi(docUrl = "/sys/news/restservice/SysNewRestServiceHelp.jsp", name = "sysNewsWebService", resourceKey = "sys-news:SysNewsRestService.title")
@RequestMapping(value = "/api/sys-news/sysNewsRestService", method = RequestMethod.POST)
public class SysNewsWebServiceImp implements ISysNewsWebService {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysNewsWebServiceImp.class);
	
	private ISysNewsMainService sysNewsMainService;

	public void setSysNewsMainService(ISysNewsMainService sysNewsMainService) {
		this.sysNewsMainService = sysNewsMainService;
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
	
	private ISysOrgElementService sysOrgElementService;

	public void setSysOrgElementService(
			ISysOrgElementService sysOrgElementService) {
		this.sysOrgElementService = sysOrgElementService;
	}
	
	private	ISysOrgPersonService sysOrgPersonService;
	public void setSysOrgPersonService(
			ISysOrgPersonService sysOrgPersonService) {
		this.sysOrgPersonService = sysOrgPersonService;
	}
	

	
	@Override
	@ResponseBody
	@RequestMapping(value = "/addNews")
	public String addNews(@ModelAttribute SysNewsParamterForm form) throws Exception {
		
		SysOrgElement creator = sysWsOrgService.findSysOrgElement(form.getDocCreator());
		if (creator == null) {
            return "创建者不能为空";
        }
		if(StringUtil.isNotNull(form.getDocContent())) {
            form.setDocContent("<p>"+form.getDocContent()+"</p>");
        }
		if(StringUtil.isNull(form.getFdContentType())) {
            form.setFdContentType("rtf");
        }
		if(StringUtil.isNull(form.getFdImportance())) {
            form.setFdImportance("3");
        }
		
		
		return (String) backgroundAuthService.switchUserById(creator.getFdId(),
				new Runner() {

					@Override
					public Object run(Object parameter) throws Exception {
				if (UserUtil.getKMSSUser().isAnonymous()) {
                    return null;
                }
				SysNewsParamterForm newsForm=(SysNewsParamterForm)parameter;
				
				ISysMetadataParser sysMetadataParser = (ISysMetadataParser) SpringBeanUtil
				.getBean("sysMetadataParser");

				SysNewsMain newsmain=new SysNewsMain();

		
				SysDictModel dict = sysMetadataParser.getDictModel(newsmain);
				
				
				
				List<AttachmentForm> attForms = newsForm.getAllAttachmentForms();
				AutoHashMap autoHashMap=new AutoHashMap(AttachmentDetailsForm.class);
				if(attForms.size()>0){
					sysWsAttService.validateAttSize(attForms); // 校验附件大小
				
				boolean isEditonline=true;
				for (AttachmentForm attachmentForm : attForms) {
					AttachmentDetailsForm attachmentDetailsForm = new AttachmentDetailsForm();
					if("Attachment".equals(attachmentForm.getFdKey())){
						attachmentDetailsForm.setFdKey(attachmentForm.getFdKey());
						attachmentDetailsForm.setAttachmentIds(attachmentForm.getFdKey());
						autoHashMap.put("Attachment", attachmentDetailsForm);
					}
					if("editonline".equals(attachmentForm.getFdKey())){
						isEditonline=false;
						if(!"word".equals(newsForm.getFdContentType())){
							return "当上传有附件fdkey为editonline时，文档内容的编辑方式fdContentType必须为word！";
						}
					}
				}
					if(isEditonline==true&&"word".equals(newsForm.getFdContentType())){
						return "当文档内容的编辑方式为word方式时，必须上传一个fdkey为editonline的word文件！";
					}
				
					
				}
					
				RequestContext requestContext = getContext(newsForm,
						dict);
				
				
				DefaultStartParameter flowParam = getStartParameter(newsForm);
				
				if (logger.isDebugEnabled()) {
					logger.debug("开始启动流程...");
				}
			

				// 启动流程
				IExtendForm exform = sysNewsMainService.initFormSetting(
						null, requestContext);
				if (null==exform) {
					logger.debug("流程启动失败...");
				}
				
				// 设置流程API
				WorkFlowParameterInitializer.initialize(
					(ISysWfMainForm) exform, flowParam);

				
				SysNewsMainForm finalform=(SysNewsMainForm) exform;
				
				String fdTagNames = newsForm.getFdTagNames();
				
				SysTagMainForm sysTagMainForm= finalform.getSysTagMainForm();
				sysTagMainForm.setFdKey("newsMainDoc");
				sysTagMainForm.setFdQueryCondition(finalform.getFdTemplateId()+";"+finalform.getFdDepartmentId()+";");
				sysTagMainForm.setFdTagNames(fdTagNames);
				
				finalform.setAutoHashMap(autoHashMap);
				
				String modelId = sysNewsMainService.add(finalform,
						requestContext);
				for (AttachmentForm attachmentForm : attForms) {
					if("editonline".equals(attachmentForm.getFdKey())){
						attachmentForm.setFdFileName(modelId+".doc");
					}
				
				}
						 String modelName = exform.getModelClass().getName();
						 sysWsAttService.save(attForms, modelId, modelName);
				

				return modelId;
			}

		}, form);
		
	
	}
	     

	
	/**
	 * 初始化主文档及流程表单数据
	 */
	private RequestContext getContext(SysNewsParamterForm newsForm,
			SysDictModel dict) throws Exception {
		RequestContext requestContext = new RequestContext();
		requestContext.setParameter("fdTemplateId", newsForm.getFdTemplateId());
		requestContext.setRemoteAddr(newsForm.getDocCreatorClientIp());
		
		Map<String, Object> values = new HashMap<String, Object>();
		requestContext.setAttribute(ISysMetadataService.INIT_MODELDATA_KEY,
				values);
		// 默认为待审状态
		if (StringUtil.isNull(newsForm.getDocStatus())) {
			newsForm.setDocStatus(SysDocConstant.DOC_STATUS_EXAMINE);
		}

		
		values.put("docStatus", newsForm.getDocStatus());
		values.put("docSubject", newsForm.getDocSubject());
		values.put("docContent", newsForm.getDocContent());
		values.put("docCreator", UserUtil.getUser());
		values.put("fdDescription", newsForm.getFdDescription());
		values.put("fdNewsSource", newsForm.getFdNewsSource());
		
		//String regEx="\\{*\\}";
		String regEx="\\{.*\\}";
		Pattern pattern = Pattern.compile(regEx);
		Matcher matcher =pattern.matcher(newsForm.getFdAuthor());
		
		boolean rs=matcher.find();
		
		if(rs){
			SysOrgPerson fdAuthor= (SysOrgPerson) sysWsOrgService.findSysOrgElement(newsForm.getFdAuthor());
			
		    values.put("fdAuthor",fdAuthor);
		}else{
			values.put("fdWriter", newsForm.getFdAuthor());
			requestContext.setParameter("fdWriter", "true");
		}
		
		SysOrgElement fdDepartment=(SysOrgElement) sysOrgElementService.findByPrimaryKey(newsForm.getFdDepartmentId());
		values.put("fdDepartment", fdDepartment);
		values.put("fdImportance", newsForm.getFdImportance());
		values.put("fdContentType", newsForm.getFdContentType());
		
		
		// 文档关键字
/*		String keywordJsonStr = newsForm.getFdKeyword();
		if (StringUtil.isNotNull(keywordJsonStr)) {
			values.put("docKeyword", parseDocKeywords(keywordJsonStr));
		}*/
		

		return requestContext;
	}
	
	/**
	 * 解析文档关键字表达式
	 * 
	 * @param jsonArrStr
	 *            格式为["关键字1", "关键字2"...]
	 * @return
	 */
/*	private List<SysNewsMainKeyword> parseDocKeywords(String jsonArrStr) {
		JSONArray jsonArr = JSONArray.fromObject(jsonArrStr);
		List<SysNewsMainKeyword> keywordList = new ArrayList<SysNewsMainKeyword>();

		for (Object value : jsonArr) {
			SysNewsMainKeyword docKeyword = new SysNewsMainKeyword();
			docKeyword.setDocKeyword((String) value);
			keywordList.add(docKeyword);
		}

		return keywordList;
	}*/
	

	

	/**
	 * 初始化流程参数
	 * 
	 * @param webForm
	 * @return
	 */
	private DefaultStartParameter getStartParameter(SysNewsParamterForm newsForm) {
		DefaultStartParameter param = new DefaultStartParameter();

		param.setDocStatus(newsForm.getDocStatus());
		param.setDrafterId(UserUtil.getUser().getFdId());
		if(StringUtil.isNotNull(newsForm.getFlowParam())) {
            setFlowParam(param, newsForm);
        }

		return param;
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
		SysNewsParamterForm newsForm) {
	JSONObject jsonObj = JSONObject.fromObject(newsForm.getFlowParam());

	if (!jsonObj.isNullObject() && !jsonObj.isEmpty()) {
		Object auditNode = jsonObj.get("auditNode"); // 审批意见
		if (auditNode != null) {
			param.setAuditNode(auditNode.toString());
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


