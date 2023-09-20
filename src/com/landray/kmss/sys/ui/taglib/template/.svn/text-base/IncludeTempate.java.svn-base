/**
 * 
 */
package com.landray.kmss.sys.ui.taglib.template;

/**
 * 抽象使用模版类，主要添加作为模版include其他jsp时，给jsp增加参数，标识是模板方式调用，而不是直接访问。
 * 
 * @author 傅游翔
 * 
 */
@SuppressWarnings("serial")
public abstract class IncludeTempate extends AbstractTemplateTag {

	protected boolean isByInclude() {
		return hasTemplateContext() && getTemplateContext().isInclude();
	}

	protected boolean hasTemplateContext() {
		return TemplateContext.hasContext(pageContext);
	}

}
