package com.landray.kmss.km.forum.service.spring;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.constant.SysDocConstant;
import com.landray.kmss.constant.SysNotifyConstant;
import com.landray.kmss.km.forum.dao.IKmForumPostDao;
import com.landray.kmss.km.forum.forms.KmForumPostForm;
import com.landray.kmss.km.forum.model.KmForumCategory;
import com.landray.kmss.km.forum.model.KmForumConfig;
import com.landray.kmss.km.forum.model.KmForumPost;
import com.landray.kmss.km.forum.model.KmForumScore;
import com.landray.kmss.km.forum.model.KmForumTopic;
import com.landray.kmss.km.forum.service.IKmForumPostService;
import com.landray.kmss.km.forum.service.IKmForumScoreService;
import com.landray.kmss.km.forum.utils.ForumStringUtil;
import com.landray.kmss.km.forum.utils.SensitiveWordCheckUtil;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.sys.notify.interfaces.NotifyContext;
import com.landray.kmss.sys.notify.interfaces.NotifyReplace;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

/**
 * 创建日期 2006-Sep-05
 * 
 * @author 吴兵 帖子业务接口实现
 */
public class KmForumPostServiceImp extends BaseServiceImp implements
		IKmForumPostService {

	private ISysNotifyMainCoreService sysNotifyMainCoreService;
	protected IKmForumScoreService kmForumScoreService;

	protected IKmForumScoreService getkmForumScoreService() {
		if (kmForumScoreService == null) {
            kmForumScoreService = (IKmForumScoreService) SpringBeanUtil
                    .getBean("kmForumScoreService");
        }
		return kmForumScoreService;
	}

	@Override
	public String add(IExtendForm form, RequestContext requestContext)
			throws Exception {
		// 添加日志信息
		UserOperHelper.logAdd(getModelName());
		KmForumPostForm postForm = (KmForumPostForm) form;
		// 防止重复提交
		IBaseModel forumPost = findByPrimaryKey(postForm.getFdId(), null, true);
		if (forumPost != null) {
			return ((KmForumPost) forumPost).getKmForumTopic().getFdId();
		}
		IBaseModel model = convertFormToModel(form, null, requestContext);
		String fdTopicId = "";
		if (model == null) {
            throw new NoRecordException();
        }
		KmForumPost post = (KmForumPost) model;
		KmForumTopic topic = null;
		KmForumCategory forum = (KmForumCategory) getBaseDao()
				.findByPrimaryKey(postForm.getFdForumId(),
						KmForumCategory.class, false);

		String globalIsAnonymous = new KmForumConfig().getAnonymous();
		if (!forum.getFdAnonymous() || !"true".equals(globalIsAnonymous)) {
			post.setFdIsAnonymous(false);
			postForm.setFdIsAnonymous("false");
		}
		if (!post.getFdIsAnonymous().booleanValue()) {
			post.setFdPoster(UserUtil.getUser());
			postForm.setFdPosterId(UserUtil.getUser().getFdId());
		}
		post.setDocCreateTime(new Date());
		post.setDocSubject(SensitiveWordCheckUtil.filterEmoji(post.getDocSubject()));
		post.setDocContent(SensitiveWordCheckUtil.filterEmoji(post.getDocContent()));
		updatePostSummary(post);

		String isDraftForum = (String) requestContext.getRequest().getAttribute("isDraftForum");
		// 新增话题开始
		if (StringUtil.isNull(postForm.getFdTopicId())) {
			// 新增话题
			topic = new KmForumTopic();
			topic.setFdIsAnonymous(post.getFdIsAnonymous());
			topic.setFdId(postForm.getFdId());
			if (StringUtil.isNotNull(isDraftForum) && "true".equals(isDraftForum)) {
				// 存为草稿则不更新板块主题计算
				topic.setFdStatus(SysDocConstant.DOC_STATUS_DRAFT);
				post.setFdPoster(UserUtil.getUser());
				// topic.setFdLastPosterName(post.getFdPoster().getFdName());
				// topic.setFdLastPostTime(post.getDocCreateTime());
				topic.setFdPoster(post.getFdPoster());
			} else {
				// 更新主题最后回复时间
				topic.setFdLastPostTime(post.getDocCreateTime());
				// 更新版块主题计算
				forum.setFdTopicCount(new Integer(getIntByInteger(forum.getFdTopicCount()) + 1));
				getBaseDao().update(forum);
				topic.setFdStatus(SysDocConstant.DOC_STATUS_PUBLISH);
				if (post.getFdIsAnonymous().booleanValue()) {
					post.setFdPoster(null);
					topic.setFdLastPosterName(ResourceUtil.getString("kmForumTopic.fdIsAnonymous.title", "km-forum",
							new RequestContext().getLocale()));
					topic.setFdPoster(null);
				} else {
					// topic.setFdLastPosterName(post.getFdPoster().getFdName());
					topic.setFdPoster(post.getFdPoster());
				}
			}
			topic.setFdPinked(new Boolean(false));
			topic.setFdSticked(new Boolean(false));
			topic.setFdReplyCount(new Integer(0));
			topic.setFdHitCount(new Integer(0));
			topic.setDocSubject(post.getDocSubject());
			topic.setDocCreateTime(post.getDocCreateTime());
			topic.setDocAlterTime(post.getDocCreateTime());
			topic.setFdImportInfo(postForm.getFdImportInfo());
			topic.setFdConcludeInfo(postForm.getFdConcludeInfo());
			topic.setKmForumCategory(forum);
			topic.setFdIsNotify(post.getFdIsNotify());
			topic.setFdNotifyType(post.getFdNotifyType());
			updateTopicInfo(topic, post);
			fdTopicId = getBaseDao().add(topic);
		} else {
			topic = (KmForumTopic) getBaseDao().findByPrimaryKey(postForm.getFdTopicId(), KmForumTopic.class, false);
		}
		// 新增话题结束
		// 话题如果已结束，返回话题id
		if ("40".equals(topic.getFdStatus())) {
			return topic.getFdId();
		}

		// 匿名不增加积分
		if (!post.getFdIsAnonymous().booleanValue()) {
			// 如果不存为草稿则更新用户积分，发帖计数--开始
			if (StringUtil.isNull(isDraftForum) || !"true".equals(isDraftForum)) {
				KmForumScore score = (KmForumScore) getBaseDao()
						.findByPrimaryKey(post.getFdPoster().getFdId(),
								KmForumScore.class, true);
				if (score == null) {
					score = new KmForumScore();
					// 发帖数1，回复数0
					if ("saveQuick".equals(requestContext.getParameter("method"))){//如果是回复的话则回帖+1，发帖+0
						score.setFdPostCount(new Integer(0));
						score.setFdReplyCount(new Integer(1));
					}else{	//其他的情况，如：发帖				
						score.setFdPostCount(new Integer(1));
						score.setFdReplyCount(new Integer(0));
					}
					if (StringUtil.isNull(postForm.getFdTopicId())) {
						score.setFdScore(new Integer(getIntByInteger(forum
								.getFdMainScore())));
					} else {
						score.setFdScore(new Integer(getIntByInteger(forum
								.getFdResScore())));
					}
					score.setPerson(post.getFdPoster());
					getBaseDao().add(score);
				} else {
					score.getFdSign();
					// 新增话题：主题数+1,新增回复：回复数=1
					if (StringUtil.isNull(postForm.getFdTopicId())) {
						int postCount = new Integer(getIntByInteger(score
								.getFdPostCount()) + 1);
						score.setFdPostCount(postCount);
					} else {
						int replyCount = new Integer(getIntByInteger(score
								.getFdReplyCount()) + 1);
						score.setFdReplyCount(replyCount);
					}
					if (StringUtil.isNull(postForm.getFdTopicId())) {
						score.setFdScore(new Integer(getIntByInteger(score
								.getFdScore())
								+ getIntByInteger(forum.getFdMainScore())));
					} else {
						score.setFdScore(new Integer(getIntByInteger(score
								.getFdScore())
								+ getIntByInteger(forum.getFdResScore())));
					}
					getBaseDao().update(score);
				}
			}
		}
		// 更新用户积分，发帖计数--结束
		// 更新话题信息,过滤主题新增--开始
		if (!StringUtil.isNull(postForm.getFdTopicId())) {
			topic.setFdReplyCount(new Integer(getIntByInteger(topic
					.getFdReplyCount()) + 1));
			if (post.getFdIsAnonymous().booleanValue()) {
				topic.setFdLastPosterName(ResourceUtil.getString(
						"kmForumTopic.fdIsAnonymous.title", "km-forum",
						requestContext.getLocale()));
			} else {
				topic.setFdLastPoster(post.getFdPoster());
				topic.setFdLastPosterName(post.getFdPoster().getFdName());
			}
			topic.setFdLastPostTime(post.getDocCreateTime());
			topic.setDocAlterTime(post.getDocCreateTime());
			getBaseDao().update(topic);
		}
		// 更新话题信息,过滤主题新增--结束

		// 如果不存为草稿则更新论坛信息--开始
		if (StringUtil.isNull(isDraftForum) || !"true".equals(isDraftForum)) {
			forum.setFdPostCount(new Integer(getIntByInteger(forum
					.getFdPostCount()) + 1));
			getBaseDao().update(forum);
		}
		// 更新论坛信息--结束
		// 保存帖子
		post.setFdFloor(new Integer(((IKmForumPostDao) getBaseDao())
				.getCurrentFloor(topic.getFdId()).intValue() + 1));
		post.setKmForumTopic(topic);
		post.setDocAlterTime(post.getDocCreateTime());
		String fdPostId = add(post);
		// 发送提醒
		if (StringUtil.isNull(isDraftForum) || !"true".equals(isDraftForum)) {
			// 判断@的人是不是为空，为空则不处理
			if (!ArrayUtil.isEmpty(post.getFdPostNotifier())) {
				// 给@的用户发送待办通知
				sendNotify(post, String.valueOf(post.getFdIsAnonymous()), null);
			}
			// 如果是回帖的话，把该用户的待办置为已办
			sysNotifyMainCoreService.getTodoProvider().removePerson(post.getKmForumTopic(), ForumStringUtil.KM_FORUM_POST_NOTIFIER,
					post.getFdPoster());
		}
		if (isDraftForum != null && "true".equals(isDraftForum)) {
			return fdPostId;
		}
		return StringUtil.isNotNull(fdTopicId) ? fdTopicId : fdPostId;
	}

	private int getIntByInteger(Integer o) {
		return (o == null ? 0 : o.intValue());
	}

	private void updatePostSummary(KmForumPost post) {
		String text = post.getDocContent();
		text = StringUtil.clearHTMLTag(text);
		if (StringUtil.isNotNull(text)) {
			if (text.length() > 198) {
				post.setDocSummary(text.substring(0, 196) + "..");
			}else{
				post.setDocSummary("");
			}
		}
	}

	private void updateTopicInfo(KmForumTopic topic, KmForumPost post) {
		List<String> imgs = getRtfPics(post.getDocContent());
		if (!imgs.isEmpty()) {
			topic.setFdThumbInfo(getLimitString(StringUtil.join(imgs, "|")));
		} else {
			topic.setFdThumbInfo(null);
		}
		String text = post.getDocSummary();
		if (StringUtil.isNotNull(text)) {
			topic.setDocSummary(text);
		}
	}

	private String getLimitString(String limStr) {
		if (StringUtil.isNotNull(limStr) && limStr.length() > 1500) {
			if (limStr.lastIndexOf("|") > -1) {
				limStr = limStr.substring(0, limStr.lastIndexOf("|"));
				return getLimitString(limStr);
			} else {
				return null;
			}
		}
		return limStr;
	}

	private final static String imgReg = "<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>";

	private List<String> getRtfPics(String docContent) {
		List<String> imgs = new ArrayList<String>();
		Matcher imgMatcher = Pattern.compile(imgReg).matcher(docContent);
		if (StringUtil.isNotNull(docContent)) {
			int i = 0;
			while (imgMatcher.find()) {
				String src = imgMatcher.group(1);
				if (StringUtil.isNotNull(src)
						&& src
								.indexOf("/resource/fckeditor/editor/filemanager/download") > -1) {// 剔除表情
					imgs.add(imgMatcher.group(1));
					i++;
					if (i > 2) {
						break;
					}
				}
			}
		}
		return imgs;
	}

	@Override
	public void update(IExtendForm form, RequestContext requestContext)
			throws Exception {
		String isDraftForum = (String) requestContext.getRequest()
				.getAttribute("isDraftForum");
		UserOperHelper.logUpdate(getModelName());
		IBaseModel model = convertFormToModel(form, null, requestContext);
		if (model == null) {
            throw new NoRecordException();
        }
		KmForumPost oldPost = (KmForumPost) this.findByPrimaryKey(form.getFdId());
		KmForumPost post = (KmForumPost) model;
		post.setDocAlterTime(new Date());
		updatePostSummary(post);
		post.getKmForumTopic().setDocAlterTime(post.getDocAlterTime());
		// post.getKmForumTopic().setFdLastPostTime(post.getDocAlterTime());
		if (StringUtil.isNull(isDraftForum) || !"true".equals(isDraftForum)) {
			if (!post.getKmForumTopic().getFdStatus().equals(
					SysDocConstant.DOC_STATUS_DRAFT)) {
				post.setFdNote(ResourceUtil.getString("kmForumPost.edit.title",
						"km-forum", requestContext.getLocale(), new Object[] {
								DateUtil.convertDateToString(post
										.getDocAlterTime(),
										DateUtil.TYPE_DATETIME, requestContext
												.getLocale()),
								UserUtil.getUser().getFdName() }));
				post.setFdAlteror(UserUtil.getUser());
			}
		}
		// 如果是第一个帖子则用此帖子的subject更新topic中的subject
		if (post.getFdFloor() != null && post.getFdFloor().intValue() == 1) {
			post.getKmForumTopic().setDocSubject(post.getDocSubject());
			post.getKmForumTopic().setFdIsNotify(post.getFdIsNotify());
			post.getKmForumTopic().setFdNotifyType(post.getFdNotifyType());
			post.getKmForumTopic().setFdIsAnonymous(post.getFdIsAnonymous());
			if (StringUtil.isNotNull(isDraftForum)
					&& "true".equals(isDraftForum)) {
				post.getKmForumTopic().setFdStatus(
						SysDocConstant.DOC_STATUS_DRAFT);
			} else {
				if (post.getKmForumTopic().getFdStatus().equals(
						SysDocConstant.DOC_STATUS_DRAFT)) {
					if (post.getFdIsAnonymous().booleanValue()) {
						post.setFdPoster(null);
						post.getKmForumTopic().setFdLastPosterName(
								ResourceUtil.getString(
										"kmForumTopic.fdIsAnonymous.title",
										"km-forum", new RequestContext()
												.getLocale()));
						post.getKmForumTopic().setFdPoster(null);
					} else {
						// post.getKmForumTopic().setFdLastPosterName(
						// post.getFdPoster().getFdName());
						post.getKmForumTopic().setFdPoster(post.getFdPoster());
					}
					// 由草稿提交,更新发帖时间、最后回复时间为当前时间，以排序
					post.getKmForumTopic().setDocCreateTime(new Date());
					post.getKmForumTopic().setFdLastPostTime(new Date());
					// 更新版块主题计算
					KmForumCategory forum = post.getKmForumTopic()
							.getKmForumCategory();
					forum.setFdTopicCount(new Integer(getIntByInteger(forum
							.getFdTopicCount()) + 1));
					// 新增话题结束
					// 更新用户积分，发帖计数--开始
					if (!post.getFdIsAnonymous().booleanValue()) {
						KmForumScore score = (KmForumScore) getBaseDao()
								.findByPrimaryKey(post.getFdPoster().getFdId(),
										KmForumScore.class, true);
						if (score == null) {
							score = new KmForumScore();
							// 发帖数1,回帖数0
							score.setFdPostCount(new Integer(1));
							score.setFdReplyCount(new Integer(0));
							score.setFdScore(new Integer(getIntByInteger(forum
									.getFdMainScore())));
							score.setPerson(post.getFdPoster());
							getBaseDao().add(score);
						} else {
							// 新增话题：主题数+1
							if (post.getFdFloor() != null
									&& post.getFdFloor().intValue() == 1) {
								int postCount = new Integer(
										getIntByInteger(score.getFdPostCount()) + 1);
								score.setFdPostCount(postCount);
							}
							score.getFdSign();
							getBaseDao().update(score);
						}
						// 更新用户积分，发帖计数--结束
					}
					// 更新论坛信息--开始
					forum.setFdPostCount(new Integer(getIntByInteger(forum
							.getFdPostCount()) + 1));
					getBaseDao().update(forum);
					// 更新论坛信息--结束
				}
				post.getKmForumTopic().setFdStatus(
						SysDocConstant.DOC_STATUS_PUBLISH);
			}
			updateTopicInfo(post.getKmForumTopic(), post);
			getBaseDao().update(post.getKmForumTopic());
		}
		update(post);
		// 重新编辑后重新发送待办或者删除待办
		// 存为草稿不进行发送待办
		if (StringUtil.isNull(isDraftForum) || !"true".equals(isDraftForum)) {
			// 判断@的人是不是为空，为空则不处理
			List<SysOrgPerson> newPostNotifier = post.getFdPostNotifier();
			List<SysOrgPerson> oldPostNotifier = oldPost.getFdPostNotifier();
			if (!ArrayUtil.isListSame(oldPostNotifier, newPostNotifier)) {
				// 补发待办的用户
				List<SysOrgPerson> postNotifier = new ArrayList();
				for (SysOrgPerson sysOrgPerson : newPostNotifier) {
					if (!oldPostNotifier.contains(sysOrgPerson)) {
						postNotifier.add(sysOrgPerson);
					}
				}
				if (!ArrayUtil.isEmpty(postNotifier)) {
					sendNotify(post, String.valueOf(post.getFdIsAnonymous()), postNotifier);
				}
				// 删除待办的用户
				postNotifier = new ArrayList();
				for (SysOrgPerson sysOrgPerson : oldPostNotifier) {
					if (!newPostNotifier.contains(sysOrgPerson)) {
						postNotifier.add(sysOrgPerson);
					}
				}
				if (!ArrayUtil.isEmpty(postNotifier)) {
					sysNotifyMainCoreService.getTodoProvider().removePersons(post.getKmForumTopic(), ForumStringUtil.KM_FORUM_POST_NOTIFIER, postNotifier);
				}
			}
		}
	}

	@Override
	public void delete(IBaseModel model) throws Exception {
		KmForumPost post = (KmForumPost) model;
		// 如果为草稿则不做操作
		if (!post.getKmForumTopic().getFdStatus().equals(
				SysDocConstant.DOC_STATUS_DRAFT)) {
			// 帖子数减1
			post.getKmForumTopic().getKmForumCategory().setFdPostCount(
					new Integer(getIntByInteger(post.getKmForumTopic()
							.getKmForumCategory().getFdPostCount()) - 1));
			getBaseDao().update(post.getKmForumTopic().getKmForumCategory());
			// 主题回复数减1
			post.getKmForumTopic().setFdReplyCount(
					new Integer(getIntByInteger(post.getKmForumTopic()
							.getFdReplyCount()) - 1));
			// 查找回帖，取最后回帖时间和回帖人，并且更新楼号
			List forumPosts = findList("kmForumPost.fdId != '" + post.getFdId()
					+ "' and kmForumPost.kmForumTopic.fdId = '"
					+ post.getKmForumTopic().getFdId() + "'",
					"docCreateTime desc");
			// 回帖人回帖数减1
			if (!post.getFdIsAnonymous() && post.getFdPoster() != null) {
				String fdPosterId = post.getFdPoster().getFdId();
				KmForumScore score = (KmForumScore) getBaseDao()
						.findByPrimaryKey(fdPosterId, KmForumScore.class, true);
				if (score != null) {
					Integer fdReplyCount = score.getFdReplyCount() == null ? 0
							: score.getFdReplyCount();
					fdReplyCount = fdReplyCount - 1;
					fdReplyCount = fdReplyCount > 0 ? fdReplyCount : 0;
					score.setFdReplyCount(fdReplyCount);
					getkmForumScoreService().update(score);
				}
			}

			int floor = forumPosts.size();
			if (forumPosts.size() == 1) {
				KmForumPost forumPost = (KmForumPost) forumPosts.get(0);
				post.getKmForumTopic().setFdLastPoster(null);
				post.getKmForumTopic().setFdLastPosterName("");
				post.getKmForumTopic().setFdLastPostTime(
						forumPost.getDocAlterTime());
				post.getKmForumTopic()
						.setDocAlterTime(forumPost.getDocAlterTime());
			} else {
				for (int i = 0; i < forumPosts.size(); i++) {
					KmForumPost forumPost = (KmForumPost) forumPosts.get(i);
					if (i == 0) {
						post.getKmForumTopic().setFdLastPoster(
								forumPost.getFdPoster());
						if (forumPost.getFdIsAnonymous().booleanValue()) {
							post.getKmForumTopic().setFdLastPosterName(
									ResourceUtil.getString(
											"kmForumTopic.fdIsAnonymous.title",
											"km-forum", new RequestContext()
													.getLocale()));
						} else {
							post.getKmForumTopic().setFdLastPosterName(
									forumPost.getFdPoster().getFdName());
						}
						post.getKmForumTopic().setFdLastPostTime(
								forumPost.getDocAlterTime());
						post.getKmForumTopic()
								.setDocAlterTime(forumPost.getDocAlterTime());
					}
					forumPost.setFdFloor(floor);

					update(forumPost);
					floor--;
				}
			}
			getBaseDao().update(post.getKmForumTopic());
		}
		post.getKmForumTopic().getForumPosts().remove(post);
		if (UserOperHelper.allowLogOper("Service_Delete", getModelName())) {
			UserOperHelper.setEventType(ResourceUtil.getString(
					"km-forum:KmForumPost.notify.title.deleteReply"));
		}
		super.delete(post);
		// 删除后，把所有相关待办删除
		sysNotifyMainCoreService.getTodoProvider().remove(post.getKmForumTopic(), ForumStringUtil.KM_FORUM_POST_NOTIFIER, post.getFdId(), null);
	}

	@Override
	public void deleteQuotePost(IBaseModel model) throws Exception {
		KmForumPost post = (KmForumPost) model;
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock(" kmForumPost.fdParent.fdId = :fdParentId ");
		hqlInfo.setParameter("fdParentId", post.getFdId());
		List kmForumPostList = findList(hqlInfo);
		for (int i = 0; i < kmForumPostList.size(); i++) {
			KmForumPost kmForumPost = (KmForumPost) kmForumPostList.get(i);
			kmForumPost.setFdParent(null);
			// 标记引用非法
			kmForumPost.setFdIsParentDelete(true);
			update(kmForumPost);
		}
		delete(model);
	}

	/*
	 * 设置论坛用户积分
	 */
	@Override
	public void setPosterScore(List list) throws Exception {
		String quoteBegin = "<table width='98%' cellspacing='1' cellpadding='3' border='0' align='center'>"
				+ "<tr><td class='quote'>";
		String quoteEnd = "</td></tr></table>";
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			KmForumPost post = (KmForumPost) iter.next();
			if (!post.getFdIsAnonymous().booleanValue()) {
				KmForumScore score = (KmForumScore) getBaseDao()
						.findByPrimaryKey(post.getFdPoster().getFdId(),
								KmForumScore.class, true);
				post.setPosterScore(score);
			}
			String content = StringUtil.replace(post.getDocContent(),
					"[quote]", quoteBegin);
			content = StringUtil.replace(content, "[/quote]", quoteEnd);
			post.setDocContent(content);
		}
	}

	/**
	 * @return 根据修改时间间隔取帖子集合
	 */
	@Override
	public List getLastPosts(Date begin, Date end, int start, int count)
			throws Exception {
		return ((IKmForumPostDao) getBaseDao()).getLastPosts(begin, end, start,
				count);
	}

	public void setSysNotifyMainCoreService(
			ISysNotifyMainCoreService sysNotifyMainCoreService) {
		this.sysNotifyMainCoreService = sysNotifyMainCoreService;
	}

	@Override
	public void saveSendNotify(KmForumPost kmForumPost, String fdIsAnonymous) throws Exception {
		if (kmForumPost == null) {
			return;
		}
		// cancelTodo(kmForumPost);
		// 由于待办的异步删除，在此移去待办重复删除代码，在待办发送服务中已经对fdSubject,fdLink,fdKey,fdType的进行了唯一性重复处理
		// update by wubing date:2014-11-29
		sendTodoFromResource(kmForumPost, fdIsAnonymous);
	}
	
	private NotifyReplace getNotifyReplace(KmForumPost kmForumPost) {
		NotifyReplace notifyReplace = new NotifyReplace();
		notifyReplace.addReplaceText("km-forum:docSubject", kmForumPost.getDocSubject());
		notifyReplace.addReplaceText("km-forum:category",
				kmForumPost.getKmForumTopic()
						.getKmForumCategory().getFdName());
		return notifyReplace;
	}

	// 通知机制
	private HashMap getReplaceMap(KmForumPost kmForumPost) {
		HashMap replaceMap = new HashMap();
		replaceMap.put("km-forum:docSubject", kmForumPost.getDocSubject());
		replaceMap.put("km-forum:category", kmForumPost.getKmForumTopic()
				.getKmForumCategory().getFdName());
		return replaceMap;
	}

	private void sendTodoFromResource(KmForumPost kmForumPost, String fdIsAnonymous) throws Exception {

		// 先删除旧的待阅，保留最新的一条
		sysNotifyMainCoreService.getTodoProvider().removePerson(kmForumPost, "km-forum-post-notify",
				kmForumPost.getFdPoster());

		// 获取上下文
		String linkUrl = ModelUtil.getModelUrl(kmForumPost);
		NotifyContext notifyContext = sysNotifyMainCoreService
				.getContext("km-forum:kmForumPost.notify");
		// 获取通知方式
		notifyContext.setNotifyType(kmForumPost.getKmForumTopic()
				.getFdNotifyType());
		// 设置发布类型为“待办”（默认为待阅）
		// “待办”消息发送出去后，需要到某事件发生后才变成已办，如审批通过等
		notifyContext.setFlag(SysNotifyConstant.NOTIFY_TODOTYPE_ONCE);
		// 设置发布KEY值，为后面的删除准备
		notifyContext.setKey("km-forum-post-notify");
		notifyContext.setLink(linkUrl);
		// 获取通知人
		List targets = new ArrayList();
		targets.add(kmForumPost.getFdPoster());
		// 设置发布通知人
		notifyContext.setNotifyTarget(targets);
		//设置是否匿名
		notifyContext.setFdAnonymous("1".equals(fdIsAnonymous) ? true : false);
		sysNotifyMainCoreService.sendNotify(kmForumPost, notifyContext,
				getNotifyReplace(kmForumPost));
	}

	@Override
	public KmForumPost findFirstFloorPost(String fdTopicId) throws Exception {
		KmForumPost rtnObj = null;
		HQLInfo hql = new HQLInfo();
		String whereBlock = "kmForumPost.kmForumTopic.fdId = '" + fdTopicId
				+ "' and kmForumPost.fdFloor = 1";
		hql.setWhereBlock(whereBlock);
		List valueList = this.findList(hql);
		if (valueList != null && valueList.size() > 0) {
			rtnObj = (KmForumPost) valueList.get(0);
		}
		return rtnObj;
	}

	private void cancelTodo(KmForumPost kmForumPost) throws Exception {
		sysNotifyMainCoreService.getTodoProvider().remove(kmForumPost,
				"km-forum-post-notify");
	}

	@Override
	public KmForumPost findLastFloorPostByCategoryId(String fdHierarchyId)
			throws Exception {
		KmForumPost kmForumPost = null;
		HQLInfo hql = new HQLInfo();
		String whereBlock = "kmForumPost.kmForumTopic.kmForumCategory.fdHierarchyId like :fdHierarchyId and kmForumPost.kmForumTopic.fdStatus!=:fdStatus";
		hql.setParameter("fdHierarchyId", "%" + fdHierarchyId + "%");
		hql.setParameter("fdStatus", SysDocConstant.DOC_STATUS_DRAFT);
		hql.setWhereBlock(whereBlock);
		hql.setOrderBy("docCreateTime desc");
		List valueList = this.findList(hql);
		if (valueList != null && valueList.size() > 0) {
			kmForumPost = (KmForumPost) valueList.get(0);
		}
		return kmForumPost;
	}

	// 发送待办
	private void sendNotify(KmForumPost kmForumPost, String fdIsAnonymous, List targets) throws Exception {
		// 获取上下文
		KmForumTopic kmForumTopic = kmForumPost.getKmForumTopic();
		String linkUrl = ModelUtil.getModelUrl(kmForumTopic);
		NotifyContext notifyContext = sysNotifyMainCoreService
				.getContext("km-forum:kmForumPost.postNotifier");
		// 获取通知方式
		notifyContext.setNotifyType("todo");
		// 设置发布类型为“待办”
		notifyContext.setFlag(SysNotifyConstant.NOTIFY_TODOTYPE_MANUAL);
		// 设置发布KEY值，为后面的删除准备
		notifyContext.setKey(ForumStringUtil.KM_FORUM_POST_NOTIFIER);
		notifyContext.setLink(linkUrl);
		// 获取通知人
		if (ArrayUtil.isEmpty(targets)) {
			targets = kmForumPost.getFdPostNotifier();
		}
		// 设置发布通知人
		notifyContext.setNotifyTarget(targets);
		// 设置是否匿名
		notifyContext.setFdAnonymous("1".equals(fdIsAnonymous) ? true : false);
		NotifyReplace notifyReplace = new NotifyReplace();
		notifyReplace.addReplaceText("km-forum:docSubject", kmForumPost.getDocSubject());
		notifyContext.setParameter1(kmForumPost.getFdId());
		sysNotifyMainCoreService.sendNotify(kmForumTopic, notifyContext, notifyReplace);
	}
}
