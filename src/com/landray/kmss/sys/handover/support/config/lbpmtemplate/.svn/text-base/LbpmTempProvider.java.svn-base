package com.landray.kmss.sys.handover.support.config.lbpmtemplate;

import java.util.ArrayList;
import java.util.List;

import com.landray.kmss.sys.handover.interfaces.config.HandoverItem;
import com.landray.kmss.util.ResourceUtil;

/**
 * 全系统流程模板
 * 
 * @author tanyouhao
 *
 */
public class LbpmTempProvider extends AbstractLbpmTempProvider {

	@Override
    public List<HandoverItem> items() throws Exception {
		List<HandoverItem> items = new ArrayList<HandoverItem>();
        addHandoverItem(items, AbstractLbpmTemplateHandler.ATT_HANDLERIDS, getMessage("handlerPeop"));	
        addHandoverItem(items, AbstractLbpmTemplateHandler.ATT_OPTHANDLERIdS, getMessage("optional"));	
        addHandoverItem(items, AbstractLbpmTemplateHandler.ATT_PRIVILEGERIDS, getMessage("privilege"));	
        addHandoverItem(items, AbstractLbpmTemplateHandler.ATT_O_CANVIEWCURNODEIDS, getMessage("adviceRead"));	
		return items;
	}
	
	/**
	 * 根据后缀返回message
	 * 
	 * @param suffix
	 * @return
	 */
	private String getMessage(String suffix) {
		String pre = "sys-handover-support-config-lbpmtemplate:sysHandoverConfigHandler.";
		return ResourceUtil.getString(pre + suffix);
	}

}
