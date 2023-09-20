/**
 * 
 */
package com.landray.kmss.sys.ui.taglib.criteria;

import net.sf.json.JSONObject;

/**
 * 弹出筛选项集合
 * 
 * @author 傅游翔
 * 
 */
@SuppressWarnings("serial")
public class CriterionPopupTag extends CriteriaBaseTag {

	protected String title;

	@Override
	public void release() {
		super.release();
		title = null;
	}

	@Override
	public String getType() {
		if (this.type == null) {
			return "lui/criteria/criterion_popup!CriterionPopup";
		}
		return this.type;
	}

	@Override
	protected void postBuildConfigJson(JSONObject cfg) {
		cfg.put("title", title);
		super.postBuildConfigJson(cfg);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
