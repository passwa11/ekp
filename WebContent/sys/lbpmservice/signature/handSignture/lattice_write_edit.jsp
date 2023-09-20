<%@page import="com.landray.kmss.util.StringUtil"%>
<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<div class="latticeWriteDiv">
	<img class="latticeImg" style="width:100%;height:100%;">
	<input name="latticeId" type="hidden" value="${param.latticeId }" />
	<input name="latticeName" type="hidden" value="${param.latticeName }" />
	<input name="latticeHight" type="hidden" value="${param.latticeHight }" />
	<input name="latticeValue" type="hidden" >
	<input name="latticeBtn" type="button" value="${ lfn:message('sys-xform-base:newAuditNote.submit') }" onclick="lbpm.globals.newAuditSimpleTagFlowSubmitEvent(this);" style="display:none;height:60px;width:60px;font-size:18px;cursor:pointer;background-color:#4285f4;z-index:101;position:absolute;top:0px;right:0px;margin-top:1px;margin-right:-1px;" class="btnopt" />
</div>
<%
	String latticeWebSocketIp = ResourceUtil.getKmssConfigString("sys.tstudy.websocket.ip");
	if(StringUtil.isNull(latticeWebSocketIp)) {
		latticeWebSocketIp = "127.0.0.1";
	}
	pageContext.setAttribute("latticeWebSocketIp", latticeWebSocketIp);
%>
<script>
var latticeWebSocketIp = "${latticeWebSocketIp}";
Com_IncludeFile("draw.js",Com_Parameter.ContextPath + "sys/lbpmservice/signature/handSignture/","js",true);
$(function(){
	var isExit = false;
	for(var i = 0;i < Com_Parameter.event["confirm"].length ; i++){
		if(Com_Parameter.event["confirm"][i] === latticeWriteAdd){
			isExit = true;
			break;
		}
	}
	if(!isExit){
		Com_Parameter.event["confirm"].push(latticeWriteAdd);
	}
})

function latticeWriteAdd(){
	$(".latticeWriteDiv").each(function(){
		var latticeValue = $(this).find("input[name='latticeValue']").val();
		if(latticeValue){
			$(".process_review_content[name='fdUsageContent']").val("");
			var latticeId = $(this).find("input[name='latticeId']").val();
			var latticeName = $(this).find("input[name='latticeName']").val();
			var latticeHight = $(this).find("input[name='latticeHight']").val();
			var url = '${LUI_ContextPath}/sys/lbpmservice/handsignture/SysLatticeAction.do?method=save';
			$.ajax({
				url : url,
				type : 'POST',
				dataType : 'json',
				data : $.param({"latticeValue":latticeValue,"latticeId":latticeId, "latticeName":latticeName, "latticeHight":latticeHight}),
		   	});
			return false;
		}
	});
	return true;
}

function latticeMouseover(dom){
	$(dom).find("input[name='latticeBtn']").show();
}

function latticeMouseout(dom){
	$(dom).find("input[name='latticeBtn']").hide();
}
</script>