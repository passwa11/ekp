package com.landray.kmss.km.imeeting.service.spring;

import java.util.List;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.km.imeeting.model.KmImeetingMapping;
import com.landray.kmss.km.imeeting.service.IKmImeetingMappingService;
import com.landray.kmss.util.StringUtil;

public class KmImeetingMappingServiceImp extends BaseServiceImp implements IKmImeetingMappingService {

	@Override
	public KmImeetingMapping findByModelId(String fdModelId, String fdModelName)
			throws Exception {
		return  findByModelId(fdModelId, fdModelName, null);
	}

	@Override
	public String getThirdIdByModel(String fdModelId, String fdModelName) throws Exception {
		KmImeetingMapping kmImeetingMapping = this.findByModelId(fdModelId, fdModelName);
		if (kmImeetingMapping != null) {
			return kmImeetingMapping.getFdThirdSysId();
		}
		return null;
	}

	@Override
	public String getThirdIdByModel(String fdModelId, String fdModelName, String fdAppKey) throws Exception {
		KmImeetingMapping kmImeetingMapping = this.findByModelId(fdModelId, fdModelName, fdAppKey);
		if (kmImeetingMapping != null) {
			return kmImeetingMapping.getFdThirdSysId();
		}
		return null;
	}

	@Override
	public KmImeetingMapping findByModelId(String fdModelId, String fdModelName, String fdAppKey) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		String whereBlock = " kmImeetingMapping.fdModelId=:fdModelId and kmImeetingMapping.fdModelName=:fdModelName";
		hqlInfo.setParameter("fdModelId", fdModelId);
		hqlInfo.setParameter("fdModelName", fdModelName);
		if (StringUtil.isNotNull(fdAppKey)) {
			whereBlock = StringUtil.linkString(whereBlock, " and ", "kmImeetingMapping.fdAppKey=:fdAppKey");
			hqlInfo.setParameter("fdAppKey", fdAppKey);
		} else { // 没有fdAppKey，默认查空，铂恩对接的数据没有fdAppKey字段值
			whereBlock = StringUtil.linkString(whereBlock, " and ", "kmImeetingMapping.fdAppKey is null");
		}
		hqlInfo.setWhereBlock(whereBlock);
		List l = this.findList(hqlInfo);
		if (l.size() > 0) {
			return (KmImeetingMapping) l.get(0);
		}
		return null;
	}

}
