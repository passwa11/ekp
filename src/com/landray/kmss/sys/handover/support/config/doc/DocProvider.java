package com.landray.kmss.sys.handover.support.config.doc;

import java.util.ArrayList;
import java.util.List;

import com.landray.kmss.sys.handover.interfaces.config.HandoverItem;
import com.landray.kmss.sys.handover.support.config.doc.handler.HandlerHandler;
import com.landray.kmss.sys.handover.support.config.doc.handler.LaterHandlerHandler;
import com.landray.kmss.sys.handover.support.config.doc.handler.OptHandlerHandler;
import com.landray.kmss.sys.handover.support.config.doc.handler.OtherCanViewHandler;
import com.landray.kmss.sys.handover.support.config.doc.handler.PrivilegerHandler;
import com.landray.kmss.util.ResourceUtil;

/**
 * 默认分类/模板provider
 * 
 * @author tanyouhao
 * @date 2015-12-18
 * @version 1.0
 * 
 */
public class DocProvider extends AbstractDocProvider {

	@Override
    public List<HandoverItem> items() throws Exception {
		List<HandoverItem> items = new ArrayList<HandoverItem>();
		//默认添加
		addHandoverItem(items, new HandlerHandler(),
				getMessage("handlerIds"));
		addHandoverItem(items,new LaterHandlerHandler(),
				getMessage("handlerIds_later"));
		addHandoverItem(items, new OptHandlerHandler(),
				getMessage("optHandlerIds"));
		addHandoverItem(items, new OtherCanViewHandler(),
				getMessage("otherCanViewCurNodeIds"));
		addHandoverItem(items, new PrivilegerHandler(),
				getMessage("privilegerIds"));
		return items;
	}

	/**
	 * 根据后缀返回message
	 * 
	 * @param suffix
	 * @return
	 */
	private String getMessage(String suffix) {
		String pre = "sys-handover-support-config-doc:sysHandoverConfigHandler.";
		return ResourceUtil.getString(pre + suffix);
	}
}
