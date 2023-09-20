package com.landray.kmss.km.imeeting.actions;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.km.imeeting.ImeetingConstant;
import com.landray.kmss.km.imeeting.model.KmImeetingTemplate;
import com.landray.kmss.km.imeeting.service.IKmImeetingMainFeedbackService;
import com.landray.kmss.km.imeeting.service.IKmImeetingMainService;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.hibernate.CacheMode;
import org.hibernate.query.Query;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class KmImeetingMainStatAction extends ExtendAction {
	protected IKmImeetingMainFeedbackService feedBackService;
	protected IKmImeetingMainService kmImeetingMainService;

	public IKmImeetingMainService getKmImeetingMainService() {
		if (kmImeetingMainService == null) {
			kmImeetingMainService = (IKmImeetingMainService) getBean("kmImeetingMainService");
		}
		return kmImeetingMainService;
	}

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (feedBackService == null) {
			feedBackService = (IKmImeetingMainFeedbackService) SpringBeanUtil.getBean("kmImeetingMainFeedbackService");
		}
		return feedBackService;
	}

	/**
	 * 会议分类统计图表（个人会议工作台使用）
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward cateStat(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONObject result = getCateStatResult(request);
		request.setAttribute("lui-source", result);
		return getActionForward("lui-source", mapping, form, request, response);
	}

	private JSONObject getCateStatResult(HttpServletRequest request)
			throws Exception {
		JSONObject result = new JSONObject();
		StringBuffer hql = new StringBuffer();
		hql.append("select sysCategoryMain.fdId,sysCategoryMain.fdName")
				.append(" from SysCategoryMain sysCategoryMain")
				.append(" where sysCategoryMain.fdModelName = :fdModelName")
				.append(" and sysCategoryMain.hbmParent is null");
		Query query = getKmImeetingMainService().getBaseDao()
				.getHibernateSession().createQuery(hql.toString());
		query.setCacheable(true);
		query.setCacheMode(CacheMode.NORMAL);
		query.setCacheRegion("km-imeeting");
		query.setParameter("fdModelName", KmImeetingTemplate.class.getName());
		List<Object[]> cates = query.list();
		JSONArray legendData = new JSONArray();
		JSONArray datas = new JSONArray();
		List l = findMyfeedback(request);
		List authOrgIds = UserUtil.getKMSSUser().getUserAuthInfo().getAuthOrgIds();
		if (!CollectionUtils.isEmpty(cates) && l.size() > 0) {
			JSONObject data = null;
			for (Object[] cate : cates) {
				hql = new StringBuffer();
				hql.append(
						"select distinct kmImeetingMain.fdId")
						.append(" from KmImeetingMain kmImeetingMain")
						.append(" left join kmImeetingMain.fdAttendPersons attendPersons ")
						.append(" where kmImeetingMain.fdTemplate.docCategory.fdModelName = :fdModelName")
						.append(" and kmImeetingMain.docCreateTime >= :startTime ")
						.append(" and kmImeetingMain.fdFinishDate<=:attendDate ")
						.append(" and kmImeetingMain.fdTemplate.docCategory.fdHierarchyId like :fdHierarchyId")
						.append(" and kmImeetingMain.docStatus !='00' and kmImeetingMain.docStatus!='41'")
						.append(" and (kmImeetingMain.fdHost.fdId=:userid or  kmImeetingMain.fdSummaryInputPerson.fdId=:userid or "
								+ HQLUtil.buildLogicIN("kmImeetingMain.fdId", l) + " or ("
								+ HQLUtil.buildLogicIN("attendPersons.fdId", authOrgIds)
								+ " and  kmImeetingMain.fdNeedFeedback = :fdNeedFeedback ))");
				query = getKmImeetingMainService().getBaseDao()
						.getHibernateSession().createQuery(hql.toString());
				query.setParameter("attendDate", new Date());
				query.setParameter("fdNeedFeedback", false);
				query.setParameter("fdHierarchyId", "x" + cate[0] + "x%");
				query.setParameter("fdModelName",
						KmImeetingTemplate.class.getName());
				query.setParameter("startTime", DateUtil.getBeginDayOfYear());
				query.setParameter("userid", UserUtil.getUser().getFdId());
				List list = query.list();
				if (!CollectionUtils.isEmpty(list)) {
					legendData.add(cate[1]);
					data = new JSONObject();
					data.put("name", cate[1]);
					data.put("value", list.size());
					datas.add(data);
				}
			}
		}
		commonPieChar(result, legendData, datas);
		return result;
	}

	private void commonPieChar(JSONObject result, JSONArray legendData,
			JSONArray datas) {
		JSONObject tooltip = new JSONObject();
		tooltip.put("trigger", "item");
		tooltip.put("formatter", "{b}: {c} ({d}%)");
		result.put("tooltip", tooltip);

		JSONObject legend = new JSONObject();
		legend.put("type", "scroll");
		legend.put("orient", "horizontal");
		// legend.put("right", 10);
		// legend.put("top", 20);
		legend.put("bottom", 0);
		legend.put("data", legendData);
		if (legendData.size() > 5) {
			JSONObject selected = new JSONObject();
			for (int i = 0; i < legendData.size(); i++) {
				if (i < 5) {
					selected.put(legendData.get(i), true);
				} else {
					selected.put(legendData.get(i), false);
				}
			}
			legend.put("selected", selected);
		}
		result.put("legend", legend);

		JSONArray series = new JSONArray();
		JSONObject serie = new JSONObject();
		serie.put("type", "pie");
		List<String> radius = new ArrayList<>(2);
		radius.add("15%");
		radius.add("45%");
		serie.put("radius", radius);
		serie.put("data", datas);
		series.add(serie);
		result.put("series", series);

		JSONArray colors = new JSONArray();
		colors.add("#8280FF");
		colors.add("#4C7BFD");
		colors.add("#19A4FF");
		colors.add("#FFC130");
		colors.add("#4CD964");
		colors.add("#FFCC00");
		result.put("color", colors);
		JSONObject toolbox = new JSONObject();
		toolbox.put("show", false);
		result.put("toolbox", toolbox);
	}

	/**
	 * 与会类型统计图表（个人会议工作台使用）
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward attendStat(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONObject result = getAttendStatResult(request);
		request.setAttribute("lui-source", result);
		return getActionForward("lui-source", mapping, form, request, response);
	}

	private JSONObject getAttendStatResult(HttpServletRequest request)
			throws Exception {
		JSONObject result = new JSONObject();
		JSONArray legendData = new JSONArray();
		legendData.add(ResourceUtil.getString("km-imeeting:kmImeeting.Participate.my"));
		legendData.add(ResourceUtil.getString("km-imeeting:kmImeeting.Host.my"));
		legendData.add(ResourceUtil.getString("km-imeeting:kmImeeting.Report.my"));
		legendData.add(ResourceUtil.getString("km-imeeting:kmImeeting.Emcc.my"));
		JSONArray datas = new JSONArray();
		JSONObject data = new JSONObject();
		Date yearFirst = DateUtil.getBeginDayOfYear();
		Date yearLast = DateUtil.getEndDayOfYear();
		// 本年我主持的
		Integer myHost = getKmImeetingMainService().getAttendStatCount(
				"myHaveAttend", "myHost", yearFirst, yearLast);
		// 本年我汇报的
		Integer myReport = getKmImeetingMainService().getAttendStatCount(
				"myHaveAttend", "myReport", yearFirst, yearLast);
		// 本年我组织的
		Integer myEmcc = getKmImeetingMainService().getAttendStatCount(
				"myHaveAttend", "myEmcc", yearFirst, yearLast);
		// 本年我参与的
		Integer myPart = getKmImeetingMainService().getAttendStatCount(
				"myHaveAttend", "myPart", yearFirst, yearLast);
		data.put("name", ResourceUtil.getString("km-imeeting:kmImeeting.Participate.my"));
		data.put("value", myPart);
		datas.add(data);
		data = new JSONObject();
		data.put("name",
				ResourceUtil.getString("km-imeeting:kmImeeting.Host.my"));
		data.put("value", myHost);
		datas.add(data);
		data = new JSONObject();
		data.put("name",
				ResourceUtil.getString("km-imeeting:kmImeeting.Report.my"));
		data.put("value", myReport);
		datas.add(data);
		data = new JSONObject();
		data.put("name",
				ResourceUtil.getString("km-imeeting:kmImeeting.Emcc.my"));
		data.put("value", myEmcc);
		datas.add(data);
		if (myEmcc == 0 && myHost == 0 && myPart == 0 && myReport == 0) {
			datas.clear();
		}
		commonPieChar(result, legendData, datas);
		return result;
	}

	/**
	 * 会议参与统计
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward attendStatCount(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String myType = request.getParameter("myType");
		String mydoc = request.getParameter("mydoc");
		String date = request.getParameter("date");
		Date start = DateUtil.getBeginDayOfMonth();
		Date end = DateUtil.getEndDayOfMonth();
		if ("year".equals(date)) {
			start = DateUtil.getBeginDayOfYear();
			end = DateUtil.getEndDayOfYear();
		}
		Integer count = getKmImeetingMainService().getAttendStatCount(myType,
				mydoc, start, end);
		JSONObject result = new JSONObject();
		result.put("count", count);
		request.setAttribute("lui-source", result);
		return getActionForward("lui-source", mapping, form, request, response);
	}


	public ActionForward showStat(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-showStat", true, getClass());
		JSONArray jsonArray = new JSONArray();
		JSONObject statInfo = new JSONObject();
		statInfo.put("text",
				ResourceUtil.getString("showstat.text", "km-imeeting"));
		statInfo.put("count", getMeetingCountByCreator(request));
		jsonArray.add(statInfo);
		request.setAttribute("lui-source", jsonArray);
		TimeCounter.logCurrentTime("Action-showStat", false, getClass());
		return getActionForward("lui-source", mapping, form, request, response);
	}

	@SuppressWarnings("unchecked")
	private long getMeetingCountByCreator(HttpServletRequest request) {
		long count = 0;
		HQLInfo hqlInfo = new HQLInfo();
		try {
			List<Object> resultList = getMyImeetingList(request, hqlInfo);
			Object result = resultList.get(0);
			count = Long.parseLong(result != null ? result.toString() : "0");
		} catch (Exception e) {
			log.info("获取参与的会议数量出错", e);
		}
		return count;
	}

	/**
	 * 我的会议HQL
	 */
	private List getMyImeetingList(HttpServletRequest request, HQLInfo hqlInfo)
			throws Exception {

		List l = findMyfeedbackMeeting(request);
		List authOrgIds = UserUtil.getKMSSUser().getUserAuthInfo().getAuthOrgIds();
		// 去除重复
		hqlInfo.setDistinctType(HQLInfo.DISTINCT_YES);
		hqlInfo.setSelectBlock("kmImeetingMain.fdId");
		hqlInfo.setGettingCount(true);
		hqlInfo.setJoinBlock(" left join kmImeetingMain.fdAttendPersons attendPersons left join kmImeetingMain.fdHost fdHost left join kmImeetingMain.fdSummaryInputPerson fdSummaryInputPerson");
		// 参加的所有会议（包括参与者中有我/TA，我/TA主持，我/TA纪要）
		String whereBlock = " kmImeetingMain.docStatus !='00' ";
		if (l.size() > 0) {
			whereBlock = StringUtil.linkString(whereBlock, " and ",
					" ( fdHost.fdId=:userid or fdSummaryInputPerson.fdId=:userid "
							+ " or " + HQLUtil.buildLogicIN("kmImeetingMain.fdId", l)
							+ " or (" + HQLUtil.buildLogicIN("attendPersons.fdId", authOrgIds)
							+ " and  kmImeetingMain.fdNeedFeedback = :fdNeedFeedback ))");
		} else {
			whereBlock = StringUtil.linkString(whereBlock, " and ",
					" ( fdHost.fdId=:userid or fdSummaryInputPerson.fdId=:userid "
							+ " or (" + HQLUtil.buildLogicIN("attendPersons.fdId", authOrgIds)
							+ " and  kmImeetingMain.fdNeedFeedback = :fdNeedFeedback ))");
		}
		hqlInfo.setParameter("fdNeedFeedback", Boolean.TRUE);
		whereBlock += " and kmImeetingMain.fdFinishDate<=:attendDate";
		hqlInfo.setParameter("userid", UserUtil.getUser().getFdId());
		hqlInfo.setParameter("attendDate", new Date());
		hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck, SysAuthConstant.AllCheck.DEFAULT);
		hqlInfo.setWhereBlock(whereBlock);
		List list = this.getKmImeetingMainService().getBaseDao().findValue(hqlInfo);
		return list;
	}

	private List findMyfeedbackMeeting(HttpServletRequest request)
			throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("kmImeetingMainFeedback.fdMeeting.fdId");
		hqlInfo.setWhereBlock(
				" kmImeetingMainFeedback.docCreator.fdId=:userId and kmImeetingMainFeedback.fdOperateType='" + ImeetingConstant.MEETING_FEEDBACK_OPT_ATTEND + "'");
		hqlInfo.setParameter("userId", UserUtil.getUser().getFdId());
		List fs = getServiceImp(request).findList(hqlInfo);
		return fs;
	}

	private List findMyfeedback(HttpServletRequest request) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("kmImeetingMainFeedback.fdMeeting.fdId");
		hqlInfo.setWhereBlock(" kmImeetingMainFeedback.docCreator.fdId=:userId");
		hqlInfo.setParameter("userId", UserUtil.getUser().getFdId());
		List fs = getServiceImp(null).findList(hqlInfo);
		return fs;
	}

}
