package com.landray.kmss.sys.portal.cloud.dto;


import java.util.HashMap;
import java.util.Map;

import com.landray.kmss.web.util.RequestUtils;

/**
 * 数据源VO
 * @date  2019/1/21 18:44
 */
public class SysPortalDataSourceVO {
    /**
     * 编码
     */
    private String fdCode;
    /**
     * 数据源名称
     */
    private String fdName;

	private Map<String, String> dynamicProps;
	/**
	 * 系统标识
	 */
	private String fdSysCode = RequestUtils.getAppName();
    /**
     * 模块标识
     */
    private String fdModuleCode;
    /**
     * 数据格式
     */
	private String fdFormatCode;
    /**
     * 描述
     */
	private String fdDesc;
    /**
     * 是否匿名
     */
	private Boolean fdAnonymous = false;
    /**
     * 数据源内容  json
     */
	private String fdContent;
    /**
	 * 系统类型 10：内部系统 20：第三方
	 */
	private int fdSource = 10;
	/**
	 * 默认呈现集合,分隔
	 */
	private String fdRenders;
	/**
	 * 数据源缩略图
	 * <p>
	 * 使用iframe框架的EKP等公司系统的组件，在组件选择时，需展示原业务数据的缩略图； 组件拖至页面配置窗口时也应显示原业务数据的缩略图；
	 * </p>
	 */
	private String fdThumbnail;

	public String getFdThumbnail() {
		return fdThumbnail;
	}

	public void setFdThumbnail(String fdThumbnail) {
		this.fdThumbnail = fdThumbnail;
	}

	/**
	 * 数据源多语言 信息 key 为语言 value 为对应的 标识与值
	 */
	private Map<String, Map<String, String>> resourceMap = new HashMap<>();

	public Map<String, Map<String, String>> getResourceMap() {
		return resourceMap;
	}

	public void setResourceMap(Map<String, Map<String, String>> resourceMap) {
		this.resourceMap = resourceMap;
	}

	public Map<String, String> getDynamicProps() {
		return dynamicProps;
	}

	public void setDynamicProps(Map<String, String> dynamicProps) {
		this.dynamicProps = dynamicProps;
	}

	public String getFdCode() {
		return fdCode;
	}

	public void setFdCode(String fdCode) {
		this.fdCode = fdCode;
	}

	public String getFdName() {
		return fdName;
	}

	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	public String getFdSysCode() {
		return fdSysCode;
	}

	public void setFdSysCode(String fdSysCode) {
		this.fdSysCode = fdSysCode;
	}

	public String getFdModuleCode() {
		return fdModuleCode;
	}

	public void setFdModuleCode(String fdModuleCode) {
		this.fdModuleCode = fdModuleCode;
	}

	public String getFdFormatCode() {
		return fdFormatCode;
	}

	public void setFdFormatCode(String fdFormatCode) {
		this.fdFormatCode = fdFormatCode;
	}

	public String getFdDesc() {
		return fdDesc;
	}

	public void setFdDesc(String fdDesc) {
		this.fdDesc = fdDesc;
	}

	public Boolean getFdAnonymous() {
		return fdAnonymous;
	}

	public void setFdAnonymous(Boolean fdAnonymous) {
		this.fdAnonymous = fdAnonymous;
	}

	public String getFdContent() {
		return fdContent;
	}

	public void setFdContent(String fdContent) {
		this.fdContent = fdContent;
	}

	public int getFdSource() {
		return fdSource;
	}

	public void setFdSource(int fdSource) {
		this.fdSource = fdSource;
	}

	public String getFdRenders() {
		return fdRenders;
	}

	public void setFdRenders(String fdRenders) {
		this.fdRenders = fdRenders;
	}
}
