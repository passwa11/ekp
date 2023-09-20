<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="/resource/jsp/jsperror.jsp"%>
<%@ include file="/resource/jsp/common.jsp"%>
<tr>
	<td width="100px"><bean:message bundle="sys-lbpmservice" key="lbpmNode.currentNode.notify.type" /></td>
	<td id="NODE_TD_notifyType">
		<kmss:editNotifyType property="node_notifyType" value="no" /><br />
		<span class="com_help"><kmss:message bundle="sys-lbpmservice" key="lbpmNode.currentNode.notify.type.info" /></span>
	</td>
</tr>
<script type="text/javascript">
	AttributeObject.Init.AllModeFuns.push(function() {
		var nodeData = AttributeObject.NodeData;
		var notifyType = nodeData["notifyType"];
		$("input[name^='__notify_type_']:checkbox").each(function(index,element){
			if(notifyType && notifyType.indexOf($(element).val())>-1){
				$(element).attr("checked","true");
			}else{
				$(element).removeAttr("checked");
			}
		});
	});
	AttributeObject.AppendDataFuns.push(function(nodeData) {
		var notifyType = "";
		$("input[name^='__notify_type_']:checkbox:checked").each(function(index,element){
			notifyType+=";"+$(element).val();
		});
		if(notifyType){
			notifyType = notifyType.substring(1);
			nodeData["notifyType"]=notifyType;
		}else{
			nodeData["notifyType"]=null;
		}	
	});

</script>