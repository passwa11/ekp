package com.landray.kmss.sys.organization.model;

import java.util.Map;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.component.locker.interfaces.ComponentLockable;
import com.landray.kmss.sys.config.dict.SysDictModel;

public class SysOrgPersonRestrictLock implements ComponentLockable {

    @Override
    public String getFdId() {
        return "sysOrgPersonRestrictLock";
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
        return 1;
    }

}
