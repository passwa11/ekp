<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="kmArchivesBorrow" list="${queryPage.list}" varIndex="status">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column col="docSubject" escape="false" headerStyle="width:400px;" title="${lfn:message('km-archives:kmArchivesBorrow.docSubject')}">
        	<span class="com_subject"><c:out value="${kmArchivesBorrow.docSubject }"/></span>
        </list:data-column>
        <list:data-column col="fdBorrower.fdName" title="${lfn:message('km-archives:kmArchivesBorrow.fdBorrower')}" escape="false">
        	<ui:person personId="${kmArchivesBorrow.fdBorrower.fdId}" personName="${kmArchivesBorrow.fdBorrower.fdName}" />
        </list:data-column>
        <!-- 借阅时间 -->
        <list:data-column headerClass="width100" col="fdBorrowDate" title="${lfn:message('km-archives:kmArchivesBorrow.fdBorrowDate')}" escape="false">
			<kmss:showDate value="${kmArchivesBorrow.fdBorrowDate }" type="datetime"></kmss:showDate>
		</list:data-column>
		<!-- 创建时间 -->
		<list:data-column col="docCreateTime" title="${lfn:message('km-archives:kmArchivesBorrow.docCreateTime')}" escape="false">
			<kmss:showDate value="${kmArchivesBorrow.docCreateTime }" type="date"></kmss:showDate>
		</list:data-column>
        <list:data-column headerClass="width140" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/km/archives/km_archives_borrow/kmArchivesBorrow.do?method=add" requestMethod="GET">
						<!-- 编辑 -->
						<a class="btn_txt" href="javascript:edit('${kmArchivesBorrow.fdId}')">${lfn:message('button.edit')}</a>
						<!-- 禁用 -->
						<a class="btn_txt" href="javascript:del('${kmArchivesBorrow.fdId}')">${lfn:message('button.delete')}</a>
					</kmss:auth>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
		<!-- lbpm_main -->
        <list:data-column col="lbpm_main_listcolumn_node" title="${lfn:message('km-archives:lbpm.currentNode') }" escape="false">
            <kmss:showWfPropertyValues var="nodevalue" idValue="${kmArchivesBorrow.fdId}" propertyName="nodeName" />
            <div title="${nodevalue}">
                <c:out value="${nodevalue}"></c:out>
            </div>
        </list:data-column>
        <!-- lbpm_main -->
        <list:data-column col="lbpm_main_listcolumn_handler" title="${lfn:message('km-archives:lbpm.currentHandler') }" escape="false">
            <kmss:showWfPropertyValues var="handlerValue" idValue="${kmArchivesBorrow.fdId}" propertyName="handlerName" />
            <div style="font-weight:bold;" title="${handlerValue}">
                <c:out value="${handlerValue}"></c:out>
            </div>
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
        <list:data-paging page="${queryPage}" />
</list:data>
