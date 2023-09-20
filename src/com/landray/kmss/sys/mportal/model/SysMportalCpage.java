package com.landray.kmss.sys.mportal.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToFormList;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.mportal.forms.SysMportalCpageForm;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.sys.organization.model.SysOrgPerson;

/**
  * 复合门户页
  */
public class SysMportalCpage extends BaseModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdName;

    private String fdIcon;

    private String fdImg;//素材库图片url

    private Boolean fdEnabled;

    private Integer fdOrder;
    
    private String fdType;
    
    private String fdCompositeId;

    private Date docCreateTime;

    private Date docAlterTime;

    private SysOrgPerson docCreator;

    private SysOrgPerson docAlteror;
    //外部链接
    private String fdUrl;
    //外部链接打开方式
    private String fdUrlOpenType;

	public String getFdUrlOpenType() {
		return fdUrlOpenType;
	}

	public void setFdUrlOpenType(String fdUrlOpenType) {
		this.fdUrlOpenType = fdUrlOpenType;
	}

	public String getFdUrl() {
		return fdUrl;
	}

	public void setFdUrl(String fdUrl) {
		this.fdUrl = fdUrl;
	}
	
    // 页面卡片
 	protected List<SysMportalCpageCard> cards = new ArrayList<SysMportalCpageCard>();

 	public List<SysMportalCpageCard> getCards() {
 		return cards;
 	}

 	public void setCards(List<SysMportalCpageCard> cards) {
 		this.cards = cards;
 	}
 	
 	// 关联门户
  	protected List<SysMportalCpageRelation> cpageRelations = new ArrayList<SysMportalCpageRelation>();

  	public List<SysMportalCpageRelation> getCpageRelations() {
  		return cpageRelations;
  	}

  	public void setCpageRelations(List<SysMportalCpageRelation> cpageRelations) {
  		this.cpageRelations = cpageRelations;
  	}
 	
 	
    @Override
    public Class<SysMportalCpageForm> getFormClass() {
        return SysMportalCpageForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("docCreateTime", new ModelConvertor_Common("docCreateTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("docAlterTime", new ModelConvertor_Common("docAlterTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
            toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
            toFormPropertyMap.put("docAlteror.fdName", "docAlterorName");
            toFormPropertyMap.put("docAlteror.fdId", "docAlterorId");           
            toFormPropertyMap.put("cards",
					new ModelConvertor_ModelListToFormList("cards"));
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
        int order = 0;
		for (SysMportalCpageCard item : this.getCards()) {
			order++;
			item.setFdOrder(order);
		}
    }
    
    

    public String getFdType() {
		return fdType;
	}

	public void setFdType(String fdType) {
		this.fdType = fdType;
	}

	/**
	 * @param fdName
	 *            名称
	 */
	public String getFdName() {
		return SysLangUtil.getPropValue(this, "fdName", this.fdName);
	}
	
	/**
	 * @param fdName
	 *            名称
	 */
	public void setFdName(String fdName) {
		this.fdName = fdName;
		SysLangUtil.setPropValue(this, "fdName", fdName);
	}

    /**
     * 图标
     */
    public String getFdIcon() {
        return this.fdIcon;
    }

    /**
     * 图标
     */
    public void setFdIcon(String fdIcon) {
        this.fdIcon = fdIcon;
    }

    /**
     * 素材库图标url
     * @return
     */
    public String getFdImg() {
        return fdImg;
    }

    public void setFdImg(String fdImg) {
        this.fdImg = fdImg;
    }

    /**
     * 是否启用
     */
    public Boolean getFdEnabled() {
        return this.fdEnabled;
    }

    /**
     * 是否启用
     */
    public void setFdEnabled(Boolean fdEnabled) {
        this.fdEnabled = fdEnabled;
    }

    /**
     * 排序号
     */
    public Integer getFdOrder() {
        return this.fdOrder;
    }

    /**
     * 排序号
     */
    public void setFdOrder(Integer fdOrder) {
        this.fdOrder = fdOrder;
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

    /**
     * 修改人
     */
    public SysOrgPerson getDocAlteror() {
        return this.docAlteror;
    }

    /**
     * 修改人
     */
    public void setDocAlteror(SysOrgPerson docAlteror) {
        this.docAlteror = docAlteror;
    }
}
