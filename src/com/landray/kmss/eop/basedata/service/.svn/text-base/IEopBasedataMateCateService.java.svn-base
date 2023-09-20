package com.landray.kmss.eop.basedata.service;

import com.landray.kmss.eop.basedata.model.EopBasedataMateCate;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;
import com.landray.kmss.eop.basedata.forms.EopBasedataMateCateForm;

import java.util.List;

/**
 * @author wangwh
 * @description:物料类别业务类
 * @date 2021/5/7
 */
public interface IEopBasedataMateCateService extends IExtendDataService {

    public abstract List<EopBasedataMateCate> findByFdParent(EopBasedataMateCate fdParent) throws Exception;

    /**
     * 根据名称查找物料类别
     * @param fdName
     * @return
     * @throws Exception
     */
    public abstract List<EopBasedataMateCate> findByName(String fdName) throws Exception;
    
    public abstract List<EopBasedataMateCate> findByNameIgnoreSelf(String fdName,String fdId) throws Exception;
    
    public abstract List<EopBasedataMateCate> findByCodeIgnoreSelf(String fdCode,String fdId) throws Exception;
    
    public EopBasedataMateCate getMateCateByCode(String fdTypeCode)   throws Exception;

    public abstract void updatePre(EopBasedataMateCateForm eopBasedataMateCateForm) throws Exception;
}
