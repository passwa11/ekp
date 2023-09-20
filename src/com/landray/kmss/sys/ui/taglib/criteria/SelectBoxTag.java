/**
 * 
 */
package com.landray.kmss.sys.ui.taglib.criteria;

/**
 * @author 傅游翔
 * 
 */
@SuppressWarnings("serial")
public class SelectBoxTag extends CriteriaBaseTag {

	@Override
	public String getType() {
		if (this.type == null) {
			return "lui/criteria!CriterionSelectBox";
		}
		return this.type;
	}
}
