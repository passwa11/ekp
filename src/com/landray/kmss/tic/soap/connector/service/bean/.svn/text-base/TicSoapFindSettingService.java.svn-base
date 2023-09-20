package com.landray.kmss.tic.soap.connector.service.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.tic.soap.connector.model.TicSoapSettCategory;
import com.landray.kmss.tic.soap.connector.model.TicSoapSetting;
import com.landray.kmss.tic.soap.connector.service.ITicSoapSettCategoryService;
import com.landray.kmss.tic.soap.connector.service.ITicSoapSettingService;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

public class TicSoapFindSettingService implements IXMLDataBean {
	private ITicSoapSettCategoryService ticSoapSettCategoryService;

	public ITicSoapSettCategoryService getTicSoapSettCategoryService() {
		return ticSoapSettCategoryService;
	}

	public void setTicSoapSettCategoryService(
			ITicSoapSettCategoryService ticSoapSettCategoryService) {
		this.ticSoapSettCategoryService = ticSoapSettCategoryService;
	}

	/**
	 * 获取选择函数名称
	 */
	@Override
    @SuppressWarnings("unchecked")
	public List getDataList(RequestContext requestInfo) throws Exception {
		ITicSoapSettingService ticSoapSettingService = (ITicSoapSettingService) SpringBeanUtil
				.getBean("ticSoapSettingService");

		String type = requestInfo.getParameter("type");
		String selectId = requestInfo.getParameter("selectId");
		String keyword = requestInfo.getParameter("keyword");
		String whereBlock = "";
		// List<TicSoapSettCategory> resList = new
		// ArrayList<TicSoapSettCategory>(1);

		List<Map<String, String>> rtnValue = new ArrayList<Map<String, String>>();
		Map<String, String> map;
		HQLInfo hqlInfo = new HQLInfo();

		if ("cate".equals(type)) {
			if (StringUtil.isNull(selectId)) {
				whereBlock = "ticSoapSettCategory.hbmParent is null";
			} else {
				whereBlock = "ticSoapSettCategory.hbmParent.fdId=:hbmParentFdId";
				hqlInfo.setParameter("hbmParentFdId", selectId);
			}
			hqlInfo.setWhereBlock(whereBlock);
			List resList = (List<TicSoapSettCategory>) ticSoapSettCategoryService
					.findList(hqlInfo);
			for (Iterator<TicSoapSettCategory> iterator = resList.iterator(); iterator
					.hasNext();) {
				map = new HashMap<String, String>(1);
				TicSoapSettCategory ticSoapSettCategory = iterator.next();
				map.put("text", ticSoapSettCategory.getFdName());
				map.put("value", ticSoapSettCategory.getFdId());
				rtnValue.add(map);
			}

		} else if ("func".equals(type)) {
			if ("".equals(selectId)) {
				whereBlock = "ticSoapSetting.fdEnable=1 ";
			} else {
				String inStr = "";
				hqlInfo.setSelectBlock("ticSoapSettCategory.fdId");
				hqlInfo
						.setWhereBlock("ticSoapSettCategory.fdHierarchyId like :fdHierarchyId");
				hqlInfo.setParameter("fdHierarchyId", "%" + selectId + "%");
				List<String> categoryList = ticSoapSettCategoryService
						.findValue(hqlInfo);
				for (Iterator iterator = categoryList.iterator(); iterator
						.hasNext();) {
					String idTmp = (String) iterator.next();
					inStr += "".equals(inStr) ? ("'" + idTmp + "'") : (",'"
							+ idTmp + "'");
				}
				whereBlock = "ticSoapSetting.settCategory.fdId in (" + inStr
						+ ") and ticSoapSetting.fdEnable=1 ";
			}

			List resList = ticSoapSettingService.findList(whereBlock, null);
			for (Iterator iterator = resList.iterator(); iterator.hasNext();) {
				map = new HashMap();
				TicSoapSetting ticSoapSetting = (TicSoapSetting) iterator
						.next();
				Map<String, String> node = new HashMap<String, String>();
				node.put("name", ticSoapSetting.getDocSubject());
				node.put("id", ticSoapSetting.getFdId());
				node.put("info", ticSoapSetting.getFdWsdlUrl());
				node.put("soap", ticSoapSetting.getFdSoapVerson());
				rtnValue.add(node);
			}

		} else if ("search".equals(type) && StringUtil.isNotNull(keyword)) {
			whereBlock = "ticSoapSetting.docSubject like :docSubject ";
			hqlInfo.setWhereBlock(whereBlock);
			hqlInfo.setParameter("docSubject", "%" + keyword + "%");
			List resList = ticSoapSettingService.findList(hqlInfo);
			for (Iterator iterator = resList.iterator(); iterator.hasNext();) {
				map = new HashMap();
				TicSoapSetting ticSoapSetting = (TicSoapSetting) iterator
						.next();
				map.put("name", ticSoapSetting.getDocSubject());
				map.put("id", ticSoapSetting.getFdId());
				map.put("info", ticSoapSetting.getFdWsdlUrl());
				map.put("soap", ticSoapSetting.getFdSoapVerson());
				rtnValue.add(map);
			}

		}
		return rtnValue;
	}
}
