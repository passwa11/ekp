package com.landray.kmss.sys.organization.service.spring;
import java.util.*;

import com.landray.kmss.sys.organization.eco.AuthOrgRange;
import com.landray.kmss.sys.organization.eco.IOrgRangeService;
import com.landray.kmss.util.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hibernate.Session;
import org.springframework.transaction.TransactionStatus;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.service.ISysOrgChartService;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.sunbor.web.tag.Page;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class SysOrgChartServiceImp extends BaseServiceImp implements SysOrgConstant, ISysOrgChartService {
	protected static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysOrgChartServiceImp.class);
	private static final String EMP_IMG_URL = "/sys/person/image.jsp?personId=%s&size=s&s_time=%s";

	private ISysOrgElementService sysOrgElementService;

	public void setSysOrgElementService(ISysOrgElementService sysOrgElementService) {
		this.sysOrgElementService = sysOrgElementService;
	}

	private IOrgRangeService orgRangeService;

	public IOrgRangeService getOrgRangeService() {
		if (orgRangeService == null) {
			orgRangeService = (IOrgRangeService) SpringBeanUtil.getBean("orgRangeService");
		}
		return orgRangeService;
	}

	@Override
	public String getChartData(String fdId, boolean isFirstTimeLoad, int expandLevel) throws Exception {
		JSONObject jsonObj = new JSONObject();
		JSONArray jsonArr = new JSONArray();

		int depth = 1; // 位于当前展示界面的层级
		SysOrgElement element = (SysOrgElement) sysOrgElementService.findByPrimaryKey(fdId);
		if (element.getFdOrgType() == ORG_TYPE_ORG || element.getFdOrgType() == ORG_TYPE_DEPT) {
			createChartData(jsonArr, element, depth, expandLevel);
			if(isFirstTimeLoad){
				int initMaxLevel = 1;
				int maxLevel = getMaxOrgLevel(element, depth, initMaxLevel);
				jsonObj.put("maxLevel", maxLevel);
			}
		}
		if (UserOperHelper.allowLogOper("GetAllList", "*")) {
			UserOperHelper.setModelNameAndModelDesc(sysOrgElementService.getModelName(), ResourceUtil.getString("sys-organization:module.sys.organization"));
			UserOperContentHelper.putFind(element);
		}

		jsonObj.put("d", jsonArr);
		return jsonObj.toString();
	}

	@SuppressWarnings("unchecked")
	private void createChartData(JSONArray array, SysOrgElement element, int depth, int expandLevel) throws Exception {
		int OrganizationalType = element.getFdOrgType(); // 组织架构类型
		String OrganizationalID = element.getFdId(); // 部门ID
		String OrganizationalName = element.getFdName(); // 部门名称
		String OrganizationalNameAll = element.getFdName(); // 部门名称（鼠标指向展示内容）

		String ParentOrganizationalID = StringUtils.EMPTY; // 部门的父级部门ID
		String ParentOrganizationalName = StringUtils.EMPTY; // 部门的父级部门名称
		SysOrgElement parent = element.getFdParent();
		if (parent != null) {
			ParentOrganizationalID = parent.getFdId();
			ParentOrganizationalName = parent.getFdName();
		}

		int DirectorType = 8; // 部门领导的组织架构类型
		int DirectorNumber = 1; // 部门或机构领导人的数量
		String DirectorEmpID = StringUtils.EMPTY; // 部门的领导ID
		String DirectorName = ResourceUtil.getString("sys-organization:sysOrgElement.leaderNull"); // 暂无领导
		Map<String, Object> PositionMap = new HashMap<String, Object>(); // 岗位信息
		JSONArray SiblingDirectors = null; // 并列领导
		String empImgUrl = "/sys/person/image.jsp";// 部门主管头像

		SysOrgElement leader = element.getHbmThisLeader();// 部门领导或机构领导
		int leaderType = 1; // 领导类型(1:部门领导,2:上级领导)
		if (leader == null) {
			// 当部门领导为空时获取上级领导
			leader = element.getHbmSuperLeader();
			if (leader != null) {
                leaderType = 2;
            }
		}
		if (leader != null) {
			DirectorType = leader.getFdOrgType();
			// 如果直接是人，则只显示当前领导人信息
			if (ORG_TYPE_PERSON == DirectorType) {
				DirectorType = leader.getFdOrgType();
				DirectorEmpID = leader.getFdId();
				DirectorName = leader.getFdName();
				empImgUrl = String.format(EMP_IMG_URL, DirectorEmpID, System.currentTimeMillis());
				PositionMap = getLeaderPositionMap(leader);
			} else if (ORG_TYPE_POST == DirectorType) {
				// 如果是岗位，则获取岗位下的所有人，并且默认显示第一个领导人信息
				List<SysOrgElement> hbmPersons = leader.getHbmPersons();
				if (hbmPersons != null && hbmPersons.size() > 0) {
					DirectorNumber = hbmPersons.size();
					for (int i = 0; i < DirectorNumber; i++) {
						SysOrgElement leaderItem = hbmPersons.get(i);
						if (i == 0) {
							DirectorEmpID = leaderItem.getFdId();
							DirectorName = leaderItem.getFdName();
							empImgUrl = String.format(EMP_IMG_URL, DirectorEmpID, System.currentTimeMillis());
							PositionMap = getLeaderPositionMap(leaderItem);
						} else {
							if (SiblingDirectors == null) {
                                SiblingDirectors = new JSONArray();
                            }
							JSONObject siblingDirector = new JSONObject();
							siblingDirector.put("DirectorEmpID", leaderItem.getFdId());
							siblingDirector.put("DirectorName", leaderItem.getFdName());
							siblingDirector.put("empImgUrl",
									String.format(EMP_IMG_URL, leaderItem.getFdId(), System.currentTimeMillis()));
							siblingDirector.putAll(getLeaderPositionMap(leaderItem));
							SiblingDirectors.add(siblingDirector);
						}
					}
				}
			}
		} else {
			PositionMap = getLeaderPositionMap(leader);
		}

		int EmpCount = 0; // 部门人员数量
		if (element.getFdPersonsNumber() != null) {
			EmpCount = element.getFdPersonsNumber();
		}

		int ChildCount = 0; // 子部门数量

		// 查询子机构或部门
		StringBuilder whereBlock = new StringBuilder();
		whereBlock.append(" sysOrgElement.hbmParent = '").append(element.getFdId())
				.append("' and sysOrgElement.fdOrgType in (").append(ORG_TYPE_ORG).append(",")
				.append(ORG_TYPE_DEPT).append(") and sysOrgElement.fdIsAvailable = :fdIsAvailable");
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock(whereBlock.toString());
		hqlInfo.setParameter("fdIsAvailable", Boolean.TRUE);
		hqlInfo.setOrderBy("sysOrgElement.fdOrgType desc, sysOrgElement.fdOrder, sysOrgElement." + SysLangUtil.getLangFieldName("fdName"));
		hqlInfo.setExternal(false);
		// 如果是机构管理员 或 使用所有组织 ，不需要过滤权限
		if (UserUtil.checkRole("ROLE_SYSORG_ORG_ADMIN") || UserUtil.checkRole("ROLE_SYSORG_DIALOG_USER")) {
			hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck, SysAuthConstant.AllCheck.NO);
		} else {
			hqlInfo.setCheckParam(SysAuthConstant.CheckType.AuthCheck, "DIALOG_READER");
		}
		List<SysOrgElement> childrens = sysOrgElementService.findList(hqlInfo);
		
		if (!ArrayUtil.isEmpty(childrens)) {
			ChildCount = childrens.size();
			int childrenDepth = depth + 1;
			if(childrenDepth<=expandLevel){
				for (SysOrgElement children : childrens) {
					createChartData(array, children, childrenDepth, expandLevel);
				}
			}
		}

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("OrganizationalType", OrganizationalType);
		jsonObject.put("OrganizationalID", OrganizationalID);
		jsonObject.put("OrganizationalName", OrganizationalName);
		jsonObject.put("OrganizationalNameAll", OrganizationalNameAll);
		jsonObject.put("ParentOrganizationalID", ParentOrganizationalID);
		jsonObject.put("ParentOrganizationalName", ParentOrganizationalName);
		jsonObject.put("DirectorType", DirectorType);
		jsonObject.put("DirectorEmpID", DirectorEmpID);
		jsonObject.put("DirectorName", DirectorName);
		jsonObject.put("DirectorNumber", DirectorNumber);
		jsonObject.putAll(PositionMap);
		jsonObject.put("empImgUrl", empImgUrl);
		jsonObject.put("EmpCount", EmpCount);
		jsonObject.put("EmpCountUnit", ResourceUtil.getString("sys-organization:sysOrgElement.orgChart.person"));
		jsonObject.put("ChildCount", ChildCount);
		jsonObject.put("leaderType", leaderType);
		jsonObject.put("depth", depth);
		// 多个领导人
		if (SiblingDirectors != null) {
			jsonObject.put("SiblingDirectors", SiblingDirectors);
		}
		array.add(jsonObject);
	}

	/**
	 * 获取领导的岗位信息
	 * 
	 * @param leader
	 * @return
	 */
	private Map<String, Object> getLeaderPositionMap(SysOrgElement leader) {
		Map<String, Object> postMap = new HashMap<String, Object>();
		postMap.put("PositionName", ResourceUtil.getString("sys-organization:sysOrgElement.postNull"));
		postMap.put("PositionId", StringUtils.EMPTY);

		if (leader != null && ORG_TYPE_PERSON == leader.getFdOrgType()) {
			@SuppressWarnings("unchecked")
			List<SysOrgElement> hbmPosts = leader.getHbmPosts();
			if (hbmPosts != null && hbmPosts.size() > 0) {
				// 显示领导人的第一个岗位
				SysOrgElement firstPost = hbmPosts.get(0);
				postMap.put("PositionName", firstPost.getFdName());
				postMap.put("PositionId", firstPost.getFdId());
			}
		}

		return postMap;
	}

	@Override
	public void countPersonsNumber() throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("fdId, fdHierarchyId");
		String whereBlock = " fdOrgType = " + ORG_TYPE_DEPT + " or fdOrgType = " + ORG_TYPE_ORG;
		hqlInfo.setWhereBlock(whereBlock);
		hqlInfo.setRowSize(200);
		hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck, SysAuthConstant.AllCheck.NO);
		int pageNo = 1;
		while (true) {
			hqlInfo.setPageNo(pageNo);
			Page page = sysOrgElementService.findPage(hqlInfo);
			if (logger.isDebugEnabled()) {
				logger.debug("总数量：" + page.getTotalrows() + "，正在处理第" + pageNo + "/" + page.getTotal() + "页数据");
			}
			List<Object[]> list = page.getList();

			// 开启手动事务进行更新
			TransactionStatus status = null;
			try {
				status = TransactionUtils.beginNewTransaction();
				Session session = sysOrgElementService.getBaseDao().getHibernateSession();
				for (Object[] objs : list) {
					String fdId = objs[0].toString();
					String fdHierarchyId = objs[1].toString();
					// 获取数量
					long count = getPersonCountByOrgDept(fdHierarchyId);
					// 更新数量
					session.createNativeQuery("update sys_org_element set fd_persons_number = ? where fd_id = ?")
							.setParameter(0, count).setParameter(1, fdId).executeUpdate();
				}
				TransactionUtils.commit(status);
			} catch (Exception e) {
				if (status != null) {
					TransactionUtils.rollback(status);
				}
				logger.error("更新人员数量失败：", e);
			}
			if (pageNo >= page.getTotal()) {
				break;
			}
			pageNo++;
		}
	}
	
	public long getPersonCountByOrgDept(String hierarchyId) {
		if ("0".equals(hierarchyId)) {
			// 失效组织显示数目为0
			return 0;
		}
		long count = 0;
		try {
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setSelectBlock("count(*)");
			hqlInfo.setWhereBlock("sysOrgElement.fdOrgType = 8 and sysOrgElement.fdIsAvailable = :fdIsAvailable and sysOrgElement.fdHierarchyId like :fdHierarchyId");
			hqlInfo.setParameter("fdIsAvailable", Boolean.TRUE);
			hqlInfo.setParameter("fdHierarchyId", hierarchyId + "%");
			List<Long> list = sysOrgElementService.findValue(hqlInfo);
			count = list.get(0);
		} catch (Exception e) {
			logger.error(e.toString());
		}
		return count;
	}
	
    /**
     * 获取指定机构下最大层级数
     * @param element
     * @param depth
     * @param maxLevel
     * @throws Exception
     */
	public int getMaxOrgLevel(SysOrgElement element, int depth, int maxLevel) throws Exception {
		// 查询子机构或部门
		HQLInfo hqlInfo = new HQLInfo();
		StringBuilder whereBlock = new StringBuilder();
		whereBlock.append(" sysOrgElement.hbmParent = '").append(element.getFdId()).append("'")
		.append(" and sysOrgElement.fdOrgType in (").append(ORG_TYPE_ORG).append(",").append(ORG_TYPE_DEPT).append(") ")
		.append(" and sysOrgElement.fdIsAvailable = :fdIsAvailable");
		hqlInfo.setWhereBlock(whereBlock.toString());
		hqlInfo.setParameter("fdIsAvailable", Boolean.TRUE);
		hqlInfo.setOrderBy("sysOrgElement.fdOrgType desc, sysOrgElement.fdOrder, sysOrgElement." + SysLangUtil.getLangFieldName("fdName"));
		List<SysOrgElement> orgElementList = sysOrgElementService.findList(hqlInfo);
		for (SysOrgElement org : orgElementList) {
			maxLevel = getMaxOrgLevel(org, depth+1, maxLevel);
		}
		if(depth>maxLevel){
			maxLevel = depth;
		}
		return maxLevel;
	}

}
