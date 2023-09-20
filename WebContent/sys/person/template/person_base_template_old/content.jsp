<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/portal/portal.tld" prefix="portal"%>
<%@ page import="com.landray.kmss.sys.ui.util.SysUiConfigUtil"%>
<%
	request.setAttribute("fdWidth", SysUiConfigUtil.getFdWidth());
	request.setAttribute("fdPersonLeftSide", SysUiConfigUtil.getFdPersonLeftSide());
%>
<table style="width:${empty param.iframe ? ((empty param.pagewidth) ? fdWidth : (param.pagewidth)) : '100%'}; min-width:${empty param.iframe ? '980px' : '100%'};max-width:${empty param.iframe ? fdPageMaxWidth : '100%'}; margin:${empty param.iframe ? '15px auto' : '0'};table-layout: fixed;">
	<tr>
		<td valign="top" style="width: ${fdPersonLeftSide}px">
			<div class="lui_list_left_sidebar_frame old" style="width: ${fdPersonLeftSide}px">
				<div class="lui_portal_sidebar_h_l">
					<div class="lui_portal_sidebar_h_r">
						<div class="lui_portal_sidebar_h_c">	
						</div>
					</div>
				</div>
				<div class="lui_portal_sidebar_c_l">
					<div class="lui_portal_sidebar_c_r">
						<div class="lui_portal_sidebar_c_c">							
							<template:block name="nav" />
						</div>
					</div>
				</div>
				<div class="lui_portal_sidebar_f_l">
					<div class="lui_portal_sidebar_f_r">
						<div class="lui_portal_sidebar_f_c">	
						</div>
					</div>
				</div>
			</div>
		</td>
		<td style="width: 15px;"></td>
		<td valign="top">
			<div class="lui_list_body_frame">
				<%-- <template:block name="path" /> --%>
				<template:block name="content" />
			</div>
		</td>
	</tr>
</table>
