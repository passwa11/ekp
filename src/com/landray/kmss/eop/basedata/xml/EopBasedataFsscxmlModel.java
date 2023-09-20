package com.landray.kmss.eop.basedata.xml;

import com.landray.kmss.util.ResourceUtil;

/**
 * 部件模型
 * 
 * @author
 *
 */
public class EopBasedataFsscxmlModel {

	public EopBasedataFsscxmlModel() {
	}
	public EopBasedataFsscxmlModel(String name, String keycol) {
		this.name = name;
		this.keycol = keycol;
	}
	
	public String getName() {
		return ResourceUtil.getMessage(this.name);
	}

	public void setName(String name) {
		this.name = name;
	}

	protected String name;
	
	protected String keycol;

	public String getKeycol() {
		return keycol;
	}
	public void setKeycol(String keycol) {
		this.keycol = keycol;
	}
	
	
}
