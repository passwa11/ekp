<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="/resource/jsp/jsperror.jsp"%>
<%@ include file="/resource/jsp/common.jsp"%>
<tr>
	<td class="tdTitle"><kmss:message key="lbpmservice.freeflow.jump" bundle="sys-lbpmservice" /></td>
	<td>
		<label>
			<input name="wf_canJump" type="checkbox" value="true" />
			<span class="__jumpMsp"><kmss:message key="lbpmservice.freeflow.close" bundle="sys-lbpmservice" /></span>
		</label>
	</td>
</tr>
<script type="text/javascript">
 	AttributeObject.Init.AllModeFuns.push(function() {
 		var isCanJump = AttributeObject.NodeData["canJump"];
 		if(isCanJump=="true"){
 			$(".__jumpMsp").text("<kmss:message key='lbpmservice.freeflow.open' bundle='sys-lbpmservice' />");
 		}
	});
 	
 	$("input[name='wf_canJump']").click(function(){
 		$(".__jumpMsp").text($(this).is(':checked')?"<kmss:message key='lbpmservice.freeflow.open' bundle='sys-lbpmservice' />":"<kmss:message key='lbpmservice.freeflow.close' bundle='sys-lbpmservice' />");
 	});
</script>