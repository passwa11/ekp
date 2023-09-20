package com.landray.kmss.fssc.fee.service;

import java.util.List;
import com.landray.kmss.sys.category.model.SysCategoryMain;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;
import com.landray.kmss.sys.category.model.SysCategoryProperty;
import com.landray.kmss.fssc.fee.model.FsscFeeTemplate;

public interface IFsscFeeTemplateService extends IExtendDataService {

    public abstract List<FsscFeeTemplate> findByDocCategory(SysCategoryMain docCategory) throws Exception;

    public abstract List<FsscFeeTemplate> findByDocProperties(SysCategoryProperty docProperties) throws Exception;
}
