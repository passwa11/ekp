package com.landray.kmss.third.weixin.forms;

import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.sys.attachment.forms.IAttachmentForm;
import com.landray.kmss.third.weixin.model.ThirdWeixinRobotMsg;

/**
  * 机器人信息
  */
public class ThirdWeixinRobotMsgForm extends ExtendForm implements IAttachmentForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdRobotId;

    private String fdRobotName;

    private String fdCreatorUserid;

    private String docCreateTime;

    private String docAlterTime;

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);
        fdRobotId = null;
        fdRobotName = null;
        fdCreatorUserid = null;
        docCreateTime = null;
        docAlterTime = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<ThirdWeixinRobotMsg> getModelClass() {
        return ThirdWeixinRobotMsg.class;
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
     * 机器人ID
     */
    public String getFdRobotId() {
        return this.fdRobotId;
    }

    /**
     * 机器人ID
     */
    public void setFdRobotId(String fdRobotId) {
        this.fdRobotId = fdRobotId;
    }

    /**
     * 机器人名称
     */
    public String getFdRobotName() {
        return this.fdRobotName;
    }

    /**
     * 机器人名称
     */
    public void setFdRobotName(String fdRobotName) {
        this.fdRobotName = fdRobotName;
    }

    /**
     * 创建者ID
     */
    public String getFdCreatorUserid() {
        return this.fdCreatorUserid;
    }

    /**
     * 创建者ID
     */
    public void setFdCreatorUserid(String fdCreatorUserid) {
        this.fdCreatorUserid = fdCreatorUserid;
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

    @Override
    public AutoHashMap getAttachmentForms() {
        return attachmentForms;
    }
}
