package com.landray.kmss.km.forum.service.spring;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.constant.SysDocConstant;
import com.landray.kmss.km.forum.model.KmForumTopic;
import com.landray.kmss.km.forum.service.IKmForumTopicService;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;

public class KmForumTopicPortletHot implements IXMLDataBean {
	protected IKmForumTopicService kmForumTopicService;

	// 添加论坛模块门户展现为精华或者热门的话题，精华状态为1，热门话题回复数不少于20
	@Override
	public List getDataList(RequestContext requestInfo) throws Exception {
		String creator = "";
		String para = requestInfo.getParameter("rowsize");
		int rowsize = 10;
		if (!StringUtil.isNull(para)) {
            rowsize = Integer.parseInt(para);
        }
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("kmForumTopic.fdPinked=:fdPinked or kmForumTopic.fdReplyCount>=20 and (kmForumTopic.fdStatus = :fdStatus1 or kmForumTopic.fdStatus = :fdStatus2)");
		hqlInfo.setParameter("fdPinked", Boolean.TRUE);
		hqlInfo.setParameter("fdStatus1", SysDocConstant.DOC_STATUS_PUBLISH);
		hqlInfo.setParameter("fdStatus2", SysDocConstant.DOC_STATUS_EXPIRE);
		hqlInfo.setOrderBy("kmForumTopic.fdSticked desc,kmForumTopic.fdLastPostTime desc");
		hqlInfo.setPageNo(1);
		hqlInfo.setRowSize(rowsize);
		List rtnList = kmForumTopicService.findPage(hqlInfo).getList();
		for (int i = 0; i < rtnList.size(); i++) {
			KmForumTopic topic = (KmForumTopic) rtnList.get(i);
			Map map = new HashMap();
			Integer replyCount = 0;
			if (topic.getFdReplyCount() != null) {
				replyCount = topic.getFdReplyCount();
			}
			map.put("text", topic.getDocSubject());
			map.put("otherinfo", " ("
					+ ResourceUtil.getString(
							"portlet.kmForum.fdPostCount.portlet", "km-forum")
					+ replyCount + ") ");
			// 如果没有最后回复人，取话题的发起者 ---modify by miaogr
			if (StringUtil.isNotNull(topic.getFdLastPosterName())) {
				creator = topic.getFdLastPosterName();
			} else if (topic.getFdPoster() != null
					&& StringUtil.isNotNull(topic.getFdPoster().getFdName())) {
				creator = topic.getFdPoster().getFdName();
			}
			map.put("creator", creator);
			map.put("created", DateUtil.convertDateToString(topic
					.getFdLastPostTime(), DateUtil.TYPE_DATE, requestInfo
					.getLocale()));
			StringBuffer sb = new StringBuffer();
			sb.append("/km/forum/km_forum/kmForumPost.do?method=view");
			sb.append("&fdForumId=" + topic.getKmForumCategory().getFdId());
			sb.append("&fdTopicId=" + topic.getFdId());
			map.put("href", sb.toString());
			map.put("id", topic.getFdId());
			rtnList.set(i, map);
		}
		return rtnList;
	}

	public void setKmForumTopicService(IKmForumTopicService kmForumTopicService) {
		this.kmForumTopicService = kmForumTopicService;
	}
}
