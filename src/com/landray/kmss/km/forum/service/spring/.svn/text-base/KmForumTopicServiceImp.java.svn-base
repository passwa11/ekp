package com.landray.kmss.km.forum.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.constant.SysDocConstant;
import com.landray.kmss.constant.SysNotifyConstant;
import com.landray.kmss.km.forum.dao.IKmForumTopicDao;
import com.landray.kmss.km.forum.model.KmForumCategory;
import com.landray.kmss.km.forum.model.KmForumConfig;
import com.landray.kmss.km.forum.model.KmForumPost;
import com.landray.kmss.km.forum.model.KmForumScore;
import com.landray.kmss.km.forum.model.KmForumTopic;
import com.landray.kmss.km.forum.service.IKmForumCategoryService;
import com.landray.kmss.km.forum.service.IKmForumScoreService;
import com.landray.kmss.km.forum.service.IKmForumTopicService;
import com.landray.kmss.sys.bookmark.service.ISysBookmarkMainService;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.sys.notify.interfaces.NotifyContext;
import com.landray.kmss.sys.notify.interfaces.NotifyReplace;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.portal.cloud.dto.IconDataVO;
import com.landray.kmss.sys.portal.cloud.util.ListDataUtil;
import com.landray.kmss.sys.portal.util.PortletTimeUtil;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.sunbor.web.tag.Page;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.CacheMode;
import org.hibernate.query.NativeQuery;
import org.hibernate.type.StandardBasicTypes;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 创建日期 2006-Sep-05
 * 
 * @author 吴兵 论坛话题业务接口实现
 */
