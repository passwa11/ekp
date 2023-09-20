package com.landray.kmss.km.imeeting.service;

import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.km.imeeting.model.KmImeetingMapping;

public interface IKmImeetingMappingService extends IBaseService {

	public KmImeetingMapping findByModelId(String fdModelId, String fdModelName) throws Exception;

	public KmImeetingMapping findByModelId(String fdModelId, String fdModelName, String fdAppKey) throws Exception;

	public String getThirdIdByModel(String fdModelId, String fdModelName) throws Exception;

	public String getThirdIdByModel(String fdModelId, String fdModelName, String fdAppKey) throws Exception;

}
