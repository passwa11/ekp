package com.landray.kmss.sys.zone.service.spring;

import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.sys.zone.model.SysZoneAddressCateVo;
import com.landray.kmss.sys.zone.service.ISysZoneAddressCateVoService;

public class SysZoneAddressCateVoServiceImp extends BaseServiceImp
		implements ISysZoneAddressCateVoService {

	@Override
	public void deleteByCateId(String cateId) throws Exception {
		SysZoneAddressCateVo cateVo = getCateVoByCateId(cateId);
		if (cateVo != null) {
			delete(cateVo);
		}
	}

	@Override
	public SysZoneAddressCateVo getCateVoByCateId(String cateId)
			throws Exception {
		SysZoneAddressCateVo obj = (SysZoneAddressCateVo)findFirstOne(
				"fdCategoryId='" + cateId + "'", "");
		return obj;
	}

}
