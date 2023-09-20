package com.landray.kmss.km.archives.actions;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.landray.kmss.common.exception.KmssException;
import com.landray.kmss.common.module.util.ModuleCenter;
import com.landray.kmss.km.archives.depend.IFileDataServiceApi;
import com.landray.kmss.util.*;
import org.apache.commons.beanutils.PropertyUtils;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.km.archives.forms.KmArchivesFileTemplateForm;
import com.landray.kmss.km.archives.interfaces.IFileDataService;
import com.landray.kmss.km.archives.model.KmArchivesFileConfig;
import com.landray.kmss.km.archives.model.KmArchivesFileTemplate;
import com.landray.kmss.km.archives.model.KmArchivesMain;
import com.landray.kmss.km.archives.service.IKmArchivesFileTemplateService;
import com.landray.kmss.km.archives.service.IKmArchivesMainService;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.sso.client.oracle.StringUtil;

import net.sf.json.JSONObject;

public class KmArchivesFileTemplateAction extends ExtendAction {

	private IKmArchivesFileTemplateService kmArchivesFileTemplateService;

	@Override
	public IKmArchivesFileTemplateService
			getServiceImp(HttpServletRequest request) {
		if (kmArchivesFileTemplateService == null) {
			kmArchivesFileTemplateService = (IKmArchivesFileTemplateService) getBean(
					"kmArchivesFileTemplateService");
        }
		return kmArchivesFileTemplateService;
    }

