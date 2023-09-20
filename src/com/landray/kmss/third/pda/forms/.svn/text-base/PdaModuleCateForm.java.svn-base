package com.landray.kmss.third.pda.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.third.pda.model.PdaModuleCate;


/**
 *
 * @author zhuhq
 */
public class PdaModuleCateForm extends ExtendForm {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5982260763825591515L;
	/*
	 * 排序号
	 */
    private String fdOrder = null;
	/*
	 * 名称
	 */
    private String fdName = null;
	/*
	 * 创建时间
	 */
    private String docCreateTime = null;
	/*
	 * 创建者
	 */
    private String docCreatorId = null;

    private String docCreatorName = null;
    /**
	 * 修改人的ID
	 */
     private String docAlterorId = null;
	 public String getDocAlterorId() {
		return docAlterorId;
	}

	public void setDocAlterorId(String docAlterorId) {
		this.docAlterorId = docAlterorId;
	}

	public String getDocAlterorName() {
		return docAlterorName;
	}

	public void setDocAlterorName(String docAlterorName) {
		this.docAlterorName = docAlterorName;
	}

	private String docAlterorName = null;
	/**
	 * @return 返回 排序号
	 */
	public String getFdOrder() {
		return fdOrder;
	}
	
	/**
	 * @param fdOrder 要设置的 排序号
	 */
	public void setFdOrder(String fdOrder) {
		this.fdOrder = fdOrder;
	}
	/**
	 * @return 返回 名称
	 */
	public String getFdName() {
		return fdName;
	}
	
	/**
	 * @param fdName 要设置的 名称
	 */
	public void setFdName(String fdName) {
		this.fdName = fdName;
	}
	/**
	 * @return 返回 创建时间
	 */
	public String getDocCreateTime() {
		return docCreateTime;
	}
	
	/**
	 * @param docCreateTime 要设置的 创建时间
	 */
	public void setDocCreateTime(String docCreateTime) {
		this.docCreateTime = docCreateTime;
	}
	/**
	 * @return 返回 创建者
	 */
	public String getDocCreatorId() {
		return docCreatorId;
	}
	
	/**
	 * @param docCreatorId 要设置的 创建者
	 */
	public void setDocCreatorId(String docCreatorId) {
		this.docCreatorId = docCreatorId;
	}
	
    

	public String getDocCreatorName() {
		return docCreatorName;
	}

	public void setDocCreatorName(String docCreatorName) {
		this.docCreatorName = docCreatorName;
	}

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
	    fdOrder = null;
	    fdName = null;
	    docCreateTime = null;
	    docCreatorId = null;
	    docCreatorName = null;
	    docAlterorId = null;
	    docAlterorName = null;
		super.reset(mapping, request);
    }

	@Override
    public Class getModelClass() {
		return PdaModuleCate.class;
	}
	
	private static FormToModelPropertyMap  toModelPropertyMap = null;
	
	@Override
    public  FormToModelPropertyMap getToModelPropertyMap() {
		if(toModelPropertyMap == null){
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.put("docCreatorId", new FormConvertor_IDToModel("docCreator", SysOrgElement.class));
			toModelPropertyMap.put("docAlterorId", new FormConvertor_IDToModel("docCreator", SysOrgElement.class));
		}
		return toModelPropertyMap;
	}
	
	
}
