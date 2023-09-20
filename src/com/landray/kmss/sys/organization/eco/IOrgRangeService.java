package com.landray.kmss.sys.organization.eco;

import com.landray.kmss.sys.authentication.user.KMSSUser;
import com.landray.kmss.sys.organization.model.SysOrgElement;

import java.util.List;
import java.util.Set;

/**
 * 组织查看范围权限接口
 *
 * @author panyh
 * @date Jul 10, 2020
 */
public interface IOrgRangeService {

    /**
     * 权限计算入口
     *
     * @param kmssUser
     * @return
     */
    public AuthOrgRange getAuthOrgRange(KMSSUser kmssUser);

    /**
     * 组织管理员权限过滤
     *
     * @param list
     * @return
     */
    public List<SysOrgElement> authFilterAdmin(List<SysOrgElement> list);

    /**
     * 获取管辖的组织层级ID，在所管辖的组织里，有维护权限
     *
     * @return
     */
    public Set<String> getAdminHids();

}
