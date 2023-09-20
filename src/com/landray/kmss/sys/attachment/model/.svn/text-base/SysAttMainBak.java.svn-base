package com.landray.kmss.sys.attachment.model;

import java.util.Date;

/**
 * 附件清理需求
 * 移出的备份数据
 */
public class SysAttMainBak extends SysAttMainBase {
    /**
     * 附件移出时间
     */
    private Date fdMovingTime;
    /**
     * 备份记录创建时间
     */
    private Date fdBakCreateTime;
    /**
     * 移出状态（0：待移出 1：已移出 2：主文档fdFileId对应的att_file表无记录 3：主文档fdFileId对应的文件不存在）
     */
    private int fdMovingStatus;

    public Date getFdMovingTime() {
        return fdMovingTime;
    }

    public void setFdMovingTime(Date fdMovingTime) {
        this.fdMovingTime = fdMovingTime;
    }

    public Date getFdBakCreateTime() {
        return fdBakCreateTime;
    }

    public void setFdBakCreateTime(Date fdBakCreateTime) {
        this.fdBakCreateTime = fdBakCreateTime;
    }

    public int getFdMovingStatus() {
        return fdMovingStatus;
    }

    public void setFdMovingStatus(int fdMovingStatus) {
        this.fdMovingStatus = fdMovingStatus;
    }
}
