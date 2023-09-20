package com.landray.kmss.sys.time.service;

import java.util.List;
import com.landray.kmss.sys.time.model.SysTimeWork;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;
import com.landray.kmss.sys.time.model.SysTimePatchwork;
import com.landray.kmss.sys.time.model.SysTimeElementEx;
import com.landray.kmss.sys.time.model.SysTimeOrgElementTime;
import com.landray.kmss.sys.time.model.SysTimeBusinessEx;

public interface ISysTimeElementExService extends IExtendDataService {

    public abstract List<SysTimeElementEx> findByFdWork(SysTimeWork fdWork) throws Exception;

    public abstract List<SysTimeElementEx> findByFdPatchwork(SysTimePatchwork fdPatchwork) throws Exception;

    public abstract List<SysTimeElementEx> findByFdVacation(SysTimeElementEx fdVacation) throws Exception;

    public abstract List<SysTimeElementEx> findByFdOrgElementTime(SysTimeOrgElementTime fdOrgElementTime) throws Exception;

    public abstract List<SysTimeElementEx> findByFdTimeBussines(SysTimeBusinessEx fdTimeBussines) throws Exception;
}
