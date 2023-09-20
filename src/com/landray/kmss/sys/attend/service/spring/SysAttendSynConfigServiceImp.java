package com.landray.kmss.sys.attend.service.spring;

import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.sys.attend.model.SysAttendSynConfig;
import com.landray.kmss.sys.attend.service.ISysAttendSynConfigService;
import com.landray.kmss.sys.cache.KmssCache;
import org.slf4j.Logger;

public class SysAttendSynConfigServiceImp extends BaseServiceImp
        implements ISysAttendSynConfigService {
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysAttendSynConfigServiceImp.class);


    @Override
    public void update(IBaseModel modelObj) throws Exception {
        super.update(modelObj);
        clearCache();
    }


    @Override
    public SysAttendSynConfig getSysAttendSynConfig() throws Exception {
        KmssCache cache = new KmssCache(SysAttendSynConfig.class);
        SysAttendSynConfig config = (SysAttendSynConfig) cache.get("sysAttendSynConfig");
        if (config == null) {
            SysAttendSynConfig sysAttendSynConfig = (SysAttendSynConfig) this.findFirstOne("", "");
            if (sysAttendSynConfig != null) {
                config = sysAttendSynConfig;
                cache.put("sysAttendSynConfig", config);
            }
        }
        return config;
    }


    private void clearCache() {
        // 清除缓存
        KmssCache cache = new KmssCache(SysAttendSynConfig.class);
        cache.clear();
    }
}
