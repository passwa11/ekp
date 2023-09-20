package com.landray.kmss.sys.oms.in.map;

import java.util.Date;

import com.landray.kmss.sys.appconfig.model.BaseAppConfig;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.StringUtil;

/**
 * 组织元素Map实现
 * 
 * @author 吴兵
 * @version 3.0 2009-10-26
 */

public class MapOmsConfig extends BaseAppConfig {
	public MapOmsConfig() throws Exception {
		super();
	}

	@Override
	public String getJSPUrl() {
		return "";
	}

	/**
	 * 最近更新时间
	 */
	public Date getLastUpdateTime() {
		String str = getValue("lastUpdateTime");
		if (StringUtil.isNull(str)) {
            return null;
        }
		return DateUtil.convertStringToDate(str, getDateTimeFormat());
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		setValue("lastUpdateTime", DateUtil.convertDateToString(lastUpdateTime,
				getDateTimeFormat()));
	}

	/**
	 * 获取日期时间格式化的格式
	 */
	public String getDateTimeFormat() {
		return getValue("dateTimeFormat");
	}
}
