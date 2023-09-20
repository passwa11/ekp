package com.landray.kmss.km.archives.service;

import java.util.List;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;
import com.landray.kmss.km.archives.model.KmArchivesCategory;

public interface IKmArchivesCategoryService extends IExtendDataService {

    public abstract List<KmArchivesCategory> findByFdParent(KmArchivesCategory fdParent) throws Exception;
}
