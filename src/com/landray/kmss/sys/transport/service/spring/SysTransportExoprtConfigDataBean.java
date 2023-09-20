package com.landray.kmss.sys.transport.service.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.sys.transport.dao.ISysTransportExportDao;
import com.landray.kmss.sys.transport.model.SysTransportExportConfig;

public class SysTransportExoprtConfigDataBean implements IXMLDataBean
{
	private ISysTransportExportDao dao;
	private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());
	@Override
	public List getDataList(RequestContext requestInfo)
			throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setModelName("SysTransportExportConfig");
		hqlInfo.setOrderBy("fdId desc");
		String fdModelName = requestInfo.getParameter("fdModelName");
		hqlInfo.setWhereBlock("sysTransportExportConfig.fdModelName=:fdModelName");
		hqlInfo.setParameter("fdModelName", fdModelName);
		List items = getDao().findList(hqlInfo);
		SysTransportExportConfig config;
		List datas = new ArrayList();
		if (items == null) {
            return datas;
        }
		for (Iterator iter = items.iterator(); iter.hasNext();) {
			Map node = new HashMap();
			config = (SysTransportExportConfig) iter.next();
			node.put("name", config.getFdName());
			node.put("id", config.getFdId());
			datas.add(node);
		}
		return datas;
	}
	public ISysTransportExportDao getDao() {
		return dao;
	}
	public void setDao(ISysTransportExportDao dao) {
		this.dao = dao;
	}

}
