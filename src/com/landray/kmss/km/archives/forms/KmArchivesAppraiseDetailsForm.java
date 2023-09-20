package com.landray.kmss.km.archives.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_Common;
import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.km.archives.model.KmArchivesAppraiseDetails;
import com.landray.kmss.km.archives.model.KmArchivesMain;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 档案借阅明细
  */
public class KmArchivesAppraiseDetailsForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;

	private KmArchivesMain fdArchives;

	// 档案ID
	private String fdArchivesId;
	// 档案名称
	private String fdArchivesName;
	// 档案编号
	private String fdArchivesNumber;

	private String fdOriginalDate;

	private String fdAfterAppraiseDate;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdArchivesId = null;
		fdArchivesName = null;
		fdArchivesNumber = null;
		fdOriginalDate = null;
		fdAfterAppraiseDate = null;
		fdArchives = new KmArchivesMain();
        super.reset(mapping, request);
    }

	@Override
    public Class<KmArchivesAppraiseDetails> getModelClass() {
		return KmArchivesAppraiseDetails.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("fdOriginalDate",
					new FormConvertor_Common("fdOriginalDate")
							.setDateTimeType(DateUtil.TYPE_DATE));
			toModelPropertyMap.put("fdAfterAppraiseDate",
					new FormConvertor_Common("fdAfterAppraiseDate")
							.setDateTimeType(DateUtil.TYPE_DATE));
			toModelPropertyMap.put("fdArchivesId", new FormConvertor_IDToModel(
					"fdArchives", KmArchivesMain.class));
        }
        return toModelPropertyMap;
    }


	public String getFdArchivesId() {
		return fdArchivesId;
	}

	public void setFdArchivesId(String fdArchivesId) {
		this.fdArchivesId = fdArchivesId;
	}

	public String getFdArchivesName() {
		return fdArchivesName;
	}

	public void setFdArchivesName(String fdArchivesName) {
		this.fdArchivesName = fdArchivesName;
	}

	public String getFdArchivesNumber() {
		return fdArchivesNumber;
	}

	public void setFdArchivesNumber(String fdArchivesNumber) {
		this.fdArchivesNumber = fdArchivesNumber;
	}

	public String getFdOriginalDate() {
		return fdOriginalDate;
	}

	public void setFdOriginalDate(String fdOriginalDate) {
		this.fdOriginalDate = fdOriginalDate;
	}

	public String getFdAfterAppraiseDate() {
		return fdAfterAppraiseDate;
	}

	public void setFdAfterAppraiseDate(String fdAfterAppraiseDate) {
		this.fdAfterAppraiseDate = fdAfterAppraiseDate;
	}

	public KmArchivesMain getFdArchives() {
		return fdArchives;
	}

	public void setFdArchives(KmArchivesMain fdArchives) {
		this.fdArchives = fdArchives;
	}


}
