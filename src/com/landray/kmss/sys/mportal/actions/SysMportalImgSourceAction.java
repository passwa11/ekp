package com.landray.kmss.sys.mportal.actions;

import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import org.json.simple.JSONObject;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.sys.mportal.forms.SysMportalImgSourceForm;
import com.landray.kmss.sys.mportal.model.SysMportalImgSource;
import com.landray.kmss.sys.mportal.service.ISysMportalImgSourceService;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.CriteriaUtil;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.sunbor.web.tag.Page;

import net.sf.json.JSONArray;


/*
 * 
 * @author zhuhq
 * @version 1.0 2017-01-13
 */
public class SysMportalImgSourceAction extends ExtendAction {
	protected ISysMportalImgSourceService sysMportalImgSourceService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if(sysMportalImgSourceService == null) {
            sysMportalImgSourceService = (ISysMportalImgSourceService)getBean("sysMportalImgSourceService");
        }
		return sysMportalImgSourceService;
	}
	
	@Override
	protected ActionForm createNewForm(ActionMapping mapping, ActionForm form,
									   HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SysMportalImgSourceForm rtnForm = (SysMportalImgSourceForm)super.createNewForm(mapping, form, request, response);
		return rtnForm;
	}
	
	@Override
	public ActionForward update(ActionMapping mapping, ActionForm form,
								HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-update", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			getServiceImp(request).update((IExtendForm) form,
					new RequestContext(request));
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-update", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(
					request);
			return getActionForward("edit", mapping, form, request, response);
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			SysMportalImgSourceForm sysMportalImgSourceForm = (SysMportalImgSourceForm) form;
			KmssReturnPage.getInstance(request).addMessages(messages)
				.addButton(
						"button.back","/sys/mportal/sys_mportal_imgsource/sysMportalImgSource.do?method=edit&fdId="+sysMportalImgSourceForm.getFdId(), false).save(request);
			
			return getActionForward("success", mapping, form, request, response);
		}
	}
	
	
	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request,
			HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		CriteriaValue cv = new CriteriaValue(request);
		CriteriaUtil.buildHql(cv, hqlInfo, SysMportalImgSource.class);
		// 列表页面增加可维护者的权限过滤
		hqlInfo.setCheckParam(SysAuthConstant.CheckType.AuthCheck,
				SysAuthConstant.AuthCheck.SYS_EDITOR);
	}
	
	public ActionForward getImg(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-getImg", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String fdIds = request.getParameter("fdIds");
			String s_rowsize = request.getParameter("rowsize");
			String orderby = request.getParameter("orderby");
			String ordertype = request.getParameter("ordertype");
			boolean isReserve = false;
			if (ordertype != null && "down".equalsIgnoreCase(ordertype)) {
				isReserve = true;
			}
			int rowsize = SysConfigParameters.getRowSize();
			if (s_rowsize != null && s_rowsize.length() > 0) {
				rowsize = Integer.parseInt(s_rowsize);
			}
			if (isReserve) {
                orderby += " desc";
            }
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setOrderBy(orderby);
			hqlInfo.setRowSize(rowsize);
			if(StringUtil.isNotNull(fdIds)) {
				String[] ids = fdIds.split(",");
				List<String> idList = ArrayUtil.convertArrayToList(ids);
				String whereBlock = " sysMportalImgSource.fdId in (:fdIds)";
				hqlInfo.setParameter("fdIds",  idList);
				hqlInfo.setWhereBlock(whereBlock);
			}
			List introList = getServiceImp(request).findPage(hqlInfo).getList();
			JSONArray rtnArray = this.getIntroArray(request, introList,fdIds);
			request.setAttribute("lui-source", rtnArray);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-getImg", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("lui-failure", mapping, form, request, response);
		} else {
			return getActionForward("lui-source", mapping, form, request, response);
		}
	}
	
	protected JSONArray getIntroArray(HttpServletRequest request, List introList,String ids)
		throws Exception{
		JSONArray rtnArray = new JSONArray();
		if(introList != null) {
			String[] idArr = ids.split(",");
			for(int j=0;j<idArr.length;j++){
				for(int i = 0; i < introList.size(); i++) {
					SysMportalImgSource  s = (SysMportalImgSource)introList.get(i);
					if(idArr[j].equals(s.getFdId())){
						JSONObject json = new JSONObject();
						json.put("text", s.getFdSubject());
						json.put("image", getImgUrl(s, request));
						json.put("href", s.getFdUrl());
						rtnArray.add(json);
					}
				}
			}
		}
		return rtnArray;
	}
	
	public  String getImgUrl(SysMportalImgSource imsSource,
			HttpServletRequest request) throws Exception {
		// 默认图
		String imgAttUrl =  "/kms/multidoc/resource/img/default.jpg";

		if (imsSource != null) {
			String fdId = imsSource.getFdId();
			SysAttMain imgAttMain = null;
			ISysAttMainCoreInnerService sysAttMainCoreInnerService = (ISysAttMainCoreInnerService) SpringBeanUtil
					.getBean("sysAttMainService");
			List<SysAttMain> attMainList = sysAttMainCoreInnerService
					.findByModelKey(
							"com.landray.kmss.sys.mportal.model.SysMportalImgSource",
							fdId, "SysMportalImgSource_fdKey");
			// 如果上传了图片
			if (attMainList.size() > 0) {
				imgAttMain = attMainList.get(0);
				imgAttUrl =  "/sys/attachment/sys_att_main/sysAttMain.do?method=view&picthumb=big&fdId="
						+ imgAttMain.getFdId();
			}

		}
		return imgAttUrl;
	}
	
	public ActionForward listdata(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-list", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String s_pageno = request.getParameter("pageno");
			String s_rowsize = request.getParameter("rowsize");
			String orderby = request.getParameter("orderby");
			String ordertype = request.getParameter("ordertype");
			String key = request.getParameter("q.key");
			boolean isReserve = false;
			if (ordertype != null && "down".equalsIgnoreCase(ordertype)) {
				isReserve = true;
			}
			int pageno = 0;
			int rowsize = SysConfigParameters.getRowSize();
			if (s_pageno != null && s_pageno.length() > 0) {
				pageno = Integer.parseInt(s_pageno);
			}
			if (s_rowsize != null && s_rowsize.length() > 0) {
				rowsize = Integer.parseInt(s_rowsize);
			}
			if (isReserve) {
                orderby += " desc";
            }
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setOrderBy(orderby);
			hqlInfo.setPageNo(pageno);
			hqlInfo.setRowSize(rowsize);
			changeFindPageHQLInfo(request, hqlInfo);
			String whereBlock = hqlInfo.getWhereBlock();
			if(StringUtil.isNotNull(key)) {
				String keyword = key.trim();
				if (StringUtil.isNull(whereBlock)) {
                    whereBlock = "";
                } else {
                    whereBlock = "(" + whereBlock + ") and ";
                }
				keyword = URLDecoder.decode(keyword, "utf-8");
				whereBlock += "(sysMportalImgSource.fdSubject like:_keyword)";
				hqlInfo.setParameter("_keyword","%" + keyword + "%");
				hqlInfo.setWhereBlock(whereBlock);
			}
			Page page = getServiceImp(request).findPage(hqlInfo);
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
			return getActionForward("listdata", mapping, form, request, response);
		}
	}
	
	@Override
	public ActionForward list(ActionMapping mapping, ActionForm form,
							  HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-list", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			Page page = null;
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
			if (s_pageno != null && s_pageno.length() > 0) {
				pageno = Integer.parseInt(s_pageno);
			}
			if (s_rowsize != null && s_rowsize.length() > 0) {
				rowsize = Integer.parseInt(s_rowsize);
			}
			if (isReserve) {
                orderby += " desc";
            }
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setOrderBy(orderby);
			hqlInfo.setPageNo(pageno);
			hqlInfo.setRowSize(rowsize);
			changeFindPageHQLInfo(request, hqlInfo);
			page = getServiceImp(request).findPage(hqlInfo);
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
	
}

