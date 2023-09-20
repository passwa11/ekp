package com.landray.kmss.km.forum.service.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.km.forum.model.KmForumScore;
import com.landray.kmss.km.forum.service.IKmForumScoreService;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.sunbor.web.tag.Page;

/**
 * 论坛排名portlet(论坛积分排名(部门、公司)、论坛回帖数排名(部门、公司))
 * 
 * @author jonathan 2010-六月-19
 */
public class KmForumRankPortlet implements IXMLDataBean {

	protected IKmForumScoreService kmForumScoreService;

	public void setKmForumScoreService(IKmForumScoreService kmForumScoreService) {
		this.kmForumScoreService = kmForumScoreService;
	}

	@Override
	public List getDataList(RequestContext requestInfo) throws Exception {
		String type = requestInfo.getParameter("type");
		if ("score".equals(type)) {
			return getScoreData(requestInfo);
		} else if ("postCount".equals(type)) {
			return getPostCountData(requestInfo);
		}
		return new ArrayList<Map<String, Object>>();
	}

	/**
	 * 获取部门或公司员工的论坛积分排名
	 * 
	 * @param requestInfo
	 * @return
	 * @throws Exception
	 */
	private List getScoreData(RequestContext requestInfo) throws Exception {
		String org = requestInfo.getParameter("org");
		String para = requestInfo.getParameter("rowsize");
		int rowsize = 10;
		if (!StringUtil.isNull(para)) {
            rowsize = Integer.parseInt(para);
        }
		StringBuffer sb = new StringBuffer();
		sb.append("kmForumScore.fdScore is not null");
		HQLInfo hqlInfo = new HQLInfo();
		if ("dept".equals(org) && UserUtil.getUser().getFdParent() != null){
			sb.append(" and kmForumScore.person.hbmParent.fdId = :parentId ");
			hqlInfo.setParameter("parentId", UserUtil.getUser().getFdParent().getFdId());
		}
		hqlInfo.setWhereBlock(sb.toString());
		hqlInfo.setOrderBy("kmForumScore.fdScore desc");
		hqlInfo.setRowSize(rowsize);
		hqlInfo.setPageNo(1);
		Page page = kmForumScoreService.findPage(hqlInfo);
		List rtnList = page.getList();
		for (int i = 0; i < rtnList.size(); i++) {
			KmForumScore kmForumScore = (KmForumScore) rtnList.get(i);
			Map<String, Object> map = new HashMap<String, Object>();
			// 用户昵称
			String fdNickName = "";
			if (StringUtil.isNotNull(kmForumScore.getFdNickName())) {
                fdNickName = kmForumScore.getFdNickName();
            } else {
                fdNickName = kmForumScore.getPerson().getFdName();
            }
			map.put("text", fdNickName);
			map.put("otherinfo", " ("
					+ ResourceUtil
							.getString("kmForumScore.fdScore", "km-forum")
					+ ": "
					+ (kmForumScore.getFdScore() == null ? 0 : kmForumScore
							.getFdScore()) + ") ");
			map.put("href", ModelUtil.getModelUrl(kmForumScore));// 链接
			rtnList.set(i, map);
		}
		return rtnList;
	}

	/**
	 * 获取部门或公司员工的论坛回帖数排名
	 * 
	 * @param requestInfo
	 * @return
	 * @throws Exception
	 */
	private List getPostCountData(RequestContext requestInfo) throws Exception {
		String org = requestInfo.getParameter("org");
		String para = requestInfo.getParameter("rowsize");
		int rowsize = 10;
		if (!StringUtil.isNull(para)) {
            rowsize = Integer.parseInt(para);
        }
		StringBuffer sb = new StringBuffer();
		sb.append("kmForumScore.fdPostCount is not null");
		HQLInfo hqlInfo = new HQLInfo();
		if ("dept".equals(org) && UserUtil.getUser().getFdParent() != null){
			sb.append(" and kmForumScore.person.hbmParent.fdId = :parentId");
			hqlInfo.setParameter("parentId", UserUtil.getUser().getFdParent().getFdId());
		}
		hqlInfo.setWhereBlock(sb.toString());
		hqlInfo.setOrderBy("kmForumScore.fdPostCount desc");
		hqlInfo.setRowSize(rowsize);
		hqlInfo.setPageNo(1);
		Page page = kmForumScoreService.findPage(hqlInfo);
		List rtnList = page.getList();
		for (int i = 0; i < rtnList.size(); i++) {
			KmForumScore kmForumScore = (KmForumScore) rtnList.get(i);
			Map<String, Object> map = new HashMap<String, Object>();
			// 用户昵称
			String fdNickName = "";
			if (StringUtil.isNotNull(kmForumScore.getFdNickName())) {
                fdNickName = kmForumScore.getFdNickName();
            } else {
                fdNickName = kmForumScore.getPerson().getFdName();
            }
			map.put("text", fdNickName);
			map.put("otherinfo", " ("
					+ ResourceUtil.getString(
							"kmForumScore.fdPostCount.portlet", "km-forum")
					+ ": "
					+ (kmForumScore.getFdPostCount() == null ? 0 : kmForumScore
							.getFdPostCount()) + ") ");
			map.put("href", ModelUtil.getModelUrl(kmForumScore));// 链接
			rtnList.set(i, map);
		}
		return rtnList;
	}
}
