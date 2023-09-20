package com.landray.kmss.third.pda.model;
import java.util.Date;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.third.pda.forms.PdaModuleCateForm;

/**
 * 创建日期 2010-三月-24
 * @author zhuhq
 * 类别设置
 */
public class PdaModuleCate  extends BaseModel{


	/**
	 */
	private static final long serialVersionUID = -8669611140783078411L;


	/*
	 * 排序号
	 */
	protected Integer fdOrder;
	
	
	/*
	 * 名称
	 */
	protected String fdName;
	
	
	/*
	 * 创建时间
	 */
	protected Date docCreateTime;
	/**
	 * 修改时间
	 */
	protected Date docAlterTime;
	
	public Date getDocAlterTime() {
		return docAlterTime;
	}


	public void setDocAlterTime(Date docAlterTime) {
		this.docAlterTime = docAlterTime;
	}


	/*
	 * 创建者
	 */
	protected SysOrgElement docCreator;
	/**
	 * 修改人
	 */
	protected SysOrgElement docAlteror;
	
	
	
	public SysOrgElement getDocAlteror() {
		return docAlteror;
	}


	public void setDocAlteror(SysOrgElement docAlteror) {
		this.docAlteror = docAlteror;
	}


	public PdaModuleCate() {
		super();
	}
	
	
	/**
	 * @return 返回 排序号
	 */
	public Integer getFdOrder() {
		return fdOrder;
	}
	
	/**
	 * @param fdOrder 要设置的 排序号
	 */
	public void setFdOrder(Integer fdOrder) {
		this.fdOrder = fdOrder;
	}
	
	
	
	/**
	 * @return 返回 名称
	 */
	public String getFdName() {
		return SysLangUtil.getPropValue(this, "fdName", this.fdName);
	}
	
	/**
	 * @param fdName 要设置的 名称
	 */
	public void setFdName(String fdName) {
		this.fdName = fdName;
		SysLangUtil.setPropValue(this, "fdName", fdName);
	}
	
	
	/**
	 * @return 返回 创建时间
	 */
	public Date getDocCreateTime() {
		return docCreateTime;
	}
	
	/**
	 * @param docCreateTime 要设置的 创建时间
	 */
	public void setDocCreateTime(Date docCreateTime) {
		this.docCreateTime = docCreateTime;
	}
	
	
	
	public SysOrgElement getDocCreator() {
		return docCreator;
	}


	public void setDocCreator(SysOrgElement docCreator) {
		this.docCreator = docCreator;
	}


	@Override
    public Class getFormClass() {
		return PdaModuleCateForm.class;
	}

	
	private static ModelToFormPropertyMap  toFormPropertyMap = null;



	@Override
    public  ModelToFormPropertyMap getToFormPropertyMap() {
		if(toFormPropertyMap == null){
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
			toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
			toFormPropertyMap.put("docAlteror.fdId", "docAlterorId");
			toFormPropertyMap.put("docAlteror.fdName", "docAlterorName");
		}
		return toFormPropertyMap;
	}
	
	
}
