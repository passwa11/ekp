package com.landray.kmss.km.forum.actions;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.constant.SysDocConstant;
import com.landray.kmss.km.forum.forms.KmForumTopicForm;
import com.landray.kmss.km.forum.model.KmForumTopic;
import com.landray.kmss.km.forum.service.IKmForumTopicService;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.KmssMessageWriter;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;

/**
 * 创建日期 2006-Sep-07
 * 
 * @author 吴兵
 */
public class KmForumTopicAction extends ExtendAction

{	
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(KmssMessageWriter.class);
	protected IKmForumTopicService kmForumTopicService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (kmForumTopicService == null) {
            kmForumTopicService = (IKmForumTopicService) getBean("kmForumTopicService");
        }
		return kmForumTopicService;
	}

	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request,
										 HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		String isQueryDraftForum = (String) request
				.getAttribute("isQueryDraftForum");
		StringBuffer sb = new StringBuffer();
		// 返回所有非草稿状态帖子
		String all = request.getParameter("all");
		if (StringUtil.isNotNull(all) && "true".equals(all)) {
			sb.append(" kmForumTopic.fdStatus != '"
					+ SysDocConstant.DOC_STATUS_DRAFT + "'");
			hqlInfo.setWhereBlock(sb.toString());

			return;
		}

		sb.append(" 1=1 ");
		String fdForumId = request.getParameter("fdForumId");
		if (!StringUtil.isNull(fdForumId)) {
			// 为兼容老数据看是否需要更新所在树链的层级id
			((IKmForumTopicService) getServiceImp(request))
					.updateHierarchyId(fdForumId);
			sb
					.append(" and (kmForumTopic.kmForumCategory.fdHierarchyId like :fdForumId) ");
			hqlInfo.setParameter("fdForumId", "%" + fdForumId + "x%");
		}
		String fdPinked = request.getParameter("fdPinked");
		if (!StringUtil.isNull(fdPinked)) {
			sb.append(" and kmForumTopic.fdPinked=:fdPinked ");
			if ("1".equals(fdPinked) || "true".equals(fdPinked.toLowerCase())) {
				hqlInfo.setParameter("fdPinked", true);
			} else {
				hqlInfo.setParameter("fdPinked", false);
			}
		}
		String fdPosterId = request.getParameter("fdPosterId");

		String myPost = request.getParameter("myPost");
		if (StringUtil.isNotNull(myPost)) {
			sb.append(" and kmForumTopic.fdPoster.fdId=:fdPosterId ");
			hqlInfo.setParameter("fdPosterId", UserUtil.getUser().getFdId());
		}
		String MyJoin = request.getParameter("MyJoin");
		if (StringUtil.isNotNull(MyJoin)) {
			sb
					.append(" and kmForumTopic.fdId in (select kmForumTopic from KmForumTopic as kmForumTopic ");
			sb.append(" inner join kmForumTopic.forumPosts as kmForumPost ");
			sb.append(" where kmForumPost.fdPoster.fdId=:fdMyPosterId ");
			sb.append(" and kmForumPost.fdFloor>1) ");
			hqlInfo.setParameter("fdMyPosterId", UserUtil.getUser().getFdId());
		}
		// 草稿只能查看自己的
		if (StringUtil.isNotNull(isQueryDraftForum)) {
			if ("true".equals(isQueryDraftForum)) {
				sb.append(" and kmForumTopic.fdStatus = '"
						+ SysDocConstant.DOC_STATUS_DRAFT + "'");
			}
			if (fdPosterId == null) {
				fdPosterId = UserUtil.getUser().getFdId();
				sb.append(" and kmForumTopic.fdPoster.fdId=:fdPosterId ");
				hqlInfo.setParameter("fdPosterId", fdPosterId);
			}
		} else {
			sb.append(" and (kmForumTopic.fdStatus = '"
					+ SysDocConstant.DOC_STATUS_PUBLISH
					+ "' or kmForumTopic.fdStatus = '"
					+ SysDocConstant.DOC_STATUS_EXPIRE + "')");
		}
		hqlInfo.setWhereBlock(sb.toString());
	}

	/**
	 * 根据http请求，返回执行list操作需要用到的orderBy语句。
	 * 
	 * @param request
	 * @param curOrderBy
	 *            默认设置的OrderBy参数
	 * @return
	 * @throws Exception
	 */
	@Override
	protected String getFindPageOrderBy(HttpServletRequest request,
										String curOrderBy) throws Exception {
		// 默认先按置顶排序，再按发帖排序
		StringBuffer sb = new StringBuffer();
		sb.append(" kmForumTopic.fdSticked desc");
		if (StringUtil.isNull(curOrderBy)) {
			sb.append(",kmForumTopic.fdLastPostTime desc");
		} else {
			sb.append("," + curOrderBy);
		}
		return sb.toString();
	}

	private String getForward(HttpServletRequest request) {
		StringBuffer sb = new StringBuffer();
		sb.append("/km/forum/km_forum/kmForumPost.do?method=view");
		sb.append("&fdForumId=" + request.getParameter("fdForumId"));
		sb.append("&fdTopicId=" + request.getParameter("fdTopicId"));
		return sb.toString();
	}

	/**
	 * 个人空间和个人中心查询代码 add by tanyh
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward listPersonOrZone(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-list", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String s_pageno = request.getParameter("pageno");
			String s_rowsize = request.getParameter("rowsize");
			int pageno = 0;
			int rowsize = SysConfigParameters.getRowSize();
			if (s_pageno != null && s_pageno.length() > 0) {
				pageno = Integer.parseInt(s_pageno);
			}
			if (s_rowsize != null && s_rowsize.length() > 0) {
				rowsize = Integer.parseInt(s_rowsize);
			}
			String type = request.getParameter("type");
			HQLInfo hqlInfo = new HQLInfo();
			String userId = StringUtil.isNotNull(type) && "person".equals(type) ? UserUtil
					.getUser().getFdId()
					: request.getParameter("userId");
			String whereBlock = "1=1";
			hqlInfo.setPageNo(pageno);
			hqlInfo.setRowSize(rowsize);
			CriteriaValue cv = new CriteriaValue(request);
			String topic = cv.poll("topic");
			String category = cv.poll("category");
			String docSubject = cv.poll("docSubject");
			// 主题
			if (StringUtil.isNotNull(docSubject)) {
				whereBlock += " and kmForumTopic.docSubject like :docSubject";
				hqlInfo.setParameter("docSubject", "%" + docSubject + "%");
			}
			// xx的帖子
			if (StringUtil.isNotNull(topic)) {
				// XX发起的
				if ("create".equals(topic)) {
					whereBlock += " and kmForumTopic.fdPoster.fdId=:userId";
					hqlInfo.setParameter("userId", userId);
				}
				// xx参加的
				else if ("attend".equals(topic)) {
					StringBuffer sb = new StringBuffer();
					sb
							.append(" and kmForumTopic.fdId in (select kmForumTopic from KmForumTopic as kmForumTopic ");
					sb
							.append(" inner join kmForumTopic.forumPosts as kmForumPost ");
					sb
							.append(" where kmForumPost.fdPoster.fdId=:fdMyPosterId ");
					sb.append(" and kmForumPost.fdFloor>1) ");
					whereBlock += sb.toString();
					hqlInfo.setParameter("fdMyPosterId", userId);
				}
			}
			// 版块
			if (StringUtil.isNotNull(category)) {
				// 为兼容老数据看是否需要更新所在树链的层级id
				((IKmForumTopicService) getServiceImp(request))
						.updateHierarchyId(category);
				whereBlock += " and (kmForumTopic.kmForumCategory.fdHierarchyId like :fdForumId)";
				hqlInfo.setParameter("fdForumId", "%" + category + "x%");
			}

			String orderByPara = request.getParameter("orderby");
			String orderBy = "fdSticked desc,";
			boolean isReserve = false;
			String ordertype = request.getParameter("ordertype");
			if (ordertype != null && "down".equalsIgnoreCase(ordertype)) {
				isReserve = true;
			}
			if(StringUtil.isNull(orderByPara)){
				orderBy = "fdSticked desc,fdPinked desc,docAlterTime desc";
			}else if("fdReplyCount;fdHitCount".equals(orderByPara)){
				if(isReserve){
					orderBy+="fdReplyCount desc,fdHitCount desc";
				}else{
					orderBy+="fdReplyCount,fdHitCount";
				}
			}else{
				if(isReserve){
					orderBy+=orderByPara+" desc";
				}else{
					orderBy+=orderByPara;
				}
			}
			hqlInfo.setOrderBy(orderBy);
			
			hqlInfo.setWhereBlock(whereBlock);
			Page page = getServiceImp(request).findPage(hqlInfo);
			// 添加日志信息
			UserOperHelper.logFindAll(page.getList(),
					getServiceImp(request).getModelName());
			request.setAttribute("queryPage", page);
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-list", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("listPersonOrZone", mapping, form, request,
					response);
		}
	}

	/**
	 * 通过URL的方式直接结贴<br>
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
	public ActionForward conclude(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			if (!"GET".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			String id = request.getParameter("fdId");
			if (StringUtil.isNull(id)) {
                messages.addError(new NoRecordException());
            } else {
                ((IKmForumTopicService) getServiceImp(request)).doConclude(id);
            }
		} catch (Exception e) {
			logger.error("", e);
			messages.addError(e);
		}
		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_CLOSE).save(request);
		PrintWriter out=response.getWriter();
		if (messages.hasError()){
			out.print(false);
		}
		else {
			KmssReturnPage.getInstance(request).setTitle(
					new KmssMessage("km-forum:kmForumTopic.conclude.msg"))
					.addButton(KmssReturnPage.BUTTON_RETURN).save(request);
			out.print(true);
		}
		return null;
	}

	public ActionForward batchConclude(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			String fdId = request.getParameter("fdId");
			if (StringUtil.isNotNull(fdId)) {
				String[] fdIds = fdId.split(",");
				for (String id : fdIds) {
					KmForumTopic kmForumTopic = (KmForumTopic) getServiceImp(
							request).findByPrimaryKey(id);
					String fdStatus = kmForumTopic.getFdStatus();
					if (!fdStatus.equals(SysDocConstant.DOC_STATUS_EXPIRE)) {
						((IKmForumTopicService) getServiceImp(request))
								.doConclude(id);
					}
				}
			} else {
				messages.addError(new NoRecordException());
			}
		} catch (Exception e) {
			logger.error("", e);
			messages.addError(e);
		}
		PrintWriter out = response.getWriter();
		if (!messages.hasError()) {
			out.print(true);
		} else {
			out.print(false);
		}
		return null;
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
		TimeCounter.logCurrentTime("Action-delete", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"GET".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			String id = request.getParameter("fdId");
			if (StringUtil.isNull(id)) {
                messages.addError(new NoRecordException());
            } else {
				getServiceImp(request).delete(id);
			}
		} catch (Exception e) {
			logger.error("", e);
			messages.addError(e);
		}
		PrintWriter out=response.getWriter();
		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_CLOSE).save(request);
		TimeCounter.logCurrentTime("Action-delete", false, getClass());
		if (messages.hasError()) {
            out.print(false);
        } else {
            out.print(true);
        }
		return null;
	}
	
	/**
	 * 通过URL的方式直接置顶一条记录。<br>
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
	public ActionForward stick(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			if (!"GET".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			String id = request.getParameter("fdId");
			Long days = new Long(request.getParameter("fdDays"));
			if (StringUtil.isNull(id)) {
                messages.addError(new NoRecordException());
            } else {
                ((IKmForumTopicService) getServiceImp(request)).doStick(id,days);
            }
		} catch (Exception e) {
			logger.error("", e);
			messages.addError(e);
		}

		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_CLOSE).save(request);
		PrintWriter out=response.getWriter();
		if (messages.hasError()) {
            out.print(false);
        } else {
			KmssReturnPage.getInstance(request).setTitle(
					new KmssMessage("km-forum:kmForumTopic.stick.msg"))
					.addButton(KmssReturnPage.BUTTON_RETURN).save(request);
			out.print(true);
		}
		return null;
	}

	/**
	 * 通过URL的方式直接取消置顶一条记录。<br>
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
	public ActionForward undoStick(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			if (!"GET".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			String id = request.getParameter("fdId");
			if (StringUtil.isNull(id)) {
                messages.addError(new NoRecordException());
            } else {
                ((IKmForumTopicService) getServiceImp(request)).undoStick(id);
            }
		} catch (Exception e) {
			logger.error("", e);
			messages.addError(e);
		}

		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_CLOSE).save(request);
		PrintWriter out=response.getWriter();
		if (messages.hasError()) {
            out.print(false);
        } else {
			KmssReturnPage.getInstance(request).setTitle(
					new KmssMessage("km-forum:kmForumTopic.unStick.msg"))
					.addButton(KmssReturnPage.BUTTON_RETURN).save(request);
			out.print(true);
		}
		return null;
	}

	/**
	 * 通过URL的方式直接置精华一条记录。<br>
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
	public ActionForward pink(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			if (!"GET".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			String id = request.getParameter("fdId");
			if (StringUtil.isNull(id)) {
                messages.addError(new NoRecordException());
            } else {
                ((IKmForumTopicService) getServiceImp(request)).doPink(id);
            }
		} catch (Exception e) {
			logger.error("", e);
			messages.addError(e);
		}

		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_CLOSE).save(request);
		PrintWriter out=response.getWriter();
		if (messages.hasError()) {
            out.print(false);
        } else {
			KmssReturnPage.getInstance(request).setTitle(
					new KmssMessage("km-forum:kmForumTopic.pink.msg"))
					.addButton(KmssReturnPage.BUTTON_RETURN).save(request);
			out.print(true);
		}
		return null;
	}

	/**
	 * 通过URL的方式直接取消置精华一条记录。<br>
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
	public ActionForward undoPink(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			if (!"GET".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			String id = request.getParameter("fdId");
			if (StringUtil.isNull(id)) {
                messages.addError(new NoRecordException());
            } else {
                ((IKmForumTopicService) getServiceImp(request)).undoPink(id);
            }
		} catch (Exception e) {
			logger.error("", e);
			messages.addError(e);
		}

		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_CLOSE).save(request);
		PrintWriter out=response.getWriter();
		if (messages.hasError()) {
            out.print(false);
        } else {
			KmssReturnPage.getInstance(request).setTitle(
					new KmssMessage("km-forum:kmForumTopic.unPink.msg"))
					.addButton(KmssReturnPage.BUTTON_RETURN).save(request);
			out.print(true);
		}
		return null;
	}

	/**
	 * 打开转移页面。<br>
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
	public ActionForward showMove(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			if(request.getParameter("type")==null){
			   loadActionForm(mapping, form, request, response);
			}
		} catch (Exception e) {
			messages.addError(e);
		}

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("showMove");
		}
	}

	/**
	 * 帖子转移。<br>
	 * URL中必须包含fdId参数，该参数为记录id。<br>
	 * 该操作一般以HTTP的POST方式触发。
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 若执行成功，返回success页面，否则返回failure页面
	 * @throws Exception
	 */
	public ActionForward move(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out=response.getWriter();
		KmssMessages messages = new KmssMessages();
		String fdTargetId ="";
		try {
			String id = request.getParameter("fdId");
		    fdTargetId = request.getParameter("fdTargetId");
			if (StringUtil.isNull(id)){
				messages.addError(new NoRecordException());
			}else{
				//多文档转移
				if(request.getParameter("type")!=null&& "moveAll".equals(request.getParameter("type"))){
					String[] ids = id.split(",");
					for(int i=0;i<ids.length;i++){
						((IKmForumTopicService) getServiceImp(request)).move(ids[i],
								fdTargetId);
					}
				}else{
					((IKmForumTopicService) getServiceImp(request)).move(id,
							fdTargetId);
				}
			}
		} catch (Exception e) {
			logger.error("", e);
			messages.addError(e);
		}
		if (messages.hasError()) {
            out.print("");
        } else {
            out.print(fdTargetId);
        }
		return null;
	}

	/**
	 * 打开列表页面。<br>
	 * 该操作一般以HTTP的GET方式触发。
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回listOut页面，否则返回failure页面
	 * @throws Exception
	 */
	public ActionForward listOut(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			String s_pageno = request.getParameter("pageno");
			String s_rowsize = request.getParameter("rowsize");
			String orderby = request.getParameter("orderby");
			String ordertype = request.getParameter("ordertype");
			boolean isReserve = false;
			if (ordertype != null && "down".equalsIgnoreCase(ordertype)) {
				isReserve = true;
			}
			int pageno = 0;
			int rowsize = 0;
			if (s_pageno != null && s_pageno.length() > 0) {
				pageno = Integer.parseInt(s_pageno);
			}
			if (s_rowsize != null && s_rowsize.length() > 0) {
				rowsize = Integer.parseInt(s_rowsize);
			}
			if (isReserve) {
                orderby += " desc";
            }
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setPageNo(pageno);
			hqlInfo.setRowSize(rowsize);
			changeFindPageHQLInfo(request, hqlInfo);
			//参与的论坛
			String postId=request.getParameter("fdMyPosterId");
			if(StringUtil.isNotNull(postId)){
				String whereBlock=hqlInfo.getWhereBlock();
				if(StringUtil.isNull(whereBlock)){
					whereBlock="1=1";
				}
				StringBuffer sb = new StringBuffer();
				sb.append(" and kmForumTopic.fdId in (select kmForumTopic from KmForumTopic as kmForumTopic ");
				sb.append(" inner join kmForumTopic.forumPosts as kmForumPost ");
				sb.append(" where kmForumPost.fdPoster.fdId=:fdMyPosterId ");
				sb.append(" and kmForumPost.fdFloor>1) ");
				whereBlock += sb.toString();
				hqlInfo.setWhereBlock(whereBlock);
				hqlInfo.setParameter("fdMyPosterId", postId);
			}
			hqlInfo.setOrderBy(getFindPageOrderBy(request, orderby));
			Page page = getServiceImp(request).findPage(hqlInfo);
			// 添加日志信息
			UserOperHelper.logFindAll(page.getList(),
					getServiceImp(request).getModelName());
			request.setAttribute("queryPage", page);
		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("listOut", mapping, form, request, response);
		}
	}

	public ActionForward docList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			String fdImportInfo = request.getParameter("fdImportInfo");
			HQLInfo hqlInfo = new HQLInfo();
			String whereBlock = " kmForumTopic.fdImportInfo=:fdImportInfo ";
			hqlInfo.setParameter("fdImportInfo", fdImportInfo);
			hqlInfo.setWhereBlock(whereBlock);
			List list = getServiceImp(request).findList(hqlInfo);
			// 添加日志信息
			UserOperHelper.logFindAll(list,
					getServiceImp(request).getModelName());
			request.setAttribute("queryList", list);
		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("docList", mapping, form, request, response);
		}
	}

	/**
	 * 查询草稿
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward draftList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-draftList", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String s_pageno = request.getParameter("pageno");
			String s_rowsize = request.getParameter("rowsize");
			String orderby = request.getParameter("orderby");
			String ordertype = request.getParameter("ordertype");
			boolean isReserve = false;
			if (ordertype != null && "down".equalsIgnoreCase(ordertype)) {
				isReserve = true;
			}
			int pageno = 0;
			int rowsize = 0;
			if (s_pageno != null && s_pageno.length() > 0) {
				pageno = Integer.parseInt(s_pageno);
			}
			if (s_rowsize != null && s_rowsize.length() > 0) {
				rowsize = Integer.parseInt(s_rowsize);
			}
			if (isReserve) {
                orderby += " desc";
            }
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setOrderBy(orderby);
			hqlInfo.setPageNo(pageno);
			hqlInfo.setRowSize(rowsize);
			request.setAttribute("isQueryDraftForum", "true");
			changeFindPageHQLInfo(request, hqlInfo);
			Page page = getServiceImp(request).findPage(hqlInfo);
			// 添加日志信息
			UserOperHelper.logFindAll(page.getList(),
					getServiceImp(request).getModelName());
			request.setAttribute("queryPage", page);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-draftList", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("draftList", mapping, form, request,
					response);
		}
	}

	public ActionForward deleteDraft(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return super.delete(mapping, form, request, response);
	}

	public ActionForward deleteallDraft(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return super.deleteall(mapping, form, request, response);
	}

	/**
	 * 打开推荐页面 URL中必须包含fdId参数，该参数为记录id。<br>
	 * 该操作一般以HTTP的GET方式触发。
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回edit页面，否则返回failure页面
	 * @throws Exception
	 */
	public ActionForward introduce(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-introduce", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			loadActionForm(mapping, form, request, response);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-introduce", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("introduce", mapping, form, request,
					response);
		}
	}

	/**
	 * 执行推荐操作 该操作一般以HTTP的POST方式触发。
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回success页面，否则返回edit页面
	 * @throws Exception
	 */
	public ActionForward updateIntroduce(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-updateIntroduce", true, getClass());
		KmssMessages messages = new KmssMessages();
		PrintWriter out=response.getWriter();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			String fdId = request.getParameter("fdId");
			String fdTargetIds = request.getParameter("fdTargetIds");
			String fdNotifyType = request.getParameter("fdNotifyType");
			((IKmForumTopicService) getServiceImp(request)).updateIntroduce(
					fdId, fdTargetIds, fdNotifyType);
		} catch (Exception e) {
			messages.addError(e);
			logger.error("", e);
		}
		TimeCounter.logCurrentTime("Action-updateIntroduce", false, getClass());
		if (messages.hasError()) {
			out.print(false);
		} else {
			out.print(true);
		}
		return null;
	}

	/**
	 * 转移帖子
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward changeDocumentCategory(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		String forumId = ((KmForumTopicForm) form).getFdForumId();
		String ids = request.getParameter("values");
		try {
			((IKmForumTopicService) getServiceImp(request))
					.updateDucmentCategory(ids, forumId);
		} catch (Exception e) {
			messages.addError(e);
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			getActionForward("failure", mapping, form, request, response);
		}
		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_CLOSE).save(request);
		return getActionForward("success", mapping, form, request, response);

	}

}
