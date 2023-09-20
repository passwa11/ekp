package com.landray.kmss.sys.portal.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.portal.model.SysPortalMaterialTag;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 素材标签
  */
public class SysPortalMaterialTagForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdName;

	private String docCreateTime;

	private String fdQuotes;

	private String docAlterTime;

	private String docCreatorId;

	private String docCreatorName;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        fdName = null;
		docCreateTime = null;
		fdQuotes = null;
		docAlterTime = null;
		docCreatorId = null;
		docCreatorName = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<SysPortalMaterialTag> getModelClass() {
        return SysPortalMaterialTag.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.addNoConvertProperty("docCreateTime");
			toModelPropertyMap.addNoConvertProperty("docAlterTime");
        }
        return toModelPropertyMap;
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
	public String getDocCreateTime() {
		return this.docCreateTime;
	}

	/**
	 * 创建时间
	 */
	public void setDocCreateTime(String docCreateTime) {
		this.docCreateTime = docCreateTime;
	}

	/**
	 * 引用次数
	 */
	public String getFdQuotes() {
		return this.fdQuotes;
	}

	/**
	 * 引用次数
	 */
	public void setFdQuotes(String fdQuotes) {
		this.fdQuotes = fdQuotes;
	}

	/**
	 * 更新时间
	 */
	public String getDocAlterTime() {
		return this.docAlterTime;
	}

	/**
	 * 更新时间
	 */
	public void setDocAlterTime(String docAlterTime) {
		this.docAlterTime = docAlterTime;
	}

	/**
	 * 创建人
	 */
	public String getDocCreatorId() {
		return this.docCreatorId;
	}

	/**
	 * 创建人
	 */
	public void setDocCreatorId(String docCreatorId) {
		this.docCreatorId = docCreatorId;
	}

	/**
	 * 创建人
	 */
	public String getDocCreatorName() {
		return this.docCreatorName;
	}

	/**
	 * 创建人
	 */
	public void setDocCreatorName(String docCreatorName) {
		this.docCreatorName = docCreatorName;
	}
}
