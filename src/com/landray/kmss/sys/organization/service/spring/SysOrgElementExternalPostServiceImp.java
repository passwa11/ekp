package com.landray.kmss.sys.organization.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPost;
import com.landray.kmss.sys.organization.service.ISysOrgElementExternalPostService;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.util.SpringBeanUtil;

import java.util.List;

/**
 * 外部岗位扩展
 *
 * @author 潘永辉 Mar 17, 2020
 */
public class SysOrgElementExternalPostServiceImp extends SysOrgPostServiceImp
        implements ISysOrgElementExternalPostService {

    public ISysOrgElementService getSysOrgElementService() {
        ISysOrgElementService sysOrgElementService = (ISysOrgElementService) SpringBeanUtil.getBean("sysOrgElementService");
        return sysOrgElementService;
    }

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        try {
            // 生态组织需要发送实时同步事件
            setEventEco(true);
            return super.add(modelObj);
        } finally {
            removeEventEco();
        }
    }

    @Override
    public void update(IBaseModel modelObj) throws Exception {
        try {
            // 生态组织需要发送实时同步事件
            setEventEco(true);
            super.update(modelObj);
        } finally {
            removeEventEco();
        }
    }

    @Override
    public void updateInvalidated(String id, RequestContext requestContext) throws Exception {
        try {
            // 生态组织需要发送实时同步事件
            setEventEco(true);
            super.updateInvalidated(id, requestContext);
        } finally {
            removeEventEco();
        }
    }

    @Override
    public void updateInvalidated(String[] ids, RequestContext requestContext) throws Exception {
        try {
            // 生态组织需要发送实时同步事件
            setEventEco(true);
            super.updateInvalidated(ids, requestContext);
        } finally {
            removeEventEco();
        }
    }

    @Override
    public void save(SysOrgPost post, boolean isAdd) throws Exception {
        try {
            // 生态组织需要发送实时同步事件
            setEventEco(true);
            if (isAdd) {
                super.add(post);
            } else {
                super.update(post);
            }
        } finally {
            removeEventEco();
        }
    }

    @Override
    public void updateTransformOut(SysOrgElement outParent, List<SysOrgElement> sysOrgElementList) throws Exception {
        for (SysOrgElement sysOrgElement : sysOrgElementList) {
            sysOrgElement.setFdIsExternal(true);
            if (outParent != null) {
                sysOrgElement.setHbmParent(outParent);
            }
            getSysOrgElementService().update(sysOrgElement);
        }

    }

}
