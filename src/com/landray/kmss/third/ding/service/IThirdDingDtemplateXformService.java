package com.landray.kmss.third.ding.service;

import java.util.List;

import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;
import com.landray.kmss.third.ding.model.ThirdDingDtemplateXform;

import net.sf.json.JSONObject;

public interface IThirdDingDtemplateXformService extends IExtendDataService {

	public String addCommonTemplate(ThirdDingDtemplateXform temp, String fdLang,
			List<String> titleList, JSONObject param, List allReader)
			throws Exception;

	public void deleteTemplate(ThirdDingDtemplateXform temp)
			throws Exception;

	public void updateTemplateInfo() throws Exception;

}
