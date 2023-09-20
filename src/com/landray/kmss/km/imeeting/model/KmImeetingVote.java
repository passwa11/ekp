package com.landray.kmss.km.imeeting.model;

import java.util.ArrayList;
import java.util.List;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.km.imeeting.forms.KmImeetingVoteForm;

public class KmImeetingVote extends BaseModel {

	private String docSubject;// 标题

	private List<String> fdVoteObjs = new ArrayList<>();// 投票对象

	private List<String> fdVoteOptions = new ArrayList<>();// 投票选项

	private String fdVoteOptionType;// 选项类型：default默认；custom自定义

	private String fdTemporaryId;// 临时ID

	private String fdVoteResult;// 投票结果

	public String getFdVoteResult() {
		return (String) readLazyField("fdVoteResult",
				fdVoteResult);
	}

	public void setFdVoteResult(String fdVoteResult) {
		this.fdVoteResult = (String) writeLazyField(
				"fdVoteResult", this.fdVoteResult,
				fdVoteResult);
	}

	private KmImeetingMain fdMeetingMain;

	public String getDocSubject() {
		return docSubject;
	}

	public void setDocSubject(String docSubject) {
		this.docSubject = docSubject;
	}

	public List<String> getFdVoteObjs() {
		return fdVoteObjs;
	}

	public void setFdVoteObjs(List<String> fdVoteObjs) {
		this.fdVoteObjs = fdVoteObjs;
	}

	public List<String> getFdVoteOptions() {
		return fdVoteOptions;
	}

	public void setFdVoteOptions(List<String> fdVoteOptions) {
		this.fdVoteOptions = fdVoteOptions;
	}

	public String getFdVoteOptionType() {
		return fdVoteOptionType;
	}

	public void setFdVoteOptionType(String fdVoteOptionType) {
		this.fdVoteOptionType = fdVoteOptionType;
	}

	public String getFdTemporaryId() {
		return fdTemporaryId;
	}

	public void setFdTemporaryId(String fdTemporaryId) {
		this.fdTemporaryId = fdTemporaryId;
	}

	public KmImeetingMain getFdMeetingMain() {
		return fdMeetingMain;
	}

	public void setFdMeetingMain(KmImeetingMain fdMeetingMain) {
		this.fdMeetingMain = fdMeetingMain;
	}

	@Override
	public Class<KmImeetingVoteForm> getFormClass() {
		return KmImeetingVoteForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("fdMeetingMain.fdId", "fdMeetingId");
		}
		return toFormPropertyMap;
	}
}
