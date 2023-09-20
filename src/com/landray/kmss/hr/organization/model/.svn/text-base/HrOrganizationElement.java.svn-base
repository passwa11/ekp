package com.landray.kmss.hr.organization.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.constant.BaseTreeConstant;
import com.landray.kmss.hr.organization.constant.HrOrgConstant;
import com.landray.kmss.hr.organization.forms.HrOrganizationElementForm;
import com.landray.kmss.hr.organization.interfaces.IHrOrgElement;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfo;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.organization.model.SysOrganizationConfig;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.ObjectUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;

/**
  * 人事组织架构
  */
public class HrOrganizationElement extends BaseModel implements HrOrgConstant, IHrOrgElement {

	
	private static final long serialVersionUID = -9135913390570793809L;

	static {
		ModelConvertor_Common.addCacheClass(HrOrganizationElement.class);
	}

	/*
	 * 类型
	 */
	private Integer fdOrgType = 0;

	@Override
	public Integer getFdOrgType() {
		return fdOrgType;
	}

	public void setFdOrgType(Integer fdOrgType) {
		this.fdOrgType = fdOrgType;
	}

	/*
	 * 层级ID
	 */
	protected String fdHierarchyId;

	public String getFdHierarchyId() {
		if (fdHierarchyId == null) {
			fdHierarchyId = BaseTreeConstant.HIERARCHY_ID_SPLIT + getFdId() + BaseTreeConstant.HIERARCHY_ID_SPLIT;
		}
		return fdHierarchyId;
	}

	public void setFdHierarchyId(String fdHierarchyId) {
		this.fdHierarchyId = fdHierarchyId;
	}

	/*
	 * 名称
	 */
	private String fdName;

	public String getFdNameOri() {
		return fdName;
	}

	/**
	 * 简称
	 */
	private String fdNameAbbr;

	public String getFdNameAbbr() {
		return fdNameAbbr;
	}

	public void setFdNameAbbr(String fdNameAbbr) {
		this.fdNameAbbr = fdNameAbbr;
	}

	@Override
	public String getFdName() {
		return SysLangUtil.getPropValue(this, "fdName", fdName);
	}

	public String getFdName(String lang) {
		Locale locale = ResourceUtil.getLocale(lang);
		if (locale != null) {
			return getFdName(locale);
		} else {
			return this.fdName;
		}
	}

	public String getFdName(Locale locale) {
		if (!SysLangUtil.isLangEnabled()) {
			return this.fdName;
		}
		if (locale == null) {
			return this.fdName;
		}
		String localeCountry = SysLangUtil.getLocaleShortName(locale);
		String value_lang = this.getDynamicMap().get("fdName" + localeCountry);
		if (StringUtil.isNull(value_lang)) {
			return this.fdName;
		}
		return value_lang;
	}

	public void setFdName(String fdName) {
		this.fdName = fdName;
		SysLangUtil.setPropValue(this, "fdName", fdName);
	}

	/*
	 * 编号
	 */
	private String fdNo;

	@Override
	public String getFdNo() {
		return fdNo;
	}

	public void setFdNo(String fdNo) {
		this.fdNo = fdNo;
	}

	/*
	 * 排序号
	 */
	private Integer fdOrder;

	@Override
	public Integer getFdOrder() {
		return fdOrder;
	}

	public void setFdOrder(Integer fdOrder) {
		this.fdOrder = fdOrder;
	}

	/*
	 * 关键字
	 */
	private String fdKeyword;

	@Override
	public String getFdKeyword() {
		return fdKeyword;
	}

	public void setFdKeyword(String fdKeyword) {
		this.fdKeyword = fdKeyword;
	}

	/*
	 * 是否有效
	 */
	private Boolean fdIsAvailable;

	@Override
	public Boolean getFdIsAvailable() {
		if (fdIsAvailable == null) {
			fdIsAvailable = Boolean.TRUE;
		}
		return fdIsAvailable;
	}

