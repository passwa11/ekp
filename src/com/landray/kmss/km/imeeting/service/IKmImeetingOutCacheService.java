package com.landray.kmss.km.imeeting.service;

import java.util.List;

import com.landray.kmss.km.imeeting.model.KmImeetingMain;
import com.landray.kmss.km.imeeting.synchro.interfaces.SynchroMeetingResponse;

/**
 * 会议接出接口
 */
public interface IKmImeetingOutCacheService extends IKmImeetingOutService {

	/**
	 * 获取exchange回执情况
	 */
	public List<SynchroMeetingResponse> getMeetingResponseList(
			KmImeetingMain kmImeetingMain) throws Exception;

}