public class KmForumTopicServiceImp extends BaseServiceImp implements
		IKmForumTopicService, IXMLDataBean {
	private IKmForumCategoryService kmForumCategoryService = null;

	private ISysOrgCoreService sysOrgCoreService;

	public void setSysOrgCoreService(ISysOrgCoreService sysOrgCoreService) {
		this.sysOrgCoreService = sysOrgCoreService;
	}

	private ISysNotifyMainCoreService sysNotifyMainCoreService;

	public void setSysNotifyMainCoreService(
			ISysNotifyMainCoreService sysNotifyMainCoreService) {
		this.sysNotifyMainCoreService = sysNotifyMainCoreService;
	}

	protected IKmForumScoreService kmForumScoreService;

	protected IKmForumScoreService getkmForumScoreService() {
		if (kmForumScoreService == null) {
            kmForumScoreService = (IKmForumScoreService) SpringBeanUtil
                    .getBean("kmForumScoreService");
        }
		return kmForumScoreService;
	}

	protected ISysBookmarkMainService sysBookmarkMainServiceImp;

	protected ISysBookmarkMainService getSysBookmarkMainServiceImp() {
		if (sysBookmarkMainServiceImp == null) {
            sysBookmarkMainServiceImp = (ISysBookmarkMainService) SpringBeanUtil
                    .getBean(
                    "sysBookmarkMainService");
        }
		return sysBookmarkMainServiceImp;
	}

	/**
	 * 推荐帖子
	 * 
	 * @param fdId
	 * @throws Exception
	 */
	@Override
	public void updateIntroduce(String fdId, String fdTargetIds,
								String fdNotifyType) throws Exception {
		KmForumTopic kmForumTopic = (KmForumTopic) findByPrimaryKey(fdId);

		// 获取上下文
		NotifyContext notifyContext = sysNotifyMainCoreService
				.getContext("km-forum:kmForumTopic.introduce.notify");
		// 获取通知方式
		notifyContext.setNotifyType(fdNotifyType);
		// 设置发布类型为“待办”（默认为待阅）
		notifyContext.setFlag(SysNotifyConstant.NOTIFY_TODOTYPE_ONCE);
		// 设置发布KEY值，为后面的删除准备
		notifyContext.setKey("kmForumTopicIntroduce");
		// 获取通知人
		List targets = new ArrayList();
		String[] targetIds = fdTargetIds.split(";");
		for (int i = 0; i < targetIds.length; i++) {
			targets.add(sysOrgCoreService.findByPrimaryKey(targetIds[i]));
		}
		// 添加日志信息
		if (UserOperHelper.allowLogOper("updateIntroduce", getModelName())) {
			StringBuilder sb = new StringBuilder();
			for (Object target : targets) {
				if (target instanceof SysOrgElement) {
					sb.append(((SysOrgElement) target).getFdName()).append(";");
				}
			}
			UserOperContentHelper.putUpdate(fdId)
					.putSimple("fdTargetIds", null, fdTargetIds)
					.putSimple("fdTargetNames", null, sb.toString())
					.putSimple("fdNotifyType", null, fdNotifyType);
		}
		// 设置发布通知人
		notifyContext.setNotifyTarget(targets);
		sysNotifyMainCoreService.sendNotify(kmForumTopic, notifyContext,
				getNotifyReplace(kmForumTopic));
	}

	private NotifyReplace getNotifyReplace(KmForumTopic kmForumTopic) {
		NotifyReplace notifyReplace = new NotifyReplace();
		notifyReplace.addReplaceText("km-forum:docSubject",
				kmForumTopic.getDocSubject());
		notifyReplace.addReplaceModel("km-forum:introducer", UserUtil.getUser(),
				"fdName");
		return notifyReplace;
	}

	private HashMap getReplaceMap(KmForumTopic kmForumTopic) {
		HashMap replaceMap = new HashMap();
		replaceMap.put("km-forum:docSubject", kmForumTopic.getDocSubject());
		replaceMap.put("km-forum:introducer", UserUtil.getUser().getFdName());
		return replaceMap;
	}

	/**
	 * 将该主题结贴
	 * 
	 * @param fdId
	 * @throws Exception
	 */
	@Override
	public void doConclude(String fdId) throws Exception {
		KmForumTopic topic = (KmForumTopic) findByPrimaryKey(fdId);
		if (UserOperHelper.allowLogOper("doConclude", getModelName())) {
			// 添加日志信息
			UserOperContentHelper.putUpdate(topic);
		}
		topic.setFdStatus(SysDocConstant.DOC_STATUS_EXPIRE);
		topic.setFdConcludeInfo(ResourceUtil.getString(
				"kmForumPost.concludeInfo",
				"km-forum",
				 null,
				 new Object[] { UserUtil.getUser().getFdName(),
						        DateUtil.convertDateToString(new Date(),DateUtil.PATTERN_DATETIME)
						      }
				 )
				);
		update(topic);

	}

	/**
	 * 将该主题置顶
	 * 
	 * @param fdId
	 * @throws Exception
	 */
	@Override
	public void doStick(String fdId, Long fdTopDays) throws Exception {
		KmForumTopic topic = (KmForumTopic) findByPrimaryKey(fdId);
		if (UserOperHelper.allowLogOper("doStick", getModelName())) {
			// 添加日志信息
			UserOperContentHelper.putUpdate(topic);
		}
		topic.setFdSticked(new Boolean(true));
		topic.setFdTopTime(new Date());

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_MONTH, fdTopDays.intValue());

		topic.setFdTopEndTime(calendar.getTime());
		update(topic);
	}

	/**
	 * 将该主题取消置顶
	 * 
	 * @param fdId
	 * @throws Exception
	 */
	@Override
	public void undoStick(String fdId) throws Exception {
		KmForumTopic topic = (KmForumTopic) findByPrimaryKey(fdId);
		if (UserOperHelper.allowLogOper("undoStick", getModelName())) {
			// 添加日志信息
			UserOperContentHelper.putUpdate(topic);
		}
		topic.setFdSticked(new Boolean(false));
		topic.setFdTopTime(null);
		topic.setFdTopEndTime(null);
		update(topic);
	}

	/**
	 * 将该主题置为精华
	 * 
	 * @param fdId
	 * @throws Exception
	 */
	@Override
	public void doPink(String fdId) throws Exception {
		KmForumTopic topic = (KmForumTopic) findByPrimaryKey(fdId);
		if (UserOperHelper.allowLogOper("doPink", getModelName())) {
			// 添加日志信息
			UserOperContentHelper.putUpdate(topic);
		}
		topic.setFdPinked(new Boolean(true));
		update(topic);
		// 置为精华时增加积分
		if (topic.getFdPoster() != null) {
			int categoryScore = topic.getKmForumCategory().getFdPinkScore();
			this.getBaseDao()
					.getHibernateSession()
					.createQuery(
							"update KmForumScore score set score.fdScore=score.fdScore+:categoryScore where score.fdId=:fdId")
					.setParameter("categoryScore", categoryScore)
					.setParameter("fdId", topic.getFdPoster().getFdId()).executeUpdate();
		}
	}

	/**
	 * 将该主题取消精华
	 * 
	 * @param fdId
	 * @throws Exception
	 */
	@Override
	public void undoPink(String fdId) throws Exception {
		KmForumTopic topic = (KmForumTopic) findByPrimaryKey(fdId);
		if (UserOperHelper.allowLogOper("undoPink", getModelName())) {
			// 添加日志信息
			UserOperContentHelper.putUpdate(topic);
		}
		topic.setFdPinked(new Boolean(false));
		update(topic);
		// 置为精华时减积分
		if (topic.getFdPoster() != null) {
			int categoryScore = topic.getKmForumCategory().getFdPinkScore();
			this.getBaseDao()
					.getHibernateSession()
					.createQuery(
							"update KmForumScore score set score.fdScore=score.fdScore-:categoryScore where score.fdId=:fdId")
					.setParameter("categoryScore", categoryScore)
					.setParameter("fdId", topic.getFdPoster().getFdId()).executeUpdate();
		}
	}

	/**
	 * 将该主题转移
	 * 
	 * @param fdId
	 *            主题ID
	 * @param fdForumId
	 *            目标版块ID
	 * @throws Exception
	 */
	@Override
	public void move(String fdId, String fdForumId) throws Exception {
		KmForumTopic topic = (KmForumTopic) findByPrimaryKey(fdId);
		// 源版块累减
		KmForumCategory foreCategory = topic.getKmForumCategory();
		if (foreCategory.getFdTopicCount()==null){
			foreCategory.setFdTopicCount(0);
		}
		topic.getKmForumCategory().setFdTopicCount(
				new Integer(topic.getKmForumCategory().getFdTopicCount()
						.intValue() - 1));
		if (foreCategory.getFdPostCount() == null){
			foreCategory.setFdPostCount(0); 	
		}
		topic.getKmForumCategory().setFdPostCount(
				new Integer(topic.getKmForumCategory().getFdPostCount()
						.intValue()
						- topic.getForumPosts().size()));
		kmForumCategoryService.update(topic.getKmForumCategory());
		// 目标版块累加
		KmForumCategory object = (KmForumCategory) kmForumCategoryService
				.findByPrimaryKey(fdForumId);
		if (object.getFdTopicCount() != null) {
			object.setFdTopicCount(new Integer(object.getFdTopicCount()
					.intValue() + 1));
		} else {
			object.setFdTopicCount(new Integer(1));
		}
		if (object.getFdPostCount() != null) {
			object.setFdPostCount(new Integer(object.getFdPostCount()
					.intValue() + topic.getForumPosts().size()));
		} else {
			object.setFdPostCount(new Integer(topic.getForumPosts().size()));
		}
		kmForumCategoryService.update(object);
		topic.setKmForumCategory(object);
		// 版块转移添加重要信息
		topic.setFdImportInfo(ResourceUtil.getString(
				"kmForumPost.moveInfo",
				"km-forum",
				null,
				new Object[] {
						UserUtil.getUser().getFdName(),
						DateUtil.convertDateToString(new Date(),
								DateUtil.PATTERN_DATETIME),
						foreCategory.getFdName(), object.getFdName() }));
		// 添加日志信息
		if (UserOperHelper.allowLogOper("move", getModelName())) {
			UserOperContentHelper.putUpdate(topic);
		}
		update(topic);
	}

	/**
	 * 点击计数
	 * 
	 * @param fdId
	 * @throws Exception
	 */
	@Override
	public void hitCount(String fdId) throws Exception {
		KmForumTopic topic = (KmForumTopic) findByPrimaryKey(fdId);
		if (topic != null) {
			String fdStatus = topic.getFdStatus();
			if (StringUtil.isNotNull(fdStatus)) {
				if (!fdStatus.equals(SysDocConstant.DOC_STATUS_DRAFT)) {
					topic.setFdHitCount(new Integer(topic.getFdHitCount()
							.intValue() + 1));
					update(topic);
				}
			} else {
				topic.setFdHitCount(new Integer(topic.getFdHitCount()
						.intValue() + 1));
				update(topic);
			}
		}
	}

	@Override
	public void delete(IBaseModel model) throws Exception {
		KmForumTopic topic = (KmForumTopic) model;
		if (!topic.getFdStatus().equals(SysDocConstant.DOC_STATUS_DRAFT)) {
			// 主题数减1
			if (topic.getKmForumCategory().getFdTopicCount() == null) {
				topic.getKmForumCategory().setFdTopicCount(0);
			}
			topic.getKmForumCategory().setFdTopicCount(
					new Integer(topic.getKmForumCategory().getFdTopicCount()
							.intValue() - 1));
			// 减帖子总数
			if (topic.getKmForumCategory().getFdPostCount() == null) {
				topic.getKmForumCategory().setFdPostCount(0);
			}
			topic.getKmForumCategory().setFdPostCount(
					new Integer(topic.getKmForumCategory().getFdPostCount()
							.intValue()
							- topic.getForumPosts().size()));
			kmForumCategoryService.update(topic.getKmForumCategory());
			// 计算用户回贴数,用户发帖数
			int topicReply = 0;// 用户发帖数
			if (!Boolean.TRUE.equals(topic.getFdIsAnonymous())
					&& topic.getFdPoster() != null) {
				topicReply = 1;
			}
			Map<String, Integer> userRMap = new HashMap<String, Integer>();// 用户回帖数
			List posts = topic.getForumPosts();
			if (posts != null && !posts.isEmpty()) {
				for (int i = 0; i < posts.size(); i++) {
					KmForumPost post = (KmForumPost) posts.get(i);
					if (post.getFdFloor() == null || post.getFdFloor() == 1
							|| post.getFdIsAnonymous()) {
						continue;
					}
					SysOrgPerson poster = post.getFdPoster();
					if (poster == null) {
						continue;
					}
					String fdPosterId = poster.getFdId();
					if (!userRMap.containsKey(fdPosterId)) {
						userRMap.put(fdPosterId, 0);
					}
					Integer replyCount = userRMap.get(fdPosterId);
					userRMap.put(fdPosterId, replyCount + 1);
				}

				// 更新用户发帖数
				SysOrgElement poster = topic.getFdPoster();
				if (poster != null) { // 匿名发帖时无发起人
					KmForumScore score = (KmForumScore) getBaseDao()
							.findByPrimaryKey(topic.getFdPoster().getFdId(),
									KmForumScore.class, true);
					if (score != null) {
						Integer fdPostCount = score.getFdPostCount() == null ? 0
								: score.getFdPostCount();
						fdPostCount = fdPostCount - topicReply;
						fdPostCount = fdPostCount > 0 ? fdPostCount : 0;
						score.setFdPostCount(fdPostCount);
						this.getkmForumScoreService().update(score);
					}
				}
				if (!userRMap.isEmpty()) {
					for (String key : userRMap.keySet()) {
						Integer count = userRMap.get(key);
						KmForumScore userScore = (KmForumScore) getBaseDao()
								.findByPrimaryKey(key, KmForumScore.class,
										true);
						if (userScore != null) {
							Integer fdReplyCount = userScore
									.getFdReplyCount() == null ? 0
											: userScore.getFdReplyCount();
							fdReplyCount = fdReplyCount-count;
							fdReplyCount = fdReplyCount > 0 ? fdReplyCount : 0;
							userScore.setFdReplyCount(fdReplyCount);
							this.getkmForumScoreService().update(userScore);
						}
					}
				}
			}
		}
		super.delete(topic);
	}

	// 为兼容老数据用于判断是否需要更新所在树的层级id
	@Override
	public void updateHierarchyId(String fdForumId) throws Exception {
		List kmForumCategoryList = kmForumCategoryService.findList("fdId='"
				+ fdForumId + "'", null);
		if (kmForumCategoryList.size() > 0) {
			KmForumCategory kmForumCategory = (KmForumCategory) kmForumCategoryList
					.get(0);
			// 为兼容老数据如果层级id为空则更新所在层级树的层级id
			if (StringUtil.isNull(kmForumCategory.getFdHierarchyId())) {
				kmForumCategoryService.updateHierarchyId(kmForumCategory);
			}
		}
	}

	@Override
	public void updateTopAgent() throws Exception {
		((IKmForumTopicDao) getBaseDao()).updateTopAgent();
	}

	public void setKmForumCategoryService(
			IKmForumCategoryService kmForumCategoryService) {
		this.kmForumCategoryService = kmForumCategoryService;
	}

	@Override
	public int updateDucmentCategory(String ids, String forumId)
			throws Exception {
		// 添加日志信息
		if (UserOperHelper.allowLogOper("updateDucmentCategory",
				getModelName())) {
			UserOperContentHelper.putUpdate(ids)
					.putSimple("forumId", null, forumId);
		}
		return ((IKmForumTopicDao) this.getBaseDao()).updateDucmentCategory(
				ids, forumId);
	}
	
	/**
	 * 获得发帖数和获取回帖数 根据分类id
	 * @param categoryId 
	 * @return Integer [] 数组第一个数字是发帖数，第二个数字是回帖数
	 * @throws Exception
	 */
	@Override
	public Integer [] getForumTopicAndReplyCount(String categoryId) throws Exception{
		
		Integer [] countArr = new Integer[2];
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("count(*),sum(fdReplyCount)");
		String whereBlock = "kmForumTopic.fdStatus!=:fdStatus";
		hqlInfo.setParameter("fdStatus", "10");
		if (StringUtil.isNotNull(categoryId)) {
			whereBlock += "  and kmForumTopic.kmForumCategory.fdHierarchyId like :categoryId";
			hqlInfo.setParameter("categoryId", "%" + categoryId + "x%");
		}
		hqlInfo.setWhereBlock(whereBlock);
		List list = this.findValue(hqlInfo);
		Object[] result = (Object[]) list.get(0);
		int topicCount = result[0] == null ? 0 : Integer.parseInt(result[0].toString());
        int replyCount = result[1] == null ? 0 : Integer.parseInt(result[1].toString());
        countArr[0] = topicCount;
        countArr[1] = replyCount;
		return countArr;
		
	}

	@Override
	public void updateForumExpire() throws Exception {
		String fdName = ResourceUtil.getString("kmForumPost.sysConclude", "km-forum");
		Date nowDate = new Date();
		String fdConcludeInfo = ResourceUtil.getString("kmForumPost.concludeInfo", "km-forum", null,
				new Object[] { fdName, DateUtil.convertDateToString(nowDate, DateUtil.PATTERN_DATETIME) });
		// String sql = "update km_forum_topic topic set fd_status=:status1, fd_conclude_info=:conclude where fd_status=:status2 and fd_forum_id in (select fd_id from km_forum_category where fd_timeliness=:fdTimeLiness and fd_timeliness_date<=datediff(now(),topic.doc_create_time))";
		String sql = null;
		String dialect = ResourceUtil.getKmssConfigString("hibernate.dialect").toLowerCase();
		if (dialect.indexOf("oracle") > 0) {
			sql = "update km_forum_topic topic set fd_status=:status1, fd_conclude_info=:conclude where fd_status=:status2 and fd_forum_id in (select fd_id from km_forum_category where fd_timeliness=:fdTimeLiness and fd_timeliness_date<=to_number(sysdate-topic.doc_create_time))";
		} else if (dialect.indexOf("sqlserver") > 0) {
			sql = "update km_forum_topic set fd_status=:status1, fd_conclude_info=:conclude where fd_status=:status2 and fd_id in (select t.fd_id from km_forum_category c,km_forum_topic t where c.fd_id=t.fd_forum_id and c.fd_timeliness=:fdTimeLiness and c.fd_timeliness_date<=datediff(day,t.doc_create_time,getdate()))";
		} else if (dialect.indexOf("db2") > 0) {
			sql = "update km_forum_topic topic set fd_status=:status1, fd_conclude_info=:conclude where fd_status=:status2 and fd_forum_id in (select fd_id from km_forum_category where fd_timeliness=:fdTimeLiness and fd_timeliness_date<=(days(CURRENT DATE)-days(topic.doc_create_time)))";
		} else if(dialect.indexOf("polardb4oracle") > 0) {
			sql = "update km_forum_topic topic set fd_status=:status1,fd_conclude_info=:conclude where fd_status=:status2 and fd_forum_id in (select fd_id from km_forum_category where fd_timeliness=:fdTimeLiness and fd_timeliness_date<=cast(EXTRACT(epoch FROM age(now(),topic.doc_create_time))/86400 as int) )";
		}
		if (StringUtil.isNotNull(sql)) {
			// 主流数据库支持日期计算函数
			NativeQuery query = getBaseDao().getHibernateSession().createNativeQuery(sql);
			query.addSynchronizedQuerySpace("km_forum_topic");
			query.setParameter("status1", SysDocConstant.DOC_STATUS_EXPIRE);
			query.setParameter("status2", SysDocConstant.DOC_STATUS_PUBLISH);
			query.setParameter("fdTimeLiness", Boolean.TRUE);
			query.setParameter("conclude", fdConcludeInfo);
			query.executeUpdate();
		} else {
			// 非主流数据库不支持日期计算函数，需要使用原始逻辑处理
			// 查询是否开启结贴功能
			sql = "select fd_id, fd_timeliness_date from km_forum_category where fd_timeliness = :fdTimeLiness and fd_timeliness_date is not null";
			NativeQuery query = getBaseDao().getHibernateSession().createNativeQuery(sql);
			query.setCacheable(true);
			query.setCacheMode(CacheMode.NORMAL);
			query.setCacheRegion("km-forum");
			query.addScalar("fd_id", StandardBasicTypes.STRING).addScalar("fd_timeliness_date",StandardBasicTypes.INTEGER);
			query.setParameter("fdTimeLiness", Boolean.TRUE);
			List<Object[]> list = query.list();
			if (CollectionUtils.isNotEmpty(list)) {
				sql = "update km_forum_topic set fd_status = :status1, fd_conclude_info = :conclude where fd_status = :status2 and fd_forum_id = :cateId and doc_create_time <= :date";
				for (Object[] objs : list) {
					String cateId = (String) objs[0];
					int timelinessDate = StringUtil.getIntFromString(objs[1].toString(), 0);
					// 更新论坛中创建时间小于自动结贴时间
					Calendar cal = Calendar.getInstance();
					cal.set(Calendar.DAY_OF_YEAR, -timelinessDate);
					query = getBaseDao().getHibernateSession().createNativeQuery(sql);
					query.setParameter("status1", SysDocConstant.DOC_STATUS_EXPIRE);
					query.setParameter("status2", SysDocConstant.DOC_STATUS_PUBLISH);
					query.setParameter("conclude", fdConcludeInfo);
					query.setParameter("cateId", cateId);
					query.setParameter("date", cal.getTime());
					query.executeUpdate();
				}
			}
		}
	}

	@Override
	public KmForumTopic getRecentTopicByCategoryId(String categoryId, boolean filterDraft)
			throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		String whereBlock = "kmForumCategory.fdHierarchyId like '%"
				+ categoryId + "%'";
		if(filterDraft) {
			whereBlock += " and fdStatus!='10'";
		}
		hqlInfo.setWhereBlock(whereBlock);
		hqlInfo.setOrderBy("docCreateTime desc");
		List<KmForumTopic> topics = findList(hqlInfo);
		if (topics != null && topics.size() > 0) {
			return topics.get(0);
		}
		return null;
	}
	
	@Override
	public Map<String, ?> getTopicList(RequestContext request)
			throws Exception {
		Map<String, Object> rtnMap = new HashMap<>();
		JSONArray datas = new JSONArray();// 列表形式使用
		Page page = Page.getEmptyPage();// 简单列表使用
		String para = request.getParameter("rowsize");
		String type = request.getParameter("type");
		String forumType = request.getParameter("forumZxType");
		String persontype = request.getParameter("persontype");
		int rowsize = 10;
		if (!StringUtil.isNull(para)) {
			rowsize = Integer.parseInt(para);
		}
		if ("owner".equals(type)) {
			page = getOwnerTopicData(request, rowsize);
		} else if ("hot".equals(type)) {
			page = getHotTopicData(request, rowsize);
		} else if ("pinked".equals(type)) {
			page = getPinkedTopicData(request, rowsize);
		} else {
			page = getTopicData(request, rowsize);
		}
		List<KmForumTopic> topics = page.getList();
		int hotReplyCount = Integer
				.parseInt(new KmForumConfig().getHotReplyCount());
		for (KmForumTopic topic : topics) {
			JSONObject data = new JSONObject();
			if (topic.getFdReplyCount() == null) {
				topic.setFdReplyCount(0);
			}
			/*if (topic.getFdLastPoster() == null
					&& topic.getFdPoster() != null) {
				 topic.setFdLastPosterName(topic.getFdPoster().getFdName());
				 topic.setFdLastPostTime(topic.getDocCreateTime());
			}*/
			data.put("text", topic.getDocSubject());
			

			if ("creator".equals(persontype) && topic.getFdPoster() != null) {
				if (request.isCloud()) {
					data.put("creator",
							ListDataUtil.buildCreator(topic.getFdPoster()));
				} else {
					data.put("creator", topic.getFdPoster().getFdName());
				}
			} else {
				if (request.isCloud()) {
					if (topic.getFdLastPoster() != null) {
						data.put("creator",
								ListDataUtil
										.buildCreator(topic.getFdLastPoster()));
					} else {
						JSONObject creator = new JSONObject();
						if (StringUtil.isNotNull(topic.getFdLastPosterName())) {
							creator.put("fdName", topic.getFdLastPosterName());
						} else {
							creator.put("fdName", topic.getFdPoster().getFdName());
						}
						data.put("creator", creator);
					}
				} else {
					if (StringUtil.isNotNull(topic.getFdLastPosterName())) {
						data.put("creator", topic.getFdLastPosterName());
					} else {
						data.put("creator", topic.getFdPoster().getFdName());
					}
				}
			}
			if (request.isCloud()) {
				if("creator".equals(persontype)) {
                    data.put("created", topic.getDocCreateTime().getTime());
                } else {
                    data.put("created", topic.getFdLastPostTime().getTime());
                }
				// 图标
				List<IconDataVO> icons = new ArrayList<>(1);
				IconDataVO icon = null;
				// 精华
				if (topic.getFdPinked() != null
						&& topic.getFdPinked()) {
					icon = new IconDataVO();
					icon.setName("essence");
					icon.setType("bitmap");
					icons.add(icon);
				}
				// 置顶
				if (topic.getFdSticked() != null && topic.getFdSticked()) {
					icon = new IconDataVO();
					icon.setName("top");
					icon.setType("bitmap");
					icons.add(icon);
				}
				// 热帖
				if (topic.getFdReplyCount() != null
						&& topic.getFdReplyCount() >= hotReplyCount) {
					icon = new IconDataVO();
					icon.setName("hot");
					icon.setType("bitmap");
					icons.add(icon);
				}
				data.put("icons", icons);
				data.put("cateName", topic.getKmForumCategory().getFdName());
				data.put("cateHref",
						"/km/forum/indexCriteria.jsp?categoryId="
								+ topic.getKmForumCategory().getFdId());
				data.put("desc", topic.getDocSummary());
				// 阅读数、回复数
				JSONArray infos = new JSONArray();
				JSONObject info = new JSONObject();
				info.put("title",
						ResourceUtil.getString("km-forum:kmForumPost.readCount")
								+ " " + topic.getFdHitCount());
				// icon = new IconDataVO();
				// icon.setName("eye");
				// info.put("icon", icon);
				infos.add(info);
				info = new JSONObject();
				info.put("title",
						ResourceUtil
								.getString("km-forum:kmForumPost.replyCount")
								+ " " + topic.getFdReplyCount());
				// icon = new IconDataVO();
				// icon.setName("message");
				// info.put("icon", icon);
				infos.add(info);
				data.put("infos", infos);
			} else {
				data.put("created", DateUtil.convertDateToString(
						topic.getFdLastPostTime(), DateUtil.TYPE_DATE, null));
				data.put("catename", topic.getKmForumCategory().getFdName());
				data.put("catehref",
						"/km/forum/indexCriteria.jsp?categoryId="
								+ topic.getKmForumCategory().getFdId());
				data.put("evalcount", topic.getFdReplyCount());
				data.put("readcount", topic.getFdHitCount());
				int markCount = getSysBookmarkMainServiceImp()
						.getMarkCountByModel(topic);
				data.put("docscore", markCount);
				if (forumType == null) {
					data.put("otherinfo", " ("
							+ ResourceUtil.getString(
									"portlet.kmForum.fdPostCount.portlet",
									"km-forum")
							+ topic.getFdReplyCount()
							+ ") ");
				}
			}
			StringBuffer sb = new StringBuffer();
			sb.append("/km/forum/km_forum/kmForumPost.do?method=view");
			sb.append("&fdForumId=" + topic.getKmForumCategory().getFdId());
			sb.append("&fdTopicId=" + topic.getFdId());
			data.put("href", sb.toString());

			datas.add(data);
		}
		rtnMap.put("datas", datas);
		rtnMap.put("page", page);
		return rtnMap;
	}

	@Override
	public Page getRankList(RequestContext request) throws Exception {
		String type = request.getParameter("type");
		Page page = Page.getEmptyPage();
		if ("score".equals(type)) {
			page = getScoreData(request);
		} else if ("postCount".equals(type)) {
			page = getPostCountData(request);
		}
		return page;
	}

	@Override
	public Page getTopicNewList(RequestContext request) throws Exception {
		Page page = Page.getEmptyPage();// 简单列表使用 KmForumTopic
		String para = request.getParameter("rowsize");
		String type = request.getParameter("type");
		int rowsize = 10;
		if (!StringUtil.isNull(para)) {
			rowsize = Integer.parseInt(para);
		}
		if ("owner".equals(type)) {
			page = getOwnerTopicData(request, rowsize);
		} else if ("hot".equals(type)) {
			page = getHotTopicData(request, rowsize);
		} else if ("pinked".equals(type)) {
			page = getPinkedTopicData(request, rowsize);
		} else {
			page = getTopicData(request, rowsize);
		}
		return page;
	}

	/**
	 * 获取部门或公司员工的论坛回帖数排名
	 * 
	 * @param requestInfo
	 * @return
	 * @throws Exception
	 */
	private Page getPostCountData(RequestContext request) throws Exception {
		String org = request.getParameter("org");
		String para = request.getParameter("rowsize");
		int rowsize = 10;
		if (!StringUtil.isNull(para)) {
            rowsize = Integer.parseInt(para);
        }
		StringBuffer sb = new StringBuffer();
		sb.append("kmForumScore.fdPostCount is not null");
		HQLInfo hqlInfo = new HQLInfo();
		if ("dept".equals(org) && UserUtil.getUser().getFdParent() != null) {
			sb.append(" and kmForumScore.person.hbmParent.fdId = :parentId");
			hqlInfo.setParameter("parentId", UserUtil.getUser().getFdParent()
					.getFdId());
		}
		hqlInfo.setWhereBlock(sb.toString());
		hqlInfo.setOrderBy("kmForumScore.fdPostCount desc");
		hqlInfo.setRowSize(rowsize);
		hqlInfo.setPageNo(1);
		hqlInfo.setGetCount(false);
		Page page = getkmForumScoreService().findPage(hqlInfo);
		List rtnList = page.getList();
		for (int i = 0; i < rtnList.size(); i++) {
			KmForumScore kmForumScore = (KmForumScore) rtnList.get(i);
			// 用户昵称
			if (StringUtil.isNull(kmForumScore.getFdNickName())) {
                kmForumScore
                        .setFdNickName(kmForumScore.getPerson().getFdName());
            }
			// 回帖数
			if (kmForumScore.getFdPostCount() == null) {
				kmForumScore.setFdPostCount(0);
			}
		}
		return page;
	}

	/**
	 * 获取部门或公司员工的论坛积分排名
	 * 
	 * @param requestInfo
	 * @return
	 * @throws Exception
	 */
	private Page getScoreData(RequestContext request) throws Exception {
		String org = request.getParameter("org");
		String para = request.getParameter("rowsize");
		int rowsize = 10;
		if (!StringUtil.isNull(para)) {
            rowsize = Integer.parseInt(para);
        }
		StringBuffer sb = new StringBuffer();
		sb.append("kmForumScore.fdScore is not null");
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setJoinBlock(
				",com.landray.kmss.sys.organization.model.SysOrgElement OrgElement");
		sb.append(
				" and OrgElement.fdId=kmForumScore.fdId and OrgElement.fdIsAvailable = :fdIsAvailable");
		if ("dept".equals(org) && UserUtil.getUser().getFdParent() != null) {
			sb.append(" and kmForumScore.person.hbmParent.fdId = :parentId ");
			hqlInfo.setParameter("parentId", UserUtil.getUser().getFdParent()
					.getFdId());
		}
		hqlInfo.setParameter("fdIsAvailable", Boolean.TRUE);
		hqlInfo.setWhereBlock(sb.toString());
		hqlInfo.setOrderBy("kmForumScore.fdScore desc");
		hqlInfo.setRowSize(rowsize);
		hqlInfo.setPageNo(1);
		hqlInfo.setGetCount(false);
		Page page = getkmForumScoreService().findPage(hqlInfo);
		List rtnList = page.getList();
		for (int i = 0; i < rtnList.size(); i++) {
			KmForumScore kmForumScore = (KmForumScore) rtnList.get(i);
			// 用户昵称
			if (StringUtil.isNull(kmForumScore.getFdNickName())) {
                kmForumScore
                        .setFdNickName(kmForumScore.getPerson().getFdName());
            }
			// 积分
			if (kmForumScore.getFdScore() == null) {
				kmForumScore.setFdScore(0);
			}
		}
		return page;
	}
	/**
	 * 最新帖子
	 */
	private Page getTopicData(RequestContext request, int rowsize)
			throws Exception {
		String fdForumIds = request.getParameter("fdForumIds");
		StringBuffer hql = new StringBuffer();
		HQLInfo hqlInfo = new HQLInfo();
		if (StringUtil.isNotNull(fdForumIds)) {
			String hqlFrame = "kmForumTopic.kmForumCategory.fdId in(:categoryIds)";
			hqlInfo.setParameter("categoryIds", kmForumCategoryService
					.expentCategoryToModuleIds(request.getRequest(),
							fdForumIds));
			hql.append(hqlFrame);
		}
		if (hql.length() > 0) {
			hql.append(
					" and (kmForumTopic.fdStatus = :fdStatus1 or kmForumTopic.fdStatus = :fdStatus2)");
			hqlInfo.setParameter("fdStatus1",
					SysDocConstant.DOC_STATUS_PUBLISH);
			hqlInfo.setParameter("fdStatus2", SysDocConstant.DOC_STATUS_EXPIRE);
		} else {
			hql.append(
					" (kmForumTopic.fdStatus = :fdStatus1 or kmForumTopic.fdStatus = :fdStatus2)");
			hqlInfo.setParameter("fdStatus1",
					SysDocConstant.DOC_STATUS_PUBLISH);
			hqlInfo.setParameter("fdStatus2", SysDocConstant.DOC_STATUS_EXPIRE);
		}
		hqlInfo.setWhereBlock(hql.toString());
		hqlInfo.setPageNo(1);
		hqlInfo.setOrderBy(
				"kmForumTopic.fdSticked desc,kmForumTopic.fdLastPostTime desc");
		hqlInfo.setRowSize(rowsize);
		hqlInfo.setGetCount(false);
		// 时间参数
		bulidTimeScope(hqlInfo, request);
		return findPage(hqlInfo);
	}

	/**
	 * 精华帖子
	 */
	private Page getPinkedTopicData(RequestContext request, int rowsize)
			throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		String whereBlock = " kmForumTopic.fdPinked=:fdPinked ";
		hqlInfo.setParameter("fdPinked", Boolean.TRUE);
		String fdForumIds = request.getParameter("fdForumIds");

		if (StringUtil.isNotNull(fdForumIds)) {
			String hqlFrame = "kmForumTopic.kmForumCategory.fdId in(:categoryIds)";
			hqlInfo.setParameter("categoryIds",
					kmForumCategoryService.expentCategoryToModuleIds(
							request.getRequest(), fdForumIds));
			whereBlock += " and " + hqlFrame;
		}

		hqlInfo.setWhereBlock(whereBlock);
		hqlInfo.setOrderBy(
				"kmForumTopic.fdSticked desc,kmForumTopic.fdLastPostTime desc");
		hqlInfo.setPageNo(1);
		hqlInfo.setRowSize(rowsize);
		hqlInfo.setGetCount(false);
		// 时间参数
		bulidTimeScope(hqlInfo, request);
		return findPage(hqlInfo);
	}
	/**
	 * 我的帖子
	 */
	private Page getOwnerTopicData(RequestContext request, int rowsize)
			throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		String whereBlock = "kmForumTopic.fdPoster.fdId = :posterId";
		hqlInfo.setParameter("posterId", UserUtil.getUser().getFdId());
		String fdForumIds = request.getParameter("fdForumIds");
		if (StringUtil.isNotNull(fdForumIds)) {
			String hqlFrame = "kmForumTopic.kmForumCategory.fdId in(:categoryIds)";
			hqlInfo.setParameter("categoryIds", kmForumCategoryService
					.expentCategoryToModuleIds(request.getRequest(),
							fdForumIds));
			whereBlock += " and " + hqlFrame;
		}
		hqlInfo.setWhereBlock(whereBlock);
		hqlInfo.setOrderBy(
				"kmForumTopic.docAlterTime desc , kmForumTopic.docCreateTime desc");
		hqlInfo.setRowSize(rowsize);
		hqlInfo.setPageNo(1);
		hqlInfo.setGetCount(false);
		// 时间参数
		bulidTimeScope(hqlInfo, request);
		return findPage(hqlInfo);
	}

	/**
	 * 门户时间参数
	 * 
	 * @param hqlInfo
	 * @param request
	 */
	private void bulidTimeScope(HQLInfo hqlInfo, RequestContext request) {
		// 时间范围参数
		String scope = request.getParameter("scope");
		if (StringUtil.isNotNull(scope) && !"no".equals(scope)) {
			String block = hqlInfo.getWhereBlock();
			hqlInfo.setWhereBlock(StringUtil.linkString(block, " and ",
					"kmForumTopic.docCreateTime > :fdStartTime"));
			hqlInfo.setParameter("fdStartTime",
					PortletTimeUtil.getDateByScope(scope));
		}
	}

	/**
	 * 热门帖子
	 */
	private Page getHotTopicData(RequestContext request, int rowsize)
			throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		String whereBlock = " kmForumTopic.fdReplyCount>=:fdReplyCount and (kmForumTopic.fdStatus = :fdStatus1 or kmForumTopic.fdStatus = :fdStatus2)";
		hqlInfo.setParameter("fdReplyCount",
				Integer.parseInt(new KmForumConfig().getHotReplyCount()));
		hqlInfo.setParameter("fdStatus1", SysDocConstant.DOC_STATUS_PUBLISH);
		hqlInfo.setParameter("fdStatus2", SysDocConstant.DOC_STATUS_EXPIRE);
		String fdForumIds = request.getParameter("fdForumIds");

		if (StringUtil.isNotNull(fdForumIds)) {
			String hqlFrame = "kmForumTopic.kmForumCategory.fdId in(:categoryIds)";
			hqlInfo.setParameter("categoryIds",
					kmForumCategoryService.expentCategoryToModuleIds(
							request.getRequest(), fdForumIds));
			whereBlock += " and " + hqlFrame;
		}

		hqlInfo.setWhereBlock(whereBlock);
		hqlInfo.setOrderBy(
				"kmForumTopic.fdSticked desc,kmForumTopic.fdLastPostTime desc");
		hqlInfo.setPageNo(1);
		hqlInfo.setRowSize(rowsize);
		hqlInfo.setGetCount(false);
		// 时间参数
		bulidTimeScope(hqlInfo, request);
		return findPage(hqlInfo);
	}

	@Override
	public List getDataList(RequestContext requestInfo) throws Exception {
		List<Map> rtnList = new ArrayList<Map>();
		Page page = Page.getEmptyPage();// 简单列表使用
		String para = requestInfo.getParameter("rowsize");
		String type = requestInfo.getParameter("type");
		String persontype = requestInfo.getParameter("persontype");
		int rowsize = 10;
		if (!StringUtil.isNull(para)) {
			rowsize = Integer.parseInt(para);
		}
		if ("owner".equals(type)) {
			page = getOwnerTopicData(requestInfo, rowsize);
		} else if ("hot".equals(type)) {
			page = getHotTopicData(requestInfo, rowsize);
		} else if ("pinked".equals(type)) {
			page = getPinkedTopicData(requestInfo, rowsize);
		} else {
			page = getTopicData(requestInfo, rowsize);
		}
		List<KmForumTopic> topics = page.getList();
		for (KmForumTopic topic : topics) {
			Map<String, Object> map = new HashMap<>();
			if (topic.getFdReplyCount() == null) {
				topic.setFdReplyCount(0);
			}
			if (topic.getFdLastPoster() == null
					&& topic.getFdPoster() != null) {
				topic.setFdLastPosterName(topic.getFdPoster().getFdName());
				topic.setFdLastPostTime(topic.getDocCreateTime());
			}
			map.put("text", topic.getDocSubject());
			map.put(
					"otherinfo",
					" ("
							+ ResourceUtil.getString(
									"portlet.kmForum.fdPostCount.portlet",
									"km-forum")
							+ topic.getFdReplyCount()
							+ ") ");

			if ("creator".equals(persontype) && topic.getFdPoster() != null) {
				if (requestInfo.isCloud()) {
					map.put("creator",
							ListDataUtil.buildCreator(topic.getFdPoster()));
				} else {
					map.put("creator", topic.getFdPoster().getFdName());
				}
			} else {
				if (requestInfo.isCloud()) {
					JSONObject creator = new JSONObject();
					creator.put("fdName", topic.getFdLastPosterName());
					map.put("creator", creator);
				} else {
					map.put("creator", topic.getFdLastPosterName());
				}
			}
			if (requestInfo.isCloud()) {
				map.put("created", topic.getFdLastPostTime().getTime());
				List<IconDataVO> icons = new ArrayList<>(1);
				IconDataVO icon = new IconDataVO();
				icon.setName("fire");
				icons.add(icon);
				map.put("icons", icons);
				map.put("cateName", topic.getKmForumCategory().getFdName());
				map.put("cateHref",
						"/km/forum/indexCriteria.jsp?categoryId="
								+ topic.getKmForumCategory().getFdId());
			} else {
				map.put("created", DateUtil.convertDateToString(
						topic.getFdLastPostTime(), DateUtil.TYPE_DATE, null));
				map.put("catename", topic.getKmForumCategory().getFdName());
				map.put("catehref",
						"/km/forum/indexCriteria.jsp?categoryId="
								+ topic.getKmForumCategory().getFdId());
			}

			StringBuffer sb = new StringBuffer();
			sb.append("/km/forum/km_forum/kmForumPost.do?method=view");
			sb.append("&fdForumId=" + topic.getKmForumCategory().getFdId());
			sb.append("&fdTopicId=" + topic.getFdId());
			map.put("href", sb.toString());
			if (StringUtil.isNull(topic.getFdThumbInfo())) {
                map.put("image", "/km/forum/resource/images/forum_img_1.png");
            } else {
				String fdThumbInfo = topic.getFdThumbInfo();
				String[] imageArray = fdThumbInfo.split("\\|");
				map.put("image", imageArray[0].replace("&amp;", "&"));
				map.put("imagefrom", "rtf");
			}

			rtnList.add(map);
		}
		return rtnList;
	}
}
