package com.landray.kmss.km.imeeting.subordinate;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.km.imeeting.model.KmImeetingMain;
import com.landray.kmss.km.imeeting.service.IKmImeetingMainFeedbackService;
import com.landray.kmss.sys.subordinate.plugin.AbstractSubordinateProvider;
import com.landray.kmss.sys.subordinate.plugin.PropertyItem;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

/**
 * 会议安排提供者
 * 
 * @author 潘永辉 2019年3月13日
 *
 */
public class KmImeetingMainProvider extends AbstractSubordinateProvider {
	private IKmImeetingMainFeedbackService kmImeetingMainFeedbackService;
	protected IKmImeetingMainFeedbackService getKmImeetingMainFeedbackService() {
		if (kmImeetingMainFeedbackService == null) {
			kmImeetingMainFeedbackService = (IKmImeetingMainFeedbackService) SpringBeanUtil.getBean("kmImeetingMainFeedbackService");
		}
		return kmImeetingMainFeedbackService;
	}

	@Override
	public List<PropertyItem> items() {
		List<PropertyItem> items = new ArrayList<PropertyItem>();
		// 参与的会议
		items.add(new PropertyItem("fdAttendPersons", ResourceUtil.getString("km-imeeting:subordinate.kmImeetingMain.fdAttendPersons")));
		// 主持的会议
		items.add(new PropertyItem("fdHost", ResourceUtil.getString("km-imeeting:subordinate.kmImeetingMain.fdHost")));
		// 组织的会议
		items.add(new PropertyItem("fdEmcee", ResourceUtil.getString("km-imeeting:subordinate.kmImeetingMain.fdEmcee")));
		// 发起的会议
		items.add(new PropertyItem("docCreator", ResourceUtil.getString("km-imeeting:subordinate.kmImeetingMain.docCreator")));
		return items;
	}

	@Override
	public void changeFindPageHQLInfo(RequestContext request, HQLInfo hqlInfo) throws Exception {
		String[] meetingStatuses = request.getParameterValues("q.meetingStatus");
		buildMeetingStatusHql(request.getRequest(), hqlInfo, meetingStatuses);
		HQLHelper.by(request.getRequest()).buildHQLInfo(hqlInfo, KmImeetingMain.class);
	}
	
	@Override
	public IBaseModel view(RequestContext request, IBaseModel model) throws Exception {
		request.setAttribute("type", "attend");
		return super.view(request, model);
	}

	/**
	 * 会议状态HQL
	 */
	private void buildMeetingStatusHql(HttpServletRequest request, HQLInfo hqlInfo, String[] meetingStatuses) throws Exception {
		if (meetingStatuses == null || meetingStatuses.length < 1) {
			return;
		}
		String statusWhereBlock = "";
		HashMap<String, Object> statusParameters = new HashMap<String, Object>();
		for (String status : meetingStatuses) {
			// 已召开
			if ("hold".equals(status)) {
				statusWhereBlock = StringUtil.linkString(
								statusWhereBlock,
								" or ",
								" kmImeetingMain.fdFinishDate<=:afterFdHoldDate and kmImeetingMain.docStatus = '30' ");
				statusParameters.put("afterFdHoldDate", new Date());
			}
			// 进行中的会议
			if ("holding".equals(status)) {
				statusWhereBlock = StringUtil.linkString(
								statusWhereBlock,
								" or ",
								" kmImeetingMain.fdHoldDate<=:fdDate and kmImeetingMain.fdFinishDate>=:fdDate and kmImeetingMain.docStatus = '30' ");
				Date now = new Date();
				statusParameters.put("fdDate", now);
			}
			// 未召开会议
			if ("unHold".equals(status)) {
				statusWhereBlock = StringUtil.linkString(
								statusWhereBlock,
								" or ",
								" kmImeetingMain.fdHoldDate>=:beforeFdHoldDate and kmImeetingMain.docStatus = '30' ");
				statusParameters.put("beforeFdHoldDate", new Date());
			}
			// 废弃、草稿、待审、发布、取消
			if ("00".equals(status) || "10".equals(status)
					|| "11".equals(status) || "20".equals(status)
					|| "30".equals(status) || "41".equals(status)) {
				statusWhereBlock = StringUtil.linkString(statusWhereBlock,
						" or ", " kmImeetingMain.docStatus=:docStatus" + status + " ");
				statusParameters.put("docStatus" + status, status);
			}
		}
		if (StringUtil.isNotNull(statusWhereBlock)) {
			statusWhereBlock = " ( " + statusWhereBlock + " ) ";
			String whereBlock = hqlInfo.getWhereBlock();
			whereBlock = StringUtil.linkString(whereBlock, " and ", statusWhereBlock);
			hqlInfo.setWhereBlock(whereBlock);
			for (String key : statusParameters.keySet()) {
				hqlInfo.setParameter(key, statusParameters.get(key));
			}
		}
	}

}
