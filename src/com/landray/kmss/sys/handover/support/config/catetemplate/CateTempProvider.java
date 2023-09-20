package com.landray.kmss.sys.handover.support.config.catetemplate;

import java.util.ArrayList;
import java.util.List;

import com.landray.kmss.sys.handover.interfaces.config.HandoverItem;
import com.landray.kmss.util.ResourceUtil;

/**
 * 默认分类/模板provider
 * 
 * @author tanyouhao
 * @date 2014-11-13
 * @version 1.0
 * 
 */
public class CateTempProvider extends AbstractCateTempProvider {

	@Override
    public List<HandoverItem> items() throws Exception {
		List<HandoverItem> items = new ArrayList<HandoverItem>();
		addHandoverItem(items, AbstractCateTempHandler.DEFAULT_READERIDS,
				getMessage("authTmpReaders"));
		addHandoverItem(items, AbstractCateTempHandler.DEFAULT_EDITORIDS,
				getMessage("authTmpEditors"));
		addHandoverItem(items, AbstractCateTempHandler.AUTH_READERS,
				getMessage("authReaders"));
		addHandoverItem(items, AbstractCateTempHandler.AUTH_EDITORS,
				getMessage("authEditors"));
		addHandoverItem(items, AbstractCateTempHandler.AUTH_ATT_PRINTS,
				getMessage("authTmpAttPrints"));
		addHandoverItem(items, AbstractCateTempHandler.AUTH_ATT_COPYS,
				getMessage("authTmpAttCopys"));
		addHandoverItem(items, AbstractCateTempHandler.AUTH_ATT_DOWNLOADS,
				getMessage("authTmpAttDownloads"));
		return items;
	}

	/**
	 * 根据后缀返回message
	 * 
	 * @param suffix
	 * @return
	 */
	private String getMessage(String suffix) {
		String pre = "sys-handover-support-config-catetemplate:sysHandoverConfigHandler.";
		return ResourceUtil.getString(pre + suffix);
	}
}
