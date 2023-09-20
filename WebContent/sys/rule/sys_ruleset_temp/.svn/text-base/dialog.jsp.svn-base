<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="x-ua-compatible" content="IE=6"/>
</head>
<body>
	<!-- 单选框切换 -->
	<div id="mode" style="margin-left:10px">
		<label><input type="radio" name="mode" value="ruleSet" checked="checked" onchange="switchMode(this.value,this)">规则集</input></label>
		<label><input type="radio" name="mode" value="rule" onchange="switchMode(this.value,this)">规则</input></label>
	</div>
	<!-- 弹窗实际内容 -->
	<iframe frameborder="0" id="dialogIframe" src="<c:url value="/sys/rule/sys_ruleset_temp/dialog_iframe.jsp"/>" width="100%" height="100%"></iframe>
	<script type="text/javascript">
		var dialogObject=null;
		if(window.showModalDialog){
			dialogObject = window.dialogArguments;
			if(!dialogObject && opener && opener.Com_Parameter){//兼容有些浏览器取不到dialogArguments
				dialogObject = opener.Com_Parameter.Dialog;
			}
		}else{
			dialogObject = opener.Com_Parameter.Dialog;
		}
		var initMode = dialogObject.parameters.initMode;
		if(initMode && (initMode == "rule" || initMode == "ruleSet")){
			//切换模式，并屏蔽选择
			switchMode(initMode, null);
			document.getElementById("mode").style.display = "none";
		}else{
			//默认切选择规则集
			//switchMode("ruleSet", null);
			var selectMode = dialogObject.parameters.selectMode;
			if(selectMode && selectMode == "rule"){
				switchMode("rule", null);
			}else if((selectMode && selectMode == "ruleSet") || !selectMode || selectMode == ""){
				switchMode("ruleSet", null);
			}
		}
		function switchMode(value, obj){
			//修改窗口数据
			var mapObjs = dialogObject.parameters.mapObjs;
			var mapIds = dialogObject.parameters.mapIds;
			var treeTitle = dialogObject.parameters.treeTitle;
			dialogObject.parameters.mode = value;
			var treeBean,dataBean,searchBean;
			if(value == "ruleSet"){
				//选择规则集
				treeBean = dialogObject.parameters.firstTreeBean + "&mode=ruleSet";
				dataBean = "sysRuleSetDocService&parentId=!{value}&alreadyMapIds="+mapIds+"&mapObjs="+mapObjs+"&dataMode=ruleSet";
				searchBean = "sysRuleSetDocService&key=!{keyword}";
			}else{
				//选择规则
				treeBean = dialogObject.parameters.firstTreeBean + "&mode=rule";
				dataBean = "sysRuleSetRuleService&sysRuleSetDocId=!{value}&returnType="+encodeURIComponent(dialogObject.parameters.returnType);
				searchBean = "sysRuleSetRuleService&key=!{keyword}";
			}
			dialogObject.tree = null;
			var node = dialogObject.CreateTree(treeTitle);
			node.AppendBeanData(treeBean, dataBean, null, null, null);
			node.parameter = dataBean;
			dialogObject.SetSearchBeanData(searchBean);
			if(dialogObject.parameters.nameFieldValue && dialogObject.parameters.nameFieldValue != ""){
				var nameFieldValue = dialogObject.parameters.nameFieldValue;
				dialogObject.valueData.data = [{'name':nameFieldValue}];
			}
			//若obj为空，说明非事件触发
			if(obj == null){
				var modeObjs = document.getElementsByName("mode");
				for(var i=0; i<modeObjs.length; i++){
					if(modeObjs[i].value == value){
						modeObjs[i].checked = true;
						break;
					}
				}
			}
			//重新加载iframe
			var src = '<c:url value="/sys/rule/sys_ruleset_temp/dialog_iframe.jsp"/>';
			document.getElementById('dialogIframe').src = src;
		}
	</script>
</body>
</html>