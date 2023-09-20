package com.landray.kmss.km.forum.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.constant.SysAuthConstant.AuthCheck;
import com.landray.kmss.constant.SysAuthConstant.CheckType;
import com.landray.kmss.constant.SysDocConstant;
import com.landray.kmss.km.forum.model.KmForumConfig;
import com.landray.kmss.km.forum.model.KmForumPost;
import com.landray.kmss.km.forum.model.KmForumScore;
import com.landray.kmss.km.forum.model.KmForumTopic;
import com.landray.kmss.km.forum.service.IKmForumPostService;
import com.landray.kmss.km.forum.service.IKmForumScoreService;
import com.landray.kmss.km.forum.service.IKmForumTopicService;
import com.landray.kmss.km.forum.service.IkmForumIndexDataService;
import com.landray.kmss.km.forum.utils.ForumTopicPerformanceSolveUtil;
import com.landray.kmss.sys.person.interfaces.PersonInfoServiceGetter;
import com.landray.kmss.util.UserUtil;
import com.landray.sso.client.oracle.StringUtil;
import com.sunbor.web.tag.Page;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * 论坛首页数据展示
 * 
 * @author tanyouhao
 *
 */
public class KmForumIndexDataService implements IkmForumIndexDataService,IXMLDataBean{
	private static final String DATE_TYPE_TODAY = "today";
    protected IKmForumTopicService kmForumTopicService;
	protected IKmForumPostService kmForumPostService;
	protected IKmForumScoreService kmForumScoreService;

	public void setKmForumTopicService(IKmForumTopicService kmForumTopicService) {
		this.kmForumTopicService = kmForumTopicService;
	}

	public void setKmForumPostService(IKmForumPostService kmForumPostService) {
		this.kmForumPostService = kmForumPostService;
	}

	public void setKmForumScoreService(IKmForumScoreService kmForumScoreService) {
		this.kmForumScoreService = kmForumScoreService;
	}
	
	@Override
    public List getDataList(RequestContext requestInfo) throws Exception {
		String type = requestInfo.getParameter("type");
		if("newTopics".equals(type)){
		   return getNewsTopics();	
		}else if("userInfo".equals(type)){
			return getUserInfo();
		}
		return null;
	}
	
	public List getNewsTopics() throws Exception {
		List rtnList = null;
		if(UserUtil.getKMSSUser().isAdmin()) {
			HQLInfo hqlInfo = new HQLInfo();
			String whereBlock = "kmForumTopic.fdStatus != '"
					+ SysDocConstant.DOC_STATUS_DRAFT + "'";
			hqlInfo.setWhereBlock(whereBlock);
			hqlInfo.setOrderBy("kmForumTopic.fdSticked desc,kmForumTopic.fdLastPostTime desc");
			hqlInfo.setRowSize(7);
			hqlInfo.setPageNo(1);
			Page page = kmForumTopicService.findPage(hqlInfo);
			rtnList = page.getList();
		}
		else{
			rtnList = ForumTopicPerformanceSolveUtil.getNewTopics();
		}
		for (int i = 0; i < rtnList.size(); i++) {
			KmForumTopic kmForumTopic = (KmForumTopic) rtnList.get(i);
			Map map = new HashMap();
			Integer replyCount = 0;
			if (kmForumTopic.getFdReplyCount() != null) {
				replyCount = kmForumTopic.getFdReplyCount();
			}
			map.put("subject", kmForumTopic.getDocSubject());
			map.put("count",replyCount );
			//首贴显示描述信息
			if(i==0){
				KmForumPost KmForumPost=getKmForumPost(kmForumTopic.getFdId());
				map.put("content", KmForumPost.getDocContent());
			}
			StringBuffer sb = new StringBuffer();
			sb.append("/km/forum/km_forum/kmForumPost.do?method=view");
			sb.append("&fdTopicId=" + kmForumTopic.getFdId());
			map.put("href", sb.toString());
			rtnList.set(i, map);
		}
		//防止为空报错
		if(rtnList.size()<7){
			int currentSize = rtnList.size();
			for(int i=currentSize;i<7;i++){
				Map map = new HashMap();
				map.put("subject","");
				map.put("count","");
				if(i==0){
					map.put("content","");
				}
				map.put("href","");
				rtnList.add(i, map);
			}
			
		}
		return rtnList;
	}
	
