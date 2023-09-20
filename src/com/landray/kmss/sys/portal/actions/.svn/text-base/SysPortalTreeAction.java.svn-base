package com.landray.kmss.sys.portal.actions;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.sys.authentication.user.KMSSUser;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.sys.portal.forms.SysPortalTreeForm;
import com.landray.kmss.sys.portal.model.SysPortalTree;
import com.landray.kmss.sys.portal.service.ISysPortalTreeService;
import com.landray.kmss.util.*;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Date;

/**
 * 多级数菜单 Action
 * 
 * @author
 * @version 1.0 2013-09-23
 */
public class SysPortalTreeAction extends ExtendAction {
	private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());
	
	protected ISysPortalTreeService sysPortalTreeService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (sysPortalTreeService == null) {
            sysPortalTreeService = (ISysPortalTreeService) getBean("sysPortalTreeService");
        }
		return sysPortalTreeService;
	}

	private Document buildDocument(String xml)
			throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		DocumentBuilder parser = dbf.newDocumentBuilder();
		return parser.parse(xml);
	}

	public ActionForward portlet(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String fdId = request.getParameter("fdId");
		String fdType = request.getParameter("fdType");
		SysPortalTree tree = null;
		if(StringUtil.isNotNull(fdId)){
			tree = (SysPortalTree) getServiceImp(request).findByPrimaryKey(fdId);
		}else{
			if(StringUtil.isNotNull(fdType)){
				HQLInfo info = new HQLInfo();
				info.setWhereBlock(" fdType =:fdType ");
				info.setParameter("fdType", fdType);
				Object obj = getServiceImp(request).findFirstOne(info);
				if (obj != null) {
					tree = (SysPortalTree)obj;
				}
			}
		}
		JSONArray array = new JSONArray();
		if(null!=tree){
			array = JSONArray.fromObject(tree.getFdContent());
		}
		KMSSUser kmssUser = UserUtil.getKMSSUser();
		String key = kmssUser.getLocale().getLanguage() + "-" + kmssUser.getLocale().getCountry();
		
		array = getLangJson(array ,key);
		
		//在这里统一处理系统导航的多语言转换
		
		request.setAttribute("lui-source", array);
		return getActionForward("lui-source", mapping, form, request, response);
	}
	
	//递归获得当前语言的系统导航text
	private JSONArray getLangJson(JSONArray array , String key){
		
		for(int i=0;i<array.size();i++){
			JSONObject obj = (JSONObject)array.get(i);
			if(obj.containsKey(key)){
				if(StringUtil.isNotNull(obj.getString(key))){
					obj.put("text", obj.getString(key));
				}
			}
			
			if(obj.containsKey("children")){
				JSONArray childArray = obj.getJSONArray("children");
				getLangJson(childArray,key);
			}
		}
		
		return array;
	}
	
	public ActionForward select(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-list", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String s_pageno = request.getParameter("pageno");
			String s_rowsize = request.getParameter("rowsize");
			String orderby = request.getParameter("orderby");
			String ordertype = request.getParameter("ordertype");
			String docSubject = request.getParameter("q.docSubject");
			HQLInfo hqlInfo = new HQLInfo();
			if(StringUtil.isNotNull(docSubject)){
				String where = "sysPortalTree.fdName like:docSubject";
				hqlInfo.setWhereBlock(where);
				hqlInfo.setParameter("docSubject", "%" + docSubject.trim() + "%");
			}
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
			hqlInfo.setOrderBy(orderby);
			hqlInfo.setPageNo(pageno);
			hqlInfo.setRowSize(rowsize);
			changeFindPageHQLInfo(request, hqlInfo);
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
			return getActionForward("select", mapping, form, request, response);
		}
	}

	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request,
										 HQLInfo hqlInfo) throws Exception {
		String where = hqlInfo.getWhereBlock();
		if (StringUtil.isNull(where)) {
			where = " 1=1 ";
		}
		String type = request.getParameter("fdType");
		if (StringUtil.isNotNull(type)) {
			where += " and sysPortalTree.fdType = :fdType ";
			hqlInfo.setParameter("fdType", type);
		}
		
		
		String fdAnonymous = request.getParameter("fdAnonymous");
		if (StringUtil.isNotNull(fdAnonymous)) {
			where += " AND sysPortalTree.fdAnonymous = :fdAnonymous ";
			hqlInfo.setParameter("fdAnonymous", "1".equals(fdAnonymous));
		}
		
		
		hqlInfo.setWhereBlock(where);
		CriteriaValue cv = new CriteriaValue(request);
		CriteriaUtil.buildHql(cv, hqlInfo, SysPortalTree.class);
		if (StringUtil.isNotNull(request.getParameter("config"))) {
		  hqlInfo.setAuthCheckType(SysAuthConstant.AUTH_CHECK_EDITOR);
		}
	}

	@Override
	protected ActionForm createNewForm(ActionMapping mapping, ActionForm form,
									   HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SysPortalTreeForm xform = (SysPortalTreeForm) super.createNewForm(
				mapping, form, request, response);
		xform.setDocCreatorId(UserUtil.getUser().getFdId());
		xform.setDocCreatorName(UserUtil.getUser().getFdName());
		xform.setDocCreateTime(DateUtil.convertDateToString(new Date(),DateUtil.TYPE_DATETIME, request.getLocale()));
		xform.setFdType("1");
		return xform;
	}
	
	
	
	

}
