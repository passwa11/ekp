package com.landray.kmss.third.pda.actions;

import java.io.File;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.config.loader.ConfigLocationsUtil;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.third.pda.constant.PdaModuleConfigConstant;
import com.landray.kmss.third.pda.model.PdaModuleConfigMain;
import com.landray.kmss.third.pda.model.PdaVersionConfig;
import com.landray.kmss.third.pda.service.IPdaModuleConfigMainService;
import com.landray.kmss.third.pda.util.PdaFlagUtil;
import com.landray.kmss.third.pda.util.PdaModuleConfigUtil;
import com.landray.kmss.util.CriteriaUtil;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

/**
 * 模块配置表 Action
 * 
 * @author zhuangwl
 * @version 1.0 2011-03-03
 */
public class PdaModuleConfigMainAction extends ExtendAction {
	protected IPdaModuleConfigMainService pdaModuleConfigMainService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (pdaModuleConfigMainService == null) {
            pdaModuleConfigMainService = (IPdaModuleConfigMainService) getBean("pdaModuleConfigMainService");
        }
		return pdaModuleConfigMainService;
	}

	/**
	 * 打开选择模块图标页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward selectIcon(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-selectIcon", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String type = request.getParameter("type");
			String contextRealPath = ConfigLocationsUtil.getWebContentPath();
			contextRealPath = contextRealPath.replaceAll("\\\\", "/");
			String iconDir = "";
			if ("module".equals(type)) {
                iconDir = PdaModuleConfigUtil.ICON_DIR;
            }
			// else if ("label".equals(type))
			// iconDir = PdaModuleConfigUtil.LABEL_ICON_DIR;
			Collection<File> files = PdaModuleConfigUtil.getListFiles(
					PdaModuleConfigUtil.getIconDir(contextRealPath, iconDir),
					PdaModuleConfigUtil.extensions);
			List<String> iconUrls = new ArrayList<String>();
			for (File file : files) {
				if (file.getName().indexOf("module_") >= 0) {
                    iconUrls.add(iconDir + "/" + file.getName());
                }
			}
			request.setAttribute("iconUrls", iconUrls);
			request.setAttribute("type", type);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-selectIcon", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("selectIcon", mapping, form, request,
					response);
		}
	}

	public ActionForward open(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-open", true, getClass());
		KmssMessages messages = new KmssMessages();
		String id = request.getParameter("fdId");
		if (!StringUtil.isNull(id)) {
			PdaModuleConfigMain model = (PdaModuleConfigMain) getServiceImp(request).findByPrimaryKey(id, null, true);
			UserOperHelper.logFind(model);
			if (model != null) {
				String menutype = model.getFdSubMenuType();
				if (PdaModuleConfigConstant.PDA_MENUS_MODULE.equalsIgnoreCase(menutype)) {
					// 子菜单为module
					List list = model.getFdSubModuleList();
					List rtnList = null;
					if (list != null && list.size() > 0) {
						rtnList = new ArrayList();
						for (Iterator iterator = list.iterator(); iterator.hasNext();) {
							PdaModuleConfigMain object = (PdaModuleConfigMain) iterator.next();
							Map map = new HashMap();
							map.put("fdId", object.getFdId());
							map.put("fdName", object.getFdName());
							map.put("fdIconUrl", PdaFlagUtil.formatUrl(request,object.getFdIconUrl()));
							rtnList.add(map);
						}
					}
					request.setAttribute("moduleList", rtnList);
					request.setAttribute("isModule", true);
					return mapping.findForward("open");
				} else if (PdaModuleConfigConstant.PDA_MENUS_DOC.equalsIgnoreCase(menutype)) {
					// 子菜单为doc
					request.setAttribute("curModel",model);
					String url = model.getFdSubDocLink();
					if(model.getFdSubDocLink().indexOf("?")>-1){
						url = url + "&moduleName=" + URLEncoder.encode(model.getFdName(), "UTF-8");
					}else{
						url = url + "?moduleName=" + URLEncoder.encode(model.getFdName(), "UTF-8");
					}
					request.setAttribute("redirectto", url);
					return mapping.findForward("redirect");
				} else if(PdaModuleConfigConstant.PDA_MENUS_LISTTAB.equalsIgnoreCase(menutype)){
					if("1".equals(model.getFdLinkerType())){//外部链接
						request.setAttribute("redirectto",  model.getFdEkpModuleUrl());
						return mapping.findForward("redirect");	
					}else{//内部链接
						if(PdaFlagUtil.checkClientIsPdaApp(request)){//为客户端访问时
							String redirect = PdaModuleConfigConstant.PDA_EKP_Module_CONFIG_URL + "&fdId=" + id;
							request.setAttribute("redirectto",  redirect);
							return mapping.findForward("redirect");
						}
						return view(mapping, form, request, response);
					}
				}else{
					if(PdaFlagUtil.checkClientIsPdaApp(request)){
						String redirect = PdaModuleConfigConstant.PDA_EKP_Module_CONFIG_URL + "&fdId=" + id;
						request.setAttribute("redirectto",  redirect);
						return mapping.findForward("redirect");
					}
					return view(mapping, form, request, response);
				}

			}
		}
		TimeCounter.logCurrentTime("Action-open", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		}
		return null;
	}

	/**
	 * 更新版本信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateIconVersion(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			PdaVersionConfig version = new PdaVersionConfig();
			version.setIconVersion(df.format(new Date()));
			version.save();
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-selectIcon", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage
					.getInstance(request)
					.addMessages(messages)
					.addButton(
							"button.back",
							"/third/pda/pda_module_config_main/index.jsp?s_path=%E5%BA%94%E7%94%A8%E9%85%8D%E7%BD%AE",
							false).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			KmssReturnPage
					.getInstance(request)
					.addMessages(messages)
					.addButton(
							"button.back",
							"/third/pda/pda_module_config_main/index.jsp?s_path=%E5%BA%94%E7%94%A8%E9%85%8D%E7%BD%AE",
							false).save(request);
			return getActionForward("success", mapping, form, request, response);
		}
	}

	/**
	 * 启用/禁用
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateStatus(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-updateStatus", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			String[] ids = request.getParameterValues("List_Selected");
			String status = request.getParameter("fdEnabled");
			String modelName = getServiceImp(request).getModelName();
			if (UserOperHelper.allowLogOper("updateStatus", modelName)) {
				for (String id : ids) {
					PdaModuleConfigMain main = (PdaModuleConfigMain) getServiceImp(
							request).findByPrimaryKey(id);
					UserOperContentHelper
							.putUpdate(id, main.getFdName(), modelName)
							.putSimple("fdStatus", main.getFdStatus(), status);
				}
			}
			if (ids != null && StringUtil.isNotNull(status)) {
				IPdaModuleConfigMainService mainService = (IPdaModuleConfigMainService) getServiceImp(request);
				mainService.updateStatus(ids, status);
			}
		} catch (Exception e) {
			messages.addError(e);
		}

		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_RETURN).save(request);
		TimeCounter.logCurrentTime("Action-updateStatus", false, getClass());
		if (messages.hasError()) {
            return getActionForward("failure", mapping, form, request, response);
        } else {
            return getActionForward("success", mapping, form, request, response);
        }
	}

	@Override
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionForward forward = super.list(mapping, form, request, response);
		String contentType = request.getParameter("contentType");
		if (!"failure".equals(forward.getName()) && "json".equals(contentType)) {
			return getActionForward("data", mapping, form, request, response);
		}
		return forward;
	}

	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request,
			HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		CriteriaValue cv = new CriteriaValue(request);
		CriteriaUtil.buildHql(cv, hqlInfo, PdaModuleConfigMain.class);

		String fdModuleCateId = cv.poll("fdModuleCate");
		if (StringUtil.isNotNull(fdModuleCateId)) {
			hqlInfo.setWhereBlock(StringUtil.linkString(
					hqlInfo.getWhereBlock(), " and ",
					" pdaModuleConfigMain.fdModuleCate.fdId =  :fdModuleCateId "));
			hqlInfo.setParameter("fdModuleCateId", fdModuleCateId);
		}

	}

}
