package com.landray.kmss.sys.organization.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.dto.HttpRequestParameterWrapper;
import com.landray.kmss.common.rest.util.ControllerHelper;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.organization.eco.IOrgRangeService;
import com.landray.kmss.sys.organization.interfaces.SysOrgHQLUtil;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.organization.service.ISysOrgGroupService;
import com.landray.kmss.sys.organization.service.ISysOrganizationStaffingLevelService;
import com.landray.kmss.sys.organization.util.SysOrgEcoUtil;
import com.landray.kmss.sys.organization.util.SysOrgUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.RestResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/data/sys-organization/organizationDialogGroupList", method = RequestMethod.POST)
public class OrgDialogGroupList implements IXMLDataBean, SysOrgConstant {

    private ISysOrgGroupService sysOrgGroupService;

    private ISysOrgElementService sysOrgElementService;

    private ISysOrganizationStaffingLevelService sysOrganizationStaffingLevelService;

    private IOrgRangeService orgRangeService;

    public ISysOrganizationStaffingLevelService getSysOrganizationStaffingLevelService() {
        return sysOrganizationStaffingLevelService;
    }

    public void setSysOrganizationStaffingLevelService(
            ISysOrganizationStaffingLevelService sysOrganizationStaffingLevelService) {
        this.sysOrganizationStaffingLevelService = sysOrganizationStaffingLevelService;
    }

    public void setOrgRangeService(IOrgRangeService orgRangeService) {
        this.orgRangeService = orgRangeService;
    }

    @ResponseBody
    @RequestMapping("getDataList")
    public RestResponse<?> getDataList(@RequestBody Map<String, Object> vo, HttpServletRequest request) throws Exception {
        HttpRequestParameterWrapper wrapper = ControllerHelper.buildRequestParameterWrapper(request, vo);
        return RestResponse.ok(getDataList(new RequestContext(wrapper)));
    }

