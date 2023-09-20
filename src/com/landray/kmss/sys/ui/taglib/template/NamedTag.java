/**
 * 
 */
package com.landray.kmss.sys.ui.taglib.template;

/**
 * 可命名标签，区块，覆盖，重写标签都是可命名标签。通过name来实现区块内容替换.
 * 
 * @author 傅游翔
 * 
 */
@SuppressWarnings("serial")
public abstract class NamedTag extends BaseTag {

	protected String name;

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	@Override
    protected void clearResource() {
		this.name = null;
		super.clearResource();
	}
}