	public void setFdIsAvailable(Boolean fdIsAvailable) {
		this.fdIsAvailable = fdIsAvailable;
	}

	/*
	 * 是否业务相关
	 */
	private Boolean fdIsBusiness;

	@Override
	public Boolean getFdIsBusiness() {
		if (fdIsBusiness == null) {
			fdIsBusiness = Boolean.TRUE;
		}
		return fdIsBusiness;
	}

	public void setFdIsBusiness(Boolean fdIsBusiness) {
		this.fdIsBusiness = fdIsBusiness;
	}

	/**
	 * 数据来源
	 */
	private String fdSource;

	public String getFdSource() {
		return fdSource;
	}

	public void setFdSource(String fdSource) {
		this.fdSource = fdSource;
	}

	/*
	 * 描述
	 */
	private String fdMemo;

	@Override
	public String getFdMemo() {
		return fdMemo;
	}

	public void setFdMemo(String fdMemo) {
		this.fdMemo = fdMemo;
	}

	/*****************************编制begin******************************/
	/**
	 * 是否开启编制
	 */
	private String fdIsCompileOpen;

	/**
	 * 是否限定人数
	 */
	private String fdIsLimitNum;

	/**
	 * 编制人数
	 */
	private Integer fdCompileNum;

	public String getFdIsCompileOpen() {
		return fdIsCompileOpen;
	}

	public String getFdIsLimitNum() {
		return fdIsLimitNum;
	}

	public Integer getFdCompileNum() {
		return fdCompileNum;
	}

	public void setFdIsCompileOpen(String fdIsCompileOpen) {
		this.fdIsCompileOpen = fdIsCompileOpen;
	}

	public void setFdIsLimitNum(String fdIsLimitNum) {
		this.fdIsLimitNum = fdIsLimitNum;
	}

	public void setFdCompileNum(Integer fdCompileNum) {
		this.fdCompileNum = fdCompileNum;
	}

	/*****************************编制end******************************/

	/**
	 * 是否虚拟组织
	 */
	private String fdIsVirOrg;

	/**
	 * 是否法人公司
	 */
	private String fdIsCorp;

	public String getFdIsVirOrg() {
		return fdIsVirOrg;
	}

	public String getFdIsCorp() {
		return fdIsCorp;
	}

	public void setFdIsVirOrg(String fdIsVirOrg) {
		this.fdIsVirOrg = fdIsVirOrg;
	}

	public void setFdIsCorp(String fdIsCorp) {
		this.fdIsCorp = fdIsCorp;
	}

	/*
	 * 创建时间
	 */
	private Date fdCreateTime = new Date();

	public Date getFdCreateTime() {
		return fdCreateTime;
	}

	public void setFdCreateTime(Date fdCreateTime) {
		this.fdCreateTime = fdCreateTime;
	}

	/*
	 * 最后修改时间
	 */
	private Date fdAlterTime = new Date();

	public Date getFdAlterTime() {
		return fdAlterTime;
	}

	public void setFdAlterTime(Date fdAlterTime) {
		this.fdAlterTime = fdAlterTime;
	}

	/*
	 * 本级领导
	 */
	private HrOrganizationElement fdThisLeader;

	public HrOrganizationElement getHbmThisLeader() {
		return fdThisLeader;
	}

	public void setHbmThisLeader(HrOrganizationElement fdThisLeader) {
		this.fdThisLeader = fdThisLeader;
	}

	/**
	 * 分管领导
	 */
	private HrOrganizationElement fdBranLeader;

	public HrOrganizationElement getFdBranLeader() {
		return fdBranLeader;
	}

	public void setFdBranLeader(HrOrganizationElement fdBranLeader) {
		this.fdBranLeader = fdBranLeader;
	}

	private List fdThisLeaderChildren;
	private List fdPersonExperienceContract;
	public List getHbmPersonExperienceContract() {
		return fdPersonExperienceContract;
	}

	public void setHbmPersonExperienceContract(List fdPersonExperienceContract) {
		this.fdPersonExperienceContract = fdPersonExperienceContract;
	}
	public List getHbmThisLeaderChildren() {
		return fdThisLeaderChildren;
	}

