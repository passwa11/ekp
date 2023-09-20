package com.landray.kmss.hr.organization.service.spring;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.hr.organization.constant.HrOrgConstant;
import com.landray.kmss.hr.organization.model.HrOrganizationElement;
import com.landray.kmss.hr.organization.service.IHrOrganizationChartService;
import com.landray.kmss.hr.organization.service.IHrOrganizationElementService;
import com.landray.kmss.hr.organization.util.HrOrgUtil;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.ResourceUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class HrOrganizationChartServiceImp extends BaseServiceImp
		implements HrOrgConstant, IHrOrganizationChartService {

	private static final String EMP_IMG_URL = "/sys/person/image.jsp?personId=%s&size=s&s_time=%s";

	private IHrOrganizationElementService hrOrganizationElementService;

	public void setHrOrganizationElementService(
			IHrOrganizationElementService hrOrganizationElementService) {
		this.hrOrganizationElementService = hrOrganizationElementService;
	}

	@Override
	public String getChartData(String fdId, boolean isFirstTimeLoad,
			int expandLevel) throws Exception {
		JSONObject jsonObj = new JSONObject();
		JSONArray jsonArr = new JSONArray();

		int depth = 1; // 位于当前展示界面的层级
		HrOrganizationElement element = (HrOrganizationElement) hrOrganizationElementService
				.findByPrimaryKey(fdId);
		if (element.getFdOrgType() == HR_TYPE_ORG
				|| element.getFdOrgType() == HR_TYPE_DEPT) {
			createChartData(jsonArr, element, depth, expandLevel);
			if (isFirstTimeLoad) {
				int initMaxLevel = 1;
				int maxLevel = getMaxOrgLevel(element, depth, initMaxLevel);
				jsonObj.put("maxLevel", maxLevel);
			}
		}
		if (UserOperHelper.allowLogOper("GetAllList", "*")) {
			UserOperHelper.setModelNameAndModelDesc(
					hrOrganizationElementService.getModelName(),
					ResourceUtil.getString(
							"hr-organization:module.hr.organization"));
			UserOperContentHelper.putFind(element);
		}

		jsonObj.put("d", jsonArr);
		return jsonObj.toString();
	}

	@SuppressWarnings("unchecked")
	private void createChartData(JSONArray array, HrOrganizationElement element,
			int depth, int expandLevel) throws Exception {

		int OrganizationalType = element.getFdOrgType(); // 组织架构类型
		String OrganizationalID = element.getFdId(); // 部门ID
		String OrganizationalName = element.getFdName(); // 部门名称
		String OrganizationalNameAll = element.getFdName(); // 部门名称（鼠标指向展示内容）

		String ParentOrganizationalID = StringUtils.EMPTY; // 部门的父级部门ID
		String ParentOrganizationalName = StringUtils.EMPTY; // 部门的父级部门名称
		HrOrganizationElement parent = element.getFdParent();
		if (parent != null) {
			ParentOrganizationalID = parent.getFdId();
			ParentOrganizationalName = parent.getFdName();
		}

		int DirectorType = 8; // 部门领导的组织架构类型
		int DirectorNumber = 1; // 部门或机构领导人的数量
		String DirectorEmpID = StringUtils.EMPTY; // 部门的领导ID
		String DirectorName = ResourceUtil
				.getString("sys-organization:sysOrgElement.leaderNull"); // 暂无领导
		Map<String, Object> PositionMap = new HashMap<String, Object>(); // 岗位信息
		JSONArray SiblingDirectors = null; // 并列领导
		String empImgUrl = "/sys/person/image.jsp";// 部门主管头像

		HrOrganizationElement leader = element.getHbmThisLeader();// 部门领导或机构领导
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
			if (HR_TYPE_PERSON == DirectorType) {
				DirectorType = leader.getFdOrgType();
				DirectorEmpID = leader.getFdId();
				DirectorName = leader.getFdName();
				empImgUrl = String.format(EMP_IMG_URL, DirectorEmpID,
						System.currentTimeMillis());
				PositionMap = getLeaderPositionMap(leader);
			} else if (HR_TYPE_POST == DirectorType) {
				// 如果是岗位，则获取岗位下的所有人，并且默认显示第一个领导人信息
				List<HrOrganizationElement> hbmPersons = leader.getHbmPersons();
				if (hbmPersons != null && hbmPersons.size() > 0) {
					DirectorNumber = hbmPersons.size();
					for (int i = 0; i < DirectorNumber; i++) {
						HrOrganizationElement leaderItem = hbmPersons.get(i);
						if (i == 0) {
							DirectorEmpID = leaderItem.getFdId();
							DirectorName = leaderItem.getFdName();
							empImgUrl = String.format(EMP_IMG_URL,
									DirectorEmpID, System.currentTimeMillis());
							PositionMap = getLeaderPositionMap(leaderItem);
						} else {
							if (SiblingDirectors == null) {
								SiblingDirectors = new JSONArray();
							}
							JSONObject siblingDirector = new JSONObject();
							siblingDirector.put("DirectorEmpID",
									leaderItem.getFdId());
							siblingDirector.put("DirectorName",
									leaderItem.getFdName());
							siblingDirector.put("empImgUrl",
									String.format(EMP_IMG_URL,
											leaderItem.getFdId(),
											System.currentTimeMillis()));
							siblingDirector
									.putAll(getLeaderPositionMap(leaderItem));
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
		HQLInfo hqlInfo = new HQLInfo();
		whereBlock.append(" hrOrganizationElement.hbmParent = '")
				.append(element.getFdId())
				.append("' and hrOrganizationElement.fdOrgType in (")
				.append(HR_TYPE_ORG).append(",").append(HR_TYPE_DEPT)
				.append(") and hrOrganizationElement.fdIsAvailable = :fdIsAvailable");
		hqlInfo.setWhereBlock(whereBlock.toString());
		hqlInfo.setParameter("fdIsAvailable", Boolean.TRUE);
		List<HrOrganizationElement> childrens = (List<HrOrganizationElement>) hrOrganizationElementService
				.findList(hqlInfo);

		if (!ArrayUtil.isEmpty(childrens)) {
			ChildCount = childrens.size();
			int childrenDepth = depth + 1;
			if (childrenDepth <= expandLevel) {
				for (HrOrganizationElement children : childrens) {
					createChartData(array, children, childrenDepth,
							expandLevel);
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
		jsonObject.put("EmpCountUnit", ResourceUtil
				.getString("sys-organization:sysOrgElement.orgChart.person"));
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
	private Map<String, Object>
			getLeaderPositionMap(HrOrganizationElement leader) {
		Map<String, Object> postMap = new HashMap<String, Object>();
		postMap.put("PositionName", ResourceUtil
				.getString("sys-organization:sysOrgElement.postNull"));
		postMap.put("PositionId", StringUtils.EMPTY);

		if (leader != null && HR_TYPE_PERSON == leader.getFdOrgType()) {
			@SuppressWarnings("unchecked")
			List<HrOrganizationElement> hbmPosts = leader.getHbmPosts();
			if (hbmPosts != null && hbmPosts.size() > 0) {
				// 显示领导人的第一个岗位
				HrOrganizationElement firstPost = hbmPosts.get(0);
				postMap.put("PositionName", firstPost.getFdName());
				postMap.put("PositionId", firstPost.getFdId());
			}
		}

		return postMap;
	}

	@Override
	public void countPersonsNumber() throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		String whereBlock = " fdOrgType = " + HR_TYPE_DEPT + " or fdOrgType = "
				+ HR_TYPE_ORG;
		hqlInfo.setWhereBlock(whereBlock);
		List<HrOrganizationElement> orgs = hrOrganizationElementService
				.findList(hqlInfo);
		for (HrOrganizationElement element : orgs) {
			String count = HrOrgUtil.getPersonCountByOrgDept(element);
			if (StringUtils.isNotEmpty(count)) {
				element.setFdPersonsNumber(Integer.parseInt(count));
			}
		}
	}

	/**
	 * 获取指定机构下最大层级数
	 * 
	 * @param element
	 * @param depth
	 * @param maxLevel
	 * @throws Exception
	 */
	public int getMaxOrgLevel(HrOrganizationElement element, int depth,
			int maxLevel) throws Exception {
		// 查询子机构或部门
		HQLInfo hqlInfo = new HQLInfo();
		StringBuilder whereBlock = new StringBuilder();
		whereBlock.append(" hrOrganizationElement.hbmParent = '")
				.append(element.getFdId()).append("'")
				.append(" and hrOrganizationElement.fdOrgType in (")
				.append(HR_TYPE_ORG).append(",").append(HR_TYPE_DEPT)
				.append(") ")
				.append(" and hrOrganizationElement.fdIsAvailable = :fdIsAvailable");
		hqlInfo.setWhereBlock(whereBlock.toString());
		hqlInfo.setParameter("fdIsAvailable", Boolean.TRUE);
		List<HrOrganizationElement> orgElementList = hrOrganizationElementService
				.findList(hqlInfo);
		for (HrOrganizationElement org : orgElementList) {
			maxLevel = getMaxOrgLevel(org, depth + 1, maxLevel);
		}
		if (depth > maxLevel) {
			maxLevel = depth;
		}
		return maxLevel;
	}

}
