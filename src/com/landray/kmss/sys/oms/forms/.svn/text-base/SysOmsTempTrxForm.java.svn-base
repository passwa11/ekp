package com.landray.kmss.sys.oms.forms;

import com.landray.kmss.util.DateUtil;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.common.convertor.FormConvertor_Common;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.oms.model.SysOmsTempTrx;

/**
  * 事务表
  */
public class SysOmsTempTrxForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String beginTime;

    private String endTime;

    private String fdSynModel;

    private String fdSynStatus;

    private String fdSynMsg;
    
    private String fdLogError;
    
    private String fdLogDetail;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        beginTime = null;
        endTime = null;
        fdSynModel = null;
        fdSynStatus = null;
        fdSynMsg = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<SysOmsTempTrx> getModelClass() {
        return SysOmsTempTrx.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.put("beginTime", new FormConvertor_Common("beginTime").setDateTimeType(DateUtil.TYPE_TIME_SEC));
            toModelPropertyMap.put("endTime", new FormConvertor_Common("endTime").setDateTimeType(DateUtil.TYPE_TIME_SEC));
        }
        return toModelPropertyMap;
    }

    /**
     * 事务开始时间
     */
    public String getBeginTime() {
        return this.beginTime;
    }

    /**
     * 事务开始时间
     */
    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    /**
     * 事务结束时间
     */
    public String getEndTime() {
        return this.endTime;
    }

    /**
     * 事务结束时间
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    /**
     * 同步模式
     */
    public String getFdSynModel() {
        return this.fdSynModel;
    }

    /**
     * 同步模式
     */
    public void setFdSynModel(String fdSynModel) {
        this.fdSynModel = fdSynModel;
    }

    /**
     * 同步状态
     */
    public String getFdSynStatus() {
        return this.fdSynStatus;
    }

    /**
     * 同步状态
     */
    public void setFdSynStatus(String fdSynStatus) {
        this.fdSynStatus = fdSynStatus;
    }

    /**
     * 同步原因
     */
    public String getFdSynMsg() {
        return this.fdSynMsg;
    }

    /**
     * 同步原因
     */
    public void setFdSynMsg(String fdSynMsg) {
        this.fdSynMsg = fdSynMsg;
    }

	public String getFdLogError() {
		return fdLogError;
	}

	public void setFdLogError(String fdLogError) {
		this.fdLogError = fdLogError;
	}

	public String getFdLogDetail() {
		return fdLogDetail;
	}

	public void setFdLogDetail(String fdLogDetail) {
		this.fdLogDetail = fdLogDetail;
	}
    
    
}
