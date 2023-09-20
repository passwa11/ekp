package com.landray.kmss.third.pda.service.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.third.pda.util.PdaModuleConfigUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;

public class PdaDictModelSelectDialog implements IXMLDataBean {
	@Override
	public List getDataList(RequestContext requestInfo) throws Exception {
		String urlPrefix = requestInfo.getParameter("urlPrefix");
		List rtnVal = new ArrayList();
		if (StringUtil.isNull(urlPrefix)) {
            return rtnVal;
        }
		List<SysDictModel> dictModelList = PdaModuleConfigUtil
				.getDictModelList(urlPrefix);
		for (SysDictModel dictModel : dictModelList) {
			String insertText = ResourceUtil.getString(dictModel
					.getMessageKey(), requestInfo.getLocale());
			Map node = new HashMap();
			node.put("name", insertText);
			node.put("id", dictModel.getModelName());
			rtnVal.add(node);
		}
		return rtnVal;
	}
}
