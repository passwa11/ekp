package com.landray.kmss.sys.ui.xml.model;

import java.util.ArrayList;
import java.util.List;

public class SysUiLayout extends SysUiXmlBase {

	private String path;
	private String thumbPath;

	public SysUiLayout() {
	}
	public SysUiLayout(String id, String name, String attribute, String type,
			SysUiCode body, String css, String thumb, String help, String kind,
			String _for, String path, String thumbPath) {
		this.fdId = id;
		this.fdName = name;
		this.fdAttribute = attribute;
		this.fdBody = body;
		this.fdCss = css;
		this.fdType = type;
		this.fdThumb = thumb;
		this.fdHelp = help;
		this.fdKind = kind;
		this.fdFor = _for;
		this.path = path;
		this.thumbPath = thumbPath;
	}

	protected String fdKind;
	protected String fdFor;
	protected String fdType;
	protected String fdAttribute;
	protected SysUiCode fdBody;
	protected String fdCss;
	protected List<SysUiVar> fdVars;

	public String getFdKind() {
		return fdKind;
	}

	public void setFdKind(String fdKind) {
		this.fdKind = fdKind;
	}

	public String getFdFor() {
		return fdFor;
	}

	public void setFdFor(String fdFor) {
		this.fdFor = fdFor;
	}

	public SysUiCode getFdBody() {
		return fdBody;
	}

	public void setFdBody(SysUiCode fdBody) {
		this.fdBody = fdBody;
	}

	public String getFdCss() {
		return fdCss;
	}

	public void setFdCss(String fdCss) {
		this.fdCss = fdCss;
	}

	public List<SysUiVar> getFdVars() {
		if (this.fdVars == null) {
			this.fdVars = new ArrayList<SysUiVar>();
		}
		return fdVars;
	}

	public void setFdVars(List<SysUiVar> fdVars) {
		this.fdVars = fdVars;
	}

	public String getFdAttribute() {
		return fdAttribute;
	}

	public void setFdAttribute(String fdAttribute) {
		this.fdAttribute = fdAttribute;
	}

	public String getFdType() {
		return fdType;
	}

	public void setFdType(String fdType) {
		this.fdType = fdType;
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
