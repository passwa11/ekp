package com.landray.kmss.sys.portal.xml.model;

import java.util.ArrayList;
import java.util.List;

import com.landray.kmss.sys.ui.xml.model.SysUiVar;
import com.landray.kmss.sys.ui.xml.model.SysUiXmlBase;

public class SysPortalFooter extends SysUiXmlBase {

	private String path;
	private String thumbPath;
	public SysPortalFooter() {
	}
	public SysPortalFooter(String id, String name, String file, String thumb,
			String help, String _for, String path, String thumbPath) {
		this.fdId = id;
		this.fdName = name;
		this.fdHelp = help;
		this.fdThumb = thumb;
		this.fdFile = file;
		this.fdFor = _for;
		this.path = path;
		this.thumbPath = thumbPath;
	}

	protected String fdFor;

	public String getFdFor() {
		return fdFor;
	}

	public void setFdFor(String fdFor) {
		this.fdFor = fdFor;
	}

	protected List<SysUiVar> fdVars;

	public List<SysUiVar> getFdVars() {
		if (this.fdVars == null) {
			this.fdVars = new ArrayList<SysUiVar>();
		}
		return fdVars;
	}

	public void setFdVars(List<SysUiVar> fdVars) {
		this.fdVars = fdVars;
	}

	protected String fdFile;

	public String getFdFile() {
		return fdFile;
	}

	public void setFdFile(String fdFile) {
		this.fdFile = fdFile;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getThumbPath() {
		return thumbPath;
	}

	public void setThumbPath(String thumbPath) {
		this.thumbPath = thumbPath;
	}

}
