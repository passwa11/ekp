package com.landray.kmss.sys.restservice.server.service.spring;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.restservice.server.model.SysRestserviceServerPolicy;
import com.landray.kmss.sys.restservice.server.service.ISysRestserviceServerPolicyService;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.taglib.xform.ICustomizeDataSourceWithRequest;

public class SysRestserviceServerPolicyDataService implements
		ICustomizeDataSourceWithRequest {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysRestserviceServerPolicyDataService.class);

	private ISysRestserviceServerPolicyService policyService = (ISysRestserviceServerPolicyService) SpringBeanUtil
			.getBean("sysRestserviceServerPolicyService");

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
			List policyList = policyService.findList(new HQLInfo());
			for (Iterator iterator = policyList.iterator(); iterator.hasNext();) {
				SysRestserviceServerPolicy policy = (SysRestserviceServerPolicy) iterator.next();
				if(StringUtil.isNotNull(policy.getFdName())){
					options.put(policy.getFdId(), "<span policy='" + policy.getFdId() + "'>" + policy.getFdName() + "</span>");
				}
				
			}
		} catch (Exception e) {
			logger.error("获取RestService访问策略列表时错误",e);
		}
		return options;
	}

}
