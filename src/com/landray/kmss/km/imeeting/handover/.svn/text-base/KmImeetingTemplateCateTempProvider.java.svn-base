package com.landray.kmss.km.imeeting.handover;

import java.util.ArrayList;
import java.util.List;

import com.landray.kmss.sys.handover.interfaces.config.HandoverItem;
import com.landray.kmss.sys.handover.support.config.catetemplate.AbstractCateTempHandler;
import com.landray.kmss.sys.handover.support.config.catetemplate.AbstractCateTempProvider;
import com.landray.kmss.util.ResourceUtil;

/**
 * 分类/模板provider
 * 
 * @author tanyouhao
 * @date 2014-11-13
 * @version 1.0
 * 
 */
public class KmImeetingTemplateCateTempProvider extends
		AbstractCateTempProvider {

	@Override
    public List<HandoverItem> items() throws Exception {
		String messagePre = "sys-handover-support-config-catetemplate:sysHandoverConfigHandler.";
		List<HandoverItem> items = new ArrayList<HandoverItem>();
		addHandoverItem(
				items,
				AbstractCateTempHandler.AUTH_READERS,
				ResourceUtil
						.getString("km-imeeting:kmImeetingTemplate.authReaders"));
		addHandoverItem(
				items,
				AbstractCateTempHandler.AUTH_EDITORS,
				ResourceUtil
						.getString("km-imeeting:kmImeetingTemplate.authEditors"));
		addHandoverItem(items, AbstractCateTempHandler.DEFAULT_READERIDS,
				ResourceUtil.getString(messagePre + "authTmpReaders"));
		addHandoverItem(items, AbstractCateTempHandler.AUTH_ATT_PRINTS,
				ResourceUtil.getString(messagePre + "authTmpAttPrints"));
		addHandoverItem(items, AbstractCateTempHandler.AUTH_ATT_COPYS,
				ResourceUtil.getString(messagePre + "authTmpAttCopys"));
		addHandoverItem(items, AbstractCateTempHandler.AUTH_ATT_DOWNLOADS,
				ResourceUtil.getString(messagePre + "authTmpAttDownloads"));
		return items;
	}
}
