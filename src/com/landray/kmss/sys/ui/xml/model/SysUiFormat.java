package com.landray.kmss.sys.ui.xml.model;

public class SysUiFormat extends SysUiXmlBase {

	protected String fdDescription;
	protected String fdDefaultRender;
	protected String fdExample;

	public SysUiFormat() {

	}

	public SysUiFormat(String id, String name, String description,
			String render, String example, String help) {
		this.fdId = id;
		this.fdName = name;
		this.fdDescription = description;
		this.fdDefaultRender = render;
		this.fdExample = example;
		this.fdHelp = help;
	}

	public String getFdExample() {
		return fdExample;
	}

	public void setFdExample(String fdExample) {
		this.fdExample = fdExample;
	}

	public String getFdDescription() {
		return fdDescription;
	}

	public void setFdDescription(String fdDescription) {
		this.fdDescription = fdDescription;
	}

	public String getFdDefaultRender() {
		return fdDefaultRender;
	}

	public void setFdDefaultRender(String fdDefaultRender) {
		this.fdDefaultRender = fdDefaultRender;
	}

}
