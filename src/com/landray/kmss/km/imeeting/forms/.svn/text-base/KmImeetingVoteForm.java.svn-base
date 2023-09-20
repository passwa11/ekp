package com.landray.kmss.km.imeeting.forms;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.km.imeeting.model.KmImeetingMain;
import com.landray.kmss.km.imeeting.model.KmImeetingVote;
import com.landray.kmss.util.AutoArrayList;
import com.landray.kmss.web.action.ActionMapping;

public class KmImeetingVoteForm extends ExtendForm {

	private String docSubject;// 标题

	private List fdVoteObjs = new AutoArrayList(String.class);// 投票对象

	private List fdVoteOptions = new AutoArrayList(String.class);// 投票选项

	private String fdVoteOptionType;

	private String fdTemporaryId;// 临时ID

	private String fdMeetingId;// 会议ID

	private String fdVoteResult;// 投票结果

	public String getFdTemporaryId() {
		return fdTemporaryId;
	}

	public void setFdTemporaryId(String fdTemporaryId) {
		this.fdTemporaryId = fdTemporaryId;
	}

	public String getFdMeetingId() {
		return fdMeetingId;
	}

	public void setFdMeetingId(String fdMeetingId) {
		this.fdMeetingId = fdMeetingId;
	}

	public String getDocSubject() {
		return docSubject;
	}

	public void setDocSubject(String docSubject) {
		this.docSubject = docSubject;
	}

	public List getFdVoteObjs() {
		return fdVoteObjs;
	}

	public void setFdVoteObjs(List fdVoteObjs) {
		this.fdVoteObjs = fdVoteObjs;
	}

	public String getFdVoteOptionType() {
		if (fdVoteOptionType == null) {
			fdVoteOptionType = "default";
		}
		return fdVoteOptionType;
	}

	public void setFdVoteOptionType(String fdVoteOptionType) {
		this.fdVoteOptionType = fdVoteOptionType;
	}

	public List getFdVoteOptions() {
		return fdVoteOptions;
	}

	public void setFdVoteOptions(List fdVoteOptions) {
		this.fdVoteOptions = fdVoteOptions;
	}

	public String getFdVoteResult() {
		return fdVoteResult;
	}

	public void setFdVoteResult(String fdVoteResult) {
		this.fdVoteResult = fdVoteResult;
	}

	@Override
	public Class<KmImeetingVote> getModelClass() {
		return KmImeetingVote.class;
	}

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		docSubject = null;
		fdTemporaryId = null;
		fdMeetingId = null;
		fdVoteOptionType = null;
		fdVoteResult = null;
		fdVoteObjs.clear();
		fdVoteOptions.clear();
		super.reset(mapping, request);
	}

	private static FormToModelPropertyMap formToModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (formToModelPropertyMap == null) {
			formToModelPropertyMap = new FormToModelPropertyMap();
			formToModelPropertyMap.putAll(super.getToModelPropertyMap());
			formToModelPropertyMap.put("fdMeetingId",
					new FormConvertor_IDToModel(
							"fdMeetingMain", KmImeetingMain.class));
		}
		return formToModelPropertyMap;
	}

}
