package com.landray.kmss.common.actions;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hibernate.query.Query;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.constant.SysAuthConstant.AuthCheck;
import com.landray.kmss.constant.SysAuthConstant.CheckType;
import com.landray.kmss.sys.config.design.SysCfgSearch;
import com.landray.kmss.sys.config.design.SysConfigs;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.search.SearchHQLUtil;
import com.landray.kmss.sys.search.model.SysSearchMain;
import com.landray.kmss.sys.search.service.ISysSearchMainService;
import com.landray.kmss.sys.search.web.SearchCondition;
import com.landray.kmss.sys.search.web.SearchOrderBy;
import com.landray.kmss.sys.search.web.SearchResult;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.util.excel.ExcelOutput;
import com.landray.kmss.util.excel.ExcelOutputImp;
import com.landray.kmss.util.excel.WorkBook;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;

public abstract class BaseSearchAction extends BaseAction {
	private Logger logger = org.slf4j.LoggerFactory.getLogger(BaseSearchAction.class);

	private ISysSearchMainService searchMainService;

	protected ISysSearchMainService getSysSearchMainService() {
		if (searchMainService == null) {
			searchMainService = (ISysSearchMainService) getBean("sysSearchMainService");
		}
		return searchMainService;
	}

	/**
	 * 获取执行CRUD操作对应的service。
	 * 
	 * @return Action中执行CRUD对应的service
	 */
	protected abstract IBaseService getServiceImp(HttpServletRequest request);

	protected String getSearchPageWhereBlock(HttpServletRequest request)
			throws Exception {
		return null;
	}

	protected String getSearchPageOrderBy(HttpServletRequest request,
			String curOrderBy) throws Exception {
		/*
		 * 自定义搜索（getSearchModel(request) != null）时暂不设置默认排序， 苏轶 2007年5月28日
		 */
		if (getSearchModel(request) == null && curOrderBy == null) {
			String className = getServiceImp(request).getModelName();
			if (StringUtil.isNull(className)) {
				return null;
			}
			SysDictModel model = SysDataDict.getInstance().getModel(className);
			if (model == null) {
				return null;
			}
			String modelName = ModelUtil.getModelTableName(className);
			Map propertyMap = model.getPropertyMap();
			logger.debug("modelNme=" + modelName);
			/*
			 * 如果fdOrder不为空，则按照fdOrder和fdName（如果存在）排序， 否则直接按照fdId逆序排序（如果存在）
			 */
			curOrderBy = "";
			if (propertyMap.get("fdOrder") != null) {
				curOrderBy += modelName + ".fdOrder";
				if (propertyMap.get("fdName") != null) {
					curOrderBy += "," + modelName + ".fdName";
				}
			} else if (propertyMap.get("fdId") != null) {
				curOrderBy += modelName + ".fdId desc";
			}
			if(logger.isDebugEnabled()){
				logger.debug("curOrderBy=" + curOrderBy);
			}
		}
		return curOrderBy;
	}

	protected void changeSearchPageHQLInfo(HttpServletRequest request,
			HQLInfo hqlInfo) throws Exception {
		hqlInfo.setWhereBlock(getSearchPageWhereBlock(request));
		hqlInfo.setOrderBy(getSearchPageOrderBy(request, hqlInfo.getOrderBy()));
	}

	protected SysSearchMain getSearchModel(HttpServletRequest request)
			throws Exception {
		String searchId = request.getParameter("searchId");
		if (StringUtil.isNull(searchId)) {
			return null;
		}
		return (SysSearchMain) getSysSearchMainService().findByPrimaryKey(
				searchId);
	}

	protected SearchCondition getSearchCondition(HttpServletRequest request,
			SysCfgSearch cfgSearch, SysSearchMain searchModel) throws Exception {
		return new SearchCondition(cfgSearch, request.getLocale(), searchModel);
	}

	protected SearchOrderBy getSearchOrderBy(HttpServletRequest request,
			SysCfgSearch cfgSearch, SysSearchMain searchModel) throws Exception {
		return new SearchOrderBy(searchModel, cfgSearch, request.getLocale());
	}

	protected SearchResult getSearchResult(HttpServletRequest request,
			SysCfgSearch cfgSearch, SysSearchMain searchModel) throws Exception {
		return new SearchResult(searchModel, cfgSearch, request.getLocale());
	}

