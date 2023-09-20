package com.landray.kmss.sys.portal.service.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.sys.portal.service.ISysPortalMainService;
import com.landray.kmss.util.IDGenerator;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.sso.client.util.StringUtil;

public class SysPortalMainTreeService implements IXMLDataBean {

	@Override
	public List getDataList(RequestContext requestInfo) throws Exception {
		List<Map<String, String>> rtnList = new ArrayList<Map<String, String>>();
		String parentId = requestInfo.getParameter("parentId");
		String beanName = "sysPortalMainTreeService";
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock(" fdId,fdName ");
		if (StringUtil.isNull(parentId)) {
			hqlInfo.setWhereBlock(" sysPortalMain.fdParent.fdId is null ");
		} else {
			hqlInfo.setWhereBlock(" sysPortalMain.fdParent.fdId =:parentId ");
			hqlInfo.setParameter("parentId", parentId);
		}

		/*
		 * 匿名门户的取值
		 * 
		 * @author 吴进 by 20191113
		 */
		String fdAnonymous = requestInfo.getParameter("fdAnonymous");
		if (StringUtil.isNotNull(fdAnonymous)) {
			hqlInfo.setWhereBlock(" sysPortalMain.fdAnonymous = :fdAnonymous ");
			Boolean fdAnonymousBoolean = Boolean.FALSE;
			if("1".equals(fdAnonymous)) {
                fdAnonymousBoolean = Boolean.TRUE;
            }
			hqlInfo.setParameter("fdAnonymous", fdAnonymousBoolean);
		}

		hqlInfo.setOrderBy(" fdOrder,fdId ");
		ISysPortalMainService sysPortalMainService = (ISysPortalMainService) SpringBeanUtil
				.getBean("sysPortalMainService");
		if (hqlInfo.getCheckParam(SysAuthConstant.CheckType.AllCheck) == null) {
			hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck,
					SysAuthConstant.AllCheck.DEFAULT); 
		}
		List<Object[]> list = sysPortalMainService.findValue(hqlInfo);
		for (int i = 0; i < list.size(); i++) {
			HashMap<String, String> node = new HashMap<String, String>();
			Object[] val = (list.get(i));
			node.put("text", val[1].toString());
			node.put("value", val[0].toString());
			node.put("beanName", beanName + "&parentId=" + val[0].toString() + "&s_seq="
					+ IDGenerator.generateID());
			node.put("nodeType", "CATEGORY");
			rtnList.add(node);
		}

		return rtnList;
	}
}
