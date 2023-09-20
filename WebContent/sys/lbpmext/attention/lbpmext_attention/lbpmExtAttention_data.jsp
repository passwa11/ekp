<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@page import="com.landray.kmss.sys.lbpmext.attention.model.LbpmExtAttention,com.landray.kmss.sys.lbpmext.attention.model.LbpmExtAttentionScope" %>
<%@page import="java.util.List" %>

<list:data>
    <list:data-columns var="lbpmExtAttention" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column col="fdPerson.name" title="${lfn:message('sys-lbpmext-attention:lbpmExtAttention.fdPerson')}" escape="false">
            <c:out value="${lbpmExtAttention.fdPerson.fdName}" />
        </list:data-column>
        <list:data-column col="fdPerson.id" escape="false">
            <c:out value="${lbpmExtAttention.fdPerson.fdId}" />
        </list:data-column>
        <list:data-column col="fdPerson.deptLevelNames" escape="false" title="${lfn:message('sys-organization:sysOrgPerson.fdParent') }">
            <c:out value="${lbpmExtAttention.fdPerson.fdParent.fdName}" />
        </list:data-column>
        <list:data-column col="fdModuleName" title="${lfn:message('sys-lbpmext-attention:lbpmExtAttention.fdModuleName')}" styleClass="width120" escape="false">
        	<%
	        	LbpmExtAttention lbpmExtAttention=(LbpmExtAttention)pageContext.getAttribute("lbpmExtAttention");
	        	List<LbpmExtAttentionScope> lbpmExtAttentionScopeList=lbpmExtAttention.getLbpmExtAttentionScopeList();
	        	String showTexts="";
	        	if(lbpmExtAttentionScopeList != null && !lbpmExtAttentionScopeList.isEmpty()){
	        		for(int i=0;i<lbpmExtAttentionScopeList.size();i++){
		        		if(i>10){
		        			break;
		        		}
		        		LbpmExtAttentionScope lbpmExtAttentionScope=lbpmExtAttentionScopeList.get(i);
		        		showTexts+=lbpmExtAttentionScope.getFdAttentionCateShowtext()+";";
		        	}
	        	}
	        	pageContext.setAttribute("showTexts",showTexts);
	        	pageContext.setAttribute("scopeCount",lbpmExtAttentionScopeList.size());
        	%>
        	<div  style="width: 220px;text-overflow: ellipsis;overflow: hidden;white-space: nowrap;">
        		${showTexts}
        	</div>
        </list:data-column>
        <list:data-column col="docCreator.name" title="${lfn:message('sys-lbpmext-attention:lbpmExtAttention.fdPerson')}" escape="false">
            <c:out value="${lbpmExtAttention.docCreator.fdName}" />
        </list:data-column>
        <list:data-column col="docCreator.id" escape="false">
            <c:out value="${lbpmExtAttention.docCreator.fdId}" />
        </list:data-column>
        <list:data-column col="docCreator.deptLevelNames" escape="false" title="${lfn:message('sys-organization:sysOrgPerson.fdParent') }">
            <c:out value="${lbpmExtAttention.docCreator.fdParent.fdName}" />
        </list:data-column>
        <list:data-column col="docCreateTime" title="${lfn:message('sys-lbpmext-attention:lbpmExtAttention.docCreateTime')}">
            <kmss:showDate value="${lbpmExtAttention.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <!-- 其它操作 -->
		<list:data-column headerClass="width120" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/sys/lbpmext/attention/lbpmExtAttention.do?method=edit&fdId=${lbpmExtAttention.fdId}" requestMethod="GET">
						<!--编辑 -->
						<a class="btn_txt" href="javascript:editDoc('${lbpmExtAttention.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>
					<kmss:auth requestURL="/sys/lbpmext/attention/lbpmExtAttention.do?method=delete&fdId=${lbpmExtAttention.fdId}" requestMethod="GET">
						<!-- 删除 -->
						<a class="btn_txt" href="javascript:delDoc('${lbpmExtAttention.fdId}')">取消关注</a>
					</kmss:auth>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
