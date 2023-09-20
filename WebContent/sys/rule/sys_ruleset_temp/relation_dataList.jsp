<%@ page import="org.apache.commons.beanutils.PropertyUtils" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="templateModel" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<list:data-column  property="fdName" title="${ lfn:message('sys-rule:templateModel.fdName') }" style="text-align:center;min-width:180px">
		</list:data-column>
		<list:data-column headerClass="width80" col="relationState" title="${ lfn:message('sys-rule:templateModel.relationStatus') }" escape="false">
		    <c:if test="${relationStates[templateModel.fdId]}">
				<span style='color:green;'><bean:message bundle="sys-rule" key="sysRuleTemplate.resultOption.true"/></span>
			</c:if>
			<c:if test="${!relationStates[templateModel.fdId]}">
				<span style='color:red'><bean:message bundle="sys-rule" key="sysRuleTemplate.resultOption.false"/></span>
			</c:if>
		</list:data-column>
		<list:data-column headerClass="width80" col="fdIsAvailable" title="${ lfn:message('sys-rule:templateModel.fdStatus') }" escape="false">
			<%
				boolean isRead = PropertyUtils.isReadable(pageContext.getAttribute("templateModel"),"fdIsAvailable");
				if(isRead){
			%>
			<c:if test="${templateModel.fdIsAvailable}">
				<bean:message bundle="sys-rule" key="sysRuleSet.available.result.true" />
			</c:if>
			<c:if test="${!templateModel.fdIsAvailable}">
				<bean:message bundle="sys-rule" key="sysRuleSet.available.result.false" />
			</c:if>
			<%
				}
			%>
		</list:data-column>
		<!-- 其它操作 -->
		<list:data-column headerClass="width140" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<c:if test="${urls[templateModel.fdId] != null && urls[templateModel.fdId] ne ''  }">
						<!-- 编辑 -->
						<a class="btn_txt rel_btn" href="#" onclick="Com_OpenWindow('<c:url value="${urls[templateModel.fdId] }"/>?method=edit&fdId=${templateModel.fdId}')">${lfn:message('button.edit')}</a>
						<!-- 查看 -->
						<a class="btn_txt rel_btn" href="#" onclick="Com_OpenWindow('<c:url value="${urls[templateModel.fdId] }"/>?method=view&fdId=${templateModel.fdId}')">${lfn:message('button.view')}</a>
						<!-- 查看引用 -->
						<a class="btn_txt rel_btn" href="#" onclick="openRefDialog('${templateModel.fdId}')"><bean:message key="button.lookRef" bundle="sys-rule"/></a>
					</c:if>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>
	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>