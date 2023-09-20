package com.landray.kmss.km.imeeting.handover;

import java.util.ArrayList;
import java.util.List;

import com.landray.kmss.sys.handover.interfaces.config.HandoverItem;
import com.landray.kmss.sys.handover.interfaces.config.IHandoverProvider;
import com.landray.kmss.util.ResourceUtil;

public class KmImeetingBookItemProvider implements IHandoverProvider {

	@Override
	public List<HandoverItem> items() throws Exception {
		List<HandoverItem> items = new ArrayList<HandoverItem>();
		// 会议发起人(单值)
		addHandoverItem(items, "docCreator",
				ResourceUtil
						.getString("km-imeeting:kmImeetingBook.docCreator"));
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
		HandoverItem item = new HandoverItem(fdAttribute, messageKey,
				new KmImeetingBookItemHandler(fdAttribute));
		items.add(item);
	}
}
