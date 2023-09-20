package com.landray.kmss.sys.zone.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.authentication.intercept.RoleValidator;
import com.landray.kmss.sys.authentication.intercept.ValidatorRequestContext;
import com.landray.kmss.sys.authentication.user.KMSSUser;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.organization.eco.IOrgRangeService;
import com.landray.kmss.sys.organization.interfaces.SysOrgHQLUtil;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.model.SysOrgPost;
import com.landray.kmss.sys.organization.model.SysOrganizationStaffingLevel;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.sys.organization.service.ISysOrgPostService;
import com.landray.kmss.sys.organization.service.ISysOrganizationStaffingLevelService;
import com.landray.kmss.sys.organization.service.ISysOrganizationVisibleService;
import com.landray.kmss.sys.organization.util.SysOrgUtil;
import com.landray.kmss.sys.person.interfaces.PersonInfoServiceGetter;
import com.landray.kmss.sys.zone.model.SysZoneOrgOuter;
import com.landray.kmss.sys.zone.model.SysZoneOrgRelation;
import com.landray.kmss.sys.zone.model.SysZonePersonInfo;
import com.landray.kmss.sys.zone.service.ISysZoneOrgOuterService;
import com.landray.kmss.sys.zone.service.ISysZoneOrgRelationService;
import com.landray.kmss.sys.zone.service.ISysZonePersonInfoService;
import com.landray.kmss.sys.zone.util.SysZonePrivateUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.Logger;

