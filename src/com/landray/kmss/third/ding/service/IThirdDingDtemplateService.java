package com.landray.kmss.third.ding.service;

import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;
import com.landray.kmss.third.ding.model.ThirdDingDtemplate;

public interface IThirdDingDtemplateService extends IExtendDataService {
	/**
	 * @return
	 * @throws Exception
	 *             是否存在通用的流程模板
	 */
	public ThirdDingDtemplate updateCommonTemplate(String fdLang)
			throws Exception;

	/**
	 * @return
	 * @throws Exception
	 *             添加通用的流程模板
	 */
	public String addCommonTemplate(ThirdDingDtemplate template, String fdLang)
			throws Exception;
}
