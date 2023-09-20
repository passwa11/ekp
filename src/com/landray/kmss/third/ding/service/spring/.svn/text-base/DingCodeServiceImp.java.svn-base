package com.landray.kmss.third.ding.service.spring;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.third.ding.service.IDingCodeService;
import org.apache.commons.lang.StringUtils;

public class DingCodeServiceImp extends BaseServiceImp implements
		IDingCodeService {
	
	@Override
    public String getUseridByCode(String fdCode)throws Exception{
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("fdUserid");
		hqlInfo.setWhereBlock("fdCode='"+fdCode+"'");
		return (String) this.findFirstOne(hqlInfo);
	}
	
	@Override
    public void deleteByCode(String fdCode)throws Exception{
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("fdId");
		hqlInfo.setWhereBlock("fdCode='"+fdCode+"'");
		String fdId = (String) this.findFirstOne(hqlInfo);
		if(StringUtils.isNotBlank(fdId)) {
			this.delete(fdId);
		}
	}
}
