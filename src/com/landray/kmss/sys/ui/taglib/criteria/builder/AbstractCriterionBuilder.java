/**
 * 
 */
package com.landray.kmss.sys.ui.taglib.criteria.builder;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;

import net.sf.json.JSONObject;

import com.landray.kmss.sys.config.dict.SysDictCommonProperty;
import com.landray.kmss.sys.ui.plugin.SysUiPluginUtil;
import com.landray.kmss.sys.ui.taglib.template.TargetUrlContentAcquirer;
import com.landray.kmss.sys.ui.taglib.widget.BuildUtils;
import com.landray.kmss.sys.ui.xml.model.SysUiCombin;
import com.landray.kmss.util.ResourceUtil;

/**
 * @author 傅游翔
 * 
 */
public abstract class AbstractCriterionBuilder implements CriterionBuilder {

	protected String buildCriterion(String title, String key, String selectHtml) {
		String selectBoxHtml = BuildUtils.buildLUIHtml(null,
				"lui/criteria!CriterionSelectBox", null, null, selectHtml);
		JSONObject cfg = new JSONObject();
		cfg.put("title", title);
		cfg.put("key", key);
		return BuildUtils.buildLUIHtml(null, "lui/criteria!Criterion", null,
				cfg, selectBoxHtml);
	}

	protected String buildCriterion(JSONObject cfg, String selectHtml) {
		String selectBoxHtml = BuildUtils.buildLUIHtml(null,
				"lui/criteria!CriterionSelectBox", null, null, selectHtml);
		return BuildUtils.buildLUIHtml(null, "lui/criteria!Criterion", null,
				cfg, selectBoxHtml);
	}

	protected String buildCriterionPopup(String title, String key,
			String selectHtml) {
		JSONObject config = new JSONObject();
		config.put("borderWidth", 1);
		String popupHtml = BuildUtils.buildLUIHtml(null, "lui/popup!Popup",
				null, config, selectHtml);

		JSONObject cfg = new JSONObject();
		cfg.put("title", title);
		cfg.put("key", key);
		String popupBoxHtml = BuildUtils.buildLUIHtml(null,
				"lui/criteria/criterion_popup!CriterionPopupBox", null, cfg,
				popupHtml);
		return popupBoxHtml;
	}

	protected String buildCriterionPopup(JSONObject cfg, String selectHtml) {
		JSONObject config = new JSONObject();
		config.put("borderWidth", 1);
		String popupHtml = BuildUtils.buildLUIHtml(null, "lui/popup!Popup",
				null, config, selectHtml);
		String popupBoxHtml = BuildUtils.buildLUIHtml(null,
				"lui/criteria/criterion_popup!CriterionPopupBox", null, cfg,
				popupHtml);
		return popupBoxHtml;
	}

	protected String getUrl(String ref, PageContext pageContext)
			throws JspException {
		SysUiCombin combin = SysUiPluginUtil.getCombins().get(ref);
		if (combin == null) {
			throw new JspTagException("ref '" + ref + "' combin is not exist!");
		}
		return TargetUrlContentAcquirer.coverUrl(combin.getFdFile(),
				pageContext);
	}

	protected String getTitle(SysDictCommonProperty property) {
		return ResourceUtil.getString(property.getMessageKey());
	}
}
