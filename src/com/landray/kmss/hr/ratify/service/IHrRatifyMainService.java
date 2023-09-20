package com.landray.kmss.hr.ratify.service;

import java.util.List;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.hr.ratify.model.HrRatifyMain;
import com.landray.kmss.hr.ratify.model.HrRatifyTemplate;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public interface IHrRatifyMainService extends IExtendDataService {

    public abstract List<HrRatifyMain> findByDocTemplate(HrRatifyTemplate docTemplate) throws Exception;

	public String getCount(String type, Boolean isDraft) throws Exception;

	public String getCount(HQLInfo hqlInfo) throws Exception;

	public void setDocNumber(HrRatifyMain mainModel) throws Exception;

	public void addLog(HrRatifyMain mainModel) throws Exception;

	public abstract void deleteEntity(HrRatifyMain mainModel) throws Exception;

	public void updateFeedbackPeople(HrRatifyMain main, List notifyTarget)
			throws Exception;

	/**
	 * <p>人事流程移动端首页</p>
	 * @return
	 * @author sunj
	 */
	public abstract JSONArray getRatifyMobileIndex() throws Exception;

	/**
	 * 人事流程列表页流程数量
	 */
	public abstract JSONObject getCount(String modelName) throws Exception;

	public abstract HrRatifyMain findEntity(String fdId) throws Exception;
}
