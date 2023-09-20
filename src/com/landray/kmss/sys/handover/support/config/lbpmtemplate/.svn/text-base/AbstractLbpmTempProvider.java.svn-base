package com.landray.kmss.sys.handover.support.config.lbpmtemplate;

import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;

import com.landray.kmss.sys.handover.interfaces.config.HandoverItem;
import com.landray.kmss.sys.handover.interfaces.config.IHandoverHandler;
import com.landray.kmss.sys.handover.interfaces.config.IHandoverProvider;

/**
 * 流程模板provider抽象类
 * 
 * @author tanyouhao
 * @date 2014-11-13
 * @version 1.0
 * 
 */
public abstract class AbstractLbpmTempProvider implements IHandoverProvider {
	
	/**
	 * 增加item
	 * 
	 * @param items 待增加的item集合
	 * @param fdAttribute item handler字段属性
	 * @param messageKey 资源文件key
	 * @throws Exception
	 */
	public void addHandoverItem(List<HandoverItem> items,String fdAttribute, String messageKey)
			throws Exception {
		IHandoverHandler handler = new LbpmTemplateHandler(fdAttribute);
		PropertyUtils.getProperty(handler, "fdAttribute");
		HandoverItem item = new HandoverItem(PropertyUtils.getProperty(handler,
				"fdAttribute").toString(), messageKey, handler);
		items.add(item);
	}

}
