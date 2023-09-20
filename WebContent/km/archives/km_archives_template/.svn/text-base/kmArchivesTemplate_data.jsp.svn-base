<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@page import="com.landray.kmss.km.archives.model.KmArchivesTemplate"%>
<%@page import="com.landray.kmss.km.archives.model.KmArchivesDense"%>
<%@page import="java.util.List"%>
<list:data>
    <list:data-columns var="kmArchivesTemplate" list="${queryPage.list}" varIndex="status">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdName" title="${lfn:message('km-archives:kmArchivesTemplate.fdName')}" />
        <list:data-column property="docCreateTime" title="${lfn:message('km-archives:kmArchivesTemplate.docCreateTime')}" />
       <%--  <list:data-column col="docCreator.fdName" title="${lfn:message('km-archives:kmArchivesTemplate.docCreator')}" escape="false">
            <ui:person personId="${kmArchivesTemplate.docCreator.fdId}" personName="${kmArchivesTemplate.docCreator.fdName}" />
        </list:data-column> --%>
        <%
			if(pageContext.getAttribute("kmArchivesTemplate")!=null){
				KmArchivesTemplate kmArchivesTemplate = (KmArchivesTemplate)pageContext.getAttribute("kmArchivesTemplate"); 
				String listDenseLevelNames = "";
				List<KmArchivesDense> list = kmArchivesTemplate.getListDenseLevel();
				if(list != null && list.size() > 0) {
					for(int i = 0; i < list.size(); i ++) {
						if(list.get(i) != null && i == (list.size() - 1)){
							listDenseLevelNames += list.get(i).getFdName();
						} else {
							listDenseLevelNames += list.get(i).getFdName() + ";";
						}
					}
				}
				request.setAttribute("listDenseLevelNames",listDenseLevelNames);
			}
		%>
        <list:data-column headerClass="width100" col="fdDenseLevel.fdName" title="${lfn:message('km-archives:table.kmArchivesDense')}">
			<c:out value="${listDenseLevelNames}"></c:out>
		</list:data-column>
        <list:data-column headerClass="width100" property="docCreator.fdName" title="${lfn:message('km-archives:kmArchivesTemplate.docCreator')}">
		</list:data-column>
		<list:data-column col="fdDefaultFlag" escape="false" title="${lfn:message('km-archives:list.isDefaultFlag')}">
        	<c:if test="${kmArchivesTemplate.fdDefaultFlag=='1' }">
				<img src='<c:url value="/sys/profile/resource/images/profile_list_status_y.png"/>'>
			</c:if>
        </list:data-column>
        <list:data-column headerClass="width140" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/km/archives/km_archives_template/kmArchivesTemplate.do?method=edit&fdId=${kmArchivesTemplate.fdId}" requestMethod="GET">
						<!-- 编辑 -->
						<a class="btn_txt" href="javascript:edit('${kmArchivesTemplate.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>
					<kmss:auth requestURL="/km/archives/km_archives_template/kmArchivesTemplate.do?method=delete&fdId=${kmArchivesTemplate.fdId}" requestMethod="GET">
						<!-- 禁用 -->
						<a class="btn_txt" href="javascript:del('${kmArchivesTemplate.fdId}')">${lfn:message('button.delete')}</a>
					</kmss:auth>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
        <list:data-paging page="${queryPage}" />
</list:data>
