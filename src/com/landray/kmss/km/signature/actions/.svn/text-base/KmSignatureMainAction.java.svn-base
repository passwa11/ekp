package com.landray.kmss.km.signature.actions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.KmssException;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.km.signature.forms.KmSignatureMainForm;
import com.landray.kmss.km.signature.model.KmSignatureConfig;
import com.landray.kmss.km.signature.model.KmSignatureMain;
import com.landray.kmss.km.signature.service.IKmSignatureMainService;
import com.landray.kmss.km.signature.service.IKmSignaturePasswordEncoder;
import com.landray.kmss.km.signature.util.BlobUtil;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.sys.authentication.intercept.ValidatorRequestContext;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.model.SysOrgPost;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.web.action.ActionRedirect;
import com.sunbor.web.tag.Page;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.hibernate.query.Query;

/**
 * 印章库 Action
 * 
 * @author
 * @version 1.0 2013-09-23
 */
public class KmSignatureMainAction extends ExtendAction {

	IKmSignaturePasswordEncoder kmSignaturePasswordEncoder = (IKmSignaturePasswordEncoder) SpringBeanUtil
			.getBean("kmSignaturePasswordEncoder");

	protected IKmSignatureMainService signatureService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (signatureService == null) {
            signatureService = (IKmSignatureMainService) getBean("kmSignatureMainService");
        }
		return signatureService;
	}

	private IKmSignatureMainService kmSignatureMainService = null;

	public IKmSignatureMainService getKmSignatureMainService() {
		if (this.kmSignatureMainService == null) {
			this.kmSignatureMainService = (IKmSignatureMainService) SpringBeanUtil
					.getBean("kmSignatureMainService");
		}
		return this.kmSignatureMainService;
	}

	@Override
	public ActionForward update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmSignatureMainForm signatureForm = (KmSignatureMainForm) form;
		signatureForm.setFdId((String) request.getParameter("fdId"));
		// 用户名称
		signatureForm
				.setFdUserName((String) request.getParameter("fdUserName"));
		// 自动编号
		signatureForm.setFdSignatureId("1");
		// 创建者
		signatureForm.setDocCreatorId(UserUtil.getUser().getFdId());
		signatureForm.setDocCreatorName(UserUtil.getUser().getFdName());
		// 用户密码
		String fdPassword = signatureForm.getFdPassword();
		if (StringUtil.isNotNull(fdPassword)) {//fdPassword != null && fdPassword != ""
			fdPassword = fdPassword.substring(1);
		}else if ( ("0".equals(signatureForm.getFdIsFreeSign())) ||
				("false".equals(signatureForm.getFdIsFreeSign())) ){//如果是 非免密签名 则
			String tempFdPassword = request.getParameter("tempFdPassword");
			if (StringUtil.isNotNull(tempFdPassword)){
				fdPassword = kmSignaturePasswordEncoder.encodePassword(tempFdPassword);// md5加密算法
			}
		}
		signatureForm.setFdPassword(fdPassword);
		// 印章名称
		signatureForm
				.setFdMarkName((String) request.getParameter("fdMarkName"));
		// 授权用户
		if ("".equals(request.getParameter("fdUsersIds"))) {
			signatureForm.setFdUsersIds(signatureForm.getDocCreatorId());
			signatureForm.setFdUsersNames(signatureForm.getDocCreatorName());
		} else {
			String usersIds = (String) request.getParameter("fdUsersIds");
			signatureForm.setFdUsersIds(usersIds);
			signatureForm.setFdUsersNames((String) request.getParameter("fdUsersNames"));
		}
		// 签章类型
		signatureForm.setFdDocType((String) request.getParameter("fdDocType"));
		// 是否有效，已弃用，但必填，所以设置默认有效
		signatureForm.setDocInForce("1");
