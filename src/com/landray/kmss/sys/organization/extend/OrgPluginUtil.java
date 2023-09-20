/**
 * 
 */
package com.landray.kmss.sys.organization.extend;

import com.landray.kmss.framework.plugin.core.config.IExtension;
import com.landray.kmss.framework.service.plugin.Plugin;

/**
 * 组织架构机制相关扩展点调用的Util类。
 * 
 * @author 龚健
 * @see
 */
public abstract class OrgPluginUtil {
	/**
	 * 获得人员扩展属性的扩展点
	 */
	public static IExtension getPersonExtendProperty() {
		return Plugin.getExtension(PersonExtendProperty.EXTENSION_POINT, "*",
				PersonExtendProperty.PARAM_ITEM);
	}
}
