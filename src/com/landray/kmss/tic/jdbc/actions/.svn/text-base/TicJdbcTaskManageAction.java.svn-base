package com.landray.kmss.tic.jdbc.actions;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.web.action.ActionMessage;
import com.landray.kmss.web.action.ActionMessages;
import org.hibernate.ObjectNotFoundException;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.sys.quartz.model.SysQuartzJob;
import com.landray.kmss.sys.quartz.scheduler.ISysQuartzJobExecutor;
import com.landray.kmss.sys.quartz.service.ISysQuartzJobService;
import com.landray.kmss.tic.core.log.constant.TicCoreLogConstant;
import com.landray.kmss.tic.core.log.interfaces.ITicCoreLogInterface;
import com.landray.kmss.tic.core.mapping.constant.Constant;
import com.landray.kmss.tic.jdbc.forms.TicJdbcTaskManageForm;
import com.landray.kmss.tic.jdbc.iface.ITicJdbcTaskSync;
import com.landray.kmss.tic.jdbc.model.TicJdbcRelation;
import com.landray.kmss.tic.jdbc.model.TicJdbcTaskCategory;
import com.landray.kmss.tic.jdbc.model.TicJdbcTaskManage;
import com.landray.kmss.tic.jdbc.service.ITicJdbcMappManageService;
import com.landray.kmss.tic.jdbc.service.ITicJdbcTaskCategoryService;
import com.landray.kmss.tic.jdbc.service.ITicJdbcTaskManageService;
import com.landray.kmss.tic.jdbc.util.JdbcRunSyncType;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.sunbor.web.tag.Page;

/**
 * 任务管理 Action
 * 
 * @author
 * @version 1.0 2013-07-24
 */
