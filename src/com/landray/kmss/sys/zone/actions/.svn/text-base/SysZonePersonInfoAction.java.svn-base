/**
 * 
 */
package com.landray.kmss.sys.zone.actions;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.KmssException;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.forms.IAttachmentForm;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.sys.attachment.util.SysAttConstant;
import com.landray.kmss.sys.authentication.interfaces.EKPValidateService;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.sys.fans.service.ISysFansMainService;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.mobile.util.MobileUtil;
import com.landray.kmss.sys.organization.forms.SysOrgPersonForm;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.sys.profile.util.ProfileMenuUtil;
import com.landray.kmss.sys.zone.constant.SysZoneConstant;
import com.landray.kmss.sys.zone.context.ImageContext;
import com.landray.kmss.sys.zone.forms.SysZonePersonInfoForm;
import com.landray.kmss.sys.zone.model.SysZoneNavLink;
import com.landray.kmss.sys.zone.model.SysZoneNavigation;
import com.landray.kmss.sys.zone.model.SysZonePersonData;
import com.landray.kmss.sys.zone.model.SysZonePersonInfo;
import com.landray.kmss.sys.zone.service.*;
import com.landray.kmss.sys.zone.util.SysZoneConfigUtil;
import com.landray.kmss.sys.zone.util.SysZonePrivateUtil;
import com.landray.kmss.util.*;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;
import edu.emory.mathcs.backport.java.util.Arrays;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.*;
import java.util.Map.Entry;

/**
 * @author 傅游翔
 */
public class SysZonePersonInfoAction extends ExtendAction {
	private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(SysZonePersonInfoAction.class);
	protected ISysZonePersonInfoService sysZonePersonInfoService;

	protected ISysOrgCoreService sysOrgCoreService;

	private String fileLimitType = "1";
	
	private String disabledFileType = SysAttConstant.DISABLED_FILE_TYPE;

	@Override
	protected ISysZonePersonInfoService getServiceImp(HttpServletRequest request) {
		if (sysZonePersonInfoService == null) {
			sysZonePersonInfoService = (ISysZonePersonInfoService) getBean("sysZonePersonInfoService");
		}
		return sysZonePersonInfoService;
	}


	protected ISysOrgCoreService getSysOrgCoreServiceImp(
			HttpServletRequest request) {
		if (sysOrgCoreService == null) {
			sysOrgCoreService = (ISysOrgCoreService) getBean("sysOrgCoreService");
		}
		return sysOrgCoreService;
	}

	protected ISysOrgPersonService sysOrgPersonService;

	protected ISysOrgPersonService getSysOrgPersonService() {
		if (sysOrgPersonService == null) {
			sysOrgPersonService = (ISysOrgPersonService) getBean("sysOrgPersonService");
		}
		return sysOrgPersonService;
	}

	protected ISysZoneImageService sysZoneImageService;  

	public ISysZoneImageService getSysZoneImageService() {
		if (sysZoneImageService == null) {
			sysZoneImageService = (ISysZoneImageService) getBean("sysZoneImageService");
		}
		return sysZoneImageService;
	}

	protected ISysAttMainCoreInnerService sysAttMainService;

	public ISysAttMainCoreInnerService getSysAttMainService() {
		if (sysAttMainService == null) {
			sysAttMainService = (ISysAttMainCoreInnerService) this.getBean("sysAttMainService");
		}
		return sysAttMainService;
	}

	protected ISysZonePageTemplateService sysZonePageTemplateService;

	public ISysZonePageTemplateService getSysZonePageTemplateService() {
		if (null == sysZonePageTemplateService) {
			sysZonePageTemplateService = (ISysZonePageTemplateService) getBean("sysZonePageTemplateService");
		}
		return sysZonePageTemplateService;
	}
	
	protected ISysFansMainService  sysFansMainService ;

	public ISysFansMainService getSysFansMainService() {
		if (null == sysFansMainService) {
			sysFansMainService = (ISysFansMainService) getBean("sysFansMainService");
		}
		return sysFansMainService;
	}

