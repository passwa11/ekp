package com.landray.kmss.sys.portal.cloud.dto;

import java.util.Locale;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.landray.kmss.web.util.RequestUtils;

/**
 * 数据源-模块VO
 * @date  2019/1/21 18:44
 */
// 有些属性不需要，不写该注解会报错"Unrecognized field"
@JsonIgnoreProperties(ignoreUnknown = true)
public class SysPortalDataModuleVO {
    /**
     * 模块编码
     */
    private String fdCode;
    /**
	 * 模块名称（messageKey）
	 */
    private String fdName;

	private Map<String, String> dynamicProps;
    /**
     * 模块信息 md5值
     */
    private String fdMd5;
    /**
     * 系统编码
     */
	private String fdSysCode = RequestUtils.getAppName();
    /**
	 * 来源，10：内部系统 20：第三方
	 */
	private int fdSource = 10;

	public Map<String, String> getDynamicProps() {
		return dynamicProps;
	}

	public void setDynamicProps(Map<String, String> dynamicProps) {
		this.dynamicProps = dynamicProps;
	}

	public String getFdCode() {
		return fdCode;
	}

	public static void main(String[] args) {
		Locale locale = Locale.CHINA;
		System.out.println(locale);
		locale = Locale.CHINESE;
		System.out.println(locale);
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

	public String getFdMd5() {
		return fdMd5;
	}

	public void setFdMd5(String fdMd5) {
		this.fdMd5 = fdMd5;
	}

	public String getFdSysCode() {
		return fdSysCode;
	}

	public void setFdSysCode(String fdSysCode) {
		this.fdSysCode = fdSysCode;
	}

	public int getFdSource() {
		return fdSource;
	}

	public void setFdSource(int fdSource) {
		this.fdSource = fdSource;
	}
}