public class TicJdbcTaskManageAction extends ExtendAction {
	private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());
	protected ITicJdbcTaskManageService ticJdbcTaskManageService;

	@Override
    protected IBaseService getServiceImp(HttpServletRequest request) {
		if (ticJdbcTaskManageService == null) {
			ticJdbcTaskManageService = (ITicJdbcTaskManageService) getBean("ticJdbcTaskManageService");
		}
		return ticJdbcTaskManageService;
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
			return getActionForward("list", mapping, form, request, response);
		}
	}

	@Override
    protected void changeFindPageHQLInfo(HttpServletRequest request,
                                         HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		String categoryId = request.getParameter("categoryId");
		String hql = hqlInfo.getWhereBlock();
		if (!StringUtil.isNull(categoryId)) {
			hql = StringUtil.linkString(hql, " and ",
					"ticJdbcTaskManage.docCategory.fdId like :categoryId ");
			hqlInfo.setParameter("categoryId", "%" + categoryId + "%");
		}
		hqlInfo.setWhereBlock(hql);
	}

	/**
	 * 启用
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward chgenabled(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		String param = request.getParameter("fdIsEnabled");
		boolean isEnable = ("1".equals(param)) || ("true".equals(param));
		try {
			getServiceImp(request);
			if (!("POST".equals(request.getMethod()))) {
				throw new UnexpectedRequestException();
			}
			String[] ids = request.getParameterValues("List_Selected");
			if (ids != null) {
				this.ticJdbcTaskManageService.updateChgEnabled(ids, isEnable);
			}
		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
			return mapping.findForward("failure");
		}
		return mapping.findForward("success");
	}

	@Override
    protected ActionForm createNewForm(ActionMapping mapping, ActionForm form,
                                       HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TicJdbcTaskManageForm mainForm = (TicJdbcTaskManageForm) form;

		String fdTemplateId = request.getParameter("fdtemplatId");

		if (StringUtil.isNull(fdTemplateId)) {
			return mainForm;
		}

		ITicJdbcTaskCategoryService service = (ITicJdbcTaskCategoryService) SpringBeanUtil
				.getBean("ticJdbcTaskCategoryService");
		TicJdbcTaskCategory category = (TicJdbcTaskCategory) service
				.findByPrimaryKey(fdTemplateId);
		mainForm.setDocCategoryId(fdTemplateId);
		mainForm.setDocCategoryName(category.getFdName());
		return mainForm;
	}

	/**
	 * 运行
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward run(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			String ekpQuartz = request.getParameter("fdId");
			// notes:没有启动是不回去
			ISysQuartzJobService sysJobService = (ISysQuartzJobService) getBean("sysQuartzJobService");
			SysQuartzJob sysQuartzJob = (SysQuartzJob) sysJobService
					.findByPrimaryKey(ekpQuartz);
			try {
				if (!StringUtil.isNull(sysQuartzJob.getFdId())) {
					((ISysQuartzJobExecutor) getBean("sysQuartzJobExecutor"))
							.execute(ekpQuartz);
				}
			} catch (ObjectNotFoundException e) {
				e.printStackTrace();
				logger.warn("该定时任务没有启动，请先启动才运行~！");
			}

		} catch (Exception e) {
			e.printStackTrace();
			messages.addError(e);
		}
		KmssReturnPage.getInstance(request).addMessages(messages).setTitle(
				new KmssMessage("sys-quartz:sysQuartzJob.running")).addButton(
				KmssReturnPage.BUTTON_CLOSE).save(request);
		if (messages.hasError()) {
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("success");
		}
	}

	/**
	 * 手动运行同步
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward transData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionMessages message = new ActionMessages();
		String errorInfor="";
		String fdId="";
		try {
			RequestContext requestInfo = new RequestContext(request);
			fdId = requestInfo.getParameter("fdId");
			ITicJdbcTaskManageService taskService = ((ITicJdbcTaskManageService) getServiceImp(request));
			if(StringUtils.isNotEmpty(fdId)){
				TicJdbcTaskManage taskManage= (TicJdbcTaskManage) taskService
						.findByPrimaryKey(fdId); 
				List<TicJdbcRelation> list = taskManage.getTicJdbcRelationList();
				// 任务同步
				if(list!=null && list.size()>0){
					Date startDate = new Date();
					long start = System.currentTimeMillis();
					ITicCoreLogInterface ticCoreLogInterface = (ITicCoreLogInterface) SpringBeanUtil
							.getBean("ticCoreLogInterface");
					String fdMessages = "";
					String fdExportPar = "";
					boolean flag=true;
					System.out.println("======任务管理同步计时开始======");
					for(TicJdbcRelation ticJdbcRelation :list){
						String syncTypeJson = ticJdbcRelation.getFdSyncType();
						JSONObject json = JSONObject.fromObject(syncTypeJson);
						String syncType = json.getString("syncType");
						// 获取任务分发容器
						Map<String, String> syncTypeMap = JdbcRunSyncType.syncTypeMap;
						ITicJdbcTaskSync taskRun = (ITicJdbcTaskSync) SpringBeanUtil
								.getBean(syncTypeMap.get(syncType));
						// 执行任务并返回日志结果
						Map<String, String> logMap = taskRun.run(json);
						// 记录日志信息
						fdExportPar += logMap.get("errorDetail") +"<p></p>";
						fdMessages += logMap.get("message") +"<p></p>";
						
						if(StringUtils.isNotEmpty(logMap.get("errorDetail"))){
							flag=false;
						}
					}
					// 判断正常日志还是错误日志
					String fdIsErr = TicCoreLogConstant.TIC_CORE_LOG_TYPE_SUCCESS;
					//本次迁移结果标志
					if (!flag) {
						fdIsErr = TicCoreLogConstant.TIC_CORE_LOG_TYPE_ERROR;
					}
					// 写入日志
					ticCoreLogInterface.saveLogMain(taskManage.getFdSubject(), Constant.FD_TYPE_JDBC, 
							startDate, "", fdExportPar, fdMessages, fdIsErr,"hejiantesttesttest");
					long end = System.currentTimeMillis();
					System.out.println("同步耗时："+ ((end - start)/1000<0 ?1:(end - start)/1000));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			errorInfor = e.toString();
		}
		if(StringUtils.isNotEmpty(errorInfor)){
			message.add("failure", new ActionMessage(ResourceUtil.getString(
					"tic-jdbc:ticJdbcTaskManage.trans.data.failure", request.getLocale())
					+ " "+ errorInfor, false));
			saveMessages(request, message);
		}else{
			message.add("success", new ActionMessage(ResourceUtil.getString(
					"tic-jdbc:ticJdbcTaskManage.trans.data.success", request.getLocale()),false));
			saveMessages(request, message);
		}
		return super.view(mapping, form, request, response);
	}
	
	public ActionForward getDelData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-getData", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			RequestContext requestInfo = new RequestContext(request);
			ITicJdbcMappManageService mappService = (ITicJdbcMappManageService)SpringBeanUtil
					.getBean("ticJdbcMappManageService");
			Map resultData = mappService.getTableData(requestInfo);
		    List titleList = (List) resultData.get("titleList");
		    List resultList = (List) resultData.get("resultList");
		    request.setAttribute("titleList", titleList);
		    request.setAttribute("resultList", resultList);
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-list", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("dataList", mapping, form, request, response);
		}
	}
}
