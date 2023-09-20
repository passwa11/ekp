package com.landray.kmss.sys.portal.actions;

import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.actions.DataAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.sys.portal.model.SysPortalTopic;
import com.landray.kmss.sys.portal.service.ISysPortalTopicService;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.sunbor.web.tag.Page;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/** 
* @author 	陈经纬 
* @date 	2017年8月8日 上午9:58:12  
*/
public class SysPortalTopicPortletAction  extends DataAction{

	protected ISysPortalTopicService sysPortalTopicService;
	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (sysPortalTopicService == null) {
            sysPortalTopicService = (ISysPortalTopicService) getBean("sysPortalTopicService");
        }
		return sysPortalTopicService;
	}

	@Override
	protected String getParentProperty() {
		return null;
	}

	@Override
	protected IBaseService getCategoryServiceImp(HttpServletRequest request) {
		return null;
	}
	
	public ActionForward getPortalTopic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-getPortalTopic", true, getClass());
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
				String whereBlock = " sysPortalTopic.fdId in (:fdIds)";
				hqlInfo.setParameter("fdIds",  idList);
				hqlInfo.setWhereBlock(whereBlock);
			}
			List introList = getServiceImp(request).findPage(hqlInfo).getList();
			JSONArray rtnArray = this.getIntroArray(request, introList);
			request.setAttribute("lui-source", rtnArray);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-getPortalTopic", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("lui-failure", mapping, form, request, response);
		} else {
			return getActionForward("lui-source", mapping, form, request, response);
		}
	}
	
	
	
	protected JSONArray getIntroArray(HttpServletRequest request, List introList)
			throws Exception{
			JSONArray rtnArray = new JSONArray();
			if(introList != null) {
				for(int i = 0; i < introList.size(); i++) {
					SysPortalTopic  s = (SysPortalTopic)introList.get(i);
					JSONObject json = new JSONObject();
					json.put("text", s.getFdName());
					json.put("image", getImgUrl(s, request));
					json.put("href", s.getFdTopUrl());
					rtnArray.add(json);
				}
			}
			return rtnArray;
		}
		
		public  String getImgUrl(SysPortalTopic portalTopic,
				HttpServletRequest request) throws Exception {
			// 默认图
			String imgAttUrl =  "/sys/portal/sys_portal_topic/resource/image/default.jpg";

			if (portalTopic != null) {
				String fdId = portalTopic.getFdId();
				SysAttMain imgAttMain = null;
				ISysAttMainCoreInnerService sysAttMainCoreInnerService = (ISysAttMainCoreInnerService) SpringBeanUtil
						.getBean("sysAttMainService");
				List<SysAttMain> attMainList = sysAttMainCoreInnerService
						.findByModelKey(
								"com.landray.kmss.sys.portal.model.SysPortalTopic",
								fdId, "sysPortalTopic_fdKey");
				// 如果上传了图片
				if (attMainList.size() > 0) {
					imgAttMain = attMainList.get(0);
					imgAttUrl =  "/sys/attachment/sys_att_main/sysAttMain.do?method=download&fdId="
							+ imgAttMain.getFdId();
				}
				//如果fdimg不为空，则是素材库的图片
				if(StringUtil.isNotNull(portalTopic.getFdImg())){
					imgAttUrl=portalTopic.getFdImg();
				}

			}
			return imgAttUrl;
		}
		
		
		@Override
		public ActionForward list(ActionMapping mapping, ActionForm form,
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
				String fdAnonymous = request.getParameter("fdAnonymous");
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
					whereBlock += "(sysPortalTopic.fdName like:_keyword)";
					hqlInfo.setParameter("_keyword","%" + keyword + "%");
					if (StringUtil.isNotNull(fdAnonymous)) {
						whereBlock += " AND sysPortalTopic.fdAnonymous = :fdAnonymous ";
						hqlInfo.setParameter("fdAnonymous", "1".equals(fdAnonymous));
					}
					hqlInfo.setWhereBlock(whereBlock);
				}else{
					if (StringUtil.isNotNull(fdAnonymous)) {
						hqlInfo.setWhereBlock(" sysPortalTopic.fdAnonymous = :fdAnonymous ");
						hqlInfo.setParameter("fdAnonymous", "1".equals(fdAnonymous));
					}
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
				return getActionForward("data", mapping, form, request, response);
			}
		}

}
