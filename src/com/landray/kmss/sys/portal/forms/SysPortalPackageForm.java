package com.landray.kmss.sys.portal.forms;

import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.web.upload.FormFile;

/**
 * @description:
 * @author: wangjf
 * @time: 2021/6/20 6:22 下午
 * @version: 1.0
 */

public class SysPortalPackageForm extends ExtendForm {

    private String portalZipPath;

    /* 文件上传 */
    private FormFile file = null;

    @Override
    public Class getModelClass() {
        return SysPortalPackageForm.class;
    }

    public FormFile getFile() {
        return file;
    }

    public void setFile(FormFile file) {
        this.file = file;
    }

    public String getPortalZipPath() {
        return portalZipPath;
    }

    public void setPortalZipPath(String portalZipPath) {
        this.portalZipPath = portalZipPath;
    }
}