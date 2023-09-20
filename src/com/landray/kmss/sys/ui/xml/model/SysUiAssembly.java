package com.landray.kmss.sys.ui.xml.model;

public class SysUiAssembly extends SysUiXmlBase {

	public SysUiAssembly(String id, String name, String help, String thumb,
			String category) {
		this.fdId = id;
		this.fdName = name;
		this.fdHelp = help;
		this.fdThumb = thumb;
		this.fdCategory = category;
	}

	protected String fdCategory;

	public String getFdCategory() {
		return fdCategory;
	}

	public void setFdCategory(String fdCategory) {
		this.fdCategory = fdCategory;
	}

}
