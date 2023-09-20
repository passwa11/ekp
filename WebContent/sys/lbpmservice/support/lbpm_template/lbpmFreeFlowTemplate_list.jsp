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
		background: transparent url(../../resource/images/search_btn.png) no-repeat center;
		position: absolute;
		right: 0px;
		top: 0px;
		z-index: 1;
	}
	.tempList{
		display: inline-block;
		padding: 10px 20px;
	}
	.templistItem{
		display: inline-block;
		width: 150px;
		line-height: 30px;
	}
	.templistItem:hover .itemIcon{
		display: inline-block;
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
	.itemIcon{
		display: none;
		width: 14px;
		height: 15px;
		cursor: pointer;
		background-size: 14px;
		background-repeat: no-repeat;
		margin-left: 5px;
		background-image: url(../../resource/images/freeflow_delete_selected@2x.png);
	}
</style>
<div class="tempSearch">
	<input type="text" class="tempSearch_input" onkeyup="enterTrigleSelect(event,this);" placeholder="<bean:message bundle='sys-lbpmservice' key='lbpmservice.freeflow.insertTemplateName'/>">
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
	var curData;
	list();

	function list(lData){
		if(lData){
			curData = lData.GetHashMapArray();
		}else{
			var data = new KMSSData().AddBeanData("lbpmFreeflowDefaultTempService&fdProcessId=${JsParam.fdProcessId}");
			data.UseCache = false;
			curData = data.GetHashMapArray();
		}
		if(curData){
			$(".tempList").html("");
			for(var i =0;i<curData.length;i++){
				$(".tempList").append("<div class='templistItem'><label><input type='radio' name='listItem' value='"+curData[i].fdId+"'><span class='itemName'>"+curData[i].fdName+"</span></label><i class='itemIcon' onclick='_delete(this);'></i></div>");
			}
		}
	}

	function save(){
		var listItemValue = $("input[name='listItem']:checked").val()
		if(listItemValue){
			for(var i =0;i<curData.length;i++){
				if(curData[i].fdId==listItemValue){
					$dialog.hide(curData[i].fdContent);
				}
			}
		}else{
			alert("<bean:message bundle='sys-lbpmservice' key='lbpmservice.freeflow.seleteTempMsg'/>");
		}
	}

	function _delete(dom){
		var fdId = $(dom).closest(".templistItem").find("input[name='listItem']").val();
		if(fdId){
			seajs.use(['lui/jquery', 'lui/dialog'], function($, dialog, topic) {
				var url = '<c:url value="/sys/lbpmservice/support/lbpmFreeflowDefaultTempAction.do?method=delete"/>';
				dialog.confirm('<bean:message bundle="sys-lbpmservice" key="lbpmservice.freeflow.deleteTempMsg"/>',function(value){
					if(value==true){
						window.del_load = dialog.loading();
						$.ajax({
							url: url,
							type: 'GET',
							data:$.param({"fdId":fdId},true),
							dataType: 'json',
							error: function(data){
								if(window.del_load!=null){
									window.del_load.hide();
								}
								dialog.result(data.responseJSON);
							},
							success: function(){
								if(window.del_load!=null){
									window.del_load.hide();
								}
								list();
							}
						});
					}
				});
			});
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
		var field = $(".tempSearch_input").val();
		var sData = new KMSSData().AddBeanData("lbpmFreeflowDefaultTempService&fdProcessId=${JsParam.fdProcessId}&keyword="+encodeURIComponent(field));
		sData.UseCache = false;
		list(sData);
	}
</script>
</body>
</html>