	@Override
	public ActionForward update(ActionMapping mapping, ActionForm form,
								HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-update", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			SysZonePersonInfoForm sysZonePersonInfoForm = (SysZonePersonInfoForm) form;
			String fdId = UserUtil.getUser().getFdId();
			sysZonePersonInfoForm.setFdId(fdId);
			if (getServiceImp(request).findByPrimaryKey(fdId, null, true) != null) {
				getServiceImp(request).update(sysZonePersonInfoForm,
						new RequestContext(request));
			} else {
				getServiceImp(request).add(sysZonePersonInfoForm,
						new RequestContext(request));
			}
			SysOrgPerson person = (SysOrgPerson) getSysOrgCoreServiceImp(
					request).findByPrimaryKey(fdId, SysOrgPerson.class);
			person.setFdSex(sysZonePersonInfoForm.getFdSex());
			person.setFdWorkPhone(sysZonePersonInfoForm.getFdCompanyPhone());
			person.setFdMobileNo(sysZonePersonInfoForm.getMobilPhone());
			person.setFdDefaultLang(sysZonePersonInfoForm.getFdDefaultLang());
			ISysOrgPersonService sysOrgPersonService = (ISysOrgPersonService) SpringBeanUtil.getBean("sysOrgPersonService");
			sysOrgPersonService.update(person);
		} catch (Exception e) {
			log.error("保存个人信息出错：", e);
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-update", false, getClass());
		PrintWriter out = response.getWriter();
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(
					request);
			out.print("false");
		} else {
			out.print("true");
		}
		return null;
	}

	private SysZonePersonInfo getSysZonePersonInfo(HttpServletRequest request)
			throws Exception {
		String fdId = UserUtil.getUser().getFdId();
		SysZonePersonInfo model = (SysZonePersonInfo) getServiceImp(request).findByPrimaryKey(
				fdId, null, true);
		if (model == null) {
			model = new SysZonePersonInfo();
			SysOrgPerson person = (SysOrgPerson) getSysOrgCoreServiceImp(
					request).findByPrimaryKey(fdId, SysOrgPerson.class);
			model.setPerson(person);
			model.setFdId(fdId);
		}
		return model;
	}

	public ActionForward getSysZoneSignature(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			SysZonePersonInfo sysZonePersonInfo = getSysZonePersonInfo(request);
			JSONObject resultObj = new JSONObject();
			resultObj.put("signature", sysZonePersonInfo.getFdSignature());
			request.setAttribute("lui-source", resultObj);
		} catch (Exception e) {
			messages.addError(e);
		}
		if(!messages.hasError()) {
			return getActionForward("lui-source", mapping, form, request, response);
		}
		return null;
	}	
	
	@Override
	@SuppressWarnings("unchecked")
	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			String fdId = UserUtil.getUser().getFdId();
			SysZonePersonInfo sysZonePersonInfo = getSysZonePersonInfo(request);
			form.reset(mapping, request);
			SysZonePersonInfoForm sysZonePersonInfoForm = (SysZonePersonInfoForm) getServiceImp(
					request).convertModelToForm((IExtendForm) form,
					sysZonePersonInfo, new RequestContext(request));

			// 将简历的附件信息去除，不使用附件机制默认的
			((SysZonePersonInfoForm) form).setAutoHashMap(new AutoHashMap(
					AttachmentDetailsForm.class));
			// 得到人员的简历信息
			List<SysAttMain> personResume = (List<SysAttMain>) getSysAttMainService().findByModelKey(
					SysZonePersonInfo.class.getName(), fdId, "personResume");
			request.setAttribute("personResume", personResume.isEmpty() ? null
					: personResume.get(0));
			
			request.setAttribute("sysOrgPerson", sysZonePersonInfo.getPerson());
			request.setAttribute("sysOrgPersonForm", createPersonform(sysZonePersonInfo.getPerson()));
			request.setAttribute("sysZonePersonInfoForm", sysZonePersonInfoForm);
		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(
					KmssReturnPage.BUTTON_CLOSE).save(request);
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("edit");
		}
	}

	public ActionForward searchPerson(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-searchPerson", true, getClass());
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
			String tagNames = request.getParameter("tagNames");
			String searchValue = request.getParameter("searchValue");
			CriteriaValue cv = new CriteriaValue(request);
			if(StringUtil.isNull(tagNames)) {
				Iterator<Entry<String, String[]>> iterator = cv.entrySet().iterator();
				while (iterator.hasNext()) { 
					Entry<String, String[]> e = iterator.next();
					if(e.getKey().startsWith("tag")) {
						for(String str : e.getValue()) {
							tagNames = StringUtil.linkString(tagNames,  " ", str);
						}
					}
				}
			}
			if(StringUtil.isNotNull(tagNames)) {
				tagNames = java.net.URLDecoder.decode(tagNames, "UTF-8");
			}
			if(StringUtil.isNotNull(searchValue)) {
				searchValue = java.net.URLDecoder.decode(searchValue, "UTF-8");
			}
			Page page = getServiceImp(request).queryPersonInfo(pageno, rowsize, 
					searchValue, tagNames, new RequestContext(request));
			request.setAttribute("tagNames", tagNames);
			request.setAttribute("queryPage", page);
		} catch (Exception e) {
			log.error("员工搜索错误", e);
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-searchPerson", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(
					KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("searchDate", mapping, form, request,
					response);
		}
	}

	/* 跳到搜索页面 */
	public ActionForward toSearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-toSearch", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String fdTags = request.getParameter("fdTags");
			request.setAttribute("tagNames",fdTags);// 标签的Ids，ekp11的标签Url要用到
			String searchValue = request.getParameter("searchValue");
			request.setAttribute("searchValue",searchValue!=null?searchValue.trim():searchValue);// 搜索条件
			request.setAttribute("fdTags", fdTags);// 页面选择的标签Json数据，把选择的标签显示在页面上
		} catch (Exception e) {
			log.error("员工搜索错误", e);
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-toSearch", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(
					KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("toSearch", mapping, form, request,
					response);
		}
	}

	// 获取所有人员标签
	public ActionForward getTags(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-getTags", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			List tagList = getServiceImp(request).getTagList();
			request.setAttribute("tagList", tagList);
		} catch (Exception e) {
			log.error("获取员工标签错误", e);
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-getTags", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(
					KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("tagsList", mapping, form, request,
					response);
		}
	}

	/* 新员工 */
	public ActionForward newOrgPerson(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-newOrgPerson", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			Page page = getServiceImp(request).getNewPersonInfo(request);
			request.setAttribute("queryPage", page);
		} catch (Exception e) {
			log.error("搜索专家错误", e);
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-newOrgPerson", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(
					KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("searchExpert", mapping, form, request,
					response);
		}
	}

	/**
	 * 根据http请求，获取model，将model转化为form并返回。<br>
	 * URL中必须包含fdId参数，该参数为记录id。<br>
	 * 若获取model不成功，则抛出errors.norecord的错误信息。
	 * 
	 * @param form
	 * @param request
	 * @return form对象
	 * @throws Exception
	 */
	@Override
	protected void loadActionForm(ActionMapping mapping, ActionForm form,
								  HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		form.reset(mapping, request);
		String fdId = request.getParameter("fdId");
		if(StringUtil.isNull(fdId)) {
			fdId = UserUtil.getUser().getFdId();
		}
		SysOrgPerson orgPerson = (SysOrgPerson) getSysOrgPersonService().findByPrimaryKey(
				fdId);// 员工组织架构信息
		SysZonePersonInfo zonePerson = (SysZonePersonInfo) getServiceImp(
				request).findByPrimaryKey(fdId, null, true);// 员工黄页信息
		if (null == zonePerson) {
			zonePerson = new SysZonePersonInfo();
			zonePerson.setFdId(fdId);
			zonePerson.setPerson(orgPerson);
		}
		
		//转换过程中组织架构字段会先取缓存 详情可看ModelConvertor_Common
		SysZonePersonInfoForm sysZonePersonForm = (SysZonePersonInfoForm) getServiceImp(
				request).convertModelToForm((IExtendForm) form, zonePerson,
				new RequestContext(request));
		
		ISysFansMainService fansService = 
			(ISysFansMainService)SpringBeanUtil.getBean("sysFansMainService");
		//粉丝数
		int fansNum = fansService.getFollowTotal(sysZonePersonForm.getFdId(),"fans");
		//关注数
		int attentNum = fansService.getFollowTotal(sysZonePersonForm.getFdId(),"attention");
		
		//勋章数
		if(ProfileMenuUtil.moduleExist("/kms/medal")){//判断是否有勋章模块
			IBaseService kmsMedalMainService = (IBaseService) SpringBeanUtil
					.getBean("kmsMedalMainService");
			int medalNum = ((ISysZoneMedalService) kmsMedalMainService).getMedalsSize(sysZonePersonForm.getFdId());
			request.setAttribute("medalNum", medalNum);
			
		}
		
		request.setAttribute("fansNum", fansNum);
		request.setAttribute("attentNum", attentNum);
		
		request.setAttribute("sysZonePersonInfoForm", sysZonePersonForm);
		request.setAttribute("sysOrgPerson", orgPerson);
		request.setAttribute("isSelf", getServiceImp(request).isSelf(fdId));// 带权限
		request.setAttribute("isSelfNoPower",
				getServiceImp(request).isSelfNoPower(fdId));// 不带权限
		
		//==========隐私信息判断==========//
		
		if(!SysZonePrivateUtil.isContactPrivate(fdId)) {
			request.setAttribute("mobilPhone", orgPerson.getFdMobileNo());
			request.setAttribute("email", orgPerson.getFdEmail());
			request.setAttribute("fdCompanyPhone", orgPerson.getFdWorkPhone());
			request.setAttribute("shortNo", orgPerson.getFdShortNo());
		} else {
			request.setAttribute("mobilPhone", ResourceUtil
					.getString("sysZonePerson.undisclosed2", "sys-zone"));
			request.setAttribute("email", ResourceUtil
					.getString("sysZonePerson.undisclosed2", "sys-zone"));
			request.setAttribute("fdCompanyPhone", ResourceUtil
					.getString("sysZonePerson.undisclosed2", "sys-zone"));
			request.setAttribute("shortNo", ResourceUtil
					.getString("sysZonePerson.undisclosed2", "sys-zone"));
		}
		
		if(!SysZonePrivateUtil.isDepInfoPrivate(fdId)) {
			String postNames = "";
			List postList = zonePerson.getPerson().getHbmPosts();
			if (postList.size() > 0) {
				for (int i = 0; i < postList.size(); i++) {
					SysOrgElement post = (SysOrgElement) postList.get(i);
					if (post != null) {
						String fullName = StringUtil.isNotNull(post
								.getFdParentsName()) ? StringUtil
								.linkString(post.getFdParentsName(), ">>", post
										.getFdName()) : post.getFdName();
						if (i == 0) {
							postNames = fullName;
						} else {
							postNames = StringUtil.linkString(postNames, ";",
									fullName);
						}
					}
				}
			}
			request.setAttribute("organization",
					getServiceImp(request).getoOrganization(orgPerson));
			request.setAttribute("post", sysZonePersonForm.getPost());
			request.setAttribute("postNames", postNames);
			request.setAttribute("fdDeptNames", zonePerson.getPerson().getFdParentsName());
			request.setAttribute("dep", orgPerson.getFdParent() != null ? orgPerson.getFdParent().getFdName() : "");
		}
		//==========隐私信息判断结束==========//
		
		boolean self = UserUtil.getUser().getFdId().equals(orgPerson.getFdId());
		String taKey = "my";
		if(!self) {
			if("F".equals(orgPerson.getFdSex())) {
				taKey = "her";
			} else if("M".equals(orgPerson.getFdSex())){
				taKey = "his";
			} else {
				taKey = "ta";
			}
		}
		String text = ResourceUtil.getString("sys-zone:zone.ta.text." + taKey);

		request.setAttribute("zone_TA_text", text);
		request.setAttribute("zone_TA", taKey);


		
		// 获取员工的简历信息
		Object resumeForm = ((IAttachmentForm) sysZonePersonForm).getAttachmentForms().get(
				"personResume");
		if (resumeForm != null) {
			request.setAttribute("personResumeId",
					(((AttachmentDetailsForm) resumeForm).getAttachmentIds()));
		}
		
		setZoneNavLinkInfo(request);
		
		
		
		Map info = getServiceImp(request).getPersonDatas(fdId);
		request.setAttribute("personDatas", info.get("datas"));
	}

	private void setZoneNavLinkInfo(HttpServletRequest request)
			throws Exception {
		String type = SysZoneConfigUtil.TYPE_PC_KEY;
		if (MobileUtil.isMobile(request)) {
			type = SysZoneConfigUtil.TYPE_MOBILE_KEY;
		}
		List<SysZoneNavigation> sysZoneNavigationList = getServiceImp(request).getZonePersonNav(
				type);
		
    	List<SysZoneNavLink> navList = new ArrayList();
       	if (!ArrayUtil.isEmpty(sysZoneNavigationList)) {
			 navList = sysZoneNavigationList.get(0).getFdLinks();
		}
       	
       	Boolean flag =false; //讲师模块判断
    	List<SysZoneNavLink> navListLect = new ArrayList();  
    	navListLect.addAll(navList);   //如果直接用原生navList操作，有时候会将页签写进数据库
		if(ProfileMenuUtil.moduleExist("/kms/lecturer")){//有讲师模块
			
			flag = true;

			String fdId = request.getParameter("fdId");
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock("kmsLecturerMain.fdPerson.fdId = :userId");
			hqlInfo.setParameter("userId",fdId);
			IBaseService kmsLecturerMainService = (IBaseService) SpringBeanUtil
				.getBean("kmsLecturerMainService");
			List  lecturerMainList =  kmsLecturerMainService.findList(hqlInfo);
        	Boolean isLecturer = false;
			if(lecturerMainList!=null&&!lecturerMainList.isEmpty()){
				isLecturer = true;
			}
			
		 	if(isLecturer){//该用户为讲师
				//讲师信息
	        	SysZoneNavLink kmsLecturerInfo = new SysZoneNavLink();
	        	kmsLecturerInfo.setFdName(ResourceUtil.getString("kms-lecturer:kmsLecturerInfo"));
	        	String fdUrl = "/kms/lecturer/kms_lecturer_navigation/kmsLecturerNavigation_zone_info.jsp";
	        	kmsLecturerInfo.setFdUrl(fdUrl);
	        	kmsLecturerInfo.setFdTarget("_self");
	        	
            	SysZoneNavLink kmsLecturerTrain = new SysZoneNavLink();
            	SysZoneNavLink kmsLecturerCourse = new SysZoneNavLink();

	        	if(ProfileMenuUtil.moduleExist("/kms/train")){//有线下培训模块
	            	//授课记录
	            	kmsLecturerTrain.setFdName(ResourceUtil.getString("kms-lecturer:kmsLecturerTrain"));
	            	fdUrl = "/kms/lecturer/kms_lecturer_navigation/kmsLecturerNavigation_zone_train.jsp";
	            	kmsLecturerTrain.setFdUrl(fdUrl);
	            	kmsLecturerTrain.setFdTarget("_self");
	        	}

	        	if(ProfileMenuUtil.moduleExist("/kms/learn")){//有在线学习模块
	              	//课件
	            	kmsLecturerCourse.setFdName(ResourceUtil.getString("kms-lecturer:kmsLecturerCourse"));
	            	fdUrl = "/kms/lecturer/kms_lecturer_navigation/kmsLecturerNavigation_zone_course.jsp";
	            	kmsLecturerCourse.setFdUrl(fdUrl);
	            	kmsLecturerCourse.setFdTarget("_self");
	        	}
	        	
	   			String fromPage =  request.getParameter("fromPage");
				if(StringUtil.isNotNull(fromPage)&& "lecturer".equals(fromPage)){//从lecturer页面跳转
					//System.out.println(navLinks.size());
					navListLect.clear();
		        	int num = 0;
		        	navListLect.add(num,kmsLecturerInfo);num++;
		        	if(ProfileMenuUtil.moduleExist("/kms/train")){//有线下培训模块
		        		navListLect.add(num,kmsLecturerTrain);num++;
					}
		        	if(ProfileMenuUtil.moduleExist("/kms/learn")){//有在线学习模块
		        		navListLect.add(num,kmsLecturerCourse);

		        	}
					//request.setAttribute("fromPage", fromPage);

					//System.out.println(navLinks.size());
				}else{//员工黄页（sys/zone）等其他页面
					navListLect.add(navListLect.size(),kmsLecturerInfo);
		        	if(ProfileMenuUtil.moduleExist("/kms/train")){//有线下培训模块
		        		navListLect.add(navListLect.size(),kmsLecturerTrain);		
		        	}
		        	if(ProfileMenuUtil.moduleExist("/kms/learn")){//有在线学习模块
		        		navListLect.add(navListLect.size(),kmsLecturerCourse);		
		        	}

				}

		 	}
		}
		if(flag){
			request.setAttribute("navLinks", navListLect);
			request.setAttribute("navLinksSize", navListLect.size());	
		}else{
			request.setAttribute("navLinks", navList);
			request.setAttribute("navLinksSize", navList.size());
		}
	
		
	}

	public ActionForward updateTag(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-updateTag", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			SysZonePersonInfoForm rtnForm = (SysZonePersonInfoForm) form;
			rtnForm.setFdId(rtnForm.getSysTagMainForm().getFdModelId());
			IBaseModel model = getServiceImp(request).convertFormToModel(
					rtnForm, null, new RequestContext(request));
			getServiceImp(request).updatePersonTags(model);
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-updateTag", false,
				getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(
					request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("success", mapping, form, request, response);
		}
	}

	public ActionForward saveOrgLang(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-saveOrgLang", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}
			JSONObject json =  getServiceImp(request).updateOrgLang(request);
			request.setAttribute("lui-source", json);
		} catch (Exception e) {
			e.printStackTrace();
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-saveOrgLang", false,
				getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(
					request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("lui-source", mapping, form, request, response);
		}
	}
	
	
	public ActionForward saveOrgPersonInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-saveOrgPersonInfo", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}
			getServiceImp(request)
				.updatePersonInfo(new RequestContext(request));
		} catch (Exception e) {
			log.error("保存个人信息出错：", e);
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-saveOrgPersonInfo", false,
				getClass());
		PrintWriter out = response.getWriter();
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(
					request);
			out.print("false");
		} else {
			out.print("true");
		}
		return null;
	}

	public ActionForward saveFdSignature(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-saveFdSignature", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}
			String fdSignature = request.getParameter("fdSignatureArea");
//			String fdId = request.getParameter("fdId");
			String fdId = UserUtil.getUser().getFdId();
			fdSignature = java.net.URLDecoder.decode(fdSignature, "UTF-8");
			getServiceImp(request).updateFdSignature(fdId, fdSignature);
		} catch (Exception e) {
			log.error("保存个人签名出错：", e);
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-saveFdSignature", false, getClass());
		PrintWriter out = response.getWriter();
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(
					request);
			out.print("false");
		} else {
			out.print("true");
		}
		return null;
	}

	/**
	 * 加载个人资料目录
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward loadPersonData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setCharacterEncoding(SysZoneConstant.COMMON_ENCODING);
		net.sf.json.JSONObject  rtnInfo = new net.sf.json.JSONObject();
		
		ISysZonePersonInfoService personService = this.getServiceImp(request);
		String fdId = request.getParameter("fdId");
		// 获取个人资料目录
		Map<String, Object> map = personService.getPersonDatas(fdId);
		List<SysZonePersonData> personDatas = (List<SysZonePersonData>) map.get("datas");
		request.setAttribute("personDatas", personDatas);
		// 构造json数据
		if (ArrayUtil.isEmpty(personDatas)) {
			rtnInfo.put("datas", personDatas);
			rtnInfo.put("isSelfData", map.get("isSelfData"));
			response.getWriter().print(rtnInfo);
			return null;
		}
		int dataSize = personDatas.size();
		JSONArray arr = new JSONArray();
		for (int i = 0; i < dataSize; i++) {
			SysZonePersonData data = personDatas.get(i);
			net.sf.json.JSONObject jsonObject = new net.sf.json.JSONObject();
			jsonObject.put("fdDataCateId", data.getFdDataCate().getFdId());
			jsonObject.put("fdName", data.getFdName());
			jsonObject.put("fdId", data.getFdId());
			jsonObject.put("docContent", data.getDocContent());
			jsonObject.put("fdOrder", data.getFdOrder());
			arr.add(jsonObject);
		}
		rtnInfo.put("datas", arr);
		rtnInfo.put("isSelfData", map.get("isSelfData"));
		response.setHeader("content-type", "application/json;charset=utf-8");
		response.getWriter().print(rtnInfo);
		return null;
	}

	
	/**
	 * 修改头像
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward modifyPhoto(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			String personId = UserUtil.getUser().getFdId();
			getServiceImp(request).updateGetPerson(personId);
			request.setAttribute("fdId", personId);
		} catch (Exception e) {
			LOGGER.error("修改头像异常{}", e);
		}
		return mapping.findForward("editPhoto");
	}
	
	public ActionForward modifyOtherPhoto(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String personId = request.getParameter("personId");
			if (StringUtil.isNull(personId)) {
				throw new Exception();
			}
			SysZonePersonInfo person
				= getServiceImp(request).updateGetPerson(personId);
			request.setAttribute("fdId", personId);
			request.setAttribute("name", person.getPerson().getFdName());
		} catch (Exception e) {
			LOGGER.error("修改他人头像异常", e);
		}
		return mapping.findForward("editOtherPhoto");
	}

	/**
	 * 压缩头像
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward zoomImg(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			String attId = request.getParameter("attId");
			ImageContext imageContext = new ImageContext(null, null,
					Integer.parseInt(request.getParameter("ruleWidth")),
					Integer.parseInt(request.getParameter("ruleHeight")), null);
			imageContext = this.getSysZoneImageService().updateZoomImg(attId,
					imageContext);
			JSONObject rtnInfo = new JSONObject();
			rtnInfo.put("attId", imageContext.getAttMains().get(0).getFdId());
			rtnInfo.put("width", imageContext.getWidth());
			rtnInfo.put("height", imageContext.getHeight());
			rtnInfo.put("zoomPath", imageContext.getZoomPath());
			response.setCharacterEncoding(SysZoneConstant.COMMON_ENCODING);
			response.getWriter().print(rtnInfo);
		} catch (Exception e) {
			LOGGER.error("压缩图片异常", e);
		}
		return null;
	}

	/**
	 * 裁剪图片
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward cropImg(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			String attId = request.getParameter("attId");
			int startX = Integer.parseInt(request.getParameter("startX"));
			int startY = Integer.parseInt(request.getParameter("startY"));
			int width = Integer.parseInt(request.getParameter("width"));
			int height = Integer.parseInt(request.getParameter("height"));
			String zoomPath = request.getParameter("zoomPath");
			ImageContext imageContext = new ImageContext(startX, startY, width,
					height, zoomPath);
			JSONObject rtnInfo = this.getSysZoneImageService().updateCropImg(
					attId, imageContext);
			response.setCharacterEncoding(SysZoneConstant.COMMON_ENCODING);
			response.getWriter().print(rtnInfo);
		} catch (Exception e) {
			LOGGER.error("裁剪图片错误", e);
		}
		return null;
	}
	
	/**
	 * 取消裁剪
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward cancelImg(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-cancelImg", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String attId = request.getParameter("attId");
			String zoomPath = request.getParameter("zoomPath");
			this.getSysZoneImageService().updateCancelImg(
					attId, zoomPath);
			request.setAttribute("lui-source", JSONObject.fromObject("{'scuccess' : 'true'}"));
		} catch (Exception e) {
			e.printStackTrace();
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-cancelImg", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(
					KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("lui-source", mapping, form, request, response);
		}
	}
	

	/**
	 * 保存简历
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveResume(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			response.setCharacterEncoding(SysZoneConstant.COMMON_ENCODING);
			String fileId = request.getParameter("fileId");
			String isDel = request.getParameter("isDel");
			//不可下载标记
			String authAttNodownload = request.getParameter("authAttNodownload");
			
			//可下载者
			String authAttDownloadIds = request.getParameter("authAttDownloadIds");
			
			List<String> authIds=new ArrayList<String>();
			if(StringUtil.isNotNull(authAttDownloadIds)){
				 authIds = Arrays.asList(authAttDownloadIds.split(";"));
			}
//			String personId = request.getParameter("personId");
			//#78951 personId由后台获取
			SysOrgPerson user = UserUtil.getUser();
			String personId = user.getFdId();
			
			SysZonePersonInfo sysZonePersonInfo = (SysZonePersonInfo)getServiceImp(request).findByPrimaryKey(personId);
			sysZonePersonInfo.getAuthAttDownloads().clear();
			for(int i=0;i<authIds.size();i++){
				SysOrgPerson person = (SysOrgPerson) getSysOrgCoreServiceImp(
						request).findByPrimaryKey(authIds.get(i), SysOrgPerson.class);
				sysZonePersonInfo.getAuthAttDownloads().add(person);
			}
			sysZonePersonInfo.setAuthAttNodownload("true".equals(authAttNodownload));
			getServiceImp(request).add(sysZonePersonInfo);
			
			String fileName = request.getParameter("fileName");
			JSONObject json = new JSONObject();
			boolean result = true;
			// #56945 附件上传漏洞
			if (StringUtil.isNotNull(fileName)) {
				String _fileType = null;
				if (fileName.indexOf(".") > -1) {
					_fileType = fileName.substring(fileName.lastIndexOf("."));
				}
				if (StringUtil.isNotNull(_fileType)) {
					_fileType = _fileType.toLowerCase();
					String[] files = getDisabledFileType().split("[;；]");					
					if("1".equals(getFileLimitType())){
						Boolean isPass = true;
						for(String f : files){
							if(_fileType.equals(f)){
								isPass = false;
								break;
							}
						}
						if(!isPass){
							result = false;
							json.put("msg", "基于安全考虑，不允许上传该附件！");
						}
					}else if("2".equals(getFileLimitType())){
						

						Boolean isPass = false;
						for(String f : files){
							if(_fileType.equals(f)){
								isPass = true;
								break;
							}
						}
						if(!isPass){
							result = false;
							json.put("msg", "基于安全考虑，不允许上传该附件！");
						}
					}
				}
			}
			if (result && fileName != null) {
				if(StringUtil.isNotNull(fileId)||"0".equals(isDel)){
					this.getServiceImp(request).saveResume(
							personId, fileName, fileId);
				}
				json.put("code", 1);
			} else {
				json.put("code", 0);
			}
			response.getWriter().print(json.toString());
			UserOperHelper.setOperSuccess(true);
		} catch (Exception e) {
			LOGGER.error("保存简历错误", e);
			UserOperHelper.setOperSuccess(false);
		}
		return null;
	}

	private String getFileLimitType() {
		String fileLimit = ResourceUtil.getKmssConfigString("sys.att.fileLimitType");
		if (StringUtil.isNotNull(fileLimit)) {
            fileLimitType = fileLimit;
        }

		return fileLimitType;
	}	
	
	private String getDisabledFileType() {
		String fileLimit = ResourceUtil.getKmssConfigString("sys.att.fileLimitType");
		String disabledfile = ResourceUtil
				.getKmssConfigString("sys.att.disabledFileType");
		if (StringUtil.isNotNull(fileLimit) && disabledfile!=null) {
            disabledFileType = disabledfile.toLowerCase();
        }
		return disabledFileType;
	}
	
	public ActionForward getDatasByTags(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-getDatasByTags", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String spageno = request.getParameter("pageno");
			String srowsize = request.getParameter("rowsize");
			String personId = request.getParameter("personId");
			if (StringUtil.isNull(personId)) {
				throw new Exception("fdId of person is can not null!");
			}
			if (StringUtil.isNull(spageno)) {
				spageno = "0";
			}
			if (StringUtil.isNull(srowsize)) {
				srowsize = SysZoneConstant.TAGS_PERSON_SIZE.toString();
			}
			Map<String, String> parameters = new HashMap<String, String>();
			parameters.put("rowsize", String.valueOf(srowsize));
			parameters.put("pageno", spageno);
			Page page = getServiceImp(request).getPersonInfoByTags(parameters,
					personId);
			if (null == page) {
				page = new Page();
			}
			request.setAttribute("loadImg", true);
			request.setAttribute("queryPage", page);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-getDatasByTags", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(
					KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("lui-failure", mapping, form, request, response);
		} else {
			return getActionForward("data", mapping, form, request, response);
		}
	}

	@Override
	public ActionForward view(ActionMapping mapping, ActionForm form,
							  HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-view", true, getClass());
		KmssMessages messages = new KmssMessages();
		ActionForward forward = null;
		String fdId = request.getParameter("fdId");
		try {
			//SysZoneConfigUtil.loadOherGroupPlugin();
			String userId = UserUtil.getUser().getFdId();
			loadActionForm(mapping, form, request, response);
			SysZonePersonInfo sysZonePersonInfo= (SysZonePersonInfo)getServiceImp(request).findByPrimaryKey(fdId);
			List person= sysZonePersonInfo.getAuthAttDownloads();
			List personIds = getSysOrgCoreServiceImp(request).expandToPersonIds(person);
			if(sysZonePersonInfo.getAuthAttNodownload()){
				request.setAttribute("download",false);
				if(userId.equals(fdId)||UserUtil.checkRole("admin")||UserUtil.checkRole("ROLE_SYSZONE_ADMIN")){
					request.setAttribute("download",true);
				}
			}else{
				if(ArrayUtil.isEmpty(personIds)){
					request.setAttribute("download",true);
				}else{
					if(personIds.contains(UserUtil.getUser().getFdId())||UserUtil.checkRole("admin")||userId.equals(fdId)||UserUtil.checkRole("ROLE_SYSZONE_ADMIN")){
						request.setAttribute("download",true);
					}else{
						request.setAttribute("download",false);
					}
				}
			}
			String type = SysZoneConfigUtil.TYPE_PC_KEY;
			if (MobileUtil.isMobile(request)) {
				type = SysZoneConfigUtil.TYPE_MOBILE_KEY;
			}
			String path = getSysZonePageTemplateService().getTemplateJspPath(type);
			if(ProfileMenuUtil.moduleExist("/kms/lecturer")){//判断是否有讲师模块
				
				HQLInfo hqlInfo = new HQLInfo();
				hqlInfo.setWhereBlock("kmsLecturerMain.fdPerson.fdId = :userId");
				hqlInfo.setParameter("userId",fdId);
				IBaseService kmsLecturerMainService = (IBaseService) SpringBeanUtil
					.getBean("kmsLecturerMainService");
				Object obj =  kmsLecturerMainService.findFirstOne(hqlInfo);
				if(obj!=null){
					request.setAttribute("isLecturer",true);
					String lectId= ((IBaseModel) obj).getFdId();
					request.setAttribute("lecturerId",lectId);
					String lectTitleName = (String)((ISysZoneLecturerService)kmsLecturerMainService).getTitleById(lectId);
					String fdSignature = (String)((ISysZoneLecturerService)kmsLecturerMainService).getSignatureById(lectId);
					String fdIsAvailable = (String) ((ISysZoneLecturerService) kmsLecturerMainService).getAvailableById(lectId);

					request.setAttribute("lectTitleName",lectTitleName);
					request.setAttribute("fdLecturerSignature",fdSignature);
					request.setAttribute("fdLecturerAvailable", fdIsAvailable);

				}else{
					request.setAttribute("isLecturer",false);
				}
			}else{
				request.setAttribute("isLecturer",false);
			}
			if(ProfileMenuUtil.moduleExist("/kms/expert")){//判断是否有专家模块
				HQLInfo hqlInfo = new HQLInfo();
				hqlInfo.setWhereBlock("kmsExpertInfo.fdPerson.fdId = :userId");
				hqlInfo.setParameter("userId",fdId);
				IBaseService kmsExpertInfoService = (IBaseService) SpringBeanUtil
					.getBean("kmsExpertInfoService");
				List  expertList =  kmsExpertInfoService.findList(hqlInfo);
				if(expertList!=null&&!expertList.isEmpty()){
					request.setAttribute("isExpert",true);
				}else{
					request.setAttribute("isExpert",false);
				}
			}else{
				request.setAttribute("isExpert",false);
			}
			forward = new ActionForward(path);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-view", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(
					KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return forward;
		}
	}

	public ActionForward getTeam(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-getTeam", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			JSONArray rtnArray = null;
			String orgId = request.getParameter("orgId");
			if (StringUtil.isNull(orgId)) {
				orgId = UserUtil.getUser().getFdId();
			}
			String type = request.getParameter("type");
			if(StringUtil.isNull(type)) {
				type = "team";
			}
			if("team".equals(type)) {
				String source = request.getParameter("source");
				if ("personal".equalsIgnoreCase(source)) {
					// 默认获取部分数据
					rtnArray = this.getServiceImp(request).updateOfGetTeam(orgId,
							request);
				} else {
					rtnArray = this.getServiceImp(request).updateOfGetTeam(orgId);
				}
			} else if("chain".equals(type)){
				rtnArray =  this.getServiceImp(request).updateOfGetLeaders(orgId);
			}
			request.setAttribute("lui-source", rtnArray);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-getTeam", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(
					KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("lui-failure", mapping, form, request, response);
		} else {
			return getActionForward("lui-source", mapping, form, request,
					response);
		}
	}
	
	public ActionForward listMyTeam(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-getTeam", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			JSONObject result = new JSONObject();
			String orgId = request.getParameter("orgId");
			if (StringUtil.isNull(orgId)) {
				orgId = UserUtil.getUser().getFdId();
			}
			result.put("status", 1);

			JSONArray rtnArray = this.getServiceImp(request).updateOfGetTeam(orgId,
					request);
			result.put("datas", rtnArray);
			Integer totalSize = (Integer) request.getAttribute("totalSize");
			totalSize = totalSize == null ? 0 : totalSize;
			result.put("totalSize", totalSize);
			request.setAttribute("lui-source", result);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-getTeam", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(
					KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("lui-failure", mapping, form, request,
					response);
		} else {
			return getActionForward("lui-source", mapping, form, request,
					response);
		}
	}

	
	public ActionForward listReadLog(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-listReadLog", true, getClass());
		KmssMessages messages = new KmssMessages();
		JSONObject json = new JSONObject();
		try {
			String personId = request.getParameter("personId");
			if (StringUtil.isNull(personId)) {
				throw new KmssException(new KmssMessage(
						"sys-zone:sysZonePerson.readLog.error.userId"));
			}
			String s_rowsize = request.getParameter("rowsize");
			if(StringUtil.isNull(s_rowsize)){
				s_rowsize = "8";
			}
			
			String curUserId = UserUtil.getUser().getFdId();
			//谁看过我
			Page page = this.getServiceImp(request)
							.listReadLog(request, personId, SysZoneConstant.ZONE_VISITOR_OTHER);
			List<Object[]> list = page.getList();
			Set<Map<String,String>> idsSet = new LinkedHashSet<Map<String,String>>();
			for(Object[] obj:list){
				if(obj[0]!=null && obj[1]!=null){
					if(idsSet.size()>=Integer.parseInt(s_rowsize)){
						break;
					}
					if(!curUserId.equals((String)obj[0])){
						Map<String,String> map = new HashMap<String,String>();
						map.put("fdId", (String)obj[0]);
						map.put("fdName", (String)obj[1]);
						idsSet.add(map);
					}
				}
			}
			JSONObject rtnJson = new JSONObject();
			rtnJson.put("visitorPage", idsSet);
			
			
			//我看过谁
			Page page2 = this.getServiceImp(request)
							.listReadLog(request, personId, SysZoneConstant.ZONE_VISITOR_ME);
			List<String> list2 = page2.getList();
			
			Set<String> idsSet2 = new LinkedHashSet<String>();
			for(String fdId:list2){
				if(StringUtil.isNotNull(fdId)){
					if(idsSet2.size()>=Integer.parseInt(s_rowsize)){
						break;
					}
					if(!curUserId.equals(fdId)){
						idsSet2.add(fdId);
					}
				}
			}
			
			List<String> idsList = new ArrayList(idsSet2);
			List<SysOrgPerson> orgList = 
				getSysOrgPersonService().findByPrimaryKeys(
						(String[]) idsList.toArray(new String[idsList.size()]));
			JSONArray visitTo = new JSONArray();
			for(SysOrgPerson person:orgList){
				JSONObject jObject = new JSONObject();
				jObject.accumulate("fdId", person.getFdId());
				jObject.accumulate("fdName", person.getFdName());
				visitTo.add(jObject);
			}
			rtnJson.put("visitTo", visitTo);
			json.put("readLogPage", rtnJson);
			request.setAttribute("lui-source", json);
		} catch (Exception e) {
			messages.addError(e);
			e.printStackTrace();
		}
		TimeCounter.logCurrentTime("Action-listReadLog", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(
					KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("lui-source", mapping, form, request,
					response);
		}
	}
	
	public ActionForward listPersons(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-listPersons", true, getClass());
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
			Page page = getServiceImp(request).obtainPersons(hqlInfo,
					request.getParameter("parentId"), request.getParameter("fdSearchName"));
			List personList = page.getList();
			UserOperHelper.logFindAll(personList, getServiceImp(request).getModelName());
			List<String> resumeList = new ArrayList<String>();
			JSONObject resumeJson = new JSONObject();
			//查询是否有简历
			if(!ArrayUtil.isEmpty(personList)) {
				ArrayList<String> strList = new ArrayList<String>();
				for(Object person : personList) {
					IBaseModel personInfo =  (IBaseModel)person;
					strList.add(personInfo.getFdId());
				}
				String hql = "select  s.fdModelId from com.landray.kmss.sys.attachment.model.SysAttMain s where "
						+ " s.fdModelId in (:idList) and s.fdKey =:fdKey and fdModelName =:modelName";
				resumeList = 
				this.getSysAttMainService().getBaseDao().getHibernateSession().createQuery(hql)
				.setParameter("fdKey", SysZoneConstant.RESUME_KEY)
				.setParameter("modelName", SysZonePersonInfo.class.getName())
				.setParameterList("idList", strList).list();
				for(Object person : personList) {
					IBaseModel personInfo =  (IBaseModel)person;
					if(resumeList.contains(personInfo.getFdId())) {
						resumeJson.put(personInfo.getFdId(), true);
					}
				}
			}
			request.setAttribute("resumeJson", resumeJson);
			request.setAttribute("queryPage", page);
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-listPersons", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(
					KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("listPerson", mapping, form, request, response);
		}
	}
	
	
	public ActionForward saveOtherResume(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-saveOtherResume", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String id = request.getParameter("personId");
			String isDel = request.getParameter("isdel");
			//不可下载标记
			String authAttNodownload = request.getParameter("authAttNodownload");
			
			//可下载者
			String authAttDownloadIds = request.getParameter("authAttDownloadIds");
			List<String> authIds=new ArrayList<String>();
			if(StringUtil.isNotNull(authAttDownloadIds)){
				 authIds = Arrays.asList(authAttDownloadIds.split(";"));
			}
			if (StringUtil.isNull(id)) {
                messages.addError(new NoRecordException());
            } else {
				
				SysZonePersonInfo sysZonePersonInfo = (SysZonePersonInfo)getServiceImp(request).findByPrimaryKey(id);
				sysZonePersonInfo.getAuthAttDownloads().clear();
				for(int i=0;i<authIds.size();i++){
					SysOrgPerson person = (SysOrgPerson) getSysOrgCoreServiceImp(
							request).findByPrimaryKey(authIds.get(i), SysOrgPerson.class);
					sysZonePersonInfo.getAuthAttDownloads().add(person);
				}
				sysZonePersonInfo.setAuthAttNodownload("true".equals(authAttNodownload));
				getServiceImp(request).add(sysZonePersonInfo);
				
				String fileId = request.getParameter("fileId");
				String fileName = request.getParameter("fileName");
				if("1".equals(isDel)||StringUtil.isNotNull(fileId)){
					this.getServiceImp(request).saveResume(
							id, fileName, fileId);	
				}
			}
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-saveOtherResume", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(
					KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		}
		else {
            return getActionForward("success", mapping, form, request, response);
        }
	}
	
	public ActionForward modifyOtherResume(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		TimeCounter.logCurrentTime("Action-modifyOtherResume", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String id = request.getParameter("personId");
			if (StringUtil.isNull(id)) {
                messages.addError(new NoRecordException());
            } else {
				//防止修改的人没在员工黄页中
				SysZonePersonInfo person
						= getServiceImp(request).updateGetPerson(id);
				request.setAttribute("fdId", id);
				request.setAttribute("name", person.getPerson().getFdName());
				form.reset(mapping, request);
				SysZonePersonInfoForm sysZonePersonInfoForm = (SysZonePersonInfoForm) getServiceImp(
						request).convertModelToForm((IExtendForm) form,
								person, new RequestContext(request));
				request.setAttribute("sysZonePersonInfoForm", sysZonePersonInfoForm);
				
				// 得到人员的简历信息
				List<SysAttMain> personResume = (List<SysAttMain>) getSysAttMainService().findByModelKey(
						SysZonePersonInfo.class.getName(), id, "personResume");
				request.setAttribute("personResume", personResume.isEmpty() ? null
						: personResume.get(0));
			}
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-modifyOtherResume", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(
					KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		}
		else {
            return getActionForward("modifyOtherResume", mapping, form, request, response);
        }
	}
	
	
	public ActionForward updatePrivate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-update", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			getServiceImp(request).update((IExtendForm) form,
					new RequestContext(request));
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-update", false, getClass());
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
	
	public ActionForward validatePassword(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-validatePassword", true, getClass());
		PrintWriter out = response.getWriter();
		boolean result = false;
		try {
			String password = request.getParameter("password");
			String fdId = UserUtil.getUser().getFdId();
			if(StringUtil.isNull(password)){
				out.print("密码不能为空");
				return null;
			}
			
			result = getEkpValidateService().validate(
					UserUtil.getUser().getFdLoginName(), password);
			// result = getSysOrgPersonService().validatePassword(fdId,
			// password);
			if(result){
				request.getSession().setAttribute("validatePass", "true");
			}
		} catch (Exception e) {
			e.printStackTrace();
			out.print(e.getMessage());
			return null;
		}

		TimeCounter.logCurrentTime("Action-validatePassword", false, getClass());
		
		out.print(result+"");
		
		return null;
	}
	
	private EKPValidateService ekpValidateService;

	public EKPValidateService getEkpValidateService() {
		if (ekpValidateService == null) {
            ekpValidateService = (EKPValidateService) SpringBeanUtil
                    .getBean("ekpValidateService");
        }
		return ekpValidateService;
	}
	
	public ActionForward getTagsByUserId(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-edit", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String fdUserId = request.getParameter("fdUserId");
			JSONArray rtnArr = new JSONArray();
			if(StringUtil.isNotNull(fdUserId)) {
				rtnArr = this.getServiceImp(request).getTagsByUserId(fdUserId);
			}
			request.setAttribute("lui-source", rtnArr);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-edit", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("lui-source", mapping, form, request, response);
		}
	}
	

	
	public ActionForward getPersons(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		long starTime = System.currentTimeMillis();
		TimeCounter.logCurrentTime("Action-searchPerson", true, getClass());
		KmssMessages messages = new KmssMessages();
		String searchPeople=null;
		try {
			String s_pageno = request.getParameter("pageno");
			String s_rowsize = request.getParameter("rowsize");
			searchPeople = request.getParameter("searchPeople");
			int pageno = 0;
			int rowsize = SysConfigParameters.getRowSize();
			if (s_pageno != null && s_pageno.length() > 0) {
				pageno = Integer.parseInt(s_pageno);
			}
			if (s_rowsize != null && s_rowsize.length() > 0) {
				rowsize = Integer.parseInt(s_rowsize);
				if(rowsize>100) {
					rowsize = 100;
					request.setAttribute("isOver", "true");
				}
			}else{
				rowsize =15;
			}			
			String tagNames = request.getParameter("tagNames");
			String searchValue = request.getParameter("fdSearchName");
			CriteriaValue cv = new CriteriaValue(request);
			if(StringUtil.isNull(tagNames)) {
				Iterator<Entry<String, String[]>> iterator = cv.entrySet().iterator();
				while (iterator.hasNext()) { 
					Entry<String, String[]> e = iterator.next();
					if(e.getKey().startsWith("tag")) {
						for(String str : e.getValue()) {
							tagNames = StringUtil.linkString(tagNames,  " ", str);
						}
					}
				}
			}
			if(StringUtil.isNotNull(tagNames)) {
				tagNames = java.net.URLDecoder.decode(tagNames, "UTF-8");
			}
			if(StringUtil.isNotNull(searchValue)) {
				if(searchValue.contains("%")){
					searchValue=searchValue.replace("%", "\\%");
				}else{
					searchValue = java.net.URLDecoder.decode(searchValue, "UTF-8");
				}
			}
			Page page = getServiceImp(request).querySearchPersonInfo(pageno, rowsize, 
					searchValue, tagNames, new RequestContext(request));
			
			List personList = page.getList();
			List persons = new ArrayList();
			Map<String,Integer> map=new HashMap<String,Integer>();
			int rel;
			Map shortNo=new HashMap();
			if(!ArrayUtil.isEmpty(personList)) {
				
				for(Object person : personList) {
					HashMap orgPerson = (HashMap)person;
					if(SysLangUtil.isLangEnabled()){
						if(!"CN".equals(SysLangUtil.getCurrentLocaleCountry())){
							if(orgPerson.get("fdName"+SysLangUtil.getCurrentLocaleCountry())!=null){
								String fdNameLang=(String)orgPerson.get("fdName"+SysLangUtil.getCurrentLocaleCountry());
								orgPerson.put("fdName", fdNameLang);
								persons.add(orgPerson);
							}
							
						}
					}
					String fdId=(String)orgPerson.get("fdId");
					if(UserUtil.getUser().getFdId().equals(fdId)){
						rel=9;
					}else{
					rel= getSysFansMainService().getRelation(UserUtil.getUser().getFdId(), fdId);
					map.put(fdId, rel);
					}
					SysOrgPerson sysOrgPerson = (SysOrgPerson) getSysOrgCoreServiceImp(request).findByPrimaryKey(fdId, SysOrgPerson.class);
					if(!SysZonePrivateUtil.isContactPrivate(fdId)){
						shortNo.put(orgPerson.get("fdId"), sysOrgPerson.getFdShortNo());
					}else{
						shortNo.put(orgPerson.get("fdId"), "");
					}
					
				}
			}
			if(persons.size()>0){
				page.setList(persons);
			}
			request.setAttribute("queryPage", page);
			request.setAttribute("map", map);
			request.setAttribute("shortNo", shortNo);

			long endTime=System.currentTimeMillis();
			Calendar c = Calendar.getInstance();
			c.setTimeInMillis(endTime - starTime);
			request.setAttribute("count", page.getTotalrows());
			long m = c.get(Calendar.MILLISECOND);
			Double s = (double) m / 1000;
			request.setAttribute("time", s);
			
			request.setAttribute("tagNames", tagNames);
		} catch (Exception e) {
			log.error("员工搜索错误", e);
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-listPersons", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(
					KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			if (searchPeople != null ){
				return getActionForward("searchPeople", mapping, form, request, response);
			}
			
		}
		return null;
	}
	
	/**
	 * 获取personform
	 * 
	 * @param person
	 * @return
	 * @throws Exception
	 */
	private SysOrgPersonForm createPersonform(SysOrgPerson person)
			throws Exception {
		RequestContext requestContext = new RequestContext();
		return (SysOrgPersonForm) getSysOrgPersonService().convertModelToForm(
				null, person, requestContext);
	}
}
