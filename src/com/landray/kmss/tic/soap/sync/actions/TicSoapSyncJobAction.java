package com.landray.kmss.tic.soap.sync.actions;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import org.hibernate.ObjectNotFoundException;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.quartz.model.SysQuartzJob;
import com.landray.kmss.sys.quartz.scheduler.ISysQuartzJobExecutor;
import com.landray.kmss.sys.quartz.service.ISysQuartzJobService;
import com.landray.kmss.tic.soap.sync.forms.TicSoapSyncJobForm;
import com.landray.kmss.tic.soap.sync.model.ClocalVo;
import com.landray.kmss.tic.soap.sync.model.TicSoapSyncCategory;
import com.landray.kmss.tic.soap.sync.service.ITicSoapSyncCategoryService;
import com.landray.kmss.tic.soap.sync.service.ITicSoapSyncJobService;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;


/**
 * 定时任务 Action
 * 
 * @author 
 * @version 1.0 2014-02-20
 */
public class TicSoapSyncJobAction extends ExtendAction {
	
	private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());
	
	protected ITicSoapSyncJobService ticSoapSyncJobService;

	@Override
    protected IBaseService getServiceImp(HttpServletRequest request) {
		if(ticSoapSyncJobService == null) {
			ticSoapSyncJobService = (ITicSoapSyncJobService)getBean("ticSoapSyncJobService");
		}
		return ticSoapSyncJobService;
	}
	public ActionForward chgenabled(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		String param = request.getParameter("fdEnabled");
		boolean isEnable = ("1".equals(param)) || ("true".equals(param));
		try {
			getServiceImp(request);
			if (!("POST".equals(request.getMethod()))) {
				throw new UnexpectedRequestException();
			}
			String[] ids = request.getParameterValues("List_Selected");
			if (ids != null) {
				this.ticSoapSyncJobService.updateChgEnabled(ids, isEnable);
			}
		} catch (Exception e) {
			e.printStackTrace();
			messages.addError(e);
		}
		
		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_RETURN).save(request);
		if (messages.hasError()) {
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("success", mapping, form, request, response);
		}
	}
	
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
				logger.warn("该定时任务没有启动，请先启动才运行~！");
				e.printStackTrace();
			}
		} catch (Exception e) {
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
	
	@Override
    protected String getFindPageWhereBlock(HttpServletRequest request)
			throws Exception {
		String categoryId=request.getParameter("categoryId");
		String whereBlock=StringUtil.isNotNull(categoryId)?" ticSoapSyncJob.docCategory.fdHierarchyId like '%"+categoryId+"%'":null;
		return whereBlock;
	}
	
	public ActionForward getXMLTable(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		String quartzId=request.getParameter("quartzId");
		 getServiceImp(request);
		 Set<ClocalVo> result=ticSoapSyncJobService.findTableByQuartzId(quartzId);
		 request.setAttribute("result", result);
		return getActionForward("tableList", mapping, form, request, response);//mapping.findForward("tableList");
	}
	
	@Override
    protected ActionForm createNewForm(ActionMapping mapping, ActionForm form,
                                       HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String fdTemplateId = request.getParameter("fdtemplatId");
		if(StringUtil.isNull(fdTemplateId)) {
			return form;
		}
		ITicSoapSyncCategoryService service = (ITicSoapSyncCategoryService) SpringBeanUtil.getBean("ticSoapSyncCategoryService");
		TicSoapSyncCategory category = (TicSoapSyncCategory) service.findByPrimaryKey(fdTemplateId);
		TicSoapSyncJobForm soapForm=(TicSoapSyncJobForm)form;
		soapForm.setDocCategoryId(category.getFdId());
		soapForm.setDocCategoryName(category.getFdName());
		return soapForm;
	}
}