    @Override
    @SuppressWarnings("unchecked")
    public List getDataList(RequestContext xmlContext) throws Exception {
        // 外部组织无法使用公共群组
        if (SysOrgEcoUtil.IS_ENABLED_ECO && SysOrgEcoUtil.isExternal()) {
            return new ArrayList();
        }

        // 判断是否有扩充的配置，注意扩充时必须将该部分代码删除
        IXMLDataBean extension = OrgDialogUtil.getExtension("groupList");
        if (extension != null && extension != this) {
            return extension.getDataList(xmlContext);
        }

        // 查找常用群组列表数据
        String whereBlock;
        String para = xmlContext.getParameter("groupCate");
        String nodeType = xmlContext.getParameter("nodeType");
        String orgType = xmlContext.getParameter("orgType");
        int selectType = ORG_TYPE_ALL;
        if (StringUtil.isNotNull(orgType)) {
            try {
                selectType = Integer.parseInt(orgType);
            } catch (NumberFormatException e) {
            }
        }
        List rtnValue = new ArrayList();
        HQLInfo hqlInfo = new HQLInfo();
        // 如果为群组类别并且当前地址本可以查询群组则直接查找群组，否则查找群组成员
        if (StringUtil.isNotNull(nodeType) && "cate".equals(nodeType)
                && (selectType & ORG_TYPE_GROUP) == ORG_TYPE_GROUP) {
            hqlInfo.setJoinBlock("left join sysOrgGroup.hbmGroupCate hbmGroupCate");
            if (StringUtil.isNull(para)) {
                whereBlock = "hbmGroupCate=null";
            } else {
                whereBlock = "hbmGroupCate.fdId=:hbmGroupCateId";
                hqlInfo.setParameter("hbmGroupCateId", para);
            }
            whereBlock += " and sysOrgGroup.fdIsAvailable= :fdIsAvailable";
            hqlInfo.setParameter("fdIsAvailable", Boolean.TRUE);
            hqlInfo.setWhereBlock(whereBlock);
            // 多语言
            hqlInfo.setOrderBy("sysOrgGroup.fdOrder, sysOrgGroup."
                    + SysLangUtil.getLangFieldName("fdName"));
            hqlInfo.setAuthCheckType("DIALOG_GROUP_READER");
            rtnValue = sysOrgGroupService.findList(hqlInfo);
        } else if (StringUtil.isNotNull(nodeType) && "group".equals(nodeType)) {
            if (StringUtil.isNotNull(para)) {
                hqlInfo.setParameter("hbmGroupsId", para);
                whereBlock = "hbmGroups.fdId = :hbmGroupsId and sysOrgElement.fdIsAvailable = :fdIsAvailable and sysOrgElement.fdOrgType<16";
                // 多语言
                hqlInfo
                        .setOrderBy(
                                "sysOrgElement.fdOrgType desc, sysOrgElement.fdOrder, sysOrgElement."
                                        + SysLangUtil
                                        .getLangFieldName("fdName"));
                hqlInfo.setParameter("fdIsAvailable", Boolean.TRUE);
                hqlInfo.setJoinBlock("left join sysOrgElement.hbmGroups hbmGroups");
                hqlInfo.setWhereBlock(SysOrgHQLUtil.buildWhereBlock(selectType,
                        whereBlock, "sysOrgElement"));
                hqlInfo.setAuthCheckType("DIALOG_READER");
                hqlInfo.setRowSize(SysOrgUtil.LIMIT_RESULT_SIZE + 1);
                hqlInfo.setGetCount(false);
                rtnValue = sysOrgElementService.findPage(hqlInfo).getList();
                // 职级过滤
                rtnValue = sysOrganizationStaffingLevelService
                        .getStaffingLevelFilterResult(rtnValue);
                if (rtnValue.size() > SysOrgUtil.LIMIT_RESULT_SIZE) {
                    return OrgDialogUtil.getResultEntries(rtnValue, xmlContext
                            .getContextPath());
                }

                if ((selectType & ORG_TYPE_GROUP) == ORG_TYPE_GROUP) {
                    HQLInfo hqlInfo_group = new HQLInfo();
                    hqlInfo_group.setParameter("hbmGroupsId", para);
                    hqlInfo_group.setJoinBlock("left join sysOrgGroup.hbmGroups hbmGroups");
                    hqlInfo_group
                            .setWhereBlock("sysOrgGroup.hbmGroups.fdId = :hbmGroupsId and sysOrgGroup.fdIsAvailable = :fdIsAvailable");
                    hqlInfo_group.setParameter("fdIsAvailable", Boolean.TRUE);
                    hqlInfo_group.setAuthCheckType("DIALOG_GROUP_READER");
                    rtnValue.addAll(sysOrgGroupService.findList(hqlInfo_group));
                    if (rtnValue.size() > SysOrgUtil.LIMIT_RESULT_SIZE) {
                        List entries = new ArrayList();
                        for (int i = 0; i < rtnValue.size(); i++) {
                            Map<String, String> map = OrgDialogUtil
                                    .getResultEntry(
                                            (SysOrgElement) rtnValue.get(i),
                                            xmlContext.getContextPath());
                            if ("group".equals(nodeType)) {
                                // #89249 添加一个canShowMore字段让前端标示是不是可以选择下一级
                                map.put("canShowMore", "false");
                            }
                            entries.add(map);
                        }

                        return entries;
                    }
                }
            }
        }
        List entries = new ArrayList();
        for (int i = 0; i < rtnValue.size(); i++) {
            SysOrgElement elem = (SysOrgElement) rtnValue.get(i);
            Map<String, String> map = OrgDialogUtil.getResultEntry(elem,
                    xmlContext.getContextPath());
            if ("group".equals(nodeType)) {
                // #89249 添加一个canShowMore字段让前端标示是不是可以选择下一级
                map.put("canShowMore", "false");
            }
            entries.add(map);
        }

        return entries;
    }

    public void setSysOrgGroupService(ISysOrgGroupService sysOrgGroupService) {
        this.sysOrgGroupService = sysOrgGroupService;
    }

    public void setSysOrgElementService(
            ISysOrgElementService sysOrgElementService) {
        this.sysOrgElementService = sysOrgElementService;
    }
}
