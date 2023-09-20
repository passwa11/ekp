package com.landray.kmss.sys.filestore.service.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.sys.filestore.model.SysAttCatalog;
import com.landray.kmss.sys.filestore.service.ISysAttCatalogService;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.StringUtil;

/**
 * 目录配置业务接口实现
 * 
 * @author 李衡
 * @version 1.0 2012-08-29
 */
public class SysAttCatalogServiceImp extends BaseServiceImp implements
		IXMLDataBean, ISysAttCatalogService {

	@Override
	public void updateCurrent(String id) throws Exception {
		SysAttCatalog attCatalog = (SysAttCatalog) findByPrimaryKey(id);
		attCatalog.setFdIsCurrent(true);
		resetCurrent(id);
		update(attCatalog);
	}
	
	private int resetCurrent(String extendId) throws Exception {
		String updateHql = "update SysAttCatalog sysAttCatalog set sysAttCatalog.fdIsCurrent=:fdIsCurrent where sysAttCatalog.fdId!=:fdId";
		return getBaseDao().getHibernateSession().createQuery(updateHql).setBoolean("fdIsCurrent", false).setParameter(
				"fdId", extendId).executeUpdate();
	}
	
	@Override
	public String add(IBaseModel modelObj) throws Exception {
		SysAttCatalog attCatalog = (SysAttCatalog)modelObj;
		if(attCatalog.getFdIsCurrent()){
			resetCurrent(attCatalog.getFdId());
		}
		return super.add(modelObj);
	}

	@Override
	public void update(IBaseModel modelObj) throws Exception {
		SysAttCatalog attCatalog = (SysAttCatalog)modelObj;
		if(attCatalog.getFdIsCurrent()){
			resetCurrent(attCatalog.getFdId());
		}
		super.update(modelObj);
	}
	
	@Override
	public List getDataList(RequestContext requestInfo) throws Exception {
		String fdCurrentIds = requestInfo.getParameter("fdCurrentIds");
		String[] ids = null;
		if (StringUtil.isNotNull(fdCurrentIds)) {
			ids = fdCurrentIds.split(";");
		}
		HQLInfo hqlInfo = new HQLInfo();
		if (ids != null) {
            hqlInfo.setWhereBlock(HQLUtil
                    .buildLogicIN("sysAttCatalog.fdId not", ArrayUtil
                            .convertArrayToList(ids)));
        }
		hqlInfo.setSelectBlock("sysAttCatalog");
		hqlInfo.setOrderBy("sysAttCatalog.fdName");
		List resultList = getBaseDao().findList(hqlInfo);
		List rtnList = new ArrayList();
		for (Iterator iterator = resultList.iterator(); iterator.hasNext();) {
			Map treeMap = new HashMap();
			SysAttCatalog catalog = (SysAttCatalog) iterator.next();
			treeMap.put("value", catalog.getFdId());
			treeMap.put("text", catalog.getFdName());
			rtnList.add(treeMap);
		}
		return rtnList;
	}

}
