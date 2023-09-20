package com.landray.kmss.sys.news.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.constant.SysDocConstant;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.news.model.SysNewsMain;
import com.landray.kmss.sys.news.service.ISysNewsMainService;
import com.landray.kmss.sys.news.service.spring.SysNewsMainPortlet;
import com.landray.kmss.sys.portal.util.PortletTimeUtil;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;

import net.sf.json.JSONArray;

/**
 * 创建日期 2013-11-17
 * 
 * @author tanyh
 */
public class SysNewsMainPortletAction extends ExtendAction {

	protected ISysNewsMainService sysNewsMainService;
	protected SysNewsMainPortlet sysNewsMainPortletService;


	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (sysNewsMainService == null) {
            sysNewsMainService = (ISysNewsMainService) getBean("sysNewsMainService");
        }
		return sysNewsMainService;
	}
	
	protected SysNewsMainPortlet getSysNewsMainPortlet(HttpServletRequest request) {
		if (sysNewsMainPortletService == null) {
            sysNewsMainPortletService = (SysNewsMainPortlet) getBean("sysNewsMainPortletService");
        }
		return sysNewsMainPortletService;
	}
	
	public ActionForward listPortlet(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception {
		TimeCounter.logCurrentTime("Action-list", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String rowsize = (String) request.getParameter("rowsize");
			String myNews = (String) request.getParameter("myNews");
			HQLInfo hqlInfo = new HQLInfo();
			if (StringUtil.isNotNull(rowsize)) {
				hqlInfo.setRowSize(Integer.parseInt(rowsize));
			}
			String whereBlock = "1=1";
			if (StringUtil.isNotNull(myNews)) {
				// 我发布的
				if ("myPublish".equals(myNews)) {
					whereBlock += " and sysNewsMain.docCreator=:docCreator and sysNewsMain.docStatus=:status";
					hqlInfo.setParameter("docCreator", UserUtil.getUser());
					hqlInfo.setParameter("status",
							SysDocConstant.DOC_STATUS_PUBLISH);
					hqlInfo.setWhereBlock(whereBlock);
					// 我点评的
				} else if ("myEv".equals(myNews)) {
					StringBuffer hqlBuffer = new StringBuffer();
					hqlBuffer.append("sysNewsMain.fdId in ");
					// 拼接子查询
					hqlBuffer.append(
							"(select distinct sysEvaluationMain.fdModelId from ");
					hqlBuffer
							.append(" com.landray.kmss.sys.evaluation.model.SysEvaluationMain"
									+ " as sysEvaluationMain ");
					hqlBuffer.append(
							"where sysEvaluationMain.fdModelName = :fdModelName ");
					hqlBuffer.append(
							"and sysEvaluationMain.fdEvaluator.fdId = :fdEvaluatorId)");
					hqlInfo.setWhereBlock(hqlBuffer.toString());
					hqlInfo.setParameter("fdModelName",
							"com.landray.kmss.sys.news.model.SysNewsMain");
					hqlInfo.setParameter("fdEvaluatorId",
							UserUtil.getUser().getFdId());
				}
			}

			// 时间范围参数
			String scope = request.getParameter("scope");
			if (StringUtil.isNotNull(scope) && !"no".equals(scope)) {
				String block = hqlInfo.getWhereBlock();
				hqlInfo.setWhereBlock(StringUtil.linkString(block, " and ",
						"sysNewsMain.docPublishTime > :fdStartTime"));
				hqlInfo.setParameter("fdStartTime",
						PortletTimeUtil.getDateByScope(scope));
			}

			if (StringUtil.isNull(hqlInfo.getOrderBy())) {
				hqlInfo.setOrderBy(
						"sysNewsMain.fdIsTop desc ,sysNewsMain.fdTopTime desc,sysNewsMain.docPublishTime desc,sysNewsMain.docAlterTime desc,sysNewsMain.docCreateTime desc");
			}
			hqlInfo.setGetCount(false);
			Page page = getServiceImp(request).findPage(hqlInfo);
			// 添加日志信息
			UserOperHelper.logFindAll(page.getList(),
					getServiceImp(request).getModelName());
			request.setAttribute("queryPage", page);
		}catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-list", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			return getActionForward("listPortlet", mapping, form, request,
					response);
		}
	}
	
	public ActionForward getNewsMportal(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception {
		TimeCounter.logCurrentTime("Action-getNewsWithImg", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			
			Page page = getSysNewsMainPortlet(request)
					.getNewsMportal(new RequestContext(request));
			// 添加日志信息
			UserOperHelper.logFindAll(page.getList(),
					getServiceImp(request).getModelName());
			if("pic".equals(request.getParameter("type"))){
				JSONArray rtnArray = this.toJsonArray(page.getList());
				request.setAttribute("lui-source", rtnArray);
			} else{
				request.setAttribute("queryPage", page);
			}
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-getImg", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("lui-failure", mapping, form, request, response);
		} else {
			if("pic".equals(request.getParameter("type"))){
				return getActionForward("lui-source", mapping, form, request, response);
			}else{
				return getActionForward("getNewsMportal", mapping, form, request, response);
			}
		}
	}
	
	protected JSONArray toJsonArray(List mainList) throws Exception{
		JSONArray rtnArray = new JSONArray();
		if (mainList != null) {
			for (int i = 0; i < mainList.size(); i++) {
				SysNewsMain sysNewsMain = (SysNewsMain) mainList.get(i);
				JSONObject json = new JSONObject();
				json.put("text", sysNewsMain.getDocSubject());
				ISysAttMainCoreInnerService sysAttMainCoreInnerService = (ISysAttMainCoreInnerService) SpringBeanUtil
						.getBean("sysAttMainService");
				List list = sysAttMainCoreInnerService.findByModelKey(
						"com.landray.kmss.sys.news.model.SysNewsMain",
						sysNewsMain.getFdId(), "Attachment");
				if (list != null && list.size() > 0) {
					SysAttMain att = (SysAttMain) list.get(0);
					json.put("image",
							"/sys/attachment/sys_att_main/sysAttMain.do?method=download&fdId="
									+ att.getFdId());
				}
				String href = "/sys/news/sys_news_main/sysNewsMain.do?method=view&fdId="
						+ sysNewsMain.getFdId();
				if (StringUtil.isNotNull(sysNewsMain.getFdLinkUrl())
						&& ("30".equals(sysNewsMain.getDocStatus())
								|| "40".equals(sysNewsMain.getDocStatus()))) {
					href = sysNewsMain.getFdLinkUrl();
				}
				json.put("href", href);

				rtnArray.add(json);
			}
		}
		return rtnArray;
	}
 }
