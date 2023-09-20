package com.landray.kmss.km.archives.model;

import java.util.List;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.km.archives.forms.KmArchivesDestroyTemplateForm;
import com.landray.kmss.sys.lbpmservice.interfaces.ISysLbpmTemplateModel;
import com.landray.kmss.sys.right.interfaces.ExtendAuthModel;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.DateUtil;

/**
 * 鉴定申请模板
 */
public class KmArchivesDestroyTemplate extends ExtendAuthModel
		implements ISysLbpmTemplateModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

	private String fdName;

    private List sysWfTemplateModels;
    
    private Integer fdDefaultFlag;

	@Override
	public Class<KmArchivesDestroyTemplateForm> getFormClass() {
		return KmArchivesDestroyTemplateForm.class;
    }

    @Override
	public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("docCreateTime", new ModelConvertor_Common("docCreateTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
            toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
            toFormPropertyMap.put("authReaders", new ModelConvertor_ModelListToString("authReaderIds:authReaderNames", "fdId:fdName"));
            toFormPropertyMap.put("authEditors", new ModelConvertor_ModelListToString("authEditorIds:authEditorNames", "fdId:fdName"));
        }
        return toFormPropertyMap;
    }

	/**
	 * 名称
	 */
	public String getFdName() {
		return this.fdName;
	}

	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

    @Override
	public void recalculateFields() {
        super.recalculateFields();
    }

    @Override
	public List getSysWfTemplateModels() {
        return this.sysWfTemplateModels;
    }

    @Override
	public void setSysWfTemplateModels(List sysWfTemplateModels) {
        this.sysWfTemplateModels = sysWfTemplateModels;
    }

	/**
	 * 返回 所有人可阅读标记
	 */
	@Override
	public Boolean getAuthReaderFlag() {
		if (getAuthNotReaderFlag() != null
				&& getAuthNotReaderFlag().booleanValue()) {
			return new Boolean(false);
		}
		if (ArrayUtil.isEmpty(getAuthReaders())) {
            return new Boolean(true);
        } else {
            return new Boolean(false);
        }
	}

	@Override
	public String getDocStatus() {
		return "30";
	}

	@Override
	public String getDocSubject() {
		return null;
	}

	/*
	 * 所有人不可阅读标记
	 */
	private Boolean authNotReaderFlag = new Boolean(false);

	public Boolean getAuthNotReaderFlag() {
		return authNotReaderFlag;
	}

	public void setAuthNotReaderFlag(Boolean authNotReaderFlag) {
		this.authNotReaderFlag = authNotReaderFlag;
	}

	/**
	 * 是否默认标记（0、非；1、是）
	 */
	public Integer getFdDefaultFlag() {
		if (fdDefaultFlag == null) {
			fdDefaultFlag = 0;
		}
		return fdDefaultFlag;
	}

	/**
	 * 是否默认标记（0、非；1、是）
	 */
	public void setFdDefaultFlag(Integer fdDefaultFlag) {
		this.fdDefaultFlag = fdDefaultFlag;
	}
}
