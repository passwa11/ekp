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
import com.landray.kmss.util.UserUtil;
import com.sunbor.web.tag.Page;

public class KmForumTopicPortlet implements IXMLDataBean {
	protected IKmForumTopicService kmForumTopicService;

	@Override
	public List getDataList(RequestContext requestInfo) throws Exception {
		String creator = "";
		String para = requestInfo.getParameter("rowsize");
		// 我的帖子portlet add by zhuangwl
		String owner = requestInfo.getParameter("owner");
		if (StringUtil.isNotNull(owner)) {
			return getOwnerTopicData(requestInfo);
		}
		int rowsize = 10;
		if (!StringUtil.isNull(para)) {
			rowsize = Integer.parseInt(para);
		}
		String fdForumId = requestInfo.getParameter("fdForumId");
		StringBuffer hql = new StringBuffer();
		HQLInfo hqlInfo = new HQLInfo();
		
		if (StringUtil.isNotNull(fdForumId)) {
			// 为兼容老数据用于判断是否需要更新所在树的层级id
			kmForumTopicService.updateHierarchyId(fdForumId);
			hql.append(" (kmForumTopic.kmForumCategory.fdHierarchyId like :fdForumId )");
			hqlInfo.setParameter("fdForumId", "%"+fdForumId+"%");
		}
		if (hql.length() > 0) {
			hql.append(" and (kmForumTopic.fdStatus = :fdStatus1 or kmForumTopic.fdStatus = :fdStatus2)");
			hqlInfo.setParameter("fdStatus1", SysDocConstant.DOC_STATUS_PUBLISH);
			hqlInfo.setParameter("fdStatus2", SysDocConstant.DOC_STATUS_EXPIRE);
		} else {
			hql.append(" (kmForumTopic.fdStatus = :fdStatus1 or kmForumTopic.fdStatus = :fdStatus2)");
			hqlInfo.setParameter("fdStatus1", SysDocConstant.DOC_STATUS_PUBLISH);
			hqlInfo.setParameter("fdStatus2", SysDocConstant.DOC_STATUS_EXPIRE);
		}
		hqlInfo.setWhereBlock(hql.toString());
		hqlInfo.setPageNo(1);
		hqlInfo.setOrderBy("kmForumTopic.fdSticked desc,kmForumTopic.fdLastPostTime desc");
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
			rtnList.set(i, map);
		}
		return rtnList;
	}

	/**
	 * 我的帖子 add by zhuangwl
	 * 
	 * @param requestInfo
	 * @return
	 * @throws Exception
	 */
	private List getOwnerTopicData(RequestContext requestInfo) throws Exception {
		String para = requestInfo.getParameter("rowsize");
		int rowsize = 10;
		if (!StringUtil.isNull(para)) {
            rowsize = Integer.parseInt(para);
        }
		HQLInfo hqlInfo = new HQLInfo();
		String whereBlock = "kmForumTopic.fdPoster.fdId = :posterId";
		hqlInfo.setParameter("posterId", UserUtil.getUser().getFdId());
		hqlInfo.setWhereBlock(whereBlock);
		hqlInfo
				.setOrderBy("kmForumTopic.docAlterTime desc , kmForumTopic.docCreateTime desc");
		hqlInfo.setRowSize(rowsize);
		hqlInfo.setPageNo(1);
		Page page = kmForumTopicService.findPage(hqlInfo);
		List rtnList = page.getList();
		for (int i = 0; i < rtnList.size(); i++) {
			KmForumTopic kmForumTopic = (KmForumTopic) rtnList.get(i);
			Map map = new HashMap();
			Integer replyCount = 0;
			if (kmForumTopic.getFdReplyCount() != null) {
				replyCount = kmForumTopic.getFdReplyCount();
			}
			map.put("text", kmForumTopic.getDocSubject()
					+ " ("
					+ ResourceUtil.getString(
							"portlet.kmForum.fdPostCount.portlet", "km-forum")
					+ replyCount + ") ");
			if (kmForumTopic.getFdLastPoster() == null) {
				map.put("creator", kmForumTopic.getFdPoster().getFdName());
				map.put("created", DateUtil.convertDateToString(kmForumTopic
						.getDocCreateTime(), DateUtil.TYPE_DATE, requestInfo
						.getLocale()));
			} else {
				map.put("creator", kmForumTopic.getFdLastPosterName());
				map.put("created", DateUtil.convertDateToString(kmForumTopic
						.getFdLastPostTime(), DateUtil.TYPE_DATE, requestInfo
						.getLocale()));
			}
			StringBuffer sb = new StringBuffer();
			sb.append("/km/forum/km_forum/kmForumPost.do?method=view");
			sb.append("&fdForumId="
					+ kmForumTopic.getKmForumCategory().getFdId());
			sb.append("&fdTopicId=" + kmForumTopic.getFdId());
			map.put("href", sb.toString());
			rtnList.set(i, map);
		}
		return rtnList;
	}

	public void setKmForumTopicService(IKmForumTopicService kmForumTopicService) {
		this.kmForumTopicService = kmForumTopicService;
	}
}
