package com.landray.kmss.km.imeeting.service;

import net.sf.json.JSON;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.km.imeeting.util.StatResult;

/**
 * 会议统计业务对象接口
 */
public interface IKmImeetingStatService extends IBaseService {

	/**
	 * 执行统计(图表)
	 */
	public StatResult statChart(IExtendForm form, RequestContext requestContext)
			throws Exception;

	/**
	 * 执行统计(列表)
	 */
	public JSON statList(IExtendForm form, RequestContext requestContext)
			throws Exception;

	/**
	 * 执行统计(列表详情)
	 */
	public JSON statListDetail(IExtendForm form, RequestContext requestContext)
			throws Exception;

}
