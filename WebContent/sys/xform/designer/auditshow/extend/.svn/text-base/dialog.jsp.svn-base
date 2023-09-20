<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ include file="/resource/jsp/edit_top.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp" %>
	<script type="text/javascript">
	Com_IncludeFile("data.js");
	</script>
	<style>
	#empty{
	    width:735px;
		height:350px;
		display: table-cell;
		vertical-align: middle;
		text-align: center;
	}
	
	</style>
	<!-- 下拉框切换 -->
	<div id="showTable" >
	<div id="flow" style="margin-left:10px">
		<span style="font-size:14px">所选流程：</span>
		<select id='lbpms' onchange="switchFlow(this)" style="width:150px">
		</select>
	</div>
		<input id = "hide" style="display: none"/>
	<!-- 弹窗实际内容 -->
	<iframe frameborder="0" id="dialogIframe" src="<c:url value="/sys/xform/designer/auditshow/extend/dialog_iframe.jsp"/>" width="100%" height="100%"></iframe>
	</div>
	<!-- 没有审批或者签字节点的缺省图 -->
	<div id="empty" display ="none" >  
    <img alt="" src="${LUI_ContextPath}/sys/xform/designer/style/img/auditshow/default.png" >
	<p>没有可选择流程节点，请先完善流程设计！</p>
	</div>
	<script type="text/javascript">
		var dialogObject=null;
		if(window.showModalDialog){
			dialogObject = window.dialogArguments;
		}else{
			dialogObject = opener.Com_Parameter.Dialog;
		}
		 
	
		window.onload = function(){
			//流程无审批节点或签字节点展示的缺省图
			 if(dialogObject.parameters.isEmpty !=null){
				$("#showTable").hide();
				$("#empty").show();
			}  
		
			//初始化下拉框
			var $select = $("#lbpms");
			var lbpms = dialogObject.parameters.lbpms || [];
			for(var i=0; i<lbpms.length; i++){
				$select.append("<option value='"+lbpms[i].id+"'>"+lbpms[i].name+"</option>");
			}
			//#133493 审批意见控件设置按照“节点展示”时，切换流程，再打开会回到默认的流程
			var optWin = $($("#dialogIframe")[0].contentWindow.document).find("[name='optFrame']")[0].contentWindow;
            var selected = optWin.document.getElementsByName("F_SelectedList")[0];
			var $selected = $(selected);
            //默认进入展示已选择的第一个节点所在的流程
			if ($selected.find('option').length >0) {
				var $firstOpt = $selected.find('option')[0];
				var firstOptValue = $firstOpt.value;
				if(firstOptValue.indexOf("##")>-1){
					$("#lbpms").val(firstOptValue.slice(firstOptValue.indexOf("##")+2));
				}else{
					$("#lbpms").val(firstOptValue.slice(4));
				}
				$("#lbpms").trigger("change");
			}
            //点击已选节点展示它所在流程
			$selected.click(function (){
				var optValue = this.value;
				var newOptValue = optValue.slice(4);
				$("#lbpms").val(newOptValue);
				$("#lbpms").trigger("change");
			})
			$("#hide").click(function (){
				//所有已选择的节点展示他的流程名字
				if ($selected.find('option').length >0){
					var $selectedValues = $selected.find('option');
			    	for(var i=0;i<$selectedValues.length;i++){
					var $selectedValue = $selectedValues[i];
					var selectedValue = $selectedValue.value.slice(4);
					var $process = $("#lbpms option");
					for (var j = 0;j<$process.length;j++){
						var processName = $process[j].text;
						var processValue = $process[j].value;
						if (processValue==selectedValue){
							$selectedValue.append("<"+processName+">");
						}
					}
				}
				}
			});
			$("#hide").trigger("click");
			var $optionList = $(optWin.document.getElementsByName("F_OptionList")[0]);
			$optionList.dblclick(function (){
				$("#hide").trigger("click");
			})
	    	var $btnAdd =$(optWin.document.getElementById("btnAdd"));
			$btnAdd.click(function (){
				$("#hide").trigger("click");
			})
			$selected.dblclick(function (){
				$("#hide").trigger("click");
			})
			var $btnDelete =$(optWin.document.getElementById("btnDelete"));
			$btnDelete.click(function (){
				$("#hide").trigger("click");
			})
			var $btnAddAll =$(optWin.document.getElementById("btnAddAll"));
			$btnAddAll.click(function (){
				$("#hide").trigger("click");
			})
			var $btnDeleteAll =$(optWin.document.getElementById("btnDeleteAll"));
			$btnDeleteAll.click(function (){
				$("#hide").trigger("click");
			})
			var $btnMoveUp =$(optWin.document.getElementById("btnMoveUp"));
			$btnMoveUp.click(function (){
				$("#hide").trigger("click");
			})
			var $btnMoveDown =$(optWin.document.getElementById("btnMoveDown"));
			$btnMoveDown.click(function (){
				$("#hide").trigger("click");
			})
		}
		
		function switchFlow(obj){
			var value = obj.value;
			var allNodes = dialogObject.parameters.allNodes;
			if(allNodes){
				var ary = allNodes[value];
				if($("#dialogIframe")[0] && $("#dialogIframe")[0].contentWindow){
					var optWin = $($("#dialogIframe")[0].contentWindow.document).find("[name='optFrame']")[0];
					if(optWin && optWin.contentWindow){
						var data=new KMSSData();
						data.AddHashMapArray(ary);
						optWin.contentWindow.setOptData(data);
					}
				}
			}else{
				alert("操作失败！");
			}
		}
	</script>
	<%-- </c:if> --%>
<%@ include file="/resource/jsp/edit_down.jsp"%>