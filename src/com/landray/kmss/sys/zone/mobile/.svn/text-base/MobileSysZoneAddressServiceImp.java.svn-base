package com.landray.kmss.sys.zone.mobile;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.sys.organization.mobile.MobileAddressServiceImp;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.zone.model.SysZonePersonInfo;
import com.landray.kmss.sys.zone.model.SysZonePrivateConfig;
import com.landray.kmss.sys.zone.service.ISysZonePersonInfoService;
import com.landray.kmss.sys.zone.util.SysZonePrivateUtil;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.StringUtil;
import org.slf4j.Logger;

import java.util.*;

public class MobileSysZoneAddressServiceImp extends MobileAddressServiceImp {

	Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

	private ISysZonePersonInfoService sysZonePersonInfoService;

	public void setSysZonePersonInfoService(
			ISysZonePersonInfoService sysZonePersonInfoService) {
		this.sysZonePersonInfoService = sysZonePersonInfoService;
	}

	@Override
	@SuppressWarnings("unchecked")
	protected Map formatElement(SysOrgElement orgElem, boolean needDetail) throws Exception {
		Map tmpMap = null;
		try {
			tmpMap = super.formatElement(orgElem, needDetail);
			if (orgElem.getFdOrgType() == ORG_TYPE_PERSON) {
				SysOrgPerson person = (SysOrgPerson) orgElem;
				List posts = person.getFdPosts();
				SysZonePrivateConfig privateConfig = new SysZonePrivateConfig();
				Map<String, String> dataMap = privateConfig.getDataMap();
				String isDepInfoPrivate = dataMap.get("isDepInfoPrivate");
				SysZonePersonInfo sysZonePersonInfo = (SysZonePersonInfo) sysZonePersonInfoService
						.updateGetPerson(orgElem.getFdId());
				Boolean isDepInfoPrivate2 = sysZonePersonInfo
						.getIsDepInfoPrivate();
				Boolean isContactPrivate2 = sysZonePersonInfo
						.getIsContactPrivate();
				boolean isDepInfoPrivate3 = isDepInfoPrivate2 != null
						? isDepInfoPrivate2.booleanValue() : false;
				boolean isContactPrivate3 = isContactPrivate2 != null
						? isContactPrivate2.booleanValue() : false;
				if (posts.size() > 0 && "0".equals(isDepInfoPrivate)
						&& !isDepInfoPrivate3) {
					tmpMap.put("post", ArrayUtil.joinProperty(posts, "fdName", ";")[0]);
				}
				String isContactPrivate = dataMap.get("isContactPrivate");
				if ("0".equals(isContactPrivate) && !isContactPrivate3) {
					tmpMap.put("email",
							StringUtil.isNotNull(person.getFdEmail())
									? person.getFdEmail() : "");
					tmpMap.put("phone",
							StringUtil.isNotNull(person.getFdMobileNo())
									? person.getFdMobileNo() : "");
				}
			}
		} catch (Exception e) {
			logger.error("个人中心通讯录转换组织机构数据发生异常：MobileSysZoneAddressServiceImp.formatElement");
			logger.error("class:"+orgElem.getClass().toString()+"     "+"orgType:"+orgElem.getFdOrgType()+"     "+"fdId:"+orgElem.getFdId()+"     "+"fdName:"+orgElem.getFdName());
			logger.error(e.toString());
			throw e;
		}
		return tmpMap;
	}

