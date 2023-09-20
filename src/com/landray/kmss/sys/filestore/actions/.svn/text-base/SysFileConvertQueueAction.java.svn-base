package com.landray.kmss.sys.filestore.actions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.appconfig.model.BaseAppconfigCache;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.sys.filestore.model.SysFileConvertConstant;
import com.landray.kmss.sys.filestore.model.SysFileConvertQueue;
import com.landray.kmss.sys.filestore.scheduler.third.dianju.util.ConfigUtil;
import com.landray.kmss.sys.filestore.scheduler.third.foxit.util.FoxitUtil;
import com.landray.kmss.sys.filestore.scheduler.third.suwell.Suwell;
import com.landray.kmss.sys.filestore.scheduler.third.wps.WpsUtil;
import com.landray.kmss.sys.filestore.service.ISysFileConvertConfigService;
import com.landray.kmss.sys.filestore.service.ISysFileConvertQueueService;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.log.xml.SysLogOperXml;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.sso.client.util.StringUtil;
import com.sunbor.web.tag.Page;

public class SysFileConvertQueueAction extends ExtendAction {

	protected ISysFileConvertQueueService sysFileConvertQueueService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (sysFileConvertQueueService == null) {
			sysFileConvertQueueService = (ISysFileConvertQueueService) SpringBeanUtil
					.getBean("sysFileConvertQueueService");
		}
		return sysFileConvertQueueService;
	}


	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		String where = hqlInfo.getWhereBlock();
		if (StringUtil.isNull(where)) {
			where = " 1=1 ";
		}
		String clientId = request.getParameter("clientId");
		if (StringUtil.isNotNull(clientId)) {
			where += "and fdClientId = :fdClientId";
			hqlInfo.setParameter("fdClientId", clientId);
		}
		CriteriaValue cv = new CriteriaValue(request);
		String[] ss = null;
		ss = cv.get("fdConvertStatus");
		HQLInfo statusHql = getStatusWhereHql(ss);
		where += statusHql.getWhereBlock();
		hqlInfo.setParameter(statusHql.getParameterList());

		ss = cv.get("fdConvertNumber");
		if (ss != null && ss.length > 0) {
			if(StringUtil.isNotNull(ss[0]) && StringUtil.isNotNull(ss[1])) {
				where += " and fdConvertNumber between  :fdConvertNumber_1 and  :fdConvertNumber_2 ";
				hqlInfo.setParameter("fdConvertNumber_1", Integer.parseInt(ss[0]));
				hqlInfo.setParameter("fdConvertNumber_2", Integer.parseInt(ss[1]));
			}else if(StringUtil.isNull(ss[1])) {
				where += " and fdConvertNumber >= :fdConvertNumber_1 ";
				hqlInfo.setParameter("fdConvertNumber_1", Integer.parseInt(ss[0]));		
			}else if(StringUtil.isNull(ss[0])) {
				where += " and fdConvertNumber <= :fdConvertNumber_2 ";
				hqlInfo.setParameter("fdConvertNumber_2", Integer.parseInt(ss[1]));					
			}
		}

		ss = cv.get("fdConverterKey");
		if (ss != null && ss.length > 0) {
			where += " and fdConverterKey like :fdConverterKey ";
			hqlInfo.setParameter("fdConverterKey", "%" + ss[0] + "%");
		} else {
			if (!existConverter()) { //过滤toOFD的队列
				where += " and fdConverterKey !=:fdConverterKey ";
				hqlInfo.setParameter("fdConverterKey", "toOFD");
			}
		}

		ss = cv.get("fdFileName");
		if (ss != null && ss.length > 0) {
			where += " and fdFileName like :fdFileName ";
			hqlInfo.setParameter("fdFileName", "%" + ss[0] + "%");
		}

		ss = cv.get("fdAttMainId");
		if (ss != null && ss.length > 0) {
			where += " and fdAttMainId = :fdAttMainId ";
			hqlInfo.setParameter("fdAttMainId", ss[0]);
		}

		ss = cv.get("fdAttModelId");
		if (ss != null && ss.length > 0) {
			where += " and fdAttModelId = :fdAttModelId ";
			hqlInfo.setParameter("fdAttModelId", ss[0]);
		}

		hqlInfo.setOrderBy(" fdStatusTime desc");

		hqlInfo.setWhereBlock(where);

	}

	/**
	 * 判断是否开启了Linu版本的WPS和数科, 及是否开启了转OFD的服务
	 * @return
	 * @throws Exception
	 */
	public boolean existConverter() throws Exception {
	
		Map<String, String> dataMap = BaseAppconfigCache.getCacheData("com.landray.kmss.sys.filestore.model.SysFileConvertUrlConfig");
		String suwellConvretEnabled = "";
		if (!dataMap.isEmpty()) {
			suwellConvretEnabled = (String) dataMap.get("suwellConvretEnabled");
		}
		ISysFileConvertConfigService  sysFileConvertConfigService = (ISysFileConvertConfigService) SpringBeanUtil.getBean("sysFileConvertConfigService");
		String isConverterWPS = sysFileConvertConfigService.findAppConfigValue("fdKey ='attconvert.converter.type.wps'", "false");
		String isConverterSKOFD = sysFileConvertConfigService.findAppConfigValue("fdKey ='attconvert.converter.type.skofd'", "false");
		String isConverterWPSCenterOFD = sysFileConvertConfigService.findAppConfigValue("fdKey ='attconvert.converter.type.wpsCenter'", "false");
		String thirdDianjuEnabled = ConfigUtil.configValue("thirdDianjuEnabled");
		String isConverterDianju = sysFileConvertConfigService.findAppConfigValue("fdKey ='attconvert.converter.type.dianju'", "false");
		String thirdFoxitEnabled = FoxitUtil.configValue("thirdFoxitEnabled");
		String isConverterFoxit = sysFileConvertConfigService.findAppConfigValue("fdKey ='attconvert.converter.type.foxit'", "false");
		boolean startWPSConvertOfd = ("true".equals(isConverterWPS))  //开启WPS转OFD
				                     && StringUtil.isNotNull(WpsUtil.configInfo("thirdWpsPreviewEnabled")) //集成配置
				                     && "true".equals(WpsUtil.configInfo("thirdWpsPreviewEnabled"))
				                     && StringUtil.isNotNull(WpsUtil.configInfo("thirdWpsOS"))
				                     && "linux".equals(WpsUtil.configInfo("thirdWpsOS"));
		
		boolean startSuwellConvertOfd = ("true".equals(isConverterSKOFD))  //开启数科转OFD
				                        &&  StringUtil.isNotNull(suwellConvretEnabled)  //配置
				                        && "true".equals(suwellConvretEnabled);

		boolean startWPSCenterConvertOfd = com.landray.kmss.util.StringUtil.isNotNull(WpsUtil.configInfo("thirdWpsCenterEnabled"))
										&& "true".equals(WpsUtil.configInfo("thirdWpsCenterEnabled")) && "true".equals(isConverterWPSCenterOFD);


		boolean startDianjuConvertOfd = com.landray.kmss.util.StringUtil.isNotNull(thirdDianjuEnabled)
				&& "true".equals(thirdDianjuEnabled) && com.landray.kmss.util.StringUtil.isNotNull(isConverterDianju)
				&& "true".equals(isConverterDianju);
		boolean startFoxitConvertOfd = com.landray.kmss.util.StringUtil.isNotNull(thirdFoxitEnabled)
				&& "true".equals(thirdFoxitEnabled) && com.landray.kmss.util.StringUtil.isNotNull(isConverterFoxit)
				&& "true".equals(isConverterFoxit);
		if(startWPSConvertOfd || startSuwellConvertOfd || startWPSCenterConvertOfd || startDianjuConvertOfd || startFoxitConvertOfd){
			return true;
		}
		else { 
		
			return false;
		}

	}
	private HQLInfo getStatusWhereHql(String[] statuses) {
		HQLInfo resultHQL = new HQLInfo();
		List<Integer> statusList = new ArrayList<Integer>();
		if (statuses == null || statuses.length == 0) {
			statusList.add(SysFileConvertConstant.ASSIGNED);
			statusList.add(SysFileConvertConstant.HAVEBEGUN);
			statusList.add(SysFileConvertConstant.UNASSIGNED);
			statusList.add(SysFileConvertConstant.CONVERTERTOOLFAIL);
			statusList.add(SysFileConvertConstant.FAILURE);
			statusList.add(SysFileConvertConstant.SUCCESS);
			statusList.add(SysFileConvertConstant.TIMEOUTFAILURE);
			statusList.add(SysFileConvertConstant.OOMFAILURE);
			statusList.add(SysFileConvertConstant.INVALID);
		} else {
			for (String status : statuses) {
				if ("converting".equals(status)) {
					statusList.add(SysFileConvertConstant.ASSIGNED);
					statusList.add(SysFileConvertConstant.HAVEBEGUN);
				} else if ("other".equals(status)) {
					statusList.add(SysFileConvertConstant.CONVERTERTOOLFAIL);
					statusList.add(SysFileConvertConstant.FAILURE);
					statusList.add(SysFileConvertConstant.SUCCESS);
					statusList.add(SysFileConvertConstant.TIMEOUTFAILURE);
					statusList.add(SysFileConvertConstant.OOMFAILURE);
					statusList.add(SysFileConvertConstant.INVALID);
				} else {
					statusList.add(Integer.valueOf(status));
				}
			}
		}
		String statusesStr = statusList.toString();
		StringBuffer where = new StringBuffer(
				" and fdConvertStatus in (" + statusesStr.substring(1, statusesStr.length() - 1) + ")");
		resultHQL.setWhereBlock(where.toString());
		return resultHQL;
	}

	public ActionForward reDistribute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-reDistribute", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			String redistributeAll = request.getParameter("Redistribute_All");
			String[] ids = request.getParameterValues("List_Selected");
			String[] failure_type = request.getParameterValues("Convert_Failure");
			sysFileConvertQueueService.saveReDistribute(redistributeAll, ids, failure_type);
		} catch (Exception e) {
			messages.addError(e);
		}

		KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_RETURN).save(request);
		TimeCounter.logCurrentTime("Action-reDistribute", false, getClass());
		if (messages.hasError()) {
            return getActionForward("failure", mapping, form, request, response);
        } else {
            return getActionForward("success", mapping, form, request, response);
        }
	}

	public ActionForward delQueues(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-delQueues", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			String[] ids = request.getParameterValues("selected");
			((ISysFileConvertQueueService) getServiceImp(request)).deleteQueues(ids);
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-delQueues", false, getClass());
		return getActionForward("success", mapping, form, request, response);
	}

	@Override
	public ActionForward data(ActionMapping mapping, ActionForm form, HttpServletRequest request,
							  HttpServletResponse response) throws Exception {
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
			Page page = ((ISysFileConvertQueueService) getServiceImp(request)).findOKPage(hqlInfo);
			// 记录日志
			if (UserOperHelper.allowLogOper(SysLogOperXml.LOGPOINT_FINDALL,
					SysFileConvertQueue.class.getName())) {
				UserOperContentHelper.putFinds(page.getList());
			}
			request.setAttribute("queryPage", page);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-list", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("data", mapping, form, request, response);
		}
	}
}
