package com.landray.kmss.sys.organization.pda;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.organization.interfaces.SysOrgHQLUtil;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;

public class PdaAddressServiceImp implements IPdaAddressService, SysOrgConstant {

	private ISysOrgElementService sysOrgElementService;

	public void setSysOrgElementService(
			ISysOrgElementService sysOrgElementService) {
		this.sysOrgElementService = sysOrgElementService;
	}

	public ISysOrgElementService getSysOrgElementService() {
		return sysOrgElementService;
	}

	/**
	 * PDA地址本list的显示
	 * 
	 * @param xmlContext
	 * @return
	 * @throws Exception
	 */
	@Override
	public List getDataList(RequestContext xmlContext, String currentOrg)
			throws Exception {
		String whereBlock;
		int orgType = ORG_TYPE_PERSON;
		String para = currentOrg;
		if (StringUtil.isNull(para)) {
            whereBlock = "sysOrgElement.hbmParent=null";
        } else {
            whereBlock = "sysOrgElement.hbmParent.fdId='" + para + "'";
        }
		para = xmlContext.getParameter("fd_orgtype");
		if (StringUtil.isNotNull(para)) {
			try {
				orgType = Integer.parseInt(para);
			} catch (NumberFormatException e) {
			}
		}
		orgType = orgType
				& (ORG_TYPE_ALLORG | ORG_FLAG_AVAILABLEALL | ORG_FLAG_BUSINESSALL);
		int orgListType = (orgType | ORG_TYPE_ORGORDEPT)
				& (ORG_TYPE_ALLORG | ORG_FLAG_AVAILABLEALL | ORG_FLAG_BUSINESSALL);
		whereBlock = SysOrgHQLUtil.buildWhereBlock(orgListType, whereBlock,
				"sysOrgElement");
		List<SysOrgElement> elemList = sysOrgElementService
				.findList(whereBlock,
						"sysOrgElement.fdOrgType desc, sysOrgElement.fdOrder, sysOrgElement.fdName");

		// 点击部门或机构时，把这部门或机构下的所有岗位中的人员，加到列表中去
		if ((orgType & ORG_TYPE_PERSON) == ORG_TYPE_PERSON
				&& StringUtil.isNotNull(para)) {
			whereBlock = SysOrgHQLUtil.buildWhereBlock(ORG_TYPE_POST,
					"sysOrgElement.hbmParent.fdId='" + para + "'",
					"sysOrgElement");
			List<SysOrgElement> postList = sysOrgElementService.findList(
					whereBlock, "sysOrgElement.fdOrder");
			for (SysOrgElement post : postList) {
				ArrayUtil.concatTwoList(post.getFdPersons(), elemList);
			}
		}
		return formatListData(elemList, orgType);
	}

	/**
	 * 获取当前部门或人员的上级部门信息
	 * 
	 * @param orgId
	 * @return
	 * @throws Exception
	 */
	@Override
	public List getParentList(RequestContext xmlContext, String currentOrg)
			throws Exception {
		String orgId = currentOrg;
		List list = null;
		if (StringUtil.isNull(orgId)) {
			String whereBlock = "sysOrgElement.hbmParent=null";
			whereBlock = SysOrgHQLUtil.buildWhereBlock(ORG_TYPE_ORGORDEPT,
					whereBlock, "sysOrgElement");
			list = sysOrgElementService
					.findList(whereBlock,
							"sysOrgElement.fdOrgType desc, sysOrgElement.fdOrder, sysOrgElement.fdName");
		} else {
			SysOrgElement org = (SysOrgElement) sysOrgElementService
					.findByPrimaryKey(orgId);
			if (org != null) {
				list = new ArrayList();
				String tid="";
				while (org != null) {
					if ((org.getFdOrgType()& ORG_TYPE_ORGORDEPT) == org.getFdOrgType()) {
                        list.add(org);
                    }
					tid=org.getFdId();
					org = org.getFdParent();
				}
				String whereBlock = "sysOrgElement.hbmParent=null and sysOrgElement.fdId!='"
					+ tid + "'";
				whereBlock = SysOrgHQLUtil.buildWhereBlock(ORG_TYPE_ORGORDEPT,
						whereBlock, "sysOrgElement");
				List list1 = sysOrgElementService
				.findList(whereBlock,
						"sysOrgElement.fdOrgType desc, sysOrgElement.fdOrder, sysOrgElement.fdName");
				if (list1 != null && list1.size() > 0) {
                    list.addAll(list1);
                }
			}
		}
		return formatListData(list, 0, true);
	}

