package com.landray.kmss.sys.zone.service;

import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.zone.model.SysZoneAddressCateVo;

public interface ISysZoneAddressCateVoService extends IBaseService {

	public void deleteByCateId(String cateId) throws Exception;

	public SysZoneAddressCateVo getCateVoByCateId(String cateId)
			throws Exception;
}
