<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.view" sidebar="no">
    
    <template:replace name="title">
        <c:out value="${ lfn:message('km-calendar:kmCalendarRequestAuth.persons') }" />
    </template:replace>
	
	<%--操作按钮--%>
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float">
			<%--关闭--%>
			<ui:button text="${lfn:message('button.close')}" order="5" onclick="Com_CloseWindow();">
			</ui:button>
		</ui:toolbar>
	</template:replace>
	
	<template:replace name="content"> 
		<ui:tabpage expand="true" var-navwidth="90%">
			<p class="txttitle">${ lfn:message('km-calendar:kmCalendarRequestAuth.persons') }</p>
			<ui:content title="${ lfn:message('km-calendar:kmCalendarRequestAuth.persons') }">
		        <div class="lui_form_content_frame" >
		            <table class="tb_normal" width="95%">
		            	<tr>
		            		<td class="td_normal_title">
		            			<bean:message bundle="km-calendar" key="kmCalendarRequestAuth.fdRequestPerson" />
		            		</td>
		            		<td colspan="3">
		            			<xform:address propertyId="fdRequestPersonIds" propertyName="fdRequestPersonNames" 
    								showStatus="view" textarea="true" style="width:98%"/>
		            		</td>
		            	</tr>
		                <tr>
							<td class="td_normal_title">
								<bean:message bundle="km-calendar" key="kmCalendarRequestAuth.fdRequestAuth" />
							</td>
							<td>
								<xform:checkbox property="fdRequestAuth" showStatus="view">
									<xform:simpleDataSource value="authRead"><bean:message key="kmCalendarRquestAuth.fdRequestAuth.authRead" bundle="km-calendar"/></xform:simpleDataSource>
									<xform:simpleDataSource value="authEdit"><bean:message key="kmCalendarRquestAuth.fdRequestAuth.authEdit" bundle="km-calendar"/></xform:simpleDataSource>
									<xform:simpleDataSource value="authModify"><bean:message key="kmCalendarRquestAuth.fdRequestAuth.authModify" bundle="km-calendar"/></xform:simpleDataSource>
								</xform:checkbox>
							</td>	
						</tr>
		            </table>
		        </div>
			</ui:content>
		</ui:tabpage>
	</template:replace>
</template:include>