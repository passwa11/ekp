package com.landray.kmss.sys.portal.actions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.sys.config.xml.XmlReaderContext;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.portal.forms.SysPortalPageForm;
import com.landray.kmss.sys.portal.model.SysPortalPage;
import com.landray.kmss.sys.portal.service.ISysPortalPageService;
import com.landray.kmss.util.CriteriaUtil;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.IDGenerator;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;

import net.sf.json.JSONObject;

/**
 * 主文档 Action
 * 
 * @author
 * @version 1.0 2013-07-18
 */
public class SysPortalPageAction extends ExtendAction {
	private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());
	
	protected ISysPortalPageService sysPortalPageService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (sysPortalPageService == null) {
            sysPortalPageService = (ISysPortalPageService) getBean("sysPortalPageService");
        }
		return sysPortalPageService;
	}

	protected IBaseService getServiceImp() {
		if (sysPortalPageService == null) {
            sysPortalPageService = (ISysPortalPageService) getBean("sysPortalPageService");
        }
		return sysPortalPageService;
	}

	@Override
	protected ActionForm createNewForm(ActionMapping mapping, ActionForm form,
									   HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SysPortalPageForm xform = (SysPortalPageForm) super.createNewForm(
				mapping, form, request, response);
		xform.setDocCreatorId(UserUtil.getUser().getFdId());
		xform.setDocCreatorName(UserUtil.getUser().getFdName());
		xform.setDocCreateTime(DateUtil.convertDateToString(new Date(),
				DateUtil.TYPE_DATETIME, request.getLocale()));
		xform.setFdIcon("lui_icon_l_icon_1");
		xform.setFdTheme("fresh_elegant");
		xform.setFdType("1");
		xform.setFdUsePortal("true");
		return xform;
	}

	@Override
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-delete", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"GET".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			String id = request.getParameter("fdId");
			if (StringUtil.isNull(id)) {
                messages.addError(new NoRecordException());
            } else {
                getServiceImp(request).delete(id);
            }
		} catch (Exception e) {
			messages.addError(e);
		}

		if(!"false".equals(request.getParameter("needReturn"))){
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(
					KmssReturnPage.BUTTON_RETURN).save(request);
		}else{
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(
					KmssReturnPage.BUTTON_CLOSE).save(request);
		}
		TimeCounter.logCurrentTime("Action-delete", false, getClass());
		if (messages.hasError()) {
            return getActionForward("failure", mapping, form, request, response);
        } else {
            return getActionForward("success", mapping, form, request, response);
        }
	}

	@Override
	public ActionForward save(ActionMapping mapping, ActionForm form,
							  HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionForward af = super.save(mapping, form, request, response);
		if (af.getName().endsWith("success")) {
			KmssReturnPage returnPage = KmssReturnPage.getInstance(request);
			returnPage.addButton("button.back",
					"sysPortalPage.do?method=edit&fdId="
							+ ((SysPortalPageForm) form).getFdId(), false);
		}
		return af;
	}

	@Override
	public ActionForward update(ActionMapping mapping, ActionForm form,
								HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionForward af = super.update(mapping, form, request, response);
		if (af.getName().endsWith("success")) {
			KmssReturnPage returnPage = KmssReturnPage.getInstance(request);
			 List<List<String>> buttons = returnPage.getButtons();
			 for (int i = 0; i < buttons.size(); i++) {
					List<String> ele = buttons.get(i);
					if (ele.contains("button.back")){
						buttons.remove(i);
						break;
					}
			 }
			
			/**
			 * 更新保存返回按钮能正确回退路径
			 * @author 吴进 by 20191113
			 */
			String fdAnonymous = request.getParameter("fdAnonymous");
			if ("0".equals(fdAnonymous)) {
				returnPage.addButton("button.back",
					"sysPortalPage.do?method=edit&fdId="
							+ ((SysPortalPageForm) form).getFdId() + "&fdAnonymous=0", false);
			} else if ("1".equals(fdAnonymous)) {
				returnPage.addButton("button.back",
					"sysPortalPage.do?method=editAnonymous&fdId="
							+ ((SysPortalPageForm) form).getFdId() + "&fdAnonymous=1", false);
			}
		}
		return af;
	}

	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request,
										 HQLInfo hqlInfo) throws Exception {
		String where = " 1=1 ";
		if (StringUtil.isNotNull(request.getParameter("q.keyword"))) {
			where += " and sysPortalPage.fdName like :fdName ";
			hqlInfo.setParameter("fdName", "%"
					+ request.getParameter("q.keyword") + "%");
		}
		hqlInfo.setWhereBlock(where);
		CriteriaValue cv = new CriteriaValue(request);
		CriteriaUtil.buildHql(cv, hqlInfo, SysPortalPage.class);
		if (StringUtil.isNotNull(request.getParameter("config"))) {
		  hqlInfo.setAuthCheckType(SysAuthConstant.AUTH_CHECK_EDITOR);
		}
		hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
	}

	@Override
	public ActionForward data(ActionMapping mapping, ActionForm form,
							  HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.list(mapping, form, request, response);
		return getActionForward("data", mapping, form, request, response);
	}
	
	/**
	 * 上传页面内容区背景图片
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward uploadBackgroundImage(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject resultObj = new JSONObject();
		MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) request;
		MultipartFile multipartFile = mRequest.getFile("imageFile");  // 获取图片文件
		String oriName = multipartFile.getOriginalFilename(); // 原始文件名称
		InputStream input = multipartFile.getInputStream();
		String relativePath = "/" + XmlReaderContext.UIEXT + "/portal/page/backgroundImage"; // 图片文件存储相对目录
		File file = new File(ResourceUtil.KMSS_RESOURCE_PATH + relativePath);
		if (!file.exists()) {
			file.mkdirs();
		}
		boolean isOk = false;
		String ext = FilenameUtils.getExtension(oriName);
		String[] exts = new String[] { "png", "gif", "jpg", "jpeg" };
		for (int j = 0; j < exts.length; j++) {
			if (exts[j].equalsIgnoreCase(ext)) {
				isOk = true;
				break;
			}
		}
		if (isOk == false) {
			throw new Exception("文件类型错误，只能是jpg,jpeg,gif,png格式");
		}
		// 重命名上传的图片文件名称
		String newFileName = IDGenerator.generateID() + "." + ext;
		file = new File(ResourceUtil.KMSS_RESOURCE_PATH + relativePath +"/" + newFileName);
		FileOutputStream output = null;
		try {
			file.createNewFile();
			output = new FileOutputStream(file);
			IOUtils.copy(input, output);
			resultObj.put("imagePath", relativePath +"/" + newFileName);
		} catch (IOException e) {
			throw e;
		} finally {
			if(input!=null){
				input.close();
			}
			if(output!=null){
				output.close();
			}
		}
		
		request.setAttribute("lui-source",resultObj);
		return getActionForward("lui-source", mapping, form, request, response);
	}


	/****************** 匿名页面 Start @author 吴进 by 20191110 *********************************/
	
	/**
	 * 因为兼容老数据，匿名字段为空需要特殊加0处理
	 * @author 吴进 by 2019120
	 * 
	 * 打开列表页面。<br>
	 * 该操作一般以HTTP的GET方式触发。
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回list页面，否则返回failure页面
	 * @throws Exception
	 */
	public ActionForward getPortalPageList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-list", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String s_pageno = request.getParameter("pageno");
			String s_rowsize = request.getParameter("rowsize");
			String orderby = request.getParameter("orderby");
			String ordertype = request.getParameter("ordertype");
			boolean isReserve = false;
			if (ordertype != null && "down".equalsIgnoreCase(ordertype)) {
				isReserve = true;
			}
			int pageno = 0;
			int rowsize = SysConfigParameters.getRowSize();
			if (s_pageno != null && s_pageno.length() > 0 && Integer.parseInt(s_pageno) > 0) {
				pageno = Integer.parseInt(s_pageno);
			}
			if (s_rowsize != null && s_rowsize.length() > 0 && Integer.parseInt(s_rowsize) > 0) {
				rowsize = Integer.parseInt(s_rowsize);
			}

			// 按多语言字段排序
			if (StringUtil.isNotNull(orderby) && form instanceof IExtendForm) {
				Class<?> modelClass = ((IExtendForm) form).getModelClass();
				if (modelClass != null) {
					String langFieldName = SysLangUtil
							.getLangFieldName(modelClass.getName(), orderby);
					if (StringUtil.isNotNull(langFieldName)) {
						orderby = langFieldName;
					}
				}
			}
			if (isReserve) {
                orderby += " desc";
            }
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setOrderBy(orderby);
			hqlInfo.setPageNo(pageno);
			hqlInfo.setRowSize(rowsize);
			changeFindPageHQLInfo(request, hqlInfo);
			Page page = getServiceImp(request).findPage(hqlInfo);
			if (page != null && page.getList() != null && !page.getList().isEmpty()) {
				List list = page.getList();
				for (int i = 0, n = list.size(); i < n; i++) {
					Object object = list.get(i);
					if (object instanceof SysPortalPage) {
						SysPortalPage sysPortalPage = (SysPortalPage) object;
						Boolean anonymous = sysPortalPage.getFdAnonymous();
						sysPortalPage.setFdAnonymous((null!=anonymous) ? anonymous : Boolean.FALSE);
					}
				}
			}
			UserOperHelper.logFindAll(page.getList(), getServiceImp(request).getModelName());
			request.setAttribute("queryPage", page);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-list", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("list", mapping, form, request, response);
		}
	}
	
	/**
	 * 新建匿名页面
	 * 
	 * @author 吴进 by 20191107
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回anonymous页面，否则返回failure页面
	 * @throws Exception
	 */
	public ActionForward addAnonymous(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-add", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			ActionForm newForm = createNewForm(mapping, form, request,
					response);
			if (newForm != form) {
                request.setAttribute(getFormName(newForm, request), newForm);
            }
		} catch (Exception e) {
			messages.addError(e);
			logger.error(e.getMessage(), e);
		}

		TimeCounter.logCurrentTime("Action-add", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			return getActionForward("editAnonymous", mapping, form, request,
					response);
		}
	}

	/**
	 * 编辑匿名页面
	 * 
	 * @author 吴进 by 20191107
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回edit页面，否则返回failure页面
	 * @throws Exception
	 */
	public ActionForward editAnonymous(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-edit", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			loadActionForm(mapping, form, request, response);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-edit", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			return getActionForward("editAnonymous", mapping, form, request,
					response);
		}
	}
	
	/****************** 匿名页面 End @author 吴进 by 20191110 *********************************/
}
