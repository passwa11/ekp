package com.landray.kmss.sys.organization.mobile;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.model.IBaseCreateInfoModel;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.constant.BaseTreeConstant;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.framework.plugin.core.config.IExtension;
import com.landray.kmss.framework.service.plugin.Plugin;
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
import com.landray.kmss.sys.organization.eco.AuthOrgRange;
import com.landray.kmss.sys.organization.eco.IOrgRangeService;
import com.landray.kmss.sys.organization.eco.SysOrgMyDeptRange;
import com.landray.kmss.sys.organization.eco.SysOrgShowRange;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.interfaces.SysOrgHQLUtil;
import com.landray.kmss.sys.organization.model.*;
import com.landray.kmss.sys.organization.service.*;
import com.landray.kmss.sys.organization.service.spring.OrgDialogUtil;
import com.landray.kmss.sys.organization.util.SysOrgEcoUtil;
import com.landray.kmss.sys.organization.util.SysOrgUtil;
import com.landray.kmss.sys.person.interfaces.PersonInfoServiceGetter;
import com.landray.kmss.sys.xform.service.DictLoadService;
import com.landray.kmss.sys.xform.util.SysFormDingUtil;
import com.landray.kmss.third.pda.util.PdaFlagUtil;
import com.landray.kmss.util.*;
import com.sunbor.web.tag.Page;
import edu.emory.mathcs.backport.java.util.Arrays;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.Map.Entry;

