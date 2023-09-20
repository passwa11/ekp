package com.landray.kmss.sys.time.service.spring;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.time.interfaces.ISysTimeCountService;
import com.landray.kmss.sys.time.model.SysTimeArea;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.ExceptionUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;

/**
 * 角色线模拟器，Ajax调用
 * 
 * @author 叶中奇
 * @version 创建时间：2008-11-21 下午04:47:25
 */
public class SysTimeAreaSimulator implements IXMLDataBean {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysTimeAreaSimulator.class);

	private ISysTimeCountService sysTimeCountService;

	public void setSysTimeCountService(
			ISysTimeCountService sysTimeCountService) {
		this.sysTimeCountService = sysTimeCountService;
	}

	private ISysOrgElementService sysOrgElementService = null;

	public void setSysOrgElementService(
			ISysOrgElementService sysOrgElementService) {
		this.sysOrgElementService = sysOrgElementService;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Map<String, String>> getDataList(RequestContext requestInfo)
			throws Exception {
		List<Map<String, String>> rtnList = new ArrayList<Map<String, String>>();
		Map<String, String> map = new HashMap<String, String>();
		map.put("noperson", "0");
		rtnList.add(map);
		try {
			String userId = requestInfo.getParameter("userId");
			String type = requestInfo.getParameter("type");
			if (StringUtil.isNull(userId)) {
				map.put("message", ResourceUtil
						.getString("calendar.simulator.user", "sys-time"));
			} else {
				StringBuffer message = new StringBuffer();
				SysTimeArea area = sysTimeCountService
						.getTimeArea((SysOrgElement) sysOrgElementService
								.findByPrimaryKey(userId));
				if (area == null) {
					message.append(ResourceUtil.getString(
							"calendar.simulator.notarea", "sys-time"));
					map.put("noperson", "1");
				} else {
					if ("work".equalsIgnoreCase(type)) {
						String startTime = requestInfo
								.getParameter("startTime");
						String endTime = requestInfo.getParameter("endTime");
						if (StringUtil.isNull(startTime)
								|| StringUtil.isNull(endTime)) {
							message.append(ResourceUtil.getString(
									"calendar.simulator.time", "sys-time"));
						} else {
							long time = sysTimeCountService.getManHour(userId,
									DateUtil.convertStringToDate(startTime,
											DateUtil.TYPE_DATETIME,
											requestInfo.getLocale()).getTime(),
									DateUtil.convertStringToDate(endTime,
											DateUtil.TYPE_DATETIME,
											requestInfo.getLocale()).getTime());
							int hour = (int) (time / 3600000);
							if (hour > 0) {
								message.append(hour + ResourceUtil.getString(
										"calendar.simulator.time.unit.hour",
										"sys-time"));
							}
							int minu = (int) ((time / 60000) % 60);
							if (minu > 0) {
								message.append(minu + ResourceUtil.getString(
										"calendar.simulator.time.unit.sec",
										"sys-time"));
							}
							if (message.length() == 0) {
								message.append("0" + ResourceUtil.getString(
										"calendar.simulator.time.unit.sec",
										"sys-time"));
							}
						}
					} else {
						String time = requestInfo.getParameter("time");
						if (StringUtil.isNull(time)) {
							message.append(ResourceUtil.getString(
									"calendar.simulator.time.notnull",
									"sys-time"));
						} else {
							Date date = DateUtil.convertStringToDate(time,
									DateUtil.TYPE_DATE,
									requestInfo.getLocale());
							List<String> list = sysTimeCountService
									.getWorkState(userId, date);
							if (list != null && list.size() > 0) {
								for (int i = 0; i < list.size(); i++) {
									if (i == 0) {
										message.append(list.get(i));
									} else {
										message.append("," + list.get(i));
									}
								}
							}
							if (message.length() == 0) {
								message.append(ResourceUtil.getString(
										"calendar.simulator.notwt",
										"sys-time"));
							}
						}
					}
				}
				map.put("message", message.toString());
			}
		} catch (Exception e) {
			logger.error(ExceptionUtil.getExceptionString(e));
			map.put("message", e.getMessage());
		}
		return rtnList;
	}
}
