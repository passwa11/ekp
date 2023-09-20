/**
 * 
 */
package com.landray.kmss.tic.jdbc.service.bean;

import java.util.List;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.tic.jdbc.constant.TicJdbcConstant;

/**
 * @author qiujh
 * @version 1.0 2013-10-11
 */
public class TicJdbcExpressionBean implements IXMLDataBean {

	@Override
    public List getDataList(RequestContext requestInfo) throws Exception {
		String parentId = requestInfo.getParameter("parentId");
		return TicJdbcConstant.EXPRESSION_LIST;
	}
	
}