	public void setHbmThisLeaderChildren(List fdThisLeaderChildren) {
		this.fdThisLeaderChildren = fdThisLeaderChildren;
	}

	/*
	 * 上级领导
	 */
	private HrOrganizationElement fdSuperLeader;

	public HrOrganizationElement getHbmSuperLeader() {
		return fdSuperLeader;
	}

	public void setHbmSuperLeader(HrOrganizationElement fdSuperLeader) {
		this.fdSuperLeader = fdSuperLeader;
	}

	private List fdSuperLeaderChildren;

	public List getHbmSuperLeaderChildren() {
		return fdSuperLeaderChildren;
	}

	public void setHbmSuperLeaderChildren(List fdSuperLeaderChildren) {
		this.fdSuperLeaderChildren = fdSuperLeaderChildren;
	}

	@Override
	public HrOrganizationElement getLeader(int level) throws Exception {
		List<HrOrganizationElement> leaders = getAllLeader(level);
		if (level < 0) {
			level = leaders.size() + level;
		}
		if (level < 0 || level >= leaders.size()) {
			return null;
		}
		return leaders.get(level);
	}

	/**
	 * #25441：获取领导线角色逻辑，由原先的遍历出所有领导线成员 优化为 获取上一级仅取上一级，而不去循环计算。
	 * 有效防止抛出死循环错误。
	 * <p>
	 * 如果level > 0，则由提交人向上获取领导，假设“3级直线领导”有死循环，而此时只需要获取2级直线领导，此方法只会返回2级直线领导，而不会抛出错误。
	 * <p>
	 * 如果level < 0，则由组织机构从上往下获取领导，此时必须要获取所有领导，无法避免死循环错误
	 * <p>
	 * 如果level = null，则获取所有领导，无法避免死循环错误
	 */
	public List<HrOrganizationElement> getAllLeader(Integer level) throws Exception {
		// 以下代码由Domino的领导获取代码翻译而成，代码逻辑有些混乱，修改时需要跟domino的逻辑同步，否则很麻烦
		List<HrOrganizationElement> rtnVal = new ArrayList<HrOrganizationElement>();
		List<String> idList = new ArrayList<String>();
		List<String> nameList = new ArrayList<String>();
		HrOrganizationElement curElem = this; // 当前的组织架构元素
		HrOrganizationElement calElem = this; // 实际参与计算的组织架构元素
		HrOrganizationElement lastSuperLeader = null; // 上次循环使用的上级领导
		for (; curElem != null && calElem != null;) {
			// 若参与计算的组织架构是个人或已未定义领导的岗位，则计算的实体为所在的部门
			boolean isPost = false;
			int curHRType = calElem.getFdOrgType();
			if (curHRType == HR_TYPE_PERSON || curHRType == HR_TYPE_POST && calElem.getHbmThisLeader() == null) {
				calElem = calElem.getFdParent();
				if (calElem == null) {
					break;
				}
			}
			// 死循环校验
			nameList.add(calElem.getFdName());
			if (idList.contains(calElem.getFdId())) {
				throw new RuntimeException(ResourceUtil.getString("HrOrganizationElement.leader.cycle",
						"sys-HRanization", null, nameList.toString().replaceAll(",", " =>")));
			}
			idList.add(calElem.getFdId());
			// 获取直接领导，若直接领导为空或跟上次循环使用的上级领导重叠了，则忽略
			HrOrganizationElement thisLeader = calElem.getHbmThisLeader();
			if (!(thisLeader == null || thisLeader.equals(lastSuperLeader))) {
				if (thisLeader.getFdOrgType().intValue() == HR_TYPE_POST) {
					isPost = true;
				} else {
					isPost = false;
				}
				rtnVal.add(thisLeader);
			}
			if (calElem.getFdOrgType().intValue() == HR_TYPE_ORG || calElem.getFdOrgType().intValue() == HR_TYPE_DEPT) {
				// 若计算对象为机构或部门，则获取上级领导，并添加到返回值列表中
				// 若上级领导不为空，则下次循环采用上级领导，否则采用上级部门
				lastSuperLeader = calElem.getHbmSuperLeader();
				if (lastSuperLeader != null) {
					rtnVal.add(lastSuperLeader);
					curElem = lastSuperLeader;
				} else {
					curElem = calElem.getFdParent();
				}
				calElem = curElem;
			} else {
				// 注意：这里计算对象只剩下岗位了
				// 若计算对象为岗位，将持续获取岗位领导，不再获取部门（机构）
				if (thisLeader == null) {
					break;
				}
				// 设定下一轮循环的对象，将岗位领导设置为当前对象。
				calElem = thisLeader;
				if (curHRType == HR_TYPE_POST) {
					curElem = calElem.getHbmThisLeader();
				} else {
					curElem = calElem.getFdParent();
				}
				lastSuperLeader = null;
			}

			// 如果是由提交人往上获取领导，可以避免死循环
			if (level != null && level >= 0) {
				// 当获取到需要的级别领导时，不再往获取领导，而是返回已经获取到的领导
				if (rtnVal.size() >= level + 1 && isPost == false) {
					return rtnVal;
				}
			}
		}
		return rtnVal;
	}

