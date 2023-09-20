package com.landray.kmss.sys.oms.forms;

import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.oms.model.SysOmsTempPp;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 组织架构岗位人员关系临时表
  */
public class SysOmsTempPpForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdTrxId;

    private String fdPersonId;

    private String fdPostId;

    private String fdAlterTime;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        fdTrxId = null;
        fdPersonId = null;
        fdPostId = null;
        fdAlterTime = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<SysOmsTempPp> getModelClass() {
        return SysOmsTempPp.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
        }
        return toModelPropertyMap;
    }

    /**
     * 事务号
     */
    public String getFdTrxId() {
        return this.fdTrxId;
    }

    /**
     * 事务号
     */
    public void setFdTrxId(String fdTrxId) {
        this.fdTrxId = fdTrxId;
    }

    /**
     * 人员id
     */
    public String getFdPersonId() {
        return this.fdPersonId;
    }

    /**
     * 人员id
     */
    public void setFdPersonId(String fdPersonId) {
        this.fdPersonId = fdPersonId;
    }

    /**
     * 岗位id
     */
    public String getFdPostId() {
        return this.fdPostId;
    }

    /**
     * 岗位id
     */
    public void setFdPostId(String fdPostId) {
        this.fdPostId = fdPostId;
    }

    /**
     * 最后更新时间
     */
    public String getFdAlterTime() {
        return this.fdAlterTime;
    }

    /**
     * 最后更新时间
     */
    public void setFdAlterTime(String fdAlterTime) {
        this.fdAlterTime = fdAlterTime;
    }
}
