package com.landray.kmss.sys.filestore.location.service;

import com.landray.kmss.sys.filestore.location.interfaces.ISysFileResourceBuzPathProvider;
import org.apache.commons.lang3.StringUtils;

public abstract class AbstractSysFileResourceBuzPathProvider implements ISysFileResourceBuzPathProvider {

    @Override
    public String getBuzPath(String root, String path) {
        String r = root.replaceAll("\\\\", "/");
        if (StringUtils.isNotEmpty(r) && !r.endsWith("/")) {
            r += "/";
        }
        String rp = path.replaceAll("\\\\","/");
        if (rp.startsWith("/")) {
            rp = rp.substring(1);
        }
        return r + rp;
    }
}
