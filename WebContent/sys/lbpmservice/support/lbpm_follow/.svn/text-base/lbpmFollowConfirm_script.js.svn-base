if(window.showModalDialog){
	dialogObject = window.dialogArguments;
}else{
	dialogObject = opener.Com_Parameter.Dialog;
}

//返回值
var retValue = {follow:false, closed:true};

function CancelFollow() {
	dialogObject.rtnData = retValue;
	top.close();
}
function Follow(){
	if (isFollow == "true"){
		if (isOldFollow){
			retValue = {follow:true, closed:true};
		}else{
			retValue = {follow:true, closed:true};
			var recordType = $("[name='recordType']:checked").val();
			retValue.recordType = recordType;
			var nodeIds = [];
			if (recordType === "0"){
				var nodeObjs = $("[name='node']:checked");
				nodeObjs.each(function(index,obj){
					nodeIds.push(obj.value);
				});
				retValue.nodeIds = nodeIds.join(";");
			}else{
				retValue.nodeIds = "";
			}
		}
	}else{
		retValue = {cancel:true, closed:true};
	}
	dialogObject.rtnData = retValue;
	parent.window.returnValue=retValue;
	top.close();
}
window.onload = function() {
	parent.document.title = document.title;
}

//构建跟踪节点html
var buildReocrdHtml = function(){
	var lbpm = dialogObject.parameter.lbpm;
	var followInfo = dialogObject.parameter.followInfo;
	var html = [];
	var isAll = followInfo.nodeIds ? false : true;
	html.push("<div class='lbpmFollow' style='padding-top:20px;'>");
	html.push("<div class='recordType'><label><input type='radio' onclick='lbpmFollowRecordTypeFun(this);' name='recordType' " + (isAll ? "checked='true'" : "") + " value='1'/>全部节点</label><br/>");
	html.push("<label><input type='radio'  onclick='lbpmFollowRecordTypeFun(this);' name='recordType'" + (isAll ? "" : "checked='true'") +" value='0'/>指定节点</label></div>");
	var nodes = lbpm.nodes;
	html.push("<div class='nodeIds'>")
	for (var key in nodes){
		var nodeObj = nodes[key];
		if('signNode' == nodeObj.XMLNODENAME 
				|| 'reviewNode' == nodeObj.XMLNODENAME ){
			
			html.push("<label><input type='checkBox' name='node' value='" + nodeObj.id + "'");
			if (!isAll){
				var followIdArr = followInfo.nodeIds.split(";");
				for (var i = 0; i < followIdArr.length; i++){
					var nodeId = followIdArr[i];
					if (key === nodeId){
						html.push(" checked='true'");
						break;
					}
				}
			}else{
				html.push(" disabled ");
			}
			html.push("/>" + nodeObj.name  + "</label><br/>");
		}
	}
	html.push("</div>");
	html.push("</div>");
	return html.join("");
};

lbpmFollowRecordTypeFun = function(src){
	var val = src.value;
	var nodeObjs = $("[name='node']",$(".lbpmFollow"));
	if (val === "1"){
		nodeObjs.prop("checked", false);
		nodeObjs.prop("disabled", true);
	}else{
		nodeObjs.prop("disabled", false);
	}
};

$(function(){
	//新的跟踪
	var newFollow = dialogObject.parameter.newFollow;
	//旧的取消跟踪
	var cancel = dialogObject.parameter.cancel;
	//旧的跟踪
	var oldFollow = dialogObject.parameter.oldFollow;
	if (newFollow){
		var nodeHtml = buildReocrdHtml();
		$("#lbpmFollowWrap").append(nodeHtml);
	}
	if (cancel){
		$("#lbpmFollowWrap").append(cancelFollowText);
		$("#lbpmFollowWrap").addClass("lbpmFollowWrap");
	}
	if (oldFollow){
		$("#lbpmFollowWrap").append(followText);
		$("#lbpmFollowWrap").addClass("lbpmFollowWrap");
	}
})

//添加关闭事件
Com_AddEventListener(window, "beforeunload", function(){dialogObject.AfterShow();});