	@Override
	public List<HrOrganizationElement> getAllLeader() throws Exception {
		// 以下代码由Domino的领导获取代码翻译而成，代码逻辑有些混乱，修改时需要跟domino的逻辑同步，否则很麻烦
		List<HrOrganizationElement> rtnVal = new ArrayList<HrOrganizationElement>();
		List<String> idList = new ArrayList<String>();
		List<String> nameList = new ArrayList<String>();
		HrOrganizationElement curElem = this; // 当前的组织架构元素
		HrOrganizationElement calElem = this; // 实际参与计算的组织架构元素
		HrOrganizationElement lastSuperLeader = null; // 上次循环使用的上级领导
		for (; curElem != null && calElem != null;) {
			// 若参与计算的组织架构是个人或已未定义领导的岗位，则计算的实体为所在的部门
			int curHRType = calElem.getFdOrgType();
			if (curHRType == HR_TYPE_PERSON || curHRType == HR_TYPE_POST && calElem.getHbmThisLeader() == null) {
				calElem = calElem.getFdParent();
				if (calElem == null) {
					break;
				}
			}
			// 死循环校验
			nameList.add(calElem.getFdName());
			if (idList.contains(calElem.getFdId())) {
				throw new RuntimeException(
						ResourceUtil.getString("HrOrganizationElement.leader.cycle", "sys-HRanization",
						null, nameList.toString().replaceAll(",", " =>")));
			}
			idList.add(calElem.getFdId());
			// 获取直接领导，若直接领导为空或跟上次循环使用的上级领导重叠了，则忽略
			HrOrganizationElement thisLeader = calElem.getHbmThisLeader();
			if (!(thisLeader == null || thisLeader.equals(lastSuperLeader))) {
				rtnVal.add(thisLeader);
			}
			if (calElem.getFdOrgType().intValue() == HR_TYPE_ORG || calElem.getFdOrgType().intValue() == HR_TYPE_DEPT) {
				// 若计算对象为机构或部门，则获取上级领导，并添加到返回值列表中
				// 若上级领导不为空，则下次循环采用上级领导，否则采用上级部门
				lastSuperLeader = calElem.getHbmSuperLeader();
				if (lastSuperLeader != null) {
					rtnVal.add(lastSuperLeader);
					curElem = lastSuperLeader;
				} else {
					curElem = calElem.getFdParent();
				}
				calElem = curElem;
			} else {
				// 注意：这里计算对象只剩下岗位了
				// 若计算对象为岗位，将持续获取岗位领导，不再获取部门（机构）
				if (thisLeader == null) {
					break;
				}
				// 设定下一轮循环的对象，将岗位领导设置为当前对象。
				calElem = thisLeader;
				if (curHRType == HR_TYPE_POST) {
					curElem = calElem.getHbmThisLeader();
				} else {
					curElem = calElem.getFdParent();
				}
				lastSuperLeader = null;
			}
		}
		return rtnVal;
	}

