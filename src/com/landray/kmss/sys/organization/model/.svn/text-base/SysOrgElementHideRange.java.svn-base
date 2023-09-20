package com.landray.kmss.sys.organization.model;

import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.organization.forms.SysOrgElementHideRangeForm;
import org.apache.commons.lang3.BooleanUtils;

import java.util.List;

/**
 * 组织隐藏范围
 *
 * @description:
 * @author: wangjf
 * @time: 2021/9/28 2:51 下午
 */
public class SysOrgElementHideRange extends BaseModel {
    /**
     * 所属组织
     */
    private SysOrgElement fdElement;

    /**
     * 是否开启隐藏，false为显示，true为隐藏
     */
    private Boolean fdIsOpenLimit;

    /**
     * 查看类型 0代表对所有人隐藏，1代表对部分人可见
     */
    private Integer fdViewType;

    /**
     * 本组织隐藏后的可见范围
     */
    private List<SysOrgElement> fdOthers;

    public SysOrgElement getFdElement() {
        return fdElement;
    }

    public void setFdElement(SysOrgElement fdElement) {
        this.fdElement = fdElement;
    }

    public Boolean getFdIsOpenLimit() {
        // 如果是生态组织，强制开启
        if (this.fdIsOpenLimit == null && this.fdElement != null && BooleanUtils.isTrue(this.fdElement.getFdIsExternal())) {
            return true;
        }
        return fdIsOpenLimit;
    }

    public void setFdIsOpenLimit(Boolean fdIsOpenLimit) {
        this.fdIsOpenLimit = fdIsOpenLimit;
    }

    public Integer getFdViewType() {
        // 如果是生态组织，并且没有配置时，默认"对所有人隐藏"
        if (this.fdViewType == null && this.fdElement != null && BooleanUtils.isTrue(this.fdElement.getFdIsExternal())) {
            return 0;
        }
        return fdViewType;
    }

    public void setFdViewType(Integer fdViewType) {
        this.fdViewType = fdViewType;
    }

    @Override
    public Class getFormClass() {
        return SysOrgElementHideRangeForm.class;
    }

    public List<SysOrgElement> getFdOthers() {
        return fdOthers;
    }

    public void setFdOthers(List<SysOrgElement> fdOthers) {
        this.fdOthers = fdOthers;
    }

    private static ModelToFormPropertyMap toFormPropertyMap;

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("fdElement.fdId", "fdElementId");
            toFormPropertyMap.put("fdElement.fdName", "fdElementName");
            toFormPropertyMap.put("fdOthers",
                    new ModelConvertor_ModelListToString("fdOtherIds:fdOtherNames", "fdId:fdName"));
        }
        return toFormPropertyMap;
    }

}
