package com.landray.kmss.km.archives.model;

import java.util.Date;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.km.archives.forms.KmArchivesAppraiseDetailsForm;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyModel;
import com.landray.kmss.util.DateUtil;

/**
  * 档案借阅明细
  */
public class KmArchivesAppraiseDetails extends BaseModel implements ISysNotifyModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

	private Date fdOriginalDate;

	private Date fdAfterAppraiseDate;

    private KmArchivesMain fdArchives;

	private KmArchivesAppraise docMain;

	@Override
    public Class<KmArchivesAppraiseDetailsForm> getFormClass() {
		return KmArchivesAppraiseDetailsForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("fdOriginalDate",
					new ModelConvertor_Common("fdOriginalDate")
							.setDateTimeType(DateUtil.TYPE_DATE));
			toFormPropertyMap.put("fdAfterAppraiseDate",
					new ModelConvertor_Common("fdAfterAppraiseDate")
							.setDateTimeType(DateUtil.TYPE_DATE));
			toFormPropertyMap.put("fdArchives.fdId", "fdArchivesId");
			toFormPropertyMap.put("fdArchives.docNumber", "fdArchivesNumber");
			toFormPropertyMap.put("fdArchives.docSubject", "fdArchivesName");
			toFormPropertyMap.put("fdArchives", "fdArchives");
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
    }

	/**
	 * 原档案有效期
	 */
	public Date getFdOriginalDate() {
		return fdOriginalDate;
	}

	/**
	 * 原档案有效期
	 */
	public void setFdOriginalDate(Date fdOriginalDate) {
		this.fdOriginalDate = fdOriginalDate;
	}

	/**
	 * 鉴定后有效期
	 */
	public Date getFdAfterAppraiseDate() {
		return fdAfterAppraiseDate;
	}

	/**
	 * 鉴定后有效期
	 */
	public void setFdAfterAppraiseDate(Date fdAfterAppraiseDate) {
		this.fdAfterAppraiseDate = fdAfterAppraiseDate;
	}

	public KmArchivesAppraise getDocMain() {
		return docMain;
	}

	public void setDocMain(KmArchivesAppraise docMain) {
		this.docMain = docMain;
	}

	/**
	 * 档案
	 */
    public KmArchivesMain getFdArchives() {
        return this.fdArchives;
    }

    /**
     * 档案
     */
    public void setFdArchives(KmArchivesMain fdArchives) {
        this.fdArchives = fdArchives;
    }


}
