package com.landray.kmss.hr.staff.actions;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.hr.staff.model.*;
import com.landray.kmss.sys.time.model.SysTimeLeaveAmountItem;
import com.landray.kmss.sys.time.service.ISysTimeLeaveAmountItemService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.hibernate.HibernateException;
import org.slf4j.Logger;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.KmssException;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.hr.function.HrFunctions;
import com.landray.kmss.hr.organization.model.HrOrganizationElement;
import com.landray.kmss.hr.organization.model.HrOrganizationPost;
import com.landray.kmss.hr.organization.model.HrOrganizationRank;
import com.landray.kmss.hr.organization.oms.SynchroOrgEkpToHrImp;
import com.landray.kmss.hr.organization.service.IHrOrganizationElementService;
import com.landray.kmss.hr.organization.service.IHrOrganizationPostService;
import com.landray.kmss.hr.organization.service.IHrOrganizationRankService;
import com.landray.kmss.hr.organization.util.HrOrgUtil;
import com.landray.kmss.hr.staff.forms.HrStaffPersonInfoForm;
import com.landray.kmss.hr.staff.service.IHrStaffEmolumentWelfareDetaliedService;
import com.landray.kmss.hr.staff.service.IHrStaffEmolumentWelfareService;
import com.landray.kmss.hr.staff.service.IHrStaffFileAuthorService;
import com.landray.kmss.hr.staff.service.IHrStaffPersonExperienceContractService;
import com.landray.kmss.hr.staff.service.IHrStaffPersonInfoService;
import com.landray.kmss.hr.staff.util.AreasUtil;
import com.landray.kmss.hr.staff.util.CitiesUtil;
import com.landray.kmss.hr.staff.util.HrStaffAuthorityUtil;
import com.landray.kmss.hr.staff.util.HrStaffDateUtil;
import com.landray.kmss.hr.staff.util.HrStaffImportUtil;
import com.landray.kmss.hr.staff.util.HrStaffPersonUtil;
import com.landray.kmss.hr.staff.util.HrStaffPortletUtil;
import com.landray.kmss.sys.appconfig.service.ISysAppConfigService;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.organization.interfaces.OrgPassUpdatePlugin;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.model.SysOrgPost;
import com.landray.kmss.sys.organization.model.SysOrganizationStaffingLevel;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.sys.organization.service.ISysOrgPostService;
import com.landray.kmss.sys.organization.service.ISysOrganizationStaffingLevelService;
import com.landray.kmss.sys.organization.util.PasswordUtil;
import com.landray.kmss.sys.tag.forms.SysTagMainForm;
import com.landray.kmss.sys.tag.model.SysTagMain;
import com.landray.kmss.sys.tag.model.SysTagMainRelation;
import com.landray.kmss.sys.tag.model.SysTagTags;
import com.landray.kmss.sys.tag.service.ISysTagMainRelationService;
import com.landray.kmss.sys.tag.service.ISysTagMainService;
import com.landray.kmss.sys.tag.service.ISysTagTagsService;
import com.landray.kmss.sys.zone.constant.SysZoneConstant;
import com.landray.kmss.sys.zone.model.SysZonePersonInfo;
import com.landray.kmss.sys.zone.service.ISysZonePersonInfoService;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.CriteriaUtil;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.util.excel.ExcelOutput;
import com.landray.kmss.util.excel.ExcelOutputImp;
import com.landray.kmss.util.excel.WorkBook;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.web.upload.FormFile;
import com.sunbor.web.tag.Page;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


/**
 * 员工信息
 * 
 * @author 潘永辉 2016-12-27
 * 
 */
public class HrStaffPersonInfoAction extends ExtendAction {
	private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());
	private ISysOrgPersonService sysOrgPersonService;
	private IHrStaffPersonInfoService hrStaffPersonInfoService;
	private IHrStaffPersonExperienceContractService hrStaffPersonExperienceContractService;
	private ISysTagMainService sysTagMainService;
	private ISysTagTagsService sysTagTagsService;
	private ISysTagMainRelationService sysTagMainRelationService;
	protected ISysAttMainCoreInnerService sysAttMainService;
	private ISysAppConfigService sysAppConfigService;
	private IHrStaffEmolumentWelfareService hrStaffEmolumentWelfareService;
	private IHrStaffEmolumentWelfareDetaliedService hrStaffEmolumentWelfareDetaliedService;
	private ISysZonePersonInfoService sysZonePersonInfoService;
	private IHrOrganizationRankService hrOrganizationRankService;
	private IHrOrganizationPostService hrOrganizationPostService;

	private String fdForeignLanguageLevel;

	public String getFdForeignLanguageLevel() {
		return fdForeignLanguageLevel;
	}

	public void setFdForeignLanguageLevel(String fdForeignLanguageLevel) {
		this.fdForeignLanguageLevel = fdForeignLanguageLevel;
	}
	public IHrOrganizationPostService getHrOrganizationPostService() {
		if (hrOrganizationPostService == null) {
			hrOrganizationPostService = (IHrOrganizationPostService) SpringBeanUtil
					.getBean("hrOrganizationPostService");
		}
		return hrOrganizationPostService;
	}
	public ISysZonePersonInfoService getSysZonePersonInfoService() {
		if (sysZonePersonInfoService == null) {
			sysZonePersonInfoService = (ISysZonePersonInfoService) getBean(
					"sysZonePersonInfoService");
		}
		return sysZonePersonInfoService;
	}

	public IHrStaffEmolumentWelfareDetaliedService
			getHrStaffEmolumentWelfareDetaliedService() {
		if (hrStaffEmolumentWelfareDetaliedService == null) {
			hrStaffEmolumentWelfareDetaliedService = (IHrStaffEmolumentWelfareDetaliedService) getBean(
					"hrStaffEmolumentWelfareDetaliedService");
		}
		return hrStaffEmolumentWelfareDetaliedService;
	}

	public IHrOrganizationRankService
			getHrOrganizationRankService() {
		if (hrOrganizationRankService == null) {
			hrOrganizationRankService = (IHrOrganizationRankService) getBean(
					"hrOrganizationRankService");
		}
		return hrOrganizationRankService;
	}

	public ISysOrgPersonService getSysOrgPersonService() {
		if (sysOrgPersonService == null) {
            sysOrgPersonService = (ISysOrgPersonService) getBean("sysOrgPersonService");
        }
		return sysOrgPersonService;
	}

	public ISysAppConfigService getSysAppConfigService() {
		if (sysAppConfigService == null) {
			sysAppConfigService = (ISysAppConfigService) this
					.getBean("sysAppConfigService");
		}
		return sysAppConfigService;
	}

	public ISysAttMainCoreInnerService getSysAttMainService() {
		if (sysAttMainService == null) {
			sysAttMainService = (ISysAttMainCoreInnerService) this
					.getBean("sysAttMainService");
		}
		return sysAttMainService;
	}

	public ISysTagMainService getSysTagMainService() {
		if (sysTagMainService == null) {
			sysTagMainService = (ISysTagMainService) getBean("sysTagMainService");
		}
		return sysTagMainService;
	}

	public ISysTagTagsService getSysTagTagsService() {
		if (sysTagTagsService == null) {
			sysTagTagsService = (ISysTagTagsService) getBean("sysTagTagsService");
		}
		return sysTagTagsService;
	}

	public ISysTagMainRelationService getSysTagMainRelationService() {
		if (sysTagMainRelationService == null) {
			sysTagMainRelationService = (ISysTagMainRelationService) getBean("sysTagMainRelationService");
		}
		return sysTagMainRelationService;
	}

	public IHrStaffEmolumentWelfareService getHrStaffEmolumentWelfareService() {
		if (hrStaffEmolumentWelfareService == null) {
			hrStaffEmolumentWelfareService = (IHrStaffEmolumentWelfareService) getBean("hrStaffEmolumentWelfareService");
		}
		return hrStaffEmolumentWelfareService;
	}

	@Override
	protected IHrStaffPersonInfoService getServiceImp(HttpServletRequest request) {
		if (hrStaffPersonInfoService == null) {
            hrStaffPersonInfoService = (IHrStaffPersonInfoService) getBean("hrStaffPersonInfoService");
        }
		return hrStaffPersonInfoService;
	}

	public IHrStaffPersonExperienceContractService getHrStaffPersonExperienceContractService() {
		if (hrStaffPersonExperienceContractService == null) {
            hrStaffPersonExperienceContractService = (IHrStaffPersonExperienceContractService) getBean("hrStaffPersonExperienceContractService");
        }
		return hrStaffPersonExperienceContractService;
	}

	private IHrOrganizationElementService hrOrganizationElementService;

	public IHrOrganizationElementService getHrOrganizationElementService() {
		if (hrOrganizationElementService == null) {
			hrOrganizationElementService = (IHrOrganizationElementService) getBean("hrOrganizationElementService");
		}
		return hrOrganizationElementService;
	}

	private ISysOrgPersonService personService = null;

	protected ISysOrgPersonService getPersonService() {
		if (personService == null) {
            personService = (ISysOrgPersonService) getBean("sysOrgPersonService");
        }
		return personService;
	}

	private static IHrStaffFileAuthorService hrStaffFileAuthorService = null;

	private static IHrStaffFileAuthorService getHrStaffFileAuthorService() {
		if (hrStaffFileAuthorService == null) {
            hrStaffFileAuthorService = (IHrStaffFileAuthorService) SpringBeanUtil
                    .getBean("hrStaffFileAuthorService");
        }
		return hrStaffFileAuthorService;
	}
	public ISysTimeLeaveAmountItemService sysTimeLeaveAmountItemService;

	public ISysTimeLeaveAmountItemService getSysTimeLeaveAmountItemService(){
		if(sysTimeLeaveAmountItemService == null){
			sysTimeLeaveAmountItemService = (ISysTimeLeaveAmountItemService)SpringBeanUtil.getBean("sysTimeLeaveAmountItemService");
		}
		return sysTimeLeaveAmountItemService;
	}
	@Override
	protected ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
									   HttpServletResponse response) throws Exception {
		HrStaffPersonInfoForm personInfoform = (HrStaffPersonInfoForm) super.createNewForm(mapping, form, request,
				response);
		String fdParentId = request.getParameter("fdParentId");
		if (StringUtil.isNotNull(fdParentId)) {
			HrOrganizationElement hrElement = (HrOrganizationElement) getHrOrganizationElementService()
					.findByPrimaryKey(fdParentId);
			personInfoform.setFdParentId(hrElement.getFdId());
			personInfoform.setFdParentName(hrElement.getFdName());
		}
		return personInfoform;
	}

	protected void changeContStatusHQLInfo(HttpServletRequest request,
			HQLInfo hqlInfo) throws Exception {
		String whereBlock = hqlInfo.getWhereBlock();
		// 合同状态
		String[] fdContStatus = request.getParameterValues("q.fdContStatus");
		if (fdContStatus != null && fdContStatus.length > 0) {
			StringBuffer sb = new StringBuffer();
			sb.append("(");
			for (int i = 0; i < fdContStatus.length; i++) {
				if (i != fdContStatus.length - 1) {
					if ("1".equals(fdContStatus[i])) {
						sb.append(
								"(hrStaffPersonExperienceContract.fdContStatus is null or hrStaffPersonExperienceContract.fdContStatus = :fdContStatus"
										+ i + ") or ");
					} else {
						sb.append(
								"hrStaffPersonExperienceContract.fdContStatus = :fdContStatus"
										+ i + " or ");
					}
				} else {
					if ("1".equals(fdContStatus[i])) {
						sb.append(
								"(hrStaffPersonExperienceContract.fdContStatus is null or hrStaffPersonExperienceContract.fdContStatus = :fdContStatus"
										+ i + ")");
					} else {
						sb.append(
								"hrStaffPersonExperienceContract.fdContStatus = :fdContStatus"
										+ i);
					}
				}
				hqlInfo.setParameter("fdContStatus" + i, fdContStatus[i]);
			}
			sb.append(")");
			whereBlock = StringUtil.linkString(whereBlock, " and ",
					sb.toString());
		}
		hqlInfo.setWhereBlock(whereBlock);

		CriteriaValue cv = new CriteriaValue(request);
		// 合同到期时间
		String[] fdEndDate = request.getParameterValues("q.fdEndDate");
		if (fdEndDate != null && fdEndDate.length > 0) {
			CriteriaUtil.buildHql(cv, hqlInfo, HrStaffPersonExperienceContract.class);
		}
	}
	private String _fdKey ;
	private String[] _fdDepts ;
	private String[] _fdPosts ;
	private String _fdSex ;
	private CriteriaValue cv ;
	@SuppressWarnings("unchecked")
	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request,
			HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		cv = new CriteriaValue(request);

		// 以下筛选属性需要手工定义筛选范围
		_fdKey = cv.poll("_fdKey");
		_fdDepts = cv.polls("_fdDept");
		String[] _fdLabel = cv.polls("_fdLabel");
		_fdPosts = cv.polls("_fdPosts");

		String hqlWhere = hqlInfo.getWhereBlock();
		StringBuffer whereBlock = null;
		if (StringUtil.isNotNull(hqlWhere)) {
			whereBlock = new StringBuffer(hqlWhere);
		} else {
			whereBlock = new StringBuffer("1 = 1");
		}
		// 本月入职和本月离职
		String _fdType = cv.poll("_fdType");
		if (StringUtil.isNotNull(_fdType) && _fdType.startsWith("month")) {
			if ("monthEntry".equals(_fdType)) {
				whereBlock.append(
						" and hrStaffPersonInfo.fdEntryTime >=:beginDay and hrStaffPersonInfo.fdEntryTime <=:endDay");
			} else {
				whereBlock.append(
						" and hrStaffPersonInfo.fdLeaveTime >=:beginDay and hrStaffPersonInfo.fdLeaveTime <=:endDay");
			}
			Date beginDay = DateUtil.getBeginDayOfMonth();
			Date endDay = DateUtil.getEndDayOfMonth();
			hqlInfo.setParameter("beginDay", beginDay);
			hqlInfo.setParameter("endDay", endDay);
		}

		_fdSex = cv.poll("_fdSex");
		String _fdNatureWork = cv.poll("_fdNatureWork");
		if (StringUtil.isNotNull(_fdSex)) {
			if ("unknown".equalsIgnoreCase(_fdSex)) {
				whereBlock.append(" and hrStaffPersonInfo.fdSex is null");
			} else {
				whereBlock.append(" and hrStaffPersonInfo.fdSex =:fdSex");
				hqlInfo.setParameter("fdSex", _fdSex);
			}
		}
		if (StringUtil.isNotNull(_fdNatureWork)) {
			whereBlock.append(
					" and hrStaffPersonInfo.fdNatureWork like :fdNatureWork");
			hqlInfo.setParameter("fdNatureWork", "%" + _fdNatureWork + "%");
		}
		//如果是周年查询 查询入职日期的月跟日等于 参数中的
		String searchType = request.getParameter("searchType");
		if(HrStaffPortletUtil.TYPE_ANNUAL.equals(searchType)) {
			String searchDate = request.getParameter("searchDate");
			if (StringUtil.isNotNull(searchDate)) { 
				Calendar cal = Calendar.getInstance();
				cal.setTime(DateUtil.convertStringToDate(searchDate));  
				whereBlock.append(" and MONTH(hrStaffPersonInfo.fdEntryTime)= :searchDateMonth");
				whereBlock.append(" and DAY(hrStaffPersonInfo.fdEntryTime)= :searchDateDay");
				whereBlock.append(" and hrStaffPersonInfo.fdEntryTime <=:searchDate");
				hqlInfo.setParameter("searchDate", cal.getTime());
				hqlInfo.setParameter("searchDateMonth", cal.get(Calendar.MONTH) + 1);
				hqlInfo.setParameter("searchDateDay", cal.get(Calendar.DATE));
			}
		}
		// 生日筛选
		String[] fdBirthday = cv.polls("fdBirthdayOfYear");
		if (fdBirthday != null && fdBirthday.length > 0) {
			String startMonStr = fdBirthday[0].substring(0, 2);
			String startDayStr = fdBirthday[0].substring(3, 5);
			String endMonStr = fdBirthday[1].substring(0, 2);
			String endDayStr = fdBirthday[1].substring(3, 5);
			int startMonth = Integer.valueOf(startMonStr);
			int startDay = Integer.valueOf(startDayStr);
			int endMonth = Integer.valueOf(endMonStr);
			int endDay = Integer.valueOf(endDayStr);
			if (StringUtil.isNotNull(fdBirthday[0])
					&& StringUtil.isNotNull(fdBirthday[1])) {
				// 如果月份相同
				if (startMonth == endMonth) {
					// 月份相同：条件为 月=startMonth && startDay<=日<=endDay
					whereBlock.append(
							"  and (Month(hrStaffPersonInfo.fdDateOfBirth) =:startMonth and  Day(hrStaffPersonInfo.fdDateOfBirth) <= :endDay and Day(hrStaffPersonInfo.fdDateOfBirth) >= :startDay) ");
				} else {
					// 月份不相同 例如2月5日-4月10日的生日
					// 条件为 startMonth<月<endMonth || (月=startMonth &&
					// 日>=startDay) ||(月=endMonth && 日<=endDay)
					whereBlock.append(
							" and ((Month(hrStaffPersonInfo.fdDateOfBirth) < :endMonth and Month(hrStaffPersonInfo.fdDateOfBirth) > :startMonth) or (Month(hrStaffPersonInfo.fdDateOfBirth) = :startMonth and Day(hrStaffPersonInfo.fdDateOfBirth) >= :startDay) or (Month(hrStaffPersonInfo.fdDateOfBirth) = :endMonth and Day(hrStaffPersonInfo.fdDateOfBirth) <= :endDay)) ");
					hqlInfo.setParameter("endMonth", endMonth);
				}
				hqlInfo.setParameter("startMonth", startMonth);
				hqlInfo.setParameter("startDay", startDay);
				hqlInfo.setParameter("endDay", endDay);
			}
		}

		// 姓名、登录名、手机号或邮箱
		if (StringUtil.isNotNull(_fdKey)) {
			// 查询组织机构人员信息
			HQLInfo _hqlInfo = new HQLInfo();
			_hqlInfo.setSelectBlock("sysOrgPerson.fdId");
			_hqlInfo
					.setWhereBlock("sysOrgPerson.fdName like :fdKey or sysOrgPerson.fdLoginName like :fdKey or sysOrgPerson.fdMobileNo like :fdKey or sysOrgPerson.fdEmail like :fdKey");
			_hqlInfo.setParameter("fdKey", "%" + _fdKey + "%");
			List<String> ids = getSysOrgPersonService().findValue(_hqlInfo);
			
			whereBlock
					.append(" and ((hrStaffPersonInfo.fdName like :fdKey or hrStaffPersonInfo.fdMobileNo like :fdKey or hrStaffPersonInfo.fdEmail like :fdKey or hrStaffPersonInfo.fdStaffNo like :fdKey)");
			if (!ids.isEmpty()) {
				whereBlock.append(" or " + HQLUtil.buildLogicIN("hrStaffPersonInfo.fdId", ids));
			}
			whereBlock.append(")");
			hqlInfo.setParameter("fdKey", "%" + _fdKey + "%");
		}

		// 部门
		boolean isJsonHrOrgElement = false;
		List<String> deptIds = null;
		if (_fdDepts != null) {
			deptIds = ArrayUtil.convertArrayToList(_fdDepts);
		} else {
			String depts = request.getParameter("q._fdDepts");
			if(null != depts && depts.length()>0) {
				deptIds = ArrayUtil.asList(depts.split(";"));
			}else {
				deptIds = new ArrayList<String>();
			}
		}
		if (!(UserUtil.getKMSSUser().isAdmin()
				|| UserUtil.checkRole("ROLE_HRSTAFF_READALL")) && "quit"
				.equalsIgnoreCase(request.getParameter("personStatus"))) {
			if (deptIds.size() > 0) {
				// 带筛选
				List<String> orgIds = this.getAuthDeptIds();
				deptIds.retainAll(orgIds);
			} else {
				deptIds = this.getAuthDeptIds();
			}
		}
		if (deptIds != null && deptIds.size() > 0) {
			isJsonHrOrgElement = true;
			whereBlock.append(
					" and hrStaffPersonInfo.fdId=hrOrganizationElement.fdId ");
			List<String> newDeptIds = this.getDeptIds(deptIds);
			String staffWhereBlock = "hrStaffPersonInfo.fdHierarchyId like :fdDept0";
			for (int i = 1; i < deptIds.size(); i++) {
				staffWhereBlock = StringUtil.linkString(staffWhereBlock,
						" or ",
						"hrStaffPersonInfo.fdHierarchyId like :fdDept" + i);
				hqlInfo.setParameter("fdDept" + i,
						"%" + deptIds.get(i) + "%");
			}
			whereBlock.append(" and (" + staffWhereBlock);
//			whereBlock.append(" or (").append(HQLUtil.buildLogicIN("hrOrganizationElement.hbmParent.fdId", newDeptIds) + ")");
			whereBlock.append(" ) ");
			hqlInfo.setParameter("fdDept0", "%" + deptIds.get(0) + "%");
		}
		

		// 员工状态
		String[] _fdStatus = request.getParameterValues("q._fdStatus");
