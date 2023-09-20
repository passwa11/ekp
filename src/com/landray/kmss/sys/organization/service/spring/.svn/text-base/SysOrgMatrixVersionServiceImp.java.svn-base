package com.landray.kmss.sys.organization.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.sys.organization.model.SysOrgMatrix;
import com.landray.kmss.sys.organization.model.SysOrgMatrixVersion;
import com.landray.kmss.sys.organization.service.ISysOrgMatrixVersionService;
import com.landray.kmss.util.IDGenerator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.BooleanUtils;
import org.hibernate.query.NativeQuery;

import java.util.Date;
import java.util.List;

/**
 * 矩阵版本
 *
 * @author 潘永辉 2019年6月6日
 */
public class SysOrgMatrixVersionServiceImp extends BaseServiceImp implements ISysOrgMatrixVersionService {

    /**
     * 更新版本状态SQL
     */
    private String updateEnableSql = "UPDATE sys_org_matrix_version SET fd_is_enable = :fdIsEnable WHERE fd_matrix_id = :fdMatrixId AND fd_name = :fdVersion AND fd_is_delete = :fdIsDelete";

    @Override
    public void updateEnable(RequestContext request) throws Exception {
        String fdMatrixId = request.getParameter("fdMatrixId");
        String fdVersion = request.getParameter("fdVersion");
        String fdIsEnable = request.getParameter("fdIsEnable");
        NativeQuery nativeQuery = getBaseDao().getHibernateSession().createNativeQuery(updateEnableSql);
        nativeQuery.addSynchronizedQuerySpace("sys_org_matrix_version");
        nativeQuery
                .setParameter("fdIsEnable", "true".equalsIgnoreCase(fdIsEnable))
                .setParameter("fdIsDelete", Boolean.FALSE)
                .setParameter("fdMatrixId", fdMatrixId)
                .setParameter("fdVersion", fdVersion).executeUpdate();
    }

    @Override
    public void addVersion(SysOrgMatrix fdMatrix, String fdVersion) throws Exception {
        // 需要增加版本信息
        SysOrgMatrixVersion matrixVersion = new SysOrgMatrixVersion();
        matrixVersion.setFdId(IDGenerator.generateID());
        matrixVersion.setFdName(fdVersion);
        matrixVersion.setFdVersion(Integer.valueOf(fdVersion.substring(1)));
        matrixVersion.setFdCreateTime(new Date());
        matrixVersion.setFdMatrix(fdMatrix);
        if ("V1".equalsIgnoreCase(fdVersion) && "1".equals(fdMatrix.getMatrixType())) {
            // 如果是系统默认矩阵，且版本是V1，默认为启用
            matrixVersion.setFdIsEnable(Boolean.TRUE);
        } else {
            // 增加版本时，初始为禁用，需要手动激活
            matrixVersion.setFdIsEnable(Boolean.FALSE);
        }
        add(matrixVersion);
    }

    @Override
    public boolean isEnable(String fdMatrixId, String fdVersion) throws Exception {
        SysOrgMatrixVersion version = getVersion(fdMatrixId, fdVersion);
        if (version != null && BooleanUtils.isNotTrue(version.getFdIsDelete())) {
            return BooleanUtils.isTrue(version.getFdIsEnable());
        }
        return false;
    }

    @Override
    public void deleteVersion(String fdMatrixId, String fdVersion) throws Exception {
        SysOrgMatrixVersion version = getVersion(fdMatrixId, fdVersion);
        if (version != null) {
            version.setFdIsEnable(Boolean.FALSE);
            version.setFdIsDelete(Boolean.TRUE);
            update(version);
        }
    }

    @Override
    public List<SysOrgMatrixVersion> getVersions(String fdMatrixId) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setJoinBlock(" inner join sysOrgMatrixVersion.hbmMatrix fdMatrix");
        hqlInfo.setWhereBlock("fdMatrix.fdId = :fdMatrixId and sysOrgMatrixVersion.fdIsDelete = :fdIsDelete");
        hqlInfo.setParameter("fdMatrixId", fdMatrixId);
        hqlInfo.setParameter("fdIsDelete", Boolean.FALSE);
        hqlInfo.setOrderBy("sysOrgMatrixVersion.fdVersion desc");
        return findList(hqlInfo);
    }

    @Override
    public SysOrgMatrixVersion addVersion(SysOrgMatrix fdMatrix) throws Exception {
        // 取出最大的版本，在后面增加一个版本
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setJoinBlock(" inner join sysOrgMatrixVersion.hbmMatrix fdMatrix");
        hqlInfo.setWhereBlock("fdMatrix.fdId = :fdMatrixId");
        hqlInfo.setParameter("fdMatrixId", fdMatrix.getFdId());
        hqlInfo.setOrderBy("sysOrgMatrixVersion.fdVersion desc");
        List<SysOrgMatrixVersion> list = findList(hqlInfo);
        SysOrgMatrixVersion oriVersion = null;
        if (CollectionUtils.isNotEmpty(list)) {
            oriVersion = list.get(0);
        }

        SysOrgMatrixVersion newVersion = new SysOrgMatrixVersion();
        newVersion.setFdId(IDGenerator.generateID());
        if (oriVersion != null) {
            newVersion.setFdVersion(oriVersion.getFdVersion() + 1);
        } else {
            newVersion.setFdVersion(1);
        }
        newVersion.setFdName("V" + newVersion.getFdVersion());
        newVersion.setFdMatrix(fdMatrix);
        newVersion.setFdIsEnable(Boolean.FALSE);
        add(newVersion);
        return newVersion;
    }

    private SysOrgMatrixVersion getVersion(String fdMatrixId, String fdVersion) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setJoinBlock(" inner join sysOrgMatrixVersion.hbmMatrix fdMatrix");
        hqlInfo.setWhereBlock("fdMatrix.fdId = :fdMatrixId and sysOrgMatrixVersion.fdName = :fdVersion");
        hqlInfo.setParameter("fdMatrixId", fdMatrixId);
        hqlInfo.setParameter("fdVersion", fdVersion);
        List<SysOrgMatrixVersion> list = findList(hqlInfo);
        if (CollectionUtils.isNotEmpty(list)) {
            return list.get(0);
        }
        return null;
    }

}
