/**
 * 
 */
package com.landray.kmss.sys.ui.taglib.criteria;

import com.landray.kmss.sys.ui.taglib.widget.ref.AttributeUtil;

import net.sf.json.JSONObject;

/**
 * @author 傅游翔
 * 
 */
@SuppressWarnings("serial")
public class TabCriterionTag extends CriterionTag {


	@Override
	public String getType() {
		if (this.type == null) {
			return "lui/criteria!TabCriterion";
		}
		return this.type;
	}

	@Override
	protected void postBuildConfigJson(JSONObject cfg) {
		cfg.put("expand", true);
		super.postBuildConfigJson(cfg);
		AttributeUtil.putAttrsToConfig(pageContext, "criterionAttrs", cfg);
	}
}
