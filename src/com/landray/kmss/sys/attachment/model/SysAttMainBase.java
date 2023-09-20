package com.landray.kmss.sys.attachment.model;

import com.landray.kmss.sys.attachment.forms.SysAttMainForm;
import com.landray.kmss.sys.print.model.SysPrintLog;

public class SysAttMainBase extends SysAttBase {
    @Override
    public Class getFormClass() {
        return SysAttMainForm.class;
    }

    /*
     * 附件借阅次数
     */
    protected Long fdBorrowCount = new Long(0);

    //附件保存转换控制，默认为true，设置false表示附件仅保存，不进入转换队列
    private Boolean addQueue = true;

    public Long getFdBorrowCount() {
        return fdBorrowCount;
    }

    public void setFdBorrowCount(Long fdBorrowCount) {
        this.fdBorrowCount = fdBorrowCount;
    }

    private String fileAlterName = null;

    public String getFileAlterName() {
        return fileAlterName;
    }

    public void setFileAlterName(String fileAlterName) {
        this.fileAlterName = fileAlterName;
    }

    public Boolean getAddQueue() {
        return addQueue;
    }

    public void setAddQueue(Boolean addQueue) {
        this.addQueue = addQueue;
    }
}
