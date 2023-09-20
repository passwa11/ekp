package com.landray.kmss.sys.time.service;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;

import net.sf.json.JSONArray;

/**
 * 创建日期 2008-一月-16
 * 
 * @author 易荣烽 班次时间业务对象接口
 */
public interface ISysTimeCommonTimeService extends IBaseService {
	@Override
    public String add(IExtendForm form, RequestContext requestContext)
			throws Exception;

	JSONArray getCommonTime(String fdId) throws Exception;

	public JSONArray getRelevantAreas(String fdId) throws Exception;
}
