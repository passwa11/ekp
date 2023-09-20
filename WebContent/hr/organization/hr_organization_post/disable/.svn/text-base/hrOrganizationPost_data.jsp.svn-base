<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ page import="com.landray.kmss.hr.organization.model.HrOrganizationSyncSetting"%>
<%
	HrOrganizationSyncSetting syncSetting = new HrOrganizationSyncSetting();
	request.setAttribute("hrToEkpEnable", syncSetting.getHrToEkpEnable());
%>
<list:data>
    <list:data-columns var="hrOrganizationPost" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdName" title="${lfn:message('hr-organization:hrOrganizationPost.fdName')}" />
        
        <%-- 所属组织--%>
        <list:data-column property="fdParent.fdName" title="${lfn:message('hr-organization:hrOrganizationPost.org')}">
        </list:data-column>
        
         <%-- 岗位序列--%>
        <list:data-column property="fdPostSeq.fdName" title="${lfn:message('hr-organization:hrOrganizationPost.seq')}" />
        
         <%-- 人员编制--%>
        <list:data-column col="fdCompile" title="${lfn:message('hr-organization:hrOrganizationPost.compile')}">
        	<c:choose>
        		<c:when test="${hrOrganizationPost.fdIsCompileOpen && hrOrganizationPost.fdIsLimitNum == 'false'}">
        			${lfn:message('hr-organization:hr.organization.info.setup.unlimited')} 
        		</c:when>
        		<c:when test="${hrOrganizationPost.fdIsCompileOpen && hrOrganizationPost.fdIsLimitNum == 'true'}">
        			<c:out value="${hrOrganizationPost.fdCompileNum }" />
        		</c:when>
        		<c:otherwise>
        			${lfn:message('hr-organization:hr.organization.info.setup.noset')}
        		</c:otherwise>
        	</c:choose>
        </list:data-column>
        
       	<%-- 在职人数--%>
        <list:data-column col="fdNum" title="${lfn:message('hr-organization:hrOrganizationPost.job.num')}">
        	<c:out value="${fn:length(hrOrganizationPost.fdBePersons) }" />
        </list:data-column>
        
        <%-- 缺编/超编--%>
        <list:data-column col="fdCompileDesc" title="${lfn:message('hr-organization:hrOrganizationPost.compile.desc')}" escape="false">
        	<c:choose>
        		<c:when test="${hrOrganizationPost.fdIsCompileOpen && hrOrganizationPost.fdIsLimitNum == 'true'}">
        			<c:if test="${hrOrganizationPost.fdCompileNum > fn:length(hrOrganizationPost.fdBePersons) }">
        				<div class="ld-org-compile">
	        				<span class="vacancy"></span>
	                        <span>
		                        ${lfn:message('hr-organization:hr.organization.info.setup.shortage')} 
		                        ${hrOrganizationPost.fdCompileNum - fn:length(hrOrganizationPost.fdBePersons)}
	                        	${lfn:message('hr-organization:hr.organization.info.emp.p')}
                        	</span>
                        </div>
        			</c:if>
        			<c:if test="${hrOrganizationPost.fdCompileNum < fn:length(hrOrganizationPost.fdBePersons) }">
        				<div class="ld-org-compile">
	        				<span class="exceed"></span>
	                        <span>
	                          	${lfn:message('hr-organization:hr.organization.info.setup.overbooking')} 
		                      	${fn:length(hrOrganizationPost.fdBePersons) - hrOrganizationPost.fdCompileNum}
                        		${lfn:message('hr-organization:hr.organization.info.emp.p')} 
	                        </span>
                        </div>
        			</c:if>
        			<c:if test="${hrOrganizationPost.fdCompileNum == fn:length(hrOrganizationPost.fdBePersons) }">
        				<div class="ld-org-compile">
	        				<span class="all"></span>
	                        <span>${lfn:message('hr-organization:hr.organization.info.setup.manbian')} </span>
                        </div>
        			</c:if>
        		</c:when>
        		<c:otherwise>
        			-
        		</c:otherwise>
        	</c:choose>
        </list:data-column>
        
        <%-- 职级范围--%>
        <list:data-column col="fdPostRank" title="${lfn:message('hr-organization:hrOrganizationPost.rank.scope')}">
        	<c:out value="${hrOrganizationPost.fdRankMix.fdName }" />-<c:out value="${hrOrganizationPost.fdRankMax.fdName }" />
        </list:data-column>
        
        <%--职等--%>
        <list:data-column col="fdPostGrade" title="${lfn:message('hr-organization:hrOrganizationPost.grade')}">
        	<c:out value="${hrOrganizationPost.fdRankMix.fdGrade.fdName }" />-<c:out value="${hrOrganizationPost.fdRankMax.fdGrade.fdName }" />
        </list:data-column>
        
         <%-- 是否关键岗位--%>
        <list:data-column col="fdIsKey" title="${lfn:message('hr-organization:hrOrganizationPost.is.key')}">
        	<sunbor:enumsShow value="${ hrOrganizationPost.fdIsKey }" enumsType="hr_organization_yes_or_no" />
        </list:data-column>
        
         <%-- 是否涉密岗位--%>
        <list:data-column col="fdIsSecret" title="${lfn:message('hr-organization:hrOrganizationPost.is.secret')}">
        	<sunbor:enumsShow value="${ hrOrganizationPost.fdIsSecret }" enumsType="hr_organization_yes_or_no" />
        </list:data-column>
            
        <list:data-column col="docCreateTime" title="${lfn:message('hr-organization:hrOrganizationPostSeq.docCreateTime')}">
            <kmss:showDate value="${hrOrganizationPost.fdCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
         <!-- 其它操作 -->
         <kmss:auth requestURL="/hr/organization/hr_organization_post/hrOrganizationPost.do?method=setCompile">
         <c:if test="${hrToEkpEnable }">
		<list:data-column headerClass="width200" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="lui_hr_link_group">
				<span class="lui_hr_link_item ">
					<a class="lui_text_primary" href="javascript:changeEnabled('${hrOrganizationPost.fdId}')">
					
					${lfn:message('hr-organization:hr.organization.log.changeEnabled')}
					</a>
                </span>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
		</c:if>
		</kmss:auth>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
