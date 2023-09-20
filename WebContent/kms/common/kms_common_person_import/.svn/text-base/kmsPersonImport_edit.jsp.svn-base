<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="config.edit">
    <template:replace name="head">
	    <link rel="stylesheet" type="text/css" href="${ LUI_ContextPath}/kms/common/kms_common_person_import/style/kmsPersonImport_edit.css" />
    </template:replace>

	<template:replace name="content">

		<html:form
			action="/kms/common/kms_common_person_import/kmsPersonImport.do?method=importData">

			<html:hidden property="fdPersonId" />
			<html:hidden property="fdPersonName" />
			<html:hidden property="excelImportPath" value="" />
			<html:hidden property="errorInfo" value="" />
			
			<table class="tb_simple " style=" width:90%;margin-top: 20px;">
			    <tr>
				    <td width="100%" colspan="2" >
				        <div class="importLable" title="${lfn:message('kms-common:person.import.label') }">${lfn:message('kms-common:person.import.label') }</div>
				        <input class="templet" type="button" value="${lfn:message('kms-common:person.import.template.download') }" onclick="Com_OpenWindow('${LUI_ContextPath}/kms/common/kms_common_person_import/kmsPersonUpload.do?method=downLoadTemplate');">
				    </td>
			    </tr>
				<tr>
					<td width="100%" colspan="2" class="lui_icon_on">
					    <div class="uploadLeft" title="${lfn:message('kms-common:upload.file') }">${lfn:message('kms-common:upload.file') }</div>
						<div class="uploadRight"> 
							<c:import url="/sys/attachment/sys_att_main/sysAttMain_edit.jsp" charEncoding="UTF-8">
								<c:param name="fdKey" value="personAttachment" />
								<c:param name="fdMulti" value="false" />
								<c:param name="enabledFileType" value=".xlsx|.xls" />
							</c:import> 
						</div>	
					</td>
				</tr>
				<tr>
				</tr>
				<tr class="changeBlock">
					<td class="td_normal_title" width=15%>
					</td>
					<td width=85% class="td_normal_title">
						<div id="rtInfo"></div>					
					</td>
				</tr>
				<tr class="changeBlock">
					<td class="td_normal_title" width=15%>
					</td>
					<td width=85%>
						<div  class="errInfo" style="display:none">
							<div class="downLoadErr" style="cursor: pointer; color: #4f88ee" onclick="downLoadErr();">
							${lfn:message('kms-common:person.import.downError') }   
							</div>
						</div>	
					</td>
				</tr>
			</table>
				</html:form>
	</template:replace>
</template:include>

