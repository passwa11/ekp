package com.landray.kmss.third.ding.forms;

import com.landray.kmss.common.convertor.FormConvertor_Common;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.forms.IAttachmentForm;
import com.landray.kmss.third.ding.model.ThirdDingCalendar;
import com.landray.kmss.third.ding.model.ThirdDingCalendarLog;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.web.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
  * 钉钉日程同步
  */
public class ThirdDingCalendarForm extends ExtendForm implements IAttachmentForm {

    private static FormToModelPropertyMap toModelPropertyMap;


    //同步token
    private String syncToken;
    //人员id
    private String personId ;
    //创建时间
    private Date docCreateTime;

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);


    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        syncToken = null;
        personId = null;
        docCreateTime = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<ThirdDingCalendar> getModelClass() {
        return ThirdDingCalendar.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
        }
        return toModelPropertyMap;
    }


    public static void setToModelPropertyMap(FormToModelPropertyMap toModelPropertyMap) {
        ThirdDingCalendarForm.toModelPropertyMap = toModelPropertyMap;
    }

    public String getSyncToken() {
        return syncToken;
    }

    public void setSyncToken(String syncToken) {
        this.syncToken = syncToken;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public Date getDocCreateTime() {
        return docCreateTime;
    }

    public void setDocCreateTime(Date docCreateTime) {
        this.docCreateTime = docCreateTime;
    }

    @Override
    public AutoHashMap getAttachmentForms() {
        return attachmentForms;
    }
}