	/**
	 * 格式化返回结果
	 * 
	 * @param list
	 *            , type
	 * @return
	 * @throws Exception
	 */
	protected List formatListData(List list, int orgType) throws Exception {
		return this.formatListData(list, orgType, false);
	}

	/**
	 * 格式化返回结果
	 * 
	 * @param list
	 * @return
	 * @throws Exception
	 */
	protected List formatListData(List list, int orgType, boolean simplefmt)
			throws Exception {
		if (list == null || list.size() <= 0) {
            return null;
        }
		List rtnList = new ArrayList();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Map tmpMap = new HashMap();
			SysOrgElement tmpOrg = (SysOrgElement) iterator.next();
			tmpMap.put("id", tmpOrg.getFdId());
			if (tmpOrg.getFdParent() != null && simplefmt) {
                tmpMap.put("name", ResourceUtil.getString(
                        "pda.address.dept.formal", "sys-organization-pda")
                        + tmpOrg.getFdName());
            } else {
                tmpMap.put("name", tmpOrg.getFdName());
            }
			if (!simplefmt) {
				tmpMap.put("isAdd",
						((orgType & tmpOrg.getFdOrgType()) == tmpOrg
								.getFdOrgType()));
				if((ORG_TYPE_PERSON & tmpOrg.getFdOrgType()) == tmpOrg.getFdOrgType()) {
                    tmpMap.put("canExpand", "2");//个人
                } else if((ORG_TYPE_POST & tmpOrg.getFdOrgType()) == tmpOrg.getFdOrgType()) {
                    tmpMap.put("canExpand", "1");//岗位
                } else {
                    tmpMap.put("canExpand", "0");//部门或群组
                }
				if (tmpOrg.getFdParent() != null) {
					tmpMap.put("summary", tmpOrg.getFdParent().getFdName());
				}
			}
			rtnList.add(tmpMap);
		}
		return rtnList;
	}

	/**
	 * 获取搜索结果列表
	 * 
	 * @param xmlContext
	 * @return
	 * @throws Exception
	 */
	@Override
	public List getSearchList(RequestContext xmlContext) throws Exception {
		int orgType = ORG_TYPE_PERSON;
		String para = xmlContext.getParameter("fd_orgtype");
		if (StringUtil.isNotNull(para)) {
			try {
				orgType = Integer.parseInt(para);
			} catch (NumberFormatException e) {
			}
		}
		String[] keys = xmlContext.getParameter("fd_keyword").replaceAll("'",
				"''").split("\\s*[,;，；]\\s*");
		StringBuffer whereBf = new StringBuffer();

		for (String key : keys) {
			if (StringUtil.isNull(key)) {
                continue;
            }
			whereBf.append(" or sysOrgElement.fdName like '%" + key
					+ "%' or sysOrgElement.fdLoginName like '%" + key + "%' "
					+ "or sysOrgElement.fdNamePinYin like '%" + key + "%'");
		}
		if (whereBf.length() == 0) {
            return null;
        }

		String whereBlock = whereBf.substring(4);
		HQLInfo hqlInfo = new HQLInfo();
		whereBlock = SysOrgHQLUtil.buildWhereBlock(orgType, whereBlock,
				"sysOrgElement");
		hqlInfo.setWhereBlock(whereBlock);
		hqlInfo
				.setOrderBy("sysOrgElement.fdOrgType desc, sysOrgElement.fdName");
		hqlInfo.setRowSize(LIMIT_SEARCH_RESULT_SIZE + 1);
		hqlInfo.setGetCount(false);

		List elemList = sysOrgElementService.findPage(hqlInfo).getList();

		return formatListData(elemList, orgType);
	}

}