//		// 分类id
//		signatureForm.setFdTempId((String) request.getParameter("fdTempId"));
//		// 分类名称
//		signatureForm
//				.setFdTempName((String) request.getParameter("fdTempName"));
		// 设置日期格式
		signatureForm.setFdMarkDate(DateUtil.convertDateToString(new Date(),
				DateUtil.TYPE_DATETIME, UserUtil.getKMSSUser().getLocale()));
		// 设置修改人
		signatureForm.setDocAlterorId(UserUtil.getUser().getFdId());
		signatureForm.setDocAlterorName(UserUtil.getUser().getFdName());
		signatureForm.setDocAlterTime(DateUtil.convertDateToString(new Date(),
				DateUtil.TYPE_DATETIME, UserUtil.getKMSSUser().getLocale()));

		return super.update(mapping, signatureForm, request, response);
	}

	@Override
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmSignatureMainForm signatureForm = (KmSignatureMainForm) form;		
		// 自动编号
		signatureForm.setFdSignatureId("1");
		// 创建者
		signatureForm.setDocCreatorId(UserUtil.getUser().getFdId());
		signatureForm.setDocCreatorName(UserUtil.getUser().getFdName());
		// 用户密码
		String fdPassword = request.getParameter("fdPassword");
		if (StringUtil.isNotNull(fdPassword)) {
			fdPassword = kmSignaturePasswordEncoder.encodePassword(fdPassword);// md5加密算法
		}
		signatureForm.setFdPassword(fdPassword);
		// 印章名称
		signatureForm
				.setFdMarkName((String) request.getParameter("fdMarkName"));
		
		if (StringUtil.isNull(signatureForm.getFdIsFreeSign())){
			signatureForm.setFdIsFreeSign("0");
		}		
//		// 分类id
//		signatureForm.setFdTempId((String) request.getParameter("fdTempId"));
//		// 分类名称
//		signatureForm
//				.setFdTempName((String) request.getParameter("fdTempName"));
		// 授权用户
		if ("".equals((String) request.getParameter("fdUsersIds"))) {
			signatureForm.setFdUsersIds(UserUtil.getUser().getFdId());
			signatureForm.setFdUsersNames(UserUtil.getUser().getFdName());
		} else {
			String usersIds = (String) request.getParameter("fdUsersIds");
			signatureForm.setFdUsersIds(usersIds);
			signatureForm.setFdUsersNames((String) request.getParameter("fdUsersNames"));
					
		}
		// 签章类型
		signatureForm.setFdDocType((String) request.getParameter("fdDocType"));
		// 是否有效，已弃用，但必填，所以设置默认有效
		signatureForm.setDocInForce("1");
		// 印章保存时间
		String fdMarkDate = DateUtil.convertDateToString(new Date(),
				DateUtil.TYPE_DATETIME, UserUtil.getKMSSUser().getLocale());
		signatureForm.setFdMarkDate(fdMarkDate);
		// 用户名称
		signatureForm.setFdUserName(UserUtil.getUser().getFdName());
		signatureForm.setDocCreatorId(UserUtil.getUser().getFdId());
		signatureForm.setDocCreatorName(UserUtil.getUser().getFdName());
		signatureForm.setDocCreateTime(DateUtil.convertDateToString(new Date(),
				DateUtil.TYPE_DATETIME, UserUtil.getKMSSUser().getLocale()));
		return super.save(mapping, signatureForm, request, response);
	}

	@Override
	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-add", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			ValidatorRequestContext validatorContext = new ValidatorRequestContext();
			List list = UserUtil.getKMSSUser().getUserAuthInfo()
					.getAuthRoleAliases();
			String[] arr = (String[]) list.toArray(new String[list.size()]);
			int handWrite = 0;
			int compSig = 0;
			int docTypeFlag = 0;
			for (int i = 0; i < arr.length; i++) {
				if ("ROLE_SIGNATURE_ADD".equals(arr[i])) {
					handWrite++;
				} else if ("ROLE_SIGNATURE_COMPANY".equals(arr[i])) {
					compSig++;
				} else if ("SYSROLE_ADMIN".equals(arr[i])
						|| "ROLE_SIGNATURE_ADMIN".equals(arr[i])) {
					handWrite++;
					compSig++;
				}
			}
			if (handWrite == 0 && compSig == 0) {// 当前用户没有查看权限
				docTypeFlag = 0;
			} else if (handWrite != 0 && compSig == 0) {// 当前用户只能选择手写签名
				docTypeFlag = 1;
			} else if (handWrite == 0 && compSig != 0) {// 当前用户只能选择公司印章
				docTypeFlag = 2;
			} else if (handWrite != 0 && compSig != 0) {// 当前用户可选择手写签名或公司印章
				docTypeFlag = 3;
			}
			request.setAttribute("docTypeFlag", docTypeFlag);
			ActionForm newForm = createNewForm(mapping, form, request, response);
			KmSignatureMainForm kmSignatureMainForm = (KmSignatureMainForm) newForm;

			kmSignatureMainForm.setDocCreatorId(UserUtil.getUser().getFdId());
			kmSignatureMainForm.setDocCreatorName(UserUtil.getUser()
					.getFdName());
			kmSignatureMainForm.setDocCreateTime(DateUtil.convertDateToString(
					new Date(), DateUtil.TYPE_DATETIME, UserUtil.getKMSSUser()
							.getLocale()));

			// kmSignatureMainForm.setDocAlterorId(UserUtil.getUser().getFdId());
			// kmSignatureMainForm.setDocAlterorName(UserUtil.getUser()
			// .getFdName());
			// kmSignatureMainForm.setDocAlterTime(DateUtil.convertDateToString(
			// new Date(), DateUtil.TYPE_DATETIME, UserUtil.getKMSSUser()
			// .getLocale()));

			if (kmSignatureMainForm != form) {
                request.setAttribute(getFormName(kmSignatureMainForm, request),
                        kmSignatureMainForm);
            }
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-add", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("add", mapping, form, request, response);
		}
	}

	protected ISysAttMainCoreInnerService sysAttMainService;

	public ISysAttMainCoreInnerService getSysAttMainService() {
		if (sysAttMainService == null) {
			sysAttMainService = (ISysAttMainCoreInnerService) SpringBeanUtil
					.getBean("sysAttMainService");
		}
		return sysAttMainService;
	}

	@Override
	public ActionForward saveadd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-saveadd", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			KmSignatureMainForm signatureForm = (KmSignatureMainForm) form;
			signatureForm.getAttachmentForms().remove(signatureForm);
			// 自动编号
			signatureForm.setFdSignatureId("1");
			// 创建者
			signatureForm.setDocCreatorId(UserUtil.getUser().getFdId());
			signatureForm.setDocCreatorName(UserUtil.getUser().getFdName());
			// 用户密码
			String fdPassword = request.getParameter("fdPassword");
			if (StringUtil.isNotNull(fdPassword)) {
				fdPassword = kmSignaturePasswordEncoder.encodePassword(fdPassword);// md5加密算法
			}
			signatureForm.setFdPassword(fdPassword);
			// 印章名称
			signatureForm.setFdMarkName((String) request
					.getParameter("fdMarkName"));
			// 分类id
