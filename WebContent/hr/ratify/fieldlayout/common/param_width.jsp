<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/htmlhead.jsp" %>
<tr>
	<td class="td_normal_title" width=40%><bean:message bundle="hr-ratify" key="hrRatify.fieldLayout.width" /></td>
	<td><input type='text' id='control_width' class='inputsgl'
			   style="width: 80%" storage="true"
			   value="${(param.defaultWidth==null)?'45%':(param.defaultWidth)}" /></td>
</tr>