	/**
	 * 根据自定义属性的类型获得选择项列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getTypeFields(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			String type = request.getParameter("type");
			String modelName = request.getParameter("modelName");
			String templateService = request.getParameter("templateService");
			String templateId = request.getParameter("templateId");
			String otherModelName = request.getParameter("otherModelName");
			Map<String, String> options = null;
			if (StringUtil.isNull(otherModelName)) {
				options = getServiceImp(request).getOptions(modelName, type,
						templateService, templateId);
			} else {
				options = new LinkedHashMap<>();
				String[] otherModelNames = otherModelName.split("[,;]");
				String[] modelNames = new String[otherModelNames.length + 1];
				modelNames[0] = modelName;
				for (int i = 1; i < modelNames.length; i++) {
					modelNames[i] = otherModelNames[i - 1];
				}
				for (int i = 0; i < modelNames.length; i++) {
					String name = modelNames[i];
					SysDictModel dict = SysDataDict.getInstance()
							.getModel(ModelUtil.getModelClassName(name));
					String messageKey = dict.getMessageKey();
					if (StringUtil.isNotNull(messageKey)) {
						messageKey = ResourceUtil.getString(messageKey,
								request.getLocale());
					}
					Map<String, String> map = new HashMap<String, String>();
					if (name.equals(modelName)) {
						map = getServiceImp(request).getOptions(name, type,
								templateService, templateId);
					} else {
						map = getServiceImp(request).getOptions(name, type,
								null, null);
					}
					Iterator<String> it = map.keySet().iterator();
					while (it.hasNext()) {
						String key = it.next();
						String value = map.get(key);
						options.put(name + ":" + key,
								messageKey + " - " + value);
					}
				}
			}
			JSONObject obj = JSONObject.fromObject(options);
			request.setAttribute("lui-source", obj);
		} catch (Exception e) {
			messages.addError(e);
		}
		if (!messages.hasError()) {
			return getActionForward("lui-source", mapping, form, request,
					response);
		}
		return null;
	}

	/**
	 * 归档多个文档
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward fileDocAll(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			String[] ids = request.getParameterValues("List_Selected");
			String serviceName = request.getParameter("serviceName");
			//原逻辑
			Object bean = getBean(serviceName);
			if(bean instanceof IFileDataService) {
				IFileDataService service = (IFileDataService) bean;
				for (String id : ids) {
					service.addFileMainDoc(request, id, null);
				}
			} else {
				//解耦逻辑
				IFileDataServiceApi apiProxy = ModuleCenter.getDeclareApiProxy(IFileDataServiceApi.class, serviceName);
				if(apiProxy != null) {
					for (String id : ids) {
						apiProxy.addFileMainDoc(request, id, null);
					}
				}
			}
		} catch (Exception e) {
			messages.addError(e);
		}
		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_RETURN).save(request);
		if (messages.hasError()) {
            return getActionForward("failure", mapping, form, request,
                    response);
        } else {
            return getActionForward("success", mapping, form, request,
                    response);
        }
	}

	/**
	 * 归档单个文档
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward fileDoc(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			String fdModelId = request.getParameter("fdMainModelId");
			String fdOldSaveApproval = request.getParameter("fdOldSaveApproval");
			String fdOldSaveOldFile = request.getParameter("fdOldSaveOldFile");
			String fdOldFilePersonId = request.getParameter("fdOldFilePersonId");
			String serviceName = request.getParameter("serviceName");
			if (StringUtil.isNull(fdModelId)) {
				throw new NoRecordException();
			}
			KmArchivesFileTemplateForm mainForm = (KmArchivesFileTemplateForm) form;
			KmArchivesFileTemplate nFlieTemplate = null;
			if (StringUtil.isNotNull(mainForm.getCategoryId())) {
				nFlieTemplate = (KmArchivesFileTemplate) getServiceImp(request)
						.convertFormToModel(mainForm, nFlieTemplate,
								new RequestContext(request));
			}
			if(UserOperHelper.allowLogOper("fileDoc", null)){
			    String fdModelName = mainForm.getFdModelName();
			    UserOperHelper.setModelNameAndModelDesc(fdModelName);
			}
			//原逻辑
			Object bean = getBean(serviceName);
			if(bean instanceof IFileDataService) {
				//必须有归档模板，必须在addFileMainDoc前判断，否则无法回滚
				if (nFlieTemplate != null && (StringUtil.isNull(nFlieTemplate.getFdModelId()) || StringUtil.isNull(nFlieTemplate.getFdModelName()))) {
					throw new KmssException(new KmssMessage("km-archives:kmArchivesFileTemplate.fdModelIsNull"));
				}
				IFileDataService service = (IFileDataService) bean;
				service.addFileMainDoc(request, fdModelId, nFlieTemplate);
			} else {
				//解耦逻辑
				IFileDataServiceApi apiProxy = ModuleCenter.getDeclareApiProxy(IFileDataServiceApi.class, serviceName);
				if (apiProxy != null) {
					apiProxy.addFileMainDoc(request, fdModelId, ModuleCenter.enhanceBean(nFlieTemplate));
				}
			}
			if (nFlieTemplate != null && StringUtil.isNotNull(mainForm.getCategoryId())) {
				nFlieTemplate.setFdSaveApproval("true".equals(fdOldSaveApproval));
				nFlieTemplate.setFdSaveOldFile("true".equals(fdOldSaveOldFile));
				nFlieTemplate.setCategory(null);
				SysOrgPerson person=null;
				if(com.landray.kmss.util.StringUtil.isNotNull(fdOldFilePersonId)){
					person = new SysOrgPerson();
					person.setFdId(fdOldFilePersonId);
				}
				nFlieTemplate.setFdFilePerson(person);
				getServiceImp(request).update(nFlieTemplate);
			}
		} catch (Exception e) {
			messages.addError(e);
		}
		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_CLOSE).save(request);
		if (messages.hasError()) {
            return getActionForward("failure", mapping, form, request,
                    response);
        } else {
            return getActionForward("success", mapping, form, request,
                    response);
        }
	}

	/**
	 * 检查文档是否有对应的归档设置
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward checkHasTmp(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			String fdModelId = request.getParameter("fdModelId");
			if (StringUtil.isNull(fdModelId)) {
				throw new NoRecordException();
			}
			JSONObject json = new JSONObject();
			KmArchivesFileConfig config = new KmArchivesFileConfig();
			// 开启归档开关才需要检查是否有归档配置
			if ("true".equals(config.getFdStartFile())) {
				String serviceName = request.getParameter("serviceName");
				String cateName = request.getParameter("cateName");
				IBaseService service = (IBaseService) SpringBeanUtil
						.getBean(serviceName);
				IBaseModel mainModel = service.findByPrimaryKey(fdModelId);

				if ("kmAgreementApplyService".equals(serviceName)
						|| "kmAgreementLedgerService".equals(serviceName)) {
					//合同管理会有二次归档，先判断是否存在档案记录
					String fdModelName = "";
					if ("kmAgreementApplyService".equals(serviceName)) {
						fdModelName = "com.landray.kmss.km.agreement.model.KmAgreementApply";
					} else {
						fdModelName = "com.landray.kmss.km.agreement.model.KmAgreementLedger";
					}
					
					KmArchivesMain kmArchivesMain = findKmArchivesInfoByModel(fdModelId,fdModelName);
					if (kmArchivesMain == null) {
						KmArchivesFileTemplate tempalte = getServiceImp(request)
								.getFileTemplate((IBaseModel) PropertyUtils
										.getProperty(mainModel, cateName), null);
						if (tempalte != null) {
							json.put("hasTmp", "true");
						} else {
							json.put("hasTmp", "false");
						}
					} else {
						json.put("hasTmp", "true");
					}
				} else {
					KmArchivesFileTemplate tempalte = getServiceImp(request)
							.getFileTemplate((IBaseModel) PropertyUtils
									.getProperty(mainModel, cateName), null);
					if (tempalte != null) {
						json.put("hasTmp", "true");
					} else {
						json.put("hasTmp", "false");
					}
				}
				
			} else { // 不开启归档开关，可以直接归档
				json.put("hasTmp", "true");
			}
			request.setAttribute("lui-source", json);
		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			return getActionForward("lui-source", mapping, form, request,
					response);
		}
	}
	
	/**
	 * 根据业务模型查询档案信息是否存在
	 * @return
	 */
	private KmArchivesMain findKmArchivesInfoByModel(String fdModelId,String fdModelName) {
		KmArchivesMain kmArchivesMain = null;
		try {
			//判断档案信息是否存在
			IKmArchivesMainService archiveMainService = (IKmArchivesMainService) getBean("kmArchivesMainService");
			HQLInfo hqlInfo = new HQLInfo();
			StringBuilder sql = new StringBuilder();
			sql.append("kmArchivesMain.fdModelId = :fdModelId");
			sql.append(" and kmArchivesMain.fdModelName = :fdModelName");
			sql.append(" and (kmArchivesMain.docDeleteFlag is null or kmArchivesMain.docDeleteFlag = :docDeleteFlag)");
			sql.append(" and (kmArchivesMain.fdDestroyed is null or kmArchivesMain.fdDestroyed = :fdDestroyed)");
			hqlInfo.setWhereBlock(sql.toString());
			hqlInfo.setOrderBy("docCreateTime desc");
			hqlInfo.setParameter("fdModelId", fdModelId);
			hqlInfo.setParameter("fdModelName", fdModelName);
			hqlInfo.setParameter("docDeleteFlag", Integer.valueOf(0));
			hqlInfo.setParameter("fdDestroyed", Boolean.FALSE);
//			List<KmArchivesMain> archiveList = archiveMainService.findList(hqlInfo);
			Object one = archiveMainService.findFirstOne(hqlInfo);
			if(one != null){
				kmArchivesMain = (KmArchivesMain) one;
			}
//			if (!ArrayUtil.isEmpty(archiveList)) {
//				kmArchivesMain = archiveList.get(0);
//			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return kmArchivesMain;
	}
	
	
	/**
	 * 跳转进入单个文档的归档设置
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward archivesFileUserSetting(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			String fdModelId = request.getParameter("fdMainModelId");
			if (StringUtil.isNull(fdModelId)) {
				throw new NoRecordException();
			}
			KmArchivesFileConfig config = new KmArchivesFileConfig();
			// 开启归档开关才需要检查是否有归档配置
			if ("true".equals(config.getFdStartFile())) {
				String serviceName = request.getParameter("serviceName");
				String cateName = request.getParameter("cateName");
				IBaseService service = (IBaseService) SpringBeanUtil.getBean(serviceName);
				IBaseModel mainModel = service.findByPrimaryKey(fdModelId);
				List list = getServiceImp(request).getCoreModels((IBaseModel) PropertyUtils.getProperty(mainModel, cateName), null);
				if (list != null && list.size() > 0) {
					KmArchivesFileTemplate template = (KmArchivesFileTemplate) list.get(0);
					KmArchivesFileTemplateForm templateForm = new KmArchivesFileTemplateForm();
					IExtendForm rtnForm = getServiceImp(request).convertModelToForm(templateForm, template, new RequestContext(request));	
					String formName = getFormName(rtnForm, request);
					request.setAttribute(formName, rtnForm);
				}
			} 		
		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("setting", mapping, form, request, response);
		}
	}
	
}
