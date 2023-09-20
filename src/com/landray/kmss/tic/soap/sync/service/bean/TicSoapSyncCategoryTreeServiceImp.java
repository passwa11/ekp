package com.landray.kmss.tic.soap.sync.service.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.tic.soap.sync.model.TicSoapSyncCategory;
import com.landray.kmss.tic.soap.sync.service.ITicSoapSyncCategoryService;
import com.landray.kmss.util.StringUtil;

public class TicSoapSyncCategoryTreeServiceImp implements IXMLDataBean  {
	
	private ITicSoapSyncCategoryService ticSoapSyncCategoryService;

	@Override
    public List getDataList(RequestContext requestInfo) throws Exception {
		String parentId = requestInfo.getParameter("parentId");
		HQLInfo hqlInfo = new HQLInfo();

		if (StringUtil.isNull(parentId)) {
			hqlInfo.setWhereBlock("ticSoapSyncCategory.hbmParent is null");

		} else {
			hqlInfo.setWhereBlock("ticSoapSyncCategory.hbmParent.fdId=:parentId");
			hqlInfo.setParameter("parentId", parentId);
		}
		if (StringUtil.isNotNull(hqlInfo.getWhereBlock())) {
			hqlInfo.setOrderBy("ticSoapSyncCategory.fdOrder");
			List result = ticSoapSyncCategoryService.findList(hqlInfo);

			List<Map<String,String>> rtnValue = new ArrayList<Map<String,String>>();
			for (int i = 0; i < result.size(); i++) {
				TicSoapSyncCategory ticSoapSyncCategory = (TicSoapSyncCategory) result.get(i);
				Map<String,String> node = new HashMap<String,String>();
				node.put("text", ticSoapSyncCategory.getFdName());
				node.put("value", ticSoapSyncCategory.getFdId());
				node.put("href",requestInfo.getContextPath()+"/tic/soap/sync/tic_soap_sync_job/ticSoapSyncJob.do?method=list&categoryId="+ticSoapSyncCategory.getFdId());
				rtnValue.add(node);
			}

			return rtnValue;
		} else {
			return null;
		}
	}

	public ITicSoapSyncCategoryService getTicSoapSyncCategoryService() {
		return ticSoapSyncCategoryService;
	}

	public void setTicSoapSyncCategoryService(
			ITicSoapSyncCategoryService ticSoapSyncCategoryService) {
		this.ticSoapSyncCategoryService = ticSoapSyncCategoryService;
	}
	
	



}
