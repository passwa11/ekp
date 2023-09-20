package com.landray.kmss.sys.handover.constant;

public interface SysHandoverConstant {
	/**
	 * 模块配置交接
	 */
	public final int HANDOVER_TYPE_CONFIG = 1;

	/**
	 * 文档流程实例交接
	 */
	public final int HANDOVER_TYPE_DOC = 2;

	/**
	 * 文档权限交接
	 */
	public final int HANDOVER_TYPE_AUTH = 3;

	/**
	 * 事项交接
	 */
	public final int HANDOVER_TYPE_ITEM = 4;

	/**
	 * 交接状态：等待执行
	 */
	public final int HANDOVER_STATE_WAIT = 0;

	/**
	 * 交接状态：执行成功
	 */
	public final int HANDOVER_STATE_SUCC = 1;

	/**
	 * 交接状态：执行失败
	 */
	public final int HANDOVER_STATE_FAIL = 2;

	/**
	 * 交接状态：执行中
	 */
	public final int HANDOVER_STATE_EXECUTING = 3;

}
