package com.landray.kmss.third.im.kk.actions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.BaseAction;
import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.dao.HQLParameter;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.third.im.kk.provider.KkPostDataRunner;
import com.landray.kmss.third.im.kk.service.IKkNotifyLogService;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import net.sf.json.JSONObject;

/**
 * kk待办集成日志 Action
 * 
 * @author
 * @version 1.0 2012-04-13
 */
public class KkNotifyLogAction extends ExtendAction {
	protected IKkNotifyLogService kkNotifyLogService;

	@Override
    protected IBaseService getServiceImp(HttpServletRequest request) {
		if (kkNotifyLogService == null) {
            kkNotifyLogService = (IKkNotifyLogService) getBean("kkNotifyLogService");
        }
		return kkNotifyLogService;
	}

	public ActionForward searchView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-add", true, getClass());
		// 记录操作日志
		if (UserOperHelper.allowLogOper("searchView", null)) {
			UserOperHelper.setModelNameAndModelDesc(
					getServiceImp(request).getModelName());
		}
		return getActionForward("searchView", mapping, form, request, response);
	}

	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request,
			HQLInfo hqlInfo) throws Exception {
		// TODO 自动生成的方法存根
		super.changeFindPageHQLInfo(request, hqlInfo);

		String whereBlock_result = "";

		// 组装页面上的数据
		StringBuffer whereBlock = new StringBuffer(
				hqlInfo.getWhereBlock() == null ? ""
						: hqlInfo.getWhereBlock());
		List<ParameterInfo> pfs = getParameters();
		List<HQLParameter> realList = new ArrayList<HQLParameter>();
		// 拼接hql
		for (ParameterInfo pf : pfs) {
			try {
				String requestValue = request.getParameter(pf.getRequestName());
				if (StringUtil.isNotNull(requestValue)) {
					if (ParameterInfo.PARSER_TYPE_DATE.equalsIgnoreCase(pf
							.getType())) {
						Date parse = DateUtil.convertStringToDate(requestValue,
								null);
						if (parse != null) {
							pf.setParseValue(parse);
							if (StringUtil.isNotNull(whereBlock.toString())) {
								whereBlock.append(" and ");
							}
							whereBlock.append(pf.getModelName() + " ");
							whereBlock.append(pf.getCondition() + " ");
							whereBlock.append(" :");
							whereBlock.append(pf.getHqlName() + " ");
							realList.add(new HQLParameter(pf.getHqlName(),
									parse));
						}
					} else if (ParameterInfo.PARSER_TYPE_STRING
							.equalsIgnoreCase(pf.getType())) {
						pf.setParseValue(requestValue);
						if (StringUtil.isNotNull(whereBlock.toString())) {
							whereBlock.append(" and ");
						}
						whereBlock.append(pf.getModelName() + " ");
						whereBlock.append(pf.getCondition() + " ");
						whereBlock.append(" :");
						whereBlock.append(pf.getHqlName());
						if ("like".equalsIgnoreCase(pf.getCondition())) {
							realList.add(new HQLParameter(pf.getHqlName(), "%"
									+ requestValue + "%"));
						} else {
							realList.add(new HQLParameter(pf.getHqlName(),
									requestValue));
						}
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				log.debug("转化数据出错~!1");
			}
		}
		whereBlock_result = whereBlock.toString();
		//刷选
		CriteriaValue cv = new CriteriaValue(request);
		//CriteriaUtil.buildHql(cv, hqlInfo, KkNotifyAction.class);
		// 按标题查询
		String fdName = request.getParameter("fdSearchName");
		if (StringUtil.isNull(fdName)) {
			fdName = cv.poll("fdSearchName");
		}
		if (StringUtil.isNotNull(fdName)) {
			whereBlock_result = StringUtil.linkString(whereBlock_result,
					" and ", "kkNotifyLog.fdSubject like :fdName");
			hqlInfo.setParameter("fdName", "%" + fdName + "%");
		}

		String[] sendTimes = request.getParameterValues("q.sendTime");
		if (sendTimes != null) {
			String start = sendTimes[0];
			String end = sendTimes[1];
			Date startTime = null;
			Date endTime = null;
			if (StringUtil.isNotNull(start)) {
				startTime = DateUtil.convertStringToDate(start,
						DateUtil.TYPE_DATETIME,
						UserUtil.getKMSSUser().getLocale());
			}
			if (StringUtil.isNotNull(end)) {
				endTime = DateUtil.convertStringToDate(end,
						DateUtil.TYPE_DATETIME,
						UserUtil.getKMSSUser().getLocale());
				Calendar c = Calendar.getInstance();
				c.setTime(endTime);
				c.add(Calendar.DAY_OF_MONTH, 1);
				endTime = c.getTime();
			}
			if (StringUtil.isNull(start) && StringUtil.isNull(end)) {

			} else if (StringUtil.isNull(start)) {
				whereBlock_result = StringUtil.linkString(whereBlock_result,
						" and ", "kkNotifyLog.sendTime <= :end");
				hqlInfo.setParameter("end", endTime);
			} else if (StringUtil.isNull(end)) {
				whereBlock_result = StringUtil.linkString(whereBlock_result,
						" and ", "kkNotifyLog.sendTime >= :start");
				hqlInfo.setParameter("start", startTime);
			} else {
				whereBlock_result = StringUtil.linkString(whereBlock_result,
						" and ",
						"kkNotifyLog.sendTime BETWEEN :start and :end");
				hqlInfo.setParameter("start", startTime);
				hqlInfo.setParameter("end", endTime);
			}
		}
		hqlInfo.setWhereBlock(whereBlock_result);
		hqlInfo.setParameter(realList);
		// System.out.println(hqlInfo.getWhereBlock());
	}

	/**
	 * 获取需要收集的request
	 * 
	 * @return
	 */
	public List<ParameterInfo> getParameters() {
		List<ParameterInfo> rtnList = new ArrayList<ParameterInfo>();
		rtnList.add(new ParameterInfo("sendTime1", "sendTime", "sendTime1",
				null, ParameterInfo.PARSER_TYPE_DATE, ">="));
		rtnList.add(new ParameterInfo("sendTime2", "sendTime", "sendTime2",
				null, ParameterInfo.PARSER_TYPE_DATE, "<="));
		rtnList.add(new ParameterInfo("rtnTime1", "rtnTime", "rtnTime1", null,
				ParameterInfo.PARSER_TYPE_DATE, ">="));
		rtnList.add(new ParameterInfo("rtnTime2", "rtnTime", "rtnTime2", null,
				ParameterInfo.PARSER_TYPE_DATE, "<="));
		rtnList.add(new ParameterInfo("fdSubject", "fdSubject", "fdSubject",
				null, ParameterInfo.PARSER_TYPE_STRING, "like"));
		return rtnList;
	}

	/**
	 * hql参数组装,request参数组装 仅仅只提供能不调用,就直接内部类
	 * 
	 * @author
	 * 
	 */
	class ParameterInfo {
		public static final String PARSER_TYPE_DATE = "date";
		public static final String PARSER_TYPE_STRING = "string";

		public ParameterInfo(String requestName, String modelName,
				String hqlName, Object parseValue, String type, String condition) {
			this.requestName = requestName;
			this.modelName = modelName;
			this.hqlName = hqlName;
			this.parseValue = parseValue;
			this.type = type;
			this.condition = condition;
		}

		private String requestName;
		private String modelName;
		private String hqlName;
		private Object parseValue;
		private String type;
		private String condition;

		public String getCondition() {
			return condition;
		}

		public void setCondition(String condition) {
			this.condition = condition;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getRequestName() {
			return requestName;
		}

		public void setRequestName(String requestName) {
			this.requestName = requestName;
		}

		public String getModelName() {
			return modelName;
		}

		public void setModelName(String modelName) {
			this.modelName = modelName;
		}

		public String getHqlName() {
			return hqlName;
		}

		public void setHqlName(String hqlName) {
			this.hqlName = hqlName;
		}

		public Object getParseValue() {
			return parseValue;
		}

		public void setParseValue(Object parseValue) {
			this.parseValue = parseValue;
		}

	}

	public ActionForward listFailTimes(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, Integer> failTimes = KkPostDataRunner.getFailTimes();

		request.setAttribute("failTimes", failTimes);
		// 记录日志
		if (UserOperHelper.allowLogOper("listFailTimes", null)) {
			UserOperHelper.setModelNameAndModelDesc(
					getServiceImp(request).getModelName());
			JSONObject json = JSONObject.fromObject(failTimes);
			String message = json.toString();
			UserOperHelper.logMessage(message);
		}
		return getActionForward("listFailTimes", mapping, form, request,
				response);

	}

	public ActionForward resetFailTimes(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		// String ip = request.getParameter("ip");
		String url = request.getParameter("url");

		if (StringUtil.isNotNull(url)) {
			KkPostDataRunner.resetFailTime(url);
		}
		// 记录日志
		if (UserOperHelper.allowLogOper("resetFailTimes", null)) {
			UserOperHelper.setModelNameAndModelDesc(
					getServiceImp(request).getModelName());
			UserOperHelper.logMessage(url);
			UserOperHelper.setOperSuccess(true);
		}
		return getActionForward("success", mapping, form, request, response);

	}
	
	public ActionForward resetCircuitBreaker(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		KkPostDataRunner.resetCircuitBreaker();
		return getActionForward("success", mapping, form, request, response);

	}
	
}
