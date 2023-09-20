package com.landray.kmss.sys.organization.plugin;

import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.organization.dao.ISysOrgGroupDao;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgRole;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.organization.service.ISysOrgGroupService;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.HQLUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.hibernate.CacheMode;
import org.hibernate.query.NativeQuery;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

public class SysOrgPlugin_Group implements ISysOrgRolePlugin {
	private ISysOrgGroupService sysOrgGroupService;
	
	public void setSysOrgGroupService(ISysOrgGroupService sysOrgGroupService) {
		this.sysOrgGroupService = sysOrgGroupService;
	}

	private ISysOrgElementService sysOrgElementService;

	public void setSysOrgElementService(
			ISysOrgElementService sysOrgElementService) {
		this.sysOrgElementService = sysOrgElementService;
	}

	@Override
	public List<SysOrgElement> parse(SysOrgRolePluginContext context)
			throws Exception {
		List<SysOrgElement> rtnList = new ArrayList<SysOrgElement>();
		if (context == null) {
            return rtnList;
        }
		SysOrgElement element = context.getBaseElement();
		SysOrgRole role = context.getRole();
		String groupId = role.getParameter("groupId");
		String type = role.getParameter("type");
		boolean isMultiple = "true".equals(role.getParameter("isMultiple"));
		Assert.notNull(groupId, "群组ID不能为空");
		Assert.notNull(type, "成员位置的类型不能为空");

		// 群组成员中岗位和个人的层级ID
		List<String> memberHIds = getMemberHierarchyIds(groupId);
		if (ArrayUtil.isEmpty(memberHIds)) {
			return rtnList;
		}
		// 提交者的部门层级ID
		String myHId = getDeptHierarchyId(element);
		if (myHId == null) {
			return rtnList;
		}
		// 根据规则查找成员ID
		int iType = Integer.parseInt(type);
		List<String> rtnIds = null;
		switch (iType) {
		case 0:
			rtnIds = findParent(memberHIds, myHId, isMultiple);
			break;
		case 1:
			rtnIds = findAllParent(memberHIds, myHId, isMultiple);
			break;
		default:
			if (iType > 1) {
				rtnIds = findLevel(memberHIds, myHId, isMultiple, iType - 1);
			}
		}
		if (ArrayUtil.isEmpty(rtnIds)) {
			return rtnList;
		}
		for (String id : rtnIds) {
			rtnList.add((SysOrgElement) sysOrgElementService
					.findByPrimaryKey(id));
		}
		return rtnList;
	}

	/** 群组成员中岗位和个人的层级ID */
	@SuppressWarnings("unchecked")
	private List<String> getMemberHierarchyIds(String groupId) throws Exception {
		List<String> groupIds= new ArrayList<String>();
		groupIds.add(groupId);
		groupIds = ((ISysOrgGroupDao)sysOrgGroupService.getBaseDao()).fetchChildGroupIds(groupIds);
		String whereBlock = HQLUtil.buildLogicIN("fd_groupid", groupIds);
		String sql = "select fd_hierarchy_id from sys_org_element left join sys_org_group_element on fd_id = fd_elementid"
				+ " where (fd_org_type=8 or fd_org_type=4) and " + whereBlock + " order by fd_hierarchy_id";
		NativeQuery query = sysOrgElementService.getBaseDao()
				.getHibernateSession().createNativeQuery(sql);
		query.setCacheable(true);
		query.setCacheMode(CacheMode.NORMAL);
		query.setCacheRegion("sys-organization");
		query.addScalar("fd_hierarchy_id", StandardBasicTypes.STRING);
		return query.list();
	}