//		String quitStatus = "";
//		if (_fdStatus != null && "quit".equals(_fdStatus[0])) {
//			_fdStatus = new String[]{"dismissal","leave","retire"};
//		}
		
		if (_fdStatus != null && Arrays.asList(_fdStatus).contains("onpost")) {
			String[] aa = new String[]{"official","temporary","trial","trialDelay","practice"};
			String[] dd = new String[aa.length+_fdStatus.length] ;
			System.arraycopy(aa, 0, dd, 0, aa.length);
			System.arraycopy(_fdStatus, 0, dd, aa.length, _fdStatus.length);
			_fdStatus = dd.clone();
		}
		//解决传参只能获取到最后一个参数的问题 add by caoyong
//		if (_fdStatus != null && "all".equals(_fdStatus[0])) {
//			_fdStatus = new String[]{"official","temporary","trial","trialDelay"};
//		}
		if (_fdStatus != null && _fdStatus.length > 0) {
//			List<String> fdStatus = new ArrayList<String>();
//			boolean isNull = false;
//			for (String _fdStatu : _fdStatus) {
//				if ("official".equals(_fdStatu)) {
//					isNull = true;
//				}
//				fdStatus.add(_fdStatu);
//			}
			whereBlock
					.append(" and (hrStaffPersonInfo.fdStatus in (:fdStatus)");
//			if (isNull) {
//				whereBlock.append(" or hrStaffPersonInfo.fdStatus is null");
//			}

			List<String> status = new ArrayList<String>();
			for (int i=0;i<_fdStatus.length;i++) {
				status.add(_fdStatus[i]);
			 
			}
			whereBlock.append(")");
			hqlInfo.setParameter("fdStatus", status);
		} else {
			//判断在职以及离职，并做区分 addby zhangcc@2017-12-08
//			String personStatus = request.getParameter("personStatus");
//			String _fdStatusValue = cv.poll("_fdStatus");
//			if(StringUtil.isNotNull(quitStatus)){
//				personStatus = quitStatus;
//				_fdStatusValue = null;
//			}
//			if (null != personStatus && "all".equals(personStatus)) {
//
//			} else if (null != personStatus
//					&& "positive".equals(personStatus)) {
//				// 待转正
//				/*	whereBlock.append(
//							" and hrStaffPersonInfo.fdOrgPerson is not null");*/
//				if (StringUtil.isNull(_fdStatusValue)) {
//					// 只查询试用或者实习，并且关联了组织架构
//					whereBlock.append(
//							" and hrStaffPersonInfo.fdStatus in ('trial','practice','trialDelay')");
//				} else {
//					whereBlock.append(
//							" and hrStaffPersonInfo.fdStatus = :fdStatusValue");
//					hqlInfo.setParameter("fdStatusValue", _fdStatusValue);
//				}
//			} else if (null != personStatus && "quit".equals(personStatus)) {
//				//离职
//				if (StringUtil.isNull(_fdStatusValue)) {
//					//只查询出解聘、离职、退休三种状态的人员
//					whereBlock.append(" and hrStaffPersonInfo.fdStatus in ('dismissal','leave','retire')");
//				} else {
//					whereBlock.append(" and hrStaffPersonInfo.fdStatus = :fdStatusValue");
//					hqlInfo.setParameter("fdStatusValue", _fdStatusValue);
//				}
//			} else {
//					// 在职
//					if (StringUtil.isNull(_fdStatusValue)) {
//						// 只查询出解聘、离职、退休三种状态的人员
//						whereBlock.append(
//								" and hrStaffPersonInfo.fdStatus in ('trial','official','temporary','trialDelay','practice','dismissal','leave','retire')");
//					} else {
//						whereBlock.append(
//								" and hrStaffPersonInfo.fdStatus = :fdStatusValue");
//						hqlInfo.setParameter("fdStatusValue", _fdStatusValue);
//					}
//
//			}
		}

		// 个人标签
		if (_fdLabel != null && _fdLabel.length > 0) {
			// 先查询有哪些员工使用这个标签
			HQLInfo tagHqlInfo = new HQLInfo();
			tagHqlInfo.setSelectBlock("sysTagMainRelation.fdMainTag.fdModelId");
			tagHqlInfo
					.setWhereBlock("sysTagMainRelation.fdTagName in (:fdTagNames)");
			tagHqlInfo.setParameter("fdTagNames", Arrays.asList(_fdLabel));
			List list = getSysTagMainRelationService().findValue(tagHqlInfo);
			if (list.isEmpty()) {
				// 如果根据标签没有找到相应的员工，那表示此条件无数据
				whereBlock.append(" and 1 != 1");
			} else {
				whereBlock.append(" and hrStaffPersonInfo.fdId in (:fdIds)");
				hqlInfo.setParameter("fdIds", list);
			}
		}

		String fdName = request.getParameter("fdName");
		if (StringUtil.isNotNull(fdName)) {
			whereBlock.append(" and hrStaffPersonInfo.fdName like:fdName");
			hqlInfo.setParameter("fdName", "%" + fdName + "%");
		}
		// 所属岗位
		if (_fdPosts != null && _fdPosts.length > 0) {
			// 先查询岗位
			hqlInfo.setJoinBlock(
					"left join hrStaffPersonInfo.fdOrgPosts fdPost");
			whereBlock.append(" and (" + HQLUtil.buildLogicIN("fdPost.fdId",
					Arrays.asList(_fdPosts)) + ")");
		}else {
			String fdOrgPostIds = request.getParameter("fdPosts");
			if(null != fdOrgPostIds && fdOrgPostIds.length()>0) {
				hqlInfo.setJoinBlock("left join hrStaffPersonInfo.fdOrgPosts fdPost");
				whereBlock.append(" and (" + HQLUtil.buildLogicIN("fdPost.fdId",
						ArrayUtil.asList(fdOrgPostIds.split(";"))) + ")");
			}
		}

		String warningType = (String) request.getAttribute("warningType");
		if (StringUtil.isNotNull(warningType)) {
			// 试用：trial
			if ("trial".equals(warningType)) {
				whereBlock
						.append(" and hrStaffPersonInfo.fdTrialExpirationTime is not null");
			}
			// 生日：birthday
			if ("birthday".equals(warningType)) {
				request.setAttribute("birthdayWarningHqlinfo", hqlInfo);
				Calendar birthday = Calendar.getInstance();
				int fdBirthdayOfYear = birthday.get(Calendar.DAY_OF_YEAR);
				// 查询条件按“生日(一年中的第几天)”查询
				whereBlock
						.append(" and hrStaffPersonInfo.fdBirthdayOfYear is not null");
			}
		}
		// #67666 预警查询不需要过滤权限，预警查询有一个角色控制：ROLE_HRSTAFF_WARNING
		if (!"quit".equalsIgnoreCase(request.getParameter("personStatus"))) {
			boolean isRight = false;
			String tableName ="hrStaffPersonInfo";
			if ("birthDay".equalsIgnoreCase(warningType)) {
				//生日预警，如果开启了档案授权查询权限，则查询权限
				HrStaffAlertWarningBirthday birthday=new HrStaffAlertWarningBirthday();
				if("true".equals(birthday.getCerifyAuthorization())){
					isRight =true;
				}
			} else if ("contract".equalsIgnoreCase(warningType)) {
				//合同预警，如果开启了档案授权查询权限，则查询权限
				HrStaffAlertWarningContract contract=new HrStaffAlertWarningContract();
				if("true".equals(contract.getCerifyAuthorization())){
					isRight =true;
					tableName ="hrStaffPersonExperienceContract";
				}
			} else if ("trial".equalsIgnoreCase(warningType)) {
				//试用期预警，如果开启了档案授权查询权限，则查询权限
				HrStaffAlertWarningTrial trial=new HrStaffAlertWarningTrial();
				if("true".equals(trial.getCerifyAuthorization())){
					isRight =true;
				}
			} else {
				isRight =true;
			}
			if (isRight) {
				whereBlock = HrStaffAuthorityUtil.builtWhereBlock(whereBlock, tableName, hqlInfo);
			}
		}

		// 组织架构数据查询
		String IsHrOrg = request.getParameter("IsHrOrg");
		String fdParentId = request.getParameter("fdParentId");
		String orgType = request.getParameter("fdOrgType");

		if (StringUtil.isNotNull(fdParentId)) {
			whereBlock.append(
					" and hrStaffPersonInfo.hbmParent.fdId=:fdParentId ");
			hqlInfo.setParameter("fdParentId", fdParentId);
		}
		if (StringUtil.isNotNull(orgType)) {
			Integer fdOrgType = Integer.parseInt(orgType);
			whereBlock
					.append(" and hrStaffPersonInfo.fdOrgType=:fdOrgType ");
			hqlInfo.setParameter("fdOrgType", fdOrgType);
		}
		if (isJsonHrOrgElement) {
			String joinBlock = hqlInfo.getJoinBlock();
			if (joinBlock == null) {
				joinBlock = "";
			}
			joinBlock = ", com.landray.kmss.hr.organization.model.HrOrganizationElement hrOrganizationElement "
					+ joinBlock;
			hqlInfo.setJoinBlock(joinBlock);
		}