	/**
	 * 获取帖子内容
	 * 
	 * @param fdTopicId
	 * @return
	 * @throws Exception
	 */
	public KmForumPost getKmForumPost(String fdTopicId) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		String whereBlock = "kmForumPost.kmForumTopic.fdId =:fdTopicId and kmForumPost.fdFloor = 1";
		hqlInfo.setWhereBlock(whereBlock);
		hqlInfo.setParameter("fdTopicId", fdTopicId);
		hqlInfo.setRowSize(1);
		hqlInfo.setPageNo(1);
		Page page = kmForumPostService.findPage(hqlInfo);
		List rtnList = page.getList();
		return (KmForumPost) rtnList.get(0);
	}
	
	/**
	 * 获取帖子总数
	 * 
	 * @param requestInfo
	 * @return
	 * @throws Exception
	 */
	public int getTopicTotal(String dateType) throws Exception {
		if(!UserUtil.getKMSSUser().isAdmin() && StringUtils.isBlank(dateType)) {
			return ForumTopicPerformanceSolveUtil.getTopicTotal4NonAdmin();
		}
		else{
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setSelectBlock("count(*)");
			String whereBlock = "kmForumTopic.fdStatus!=:fdStatus";
			hqlInfo.setParameter("fdStatus","10");
			if(StringUtil.isNotNull(dateType)){
				whereBlock+=" and kmForumTopic.docCreateTime <:endTime and kmForumTopic.docCreateTime >:startTime";
				Map dateSection = getDateSection(dateType);
				hqlInfo.setParameter("startTime", dateSection.get("startTime"));
				hqlInfo.setParameter("endTime", dateSection.get("endTime"));
			}
			hqlInfo.setWhereBlock(whereBlock);
			// 增加版块可访问者权限过滤
			hqlInfo.setCheckParam(CheckType.AuthCheck, AuthCheck.SYS_READER);
			List list = kmForumTopicService.findValue(hqlInfo);
			int count = Integer.parseInt(list.get(0).toString());
			return count;
		}
	}
	
	/**
	 * 获取用户信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@Override
    @SuppressWarnings("unchecked")
	public List getUserInfo() throws Exception {
		KmForumScore kmForumScore = (KmForumScore) kmForumScoreService
		      .findByPrimaryKey(UserUtil.getUser().getFdId(), null, true);
		List rtnList = new ArrayList();
		Map infoMap = new HashMap<String, Date>();
		infoMap.put("count1", getTopicTotal(DATE_TYPE_TODAY));
		infoMap.put("count2", getTopicTotal("yesterday"));
		infoMap.put("count3", getTopicTotal(""));
		
		infoMap.put("id", UserUtil.getUser().getFdId());
		infoMap.put("name",UserUtil.getUser().getFdName());
		//infoMap.put("sign",kmForumScore.getFdSign());
		infoMap.put("pcount",kmForumScore==null?0:kmForumScore.getFdPostCount());
		infoMap.put("rcount",kmForumScore==null?0:kmForumScore.getFdReplyCount()==null?0:kmForumScore.getFdReplyCount());
		int score = kmForumScore == null ? 0 : kmForumScore.getFdScore();
		infoMap.put("score", score);
		KmForumConfig forumConfig = new KmForumConfig();
		String level = forumConfig.getLevelByScore(score).trim();
		infoMap.put("level", forumConfig.getLevelIcon());
		//头像
		String path = PersonInfoServiceGetter.getPersonHeadimageUrl(
				UserUtil.getUser().getFdId(), null);
		infoMap.put("headurl", path);
		rtnList.add(infoMap);
		return rtnList;
	}
	
	/**
	 * 获取今日时间范围
	 * 
	 * @param dateType
	 * @return
	 */
	public Map getDateSection(String dateType){
		Map dateMap = new HashMap<String, Date>();
		Calendar c1 = new GregorianCalendar();
	    c1.set(Calendar.HOUR_OF_DAY, 0);
	    c1.set(Calendar.MINUTE, 0);
	    c1.set(Calendar.SECOND, 0);
	    if(!dateType.equals(DATE_TYPE_TODAY)){
	    	c1.add(Calendar.DATE,-1); 
	    }
	    Calendar c2 = new GregorianCalendar();
	    c2.set(Calendar.HOUR_OF_DAY, 23);
	    c2.set(Calendar.MINUTE, 59);
	    c2.set(Calendar.SECOND, 59);
	    if(!dateType.equals(DATE_TYPE_TODAY)){
	    	c2.add(Calendar.DATE,-1); 
	    }
	    Date startTime = c1.getTime();
	    Date endTime = c2.getTime();
	    dateMap.put("startTime", startTime);
	    dateMap.put("endTime", endTime);
	    return dateMap;
	}
}
