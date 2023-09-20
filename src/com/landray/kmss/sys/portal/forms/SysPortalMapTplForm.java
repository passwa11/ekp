package com.landray.kmss.sys.portal.forms;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_FormListToModelList;
import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.forms.IAttachmentForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.portal.model.SysPortalMapTpl;
import com.landray.kmss.sys.right.interfaces.ExtendAuthTmpForm;
import com.landray.kmss.util.AutoArrayList;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 地图模板
  */
public class SysPortalMapTplForm extends ExtendAuthTmpForm implements IAttachmentForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdName;

    private String docCreateTime;

    private String tplType;
    
    private String fdIsCustom;

    private String docCreatorId;

    private String docCreatorName;

    private String fdNavId;

    private String fdNavName;
    
	public String getFdIsCustom() {
		return fdIsCustom;
	}

	public void setFdIsCustom(String fdIsCustom) {
		this.fdIsCustom = fdIsCustom;
	}

	private List fdPortalNavForms = new AutoArrayList(
			SysPortalMapTplNavForm.class);

	public List getFdPortalNavForms() {
		return fdPortalNavForms;
	}

	public void setFdPortalNavForms(List fdPortalNavForms) {
		this.fdPortalNavForms = fdPortalNavForms;
	}

	private List fdMapInletForms = new AutoArrayList(
			SysPortalMapInletForm.class);

	public List getFdMapInletForms() {
		return fdMapInletForms;
	}

	public void setFdMapInletForms(List fdMapInletForms) {
		this.fdMapInletForms = fdMapInletForms;
	}
	
	/**
	 * 自定义导航数据源
	 */
	@SuppressWarnings("rawtypes")
	private List fdNavCustomForms = new AutoArrayList(
			SysPortalMapTplNavCustomForm.class);

	@SuppressWarnings("rawtypes")
    public List getFdNavCustomForms() {
		return fdNavCustomForms;
	}

	@SuppressWarnings("rawtypes")
	public void setFdNavCustomForms(List fdNavCustomForms) {
		this.fdNavCustomForms = fdNavCustomForms;
	}
	

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        fdName = null;
        docCreateTime = null;
        tplType = null;
        docCreatorId = null;
        docCreatorName = null;
        fdNavId = null;
        fdNavName = null;
        fdIsCustom = null;
        attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);
        fdNavCustomForms = new AutoArrayList(
        		SysPortalMapTplNavCustomForm.class);
		fdPortalNavForms = new AutoArrayList(SysPortalMapTplNavForm.class);
        super.reset(mapping, request);
    }

    @Override
    public Class<SysPortalMapTpl> getModelClass() {
        return SysPortalMapTpl.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.addNoConvertProperty("docCreateTime");
            toModelPropertyMap.put("authReaderIds", new FormConvertor_IDsToModelList("authReaders", SysOrgElement.class));
            toModelPropertyMap.put("authEditorIds", new FormConvertor_IDsToModelList("authEditors", SysOrgElement.class));
			toModelPropertyMap.put("fdMapInletForms",
					new FormConvertor_FormListToModelList("fdMapInlet",
							"fdMain"));
			toModelPropertyMap.put("fdNavCustomForms",
					new FormConvertor_FormListToModelList("fdNavCustom",
							"fdMain"));
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
     * 地图模板类型
     */
    public String getTplType() {
        return this.tplType;
    }

    /**
     * 地图模板类型
     */
    public void setTplType(String tplType) {
        this.tplType = tplType;
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

    /**
     * 系统导航
     */
    public String getFdNavId() {
        return this.fdNavId;
    }

    /**
     * 系统导航
     */
    public void setFdNavId(String fdNavId) {
        this.fdNavId = fdNavId;
    }

    /**
     * 系统导航
     */
    public String getFdNavName() {
        return this.fdNavName;
    }

    /**
     * 系统导航
     */
    public void setFdNavName(String fdNavName) {
        this.fdNavName = fdNavName;
    }
    
    /**
	 * 附件机制
	 */
	private AutoHashMap attachmentForms = new AutoHashMap(
			AttachmentDetailsForm.class);

	@Override
	public AutoHashMap getAttachmentForms() {
		return attachmentForms;
	}
	
}
