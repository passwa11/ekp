package com.landray.kmss.sys.portal.model.export;

import java.util.List;

public class Portlet {

	private String title;
	private String subtitle;
	private String titleicon;
	private String titleimg;
	private String refresh;
	private String extendClass;
	private String sourceId;
	private String sourceName;
	private String format;
	private String formats;
	private List<Operation> operations;
	private SourceOpt sourceOpt;
	private String renderId;
	private String renderName;
	private RenderOpt renderOpt;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSubtitle() {
		return subtitle;
	}
	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}
	public String getTitleicon() {
		return titleicon;
	}
	public void setTitleicon(String titleicon) {
		this.titleicon = titleicon;
	}
	public String getTitleimg() { return titleimg; }
	public void setTitleimg(String titleimg) { this.titleimg = titleimg; }
	public String getRefresh() {
		return refresh;
	}
	public void setRefresh(String refresh) {
		this.refresh = refresh;
	}
	public String getExtendClass() {
		return extendClass;
	}
	public void setExtendClass(String extendClass) {
		this.extendClass = extendClass;
	}
	public String getSourceId() {
		return sourceId;
	}
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
	public String getSourceName() {
		return sourceName;
	}
	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public String getFormats() {
		return formats;
	}
	public void setFormats(String formats) {
		this.formats = formats;
	}
	public List<Operation> getOperations() {
		return operations;
	}
	public void setOperations(List<Operation> operations) {
		this.operations = operations;
	}
	public SourceOpt getSourceOpt() {
		return sourceOpt;
	}
	public void setSourceOpt(SourceOpt sourceOpt) {
		this.sourceOpt = sourceOpt;
	}
	public String getRenderId() {
		return renderId;
	}
	public void setRenderId(String renderId) {
		this.renderId = renderId;
	}
	public String getRenderName() {
		return renderName;
	}
	public void setRenderName(String renderName) {
		this.renderName = renderName;
	}
	public RenderOpt getRenderOpt() {
		return renderOpt;
	}
	public void setRenderOpt(RenderOpt renderOpt) {
		this.renderOpt = renderOpt;
	}
}
