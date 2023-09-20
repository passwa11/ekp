package com.landray.kmss.km.forum.webservice;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.constant.SysDocConstant;
import com.landray.kmss.km.forum.dao.IKmForumPostDao;
import com.landray.kmss.km.forum.model.KmForumCategory;
import com.landray.kmss.km.forum.model.KmForumConfig;
import com.landray.kmss.km.forum.model.KmForumPost;
import com.landray.kmss.km.forum.model.KmForumScore;
import com.landray.kmss.km.forum.model.KmForumTopic;
import com.landray.kmss.km.forum.service.IKmForumCategoryService;
import com.landray.kmss.km.forum.service.IKmForumPostService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.webservice2.forms.AttachmentForm;
import com.landray.kmss.sys.webservice2.interfaces.ISysWsAttService;
import com.landray.kmss.sys.webservice2.interfaces.ISysWsOrgService;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.annotation.RestApi;

/**
 * WebService推送话题
 * 
 * @author 潘永辉 2017-2-9
 * 
 */
@Controller
@RequestMapping(value = "/api/km-forum/kmForumRestService", method = RequestMethod.POST)
@RestApi(docUrl = "/km/forum/restservice/kmForumWebServiceHelp.jsp", name = "kmForumRestService", resourceKey = "km-forum:kmForumRestService.title")
public class KmForumWebserviceServiceImp implements IKmForumWebserviceService {
	private ISysWsOrgService sysWsOrgService;
	private ISysWsAttService sysWsAttService;
	private IKmForumPostService kmForumPostService;
	private IKmForumCategoryService kmForumCategoryService;

	public void setSysWsOrgService(ISysWsOrgService sysWsOrgService) {
		this.sysWsOrgService = sysWsOrgService;
	}

	public void setSysWsAttService(ISysWsAttService sysWsAttService) {
		this.sysWsAttService = sysWsAttService;
	}

	public void setKmForumPostService(IKmForumPostService kmForumPostService) {
		this.kmForumPostService = kmForumPostService;
	}

	public void setKmForumCategoryService(
			IKmForumCategoryService kmForumCategoryService) {
		this.kmForumCategoryService = kmForumCategoryService;
	}

	private void checkForm(KmForumPostParamterForm webForm) throws Exception {
		// 标题不能为空
		if (StringUtil.isNull(webForm.getDocSubject())) {
			throw new Exception(
					ResourceUtil
							.getString("km-forum:kmForumTopic.webservice.docSubject.null"));
		}
		// 内容不能为空
		if (StringUtil.isNull(webForm.getDocContent())) {
			throw new Exception(
					ResourceUtil
							.getString("km-forum:kmForumTopic.webservice.docContent.null"));
		}
		// 推送者不为空
		if (StringUtil.isNull(webForm.getFdPoster())) {
			throw new Exception(ResourceUtil
					.getString("km-forum:kmForumTopic.webservice.poster.null"));
		}
	}

