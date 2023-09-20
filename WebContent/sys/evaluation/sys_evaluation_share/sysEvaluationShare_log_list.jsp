<%@page import="com.landray.kmss.util.face.SysFaceConfig"%>
<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/list.tld" prefix="list" %>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/lfn.tld" prefix="lfn" %>

<%@page import="com.landray.kmss.util.StringUtil" %>
<%@ page import="com.landray.kmss.sys.evaluation.model.SysEvaluationShare" %>
<%@ page import="com.landray.kmss.sys.organization.model.SysOrgElement" %>
<%@ page import="com.landray.kmss.util.ArrayUtil" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="com.landray.kmss.util.SpringBeanUtil" %>
<%@ page import="com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService" %>
<%@ page import="com.landray.kmss.constant.SysOrgConstant" %>
<%@ page import="com.landray.kmss.common.service.IBaseService" %>
<%@ page import="com.landray.kmss.common.model.IBaseModel" %>
<%@ page import="com.landray.kmss.util.ModelUtil" %>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld"
           prefix="person" %>
<list:data>
    <list:data-columns var="item" list="${queryPage.list}">
        <list:data-column property="docCreator.fdName" title="${ lfn:message('sys-evaluation:sysEvaluationShare.docCreator') }">
        </list:data-column>
        <list:data-column property="docCreator.fdId">
        </list:data-column>
        <list:data-column col="docCreator" title="${ lfn:message('sys-evaluation:sysEvaluationShare.docCreator') }" escape="false">
            <ui:person personId="${item.docCreator.fdId}" personName="${item.docCreator.fdName}"></ui:person>
        </list:data-column>
        <list:data-column property="fdShareTime" title="${ lfn:message('sys-evaluation:sysEvaluationShare.fdShareTime') }">
        </list:data-column>
        <list:data-column col="fdShareReason" title="${ lfn:message('sys-evaluation:sysEvaluationShare.fdShareReason')}" escape="false">
            <%
                SysEvaluationShare sysEvaluationShare = (SysEvaluationShare) pageContext.getAttribute("item");
                if (sysEvaluationShare != null &&
                        StringUtil.isNotNull(sysEvaluationShare.getFdShareReason())) {
                	out.print(SysFaceConfig.getUrl(request, sysEvaluationShare.getFdShareReason()));
                } else {
                    out.print("");
                }
            %>
        </list:data-column>
        <list:data-column col="goalPersonNames" title="${ lfn:message('kms-common:kmsShareMain.shareTarget')}" escape="false">
            <%
                SysEvaluationShare sysEvaluationShare = (SysEvaluationShare) pageContext.getAttribute("item");
                if (sysEvaluationShare != null) {
                    Integer shareMode = sysEvaluationShare.getFdShareMode();
                    // 分享到个人才有此字段
                    if (1 == shareMode) {
                        String goalPersonids = sysEvaluationShare.getGoalPersonids();

                        ISysOrgCoreService sysOrgCoreService =
                                (ISysOrgCoreService) SpringBeanUtil.getBean("sysOrgCoreService");
                        List orgList = new ArrayList();
                        if (StringUtil.isNotNull(goalPersonids)) {
                            orgList = sysOrgCoreService.findByPrimaryKeys(ArrayUtil.toStringArray(goalPersonids.split(";")));
                        }

                        if (orgList.size() > 0) {
                            StringBuffer sb = new StringBuffer();
                            int personType = SysOrgConstant.ORG_TYPE_PERSON;
                            for (int i = 0; i < orgList.size(); i++) {
                                SysOrgElement p = (SysOrgElement) orgList.get(i);
                                String authorString = "";
                                if (personType == p.getFdOrgType()) {
                                    authorString = "<a class='com_author' href='" + request.getContextPath() +
                                            "/sys/person/sys_person_zone/sysPersonZone.do?method=view&fdId=" +
                                            p.getFdId() + "' target='_blank'>" + p.getFdName() + "</a>";
                                } else {
                                    authorString = "<span style='color:#333;'>"+p.getFdName()+"</span>";
                                }

                                sb.append(authorString + "，");
                            }
                            String goalPesonNames = sb.toString();
                            out.print(goalPesonNames.substring(0, goalPesonNames.length() - 1));
                        }
                    }
                }
            %>
        </list:data-column>
        <list:data-column property="fdGroupId">
        </list:data-column>
        <list:data-column col="fdGroupName">
            <%
	            try{
	                SysEvaluationShare sysEvaluationShare = (SysEvaluationShare) pageContext.getAttribute("item");
	                IBaseService snsGroupMainService = (IBaseService) SpringBeanUtil.getBean("snsGroupMainService");
	                if(snsGroupMainService!=null){
	                     IBaseModel baseModel = snsGroupMainService.findByPrimaryKey(sysEvaluationShare.getFdGroupId());
	                     if(null!=baseModel){
	                         String groupName = ModelUtil.getModelPropertyString(baseModel,"docSubject",null,null);
	                         out.print(groupName);
	                     }else{
	                         out.print("");
	                     }
	                }
	            }catch(Exception e){
	                e.printStackTrace();
	                out.print("");
	            }
            %>
        </list:data-column>
        <list:data-column col="imgUrl">
            <person:headimageUrl personId="${item.docCreator.fdId}"/>
        </list:data-column>
    </list:data-columns>

    <list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }"></list:data-paging>

</list:data>