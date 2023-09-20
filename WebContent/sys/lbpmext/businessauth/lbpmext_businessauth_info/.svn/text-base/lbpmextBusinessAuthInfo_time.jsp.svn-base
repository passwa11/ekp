<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<script type="text/javascript">
Com_IncludeFile("dialog.js");
</script>
<script>
var dialogObject = null;
if(window.showModalDialog) {
	dialogObject = window.dialogArguments;
	if (dialogObject == null) {
		dialogObject = parent.Com_Parameter.Dialog;
	}
} else if(opener) {
	dialogObject = opener.Com_Parameter.Dialog;
} else {
	if (dialogObject == null) {
		dialogObject = parent.Com_Parameter.Dialog;
	}
}
$(function(){
	if(dialogObject){
		for(var key in dialogObject){
			if(key == "fdAuthStartTime"){
				$("input[name='fdStartTime']").val(dialogObject[key]);
			}else if(key == "fdAuthEndTime"){
				$("input[name='fdEndTime']").val(dialogObject[key]);
			}
		}
	}
});
</script>
<style>
.DIV_EditButtons{position: fixed;    left: 0;    right: 0;    text-align: center;    padding-top: 10px;    padding-bottom: 10px;    bottom: 0;    background-color: #fff;    border-top: 1px solid #d5d5d5;    z-index: 1070;}
</style>
<form>
<center>
<table class="tb_normal" width=95%>
	<tr>
		<td width=25% class="td_normal_title">
			<bean:message bundle="sys-lbpmext-businessauth" key="lbpmext.businessAuthDetail.fdStartTime"/>
		</td><td width=75% colspan="3">
			<xform:datetime property="fdStartTime" dateTimeType="datetime" validators="checkStartTime" showStatus="edit" required="true" style="width:150px;" subject="${lfn:message('sys-lbpmext-businessauth:lbpmext.businessAuthDetail.fdStartTime') }">
				</xform:datetime>					
		</td>
	</tr>
	<tr>
		<td width=25% class="td_normal_title">
			<bean:message bundle="sys-lbpmext-businessauth" key="lbpmext.businessAuthDetail.fdEndTime"/>
		</td><td width=75% colspan="3">
			<xform:datetime property="fdEndTime" dateTimeType="datetime" validators="checkEndTime checkEndTime2" showStatus="edit" required="true" style="width:150px;" subject="${lfn:message('sys-lbpmext-businessauth:lbpmext.businessAuthDetail.fdEndTime') }" htmlElementProperties="data-init-hour='23' data-init-minute='59'">
				</xform:datetime>					
		</td>
	</tr>
</table>
<div class="DIV_EditButtons" style="height:46px;">
	<ui:button text="${lfn:message('button.save') }" order="1" onclick="save();"  style="width:77px;">
	</ui:button>
	<ui:button text="${lfn:message('button.cancel') }" order="2" onclick="closeDialog();" style="width:77px;padding-left:10px">
	</ui:button>
</div>
</center>
</form>
<script>
var _$validation = $KMSSValidation();
function save(){
	if(!_$validation.validate()){
		return;
	}
	returnValue = {
		fdStartTime:$("input[name='fdStartTime']").val(),
		fdEndTime:$("input[name='fdEndTime']").val()
	};
	if (typeof(window.$dialog) != 'undefined') {
		$dialog.hide(returnValue);
	} else {
		window.close();
	}
}

function closeDialog(){
	if(!confirm('<bean:message key="message.closeWindow"/>')){
		return;
	}
	Com_CloseWindow();
}

</script>

<script>
_$validation.addValidator('checkStartTime',
	"<bean:message bundle='sys-lbpmext-businessauth' key='lbpmext.businessAuthDetail.startTimeMsg2'/>",
	function(v,e,o){
		var bool = true;
		var startTime = dialogObject.fdAuthStartTime;
		var endTime = $("input[name='fdEndTime']").val();
		if(v){
			if(startTime){
				if(Date.parse(v)<Date.parse(startTime)){
					bool = false;
				}
			}
			if(endTime){
				if(Date.parse(v)>Date.parse(endTime)){
					bool = false;
				}
			}
		}
		return bool;
	}
);
_$validation.addValidator('checkEndTime',
	"<bean:message bundle='sys-lbpmext-businessauth' key='lbpmext.businessAuthDetail.endTimeMsg2'/>",
	function(v,e,o){
		var bool = true;
		var endTime = dialogObject.fdAuthEndTime;
		var startTime = $("input[name='fdStartTime']").val();
		if(v){
			if(endTime){
				if(Date.parse(v)>Date.parse(endTime)){
					bool = false;
				}
			}
			if(startTime){
				if(Date.parse(v)<Date.parse(startTime)){
					bool = false;
				}
			}
		}
		return bool;
	}
);

_$validation.addValidator('checkEndTime2',
	"<bean:message bundle='sys-lbpmext-businessauth' key='lbpmext.businessAuthDetail.endTimeMsg3'/>",
	function(v,e,o){
		if(v){
			if(new Date().getTime()>=Date.parse(v)){
				return false;
			}
		}
		return true;
	}
);
</script>
<%@ include file="/resource/jsp/edit_down.jsp"%>