	@SuppressWarnings("unchecked")
	private void checkReplyTimeInterval(SysOrgPerson poster) throws Exception {
		KmForumConfig config = new KmForumConfig();
		int replyTimeInterval = Integer.valueOf(config.getReplyTimeInterval()); // 单位：秒
		if (replyTimeInterval > 0) {
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setSelectBlock("kmForumPost.docCreateTime");
			hqlInfo.setWhereBlock("kmForumPost.fdPoster = :poster");
			hqlInfo.setParameter("poster", poster);
			hqlInfo.setOrderBy("kmForumPost.docCreateTime desc");
			hqlInfo.setRowSize(1);
			List<Date> list = kmForumPostService.findValue(hqlInfo);
			if (!list.isEmpty()) {
				Date date = list.get(0);
				int interval = (int) ((System.currentTimeMillis() - date
						.getTime()) / DateUtil.SECOND);
				if (interval < replyTimeInterval) {
					throw new Exception(ResourceUtil.getString(
							"kmForumTopic.webservice.timeInterval", "km-forum",
							null, replyTimeInterval));
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	private KmForumCategory findForumByName(String name) throws Exception {
		KmForumCategory forum = null;
		if (StringUtil.isNotNull(name)) {
			// 如果有传入版块名称，则按传入的版块名称查询版块
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock("kmForumCategory.fdName = :fdName");
			hqlInfo.setParameter("fdName", name);
			hqlInfo.setRowSize(1);
			List<KmForumCategory> list = kmForumCategoryService.findPage(
					hqlInfo).getList();
			if (!list.isEmpty()) {
				forum = list.get(0);
			}
		} else {
			// 如果没有传入版块名称，则取后台配置的默认版块
			KmForumConfig forumConfig = new KmForumConfig();
			String forumId = forumConfig.getWebServiceDefForumId();
			forum = (KmForumCategory) kmForumCategoryService
					.findByPrimaryKey(forumId);
		}
		return forum;
	}

	@ResponseBody
	@RequestMapping(value = "/addTopic")
	@Override
	public String addTopic(@ModelAttribute KmForumPostParamterForm webForm)
			throws Exception {
		// 校验信息
		checkForm(webForm);

		// 获取贴子发送者
		SysOrgElement _poster = sysWsOrgService.findSysOrgElement(webForm
				.getFdPoster());
		if (_poster == null) {
			throw new Exception(ResourceUtil
					.getString("km-forum:kmForumTopic.webservice.poster.null")
					+ " " + webForm.getFdPoster());
		}
		SysOrgPerson poster = (SysOrgPerson) kmForumPostService
				.findByPrimaryKey(_poster.getFdId(), SysOrgPerson.class, true);
		if (poster == null) {
			throw new Exception(ResourceUtil
					.getString("km-forum:kmForumTopic.webservice.poster.null")
					+ " " + webForm.getFdPoster());
		}

		// 发贴时间间隔（防止恶意刷贴）
		checkReplyTimeInterval(poster);

		// 获取版块
		KmForumCategory forum = findForumByName(webForm.getFdForum());
		if (forum == null) {
			throw new Exception(ResourceUtil
					.getString("km-forum:kmForumTopic.webservice.forum.null"));
		}

		KmForumTopic topic = new KmForumTopic();
		KmForumPost post = new KmForumPost();

		// 保存话题
		topic.setFdPinked(new Boolean(false));
		topic.setFdSticked(new Boolean(false));
		topic.setFdReplyCount(new Integer(0));
		topic.setFdHitCount(new Integer(0));
		topic.setDocSubject(webForm.getDocSubject());
		topic.setDocCreateTime(new Date());
		topic.setDocAlterTime(new Date());
		topic.setFdLastPostTime(new Date());
		topic.setFdLastPoster(poster);
		topic.setFdStatus(SysDocConstant.DOC_STATUS_PUBLISH);
		post.setFdPoster(poster);
		topic.setFdPoster(poster);
		topic.setFdLastPosterName(poster.getFdName());

		// 设置版块
		topic.setKmForumCategory(forum);
		String fdTopicId = kmForumPostService.add(topic);

		// 更新版块主题计算
		forum.setFdTopicCount(new Integer(getIntByInteger(forum
				.getFdTopicCount()) + 1));
		forum.setFdPostCount(new Integer(
				getIntByInteger(forum.getFdPostCount()) + 1));
		kmForumCategoryService.update(forum);

		// 计算积分(匿名不增加积分)
		updateScore(forum, poster);

		// 保存帖子
		post
				.setFdFloor(new Integer(((IKmForumPostDao) kmForumPostService
						.getBaseDao()).getCurrentFloor(topic.getFdId())
						.intValue() + 1));
		post.setKmForumTopic(topic);
		post.setDocSubject(webForm.getDocSubject());
		post.setDocContent(webForm.getDocContent());
		post.setDocAlterTime(new Date());
		// 处理摘要
		updatePostSummary(post);
		String fdPostId = kmForumPostService.add(post);

		// 保存附件
		List<AttachmentForm> attForms = webForm.getAttachmentForms();
		if (!attForms.isEmpty()) {
			// 检查fdKey
			for (AttachmentForm attForm : attForms) {
				if (StringUtil.isNull(attForm.getFdKey())) {
					attForm.setFdKey("attachment");
				}
			}
			sysWsAttService.validateAttSize(attForms); // 校验附件大小
			sysWsAttService.save(attForms, fdPostId,
					"com.landray.kmss.km.forum.model.KmForumPost");
		}

		return StringUtil.isNotNull(fdTopicId) ? fdTopicId : fdPostId;
	}

	/**
	 * 更新发贴者积分
	 * 
	 * @param forum
	 * @param poster
	 * @throws Exception
	 */
	private void updateScore(KmForumCategory forum, SysOrgPerson poster)
			throws Exception {
		KmForumScore score = (KmForumScore) kmForumPostService.getBaseDao()
				.findByPrimaryKey(poster.getFdId(), KmForumScore.class, true);
		if (score == null) {
			score = new KmForumScore();
			// 发帖数1，回复数0
			score.setFdPostCount(new Integer(1));
			score.setFdReplyCount(new Integer(0));
			// 发文得分
			score.setFdScore(new Integer(
					getIntByInteger(forum.getFdMainScore())));
			score.setPerson(poster);
			kmForumPostService.add(score);
		} else {
			score.getFdSign();
			// 新增话题：主题数+1,新增回复：回复数=1
			int postCount = new Integer(
					getIntByInteger(score.getFdPostCount()) + 1);
			score.setFdPostCount(postCount);

			// 发文得分
			score.setFdScore(new Integer(getIntByInteger(score.getFdScore())
					+ getIntByInteger(forum.getFdMainScore())));
			kmForumPostService.update(score);
		}
	}

	private void updatePostSummary(KmForumPost post) {
		String text = post.getDocContent();
		text = StringUtil.clearHTMLTag(text);
		if (StringUtil.isNotNull(text)) {
			if (text.length() > 198) {
				post.setDocSummary(text.substring(0, 196) + "..");
			} else {
				post.setDocSummary("");
			}
		}
	}

	private int getIntByInteger(Integer o) {
		return (o == null ? 0 : o.intValue());
	}

}