	@Override
	public HrOrganizationElement getMyLeader(int level) throws Exception {
		List<HrOrganizationElement> leaders = getAllMyLeader(level);
		if (level < 0) {
			level = leaders.size() + level;
		}
		if (level < 0 || level >= leaders.size()) {
			return null;
		}
		return leaders.get(level);
	}

	public List<HrOrganizationElement> getAllMyLeader(int level) throws Exception {
		List<HrOrganizationElement> rtnList = getAllLeader(level);
		if (getFdOrgType().intValue() == HR_TYPE_POST) {
			rtnList.remove(this);
		} else if (getFdOrgType().intValue() == HR_TYPE_PERSON) {
			rtnList.remove(this);
			rtnList.removeAll(getFdPosts());
		}
		return rtnList;
	}

	@Override
	public List<HrOrganizationElement> getAllMyLeader() throws Exception {
		List<HrOrganizationElement> rtnList = getAllLeader();
		if (getFdOrgType().intValue() == HR_TYPE_POST) {
			rtnList.remove(this);
		} else if (getFdOrgType().intValue() == HR_TYPE_PERSON) {
			rtnList.remove(this);
			rtnList.removeAll(getFdPosts());
		}
		return rtnList;
	}


	/**
	 * 机构
	 */
	private HrOrganizationElement fdParentOrg;

	@Override
	public HrOrganizationElement getFdParentOrg() {
		return fdParentOrg;
	}

	public HrOrganizationElement getHbmParentOrg() {
		return fdParentOrg;
	}

	public void setHbmParentOrg(HrOrganizationElement parentOrg) {
		this.fdParentOrg = parentOrg;
	}

	/*
	 * 父
	 */
	private HrOrganizationElement fdParent;

	@Override
	public HrOrganizationElement getFdParent() {
		return fdParent;
	}

	public void setFdParent(HrOrganizationElement parent) {
		this.fdParent = parent;
	}

	public HrOrganizationElement getHbmParent() {
		return fdParent;
	}

	public void setHbmParent(HrOrganizationElement parent) {
		this.fdParent = parent;
	}

	public String getFdParentsName() {
		return getFdParentsName("_");
	}

	/**
	 * 根据当前语言取层级名称
	 * 
	 * @param splitStr
	 * @return
	 */
	public String getFdParentsName(String splitStr) {
		return getFdParentsName(splitStr, false);
	}

	/**
	 * 根据语言取层级名称
	 * 
	 * @param splitStr,lang
	 * @return
	 */
	public String getFdParentsName(String splitStr, String lang) {
		String fdParentsName = "";
		List list = new ArrayList();
		if (fdParent != null) {
			try {
				HrOrganizationElement parent = fdParent;
				while (parent != null) {
					list.add(parent);
					parent = parent.getFdParent();
				}
			} catch (Exception ex) {
			}
		}
		for (int i = list.size() - 1; i >= 0; i--) {
			fdParentsName += ((HrOrganizationElement) list.get(i)).getFdName(lang);
			if (i > 0) {
				fdParentsName += splitStr;
			}
		}
		return fdParentsName;
	}

	/**
	 * 取官方语言的层级名称
	 * 
	 * @param splitStr
	 * @return
	 */
	public String getFdParentsNameOri(String splitStr) {
		return getFdParentsName(splitStr, true);
	}

