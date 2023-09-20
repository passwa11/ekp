package com.landray.kmss.sys.handover.support.config.doc.handler;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.sunbor.web.tag.Page;

/**
 * 文档类明细执行器接口
 * 
 * @author tanyh
 *
 */
public interface IDocHandler {
	
	
	public Page detail(HQLInfo hqlInfo,SysOrgElement org, String module,String item,
			RequestContext context) throws Exception;

	
}
