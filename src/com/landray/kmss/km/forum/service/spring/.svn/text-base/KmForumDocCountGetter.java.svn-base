package com.landray.kmss.km.forum.service.spring;

import java.util.List;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.km.forum.service.IKmForumTopicService;
import com.landray.kmss.sys.zone.service.ISysZoneDocCountGetter;
import com.landray.kmss.util.UserUtil;

public class KmForumDocCountGetter implements ISysZoneDocCountGetter{

	private IKmForumTopicService kmForumTopicService;
	
	public void setKmForumTopicService(IKmForumTopicService kmForumTopicService) {
		this.kmForumTopicService = kmForumTopicService;
	}

	@Override
	public int getDocNum(String fdPersonId) throws Exception {
		HQLInfo hql = new HQLInfo();
		hql.setSelectBlock(" count(kmForumTopic.fdId)");
		hql.setWhereBlock(" kmForumTopic.fdPoster.fdId =:fdUserId");
		hql.setParameter("fdUserId", fdPersonId);
		List<Long> list = kmForumTopicService.getBaseDao().findValue(hql);
		if(list.size()>0){
			return list.get(0).intValue();
		}else{
			return 0;
		}
	}

}
