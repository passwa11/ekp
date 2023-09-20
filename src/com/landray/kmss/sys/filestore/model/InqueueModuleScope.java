package com.landray.kmss.sys.filestore.model;

import java.util.Set;

public class InqueueModuleScope {

	public InqueueModuleScope(Set<String> fdModules) {
		this.isAll = false;
		this.fdModules = fdModules;
	}

	public InqueueModuleScope() {
		this.isAll = false;
	}

	public InqueueModuleScope(boolean isAll) {
		this.isAll = isAll;
	}

	private boolean isAll;

	public boolean isAll() {
		return isAll;
	}

	public void setAll(boolean isAll) {
		this.isAll = isAll;
	}

	/**
	 * 入队模块
	 */
	private Set<String> fdModules;

	public Set<String> getFdModules() {
		return fdModules;
	}

	public void setFdModules(Set<String> fdModules) {
		this.fdModules = fdModules;
	}
}
