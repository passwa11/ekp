/**
 * 
 */
package com.landray.kmss.sys.ui.taglib.list;

import com.landray.kmss.sys.ui.taglib.widget.WidgetTag;

/**
 * 列表视图标签
 * 
 * @author 傅游翔
 * 
 */
@SuppressWarnings("serial")
public class ListViewTag extends WidgetTag {
	// TODO 指定显示模式

	@Override
	public String getType() {
		if (this.type == null) {
			return "lui/listview/listview!ListView";
		}
		return this.type;
	}
}
