package com.landray.kmss.sys.mportal.forms;

import com.landray.kmss.common.convertor.FormConvertor_FormListToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.mportal.model.SysMportalCpage;
import com.landray.kmss.util.AutoArrayList;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 复合门户页
  */
public class SysMportalCpageForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdName;

    private String fdIcon;

    private String fdImg; //素材库图片url

    private String fdEnabled;

    private String fdOrder;
    
    private String fdType;

    private String docCreateTime;

    private String docAlterTime;

    private String docCreatorId;

    private String docCreatorName;

    private String docAlterorId;

    private String docAlterorName;
    
    private String fdUrl;
    
    //外部链接打开方式
    private String fdUrlOpenType;

	public void setFdUrlOpenType(String fdUrlOpenType) {
		this.fdUrlOpenType = fdUrlOpenType;
	}

	public String getFdUrlOpenType() {
		return fdUrlOpenType;
	}

	public String getFdUrl() {
		return fdUrl;
	}

	public void setFdUrl(String fdUrl) {
		this.fdUrl = fdUrl;
	}
    
    private List cards = new AutoArrayList(SysMportalCpageCardForm.class);

	public List getCards() {
		return cards;
	}

	public void setCards(List cards) {
		this.cards = cards;
	}

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        fdName = null;
        fdIcon = null;
        fdEnabled = null;
        fdType = null;
        fdOrder = null;
        fdUrl = null;
        fdUrlOpenType = null;
        docCreateTime = null;
        docAlterTime = null;
        docCreatorId = null;
        docCreatorName = null;
        docAlterorId = null;
        docAlterorName = null;
        cards.clear();
        super.reset(mapping, request);
    }

    @Override
    public Class<SysMportalCpage> getModelClass() {
        return SysMportalCpage.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.addNoConvertProperty("docCreateTime");
            toModelPropertyMap.addNoConvertProperty("docAlterTime");
            toModelPropertyMap.put("cards",
					new FormConvertor_FormListToModelList("cards",
							"sysMportalCpage"));
        }
        return toModelPropertyMap;
    }
    
    

    public String getFdType() {
		return fdType;
	}

	public void setFdType(String fdType) {
		this.fdType = fdType;
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
     * 图标
     */
    public String getFdIcon() {
        return fdIcon;
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
    public String getFdEnabled() {
        return this.fdEnabled;
    }

    /**
     * 是否启用
     */
    public void setFdEnabled(String fdEnabled) {
        this.fdEnabled = fdEnabled;
    }

    /**
     * 排序号
     */
    public String getFdOrder() {
        return this.fdOrder;
    }

    /**
     * 排序号
     */
    public void setFdOrder(String fdOrder) {
        this.fdOrder = fdOrder;
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

    /**
     * 修改人
     */
    public String getDocAlterorId() {
        return this.docAlterorId;
    }

    /**
     * 修改人
     */
    public void setDocAlterorId(String docAlterorId) {
        this.docAlterorId = docAlterorId;
    }

    /**
     * 修改人
     */
    public String getDocAlterorName() {
        return this.docAlterorName;
    }

    /**
     * 修改人
     */
    public void setDocAlterorName(String docAlterorName) {
        this.docAlterorName = docAlterorName;
    }
}