	protected ActionForward getActionForward(String defaultForward,
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String para = request.getParameter("forward");
		if (!StringUtil.isNull(para)) {
			defaultForward = para;
		}
		return mapping.findForward(defaultForward);
	}

	public ActionForward condition(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			IBaseService baseService = getServiceImp(request);
			SysCfgSearch cfgSearch = SysConfigs.getInstance().getSearch(
					baseService.getModelName());
			if (cfgSearch == null) {
				throw new UnexpectedRequestException();
			}

			request.setAttribute("searchConditionInfo", getSearchCondition(
					request, cfgSearch, getSearchModel(request)));
			
		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("searchCondition", mapping, form, request,
					response);
		}
	}

	/**
	 * 执行查询操作。<br>
	 * 该操作一般以HTTP的GET方式触发。
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回search页面，否则返回failure页面
	 * @throws Exception
	 */
	public ActionForward result(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		long starTime = System.currentTimeMillis();
		TimeCounter.logCurrentTime("Action-search", true, getClass());
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
			int rowsize = 0;
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
			if (logger.isDebugEnabled()) {
				logger.debug("from request, Order By=" + hqlInfo.getOrderBy());
			}
			hqlInfo.setPageNo(pageno);
			hqlInfo.setRowSize(rowsize);
			changeSearchPageHQLInfo(request, hqlInfo);
			if (logger.isDebugEnabled()) {
				logger.debug("after call changeSearchPageHQLInfo(), Order By="
						+ hqlInfo.getOrderBy());
			}

			IBaseService baseService = getServiceImp(request);
			String modelName = baseService.getModelName();
			SysCfgSearch cfgSearch = SysConfigs.getInstance().getSearch(
			        modelName);
			
			if (cfgSearch == null) {
				throw new UnexpectedRequestException();
			}

			SysSearchMain searchModel = getSearchModel(request);
			if (logger.isDebugEnabled()) {
				/*
				 * 自定义搜索searchModel != null 普通搜索时resultUrl未配置
				 */
				logger.debug("searchModel=" + searchModel);
				logger.debug("cfgSearch.getResultUrl()=["
						+ cfgSearch.getResultUrl() + "]");
			}
			if (searchModel != null
					|| StringUtil.isNull(cfgSearch.getResultUrl())) {
				request.setAttribute("searchResultInfo", getSearchResult(
						request, cfgSearch, searchModel));
			}
			SearchHQLUtil.buildHQLInfo(baseService,
					new RequestContext(request), hqlInfo, getSearchCondition(
							request, cfgSearch, searchModel));
			SearchHQLUtil.buildHQLOrderBy(baseService, new RequestContext(
					request), hqlInfo, getSearchOrderBy(request, cfgSearch,
					searchModel));
			if (logger.isDebugEnabled()) {
				logger.debug("before findPage(), Order By="
						+ hqlInfo.getOrderBy());
			}
			// 判断是否关闭权限校验
			if (searchModel != null
					&& BooleanUtils.isFalse(searchModel.getFdAuthEnabled())) {
				// 关闭权限校验
				hqlInfo.setCheckParam(CheckType.AuthCheck, AuthCheck.SYS_NONE);
			}
			Page page = baseService.findPage(hqlInfo);
			List list = page.getList();
			UserOperHelper.setModelNameAndModelDesc(modelName);
			UserOperContentHelper.putFinds(list);
			request.setAttribute("queryPage", page);

			String queryString = request.getQueryString();
			queryString = queryString.replaceAll("method=result",
					"method=exportResult");
			String exportURL = request.getRequestURI() + "?" + queryString;
			request.setAttribute("exportURL", exportURL);
		} catch (Exception e) {
			messages.addError(e);
		}
		long endTime = System.currentTimeMillis();
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(endTime - starTime);
		// m代表毫秒，s代表秒
		long m = c.get(Calendar.MILLISECOND);
		Double s = (double) m / 1000;
		request.setAttribute("c", s);
		TimeCounter.logCurrentTime("Action-search", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("searchResult", mapping, form, request,
					response);
		}
	}

	public ActionForward exportResult(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			String orderby = request.getParameter("orderby");
			String ordertype = request.getParameter("ordertype");
			boolean isReserve = false;
			if (ordertype != null && "down".equalsIgnoreCase(ordertype)) {
				isReserve = true;
			}
			if (isReserve) {
				orderby += " desc";
			}
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setOrderBy(orderby);
			hqlInfo.setAuthCheckType(HQLInfo.AUTH_CHECK_READER);
			changeSearchPageHQLInfo(request, hqlInfo);

			IBaseService baseService = getServiceImp(request);
			String modelName = baseService.getModelName();
			SysCfgSearch cfgSearch = SysConfigs.getInstance().getSearch(modelName);
			if (cfgSearch == null) {
				throw new UnexpectedRequestException();
			}

			SysSearchMain searchModel = getSearchModel(request);
			SearchHQLUtil.buildHQLInfo(baseService,
					new RequestContext(request), hqlInfo, getSearchCondition(
							request, cfgSearch, searchModel));
			SearchHQLUtil.buildHQLOrderBy(baseService, new RequestContext(
					request), hqlInfo, getSearchOrderBy(request, cfgSearch,
					searchModel));
			// 数据总数
			String s_rowsize = request.getParameter("fdNum");
			// 导出数据开始
			String s_rowsizeStart = request.getParameter("fdNumStart");
			// 导出数据结束
			String s_rowsizeEnd = request.getParameter("fdNumEnd");
			//选中的记录
			String s_checkIdValues = request.getParameter("checkIdValues");
			String [] arrIds =null;
			if (StringUtil.isNotNull(s_checkIdValues)){
				arrIds = s_checkIdValues.split("[;\\|]");
			}
			
			// 判断是否关闭权限校验
			if (searchModel != null
					&& BooleanUtils.isFalse(searchModel.getFdAuthEnabled())) {
				// 关闭权限校验
				hqlInfo.setCheckParam(CheckType.AuthCheck, AuthCheck.SYS_NONE);
			}

			// 接收查询的记录
			List exportList = null;
			
			// 导出数据，有3种方式
			// 1.按选择的记录导出
			// 2.按起止时间导出
			// 3.导出全部（此方式导出，如果一次导出的数据量太大，会严重影响服务器性能，还可能引发耗尽内存而宕机）
			if (arrIds != null && arrIds.length > 0) { // 如果用户选了记录，则优先按选择记录的方式导出
				exportList = baseService.findByPrimaryKeys(arrIds);
			} else if (StringUtil.isNotNull(s_rowsizeStart)
					&& StringUtil.isNotNull(s_rowsizeEnd)) { //按范围导出记录
				int rowsizeStart = Integer.parseInt(s_rowsizeStart) - 1;
				int rowsizeEnd = Integer.parseInt(s_rowsizeEnd);
				if (rowsizeEnd < rowsizeStart) {
					throw new Exception("导出数据的“开始数”不能大于“终止数”！");
				}
				int max = rowsizeEnd - rowsizeStart;
				if (max > 5000) {
					logger.warn("导出的数量大于5000，为了避免服务器宕机，强制限制导出数量为5000");
					max = 5000;
				}
				
				hqlInfo.setModelName(baseService.getModelName());
				Query query = getSysSearchMainService().createHbmQuery(hqlInfo);
				query.setFirstResult(rowsizeStart);
				query.setMaxResults(max);
				exportList = query.list();
			} else { //全部导出
				int rowsize = Integer.parseInt(s_rowsize);
				// 避免出现宕机，当导出的数量大于5000时，这里强制限制5000
				if (rowsize > 5000) {
					rowsize = 5000;
				}
				hqlInfo.setRowSize(rowsize);
				exportList = baseService.findPage(hqlInfo).getList();
			}
			
			ExcelOutput output = new ExcelOutputImp();
            UserOperHelper.setModelNameAndModelDesc(modelName);
            UserOperContentHelper.putFinds(exportList);
			String userAgent = request.getHeader("User-Agent");
			if (userAgent.contains("Edge")) {// #89015 edge浏览器导出文件名乱码
				ExcelOutputImp excelOutput = (ExcelOutputImp) output;
				excelOutput.output(getExportWorkBook(form, request, exportList),
						request, response);
			} else {
				output.output(getExportWorkBook(form, request, exportList),
						response);
			}

			return null;
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-search", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("searchResult", mapping, form, request,
					response);
		}
	}

	protected WorkBook getExportWorkBook(ActionForm form,
			HttpServletRequest request, List modelList) throws Exception {
		return getSysSearchMainService().buildWorkBook(null,
				new RequestContext(request), modelList);
	}
}
