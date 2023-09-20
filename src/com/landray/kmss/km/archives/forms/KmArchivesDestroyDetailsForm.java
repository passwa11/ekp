package com.landray.kmss.km.archives.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.km.archives.model.KmArchivesDestroyDetails;
import com.landray.kmss.km.archives.model.KmArchivesMain;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 档案借阅明细
  */
public class KmArchivesDestroyDetailsForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;

	private KmArchivesMain fdArchives;

	// 档案ID
	private String fdArchivesId;
	// 档案名称
	private String fdArchivesName;
	// 档案编号
	private String fdArchivesNumber;
	// 所属分类名称
	private String fdCategoryName;
	// 归档时间
	private String fdReturnDate;
	// 归档人
	private String fdReturnPerson;


    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdArchivesId = null;
		fdArchivesName = null;
		fdArchivesNumber = null;
		fdCategoryName = null;
		fdReturnDate = null;
		fdReturnPerson = null;
		fdArchives = new KmArchivesMain();
        super.reset(mapping, request);
    }

	@Override
    public Class<KmArchivesDestroyDetails> getModelClass() {
		return KmArchivesDestroyDetails.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
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

	/**
	 * 所属分类
	 */
	public String getFdCategoryName() {
		return this.fdCategoryName;
	}

	/**
	 * 所属分类
	 */
	public void setFdCategoryName(String fdCategoryName) {
		this.fdCategoryName = fdCategoryName;
	}

	/**
	 * 归档日期
	 */
	public String getFdReturnDate() {
		return this.fdReturnDate;
	}

	/**
	 * 归档日期
	 */
	public void setFdReturnDate(String fdReturnDate) {
		this.fdReturnDate = fdReturnDate;
	}

	/**
	 * 归档人
	 */
	public String getFdReturnPerson() {
		return this.fdReturnPerson;
	}

	/**
	 * 归档人
	 */
	public void setFdReturnPerson(String fdReturnPerson) {
		this.fdReturnPerson = fdReturnPerson;
	}

	public KmArchivesMain getFdArchives() {
		return fdArchives;
	}

	public void setFdArchives(KmArchivesMain fdArchives) {
		this.fdArchives = fdArchives;
	}

}
