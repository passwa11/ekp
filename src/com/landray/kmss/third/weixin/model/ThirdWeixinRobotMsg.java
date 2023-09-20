package com.landray.kmss.third.weixin.model;

import java.util.Date;
import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.third.weixin.forms.ThirdWeixinRobotMsgForm;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.sys.attachment.model.IAttachment;
import com.landray.kmss.util.DateUtil;

/**
  * 机器人信息
  */
public class ThirdWeixinRobotMsg extends BaseModel implements IAttachment {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdRobotId;

    private String fdRobotName;

    private String fdCreatorUserid;

    private Date docCreateTime;

    private Date docAlterTime;

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    @Override
    public Class<ThirdWeixinRobotMsgForm> getFormClass() {
        return ThirdWeixinRobotMsgForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("docCreateTime", new ModelConvertor_Common("docCreateTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("docAlterTime", new ModelConvertor_Common("docAlterTime").setDateTimeType(DateUtil.TYPE_DATETIME));
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
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

    @Override
    public AutoHashMap getAttachmentForms() {
        return attachmentForms;
    }
}
