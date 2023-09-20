package com.landray.kmss.elec.device.model;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.component.locker.interfaces.ComponentLockable;
import com.landray.kmss.sys.config.dict.SysDictModel;

import java.util.Map;

/**
 * ELEC模块提供的附件锁，用于附件删除-新增操作时使用
 */
public class ElecFileLock implements ComponentLockable {

    private String fdId;

    public ElecFileLock(String fdId){
        this.fdId = fdId;
    }
    @Override
    public String getFdId() {
        return fdId;
    }

    @Override
    public void setFdId(String id) {

    }

    @Override
    public void recalculateFields() {

    }

    @Override
    public SysDictModel getSysDictModel() {
        return null;
    }

    @Override
    public void setSysDictModel(SysDictModel sysDictModel) {

    }

    @Override
    public Class getFormClass() {
        return null;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        return null;
    }

    @Override
    public Map<String, String> getDynamicMap() {
        return null;
    }

    @Override
    public Map<String, Object> getCustomPropMap() {
        return null;
    }

    @Override
    public void setVersion(Integer version) {

    }

    @Override
    public Integer getVersion() {
        return null;
    }
}
