package com.landray.kmss.sys.portal.model.export;

import java.util.List;

public class JPortal {

	private String panel;
	private String layoutId;
	private String layoutName;
	private LayoutOpt layoutOpt;
	private String height;
	private String heightExt;
	private String panelType;
	private String authReaderIds;
	private String authReaderNames;
	private List<Portlet> portlet;
	
	public String getPanel() {
		return panel;
	}
	public void setPanel(String panel) {
		this.panel = panel;
	}
	public String getLayoutId() {
		return layoutId;
	}
	public void setLayoutId(String layoutId) {
		this.layoutId = layoutId;
	}
	public String getLayoutName() {
		return layoutName;
	}
	public void setLayoutName(String layoutName) {
		this.layoutName = layoutName;
	}
	public LayoutOpt getLayoutOpt() {
		return layoutOpt;
	}
	public void setLayoutOpt(LayoutOpt layoutOpt) {
		this.layoutOpt = layoutOpt;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getHeightExt() {
		return heightExt;
	}
	public void setHeightExt(String heightExt) {
		this.heightExt = heightExt;
	}
	public String getPanelType() {
		return panelType;
	}
	public void setPanelType(String panelType) {
		this.panelType = panelType;
	}
	public String getAuthReaderIds() {
		return authReaderIds;
	}
	public void setAuthReaderIds(String authReaderIds) {
		this.authReaderIds = authReaderIds;
	}
	public String getAuthReaderNames() {
		return authReaderNames;
	}
	public void setAuthReaderNames(String authReaderNames) {
		this.authReaderNames = authReaderNames;
	}
	public List<Portlet> getPortlet() {
		return portlet;
	}
	public void setPortlet(List<Portlet> portlet) {
		this.portlet = portlet;
	}
}
