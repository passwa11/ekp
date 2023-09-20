package com.landray.kmss.sys.portal.model;

import java.util.List;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToFormList;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.model.IAttachment;
import com.landray.kmss.sys.portal.forms.SysPortalMapTplForm;
import com.landray.kmss.sys.right.interfaces.ExtendAuthTmpModel;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.util.DateUtil;

/**
  * 地图模板
  */
public class SysPortalMapTpl extends ExtendAuthTmpModel implements IAttachment  {
	
    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdId;

    private String fdName;

    private Integer tplType;
    
    // 是否自定义导航数据源
    private Boolean fdIsCustom;

	private SysPortalMapTpl fdNav;

	// 系统导航信息
	protected List<SysPortalMapTplNav> fdPortalNav;
	
	/**
	 * 自定义导航数据源
	 */
	protected List<SysPortalMapTplNavCustom> fdNavCustom;

	// 便捷入口
	protected List<SysPortalMapInlet> fdMapInlet;
	
	public Boolean getFdIsCustom() {
		return fdIsCustom;
	}


	public void setFdIsCustom(Boolean fdIsCustom) {
		this.fdIsCustom = fdIsCustom;
	}


	public List<SysPortalMapTplNav> getFdPortalNav() {
		return fdPortalNav;
	}
	
	
	public List<SysPortalMapTplNavCustom> getFdNavCustom() {
		return fdNavCustom;
	}

	public void setFdNavCustom(List<SysPortalMapTplNavCustom> fdNavCustom) {
		this.fdNavCustom = fdNavCustom;
	}



	public void setFdPortalNav(List<SysPortalMapTplNav> fdPortalNav) {
		this.fdPortalNav = fdPortalNav;
	}

	public List<SysPortalMapInlet> getFdMapInlet() {
		return fdMapInlet;
	}

	public void setFdMapInlet(List<SysPortalMapInlet> fdMapInlet) {
		this.fdMapInlet = fdMapInlet;
	}

    @Override
    public Class<SysPortalMapTplForm> getFormClass() {
        return SysPortalMapTplForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.addNoConvertProperty("fdId");
            toFormPropertyMap.put("docCreateTime", new ModelConvertor_Common("docCreateTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.addNoConvertProperty("authReaderFlag");
            toFormPropertyMap.addNoConvertProperty("authEditorFlag");
            toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
            toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
			toFormPropertyMap.put("fdNav.fdName", "fdNavName");
			toFormPropertyMap.put("fdNav.fdId", "fdNavId");
			toFormPropertyMap.put("fdPortalNav",
					new ModelConvertor_ModelListToFormList("fdPortalNavForms"));
			toFormPropertyMap.put("fdMapInlet",
					new ModelConvertor_ModelListToFormList(
							"fdMapInletForms"));
			toFormPropertyMap.put("fdNavCustom",
					new ModelConvertor_ModelListToFormList(
							"fdNavCustomForms"));
            toFormPropertyMap.put("authReaders", new ModelConvertor_ModelListToString("authReaderIds:authReaderNames", "fdId:fdName"));
            toFormPropertyMap.put("authEditors", new ModelConvertor_ModelListToString("authEditorIds:authEditorNames", "fdId:fdName"));
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
    }

    /**
     * 模板id
     */
    @Override
    public String getFdId() {
        return this.fdId;
    }

    /**
     * 模板id
     */
    @Override
    public void setFdId(String fdId) {
        this.fdId = fdId;
    }

    /**
     * 名称
     */
    @Override
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
     * 地图模板类型
     */
    public Integer getTplType() {
        return this.tplType;
    }

    /**
     * 地图模板类型
     */
    public void setTplType(Integer tplType) {
        this.tplType = tplType;
    }

	/**
	 * 系统导航
	 */
	public SysPortalMapTpl getFdNav() {
		return this.fdNav;
	}

	/**
	 * 系统导航
	 */
	public void setFdNav(SysPortalMapTpl fdNav) {
		this.fdNav = fdNav;
	}

    /**
     * 返回 所有人可阅读标记
     */
    @Override
    public Boolean getAuthReaderFlag() {
        return false;
    }

 // ===== 附件机制（开始） =====
 	protected AutoHashMap attachmentForms = new AutoHashMap(
 			AttachmentDetailsForm.class);

 	@Override
    public AutoHashMap getAttachmentForms() {
 		return attachmentForms;
 	}
 	
}
