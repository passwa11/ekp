/**
 * 
 */
package com.landray.kmss.km.smissive.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.km.smissive.model.KmSmissiveCirculation;
import com.landray.kmss.km.smissive.model.KmSmissiveMain;
import com.landray.kmss.util.UserUtil;

/**
 * @author Administrator
 * 
 */
@SuppressWarnings("serial")
public class KmSmissiveCirculationForm extends ExtendForm {
	private String fdSmissiveMainId;
	private String docSubject;
	private String fdCirculationIds;
	private String fdCirculationNames;
	private String docCreatorName;

	public String getFdSmissiveMainId() {
		return fdSmissiveMainId;
	}

	public void setFdSmissiveMainId(String fdSmissiveMainId) {
		this.fdSmissiveMainId = fdSmissiveMainId;
	}

	public String getDocSubject() {
		return docSubject;
	}

	public void setDocSubject(String docSubject) {
		this.docSubject = docSubject;
	}

	public String getFdCirculationIds() {
		return fdCirculationIds;
	}

	public void setFdCirculationIds(String fdCirculationIds) {
		this.fdCirculationIds = fdCirculationIds;
	}

	public String getFdCirculationNames() {
		return fdCirculationNames;
	}

	public void setFdCirculationNames(String fdCirculationNames) {
		this.fdCirculationNames = fdCirculationNames;
	}

	public String getDocCreatorName() {
		return docCreatorName;
	}

	public void setDocCreatorName(String docCreatorName) {
		this.docCreatorName = docCreatorName;
	}

	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		// TODO Auto-generated method stub
		fdSmissiveMainId = null;
		docSubject = null;
		fdCirculationIds = null;
		fdCirculationNames = null;
		docCreatorName = UserUtil.getUser().getFdName();
		super.reset(mapping, request);
	}

	@Override
    public Class<KmSmissiveCirculation> getModelClass() {
		// TODO Auto-generated method stub
		return KmSmissiveCirculation.class;
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("fdSmissiveMainId",
					new FormConvertor_IDToModel("fdSmissiveMain",
							KmSmissiveMain.class));

		}
		return toModelPropertyMap;
	}

}
