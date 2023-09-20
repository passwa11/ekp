package com.landray.kmss.sys.organization.plugin;

import java.util.ArrayList;
import java.util.List;

import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgRole;
import com.landray.kmss.util.StringUtil;

public class SysOrgPlugin_Leader implements ISysOrgRolePlugin {
	@Override
	public List<SysOrgElement> parse(SysOrgRolePluginContext context)
			throws Exception {
		if (context == null) {
            return new ArrayList<SysOrgElement>();
        }
		SysOrgElement element = context.getBaseElement();
		SysOrgRole role = context.getRole();
		String type = role.getParameter("type");
		String endlevel = role.getParameter("endlevel");
		int lv = Integer.parseInt(role.getParameter("level"));
		int elv = 0;
		if (StringUtil.isNull(endlevel)) {
			elv = lv;
		} else {
			elv = Integer.parseInt(endlevel);
		}
		// 如果是查询“多个领导”，因为有上限和下限，所以还得要取所有数据
		if (lv != elv) {
			if ("excludeme".equals(type)) {
				return getLeaders(element.getAllMyLeader(), lv, elv);
			} else {
				return getLeaders(element.getAllLeader(), lv, elv);
			}
		} else {
			if ("excludeme".equals(type) && lv >= 0) {
				// 当包含自己并从下往上获取领导时，层级需要+1
				return getLeaders(element.getAllMyLeader(lv + 1), lv, elv);
			} else {
				return getLeaders(element.getAllLeader(lv), lv, elv);
			}
		 }
	}

	private List<SysOrgElement> getLeaders(List<SysOrgElement> leaders, int s,
			int e) {
		List<SysOrgElement> rtnList = new ArrayList<SysOrgElement>();
		if (s < 0) {
            s = leaders.size() + s;
        }
		if (e < 0) {
            e = leaders.size() + e;
        }
		int step = s > e ? -1 : 1;
		for (int i = s; i >= 0 && i < leaders.size()
				&& (s > e ? i >= e : i <= e); i += step) {
			rtnList.add(leaders.get(i));
		}
		return rtnList;
	}

}
