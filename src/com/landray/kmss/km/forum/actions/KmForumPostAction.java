package com.landray.kmss.km.forum.actions;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.km.forum.forms.KmForumPostForm;
import com.landray.kmss.km.forum.model.KmForumCategory;
import com.landray.kmss.km.forum.model.KmForumConfig;
import com.landray.kmss.km.forum.model.KmForumPost;
import com.landray.kmss.km.forum.model.KmForumScore;
import com.landray.kmss.km.forum.model.KmForumTopic;
import com.landray.kmss.km.forum.service.IKmForumCategoryService;
import com.landray.kmss.km.forum.service.IKmForumPostService;
import com.landray.kmss.km.forum.service.IKmForumTopicService;
import com.landray.kmss.km.forum.utils.SensitiveWordCheckUtil;
import com.landray.kmss.sys.attachment.model.IAttachment;
import com.landray.kmss.sys.attachment.service.ISysAttachmentService;
import com.landray.kmss.sys.bookmark.service.ISysBookmarkMainService;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.mobile.annotation.Separater;
import com.landray.kmss.sys.mobile.util.MobileUtil;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.KmssMessageWriter;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;

import net.sf.json.JSONObject;

/**
 * 创建日期 2006-Sep-07
 * 
 * @author 吴兵
 */
@Separater
public class KmForumPostAction extends ExtendAction {
	public static final Integer FLAG_PCTYPE = 10; // 表示pc端
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(KmssMessageWriter.class);
	protected IKmForumPostService kmForumPostService;

	protected IKmForumTopicService kmForumTopicService;

	protected ISysAttachmentService sysAttachmentService;

	protected IKmForumCategoryService kmForumCategoryService;

	protected ISysBookmarkMainService sysBookmarkMainServiceImp;

	protected IKmForumCategoryService getKmForumCategoryServiceImp(
			HttpServletRequest request) {
		if (kmForumCategoryService == null) {
            kmForumCategoryService = (IKmForumCategoryService) getBean("kmForumCategoryService");
        }
		return kmForumCategoryService;
	}

	@Override
	protected IKmForumPostService getServiceImp(HttpServletRequest request) {
		if (kmForumPostService == null) {
            kmForumPostService = (IKmForumPostService) getBean("kmForumPostService");
        }
		return kmForumPostService;
	}

	protected IKmForumTopicService getTopicServiceImp(HttpServletRequest request) {
		if (kmForumTopicService == null) {
            kmForumTopicService = (IKmForumTopicService) getBean("kmForumTopicService");
        }
		return kmForumTopicService;
	}

	protected ISysAttachmentService getSysAttachmentServiceImp(
			HttpServletRequest request) {
		if (sysAttachmentService == null) {
            sysAttachmentService = (ISysAttachmentService) getBean("sysAttachmentService");
        }
		return sysAttachmentService;
	}

	protected ISysBookmarkMainService getSysBookmarkMainServiceImp(
			HttpServletRequest request) {
		if (sysBookmarkMainServiceImp == null) {
            sysBookmarkMainServiceImp = (ISysBookmarkMainService) getBean("sysBookmarkMainService");
        }
		return sysBookmarkMainServiceImp;
	}

