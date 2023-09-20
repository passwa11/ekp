package com.landray.kmss.sys.ui.xml.model;

public class SysUiVarKind extends SysUiXmlBase {
	public SysUiVarKind(String id, String name, String thumb, String help,
			String file, String config) {
		this.fdId = id;
		this.fdName = name;
		this.fdThumb = thumb;
		this.fdHelp = help;
		this.fdFile = file;
		this.fdConfig = config;
	}

	protected String fdFile;

	protected String fdConfig;

	public String getFdFile() {
		return fdFile;
	}

	public void setFdFile(String fdFile) {
		this.fdFile = fdFile;
	}

	public String getFdConfig() {
		return fdConfig;
	}

	public void setFdConfig(String fdConfig) {
		this.fdConfig = fdConfig;
	}
}
