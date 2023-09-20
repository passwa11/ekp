package com.landray.kmss.km.signature.handover;

import java.util.ArrayList;
import java.util.List;

import com.landray.kmss.sys.handover.interfaces.config.HandoverItem;
import com.landray.kmss.sys.handover.interfaces.config.IHandoverProvider;
import com.landray.kmss.util.ResourceUtil;

/**
 * 工作交接提供类（提供需要交接的属性）
 * 
 * @author 潘永辉 2019年3月1日
 *
 */
public class KmSignatureMainItemProvider implements IHandoverProvider {

	@Override
	public List<HandoverItem> items() throws Exception {
		List<HandoverItem> items = new ArrayList<HandoverItem>();
		// 授权用户（个人签章：单值，单位签章：多值）
		addHandoverItem(items, "fdUsers", ResourceUtil.getString("km-signature:signature.users"));
		return items;
	}

	/**
	 * 增加属性
	 * 
	 * @param items
	 * @param fdAttribute
	 * @param messageKey
	 * @throws Exception
	 */
	public void addHandoverItem(List<HandoverItem> items, String fdAttribute,
			String messageKey) throws Exception {
		/**
		 * 构建交接项，需要传入处理实现类
		 * <li>fdAttribute：交接的属性名称
		 * <li>messageKey：交接属性的显示值
		 * <li>handler：交接处理实现类（在实现里需要传入交接的属性名称）
		 */
		HandoverItem item = new HandoverItem(fdAttribute, messageKey, new KmSignatureMainItemHandler(fdAttribute));
		items.add(item);
	}

}
