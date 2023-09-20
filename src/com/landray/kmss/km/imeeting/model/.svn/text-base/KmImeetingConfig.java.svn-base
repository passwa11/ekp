package com.landray.kmss.km.imeeting.model;

import java.util.Map;

import com.landray.kmss.sys.appconfig.model.BaseAppConfig;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;

public class KmImeetingConfig extends BaseAppConfig {

	public KmImeetingConfig() throws Exception {
		super();
		if (StringUtil.isNull(getUnShow())) {
			setUnShow("true");
		}

		Map<String, String> dataMap = getDataMap();
		if (!dataMap.containsKey("feedbackViewPerson")) {
			if (StringUtil.isNull(getFeedbackViewPerson())) {
				setFeedbackViewPerson("1;2;3;4;5;6;7;");
			}
		}
	}

	// 纪要通知对象默认值
	public String getSummaryNotifyPerson() {
		return getValue("summaryNotifyPerson");
	}

	public void setSummaryNotifyPerson(String summaryNotifyPerson) {
		setValue("summaryNotifyPerson", summaryNotifyPerson);
	}

	// 资源冲突处理方式
	public String getUnShow() {
		return getValue("unShow");
	}

	public void setUnShow(String unShow) {
		setValue("unShow", unShow);
	}

	@Override
    public String getJSPUrl() {
		return "/km/imeeting/km_imeeting_config/kmImeetingConfig.jsp";
	}

	public String getSetICS() {
		return getValue("setICS");
	}

	public void setSetICS(String setICS) {
		setValue("setICS", setICS);
	}
	
	/**
	 * 回执查看人员 1：会议发起人 2：会议组织人 3：会议主持人 4：参加人员(包含与会、列席人员、汇报人、建议列席单位人员、建议旁听单位人员)
	 * 5：抄送人员 6：纪要人员
	 * 
	 * @return
	 */
	public String getFeedbackViewPerson() {
		return getValue("feedbackViewPerson");
	}

	public void setFeedbackViewPerson(String feedbackViewPerson) {
		setValue("feedbackViewPerson", feedbackViewPerson);
	}

	private static final String USE_CLOUD_ON = "2";
	private static final String USE_CLOUD_OFF = "1";
	private static final String USE_CYCLICITY_NO = "1";
	private static final String USE_CYCLICITY_ALL = "2";
	private static final String USE_CYCLICITY_OTHER = "3";

	/**
	 * 使用周期性会议设置 <br/>
	 * 1为不使用 <br/>
	 * 2为所有人可使用 <br/>
	 * 3为指定人可使用
	 */
	public String getUseCyclicity() {
		String useCyclicity = getValue("useCyclicity");
		if (StringUtil.isNull(useCyclicity)) {
			useCyclicity = USE_CYCLICITY_NO;
		}
		return useCyclicity;
	}

	public void setUseCyclicity(String useCyclicity) {
		setValue("useCyclicity", useCyclicity);
	}

	public String getUseCyclicityPersonName() {
		return getValue("useCyclicityPersonName");
	}

	public void setUseCyclicityPersonName(String useCyclicityPersonName) {
		setValue("useCyclicityPersonName", useCyclicityPersonName);
	}

	public String getUseCyclicityPersonId() {
		return getValue("useCyclicityPersonId");
	}

	public void setUseCyclicityPersonId(String useCyclicityPersonId) {
		setValue("useCyclicityPersonId", useCyclicityPersonId);
	}
	
	public String getUseClodMng() {
		String useClodMng = getValue("useClodMng");
		if (StringUtil.isNull(useClodMng)) {
			setValue("useClodMng", USE_CLOUD_OFF);
		}
		return getValue("useClodMng");
	}
	
	public void setUseClodMng(String useClodMng) {
		setValue("useClodMng", useClodMng);
	}

	@Override
	public String getModelDesc() {
		return ResourceUtil.getString("km-imeeting:appConfig.KmImeetingConfig");
	}

}
