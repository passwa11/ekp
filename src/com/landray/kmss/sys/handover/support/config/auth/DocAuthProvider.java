package com.landray.kmss.sys.handover.support.config.auth;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;

import com.landray.kmss.sys.handover.interfaces.config.HandoverItem;
import com.landray.kmss.sys.handover.interfaces.config.IHandoverHandler;
import com.landray.kmss.sys.handover.support.config.catetemplate.AbstractCateTempHandler;
import com.landray.kmss.sys.handover.support.config.catetemplate.AbstractCateTempProvider;
import com.landray.kmss.util.ResourceUtil;

/**
 * 文档权限交接提供者
 * 
 * @author 潘永辉 2016-10-25
 * 
 */
public class DocAuthProvider extends AbstractCateTempProvider {

	@Override
    public List<HandoverItem> items() throws Exception {
		List<HandoverItem> items = new ArrayList<HandoverItem>();
		// 可阅读者
		addHandoverItem(items, AbstractCateTempHandler.AUTH_READERS, getMessage("authReaders"));
		// 可编辑者
		addHandoverItem(items, AbstractCateTempHandler.AUTH_EDITORS, getMessage("authEditors"));
		// 流程意见阅读权限
		addHandoverItem(items, AbstractCateTempHandler.AUTH_LBPM_READERS, getMessage("authLbpmReaders"));
		// 附件可打印者
		addHandoverItem(items, "authAttPrints", getMessage("authAttPrints"));
		// 附件可拷贝者
		addHandoverItem(items, "authAttCopys", getMessage("authAttCopys"));
		// 附件可下载者
		addHandoverItem(items, "authAttDownloads", getMessage("authAttDownloads"));
		return items;
	}

	@Override
	public void addHandoverItem(List<HandoverItem> items, String fdAttribute,
			String messageKey) throws Exception {
		IHandoverHandler handler = new DocAuthItemHandler(fdAttribute);
		PropertyUtils.getProperty(handler, "fdAttribute");
		HandoverItem item = new HandoverItem(PropertyUtils.getProperty(handler,
				"fdAttribute").toString(), messageKey, handler);
		items.add(item);
	}

	/**
	 * 根据后缀返回message
	 * 
	 * @param suffix
	 * @return
	 */
	public static String getMessage(String suffix) {
		String pre = "sys-handover-support-config-auth:sysHandoverDocAuth.";
		return ResourceUtil.getString(pre + suffix);
	}

}
