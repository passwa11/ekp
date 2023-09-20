<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<html>
<body>
<script>
	Com_IncludeFile("jquery.js|data.js");
</script>
<style>
	.tempSearch {
		display: inline-block;
		position: relative;
		line-height: 25px;
		height: 25px;
		padding-right: 24px;
		border: 1px solid #b4b4b4;
		border-radius: 5px;
		top: 10px;
		left: 20px;
		right: 20px;
	}
	.tempSearch_input {
		width: 380px;
		height: 24px;
		padding-left: 5px;
		padding-right: 24px;
		color: #b4b4b4;
		border: 0px;
		border-radius: 5px;
	}
	.tempSearch_select {
		border: 0;
		cursor: pointer;
		padding: 0;
		width: 24px;
		height: 24px;
		background: transparent url(../../../resource/images/search_btn.png) no-repeat center;
		position: absolute;
		right: 0px;
		top: 0px;
		z-index: 1;
	}
	.tempList{
		padding: 0 20px;
		height: 160px;
		overflow: auto;
		margin-top: 10px;
	}
	.templistItem{
		display: inline-block;
		width: 148px;
		line-height: 30px;
	}
	.itemName{
		max-width: 115px;
		overflow: hidden;
		text-overflow: ellipsis;
		white-space: nowrap;
		position: relative;
		top: 8px;
		display: inline-block;
		cursor: pointer;
	}
	.tempOperation{
		position: fixed;
		bottom: 10px;
		right: 40px;
	}
</style>
<div class="tempSearch">
	<input type="text" class="tempSearch_input" onkeyup="enterTrigleSelect(event,this);" placeholder="<bean:message bundle='sys-lbpmservice' key='lbpmservice.please.insert'/>">
	<input type="button" class="tempSearch_select" title="<bean:message key='button.search'/>" onclick="_select();">
</div>
<div class="tempList"></div>
<div class="tempOperation">
	<ui:button text="${lfn:message('button.cancel') }" order="2" onclick="closeDialog();" style="width:77px;">
	</ui:button>
	<ui:button text="${lfn:message('button.ok') }" order="1" onclick="save();"  style="width:77px;padding-left:10px">
	</ui:button>
</div>
<script>
	var selectedValues = [];
	var interval = setInterval(____Interval, "50");
	function ____Interval() {
		if (!window['$dialog'])
			return;
		window.parentParams = $dialog.___params;
		if(parentParams.selectedValue){
			selectedValues = parentParams.selectedValue.split(";");
		}else if(parentParams.defaultStartBranchIds){
			selectedValues = parentParams.defaultStartBranchIds.split(";");
		}
		__init();
		clearInterval(interval);
	}

	function __init(key){
		if(parentParams.store){
			$(".tempList").html("");
			for(var i =0;i<parentParams.store.length;i++){
				if(!key || parentParams.store[i].name.indexOf(key)>-1){
					$(".tempList").append("<div class='templistItem'><label><input type='checkbox' name='listItem' value='"+parentParams.store[i].id+"' onclick='setChecked(this);' "+($.inArray(parentParams.store[i].id,selectedValues)>-1?"checked":"")+(parentParams.defaultStartBranchIds && $.inArray(parentParams.store[i].id,parentParams.defaultStartBranchIds.split(";"))>-1 && parentParams.canSelectDefaultBranch=="false"?" disabled":"")+"><span class='itemName' title='"+parentParams.store[i].name+"'>"+parentParams.store[i].name+"</span></label></div>");
				}
			}
		}
	}

	function setChecked(dom){
		var index = $.inArray(dom.value,selectedValues);
		if(dom.checked){
			if(index==-1){
				selectedValues.push(dom.value);
			}
		}else{
			if(index>-1){
				selectedValues.splice(index,1);
			}
		}
	}

	function save(){
		if(selectedValues.length>0){
			$dialog.hide(selectedValues.join(";"));
		}else{
			alert("<bean:message bundle='sys-lbpmservice' key='lbpmservice.freeflow.seleteTempMsg'/>");
		}
	}

	function closeDialog(){
		$dialog.hide();
	}

	//搜索框按enter即可触发搜索
	function enterTrigleSelect(event,self){
		if (event && event.keyCode == '13') {
			_select();
		}
	}

	function _select(){
		var key = $(".tempSearch_input").val();
		if(key){
			__init(key);
		}else{
			__init();
		}
	}
</script>
</body>
</html>