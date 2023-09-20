package com.landray.kmss.sys.time.util;

import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.time.service.ISysTimeBusinessExService;
import com.landray.kmss.util.SpringBeanUtil;
import org.slf4j.Logger;

import java.util.List;

/**
 * 排班获取用户人员
 * @author 王京
 * @version 1.0 2022-03-14
 */
public class SysTimePersonUtil implements SysOrgConstant {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysTimePersonUtil.class);

	private static ISysTimeBusinessExService sysTimeBusinessExService;
	public  static ISysTimeBusinessExService getSysTimeBusinessExService() {
		if (sysTimeBusinessExService == null) {
			sysTimeBusinessExService = (ISysTimeBusinessExService) SpringBeanUtil.getBean("sysTimeBusinessExService");
		}
		return sysTimeBusinessExService;
	}
	/**
	 * 组织ID，转换成人员ID
	 * 不取部门下岗位
	 * @param orgList
	 * @return
	 * @throws Exception
	 */
	public static List<String> expandToPersonIds(List orgList) throws Exception {
		return getSysTimeBusinessExService().expandToPersonIds(orgList);
	}

	/**
	 * 获取人员对象
	 * @param orgList
	 * @return
	 * @throws Exception
	 */
	public static List<SysOrgPerson> expandToPerson(List orgList) throws Exception {
		return getSysTimeBusinessExService().expandToPerson(orgList);
	}
}