import java.util.*;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class SysZonePersonInfoProvider implements IXMLDataBean, SysOrgConstant {
	private static Logger log = org.slf4j.LoggerFactory.getLogger(SysZonePersonInfoProvider.class);

	private ISysZonePersonInfoService personInfoService = null;
	private ISysZoneOrgOuterService sysZoneOrgOuterService = null;
	private ISysOrgPersonService sysOrgPersonService = null;
	private ISysZoneOrgRelationService sysZoneOrgRelationService = null;
	private ISysOrganizationVisibleService sysOrganizationVisibleService;
	private ISysOrganizationStaffingLevelService sysOrganizationStaffingLevelService;
	private RoleValidator roleValidator;
	private ISysOrgPostService sysOrgPostService;
	private IOrgRangeService orgRangeService;

	public void setPersonInfoService(ISysZonePersonInfoService personInfoService) {
		this.personInfoService = personInfoService;
	}

	public void setSysZoneOrgOuterService(
			ISysZoneOrgOuterService sysZoneOrgOuterService) {
		this.sysZoneOrgOuterService = sysZoneOrgOuterService;
	}

	public void setSysOrgPersonService(
			ISysOrgPersonService sysOrgPersonService) {
		this.sysOrgPersonService = sysOrgPersonService;
	}

	public void setSysZoneOrgRelationService(
			ISysZoneOrgRelationService sysZoneOrgRelationService) {
		this.sysZoneOrgRelationService = sysZoneOrgRelationService;
	}

	public void setSysOrganizationVisibleService(
			ISysOrganizationVisibleService sysOrganizationVisibleService) {
		this.sysOrganizationVisibleService = sysOrganizationVisibleService;
	}

	public void setSysOrganizationStaffingLevelService(
			ISysOrganizationStaffingLevelService sysOrganizationStaffingLevelService) {
		this.sysOrganizationStaffingLevelService = sysOrganizationStaffingLevelService;
	}

	public ISysOrganizationStaffingLevelService
	getSysOrganizationStaffingLevelService() {
		return sysOrganizationStaffingLevelService;
	}

	public void setRoleValidator(RoleValidator roleValidator) {
		this.roleValidator = roleValidator;
	}

	public void setSysOrgPostService(ISysOrgPostService sysOrgPostService) {
		this.sysOrgPostService = sysOrgPostService;
	}

	public void setOrgRangeService(IOrgRangeService orgRangeService) {
		this.orgRangeService = orgRangeService;
	}

	/**
	 * 通讯录搜索（按地址本逻辑处理）
	 */
	@Override
	public List getDataList(RequestContext requestInfo) throws Exception {
		ArrayList rtnMapList = new ArrayList();
		String paramType = requestInfo.getParameter("paramType");
		if ("search".equals(paramType.toLowerCase())) {
			// 内部通讯录搜索逻辑
			return getDataListByKey(requestInfo);
		} else if ("fixed".equals(paramType.toLowerCase())) {
			// 固定人员
			String personType = requestInfo.getParameter("personType");
			String cateId = requestInfo.getParameter("cateId");
			if ("inner".equals(personType)) {
				String personId = requestInfo.getParameter("personId");
				boolean flag = false;
				try {
					SysZonePersonInfo personInfo = (SysZonePersonInfo) personInfoService.findByPrimaryKey(personId);
					rtnMapList.add(getPersonInfoMap(personInfo, requestInfo.getContextPath(), cateId));
				} catch (Exception e) {
					flag = true;
				}
				if (flag) {
					SysOrgPerson sysPerson = (SysOrgPerson) sysOrgPersonService.findByPrimaryKey(personId);
					rtnMapList.add(getPersonMap(sysPerson, requestInfo.getContextPath(), cateId));
				}
			} else if ("outer".equals(personType)) {
				// 外部伙伴sys_zone_org_outer
				String personId = requestInfo.getParameter("personId");
				SysZoneOrgOuter outer = (SysZoneOrgOuter) sysZoneOrgOuterService.findByPrimaryKey(personId);
				rtnMapList.add(getOuterPersonInfoMap(outer, requestInfo.getContextPath()));
			}
		}
		return rtnMapList;
	}

	/**
	 * 根据关键字搜索（地址本逻辑）
	 *
	 * @param requestInfo
	 * @return
	 * @throws Exception
	 */
	private List getDataListByKey(RequestContext requestInfo) throws Exception {
		long s1 = System.currentTimeMillis();
		List startList = null;
		ArrayList rtnMapList = new ArrayList();
		String[] keys = requestInfo.getParameter("searchWord").split("\\s*[,;，；]\\s*");
		if (log.isDebugEnabled()) {
			log.debug("根据关键字搜索通讯录，关键字：" + Arrays.toString(keys));
		}

		String modelTable = "sysOrgPerson";
		String authCheckType = "DIALOG_READER";

		// 模糊查询
		StringBuffer whereBf = new StringBuffer();
		HQLInfo hqlInfo = new HQLInfo();
		int i = 0;
		for (String key : keys) {
			if (StringUtil.isNull(key)) {
				continue;
			}
			whereBf.append(" or lower(" + modelTable + ".fdName) like :key" + i);
			whereBf.append(" or lower(" + modelTable + ".fdMobileNo) like :key" + i);
			whereBf.append(" or lower(" + modelTable + ".fdEmail) like :key" + i);
			whereBf.append(" or lower(" + modelTable
					+ ".fdLoginName) like :key" + i + " or lower(" + modelTable
					+ ".fdNickName) like :key" + i
					+ " or lower(fdStaffingLevel.fdName) like :key" + i);
			whereBf.append(" or " + modelTable + ".fdNamePinYin like :key" + i);
			// 简拼搜索
			whereBf.append(" or " + modelTable + ".fdNameSimplePinyin like :key" + i);
			String fdName_lang = SysLangUtil.getLangFieldName("fdName");
			if (!"fdName".equals(fdName_lang)) {
				whereBf.append(" or lower(" + modelTable + "." + fdName_lang + ") like :key" + i);
			}
			hqlInfo.setParameter("key" + i, "%" + key.toLowerCase() + "%");
			i++;
		}
		if (whereBf.length() == 0) {
			return new ArrayList();
		} else {
			hqlInfo.setJoinBlock(" left join " + modelTable + ".fdStaffingLevel fdStaffingLevel");
		}

		String where = whereBf.substring(4);
		buildSearchHQLInfo(hqlInfo, where, startList, ORG_TYPE_PERSON, modelTable);
		// 通讯录只能查询内部组织
		hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and ", "(" + modelTable + ".fdIsExternal is null or " + modelTable + ".fdIsExternal = :fdIsExternal)"));
		hqlInfo.setParameter("fdIsExternal", Boolean.FALSE);
		// 性能优化，这里只查询100条数据
		hqlInfo.setRowSize(101);
		hqlInfo.setCacheable(true);
		hqlInfo.setGetCount(false);
		if (!UserUtil.checkRole("ROLE_SYSORG_DIALOG_USER")) {
			hqlInfo.setAuthCheckType(authCheckType);
		}
		List elemList = sysOrgPersonService.findPage(hqlInfo).getList();
		Set<String> elementIds = sysOrganizationVisibleService
				.getPersonRootVisibleOrgIds(UserUtil.getUser());
		if (elementIds != null && !elementIds.isEmpty()) {
			HQLInfo postInfo = new HQLInfo();
			StringBuffer sb = new StringBuffer();
			int j = 0;
			for (String id : elementIds) {
				sb.append(" or sysOrgPost.fdHierarchyId like :key" + j);
				postInfo.setParameter("key" + j, "%" + id + "%");
				j++;
			}
			postInfo.setWhereBlock(sb.substring(4));
			List<SysOrgPost> postList = sysOrgPostService.findList(postInfo);
			for (SysOrgPost post : postList) {
				List<SysOrgPerson> fdPersons = post.getFdPersons();
				for (SysOrgPerson person : fdPersons) {
					if (BooleanUtils.isTrue(person.getFdIsExternal())) {
						continue;
					}
					boolean flag = false;
					for (String key : keys) {
						String fdName = person.getFdName();
						if (StringUtil.isNotNull(fdName)) {
                            flag = fdName.contains(key);
                        }
						if (flag) {
                            break;
                        }
						String fdMobile = person.getFdMobileNo();
						if (StringUtil.isNotNull(fdMobile)) {
                            flag = fdMobile.contains(key);
                        }
						if (flag) {
                            break;
                        }
						String fdEmail = person.getFdEmail();
						if (StringUtil.isNotNull(fdEmail)) {
                            flag = fdEmail.contains(key);
                        }
						if (flag) {
                            break;
                        }
						String fdLoginName = person.getFdLoginName();
						if (StringUtil.isNotNull(fdLoginName)) {
                            flag = fdLoginName.contains(key);
                        }
						if (flag) {
                            break;
                        }
						String fdNickName = person.getFdNickName();
						if (StringUtil.isNotNull(fdNickName)) {
                            flag = fdNickName.contains(key);
                        }
						if (flag) {
                            break;
                        }
						SysOrganizationStaffingLevel level = person
								.getFdStaffingLevel();
						if (level != null
								&& StringUtil.isNotNull(level.getFdName())) {
                            flag = level.getFdName().contains(key);
                        }
						if (flag) {
                            break;
                        }
					}
					if (!elemList.contains(person) && flag) {
                        elemList.add(person);
                    }
				}
			}
		}

		long s2 = System.currentTimeMillis();
		if (log.isDebugEnabled()) {
			log.debug("数据查询HQL=[" + hqlInfo.getWhereBlock() + "]，用时：" + (s2 - s1) + "毫秒");
		}
		elemList = sysOrganizationStaffingLevelService.getStaffingLevelFilterResult(elemList);
		long s3 = System.currentTimeMillis();
		if (log.isDebugEnabled()) {
			log.debug("数据过渡完成，用时：" + (s3 - s2) + "毫秒");
		}

		for (Object obj : elemList) {
			SysOrgPerson person = (SysOrgPerson) obj;
			HashMap<String, String> personInfoMap = new HashMap<String, String>();

			String img = PersonInfoServiceGetter.getPersonHeadimageUrl(person.getFdId());
			if (!PersonInfoServiceGetter.isFullPath(img)) {
				img = requestInfo.getContextPath() + img;
			}

			personInfoMap.put("personType", "inner");
			personInfoMap.put("personId", person.getFdId());
			personInfoMap.put("personName", StringEscapeUtils.escapeHtml(person.getFdName()));
			personInfoMap.put("personImg", img);
			personInfoMap.put("personSex", person.getFdSex());
			personInfoMap.put("parentsName", person.getFdParentsName());
			try {
				if (!SysZonePrivateUtil.isDepInfoPrivate(person.getFdId())) {
					// 列表数据暂时没有显示岗位信息
					personInfoMap.put("postName", "");
					String parentName = person.getFdParent() == null ? "" : person.getFdParent().getDeptLevelNames();
					personInfoMap.put("deptName", parentName);
					String staffingLevel = "";
					SysOrganizationStaffingLevel sysOrganizationStaffingLevel = person.getFdStaffingLevel();
					if (sysOrganizationStaffingLevel != null) {
						staffingLevel = sysOrganizationStaffingLevel.getFdName();
					}
					personInfoMap.put("staffingLevelName", staffingLevel);
				} else {
					personInfoMap.put("postName", ResourceUtil
							.getString("sysZonePerson.undisclosed2",
									"sys-zone"));
					personInfoMap.put("deptName", ResourceUtil
							.getString("sysZonePerson.undisclosed2",
									"sys-zone"));
					personInfoMap.put("staffingLevelName", ResourceUtil
							.getString("sysZonePerson.undisclosed2",
									"sys-zone"));
				}
			} catch (Exception e) {
				log.error("查询失败：", e);
			}

			rtnMapList.add(personInfoMap);
		}
		long s4 = System.currentTimeMillis();
		if (log.isDebugEnabled()) {
			log.debug("数据处理完成，用时：" + (s4 - s3) + "毫秒");
		}
		return rtnMapList;
	}

	/**
	 * 构造HQLInfo的where与order by
	 *
	 * @param whereBlock
	 * @param startList
	 * @param orgType
	 * @return
	 */
	private void buildSearchHQLInfo(HQLInfo hqlInfo, String whereBlock,
									List startList, int orgType, String modelTable) {
		if (startList != null) {
			whereBlock = SysOrgHQLUtil.buildAllChildrenWhereBlock(startList, whereBlock, modelTable);
		}
		whereBlock = SysOrgHQLUtil.buildWhereBlock(orgType, whereBlock, modelTable);
		hqlInfo.setWhereBlock(whereBlock);
		hqlInfo.setOrderBy(modelTable + ".fdOrgType desc, " + modelTable + ".fdOrder , " + modelTable + "."
				+ SysLangUtil.getLangFieldName("fdName"));
	}

	/**
	 * 原逻辑，有性能问题，已废弃
	 */
	@Deprecated
	public List getDataListBack(RequestContext requestInfo) throws Exception {
		ArrayList rtnMapList = new ArrayList();
		// ArrayList rtnList = new ArrayList();
		String paramType = requestInfo.getParameter("paramType");
		if ("search".equals(paramType.toLowerCase())) {
			// 搜索
			// 若每次新建人员后都同步，则后台逻辑将会简单很多
			String searchWord = requestInfo.getParameter("searchWord");
			// String personType = requestInfo.getParameter("personType");
			// #43349
			Set<String> elementIds = null;
			KMSSUser user = UserUtil.getKMSSUser();

			ValidatorRequestContext context = new ValidatorRequestContext();
			context.setUser(user);
			context.addValidatorParas("role=ROLE_SYSORG_DIALOG_USER");

			if (!roleValidator.validate(context)
					&& sysOrganizationVisibleService.isOrgVisibleEnable()) {
				// sysOrganizationVisibleService.updateCacheLocal();
				elementIds = sysOrganizationVisibleService.getPersonVisibleIds(UserUtil.getUser());
			}
			// 搜内部的
			HQLInfo searchInnerHQL = new HQLInfo();
			HQLInfo hql = new HQLInfo();
			HQLInfo hqlFlag = new HQLInfo();

			if (elementIds == null || elementIds.size() == 0
					|| (user != null && user.isAdmin())) {
				searchInnerHQL.setWhereBlock(
						"(person.fdLoginName like :searchWord or person.fdName like :searchWord or person.fdMobileNo like :searchWord or person.fdWorkPhone like :searchWord or person.fdEmail like :searchWord)");
				searchInnerHQL.setOrderBy(
						"person.fdOrgType desc," + "person."
								+ SysLangUtil.getLangFieldName("fdName"));
				hql.setWhereBlock(
						"(sysOrgPerson.fdLoginName like :searchWord or sysOrgPerson.fdName like :searchWord or sysOrgPerson.fdMobileNo like :searchWord or sysOrgPerson.fdWorkPhone like :searchWord or sysOrgPerson.fdEmail like :searchWord) and sysOrgPerson.fdIsAvailable =:fdIsAvailable and sysOrgPerson.fdIsBusiness =:fdIsBusiness");
				hql.setOrderBy(
						"sysOrgPerson.fdOrgType desc," + "sysOrgPerson."
								+ SysLangUtil.getLangFieldName("fdName"));
				searchInnerHQL.setCheckParam(SysAuthConstant.CheckType.AllCheck,
						SysAuthConstant.AllCheck.NO);
				hql.setCheckParam(SysAuthConstant.CheckType.AllCheck,
						SysAuthConstant.AllCheck.NO);
			} else {
				Set<String> fdIds = new HashSet<String>();
				List<SysOrgPerson> personFindList = new ArrayList();
				for (String str : elementIds) {
					hqlFlag.setWhereBlock(
							SysOrgHQLUtil.buildWhereBlock(ORG_TYPE_PERSON,
									"sysOrgPerson.fdHierarchyId like :hbmParentId",
									"sysOrgPerson"));
					hqlFlag.setParameter("hbmParentId", "%" + str + "%");
					personFindList = sysOrgPersonService
							.findList(hqlFlag);
					for (int i = 0; i < personFindList.size(); i++) {
						fdIds.add(personFindList.get(i).getFdId());
					}
				}
				String personVisibleIds="";
				if (fdIds != null) {
					personVisibleIds = SysOrgUtil.buildInBlock(fdIds);
				}

				String whereBlock = "";
				whereBlock = "(person.fdLoginName like :searchWord or person.fdName like :searchWord or person.fdMobileNo like :searchWord or person.fdWorkPhone like :searchWord or person.fdEmail like :searchWord)";
				whereBlock += " and person.fdId in(" + personVisibleIds + ")";
				searchInnerHQL.setWhereBlock(whereBlock);
				searchInnerHQL.setOrderBy(
						"person.fdOrgType desc," + "person."
								+ SysLangUtil.getLangFieldName("fdName"));
				// 组织架构表
				String OrgWhereBlock = "";
				OrgWhereBlock = "(sysOrgPerson.fdLoginName like :searchWord or sysOrgPerson.fdName like :searchWord or sysOrgPerson.fdMobileNo like :searchWord or sysOrgPerson.fdWorkPhone like :searchWord or sysOrgPerson.fdEmail like :searchWord) and sysOrgPerson.fdIsAvailable =:fdIsAvailable and sysOrgPerson.fdIsBusiness =:fdIsBusiness";
				OrgWhereBlock += " and sysOrgPerson.fdId in("
						+ personVisibleIds + ")";
				hql.setWhereBlock(OrgWhereBlock);
				hql.setOrderBy("sysOrgPerson.fdOrgType desc," + "sysOrgPerson."
						+ SysLangUtil.getLangFieldName("fdName"));

				searchInnerHQL.setCheckParam(SysAuthConstant.CheckType.AuthCheck, "DIALOG_READER");
				hql.setCheckParam(SysAuthConstant.CheckType.AuthCheck, "DIALOG_READER");
			}
			searchInnerHQL.setParameter("searchWord", "%" + searchWord + "%");
			// 性能优化，这里只查询100条数据
			searchInnerHQL.setRowSize(101);
			searchInnerHQL.setCacheable(true);
			searchInnerHQL.setGetCount(false);
			List<SysZonePersonInfo> findInnerInfoPersons = personInfoService.findPage(searchInnerHQL).getList();
			// 获取符合查询条件的组织架构人员列表
			hql.setParameter("searchWord", "%" + searchWord + "%");
			hql.setParameter("fdIsAvailable", true);
			hql.setParameter("fdIsBusiness", true);
			// 性能优化，这里只查询100条数据
			hql.setRowSize(101);
			hql.setCacheable(true);
			hql.setGetCount(false);
			List<SysOrgPerson> findInnerPersons = sysOrgPersonService.findPage(hql).getList();
			// #42381
			// 判断SysZonePersonInfo和SysOrgPerson中人员个数是否相等
			if (findInnerPersons.size() == findInnerInfoPersons.size()) {
				// 职级过滤
				if (sysOrganizationStaffingLevelService
						.isStaffingLevelFilter()) {
					hql = sysOrganizationStaffingLevelService
							.getPersonStaffingLevelFilterHQLInfo(hql);
					List<SysOrgPerson> findInnerPersonsLevel = sysOrgPersonService
							.findPage(hql).getList();
					// 根据组织架构人员Id去找SysZonePersonInfo中的人员
					List<String> personId = new ArrayList<String>();
					for (int i = 0; i < findInnerPersonsLevel.size(); i++) {
						personId.add(findInnerPersonsLevel.get(i).getFdId());
					}
					String[] infoPerson = new String[personId.size()];
					String[] personInfoArrayIds = (String[]) personId
							.toArray(infoPerson);
					List<SysZonePersonInfo> findLevelInfoPersons;
					try {
						findLevelInfoPersons = personInfoService
								.findByPrimaryKeys(personInfoArrayIds);
						for (SysZonePersonInfo item : findLevelInfoPersons) {
							if (item.getPerson().getFdLoginName()
									.startsWith("admin")) {
								continue;
							}
							rtnMapList.add(getPersonInfoMap(item,
									requestInfo.getContextPath(), ""));
						}
					} catch (Exception e) {
						e.printStackTrace();
					}


				} else {
					// 未开启职级过滤
					// 首先判断无效人员和新建人员个数是否相等
					List<String> personInfoId = new ArrayList<String>();
					List<String> personId = new ArrayList<String>();
					List<SysZonePersonInfo> backupFindInnerInfoPersons = new ArrayList();
					for (int i = 0; i < findInnerInfoPersons.size(); i++) {
						personInfoId.add(findInnerInfoPersons.get(i).getFdId());
					}
					for (int i = 0; i < findInnerPersons.size(); i++) {
						personId.add(findInnerPersons.get(i).getFdId());
					}
					for (int i = 0; i < personInfoId.size(); i++) {
						for (int j = 0; j < personId.size(); j++) {
							if (personId.get(j).equals(personInfoId.get(i))) {
								personId.remove(j);
								// 把findInnerInfoPersons的值添加到中间对象去
								backupFindInnerInfoPersons
										.add(findInnerInfoPersons.get(i));
							}
						}
					}
					if (personId.size() == 0) {
						// 搜索中的结果中新建人员和无效人员不存在时
						for (SysZonePersonInfo item : findInnerInfoPersons) {
							if (item.getPerson().getFdLoginName()
									.startsWith("admin")) {
								continue;
							}
							rtnMapList.add(getPersonInfoMap(item,
									requestInfo.getContextPath(), ""));
						}
					} else {
						// 新建人员和无效人员个数相等时
						for (SysZonePersonInfo item : backupFindInnerInfoPersons) {
							if (item.getPerson().getFdLoginName()
									.startsWith("admin")) {
								continue;
							}
							rtnMapList.add(getPersonInfoMap(item,
									requestInfo.getContextPath(), ""));
						}
						//新建未同步人员
						String[] person = new String[personId.size()];
						String[] personArrayIds = (String[]) personId
								.toArray(person);
						List<SysOrgPerson> newFindPersons = sysOrgPersonService
								.findByPrimaryKeys(personArrayIds);
						for (SysOrgPerson item : newFindPersons) {
							if (item.getFdLoginName().startsWith("admin")) {
								continue;
							}
							rtnMapList.add(getPersonMap(item,
									requestInfo.getContextPath(), ""));
						}
					}
				}
			} else if (findInnerPersons.size() > findInnerInfoPersons.size()) {
				// #42381

				/*
				 * 先找出SysZonePersonInfo中的人员id，然后找出SysOrgPerson中人员id，
				 * 若SysOrgPerson中id包含SysZonePersonInfo中id，则删除。
				 *
				 */
				if (sysOrganizationStaffingLevelService
						.isStaffingLevelFilter()) {
					hql = sysOrganizationStaffingLevelService
							.getPersonStaffingLevelFilterHQLInfo(hql);
					List<SysOrgPerson> findInnerPersonsLevel = sysOrgPersonService
							.findPage(hql).getList();
					// 根据组织架构人员Id去找SysZonePersonInfo中的人员
					List<String> personId = new ArrayList<String>();
					for (int i = 0; i < findInnerPersonsLevel.size(); i++) {
						personId.add(findInnerPersonsLevel.get(i).getFdId());
					}
					String[] infoPerson = new String[personId.size()];
					String[] personInfoArrayIds = (String[]) personId
							.toArray(infoPerson);
					List<SysZonePersonInfo> findLevelInfoPersons;
					// 暂不考虑新建但未同步的人员
					try {
						findLevelInfoPersons = personInfoService
								.findByPrimaryKeys(personInfoArrayIds);
						for (SysZonePersonInfo item : findLevelInfoPersons) {
							if (item.getPerson().getFdLoginName()
									.startsWith("admin")) {
								continue;
							}
							rtnMapList.add(getPersonInfoMap(item,
									requestInfo.getContextPath(), ""));
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					// 未开启职级过滤
					List<String> personInfoId = new ArrayList<String>();
					List<String> personId = new ArrayList<String>();
					List<SysZonePersonInfo> backupFindInnerInfoPersons = new ArrayList();
					for (int i = 0; i < findInnerInfoPersons.size(); i++) {
						personInfoId.add(findInnerInfoPersons.get(i).getFdId());
					}
					for (int i = 0; i < findInnerPersons.size(); i++) {
						personId.add(findInnerPersons.get(i).getFdId());
					}
					for (int i = 0; i < personInfoId.size(); i++) {
						for (int j = 0; j < personId.size(); j++) {
							if (personId.get(j).equals(personInfoId.get(i))) {
								personId.remove(j);
								backupFindInnerInfoPersons
										.add(findInnerInfoPersons.get(i));
							}
						}
					}
					// String[] infoPerson = new String[personInfoId.size()];
					// String[] personInfoArrayIds = (String[]) personInfoId
					// .toArray(infoPerson);
					String[] person = new String[personId.size()];
					String[] personArrayIds = (String[]) personId
							.toArray(person);
					// List<SysZonePersonInfo> findInfoPersons =
					// personInfoService
					// .findByPrimaryKeys(personInfoArrayIds);
					for (SysZonePersonInfo item : backupFindInnerInfoPersons) {
						if (item.getPerson().getFdLoginName()
								.startsWith("admin")) {
							continue;
						}
						rtnMapList.add(getPersonInfoMap(item,
								requestInfo.getContextPath(), ""));
					}
					List<SysOrgPerson> findPersons = sysOrgPersonService
							.findByPrimaryKeys(personArrayIds);
					for (SysOrgPerson item : findPersons) {
						if (item.getFdLoginName().startsWith("admin")) {
							continue;
						}
						rtnMapList.add(getPersonMap(item,
								requestInfo.getContextPath(), ""));
					}
				}
			} else {
				// 此种情况是SysZonePersonInfo中有无效的人员，只要拿组织架构中有效人员的id去取SysZonePersonInfo中数据即可
				if (sysOrganizationStaffingLevelService
						.isStaffingLevelFilter()) {
					hql = sysOrganizationStaffingLevelService
							.getPersonStaffingLevelFilterHQLInfo(hql);
					List<SysOrgPerson> findInnerPersonsLevel = sysOrgPersonService
							.findPage(hql).getList();
					// 根据组织架构人员Id去找SysZonePersonInfo中的人员
					List<String> personId = new ArrayList<String>();
					for (int i = 0; i < findInnerPersonsLevel.size(); i++) {
						personId.add(findInnerPersonsLevel.get(i).getFdId());
					}
					String[] infoPerson = new String[personId.size()];
					String[] personInfoArrayIds = (String[]) personId
							.toArray(infoPerson);
					List<SysZonePersonInfo> findLevelInfoPersons = personInfoService
							.findByPrimaryKeys(personInfoArrayIds);
					for (SysZonePersonInfo item : findLevelInfoPersons) {
						if (item.getPerson().getFdLoginName()
								.startsWith("admin")) {
							continue;
						}
						rtnMapList.add(getPersonInfoMap(item,
								requestInfo.getContextPath(), ""));
					}
				} else {
					// 未开启职级过滤
					/////////
					List<String> personInfoId = new ArrayList<String>();
					List<String> personId = new ArrayList<String>();
					List<SysZonePersonInfo> backupFindInnerInfoPersons = new ArrayList();
					for (int i = 0; i < findInnerInfoPersons.size(); i++) {
						personInfoId.add(findInnerInfoPersons.get(i).getFdId());
					}
					for (int i = 0; i < findInnerPersons.size(); i++) {
						personId.add(findInnerPersons.get(i).getFdId());
					}
					for (int i = 0; i < personInfoId.size(); i++) {
						for (int j = 0; j < personId.size(); j++) {
							if (personId.get(j).equals(personInfoId.get(i))) {
								personId.remove(j);
								backupFindInnerInfoPersons
										.add(findInnerInfoPersons.get(i));
							}
						}
					}
					String[] person = new String[personId.size()];
					String[] personArrayIds = (String[]) personId
							.toArray(person);
					for (SysZonePersonInfo item : backupFindInnerInfoPersons) {
						if (item.getPerson().getFdLoginName()
								.startsWith("admin")) {
							continue;
						}
						rtnMapList.add(getPersonInfoMap(item,
								requestInfo.getContextPath(), ""));
					}
					List<SysOrgPerson> findPersons = sysOrgPersonService
							.findByPrimaryKeys(personArrayIds);
					for (SysOrgPerson item : findPersons) {
						if (item.getFdLoginName().startsWith("admin")) {
							continue;
						}
						rtnMapList.add(getPersonMap(item,
								requestInfo.getContextPath(), ""));
					}
					/////////
				}
			}
			// 搜外部的（sys_zone_org_outer)
			HQLInfo searchOuterHQL = new HQLInfo();
			searchOuterHQL.setWhereBlock(
					"sysZoneOrgOuter.fdName like :searchWord or sysZoneOrgOuter.fdMobileNo like :searchWord or sysZoneOrgOuter.fdWorkPhone like :searchWord or sysZoneOrgOuter.fdEmail like :searchWord ");
			searchOuterHQL.setParameter("searchWord", "%" + searchWord + "%");
			// 性能优化，这里只查询100条数据
			searchOuterHQL.setRowSize(101);
			searchOuterHQL.setCacheable(true);
			searchOuterHQL.setGetCount(false);
			List<SysZoneOrgOuter> outerInnerPersons = sysZoneOrgOuterService
					.findPage(searchOuterHQL).getList();
			for (SysZoneOrgOuter item : outerInnerPersons) {
				rtnMapList.add(getOuterPersonInfoMap(item,
						requestInfo.getContextPath()));
			}
		} else if ("fixed".equals(paramType.toLowerCase())) {
			// 固定人员
			String personType = requestInfo.getParameter("personType");
			String cateId = requestInfo.getParameter("cateId");
			if ("inner".equals(personType)) {
				String personId = requestInfo.getParameter("personId");
				boolean flag = false;
				try {
					SysZonePersonInfo personInfo = (SysZonePersonInfo) personInfoService
							.findByPrimaryKey(personId);
					rtnMapList.add(getPersonInfoMap(personInfo,
							requestInfo.getContextPath(), cateId));
				} catch (Exception e) {
					flag = true;
					// e.printStackTrace();
				}
				if (flag) {
					SysOrgPerson sysPerson = (SysOrgPerson) sysOrgPersonService
							.findByPrimaryKey(personId);
					rtnMapList.add(getPersonMap(sysPerson,
							requestInfo.getContextPath(), cateId));
				}
			} else if ("outer".equals(personType)) {
				// 外部伙伴sys_zone_org_outer
				String personId = requestInfo.getParameter("personId");
				SysZoneOrgOuter outer = (SysZoneOrgOuter) sysZoneOrgOuterService
						.findByPrimaryKey(personId);
				rtnMapList.add(getOuterPersonInfoMap(outer,
						requestInfo.getContextPath()));
			}
		}
		return rtnMapList;
	}

	// #42381 新建人员未进行个人设置且未同步
	private Map getPersonMap(SysOrgPerson sysPersonInfo, String ctxPath,
							 String cateId) {
		Map personMap = new HashMap();
		personMap.put("personId", sysPersonInfo.getFdId());
		personMap.put("personName", StringEscapeUtils.escapeHtml(sysPersonInfo.getFdName()));
		String personImg = PersonInfoServiceGetter
				.getPersonHeadimageUrl(sysPersonInfo.getFdId());
		if (!PersonInfoServiceGetter.isFullPath(personImg)) {
			personImg = ctxPath + personImg;
		}
		personMap.put("personImg", personImg);
		personMap.put("personSex", sysPersonInfo.getFdSex());
		personMap.put("parentsName", sysPersonInfo.getFdParentsName());
		try {
			if (!SysZonePrivateUtil.isContactPrivate(sysPersonInfo.getFdId())) {
				personMap.put("phoneNo",
						StringUtil.isNotNull(sysPersonInfo.getFdMobileNo())
								? sysPersonInfo.getFdMobileNo() : "");
				personMap.put("officeNo",
						StringUtil.isNotNull(sysPersonInfo.getFdWorkPhone())
								? sysPersonInfo.getFdWorkPhone() : "");
				personMap.put("email",
						StringUtil.isNotNull(sysPersonInfo.getFdEmail())
								? sysPersonInfo.getFdEmail() : "");
			} else {
				personMap.put("phoneNo", ResourceUtil
						.getString("sysZonePerson.undisclosed2", "sys-zone"));
				personMap.put("officeNo", ResourceUtil
						.getString("sysZonePerson.undisclosed2", "sys-zone"));
				personMap.put("email", ResourceUtil
						.getString("sysZonePerson.undisclosed2", "sys-zone"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		StringBuffer personPost = new StringBuffer();
		List<SysOrgPost> personPosts = sysPersonInfo.getFdPosts();
		for (SysOrgPost post : personPosts) {
			personPost.append(";").append(post.getFdName());
		}
		if (personPost.length() > 0) {
			personPost.deleteCharAt(0);
		}
		try {
			if (!SysZonePrivateUtil.isDepInfoPrivate(sysPersonInfo.getFdId())) {
				personMap.put("postName", personPost.toString());
				// 获取relation表
				HQLInfo relationHQL = new HQLInfo();
				relationHQL.setWhereBlock(
						"sysZoneOrgRelation.fdOrgId=:fdOrgId and sysZoneOrgRelation.fdCategoryId=:fdCategoryId");
				relationHQL.setParameter("fdOrgId", sysPersonInfo.getFdId());
				// 怎样获取类别cateId
				relationHQL.setParameter("fdCategoryId", cateId);
				SysZoneOrgRelation obj = (SysZoneOrgRelation)sysZoneOrgRelationService
						.findFirstOne(relationHQL);
				if (obj!=null) {
					personMap.put("signatureName",
							StringUtil
									.isNotNull(
											obj.getFdOrgMemo())
									? obj
									.getFdOrgMemo()
									: "");
					personMap.put("finder", "finder");
				} else {
					personMap.put("signatureName", "");
				}
				personMap.put("deptName", sysPersonInfo.getFdParentsName("_"));
				personMap.put("staffingLevelName",
						sysPersonInfo.getFdStaffingLevel() == null
								? ""
								: sysPersonInfo.getFdStaffingLevel()
								.getFdName());
			} else {
				personMap.put("postName", ResourceUtil
						.getString("sysZonePerson.undisclosed2", "sys-zone"));
				// #43227
				// 获取relation表
				HQLInfo relationHQL = new HQLInfo();
				relationHQL.setWhereBlock(
						"sysZoneOrgRelation.fdOrgId=:fdOrgId and sysZoneOrgRelation.fdCategoryId=:fdCategoryId");
				relationHQL.setParameter("fdOrgId", sysPersonInfo.getFdId());
				// 怎样获取类别cateId
				relationHQL.setParameter("fdCategoryId", cateId);
				SysZoneOrgRelation relationNew = (SysZoneOrgRelation)sysZoneOrgRelationService
						.findFirstOne(relationHQL);
				if (relationNew != null) {
					personMap.put("signatureName",
							StringUtil
									.isNotNull(
											relationNew.getFdOrgMemo())
									? relationNew
									.getFdOrgMemo()
									: "");
					personMap.put("finder", "finder");
				} else {
					personMap.put("signatureName",
							StringUtil.isNotNull(sysPersonInfo.getFdMemo())
									? sysPersonInfo.getFdMemo() : "");
				}
				personMap.put("deptName", ResourceUtil
						.getString("sysZonePerson.undisclosed2", "sys-zone"));
				personMap.put("staffingLevelName",
						sysPersonInfo.getFdStaffingLevel() == null
								? ""
								: sysPersonInfo.getFdStaffingLevel()
								.getFdName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		personMap.put("personType", "inner");
		return personMap;
	}

	// 签名信息要到SysZonePersonInfo里取
	private Map getPersonInfoMap(SysZonePersonInfo zonePersonInfo,
								 String ctxPath, String cateId) {
		SysOrgPerson person = zonePersonInfo.getPerson();
		Map personInfoMap = new HashMap();
		personInfoMap.put("personId", person.getFdId());
		personInfoMap.put("personName",
				StringUtil.XMLEscape(person.getFdName()));
		String personImg = PersonInfoServiceGetter
				.getPersonHeadimageUrl(person.getFdId());
		if (!PersonInfoServiceGetter.isFullPath(personImg)) {
			personImg = ctxPath + personImg;
		}
		personInfoMap.put("personImg", personImg);
		personInfoMap.put("personSex", person.getFdSex());
		try {
			if (!SysZonePrivateUtil.isContactPrivate(person.getFdId())) {
				personInfoMap.put("phoneNo",
						StringUtil.isNotNull(person.getFdMobileNo())
								? person.getFdMobileNo() : "");
				personInfoMap.put("officeNo",
						StringUtil.isNotNull(person.getFdWorkPhone())
								? person.getFdWorkPhone() : "");
				personInfoMap.put("shortNo",
						StringUtil.isNotNull(person.getFdShortNo())
								? person.getFdShortNo() : "");
				personInfoMap.put("email",
						StringUtil.isNotNull(person.getFdEmail())
								? person.getFdEmail() : "");
			} else {
				personInfoMap.put("phoneNo", ResourceUtil
						.getString("sysZonePerson.undisclosed2", "sys-zone"));
				personInfoMap.put("officeNo", ResourceUtil
						.getString("sysZonePerson.undisclosed2", "sys-zone"));
				personInfoMap.put("shortNo", ResourceUtil
						.getString("sysZonePerson.undisclosed2", "sys-zone"));
				personInfoMap.put("email", ResourceUtil
						.getString("sysZonePerson.undisclosed2", "sys-zone"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		StringBuffer personPost = new StringBuffer();
		List<SysOrgPost> personPosts = person.getFdPosts();
		for (SysOrgPost post : personPosts) {
			personPost.append(";").append(post.getFdName());
		}
		if (personPost.length() > 0) {
			personPost.deleteCharAt(0);
		}
		try {
			if (!SysZonePrivateUtil.isDepInfoPrivate(person.getFdId())) {
				personInfoMap.put("postName", personPost.toString());
				// 获取relation表
				HQLInfo relaHQL = new HQLInfo();
				relaHQL.setWhereBlock(
						"sysZoneOrgRelation.fdOrgId=:fdOrgId and sysZoneOrgRelation.fdCategoryId=:fdCategoryId");
				relaHQL.setParameter("fdOrgId", person.getFdId());
				// 怎样获取类别cateId
				relaHQL.setParameter("fdCategoryId", cateId);
				SysZoneOrgRelation relation = (SysZoneOrgRelation)sysZoneOrgRelationService
						.findFirstOne(relaHQL);
				if (relation != null) {
					personInfoMap.put("signatureName",
							StringUtil
									.isNotNull(relation.getFdOrgMemo())
									? relation.getFdOrgMemo()
									: "");
					personInfoMap.put("finder", "finder");
				} else {
					personInfoMap.put("signatureName",
							StringUtil
									.isNotNull(zonePersonInfo.getFdSignature())
									? zonePersonInfo.getFdSignature()
									: "");
				}
				personInfoMap.put("deptName", person.getFdParentsName("_"));
				personInfoMap.put("staffingLevelName",
						person.getFdStaffingLevel() == null
								? ""
								: person.getFdStaffingLevel()
								.getFdName());
			} else {
				personInfoMap.put("postName", ResourceUtil
						.getString("sysZonePerson.undisclosed2", "sys-zone"));
				// #43227
				// 获取relation表
				HQLInfo relaHQL = new HQLInfo();
				relaHQL.setWhereBlock(
						"sysZoneOrgRelation.fdOrgId=:fdOrgId and sysZoneOrgRelation.fdCategoryId=:fdCategoryId");
				relaHQL.setParameter("fdOrgId", person.getFdId());
				// 怎样获取类别cateId
				relaHQL.setParameter("fdCategoryId", cateId);
				SysZoneOrgRelation relation = (SysZoneOrgRelation)sysZoneOrgRelationService
						.findFirstOne(relaHQL);
				if (relation != null) {
					personInfoMap.put("signatureName",
							StringUtil
									.isNotNull(relation.getFdOrgMemo())
									? relation.getFdOrgMemo()
									: "");
					personInfoMap.put("finder", "finder");
				} else {
					personInfoMap.put("signatureName",
							StringUtil.isNotNull(zonePersonInfo.getFdSignature())
									? zonePersonInfo.getFdSignature() : "");
				}
				personInfoMap.put("deptName", ResourceUtil
						.getString("sysZonePerson.undisclosed2", "sys-zone"));
				personInfoMap.put("staffingLevelName",
						person.getFdStaffingLevel() == null
								? ""
								: person.getFdStaffingLevel()
								.getFdName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		personInfoMap.put("personType", "inner");
		return personInfoMap;
	}

	private Map getOuterPersonInfoMap(SysZoneOrgOuter outerPersonInfo,
									  String ctxPath) {
		Map personInfoMap = new HashMap();
		personInfoMap.put("personId", outerPersonInfo.getFdId());
		personInfoMap.put("personName",
				StringUtil.XMLEscape(outerPersonInfo.getFdName()));
		personInfoMap.put("postName", outerPersonInfo.getFdPostDesc());
		personInfoMap.put("personMobileNo", outerPersonInfo.getFdMobileNo());
		personInfoMap.put("personWorkPhone", outerPersonInfo.getFdWorkPhone());
		personInfoMap.put("personEmail", outerPersonInfo.getFdEmail());
		personInfoMap.put("personMemo", outerPersonInfo.getFdMemo());
		personInfoMap.put("personType", "outer");
		return personInfoMap;
	}
}
