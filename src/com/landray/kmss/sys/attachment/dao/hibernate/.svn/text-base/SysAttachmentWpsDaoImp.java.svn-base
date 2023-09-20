package com.landray.kmss.sys.attachment.dao.hibernate;

import com.landray.kmss.common.model.IBaseModel;
import java.util.Date;
import com.landray.kmss.sys.attachment.dao.ISysAttachmentWpsDao;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.sys.attachment.model.SysAttachmentWps;
import com.landray.kmss.common.dao.BaseDaoImp;

public class SysAttachmentWpsDaoImp extends BaseDaoImp implements ISysAttachmentWpsDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        SysAttachmentWps sysAttachmentWps = (SysAttachmentWps) modelObj;
        if (sysAttachmentWps.getDocCreator() == null) {
            sysAttachmentWps.setDocCreator(UserUtil.getUser());
        }
        if (sysAttachmentWps.getDocCreateTime() == null) {
            sysAttachmentWps.setDocCreateTime(new Date());
        }
        return super.add(sysAttachmentWps);
    }
}
