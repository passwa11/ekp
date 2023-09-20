/**
 * 
 */
package com.landray.kmss.third.pda.forms;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.third.pda.model.PdaDataExtendConfig;
import com.landray.kmss.util.AutoArrayList;

@SuppressWarnings("serial")
public class PdaDataExtendConfigForm extends ExtendForm {

	private String fdName;

	private String fdValue;

	private String fdKey;

	private String fdType;

	@SuppressWarnings("unchecked")
	private List<PdaDataExtendConfig> pdaDataExtendConfigList = new AutoArrayList(
			PdaDataExtendConfig.class);

	public String getFdName() {
		return fdName;
	}

	public List<PdaDataExtendConfig> getPdaDataExtendConfigList() {
		return pdaDataExtendConfigList;
	}

	public void setPdaDataExtendConfigList(
			List<PdaDataExtendConfig> pdaDataExtendConfigList) {
		this.pdaDataExtendConfigList = pdaDataExtendConfigList;
	}

	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	public String getFdValue() {
		return fdValue;
	}

	public void setFdValue(String fdValue) {
		this.fdValue = fdValue;
	}

	public String getFdKey() {
		return fdKey;
	}

	public void setFdKey(String fdKey) {
		this.fdKey = fdKey;
	}

	public String getFdType() {
		return fdType;
	}

	/**
	 * @param fdType
	 *            要设置的 fdType
	 */
	public void setFdType(String fdType) {
		this.fdType = fdType;
	}

	@Override
	public Class<?> getModelClass() {
		return PdaDataExtendConfig.class;
	}

	@Override
    @SuppressWarnings("unchecked")
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		fdName = null;
		fdKey = null;
		fdValue = null;
		fdType = null;
		pdaDataExtendConfigList = new AutoArrayList(PdaDataExtendConfig.class);
	}
}
