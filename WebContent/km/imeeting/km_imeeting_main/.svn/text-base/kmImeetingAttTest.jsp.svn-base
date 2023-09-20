<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple" sidebar="no">
	<template:replace name="body">
	<style>
	.numberTb .titleTd{
		min-width:120px!important;
		max-width:150px!important;
	}
	</style>
	<script language="JavaScript">
	Com_IncludeFile("jquery.js");
	seajs.use(['theme!form']);
	</script>
		<div style="height: 350px;overflow-x:hidden;overflow-y:auto ">
			<center>
				<table class="tb_normal" width=95% style="margin-top:25px">
				   <tr>
					    <td class="td_normal_title" width=15%>附件名称</td>
						<td width=85%>
							 <input type="text" name="attachmentName"  class="inputsgl" style="width:100%"/>
						</td>
					</tr>
					<tr>
					    <td class="td_normal_title" width=15%>附件Url</td>
						<td width=85%>
						 <input type="text" name="attachmentUrl" class="inputsgl"  style="width:100%"/>
						</td>
					</tr>
					<tr>
					    <td class="td_normal_title" width=15%>附件fdKey</td>
						<td width=85%>
						 <input type="text" name="fdKey" class="inputsgl"  style="width:100%"/>
						</td>
					</tr>
				</table>
			</center>
			<center>
				<div style="margin-top: 25px;margin-bottom:20px;z-index: 100">
				    <ui:button id="ok_id" text="${lfn:message('button.ok') }" order="2"  onclick="optSubmit();">
					</ui:button>&nbsp;&nbsp;
					<ui:button text="${lfn:message('button.cancel') }" order="2"  onclick="$dialog.hide();">
					</ui:button>
				</div>
			</center>
		</div>
	<script language="JavaScript">
	function optSubmit(){
		var rtn = {};
		var attachmentName = document.getElementsByName("attachmentName")[0].value;
		var attachmentUrl = document.getElementsByName("attachmentUrl")[0].value;
		var fdKey = document.getElementsByName("fdKey")[0].value;
		
		rtn.attachmentName = attachmentName;
		rtn.attachmentUrl = attachmentUrl;
		rtn.fdKey = fdKey;
		$dialog.hide(rtn);
	}
	</script>
	</template:replace>
</template:include>
