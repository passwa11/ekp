/**
 * 
 */
package com.landray.kmss.sys.ui.taglib.criteria;

import net.sf.json.JSONObject;

import com.landray.kmss.sys.ui.taglib.widget.BuildUtils;
import com.landray.kmss.sys.ui.taglib.widget.ref.AttributeUtil;

/**
 * @author 傅游翔
 * 
 */
@SuppressWarnings("serial")
public class CriterionPopupBoxTag extends CriteriaBaseTag {

	protected String title;

	protected String key;

	@Override
	public void release() {
		super.release();
		title = null;
		key = null;
	}

	@Override
	public String getType() {
		if (this.type == null) {
			return "lui/criteria/criterion_popup!CriterionPopupBox";
		}
		return this.type;
	}

	@Override
	protected void postBuildConfigJson(JSONObject cfg) {
		putConfigValue(cfg, "title", getTitle());
		putConfigValue(cfg, "key", getKey());
		super.postBuildConfigJson(cfg);
		AttributeUtil.putAttrsToConfig(pageContext, "criterionAttrs", cfg);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@Override
	protected String acquireString(String body) throws Exception {
		String type = "lui/popup!Popup";
		JSONObject config = new JSONObject();
		putConfigValue(config, "borderWidth", 1);
		// putConfigValue(config, "borderColor", "");
		// putConfigValue(config, "align", "");
		String newBody = BuildUtils
				.buildLUIHtml(null, type, null, config, body);
		return super.acquireString(newBody);
	}
}
