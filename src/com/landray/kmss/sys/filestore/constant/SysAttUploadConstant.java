package com.landray.kmss.sys.filestore.constant;

public interface SysAttUploadConstant {
	/**
	 * 文件状态 0 切片完成 ， 1 切片上传中， 2 上传合并完成。
	 */
	public final static int SYS_ATT_FILE_STATUS_Init = 0;

	public final static int SYS_ATT_FILE_STATUS_UPLOADING = 1;

	public final static int SYS_ATT_FILE_STATUS_UPLOADED = 2;

	/**
	 * 切片状态 0 初始转台 ， 1 切片上传中， 2 上传完毕。
	 */
	public final static int SYS_ATT_SLICE_STATUS_INIT = 0;

	public final static int SYS_ATT_SLICE_STATUS_UPLOADING = 1;

	public final static int SYS_ATT_SLICE_STATUS_UPLOADED = 2;

	/**
	 * 默认配置,过期时间,60s
	 */
	public final static long SYS_ATT_CONFIG_EXPIRETIME = 60L * 1000L;

	/**
	 * 默认配置,切片大小5m
	 */
	public final static long SYS_ATT_CONFIG_SLICE_SIZE = 5L * 1024L * 1024L;

	/**
	 * 默认配置,小附件最大大小
	 */
	public final static long SYS_ATT_CONFIG_FILE_SIZE = 100L * 1024L * 1024L;

	/**
	 * 默认配置,未上传完切片存放期限，1个月
	 */
	public final static int SYS_ATT_CONFIG_SLICE_EXPIRE = 1;
}
