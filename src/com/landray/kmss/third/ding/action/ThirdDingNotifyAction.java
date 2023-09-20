package com.landray.kmss.third.ding.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.third.ding.model.OmsRelationModel;
import com.landray.kmss.third.ding.util.clean.DingNotifyUpdateUtil;
import org.slf4j.Logger;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.third.ding.forms.ThirdDingNotifyForm;
import com.landray.kmss.third.ding.model.DingConfig;
import com.landray.kmss.third.ding.model.ThirdDingNotify;
import com.landray.kmss.third.ding.notify.queue.service.IThirdDingNotifyQueueErrorService;
import com.landray.kmss.third.ding.service.IOmsRelationService;
import com.landray.kmss.third.ding.service.IThirdDingNotifyService;
import com.landray.kmss.third.ding.util.CleaningToolUtil;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ThirdDingNotifyAction extends ExtendAction {

	private static final Logger logger = org.slf4j.LoggerFactory
			.getLogger(ThirdDingNotifyAction.class);

    private IThirdDingNotifyService thirdDingNotifyService;

    @Override
    public IThirdDingNotifyService getServiceImp(HttpServletRequest request) {
        if (thirdDingNotifyService == null) {
            thirdDingNotifyService = (IThirdDingNotifyService) getBean("thirdDingNotifyService");
        }
        return thirdDingNotifyService;
    }

	private IOmsRelationService omsRelationService;

	public IOmsRelationService getOmsRelationService() {
		if (omsRelationService == null) {
			omsRelationService = (IOmsRelationService) SpringBeanUtil.getBean("omsRelationService");
		}
		return omsRelationService;
	}

	private IThirdDingNotifyService thirdDingNotifyWRService;

	public IThirdDingNotifyService getThirdDingNotifyWRService() {
		if (thirdDingNotifyWRService == null) {
			thirdDingNotifyWRService = (IThirdDingNotifyService) getBean("thirdDingNotifyWRService");
		}
		return thirdDingNotifyWRService;
	}

	private IThirdDingNotifyQueueErrorService thirdDingNotifyQueueErrorService;

	public IThirdDingNotifyQueueErrorService getThirdDingNotifyQueueErrorService() {
		if (thirdDingNotifyQueueErrorService == null) {
			thirdDingNotifyQueueErrorService = (IThirdDingNotifyQueueErrorService) getBean("thirdDingNotifyQueueErrorService");
		}
		return thirdDingNotifyQueueErrorService;
	}

	private ISysOrgPersonService sysOrgPersonService;

	public void setSysOrgPersonService(ISysOrgPersonService sysOrgPersonService) {
		this.sysOrgPersonService = sysOrgPersonService;
	}

	public ISysOrgPersonService getSysOrgPersonService() {
		if (sysOrgPersonService == null) {
			sysOrgPersonService = (ISysOrgPersonService) getBean("sysOrgPersonService");
		}
		return sysOrgPersonService;
	}

	@Override
    public void changeFindPageHQLInfo(HttpServletRequest request,
                                      HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, ThirdDingNotify.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ThirdDingNotifyForm thirdDingNotifyForm = (ThirdDingNotifyForm) super.createNewForm(mapping, form, request, response);
        ((IThirdDingNotifyService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return thirdDingNotifyForm;
    }
    
    public ActionForward cleaningNotify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-cleaningNotify", true, getClass());
		KmssMessages messages = new KmssMessages();
		JSONObject jsonObject = new JSONObject();
		String msg ="";
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			String ekpUserId = request.getParameter("userId");
			if(StringUtil.isNotNull(ekpUserId)){
				msg = getServiceImp(request).updateCleaningNotify(ekpUserId);
				if("".equals(msg)){
					jsonObject.put("success", true);
			   	    jsonObject.put("message", "成功");
				}else {
					jsonObject.put("success", false);
					jsonObject.put("message", msg); 
				}
			}	
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(jsonObject.toString());
		} catch (Exception e) {
			jsonObject.put("success", false);
			jsonObject.put("message", e.getMessage() == null ? "NullPointerException异常" : e.getMessage());
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(jsonObject.toString());
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * 全部清除钉钉待办
	 */
    public ActionForward cleaningAllNotify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-cleaningAllNotify", true, getClass());
		KmssMessages messages = new KmssMessages();
		JSONObject jsonObject = new JSONObject();
		PrintWriter printWriter = response.getWriter();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			getServiceImp(request).cleaningAllNotify();
			jsonObject.put("success", true);
	   	    jsonObject.put("message", "成功");  
	   	    printWriter.print(jsonObject);
		} catch (Exception e) {
			jsonObject.put("success", false);
			jsonObject.put("message", e.getMessage());  
			printWriter.print(jsonObject);
			e.printStackTrace();
		}finally {
			printWriter.close();
		}

		return null;
	}

	/**
	 * 清理工具：查询并对比两边的待办情况
	 */
	public ActionForward queryNotify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-cleaningNotify", true, getClass());
		JSONObject jsonObject = new JSONObject();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			//需要处理的用户ID列表
			String ekpUserId = request.getParameter("userId");
			if (StringUtil.isNull(ekpUserId)) {
				logger.error("ekpUserId为空");
				return null;
			}
			logger.debug("userId：" + ekpUserId);
			String[] users = ekpUserId.split(";");

			//已推送的待办数据（两边待办列表都存在）
			JSONArray hadSendArray = new JSONArray();
			//未推送的待办数据（EKP待办列表有，而钉钉端没有）
			JSONArray notSendArray = new JSONArray();
			// 存放异常数据（无法提取ekp待办fdId参数）
			JSONArray checkErrorArray = new JSONArray();
			//处理了没更新的待办（EKP待办列表没有，钉钉待办列表还有）
			JSONArray updateFailArray = new JSONArray();

			HQLInfo hqlInfo = null;
			for (String fdId : users) {
				SysOrgPerson user = (SysOrgPerson) getSysOrgPersonService().findByPrimaryKey(fdId);
				logger.info("【查询待办记录】用户：{}",user.getFdName());
				OmsRelationModel relationModel = getOmsRelationService().findByEkpId(fdId);
				if (relationModel == null) {
					logger.warn("找不到用户：{} 的对照关系，清理忽略",user.getFdName());
					continue;
				}
				CleaningToolUtil.setUserTodoInfo(relationModel,user,hadSendArray,notSendArray,checkErrorArray,updateFailArray);
			}
			jsonObject.put("success", true);
			jsonObject.put("message", "查询成功");
			jsonObject.put("hadSendArray", hadSendArray);
			jsonObject.put("notSendArray", notSendArray);
			jsonObject.put("updateFailArray", updateFailArray);
			jsonObject.put("checkErrorArray", checkErrorArray);
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(jsonObject.toString());
		} catch (Exception e) {
			jsonObject.put("success", false);
			jsonObject.put("message", e.getMessage() == null ? "NullPointerException异常" : e.getMessage());
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(jsonObject.toString());
			logger.error(e.getMessage(),e);
		}
		return null;
	}

	/**
	 * 批量更新
	 */
	public ActionForward cleaningFail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		JSONObject jsonObject = new JSONObject();
		if (!"POST".equals(request.getMethod())) {
            throw new UnexpectedRequestException();
        }
		String targets = request.getParameter("targets");
		JSONArray targetsData = JSONArray.fromObject(targets);
		logger.info("批量更新待办:" + targets);
		// 清理待办（更新状态）
		String rs = DingNotifyUpdateUtil.updateDingNotifyByTool(targetsData);
		logger.debug("清理结果：" + rs);
		jsonObject.put("success", true);
		jsonObject.put("message", rs);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(jsonObject.toString());
		return null;
	}

	/**
	 * 批量重发
	 */
	public ActionForward reSend(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-cleaningNotify", true, getClass());
		JSONObject jsonObject = new JSONObject();
		if (!"POST".equals(request.getMethod())) {
            throw new UnexpectedRequestException();
        }
		String targets = request.getParameter("targets");
		JSONArray targetsData = JSONArray.fromObject(targets);
		logger.warn("targets:" + targets);
		// 重发
		String rs = getThirdDingNotifyQueueErrorService().updateResendByCleaningTool(targetsData);
		if (StringUtil.isNotNull(rs)) {
			logger.warn("rs" + rs);
			jsonObject.put("success", false);
			jsonObject.put("message", rs);
		} else {
			jsonObject.put("success", true);
			jsonObject.put("message", "查询成功");
		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(jsonObject.toString());
		return null;
	}


	/*
	 * 一键清理以及重发
	 */
	public ActionForward cleanAndResend(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-cleaningNotify", true, getClass());
		KmssMessages messages = new KmssMessages();
		JSONObject jsonObject = new JSONObject();
		String msg = "";
		if (!"POST".equals(request.getMethod())) {
            throw new UnexpectedRequestException();
        }
		String ekpUserId = request.getParameter("userId");
		logger.warn("一键清理以及重发ekpUserId:" + ekpUserId);
		if (StringUtil.isNull(ekpUserId)) {
			logger.error("ekpUserId为空");
			return null;
		}
		logger.warn("userId：" + ekpUserId);
		String[] users = ekpUserId.split(";");
		JSONArray notSendArray = new JSONArray();
		// 存放异常数据
		JSONArray checkErrorArray = new JSONArray();
		JSONArray updateFailArray = new JSONArray();
		JSONArray hadSendArray = new JSONArray();
		for (String fdId : users) {
			SysOrgPerson user = (SysOrgPerson) getSysOrgPersonService().findByPrimaryKey(fdId);
			logger.info("【查询待办记录】用户：{}",user.getFdName());
			OmsRelationModel relationModel = getOmsRelationService().findByEkpId(fdId);
			if (relationModel == null) {
				logger.warn("找不到用户：{} 的对照关系，清理忽略",user.getFdName());
				continue;
			}
			CleaningToolUtil.setUserTodoInfo(relationModel,user,hadSendArray,notSendArray,checkErrorArray,updateFailArray);
		}
		// 处理待办重发
		String rs = null;
		String apiType = DingConfig.newInstance().getNotifyApiType();
		if ("WR".equals(apiType)) {
			if (notSendArray != null && !notSendArray.isEmpty()) {
				rs = getThirdDingNotifyQueueErrorService()
						.updateResendByCleaningTool(notSendArray);
			}

		} else if ("TODO".equals(apiType)) {
			if (notSendArray != null && !notSendArray.isEmpty()) {
				rs = getThirdDingNotifyQueueErrorService().updateResendByCleaningTool(notSendArray);
			}
		} else {
			logger.warn("非待办任务接口(新接口),重发不处理！");
		}
		if (StringUtil.isNull(rs)) {
			// 处理待办清理
			if (updateFailArray != null && !updateFailArray.isEmpty()) {
				 rs = DingNotifyUpdateUtil.updateDingNotifyByTool(updateFailArray);
			}
		} else {
			logger.warn("待办重发异常：" + rs);
		}
		jsonObject.put("success", true);
		jsonObject.put("message", rs);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(jsonObject.toString());
		return null;
	}
}
