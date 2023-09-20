package com.landray.kmss.sys.organization.service;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.organization.model.SysOrgMatrix;
import com.landray.kmss.sys.organization.model.SysOrgMatrixVersion;

import java.util.List;

/**
 * 矩阵版本
 *
 * @author 潘永辉 2019年6月6日
 */
public interface ISysOrgMatrixVersionService extends IBaseService {

    /**
     * 更新版本状态
     *
     * @param request
     * @throws Exception
     */
    public void updateEnable(RequestContext request) throws Exception;

    /**
     * 增加版本
     *
     * @param fdMatrix
     * @param fdVersion
     * @throws Exception
     */
    public void addVersion(SysOrgMatrix fdMatrix, String fdVersion) throws Exception;

    /**
     * 复制版本（增加版本）
     *
     * @param fdMatrix
     * @return
     * @throws Exception
     */
    public SysOrgMatrixVersion addVersion(SysOrgMatrix fdMatrix) throws Exception;

    /**
     * 检查版本是否可用（激活）
     *
     * @param fdMatrixId
     * @param fdVersion
     * @return
     * @throws Exception
     */
    public boolean isEnable(String fdMatrixId, String fdVersion) throws Exception;

    /**
     * 删除版本
     *
     * @param fdMatrixId
     * @param fdVersion
     * @return
     * @throws Exception
     */
    public void deleteVersion(String fdMatrixId, String fdVersion) throws Exception;

    /**
     * 获取矩阵版本
     *
     * @param fdMatrixId
     * @return
     * @throws Exception
     */
    public List<SysOrgMatrixVersion> getVersions(String fdMatrixId) throws Exception;

}
