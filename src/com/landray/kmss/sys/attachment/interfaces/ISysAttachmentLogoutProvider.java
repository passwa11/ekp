package com.landray.kmss.sys.attachment.interfaces;

import com.landray.kmss.sys.authentication.user.KMSSUser;

public interface ISysAttachmentLogoutProvider {
    void logout(KMSSUser user);
}
