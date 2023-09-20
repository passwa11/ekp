package com.landray.kmss.sys.zone.service.spring;

import java.util.List;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

/**
 * 检查名称是否唯一
 * 
 * @author XuJieYang
 * 
 */
public class SysZoneCheckNameOnlyServiceImpl implements IXMLDataBean {

	@Override
	public List getDataList(RequestContext requestInfo) throws Exception {
		String fdId = requestInfo.getParameter("fdId");
		String beanName = requestInfo.getParameter("beanName");
		String fieldName = requestInfo.getParameter("fieldName");
		String fieldValue = requestInfo.getParameter("fieldValue");
		IBaseService baseService = (IBaseService) SpringBeanUtil
				.getBean(beanName);
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("new map(count(fdId) as size)");
		StringBuilder whereBlock = new StringBuilder();
		whereBlock.append(" ");
		whereBlock.append(fieldName + "=:fieldValue ");
		if (StringUtil.isNotNull(fdId)) {
			// 如果fdid存在，说明是处于编辑的情况。排除和自身比较的情况
			whereBlock.append(" and fdId<>'" + fdId + "'");
		}
		whereBlock.append(" ");
		hqlInfo.setWhereBlock(whereBlock.toString());
		hqlInfo.setParameter("fieldValue", fieldValue);
		return baseService.findList(hqlInfo);
	}

}
