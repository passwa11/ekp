package com.landray.kmss.sys.ui.xml.model;

import java.util.ArrayList;
import java.util.List;

public class SysUiRender extends SysUiXmlBase {
	private String path;
	private String thumbPath;
	public SysUiRender() {

	}
	public SysUiRender(String id, String name, String format, String type,
			String attribute, SysUiCode body, String thumb, String css,
			String help, String _for, String path, String thumbPath) {
		this.fdId = id;
		this.fdName = name;
		this.fdFormat = format;
		this.fdAttribute = attribute;
		this.fdBody = body;
		this.fdType = type;
		this.fdCss = css;
		this.fdThumb = thumb;
		this.fdHelp = help;
		this.fdFor = _for;
		this.path = path;
		this.setThumbPath(thumbPath);
	}

	protected String fdFormat;
	protected String fdAttribute;
	protected SysUiCode fdBody;
	protected String fdType;
	protected String fdCss;
	private String fdFor;
	public String getFdFor() {
		return fdFor;
	}

	public void setFdFor(String fdFor) {
		this.fdFor = fdFor;
	}

	protected List<SysUiVar> fdVars;

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

	public String getFdType() {
		return fdType;
	}

	public void setFdType(String fdType) {
		this.fdType = fdType;
	}

	public String getFdFormat() {
		return fdFormat;
	}

	public void setFdFormat(String fdFormat) {
		this.fdFormat = fdFormat;
	}

	public String getFdAttribute() {
		return fdAttribute;
	}

	public void setFdAttribute(String fdAttribute) {
		this.fdAttribute = fdAttribute;
	}

	public SysUiCode getFdBody() {
		return fdBody;
	}

	public void setFdBody(SysUiCode fdBody) {
		this.fdBody = fdBody;
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