	/** 提交者的部门层级ID */
	private String getDeptHierarchyId(SysOrgElement element) {
		String hierarchyId = element.getFdHierarchyId();
		if (hierarchyId.length() < 2) {
			return null;
		}
		if (element.getFdOrgType() == SysOrgConstant.ORG_TYPE_PERSON
				|| element.getFdOrgType() == SysOrgConstant.ORG_TYPE_POST) {
			hierarchyId = hierarchyId.substring(0, hierarchyId.length() - 1);
			int index = hierarchyId.lastIndexOf("x");
			if (index < 1) {
				return null;
			}
			hierarchyId = hierarchyId.substring(0, index + 1);
		}
		return hierarchyId;
	}

	/** 将层级ID拆解成：父层级ID+自己的ID */
	private static String[] splitHId(String hid) {
		if (hid.length() < 3) {
			return null;
		}
		String s = hid.substring(0, hid.length() - 1);
		int index = s.lastIndexOf("x");
		if (index < 1) {
			return null;
		}
		return new String[] { s.substring(0, index + 1), s.substring(index + 1) };
	}

	/** 查找当前层级父节点的群组成员 */
	private List<String> findParent(List<String> memberHIds, String myHId,
			boolean isMultiple) {
		List<String> rtnIds = new ArrayList<String>();
		for (String memberHId : memberHIds) {
			String[] parentIds = splitHId(memberHId);
			if (parentIds == null) {
				continue;
			}
			if (myHId.equals(parentIds[0])) {
				rtnIds.add(parentIds[1]);
				if (!isMultiple) {
                    break;
                }
			}
		}
		return rtnIds;
	}

	/** 查找最近层级父节点的群组成员 */
	private List<String> findAllParent(List<String> memberHIds, String myHId,
			boolean isMultiple) {
		List<String> rtnIds = new ArrayList<String>();
		int maxLv = -1;
		String maxLvId = null;
		JSONObject obj = new JSONObject();
		for (String memberHId : memberHIds) {
			String[] parentIds = splitHId(memberHId);
			if (parentIds == null) {
				continue;
			}
			if (myHId.startsWith(parentIds[0])) {
				int thisLv = parentIds[0].split("x").length;
				
				if (isMultiple) {
                    obj.accumulate(String.valueOf(thisLv), parentIds[1]);
                }

				if (thisLv > maxLv) {
					maxLv = thisLv;
					if (!isMultiple) {
						maxLvId = parentIds[1];
					}
				}
			}
		}
		if (isMultiple) {
			Object o = obj.get(String.valueOf(maxLv));
			if (o != null) {
				if (o instanceof JSONArray) {
					JSONArray arr = (JSONArray) o;
					for (int i = 0; i < arr.size(); i++) {
						rtnIds.add(arr.getString(i));
					}
				} else {
					rtnIds.add(o.toString());
				}
			}
		} else {
			if (maxLvId != null) {
				rtnIds.add(maxLvId);
			}
		}
		return rtnIds;
	}

	/** 查找与提交人前几级一样的群组成员 */
	private List<String> findLevel(List<String> memberHIds, String myHId,
			boolean isMultiple, int level) {
		List<String> rtnIds = new ArrayList<String>();
		String[] myHIdArr = myHId.split("x");
		int maxLv = -1;
		for (String memberHId : memberHIds) {
			String[] memberHidArr = memberHId.split("x");
			int thisLv = matchLevel(myHIdArr, memberHidArr);
			if (thisLv >= level) {
				if (isMultiple) {
					rtnIds.add(memberHidArr[memberHidArr.length - 1]);
				} else {
					if (thisLv > maxLv) {
						maxLv = thisLv;
						rtnIds.clear();
						rtnIds.add(memberHidArr[memberHidArr.length - 1]);
					}
				}
			}
		}
		return rtnIds;
	}

	/** 返回hid1和hid2前面有多少个元素是一样的 */
	private int matchLevel(String[] hid1, String[] hid2) {
		// 注意层级Id进行split后，第0个元素为""，所以最少有一个匹配
		int i = 1;
		for (; i < hid1.length && i < hid2.length; i++) {
			if (!hid1[i].equals(hid2[i])) {
				break;
			}
		}
		return i - 1;
	}
}
