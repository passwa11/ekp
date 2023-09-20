package com.landray.kmss.third.weixin.service.spring;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.web.taglib.xform.ICustomizeDataSourceWithRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import java.util.*;

public class ThirdWeixinContactOrgTypeDataSource implements ICustomizeDataSourceWithRequest {

	private static final Logger logger = LoggerFactory.getLogger(ThirdWeixinContactOrgTypeDataSource.class);

	private static ISysOrgElementService sysOrgElementService;

	public static ISysOrgElementService getSysOrgElementService(){
		if(sysOrgElementService==null){
			sysOrgElementService = (ISysOrgElementService) SpringBeanUtil.getBean("sysOrgElementService");
		}
		return sysOrgElementService;
	}

	@Override
	public void setRequest(ServletRequest request) {

	}

	@Override
	public Map<String, String> getOptions() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		try {
			HQLInfo info = new HQLInfo();
			info.setWhereBlock("fdOrgType=1 and fdIsExternal = :fdIsExternal");
			info.setParameter("fdIsExternal",true);
			info.setSelectBlock("fdId,fdName");
			List list  = getSysOrgElementService().findValue(info);
			for (Object o : list) {
				Object[] atts = (Object[])o;
				map.put((String)atts[0], (String)atts[1]);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return new HashMap<String, String>();
		}
		return map;
	}

	@Override
	public String getDefaultValue() {
		// TODO Auto-generated method stub
		return null;
	}


}
