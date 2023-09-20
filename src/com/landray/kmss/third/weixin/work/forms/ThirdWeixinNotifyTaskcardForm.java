package com.landray.kmss.third.weixin.work.forms;

import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.sys.attachment.forms.IAttachmentForm;
import com.landray.kmss.third.weixin.work.model.ThirdWeixinNotifyTaskcard;

/**
  * 任务卡片
  */
public class ThirdWeixinNotifyTaskcardForm extends ExtendForm implements IAttachmentForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdNotifyId;

    private String fdSubject;

    private String fdTaskcardId;

    private String fdTouser;

    private String docCreateTime;

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);
        fdNotifyId = null;
        fdSubject = null;
        fdTaskcardId = null;
        fdTouser = null;
        docCreateTime = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<ThirdWeixinNotifyTaskcard> getModelClass() {
        return ThirdWeixinNotifyTaskcard.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.addNoConvertProperty("docCreateTime");
        }
        return toModelPropertyMap;
    }

    /**
     * 待办ID
     */
    public String getFdNotifyId() {
        return this.fdNotifyId;
    }

    /**
     * 待办ID
     */
    public void setFdNotifyId(String fdNotifyId) {
        this.fdNotifyId = fdNotifyId;
    }

    /**
     * 标题
     */
    public String getFdSubject() {
        return this.fdSubject;
    }

    /**
     * 标题
     */
    public void setFdSubject(String fdSubject) {
        this.fdSubject = fdSubject;
    }

    /**
     * 任务卡片ID
     */
    public String getFdTaskcardId() {
        return this.fdTaskcardId;
    }

    /**
     * 任务卡片ID
     */
    public void setFdTaskcardId(String fdTaskcardId) {
        this.fdTaskcardId = fdTaskcardId;
    }

    /**
     * 接收人
     */
    public String getFdTouser() {
        return this.fdTouser;
    }

    /**
     * 接收人
     */
    public void setFdTouser(String fdTouser) {
        this.fdTouser = fdTouser;
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

    @Override
    public AutoHashMap getAttachmentForms() {
        return attachmentForms;
    }

    public String getFdCorpId() {
        return fdCorpId;
    }

    public void setFdCorpId(String fdCorpId) {
        this.fdCorpId = fdCorpId;
    }

    private String fdCorpId;
}
