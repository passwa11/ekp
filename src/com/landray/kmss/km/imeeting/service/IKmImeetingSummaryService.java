package com.landray.kmss.km.imeeting.service;

import java.util.Map;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.attachment.forms.IAttachmentForm;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;

/**
 * 会议纪要业务对象接口
 */
public interface IKmImeetingSummaryService extends IBaseService {

	/**
	 * 催办会议纪要
	 */
	public void hastenMeetingSummary(SysQuartzJobContext context)
			throws Exception;

	/**
	 * 分类转移
	 * 
	 * @param ids
	 * @param templateId
	 */
	public int updateDucmentTemplate(String ids, String templateId)
			throws Exception;

	/**
	 * 会议附件转纪要
	 */
	public void saveAttachment(String meetingId,
			IAttachmentForm attchmentForm) throws Exception;

	/**
	 * 纪要门户部件数据源
	 * 
	 * @param request
	 *            请求
	 * @return
	 * @throws Exception
	 */
	public Map<String, ?> listPortlet(RequestContext request) throws Exception;
}
