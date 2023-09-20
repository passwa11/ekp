package com.landray.kmss.third.weixin.work.spi.service.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.third.pda.model.PdaModuleConfigMain;
import com.landray.kmss.third.pda.service.IPdaModuleConfigMainService;
import com.landray.kmss.third.weixin.work.model.WeixinWorkConfig;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

public class WxworkPdaModelSelectDialog implements IXMLDataBean {
	@Override
    public List getDataList(RequestContext requestInfo) throws Exception {
		IPdaModuleConfigMainService pdaModuleConfigMainService = (IPdaModuleConfigMainService)SpringBeanUtil.getBean("pdaModuleConfigMainService");
		List cms = pdaModuleConfigMainService.findList("fdStatus='1' and fdSubMenuType='doc'","fdOrder asc");
		List rtnVal = new ArrayList();
		for(int i=0;i<cms.size();i++){
			PdaModuleConfigMain cm = (PdaModuleConfigMain)cms.get(i);
			Map node = new HashMap();
			node.put("name", cm.getFdName());
			node.put("id", getUrl(cm.getFdSubDocLink()));
			rtnVal.add(node);
		}

		return rtnVal;
	}
	
	private String getUrl(String url){
//		if(url.indexOf("?")!=-1){
//			url += "&oauth="+WxConstant.OAUTH_EKP_FLAG; 
//		}else{
//			url += "?oauth="+WxConstant.OAUTH_EKP_FLAG; 
//		}
		
		String domainName = WeixinWorkConfig.newInstance().getWxDomain();
		if (StringUtils.isNotEmpty(domainName)) {
			url = domainName + url;
		} else {
			url = StringUtil.formatUrl(url);
		}
		return url;
	}
}
