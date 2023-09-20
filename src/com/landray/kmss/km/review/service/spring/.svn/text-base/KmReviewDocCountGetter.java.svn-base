package com.landray.kmss.km.review.service.spring;

import java.util.List;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.km.review.service.IKmReviewMainService;
import com.landray.kmss.sys.zone.service.ISysZoneDocCountGetter;

public class KmReviewDocCountGetter implements ISysZoneDocCountGetter{
	private IKmReviewMainService kmReviewMainService ;
	
	
	public void setKmReviewMainService(IKmReviewMainService kmReviewMainService) {
		this.kmReviewMainService = kmReviewMainService;
	}



	@Override
	public int getDocNum(String fdPersonId) throws Exception {
		HQLInfo hql = new HQLInfo();
		hql.setSelectBlock(" count(kmReviewMain.fdId)");
		hql.setWhereBlock(" kmReviewMain.docCreator.fdId=:fdUserId");
		hql.setParameter("fdUserId",fdPersonId);
		List<Long> list = kmReviewMainService.getBaseDao().findValue(hql);
		if(list.size()>0){
			return list.get(0).intValue();
		}else{
			return 0;
		}
	}
}
