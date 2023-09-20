package com.landray.kmss.sys.attachment.service;

import com.landray.kmss.sys.attachment.model.SysAttPlayLogConfig;
import com.landray.kmss.util.NumberUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;

public abstract class ISysAttPlayLogType {

	/**
	 * 类型
	 * 
	 * @return
	 */
	public abstract String getType();

	/**
	 * 获取进度<br>
	 * 可在此方法中进行参数解析
	 * 
	 * @param fdParam
	 * @return
	 */
	public String getNum(String fdParam) {

		if (StringUtil.isNull(fdParam)) {
			return String.valueOf(Integer.MIN_VALUE);
		}

		return NumberUtil.roundDecimal(Double.parseDouble(fdParam), 0);
	};

	/**
	 * 是否启用
	 * 
	 * @return
	 * @throws Exception
	 */
	public Boolean enable() throws Exception {

		return "true".equals((new SysAttPlayLogConfig()).getValue(
				"kmss.sys.attachment.play.log." + this.getType() + ".enabled"));
	};

	private final String prefix = "sys-attachment:sysAttachmentPlayLog.";

	/**
	 * 标题信息
	 * 
	 * @return
	 */
	public String getTitle() {
		return ResourceUtil.getString(prefix + this.getType() + ".title");
	};

	/**
	 * 描述信息
	 * 
	 * @return
	 */
	public String getDesc() {
		return ResourceUtil.getString(prefix + this.getType() + ".desc");
	};

	/**
	 * 单位
	 * 
	 * @return
	 */
	public String getUnit() {
		return ResourceUtil.getString(prefix + this.getType() + ".unit");
	};

}
