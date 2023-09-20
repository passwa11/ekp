package com.landray.kmss.sys.oms.model;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.component.locker.interfaces.ComponentLockable;
import com.landray.kmss.sys.oms.forms.SysOmsTempConfigForm;
import com.landray.kmss.util.DateUtil;

/**
  * 同步配置表
  */
public class SysOmsTempConfig extends BaseModel implements ComponentLockable {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private Integer fdSynStatus;

    private Long fdSynTimestamp;

    private String fdTrxId;
    

    @Override
    public Class<SysOmsTempConfigForm> getFormClass() {
        return SysOmsTempConfigForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("fdSynTimestamp", new ModelConvertor_Common("fdSynTimestamp").setDateTimeType(DateUtil.TYPE_DATE));
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
    }

    /**
     * 同步状态
     */
    public Integer getFdSynStatus() {
        return this.fdSynStatus;
    }

    /**
     * 同步状态
     */
    public void setFdSynStatus(Integer fdSynStatus) {
        this.fdSynStatus = fdSynStatus;
    }

    /**
     * 同步时间戳
     */
    public Long getFdSynTimestamp() {
        return this.fdSynTimestamp;
    }

    /**
     * 同步时间戳
     */
    public void setFdSynTimestamp(Long fdSynTimestamp) {
        this.fdSynTimestamp = fdSynTimestamp;
    }

    

	public String getFdTrxId() {
		return fdTrxId;
	}

	public void setFdTrxId(String fdTrxId) {
		this.fdTrxId = fdTrxId;
	}

	@Override
	public void setVersion(Integer version) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Integer getVersion() {
		// TODO Auto-generated method stub
		return 1;
	}
}