//			signatureForm
//					.setFdTempId((String) request.getParameter("fdTempId"));
//			// 分类名称
//			signatureForm.setFdTempName((String) request
//					.getParameter("fdTempName"));
			// 授权用户
			if ("".equals((String) request.getParameter("fdUsersIds"))) {
				signatureForm.setFdUsersIds(UserUtil.getUser().getFdId());
				signatureForm.setFdUsersNames(UserUtil.getUser().getFdName());
			} else {
				// 提交人去重
				Boolean flag = true;
				String usersIds = (String) request.getParameter("fdUsersIds");
				String[] fdUsersIds = usersIds.split(";");
				for (int i = 0; i < fdUsersIds.length; i++) {
					if (fdUsersIds[i].equals(UserUtil.getUser().getFdId())) {
						flag = false;
					}
				}
				if (flag) {
					signatureForm.setFdUsersIds((String) request
							.getParameter("fdUsersIds")
							+ ";" + UserUtil.getUser().getFdId());
					signatureForm.setFdUsersNames((String) request
							.getParameter("fdUsersNames")
							+ ";" + UserUtil.getUser().getFdName());
				} else {
					signatureForm.setFdUsersIds((String) request
							.getParameter("fdUsersIds"));
					signatureForm.setFdUsersNames((String) request
							.getParameter("fdUsersNames"));
				}
			}
			// 签章类型
			signatureForm.setFdDocType((String) request
					.getParameter("fdDocType"));
			// 是否有效，已弃用，但必填，所以设置默认有效
			signatureForm.setDocInForce("1");
			// 印章保存时间
			signatureForm.setFdMarkDate(DateUtil.convertDateToString(
					new Date(), DateUtil.TYPE_DATETIME, UserUtil.getKMSSUser()
							.getLocale()));
			// 用户名称
			signatureForm.setFdUserName(UserUtil.getUser().getFdName());
			// 创建者
			signatureForm.setDocCreatorId(UserUtil.getUser().getFdId());
			signatureForm.setDocCreatorName(UserUtil.getUser().getFdName());
			signatureForm.setDocCreateTime(DateUtil.convertDateToString(
					new Date(), DateUtil.TYPE_DATETIME, UserUtil.getKMSSUser()
							.getLocale()));
			// 修改人
			signatureForm.setDocAlterorId(UserUtil.getUser().getFdId());
			signatureForm.setDocAlterorName(UserUtil.getUser().getFdName());
			signatureForm.setDocAlterTime(DateUtil.convertDateToString(
					new Date(), DateUtil.TYPE_DATETIME, UserUtil.getKMSSUser()
							.getLocale()));

			getServiceImp(request).add(signatureForm,
					new RequestContext(request));
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-saveadd", false, getClass());
		KmssReturnPage.getInstance(request).addMessages(messages).save(request);
		if (messages.hasError()) {
            return getActionForward("edit", mapping, form, request, response);
        } else {
            return add(mapping, form, request, response);
        }
	}

	@Override
	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ValidatorRequestContext validatorContext = new ValidatorRequestContext();
		List list = UserUtil.getKMSSUser().getUserAuthInfo()
				.getAuthRoleAliases();
		String[] arr = (String[]) list.toArray(new String[list.size()]);
		int handWrite = 0;
		int compSig = 0;
		int docTypeFlag = 0;
		for (int i = 0; i < arr.length; i++) {
			if ("ROLE_SIGNATURE_ADD".equals(arr[i])) {
				handWrite++;
			} else if ("ROLE_SIGNATURE_COMPANY".equals(arr[i])) {
				compSig++;
			} else if ("SYSROLE_ADMIN".equals(arr[i])
					|| "ROLE_SIGNATURE_ADMIN".equals(arr[i])) {
				handWrite++;
				compSig++;
			}
		}
		if (handWrite != 0 && compSig == 0) {// 当前用户只能选择手写签名
			docTypeFlag = 1;
		} else if (handWrite == 0 && compSig != 0) {// 当前用户只能选择公司印章
			docTypeFlag = 2;
		} else if (handWrite != 0 && compSig != 0) {// 当前用户可选择手写签名或公司印章
			docTypeFlag = 3;
		} else {
			docTypeFlag = 0;
		}
		request.setAttribute("docTypeFlag", docTypeFlag);
		return super.edit(mapping, form, request, response);
	}

	public ActionForward personalList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-list", true, getClass());
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
			int rowsize = SysConfigParameters.getRowSize();
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
			changeFindPageHQLInfo(request, hqlInfo);
			Page page = getServiceImp(request).findPage(hqlInfo);
			request.setAttribute("queryPage", page);
			UserOperHelper.logFindAll(page.getList(),
					getServiceImp(request).getModelName());
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-list", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("personalList", mapping, form, request,
					response);
		}
	}
	
	/**
	 * 通过当前登陆用户id解析当前登陆用户的机构、部门、岗位
	 * 
	 * @param userId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private String getAuthEditIds(String userId) {
		StringBuffer sb = new StringBuffer("");
		ISysOrgPersonService sysOrgPersonService = (ISysOrgPersonService) SpringBeanUtil
				.getBean("sysOrgPersonService");
		SysOrgPerson person;
		try {
			person = (SysOrgPerson) sysOrgPersonService
					.findByPrimaryKey(userId);
			if (person != null) {
				List<SysOrgPost> postsList = person.getFdPosts();
				for (SysOrgPost post : postsList) {
					sb.append("'").append(post.getFdId()).append("',");
				}
				String[] parentIds = person.getFdHierarchyId().split("x");
				for (String parentId : parentIds) {
					if (StringUtil.isNotNull(parentId)) {
						sb.append("'").append(parentId).append("',");
					}
				}
				sb.append("'").append(userId).append("'");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request,
										 HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);

		StringBuffer whereBlock = new StringBuffer(" 1=1 ");
		///String fdTempS = request.getParameter("fdTempS");
		String fdNameS = request.getParameter("fdNameS");

		String departmentId = request.getParameter("departmentId");// 部门ID
		String mydoc = request.getParameter("mydoc");
		String fdDocType = request.getParameter("docType");
//		if (StringUtil.isNotNull(fdTempS)) {
//			whereBlock
//					.append(" and (kmSignatureMain.fdTemp.fdName like :fdTempS) ");
//			hqlInfo.setParameter("fdTempS", (new StringBuilder("%")).append(
//					fdTempS).append("%").toString());
//			request.setAttribute("fdTempS", fdTempS);
//		}
		if (StringUtil.isNotNull(fdNameS)) {
			whereBlock
					.append(" and (kmSignatureMain.fdMarkName like :fdNameS) ");
			hqlInfo.setParameter("fdNameS", (new StringBuilder("%")).append(
					fdNameS).append("%").toString());
			request.setAttribute("fdNameS", fdNameS);
		}
		if (StringUtil.isNotNull(mydoc)) {
			if ("mydoc".equals(mydoc)) {
				whereBlock
						.append(" and (kmSignatureMain.docCreator.fdId=:creatorId) ");
				hqlInfo.setParameter("creatorId", UserUtil.getUser().getFdId());
			} else if ("authorize".equals(mydoc)
					&& !UserUtil.getKMSSUser().isAdmin()) {
				String sql = "select s.fd_id from km_signature_main s,km_signature_users su where s.fd_id=su.fd_signature_id and su.fd_org_id in ("
						+ getAuthEditIds(UserUtil.getKMSSUser().getUserId())
						+ ")";
				IKmSignatureMainService signatureService = (IKmSignatureMainService) SpringBeanUtil
						.getBean("kmSignatureMainService");
				List<String> list = signatureService.getBaseDao().getHibernateSession().createNativeQuery(sql).list();
				StringBuffer wherebuffer = new StringBuffer(" ('' ");
				for (int i = 0; i < list.size(); i++) {
					wherebuffer.append(",'" + list.get(i).toString() + "'");
					// mMarkList += list.get(i).toString() + "\r\n";
				}
				wherebuffer.append(" )");
				whereBlock.append(" and " + StringUtil.linkString(whereBlock.toString(), " and ",
						"kmSignatureMain.fdId in " + wherebuffer.toString()));
			}
		}
		if (StringUtil.isNotNull(departmentId)) {// 按部门查看
			whereBlock
					.append(" and (kmSignatureMain.docCreator.fdHierarchyId like :departmentId) ");
			hqlInfo.setParameter("departmentId", "%" + departmentId + "%");
		}
		if ("1".equals(fdDocType) || "2".equals(fdDocType)) {// 按签章类型查看
			whereBlock.append(" and (kmSignatureMain.fdDocType=:fdDocType) ");
			hqlInfo.setParameter("fdDocType", Long.parseLong(fdDocType));
		}
		hqlInfo.setWhereBlock(whereBlock.toString());
	}

	@Override
	protected String getFindPageWhereBlock(HttpServletRequest request)
			throws Exception {
		String departmentId = request.getParameter("departmentId");// 部门ID
		String mydoc = request.getParameter("mydoc");
		String fdDocType = request.getParameter("docType");
		String whereBlock = " 1=1 ";

		if (StringUtil.isNotNull(mydoc)) {
			whereBlock += " and kmSignatureMain.docCreator.fdId='"
					+ UserUtil.getUser().getFdId() + "'";

		}
		if (StringUtil.isNotNull(departmentId)) {// 按部门查看
			whereBlock += " and kmSignatureMain.docCreator.fdHierarchyId like '%"
					+ departmentId + "%'";
		}
		if ("1".equals(fdDocType) || "2".equals(fdDocType)) {// 按签章类型查看
			whereBlock += " and kmSignatureMain.fdDocType = "
					+ Long.parseLong(fdDocType);
		}
		return whereBlock;
	}

	public ActionForward showSig(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return getActionForward("showSig", mapping, form, request, response);
	}

	/**
	 * 流程审批意见盖章时确认密码操作
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward confirmSignature(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		JSONObject json = null;
		try {
			json = new JSONObject();
			String flagx = "0";
			//Boolean isFreeSign = false;
			//json.put("flag", "0");
			//json.put("isFreeSign", false);			
			String fdMainId = request.getParameter("fdMainId");
			String autoSignature = request.getParameter("autoSignature");
			String confirmPassword = request.getParameter("confirmPassword");
			if (StringUtil.isNotNull(confirmPassword)) {
				confirmPassword = kmSignaturePasswordEncoder.encodePassword(confirmPassword);// md5加密算法
			}
			
			//新建流程的时候自动签名：前提是开签章参数配置开启了自动签名、并且设置了默认的个人签名
			if ("true".equals(autoSignature)){
				KmSignatureConfig signatureConfig = new KmSignatureConfig();
				if ("true".equals(signatureConfig.getFdIsAutoSign())){//如果 开启自动签名 则
					List lst = getKmSignatureMainService().getAutoSignature(1);
					if (lst!=null && lst.size()>0){
						Object[] object = (Object[])lst.get(0);
						if(UserOperHelper.allowLogOper("confirmSignature", getServiceImp(request).getModelName())){
							UserOperContentHelper.putFind(object[0].toString(), object[1].toString(), getServiceImp(request).getModelName());
						}
						json.put("attId", object[2].toString());
						json.put("fdMainId", object[0].toString());
						flagx = "1";
						//json.put("flag", "1");	
					}
				}			
			}else if (StringUtil.isNotNull(fdMainId) ) {
				KmSignatureMain signature = (KmSignatureMain) getKmSignatureMainService().findByPrimaryKey(fdMainId);
				if(UserOperHelper.allowLogOper("confirmSignature", getServiceImp(request).getModelName())){
					UserOperContentHelper.putFind(signature.getFdId(), signature.getFdMarkName(), getServiceImp(request).getModelName());
				}
				Boolean flag = false;
				if (fdMainId.equals(signature.getFdId())){
					if (signature.getFdIsFreeSign()){//如果是免签密签名，则直接读取签名图片
						flag = true;
						json.put("isFreeSign", true);
					}else if (StringUtil.isNotNull(confirmPassword)){//如果密码不空，且输入密码正确则读取签名图片
						// 有的地方密码没有加特殊符号，需要兼容
						String password = "\u0000" + confirmPassword;
						if (confirmPassword.equals(signature.getFdPassword())
								|| password.equals(signature.getFdPassword())){
							flag = true;
						}
					}
					if (flag){//读取签章图片
						List<SysAttMain> list = ((ISysAttMainCoreInnerService) SpringBeanUtil
								.getBean("sysAttMainService"))
								.findByModelKey(
										"com.landray.kmss.km.signature.model.KmSignatureMain",
										signature.getFdId(), "sigPic");
						SysAttMain att = (SysAttMain) list.get(0);
						json.put("attId", att.getFdId());
						json.put("fdMainId", fdMainId);
						flagx = "1";
						//json.put("flag", "1");
					}
				} 
			} 
			json.put("flag", flagx);
			request.setAttribute("lui-source", json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		TimeCounter.logCurrentTime("Action-getPerInfo", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_RETURN).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("lui-source", mapping, form, request,
					response);
		}
	}

	@SuppressWarnings("unchecked")
	public ActionForward submitSignature(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		JSONObject json = null;
		try {
			json = new JSONObject();
			String fdAttIds = request.getParameter("fdAttIds");
			String fdKey = request.getParameter("fdKey");
			String fdModelId = request.getParameter("fdModelId");
			String fdModelName = request.getParameter("fdModelName");
			String[] fdAttMainIds = fdAttIds.split(";");
			ISysAttMainCoreInnerService coreInnerService = ((ISysAttMainCoreInnerService) SpringBeanUtil
					.getBean("sysAttMainService"));
			List<SysAttMain> listAtt = coreInnerService
					.findByPrimaryKeys(fdAttMainIds);
			// #163912 修复 签名出现多次重复
			if (StringUtil.isNotNull(fdKey) && fdKey.endsWith("_qz")) {
				// 删除已产生签名
				deleteAtt(fdKey, fdModelId);
			}
			List<SysAttMain> list1 = new ArrayList<SysAttMain>();
			for (int i = 0; i < listAtt.size(); i++) {
				SysAttMain sysAttMain = (SysAttMain) listAtt.get(i);
				SysAttMain sysAttMain1 = coreInnerService.clone(sysAttMain);
				sysAttMain1.setFdKey(fdKey);
				sysAttMain1.setFdModelId(fdModelId);
				sysAttMain1.setFdModelName(fdModelName);
				list1.add(sysAttMain1);
			}
			coreInnerService.update(list1);
			json.put("flag", true);
			request.setAttribute("lui-source", json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		TimeCounter.logCurrentTime("Action-getPerInfo", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_RETURN).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("lui-source", mapping, form, request,
					response);
		}
	}

	private void deleteAtt(String fdKey, String fdModelId) throws Exception {
		ISysAttMainCoreInnerService coreInnerService = ((ISysAttMainCoreInnerService) SpringBeanUtil
				.getBean("sysAttMainService"));
		String hql = "delete from SysAttMain sysAttMain where sysAttMain.fdModelId=:fdModelId and sysAttMain.fdKey=:fdKey";
		Query query = coreInnerService.getBaseDao().getHibernateSession().createQuery(hql);
		query.setString("fdModelId", fdModelId);
		query.setString("fdKey", fdKey);
		query.executeUpdate();
	}

	/**
	 * 重置密码
	 * 
	 * @author weiby
	 */
	public ActionForward chgPwd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		return mapping.findForward("chgPwd");
	}

	/**
	 * 保存重置密码
	 * 
	 * @author weiby
	 */
	public ActionForward savePwd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		KmssMessages messages = new KmssMessages();
		try {
			String pwd = request.getParameter("fdNewPassword");
			if (StringUtil.isNull(pwd)) {
                throw new KmssException(new KmssMessage("errors.required",
                        new KmssMessage("km-signature:signature.newPassword")));
            }
			String id = request.getParameter("fdId");
			getKmSignatureMainService().savePassword(id, pwd,
					new RequestContext(request));
		} catch (Exception e) {
			messages.addError(e);
		}
		KmssReturnPage rtnPage = KmssReturnPage.getInstance(request);
		rtnPage.addMessages(messages).setOperationKey(
				"km-signature:signature.changePassword").save(request);
		if (messages.hasError()) {
			return mapping.findForward("chgPwd");
		} else {
			rtnPage.addButton(KmssReturnPage.BUTTON_CLOSE);
			return mapping.findForward("success");
		}
	}

	@SuppressWarnings("unchecked")
	public ActionForward docThumb(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-docThumb", true, getClass());
		KmssMessages messages = new KmssMessages();
		String imgAttUrl = "";
		String fdId = request.getParameter("fdId");
		String modelName = "com.landray.kmss.km.signature.model.KmSignatureMain";
		try {
			if (StringUtil.isNotNull(fdId)) {
				SysAttMain imgAttMain = null;
				ISysAttMainCoreInnerService sysAttMainCoreInnerService = (ISysAttMainCoreInnerService) SpringBeanUtil
						.getBean("sysAttMainService");
				List<SysAttMain> attMainList = sysAttMainCoreInnerService
						.findByModelKey(modelName, fdId, "sigPic");
				// 如果上传了封面图片
				if (attMainList.size() > 0) {
					imgAttMain = attMainList.get(0);
					imgAttUrl = "/sys/attachment/sys_att_main/sysAttMain.do?method=view&picthumb=big&filekey=sigPic&fdId="
							+ imgAttMain.getFdId();
				} else {
					List<SysAttMain> attachments = sysAttMainCoreInnerService
							.findByModelKey(modelName, fdId, "attachment");
					// 如果有上传附件
					if (attachments.size() > 0) {
						SysAttMain attmain = attachments.get(0);
						imgAttUrl = BlobUtil.getThumbUrlByAttMain(attmain);
					}
				}
			}
			if (StringUtil.isNull(imgAttUrl)) {
				String style = "default";
				String img = "default.png";
				imgAttUrl = "/resource/style/" + style + "/attachment/" + img;
			}
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-docThumb", false, getClass());
		ActionRedirect redirect = new ActionRedirect(imgAttUrl);
		return redirect;
	}

	/**
	 * 置为无效
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward invalidated(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-invalidated", true, getClass());
		KmssMessages messages = new KmssMessages();
		String fdId = request.getParameter("fdId");
		try {
			if (!"GET".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			if (StringUtil.isNull(fdId)) {
                messages.addError(new NoRecordException());
            } else {
                getKmSignatureMainService().updateInvalidated(fdId,
                        new RequestContext(request));
            }
		} catch (Exception e) {
			messages.addError(e);
		}

		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_CLOSE).save(request);
		TimeCounter.logCurrentTime("Action-invalidated", false, getClass());
		if (messages.hasError()) {
            return getActionForward("failure", mapping, form, request, response);
        } else {
            return getActionForward("success", mapping, form, request, response);
        }
	}

	/**
	 * 批量置为无效
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward invalidatedAll(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-invalidated", true, getClass());
		KmssMessages messages = new KmssMessages();
		String id = request.getParameter("List_Selected");
		String[] ids = id.split(";");
		try {
			if (ids != null) {
				getKmSignatureMainService().updateInvalidated(ids,
						new RequestContext(request));
			}
		} catch (Exception e) {
			messages.addError(e);
		}
		// KmssReturnPage.getInstance(request).addMessages(messages).addButton(
		// KmssReturnPage.BUTTON_RETURN).save(request);
		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_CLOSE).save(request);
		TimeCounter.logCurrentTime("Action-invalidated", false, getClass());
		if (messages.hasError()) {
            return getActionForward("failure", mapping, form, request, response);
        } else {
            return getActionForward("success", mapping, form, request, response);
        }
	}
	
	public ActionForward validatedAll(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-invalidated", true, getClass());
		KmssMessages messages = new KmssMessages();
		String[] ids = request.getParameterValues("List_Selected");
		try {
			if (ids != null) {
				getKmSignatureMainService().updateValidated(ids,
						new RequestContext(request));
			}
		} catch (Exception e) {
			messages.addError(e);
		}
		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_CLOSE).save(request);
		TimeCounter.logCurrentTime("Action-invalidated", false, getClass());
		if (messages.hasError()) {
            return getActionForward("failure", mapping, form, request, response);
        } else {
            return getActionForward("success", mapping, form, request, response);
        }
	}
	
	//设置默认签章
	public ActionForward settingDefaultSignature(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-settingDefaultSignature", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String fdId = request.getParameter("fdId");
			if (StringUtil.isNull(fdId)){
				messages.addError(new NoRecordException());
			}else{
				KmSignatureMain kmSignatureMain = (KmSignatureMain) getKmSignatureMainService().findByPrimaryKey(fdId);
				getKmSignatureMainService().updateDefault(kmSignatureMain,true);
			}
		} catch (Exception e) {
			messages.addError(e);
		}
		
		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_CLOSE).save(request);
		TimeCounter.logCurrentTime("Action-invalidated", false, getClass());
		if (messages.hasError()) {
            return getActionForward("failure", mapping, form, request, response);
        } else {
            return getActionForward("success", mapping, form, request, response);
        }
	}

	//移动端查询签章列表
	public ActionForward getSignatureList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONArray array = new JSONArray();
		JSONObject object = null;
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("fdId,fdMarkName,fdIsFreeSign,fdIsDefault");
		hqlInfo.setWhereBlock(
				"(kmSignatureMain.fdIsAvailable = :fdIsAvailable or kmSignatureMain.fdIsAvailable is null) and (kmSignatureMain.fdUsers.fdId in (:id))");
		hqlInfo.setParameter("fdIsAvailable", Boolean.TRUE);
		hqlInfo.setParameter("id", UserUtil.getKMSSUser().getUserAuthInfo().getAuthOrgIds());
		List<?> resultList = getServiceImp(request).findValue(hqlInfo);
		//是否开启自动签章
		KmSignatureConfig signatureConfig = new KmSignatureConfig();
		String isAutoSign = signatureConfig.getFdIsAutoSign();
		if (null != resultList && resultList.size() > 0) {
			for (int i = 0; i < resultList.size(); i++) {
				object = new JSONObject();
				Object[] infos = (Object[]) resultList.get(i);
				object.put("id", infos[0]);
				object.put("name", infos[1]);
				object.put("fdIsFreeSign", infos[2]);
				object.put("fdIsDefault", "true".equals(isAutoSign) ? infos[3] : false);
				array.add(object);
			}
		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(array.toString());
		response.getWriter().flush();
		response.getWriter().close();
		return null;
	}

}
