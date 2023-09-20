package com.landray.kmss.sys.praise.model;

import java.util.Date;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyModel;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.praise.forms.SysPraiseInfoForm;

/**
  * 赞赏信息表
  */
public class SysPraiseInfo extends BaseModel implements ISysNotifyModel {

    private static ModelToFormPropertyMap toFormPropertyMap;
    
    private String docSubject;
    
    private Date docCreateTime;

    private String fdTargetId;

    private String fdTargetName;

    private String fdReason;

    private Integer fdRich;

    private Integer fdType;

    private String fdSourceId;

    private String fdSourceName;

    private String fdSourceTitle;
    
    private String fdIsHideName;

	private Boolean isReply;

	private String replyContent;

	private Date replyTime;

    private SysOrgElement fdTargetPerson;

    private SysOrgElement fdPraisePerson;
    
    private SysPraiseInfoCategory docCategory;

    public SysPraiseInfoCategory getDocCategory() {
		return docCategory;
	}

	public void setDocCategory(SysPraiseInfoCategory docCategory) {
		this.docCategory = docCategory;
	}

	@Override
    public Class<SysPraiseInfoForm> getFormClass() {
        return SysPraiseInfoForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("fdTargetPerson.fdName", "fdTargetPersonName");
            toFormPropertyMap.put("fdTargetPerson.fdId", "fdTargetPersonId");
            toFormPropertyMap.put("fdPraisePerson.fdName", "fdPraisePersonName");
            toFormPropertyMap.put("fdPraisePerson.fdId", "fdPraisePersonId");
            toFormPropertyMap.put("docCategory.fdId", "docCategoryId");
			toFormPropertyMap.put("docCategory.fdName", "docCategoryName");
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
    }

    /**
     * 目标id
     */
    public String getFdTargetId() {
        return this.fdTargetId;
    }

    /**
     * 目标id
     */
    public void setFdTargetId(String fdTargetId) {
        this.fdTargetId = fdTargetId;
    }

    /**
     * 目标模块名
     */
    public String getFdTargetName() {
        return this.fdTargetName;
    }

    /**
     * 目标模块名
     */
    public void setFdTargetName(String fdTargetName) {
        this.fdTargetName = fdTargetName;
    }

    /**
     * 赞赏原因
     */
    public String getFdReason() {
        return this.fdReason;
    }

    /**
     * 赞赏原因
     */
    public void setFdReason(String fdReason) {
        this.fdReason = fdReason;
    }

    /**
     * 财富值
     */
    public Integer getFdRich() {
        return this.fdRich;
    }

    /**
     * 财富值
     */
    public void setFdRich(Integer fdRich) {
        this.fdRich = fdRich;
    }

    /**
     * 类型
     */
    public Integer getFdType() {
        return this.fdType;
    }

    /**
     * 类型
     */
    public void setFdType(Integer fdType) {
        this.fdType = fdType;
    }

    /**
     * 目标来源id
     */
    public String getFdSourceId() {
        return this.fdSourceId;
    }

    /**
     * 目标来源id
     */
    public void setFdSourceId(String fdSourceId) {
        this.fdSourceId = fdSourceId;
    }

    /**
     * 目标来源模块名
     */
    public String getFdSourceName() {
        return this.fdSourceName;
    }

    /**
     * 目标来源模块名
     */
    public void setFdSourceName(String fdSourceName) {
        this.fdSourceName = fdSourceName;
    }

    /**
     * 目标来源标题
     */
    public String getFdSourceTitle() {
        return this.fdSourceTitle;
    }

    /**
     * 目标来源标题
     */
    public void setFdSourceTitle(String fdSourceTitle) {
        this.fdSourceTitle = fdSourceTitle;
    }

    /**
     * 目标人员
     */
    public SysOrgElement getFdTargetPerson() {
        return this.fdTargetPerson;
    }

    /**
     * 目标人员
     */
    public void setFdTargetPerson(SysOrgElement fdTargetPerson) {
        this.fdTargetPerson = fdTargetPerson;
    }

    /**
     * 赞赏人
     */
    public SysOrgElement getFdPraisePerson() {
        return this.fdPraisePerson;
    }

    /**
     * 赞赏人
     */
    public void setFdPraisePerson(SysOrgElement fdPraisePerson) {
        this.fdPraisePerson = fdPraisePerson;
    }

	public String getDocSubject() {
		return docSubject;
	}

	public void setDocSubject(String docSubject) {
		this.docSubject = docSubject;
	}

	public Date getDocCreateTime() {
		return docCreateTime;
	}

	public void setDocCreateTime(Date docCreateTime) {
		this.docCreateTime = docCreateTime;
	}

	public String getFdIsHideName() {
		return fdIsHideName;
	}

	public void setFdIsHideName(String fdIsHideName) {
		this.fdIsHideName = fdIsHideName;
	}

	public Boolean getIsReply() {
		return isReply;
	}

	public void setIsReply(Boolean isReply) {
		this.isReply = isReply;
	}

	public String getReplyContent() {
		return replyContent;
	}

	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}

	public Date getReplyTime() {
		return replyTime;
	}

	public void setReplyTime(Date replyTime) {
		this.replyTime = replyTime;
	}

	// 通知类型
	private String fdNotifyType;

	public String getFdNotifyType() {
		return fdNotifyType;
	}

	public void setFdNotifyType(String fdNotifyType) {
		this.fdNotifyType = fdNotifyType;
	}
}
