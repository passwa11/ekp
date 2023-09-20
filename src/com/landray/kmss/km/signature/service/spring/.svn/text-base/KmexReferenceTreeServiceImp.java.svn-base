package com.landray.kmss.km.signature.service.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.km.signature.service.IKmexReferenceTreeService;
import com.landray.kmss.util.StringUtil;

/**
 * 数据源业务接口实现
 * 
 * @author 卢汉佳
 * @version 1.0 2013-08-14
 */
public class KmexReferenceTreeServiceImp extends BaseServiceImp implements
		IXMLDataBean, IKmexReferenceTreeService {

	IBaseService sysCategoryMainService;

	/**
	 * @param sysCategoryMainService
	 *            the sysCategoryMainService to set
	 */
	public void setSysCategoryMainService(IBaseService sysCategoryMainService) {
		this.sysCategoryMainService = sysCategoryMainService;
	}

	@Override
	public List<Map<String, String>> getDataList(RequestContext requestInfo)
			throws Exception {
		String parentId = requestInfo.getParameter("parentId");
		if ("recent".equals(parentId)) {
            return null;
        }
		HQLInfo hqlInfo = new HQLInfo();
		List<?> result = null;
		hqlInfo.setSelectBlock("sysCategoryMain.fdName, sysCategoryMain.fdId");
		if (StringUtil.isNull(parentId)) {
			hqlInfo.setWhereBlock("sysCategoryMain.hbmParent is null");
		} else {
			hqlInfo.setWhereBlock("sysCategoryMain.hbmParent.fdId=:parentId");
			hqlInfo.setParameter("parentId", parentId);
		}
		result = sysCategoryMainService.findValue(hqlInfo);
		List<Map<String, String>> rtnList = new ArrayList<Map<String, String>>();
		for (int i = 0; i < result.size(); i++) {
			Object[] obj = (Object[]) result.get(i);
			Map<String, String> node = new HashMap<String, String>();
			node.put("text", obj[0].toString());
			node.put("value", obj[1].toString());
			rtnList.add(node);
		}
		if (StringUtil.isNull(parentId)) {
			Map<String, String> node = new HashMap<String, String>();
			node.put("text", "最近");
			node.put("value", "recent");
			rtnList.add(node);
		}

		return rtnList;
	}
}
