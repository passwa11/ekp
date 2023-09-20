package com.landray.kmss.third.pda.service.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.third.pda.constant.PdaModuleConfigConstant;
import com.landray.kmss.third.pda.service.IPdaModuleConfigMainService;
import com.landray.kmss.util.StringUtil;

public class PdaModuleSelectLocal implements IXMLDataBean {

	private IPdaModuleConfigMainService pdaModuleConfigMainService;

	public IPdaModuleConfigMainService getPdaModuleConfigMainService() {
		return pdaModuleConfigMainService;
	}

	public void setPdaModuleConfigMainService(
			IPdaModuleConfigMainService pdaModuleConfigMainService) {
		this.pdaModuleConfigMainService = pdaModuleConfigMainService;
	}

	@Override
	public List getDataList(RequestContext requestInfo) throws Exception {
		String extendId = requestInfo.getParameter("curid");
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo
				.setSelectBlock("pdaModuleConfigMain.fdId,pdaModuleConfigMain.fdName,pdaModuleConfigMain.fdUrlPrefix");
		if (StringUtil.isNotNull(extendId)) {
			hqlInfo.setWhereBlock("pdaModuleConfigMain.fdStatus='1' "
					+ "and pdaModuleConfigMain.fdId !=:curId "
					+ "and pdaModuleConfigMain.fdSubMenuType = :fdSubMenuType");
			hqlInfo.setParameter("curId", extendId);
			hqlInfo.setParameter("fdSubMenuType", PdaModuleConfigConstant.PDA_MENUS_LISTTAB);
		} else {
            hqlInfo.setWhereBlock("pdaModuleConfigMain.fdStatus='1' "
                    + "and pdaModuleConfigMain.fdSubMenuType != 'module'");
        }
		List moduleList = pdaModuleConfigMainService.findList(hqlInfo);
		List retList = null;
		if (moduleList != null && moduleList.size() > 0) {
			retList = new ArrayList();
			for (Iterator iterator = moduleList.iterator(); iterator.hasNext();) {
				Object[] object = (Object[]) iterator.next();
				Map map = new HashMap();
				map.put("id", object[0]);
				map.put("name", object[1]);
				map.put("key", object[2]);
				retList.add(map);
			}
		}
		return retList;
	}

}