<script type="text/javascript">
	Com_IncludeFile("dialog.js")
	attachmentObject_personAttachment.uploadConfig.server = attachmentObject_personAttachment
			.getHost()
			+ Com_Parameter.ContextPath
			+ "kms/common/kms_common_person_import/kmsPersonUpload.do?method=uploadFile";

	attachmentObject_personAttachment.on('uploadSuccess', getLicenseFileName);
	function getLicenseFileName() {
		$('.upload_list_tr td').each(function(index) {

			if(index>11){
			    $(this).remove();
			}
			
		})
		buildFilePath();//得到附件地址
		importPerson(); //查询excel表中人员信息
	}

	function buildFilePath() {
		var arg = attachmentObject_personAttachment.__uploadSuccess.arguments[1];

		var excelPath = arg.excelPath || '';
		$("input[name='excelImportPath']").val(excelPath);

	}

	function importPerson() {
		$("input[name='errorInfo']").val("");
		var infoTable = $("#info_table");
		infoTable.empty();
		var emptyInfo = "";
		var att_table_tr = $("#att_xtable_personAttachment tr");
		
		if (att_table_tr.size() <= 0) {
			emptyInfo += "${lfn:message('kms-common:person.import.tip2') }";
		}
		if (emptyInfo != "") {
			seajs.use([ 'sys/ui/js/dialog' ], function(dialog) {
				dialog.alert(emptyInfo, function() {

				});
			});
			return;
		}



		seajs
				.use(
						[ 'sys/ui/js/dialog' ],
						function(dialog) {

							//校验中

							var validateDialog = dialog
									.loading("${lfn:message('kms-common:person.import.tip3') }");
							$
									.ajax({
										type : "post",

										url : "${LUI_ContextPath}/kms/common/kms_common_person_import/kmsPersonImport.do?method=importData",
										data : $('form[name=kmsPersonImportForm]')
												.serialize(),
										dataType : "json",
										cache : false,
										success : function(data) {
											$(".changeBlock").show();
											validateDialog.hide();												
											var error=data.error;
											$("input[name='errorInfo']").val(error.errorInfo);
											var success = data.success;													
											$("input[name='fdPersonId']").val(success.ids);
											$("input[name='fdPersonName']").val(success.names);
											var rowNum = data.rowNum;	
											var sucNum = data.sucNum;	
											var errNum = data.errNum;	
											var rtInfo = "<p style='float:left;'>${lfn:message('kms-common:person.import.tip4') }</p><p style='float:left;color:red'>"+rowNum+"</p><p style='float:left;'>${lfn:message('kms-common:person.import.tip5') }  </p><p style='float:left;color:red'>"+sucNum+"</p><p style='float:left;'>${lfn:message('kms-common:person.import.tip6') } </p><p style='float:left;color:red'>"+errNum+"</p><p style='float:left;'>${lfn:message('kms-common:person.import.tip7') }</p>"
											$("#rtInfo").html(rtInfo);
											$(".errInfo").show();
						                    $("#rtInfo").show();

											 $("#att_xtable_personAttachment .upload_list_status div").click(function(){
												 $(".changeBlock").hide();
											 });
											var nullError = "${lfn:message('kms-common:person.import.tip2') }"
											
											var _errorInfo = $.trim(error.errorInfo);
											if(_errorInfo.indexOf(nullError)>-1){
												dialog.alert("${lfn:message('kms-common:person.import.tip20') }",function() {});
												$(".errInfo").hide();
							                    $("#rtInfo").hide();
											}


										},
										error : function(err) {

											seajs.use([ 'sys/ui/js/dialog' ],
													function(dialog) {

														dialog.alert("${lfn:message('kms-common:person.import.tip8') }",
																function() {
																});
													});
											return;
										}
									});
							//validateDialog.hide();
						});
	
	}
	window.onload = function() {
		var obj = window.attachmentObject_personAttachment;
		//替换编辑界面
		obj.renderurl = Com_Parameter.ContextPath
				+ "kms/common/kms_common_person_import/kms_common_upload_person_display.js";

	}
	attachmentObject_personAttachment.on("uploadSuccess", function(data) {
		var a1 = LUI.$('input[name="inputFileName"]');
		a1.attr("disabled", "true");
	});
				
	function downLoadErr(){
		var _errorInfo = $("input[name='errorInfo']").val();

		var errorInfo = $.trim(_errorInfo);
		seajs.use( [ 'sys/ui/js/dialog' ],function(dialog){ 
			if(errorInfo.length !=0){
				var nullError = "${lfn:message('kms-common:person.import.tip2') }"
				if(_errorInfo.indexOf(nullError)>-1){
					dialog.alert("${lfn:message('kms-common:person.import.tip20') }",function() {});						
				}else{
					dialog.confirm("${lfn:message('kms-common:person.import.tip9') }",
							function(flag, d) {
							 	if(flag){
							 		 var form = $("<form>");
								 	   form.attr({
								 		   "id" : "downloadform",
								 		   "style" : "display:none", 
								 		   "target" : "",
								 		   "method" : "post",
								 		   "action" : "${LUI_ContextPath}/kms/common/kms_common_person_import/kmsPersonImport.do?method=downErrExcel"
								 	   })

								 	    var input1 = $("<input>");
								 	    input1.attr({
								 	    	"name":"errorInfo",
								 	    	"value" :errorInfo,
								 	    	"type" : "hidden"
								 	    });

								 	    form.append(input1);//一定要把参数添加到form里
								 	    $("body").append(form);//将表单放置在页面中
								 	    form.submit();//表单提交
								 	    $("#downloadform").remove();//移除表单
								 }

					})
				}

			}else{
				dialog.alert("${lfn:message('kms-common:person.import.tip10') }",function() {});						

			}
						

		})
		
	}
	
</script>

<table style="display: none;border: 1px solid #5CBAEE;width: 100%;" id="info_table" border="1" cellpadding="1" cellspacing="0" >
		
</table>

