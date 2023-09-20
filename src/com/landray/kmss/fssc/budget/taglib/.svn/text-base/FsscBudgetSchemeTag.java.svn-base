package com.landray.kmss.fssc.budget.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.landray.kmss.eop.basedata.model.EopBasedataBudgetScheme;
import com.landray.kmss.eop.basedata.service.IEopBasedataBudgetSchemeService;
import com.landray.kmss.fssc.common.util.FsscCommonUtil;
import com.landray.kmss.util.SpringBeanUtil;

public class FsscBudgetSchemeTag extends TagSupport{
	protected IEopBasedataBudgetSchemeService eopBasedataBudgetSchemeService;
	
	public IEopBasedataBudgetSchemeService getEopBasedataBudgetSchemeService() {
		if(eopBasedataBudgetSchemeService==null){
			eopBasedataBudgetSchemeService=(IEopBasedataBudgetSchemeService) SpringBeanUtil.getBean("eopBasedataBudgetSchemeService");
		}
		return eopBasedataBudgetSchemeService;
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = -97206922070209413L;
	/**
	 * 判断预算方案是否包含当前维度
	 */
	/**
	 * 预算方案ID
	 */
	private String fdSchemeId;
	
	public String getFdSchemeId() {
		return fdSchemeId;
	}
	public void setFdSchemeId(String fdSchemeId) {
		this.fdSchemeId = fdSchemeId;
	}
	/**
	 * 判断类型，dimension  维度，period 期间
	 */
	private String type;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * 维度值
	 */
	private Object value;
	
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	@Override
    public int doStartTag()throws JspException{
		try {
			EopBasedataBudgetScheme scheme=(EopBasedataBudgetScheme) getEopBasedataBudgetSchemeService().findByPrimaryKey(fdSchemeId);
			if("period".equals(type)) {
				if(FsscCommonUtil.isContain(scheme.getFdPeriod(), value+";", ";")){
					return 1;
				}
			}else{
				if(FsscCommonUtil.isContain(scheme.getFdDimension(), value+";", ";")){
					return 1;
				}
			}
		} catch (Exception e) {
			return 0;
		}
		return 0;
	}

}
