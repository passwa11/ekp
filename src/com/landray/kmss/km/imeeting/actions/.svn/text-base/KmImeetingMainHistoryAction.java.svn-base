package com.landray.kmss.km.imeeting.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.km.imeeting.model.KmImeetingMainHistory;
import com.landray.kmss.km.imeeting.service.IKmImeetingMainHistoryService;
import com.landray.kmss.sys.person.interfaces.PersonInfoServiceGetter;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 会议时间轴 Action
 */
public class KmImeetingMainHistoryAction extends ExtendAction {
	protected IKmImeetingMainHistoryService kmImeetingMainHistoryService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (kmImeetingMainHistoryService == null) {
            kmImeetingMainHistoryService = (IKmImeetingMainHistoryService) getBean("kmImeetingMainHistoryService");
        }
		return kmImeetingMainHistoryService;
	}

	/**
	 * 获取时间轴信息（历史操作）
	 */
	public ActionForward getHistorysByMeeting(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-getHistorysByMeeting", true,
				getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String meetingId = request.getParameter("meetingId");
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo
					.setWhereBlock("kmImeetingMainHistory.fdMeeting.fdId=:meetingId");
			hqlInfo.setParameter("meetingId", meetingId);
			hqlInfo.setOrderBy("fdOptDate");
			List<KmImeetingMainHistory> historys = getServiceImp(request)
					.findList(hqlInfo);
			JSONArray jsonArray = new JSONArray();
			for (KmImeetingMainHistory history : historys) {
				JSONObject object = genHistoryJson(history);
				jsonArray.add(object);
			}
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(jsonArray.toString());
		} catch (Exception e) {
			e.printStackTrace();
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-getHistorysByMeeting", true,
				getClass());
		return null;
	}

	/**
	 * 获取会议变更记录（历史操作）
	 */
	public ActionForward getChangeHistorysByMeeting(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-getChangeHistorysByMeeting", true,
				getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String meetingId = request.getParameter("meetingId");
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock("kmImeetingMainHistory.fdMeeting.fdId=:meetingId and (kmImeetingMainHistory.fdOptType='04' or kmImeetingMainHistory.fdOptType='05') ");
			hqlInfo.setParameter("meetingId", meetingId);
			hqlInfo.setOrderBy("fdOptDate");
			List<KmImeetingMainHistory> historys = getServiceImp(request)
					.findList(hqlInfo);
			JSONArray jsonArray = new JSONArray();
			for (KmImeetingMainHistory history : historys) {
				JSONObject object = genHistoryJson(history);
				jsonArray.add(object);
			}
			response.setHeader("content-type",
					"application/json;charset=utf-8");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(jsonArray.toString());
		} catch (Exception e) {
			e.printStackTrace();
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-getChangeHistorysByMeeting", true,
				getClass());
		return null;
	}

	/**
	 * 获取会议提前结束记录（历史操作）
	 */
	public ActionForward getEarlyEndHistoryByMeeting(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-getEarlyEndHistoryByMeeting", true,
				getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String meetingId = request.getParameter("meetingId");
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock(
					"kmImeetingMainHistory.fdMeeting.fdId=:meetingId and (kmImeetingMainHistory.fdOptType='08'");
			hqlInfo.setParameter("meetingId", meetingId);
			hqlInfo.setOrderBy("fdOptDate");
			List<KmImeetingMainHistory> historys = getServiceImp(request)
					.findList(hqlInfo);
			JSONArray jsonArray = new JSONArray();
			for (KmImeetingMainHistory history : historys) {
				JSONObject object = genHistoryJson(history);
				jsonArray.add(object);
			}
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(jsonArray.toString());
		} catch (Exception e) {
			e.printStackTrace();
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-getEarlyEndHistoryByMeeting", true,
				getClass());
		return null;
	}

	// 生成历史操作的JSON
	private JSONObject genHistoryJson(KmImeetingMainHistory history) {
		JSONObject object = new JSONObject();
		if (history.getFdOptPerson() != null) {
			object.put("optPersonId", history.getFdOptPerson().getFdId());
			object.put("optPersonName", history.getFdOptPerson().getFdName());
			String path = PersonInfoServiceGetter.getPersonHeadimageUrl(
					history.getFdOptPerson().getFdId(), null);
			object.put("optPersonHeadUrl",path);
		}else{
			String path = PersonInfoServiceGetter.getPersonHeadimageUrl(null, null);
			object.put("optPersonHeadUrl",path);
		}
		
		object.put("opt", history.getFdOptType());
		object.put("content", history.getFdOptContent());
		object.put("date", DateUtil.convertDateToString(history.getFdOptDate(),
				DateUtil.TYPE_DATETIME, ResourceUtil.getLocaleByUser()));
		if ("05".equals(history.getFdOptType())
				|| "08".equals(history.getFdOptType())) {
			//主持人头像
			JSONObject content = JSONObject.fromObject(history.getFdOptContent());
			String path = "";
			if(content!=null && content.containsKey("fdHostId")){
				path = PersonInfoServiceGetter.getPersonHeadimageUrl(content.getString("fdHostId"), null);
			}else{
				path = PersonInfoServiceGetter.getPersonHeadimageUrl(null, null);
			}
			object.put("hostHeadUrl", path);
		}
		
		return object;
	}

}
