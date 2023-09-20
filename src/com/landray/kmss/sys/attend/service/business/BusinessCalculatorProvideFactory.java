package com.landray.kmss.sys.attend.service.business;

import com.landray.kmss.sys.attend.model.SysAttendCategory;

/**
 * @description: 计算机提供器工厂
 * @author: wangjf
 * @time: 2022/3/31 4:31 下午
 * @version: 1.0
 */

public class BusinessCalculatorProvideFactory {

    public static AbstractBusinessCalculatorProvide getBusinessCalculatorProvide(SysAttendCategory sysAttendCategory) {
        //不存在的规则
        if (sysAttendCategory == null || sysAttendCategory.getFdShiftType() == null) {
            return null;
        }
        if (2 == sysAttendCategory.getFdShiftType()) {
            //自定义考勤规则
            return new DefinedBusinessCalculatorProvide(sysAttendCategory);
        } else if (0 == sysAttendCategory.getFdShiftType() && 1 == sysAttendCategory.getFdSameWorkTime()) {
            return new WeekDiffBusinessCalculatorProvide(sysAttendCategory);
        } else {
            return new WeekSameBusinessCalculatorProvide(sysAttendCategory);
        }

    }
}