	/**
	 * 打开贴子列表页面。<br>
	 * 该操作一般以HTTP的GET方式触发。
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回list页面，否则返回failure页面
	 * @throws Exception
	 */
	@Override
	public ActionForward view(ActionMapping mapping, ActionForm form,
							  HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		boolean showTopic = true;
		try {
			String fdTopicId = request.getParameter("fdTopicId");
			//兼容数据引用数据CompBklinkServiceImp中的297行代码
			if(StringUtil.isNull(fdTopicId)){
				fdTopicId = request.getParameter("fdId");
			}
			String fdfirst = request.getParameter("fdfirst");
			if (StringUtil.isNull(fdTopicId)) {
				throw new NoRecordException();
			}
			logger.debug("访问类型:" + MobileUtil
					.getClientType(new RequestContext(request)));
			if (MobileUtil
					.getClientType(new RequestContext(request)) != MobileUtil.PC
					&& MobileUtil.getClientType(new RequestContext(
							request)) != MobileUtil.DING_PC) {
				return new ActionForward(
						"/km/forum/mobile/kmForumPost.do?method=view&fdTopicId="
								+ fdTopicId, true);
			}
			String s_pageno = request.getParameter("pageno");
			String s_rowsize = request.getParameter("rowsize");
			int pageno = 0;
			int rowsize = 10;
			if (s_pageno != null && s_pageno.length() > 0) {
				pageno = Integer.parseInt(s_pageno);
			}
			if (s_rowsize != null && s_rowsize.length() > 0) {
				rowsize = Integer.parseInt(s_rowsize);
			}
			// 主题信息
			KmForumTopic topic = (KmForumTopic) getTopicServiceImp(request)
					.findByPrimaryKey(fdTopicId, null, true);
			if (topic == null ) {
				throw new NoRecordException();
			}
			String fdForumId = request.getParameter("fdForumId");
			//防止ssl攻击
			if(StringUtil.isNotNull(fdForumId) && !fdForumId.equals(topic.getKmForumCategory().getFdId())){
				throw new NoRecordException();
			}
			request.setAttribute("topic", topic);
			if (s_pageno == null && s_rowsize == null) {
				if (StringUtil.isNull(fdfirst)) {
                    getTopicServiceImp(request).hitCount(fdTopicId);
                }
			}
			// 回帖信息
			String type = request.getParameter("type");
			String whereBlock = "kmForumPost.kmForumTopic.fdId='" + fdTopicId
					+ "' and kmForumPost.fdFloor !=1";
			// 只查看该作者
			if (StringUtil.isNotNull(type) && "onlyViewPoster".equals(type)) {
				String fdPosterId = request.getParameter("fdPosterId");
				whereBlock += " and kmForumPost.fdPoster.fdId='" + fdPosterId
						+ "' and kmForumPost.fdIsAnonymous = 0 ";
				// 作者匿名或者发帖人!=查看人
				if (topic.getFdPoster() == null
						|| !topic.getFdPoster().getFdId().equals(fdPosterId)) {
					showTopic = false;
				}
			}
			// 锚点定位
			String fdPostId = request.getParameter("fdPostId");
			if (StringUtil.isNotNull(fdPostId)) {
				// fordward图标跳转
				String fdFloorStr = request.getParameter("fdFloor");
				if (StringUtil.isNotNull(fdFloorStr)) {
					int fdFloor = Integer.parseInt(fdFloorStr);
					pageno = (fdFloor - 2) / 10 + 1;
				}
			}
			List topicList = getServiceImp(request).findList(
					"kmForumPost.kmForumTopic.fdId='" + fdTopicId
							+ "' and kmForumPost.fdFloor =1",
					"kmForumPost.fdFloor asc");
			((IKmForumPostService) getServiceImp(request))
					.setPosterScore(topicList);
			KmForumPost topicPoster = (KmForumPost) topicList.get(0);
			List<SysOrgElement> fdPostNotifier = topicPoster.getFdPostNotifier();
			String fdPostNotifierName = "";
			for (SysOrgElement sysOrgElement : fdPostNotifier) {
				fdPostNotifierName += sysOrgElement.getFdName() + ";";
			}
			if (fdPostNotifierName.lastIndexOf(";") > 0) {
				fdPostNotifierName = fdPostNotifierName.substring(0, fdPostNotifierName.length() - 1);
			}
			request.setAttribute("fdPostNotifierName", fdPostNotifierName);
			// 添加日志信息
			UserOperHelper.logFind(topicPoster);
			// 作者岗位和等级
			if (!topicPoster.getFdIsAnonymous()) {
				KmForumScore forumScore = topicPoster.getPosterScore();
				int score = 0;
				if (forumScore != null) {
					score = forumScore.getFdScore() != null
							? forumScore.getFdScore().intValue() : score;
				}
				KmForumConfig forumConfig = new KmForumConfig();
				String level = forumConfig.getLevelByScore(score);
				String levelIcon = forumConfig.getLevelIcon();
				String postName = ArrayUtil.joinProperty(topicPoster
						.getFdPoster().getFdPosts(), "fdName", ",")[0];
				request.setAttribute("level", level.replace(" ", ""));
				request.setAttribute("levelIcon", levelIcon);
				request.setAttribute("postName", postName);
			}
			// 初始表单的值
			((KmForumPostForm) form).setDocContent(topicPoster.getDocContent());
			// 回帖紧作者可见,!=null是兼容老数据
			if (topicPoster.getFdIsOnlyView() != null
					&& topicPoster.getFdIsOnlyView()) {
				// 匿名全部可见,作者可以看到全部，非作者只能看到自己
				if (!topic.getFdIsAnonymous()
						&& !topicPoster.getFdPoster().getFdId().equals(
								UserUtil.getUser(request).getFdId())) {
					whereBlock += " and kmForumPost.fdPoster.fdId='"
							+ UserUtil.getUser(request).getFdId() + "'";
				}
			}
			// 兼容老数据移动端从1楼开始
			String flag = request.getParameter("flag");
			if ("pda".equals(flag)) {
				whereBlock = "kmForumPost.kmForumTopic.fdId='" + fdTopicId
						+ "'";
				// 移动端回帖作者可见数据过滤
				if (topicPoster.getFdIsOnlyView() != null
						&& topicPoster.getFdIsOnlyView()) {
					// 匿名全部只能自己可见,作者可以看到全部，非作者只能看到自己
					if (!topic.getFdIsAnonymous()
							&& !topicPoster.getFdPoster().getFdId().equals(
									UserUtil.getUser(request).getFdId())) {
						whereBlock += " and (kmForumPost.fdFloor =1 or kmForumPost.fdPoster.fdId='"
								+ UserUtil.getUser(request).getFdId() + "')";
					}
				}
			}
			Page page = getServiceImp(request).findPage(whereBlock,
					"kmForumPost.fdFloor asc", pageno, rowsize);
			// 帖子删除后 显示文档不存在
			if (topicList.isEmpty()) {
				throw new NoRecordException();
			}
			((IKmForumPostService) getServiceImp(request)).setPosterScore(page
					.getList());
			// 添加附件
			for (int i = 0; i < page.getList().size(); i++) {
				IAttachment attObj = (IAttachment) page.getList().get(i);
				getSysAttachmentServiceImp(request).addAttachment(attObj,
						attObj);
			}
			// 附件添加结束
			// 添加用户图标
			for (int i = 0; i < page.getList().size(); i++) {
				KmForumPost post = (KmForumPost) page.getList().get(i);
				IAttachment attObj = (IAttachment) post.getPosterScore();
				if (attObj == null) {
                    continue;
                }
				attObj.getAttachmentForms().clear();
				getSysAttachmentServiceImp(request).addAttachment(attObj,
						attObj);
			}
			// 添加用户图标结束

			request.setAttribute("queryPage", page);
			// modify by tanyh 2014-6-11 论坛ued 显示内容
			request.setAttribute("topicPoster", topicPoster);

			int markCount = getSysBookmarkMainServiceImp(request)
					.getMarkCountByModel(topic);
			request.setAttribute("markCount", markCount);
			if (page.getPageno() != 1) {
				showTopic = false;
			}
			request.setAttribute("showTopic", showTopic);
		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("view");
		}
	}

