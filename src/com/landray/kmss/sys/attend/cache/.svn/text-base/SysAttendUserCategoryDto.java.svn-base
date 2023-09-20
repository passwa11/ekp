package com.landray.kmss.sys.attend.cache;

import java.io.Serializable;
import java.util.Date;

/**
 * 考勤组人员跟考勤组关系的存储对象
 * 用于缓存中
 * @author wj
 * @date 2022-06-10
 */
public class SysAttendUserCategoryDto implements Serializable {
    /**
     * 原始考勤组id(备用)
     */
    private String fdOldCategoryId;
    /**
     * 考勤组id
     * 历史考勤组id
     */
    private String fdCategoryId;

    /**
     * 考勤组所属开始时间
     */
    private Date fdBeginDate;

    /**
     * 考勤组所属结束时间
     */
    private Date fdEndDate;

    /**
     * 人员所属考勤组层级。数字越大，匹配的值越高
     */
    private Integer level;

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getFdCategoryId() {
        return fdCategoryId;
    }

    public void setFdCategoryId(String fdCategoryId) {
        this.fdCategoryId = fdCategoryId;
    }

    public Date getFdBeginDate() {
        return fdBeginDate;
    }

    public void setFdBeginDate(Date fdBeginDate) {
        this.fdBeginDate = fdBeginDate;
    }

    public Date getFdEndDate() {
        return fdEndDate;
    }

    public void setFdEndDate(Date fdEndDate) {
        this.fdEndDate = fdEndDate;
    }

    public String getFdOldCategoryId() {
        return fdOldCategoryId;
    }

    public void setFdOldCategoryId(String fdOldCategoryId) {
        this.fdOldCategoryId = fdOldCategoryId;
    }
}
