package com.landray.kmss.sys.organization.interfaces;

import java.util.List;

public interface ISysOrgPost extends ISysOrgElement {
	/**
	 * @return 岗位下的个人列表
	 */
	public abstract List getFdPersons();
}
