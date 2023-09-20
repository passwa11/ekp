package com.landray.kmss.sys.portal.model;

import java.util.Date;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.portal.forms.SysPortalMaterialTagForm;
import com.landray.kmss.util.DateUtil;

/**
  * 素材标签
  */
public class SysPortalMaterialTag extends BaseModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdName;

	private Date docCreateTime;

	private Integer fdQuotes;

	private Date docAlterTime;

	private SysOrgPerson docCreator;

    @Override
    public Class<SysPortalMaterialTagForm> getFormClass() {
        return SysPortalMaterialTagForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("docCreateTime",
					new ModelConvertor_Common("docCreateTime")
							.setDateTimeType(DateUtil.TYPE_DATETIME));
			toFormPropertyMap.put("docAlterTime",
					new ModelConvertor_Common("docAlterTime")
							.setDateTimeType(DateUtil.TYPE_DATETIME));
			toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
			toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
    }

    /**
     * 名称
     */
    public String getFdName() {
        return this.fdName;
    }

    /**
     * 名称
     */
    public void setFdName(String fdName) {
        this.fdName = fdName;
    }

	/**
	 * 创建时间
	 */
	public Date getDocCreateTime() {
		return this.docCreateTime;
	}

	/**
	 * 创建时间
	 */
	public void setDocCreateTime(Date docCreateTime) {
		this.docCreateTime = docCreateTime;
	}

	/**
	 * 引用次数
	 */
	public Integer getFdQuotes() {
		return this.fdQuotes;
	}

	/**
	 * 引用次数
	 */
	public void setFdQuotes(Integer fdQuotes) {
		this.fdQuotes = fdQuotes;
	}

	/**
	 * 更新时间
	 */
	public Date getDocAlterTime() {
		return this.docAlterTime;
	}

	/**
	 * 更新时间
	 */
	public void setDocAlterTime(Date docAlterTime) {
		this.docAlterTime = docAlterTime;
	}

	/**
	 * 创建人
	 */
	public SysOrgPerson getDocCreator() {
		return this.docCreator;
	}

	/**
	 * 创建人
	 */
	public void setDocCreator(SysOrgPerson docCreator) {
		this.docCreator = docCreator;
	}
}