//		String fdEnterPriseStartTime = request.getParameter("fdEnterPriseStartTime");
//		String fdEnterPriseEndTime = request.getParameter("fdEnterPriseEndTime");		
//		if (StringUtil.isNotNull(fdEnterPriseStartTime) && StringUtil.isNotNull(fdEnterPriseEndTime)) {
//			whereBlock.append(" and hrStaffPersonInfo.fdTimeOfEnterprise  BETWEEN  :fdEnterPriseStartTime and :fdEnterPriseEndTime ");
//			Date startDate = new Date();
//			Date endDate = new Date();
//			startDate = DateUtil.convertStringToDate(fdEnterPriseStartTime,
//					DateUtil.TYPE_DATE, null);
//			Calendar c = Calendar.getInstance();
//			c.setTime(startDate);
//			c.add(Calendar.DATE, -1);
//			c.add(Calendar.HOUR, 23);
//			c.add(Calendar.MINUTE, 59);
//			c.add(Calendar.SECOND, 59);
//			startDate = c.getTime();
//			endDate = DateUtil.convertStringToDate(fdEnterPriseEndTime,
//					DateUtil.TYPE_DATE, null);
//			c.setTime(endDate);
//			c.add(Calendar.HOUR, 23);
//			c.add(Calendar.MINUTE, 59);
//			c.add(Calendar.SECOND, 59);
//			endDate = c.getTime();
//			hqlInfo.setParameter("fdEnterPriseStartTime", startDate);
//			hqlInfo.setParameter("fdEnterPriseEndTime", endDate);
//		}
		hqlInfo.setWhereBlock(whereBlock.toString());
		// 解析其它筛选属性
		CriteriaUtil.buildHql(cv, hqlInfo, HrStaffPersonInfo.class);
		
	}

	private List<String> getAuthDeptIds() throws HibernateException, Exception {
		String[] postId = UserUtil.getKMSSUser().getPostIds();
		String sql = "select a.fd_id,a.fd_name,d.fd_org_id from hr_staff_file_author_detail d left join hr_staff_file_author a on a.fd_id = d.fd_author_id where fd_org_id =:personId";
		if (postId != null && postId.length > 0) {
			List list = Arrays.asList(postId);
			sql = sql + " or " + HQLUtil.buildLogicIN("fd_org_id", list);
		}
		List<Object[]> authorDetails = getHrStaffFileAuthorService().getBaseDao().getHibernateSession().createNativeQuery(sql).setString("personId",
						UserUtil.getKMSSUser().getPerson().getFdId())
				.list();
		List<String> orgIds = new ArrayList<String>();

		// 处理DB2会多出一列的情况
		if (authorDetails.size() > 0) {
			int j = 1;
			if (authorDetails.get(0)[0] instanceof BigInteger) {
				j++;
			}

			for (Object[] obj : authorDetails) {
				if (StringUtil.isNotNull((String) obj[j])) {
                    orgIds.add((String) obj[j]);
                }
			}
		}
		return this.getDeptIds(orgIds);
	}

	private List<String> getDeptIds(List<String> deptIds) throws Exception {
		List<String> newDeptIds = new ArrayList<String>();
		newDeptIds.addAll(deptIds);
		for (String deptId : deptIds) {
			HrOrganizationElement element = (HrOrganizationElement) this
					.getHrOrganizationElementService()
					.findByPrimaryKey(deptId, null, true);
			if (element != null && !ArrayUtil.isEmpty(element.getFdChildren())) {
				List<HrOrganizationElement> childs = element
						.getFdChildren();
				List<String> childIds = new ArrayList<String>();
				for (HrOrganizationElement child : childs) {
					// 子级如果是部门或者机构
					if (child.getFdOrgType() == 1
							|| child.getFdOrgType() == 2) {
						childIds.add(child.getFdId());
					} else {
						continue;
					}
				}
				if (!ArrayUtil.isEmpty(childIds)) {
					newDeptIds.addAll(this.getDeptIds(childIds));
				}
			}
		}
		return newDeptIds;
	}

	/**
	 * 下载模板
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param responses
	 * @return
	 * @throws Exception
	 */
	public ActionForward downloadTemplet(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			// 模板名称
			String templetName = ResourceUtil
					.getString("hr-staff:hrStaffPersonInfo.templetName");
			// 构建模板文件
			HSSFWorkbook workbook = getServiceImp(request)
					.buildTempletWorkBook(request);

			response.setContentType("multipart/form-data");
			response.setHeader("Content-Disposition", "attachment;fileName="
					+ HrStaffImportUtil.encodeFileName(request, templetName));
			OutputStream out = response.getOutputStream();
			workbook.write(out);
			// 添加日志信息
			if (UserOperHelper.allowLogOper("downloadTemplet",
					getServiceImp(request).getModelName())) {
				UserOperHelper.setEventType(ResourceUtil
						.getString("hr-staff:hrStaff.import.button.download"));
				UserOperContentHelper.putFind("", templetName,
						getServiceImp(request).getModelName());
			}
			return null;
		} catch (IOException e) {
			messages.addError(e);
		}

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(
					request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("success", mapping, form, request, response);
		}
	}

	/**
	 * 导入员工
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward fileUpload(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-fileUpload", true, getClass());
		KmssMessage message = null;
		String forward = "hrStaffFileUpload";
		if (StringUtil.isNotNull(request.getParameter("ratify"))) {
			forward += "2";
		}
		HrStaffPersonInfoForm personInfoForm = (HrStaffPersonInfoForm) form;
		FormFile file = personInfoForm.getFile();
		String resultMsg = null;
		boolean state = false;
		if (file == null || file.getFileSize() < 1) {
			resultMsg = ResourceUtil.getString("hrStaff.import.noFile",
					"hr-staff");
		} else {
			try {
				message = getServiceImp(request).saveImportData(personInfoForm);
				state = message.getMessageType() == KmssMessage.MESSAGE_COMMON;
			} catch (Exception e) {
				message = new KmssMessage(e.getMessage());
				logger.error("", e);
				e.printStackTrace();
			}
			resultMsg = message.getMessageKey();
		}
		// 保存导入信息
		request.setAttribute("resultMsg", resultMsg);
		// 状态
		request.setAttribute("state", state);
		// 保存导入的类型
		request.setAttribute("type", request.getParameter("type"));
		request.setAttribute("uploadActionUrl", request.getParameter("uploadActionUrl"));
		request.setAttribute("downLoadUrl", request.getParameter("downLoadUrl"));
		// 添加日志信息
		if (UserOperHelper.allowLogOper("fileUpload",
				getServiceImp(request).getModelName())) {
			UserOperHelper.setEventType(ResourceUtil
					.getString("hr-staff:hrStaff.import.button.submit"));
		}
		return getActionForward(forward, mapping, form, request,
				response);
	}

	public ActionForward fileUploadPerson(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONObject importResult = new JSONObject();
		KmssMessage message = null;
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			String fdParentId = request.getParameter("fdParentId");
			importResult.put("otherErrors", new JSONArray());
			HrStaffPersonInfoForm personInfoForm = (HrStaffPersonInfoForm) form;
			personInfoForm.setFdSource("com.landray.kmss.hr.organization");
			personInfoForm.setFdParentId(fdParentId);
			message = getServiceImp(request).saveImportData(personInfoForm);
			importResult.put("importMsg", message.getMessageKey());
		} catch (Exception e) {
			e.printStackTrace();
			message = new KmssMessage(e.getMessage());
			importResult.put("hasError", 1);
			//importResult.put("importMsg", ResourceUtil.getString("hr-organization:hr.organization.import.fail"));
			importResult.getJSONArray("otherErrors").add(e.getMessage());
		}
		String result = HrOrgUtil.replaceCharacter(importResult.toString());
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write("<script>parent.callback(" + result + ");</script>");
		return null;
	}

	/**
	 * 获取概况数据
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void overview(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		JSONArray array = null;

		// 类型：1（生日），2（合同），3（试用）
		int _type = StringUtil
				.getIntFromString(request.getParameter("type"), 1);

		switch (_type) {
		case 1: {
			array = getDateOfBirthData(request);
			break;
		}
		case 2: {
			array = getContractExpirationData(request);
			break;
		}
		case 3: {
			array = getTrialExpirationData(request);
			break;
		}
		}

		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(array);
		response.getWriter().flush();
		response.getWriter().close();
	}

	/**
	 * 获取最近生日数据
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private JSONArray getDateOfBirthData(HttpServletRequest request)
			throws Exception {
		JSONArray array = new JSONArray();
		List<HrStaffPersonInfo> list =null;
		HrStaffAlertWarningBirthday warningBirthday=new HrStaffAlertWarningBirthday();
		if("true".equals(warningBirthday.getStaffReminder())){
			//开启了生日提醒，则按照提醒规则来取生日的人数。
			Page page = getServiceImp(request).findByBirthdayPage(warningBirthday.getCycleReminder(),new Date(),WARNINGMAX_ROW,1);
			list =page.getList();
		}else {
			Calendar birthday = Calendar.getInstance();
			int fdBirthdayOfYear = birthday.get(Calendar.DAY_OF_YEAR) - 1;
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setRowSize(WARNINGMAX_ROW); // 只显示5条记录
			// 查询条件按“生日(一年中的第几天)”查询
			StringBuffer whereBlock = new StringBuffer(
					"hrStaffPersonInfo.fdBirthdayOfYear is not null and hrStaffPersonInfo.fdBirthdayOfYear >= :fdBirthdayOfYear and hrStaffPersonInfo.fdStatus in ( :trial,:official,:temporary,:trialDelay,:retire )");
			whereBlock = HrStaffAuthorityUtil.builtWhereBlock(whereBlock,
					"hrStaffPersonInfo", hqlInfo);
			hqlInfo.setWhereBlock(whereBlock.toString());
			hqlInfo.setParameter("fdBirthdayOfYear", fdBirthdayOfYear);
			hqlInfo.setParameter("trial", "trial");
			hqlInfo.setParameter("official", "official");
			hqlInfo.setParameter("temporary", "temporary");
			hqlInfo.setParameter("trialDelay", "trialDelay");
			hqlInfo.setParameter("retire", "retire");
			hqlInfo.setOrderBy("hrStaffPersonInfo.fdBirthdayOfYear");
			list = getServiceImp(request).findPage(hqlInfo).getList();
			// 添加日志信息
			UserOperHelper.logFindAll(list, getServiceImp(request).getModelName());

			// 如果获取的数据少于5条，则需要获取第二年，并在今天之前的数据
			// 比如今天是10月01日，获取今年并在今天之后的数据不足5条，则需要获取下年并在10月01日之前的数据
			if (list.size() < WARNINGMAX_ROW) {
				hqlInfo = new HQLInfo();
				hqlInfo.setRowSize(WARNINGMAX_ROW - list.size()); // 获取剩余数量
				// 查询条件按“生日(一年中的第几天)”查询
				StringBuffer _whereBlock = new StringBuffer(
						"hrStaffPersonInfo.fdBirthdayOfYear is not null and hrStaffPersonInfo.fdBirthdayOfYear < :fdBirthdayOfYear and hrStaffPersonInfo.fdStatus  in  ( :trial,:official,:temporary,:trialDelay,:retire )");
				_whereBlock = HrStaffAuthorityUtil.builtWhereBlock(_whereBlock,
						"hrStaffPersonInfo", hqlInfo);
				hqlInfo.setWhereBlock(_whereBlock.toString());
				hqlInfo.setParameter("fdBirthdayOfYear", fdBirthdayOfYear);
				hqlInfo.setParameter("trial", "trial");
				hqlInfo.setParameter("official", "official");
				hqlInfo.setParameter("temporary", "temporary");
				hqlInfo.setParameter("trialDelay", "trialDelay");
				hqlInfo.setParameter("retire", "retire");
				hqlInfo.setOrderBy("hrStaffPersonInfo.fdBirthdayOfYear");
				List<HrStaffPersonInfo> list2 = getServiceImp(request)
						.findPage(hqlInfo).getList();
				buildDateOfBirthData(list2, array);
			}
		}
		buildDateOfBirthData(list, array);

		return array;
	}

	private void buildDateOfBirthData(List<HrStaffPersonInfo> list,
			JSONArray array) {
		if(CollectionUtils.isNotEmpty(list)) {
			for (HrStaffPersonInfo info : list) {
				JSONObject obj = new JSONObject();
				obj.put("fdId", info.getFdId());
				obj.put("fdName", getPersonName(info));

				Date fdBirthday = info.getFdDateOfBirth();
				if (fdBirthday != null) {
					Calendar _cal = Calendar.getInstance();
					Calendar cal = Calendar.getInstance();
					cal.setTime(fdBirthday);
					cal.set(Calendar.YEAR, _cal.get(Calendar.YEAR));
					fdBirthday = cal.getTime();
				}

				String[] str = processingDate(fdBirthday, true);
				obj.put("fdIsToday", str[0]);
				obj.put("fdDate", str[1]);
				array.add(obj);
			}
		}
	}
	private int WARNINGMAX_ROW=5;
	/**
	 * 获取试用到期数据
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private JSONArray getTrialExpirationData(HttpServletRequest request)
			throws Exception {
		//根据试用期预警的配置
		HrStaffAlertWarningTrial warningTrial=new HrStaffAlertWarningTrial();
		List<HrStaffPersonInfo> list =null;
		JSONArray array = new JSONArray();
		if("true".equals(warningTrial.getStaffReminder())){
			list =getServiceImp(request).findByTrialPage(warningTrial.getCycleReminder(),new Date(),WARNINGMAX_ROW,1).getList();
		} else {
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setRowSize(WARNINGMAX_ROW); // 只显示5条记录
			StringBuffer whereBlock = new StringBuffer(
					"hrStaffPersonInfo.fdTrialExpirationTime is not null and hrStaffPersonInfo.fdTrialExpirationTime >= :fdTrialExpirationTime and hrStaffPersonInfo.fdStatus in ( :trial,:trialDelay )");
			whereBlock = HrStaffAuthorityUtil.builtWhereBlock(whereBlock,
					"hrStaffPersonInfo", hqlInfo);
			hqlInfo.setWhereBlock(whereBlock.toString());
			hqlInfo.setParameter("fdTrialExpirationTime", getDate(null));
			hqlInfo.setParameter("trial", "trial");
			hqlInfo.setParameter("trialDelay", "trialDelay");
			hqlInfo.setOrderBy("hrStaffPersonInfo.fdTrialExpirationTime");
			list = getServiceImp(request).findPage(hqlInfo)
					.getList();
		}
		// 添加日志信息
		UserOperHelper.logFindAll(list, getServiceImp(request).getModelName());
		if(CollectionUtils.isNotEmpty(list)) {
			for (HrStaffPersonInfo info : list) {
				JSONObject obj = new JSONObject();
				obj.put("fdId", info.getFdId());
				obj.put("fdName", getPersonName(info));
				String[] str = processingDate(info.getFdTrialExpirationTime(),
						false);
				obj.put("fdIsToday", str[0]);
				obj.put("fdDate", str[1]);
				array.add(obj);
			}
		}
		return array;
	}

	/**
	 * 获取合同到期数据
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private JSONArray getContractExpirationData(HttpServletRequest request)
			throws Exception {
		List<HrStaffPersonExperienceContract> list =null;
		JSONArray array = new JSONArray();
		HrStaffAlertWarningContract warningContract=new HrStaffAlertWarningContract();
		if("true".equals(warningContract.getStaffReminder())){
			list =getHrStaffPersonExperienceContractService().findByContractPage(warningContract.getCycleReminder(),new Date(),WARNINGMAX_ROW,1).getList();
		}else {
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setRowSize(WARNINGMAX_ROW); // 只显示5条记录
			hqlInfo.setJoinBlock(", HrStaffPersonInfo hrStaffPersonInfo");
			StringBuffer whereBlock = new StringBuffer(
					" hrStaffPersonInfo.fdId = hrStaffPersonExperienceContract.fdPersonInfo.fdId and hrStaffPersonExperienceContract.fdEndDate is not null and hrStaffPersonExperienceContract.fdEndDate >= :fdEndDate and hrStaffPersonInfo.fdStatus in ( :trial,:official,:temporary,:trialDelay )");
			whereBlock = HrStaffAuthorityUtil.builtWhereBlock(whereBlock,
					"hrStaffPersonExperienceContract", hqlInfo);
			hqlInfo.setWhereBlock(whereBlock.toString());
			hqlInfo.setParameter("fdEndDate", getDate(null));
			hqlInfo.setParameter("trial", "trial");
			hqlInfo.setParameter("official", "official");
			hqlInfo.setParameter("temporary", "temporary");
			hqlInfo.setParameter("trialDelay", "trialDelay");
			hqlInfo.setOrderBy("hrStaffPersonExperienceContract.fdEndDate");
			list = getHrStaffPersonExperienceContractService()
					.findPage(hqlInfo).getList();
		}
		// 添加日志信息
		UserOperHelper.logFindAll(list, getServiceImp(request).getModelName());
		if(CollectionUtils.isNotEmpty(list)) {
			for (HrStaffPersonExperienceContract contract : list) {
				JSONObject obj = new JSONObject();
				obj.put("fdId", contract.getFdPersonInfo().getFdId());
				obj.put("fdName", getPersonName(contract.getFdPersonInfo()));
				String[] str = processingDate(contract.getFdEndDate(), false);
				obj.put("fdIsToday", str[0]);
				obj.put("fdDate", str[1]);
				array.add(obj);
			}
		}
		return array;
	}

	/**
	 * 获取人员名称
	 * 
	 * @param personInfo
	 * @return
	 */
	private String getPersonName(HrStaffPersonInfo personInfo) {
		if (personInfo == null) {
			return "";
		} else if (personInfo.getFdOrgPerson() == null) {
			return personInfo.getFdName();
		} else {
			return personInfo.getFdOrgPerson().getFdName();
		}
	}

	/**
	 * 处理日期
	 * 
	 * @param date
	 * @return 返回数组，是否是今天；“今天”、“明天”、或者具体日期
	 */
	private String[] processingDate(Date date, boolean isBirthday) {
		String[] data = new String[2];
		if (date != null) {
			// 今天
			long _today = getDate(null).getTime();
			// 明天
			long _tomorrow = getTomorrow().getTime();
			// 传入的时间
			Date current = getDate(date);
			long _current = current.getTime();

			if (_current == _today) {
				data[0] = "true";
				data[1] = ResourceUtil
						.getString("hr-staff:hrStaff.overview.today");
			} else if (_current == _tomorrow) {
				data[0] = "false";
				data[1] = ResourceUtil
						.getString("hr-staff:hrStaff.overview.tomorrow");
			} else {
				data[0] = "false";
				if (isBirthday) {
					data[1] = DateUtil.convertDateToString(current, "MM-dd");
				} else {
					data[1] = DateUtil.convertDateToString(current,
							DateUtil.TYPE_DATE, null);
				}
			}
		}

		return data;
	}

	private Date getDate(Date date) {
		if (date == null) {
			date = new Date();
		}
		return date;
	}

	private Date getTomorrow() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}

	/**
	 * 生日预警
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward birthdayList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setAttribute("warningType", "birthday");
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
			if (s_pageno != null && s_pageno.length() > 0 && Integer.parseInt(s_pageno) > 0) {
				pageno = Integer.parseInt(s_pageno);
			}
			if (s_rowsize != null && s_rowsize.length() > 0 && Integer.parseInt(s_rowsize) > 0) {
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
			return getActionForward("birthday_list", mapping, form, request,
					response);
		}

	}

	/**
	 * 试用期预警
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward trialList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setAttribute("warningType", "trial");
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
			if (s_pageno != null && s_pageno.length() > 0 && Integer.parseInt(s_pageno) > 0) {
				pageno = Integer.parseInt(s_pageno);
			}
			if (s_rowsize != null && s_rowsize.length() > 0 && Integer.parseInt(s_rowsize) > 0) {
				rowsize = Integer.parseInt(s_rowsize);
			}

			// 按多语言字段排序
			if (StringUtil.isNotNull(orderby) && form instanceof IExtendForm) {
				Class<?> modelClass = ((IExtendForm) form).getModelClass();
				if (modelClass != null) {
					String langFieldName = SysLangUtil
							.getLangFieldName(modelClass.getName(), orderby);
					if (StringUtil.isNotNull(langFieldName)) {
						orderby = langFieldName;
					}
				}
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
			return getActionForward("trial_list", mapping, form, request, response);
		}
	}

	/**
	 * 合同预警
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward contractList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setAttribute("warningType", "contract");
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
			if (StringUtil.isNotNull(orderby)) {
				hqlInfo
						.setOrderBy("hrStaffPersonExperienceContract."
								+ orderby);
			}
			hqlInfo.setPageNo(pageno);
			hqlInfo.setRowSize(rowsize);
			hqlInfo.setJoinBlock(", HrStaffPersonInfo hrStaffPersonInfo ");
			changeFindPageHQLInfo(request, hqlInfo);
			// 关联合同信息
			//hqlInfo.setJoinBlock("HrStaffPersonExperienceContract hrStaffPersonInfo");
			//if(StringUtil.isNotNull(hqlInfo.getJoinBlock()))
			hqlInfo
					.setWhereBlock(hqlInfo.getWhereBlock()
							+ " and hrStaffPersonInfo.fdId = hrStaffPersonExperienceContract.fdPersonInfo.fdId and hrStaffPersonExperienceContract.fdEndDate is not null and hrStaffPersonExperienceContract.fdIsLongtermContract = false");
			changeContStatusHQLInfo(request, hqlInfo);
			Page page = getHrStaffPersonExperienceContractService().findPage(hqlInfo);
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
			return getActionForward("contract_list", mapping, form, request,
					response);
		}
	}

	public ActionForward listPersons(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
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
					request.getParameter("parentId"),
					request.getParameter("fdSearchName"));
			List personList = page.getList();
			// 添加日志信息
			UserOperHelper.logFindAll(personList,
					getServiceImp(request).getModelName());
			List<String> resumeList = new ArrayList<String>();
			JSONObject resumeJson = new JSONObject();
			// 查询是否有简历
			if (!ArrayUtil.isEmpty(personList)) {
				ArrayList<String> strList = new ArrayList<String>();
				for (Object person : personList) {
					IBaseModel personInfo = (IBaseModel) person;
					strList.add(personInfo.getFdId());
				}
				String hql = "select  s.fdModelId from com.landray.kmss.sys.attachment.model.SysAttMain s where "
						+ " s.fdModelId in (:idList) and s.fdKey =:fdKey and fdModelName =:modelName";
				resumeList = this.getSysAttMainService().getBaseDao()
						.getHibernateSession().createQuery(hql).setParameter(
								"fdKey", SysZoneConstant.RESUME_KEY)
						.setParameter("modelName",
								SysZonePersonInfo.class.getName())
						.setParameterList("idList", strList).list();
				for (Object person : personList) {
					IBaseModel personInfo = (IBaseModel) person;
					if (resumeList.contains(personInfo.getFdId())) {
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
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("listPerson", mapping, form, request,
					response);
		}
	}

	public String buildAreasHtml(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String citiesId = request.getParameter("citiesId");
		response.setContentType("text/html;charset=UTF-8");
		JSONObject obj = new JSONObject();
		String html = AreasUtil.buildAreasHtml(request.getParameter("fieldName"),
				request.getParameter("prevFieldName"), request);
		obj.accumulate("html", html);
		String res = obj.toString();
		response.getWriter()
				.write(res);
		return null;
	}
	public String buildCitiesHtml(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		response.setContentType("text/html;charset=UTF-8");
		JSONObject obj = new JSONObject();
		String o = request.getParameter("fdPostalAddressProvinceId");
		String html = CitiesUtil.buildCitiesHtml(
				request.getParameter("fieldName"),
				request.getParameter("prevFieldName"), request);
		;
		obj.accumulate("html", html);
		String res = obj.toString();
		response.getWriter()
				.write(res);
		return null;
	}

	public String getStrPinYin(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TimeCounter.logCurrentTime("Action-checkSyncOrg", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			JSONObject json = new JSONObject();
			if (!"POST".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}
			String str = request.getParameter("str");

			

			JSONObject object = new JSONObject();
			JSONArray ja = new JSONArray();
			object.put("str", HrFunctions.getPinyinStringWithDefaultFormat(str));
			ja.add(object);

			json.put("result", ja);

			response.setCharacterEncoding("utf-8");
			response.getWriter().write(json.toString());
			response.getWriter().flush();
			response.getWriter().close();
		} catch (Exception e) {
			logger.error("", e);
			messages.addError(e);
		}

		return null;
	}
	public String getFdDirectSuperiorJobNumber(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TimeCounter.logCurrentTime("Action-checkSyncOrg", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			JSONObject json = new JSONObject();
			if (!"POST".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}
			String fdDeptId = request.getParameter("fdId");

			SysOrgElement sysOrgElement = (SysOrgElement) getSysOrgElementService()
					.findByPrimaryKey(fdDeptId);

			JSONObject object = new JSONObject();
			JSONArray ja = new JSONArray();
			object.put("No", sysOrgElement.getFdNo());
			ja.add(object);

			json.put("result", ja);

			response.setCharacterEncoding("utf-8");
			response.getWriter().write(json.toString());
			response.getWriter().flush();
			response.getWriter().close();
		} catch (Exception e) {
			logger.error("", e);
			messages.addError(e);
		}

		return null;
	}
	public String buildRankHtml(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String fdId = request.getParameter("fdId");
		String grade = request.getParameter("grade");
		KmssMessages messages = new KmssMessages();
		try {
			if (StringUtil.isNotNull(fdId)) {
				String[] ids = fdId.split(";");
				List list = getHrOrganizationPostService()
						.findByPrimaryKeys(ids);
				String html = buildSelectRankHtml(
						(HrOrganizationPost) list.get(0), null);
				JSONObject obj = new JSONObject();
				obj.accumulate("html", html);
				String res = obj.toString();
				response.setContentType("text/html;charset=UTF-8");
				response.getWriter().write(res);
			}
		} catch (Exception e) {
			messages.addError(e);
		}

		return null;
	}

	private String buildSelectRankHtml(HrOrganizationPost orgPost,
			String defaultValue)
			throws Exception {
		if (orgPost == null) {
			return "";
		}
		if (orgPost.getFdRankMax() == null) {
			return "";
		}
		if (orgPost.getFdRankMix() == null) {
			return "";
		}
			Integer fdGradeMax = orgPost.getFdRankMax().getFdGrade()
					.getFdWeight();
			Integer fdGradeMix = orgPost.getFdRankMix().getFdGrade()
					.getFdWeight();
			Integer fdRankMax = orgPost.getFdRankMax().getFdWeight();
			Integer fdRankMix = orgPost.getFdRankMix().getFdWeight();
			if (fdRankMax != null && fdRankMix != null) {
				HQLInfo hqlInfo = new HQLInfo();
				hqlInfo.setFromBlock(
						"com.landray.kmss.hr.organization.model.HrOrganizationRank hrOrganizationRank");
				hqlInfo.setWhereBlock(
						"hrOrganizationRank.fdGrade.fdWeight<=:fdWeightMax and hrOrganizationRank.fdGrade.fdWeight>=:fdWeightMix");
				hqlInfo.setParameter("fdWeightMax", fdGradeMax);
				hqlInfo.setParameter("fdWeightMix", fdGradeMix);
				hqlInfo.setOrderBy(
						"hrOrganizationRank.fdGrade.fdWeight desc");
				List list = getHrOrganizationRankService()
						.findList(hqlInfo);
			if (list.size() == 0) {
				return "";
			}
				StringBuffer html = new StringBuffer(
					"<select name='fdOrgRankId'>");
			String rankName = "";
				for (int i = 0; i < list.size(); i++) {
					HrOrganizationRank rank = (HrOrganizationRank) list.get(i);
					Integer rankWeight = rank.getFdWeight();
					if (rankWeight >= fdRankMix
							&& rankWeight <= fdRankMax) {
					String seleted = "";
					if (rank.getFdId().equals(defaultValue)) {
						seleted = "selected =selected";
					}
					html.append(
							"<option " + seleted + " value='" + rank.getFdId()
									+ "' gradeName="
									+ rank.getFdGrade().getFdName() + ">"
									+ rank.getFdName()
								+ "</option>");
					}
				}
			html.append("</select>");
				return html.toString();
			}

		return "";
	}
	@Override
	protected void loadActionForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		form.reset(mapping, request);
		IExtendForm rtnForm = null;
		//判断该员工是否是组织架构的人员，并且是否为无效
		Boolean isAvailable = null;
		//档案是否可以编辑，默认可以编辑
		boolean isReadOnly = false;
		String id = request.getParameter("fdId");
		//代表档案不可以编辑
		if(StringUtil.isNotNull(request.getParameter("readOnly")) && "true".equals(request.getParameter("readOnly"))){
			isReadOnly = true;
		}
		if (!StringUtil.isNull(id)) {
			IBaseModel model = getServiceImp(request).findByPrimaryKey(id,
					null, true);
			if (model != null) {
				UserOperHelper.logFind(model);// 添加日志信息
				rtnForm = getServiceImp(request).convertModelToForm(
						(IExtendForm) form, model, new RequestContext(request));

				//判断该员工是否是组织架构的人员，并且是否为无效
				HrStaffPersonInfo personInfo = (HrStaffPersonInfo) model;
				if(personInfo.getFdOrgPerson() != null){
					isAvailable = personInfo.getFdOrgPerson().getFdIsAvailable();
					getSysZonePersonInfoService()
							.updateGetPerson(personInfo.getFdId());
				}
				String rankId = null;
				if (personInfo.getFdOrgRank() != null) {
					rankId = personInfo.getFdOrgRank().getFdId();
				}
				if (personInfo.getFdPosts().size() > 0) {
					String rank = buildSelectRankHtml(
							(HrOrganizationPost) personInfo.getFdPosts().get(0),
							rankId);
					request.setAttribute("rankList", rank);
				}
				if (personInfo.getFdOrgRank() != null) {
					request.setAttribute("ranGrade",
							personInfo.getFdOrgRank().getFdGrade().getFdName());
				}

			}
		}
		if (rtnForm == null) {
            throw new NoRecordException();
        }
		request.setAttribute(getFormName(rtnForm, request), rtnForm);
		request.setAttribute("isAvailable",isAvailable);
		request.setAttribute("readOnly",isReadOnly);
		
	}

	/**
	 * 根据modelID获取标签（view页面）
	 * 
	 * @param modelId
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private SysTagMainForm findTagByModelId(String modelId,
			HttpServletRequest request) throws Exception {
		SysTagMainForm tagForm = null;
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock(
				"sysTagMain.fdModelId = :fdModelId and sysTagMain.fdModelName = :fdModelName");
		hqlInfo.setOrderBy("sysTagMain.docAlterTime desc");
		hqlInfo.setParameter("fdModelId", modelId);
		hqlInfo.setParameter("fdModelName",
				"com.landray.kmss.sys.zone.model.SysZonePersonInfo");
		List<SysTagMain> list = getSysTagMainService().findPage(hqlInfo)
				.getList();
		if (!list.isEmpty()) {
			tagForm = (SysTagMainForm) getSysTagMainService()
					.convertModelToForm(null, list.get(0),
							new RequestContext(request));
		}
		return tagForm;
	}

	/**
	 * 根据modelId获取当前用户的标签
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getTagsByModelId(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-getTagsByModelId", true, getClass());
		KmssMessages messages = new KmssMessages();
		JSONArray array = new JSONArray();
		try {
			String modelId = request.getParameter("modelId");
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock(
					"sysTagMain.fdModelId = :fdModelId and sysTagMain.fdModelName = :fdModelName");
			hqlInfo.setOrderBy("sysTagMain.docAlterTime desc");
			hqlInfo.setParameter("fdModelId", modelId);
			hqlInfo.setParameter("fdModelName",
					"com.landray.kmss.sys.zone.model.SysZonePersonInfo");
			List<SysTagMain> list = getSysTagMainService().findPage(hqlInfo)
					.getList();
			if (!list.isEmpty()) {
				SysTagMain sysTagMain = list.get(0);
				List<SysTagMainRelation> sysTagMainRelationList = sysTagMain
						.getSysTagMainRelationList();
				for (SysTagMainRelation sysTagMainRelation : sysTagMainRelationList) {
					JSONObject obj = new JSONObject();
					obj.put("id", sysTagMainRelation.getFdId());
					obj.put("value", sysTagMainRelation.getFdTagName());
					array.add(obj);
				}
			}
		} catch (Exception e) {
			messages.addError(e);
		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(array.toString());
		response.getWriter().flush();
		response.getWriter().close();
		return null;
	}

	/**
	 * 获取所有有效标签（筛选器）
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getTags(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-getTags", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setRowSize(Integer.MAX_VALUE);
			hqlInfo.setOrderBy("sysTagTags.fdCountQuoteTimes desc");
			hqlInfo.setWhereBlock(
					"sysTagTags.fdStatus = 1 and sysTagTags.fdIsPrivate = 1");
			List<SysTagTags> sysTagTags = getSysTagTagsService().findList(
					hqlInfo);
			JSONArray array = new JSONArray();
			for (SysTagTags sysTagTag : sysTagTags) {
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("text", sysTagTag.getFdName());
				jsonObj.put("value", sysTagTag.getFdName());
				array.add(jsonObj);
			}
			request.setAttribute("lui-source", array);
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-getTags", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("lui-source");
		}
	}

	public void getDocCount(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String fdPersonInfoId=request.getParameter("personInfoId");
		
		JSONArray array = getServiceImp(request).getDocCount(fdPersonInfoId);

		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(array);
		response.getWriter().flush();
		response.getWriter().close();
	}

	/**
	 * 查询员工是否存在
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void isExist(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String isExist = "false";
		String fdPersonInfoId = request.getParameter("fdPersonInfoId");
		IBaseModel model = getServiceImp(request).findByPrimaryKey(
				fdPersonInfoId, null, true);
		if (model != null) {
			isExist = "true";
		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(isExist);
		response.getWriter().flush();
		response.getWriter().close();
	}
	
	public String  getLastBirthday(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map map = getSysAppConfigService()
				.findByKey(
						"com.landray.kmss.hr.staff.model.HrStaffAlertWarningBirthday");
		String alertDate = (String) map.get("cycleReminder");
		String staffReminder = (String) map.get("staffReminder");
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("MM-dd");
		String data="";
		if ("true".equals(staffReminder)) {
			if ("week".equals(alertDate)) {
				String startDate = formatter.format(HrStaffDateUtil
						.getTimesWeekmorning());
				String endDate = formatter.format(HrStaffDateUtil
						.getTimesWeeknight());
				data=startDate+"_"+endDate;
			} else if ("month".equals(alertDate)) {
				String startDate = formatter.format(HrStaffDateUtil
						.getTimesMonthmorning());
				String endDate = formatter.format(HrStaffDateUtil
						.getTimesMonthnight());
				data=startDate+"_"+endDate;
			} else if ("twoMonth".equals(alertDate)) {
				String startDate = formatter.format(HrStaffDateUtil
						.getTimesMonthmorning());
				String endDate = formatter.format(HrStaffDateUtil
						.getTimeLastMonthLast());
				data=startDate+"_"+endDate;
			} else if ("quarter".equals(alertDate)) {
				String startDate = formatter.format(HrStaffDateUtil
						.getFirstDayOfQuarter());
				String endDate = formatter.format(HrStaffDateUtil
						.getLastDayOfQuarter());
				data=startDate+"_"+endDate;
			}
		} else {
			// 如果不开启提醒功能，默认为全年数据
			data = "01-01_12-31";
		}
		
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(data);
		response.getWriter().flush();
		response.getWriter().close();
		return null;
		
	}

	// 显示最近生日员工页面：要求默认根据后台所选的提醒周期筛选
	public ActionForward lastBirthdayShow(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
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
			Map map = getSysAppConfigService()
					.findByKey(
							"com.landray.kmss.hr.staff.model.HrStaffAlertWarningBirthday");
			String alertDate = (String) map.get("cycleReminder");
			String staffReminder = (String) map.get("staffReminder");
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat formatter = new SimpleDateFormat("MM-dd");
			if ("true".equals(staffReminder)) {
				if ("week".equals(alertDate)) {
					String startDate = formatter.format(HrStaffDateUtil
							.getTimesWeekmorning());
					String endDate = formatter.format(HrStaffDateUtil
							.getTimesWeeknight());
					response
							.sendRedirect(request.getContextPath()
									+ "/hr/staff/hr_staff_person_info/index.jsp?type=warningBirthday#cri.q=_fdStatus:trial;_fdStatus:official;_fdStatus:temporary;_fdStatus:trialDelay;_fdStatus:retire;fdBirthdayOfYear:"
									+ startDate + ";fdBirthdayOfYear:"
									+ endDate);
					return null;
				} else if ("month".equals(alertDate)) {
					String startDate = formatter.format(HrStaffDateUtil
							.getTimesMonthmorning());
					String endDate = formatter.format(HrStaffDateUtil
							.getTimesMonthnight());
					response
							.sendRedirect(request.getContextPath()
									+ "/hr/staff/hr_staff_person_info/index.jsp?type=warningBirthday#cri.q=_fdStatus:trial;_fdStatus:official;_fdStatus:temporary;_fdStatus:trialDelay;_fdStatus:retire;fdBirthdayOfYear:"
									+ startDate + ";fdBirthdayOfYear:"
									+ endDate);
					return null;
				} else if ("twoMonth".equals(alertDate)) {
					String startDate = formatter.format(HrStaffDateUtil
							.getTimesMonthmorning());
					String endDate = formatter.format(HrStaffDateUtil
							.getTimeLastMonthLast());
					response
							.sendRedirect(request.getContextPath()
									+ "/hr/staff/hr_staff_person_info/index.jsp?type=warningBirthday#cri.q=_fdStatus:trial;_fdStatus:official;_fdStatus:temporary;_fdStatus:trialDelay;_fdStatus:retire;fdBirthdayOfYear:"
									+ startDate + ";fdBirthdayOfYear:"
									+ endDate);
					return null;
				} else if ("quarter".equals(alertDate)) {
					String startDate = formatter.format(HrStaffDateUtil
							.getFirstDayOfQuarter());
					String endDate = formatter.format(HrStaffDateUtil
							.getLastDayOfQuarter());
					response
							.sendRedirect(request.getContextPath()
									+ "/hr/staff/hr_staff_person_info/index.jsp?type=warningBirthday#cri.q=_fdStatus:trial;_fdStatus:official;_fdStatus:temporary;_fdStatus:trialDelay;_fdStatus:retire;fdBirthdayOfYear:"
									+ startDate + ";fdBirthdayOfYear:"
									+ endDate);
					return null;
				}
			}
			return getActionForward("birthday_show", mapping, form, request,
					response);
		}
	}

	public String  getTrialExpiration(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map map = getSysAppConfigService().findByKey(
				"com.landray.kmss.hr.staff.model.HrStaffAlertWarningTrial");
		String alertDate = (String) map.get("cycleReminder");
		String staffReminder = (String) map.get("staffReminder");
		Calendar cal = Calendar.getInstance();
		//SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String data=null;
		if ("true".equals(staffReminder)) {
			if ("week".equals(alertDate)) {
				String startDate = DateUtil.convertDateToString(HrStaffDateUtil
						.getTimesWeekmorning(), DateUtil.TYPE_DATE, UserUtil.getKMSSUser().getLocale());
				String endDate = DateUtil.convertDateToString(HrStaffDateUtil
						.getTimesWeeknight(), DateUtil.TYPE_DATE, UserUtil.getKMSSUser().getLocale());
				
						data=startDate+"_"+endDate;
			} else if ("month".equals(alertDate)) {
				String startDate = DateUtil.convertDateToString(HrStaffDateUtil
						.getTimesMonthmorning(), DateUtil.TYPE_DATE, UserUtil.getKMSSUser().getLocale());
				String endDate = DateUtil.convertDateToString(HrStaffDateUtil
						.getTimesMonthnight(), DateUtil.TYPE_DATE, UserUtil.getKMSSUser().getLocale());
				
				data=startDate+"_"+endDate;
			} else if ("twoMonth".equals(alertDate)) {
				String startDate = DateUtil.convertDateToString(HrStaffDateUtil
						.getTimesMonthmorning(), DateUtil.TYPE_DATE, UserUtil.getKMSSUser().getLocale());
				String endDate = DateUtil.convertDateToString(HrStaffDateUtil
						.getTimeLastMonthLast(), DateUtil.TYPE_DATE, UserUtil.getKMSSUser().getLocale());

				data=startDate+"_"+endDate;
			} else if ("quarter".equals(alertDate)) {
				String startDate = DateUtil.convertDateToString(HrStaffDateUtil
						.getFirstDayOfQuarter(), DateUtil.TYPE_DATE, UserUtil.getKMSSUser().getLocale());
				String endDate = DateUtil.convertDateToString(HrStaffDateUtil
						.getLastDayOfQuarter(), DateUtil.TYPE_DATE, UserUtil.getKMSSUser().getLocale());

				data=startDate+"_"+endDate;
			}
		} else {
			// 如果不开启提醒功能，默认为全年数据
			int year = cal.get(Calendar.YEAR);
			cal.set(year, 0, 1);
			String startDate = DateUtil.convertDateToString(cal.getTime(), DateUtil.TYPE_DATE, UserUtil.getKMSSUser().getLocale());
			cal.set(year, 11, 31);
			String endDate = DateUtil.convertDateToString(cal.getTime(), DateUtil.TYPE_DATE, UserUtil.getKMSSUser().getLocale());
			
			data = startDate+"_"+endDate;

		}
		
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(data);
		response.getWriter().flush();
		response.getWriter().close();
		return null;
		
	}
	// 显示最近试用期到期员工页面：要求默认根据后台所选的提醒周期筛选
	public ActionForward trialExpirationShow(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
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
			Map map = getSysAppConfigService().findByKey(
					"com.landray.kmss.hr.staff.model.HrStaffAlertWarningTrial");
			String alertDate = (String) map.get("cycleReminder");
			String staffReminder = (String) map.get("staffReminder");
			Calendar cal = Calendar.getInstance();
			//SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			if ("true".equals(staffReminder)) {
				if ("week".equals(alertDate)) {
					String startDate = DateUtil.convertDateToString(HrStaffDateUtil
							.getTimesWeekmorning(), DateUtil.TYPE_DATE, UserUtil.getKMSSUser().getLocale());
					String endDate = DateUtil.convertDateToString(HrStaffDateUtil
							.getTimesWeeknight(), DateUtil.TYPE_DATE, UserUtil.getKMSSUser().getLocale());

					response
							.sendRedirect(request.getContextPath()
									+ "/hr/staff/hr_staff_person_info/index.jsp?type=warningTrial#cri.q=_fdStatus:trial;_fdStatus:trialDelay;fdTrialExpirationTime:"
									+ startDate + ";fdTrialExpirationTime:"
									+ endDate);
					return null;
				} else if ("month".equals(alertDate)) {
					String startDate = DateUtil.convertDateToString(HrStaffDateUtil
							.getTimesMonthmorning(), DateUtil.TYPE_DATE, UserUtil.getKMSSUser().getLocale());
					String endDate = DateUtil.convertDateToString(HrStaffDateUtil
							.getTimesMonthnight(), DateUtil.TYPE_DATE, UserUtil.getKMSSUser().getLocale());

					response
							.sendRedirect(request.getContextPath()
									+ "/hr/staff/hr_staff_person_info/index.jsp?type=warningTrial#cri.q=_fdStatus:trial;_fdStatus:trialDelay;fdTrialExpirationTime:"
									+ startDate + ";fdTrialExpirationTime:"
									+ endDate);
					return null;
				} else if ("twoMonth".equals(alertDate)) {
					String startDate = DateUtil.convertDateToString(HrStaffDateUtil
							.getTimesMonthmorning(), DateUtil.TYPE_DATE, UserUtil.getKMSSUser().getLocale());
					String endDate = DateUtil.convertDateToString(HrStaffDateUtil
							.getTimeLastMonthLast(), DateUtil.TYPE_DATE, UserUtil.getKMSSUser().getLocale());

					response
							.sendRedirect(request.getContextPath()
									+ "/hr/staff/hr_staff_person_info/index.jsp?type=warningTrial#cri.q=_fdStatus:trial;_fdStatus:trialDelay;fdTrialExpirationTime:"
									+ startDate + ";fdTrialExpirationTime:"
									+ endDate);
					return null;
				} else if ("quarter".equals(alertDate)) {
					String startDate = DateUtil.convertDateToString(HrStaffDateUtil
							.getFirstDayOfQuarter(), DateUtil.TYPE_DATE, UserUtil.getKMSSUser().getLocale());
					String endDate = DateUtil.convertDateToString(HrStaffDateUtil
							.getLastDayOfQuarter(), DateUtil.TYPE_DATE, UserUtil.getKMSSUser().getLocale());

					response
							.sendRedirect(request.getContextPath()
									+ "/hr/staff/hr_staff_person_info/index.jsp?type=warningTrial#cri.q=_fdStatus:trial;_fdStatus:trialDelay;fdTrialExpirationTime:"
									+ startDate + ";fdTrialExpirationTime:"
									+ endDate);
					return null;
				}
			}
			return getActionForward("trial_show", mapping, form, request,
					response);
		}
	}

	
	public String  getContractExpiration(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map map = getSysAppConfigService()
				.findByKey(
						"com.landray.kmss.hr.staff.model.HrStaffAlertWarningContract");
		String alertDate = (String) map.get("cycleReminder");
		String staffReminder = (String) map.get("staffReminder");
		Calendar cal = Calendar.getInstance();
		//SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String data=null;
		if ("true".equals(staffReminder)) {
			if ("week".equals(alertDate)) {
				String startDate = DateUtil.convertDateToString(HrStaffDateUtil
						.getTimesWeekmorning(), DateUtil.TYPE_DATE, UserUtil.getKMSSUser().getLocale());
				String endDate = DateUtil.convertDateToString(HrStaffDateUtil
						.getTimesWeeknight(), DateUtil.TYPE_DATE, UserUtil.getKMSSUser().getLocale());

				data=startDate+"_"+endDate;
			} else if ("month".equals(alertDate)) {
				String startDate = DateUtil.convertDateToString(HrStaffDateUtil
						.getTimesMonthmorning(), DateUtil.TYPE_DATE, UserUtil.getKMSSUser().getLocale());
				String endDate = DateUtil.convertDateToString(HrStaffDateUtil
						.getTimesMonthnight(), DateUtil.TYPE_DATE, UserUtil.getKMSSUser().getLocale());

				data=startDate+"_"+endDate;
			} else if ("twoMonth".equals(alertDate)) {
				String startDate = DateUtil.convertDateToString(HrStaffDateUtil
						.getTimesMonthmorning(), DateUtil.TYPE_DATE, UserUtil.getKMSSUser().getLocale());
				String endDate = DateUtil.convertDateToString(HrStaffDateUtil
						.getTimeLastMonthLast(), DateUtil.TYPE_DATE, UserUtil.getKMSSUser().getLocale());

				data=startDate+"_"+endDate;
			} else if ("quarter".equals(alertDate)) {
				String startDate = DateUtil.convertDateToString(HrStaffDateUtil
						.getFirstDayOfQuarter(), DateUtil.TYPE_DATE, UserUtil.getKMSSUser().getLocale());
				String endDate = DateUtil.convertDateToString(HrStaffDateUtil
						.getLastDayOfQuarter(), DateUtil.TYPE_DATE, UserUtil.getKMSSUser().getLocale());

				data=startDate+"_"+endDate;
			}
		} else {
			// 如果不开启提醒功能，默认为全年数据
			int year = cal.get(Calendar.YEAR);
			cal.set(year, 0, 1);
			String startDate = DateUtil.convertDateToString(cal.getTime(), DateUtil.TYPE_DATE, UserUtil.getKMSSUser().getLocale());
			cal.set(year, 11, 31);
			String endDate = DateUtil.convertDateToString(cal.getTime(), DateUtil.TYPE_DATE, UserUtil.getKMSSUser().getLocale());
			
			data = startDate+"_"+endDate;

		}
		
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(data);
		response.getWriter().flush();
		response.getWriter().close();
		return null;
		
	}
	// 显示最近合同到期员工页面：要求默认根据后台所选的提醒周期筛选
	public ActionForward contractExpirationShow(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
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
			Map map = getSysAppConfigService()
					.findByKey(
							"com.landray.kmss.hr.staff.model.HrStaffAlertWarningContract");
			String alertDate = (String) map.get("cycleReminder");
			String staffReminder = (String) map.get("staffReminder");
			Calendar cal = Calendar.getInstance();
			//SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			if ("true".equals(staffReminder)) {
				if ("week".equals(alertDate)) {
					String startDate = DateUtil.convertDateToString(HrStaffDateUtil
							.getTimesWeekmorning(), DateUtil.TYPE_DATE, UserUtil.getKMSSUser().getLocale());
					String endDate = DateUtil.convertDateToString(HrStaffDateUtil
							.getTimesWeeknight(), DateUtil.TYPE_DATE, UserUtil.getKMSSUser().getLocale());

					response
							.sendRedirect(request.getContextPath()
									+ "/hr/staff/hr_staff_person_info/index.jsp?type=warningContract#cri.q=_fdStatus:trial;_fdStatus:official;_fdStatus:temporary;_fdStatus:trialDelay;fdEndDate:"
									+ startDate + ";fdEndDate:" + endDate);
					return null;
				} else if ("month".equals(alertDate)) {
					String startDate = DateUtil.convertDateToString(HrStaffDateUtil
							.getTimesMonthmorning(), DateUtil.TYPE_DATE, UserUtil.getKMSSUser().getLocale());
					String endDate = DateUtil.convertDateToString(HrStaffDateUtil
							.getTimesMonthnight(), DateUtil.TYPE_DATE, UserUtil.getKMSSUser().getLocale());

					response
							.sendRedirect(request.getContextPath()
									+ "/hr/staff/hr_staff_person_info/index.jsp?type=warningContract#cri.q=_fdStatus:trial;_fdStatus:official;_fdStatus:temporary;_fdStatus:trialDelay;fdEndDate:"
									+ startDate + ";fdEndDate:" + endDate);
					return null;
				} else if ("twoMonth".equals(alertDate)) {
					String startDate = DateUtil.convertDateToString(HrStaffDateUtil
							.getTimesMonthmorning(), DateUtil.TYPE_DATE, UserUtil.getKMSSUser().getLocale());
					String endDate = DateUtil.convertDateToString(HrStaffDateUtil
							.getTimeLastMonthLast(), DateUtil.TYPE_DATE, UserUtil.getKMSSUser().getLocale());

					response
							.sendRedirect(request.getContextPath()
									+ "/hr/staff/hr_staff_person_info/index.jsp?type=warningContract#cri.q=_fdStatus:trial;_fdStatus:official;_fdStatus:temporary;_fdStatus:trialDelay;fdEndDate:"
									+ startDate + ";fdEndDate:" + endDate);
					return null;
				} else if ("quarter".equals(alertDate)) {
					String startDate = DateUtil.convertDateToString(HrStaffDateUtil
							.getFirstDayOfQuarter(), DateUtil.TYPE_DATE, UserUtil.getKMSSUser().getLocale());
					String endDate = DateUtil.convertDateToString(HrStaffDateUtil
							.getLastDayOfQuarter(), DateUtil.TYPE_DATE, UserUtil.getKMSSUser().getLocale());

					response
							.sendRedirect(request.getContextPath()
									+ "/hr/staff/hr_staff_person_info/index.jsp?type=warningContract#cri.q=_fdStatus:trial;_fdStatus:official;_fdStatus:temporary;_fdStatus:trialDelay;fdEndDate:"
									+ startDate + ";fdEndDate:" + endDate);
					return null;
				}
			}
			return getActionForward("contract_show", mapping, form, request,
					response);
		}
	}

	/**
	 * 打印
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward print(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		view(mapping, form, request, response);

		// 获取“薪酬福利”信息
		String personInfoId = request.getParameter("fdId");
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("fdPersonInfo.fdId = :personInfoId");
		hqlInfo.setParameter("personInfoId", personInfoId);
		List<HrStaffEmolumentWelfare> list = getHrStaffEmolumentWelfareService()
				.findPage(hqlInfo).getList();
		if (!list.isEmpty()) {
			request.setAttribute("hrStaffEmolumentWelfare", list.get(0));
			UserOperHelper.logFind(list.get(0));// 添加日志信息
		}
		return mapping.findForward("print");
	}

	/**
	 * 根据人员ID获取相应的数据
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void dataIntrgrity(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String fdPersonId = request.getParameter("personInfoId");
		String percent = getServiceImp(request).dataIntrgrity(fdPersonId);

		// 子类自己处理JOSN数组
		JSONArray array = new JSONArray();
		JSONObject obj = new JSONObject();
		obj.put("intrgrity", percent);
		array.add(obj);

		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(array);
		response.getWriter().flush();
		response.getWriter().close();
	}
	
	/**
	 * 组织机构同步
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward sync(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();

		try { 
			SynchroOrgEkpToHrImp service = (SynchroOrgEkpToHrImp) getBean("synchroOrgEkpToHr");
			service.synchroAddPersonEkpToHr(null);
			// 添加日志信息
			if (UserOperHelper.allowLogOper("sync",
					getServiceImp(request).getModelName())) {
				UserOperHelper.setEventType(
						ResourceUtil.getString("hr-staff:hr.staff.btn.sync"));
			}
		} catch (Exception e) {
			messages.addError(e);
		}

		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_RETURN).save(request);
		if (messages.hasError()) {
            return mapping.findForward("failure");
        } else {
            return mapping.findForward("success");
        }
	}

	private static Map<String, String> canDownMap = new HashMap<String, String>();

	public ActionForward downloadAjax(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String userId = UserUtil.getUser().getFdId();
		if (!canDownMap.containsKey(userId)) {
			canDownMap.put(userId, "true");
		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().append(canDownMap.get(userId));
		response.getWriter().flush();
		response.getWriter().close();
		return null;
	}

	/**
	 * 导出花名册
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public ActionForward exportPerson(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String userId = UserUtil.getUser().getFdId();
		try {
			canDownMap.put(userId, "false");
			HQLInfo hqlInfo = new HQLInfo();
			// 排序规则：层级长度，层级，排序号，名称
			hqlInfo.setOrderBy(
					"length(hrStaffPersonInfo.fdOrgParent.fdHierarchyId), hrStaffPersonInfo.fdOrgParent.fdHierarchyId, hrStaffPersonInfo.fdOrgParent.fdOrder, hrStaffPersonInfo.fdOrgPerson.fdOrder, hrStaffPersonInfo.fdOrgPerson."
							+ SysLangUtil.getLangFieldName("fdName"));
			String personStatus = request.getParameter("personStatus");
			StringBuffer whereBlock = new StringBuffer();
//			if (StringUtil.isNotNull(personStatus)
//					&& "quit".equals(personStatus)) {
//				whereBlock.append(
//						"hrStaffPersonInfo.fdStatus in ('dismissal','leave','retire')");
//			} else {
//				whereBlock.append(
//						"hrStaffPersonInfo.fdStatus in ('trial','official','temporary','trialDelay','practice')");
//			}
			whereBlock.append(
					"hrStaffPersonInfo.fdStatus in ('trial','official','temporary','trialDelay','practice','dismissal','leave','retire')");
			
			String fdDeptId = request.getParameter("fdDeptId");
			List<String> fdDeptIds = null;
			if (StringUtil.isNotNull(fdDeptId)) {
				//排除admin
				if (UserUtil.getKMSSUser().isAdmin()
						|| UserUtil.checkRole("ROLE_HRSTAFF_READALL")) {
					SysOrgElement dept = (SysOrgElement) getSysOrgElementService().findByPrimaryKey(fdDeptId);
					whereBlock.append(" and hrStaffPersonInfo.fdHierarchyId like :fdDeptId");
					hqlInfo.setParameter("fdDeptId", "%" + dept.getFdHierarchyId() + "%");
				}else {
					// 是否具有该部门权限
					List<String> list = HrStaffAuthorityUtil.obtainOrgAuth();
					// 获取该部门的所有子部门
					fdDeptIds = HrStaffAuthorityUtil.getchildDept(fdDeptId);
					if (fdDeptIds.contains(fdDeptId)) {
						// 有权限
						whereBlock = HrStaffAuthorityUtil.getLeavePerson(
								whereBlock,
								fdDeptIds, hqlInfo);
					} else {
						// 没权限
						whereBlock.append(" and 1=2");
					}
				}
			}
			// 导出所有，仅导出所有管辖范围内的数据
			String warningType = (String) request.getAttribute("warningType");
			if (StringUtil.isNull(warningType) && StringUtil.isNull(fdDeptId)
					&& (!UserUtil.getKMSSUser().isAdmin()
							&& !UserUtil.checkRole("ROLE_HRSTAFF_READALL"))) {
				// 获取有权限的部门数据
				List<String> orgIds = this.getAuthDeptIds();
				whereBlock = HrStaffAuthorityUtil.getLeavePerson(whereBlock,
						orgIds, hqlInfo);
			}
			hqlInfo.setWhereBlock(whereBlock.toString());
			List<HrStaffPersonInfo> rtnList = getServiceImp(request)
					.findList(hqlInfo);
			// 添加日志信息
			UserOperHelper.logFindAll(rtnList,
					getServiceImp(request).getModelName());
			this.getServiceImp(request).exportList(request, response, rtnList);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			canDownMap.remove(userId);
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
	 * @return 执行成功，返回list页面，否则返回failure页面
	 * @throws Exception
	 */
	@Override
	public ActionForward list(ActionMapping mapping, ActionForm form,
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
			if (s_pageno != null && s_pageno.length() > 0 && Integer.parseInt(s_pageno) > 0) {
				pageno = Integer.parseInt(s_pageno);
			}
			if (s_rowsize != null && s_rowsize.length() > 0 && Integer.parseInt(s_rowsize) > 0) {
				rowsize = Integer.parseInt(s_rowsize);
			}

			// 按多语言字段排序
			if (StringUtil.isNotNull(orderby) && form instanceof IExtendForm) {
				Class<?> modelClass = ((IExtendForm) form).getModelClass();
				if (modelClass != null) {
					String langFieldName = SysLangUtil
							.getLangFieldName(modelClass.getName(), orderby);
					if (StringUtil.isNotNull(langFieldName)) {
						orderby = langFieldName;
					}
				}
			}
//			if (isReserve) {
//                orderby += " desc";
//            }
			HQLInfo hqlInfo = new HQLInfo();
//			if (StringUtil.isNotNull(orderby)) {
//				hqlInfo
//						.setOrderBy("hrStaffPersonInfo."
//								+ orderby);
//			}
            orderby = "hrStaffPersonInfo.fdFirstLevelDepartment.fdName,hrStaffPersonInfo.fdSecondLevelDepartment.fdName,hrStaffPersonInfo.fdThirdLevelDepartment.fdName desc";
            hqlInfo
			.setOrderBy(orderby);
			hqlInfo.setPageNo(pageno);
			hqlInfo.setRowSize(rowsize);
			changeFindPageHQLInfo(request, hqlInfo);
			Page page = getServiceImp(request).findPage(hqlInfo);
			List<HrStaffPersonInfo> staffInfoList = page.getList();
			// 添加日志信息
			UserOperHelper.logFindAll(staffInfoList,
					getServiceImp(request).getModelName());
			JSONObject urlJson = new JSONObject();
			String staffId = "";
			for (HrStaffPersonInfo hrStaffPersonInfo : staffInfoList) {
				staffId = hrStaffPersonInfo.getFdId();
				List hrStaffPersonExperienceContracts=hrStaffPersonInfo.getHbmPersonExperienceContract();
				if(!hrStaffPersonExperienceContracts.isEmpty()){
					HrStaffPersonExperienceContract hrStaffPersonExperienceContract = (HrStaffPersonExperienceContract) hrStaffPersonExperienceContracts.get(hrStaffPersonExperienceContracts.size()-1);
					hrStaffPersonInfo.setFdBeginDate(hrStaffPersonExperienceContract.getFdBeginDate());
					if(hrStaffPersonExperienceContract.getFdStaffContType()!=null){
						hrStaffPersonInfo.setFdContType(hrStaffPersonExperienceContract.getFdStaffContType().getFdName());
					}
					hrStaffPersonInfo.setFdContractUnit(hrStaffPersonExperienceContract.getFdContractUnit());
					hrStaffPersonInfo.setFdEndDate(hrStaffPersonExperienceContract.getFdEndDate());
					hrStaffPersonInfo.setFdContractYear(hrStaffPersonExperienceContract.getFdContractYear());
					hrStaffPersonInfo.setFdContractMonth(hrStaffPersonExperienceContract.getFdContractMonth());
				}
				urlJson.put(staffId,
						HrStaffPersonUtil.getImgUrl(hrStaffPersonInfo, request));
			}
			request.setAttribute("queryPage", page);
			request.setAttribute("urlJson", urlJson);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-list", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("list", mapping, form, request, response);
		}
	}

	/**
	 * 人事组织list
	 */
	public ActionForward findPersonList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-list", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String s_pageno = request.getParameter("pageno");
			String s_rowsize = request.getParameter("rowsize");
			String orderby = request.getParameter("orderby");
			String ordertype = request.getParameter("ordertype");
			String orgOrder = request.getParameter("orgOrder");
			boolean isReserve = false;
			if (ordertype != null && "down".equalsIgnoreCase(ordertype)) {
				isReserve = true;
			}
			int pageno = 0;
			int rowsize = SysConfigParameters.getRowSize();
			if (s_pageno != null && s_pageno.length() > 0 && Integer.parseInt(s_pageno) > 0) {
				pageno = Integer.parseInt(s_pageno);
			}
			if (s_rowsize != null && s_rowsize.length() > 0 && Integer.parseInt(s_rowsize) > 0) {
				rowsize = Integer.parseInt(s_rowsize);
			}

			// 按多语言字段排序
			if (StringUtil.isNotNull(orderby) && form instanceof IExtendForm) {
				Class<?> modelClass = ((IExtendForm) form).getModelClass();
				if (modelClass != null) {
					String langFieldName = SysLangUtil.getLangFieldName(modelClass.getName(), orderby);
					if (StringUtil.isNotNull(langFieldName)) {
						orderby = langFieldName;
					}
				}
			}
			if (isReserve) {
                orderby += " desc";
            }
			HQLInfo hqlInfo = new HQLInfo();
			if (StringUtil.isNotNull(orderby)) {
				hqlInfo.setOrderBy("hrStaffPersonInfo." + orderby);

			}
			hqlInfo.setPageNo(pageno);
			hqlInfo.setRowSize(rowsize);
			changeHrOrgFindPageHQLInfo(request, hqlInfo);
			if (StringUtil.isNotNull(orgOrder)) {
				hqlInfo.setOrderBy("hrStaffPersonInfo." + orgOrder);
			}
			Page page = getServiceImp(request).findPersonList(hqlInfo, request);
			List<HrStaffPersonInfo> staffInfoList = page.getList();
			// 添加日志信息
			UserOperHelper.logFindAll(staffInfoList, getServiceImp(request).getModelName());
			request.setAttribute("queryPage", page);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-list", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("hrOrgPersonList", mapping, form, request, response);
		}
	}

	protected void changeHrOrgFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		CriteriaValue cv = new CriteriaValue(request);

		// 以下筛选属性需要手工定义筛选范围
		String _fdKey = cv.poll("_fdKey");
		String[] _fdDepts = cv.polls("_fdDept");
		String[] _fdLabel = cv.polls("_fdLabel");
		String[] _fdPosts = cv.polls("_fdPosts");

		String hqlWhere = hqlInfo.getWhereBlock();
		StringBuffer whereBlock = null;
		String queryPerson = request.getParameter("queryPerson");
		if (StringUtil.isNotNull(hqlWhere)) {
			whereBlock = new StringBuffer(hqlWhere);
		} else {
			whereBlock = new StringBuffer("1 = 1");
		}
		// 姓名、登录名、手机号或邮箱
		if (StringUtil.isNotNull(_fdKey)) {
			// 查询组织机构人员信息
			HQLInfo _hqlInfo = new HQLInfo();
			_hqlInfo.setSelectBlock("sysOrgPerson.fdId");
			_hqlInfo.setWhereBlock(
					"sysOrgPerson.fdName like :fdKey or sysOrgPerson.fdLoginName like :fdKey or sysOrgPerson.fdMobileNo like :fdKey or sysOrgPerson.fdEmail like :fdKey");
			_hqlInfo.setParameter("fdKey", "%" + _fdKey + "%");
			List<String> ids = getSysOrgPersonService().findValue(_hqlInfo);

			whereBlock.append(
					" and ((hrStaffPersonInfo.fdName like :fdKey or hrStaffPersonInfo.fdMobileNo like :fdKey or hrStaffPersonInfo.fdEmail like :fdKey)");
			if (!ids.isEmpty()) {
				whereBlock.append(" or " + HQLUtil.buildLogicIN("hrStaffPersonInfo.fdId", ids));
			}
			whereBlock.append(")");
			hqlInfo.setParameter("fdKey", "%" + _fdKey + "%");
		}

		// 员工状态
		String[] _fdStatus = request.getParameterValues("q._fdStatus");
		String quitStatus = "";
		if (_fdStatus != null && "quit".equals(_fdStatus[0])) {
			_fdStatus = null;
			quitStatus = "quit";
		}
		if (_fdStatus != null && _fdStatus.length > 0) {
			List<String> fdStatus = new ArrayList<String>();
			boolean isNull = false;
			for (String _fdStatu : _fdStatus) {
				if ("official".equals(_fdStatu)) {
					isNull = true;
					whereBlock.append(
							" and (hrStaffPersonInfo.fdStatus in ('trial','official','temporary','trialDelay','practice')");
				}
				fdStatus.add(_fdStatu);
			}
			if (isNull) {
				whereBlock.append(" or hrStaffPersonInfo.fdStatus is null");
			}
			whereBlock.append(")");
			//hqlInfo.setParameter("fdStatus", fdStatus);
		} else {
			//判断在职以及离职，并做区分 addby zhangcc@2017-12-08
			String personStatus = request.getParameter("personStatus");
			String _fdStatusValue = cv.poll("_fdStatus");
			if (StringUtil.isNotNull(quitStatus)) {
				personStatus = quitStatus;
				_fdStatusValue = null;
			}
			if (null != personStatus && "all".equals(personStatus)) {

			} else if (null != personStatus && "positive".equals(personStatus)) {
				// 待转正
				whereBlock.append(" and hrStaffPersonInfo.fdOrgPerson is not null");
				if (StringUtil.isNull(_fdStatusValue)) {
					// 只查询试用或者实习，并且关联了组织架构
					whereBlock.append(" and hrStaffPersonInfo.fdStatus in ('trial','practice')");
				} else {
					whereBlock.append(" and hrStaffPersonInfo.fdStatus = :fdStatusValue");
					hqlInfo.setParameter("fdStatusValue", _fdStatusValue);
				}
			} else if (null != personStatus && "quit".equals(personStatus)) {
				//离职
				if (StringUtil.isNull(_fdStatusValue)) {
					//只查询出解聘、离职、退休三种状态的人员
					whereBlock.append(" and hrStaffPersonInfo.fdStatus in ('dismissal','leave','retire')");
				} else {
					whereBlock.append(" and hrStaffPersonInfo.fdStatus = :fdStatusValue");
					hqlInfo.setParameter("fdStatusValue", _fdStatusValue);
				}
			} else {
					// 在职
					if (StringUtil.isNull(_fdStatusValue)) {
						// 只查询出解聘、离职、退休三种状态的人员
						whereBlock.append(
							" and hrStaffPersonInfo.fdStatus in ('trial','official','temporary','trialDelay','practice','dismissal','leave','retire')");
					} else {
						whereBlock.append(" and hrStaffPersonInfo.fdStatus = :fdStatusValue");
						hqlInfo.setParameter("fdStatusValue", _fdStatusValue);
					}
			}
		}

		String fdName = request.getParameter("fdName");
		if (StringUtil.isNotNull(fdName)) {
			whereBlock.append(" and hrStaffPersonInfo.fdName like:fdName");
			hqlInfo.setParameter("fdName", "%" + fdName + "%");
		}

		// 所属岗位
		if (_fdPosts != null && _fdPosts.length > 0) {
			// 先查询岗位
			hqlInfo.setJoinBlock("left join hrStaffPersonInfo.fdOrgPosts fdPost");
			whereBlock.append(" and (" + HQLUtil.buildLogicIN("fdPost.fdId", Arrays.asList(_fdPosts)) + ")");
		}

		if (!UserUtil.checkRole("ROLE_HRORGANIZATION_ORG_ADMIN")) {
			whereBlock = HrStaffAuthorityUtil.builtWhereBlock(whereBlock, "hrStaffPersonInfo", hqlInfo);
		}
		// 组织架构数据查询
		String IsHrOrg = request.getParameter("IsHrOrg");
		String fdParentId = request.getParameter("fdParentId");
		String orgType = request.getParameter("fdOrgType");

		if (StringUtil.isNotNull(fdParentId)) {
			whereBlock.append(" and hrStaffPersonInfo.hbmParent.fdId=:fdParentId ");
			hqlInfo.setParameter("fdParentId", fdParentId);
		}
		if (StringUtil.isNotNull(orgType)) {
			Integer fdOrgType = Integer.parseInt(orgType);
			whereBlock.append(" and hrStaffPersonInfo.fdOrgType=:fdOrgType ");
			hqlInfo.setParameter("fdOrgType", fdOrgType);
		}
		// 查询所有下级员工
		whereBlock.append(
				"and hrStaffPersonInfo.hbmParent.fdId=:fdParentId and hrStaffPersonInfo.fdOrgType=:orgPerson");
		hqlInfo.setParameter("fdParentId", queryPerson);
		// 值可以换成常量
		hqlInfo.setParameter("orgPerson", 8);

		hqlInfo.setWhereBlock(whereBlock.toString());
		// 解析其它筛选属性
		CriteriaUtil.buildHql(cv, hqlInfo, HrStaffPersonInfo.class);
	}

	public ActionForward getPersonInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		JSONObject obj = new JSONObject();
		String id = request.getParameter("id");
		HrStaffPersonInfo personInfo = (HrStaffPersonInfo) getServiceImp(request).updateGetPersonInfo(id);
		SysOrgElement sysOrgElement = (SysOrgElement) getSysOrgElementService().findByPrimaryKey(id, null, true);
		if (personInfo != null && sysOrgElement != null) {
			// 所在部门
			SysOrgElement fdOrgParent = sysOrgElement.getFdParent();
			if (fdOrgParent != null) {
				JSONObject dept = new JSONObject();
				dept.put("id", fdOrgParent.getFdId());
				dept.put("name", fdOrgParent.getFdName());
				obj.put("dept", dept);
			}
			// 所属岗位
			List<SysOrgPost> fdOrgPosts = sysOrgElement.getFdPosts();
			if (!fdOrgPosts.isEmpty()) {
				if ("leavepost".equals(request.getParameter("type"))) {
					JSONArray posts = new JSONArray();
					for (SysOrgPost p : fdOrgPosts) {
						JSONObject pJson = new JSONObject();
						pJson.put("id", p.getFdId());
						pJson.put("name", p.getFdName());
						posts.add(pJson);
					}
					obj.put("posts", posts);
				} else {
					JSONObject post = new JSONObject();
					for (SysOrgPost p : fdOrgPosts) {
						if (fdOrgParent != null
								&& fdOrgParent.equals(p.getFdParent())) {
							post.put("id", p.getFdId());
							post.put("name", p.getFdName());
							obj.put("post", post);
							break;
						}
					}
					if (!post.containsKey("id")) {
						SysOrgPost p = fdOrgPosts.get(0);
						post.put("id", p.getFdId());
						post.put("name", p.getFdName());
						obj.put("post", post);
					}
				}
			}
			// 职级
			SysOrganizationStaffingLevel fdStaffingLevel = personInfo
					.getFdStaffingLevel();
			if (fdStaffingLevel != null) {
				JSONObject level = new JSONObject();
				level.put("id", fdStaffingLevel.getFdId());
				level.put("name", fdStaffingLevel.getFdName());
				obj.put("level", level);
			}
			// 入职日期
			obj.put("entryTime",
					DateUtil.convertDateToString(personInfo.getFdEntryTime(),
							DateUtil.TYPE_DATE, request.getLocale()));
			// 拟转正日期
			obj.put("trialTime",
					DateUtil.convertDateToString(
							personInfo.getFdTrialExpirationTime(),
							DateUtil.TYPE_DATE, request.getLocale()));
			// 试用期限（月）
			obj.put("trialPeriod", personInfo.getFdTrialOperationPeriod());
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock(
					"hrStaffEmolumentWelfareDetalied.fdPersonInfo=:fdPersonInfo");
			hqlInfo.setParameter("fdPersonInfo", personInfo);
			hqlInfo.setOrderBy(
					"hrStaffEmolumentWelfareDetalied.fdAdjustDate desc");
			List<HrStaffEmolumentWelfareDetalied> list = getHrStaffEmolumentWelfareDetaliedService()
					.findList(hqlInfo);
			if (list != null && !list.isEmpty()) {
				HrStaffEmolumentWelfareDetalied detail = list.get(0);
				if (detail.getFdAfterEmolument() != null) {
                    obj.put("salary", detail.getFdAfterEmolument());
                }
			}
		}
		//汇报上级
		if (null != personInfo.getFdReportLeader()) {
			obj.put("fdReportLeaderId", personInfo.getFdReportLeader().getFdId());
			obj.put("fdReportLeaderName", personInfo.getFdReportLeader().getFdName());
		} else if (null != personInfo.getFdHrReportLeader()) {
			obj.put("fdReportLeaderId", personInfo.getFdHrReportLeader().getFdId());
			obj.put("fdReportLeaderName", personInfo.getFdHrReportLeader().getFdName());
		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(obj);
		response.getWriter().flush();
		response.getWriter().close();
		return null;
	}

	/**
	 * <p>转正管理列表页面</p>
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author sunj
	 */
	public ActionForward positiveManageList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
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
			if (s_pageno != null && s_pageno.length() > 0 && Integer.parseInt(s_pageno) > 0) {
				pageno = Integer.parseInt(s_pageno);
			}
			if (s_rowsize != null && s_rowsize.length() > 0 && Integer.parseInt(s_rowsize) > 0) {
				rowsize = Integer.parseInt(s_rowsize);
			}

			// 按多语言字段排序
			if (StringUtil.isNotNull(orderby) && form instanceof IExtendForm) {
				Class<?> modelClass = ((IExtendForm) form).getModelClass();
				if (modelClass != null) {
					String langFieldName = SysLangUtil.getLangFieldName(modelClass.getName(), orderby);
					if (StringUtil.isNotNull(langFieldName)) {
						orderby = langFieldName;
					}
				}
			}
			if (isReserve) {
                orderby += " desc";
            }
			HQLInfo hqlInfo = new HQLInfo();
			if (StringUtil.isNotNull(orderby)) {
				hqlInfo.setOrderBy("hrStaffPersonInfo." + orderby);
			}
			hqlInfo.setPageNo(pageno);
			hqlInfo.setRowSize(rowsize);
			changeFindPageHQLInfo(request, hqlInfo);
			Page page = getServiceImp(request).findPage(hqlInfo);
			List<HrStaffPersonInfo> staffInfoList = page.getList();
			// 添加日志信息
			UserOperHelper.logFindAll(staffInfoList, getServiceImp(request).getModelName());
			JSONObject urlJson = new JSONObject();
			String staffId = "";
			for (HrStaffPersonInfo hrStaffPersonInfo : staffInfoList) {
				staffId = hrStaffPersonInfo.getFdId();
				urlJson.put(staffId, HrStaffPersonUtil.getImgUrl(hrStaffPersonInfo, request));
			}
			request.setAttribute("queryPage", page);
			request.setAttribute("urlJson", urlJson);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-list", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("positiveManage", mapping, form, request, response);
		}
	}

	/**
	 * <p>人事合同</p>
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author sunj
	 */
	public ActionForward contractManageList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
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
			if (s_pageno != null && s_pageno.length() > 0 && Integer.parseInt(s_pageno) > 0) {
				pageno = Integer.parseInt(s_pageno);
			}
			if (s_rowsize != null && s_rowsize.length() > 0 && Integer.parseInt(s_rowsize) > 0) {
				rowsize = Integer.parseInt(s_rowsize);
			}

			// 按多语言字段排序
			if (StringUtil.isNotNull(orderby) && form instanceof IExtendForm) {
				Class<?> modelClass = ((IExtendForm) form).getModelClass();
				if (modelClass != null) {
					String langFieldName = SysLangUtil.getLangFieldName(modelClass.getName(), orderby);
					if (StringUtil.isNotNull(langFieldName)) {
						orderby = langFieldName;
					}
				}
			}
			if (isReserve) {
                orderby += " desc";
            }
			HQLInfo hqlInfo = new HQLInfo();
			if (StringUtil.isNotNull(orderby)) {
				hqlInfo.setOrderBy("hrStaffPersonInfo." + orderby);
			}
			hqlInfo.setPageNo(pageno);
			hqlInfo.setRowSize(rowsize);
			contractManagePageHQLInfo(request, hqlInfo);
			Page page = getServiceImp(request).findPage(hqlInfo);
			List<HrStaffPersonInfo> staffInfoList = page.getList();
			// 添加日志信息
			UserOperHelper.logFindAll(staffInfoList, getServiceImp(request).getModelName());
			JSONObject urlJson = new JSONObject();
			String staffId = "";
			for (HrStaffPersonInfo hrStaffPersonInfo : staffInfoList) {
				staffId = hrStaffPersonInfo.getFdId();
				urlJson.put(staffId, HrStaffPersonUtil.getImgUrl(hrStaffPersonInfo, request));
			}
			request.setAttribute("queryPage", page);
			request.setAttribute("urlJson", urlJson);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-list", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("contractManage", mapping, form, request, response);
		}
	}

	private void contractManagePageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
		changeFindPageHQLInfo(request, hqlInfo);
	    String fdSignType = request.getParameter("fdSignType");
	    String[] fdBeginDate = request.getParameterValues("q.fdBeginDate");
	    String[] fdEndDate = request.getParameterValues("q.fdEndDate");
	    
	    String contractWhere =""; 
	    StringBuilder contractWhereBlock =new StringBuilder(" ( select hrStaffPersonExperienceContract.fdPersonInfo.fdId from HrStaffPersonExperienceContract hrStaffPersonExperienceContract where hrStaffPersonExperienceContract.fdContStatus = '1' ");
	    if ("1".equals(fdSignType)) {
	      contractWhere =" hrStaffPersonInfo.fdId in ";
	      // 在有签订合同里面的人员过滤，否则查询未签订合同就是排除有合同的人
	      if (fdBeginDate != null && fdBeginDate.length > 1) {
	        if (StringUtil.isNotNull(fdBeginDate[0])) {
	          
	          contractWhereBlock.append(" and (hrStaffPersonExperienceContract.fdBeginDate >= :fdBeginDateBegin )"); 
	          hqlInfo.setParameter("fdBeginDateBegin", DateUtil.convertStringToDate(fdBeginDate[0], DateUtil.TYPE_DATE, request.getLocale()));
	        }
	        if (StringUtil.isNotNull(fdBeginDate[1])) {
	          contractWhereBlock.append(" and (hrStaffPersonExperienceContract.fdBeginDate <= :fdBeginDateEnd )");  
	          hqlInfo.setParameter("fdBeginDateEnd",DateUtil.convertStringToDate(fdBeginDate[1], DateUtil.TYPE_DATE, request.getLocale()));
	        }
	      }
	      if (fdEndDate != null && fdEndDate.length > 1) {
	        if (StringUtil.isNotNull(fdEndDate[0])) {
	          contractWhereBlock.append(" and (hrStaffPersonExperienceContract.fdEndDate >= :fdEndDateBegin )"); 
	          hqlInfo.setParameter("fdEndDateBegin", DateUtil.convertStringToDate(fdEndDate[0], DateUtil.TYPE_DATE, request.getLocale()));
	        }
	        if (StringUtil.isNotNull(fdEndDate[1])) {
	          contractWhereBlock.append(" and (hrStaffPersonExperienceContract.fdEndDate <= :fdEndDateEnd )"); 
	          hqlInfo.setParameter("fdEndDateEnd",DateUtil.convertStringToDate(fdEndDate[1], DateUtil.TYPE_DATE, request.getLocale()));
	        }
	      } 
	    } else {
	      contractWhere =" hrStaffPersonInfo.fdId not in ";
	    }
	    contractWhereBlock.append(") "); 
	    String whereBlock =hqlInfo.getWhereBlock();
	    whereBlock = StringUtil.linkString(whereBlock, " and ", contractWhere); 
	    whereBlock = StringUtil.linkString(whereBlock, "  ", contractWhereBlock.toString());
	    hqlInfo.setWhereBlock(whereBlock); 
	}

	/**
	 * <p>
	 * 离职管理已离职列表页面
	 * </p>
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward leaveManageList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-list", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String s_pageno = request.getParameter("pageno");
			String s_rowsize = request.getParameter("rowsize");
			String orderby = request.getParameter("orderby");
			String ordertype = request.getParameter("ordertype");
			String beforeTime = request.getParameter("beforeTime");
			boolean isReserve = false;
			if (ordertype != null && "down".equalsIgnoreCase(ordertype)) {
				isReserve = true;
			}
			int pageno = 0;
			int rowsize = SysConfigParameters.getRowSize();
			if (s_pageno != null && s_pageno.length() > 0
					&& Integer.parseInt(s_pageno) > 0) {
				pageno = Integer.parseInt(s_pageno);
			}
			if (s_rowsize != null && s_rowsize.length() > 0
					&& Integer.parseInt(s_rowsize) > 0) {
				rowsize = Integer.parseInt(s_rowsize);
			}

			// 按多语言字段排序
			if (StringUtil.isNotNull(orderby) && form instanceof IExtendForm) {
				Class<?> modelClass = ((IExtendForm) form).getModelClass();
				if (modelClass != null) {
					String langFieldName = SysLangUtil
							.getLangFieldName(modelClass.getName(), orderby);
					if (StringUtil.isNotNull(langFieldName)) {
						orderby = langFieldName;
					}
				}
			}
			if (isReserve) {
                orderby += " desc";
            }
			HQLInfo hqlInfo = new HQLInfo();
			if (StringUtil.isNotNull(orderby)) {
				hqlInfo.setOrderBy("hrStaffPersonInfo." + orderby);
			}
			hqlInfo.setPageNo(pageno);
			hqlInfo.setRowSize(rowsize);
			changeFindPageHQLInfo(request, hqlInfo);
			if("true".equals(beforeTime)) {
				//离职日期显示当前时间之前
				String whereBlock=hqlInfo.getWhereBlock();
				whereBlock = StringUtil.linkString(whereBlock, " and ",
						"hrStaffPersonInfo.fdLeaveTime <=:fdLeaveTimeNow "); 
				hqlInfo.setParameter("fdLeaveTimeNow",new Date());
				hqlInfo.setWhereBlock(whereBlock);
			}
			Page page = getServiceImp(request).findPage(hqlInfo);
			List<HrStaffPersonInfo> staffInfoList = page.getList();
			// 添加日志信息
			UserOperHelper.logFindAll(staffInfoList,
					getServiceImp(request).getModelName());
			JSONObject urlJson = new JSONObject();
			String staffId = "";
			for (HrStaffPersonInfo hrStaffPersonInfo : staffInfoList) {
				staffId = hrStaffPersonInfo.getFdId();
				urlJson.put(staffId, HrStaffPersonUtil
						.getImgUrl(hrStaffPersonInfo, request));
				String deptName =null;
				if(hrStaffPersonInfo.getFdOrgPerson() !=null){
					//离职人员之前的部门
					String preDeptId = hrStaffPersonInfo.getFdOrgPerson().getFdPreDeptId();
					if(StringUtil.isNotNull(preDeptId)) {
						SysOrgElement element = (SysOrgElement)getSysOrgElementService().findByPrimaryKey(preDeptId);
						if(element !=null){
							deptName = HrStaffPersonUtil.getFdOrgParentsName(element);
						}
					}
					if(StringUtil.isNull(deptName)){
						deptName = hrStaffPersonInfo.getFdOrgParentDeptName();
					}
				} else{
					//系统组织架构没有关联的，则直接取上级部门
					deptName =hrStaffPersonInfo.getFdParentsName();
				}

				hrStaffPersonInfo.setFdLeavelOrgParentsName(deptName);
			}
			request.setAttribute("queryPage", page);
			request.setAttribute("urlJson", urlJson);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-list", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			return getActionForward("leaveManage", mapping, form, request,
					response);
		}
	}

	public ActionForward updatePersonInfo(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-updatePersonInfo", true, getClass());
		KmssMessage message = null;
		HrStaffPersonInfoForm personInfoForm = (HrStaffPersonInfoForm) form;
		if (StringUtil.isNotNull(personInfoForm.getFdOrgPostIds())) {
			personInfoForm.setFdOrgPostIds(URLDecoder.decode(personInfoForm.getFdOrgPostIds()));
			if(StringUtil.isNotNull(personInfoForm.getFdOrgPostNames())) {
				personInfoForm.setFdOrgPostNames(URLDecoder.decode(personInfoForm.getFdOrgPostNames()));
			}
		}
		boolean status = true;// 执行结果
		JSONObject json = new JSONObject();
		try {
			getServiceImp(request).updatePersonInfo(personInfoForm, request);
		} catch (Exception e) {
			status = false;
			logger.error("", e);
			e.printStackTrace();
			message = new KmssMessage(e.getMessage());
			json.put("errorMsg", message.getMessageKey());
		}
		TimeCounter.logCurrentTime("Action-updatePersonInfo", false,
				getClass());
		json.put("status", status);// 执行结果
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-type", "text/json;charset=UTF-8");
		response.getWriter().write(json.toString());
		return null;
	}

	private ISysOrgElementService sysOrgElementService;

	public ISysOrgElementService getSysOrgElementService() {
		if (sysOrgElementService == null) {
			sysOrgElementService = (ISysOrgElementService) getBean(
					"sysOrgElementService");
		}
		return sysOrgElementService;
	}

	private ISysOrganizationStaffingLevelService sysOrganizationStaffingLevelService;

	public ISysOrganizationStaffingLevelService
			getSysOrganizationStaffingLevelService() {
		if (sysOrganizationStaffingLevelService == null) {
			sysOrganizationStaffingLevelService = (ISysOrganizationStaffingLevelService) getBean(
					"sysOrganizationStaffingLevelService");
		}
		return sysOrganizationStaffingLevelService;
	}

	private ISysOrgPostService sysOrgPostService;

	public ISysOrgPostService getSysOrgPostService() {
		if (sysOrgPostService == null) {
			sysOrgPostService = (ISysOrgPostService) getBean(
					"sysOrgPostService");
		}
		return sysOrgPostService;
	}

	public ActionForward editType(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-editType", true, getClass());
		KmssMessages messages = new KmssMessages();
		String key = request.getParameter("key");
		if (StringUtil.isNull(key)) {
            key = "edit";
        }
		try {
			loadActionForm(mapping, form, request, response);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-editType", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			return getActionForward(key, mapping, form, request,
					response);
		}
	}

	public ActionForward addPerson(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-add", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			ActionForm newForm = createNewForm(mapping, form, request,
					response);
			if (newForm != form) {
                request.setAttribute(getFormName(newForm, request), newForm);
            }
		} catch (Exception e) {
			messages.addError(e);
			logger.error(e.getMessage(), e);
		}

		TimeCounter.logCurrentTime("Action-add", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			return getActionForward("add", mapping, form, request, response);
		}
	}

	public ActionForward getHrStaffMobileIndex(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-getHrStaffMobileIndex", true,
				getClass());
		JSONArray ja = new JSONArray();
		try {
			ja = getServiceImp(request).getStaffMobileIndex();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("", e);
		}
		TimeCounter.logCurrentTime("Action-getHrStaffMobileIndex", false,
				getClass());
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().println(ja.toString());
		return null;
	}

	public ActionForward getHrStaffMobileStat(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-getHrStaffMobileStat", true,
				getClass());
		JSONArray ja = new JSONArray();
		try {
			String[] ids = null;
			String fdIds = request.getParameter("fdIds");
			if (StringUtil.isNotNull(fdIds)) {
				ids = fdIds.split(";");
			}
			ja = getServiceImp(request).getStaffMobileStat(ids);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("", e);
		}
		TimeCounter.logCurrentTime("Action-getHrStaffMobileStat", false,
				getClass());
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().println(ja.toString());
		return null;
	}

	/**
	 * 合同导出
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward exportContract(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			WorkBook wb = getServiceImp(request).exportContract(request);
			ExcelOutput output = new ExcelOutputImp();
			output.output(wb, response);
		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.save(request);
			return getActionForward("failure", mapping, form, request,
					response);
		}
		return null;
	}

	public ActionForward chgPwd(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		return mapping.findForward("chgPwd");
	}
	
	/**
	 * 是否禁用本系统数据库的身份验证
	 */
	private static String filterDisable = ResourceUtil
			.getKmssConfigString("kmss.authentication.processing.filter.disable");
	/**
	 * 例外人员
	 */
	private static String excludeUser = ResourceUtil
			.getKmssConfigString("kmss.authentication.processing.filter.exclude.user");

	/**
	 * 管理员重置密码，不需要遵循admin.do中密码长度和强度要求，admin用户除外
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward savePwd(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		KmssMessages messages = new KmssMessages();
		try {
			String pwd = request.getParameter("fdNewPassword");
			if (StringUtil.isNull(pwd)) {
                throw new KmssException(new KmssMessage("errors.required",
                        new KmssMessage("sys-organization:sysOrgPerson.newPassword")));
            }
			String id = request.getParameter("fdId");
			SysOrgPerson person = (SysOrgPerson) getPersonService().findByPrimaryKey(id);
			if (UserOperHelper.allowLogOper("savePwd", SysOrgPerson.class.getName())) {
				UserOperContentHelper.putUpdate(person);
			}
			if ("admin".equals(person.getFdLoginName())) {
				adminPasswordStrength(pwd);
			}

			getPersonService().savePassword(id, pwd, new RequestContext(request));

			if (StringUtil.isNotNull(filterDisable) && "true".equalsIgnoreCase(filterDisable)
					&& OrgPassUpdatePlugin.getEnabledExtensionList().isEmpty()) {
				// 修改密码后，使用本系统登录是否生效，如果配置了禁用使用本系统数据库密码认证，并且不在例外人员列表里面的话，即使密码修改成功也不会生效
				boolean isEffect = false;
				if (StringUtil.isNotNull(excludeUser)) {
					String username = person == null ? "" : person.getFdLoginName();
					String[] excludeUsers = excludeUser.split("\\s*[,;]\\s*");
					for (int i = 0; i < excludeUsers.length; i++) {
						if (excludeUsers[i].equals(username)) {
							isEffect = true;
							break;
						}
					}
				}
				if (!isEffect) {
					messages.addMsg(new KmssMessage("sys-organization:sysOrgPerson.isEffect.prompt"));
				}
			}
		} catch (Exception e) {
			messages.addError(e);
		}
		KmssReturnPage rtnPage = KmssReturnPage.getInstance(request);
		rtnPage.addMessages(messages).setOperationKey("sys-organization:sysOrgPerson.button.changePassword")
				.save(request);
		if (messages.hasError()) {
			return mapping.findForward("chgPwd");
		} else {
			rtnPage.addButton(KmssReturnPage.BUTTON_CLOSE);
			return mapping.findForward("success");
		}
	}

	/**
	 * 管理员密码校验
	 * 
	 * @param pwd
	 * @throws KmssException
	 */
	protected void adminPasswordStrength(String pwd) throws KmssException {
		if (pwd == null || pwd.length() < 8) {
			throw new KmssException(new KmssMessage("sys-organization:sysOrgPerson.newPasswordLength", 8));
		}

		int pwdth = PasswordUtil.pwdStrength(pwd);
		if (pwdth < 3) {
			throw new KmssException(new KmssMessage("sys-organization:sysOrgPerson.newPasswordStrength", 3));
		}
	}

	/**
	 * <p>检查部门、岗位是否同步到EKP组织</p>
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward checkSyncOrg(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-checkSyncOrg", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			JSONObject json = new JSONObject();
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			boolean result = true;
			String fdDeptId = request.getParameter("fdDeptId");
			String fdPostId = request.getParameter("fdPostId");
			StringBuffer sbf = new StringBuffer("1=1");
			HQLInfo hql = null;
			if (StringUtil.isNotNull(fdDeptId)) {
				hql = new HQLInfo();
				sbf.append(" and fdDeptId = :fdDeptId");
				hql.setParameter("fdDeptId", "fdDeptId");
				IBaseModel element = getSysOrgElementService().findByPrimaryKey(fdDeptId, null, true);
				result = (null != element) ? true : false;
			}
			if (StringUtil.isNotNull(fdPostId) && result) {
				hql = new HQLInfo();
				sbf.append(" and fdPostId = :fdPostId");
				hql.setParameter("fdPostId", "fdPostId");
				IBaseModel element = getSysOrgElementService().findByPrimaryKey(fdPostId, null, true);
				result = (null != element) ? true : false;
			}
			json.put("result", result);
			request.setAttribute("lui-source", json);
		} catch (Exception e) {
			logger.error("", e);
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("lui-source", mapping, form, request, response);
		}
	}
	
	public String getWorkDaysByPersonInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		String res = null;
		JSONObject obj = new JSONObject();
		String days ="0";
		try {
			obj = getServiceImp(request).findWorkDaysByPersonInfo();
		} catch (Exception e) {
			e.printStackTrace();
		}
		res = obj.toString();
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(res);
		return null;
	}

	/**
	 *
	 * 根据部门ID 获取部门一级,二级,三级部门
	 * @throws Exception
	 */
	public ActionForward findOrgParents(ActionMapping mapping, ActionForm form, HttpServletRequest request,
									  HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-checkSyncOrg", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			JSONObject json = new JSONObject();
			if (!"POST".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}
			String fdDeptId = request.getParameter("fdDeptId");
			SysOrgElement sysOrgElement = (SysOrgElement)getSysOrgElementService().findByPrimaryKey(fdDeptId);

			SysOrgElement thisLeader = sysOrgElement.getHbmThisLeader();
			List<SysOrgElement> allParent = sysOrgElement.getAllParent(true);

			JSONArray ja = new JSONArray();


			JSONObject dep = new JSONObject();

			JSONObject self = new JSONObject();
			JSONObject this_object = new JSONObject();
			this_object.put("id",sysOrgElement.getFdId());
			this_object.put("name",sysOrgElement.getFdName());
			dep.put("department0",this_object);
			if(allParent.size()!=1)
			for (int i=0;i<=allParent.size() - 2; i++) {
				SysOrgElement sysOrgElement1 = allParent.get(i);
				JSONObject object = new JSONObject();
				object.put("id",sysOrgElement1.getFdId());
				object.put("name",sysOrgElement1.getFdName());
				int j=i+1;
				dep.put("department"+j,object);
			}
			ja.add(dep);
//			if (allParent.size() == 3) {
//			JSONObject object = new JSONObject();
//			object.put("id",sysOrgElement.getFdId());
//			object.put("name",sysOrgElement.getFdName());
//			ja.add(object);
//			}

			JSONObject object1 = new JSONObject();
			if(thisLeader!=null){
				object1.put("name", thisLeader.getFdName());
				object1.put("id", thisLeader.getFdId());
			}else{
				object1.put("name", "");
				object1.put("id", "");
			}
			SysOrgElement sysOrgElement2 = null;
			if(allParent.size()!=1)
			sysOrgElement2 = allParent.get(allParent.size() - 2);
			else sysOrgElement2 = sysOrgElement;
			JSONObject object2 = new JSONObject();
			SysOrgElement sysOrgElement3 = (SysOrgElement) getSysOrgElementService()
					.findByPrimaryKey(
							sysOrgElement2.getHbmThisLeader().getFdId());
			object2.put("name", sysOrgElement3.getFdName());

			object2.put("id", sysOrgElement3.getFdId());
			JSONObject other = new JSONObject();
			other.put("DepartmentHead",object1);
			other.put("HeadOfFirstLevelDepartment",object2);
			ja.add(other);
			json.put("result", ja);

			response.setCharacterEncoding("utf-8");
			response.getWriter().write(json.toString());
			response.getWriter().flush();
			response.getWriter().close();
		} catch (Exception e) {
			logger.error("", e);
			messages.addError(e);
		}

		return null;
	}

	/**
	 * 根据组织人员id 获取职级，职类，职级系数等信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward findPersonInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		TimeCounter.logCurrentTime("Action-findPersonInfo", true, getClass());
		//组织架构id或者人员档案id
		String fdOrgPersonId = request.getParameter("fdId");
		if (StringUtil.isNull(fdOrgPersonId)) {
			return null;
		}
		logger.info("正在查找人员档案id:{}的信息",fdOrgPersonId);
		JSONObject json = new JSONObject();
		try {
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock("fdOrgPerson.fdId=:fdId or fdId=:fdId");
			hqlInfo.setParameter("fdId", fdOrgPersonId);
			List<HrStaffPersonInfo> hrStaffPersonInfos = getServiceImp(request).findList(hqlInfo);
			if (!ArrayUtil.isEmpty(hrStaffPersonInfos)) {
				HrStaffPersonInfo info = hrStaffPersonInfos.get(0);
				if (info != null) {
					//部门
					if (null != info.getFdOrgParent()) {
						json.put("fdDeptId", info.getFdOrgParent().getFdId());
						json.put("fdDeptName", info.getFdOrgParent().getFdName());
						String deptInfo = (String) info.getFdOrgParent().getCustomPropMap().get("bumenjibie");
						json.put("fdDeptInfo", deptInfo);
						//部门领导
						SysOrgElement fdLeader = info.getFdOrgParent().getHbmThisLeader();
						if (null != fdLeader) {
							json.put("fdLeaderId", fdLeader.getFdId());
							json.put("fdLeaderName", fdLeader.getFdName());
						}
					}
					//职务
					SysOrganizationStaffingLevel level = info.getFdStaffingLevel();
					if(level != null){
						json.put("fdStaffingLevel",level.getFdName());
					}
					//职类
					json.put("fdCategory", info.getFdCategory());
					if (info.getFdOrgRank() != null) {
						//职级
						json.put("fdRankName", info.getFdOrgRank().getFdName());
						//职级系数
						String fdRank = "";
						if(StringUtil.isNotNull(info.getFdOrgRank().getFdName())){
							Matcher matcher = Pattern.compile("[^0-9]").matcher(info.getFdOrgRank().getFdName());
							if(StringUtil.isNotNull(matcher.replaceAll("").trim())){
								fdRank = matcher.replaceAll("").trim();
							}else{
								fdRank = "0";
							}
						}
						json.put("fdRank", fdRank);
					}
					//岗位
					List<HrOrganizationPost> posts = info.getFdPosts();
					JSONArray postArray = new JSONArray();
					posts.forEach(entry -> {
						JSONObject object = new JSONObject();
						object.put("id", entry.getFdId());
						object.put("name", entry.getFdName());
						postArray.add(object);
					});
					json.put("fdPost", postArray);
					//入职日期
					json.put("fdEntryTime", DateUtil.convertDateToString(info.getFdEntryTime(), "yyyy-MM-dd"));
					//直接上级
					if (null != info.getFdReportLeader()) {
						json.put("fdReportLeaderName", info.getFdReportLeader().getFdName());
						json.put("fdReportLeaderId", info.getFdReportLeader().getFdId());
					}
					//人员类型
					json.put("fdStaffType", info.getFdStaffType());
					//性别
					json.put("fdSex", info.getFdSex());
					//员工编号
					json.put("fdStaffNo", info.getFdStaffNo());
					//组织架构编号
					json.put("fdOrgPersonNo", info.getFdOrgPerson().getFdNo());
					//最高学历
					json.put("fdHighestEducation", info.getFdHighestEducation());
					//拟转正时间
					json.put("fdProposedEmploymentConfirmationDate", DateUtil.convertDateToString(info.getFdProposedEmploymentConfirmationDate(), "yyyy-MM-dd"));
					//是否OA用户
					json.put("fdIsOAUser", info.getFdIsOAUser());
					//最小部门
					//办公区域
					//省区域id
					json.put("fdOfficeAreaProvinceId", info.getFdOfficeAreaProvinceName());
					json.put("fdOfficeAreaCityId", info.getFdOfficeAreaCityName());
					json.put("fdOfficeAreaAreaId", info.getFdOfficeAreaAreaName());
					json.put("fdOfficeLocation", info.getFdOfficeLocation());

					//一级部门
					SysOrgElement fdFirstLevelDepartment = info.getFdFirstLevelDepartment();
					if (null != fdFirstLevelDepartment) {
						json.put("fdFirstLevelDepartmentId", fdFirstLevelDepartment.getFdId());
						json.put("fdFirstLevelDepartmentName", fdFirstLevelDepartment.getFdName());
					}
					//二级部门
					SysOrgElement fdSecondLevelDepartment = info.getFdSecondLevelDepartment();
					if (null != fdSecondLevelDepartment) {
						json.put("fdSecondLevelDepartmentId", fdSecondLevelDepartment.getFdId());
						json.put("fdSecondLevelDepartmentName", fdSecondLevelDepartment.getFdName());
					}
					//三级部门
					SysOrgElement fdThirdLevelDepartment = info.getFdThirdLevelDepartment();
					if (null != fdThirdLevelDepartment) {
						json.put("fdThirdLevelDepartmentId", fdThirdLevelDepartment.getFdId());
						json.put("fdThirdLevelDepartmentName", fdThirdLevelDepartment.getFdName());
					}
					//获取最近一条合同信息
					HrStaffPersonExperienceContract contract = getHrStaffPersonExperienceContractService().findContractByPersonId(info.getFdId());
					if (null != contract) {
						HrStaffContractType hrStaffContractType = contract.getFdStaffContType();
						if (null != hrStaffContractType) {
							//合同类型
							json.put("fdStaffContTypeId", hrStaffContractType.getFdId());
							json.put("fdStaffContTypeName", hrStaffContractType.getFdName());
							//合同开始日期
							json.put("fdBeginDate", DateUtil.convertDateToString(contract.getFdBeginDate(), "yyyy-MM-dd"));
							//合同结束日期
							json.put("fdEndDate", DateUtil.convertDateToString(contract.getFdEndDate(), "yyyy-MM-dd"));
						}
					}
					//归属公司
					json.put("fdAffiliatedCompany", info.getFdAffiliatedCompany());
					//去年剩余调休假
					Calendar calendar = Calendar.getInstance();
					int year = calendar.get(Calendar.YEAR);
					SysTimeLeaveAmountItem itemTx = getSysTimeLeaveAmountItemService().getAmountItem(fdOrgPersonId,year , "13");
					if(itemTx != null && itemTx.getFdLastRestDay() != null){
						json.put("fdRestTxDay", itemTx.getFdLastRestDay());
					} else {
						json.put("fdRestTxDay", 0);
					}
					//去年剩余年假
					SysTimeLeaveAmountItem item = getSysTimeLeaveAmountItemService().getAmountItem(fdOrgPersonId,year , "1");
					if(item != null && item.getFdLastRestDay() != null){
						json.put("fdRestDay", item.getFdLastRestDay());
					} else {
						json.put("fdRestDay", 0);
					}
					logger.info("查找人员档案id:{},name:{}的信息结束,信息：{}",info.getFdId(),info.getFdName(),json.toString());
				}
				response.setContentType("application/json;charset=utf-8");
				response.getWriter().write(json.toString());
				response.getWriter().flush();
				response.getWriter().close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 自定义列导出
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward exportPersonInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-exportPersonInfo", true, getClass());
		KmssMessages messages = new KmssMessages();
		String fdShowCols = request.getParameter("fdShowCols");
		try{
			if(StringUtil.isNull(fdShowCols)){
				throw new Exception("未选择导出列");
			}
			HQLInfo info = new HQLInfo();
			String whereBlock=" 1=1 ";
			if(_fdKey!=null){
				whereBlock=" hrStaffPersonInfo.fdName like :fdKey or hrStaffPersonInfo.fdMobileNo like :fdKey or hrStaffPersonInfo.fdEmail like :fdKey";
				info.setParameter("fdKey", "%" + _fdKey + "%");
			}
			if (_fdPosts != null && _fdPosts.length > 0) {
				// 先查询岗位
				info.setJoinBlock(
						"left join hrStaffPersonInfo.fdOrgPosts fdPost");
				whereBlock+=" and (" + HQLUtil.buildLogicIN("fdPost.fdId",
						Arrays.asList(_fdPosts)) + ")";
			}else {
				String fdOrgPostIds = request.getParameter("fdPosts");
				if(null != fdOrgPostIds && fdOrgPostIds.length()>0) {
					info.setJoinBlock("left join hrStaffPersonInfo.fdOrgPosts fdPost");
					whereBlock+=" and (" + HQLUtil.buildLogicIN("fdPost.fdId",
							ArrayUtil.asList(fdOrgPostIds.split(";"))) + ")";
				}
			}
			if (StringUtil.isNotNull(_fdSex)) {
				if ("unknown".equalsIgnoreCase(_fdSex)) {
					whereBlock+=" and hrStaffPersonInfo.fdSex is null";
				} else {
					whereBlock+=" and hrStaffPersonInfo.fdSex =:fdSex";
					info.setParameter("fdSex", _fdSex);
				}
			}
		
			List<String> deptIds = null;
			if (_fdDepts != null) {
				deptIds = ArrayUtil.convertArrayToList(_fdDepts);
			} else {
				String depts = request.getParameter("q._fdDepts");
				if(null != depts && depts.length()>0) {
					deptIds = ArrayUtil.asList(depts.split(";"));
				}else {
					deptIds = new ArrayList<String>();
				}
			}
			if (!(UserUtil.getKMSSUser().isAdmin()
					|| UserUtil.checkRole("ROLE_HRSTAFF_READALL")) && "quit"
					.equalsIgnoreCase(request.getParameter("personStatus"))) {
				if (deptIds.size() > 0) {
					// 带筛选
					List<String> orgIds = this.getAuthDeptIds();
					deptIds.retainAll(orgIds);
				} else {
					deptIds = this.getAuthDeptIds();
				}
			}
			if (deptIds != null && deptIds.size() > 0) {
//				whereBlock+=
//						" and hrStaffPersonInfo.fdId=hrOrganizationElement.fdId ";
				List<String> newDeptIds = this.getDeptIds(deptIds);
				String staffWhereBlock = "hrStaffPersonInfo.fdHierarchyId like :fdDept0";
				for (int i = 1; i < deptIds.size(); i++) {
					staffWhereBlock = StringUtil.linkString(staffWhereBlock,
							" or ",
							"hrStaffPersonInfo.fdHierarchyId like :fdDept" + i);
					info.setParameter("fdDept" + i,
							"%" + deptIds.get(i) + "%");
				}
				whereBlock+=" and (" + staffWhereBlock;
//				whereBlock.append(" or (").append(HQLUtil.buildLogicIN("hrOrganizationElement.hbmParent.fdId", newDeptIds) + ")");
				whereBlock+=" ) ";
				info.setParameter("fdDept0", "%" + deptIds.get(0) + "%");
			}
			info.setWhereBlock(whereBlock);
			IHrStaffPersonInfoService hrStaffPersonInfoService = getServiceImp(request);

			CriteriaUtil.buildHql(cv, info, HrStaffPersonInfo.class);
			List<HrStaffPersonInfo> hrStaffPersonInfoList = hrStaffPersonInfoService.findList(info);
			for (HrStaffPersonInfo hrStaffPersonInfo : hrStaffPersonInfoList) {
				List hrStaffPersonExperienceContracts=hrStaffPersonInfo.getHbmPersonExperienceContract();
				if(!hrStaffPersonExperienceContracts.isEmpty()){
					HrStaffPersonExperienceContract hrStaffPersonExperienceContract = (HrStaffPersonExperienceContract) hrStaffPersonExperienceContracts.get(hrStaffPersonExperienceContracts.size()-1);
					hrStaffPersonInfo.setFdBeginDate(hrStaffPersonExperienceContract.getFdBeginDate());
					hrStaffPersonInfo.setFdContType(hrStaffPersonExperienceContract.getFdContType());
					hrStaffPersonInfo.setFdContractUnit(hrStaffPersonExperienceContract.getFdContractUnit());
					hrStaffPersonInfo.setFdEndDate(hrStaffPersonExperienceContract.getFdEndDate());
					hrStaffPersonInfo.setFdContractYear(hrStaffPersonExperienceContract.getFdContractYear());
					hrStaffPersonInfo.setFdContractMonth(hrStaffPersonExperienceContract.getFdContractMonth());
				}
			}
			hrStaffPersonInfoService.exportPersonList(request,response,hrStaffPersonInfoList,fdShowCols);
		}catch (Exception e){
			messages.addError(e);
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		}
		return null;
	}
}

