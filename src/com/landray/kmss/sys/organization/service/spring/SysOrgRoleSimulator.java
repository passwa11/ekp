package com.landray.kmss.sys.organization.service.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.ExceptionUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;

/**
 * 角色线模拟器，Ajax调用
 * 
 * @author 叶中奇
 * @version 创建时间：2008-11-21 下午04:47:25
 */
public class SysOrgRoleSimulator implements IXMLDataBean {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysOrgRoleSimulator.class);

	private ISysOrgCoreService sysOrgCoreService;

	public void setSysOrgCoreService(ISysOrgCoreService sysOrgCoreService) {
		this.sysOrgCoreService = sysOrgCoreService;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Map<String, String>> getDataList(RequestContext requestInfo)
			throws Exception {
		List<Map<String, String>> rtnList = new ArrayList<Map<String, String>>();
		Map<String, String> map = new HashMap<String, String>();
		rtnList.add(map);
		try {
			String orgIds = requestInfo.getParameter("orgIds");
			if (StringUtil.isNull(orgIds)) {
				map.put("message", "");
			} else {
				String[] idArr = requestInfo.getParameter("orgIds").split(";");
				String userId = requestInfo.getParameter("userId");
				SysOrgElement baseElem = sysOrgCoreService
						.findByPrimaryKey(userId);
				List originElemList = sysOrgCoreService
						.findByPrimaryKeys(idArr);
				if (UserOperHelper.allowLogOper("sysOrgRoleSimulator", null)) {
					UserOperHelper.setModelNameAndModelDesc("com.landray.kmss.sys.organization.model.SysOrgRoleLine");
					UserOperContentHelper.putFind(baseElem);
					UserOperContentHelper.putFinds(originElemList);
				}
				StringBuffer message = new StringBuffer();
				for (int i = 0; i < originElemList.size(); i++) {
					SysOrgElement elem = (SysOrgElement) originElemList.get(i);
					if (elem.getFdOrgType().intValue() == SysOrgConstant.ORG_TYPE_ROLE) {
						List result = sysOrgCoreService.parseSysOrgRole(elem,
								baseElem);
						message.append(StringUtil.XMLEscape(elem.getFdName())).append(" = ");
						// 判断是否无效
						StringBuffer names = new StringBuffer();
						if (!result.isEmpty()) {
							for (Object obj : result) {
								if (obj instanceof SysOrgElement) {
									SysOrgElement element = (SysOrgElement) obj;
									names.append(";").append(element.getFdName());
									if (element.getFdIsAvailable() != null && !element.getFdIsAvailable()) // 无效
                                    {
                                        names.append("(").append(ResourceUtil.getString("sysOrg.address.info.disable", "sys-organization")).append(")");
                                    }
								}
							}
						}
						if (names.length() > 0) {
                            names.deleteCharAt(0);
                        }
						message.append(names).append("<br>");
					} else {
						message.append(StringUtil.XMLEscape(elem.getFdName()));
						if (elem.getFdIsAvailable() != null && !elem.getFdIsAvailable()) // 无效
                        {
                            message.append("(")
                                .append(ResourceUtil.getString("sysOrg.address.info.disable", "sys-organization"))
                                .append(")");
                        }
						message.append("<br>");
					}

				}
				map.put("message", message.toString());
			}
		} catch (Exception e) {
			logger.error(ExceptionUtil.getExceptionString(e));
			// 页面不显示错误代码
			map.put("message", e.getMessage());
		}
		return rtnList;
	}
}
