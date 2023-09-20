package com.landray.kmss.km.forum.service.spring;

import org.springframework.context.ApplicationEvent;

import com.landray.kmss.km.forum.interfaces.IKmForumScoreCommunicationChangeEvent;
import com.landray.kmss.km.forum.model.KmForumScore;

/**
 * 创建日期 2006-09-15
 * 
 * @author 吴兵 论坛积分用户信息修改接口实现
 */

public class KmForumScoreCommunicationChangeEvent extends ApplicationEvent
		implements IKmForumScoreCommunicationChangeEvent {
	private KmForumScore score;

	public KmForumScoreCommunicationChangeEvent(KmForumScore score) {
		super(score);
		this.score = score;
	}

	@Override
    public String getKmForumScoreId() {
		return score.getFdId();
	}

	@Override
    public String getKmForumScoreNickName() {
		return score.getFdNickName();
	}

	@Override
    public String getKmForumScoreSign() {
		return score.getFdSign();
	}
	
    public Integer getKmForumPostCount(){
    	return score.getFdPostCount();
    }
    
    public Integer getKmForumReplyCount(){
    	return score.getFdReplyCount();
    }
}