	@Override
	protected List formatListData(RequestContext xmlContext, List orgList,
								  boolean needSort) throws Exception {
		//隐私处理需要
		List<String> ids = new ArrayList<String>();
		List rtnList = new ArrayList();
		orgList = sysOrganizationStaffingLevelService.getStaffingLevelFilterResult(orgList);
		if (orgList == null || orgList.isEmpty()) {
			return rtnList;
		}
		// 先构造机构|部门信息
		List deptList = new ArrayList();
		List orgList1 = new ArrayList();
		List postList = new ArrayList();
		List personList = new ArrayList();
		String exceptValue = xmlContext.getParameter("exceptValue");

		String curCate = "";
		for (Iterator iterator = orgList.iterator(); iterator.hasNext();) {
			SysOrgElement tmpOrg = (SysOrgElement) iterator.next();
			if (StringUtil.isNotNull(exceptValue)
					&& exceptValue.indexOf(tmpOrg.getFdId()) > -1) {// 排除例外
				continue;
			}
			int orgType = tmpOrg.getFdOrgType();
			if ((orgType & ORG_TYPE_DEPT) == orgType) {// 机构或部门
				deptList.add(formatElement(tmpOrg, false));
			} else if ((orgType & ORG_TYPE_ORG) == orgType) {// 机构
				orgList1.add(formatElement(tmpOrg, false));
			} else if (orgType == ORG_TYPE_POST) {
				postList.add(formatElement(tmpOrg, false));
			} else if (orgType == ORG_TYPE_PERSON) {//通讯录的不需要旁边的字母目录
				personList.add(formatElement(tmpOrg, false));
				ids.add(tmpOrg.getFdId());
			}
		}
		if (!deptList.isEmpty() || !orgList1.isEmpty()) {
			rtnList.add(formatHeader("2"));
			Collections.sort(deptList, new OrderComparator());
			Collections.sort(orgList1, new OrderComparator());
			rtnList.addAll(deptList);
			rtnList.addAll(orgList1);
		}
		if (!postList.isEmpty()) {
			rtnList.add(formatHeader("4"));
			Collections.sort(postList, new OrderComparator());
			rtnList.addAll(postList);
		}
		if (!personList.isEmpty()) {
			rtnList.add(formatHeader("8"));
			Collections.sort(personList, new PersonComparator());
			rtnList.addAll(personList);
		}

		//隐私处理
		if (!ArrayUtil.isEmpty(ids)) {
			List privateCList = null;
			List privateDList = null;
			Boolean isALlContactPrivate = SysZonePrivateUtil
					.isALlContactPrivate();
			Boolean isALlDepInfoPrivate = SysZonePrivateUtil
					.isAllDepInfoPrivate();
			if (!isALlContactPrivate) {
				privateCList = SysZonePrivateUtil.getContactPrivateByIds(ids,
						"isContactPrivate");
			}
			if (!isALlDepInfoPrivate) {
				privateDList = SysZonePrivateUtil.getContactPrivateByIds(ids,
						"isDepInfoPrivate");
			}
			if (isALlContactPrivate || isALlDepInfoPrivate
					|| !ArrayUtil.isEmpty(privateCList)
					|| !ArrayUtil.isEmpty(privateDList)) {
				for (int i = 0; i < rtnList.size(); i++) {
					Map ele = (Map) rtnList.get(i);
					if (ele.containsKey("type")
							&& (Integer) ele.get("type") == ORG_TYPE_PERSON) {
						String fdId = (String) ele.get("fdId");
						if (isALlContactPrivate
								|| (!ArrayUtil.isEmpty(privateCList) && privateCList
								.contains(fdId))) {
							ele.remove("email");
							ele.remove("phone");
						}
						if (isALlDepInfoPrivate
								|| (!ArrayUtil.isEmpty(privateDList) && privateDList
								.contains(fdId))) {
							ele.remove("post");
						}
					}
				}
			}
		}
		return rtnList;
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
			if (((Integer) order1).equals((Integer) order2)) {
				return ((Integer) order1).compareTo((Integer) order2);
			} else {
                return ((Integer) order1).compareTo((Integer) order2);
            }
		}
	}

	/**
	 * 人员排序，先按排序号，再按名称<br>
	 * 现改为和PC端保持一致 by zc 2018.4.27
	 *
	 * @author 潘永辉 2018年4月19日
	 *
	 */
	private class PersonComparator implements Comparator {
		@Override
		public int compare(Object o1, Object o2) {
			Map<String, Object> m1 = (Map<String, Object>) o1;
			Map<String, Object> m2 = (Map<String, Object>) o2;
			//
			// Integer i1 = m1.get("order") == null ? Integer.MAX_VALUE :
			// (Integer) m1.get("order");
			// Integer i2 = m2.get("order") == null ? Integer.MAX_VALUE :
			// (Integer) m2.get("order");
			//
			// String p1 = m1.get("pinyin") == null ? "" :
			// m1.get("pinyin").toString();
			// String p2 = m2.get("pinyin") == null ? "" :
			// m2.get("pinyin").toString();
			//
			// if (i1.equals(i2)) {
			// return p1.compareTo(p2);
			// } else if (i1 > i2) {
			// return 1;
			// } else {
			// return -1;
			// }
			Integer t1 = m1.get("type") != null
					? StringUtil.getIntFromString(m1.get("type").toString(), 8)
					: 8;
			Integer t2 = m2.get("type") != null
					? StringUtil.getIntFromString(m2.get("type").toString(), 8)
					: 8;

			Integer i1 = m1.get("order") != null
					? StringUtil.getIntFromString(m1.get("order").toString(),
					Integer.MAX_VALUE)
					: Integer.MAX_VALUE;
			Integer i2 = m2.get("order") != null
					? StringUtil.getIntFromString(m2.get("order").toString(),
					Integer.MAX_VALUE)
					: Integer.MAX_VALUE;

			if (t1.equals(t2)) {
				if (i1.equals(i2)) {
					return ((String) m1.get("label"))
							.compareTo((String) m2.get("label"));
				} else if (i1 > i2) {
					return 1;
				} else {
					return -1;
				}
			} else if (t1 > t2) {
				return -1;
			} else {
				return 1;
			}
		}
	}
}
