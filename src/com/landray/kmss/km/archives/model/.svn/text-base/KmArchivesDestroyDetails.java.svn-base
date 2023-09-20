package com.landray.kmss.km.archives.model;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.km.archives.forms.KmArchivesDestroyDetailsForm;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyModel;
import com.landray.kmss.util.DateUtil;

/**
  * 档案借阅明细
  */
public class KmArchivesDestroyDetails extends BaseModel implements ISysNotifyModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private KmArchivesMain fdArchives;

	private KmArchivesDestroy docMain;

	@Override
    public Class<KmArchivesDestroyDetailsForm> getFormClass() {
		return KmArchivesDestroyDetailsForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("fdArchives.fdId", "fdArchivesId");
			toFormPropertyMap.put("fdArchives.docNumber", "fdArchivesNumber");
			toFormPropertyMap.put("fdArchives.docSubject", "fdArchivesName");
			toFormPropertyMap.put("fdArchives.docTemplate.fdName",
					"fdCategoryName");
			toFormPropertyMap.put("fdArchives.fdFileDate",
					new ModelConvertor_Common("fdReturnDate")
							.setDateTimeType(DateUtil.TYPE_DATE));
			toFormPropertyMap.put("fdArchives.docCreator.fdName",
					"fdReturnPerson");
			toFormPropertyMap.put("fdArchives", "fdArchives");
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
    }

	public KmArchivesDestroy getDocMain() {
		return docMain;
	}

	public void setDocMain(KmArchivesDestroy docMain) {
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
