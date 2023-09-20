<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.dialog">
	<template:replace name="content">
		<div style="position:relative;padding:7px 25px 5px 0px;text-align: right">
			<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">
				<ui:button text="${ lfn:message('third-ding:detail.transport.templateDownLoad') }" onclick="exportTemplate()" order="1">
				</ui:button>
				<ui:button text="${ lfn:message('third-ding:detail.transport.upLoad') }" onclick="_submit()" order="2">
				</ui:button>
				<ui:button text="${ lfn:message('button.close') }" onclick="_cancel();" order="2">
				</ui:button>
			</ui:toolbar>
		</div>
		<center>
		<form action="" method="post" enctype="multipart/form-data">
			<%-- 上传路径 --%>
			<input type="hidden" name="uploadActionUrl">
			<%-- 异常后数据是否需要回滚，默认为true --%>
			<input type="hidden" name="isRollBack">
			<table class="tb_normal" style="margin: 20px 0" width=98%>
				<tr>
					<td width="15%" class="td_normal_title">
						${lfn:message('common.fileUpLoad.selectFile')}
					</td>
					<td colspan="3">
						<input class="input_file" type="file" name="file" accept=".xls,.xlsx" enctype = 'multipart/form-data'/>
					</td>
				</tr>
				<tr>
				<td class="td_normal_title" width="20%">
					<bean:message  bundle="sys-transport" key="fdImportType"/></td>
				<td>
					<input type="radio" checked="checked" name="fdImportType" value="cover"
						 title='${ lfn:message('third-ding:detail.transport.cover') }'/>
						${ lfn:message('third-ding:detail.transport.cover') }&nbsp;
					<input type="radio" name="fdImportType" value="append"
						title='${ lfn:message('third-ding:detail.transport.append') }'/>
						${ lfn:message('third-ding:detail.transport.append') }&nbsp;
				</td>
				</tr>
				
				<tr>
					<td colspan="2">
						${ lfn:message('third-ding:detail.transport.result') }：<span style="color:red" id="result"></span>
					</td>
				</tr>
				<tr>
				<!-- 详情 -->
				<td colspan="2">
					<div style="margin-top: 8px;">
					    <div>
							<image src='${KMSS_Parameter_StylePath}msg/plus.gif' onclick='showMoreErrInfo(this);' style='cursor:pointer'>
							<bean:message bundle="sys-transport" key="sysTransport.title.detail"/>	    
					    </div>
						<div id="div_errorCell" style="display:none;margin-top: 5px;"></div>	
					</div>
				</td>
				</tr>
				<tr>
					<td colspan="2">
					<span>
					注意：</br>
					</span>
					<span>
						1.组织架构类型的字段，导入时，id与名称必须对应，且id必须是已存在的数据，否则不会导入,例如:136de1d7d67ddf6a694a59545a48e15b,深圳蓝凌。</br>
					</span>
					<span>	
						2.时间类型的字段，必须为时间格式，且格式必须和字段对应（date、datetime、time）,例如：2019-04-05，2019-04-05 00:00，15:30。</br>
					</span>
					<span>	
						3.未填必填字段，导入时会跳过该行数据。</br>
					</span>
					<span>	
						4.附件不能导入。</br>
					</span>
					<span>	
						5.报错信息提示的列数是只文件中的列数。</br>
					</span>	
					</td>
				</tr>
	</table>
	</form>
	</center>
<script>