	public ActionForward viewDraft(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-viewDraft", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String fdTopicId = request.getParameter("fdTopicId");
			KmForumTopic kmForumTopic = (KmForumTopic) this.getTopicServiceImp(
					request).findByPrimaryKey(fdTopicId);
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock(" kmForumPost.kmForumTopic.fdId = :topicId ");
			hqlInfo.setParameter("topicId", kmForumTopic.getFdId());
			hqlInfo.setOrderBy("kmForumPost.fdFloor asc");
			List kmForumPostList = this.getServiceImp(request)
					.findList(hqlInfo);
			if (kmForumPostList != null && kmForumPostList.size() > 0) {
				KmForumPost kmForumPost = (KmForumPost) kmForumPostList.get(0);
				KmForumPostForm kmForumPostForm = new KmForumPostForm();
				kmForumPostForm = (KmForumPostForm) getServiceImp(request)
						.convertModelToForm((IExtendForm) kmForumPostForm,
								kmForumPost, new RequestContext(request));
				kmForumPostForm.setFdNotifyType(kmForumTopic.getFdNotifyType());
				kmForumPostForm.setFdIsNotify(kmForumTopic.getFdIsNotify());
				request.setAttribute("kmForumPostForm", kmForumPostForm);
				// 添加日志信息
				UserOperHelper.logFind(kmForumPost);
			} else {
				request.setAttribute("kmForumPostForm", null);
			}
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-viewDraft", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("viewDraft", mapping, form, request,
					response);
		}
	}

	private void setForward(HttpServletRequest request) {
		StringBuffer sb = new StringBuffer();
		sb.append("/km/forum/km_forum/kmForumPost.do?method=view");
		sb.append("&fdForumId=" + request.getParameter("fdForumId"));
		sb.append("&fdTopicId=" + request.getParameter("fdTopicId"));
		sb.append("&flag=" + request.getParameter("flag"));
		if (StringUtil.isNotNull(request.getParameter("s_css"))) {
			sb.append("&s_css=" + request.getParameter("s_css"));
		}
		if (request.getParameter("pageno") != null) {
            sb.append("&pageno=" + request.getParameter("pageno"));
        }
		if (request.getParameter("rowsize") != null) {
            sb.append("&rowsize=" + request.getParameter("rowsize"));
        }
		request.setAttribute("redirectto", sb.toString());
	}

	/**
	 * 将浏览器提交的表单数据添加到数据库中。<br>
	 * 该操作一般以HTTP的POST方式触发。
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回success页面，否则将将错误信息返回edit页面
	 * @throws Exception
	 */
	@Override
	public ActionForward save(ActionMapping mapping, ActionForm form,
							  HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		String fdId = "";
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			request.setAttribute("isDraftForum", "false");
			fdId = getServiceImp(request).add((IExtendForm) form,
					new RequestContext(request));
		} catch (Exception e) {
			logger.error("", e);
			messages.addError(e);
		}

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(
					request);
				return mapping.findForward("edit");
		} else {
				// 快速新建
				if (StringUtil.isNotNull(request.getParameter("isQuick"))) {
					PrintWriter out = response.getWriter();
					out.print(StringEscapeUtils.escapeHtml(fdId));
					return null;
				}
				if (StringUtil.isNull(request.getParameter("fdTopicId"))) {
					request.setAttribute("fdId", fdId);
					request.setAttribute("fdForumId",
							((KmForumTopic) getTopicServiceImp(request)
									.findByPrimaryKey(fdId))
									.getKmForumCategory().getFdId());
					return mapping.findForward("postSuccess");
				} else {
					setForward(request);
					return new ActionForward("/resource/jsp/redirect.jsp");
				}
		}
	}

	public ActionForward saveDraft(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			request.setAttribute("isDraftForum", "true");
			String fdId = "";
			String fdForumId = request.getParameter("fdForumId");
			String s_css = request.getParameter("s_css");
			fdId = getServiceImp(request).add((IExtendForm) form,
					new RequestContext(request));
			String backUrl = "kmForumPost.do?method=edit&fdId=" + fdId
					+ "&fdForumId=" + fdForumId;
			if (StringUtil.isNotNull(s_css)) {
				backUrl += "&s_css=" + s_css;
			}
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton("button.back", backUrl, false).save(request);
		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(
					request);
			return getActionForward("edit", mapping, form, request, response);
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("success", mapping, form, request, response);
		}
	}

	public ActionForward updateDraft(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			request.setAttribute("isDraftForum", "true");
			String fdId = "";
			String fdForumId = request.getParameter("fdForumId");
			String s_css = request.getParameter("s_css");
			getServiceImp(request).update((IExtendForm) form,
					new RequestContext(request));
			fdId = request.getParameter("fdId");
			String backUrl = "kmForumPost.do?method=edit&fdId=" + fdId
					+ "&fdForumId=" + fdForumId;
			if (StringUtil.isNotNull(s_css)) {
				backUrl += "&s_css=" + s_css;
			}
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton("button.back", backUrl, false).save(request);
		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(
					request);
			return getActionForward("edit", mapping, form, request, response);
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("success", mapping, form, request, response);
		}
	}

	/**
	 * 将浏览器提交的表单数据添加到数据库中。<br>
	 * 该操作一般以HTTP的POST方式触发。
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回success页面，否则将将错误信息返回edit页面
	 * @throws Exception
	 */
	public ActionForward saveQuick(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		// 是否是移动端
		String flag = request.getParameter("flag");
		try {
			KmForumPostForm kmForumPostForm = (KmForumPostForm) form;
			if (checkIsRtfXss(kmForumPostForm.getDocContent())) {
				throw new RuntimeException(
						"参数：docContent包括非法值:" + StringEscapeUtils
								.escapeHtml(kmForumPostForm.getDocContent()));
			}
			String parentId = kmForumPostForm.getFdParentId();
			String fdTopicId = kmForumPostForm.getFdTopicId();
			KmForumPost kmForumPost = ((IKmForumPostService) getServiceImp(request))
					.findFirstFloorPost(fdTopicId);
			
			// 判断发帖时间间隔是否超过限制
			KmForumConfig forumConfig = new KmForumConfig();
			String configReplyTimeInterval = forumConfig.getReplyTimeInterval();
			if (!"0".equals(configReplyTimeInterval)) {
				HQLInfo hqlInfo = new HQLInfo();
				if (StringUtil.isNotNull(parentId)) {
					hqlInfo
							.setWhereBlock("(kmForumPost.fdId = :parentId or kmForumPost.fdParent.fdId = :parentId) and kmForumPost.fdPoster.fdId = :fdPosterId");
					hqlInfo.setParameter("parentId", parentId);
				} else {
					hqlInfo
							.setWhereBlock("kmForumPost.kmForumTopic.fdId = :fdTopicId and kmForumPost.fdParent.fdId is null and kmForumPost.fdPoster.fdId = :fdPosterId");
					hqlInfo.setParameter("fdTopicId", fdTopicId);
				}
				hqlInfo
						.setParameter("fdPosterId", UserUtil.getUser()
								.getFdId());
				hqlInfo.setOrderBy("kmForumPost.docCreateTime desc");
				List<KmForumPost> kmForumPostList = getServiceImp(request)
						.findList(hqlInfo);
				if (!kmForumPostList.isEmpty()) {
					Long docCreateTime = kmForumPostList.get(0)
							.getDocCreateTime().getTime();
					Long replyTimeInterval = Long
							.parseLong(configReplyTimeInterval) * 1000;
					Date now = new Date();
					if ((now.getTime() - docCreateTime) < replyTimeInterval) {
						if (!"pda".equals(flag)) {
							JSONObject obj = new JSONObject();
							obj.put("timeInterval", ResourceUtil.getString(
									"kmForumTopic.replyTime.interval",
									"km-forum", request.getLocale(),
									configReplyTimeInterval));
							response.setCharacterEncoding("UTF-8");
							response.getWriter().write(obj.toString());
							return null;
						}
					}
				}
			}
			
			// 移动端标记
			String fdPdaType = request.getParameter("fdPdaType");
						
			if (StringUtil.isNotNull(fdPdaType)) {
				kmForumPostForm.setFdPdaType(Integer.parseInt(fdPdaType));
			} else {
				kmForumPostForm.setFdPdaType(FLAG_PCTYPE);
			}
			
			super.save(mapping, form, request, response);
			
			//#111704 数据库不能将String值为1或‘true’转为boolean类型的值为true.因此所以重新更新一下保存的记录
			if("1".equals(kmForumPostForm.getFdIsAnonymous()))
			{
				HQLInfo hql = new HQLInfo();
				String whereBlock = "kmForumPost.fdId = '" + kmForumPostForm.getFdId() + "'";
				hql.setWhereBlock(whereBlock);
				List<KmForumPost> kmForumPostList = getServiceImp(request)
						.findList(hql);
				if(kmForumPostList != null && kmForumPostList.size() > 0)
				{
					KmForumPost kfp = kmForumPostList.get(0);
					kfp.setFdIsAnonymous(true);
					getServiceImp(request).update(kfp);
				}
			}
			
			if (kmForumPost.getFdPoster() != null) {
				if (kmForumPost != null
						&& !kmForumPostForm.getFdPosterName().equals(
								kmForumPost.getFdPoster().getFdName())
						&& "1".equals(kmForumPost.getKmForumTopic()
								.getFdIsNotify())) {
					((IKmForumPostService) getServiceImp(request))
							.saveSendNotify(kmForumPost, kmForumPostForm.getFdIsAnonymous());
				}
			}
		} catch (Exception e) {
			logger.error("", e);
			messages.addError(e);
			PrintWriter out = response.getWriter();
			out.print(false);
			return null;
		}

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(
					request);
			String method = request.getMethod();
			return mapping.findForward("edit");
		} else {
			if ("pda".equals(flag)) {
				setForward(request);
				return new ActionForward("/resource/jsp/redirect.jsp");
			}
			PrintWriter out = response.getWriter();
			out.print(true);
			return null;
		}
	}

	public ActionForward saveReplyQuote(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			KmForumPostForm kmForumPostForm = (KmForumPostForm) form;
			String parentId = kmForumPostForm.getFdParentId();
			String fdTopicId = kmForumPostForm.getFdTopicId();
			KmForumPost kmForumPost = ((IKmForumPostService) getServiceImp(request))
					.findFirstFloorPost(fdTopicId);
			/*
			 * if (!StringUtil.isNull(kmForumPostForm.getDocContent()))
			 * kmForumPostForm.setDocContent(kmForumPostForm.getQuoteMsg()
			 * .concat(kmForumPostForm.getDocContent())); else
			 * kmForumPostForm.setDocContent(kmForumPostForm.getQuoteMsg());
			 */
			// 移动端标记
			String fdPdaType = request.getParameter("fdPdaType");
			if (StringUtil.isNotNull(fdPdaType)) {
				kmForumPostForm.setFdPdaType(Integer.parseInt(fdPdaType));
			} else {
				kmForumPostForm.setFdPdaType(FLAG_PCTYPE);
			}

			// 判断发帖时间间隔是否超过限制
			KmForumConfig forumConfig = new KmForumConfig();
			String configReplyTimeInterval = forumConfig.getReplyTimeInterval();
			if (!"0".equals(configReplyTimeInterval)) {
				HQLInfo hqlInfo = new HQLInfo();
				if (StringUtil.isNotNull(parentId)) {
					hqlInfo
							.setWhereBlock("(kmForumPost.fdId = :parentId or kmForumPost.fdParent.fdId = :parentId) and kmForumPost.fdPoster.fdId = :fdPosterId");
					hqlInfo.setParameter("parentId", parentId);
				} else {
					hqlInfo
							.setWhereBlock("kmForumPost.kmForumTopic.fdId = :fdTopicId and kmForumPost.fdParent.fdId is null and kmForumPost.fdPoster.fdId = :fdPosterId");
					hqlInfo.setParameter("fdTopicId", fdTopicId);
				}
				hqlInfo
						.setParameter("fdPosterId", UserUtil.getUser()
								.getFdId());
				hqlInfo.setOrderBy("kmForumPost.docCreateTime desc");
				List<KmForumPost> kmForumPostList = getServiceImp(request)
						.findList(hqlInfo);
				if (!kmForumPostList.isEmpty()) {
					Long docCreateTime = kmForumPostList.get(0)
							.getDocCreateTime().getTime();
					Long replyTimeInterval = Long
							.parseLong(configReplyTimeInterval) * 1000;
					Date now = new Date();
					if ((now.getTime() - docCreateTime) < replyTimeInterval) {
						if (StringUtil.isNotNull(fdPdaType)) {
							JSONObject obj = new JSONObject();
							obj.put("timeInterval", ResourceUtil.getString(
									"kmForumTopic.replyTime.interval",
									"km-forum", request.getLocale(),
									configReplyTimeInterval));
							response.setContentType("text/javascript");
							response.setCharacterEncoding("UTF-8");
							response.getWriter().write(obj.toString());
							return null;
						}
					}
				}
			}

			super.save(mapping, form, request, response);
			if (kmForumPost.getFdPoster() != null) {
				if (kmForumPost != null
						&& !kmForumPostForm.getFdPosterName().equals(
								kmForumPost.getFdPoster().getFdName())
						&& "1".equals(kmForumPost.getKmForumTopic()
								.getFdIsNotify())) {
					((IKmForumPostService) getServiceImp(request))
							.saveSendNotify(kmForumPost, kmForumPostForm.getFdIsAnonymous());
				}
			}
		} catch (Exception e) {
			messages.addError(e);
		}

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(
					request);
			return mapping.findForward("quote");
		} else {
			if (StringUtil.isNull(request.getParameter("fdTopicId"))) {
                return mapping.findForward("success");
            } else {
				setForward(request);
				return new ActionForward("/resource/jsp/redirect.jsp");
			}
		}
	}

	/**
	 * 将浏览器提交的表单数据更新到数据库中。<br>
	 * 该操作一般以HTTP的POST方式触发。
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回success页面，否则返回edit页面
	 * @throws Exception
	 */
	@Override
	public ActionForward update(ActionMapping mapping, ActionForm form,
								HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {			
			request.setAttribute("isDraftForum", "false");
			super.update(mapping, form, request, response);
		} catch (Exception e) {
			messages.addError(e);			
		}

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(
					request);
			return mapping.findForward("edit");
		} else {
			setForward(request);
			return new ActionForward("/resource/jsp/redirect.jsp");
		}
	}

	/**
	 * 通过URL的方式直接删除一条记录。<br>
	 * URL中必须包含fdId参数，该参数为记录id。<br>
	 * 该操作一般以HTTP的GET方式触发。
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 若执行成功，返回success页面，否则返回failure页面
	 * @throws Exception
	 */
	@Override
	public ActionForward delete(ActionMapping mapping, ActionForm form,
								HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			String id = request.getParameter("fdId");
			boolean isQuote = new Boolean(request.getParameter("isQuote"));
			if (isQuote) {
				// 删除引用楼层
				getServiceImp(request).deleteQuotePost(
						getServiceImp(request).findByPrimaryKey(id));
			} else {
				getServiceImp(request).delete(id);
			}
		} catch (Exception e) {
			logger.error("", e);
			messages.addError(e);
		}

		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_CLOSE).save(request);
		PrintWriter out = response.getWriter();
		if (messages.hasError()) {
            out.print(false);
        } else {
			out.println(true);
		}
		return null;
	}

	/**
	 * 
	 * 检测是否被引用
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 若执行成功，返回success页面，否则返回failure页面
	 * @throws Exception
	 */
	public ActionForward deleteIsQuoted(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		String result = "";
		try {
			String fdId = request.getParameter("fdId");
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock(" kmForumPost.fdParent.fdId = :fdParentId ");
			hqlInfo.setParameter("fdParentId", fdId);
			List kmForumPostList = this.getServiceImp(request)
					.findList(hqlInfo);
			// 添加日志信息
			UserOperHelper.logFindAll(kmForumPostList,
					getServiceImp(request).getModelName());
			if (kmForumPostList.size() > 0) {
				result = "yes";
			} else {
				result = "no";
			}
		} catch (Exception e) {
			logger.error("", e);
			messages.addError(e);
			result = "error";
		}
		PrintWriter out = response.getWriter();
		out.print(result);
		return null;
	}

	/**
	 * 将浏览器提交的表单数据更新到数据库中。<br>
	 * 该操作一般以HTTP的POST方式触发。
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回success页面，否则返回edit页面
	 * @throws Exception
	 */
	public ActionForward updateReply(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			
			KmForumPostForm kmForumPostForm = (KmForumPostForm) form;
			if (checkIsRtfXss(kmForumPostForm.getDocContent())) {
				throw new RuntimeException(
						"参数：docContent包括非法值:" + StringEscapeUtils
								.escapeHtml(kmForumPostForm.getDocContent()));
			}
			
			//对回复帖子的编辑操作做唯一标示符验证
			boolean postAuth = UserUtil.checkAuthentication("/km/forum/km_forum/kmForumPost.do?method=edit&fdId=" + kmForumPostForm.getFdId(), "post");
			if(!postAuth){//无权限修改				
				messages.setHasError();				
				throw new UnexpectedRequestException();
			}
			
			kmForumPostForm.setFdAlterorId(UserUtil.getUser(request).getFdId());
			super.update(mapping, form, request, response);
		} catch (Exception e) {
			logger.error("", e);
			messages.addError(e);			
		}
		PrintWriter out = response.getWriter();
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(
					request);
			out.print(false);
		} else {
			setForward(request);
			out.print(true);
		}
		return null;
	}

	/**
	 * 引用贴子。<br>
	 * URL中必须包含fdId参数，该参数为记录id。<br>
	 * 该操作一般以HTTP的GET方式触发。
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回edit页面，否则返回failure页面
	 * @throws Exception
	 */
	public ActionForward quote(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		String flag = request.getParameter("flag");
		try {
			KmForumPostForm postForm = (KmForumPostForm) form;
			KmForumPost model = (KmForumPost) getModel(request, response);
			// 设置引用信息
			postForm.setFdId(null);
			postForm.setFdTopicId(model.getKmForumTopic().getFdId().toString());
			postForm.setFdForumId(model.getKmForumTopic().getKmForumCategory()
					.getFdId().toString());
			postForm.setFdForumName(model.getKmForumTopic()
					.getKmForumCategory().getFdName());
			if (model.getDocSubject().contains(
					ResourceUtil.getString("kmForumPost.toolTip.re",
							"km-forum", request.getLocale())
							+ ":")) {
				postForm.setDocSubject(model.getDocSubject());
			} else {
				postForm.setDocSubject(ResourceUtil.getString(
						"kmForumPost.toolTip.re", "km-forum", request
								.getLocale())
						+ ":" + model.getDocSubject());
			}
			// if (!StringUtil.isNull(model.getDocContent()))
			// postForm.setDocContent("[quote]".concat(model.getDocContent())
			// .concat("[/quote]"));
			// 为了防止用户在edit页面修改引用标签，取一个字段存放引用内容，在saveReplyQuote方法中提交时set进docContent字段
			/*
			 * if (!StringUtil.isNull(model.getDocContent()))
			 * postForm.setQuoteMsg("[quote]".concat(model.getDocContent())
			 * .concat("[/quote]")); postForm.setFdQuoteMsg(getQuoteMsg(request,
			 * model));
			 */
			String postId = request.getParameter("postId");
			postForm.setFdParentId(postId);
		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return mapping.findForward("failure");
		} else {
			if ("pda".equals(flag)) {
				return mapping.findForward("quote.4pda");
			} else {
				return mapping.findForward("quote");
			}
		}
	}

	private String getQuoteMsg(HttpServletRequest request, KmForumPost model) {
		StringBuffer sb = new StringBuffer();
		sb.append(ResourceUtil.getString("kmForumPost.button.quote",
				"km-forum", request.getLocale()));
		if (model.getFdIsAnonymous().booleanValue()) {
			sb.append(" "
					+ ResourceUtil.getString(
							"kmForumTopic.fdIsAnonymous.title", "km-forum",
							request.getLocale()));
		} else {
			sb.append(" " + model.getFdPoster().getFdName());
		}
		if (model.getFdFloor().intValue() == 1) {
			sb.append(ResourceUtil.getString("kmForumPost.mainFloor.title",
					"km-forum", request.getLocale())
					+ " ");
		} else {
			sb.append(ResourceUtil.getString("kmForumPost.floor.title",
					"km-forum", request.getLocale(), new Object[] { model
							.getFdFloor() })
					+ " ");
		}
		sb.append(ResourceUtil.getString("kmForumPost.fdQuoteMsg.at",
				"km-forum", request.getLocale()));
		sb.append(DateUtil.convertDateToString(model.getDocCreateTime(),
				DateUtil.TYPE_DATETIME, null));
		sb.append(ResourceUtil.getString("kmForumPost.fdQuoteMsg.post",
				"km-forum", request.getLocale()));
		return sb.toString();
	}

	/**
	 * 回复贴子。<br>
	 * URL中必须包含fdId参数，该参数为记录id。<br>
	 * 该操作一般以HTTP的GET方式触发。
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回edit页面，否则返回failure页面
	 * @throws Exception
	 */
	public ActionForward reply(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			KmForumPostForm postForm = (KmForumPostForm) form;
			
			//对回复帖子的编辑操作做唯一标示符验证
			boolean postAuth = UserUtil.checkAuthentication("/km/forum/km_forum/kmForumPost.do?method=edit&fdId=" + postForm.getFdId(), "post");
			if(!postAuth){//无权限修改				
				messages.setHasError();				
				throw new UnexpectedRequestException();
			}
			
			KmForumPost model = (KmForumPost) getModel(request, response);
			postForm.setFdId(null);
			postForm.setFdTopicId(model.getKmForumTopic().getFdId().toString());
			postForm.setFdForumId(model.getKmForumTopic().getKmForumCategory()
					.getFdId().toString());
			postForm.setFdForumName(model.getKmForumTopic()
					.getKmForumCategory().getFdName());
			if (model.getDocSubject().contains(
					ResourceUtil.getString("kmForumPost.button.reply",
							"km-forum", request.getLocale())
							+ ":")) {
				postForm.setDocSubject(model.getDocSubject());
			} else {
				postForm.setDocSubject(ResourceUtil.getString(
						"kmForumPost.button.reply", "km-forum", request
								.getLocale())
						+ ":" + model.getDocSubject());
			}
			// postForm.setFdQuoteMsg(getQuoteMsg(request, model));
		} catch (Exception e) {
			messages.addError(e);
		}

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("reply");
		}
	}

	/**
	 * 快速回复贴子。<br>
	 * URL中必须包含fdId参数，该参数为记录id。<br>
	 * 该操作一般以HTTP的GET方式触发。
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回edit页面，否则返回failure页面
	 * @throws Exception
	 */
	public ActionForward quickReply(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		String flag = request.getParameter("flag");
		try {
			KmForumPostForm postForm = (KmForumPostForm) form;
			postForm.setMethod_GET("add");
			String fdTopicId = request.getParameter("fdTopicId");
			if (StringUtil.isNotNull(fdTopicId)) {
				KmForumTopic kmForumTopic = (KmForumTopic) getTopicServiceImp(
						request).findByPrimaryKey(fdTopicId);
				postForm.setFdId(null);
				postForm.setFdTopicId(kmForumTopic.getFdId().toString());
				postForm.setFdForumId(kmForumTopic.getKmForumCategory()
						.getFdId().toString());
				postForm.setFdForumName(kmForumTopic.getKmForumCategory()
						.getFdName());
				if (kmForumTopic.getDocSubject().contains(
						ResourceUtil.getString("kmForumPost.toolTip.re",
								"km-forum", request.getLocale())
								+ ":")) {
					postForm.setDocSubject(kmForumTopic.getDocSubject());
				} else {
					postForm.setDocSubject(ResourceUtil.getString(
							"kmForumPost.toolTip.re", "km-forum", request
									.getLocale())
							+ ":" + kmForumTopic.getDocSubject());
				}
				request.setAttribute("globalIsAnonymous", new KmForumConfig()
						.getAnonymous());
				request.setAttribute("fdForumAnonymous", kmForumTopic
						.getKmForumCategory().getFdAnonymous());
			}
			// postForm.setFdQuoteMsg(getQuoteMsg(request, model));
			request.setAttribute(getFormName(postForm, request), postForm);
		} catch (Exception e) {
			messages.addError(e);
		}

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return mapping.findForward("failure");
		} else {
			if ("pda".equals(flag)) {
				return mapping.findForward("quickReply.4pda");
			} else {
				return mapping.findForward("quickReply");
			}
		}
	}

	private IBaseModel getModel(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("postId");
		IBaseModel model = null;
		if (!StringUtil.isNull(id)) {
			model = getServiceImp(request).findByPrimaryKey(id);
		}
		if (model == null) {
            throw new NoRecordException();
        }
		UserOperHelper.logFind(model);// 添加日志信息
		return model;
	}

	/**
	 * 打开单个贴子显示页面。<br>
	 * 该操作一般以HTTP的GET方式触发。
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回singleView页面，否则返回failure页面
	 * @throws Exception
	 */
	public ActionForward singleView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			loadActionForm(mapping, form, request, response);
		} catch (Exception e) {
			messages.addError(e);
		}

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("singleView");
		}
	}

	@Override
	protected ActionForm createNewForm(ActionMapping mapping, ActionForm form,
									   HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		form.reset(mapping, request);
		KmForumPostForm postForm = (KmForumPostForm) form;
		postForm.setDocCreateTime(DateUtil.convertDateToString(new Date(),
				DateUtil.TYPE_DATETIME, request.getLocale()));
		String fdForumCategoryId = request.getParameter("fdForumId");
		if (StringUtil.isNotNull(fdForumCategoryId)) {
			KmForumCategory category = (KmForumCategory) getKmForumCategoryServiceImp(
					request).findByPrimaryKey(fdForumCategoryId,
					KmForumCategory.class, true);
			if (category != null && category.getFdParent() != null) {
				postForm.setFdForumName(category.getFdName());
			} else {
				postForm.setFdForumId(null);

			}
			request.setAttribute("globalIsAnonymous", new KmForumConfig()
					.getAnonymous());
			request.setAttribute("fdForumAnonymous", category.getFdAnonymous());
		}
		return form;
	}

	@Override
	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-edit", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			loadActionForm(mapping, form, request, response);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-edit", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("edit", mapping, form, request, response);
		}
	}

	@Override
	protected void loadActionForm(ActionMapping mapping, ActionForm form,
								  HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		form.reset(mapping, request);
		IExtendForm rtnForm = null;
		String id = request.getParameter("fdId");
		if (!StringUtil.isNull(id)) {
			IBaseModel model = getServiceImp(request).findByPrimaryKey(id,
					null, true);
			if (model != null) {
				// 添加日志信息
				UserOperHelper.logFind(model);
				KmForumPost kmForumPost = (KmForumPost) model;
				KmForumTopic kmForumTopic = kmForumPost.getKmForumTopic();
				kmForumPost.setFdIsNotify(kmForumTopic.getFdIsNotify());
				kmForumPost.setFdNotifyType(kmForumTopic.getFdNotifyType());
				rtnForm = getServiceImp(request).convertModelToForm(
						(IExtendForm) form, model, new RequestContext(request));
				request.setAttribute("kmForumTopic", kmForumTopic);
				if (kmForumPost.getFdIsAnonymous()) {
					((KmForumPostForm) rtnForm).setFdIsAnonymous("1");
				}
				if (kmForumPost.getFdIsOnlyView() != null
						&& kmForumPost.getFdIsOnlyView()) {
					((KmForumPostForm) rtnForm).setFdIsOnlyView("1");
				}
			}
		}
		if (rtnForm == null) {
            throw new NoRecordException();
        }
		{
			request.setAttribute(getFormName(rtnForm, request), rtnForm);
		}
	}

	/**
	 * 选择模板后刷新页面，加载配置
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward reload(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-reload", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			KmForumPostForm postForm = (KmForumPostForm) form;
			if (!StringUtil.isNull(postForm.getFdForumId())) {
				KmForumCategory category = (KmForumCategory) getKmForumCategoryServiceImp(
						request).findByPrimaryKey(postForm.getFdForumId(),
						KmForumCategory.class, true);
				if (category != null && category.getFdParent() != null) {
					postForm.setFdForumName(category.getFdName());
					postForm.setFdForumId(category.getFdId());
				}
			}
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-reload", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
            return getActionForward("edit", mapping, form, request, response);
        }
	}

	/**
	 * 判断论坛版块是否可以匿名
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getIsAnonymous(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmForumCategory kmForumCategory = (KmForumCategory) getKmForumCategoryServiceImp(
				request).findByPrimaryKey(request.getParameter("fdForumId"));
		Boolean fdForumAnonymous = new Boolean(false);
		if(kmForumCategory == null){
			throw new NoRecordException();
		}
		fdForumAnonymous = kmForumCategory.getFdAnonymous();
		String globalIsAnonymous = new KmForumConfig().getAnonymous();
		response.setHeader("content-type", "text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		if (fdForumAnonymous && "true".equals(globalIsAnonymous)) {
			out.print(true);
		} else {
			out.print(false);
		}
		return null;
	}

	/**
	 * 判断内容是否含有敏感词
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getIsHasSensitiveword(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String content = URLDecoder.decode(request.getParameter("content"),
				"UTF-8");
		String keysString = new KmForumConfig().getWords();
		// 重新读取敏感词库
		if ("true".equals(new KmForumConfig().getIsNeedAcquire())) {
			SensitiveWordCheckUtil.isNeedAcquire = "true";
		}
		boolean isHas = SensitiveWordCheckUtil.CheckSensitiveWord(content,
				keysString.split(";"));
		// 还原
		if ("true".equals(SensitiveWordCheckUtil.isNeedAcquire)) {
			KmForumConfig config = new KmForumConfig();
			config.setIsNeedAcquire("false");
			config.save();
			SensitiveWordCheckUtil.isNeedAcquire = "false";
		}
		SensitiveWordCheckUtil.isNeedAcquire = "true";
		PrintWriter out = response.getWriter();
		if (isHas) {
			out.print(true);
		} else {
			out.print(false);
		}
		return null;
	}

	/**
	 * #87466 RTF域xss漏洞
	 * 
	 * @param docContent
	 * @return
	 */
	private boolean checkIsRtfXss(String docContent) {
		boolean rtn = false;
		if (StringUtil.isNotNull(docContent)) {

			// rtf通过H5上传视频后会形成Video标签，标签是存在on字符，先过滤掉
			/*
			 * <video controls="controls" height="450" src=
			 * "/ekp/resource/fckeditor/editor/filemanager/download?fdId=1722b6ddf9c504cb3668d214e3e8936f"
			 * style="background: black none repeat scroll 0% 0%;"
			 * width="600">&nbsp;</video>
			 */
			docContent = docContent.replaceAll("controls", "");
			docContent = docContent.replaceAll("none", "");			
			Pattern p = Pattern.compile(
					"<(?:[a-z][^>]*)[\\s|\\\\]*?on[a-z]+\\s*=\\s*(\"[^\"]+\"|'[^']+'|[^\\s]+)([^>]*)>");
			Matcher m = p.matcher(docContent);
			rtn = m.find();
		}
		return rtn;
	}
	
	
	@Override
	@Separater("/km/forum/mobile/edit.jsp")
	public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		return super.add(mapping, form, request, response);
	}

	/**
	 * 回帖新页面功能
	 */
	@Separater("/km/forum/mobile/editQuickReply.jsp")
	public ActionForward addQuickReply(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-addReplyQuote", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			KmForumPostForm postForm = (KmForumPostForm) form;
			postForm.setMethod_GET("add");
			String fdTopicId = request.getParameter("fdTopicId");
			if (StringUtil.isNotNull(fdTopicId)) {
				KmForumTopic kmForumTopic = (KmForumTopic) getTopicServiceImp(
						request).findByPrimaryKey(fdTopicId);
				postForm.setFdId(null);
				postForm.setFdTopicId(kmForumTopic.getFdId().toString());
				postForm.setFdForumId(kmForumTopic.getKmForumCategory()
						.getFdId().toString());
				postForm.setFdForumName(kmForumTopic.getKmForumCategory()
						.getFdName());
				if (kmForumTopic.getDocSubject().contains(
						ResourceUtil.getString("kmForumPost.toolTip.re",
								"km-forum", request.getLocale())
								+ ":")) {
					postForm.setDocSubject(kmForumTopic.getDocSubject());
				} else {
					postForm.setDocSubject(ResourceUtil.getString(
							"kmForumPost.toolTip.re", "km-forum", request
									.getLocale())
							+ ":" + kmForumTopic.getDocSubject());
				}
				request.setAttribute("globalIsAnonymous", new KmForumConfig()
						.getAnonymous());
				request.setAttribute("fdForumAnonymous", kmForumTopic
						.getKmForumCategory().getFdAnonymous());
			}
			request.setAttribute(getFormName(postForm, request), postForm);
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-addReplyQuote", true, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("quickReply");
		}
	}
}