	/**
	 * 取层级名称
	 * 
	 * @param splitStr
	 * @param isOfficialLang
	 *            是否取官方语言（当为false时，取当前语言的层级名称）
	 * @return
	 */
	private String getFdParentsName(String splitStr, boolean isOfficialLang) {
		String fdParentsName = "";
		List list = new ArrayList();
		if (fdParent != null) {
			try {
				HrOrganizationElement parent = fdParent;
				while (parent != null) {
					list.add(parent);
					parent = parent.getFdParent();
				}
			} catch (Exception ex) {
			}
		}
		for (int i = list.size() - 1; i >= 0; i--) {
			if (isOfficialLang) {
				fdParentsName += ((HrOrganizationElement) list.get(i)).getFdNameOri();
			} else {
				fdParentsName += ((HrOrganizationElement) list.get(i)).getFdName();
			}
			if (i > 0) {
				fdParentsName += splitStr;
			}
		}
		return fdParentsName;
	}

	/*
	 * 子（此关系由父维护，不提供set方法）
	 */
	private List fdChildren;

	public List getFdChildren() {
		List rtnVal = new ArrayList();
		if (getHbmChildren() != null) {
			rtnVal.addAll(getHbmChildren());
		}
		return rtnVal;
	}

	public List getHbmChildren() {
		return fdChildren;
	}

	public void setHbmChildren(List children) {
		this.fdChildren = children;
	}


	/*
	 * 人事档案历史数据-人员对应岗位
	 */
	public List fdOrgPosts;

	public List getFdOrgPosts() {
		return fdOrgPosts;
	}

	public void setFdOrgPosts(List fdOrgPosts) {
		this.fdOrgPosts = fdOrgPosts;
	}

	/*
	 * 岗位列表（个人使用）
	 */
	private List fdPosts = null;

	public List getFdPosts() {
		List rtnVal = new ArrayList();
		if (getHbmPosts() != null) {
			rtnVal.addAll(getHbmPosts());
		}
		return rtnVal;
	}

	public void setFdPosts(List posts) {
		if (this.fdPosts == posts) {
			return;
		}
		if (this.fdPosts == null) {
			this.fdPosts = new ArrayList();
		} else {
			this.fdPosts.clear();
		}
		if (posts != null) {
			this.fdPosts.addAll(posts);
		}
	}

	public List getHbmPosts() {
		return fdPosts;
	}

	public void setHbmPosts(List posts) {
		this.fdPosts = posts;
	}

	/*
	 * 个人列表（岗位使用，包含在职和离职人员）
	 */
	public List fdPersons = null;

	public List getFdPersons() {
		List rtnVal = new ArrayList();
		if (getHbmPersons() != null) {
			rtnVal.addAll(getHbmPersons());
		}
		return rtnVal;
	}

	public void setFdPersons(List persons) {
		if (this.fdPersons == persons) {
			return;
		}
		if (this.fdPersons == null) {
			this.fdPersons = new ArrayList();
		} else {
			this.fdPersons.clear();
		}
		if (persons != null) {
			this.fdPersons.addAll(persons);
		}
	}

	public List getHbmPersons() {
		return fdPersons;
	}

	public void setHbmPersons(List persons) {
		this.fdPersons = persons;
	}

	/**
	 * 个人列表（岗位使用，只包含在职人员）
	 */
	private List fdBePersons;

	public List getFdBePersons() throws Exception {
		Set set = new HashSet();
		List persons = getFdPersons();
		for (int i = 0; i < persons.size(); i++) {
			HrStaffPersonInfo personInfo = (HrStaffPersonInfo) persons.get(i);
			if (!"leave".equals(personInfo.getFdStatus())
					&& !"retire".equals(personInfo.getFdStatus())
					&& !"dismissal".equals(personInfo.getFdStatus())) {
				set.add(persons.get(i));
			}
		}
		/*	//查询兼岗
			IHrStaffTrackRecordService hrStaffTrackRecordService = (IHrStaffTrackRecordService)SpringBeanUtil.getBean("hrStaffTrackRecordService");
			List trackRecords = hrStaffTrackRecordService.findList("fdHrOrgPost.fdId = '" + this.getFdId() + "' and fdType = '2' and fdStatus = '1'", null);
			set.addAll(trackRecords);*/
		return new ArrayList<>(set);
	}

