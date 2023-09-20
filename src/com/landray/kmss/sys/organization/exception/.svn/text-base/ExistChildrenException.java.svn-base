package com.landray.kmss.sys.organization.exception;

import com.landray.kmss.common.exception.KmssRuntimeException;
import com.landray.kmss.util.KmssMessage;

/**
 * 部门置为无效时，当前部门/机构下存在子部门/岗位/个人将抛出该异常
 * 
 * @author 李斌斌
 * 
 */
public class ExistChildrenException extends KmssRuntimeException {

	private static final long serialVersionUID = -1453341711879652625L;

	public ExistChildrenException() {
		super(
				new KmssMessage(
						"sys-organization:sysOrgDept.error.existChildren"));
	}
}
