package com.landray.kmss.sys.webservice2.service.spring;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.webservice2.model.SysWebserviceUser;
import com.landray.kmss.sys.webservice2.service.ISysWebserviceUserService;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.taglib.xform.ICustomizeDataSourceWithRequest;

public class SysWebservicePolicyDataService implements
		ICustomizeDataSourceWithRequest {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysWebservicePolicyDataService.class);

	private ISysWebserviceUserService userService = (ISysWebserviceUserService) SpringBeanUtil
			.getBean("sysWebserviceUserService");

	@Override
    public void setRequest(ServletRequest request) {

	}

	@Override
    public String getDefaultValue() {
		return null;
	}

	@Override
    public Map<String, String> getOptions() {
		Map<String,String> options = new HashMap<String, String>();
		try {
			List policyList = userService.findList(new HQLInfo());
			for (Iterator iterator = policyList.iterator(); iterator.hasNext();) {
				SysWebserviceUser policy = (SysWebserviceUser) iterator.next();
				if(StringUtil.isNotNull(policy.getFdName())){
					options.put(policy.getFdId(), "<span policy='" + policy.getFdId() + "'>" + policy.getFdName() + "</span>");
				}else{
					options.put(policy.getFdId(), "<span policy='" + policy.getFdId() + "'>" + policy.getFdUserName()+ "</span>");
				}
				
			}
		} catch (Exception e) {
			logger.error("获取webservice访问策略列表时错误",e);
		}
		return options;
	}

}
