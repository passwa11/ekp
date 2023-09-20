package com.landray.kmss.sys.handover.support.config.doc;

import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;

import com.landray.kmss.sys.handover.interfaces.config.HandoverItem;
import com.landray.kmss.sys.handover.interfaces.config.IHandoverHandler;
import com.landray.kmss.sys.handover.interfaces.config.IHandoverProvider;

/**
 * 文档类provider抽象类,</t>供各个模块文档类provider继承
 * 
 * @author tanyouhao
 * @date 2015-12-18
 * @version 1.0
 * 
 */
public abstract class AbstractDocProvider implements IHandoverProvider {
	/**
	 * 增加item
	 * 
	 * @param items
	 *            待增加的item集合 必填
	 * @param handler
	 *            item对应的handler
	 * @param messageKey
	 *            资源文件key 必填
	 * @param url
	 *            分类/模板 view页面url 可为空
	 * @throws Exception
	 */
	public void addHandoverItem(List<HandoverItem> items, IHandoverHandler handler, String messageKey) throws Exception {
		HandoverItem item = new HandoverItem(PropertyUtils.getProperty(handler, "fdAttribute").toString(), messageKey, handler);
		items.add(item);
	}
}
