package com.landray.kmss.km.forum.transfer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.km.forum.model.KmForumScore;
import com.landray.kmss.km.forum.service.IKmForumPostService;
import com.landray.kmss.km.forum.service.IKmForumScoreService;
import com.landray.kmss.km.forum.service.IKmForumTopicService;
import com.landray.kmss.sys.admin.transfer.service.ISysAdminTransferTask;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferContext;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferResult;
import com.landray.kmss.util.SpringBeanUtil;

public class KmForumTransferTask implements ISysAdminTransferTask {
	protected final Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());
	@Override
    public SysAdminTransferResult run(
			SysAdminTransferContext sysAdminTransferContext) {
		IKmForumScoreService kmForumScoreService = (IKmForumScoreService) SpringBeanUtil
		                    .getBean("kmForumScoreService");
		IKmForumPostService kmForumPostService = (IKmForumPostService) SpringBeanUtil
							.getBean("kmForumPostService");
		IKmForumTopicService kmForumTcopicService = (IKmForumTopicService) SpringBeanUtil
							.getBean("kmForumTopicService");
		try {
			HQLInfo topicHqlInfo = getTopicHqlInfo();
			HQLInfo replyHqlInfo = getReplyHqlInfo();
			
			Map postCountMap = new HashMap<String, Integer>();
			Map replyCountMap = new HashMap<String, Integer>();
			
			List topicList = kmForumTcopicService.findList(topicHqlInfo);
			List replyList = kmForumPostService.findList(replyHqlInfo);
			
			for(int i = 0;i<topicList.size();i++){
				Object[] resultArrTopic = (Object[]) topicList.get(i);
				postCountMap.put(resultArrTopic[1].toString(), Integer.parseInt(resultArrTopic[0].toString()));
			}
			for(int i = 0;i<replyList.size();i++){
				Object[] resultArrReply = (Object[]) replyList.get(i);
				replyCountMap.put(resultArrReply[1].toString(), Integer.parseInt(resultArrReply[0].toString()));
			}
			
			List<KmForumScore> scoreList = kmForumScoreService.findList(new HQLInfo());
			for(int i=0;i<scoreList.size();i++){
				KmForumScore kmForumScore = (KmForumScore)scoreList.get(i);
				if(postCountMap.get(kmForumScore.getFdId())!=null){
					kmForumScore.setFdPostCount((Integer) postCountMap.get(kmForumScore.getFdId()));
				}
				if(replyCountMap.get(kmForumScore.getFdId())!=null){
					kmForumScore.setFdReplyCount((Integer) replyCountMap.get(kmForumScore.getFdId()));
				}
				kmForumScore.getFdSign();
				kmForumScoreService.getBaseDao().update(kmForumScore);
			}
		} catch (Exception e) {
			logger.error("执行旧数据迁移为空异常", e);
		}
		return SysAdminTransferResult.OK;
	}

	private HQLInfo getTopicHqlInfo(){
		HQLInfo HqlInfo = new HQLInfo();
		HqlInfo.setSelectBlock("count(*),kmForumTopic.fdPoster.fdId");
		HqlInfo.setWhereBlock("kmForumTopic.fdPoster.fdId !=null group by kmForumTopic.fdPoster.fdId");
		return HqlInfo;
	}
	
	private HQLInfo getReplyHqlInfo(){
		HQLInfo HqlInfo = new HQLInfo();
		HqlInfo.setSelectBlock("count(*),kmForumPost.fdPoster.fdId");
		HqlInfo.setWhereBlock("kmForumPost.fdPoster.fdId !=null and kmForumPost.fdFloor !=:fdFloor group by kmForumPost.fdPoster.fdId");
		HqlInfo.setParameter("fdFloor", 1);
		return HqlInfo;
	}
}
