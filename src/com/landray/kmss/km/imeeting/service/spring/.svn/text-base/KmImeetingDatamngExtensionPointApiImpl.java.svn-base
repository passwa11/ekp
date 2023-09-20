package com.landray.kmss.km.imeeting.service.spring;

import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.common.module.core.register.annotation.declare.ApiImplement;
import com.landray.kmss.km.imeeting.model.KmImeetingMain;
import com.landray.kmss.km.imeeting.model.KmImeetingSummary;
import com.landray.kmss.km.imeeting.model.KmImeetingTopicCategory;
import com.landray.kmss.sys.config.dict.SysDictCommonProperty;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.ResourceUtil;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.BooleanUtils;

/**
 * 数据权限管理扩展实现
 *
 * @author 潘永辉
 * @date 2022/5/25 18:15
 */
public class KmImeetingDatamngExtensionPointApiImpl {

    /**
     * 数据转换
     *
     * @param model
     * @param property
     * @return
     */
    @ApiImplement("com.landray.kmss.sys.datamng.depend.ISysDatamngExtensionPointApi")
    public boolean dataConvert(BaseModel model, SysDictCommonProperty property) throws Exception {
        if ((model instanceof KmImeetingMain || model instanceof KmImeetingSummary) && "fdHoldDuration".equals(property.getName())) {
            // 获取原数据
            Double fdHoldDuration = (Double) PropertyUtils.getProperty(model, property.getName());
            Double hour = fdHoldDuration / DateUtil.HOUR;
            int h = (int) hour.doubleValue();
            String value = "";
            if (h > 0) {
                value += h + ResourceUtil.getString("date.interval.hour");
            }
            int m = (int) ((hour - h) * 60 + 0.5);
            if (m > 0) {
                value += m + ResourceUtil.getString("date.interval.minute");
            }
            // 数据处理完，保存到原model的transientInfoMap集合中，key为：datamngConvert
            model.getTransientInfoMap().put("datamngConvert", value);
            return true;
        }
        if (model instanceof KmImeetingTopicCategory && "authRBPFlag".equals(property.getName())) {
            Boolean flag = (Boolean) PropertyUtils.getProperty(model, property.getName());
            String value = "";
            if (BooleanUtils.isTrue(flag)) {
                value = ResourceUtil.getString("right.enums.rbpflag1", "sys-right");
            } else {
                value = ResourceUtil.getString("right.enums.rbpflag2", "sys-right");
            }
            // 数据处理完，保存到原model的transientInfoMap集合中，key为：datamngConvert
            model.getTransientInfoMap().put("datamngConvert", value);
            return true;
        }
        return false;
    }

}