	public void setFdBePersons(List fdBePersons) {
		this.fdBePersons = fdBePersons;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (object == null) {
			return false;
		}
		if (!(object instanceof HrOrganizationElement)) {
			return false;
		}
		BaseModel objModel = (BaseModel) object;
		return ObjectUtil.equals(objModel.getFdId(), this.getFdId(), false);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
	public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.put("fdCreateTime", new ModelConvertor_Common("fdCreateTime").setDateTimeType(DateUtil.TYPE_DATETIME));
			toFormPropertyMap.put("fdParent.fdId", "fdParentId");
			toFormPropertyMap.put("fdParent.fdName", "fdParentName");
			toFormPropertyMap.put("hbmThisLeader.fdId", "fdThisLeaderId");
			toFormPropertyMap.put("hbmThisLeader.fdName", "fdThisLeaderName");
			toFormPropertyMap.put("fdBranLeader.fdId", "fdBranLeaderId");
			toFormPropertyMap.put("fdBranLeader.fdName", "fdBranLeaderName");
			toFormPropertyMap.put("fdParentOrg.fdId", "fdParentOrgId");
			toFormPropertyMap.put("fdParentOrg.fdName", "fdParentOrgName");

		}
		return toFormPropertyMap;
	}

	@Override
	public Class<HrOrganizationElementForm> getFormClass() {
		return HrOrganizationElementForm.class;
	}

	/*
	 * 是否已废弃,true表示禁用
	 */
	protected Boolean fdIsAbandon = new Boolean(false);

	public Boolean getFdIsAbandon() {
		return fdIsAbandon;
	}

	public void setFdIsAbandon(Boolean fdIsAbandon) {
		this.fdIsAbandon = fdIsAbandon;
	}


	/**
	 * 管理员 (部门与机构使用)
	 */
	protected List<HrOrganizationElement> authElementAdmins;

	/**
	 * @return 管理员
	 */
	public List<HrOrganizationElement> getAuthElementAdmins() {
		return authElementAdmins;
	}

	/**
	 * @param authAreaAdmin
	 *            管理员
	 */
	public void setAuthElementAdmins(List<HrOrganizationElement> authElementAdmins) {
		this.authElementAdmins = authElementAdmins;
	}

	@Override
	public void recalculateFields() {
		ModelUtil.checkTreeCycle(this, fdParent, "fdParent");

		if (fdParent == null || getFdOrgType().intValue() == HR_TYPE_ORG) {
			this.fdParentOrg = null;
		} else {
			if (fdParent.getFdOrgType().intValue() == HR_TYPE_ORG) {
				this.fdParentOrg = fdParent;
			} else {
				this.fdParentOrg = fdParent.getFdParentOrg();
			}
		}
	}

	public List<HrOrganizationElement> getAllParent(boolean excludeMe) throws Exception {
		List<HrOrganizationElement> rtnVal = new ArrayList<HrOrganizationElement>();
		HrOrganizationElement curElem = this; // 当前的组织架构元素

		while (curElem != null) {
			if (HrOrgConstant.HR_TYPE_ORG == curElem.getFdOrgType()
					|| HrOrgConstant.HR_TYPE_DEPT == curElem.getFdOrgType()) {
				rtnVal.add(curElem);
			}

			curElem = curElem.getFdParent();
		}

		if (rtnVal.size() > 0 && excludeMe) {
			rtnVal.remove(0);
		}

		return rtnVal;
	}

	public String getFdOrgEmail() {
		return fdOrgEmail;
	}

	public void setFdOrgEmail(String fdOrgEmail) {
		this.fdOrgEmail = fdOrgEmail;
	}

	private String fdOrgEmail;

	/**
	 * 部门/机构人员总数
	 */
	private Integer fdPersonsNumber;

	public Integer getFdPersonsNumber() {
		return fdPersonsNumber;
	}

	public void setFdPersonsNumber(Integer fdPersonsNumber) {
		this.fdPersonsNumber = fdPersonsNumber;
	}

	/**
	 * 部门/机构人员编制总数
	 */

	private Integer fdCompilationNum;

	public Integer getFdCompilationNum() {
		return fdCompilationNum;
	}

	public void setFdCompilationNum(Integer fdCompilationNum) {
		this.fdCompilationNum = fdCompilationNum;
	}

	/**
	 * 名称简拼
	 */
	private String fdNameSimplePinyin;

	public String getFdNameSimplePinyin() {
		return fdNameSimplePinyin;
	}

	public void setFdNameSimplePinyin(String fdNameSimplePinyin) {
		this.fdNameSimplePinyin = fdNameSimplePinyin;
	}

	/*
	 * 名称拼音
	 */
	private String fdNamePinYin;

	public String getFdNamePinYin() {
		return fdNamePinYin;
	}

	public void setFdNamePinYin(String fdNamePinYin) {
		this.fdNamePinYin = fdNamePinYin;
	}

	/*
	 * 部门层级显示名称
	 */
	private String deptLevelNames;

	/**
	 * 根据后台配置取部门层级名称
	 * 
	 * @return
	 */
	public String getDeptLevelNames() {
		return getFdName();
	}


	/**
	 * 根据后台配置的层级数来取部门名称
	 * 
	 * @param length
	 * @return
	 */
	public String getLatelyNames(int length) {
		StringBuffer latelyNames = new StringBuffer();
		HrOrganizationElement parent = this.fdParent;
		try {
			int count = 1;
			while (parent != null) {
				if (++count > length) {
					break;
				}
				latelyNames.insert(0, "_" + parent.getFdName());
				parent = parent.getFdParent();
			}
		} catch (Exception ex) {
		}
		latelyNames.append("_").append(getFdName());
		latelyNames.deleteCharAt(0);
		return latelyNames.toString();
	}

	private String fdPreDeptId;

	private String fdPrePostIds;

	public String getFdPreDeptId() {
		return fdPreDeptId;
	}

	public void setFdPreDeptId(String fdPreDeptId) {
		this.fdPreDeptId = fdPreDeptId;
	}

	public String[] getFdPrePostIdsArr() {
		String[] ids;
		if (StringUtil.isNotNull(fdPrePostIds)) {
			ids = fdPrePostIds.split(";");
		} else {
			ids = null;
		}

		return ids;
	}

	public void setFdPrePostIds(String[] fdPrePostIds) {
		String ids = "";
		if (fdPrePostIds != null) {
			for (int i = 0; i < fdPrePostIds.length; i++) {
				if (i == 0) {
					ids = fdPrePostIds[i];
				} else {
					ids += ";" + fdPrePostIds[i];
				}
			}
		}
		this.fdPrePostIds = ids;
	}

	public String getFdPrePostIds() {
		return fdPrePostIds;
	}

	public void setFdPrePostIds(String fdPrePostIds) {
		this.fdPrePostIds = fdPrePostIds;
	}
	
	
	/**
	 * 
	 * @param display
	 *            显示类别
	 * @param displayLength
	 *            最近的X级
	 */
	public String getDeptLevelNames(int display, int displayLength) {
		String dlns = "";
		// 如果类型不是部门，或者取配置信息失败，就直接返回fdName
		if ((getFdOrgType()
				| HrOrgConstant.HR_TYPE_HRORDEPT) != HrOrgConstant.HR_TYPE_HRORDEPT) {
			return getFdName();
		}

		switch (display) {
		case SysOrganizationConfig.DEPT_LEVEL_ALL: { // 部门全路径
			dlns = this.getFdParentsName();
			if (StringUtil.isNotNull(dlns)) {
				dlns += "_";
			}
			dlns += getFdName();
			break;
		}
		case SysOrganizationConfig.DEPT_LEVEL_ONLY_LAST: { // 仅末级部门
			dlns = getFdName();
			break;
		}
		case SysOrganizationConfig.DEPT_LEVEL_LATELY: { // 最近的X级
			dlns = getLatelyNames(displayLength);
			break;
		}
		default:
			dlns = getFdName();
			break;
		}
		return dlns;
	}


}
