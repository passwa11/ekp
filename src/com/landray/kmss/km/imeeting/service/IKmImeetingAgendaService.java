package com.landray.kmss.km.imeeting.service;

import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.km.imeeting.forms.KmImeetingMainForm;

/**
 * 会议议程业务对象接口
 */
public interface IKmImeetingAgendaService extends IBaseService {

	/**
	 * 保存上会材料
	 */
	public void saveUploadAtt(KmImeetingMainForm kmImeetingMainForm)
			throws Exception;

}