seajs.use([ 'lui/jquery','lui/dialog'],function($, dialog) {
	$(function() {
		var uploadActionUrl = "${JsParam.url}uploadActionUrl";
		$("form").attr("action", uploadActionUrl); // 设置form请求路径
		$("form [name=uploadActionUrl]").val(uploadActionUrl); // 保存form请求路径
	});

	window.exportTemplate=function(){
		var tableId="${JsParam.tableId}";
		var ths=window.parent.document.getElementById(tableId).getElementsByClassName("tr_normal_title")[0].getElementsByTagName("td");
		var thsValues=null;
			for (var i = 2; i < ths.length-1; i++) {
					if(thsValues==null){
						thsValues=ths[i].innerText;
					}else{
						thsValues=thsValues+","+ths[i].innerText;
					}
			}
			var url="${JsParam.url}templateDownload";
			submitForm(url,"post","json",encodeURI(thsValues));
	}

	window.submitForm=function(url,name,key,thvalue){
	    var newWindow = window.parent.open(name);  
	    if (!newWindow)  
	        return false;  
	    var html = "";  
	    html += "<html><head></head><body><form id='formid' method='post' action='" + url + "'>";  
	    if(thvalue){
	       html += "<input type='hidden' name='thsvalue' value='" +thvalue+ "'/>";
	    }  
	    html += "</form><script type='text/javascript'>document.getElementById('formid').submit();";  
	    html += "<\/script></body></html>".toString().replace(/^.+?\*|\\(?=\/)|\*.+?$/gi, "");   
	    newWindow.document.write(html);  
	    return newWindow;
	};
	
	var message = "";
	var flag=true;
	var required=true;

	// 上传
	window._submit = function() {
		var url="${JsParam.url}uploadActionUrl";
		var form = new FormData($("form")[0]);
		var success=true;
		message = "";
		var fileInput = $('.input_file').get(0).files[0];
		if(!fileInput){
			$("#result").text("${ lfn:message('third-ding:detail.transport.result.failed') }");
			$("#div_errorCell").empty();
			$("#div_errorCell").append("${ lfn:message('third-ding:detail.transport.result.fileEmpty') }");
			return;
		}
		
		$.ajax({
			type:"post",
			url:url,
			data:form,
			//告诉jQuery不要去处理发送的数据
			processData:false,
			//告诉jQuery不要去设置Content-Type请求头,因为表单已经设置了multipart/form-data
			contentType:false,
			success:function(data) {
				flag=true;
				required=true;
				window.export_load = dialog.loading();
				var jsonObj = eval('(' + data + ')');
				var ths = "";
				var importType="";
				for(var f=0;f<jsonObj.length;f++){
					var obj	= jsonObj[f];
					if(obj.ths){
						ths = obj.ths;
					}else if(obj.importType){
						importType=obj.importType;
						if(importType=="cover"){
							var tableId="${JsParam.tableId}";
							var trs=window.parent.document.getElementById(tableId).getElementsByClassName("docListTr");
							if(trs){
								for (var x = 0; x < trs.length; x++) {
									window.parent.DocList_DeleteRow(trs[x]);
								}
							}
							
						}
					}else{
							var valueObj = obj.value;
							var values=valueObj.split(";");
							var newRow = window.parent.DocList_AddRow("${JsParam.tableId}");
							var tds = LUI.$(newRow).find(".docList");
							for (var i = 0; i < ths.length; i++) {
							 if(LUI.$(tds[i+2]).children("div").length>0){
								 if(LUI.$(tds[i+2]).children("div").attr("_xform_type")=="radio"){
									var radios = LUI.$(tds[i+2]).find("input[type=radio]");
									var haChecked  = false;
									 if(radios){
										 var isBreak = isNotNull(tds[i+2],values,f,i,"radio");
											if(isBreak){
												break;
											}
										 for(var j = 0; j < radios.length; j++){
											 if(radios[j].value==values[i]){
												 radios[j].checked=true;
												 haChecked=true;
											 }
										 }
									 }
									 if(!haChecked){
										 message=message+"第"+(f-1)+"行，"+"第"+(i+1)+"列，"+"数据有误！</br>";
										 flag=false;
									 }
									 
								 }else if(LUI.$(tds[i+2]).children("div").attr("_xform_type")=="text"){
									 if(LUI.$(tds[i+2]).find("input[type=text]")[0]){
										var isBreak = isNotNull(tds[i+2],values,f,i,"text");
										if(isBreak){
											break;
										}
										 LUI.$(tds[i+2]).find("input[type=text]")[0].value=values[i];
									 }
								 }else if(LUI.$(tds[i+2]).children("div").attr("_xform_type")=="address"){
									 if(LUI.$(tds[i+2]).find("input[type=text]")[0]&&LUI.$(tds[i+2]).find("input[type=hidden]")[0]){
										 var isBreak = isNotNull(tds[i+2],values,f,i,"text");
											if(isBreak){
												break;
											}
										 if(values[i].indexOf(",")>0||values[i]==""){
											 if(values[i].indexOf(",")>0){
												 var value=values[i].split(",");
												 $.ajax({     
												     type:"post",   
												     url:"${JsParam.url}checkSysOrgElement",     
												     data:{fdId:value[0],fdName:value[1]},
												     async:false,
												     success:function(data){
												    	 if(data =='false'){
												    		 flag = false;
												    		 message=message+"第"+(f-1)+"行，"+"第"+(i+1)+"列，"+"数据有误！</br>";
												    	 }else{
												    		 LUI.$(tds[i+2]).find("input[type=hidden]")[0].value=value[0];
															 LUI.$(tds[i+2]).find("input[type=text]")[0].value=value[1];
												    	 }
													},
													error:function(data){
														 flag = false;
											    		 message=message+"第"+(f-1)+"行，"+"第"+(i+1)+"列，"+"数据有误！</br>";
													}
											 });
											 }
										}else{
											message=message+"第"+(f-1)+"行，"+"第"+(i+1)+"列，"+"数据有误！</br>";
											flag=false;
											//continue;
										}
									 }
								 }else if(LUI.$(tds[i+2]).children("div").attr("_xform_type")=="textarea"){
									 if(LUI.$(tds[i+2]).find("textarea")[0]){
										 var isBreak = isNotNull(tds[i+2],values,f,i,"textarea");
											if(isBreak){
												break;
											}
										 LUI.$(tds[i+2]).find("textarea")[0].value=values[i];
									 }
								 }else if(LUI.$(tds[i+2]).children("div").attr("_xform_type")=="rtf"){
									 if(LUI.$(tds[i+2]).find("textarea")[0]){
										 var isBreak = isNotNull(tds[i+2],values,f,i,"textarea");
											if(isBreak){
												break;
											}
										 LUI.$(tds[i+2]).find("textarea")[0].value=values[i];
									 }
								 }else if(LUI.$(tds[i+2]).children("div").attr("_xform_type")=="datetime"||LUI.$(tds[i+2]).children("div").attr("_xform_type")=="date"||LUI.$(tds[i+2]).children("div").attr("_xform_type")=="time"){
									 if(LUI.$(tds[i+2]).find("input[type=text]")[0]){
										 var isBreak = isNotNull(tds[i+2],values,f,i,"text");
											if(isBreak){
												break;
											}
										 LUI.$(tds[i+2]).find("input[type=text]")[0].value=values[i];
									 }
								 }else if(LUI.$(tds[i+2]).children("div").attr("_xform_type")=="checkbox"){
									 var checkbox =LUI.$(tds[i+2]).find("input[type='checkbox']");
									 if(checkbox){
										 var isBreak = isNotNull(tds[i+2],values,f,i,"checkbox");
											if(isBreak){
												break;
											}
										 var value=values[i].split(",");
										 for(var j = 0; j < value.length; j++){
											 var hasChecked = false;
											 checkbox.each(
										                function() {
										                	var	text=$(this)[0].value;
										                	for (var k = 0; k < value.length; k++) {
										                		if(text==value[k]){
											                		$(this)[0].checked=true;
											                		hasChecked=true;
											                		__cbClick(this.name.substring(1),'null',false,null);
											                	}
															}
										                }
										            );
											 if(!hasChecked){
												 message=message+"第"+(f-1)+"行，"+"第"+(i+1)+"列，"+"数据有误！</br>";
												 flag=false;
											 }
										 }
									 }
									 
								 }else{
									 if(LUI.$(tds[i+2]).find("input[type=text]")[0]){
										 LUI.$(tds[i+2]).find("input[type=text]")[0].value=values[i];
									 }
									}
							 }
					}
							if(!required||!flag){
								success=false;
							}
							if(!required){
								window.parent.DocList_DeleteRow(newRow);
							}
							
							
					}
					
				}
				if(window.export_load!=null){
 					window.export_load.hide(); 
 				}
				if(!success){
					$("#result").text("${ lfn:message('third-ding:detail.transport.result.failed') }");
					$("#div_errorCell").empty();
					$("#div_errorCell").append(message);
				}else{
					$("#result").text("${ lfn:message('third-ding:detail.transport.result.success') }");
				}
			}
		});
	}
	
	function isNotNull(td,values,f,i,name){
		 var detailNotNullProp =  window.parent.formOption.detailNotNullProp;
		 var tableId="${JsParam.tableId}";
		 var tableForm = tableId.replace("TABLE_DocList_","");
		 var detail = detailNotNullProp[tableForm];
		 var propName = detail[name];
		 if(propName!=null){
		 var isBreak = false;
		 for (var t = 0; t < propName.length; t++) {
				var prop = propName[t].split(",");
				if("textarea"==name){
					 if(LUI.$(td).find(name)[0].name.indexOf("."+prop[0]) >= 0 && values[i]==""){
						 	message=message+"第"+(f-1)+"行，"+"第"+(i+1)+"列，"+prop[1]+"为必填字段，不能为空！</br>";
							required=false;
							isBreak=true;
							break;
					 }
				}else{
					 if(LUI.$(td).find("input[type="+name+"]")[0].name.indexOf("."+prop[0]) >= 0 && values[i]==""){
						 	message=message+"第"+(f-1)+"行，"+"第"+(i+1)+"列，"+prop[1]+"为必填字段，不能为空！</br>";
							required=false;
							isBreak=true;
							break;
					 }
				}
				
			}
				 return isBreak;;
		 }
	}
	
	// 取消
	window._cancel = function() {
		window.$dialog.hide();
	};
	
	//展开出错列表
	window.showMoreErrInfo = function(srcImg){
		var obj = document.getElementById("div_errorCell");
		if(obj.style.display=="none"){
			obj.style.display="block";
			srcImg.src = Com_Parameter.StylePath + "msg/minus.gif";
		}else{
			obj.style.display="none";
			srcImg.src = Com_Parameter.StylePath + "msg/plus.gif";
		}
	}
	
		
	function __cbClick(name, type, isArrayValue, onValueChange) {
		// 获得相应存储checkbox值列的div容器
		var divValue = window.parent.document.getElementById("div_" + name);
		if (!divValue) return;
		// 获得相应的值列表
		var hiddenElements = '', cbElement;
		var cbElements = window.parent.document.getElementsByName("_" + name);
		for (var i = cbElements.length - 1; i >= 0; i--) {
			cbElement = cbElements[i];
			if (cbElement.type.toLowerCase() == 'checkbox') {
				if (cbElement.checked) {
					hiddenElements += ';' + cbElement.value;
				} else if (type == 'boolean') {
					hiddenElements += ';false';
				}
			}
		}
		// 输出隐藏字段的HTML
		var buf = new Array(), elemList = hiddenElements.split(';');
		elemList.shift();
		var xformValue = null;
		if (isArrayValue) {
			for (var i = elemList.length - 1; i >= 0; i--) {
				buf.push('<input name="' + name + '" type="hidden" value="' + elemList[i] + '">');
			}
			xformValue = elemList;
		} else {
			xformValue = elemList.join(';');
			buf.push('<input name="' + name + '" type="hidden" value="' + xformValue + '">');
		}
		// 原生的innerhtml 在IE6-9的时候，特殊情况下会报“该操作的目标元件无效”的错误
		$(divValue).html(buf.join(''));
		if (onValueChange != null) {
			onValueChange(xformValue , cbElements);
		}
	};
});
</script>
	</template:replace>
</template:include>