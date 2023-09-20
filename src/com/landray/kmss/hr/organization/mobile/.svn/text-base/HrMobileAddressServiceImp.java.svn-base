package com.landray.kmss.hr.organization.mobile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.landray.kmss.util.HibernateUtil;
import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;
import com.landray.kmss.util.ClassUtils;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.dao.IBaseDao;
import com.landray.kmss.common.model.IBaseCreateInfoModel;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.constant.BaseTreeConstant;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.framework.plugin.core.config.IExtension;
import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.hr.organization.constant.HrOrgConstant;
import com.landray.kmss.hr.organization.dao.IHrOrganizationDeptDao;
import com.landray.kmss.hr.organization.dao.IHrOrganizationElementDao;
import com.landray.kmss.hr.organization.dao.IHrOrganizationOrgDao;
import com.landray.kmss.hr.organization.dao.IHrOrganizationPostDao;
import com.landray.kmss.hr.organization.model.HrOrganizationElement;
import com.landray.kmss.hr.organization.service.IHrOrgCoreService;
import com.landray.kmss.hr.organization.service.IHrOrganizationElementService;
import com.landray.kmss.hr.organization.util.HrOrgDialogUtil;
import com.landray.kmss.hr.staff.dao.IHrStaffPersonInfoDao;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfo;
import com.landray.kmss.hr.staff.service.IHrStaffPersonInfoService;
import com.landray.kmss.sys.appconfig.model.BaseAppConfig;
import com.landray.kmss.sys.config.dict.SysDictCommonProperty;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.lbpm.engine.formula.LbpmFunction;
import com.landray.kmss.sys.lbpm.engine.integrate.org.ILbpmOrgParseService;
import com.landray.kmss.sys.lbpm.engine.integrate.rules.IRuleProvider;
import com.landray.kmss.sys.lbpm.engine.integrate.rules.RuleFact;
import com.landray.kmss.sys.lbpm.engine.manager.NoExecutionEnvironment;
import com.landray.kmss.sys.lbpm.engine.manager.ProcessServiceManager;
import com.landray.kmss.sys.lbpmservice.constant.LbpmConstants;
import com.landray.kmss.sys.lbpmservice.node.autobranchnode.ModelVarProviderExtend;
import com.landray.kmss.sys.lbpmservice.node.support.rules.ModelDataFiller;
import com.landray.kmss.sys.metadata.dict.SysDictExtendElementProperty;
import com.landray.kmss.sys.metadata.dict.SysDictExtendModel;
import com.landray.kmss.sys.metadata.dict.SysDictExtendSubTableProperty;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataModel;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.interfaces.SysOrgHQLUtil;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgGroup;
import com.landray.kmss.sys.organization.model.SysOrgGroupCate;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.model.SysOrganizationConfig;
import com.landray.kmss.sys.organization.service.ISysOrgGroupCateService;
import com.landray.kmss.sys.organization.service.ISysOrgGroupService;
import com.landray.kmss.sys.organization.service.ISysOrganizationVisibleService;
import com.landray.kmss.sys.organization.util.SysOrgUtil;
import com.landray.kmss.sys.person.interfaces.PersonInfoServiceGetter;
import com.landray.kmss.sys.xform.service.DictLoadService;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.sunbor.web.tag.Page;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class HrMobileAddressServiceImp
		implements IHrMobileAddressService, HrOrgConstant {
	Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());
	private static final Set<String> paramsExcept = new HashSet<String>();

	static {
		paramsExcept.add("modelId");
		paramsExcept.add("modelName");
		paramsExcept.add("extendFilePath");
		paramsExcept.add("orgType");
		paramsExcept.add("scope");
		paramsExcept.add("fdControlId");
		paramsExcept.add("scopeType");
		paramsExcept.add("keyword");
		paramsExcept.add("method");
		paramsExcept.add("pageno");
		paramsExcept.add("exceptValue");
		paramsExcept.add("distinct");
		paramsExcept.add("contains");

	}

	private static final String MODEL_FILLER = "com.landray.kmss.sys.lbpmservice.node.support.rules.routeDecision";

	private IHrOrganizationElementService hrOrganizationElementService;

	private ISysOrganizationVisibleService sysOrganizationVisibleService;

	/*
	 * protected ISysOrganizationStaffingLevelService
	 * sysOrganizationStaffingLevelService;
	 */

	private ISysOrgGroupService sysOrgGroupService;

	private ISysOrgGroupCateService sysOrgGroupCateService;

	private IHrOrganizationDeptDao deptDao;

	private IHrOrganizationElementDao elementDao;

	private IHrOrganizationOrgDao orgDao;

	private IHrStaffPersonInfoDao personDao;

	private IHrOrganizationPostDao postDao;

	private IBaseDao getOptimalDao(int rtnType) {
		switch (rtnType & HR_TYPE_ALL) {
		case HR_TYPE_ORG:
			return orgDao;
		case HR_TYPE_DEPT:
			return deptDao;
		case HR_TYPE_POST:
			return postDao;
		case HR_TYPE_PERSON:
			return personDao;
		/*
		 * case ORG_TYPE_GROUP: return groupDao; case ORG_TYPE_ROLE: return
		 * roleDao;
		 */
		default:
			return elementDao;
		}
	}

	private IXMLDataBean commonGroupCateBean;

	private IXMLDataBean commonGroupBean;

	private IXMLDataBean myGroupBean;

	/*
	 * public void setSysOrganizationStaffingLevelService(
	 * ISysOrganizationStaffingLevelService sysOrganizationStaffingLevelService)
	 * { this.sysOrganizationStaffingLevelService =
	 * sysOrganizationStaffingLevelService; }
	 */

	public void setHrOrganizationElementService(
			IHrOrganizationElementService hrOrganizationElementService) {
		this.hrOrganizationElementService = hrOrganizationElementService;
	}

	public IHrOrganizationElementService getHrOrganizationElementService() {
		return hrOrganizationElementService;
	}

	/*
	 * public void setSysOrganizationVisibleService(
	 * ISysOrganizationVisibleService sysOrganizationVisibleService) {
	 * this.sysOrganizationVisibleService = sysOrganizationVisibleService; }
	 */
	public void setCommonGroupCateBean(IXMLDataBean commonGroupCateBean) {
		this.commonGroupCateBean = commonGroupCateBean;
	}

	public void setCommonGroupBean(IXMLDataBean commonGroupBean) {
		this.commonGroupBean = commonGroupBean;
	}

	public void setMyGroupBean(IXMLDataBean myGroupBean) {
		this.myGroupBean = myGroupBean;
	}

	public void setSysOrgGroupService(ISysOrgGroupService sysOrgGroupService) {
		this.sysOrgGroupService = sysOrgGroupService;
	}

	public void setSysOrgGroupCateService(
			ISysOrgGroupCateService sysOrgGroupCateService) {
		this.sysOrgGroupCateService = sysOrgGroupCateService;
	}

	@Override
	public List addressList(RequestContext xmlContext) throws Exception {
		// 定义返回结果
		List resultList = null;

		// 定义最大返回的数据记录条数
		int maxPageSize = 500;
		String maxPageSizeParam = xmlContext.getParameter("maxPageSize");
		if (StringUtil.isNotNull(maxPageSizeParam)) {
			maxPageSize = Integer.parseInt(maxPageSizeParam);
		}

		// 组织类型
		int orgType = HR_TYPE_PERSON;
		String orgTypeParam = xmlContext.getParameter("orgType");
		if (StringUtil.isNotNull(orgTypeParam)) {
			orgType = Integer.parseInt(orgTypeParam);
		}
		if (orgType == HR_TYPE_ORG) {
			orgType = orgType & (HR_TYPE_ALLORG | HR_FLAG_AVAILABLEALL
					| HR_FLAG_BUSINESSALL);
		} else {
			orgType = (orgType | HR_TYPE_HRORDEPT) & (HR_TYPE_ALLORG
					| HR_FLAG_AVAILABLEALL | HR_FLAG_BUSINESSALL);
		}

		// 获取前端传递的父组织ID
		String parentId = xmlContext.getParameter("parentId");

		// 获取需要排除过滤掉的值（以逗号分隔的fdId集合）
		String exceptValue = xmlContext.getParameter("exceptValue");
		Set<String> exceptValues = null;
		if (StringUtil.isNotNull(exceptValue)) {
			String[] tempExceptValues = exceptValue.split("[;；,，]");
			exceptValues = new HashSet<String>();
			for (String value : tempExceptValues) {
				exceptValues.add(value);
			}
		}

		if (StringUtil.isNotNull(parentId)) { // *****
												// 如果前端有传递父组织ID，则根据父组织ID来查询下级机构、部门、岗位、人员
												// *****

			resultList = this.getAddressResultList(parentId, null, orgType,
					exceptValues, maxPageSize);

		} else { // ***** 如果前端没有有传递父组织ID，则再查询用户是否有设置可视组织，如果有可视组织，通过可视组织查询 *****

			// 获取人员的顶级可视组织 加入角色过滤（数据来源：组织权限中心》基础设置》地址本隔离》组织可见性配置）
			/*
			 * Set<String> elementIds = sysOrganizationVisibleService
			 * .getPersonAuthVisibleOrgIds(UserUtil.getKMSSUser());
			 */
			Set<String> elementIds = null;
			if (elementIds != null) {

				// 根据可视组织进行查询
				resultList = this.getAddressResultList(null, elementIds,
						orgType, exceptValues, maxPageSize);

			} else {

				// 首次进入地址本，还未点击子级部门或子级机构时，默认查询组织最顶层数据
				resultList = this.getAddressResultList(null, null, orgType,
						exceptValues, maxPageSize);
			}

		}

		return resultList;
	}

	/**
	 * 拼凑跟组织架构相关的HQL中where语句
	 * 
	 * @param rtnType
	 *            SysOrgConstant中的常量组合
	 * @param whereBlock
	 *            原有的where语句，将跟组织架构的条件进行and运算
	 * @param orgProperty
	 *            组织架构的属性
	 * @return where 语句拼凑结果
	 */
	private String buildWhereBlock(int rtnType, String whereBlock,
			String orgProperty) {
		String m_where = null;
		String filter = null;
		int orgFlag = rtnType;

		orgFlag = rtnType & HR_FLAG_AVAILABLEALL;
		if (orgFlag == 0) {
			orgFlag = HR_FLAG_AVAILABLEDEFAULT;
		}
		if (orgFlag != HR_FLAG_AVAILABLEALL) {
			if (orgFlag == HR_FLAG_AVAILABLENO) {
				filter = orgProperty + ".fdIsAvailable = " + HibernateUtil.toBooleanValueString(false);
			} else {
				filter = orgProperty + ".fdIsAvailable = " + HibernateUtil.toBooleanValueString(true);
			}
			m_where = StringUtil.linkString(m_where, " and ", filter);
		}

		orgFlag = rtnType & HR_FLAG_BUSINESSALL;
		if (orgFlag == 0) {
			orgFlag = HR_FLAG_BUSINESSDEFAULT;
		}
		if (orgFlag != HR_FLAG_BUSINESSALL) {
			if (orgFlag == HR_FLAG_BUSINESSNO) {
				filter = orgProperty + ".fdIsBusiness = " + HibernateUtil.toBooleanValueString(false);
			} else {
				filter = orgProperty + ".fdIsBusiness = " + HibernateUtil.toBooleanValueString(true);
			}
			m_where = StringUtil.linkString(m_where, " and ", filter);
		}

		return StringUtil.linkString(
				StringUtil.isNull(whereBlock) ? null : "(" + whereBlock + ")",
				" and ", m_where);
	}

	/**
	 * 获取地址本展示数据（包括：部门、机构、人员、岗位）
	 * 
	 * @param parentId
	 *            父级ID
	 * @param elementIds
	 *            人员的顶级可视组织
	 * @param orgType
	 *            组织类型
	 * @param exceptValues
	 *            需要排除过滤掉的fdId集合
	 * @param maxPageSize
	 *            最大返回的数据记录条数
	 * @return 返回地址本机构记录条数
	 */
	private List getAddressResultList(String parentId, Set<String> elementIds,
			int orgType, Set<String> exceptValues, int maxPageSize)
			throws Exception {
		List resultList = new ArrayList();
		int orgFlag = orgType;
		/*
		 * if (orgFlag != HR_TYPE_ALL && orgFlag != (HR_TYPE_ALL |
		 * ORG_TYPE_ROLE)) {
		 */
		if (orgFlag != HR_TYPE_ALL
				&& orgFlag != (HR_TYPE_ALL)) {
			// 剩余可查询记录条数
			int surplusQty = maxPageSize;

			// 查询《 机构 》
			if ((orgFlag & HR_TYPE_ORG) == HR_TYPE_ORG && surplusQty > 0) {
				List<HrOrganizationElement> orgElementList = this
						.getAddressOrgList(
						parentId, elementIds, surplusQty, exceptValues,
						orgType);
				resultList.addAll(this.getFormatElementList(orgElementList));
				surplusQty = maxPageSize - resultList.size();
			}

			// 查询《 部门 》
			if ((orgFlag & HR_TYPE_DEPT) == HR_TYPE_DEPT && surplusQty > 0) {
				List<HrOrganizationElement> deptElementList = this
						.getAddressDeptList(
						parentId, elementIds, surplusQty, exceptValues,
						orgType);
				resultList.addAll(this.getFormatElementList(deptElementList));
				surplusQty = maxPageSize - resultList.size();
			}

			// 查询《 岗位 》
			if ((orgFlag & HR_TYPE_POST) == HR_TYPE_POST && surplusQty > 0) {
				List<HrOrganizationElement> postElementList = this.getAddressPostList(
						parentId, elementIds, surplusQty, exceptValues,
						orgType);
				resultList.addAll(this.getFormatElementList(postElementList));
				surplusQty = maxPageSize - resultList.size();
			}

			// 查询《 人员 》
			if (((orgFlag & HR_TYPE_PERSON) == HR_TYPE_PERSON
					|| orgFlag == 515) && surplusQty > 0) {
				List<HrOrganizationElement> personElementList = this
						.getAddressPersonList(parentId, elementIds, surplusQty,
								exceptValues, orgType);
				List personList = this.getFormatElementList(personElementList);
				//Collections.sort(personList, new PinyinComparator()); // 重新按照拼音排序
				Collections.sort(personList, new OrderComparator()); // 重新按照排序号排序
				resultList.addAll(personList);
				surplusQty = maxPageSize - resultList.size();
			}


		}
		return resultList;
	}

	private List getFormatElementList(List<HrOrganizationElement> list)
			throws Exception {
		List resultList = new ArrayList();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			HrOrganizationElement element = (HrOrganizationElement) iterator
					.next();
			resultList.add(formatElement(element));
		}
		return resultList;
	}

	/**
	 * 获取地址本 《机构》 信息列表
	 * 
	 * @param parentId
	 *            父级ID
	 * @param elementIds
	 *            人员的顶级可视组织
	 * @param maxPageSize
	 *            最大记录行数
	 * @param exceptValues
	 *            需要排除过滤掉的fdId集合
	 * @param orgType
	 *            组织类型
	 * @return 返回地址本机构信息列表
	 */
	private List<HrOrganizationElement> getAddressOrgList(String parentId,
			Set<String> elementIds, int maxPageSize, Set<String> exceptValues,
			int orgType) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();

		StringBuffer whereBlockSb = new StringBuffer();
		whereBlockSb.append("hrOrganizationElement.fdOrgType=:orgType ");
		hqlInfo.setParameter("orgType", HR_TYPE_ORG);
		if (StringUtil.isNotNull(parentId)) {
			whereBlockSb.append(
					"and hrOrganizationElement.hbmParent.fdId=:parentId ");
			hqlInfo.setParameter("parentId", parentId);
		} else if (elementIds != null) {
			whereBlockSb.append("and hrOrganizationElement.fdId in (")
					.append(SysOrgUtil.buildInBlock(elementIds)).append(") ");
		} else {
			whereBlockSb.append("and hrOrganizationElement.hbmParent=null ");
		}

		if (exceptValues != null) {
			whereBlockSb.append("and hrOrganizationElement.fdId not in (")
					.append(SysOrgUtil.buildInBlock(exceptValues)).append(") ");
		}

		String whereBlock = this.buildWhereBlock(orgType,
				whereBlockSb.toString(), "hrOrganizationElement");
		hqlInfo.setWhereBlock(whereBlock);

		hqlInfo.setOrderBy("hrOrganizationElement.fdOrder"); // 按排序号升序进行排序

		hqlInfo.setPageNo(1);
		hqlInfo.setRowSize(maxPageSize);
		hqlInfo.setAuthCheckType("DIALOG_READER");
		List<HrOrganizationElement> orgElementList = hrOrganizationElementService
				.findPage(hqlInfo).getList();
		return orgElementList;
	}

	/**
	 * 获取地址本 《部门》 信息列表
	 * 
	 * @param parentId
	 *            父级ID
	 * @param elementIds
	 *            人员的顶级可视组织
	 * @param maxPageSize
	 *            最大记录行数
	 * @param exceptValues
	 *            需要排除过滤掉的fdId集合
	 * @param orgType
	 *            组织类型
	 * @return 返回地址本部门信息列表
	 */
	private List<HrOrganizationElement> getAddressDeptList(String parentId,
			Set<String> elementIds, int maxPageSize, Set<String> exceptValues,
			int orgType) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();

		StringBuffer whereBlockSb = new StringBuffer();
		whereBlockSb.append("hrOrganizationElement.fdOrgType=:orgType ");
		hqlInfo.setParameter("orgType", HR_TYPE_DEPT);
		if (StringUtil.isNotNull(parentId)) {
			whereBlockSb.append(
					"and hrOrganizationElement.hbmParent.fdId=:parentId ");
			hqlInfo.setParameter("parentId", parentId);
		} else if (elementIds != null) {
			whereBlockSb.append("and hrOrganizationElement.fdId in (")
					.append(SysOrgUtil.buildInBlock(elementIds)).append(") ");
		} else {
			whereBlockSb.append("and hrOrganizationElement.hbmParent=null ");
		}

		if (exceptValues != null) {
			whereBlockSb.append("and hrOrganizationElement.fdId not in (")
					.append(SysOrgUtil.buildInBlock(exceptValues)).append(") ");
		}

		String whereBlock = this.buildWhereBlock(orgType,
				whereBlockSb.toString(), "hrOrganizationElement");
		hqlInfo.setWhereBlock(whereBlock);

		hqlInfo.setOrderBy("hrOrganizationElement.fdOrder"); // 按排序号升序进行排序

		hqlInfo.setPageNo(1);
		hqlInfo.setRowSize(maxPageSize);
		hqlInfo.setAuthCheckType("DIALOG_READER");
		List<HrOrganizationElement> orgElementList = hrOrganizationElementService
				.findPage(hqlInfo).getList();
		return orgElementList;
	}

	/**
	 * 获取地址本 《岗位》 信息列表
	 * 
	 * @param parentId
	 *            父级ID
	 * @param elementIds
	 *            人员的顶级可视组织
	 * @param maxPageSize
	 *            最大记录行数
	 * @param exceptValues
	 *            需要排除过滤掉的fdId集合
	 * @param orgType
	 *            组织类型
	 * @return 返回地址本岗位信息列表
	 */
	private List<HrOrganizationElement> getAddressPostList(String parentId,
			Set<String> elementIds, int maxPageSize, Set<String> exceptValues,
			int orgType) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();

		StringBuffer whereBlockSb = new StringBuffer();
		whereBlockSb.append("sysOrgElement.fdOrgType=:orgType ");
		hqlInfo.setParameter("orgType", HR_TYPE_POST);
		if (StringUtil.isNotNull(parentId)) {
			whereBlockSb.append("and sysOrgElement.hbmParent.fdId=:parentId ");
			hqlInfo.setParameter("parentId", parentId);
		} else if (elementIds != null) {
			// 开启组织可见性，parentId为空时不可能存在人员
			whereBlockSb.append("and 1=2 ");
//			whereBlockSb.append("and sysOrgElement.hbmParent.fdId in (")
//					.append(SysOrgUtil.buildInBlock(elementIds)).append(") ");
		} else {
			whereBlockSb.append("and sysOrgElement.hbmParent=null ");
		}

		if (exceptValues != null) {
			whereBlockSb.append("and sysOrgElement.fdId not in (")
					.append(SysOrgUtil.buildInBlock(exceptValues)).append(") ");
		}

		String whereBlock = this.buildWhereBlock(orgType,
				whereBlockSb.toString(), "sysOrgElement");
		hqlInfo.setWhereBlock(whereBlock);

		hqlInfo.setOrderBy("sysOrgElement.fdOrder"); // 按排序号升序进行排序

		hqlInfo.setPageNo(1);
		hqlInfo.setRowSize(maxPageSize);
		hqlInfo.setAuthCheckType("DIALOG_READER");
		List<HrOrganizationElement> orgElementList = hrOrganizationElementService
				.findPage(hqlInfo).getList();
		return orgElementList;
	}

	/**
	 * 获取地址本 《人员》 信息列表
	 * 
	 * @param parentId
	 *            父级ID
	 * @param elementIds
	 *            人员的顶级可视组织
	 * @param maxPageSize
	 *            最大记录行数
	 * @param exceptValues
	 *            需要排除过滤掉的fdId集合
	 * @param orgType
	 *            组织类型
	 * @return 返回地址本人员信息列表
	 */
	private List<HrOrganizationElement> getAddressPersonList(String parentId,
			Set<String> elementIds, int maxPageSize, Set<String> exceptValues,
			int orgType) throws Exception {

		// 初始查询第一页
		int pageNo = 1;

		// 定义返回的结果集
		List<HrOrganizationElement> resultList = new ArrayList<HrOrganizationElement>();

		// 分页递归查询并将结果集添加至 resultList 中 （注意：返回的结果集有可能会超过限制的最大的记录条数maxPageSize）
		getPersonForResultList(parentId, elementIds, maxPageSize, pageNo,
				exceptValues, orgType, resultList);
		if (resultList.size() > maxPageSize) {
			resultList = resultList.subList(0, maxPageSize);
		}

		return resultList;
	}

	private List<HrOrganizationElement> getPersonForResultList(String parentId,
			Set<String> elementIds, int maxPageSize, int pageNo,
			Set<String> exceptValues, int orgType,
			List<HrOrganizationElement> resultList) throws Exception {
		// 注：查询分为两段逻辑分开查询（ 1、查询直接挂在部门下的人 2、查询挂在指定部门的岗位下的人 ）
		List<HrOrganizationElement> personElementList = new ArrayList<HrOrganizationElement>();

		// 1、查询直接挂在部门下的人
		HQLInfo hqlInfo1 = new HQLInfo();
		/*
		 * hqlInfo1.setFromBlock(
		 * "com.landray.kmss.hr.staff.model.HrStaffPersonInfo hrStaffPersonInfo"
		 * );
		 */
		hqlInfo1.setModelName(HrStaffPersonInfo.class.getName());
		StringBuffer whereBlockSb1 = new StringBuffer();
		whereBlockSb1.append("hrStaffPersonInfo.fdOrgType=:orgType ");
		hqlInfo1.setParameter("orgType", HR_TYPE_PERSON);
		if (StringUtil.isNotNull(parentId)) {
			whereBlockSb1.append("and hrStaffPersonInfo.hbmParent.fdId=:parentId ");
			hqlInfo1.setParameter("parentId", parentId);
		} else if (elementIds != null) {
			// 开启组织可见性，parentId为空时不可能存在人员
			whereBlockSb1.append("and 1=2 ");
		} else {
			whereBlockSb1.append("and hrStaffPersonInfo.hbmParent=null ");
		}

		if (exceptValues != null) {
			whereBlockSb1.append("and hrStaffPersonInfo.fdId not in (")
					.append(SysOrgUtil.buildInBlock(exceptValues)).append(") ");
		}

		String whereBlock1 = this.buildWhereBlock(orgType,
				whereBlockSb1.toString(), "hrStaffPersonInfo");
		hqlInfo1.setWhereBlock(whereBlock1);

		// hqlInfo1.setOrderBy("sysOrgPerson.fdNamePinYin"); // 按人员姓名拼音升序进行排序
		hqlInfo1.setGetCount(false);
		hqlInfo1.setPageNo(pageNo);
		hqlInfo1.setRowSize(maxPageSize);
		hqlInfo1.setAuthCheckType("DIALOG_READER"); // 加入组织可见性配置过滤 （组织权限中心》基础设置》地址本隔离》组织可见性配置）
		Page page1 = hrOrganizationElementService.findPage(hqlInfo1);
		List<HrOrganizationElement> personElementList1 = page1
				.getPageno() == pageNo ? page1.getList()
						: new ArrayList<HrOrganizationElement>();
		personElementList.addAll(personElementList1);

		// 2、查询挂在指定部门的岗位下的人
		if ((orgType & HR_TYPE_PERSON) == HR_TYPE_PERSON
				&& StringUtil.isNotNull(parentId)) {
			int maxPageSize2 = maxPageSize - personElementList1.size();
			HQLInfo hqlInfo2 = new HQLInfo();
			hqlInfo2.setModelName(HrStaffPersonInfo.class.getName());

			StringBuffer whereBlockSb2 = new StringBuffer();
			whereBlockSb2.append("hrStaffPersonInfo.fdOrgType=:orgType ");
			hqlInfo2.setParameter("orgType", HR_TYPE_PERSON);
			whereBlockSb2.append("and hrStaffPersonInfo.hbmPosts.fdId in "); // 查询挂在指定部门的岗位下的人
			whereBlockSb2.append("(");
			whereBlockSb2
					.append("select postElement.fdId from com.landray.kmss.hr.organization.model.HrOrganizationElement postElement where postElement.hbmParent.fdId=:parentId and postElement.fdOrgType=")
					.append(HR_TYPE_POST);
			whereBlockSb2.append(") ");
			hqlInfo2.setParameter("parentId", parentId);

			if (exceptValues != null) {
				whereBlockSb2.append("and hrStaffPersonInfo.fdId not in (")
						.append(SysOrgUtil.buildInBlock(exceptValues))
						.append(") ");
			}

			String whereBlock = this.buildWhereBlock(orgType,
					whereBlockSb2.toString(), "hrStaffPersonInfo");
			hqlInfo2.setWhereBlock(whereBlock);

			// hqlInfo2.setOrderBy("sysOrgPerson.fdNamePinYin"); //
			// 按人员姓名拼音升序进行排序
			hqlInfo2.setGetCount(false);
			hqlInfo2.setPageNo(pageNo);
			hqlInfo2.setRowSize(maxPageSize2);
			hqlInfo2.setCheckParam(SysAuthConstant.CheckType.AllCheck, SysAuthConstant.AllCheck.NO); // 取消默认的权限过滤
			Page page2 = hrStaffPersonInfoService.findPage(hqlInfo2);
			List<HrOrganizationElement> personElementList2 = page2
					.getPageno() == pageNo ? page2.getList()
							: new ArrayList<HrOrganizationElement>();
			
			for (HrOrganizationElement element : personElementList2) {
				if (!personElementList.contains(element)) { // 剔除人员重复数据
					personElementList.add(element);
				}
			}

		}

		// 去除人员重复数据
		List<HrOrganizationElement> noRepeatPersonElementList = new ArrayList<HrOrganizationElement>();
		for (HrOrganizationElement element : personElementList) {
			if (!resultList.contains(element)) {
				noRepeatPersonElementList.add(element);
			}
		}

		// 人员需要进行职级过滤
/*		List<HrOrganizationElement> filterPersonList = sysOrganizationStaffingLevelService
				.getStaffingLevelFilterResult(noRepeatPersonElementList);*/
		List<HrOrganizationElement> filterPersonList  = noRepeatPersonElementList;
		resultList.addAll(filterPersonList);

		// 如果职级过滤掉了一部分人，查找下一批数据补齐人员满足最大返回数量
		if (resultList.size() < maxPageSize
				&& noRepeatPersonElementList.size() > 0
				&& filterPersonList.size() < noRepeatPersonElementList.size()) {
			pageNo = pageNo + 1;
			this.getPersonForResultList(parentId, elementIds, maxPageSize,
					pageNo, exceptValues, orgType, resultList);
		}

		return resultList;
	}

	@Override
	public List detailList(RequestContext xmlContext) throws Exception {
		String orgIds = xmlContext.getParameter("orgIds");
		List rtnList = new ArrayList();
		List<HrOrganizationElement> elemList = null;
		if (StringUtil.isNotNull(orgIds)) {
			elemList = hrOrganizationElementService
					.findByPrimaryKeys(orgIds.split(";"));
			for (Iterator iterator = elemList.iterator(); iterator.hasNext();) {
				rtnList.add(
						formatElement((HrOrganizationElement) iterator.next(),
								true));
			}
		}
		return rtnList;
	}

	@Override
	public List searchList(RequestContext xmlContext) throws Exception {
		int orgType = HR_TYPE_PERSON;
		String para = xmlContext.getParameter("orgType");
		if (StringUtil.isNotNull(para)) {
			try {
				orgType = Integer.parseInt(para);
			} catch (NumberFormatException e) {
			}
		}
		String[] keys = xmlContext.getParameter("keyword")
				.replaceAll("'", "''").split("\\s*[,;，；]\\s*");
		StringBuffer whereBf = new StringBuffer();
		for (String key : keys) {
			if (StringUtil.isNull(key)) {
				continue;
			}
			key = key.toLowerCase();
			whereBf.append(
					" or lower(hrOrganizationElement.fdName) like '%" + key
							+ "%' or lower(hrOrganizationElement.fdLoginName) like '%"
							+ key
							+ "%' or lower(hrOrganizationElement.fdNameSimplePinyin) like '%"
					+ key // 简拼搜索
							+ "%' "
							+ "or lower(hrOrganizationElement.fdNamePinYin) like '%"
					+ key + "%'");
		}
		if (whereBf.length() == 0) {
			return null;
		}

		String whereBlock = whereBf.substring(4);
		HQLInfo hqlInfo = new HQLInfo();
		whereBlock = SysOrgHQLUtil.buildWhereBlock(orgType, whereBlock,
				"hrOrganizationElement");
		hqlInfo.setWhereBlock(whereBlock);
		hqlInfo.setOrderBy(
				"hrOrganizationElement.fdOrgType desc, hrOrganizationElement.fdOrder,  hrOrganizationElement."
						+ SysLangUtil.getLangFieldName("fdName"));
		hqlInfo.setRowSize(101);
		hqlInfo.setCacheable(true);
		hqlInfo.setGetCount(false);
		hqlInfo.setAuthCheckType("DIALOG_READER");
		return formatListData(xmlContext, hrOrganizationElementService
				.findPage(hqlInfo).getList(), false);
	}

	// needSort 是否需要排序 true：排序规则 先群组后拼音
	protected List formatListData(RequestContext xmlContext, List orgList,
			boolean needSort) throws Exception {
		List rtnList = new ArrayList();
	/*	orgList = sysOrganizationStaffingLevelService
				.getStaffingLevelFilterResult(orgList);*/
		if (orgList == null || orgList.isEmpty()) {
			return rtnList;
		}
		// Collections.sort(orgList);
		// 先构造机构|部门信息
		List deptList = new ArrayList();
		List orgList1 = new ArrayList();
		List postList = new ArrayList();
		List personList = new ArrayList();
		List groupList = new ArrayList();
		String exceptValue = xmlContext.getParameter("exceptValue");

		String curCate = "";
		for (Iterator iterator = orgList.iterator(); iterator.hasNext();) {
			HrOrganizationElement tmpOrg = (HrOrganizationElement) iterator
					.next();

			if (StringUtil.isNotNull(exceptValue)) {// 排除例外
				String[] exceptValues = exceptValue.split("[;；,，]");
				if (Arrays.asList(exceptValues).contains(tmpOrg.getFdId())) {
					continue;
				}
			}
			int orgType = tmpOrg.getFdOrgType();
			if ((orgType & HR_TYPE_DEPT) == orgType) {// 部门
				deptList.add(formatElement(tmpOrg));
			} else if ((orgType & HR_TYPE_ORG) == orgType) {// 机构
				orgList1.add(formatElement(tmpOrg));
			} else if (orgType == HR_TYPE_POST) {
				postList.add(formatElement(tmpOrg));
			} else if (orgType == HR_TYPE_PERSON) {
				if (!needSort) {
					String pinyin = tmpOrg.getFdNamePinYin();
					if (StringUtil.isNull(pinyin)) {
						pinyin = "#";
					} else {
						pinyin = pinyin.substring(0, 1);
						if (StringUtil.getIntFromString(pinyin, -1) != -1) {
							pinyin = "#";
						}
					}
					// 暂时不按照拼音排序，解决企业领导排序问题
					// if (!pinyin.equalsIgnoreCase(curCate)) {
					// personList.add(formatHeader(pinyin.toUpperCase()));
					// curCate = pinyin;
					// }
					personList.add(formatElement(tmpOrg));
				} else {
					personList.add(formatElement(tmpOrg));
				}
			}
			/*
			 * else if (orgType == ORG_TYPE_GROUP) {
			 * groupList.add(formatElement(tmpOrg)); }
			 */
		}
		if (!orgList1.isEmpty()) {
			rtnList.add(formatHeader("1"));
			Collections.sort(orgList1, new OrderComparator());
			rtnList.addAll(orgList1);
		}
		if (!deptList.isEmpty()) {
			rtnList.add(formatHeader("2"));
			Collections.sort(deptList, new OrderComparator());
			rtnList.addAll(deptList);
		}
		if (!postList.isEmpty()) {
			rtnList.add(formatHeader("4"));
			Collections.sort(postList, new OrderComparator());
			rtnList.addAll(postList);
		}
		if (needSort) {
			Collections.sort(personList, new PinyinComparator());
			List tmpPersonList = new ArrayList();
			curCate = "";
			for (Iterator iterator = personList.iterator(); iterator
					.hasNext();) {
				Map tmpOrg = (Map) iterator.next();
				String pinyin = (String) tmpOrg.get("pinyin");
				if (StringUtil.isNull(pinyin)) {
					pinyin = "#";
				} else {
					pinyin = pinyin.substring(0, 1);
					if (StringUtil.getIntFromString(pinyin, -1) != -1) {
						pinyin = "#";
					}
				}
				if (!pinyin.equalsIgnoreCase(curCate)) {
					tmpPersonList.add(formatHeader(pinyin.toUpperCase()));
					curCate = pinyin;
				}
				tmpPersonList.add(tmpOrg);
			}
			personList = null;
			personList = tmpPersonList;
		}
		if (!personList.isEmpty()) {
			rtnList.add(formatHeader("8"));
			rtnList.addAll(personList);
		}
		if (!groupList.isEmpty()) {
			rtnList.add(formatHeader("16"));
			Collections.sort(groupList, new OrderComparator());
			rtnList.addAll(groupList);
		}
		return rtnList;
	}

	protected Map formatHeader(String label) {
		Map tmpMap = new HashMap();
		tmpMap.put("label", label);
		tmpMap.put("header", "true");
		return tmpMap;
	}

	private Map formatElement(HrOrganizationElement orgElem) throws Exception {
		return formatElement(orgElem, false);
	}

	protected Map formatElement(HrOrganizationElement orgElem,
			boolean needDetail)
			throws Exception {
		Map tmpMap = new HashMap();
		try {
			tmpMap.put("fdId", orgElem.getFdId());
			tmpMap.put("label", HrOrgDialogUtil.getDeptLevelNames(orgElem));
			// 获取系统组织架构基本设置信息

			BaseAppConfig sysOrgCon = new SysOrganizationConfig();
			String display = sysOrgCon.getDataMap()
					.get("kmssOrgDeptLevelDisplay");
			if (display != null) {
				if (display.equals(
						String.valueOf(SysOrganizationConfig.DEPT_LEVEL_ALL))) {
					// 显示部门全路径
					tmpMap.put("labelLevel", orgElem.getDeptLevelNames());
				} else if (display.equals(String
						.valueOf(SysOrganizationConfig.DEPT_LEVEL_ONLY_LAST))) {
					// 仅显示部门末级部门
					tmpMap.put("labelLevel", orgElem.getFdName());
				} else if (display.equals(String
						.valueOf(SysOrganizationConfig.DEPT_LEVEL_LATELY))) {
					// 显示最近的N级部门
					String displayLength = sysOrgCon.getDataMap()
							.get("kmssOrgDeptLevelDisplayLength");
					if (null != displayLength) {
						Integer dl = Integer.parseInt(displayLength);
						String deptLevelNames = orgElem.getDeptLevelNames();
						if (dl == 0 || StringUtil.isNull(deptLevelNames)) {
							tmpMap.put("labelLevel", "");
						} else {
							String[] dlNamesArr = deptLevelNames.split("_");
							if (dlNamesArr.length > dl) {
								String dlNames = "";
								for (int i = (dlNamesArr.length
										- dl); i < dlNamesArr.length; i++) {
									dlNames = dlNames + "_" + dlNamesArr[i];
								}
								tmpMap.put("labelLevel", dlNames.substring(1));
							} else {
								tmpMap.put("labelLevel",
										orgElem.getDeptLevelNames());
							}
						}
					} else {
						// 默认仅显示末级部门
						tmpMap.put("labelLevel", orgElem.getFdName());
					}
				}
			} else {
				// 默认仅显示末级部门
				tmpMap.put("labelLevel", orgElem.getFdName());
			}
			tmpMap.put("type", orgElem.getFdOrgType());
			tmpMap.put("fdIsAvailable", orgElem.getFdIsAvailable());
			tmpMap.put("order", orgElem.getFdOrder());
			tmpMap.put("pinyin", orgElem.getFdNamePinYin());
			if (orgElem.getFdOrgType().equals(HR_TYPE_PERSON)
					|| orgElem.getFdOrgType().equals(HR_TYPE_POST)
					|| orgElem.getFdOrgType().equals(HR_TYPE_ORG)
					|| orgElem.getFdOrgType().equals(HR_TYPE_DEPT)) {
				tmpMap.put("parentNames",
						StringUtil.isNotNull(orgElem.getFdParentsName("_"))
								? orgElem.getFdParentsName("_") : "");
			}
			if (needDetail) {
				if (orgElem.getFdParent() != null) {
					tmpMap.put("parentId", orgElem.getFdParent().getFdId());
				}
			}
			if (orgElem.getFdOrgType() == HR_TYPE_PERSON) {
				tmpMap.put("icon", PersonInfoServiceGetter
						.getPersonHeadimageUrl(orgElem.getFdId()));

				HrStaffPersonInfo p = (HrStaffPersonInfo) hrStaffPersonInfoService
						.findByPrimaryKey(orgElem.getFdId());

				String showStaffingLevel = new SysOrganizationConfig()
						.getShowStaffingLevel();

				if (p.getFdStaffingLevel() != null
						&& "true".equals(showStaffingLevel)) {
					tmpMap.put("staffingLevel",
							p.getFdStaffingLevel().getFdName());
				}
				if (p.getFdParent() != null
						&& p.getFdParent().getHbmThisLeader() != null
						&& p.getFdId().equals(
								p.getFdParent().getHbmThisLeader().getFdId())) {
					tmpMap.put("leaderType", p.getFdParent().getFdOrgType());
				}

			}
		} catch (Exception e) {
			logger.error(
					"移动端地址本转换组织机构数据发生异常：MobileAddressServiceImp.formatElement");
			logger.error("class:" + orgElem.getClass().toString() + "     "
					+ "orgType:" + orgElem.getFdOrgType() + "     " + "fdId:"
					+ orgElem.getFdId() + "     " + "fdName:"
					+ orgElem.getFdName());
			logger.error("", e);
			throw e;
		}
		return tmpMap;
	}

	private class OrderComparator implements Comparator {
		@Override
		public int compare(Object o1, Object o2) {
			Object order1 = ((Map) o1).get("order");
			Object order2 = ((Map) o2).get("order");
			if (order1 == null) {
				order1 = Integer.MAX_VALUE;
			}
			if (order2 == null) {
				order2 = Integer.MAX_VALUE;
			}
			return ((Integer) order1).compareTo((Integer) order2);
		}
	}

	private class PinyinComparator implements Comparator {
		@Override
		public int compare(Object o1, Object o2) {
			Object str1 = ((Map) o1).get("pinyin");
			Object str2 = ((Map) o2).get("pinyin");
			if (str1 == null) {
				str1 = "";
			}
			if (str2 == null) {
				str2 = "";
			}
			return ((String) str1).compareTo((String) str2);
		}
	}

	private class SequenceComparator
			implements Comparator<HrOrganizationElement> {

		private String sequence;

		public SequenceComparator(String sequence) {
			this.sequence = sequence;
		}

		@Override
		public int compare(HrOrganizationElement p1, HrOrganizationElement p2) {
			int p1Index = sequence.indexOf(p1.getFdId());
			int p2Index = sequence.indexOf(p2.getFdId());
			if (p1Index > p2Index) {
				return 1;
			}
			return -1;
		}
	}

	@Override
	public JSONObject personList(RequestContext xmlContext) throws Exception {
		JSONObject result = new JSONObject();
		String count = xmlContext.getParameter("count");
		String personId = xmlContext.getParameter("personId");

		IHrStaffPersonInfoService hrStaffPersonInfoService = (IHrStaffPersonInfoService) SpringBeanUtil
				.getBean("hrStaffPersonInfoService");
		ISysOrgCoreService sysOrgCoreService = (ISysOrgCoreService) SpringBeanUtil
				.getBean("sysOrgCoreService");
		if (StringUtil.isNotNull(personId)) {
			JSONArray array = new JSONArray();
			List<String> l = ArrayUtil.convertArrayToList(personId.split(";"));
			List<String> personIds = sysOrgCoreService.expandToPersonIds(l);
			List<String> selectPersonIds = new ArrayList<String>();

			result.put("total", personIds.size());
			int size = personIds.size() > Integer.parseInt(count) ? Integer
					.parseInt(count) : personIds.size();
			selectPersonIds = personIds.subList(0, size);
			if (selectPersonIds.size() > 0) {
				HQLInfo hqlInfo = new HQLInfo();
				hqlInfo.setWhereBlock(HQLUtil.buildLogicIN("fdId",
						selectPersonIds));
				List<HrOrganizationElement> persons = hrOrganizationElementService
						.findList(hqlInfo);
				Collections.sort(persons, new SequenceComparator(personId));

				for (HrOrganizationElement person : persons) {
					JSONObject json = genPersonJson(xmlContext, person);
					array.add(json);
				}
			}
			result.put("list", array);
		}
		return result;
	}

	@Override
	public JSONObject personDetailList(RequestContext xmlContext)
			throws Exception {
		JSONObject result = new JSONObject();
		String s_pageno = xmlContext.getParameter("pageno");
		String s_rowsize = xmlContext.getParameter("rowsize");
		int pageno = 1;
		int rowsize = SysConfigParameters.getRowSize();
		if (s_pageno != null && s_pageno.length() > 0) {
			pageno = Integer.parseInt(s_pageno);
		}
		if (s_rowsize != null && s_rowsize.length() > 0) {
			rowsize = Integer.parseInt(s_rowsize);
		}
		ISysOrgCoreService sysOrgCoreService = (ISysOrgCoreService) SpringBeanUtil
				.getBean("sysOrgCoreService");
		JSONArray array = new JSONArray();
		String personId = xmlContext.getParameter("personId");

		List<String> l = ArrayUtil.convertArrayToList(personId.split(";"));
		List<String> personIds = sysOrgCoreService.expandToPersonIds(l);
		int start = (pageno - 1) * rowsize;
		int end = pageno * rowsize > personIds.size() ? personIds.size()
				: pageno * rowsize;
		List<String> selectPersonIds = personIds.subList(start, end);
		if (selectPersonIds.size() > 0) {
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock(
					HQLUtil.buildLogicIN("fdId", selectPersonIds));
			List<HrOrganizationElement> persons = hrOrganizationElementService
					.findList(hqlInfo);
			Collections.sort(persons, new SequenceComparator(personId));
			for (HrOrganizationElement person : persons) {
				JSONObject json = genPersonJson(xmlContext, person);
				array.add(json);
			}
		}
		JSONObject page = new JSONObject();
		page.put("currentPage", pageno);
		page.put("pageSize", rowsize);
		page.put("totalSize", personIds.size());
		result.put("page", page);
		result.put("datas", array);
		return result;
	}

	private JSONObject genPersonJson(RequestContext xmlContext,
			HrOrganizationElement person) {
		JSONObject json = new JSONObject();
		json.put("fdId", person.getFdId());
		json.put("name", person.getFdName());
		String src = PersonInfoServiceGetter.getPersonHeadimageUrl(person
				.getFdId());
		json.put("fdParentName", person.getFdParentsName());
		json.put("src", src);
		return json;
	}

	@Override
	public List addCustomList(RequestContext xmlContext) throws Exception {
		String scope = xmlContext.getParameter("scope");
		String scopeType = xmlContext.getParameter("scopeType");
		String orgType = xmlContext.getParameter("orgType");
		String keyword = xmlContext.getParameter("keyword");
		String scopeHandlerIds = "";
		HrOrganizationElement currentUser = (HrOrganizationElement) getHrOrganizationElementService()
				.findByPrimaryKey(UserUtil.getUser().getFdId());
		List<HrOrganizationElement> elemList = null;
		if ("22".equals(scope)) {
			// 本机构
			scopeHandlerIds = (currentUser.getFdParentOrg() != null)
					? currentUser.getFdParentOrg().getFdId() : "";
			elemList = findChildren(scopeHandlerIds, orgType, currentUser,
					keyword, false);
		} else if ("33".equals(scope)) {
			// 本部门
			scopeHandlerIds = (currentUser.getFdParent() != null)
					? currentUser.getFdParent().getFdId() : "";
			List<HrOrganizationElement> fdPosts = currentUser.getFdPosts();
			for (HrOrganizationElement post : fdPosts) {
				if (post.getFdParent() != null) {
					if (StringUtil.isNotNull(scopeHandlerIds)) {
						if (!scopeHandlerIds
								.contains(post.getFdParent().getFdId())) {
							scopeHandlerIds += ";"
									+ post.getFdParent().getFdId();
						}
					} else {
						scopeHandlerIds = post.getFdParent().getFdId();
					}
				}
			}
			elemList = findChildren(scopeHandlerIds, orgType, currentUser,
					keyword, true);
		} else if ("44".equals(scope)) {
			// 自定义
			scopeHandlerIds = getScript(xmlContext);
			if (!StringUtil.isNull(scopeHandlerIds)) {
				if (LbpmConstants.HANDLER_SELECT_TYPE_FORMULA
						.equals(scopeType)) {
					elemList = getFormulaValue(xmlContext, scopeHandlerIds);
				} else {
					//elemList = getResultList(scopeHandlerIds, currentUser);
				}
			}
		}
		if (StringUtil.isNotNull(keyword) && elemList != null) {
			if (elemList.size() == 1 && elemList.get(0) != null) {

			} else {
				elemList = searchByKeyWord(elemList,
						"44".equals(scope) ? keyword : "");
			}
		}
		if (elemList == null
				|| (elemList.size() == 1 && elemList.get(0) == null)) {
			return formatListData(xmlContext, null, true);
		}
		return formatListData(xmlContext, elemList, true);
	}

	private String getScript(RequestContext requestInfo) {
		String script = "";
		String extendFilePath = requestInfo.getParameter("extendFilePath");
		String fdControlId = requestInfo.getParameter("fdControlId");
		String detiltabId = "";
		if (fdControlId.indexOf(".") > -1) {
			detiltabId = fdControlId.substring(0, fdControlId.indexOf("."));
			fdControlId = fdControlId
					.substring(fdControlId.lastIndexOf(".") + 1);
		}
		DictLoadService sysFormDictLoadService = (DictLoadService) SpringBeanUtil
				.getBean("sysFormDictLoadService");
		SysDictExtendModel extendModel = sysFormDictLoadService
				.loadExistFileDict(extendFilePath);
		List<SysDictCommonProperty> comPropertyList = extendModel
				.getPropertyList();
		for (SysDictCommonProperty dictProperty : comPropertyList) {
			if (dictProperty instanceof SysDictExtendElementProperty) {
				if (dictProperty.getName().equalsIgnoreCase(fdControlId)) {
					script = ((SysDictExtendElementProperty) dictProperty)
							.getCustomElementProperties();
					break;
				}
			} else if (dictProperty instanceof SysDictExtendSubTableProperty) {
				if (dictProperty.getName().equalsIgnoreCase(detiltabId)) {
					List<SysDictCommonProperty> propertyList = ((SysDictExtendSubTableProperty) dictProperty)
							.getElementDictExtendModel().getPropertyList();
					boolean bool = false;
					for (SysDictCommonProperty commonProperty : propertyList) {
						if (commonProperty instanceof SysDictExtendElementProperty) {
							if (commonProperty.getName()
									.equalsIgnoreCase(fdControlId)) {
								script = ((SysDictExtendElementProperty) commonProperty)
										.getCustomElementProperties();
								bool = true;
								break;
							}
						}
					}
					if (bool) {
						break;
					}
				}
			}
		}
		if (StringUtil.isNotNull(script)) {
			/* script = StringEscapeUtils.unescapeHtml(script); */
			JSONObject customProperties = toJsonObject(script);
			if (customProperties != null) {
				if (customProperties.containsKey("formula")) {
					script = customProperties.getString("formula");
				}
			}
		}
		return StringUtil.isNull(script) ? ""
				: StringEscapeUtils.unescapeHtml(script);
	}

	private JSONObject toJsonObject(String customElementProperties) {
		JSONObject json = null;
		try {
			json = JSONObject.fromObject(customElementProperties);
		} catch (Exception e) {

		}
		return json;
	}

	private List<HrOrganizationElement> searchByKeyWord(
			List<HrOrganizationElement> elemList,
			String keyword) throws Exception {
		List<HrOrganizationElement> rtnList = new ArrayList<HrOrganizationElement>();
		Collection<String> tempOrgIds = new HashSet<String>();
		for (HrOrganizationElement handler : elemList) {
			if (handler != null && !tempOrgIds.contains(handler.getFdId())) {
				if (StringUtil.isNotNull(keyword)) {
					if (handler
							.getFdOrgType() == HR_TYPE_PERSON) {
						HrStaffPersonInfo handlerPerson = (HrStaffPersonInfo) hrStaffPersonInfoService
								.findByPrimaryKey(handler.getFdId());
						if (handler.getFdName().contains(keyword)
								|| (StringUtil.isNotNull(
										handlerPerson.getFdLoginName())
										&& handlerPerson.getFdLoginName()
												.contains(keyword))
								/*
								 * 人事档案没有昵称故注释 || (StringUtil.isNotNull(
								 * handlerPerson.getFdNickName()) &&
								 * handlerPerson.getFdNickName()
								 * .contains(keyword))
								 */
								|| (StringUtil.isNotNull(
										handlerPerson.getFdNamePinYin())
										&& handlerPerson.getFdNamePinYin()
												.contains(keyword))
								|| (StringUtil.isNotNull(
										handlerPerson.getFdNameSimplePinyin())
										&& handlerPerson.getFdNameSimplePinyin()
												.contains(keyword))) {
							rtnList.add(handler);
							tempOrgIds.add(handler.getFdId());
						}
					} else {
						if (StringUtil.isNull(keyword)
								|| handler.getFdName().contains(keyword)
								|| (StringUtil
										.isNotNull(handler.getFdNamePinYin())
										&& handler.getFdNamePinYin()
												.contains(keyword))
								|| (StringUtil.isNotNull(
										handler.getFdNameSimplePinyin())
										&& handler.getFdNameSimplePinyin()
												.contains(keyword))) {
							rtnList.add(handler);
							tempOrgIds.add(handler.getFdId());
						}
					}
				} else {
					rtnList.add(handler);
					tempOrgIds.add(handler.getFdId());
				}
			}
		}
		return rtnList;
	}

	private List<HrOrganizationElement> getFormulaValue(
			RequestContext requestInfo,
			String script)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException {
		List<HrOrganizationElement> rtnVal = new ArrayList<HrOrganizationElement>();
		String modelId = requestInfo.getParameter("modelId");
		String modelName = requestInfo.getParameter("modelName");
		String extendFilePath = requestInfo.getParameter("extendFilePath");
		IBaseModel mainModel = getMainModel(modelId, modelName, extendFilePath);
		Map<String, Object> params = setParms(requestInfo);

		// 扩展的数据填充
		mainModel = fillupDataToModel(mainModel, params);

		// 规则提供器
		IRuleProvider ruleProvider = processServiceManager.getRuleService()
				.getRuleProvider(new NoExecutionEnvironment(mainModel));
		// 追加解析器
		ruleProvider.addRuleParser(LbpmFunction.class.getName());
		// 规则事实参数
		RuleFact fact = new RuleFact(mainModel);
		fact.addParameter(new ModelVarProviderExtend(params));
		fact.addParameter(mainModel);
		fact.setModelName(modelName);
		fact.setScript(script);
		fact.setReturnType(HrOrganizationElement.class.getName() + "[]");
		try {
			rtnVal = (List<HrOrganizationElement>) ruleProvider
					.executeRules(fact);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rtnVal;
	}

	private IBaseModel fillupDataToModel(IBaseModel mainModel,
			Map<String, Object> params) {
		IExtension[] extensions = Plugin.getExtensions(MODEL_FILLER, "*",
				"decision");
		for (IExtension extension : extensions) {
			ModelDataFiller filler = Plugin.getParamValue(extension,
					"modelFiller");
			mainModel = filler.fillupDataToModel(mainModel, params);
		}
		return mainModel;
	}

	private Map<String, Object> setParms(RequestContext requestInfo) {
		Map<String, String> params = new HashMap<String, String>();
		Enumeration<?> ele = requestInfo.getRequest().getParameterNames();
		while (ele.hasMoreElements()) {
			String key = ele.nextElement().toString();
			if (paramsExcept.contains(key)) {
				continue;
			}
			String value = requestInfo.getParameter(key);
			if (value == null || "null".equals(value) || "".equals(value)) {
				continue;
			}
			params.put(key, value);
		}
		Map<String, Object> newParams = filterParamsData(params);
		return newParams;
	}

	private Map<String, Object> filterParamsData(Map<String, String> params) {
		Map<String, Object> tempMap = new HashMap<String, Object>();
		Iterator<Entry<String, String>> iter = params.entrySet().iterator();

		while (iter.hasNext()) {
			Entry<String, String> entry = iter.next();
			String key = entry.getKey();
			Object valObj = entry.getValue();

			// checkbox选单值是后面会多一个逗号
			if (valObj.toString().indexOf(",") >= 0
					&& valObj.toString().indexOf(",") == valObj.toString()
							.length() - 1) {
				valObj = valObj.toString().substring(0,
						valObj.toString().length() - 1);
			}
			// 如果为地址本数据，则设置为map
			// 数据格式为“id1;id2;id3,name1;name2;name3”
			if (valObj.toString().indexOf(",") != -1) {
				Map<String, String> m = new HashMap<String, String>();
				String[] s = valObj.toString().split(",");
				for (int i = 0; i < s.length - 1; i++) {
					m.put("id", s[i]);
					m.put("name", s[i + 1]);
				}
				valObj = m;
			}
			tempMap.put(key, valObj);
		}
		return tempMap;
	}

	private IBaseModel getMainModel(String modelId, String modelName,
			String extendFilePath) {
		IBaseModel mainModel = processServiceManager
				.getMainModelPerstenceService()
				.findByPrimaryKey(modelId, modelName, true);
		// 起草状态的文档，需要手动构建model
		if (mainModel == null) {
			try {
				mainModel = (IBaseModel) ClassUtils.forName(modelName).newInstance();
			} catch (Exception ex) {
				throw new RuntimeException("生成主model类出错: " + modelName, ex);
			}

			// 对包含了扩张属性的字段是model还必须设置extendFilePath
			if (mainModel instanceof IExtendDataModel) {
				IExtendDataModel model = (IExtendDataModel) mainModel;
				model.setExtendFilePath(extendFilePath);
			}
			if (mainModel instanceof IBaseCreateInfoModel) {
				IBaseCreateInfoModel model = (IBaseCreateInfoModel) mainModel;
				if (model.getDocCreator() == null) {
					model.setDocCreator(UserUtil.getUser());
				}
				if (model.getDocCreateTime() == null) {
					model.setDocCreateTime(new Date());
				}
			}
		}
		return mainModel;
	}

	private List<HrOrganizationElement> getResultList(String scopeHandlerIds,
			HrOrganizationElement currentUser) {
		List<HrOrganizationElement> scopeHandlerResultList = null;
		/*
		 * List<HrOrganizationElement> scopeHanderList = getOrgServer()
		 * .findByPrimaryKeys(scopeHandlerIds.split(";")); String
		 * handlerIdentity = currentUser.getFdId(); HrOrganizationElement
		 * sysOrgElement = getOrgServer() .findByPrimaryKey(handlerIdentity);
		 * scopeHandlerResultList =
		 * getOrgServer().parseSysOrgRole(scopeHanderList, sysOrgElement);
		 */
		return scopeHandlerResultList;
	}

	private List<HrOrganizationElement> findChildren(String scopeHandlerIds,
			String orgType,
			HrOrganizationElement currentUser, String keyword, boolean isDept)
			throws Exception {
		IHrOrgCoreService hrOrgCoreService = (IHrOrgCoreService) SpringBeanUtil
				.getBean("hrOrgCoreService");
		List<HrOrganizationElement> allChildren = new ArrayList<HrOrganizationElement>();
		List<HrOrganizationElement> scopeHandlerResultList = null;
		if (!StringUtil.isNull(scopeHandlerIds)) {
			scopeHandlerResultList = getResultList(scopeHandlerIds,
					currentUser);
			for (int i = 0; i < scopeHandlerResultList.size(); i++) {
				HrOrganizationElement handler = scopeHandlerResultList.get(i);
				if (orgType.contains("DEPT")) {
					if (StringUtil.isNotNull(keyword)) {
						ArrayUtil.concatTwoList(
								hrOrgCoreService.findAllChildren(handler,
										HR_TYPE_DEPT,
										"sysOrgDept.fdName like '%" + keyword
												+ "%' or sysOrgDept.fdNamePinYin like '%"
												+ keyword
												+ "%' or sysOrgDept.fdNameSimplePinyin like '%"
												+ keyword
												+ "%'"),
								allChildren);
					} else {
						ArrayUtil.concatTwoList(
								hrOrgCoreService.findAllChildren(handler,
										HR_TYPE_DEPT),
								allChildren);
					}
				}
				if (orgType.contains("POST")) {
					if (StringUtil.isNotNull(keyword)) {
						ArrayUtil.concatTwoList(
								hrOrgCoreService.findAllChildren(handler,
										HR_TYPE_POST,
										" sysOrgPost.fdName like '%" + keyword
												+ "%' or sysOrgPost.fdNamePinYin like '%"
												+ keyword
												+ "%' or sysOrgPost.fdNameSimplePinyin like '%"
												+ keyword
												+ "%'"),
								allChildren);
						if (allChildren.size() >= 50) {
							break;
						}
					} else {
						ArrayUtil.concatTwoList(
								hrOrgCoreService.findAllChildren(handler,
										HR_TYPE_POST),
								allChildren);
					}
				}
				if (orgType.contains("PERSON")) {
					HQLInfo hqlInfo = new HQLInfo();
					hqlInfo.setPageNo(1);
					hqlInfo.setRowSize(50);
					hqlInfo.setDistinctType(HQLInfo.DISTINCT_YES);
					String whereBlock = "";
					if (StringUtil.isNotNull(keyword)) {
						whereBlock = "(sysOrgPerson.fdLoginName like '%"
								+ keyword
								+ "%' or sysOrgPerson.fdName like '%" + keyword
								+ "%' "
								+ "or sysOrgPerson.fdNickName like '%" + keyword
								+ "%' "
								+ "or sysOrgPerson.fdNamePinYin like '%"
								+ keyword + "%' "
								+ "or sysOrgPerson.fdNameSimplePinyin like '%"
								+ keyword + "%') "
								+ "and ((sysOrgPerson.fdHierarchyId like '"
								+ handler.getFdHierarchyId() + "%'";
						if (isDept) {
							whereBlock += " and sysOrgPost.fdId is null) or sysOrgPerson.fdHierarchyId like '"
									+ handler.getFdHierarchyId()
									+ "%' or sysOrgPost.fdHierarchyId like '"
									+ handler.getFdHierarchyId() + "%')";
							hqlInfo.setJoinBlock(
									"left join sysOrgPerson.hbmPosts sysOrgPost");
						} else {
							whereBlock += "))";
						}
					} else {
						whereBlock = "((sysOrgPerson.fdHierarchyId like '"
								+ handler.getFdHierarchyId() + "%'";
						if (isDept) {
							whereBlock += " and sysOrgPost.fdId is null) or sysOrgPerson.fdHierarchyId like '"
									+ handler.getFdHierarchyId()
									+ "%' or sysOrgPost.fdHierarchyId like '"
									+ handler.getFdHierarchyId() + "%')";
							hqlInfo.setJoinBlock(
									"left join sysOrgPerson.hbmPosts sysOrgPost");
						} else {
							whereBlock += "))";
						}
					}
					hqlInfo.setWhereBlock(SysOrgHQLUtil.buildWhereBlock(
							HR_TYPE_PERSON, whereBlock,
							"sysOrgPerson"));
					ArrayUtil.concatTwoList(
							hrStaffPersonInfoService.findList(hqlInfo),
							allChildren);
					if (allChildren.size() >= 50) {
						break;
					}
				}
			}
		}
		return allChildren;
	}

	private IHrStaffPersonInfoService hrStaffPersonInfoService;

	private ProcessServiceManager processServiceManager;

	private ILbpmOrgParseService getOrgServer() {
		return processServiceManager.getOrgParseService();
	}

	public void
			setHrStaffPersonInfoService(
					IHrStaffPersonInfoService hrStaffPersonInfoService) {
		this.hrStaffPersonInfoService = hrStaffPersonInfoService;
	}

	public void setProcessServiceManager(
			ProcessServiceManager processServiceManager) {
		this.processServiceManager = processServiceManager;
	}

	/**
	 * 获取同部门人员
	 */
	@Override
	public List getDeptMembers(RequestContext request) throws Exception {
		String keyword = request.getParameter("keyword");
		List members = new ArrayList();
		String userId = UserUtil.getUser().getFdId();
		String parentId = "";
		HrOrganizationElement user = (HrOrganizationElement) getHrOrganizationElementService()
				.findByPrimaryKey(userId);
		if (user != null && user.getFdParent() != null) {
			parentId = user.getFdParent().getFdId();
		} else if (user != null && user.getFdParentOrg() != null) {
			parentId = user.getFdParentOrg().getFdId();
		}
		if (StringUtil.isNotNull(parentId)) {
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo
					.setWhereBlock("sysOrgElement.hbmParent.fdId = :parentId");
			hqlInfo.setParameter("parentId", parentId);
			hqlInfo.setWhereBlock(SysOrgHQLUtil
					.buildWhereBlock(HR_TYPE_PERSON
							| HR_TYPE_POST, hqlInfo
									.getWhereBlock(),
							"sysOrgElement"));
			hqlInfo.setOrderBy("sysOrgElement.fdOrder,sysOrgElement."
					+ SysLangUtil.getLangFieldName("fdName"));

			List<HrOrganizationElement> list = getHrOrganizationElementService()
					.findList(hqlInfo);
			/*
			 * 暂时注释 list = sysOrganizationStaffingLevelService
			 * .getStaffingLevelFilterResult(list);
			 */

			members = expandToPersons(list, keyword);
			sortMobileElement(members);

			if (StringUtil.isNotNull(keyword)) {
				Map tmpMap = new HashMap();
				tmpMap.put("label", ResourceUtil.getMessage(
						"{sys-organization:sysOrganizationRecentContact.fdContact}"));
				tmpMap.put("header", "true");
				members.add(0, tmpMap);
			}
		}
		return members;
	}

	/**
	 * 获取下属机构路径
	 */
	@Override
	public List getSubordinatePath(RequestContext xmlContext) throws Exception {
		String fdId = xmlContext.getParameter("fdId");
		JSONArray result = new JSONArray();
		if (StringUtil.isNotNull(fdId)) {
			List<String> orgHierarchyIds = getOrgHierarchyIds();
			HrOrganizationElement org = (HrOrganizationElement) hrOrganizationElementService
					.findByPrimaryKey(fdId);
			if (org != null) {
				List<HrOrganizationElement> ls = new ArrayList<HrOrganizationElement>();
				getOrgOrgParents(ls, org, orgHierarchyIds);
				for (int i = ls.size() - 1; i >= 0; i--) {
					HrOrganizationElement parent = ls.get(i);
					JSONObject obj = new JSONObject();
					obj.put("fdId", parent.getFdId());
					obj.put("label", parent.getFdName());
					result.add(obj);
				}

				JSONObject selfObj = new JSONObject();
				selfObj.put("fdId", org.getFdId());
				selfObj.put("label", org.getFdName());
				result.add(selfObj);
			}

		}
		return result;
	}

	private void getOrgOrgParents(List<HrOrganizationElement> ls,
			HrOrganizationElement sysOrgElement, List<String> orgHierarchyIds) {
		if (sysOrgElement.getFdParent() != null) {
			String hierarchyId = sysOrgElement.getFdParent().getFdHierarchyId();
			for (String id : orgHierarchyIds) {
				if (!id.equals(hierarchyId) && id.indexOf(hierarchyId) > -1) {
					return;
				}
			}
			ls.add(sysOrgElement.getFdParent());
			if (sysOrgElement.getFdParent().getFdParent() != null) {
				getOrgOrgParents(ls, sysOrgElement.getFdParent(),
						orgHierarchyIds);
			}
		}
	}

	/**
	 * 获取所有下属机构
	 */
	@Override
	public List getAllSubordinateOrgs(RequestContext request) throws Exception {
		String keyword = request.getParameter("keyword");
		List rtnMapList = new ArrayList();
		// 上级部门
		String parentId = request.getParameter("parentId");
		List<String> orgHierarchyIds = getOrgHierarchyIds();
		if (orgHierarchyIds == null || orgHierarchyIds.size() == 0) {
			return rtnMapList;
		}
		HQLInfo info = new HQLInfo();
		StringBuilder where = new StringBuilder();
		where.append(
				"fdIsBusiness is true and fdIsAvailable is true ");
		if (StringUtil.isNotNull(keyword)) { // 搜索的情况，TODO：目前还没有办法搜索岗位中的人
			where.append(
					" and fdOrgType = 8 and ( 1=2 ");
			for (String hierarchyId : orgHierarchyIds) {
				where.append(" or fdHierarchyId like '").append(hierarchyId)
						.append("%'");
			}
			where.append(")");
			String[] keys = keyword.replaceAll("'", "''")
					.split("\\s*[,;，；]\\s*");
			StringBuffer whereBf = new StringBuffer();
			for (String key : keys) {
				if (StringUtil.isNull(key)) {
					continue;
				}
				key = key.toLowerCase();
				whereBf.append(" (lower(sysOrgElement.fdName) like '%" + key
						+ "%' or lower(sysOrgElement.fdLoginName) like '%"
						+ key
						+ "%' or lower(sysOrgElement.fdNameSimplePinyin) like '%"
						+ key // 简拼搜索
						+ "%' "
						+ "or lower(sysOrgElement.fdNamePinYin) like '%"
						+ key + "%' ) ");
			}
			if (whereBf.length() != 0) {
				where.append(" and " + whereBf.toString());
			}
		} else if (StringUtil.isNull(parentId)) { // 如果parentId为空，需要查询所有的根，并且需要带权限查询
			Set<String> parentIds = new HashSet<String>();
			// 取离我最近的一级
			for (String orgHierarchyId : orgHierarchyIds) {
				String[] hierarchyIds = orgHierarchyId
						.split(BaseTreeConstant.HIERARCHY_ID_SPLIT);
				for (int i = hierarchyIds.length - 1; i >= 0; i--) {
					if (StringUtil.isNotNull(hierarchyIds[i])) {
						parentIds.add(hierarchyIds[i]);
						break;
					}
				}
			}
			where.append(
					" and fdOrgType in (1, 2, 4, 8) and fdId in (:parentIds)");
			info.setParameter("parentIds", parentIds);
		} else {
			where.append(
					" and fdOrgType in (1, 2, 4, 8) and hbmParent.fdId = :parentId and (fdId in (:orgIds) ");
			for (String hierarchyId : orgHierarchyIds) {
				where.append(" or fdHierarchyId like '").append(hierarchyId)
						.append("%'");
			}
			where.append(")");
			info.setParameter("parentId", parentId);
			info.setParameter("orgIds", splitHierarchyId(orgHierarchyIds));
		}

		info.setWhereBlock(where.toString());
		info.setOrderBy("fdOrgType asc, fdOrder, "
				+ SysLangUtil.getLangFieldName("fdName"));
		List<HrOrganizationElement> list = hrOrganizationElementService
				.findList(info);
		rtnMapList = expandToPersons(list);
		// 排序
		sortMobileElement(rtnMapList);
		return rtnMapList;
	}

	/**
	 * 将组织架构展开成人
	 */
	private List expandToPersons(List<HrOrganizationElement> list)
			throws Exception {
		return expandToPersons(list, null);
	}

	private List expandToPersons(List<HrOrganizationElement> list,
			String keyword)
			throws Exception {
		List members = new ArrayList();
		// 记录人员ID，防止与岗位成员重复
		List<String> personIds = new ArrayList<String>();
		// 如果有岗位，需要解析岗位中的人员
		for (HrOrganizationElement elem : list) {
			if (elem.getFdOrgType()
					.intValue() == HR_TYPE_POST) {
				List<HrOrganizationElement> persons = elem.getFdPersons();
				if (persons != null && !persons.isEmpty()) {
					for (HrOrganizationElement person : persons) {
						if (personIds.contains(person.getFdId())) {
							continue;
						}
						HrStaffPersonInfo p = (HrStaffPersonInfo) hrStaffPersonInfoService
								.findByPrimaryKey(person.getFdId());
						if (StringUtil.isNull(keyword)
								|| searchPerson(p, keyword)) {
							personIds.add(person.getFdId());
							Map<String, Object> obj = formatElement(
									person);
							// 岗位成员的排序使用岗位的排序号
							obj.put("order", elem.getFdOrder());
							members.add(obj);
						}
					}
				}
			} else {
				if (personIds.contains(elem.getFdId())) {
					continue;
				}
				int orgType = elem.getFdOrgType();
				if (orgType == 8 && StringUtil.isNotNull(keyword)) {
					HrStaffPersonInfo p = (HrStaffPersonInfo) hrOrganizationElementService
							.findByPrimaryKey(elem.getFdId());
					if (searchPerson(p, keyword)) {
						personIds.add(elem.getFdId());
						members.add(formatElement(elem));
					}
				} else {

					personIds.add(elem.getFdId());
					members.add(formatElement(elem));
				}
			}
		}
		return members;
	}

	/** 指定人员是否匹配关键字 */
	private boolean searchPerson(HrStaffPersonInfo p, String keyword) {
		String name = p.getFdName();
		String loginName = p.getFdLoginName();
		String nameSimplePinyin = p.getFdNameSimplePinyin();
		String namePinyin = p.getFdNamePinYin();
		return name.indexOf(keyword) > -1 || loginName.indexOf(keyword) > -1
				|| StringUtil.isNotNull(nameSimplePinyin)
						&& nameSimplePinyin.indexOf(keyword) > -1
				|| StringUtil.isNotNull(namePinyin)
						&& namePinyin.indexOf(keyword) > -1;
	}

	/**
	 * 获取当前用户管辖的部门（机构）层级ID集合
	 */
	private List<String> getOrgHierarchyIds() throws Exception {
		List<String> list = getIds("fdHierarchyId");
		return list == null ? null : formatHierarchyIdList(list);
	}

	private List<String> getIds(String propId) throws Exception {
		// 领导指行政组织架构中的机构（部门）领导、上级领导，含人或岗位
		// 1. 查找当前用户ID 及 当前用户所有岗位的ID
		List<String> ids = new ArrayList<String>();
		SysOrgPerson person = (SysOrgPerson) hrStaffPersonInfoService
				.findByPrimaryKey(UserUtil.getUser().getFdId());
		ids.add(person.getFdId());
		List<HrOrganizationElement> posts = person.getFdPosts();
		if (posts != null && !posts.isEmpty()) {
			for (HrOrganizationElement post : posts) {
				ids.add(post.getFdId());
			}
		}
		// 2. 用上面的ID查找组织架构中的机构（部门）领导、上级领导
		HQLInfo info = new HQLInfo();
		info.setSelectBlock(propId);
		info.setWhereBlock(
				"fdIsBusiness is true and fdIsAvailable is true and fdOrgType in (1, 2) and (hbmThisLeader.fdId in (:ids) or hbmSuperLeader.fdId in (:ids))");
		info.setParameter("ids", ids);
		return hrOrganizationElementService.findList(info);
	}

	/**
	 * 层级ID拆分成ID
	 */
	private Set<String> splitHierarchyId(List<String> hierarchyIds) {
		Set<String> ids = new HashSet<String>();
		for (String hierarchyId : hierarchyIds) {
			for (String id : hierarchyId
					.split(BaseTreeConstant.HIERARCHY_ID_SPLIT)) {
				if (StringUtil.isNotNull(id)) {
					ids.add(id);
				}
			}
		}
		return ids;
	}

	/**
	 * 格式化层级ID
	 */
	private List<String> formatHierarchyIdList(List<String> srcList) {
		List<String> rtnList = new ArrayList();
		outloop: for (String insertHid : srcList) {
			for (int i = 0; i < rtnList.size(); i++) {
				String hid = rtnList.get(i);
				if (hid.startsWith(insertHid)) {
					// 替换为上层部门
					rtnList.set(i, insertHid);
					continue outloop;
				} else if (insertHid.startsWith(hid)) {
					continue outloop;
				} else if (insertHid.compareTo(hid) > 0) {
					break;
				}
			}
			rtnList.add(insertHid);
		}
		return rtnList;
	}

	private boolean isMatch(List<String> hierarchyIds, String hierarchyId) {
		for (String temp : hierarchyIds) {
			if (hierarchyId.contains(temp)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 格式化人员数据
	 * 
	 */
	private Map<String, Object> formatMobileElement(SysOrgElement elem)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("fdId", elem.getFdId());
		map.put("type", elem.getFdOrgType());
		map.put("label", elem.getFdName());
		map.put("labelLevel", elem.getFdName());
		map.put("order", elem.getFdOrder());
		map.put("parentNames", elem.getFdParentsName());
		map.put("pinyin", elem.getFdNamePinYin());
		map.put("hierarchyId", elem.getFdHierarchyId());
		if (elem.getFdOrgType() == HR_TYPE_PERSON) {
			map.put("icon", PersonInfoServiceGetter
					.getPersonHeadimageUrl(elem.getFdId()));
			SysOrgPerson p = (SysOrgPerson) hrStaffPersonInfoService
					.findByPrimaryKey(elem.getFdId());
			// 职级
			String showStaffingLevel = new SysOrganizationConfig()
					.getShowStaffingLevel();
			if (p.getFdStaffingLevel() != null
					&& "true".equals(showStaffingLevel)) {
				map.put("staffingLevel",
						p.getFdStaffingLevel().getFdName());
			}
			// 领导标示
			if (p.getFdParent() != null
					&& p.getFdParent().getHbmThisLeader() != null
					&& p.getFdId().equals(
							p.getFdParent().getHbmThisLeader().getFdId())) {
				map.put("leaderType", p.getFdParent().getFdOrgType());
			}
		}
		return map;
	}

	/**
	 * 排序
	 */
	private void sortMobileElement(List rtnMapList) {
		Collections.sort(rtnMapList, new Comparator<Map<String, Object>>() {
			@Override
			public int compare(Map<String, Object> m1, Map<String, Object> m2) {
				Integer i1 = m1.get("order") == null ? Integer.MAX_VALUE
						: (Integer) m1.get("order");
				Integer i2 = m2.get("order") == null ? Integer.MAX_VALUE
						: (Integer) m2.get("order");
				 if (((Integer) m1.get("type")) > ((Integer) m2
						.get("type"))) {
					return 1;
				} else if (((Integer) m1.get("type")) < ((Integer) m2
						.get("type"))) {
					return -1;
				} else {
					if (i1.equals(i2)) {
						return 0;
					} else if (i1 > i2) {
						return 1;
					} else {
						return -1;
					}
				} 
			}
		});
	}

	@Override
	public List commonGroupPath(RequestContext request) throws Exception {
		List resultList = new ArrayList();
		String fdId = request.getParameter("fdId");
		String nodeType = request.getParameter("nodeType");
		if (StringUtil.isNotNull(fdId)) {
			if ("group".equals(nodeType)) {
				SysOrgGroup group = (SysOrgGroup) sysOrgGroupService
						.findByPrimaryKey(fdId);
				Map<String, Object> obj = new HashMap<String, Object>();
				obj.put("fdId", group.getFdId());
				obj.put("label", group.getFdName());
				resultList.add(0, obj);
				if (group.getFdGroupCate() != null) {
					addGroupCate(group.getFdGroupCate(), resultList);
				}
			} else {
				SysOrgGroupCate cate = (SysOrgGroupCate) sysOrgGroupCateService
						.findByPrimaryKey(fdId);
				addGroupCate(cate, resultList);
			}
		}
		return resultList;
	}

	private void addGroupCate(SysOrgGroupCate cate, List resultList)
			throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("fdId", cate.getFdId());
		obj.put("label", cate.getFdName());
		resultList.add(0, obj);
		if (cate.getFdParent() != null) {
			addGroupCate(cate.getFdParent(), resultList);
		}
	}


	@Override
	public List commonGroupCate(RequestContext request) throws Exception {
		return commonGroupCateBean.getDataList(request);
	}

	@Override
	public List commonGroupList(RequestContext request) throws Exception {
		List commonGroup = commonGroupBean.getDataList(request);
		return commonGroup;
	}

	@Override
	public List myGroupList(RequestContext request) throws Exception {
		return myGroupBean.getDataList(request);
	}


}