public class MobileAddressServiceImp
		implements IMobileAddressService, SysOrgConstant {
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

	private ISysOrgElementService sysOrgElementService;

	private ISysOrgDeptService sysOrgDeptService;

	private ISysOrganizationVisibleService sysOrganizationVisibleService;

	protected ISysOrganizationStaffingLevelService sysOrganizationStaffingLevelService;

	private ISysOrgGroupService sysOrgGroupService;

	private ISysOrgGroupCateService sysOrgGroupCateService;

	private IXMLDataBean commonGroupCateBean;

	private IXMLDataBean commonGroupBean;

	private IXMLDataBean myGroupBean;

	private ISysOrgCoreService sysOrgCoreService;

	public void setSysOrganizationStaffingLevelService(
			ISysOrganizationStaffingLevelService sysOrganizationStaffingLevelService) {
		this.sysOrganizationStaffingLevelService = sysOrganizationStaffingLevelService;
	}

	public void setSysOrgDeptService(
			ISysOrgDeptService sysOrgDeptService) {
		this.sysOrgDeptService = sysOrgDeptService;
	}

	public void setSysOrgElementService(
			ISysOrgElementService sysOrgElementService) {
		this.sysOrgElementService = sysOrgElementService;
	}

	public ISysOrgElementService getSysOrgElementService() {
		return sysOrgElementService;
	}

	public void setSysOrganizationVisibleService(
			ISysOrganizationVisibleService sysOrganizationVisibleService) {
		this.sysOrganizationVisibleService = sysOrganizationVisibleService;
	}

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

	protected IOrgRangeService orgRangeService;

	public void setOrgRangeService(IOrgRangeService orgRangeService) {
		this.orgRangeService = orgRangeService;
	}

	public ISysOrgCoreService getSysOrgCoreService() {
		if (sysOrgCoreService == null) {
			sysOrgCoreService = (ISysOrgCoreService) SpringBeanUtil.getBean("sysOrgCoreService");
		}
		return sysOrgCoreService;
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
		int orgType = ORG_TYPE_PERSON;
		String orgTypeParam = xmlContext.getParameter("orgType");
		if (StringUtil.isNotNull(orgTypeParam)) {
			orgType = Integer.parseInt(orgTypeParam);
		}
		if (orgType == ORG_TYPE_ORG) {
			orgType = orgType & (ORG_TYPE_ALLORG | ORG_FLAG_AVAILABLEALL
					| ORG_FLAG_BUSINESSALL);
		} else {
			orgType = (orgType | ORG_TYPE_ORGORDEPT) & (ORG_TYPE_ALLORG
					| ORG_FLAG_AVAILABLEALL | ORG_FLAG_BUSINESSALL);
		}

		String deptLimit = xmlContext.getParameter("deptLimit");
		// 获取前端传递的父组织ID
		String parentId = xmlContext.getParameter("parentId");

		// 获取需要排除过滤掉的值（以逗号分隔的fdId集合）
		String exceptValue = xmlContext.getParameter("exceptValue");
		Set<String> exceptValues = null;
		if (StringUtil.isNotNull(exceptValue)) {
			String[] tempExceptValues = exceptValue.split("[;；,，]");
			exceptValues = new HashSet<String>();
			List<SysOrgElement> eles = getSysOrgElementService().findByPrimaryKeys(tempExceptValues); //防止sql注入，先查询是否存在。
			exceptValues = new HashSet<String>();
			for(SysOrgElement ele : eles){
				exceptValues.add(ele.getFdId());
			}
		}

		Boolean isExternal = null;
		boolean isAdminPage = false;
		String fdIsExternal = xmlContext.getParameter("isExternal");
		if (StringUtil.isNotNull(fdIsExternal)) {
			isExternal = Boolean.valueOf(fdIsExternal);

			// 处理生态组织管理端
			String sys_page = xmlContext.getParameter("sys_page");
			if ("true".equals(fdIsExternal) && "true".equals(sys_page)) {
				// 后台查询生态组织，如果不是管理员，那么需要校验是否组织管理员
				AuthOrgRange range = UserUtil.getKMSSUser().getUserAuthInfo().getAuthOrgRange();
				if (range != null && CollectionUtils.isNotEmpty(range.getAdminRanges())) {
					isAdminPage = true;
				}
			}
		}

		if (SysOrgEcoUtil.IS_ENABLED_ECO) {
			String cateId = xmlContext.getParameter("cateId");
			// 生态组织组织选择上级组织或者人员选择所在组织特殊处理
			if (StringUtil.isNull(parentId) && StringUtil.isNotNull(cateId)) {
				SysOrgElement orgElem = (SysOrgElement) sysOrgElementService.findByPrimaryKey(cateId);
				List temp = new ArrayList<>();
				resultList = new ArrayList();
				temp.add(orgElem);
				resultList.addAll(getFormatElementList(temp));
				return resultList;
			}
		}

		Set<String> elementIds = null;
		if (StringUtil.isNotNull(deptLimit) && !"undefined".equals(deptLimit)) {
			elementIds = OrgDialogUtil.getRootOrgIds(deptLimit);
			if (CollectionUtils.isEmpty(elementIds)) {
				// 有限制，但是没有解析对应的数据
				elementIds = new HashSet<>();
				elementIds.add(UserUtil.getKMSSUser().getUserId());
			}
		}
//		if (StringUtil.isNotNull(parentId)) { // *****如果前端有传递父组织ID，则根据父组织ID来查询下级机构、部门、岗位、人员 *****
//			// 判断是否有该部门的使用权限
//			SysOrgElement parnet = (SysOrgElement) sysOrgElementService.findByPrimaryKey(parentId);
//			if (orgRangeService.check(parnet)) {
//				// 有使用权限，不进行过滤
//				resultList = this.getAddressResultList(parentId, null, orgType, isExternal, exceptValues, maxPageSize, true);
//			} else {
//				// 没有使用权限，需要过滤可使用的组织
//				Set<String> ids = new HashSet<String>();
//				ids.addAll(orgRangeService.getDeptIds());
//				ids.addAll(orgRangeService.getPersonIds());
//				resultList = this.getAddressResultList(parentId, ids, orgType, isExternal, exceptValues, maxPageSize, false);
//			}
//		} else { // ***** 如果前端没有有传递父组织ID，则再查询用户是否有设置可视组织，如果有可视组织，通过可视组织查询 *****
//			if (CollectionUtils.isNotEmpty(elementIds)) {
//				// 根据可视组织进行查询
//				resultList = this.getAddressResultList(null, elementIds, orgType, isExternal, exceptValues, maxPageSize, false);
//			} else {
//				// 首次进入地址本，还未点击子级部门或子级机构时，默认查询组织最顶层数据
//				resultList = this.getAddressResultList(null, null, orgType, isExternal, exceptValues, maxPageSize, false);
//			}
//		}

		// 员工黄页不显示生态组织
		if (this.getClass().getName().contains("MobileSysZoneAddressServiceImp")) {
			isExternal = false;
		}
		return this.getAddressResultList(parentId, elementIds, orgType, isExternal, exceptValues, maxPageSize, isAdminPage);
	}

	@Override
	public List mobileAddressList(RequestContext xmlContext) throws Exception {
		// 定义返回结果
		List resultList = null;

		// 定义最大返回的数据记录条数
		int maxPageSize = 500;
		String maxPageSizeParam = xmlContext.getParameter("maxPageSize");
		if (StringUtil.isNotNull(maxPageSizeParam)) {
			maxPageSize = Integer.parseInt(maxPageSizeParam);
		}

		// 组织类型
		int orgType = ORG_TYPE_PERSON;
		String orgTypeParam = xmlContext.getParameter("orgType");
		if (StringUtil.isNotNull(orgTypeParam)) {
			orgType = Integer.parseInt(orgTypeParam);
		}
		if (orgType == ORG_TYPE_ORG) {
			orgType = orgType & (ORG_TYPE_ALLORG | ORG_FLAG_AVAILABLEALL
					| ORG_FLAG_BUSINESSALL);
		} else {
			orgType = (orgType | ORG_TYPE_ORGORDEPT) & (ORG_TYPE_ALLORG
					| ORG_FLAG_AVAILABLEALL | ORG_FLAG_BUSINESSALL);
		}

		// 获取前端传递的父组织ID
		String parentId = xmlContext.getParameter("parentId");
		// 获取外部组织类型
		String cateId = xmlContext.getParameter("cateId");
		if (StringUtil.isNotNull(cateId)) {
			parentId = cateId;
		}

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

		Boolean isExternal = null;
		boolean isAdminPage = false;
		String fdIsExternal = xmlContext.getParameter("fdIsExternal");
		if (StringUtil.isNotNull(fdIsExternal)) {
			isExternal = Boolean.valueOf(fdIsExternal);

			// 处理生态组织管理端
			String sys_page = xmlContext.getParameter("sys_page");
			if ("true".equals(fdIsExternal) && "true".equals(sys_page)) {
				// 后台查询生态组织，如果不是管理员，那么需要校验是否组织管理员
				AuthOrgRange range = UserUtil.getKMSSUser().getUserAuthInfo().getAuthOrgRange();
				if (range != null && CollectionUtils.isNotEmpty(range.getAdminRanges())) {
					isAdminPage = true;
				}
			}
		}

		if (StringUtil.isNotNull(parentId)) {
			// 如果前端有传递父组织ID，则根据父组织ID来查询下级机构、部门、岗位、人员
			resultList = this.getAddressResultListForEco(parentId, null, orgType, isExternal,
					exceptValues, maxPageSize, isAdminPage);
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

		orgFlag = rtnType & ORG_FLAG_AVAILABLEALL;
		if (orgFlag == 0) {
			orgFlag = ORG_FLAG_AVAILABLEDEFAULT;
		}
		if (orgFlag != ORG_FLAG_AVAILABLEALL) {
			if (orgFlag == ORG_FLAG_AVAILABLENO) {
				filter = orgProperty + ".fdIsAvailable=" + SysOrgHQLUtil.toBooleanValueString(false);
			} else {
				filter = orgProperty + ".fdIsAvailable=" + SysOrgHQLUtil.toBooleanValueString(true);
			}
			m_where = StringUtil.linkString(m_where, " and ", filter);
		}

		orgFlag = rtnType & ORG_FLAG_BUSINESSALL;
		if (orgFlag == 0) {
            orgFlag = ORG_FLAG_BUSINESSDEFAULT;
        }
		if (orgFlag != ORG_FLAG_BUSINESSALL) {
			if (orgFlag == ORG_FLAG_BUSINESSNO) {
				filter = orgProperty + ".fdIsBusiness=" + SysOrgHQLUtil.toBooleanValueString(false);
			} else {
				filter = orgProperty + ".fdIsBusiness=" + SysOrgHQLUtil.toBooleanValueString(true);
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
	 * @param isExternal
	 *            是否是外部组织
	 * @param exceptValues
	 *            需要排除过滤掉的fdId集合
	 * @param maxPageSize
	 *            最大返回的数据记录条数
	 * @param isAdminPage
	 *
	 * @return 返回地址本机构记录条数
	 */
	private List getAddressResultList(String parentId, Set<String> elementIds,
									  int orgType, Boolean isExternal, Set<String> exceptValues, int maxPageSize, boolean isAdminPage)
			throws Exception {
		List resultList = new ArrayList();
		int orgFlag = orgType;
		if (orgFlag != ORG_TYPE_ALL
				&& orgFlag != (ORG_TYPE_ALL | ORG_TYPE_ROLE)) {
			// 剩余可查询记录条数
			int surplusQty = maxPageSize;

			// 查询《 机构 》
			if ((orgFlag & ORG_TYPE_ORG) == ORG_TYPE_ORG && surplusQty > 0) {
				List<SysOrgElement> orgElementList = this.getAddressOrgList(
						parentId, elementIds, surplusQty, exceptValues,
						orgType, isExternal, isAdminPage);
				resultList.addAll(this.getFormatElementList(orgElementList));
				surplusQty = maxPageSize - resultList.size();
			}

			// 查询《 部门 》
			if ((orgFlag & ORG_TYPE_DEPT) == ORG_TYPE_DEPT && surplusQty > 0) {
				List<SysOrgElement> deptElementList = this.getAddressDeptList(
						parentId, elementIds, surplusQty, exceptValues,
						orgType, isExternal, isAdminPage);
				resultList.addAll(this.getFormatElementList(deptElementList));
				surplusQty = maxPageSize - resultList.size();
			}

			// 查询《 人员 》
			if (((orgFlag & ORG_TYPE_PERSON) == ORG_TYPE_PERSON
					|| orgFlag == 515) && surplusQty > 0) {
				List<SysOrgElement> personElementList = this
						.getAddressPersonList(parentId, elementIds, surplusQty,
								exceptValues, orgType, isExternal, isAdminPage);
				List personList = this.getFormatElementList(personElementList);
				Object p = getParentName(personList);
				Collections.sort(personList, new OrderComparator(p)); // 重新按照排序号排序
				resultList.addAll(personList);
				surplusQty = maxPageSize - resultList.size();
			}

			// 查询《 岗位 》
			if ((orgFlag & ORG_TYPE_POST) == ORG_TYPE_POST && surplusQty > 0) {
				List<SysOrgElement> postElementList = this.getAddressPostList(parentId, elementIds, surplusQty,
						exceptValues, orgType, isExternal, isAdminPage);
				resultList.addAll(this.getFormatElementList(postElementList));
				surplusQty = maxPageSize - resultList.size();
			}

		}
		return resultList;
	}

	private List getAddressResultListForEco(String parentId, Set<String> elementIds,
											int orgType, Boolean isExternal, Set<String> exceptValues, int maxPageSize, boolean isAdminPage)
			throws Exception {
		List resultList = new ArrayList();
		int orgFlag = orgType;
		if (orgFlag != ORG_TYPE_ALL
				&& orgFlag != (ORG_TYPE_ALL | ORG_TYPE_ROLE)) {
			// 剩余可查询记录条数
			int surplusQty = maxPageSize;

			// 查询《 机构 》
			if ((orgFlag & ORG_TYPE_ORG) == ORG_TYPE_ORG && surplusQty > 0) {
				List<SysOrgElement> orgElementList = this.getAddressOrgList(
						parentId, elementIds, surplusQty, exceptValues,
						orgType, isExternal, isAdminPage);
				resultList.addAll(this.getFormatElementList(orgElementList));
				surplusQty = maxPageSize - resultList.size();
			}

			// 查询《 部门 》
			if ((orgFlag & ORG_TYPE_DEPT) == ORG_TYPE_DEPT && surplusQty > 0) {
				List<SysOrgElement> deptElementList = this.getAddressDeptListForEco(
						parentId, elementIds, surplusQty, exceptValues,
						orgType, isExternal, isAdminPage);
				resultList.addAll(this.getFormatElementList(deptElementList));
				surplusQty = maxPageSize - resultList.size();
			}

			// 查询《 人员 》
			if (((orgFlag & ORG_TYPE_PERSON) == ORG_TYPE_PERSON
					|| orgFlag == 515) && surplusQty > 0) {
				List<SysOrgElement> personElementList = this
						.getAddressPersonListForEco(parentId, elementIds, surplusQty,
								exceptValues, orgType, isExternal, isAdminPage);
				List personList = this.getFormatElementList(personElementList);
				Object p = getParentName(personList);
				//Collections.sort(personList, new PinyinComparator()); // 重新按照拼音排序
				Collections.sort(personList, new OrderComparator(p)); // 重新按照排序号排序
				resultList.addAll(personList);
				surplusQty = maxPageSize - resultList.size();
			}

			// 查询《 岗位 》
			if ((orgFlag & ORG_TYPE_POST) == ORG_TYPE_POST && surplusQty > 0) {
				List<SysOrgElement> postElementList = this.getAddressPostList(parentId, elementIds, surplusQty,
						exceptValues, orgType, isExternal, isAdminPage);
				resultList.addAll(this.getFormatElementList(postElementList));
				surplusQty = maxPageSize - resultList.size();
			}
		}
		return resultList;
	}

	public Object getParentName(List<Map> array) {
		if (CollectionUtils.isEmpty(array)) {
			return null;
		}
		Object p = null;
		Map<Object, Integer> map = new HashMap<>();
		for (Map m : array) {
			Integer v = map.get(m.get("parentNames"));
			if (v == null) {
				map.put(m.get("parentNames"),1);
			}else{
				map.put(m.get("parentNames"), v + 1);
			}
		};
		Collection<Integer> values = map.values();
		Integer max = Collections.max(values);
		for (Entry<Object,Integer> entry : map.entrySet()) {
			if (entry.getValue().equals(max)) {
				p = entry.getKey();
				break;
			}
		}
		return p;
	}

	private List getFormatElementList(List<SysOrgElement> list)
			throws Exception {
		List resultList = new ArrayList();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			SysOrgElement element = (SysOrgElement) iterator.next();
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
	private List<SysOrgElement> getAddressOrgList(String parentId,
												  Set<String> elementIds, int maxPageSize, Set<String> exceptValues,
												  int orgType, Boolean isExternal, boolean isAdminPage) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();

		StringBuffer whereBlockSb = new StringBuffer();
		whereBlockSb.append("sysOrgElement.fdOrgType=:orgType ");
		hqlInfo.setParameter("orgType", ORG_TYPE_ORG);
		if (SysOrgEcoUtil.IS_ENABLED_ECO) {
			if (BooleanUtils.isTrue(isExternal)) {
				whereBlockSb.append(" and sysOrgElement.fdIsExternal=true ");
			} else if (BooleanUtils.isFalse(isExternal)) {
				whereBlockSb.append(" and sysOrgElement.fdIsExternal=false ");
			}
		} else {
			// 未开启生态，使用默认值
			whereBlockSb.append(" and (sysOrgElement.fdIsExternal is null or sysOrgElement.fdIsExternal=false) ");
		}
		if (StringUtil.isNotNull(parentId)) {
			// 如果点击的组织不在指定范围内，强制只查询范围内的数据
			if (CollectionUtils.isNotEmpty(elementIds) && !checkVisible(parentId, elementIds)) {
				whereBlockSb.append("and sysOrgElement.fdId in (").append(SysOrgUtil.buildInBlock(elementIds)).append(") ");
			} else {
				whereBlockSb.append("and sysOrgElement.hbmParent.fdId = :parentId ");
				hqlInfo.setParameter("parentId", parentId);
			}
		} else if (CollectionUtils.isNotEmpty(elementIds)) {
			if(!UserUtil.getKMSSUser().isAdmin()) {
				whereBlockSb.append("and sysOrgElement.fdId in (").append(SysOrgUtil.buildInBlock(elementIds)).append(") ");
			}
		} else {
			AuthOrgRange orgRange = UserUtil.getKMSSUser().getUserAuthInfo().getAuthOrgRange();
			StringBuffer whereBlock = new StringBuffer();
			// 内部组织，如果有管理员角色，可以查看所有，否则按需查看
			if (UserUtil.checkRole("ROLE_SYSORG_ORG_ADMIN") || UserUtil.checkRole("ROLE_SYSORG_DIALOG_USER")) {
				whereBlock.append("sysOrgElement.hbmParent = null");
				if (UserUtil.checkRole("ROLE_SYSORG_ORG_ADMIN") && orgRange != null && CollectionUtils.isNotEmpty(orgRange.getAuthOtherRootIds())) {
					whereBlock.append(" or sysOrgElement.fdId in (").append(SysOrgUtil.buildInBlock(orgRange.getAuthOtherRootIds())).append(")");
				}
			} else {
				if (orgRange != null && (orgRange.isShowMyDept() || CollectionUtils.isNotEmpty(orgRange.getAuthOtherRootIds()))) {
					whereBlock.append("(");
				}
				// 如果有查看范围限制，就取查看范围的根组织
				if (orgRange != null && CollectionUtils.isNotEmpty(orgRange.getRootDeptIds())) {
					whereBlock.append("sysOrgElement.fdId in (").append(SysOrgUtil.buildInBlock(orgRange.getRootDeptIds())).append(") ");
				} else {
					whereBlock.append("sysOrgElement.hbmParent = null ");
				}
				if (orgRange != null) {
					Set<String> rootIds = new HashSet<>();
					if (orgRange.isShowMyDept()) {
						rootIds.addAll(orgRange.getMyDeptIds());
					}
					if (CollectionUtils.isNotEmpty(orgRange.getAuthOtherRootIds())) {
						rootIds.addAll(orgRange.getAuthOtherRootIds());
					}
					if (CollectionUtils.isNotEmpty(rootIds)) {
						whereBlock.append(" or sysOrgElement.fdId in (").append(SysOrgUtil.buildInBlock(rootIds)).append("))");
					}
				}
			}
			// 查看所有生态组织时，特殊处理
			if (BooleanUtils.isTrue(isExternal) && UserUtil.checkRole("ROLE_SYSORG_ECO_DEPT_READER")) {
				whereBlock.insert(0, "(").append(") or (sysOrgElement.hbmParent = null and sysOrgElement.fdIsExternal = true)");
			}
			whereBlockSb.append(" and ").append(whereBlock);
		}

		if (exceptValues != null) {
			whereBlockSb.append(" and sysOrgElement.fdId not in (").append(SysOrgUtil.buildInBlock(exceptValues)).append(") ");
		}

		String whereBlock = this.buildWhereBlock(orgType, whereBlockSb.toString(), "sysOrgElement");
		hqlInfo.setWhereBlock(whereBlock);
		hqlInfo.setOrderBy("sysOrgElement.fdOrder, sysOrgElement." + SysLangUtil.getLangFieldName("fdName")); // 按排序号升序进行排序

		hqlInfo.setPageNo(1);
		hqlInfo.setRowSize(maxPageSize);
		if (BooleanUtils.isTrue(isExternal) && isAdminPage) {
			hqlInfo.setAuthCheckType("EXTERNAL_READER");
		} else {
			hqlInfo.setAuthCheckType("DIALOG_READER");
		}
		List<SysOrgElement> orgElementList = sysOrgElementService.findPage(hqlInfo).getList();
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
	private List<SysOrgElement> getAddressDeptList(String parentId,
												   Set<String> elementIds, int maxPageSize, Set<String> exceptValues,
												   int orgType, Boolean isExternal, boolean isAdminPage) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();

		StringBuffer whereBlockSb = new StringBuffer();
		whereBlockSb.append("sysOrgElement.fdOrgType=:orgType ");
		hqlInfo.setParameter("orgType", ORG_TYPE_DEPT);
		if (SysOrgEcoUtil.IS_ENABLED_ECO) {
			if (BooleanUtils.isTrue(isExternal)) {
				whereBlockSb.append(" and sysOrgElement.fdIsExternal=true ");
			} else if (BooleanUtils.isFalse(isExternal)) {
				whereBlockSb.append(" and sysOrgElement.fdIsExternal=false ");
			}
		} else {
			// 未开启生态，使用默认值
			whereBlockSb.append(" and (sysOrgElement.fdIsExternal is null or sysOrgElement.fdIsExternal=false) ");
		}
		if (StringUtil.isNotNull(parentId)) {
			// 如果点击的组织不在指定范围内，强制只查询范围内的数据
			if (CollectionUtils.isNotEmpty(elementIds) && !checkVisible(parentId, elementIds)) {
				whereBlockSb.append("and sysOrgElement.fdId in (").append(SysOrgUtil.buildInBlock(elementIds)).append(") ");
			} else {
				// 如果该父无权查看，判断所在部门是否在该父组织下
				AuthOrgRange orgRange = UserUtil.getKMSSUser().getUserAuthInfo().getAuthOrgRange();
				String myDeptId = null;
				if (orgRange != null) {
					Set<SysOrgMyDeptRange> myDepts = orgRange.getMyDepts();
					for (SysOrgMyDeptRange deptRange : myDepts) {
						if (deptRange.getFdHierarchyId().contains(parentId)) {
							myDeptId = deptRange.getFdId();
							break;
						}
					}
				}
				if (StringUtil.isNotNull(myDeptId) && !parentId.equals(myDeptId)) {
					whereBlockSb.append("and (sysOrgElement.hbmParent.fdId = :parentId or sysOrgElement.fdId = :fdId)");
					hqlInfo.setParameter("fdId", myDeptId);
				} else {
					whereBlockSb.append("and sysOrgElement.hbmParent.fdId = :parentId ");
				}
				hqlInfo.setParameter("parentId", parentId);
			}
		} else if (CollectionUtils.isNotEmpty(elementIds)) {
			if(!UserUtil.getKMSSUser().isAdmin()) {
				whereBlockSb.append("and sysOrgElement.fdId in (").append(SysOrgUtil.buildInBlock(elementIds)).append(") ");
			}
		} else {
			AuthOrgRange orgRange = UserUtil.getKMSSUser().getUserAuthInfo().getAuthOrgRange();
			if (orgRange != null && orgRange.isShowMyDept()) {
				whereBlockSb.append("and (");
			} else {
				whereBlockSb.append("and ");
			}
			// 如果有查看范围限制，就取查看范围的根组织
			if (orgRange != null && CollectionUtils.isNotEmpty(orgRange.getRootDeptIds())) {
				whereBlockSb.append("sysOrgElement.fdId in (").append(SysOrgUtil.buildInBlock(orgRange.getRootDeptIds())).append(") ");
			} else {
				whereBlockSb.append("sysOrgElement.hbmParent = null ");
			}
			if (orgRange != null && orgRange.isShowMyDept()) {
				whereBlockSb.append(" or sysOrgElement.fdId in (").append(SysOrgUtil.buildInBlock(orgRange.getMyDeptIds())).append(")) ");
			}
		}

		if (exceptValues != null) {
			whereBlockSb.append(" and sysOrgElement.fdId not in (").append(SysOrgUtil.buildInBlock(exceptValues)).append(") ");
		}
		String whereBlock = this.buildWhereBlock(orgType, whereBlockSb.toString(), "sysOrgElement");
		hqlInfo.setWhereBlock(whereBlock);

		if (SysOrgEcoUtil.IS_ENABLED_ECO) {
			hqlInfo.setOrderBy("sysOrgElement.fdIsExternal,sysOrgElement.fdOrder");
		} else {
			hqlInfo.setOrderBy("sysOrgElement.fdOrder"); // 按排序号升序进行排序
		}

		hqlInfo.setPageNo(1);
		hqlInfo.setRowSize(maxPageSize);
		hqlInfo.setAuthCheckType("DIALOG_READER");
		List<SysOrgElement> orgElementList = sysOrgElementService.findPage(hqlInfo).getList();
		return orgElementList;
	}

	private List<SysOrgElement> getAddressDeptListForEco(String parentId,
														 Set<String> elementIds, int maxPageSize, Set<String> exceptValues,
														 int orgType, Boolean isExternal, boolean isAdminPage) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();

		StringBuffer whereBlockSb = new StringBuffer();
		whereBlockSb.append("sysOrgDept.fdOrgType=:orgType ");
		hqlInfo.setParameter("orgType", ORG_TYPE_DEPT);
		if (SysOrgEcoUtil.IS_ENABLED_ECO) {
			if (BooleanUtils.isTrue(isExternal)) {
				whereBlockSb.append(" and sysOrgDept.fdIsExternal=true ");
			} else if (BooleanUtils.isFalse(isExternal)) {
				whereBlockSb.append(" and sysOrgDept.fdIsExternal=false ");
			}
		} else {
			// 未开启生态，使用默认值
			whereBlockSb.append(" and (sysOrgDept.fdIsExternal is null or sysOrgDept.fdIsExternal=false) ");
		}
		if (StringUtil.isNotNull(parentId)) {
			// 如果是生态的移动管理端，需要对组织负责人作特殊的处理
			HttpServletRequest request = Plugin.currentRequest();
			Set<String> authIds = new HashSet<>();
			if (BooleanUtils.isTrue(isExternal) && isAdminPage && request != null && PdaFlagUtil.checkClientIsPda(request)) {
				AuthOrgRange orgRange = UserUtil.getKMSSUser().getUserAuthInfo().getAuthOrgRange();
				if (orgRange != null && CollectionUtils.isNotEmpty(orgRange.getAdminRanges())) {
					for (SysOrgShowRange range : orgRange.getAdminRanges()) {
						if (!range.getFdId().equals(parentId) && range.getFdHierarchyId().contains(parentId)) {
							authIds.add(range.getFdId());
						}
					}
				}
			}
			if (CollectionUtils.isNotEmpty(authIds)) {
				whereBlockSb.append("and (sysOrgDept.hbmParent.fdId=:parentId or sysOrgDept.fdId in (").append(SysOrgUtil.buildInBlock(authIds)).append(")) ");
				hqlInfo.setParameter("parentId", parentId);
			} else {
				whereBlockSb.append("and sysOrgDept.hbmParent.fdId=:parentId ");
				hqlInfo.setParameter("parentId", parentId);
			}
		} else if (CollectionUtils.isNotEmpty(elementIds)) {
			if (UserUtil.checkRole("ROLE_SYSORG_DIALOG_USER")) {
				whereBlockSb.append("and (sysOrgDept.hbmParent is null "
						+ "or sysOrgDept.fdId in (" + SysOrgUtil.buildInBlock(elementIds) + "))");
			} else {
				whereBlockSb.append("and sysOrgDept.fdId in (").append(SysOrgUtil.buildInBlock(elementIds)).append(") ");
			}
		} else {
			whereBlockSb.append("and sysOrgDept.hbmParent is null ");
		}

		if (exceptValues != null) {
			whereBlockSb.append(" and sysOrgDept.fdId not in (")
					.append(SysOrgUtil.buildInBlock(exceptValues)).append(") ");
		}

		String whereBlock = this.buildWhereBlock(orgType,
				whereBlockSb.toString(), "sysOrgDept");
		hqlInfo.setWhereBlock(whereBlock);
		hqlInfo.setOrderBy("sysOrgDept.fdOrder, sysOrgDept.fdNamePinYin"); // 按排序号升序进行排序
		hqlInfo.setPageNo(1);
		hqlInfo.setGetCount(false);
		hqlInfo.setRowSize(maxPageSize);
		hqlInfo.setExternal(true);
		hqlInfo.setAuthCheckType("EXTERNAL_READER");
		List<SysOrgElement> orgElementList = sysOrgDeptService.findPage(hqlInfo).getList();
		if (isAdminPage) {
			orgElementList = orgRangeService.authFilterAdmin(orgElementList);
		}
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
	private List<SysOrgElement> getAddressPostList(String parentId,
												   Set<String> elementIds, int maxPageSize, Set<String> exceptValues,
												   int orgType, Boolean isExternal, boolean isAdminPage) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();

		StringBuffer whereBlockSb = new StringBuffer();
		whereBlockSb.append("sysOrgElement.fdOrgType=:orgType ");
		hqlInfo.setParameter("orgType", ORG_TYPE_POST);
		if (SysOrgEcoUtil.IS_ENABLED_ECO) {
			if (BooleanUtils.isTrue(isExternal)) {
				whereBlockSb.append(" and sysOrgElement.fdIsExternal=true ");
			} else if (BooleanUtils.isFalse(isExternal)) {
				whereBlockSb.append(" and sysOrgElement.fdIsExternal=false ");
			}
		} else {
			// 未开启生态，使用默认值
			whereBlockSb.append(" and (sysOrgElement.fdIsExternal is null or sysOrgElement.fdIsExternal=false) ");
		}
		if (StringUtil.isNotNull(parentId)) {
			// 如果点击的组织不在指定范围内，强制只查询范围内的数据
			if (CollectionUtils.isNotEmpty(elementIds) && !checkVisible(parentId, elementIds)) {
				whereBlockSb.append("and sysOrgElement.fdId in (").append(SysOrgUtil.buildInBlock(elementIds)).append(") ");
			} else {
				whereBlockSb.append("and sysOrgElement.hbmParent.fdId = :parentId ");
				hqlInfo.setParameter("parentId", parentId);
			}
		} else {
			AuthOrgRange orgRange = UserUtil.getKMSSUser().getUserAuthInfo().getAuthOrgRange();
			if (orgRange != null && orgRange.isShowMyDept()) {
				whereBlockSb.append("and (");
			} else {
				whereBlockSb.append("and ");
			}
			if (orgRange != null && CollectionUtils.isNotEmpty(orgRange.getRootDeptIds())) {
				whereBlockSb.append("sysOrgElement.fdId in (").append(SysOrgUtil.buildInBlock(orgRange.getRootDeptIds())).append(")");
			} else {
				whereBlockSb.append("sysOrgElement.hbmParent = null");
			}
			if (orgRange != null && orgRange.isShowMyDept()) {
				whereBlockSb.append(" or sysOrgElement.fdId in (").append(SysOrgUtil.buildInBlock(orgRange.getMyDeptIds())).append("))");
			}
		}

		if (exceptValues != null) {
			whereBlockSb.append(" and sysOrgElement.fdId not in (")
					.append(SysOrgUtil.buildInBlock(exceptValues)).append(") ");
		}
		String whereBlock = this.buildWhereBlock(orgType, whereBlockSb.toString(), "sysOrgElement");
		hqlInfo.setWhereBlock(whereBlock);
		hqlInfo.setOrderBy("sysOrgElement.fdOrder, sysOrgElement.fdNamePinYin"); // 按排序号升序进行排序
		hqlInfo.setPageNo(1);
		hqlInfo.setGetCount(false);
		hqlInfo.setRowSize(maxPageSize);

		if (BooleanUtils.isTrue(isExternal) && isAdminPage) {
			hqlInfo.setAuthCheckType("EXTERNAL_READER");
		} else {
			hqlInfo.setAuthCheckType("DIALOG_READER");
		}
		List<SysOrgElement> orgElementList = sysOrgElementService.findPage(hqlInfo).getList();
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
	private List<SysOrgElement> getAddressPersonList(String parentId,
													 Set<String> elementIds, int maxPageSize, Set<String> exceptValues,
													 int orgType, Boolean isExternal, boolean isAdminPage) throws Exception {

		// 初始查询第一页
		int pageNo = 1;

		// 定义返回的结果集
		List<SysOrgElement> resultList = new ArrayList<SysOrgElement>();

		// 分页递归查询并将结果集添加至 resultList 中 （注意：返回的结果集有可能会超过限制的最大的记录条数maxPageSize）
		getPersonForResultList(parentId, elementIds, maxPageSize, pageNo,
				exceptValues, orgType, resultList, isExternal, isAdminPage);
		if (resultList.size() > maxPageSize) {
			resultList = resultList.subList(0, maxPageSize);
		}

		return resultList;
	}

	private List<SysOrgElement> getAddressPersonListForEco(String parentId,
														   Set<String> elementIds, int maxPageSize, Set<String> exceptValues,
														   int orgType, Boolean isExternal, boolean isAdminPage) throws Exception {

		// 初始查询第一页
		int pageNo = 1;

		// 定义返回的结果集
		List<SysOrgElement> resultList = new ArrayList<SysOrgElement>();

		// 分页递归查询并将结果集添加至 resultList 中 （注意：返回的结果集有可能会超过限制的最大的记录条数maxPageSize）
		getPersonForResultListForEco(parentId, elementIds, maxPageSize, pageNo,
				exceptValues, orgType, resultList, isExternal);
		if (resultList.size() > maxPageSize) {
			resultList = resultList.subList(0, maxPageSize);
		}

		return resultList;
	}

	private List<SysOrgElement> getPersonForResultList(String parentId,
													   Set<String> elementIds, int maxPageSize, int pageNo,
													   Set<String> exceptValues, int orgType,
													   List<SysOrgElement> resultList, Boolean isExternal, boolean isAdminPage) throws Exception {
		// 注：查询分为两段逻辑分开查询（ 1、查询直接挂在部门下的人 2、查询挂在指定部门的岗位下的人 ）
		List<SysOrgElement> personElementList = new ArrayList<SysOrgElement>();

		// 1、查询直接挂在部门下的人
		HQLInfo hqlInfo1 = new HQLInfo();
		StringBuffer whereBlockSb1 = new StringBuffer();
		whereBlockSb1.append("sysOrgPerson.fdOrgType=:orgType ");
		hqlInfo1.setParameter("orgType", ORG_TYPE_PERSON);
		if (SysOrgEcoUtil.IS_ENABLED_ECO) {
			if (BooleanUtils.isTrue(isExternal)) {
				whereBlockSb1.append(" and sysOrgPerson.fdIsExternal=true ");
			} else if (BooleanUtils.isFalse(isExternal)) {
				whereBlockSb1.append(" and sysOrgPerson.fdIsExternal=false ");
			}
		} else {
			// 未开启生态，使用默认值
			whereBlockSb1.append(" and (sysOrgPerson.fdIsExternal is null or sysOrgPerson.fdIsExternal=false) ");
		}
		if (StringUtil.isNotNull(parentId)) {
			// 如果点击的组织不在指定范围内，强制只查询范围内的数据
			if (CollectionUtils.isNotEmpty(elementIds) && !checkVisible(parentId, elementIds)) {
				whereBlockSb1.append(" and sysOrgPerson.fdId in (").append(SysOrgUtil.buildInBlock(elementIds)).append(") ");
			} else {
				whereBlockSb1.append(" and sysOrgPerson.hbmParent.fdId = :parentId ");
				hqlInfo1.setParameter("parentId", parentId);
			}
		} else {
			if (CollectionUtils.isNotEmpty(elementIds)) {
				// 开启组织可见性，parentId为空时不可能存在人员
				whereBlockSb1.append(" and 1=2 ");
			} else {
				// 如果有查看范围限制，获取根组织
				AuthOrgRange orgRange = UserUtil.getKMSSUser().getUserAuthInfo().getAuthOrgRange();
				if (orgRange != null && orgRange.isShowMyDept()) {
					whereBlockSb1.append(" and (");
				} else {
					whereBlockSb1.append(" and ");
				}
				if (orgRange != null && CollectionUtils.isNotEmpty(orgRange.getRootPersonIds())) {
					whereBlockSb1.append("sysOrgPerson.fdId in (").append(SysOrgUtil.buildInBlock(orgRange.getRootPersonIds())).append(")");
				} else {
					whereBlockSb1.append("sysOrgPerson.hbmParent = null");
				}
				if (orgRange != null && orgRange.isShowMyDept()) {
					whereBlockSb1.append(" or sysOrgPerson.fdId in (").append(SysOrgUtil.buildInBlock(orgRange.getMyDeptIds())).append("))");
				}
			}
		}

		if (exceptValues != null) {
			whereBlockSb1.append(" and sysOrgPerson.fdId not in (")
					.append(SysOrgUtil.buildInBlock(exceptValues)).append(") ");
		}
		String whereBlock1 = this.buildWhereBlock(orgType, whereBlockSb1.toString(), "sysOrgPerson");
		hqlInfo1.setWhereBlock(whereBlock1);

		hqlInfo1.setOrderBy("sysOrgPerson.fdNamePinYin"); // 按人员姓名拼音升序进行排序
		hqlInfo1.setGetCount(false);
		hqlInfo1.setPageNo(pageNo);
		hqlInfo1.setRowSize(maxPageSize);
		hqlInfo1.setAuthCheckType("DIALOG_READER"); // 加入组织可见性配置过滤 （组织权限中心》基础设置》地址本隔离》组织可见性配置）
		Page page1 = sysOrgPersonService.findPage(hqlInfo1);
		List<SysOrgElement> personElementList1 = page1.getPageno()==pageNo ? page1.getList() : new ArrayList<SysOrgElement>();
		personElementList.addAll(personElementList1);

		AuthOrgRange orgRange = UserUtil.getKMSSUser().getUserAuthInfo().getAuthOrgRange();
		// 2、查询挂在指定部门的岗位下的人
		if ((orgType & ORG_TYPE_PERSON) == ORG_TYPE_PERSON
				&& StringUtil.isNotNull(parentId) && (orgRange == null || !orgRange.isSelf())) {
			int maxPageSize2 = maxPageSize - personElementList1.size();
			HQLInfo hqlInfo2 = new HQLInfo();

			String model = "com.landray.kmss.sys.organization.model.SysOrgPerson";
			String hqlFrom = model
					+ " sysOrgPerson inner join sysOrgPerson.hbmPosts as sysHbmPosts ";
			hqlInfo2.setFromBlock(hqlFrom);

			StringBuffer whereBlockSb2 = new StringBuffer();
			whereBlockSb2.append("sysOrgPerson.fdOrgType=:orgType ");
			hqlInfo2.setParameter("orgType", ORG_TYPE_PERSON);
			whereBlockSb2.append("and sysHbmPosts.fdId in "); // 查询挂在指定部门的岗位下的人
			whereBlockSb2.append("(");
			whereBlockSb2
					.append("select postElement.fdId from com.landray.kmss.sys.organization.model.SysOrgElement postElement where postElement.hbmParent.fdId=:parentId and postElement.fdOrgType=")
					.append(ORG_TYPE_POST);
			whereBlockSb2.append(") ");
			hqlInfo2.setParameter("parentId", parentId);

			if (exceptValues != null) {
				whereBlockSb2.append(" and sysOrgPerson.fdId not in (")
						.append(SysOrgUtil.buildInBlock(exceptValues))
						.append(") ");
			}

			String whereBlock = this.buildWhereBlock(orgType,
					whereBlockSb2.toString(), "sysOrgPerson");
			hqlInfo2.setWhereBlock(whereBlock);

			hqlInfo2.setOrderBy("sysOrgPerson.fdNamePinYin"); // 按人员姓名拼音升序进行排序
			hqlInfo2.setGetCount(false);
			hqlInfo2.setPageNo(pageNo);
			hqlInfo2.setRowSize(maxPageSize2);
			hqlInfo2.setCheckParam(SysAuthConstant.CheckType.AllCheck, SysAuthConstant.AllCheck.NO); // 取消默认的权限过滤
			Page page2 = sysOrgPersonService.findPage(hqlInfo2);
			List<SysOrgElement> personElementList2 =  page2.getPageno()==pageNo ? page2.getList() : new ArrayList<SysOrgElement>();
			for (SysOrgElement element : personElementList2) {
				if (!personElementList.contains(element)) { // 剔除人员重复数据
					personElementList.add(element);
				}
			}

		}

		// 去除人员重复数据
		List<SysOrgElement> noRepeatPersonElementList = new ArrayList<SysOrgElement>();
		for (SysOrgElement element : personElementList) {
			if (!resultList.contains(element)) {
				noRepeatPersonElementList.add(element);
			}
		}

		// 人员需要进行职级过滤
		List<SysOrgElement> filterPersonList = sysOrganizationStaffingLevelService
				.getStaffingLevelFilterResult(noRepeatPersonElementList);
		resultList.addAll(filterPersonList);

		// 如果职级过滤掉了一部分人，查找下一批数据补齐人员满足最大返回数量
		if (resultList.size() < maxPageSize
				&& noRepeatPersonElementList.size() > 0
				&& filterPersonList.size() < noRepeatPersonElementList.size()) {
			pageNo = pageNo + 1;
			this.getPersonForResultList(parentId, elementIds, maxPageSize,
					pageNo, exceptValues, orgType, resultList, isExternal, isAdminPage);
		}

		return resultList;
	}

	private List<SysOrgElement> getPersonForResultListForEco(String parentId,
															 Set<String> elementIds, int maxPageSize, int pageNo,
															 Set<String> exceptValues, int orgType,
															 List<SysOrgElement> resultList, Boolean isExternal) throws Exception {
		// 注：查询分为两段逻辑分开查询
		List<SysOrgElement> personElementList = new ArrayList<SysOrgElement>();

		HQLInfo hqlInfo1 = new HQLInfo();
		StringBuffer whereBlockSb1 = new StringBuffer();
		whereBlockSb1.append("sysOrgPerson.fdOrgType=:orgType ");
		hqlInfo1.setParameter("orgType", ORG_TYPE_PERSON);
		if (SysOrgEcoUtil.IS_ENABLED_ECO) {
			if (BooleanUtils.isTrue(isExternal)) {
				whereBlockSb1.append(" and sysOrgPerson.fdIsExternal=true ");
			} else if (BooleanUtils.isFalse(isExternal)) {
				whereBlockSb1.append(" and sysOrgPerson.fdIsExternal=false ");
			}
		} else {
			// 未开启生态，使用默认值
			whereBlockSb1.append(" and (sysOrgPerson.fdIsExternal is null or sysOrgPerson.fdIsExternal=false) ");
		}
		if (StringUtil.isNotNull(parentId)) {
			whereBlockSb1.append(" and sysOrgPerson.hbmParent.fdId=:parentId ");
			hqlInfo1.setParameter("parentId", parentId);
		}  else {
			whereBlockSb1.append(" and sysOrgPerson.hbmParent=null ");
		}

		if (exceptValues != null) {
			whereBlockSb1.append(" and sysOrgPerson.fdId not in (")
					.append(SysOrgUtil.buildInBlock(exceptValues)).append(") ");
		}

		String whereBlock1 = this.buildWhereBlock(orgType, whereBlockSb1.toString(), "sysOrgPerson");
		hqlInfo1.setWhereBlock(whereBlock1);

		hqlInfo1.setOrderBy("sysOrgPerson.fdOrder, sysOrgPerson.fdNamePinYin"); // 按排序号升序进行排序
		hqlInfo1.setGetCount(false);
		hqlInfo1.setPageNo(pageNo);
		hqlInfo1.setRowSize(maxPageSize);
		hqlInfo1.setExternal(isExternal);
		hqlInfo1.setAuthCheckType("EXTERNAL_READER");
		Page page1 = sysOrgPersonService.findPage(hqlInfo1);
		List<SysOrgElement> personElementList1 = page1.getPageno()==pageNo ? page1.getList() : new ArrayList<SysOrgElement>();
		personElementList.addAll(personElementList1);

		// 去除人员重复数据
		List<SysOrgElement> noRepeatPersonElementList = new ArrayList<SysOrgElement>();
		for (SysOrgElement element : personElementList) {
			if (!resultList.contains(element)) {
				noRepeatPersonElementList.add(element);
			}
		}

		resultList.addAll(noRepeatPersonElementList);

		return resultList;
	}

	@Override
	public List detailList(RequestContext xmlContext) throws Exception {
		String orgIds = xmlContext.getParameter("orgIds");
		List rtnList = new ArrayList();
		List<SysOrgElement> elemList = null;
		if (StringUtil.isNotNull(orgIds)) {
			String[] ids = orgIds.split(";");
			elemList = sysOrgElementService.findByPrimaryKeys(ids);
			// #168003 按传入的ID顺序进行排序
			Map<String, SysOrgElement> temp = new HashMap<>(16);
			for (SysOrgElement elem : elemList) {
				temp.put(elem.getFdId(), elem);
			}
			for (String id : ids) {
				SysOrgElement element = temp.get(id);
				if (element != null) {
					rtnList.add(formatElement(element, true));
				}
			}
		}
		return rtnList;
	}

	@Override
	public List searchList(RequestContext xmlContext) throws Exception {
		int orgType = ORG_TYPE_PERSON;
		String para = xmlContext.getParameter("orgType");
		String deptLimit = xmlContext.getParameter("deptLimit");
		if (StringUtil.isNotNull(para)) {
			try {
				orgType = Integer.parseInt(para);
			} catch (NumberFormatException e) {
			}
		}
		Set<String> elementIds = null;
		if (StringUtil.isNotNull(deptLimit) && !"undefined".equals(deptLimit)) {
			elementIds = OrgDialogUtil.getRootOrgIds(deptLimit);
		}
		HQLInfo hqlInfo = new HQLInfo();
		String[] keys = xmlContext.getParameter("keyword").replaceAll("'", "''").split("\\s*[,;，；]\\s*");
		StringBuffer whereBf = new StringBuffer();
		if (keys.length > 0) {
			for (int i = 0; i < keys.length; i++) {
				String key = keys[i];
				if (StringUtil.isNull(key)) {
                    continue;
                }
				key = key.toLowerCase();
				whereBf.append(" or lower(sysOrgElement.fdName) like :key_" + i)
						.append(" or lower(sysOrgElement.fdLoginName) like :key_" + i)
						.append(" or lower(sysOrgElement.fdNameSimplePinyin) like :key_" + i)
						.append(" or lower(sysOrgElement.fdMobileNo) like :key_" + i)
						.append(" or lower(sysOrgElement.fdNo) like :key_" + i);
				hqlInfo.setParameter("key_" + i, "%" + key + "%");
			}
			if (whereBf.length() > 0) {
				whereBf.delete(0, 4);
				whereBf.insert(0, "(");
				whereBf.append(") and ");
			}
		}
		if (whereBf.length() == 0) {
			return null;
		}

		if (CollectionUtils.isNotEmpty(elementIds)) {
			List<String> hierarchyIds = getSysOrgCoreService().getHierarchyIdsByIds(new ArrayList<>(elementIds));
			if (CollectionUtils.isNotEmpty(hierarchyIds)) {
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < hierarchyIds.size(); i++) {
					String hierarchyId = hierarchyIds.get(i);
					if (i > 0) {
						sb.append(" or ");
					}
					sb.append("sysOrgElement.fdHierarchyId like :fdHierarchyId_" + i);
					hqlInfo.setParameter("fdHierarchyId_" + i, hierarchyId + "%");
				}
				whereBf.append(" (").append(sb).append(") and ");
			}
		}

		String isExternal = null;
		// 员工黄页不显示生态组织
		if (this.getClass().getName().contains("MobileSysZoneAddressServiceImp")) {
			isExternal = "false";
		} else {
			isExternal = xmlContext.getParameter("isExternal");
		}
		whereBf.append(SysOrgHQLUtil.buildWhereBlock(orgType, null, "sysOrgElement"));
		if (SysOrgEcoUtil.IS_ENABLED_ECO) {
			if (StringUtil.isNotNull(isExternal)) {
				whereBf.append(" and sysOrgElement.fdIsExternal = :isExternal ");
				hqlInfo.setParameter("isExternal", Boolean.parseBoolean(isExternal));
			}
			// 处理生态组织管理端
			String sys_page = xmlContext.getParameter("sys_page");
			if ("true".equals(sys_page)) {
				// 后台查询生态组织，如果不是管理员，那么需要校验是否组织管理员
				AuthOrgRange range = UserUtil.getKMSSUser().getUserAuthInfo().getAuthOrgRange();
				if (range != null && CollectionUtils.isNotEmpty(range.getAdminRanges())) {
					xmlContext.setParameter("admin_page", "true");
				}
			}
		} else {
			whereBf.append(" and (sysOrgElement.fdIsExternal is null or sysOrgElement.fdIsExternal = :isExternal) ");
			hqlInfo.setParameter("isExternal", Boolean.FALSE);
		}

		String parentId = xmlContext.getParameter("parentId");
		String cateId = xmlContext.getParameter("cateId");
		if(StringUtil.isNotNull(cateId)) {
			parentId = cateId;
		}
		if (StringUtil.isNotNull(parentId)) {
			whereBf.append(" and sysOrgElement.fdHierarchyId like :parentId and sysOrgElement.fdId != :parentId_ ");
			hqlInfo.setParameter("parentId", "%" + parentId + "%");
			hqlInfo.setParameter("parentId_", parentId);
		}
		hqlInfo.setWhereBlock(whereBf.toString());
		hqlInfo.setOrderBy(
				"sysOrgElement.fdOrgType desc, sysOrgElement.fdOrder,  sysOrgElement."
						+ SysLangUtil.getLangFieldName("fdName"));
		hqlInfo.setRowSize(101);
		hqlInfo.setCacheable(true);
		hqlInfo.setGetCount(false);
		if (UserUtil.getKMSSUser().isAdmin()) {
			hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck, SysAuthConstant.AllCheck.NO);
		} else {
			// 生态管理端
			if ("true".equals(xmlContext.getParameter("admin_page"))) {
				hqlInfo.setAuthCheckType("EXTERNAL_READER");
			} else {
				hqlInfo.setAuthCheckType("DIALOG_READER");
			}
			xmlContext.setParameter("filterList", "true");
		}
		return formatListData(xmlContext, sysOrgElementService
				.findPage(hqlInfo).getList(), false);
	}

	// needSort 是否需要排序 true：排序规则 先群组后拼音
	protected List formatListData(RequestContext xmlContext, List orgList,
								  boolean needSort) throws Exception {
		List rtnList = new ArrayList();
		if (!UserUtil.getKMSSUser().isAdmin()) {
			if ("true".equals(xmlContext.getParameter("admin_page"))) {
				orgList = orgRangeService.authFilterAdmin(orgList);
			} else {
//				if ("true".equals(xmlContext.getParameter("filterList"))) {
//					orgList = orgRangeService.authFilterList(orgList);
//				} else {
//					orgList = orgRangeService.authFilterTree(orgList);
//				}
			}
		}
		orgList = sysOrganizationStaffingLevelService
				.getStaffingLevelFilterResult(orgList);
		if (orgList == null || orgList.isEmpty()) {
			return rtnList;
		}
		// 先构造机构|部门信息
		List deptList = new ArrayList();
		List orgList1 = new ArrayList();
		List postList = new ArrayList();
		List personList = new ArrayList();
		List groupList = new ArrayList();
		String exceptValue = xmlContext.getParameter("exceptValue");

		String curCate = "";
		for (Iterator iterator = orgList.iterator(); iterator.hasNext();) {
			SysOrgElement tmpOrg = (SysOrgElement) iterator.next();
			if (StringUtil.isNotNull(exceptValue)) {// 排除例外
				String[] exceptValues = exceptValue.split("[;；,，]");
				if (Arrays.asList(exceptValues).contains(tmpOrg.getFdId())) {
					continue;
				}
			}
			Map map = formatElement(tmpOrg);
			int orgType = tmpOrg.getFdOrgType();
			if ((orgType & ORG_TYPE_DEPT) == orgType) {// 部门
				deptList.add(map);
			} else if ((orgType & ORG_TYPE_ORG) == orgType) {// 机构
				orgList1.add(map);
			} else if (orgType == ORG_TYPE_PERSON) {// 人员
				personList.add(map);
			} else if (orgType == ORG_TYPE_POST) {// 岗位
				postList.add(map);
			} else if (orgType == ORG_TYPE_GROUP) {// 群组
				groupList.add(map);
			}
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
		if (!postList.isEmpty()) {
			rtnList.add(formatHeader("4"));
			Collections.sort(postList, new OrderComparator());
			rtnList.addAll(postList);
		}
		if (!groupList.isEmpty()) {
			rtnList.add(formatHeader("16"));
			Collections.sort(groupList, new OrderComparator());
			rtnList.addAll(groupList);
		}
		return rtnList;
	}

	//只对高级地址本的自定义的数据作排序，优先排序号，其次拼音
	protected List formatListDataForCustomize(RequestContext xmlContext, List orgList,
											  boolean needSort) throws Exception {
		List rtnList = new ArrayList();
		orgList = sysOrganizationStaffingLevelService
				.getStaffingLevelFilterResult(orgList);
		if (orgList == null || orgList.isEmpty()) {
			return rtnList;
		}
		// 先构造机构|部门信息
		List deptList = new ArrayList();
		List orgList1 = new ArrayList();
		List postList = new ArrayList();
		List personList = new ArrayList();
		List groupList = new ArrayList();
		String exceptValue = xmlContext.getParameter("exceptValue");

		String curCate = "";
		for (Iterator iterator = orgList.iterator(); iterator.hasNext();) {
			SysOrgElement tmpOrg = (SysOrgElement) iterator.next();
			if (StringUtil.isNotNull(exceptValue)) {// 排除例外
				String[] exceptValues = exceptValue.split("[;；,，]");
				if (Arrays.asList(exceptValues).contains(tmpOrg.getFdId())) {
					continue;
				}
			}
			Map map = formatElement(tmpOrg);
			int orgType = tmpOrg.getFdOrgType();
			if ((orgType & ORG_TYPE_DEPT) == orgType) {// 部门
				deptList.add(map);
			} else if ((orgType & ORG_TYPE_ORG) == orgType) {// 机构
				orgList1.add(map);
			} else if (orgType == ORG_TYPE_PERSON) {// 人员
				personList.add(map);
			} else if (orgType == ORG_TYPE_POST) {// 岗位
				postList.add(map);
			} else if (orgType == ORG_TYPE_GROUP) {// 群组
				groupList.add(map);
			}
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

		if (!personList.isEmpty()) {
			Collections.sort(personList, new OrderComparator());
			rtnList.add(formatHeader("8"));
			rtnList.addAll(personList);
		}
		if (!postList.isEmpty()) {
			rtnList.add(formatHeader("4"));
			Collections.sort(postList, new OrderComparator());
			rtnList.addAll(postList);
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

	private Map formatElement(SysOrgElement orgElem) throws Exception {
		return formatElement(orgElem, false);
	}

	protected Map formatElement(SysOrgElement orgElem, boolean needDetail)
			throws Exception {
		Map tmpMap = new HashMap();
		try {
			tmpMap.put("fdId", orgElem.getFdId());
			tmpMap.put("label", OrgDialogUtil.getDeptLevelNames(orgElem));
			tmpMap.put("fdNo", orgElem.getFdNo());
			tmpMap.put("isExternal", orgElem.getFdIsExternal().toString());

//			if (orgRangeService.checkAuth(orgElem)) {
			tmpMap.put("isAllDept", "true");
//			} else {
//				tmpMap.put("isAllDept", "false");
//			}

			String adminStr = "";
			List<SysOrgElement> list = orgElem.getAuthElementAdmins();
			for (SysOrgElement sysOrgElement : list) {
				if (StringUtil.isNotNull(adminStr)) {
                    adminStr += ";";
                }
				adminStr += sysOrgElement.getFdName();
			}
			tmpMap.put("admin", adminStr);

			SysOrgElementRange range = orgElem.getFdRange();
			if (range != null) {
				tmpMap.put("fdInviteUrl", range.getFdInviteUrl());
			}
			tmpMap.put("type", orgElem.getFdOrgType());
			tmpMap.put("fdIsAvailable", orgElem.getFdIsAvailable());
			tmpMap.put("order", orgElem.getFdOrder());
			tmpMap.put("pinyin", orgElem.getFdNamePinYin());
//			if (SysOrgEcoUtil.IS_ENABLED_ECO) {
//				SysOrgElement parent = orgElem.getFdParent();
			// 判断是否有父部门的使用权限
//				if (parent != null && orgRangeService.checkAuth(parent)) {
			setParent(tmpMap, orgElem, needDetail);
//				}
//			} else {
//				setParent(tmpMap, orgElem, needDetail);
//			}
			if (orgElem.getFdOrgType() == ORG_TYPE_PERSON) {
				String img = "";
				if ("true".equals(SysFormDingUtil.getEnableDing())) {
					img = PersonInfoServiceGetter.getPersonDingHeadimage(orgElem
							.getFdId(), null);
				} else {
					img = PersonInfoServiceGetter.getPersonHeadimageUrl(orgElem
							.getFdId());
				}
				tmpMap.put("icon", img);
				tmpMap.put("dingImg", SysFormDingUtil.getEnableDing());

				SysOrgPerson p = (SysOrgPerson) sysOrgPersonService
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
				// 外部人员额外
				if (BooleanUtils.isTrue(orgElem.getFdIsExternal())) {
					// 岗位
					if (CollectionUtils.isNotEmpty(orgElem.getFdPosts())) {
						List<String> posts = new ArrayList<String>();
						for (Object post : orgElem.getFdPosts()) {
							posts.add(((SysOrgElement) post).getFdName());
						}
						tmpMap.put("posts", posts);
					}
					// 负责人（获取本组织的负责人）
					String[] split = orgElem.getFdHierarchyId().split(BaseTreeConstant.HIERARCHY_ID_SPLIT);
					List<Object> temp = sysOrgElementService.getBaseDao().getHibernateSession().createNativeQuery("select count(*) from sys_org_element_admins where fd_admin_id = :adminId and fd_element_id in (:elementIds)").setParameter("adminId", orgElem.getFdId()).setParameterList("elementIds", Arrays.asList(split)).list();
					if (NumberUtils.toInt(temp.get(0).toString(), 0) > 0) {
						tmpMap.put("authElementAdmin",
								ResourceUtil.getString("sys-organization:sysOrgElementExternal.authElementAdmin"));
					}
				}
			}
		} catch (Exception e) {
			logger.error(
					"移动端地址本转换组织机构数据发生异常：MobileAddressServiceImp.formatElement");
			logger.error("class:" + orgElem.getClass().toString() + "     "
					+ "orgType:" + orgElem.getFdOrgType() + "     " + "fdId:"
					+ orgElem.getFdId() + "     " + "fdName:"
					+ orgElem.getFdName());
			logger.error(e.toString());
			throw e;
		}

		return tmpMap;
	}

	private void setParent(Map tmpMap, SysOrgElement orgElem, boolean needDetail) {
		if (orgElem.getFdOrgType().equals(ORG_TYPE_PERSON) || orgElem.getFdOrgType().equals(ORG_TYPE_POST)
				|| orgElem.getFdOrgType().equals(ORG_TYPE_ORG) || orgElem.getFdOrgType().equals(ORG_TYPE_DEPT)) {
			tmpMap.put("parentNames",
					StringUtil.isNotNull(orgElem.getFdParentsName("_")) ? orgElem.getFdParentsName("_") : "");
		}
		if (needDetail) {
			if (orgElem.getFdParent() != null) {
				tmpMap.put("parentId", orgElem.getFdParent().getFdId());
			}
		}
	}

	private class OrderComparator implements Comparator {
		private Object parentName;

		public OrderComparator(){}

		public OrderComparator(Object parentName) {
			this.parentName = parentName;
		}

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
			if (parentName == null) {
				return ((Integer) order1).compareTo((Integer) order2);
			}else{
				Integer c1 = 1, c2 = 1;
				Object p1 = ((Map) o1).get("parentNames"), p2 = ((Map) o2).get("parentNames");
				if (p1 == null) {
					p1 = "";
				}
				if (p2 == null) {
					p2 = "";
				}
				if (!p1.equals(parentName)) {
					c1 = 2;
				}
				if (!p2.equals(parentName)) {
					c2 = 2;
				}
				return ((Integer) order1).compareTo((Integer) order2) + c1.compareTo(c2);
			}
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

	private class SequenceComparator implements Comparator<SysOrgElement> {

		private String sequence;

		public SequenceComparator(String sequence) {
			this.sequence = sequence;
		}

		@Override
		public int compare(SysOrgElement p1, SysOrgElement p2) {
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

		if (StringUtil.isNotNull(personId)) {
			JSONArray array = new JSONArray();
			List<String> l = ArrayUtil.convertArrayToList(personId.split(";"));
			List<String> personIds = getSysOrgCoreService().expandToPersonIds(l);
			List<String> selectPersonIds = new ArrayList<String>();

			result.put("total", personIds.size());
			int size = personIds.size() > Integer.parseInt(count) ? Integer
					.parseInt(count) : personIds.size();
			selectPersonIds = personIds.subList(0, size);
			if (selectPersonIds.size() > 0) {
				HQLInfo hqlInfo = new HQLInfo();
				hqlInfo.setWhereBlock(HQLUtil.buildLogicIN("fdId",
						selectPersonIds));
				List<SysOrgElement> persons = sysOrgElementService
						.findList(hqlInfo);
				Collections.sort(persons, new SequenceComparator(personId));

				for (SysOrgElement person : persons) {
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
		JSONArray array = new JSONArray();
		String personId = xmlContext.getParameter("personId");

		List<String> l = ArrayUtil.convertArrayToList(personId.split(";"));
		List<String> personIds = getSysOrgCoreService().expandToPersonIds(l);
		int start = (pageno - 1) * rowsize;
		int end = pageno * rowsize > personIds.size() ? personIds.size()
				: pageno * rowsize;
		List<String> selectPersonIds = personIds.subList(start, end);
		if (selectPersonIds.size() > 0) {
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock(
					HQLUtil.buildLogicIN("fdId", selectPersonIds));
			List<SysOrgElement> persons = sysOrgElementService
					.findList(hqlInfo);
			Collections.sort(persons, new SequenceComparator(personId));
			for (SysOrgElement person : persons) {
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
									 SysOrgElement person) {
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
		SysOrgElement currentUser = UserUtil.getUser();
		List<SysOrgElement> elemList = null;
		if ("22".equals(scope)) {
			// 本机构
			scopeHandlerIds = "";

			Set<String> elementIds = OrgDialogUtil.getRootOrgIds("myOrg");
			if (CollectionUtils.isEmpty(elementIds)) {
				scopeHandlerIds = (currentUser.getFdParentOrg() != null)
						? currentUser.getFdParentOrg().getFdId() : "";
			} else {
				for (String str : elementIds) {
					if("".equals(scopeHandlerIds)) {
						scopeHandlerIds+=str;
					}else {
						scopeHandlerIds+= ";"+str;
					}
				}
			}

			elemList = findChildren(scopeHandlerIds, orgType, currentUser,
					keyword, true);

		} else if ("33".equals(scope)) {
			// 本部门
			scopeHandlerIds = (currentUser.getFdParent() != null)
					? currentUser.getFdParent().getFdId() : "";
			List<SysOrgElement> fdPosts = currentUser.getFdPosts();
			for (SysOrgElement post : fdPosts) {
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
					elemList = getResultList(scopeHandlerIds, currentUser);
				}
			}
		} else if("55".equals(scope)) {
			String deptLimit = xmlContext.getParameter("deptLimit");
			if(StringUtil.isNotNull(deptLimit)) {
				scopeHandlerIds = "";
				//解析deptLimit，获取id数组
				String prefix = "otherOrgDept-";
				deptLimit = deptLimit.substring(prefix.length());
				String[] ids = deptLimit.split(";");
				List<String> parentIds = OrgDialogUtil.getCurUserOrgDeptIds();
				//排除本机构和本部门
				for (int i = 0; i < ids.length; i++) {
					String id = ids[i];
					if(parentIds.contains(id)) {
						continue;
					}
					scopeHandlerIds += id + ";";
				}
				if(StringUtil.isNotNull(scopeHandlerIds)) {
					scopeHandlerIds = scopeHandlerIds.substring(0,scopeHandlerIds.length()-1);
					elemList = findChildren(scopeHandlerIds, orgType, currentUser,
							keyword, true);
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
		if ("44".equals(scope)) {
			// 对高级地址本自定义的数据进行排序：优先排序号，其次拼音
			if (elemList == null
					|| (elemList.size() == 1 && elemList.get(0) == null)) {
				return formatListDataForCustomize(xmlContext, null, true);
			}
			return formatListDataForCustomize(xmlContext, elemList, true);
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

	private List<SysOrgElement> searchByKeyWord(List<SysOrgElement> elemList,
												String keyword) throws Exception {
		List<SysOrgElement> rtnList = new ArrayList<SysOrgElement>();
		Collection<String> tempOrgIds = new HashSet<String>();
		for (SysOrgElement handler : elemList) {
			if (handler != null && !tempOrgIds.contains(handler.getFdId())) {
				if (StringUtil.isNotNull(keyword)) {
					if (handler
							.getFdOrgType() == SysOrgConstant.ORG_TYPE_PERSON) {
						SysOrgPerson handlerPerson = (SysOrgPerson) sysOrgPersonService
								.findByPrimaryKey(handler.getFdId());
						if (handler.getFdName().contains(keyword)
								|| (StringUtil.isNotNull(
								handlerPerson.getFdLoginName())
								&& handlerPerson.getFdLoginName()
								.contains(keyword))
								|| (StringUtil.isNotNull(
								handlerPerson.getFdNickName())
								&& handlerPerson.getFdNickName()
								.contains(keyword))
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

	private List<SysOrgElement> getFormulaValue(RequestContext requestInfo,
												String script)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException {
		List<SysOrgElement> rtnVal = new ArrayList<SysOrgElement>();
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
		fact.setReturnType(SysOrgElement.class.getName() + "[]");
		try {
			rtnVal = (List<SysOrgElement>) ruleProvider
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
			// TODO 什么问题？待验证
			if (valObj.toString().endsWith(",")) {
				valObj = valObj.toString().substring(0,
						valObj.toString().length() - 1);
			}
			// 如果为地址本数据，则设置为map
			// 数据格式为“id1;id2;id3,name1;name2;name3”
			// TODO 地址本多值情况有问题？
			int indexOf = valObj.toString().indexOf(",");
			if (indexOf > -1) {
				Map<String, String> m = new HashMap<String, String>();
				m.put("id", valObj.toString().substring(0, indexOf));
				m.put("name", valObj.toString().substring(indexOf + 1));
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

	private List<SysOrgElement> getResultList(String scopeHandlerIds,
											  SysOrgElement currentUser) {
		List<SysOrgElement> scopeHandlerResultList = null;
		List<SysOrgElement> scopeHanderList = getOrgServer()
				.findByPrimaryKeys(scopeHandlerIds.split(";"));
		String handlerIdentity = currentUser.getFdId();
		SysOrgElement sysOrgElement = getOrgServer().findByPrimaryKey(
				handlerIdentity);
		scopeHandlerResultList = getOrgServer().parseSysOrgRole(
				scopeHanderList, sysOrgElement);
		if (scopeHandlerResultList != null) {
			List<SysOrgElement> rtnList = new ArrayList<>();
			for (SysOrgElement element : scopeHandlerResultList) {
				if (element.getFdIsAvailable() && element.getFdIsBusiness()) {
					rtnList.add(element);
				}
			}
			return rtnList;
		}
		return scopeHandlerResultList;
	}

	private List<SysOrgElement> findChildren(String scopeHandlerIds,
											 String orgType,
											 SysOrgElement currentUser, String keyword, boolean isDept)
			throws Exception {
		List<SysOrgElement> allChildren = new ArrayList<SysOrgElement>();
		List<SysOrgElement> scopeHandlerResultList = null;
		if (!StringUtil.isNull(scopeHandlerIds)) {
			scopeHandlerResultList = getResultList(scopeHandlerIds,
					currentUser);
			for (int i = 0; i < scopeHandlerResultList.size(); i++) {
				SysOrgElement handler = scopeHandlerResultList.get(i);
				if (orgType.contains("DEPT")) {
					if (StringUtil.isNotNull(keyword)) {
						ArrayUtil.concatTwoList(
								getSysOrgCoreService().findAllChildren(handler,
										SysOrgConstant.ORG_TYPE_DEPT,
										"sysOrgDept.fdName like '%" + keyword
												+ "%' or sysOrgDept.fdNamePinYin like '%"
												+ keyword
												+ "%' or sysOrgDept.fdNameSimplePinyin like '%"
												+ keyword
												+ "%'"),
								allChildren);
					} else {
						ArrayUtil.concatTwoList(
								getSysOrgCoreService().findAllChildren(handler,
										SysOrgConstant.ORG_TYPE_DEPT),
								allChildren);
					}
				}
				if (orgType.contains("POST")) {
					if (StringUtil.isNotNull(keyword)) {
						ArrayUtil.concatTwoList(
								getSysOrgCoreService().findAllChildren(handler,
										SysOrgConstant.ORG_TYPE_POST,
										"sysOrgPost.fdName like '%" + keyword
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
								getSysOrgCoreService().findAllChildren(handler,
										SysOrgConstant.ORG_TYPE_POST),
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
							SysOrgConstant.ORG_TYPE_PERSON, whereBlock,
							"sysOrgPerson"));
					ArrayUtil.concatTwoList(
							sysOrgPersonService.findList(hqlInfo), allChildren);
					if (allChildren.size() >= 50) {
						break;
					}
				}
			}
		}
		return allChildren;
	}

	private ISysOrgPersonService sysOrgPersonService;

	private ProcessServiceManager processServiceManager;

	private ILbpmOrgParseService getOrgServer() {
		return processServiceManager.getOrgParseService();
	}

	public void
	setSysOrgPersonService(ISysOrgPersonService sysOrgPersonService) {
		this.sysOrgPersonService = sysOrgPersonService;
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
		String parentId = "";
		SysOrgElement user = UserUtil.getUser();
		if (user != null && user.getFdParent() != null) {
			parentId = user.getFdParent().getFdId();
		} else if (user != null && user.getFdParentOrg() != null) {
			parentId = user.getFdParentOrg().getFdId();
		}
		if (StringUtil.isNotNull(parentId)) {
			// 获取需要排除过滤掉的值（以逗号分隔的fdId集合）
			String exceptValue = request.getParameter("exceptValue");
			Set<String> exceptValues = null;
			if (StringUtil.isNotNull(exceptValue)) {
				String[] tempExceptValues = exceptValue.split("[;；,，]");
				exceptValues = new HashSet<String>();
				for (String value : tempExceptValues) {
					exceptValues.add(value);
				}
			}

			AuthOrgRange orgRange = UserUtil.getKMSSUser().getUserAuthInfo().getAuthOrgRange();
			HQLInfo hqlInfo = new HQLInfo();
			if (orgRange != null && orgRange.isSelf()) {
				hqlInfo.setWhereBlock("sysOrgElement.fdId = :fdId");
				hqlInfo.setParameter("fdId", UserUtil.getKMSSUser().getUserId());
			} else {
				hqlInfo
						.setWhereBlock("sysOrgElement.hbmParent.fdId = :parentId");
				hqlInfo.setParameter("parentId", parentId);
			}
			hqlInfo.setWhereBlock(SysOrgHQLUtil
					.buildWhereBlock(SysOrgConstant.ORG_TYPE_PERSON
									| SysOrgConstant.ORG_TYPE_POST, hqlInfo
									.getWhereBlock(),
							"sysOrgElement"));
			StringBuilder whereSb = new StringBuilder(hqlInfo.getWhereBlock());

			String isExternal = request.getParameter("isExternal");
			if (StringUtil.isNotNull(isExternal)) {
				whereSb.append(" and sysOrgElement.fdIsExternal=:isExternal ");
				hqlInfo.setParameter("isExternal", Boolean.parseBoolean(isExternal));
			}

			hqlInfo.setWhereBlock(whereSb.toString());

			if (CollectionUtils.isNotEmpty(exceptValues)) {
				hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and ",
						"sysOrgElement.fdId not in (:exceptValues)"));
				hqlInfo.setParameter("exceptValues", exceptValues);
			}

			hqlInfo.setOrderBy("sysOrgElement.fdOrder,sysOrgElement."
					+ SysLangUtil.getLangFieldName("fdName"));

			List<SysOrgElement> list = getSysOrgElementService()
					.findList(hqlInfo);
			list = sysOrganizationStaffingLevelService
					.getStaffingLevelFilterResult(list);

			if(StringUtil.isNotNull(keyword)) {
				keyword = keyword.toLowerCase();
			}
			members = expandToPersons(list, keyword, exceptValues);
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
			SysOrgElement org = (SysOrgElement) sysOrgElementService
					.findByPrimaryKey(fdId);
			if (org != null) {
				List<SysOrgElement> ls = new ArrayList<SysOrgElement>();
				getOrgOrgParents(ls, org, orgHierarchyIds);
				for (int i = ls.size() - 1; i >= 0; i--) {
					SysOrgElement parent = ls.get(i);
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

	private void getOrgOrgParents(List<SysOrgElement> ls,
								  SysOrgElement sysOrgElement, List<String> orgHierarchyIds) {
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

		// 获取需要排除过滤掉的值（以逗号分隔的fdId集合）
		String exceptValue = request.getParameter("exceptValue");
		Set<String> exceptValues = null;
		if (StringUtil.isNotNull(exceptValue)) {
			String[] tempExceptValues = exceptValue.split("[;；,，]");
			exceptValues = new HashSet<String>();
			for (String value : tempExceptValues) {
				exceptValues.add(value);
			}
		}
		if (CollectionUtils.isNotEmpty(exceptValues)) {
			where.append(" and sysOrgElement.fdId not in (:exceptValues)");
			info.setParameter("exceptValues", exceptValues);
		}
		info.setWhereBlock(where.toString());
		info.setOrderBy("fdOrgType asc, fdOrder, "
				+ SysLangUtil.getLangFieldName("fdName"));
		info.setCheckParam(SysAuthConstant.CheckType.AuthCheck, "DIALOG_READER");
		List<SysOrgElement> list = sysOrgElementService.findList(info);
		rtnMapList = expandToPersons(list, null, exceptValues);
		// 排序
		sortMobileElement(rtnMapList);
		return rtnMapList;
	}

	private List expandToPersons(List<SysOrgElement> list, String keyword, Set<String> exceptValues)
			throws Exception {
		List members = new ArrayList();
		// 记录人员ID，防止与岗位成员重复
		List<String> personIds = new ArrayList<String>();
		// 如果有岗位，需要解析岗位中的人员
		for (SysOrgElement elem : list) {
			if (elem.getFdOrgType()
					.intValue() == SysOrgConstant.ORG_TYPE_POST) {
				// 从岗位中获取有权限查看的人
				List<SysOrgElement> persons = getPersonByOrgRange(elem);
				if (persons != null && !persons.isEmpty()) {
					for (SysOrgElement person : persons) {
						if (personIds.contains(person.getFdId())) {
							continue;
						}
						// 排除人员
						if (CollectionUtils.isNotEmpty(exceptValues)) {
							if (exceptValues.contains(person.getFdId())) {
								continue;
							}
						}
						SysOrgPerson p = (SysOrgPerson) sysOrgPersonService
								.findByPrimaryKey(person.getFdId());
						if (StringUtil.isNull(keyword)
								|| searchPerson(p, keyword)) {
							personIds.add(person.getFdId());
							Map<String, Object> obj = formatElement(person);
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
					SysOrgPerson p = (SysOrgPerson) sysOrgPersonService
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

	private boolean checkVisible(String fdId, Set<String> elementIds) throws Exception {
		SysOrgElement element = (SysOrgElement) sysOrgElementService.findByPrimaryKey(fdId);
		if (element != null) {
			String hierarchyId = SysOrgUtil.buildHierarchyIdIncludeOrg(element);
			for (String id : elementIds) {
				if (hierarchyId.contains(id)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 通过岗位获有权限的人员
	 * @param post
	 * @return
	 */
	private List<SysOrgElement> getPersonByOrgRange(SysOrgElement post) {
		List<SysOrgElement> persons = post.getFdPersons();
		if (SysOrgEcoUtil.IS_ENABLED_ECO) {
			SysOrgElement parent = post.getFdParent();
			if (parent != null) {
				return persons;
			} else {
				AuthOrgRange orgRange = UserUtil.getKMSSUser().getUserAuthInfo().getAuthOrgRange();
				List<SysOrgElement> result = new ArrayList<>();
				if (orgRange != null && CollectionUtils.isNotEmpty(orgRange.getAuthRanges())) {
					for (SysOrgElement person : persons) {
						boolean isAuth = false;
						for (SysOrgShowRange range : orgRange.getAuthRanges()) {
							if (person.getFdHierarchyId().startsWith(range.getFdHierarchyId())) {
								isAuth = true;
								break;
							}
						}
						if (isAuth) {
							result.add(person);
						}
					}
				}
				return result;
			}
		} else {
			return persons;
		}
	}

	/** 指定人员是否匹配关键字 */
	private boolean searchPerson(SysOrgPerson p, String keyword) {
		String name = StringUtil.isNotNull(p.getFdName()) ? p.getFdName().toLowerCase() : "";
		String loginName = StringUtil.isNotNull(p.getFdLoginName()) ? p.getFdLoginName().toLowerCase() : "";
		String nameSimplePinyin = StringUtil.isNotNull(p.getFdNameSimplePinyin()) ? p.getFdNameSimplePinyin() : "";
		String namePinyin = p.getFdNamePinYin();
		return name.indexOf(keyword) > -1 || loginName.indexOf(keyword) > -1
				|| StringUtil.isNotNull(nameSimplePinyin)
				&& nameSimplePinyin.toLowerCase().indexOf(keyword) > -1
				|| StringUtil.isNotNull(namePinyin)
				&& namePinyin.toLowerCase().indexOf(keyword) > -1;
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
		SysOrgPerson person = (SysOrgPerson) sysOrgPersonService
				.findByPrimaryKey(UserUtil.getUser().getFdId());
		ids.add(person.getFdId());
		List<SysOrgElement> posts = person.getFdPosts();
		if (posts != null && !posts.isEmpty()) {
			for (SysOrgElement post : posts) {
				ids.add(post.getFdId());
			}
		}
		// 2. 用上面的ID查找组织架构中的机构（部门）领导、上级领导
		HQLInfo info = new HQLInfo();
		info.setSelectBlock(propId);
		info.setWhereBlock(
				"fdIsBusiness is true and fdIsAvailable is true and fdOrgType in (1, 2) and (hbmThisLeader.fdId in (:ids) or hbmSuperLeader.fdId in (:ids))");
		info.setParameter("ids", ids);
		return sysOrgElementService.findList(info);
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
