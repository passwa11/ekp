<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple">

	<template:replace name="body">
   		 <link rel="stylesheet" href="../resource/style/hr.css">
   		<html:form action="/hr/ratify/hr_ratify_transfer/hrRatifyTransfer.do?method=importTransfer"  enctype="multipart/form-data" styleClass="lui_hr_import_form">
   			<iframe name="file_frame" style="display:none;"></iframe>	
        <div class="lui_hr_import_wrap">
            <!--提示文字Starts-->
            <div class="lui_hr_validation_msg msg_danger">
                <i class="lui_hr_validation_icon lui_hr_icon_danger_warning">
                </i>
             	  请先下载确认模板后上传
            </div>
            <!--提示文字Ends-->

            <!--步骤Starts-->
            <div class="lui_hr_step_list">
                <!--步骤1 下载模板Starts-->
                <div class="lui_hr_step_list_item">
                    <div class="lui_text_primary lui_hr_step_title">
                        <span class="lui_hr_step_text">步骤1</span>
                    </div>
                    <p class="lui_hr_step_content">
                        <p>请先下载Excel模版，并按模版信息填写员工信息</p>
                        <div class="lui_hr_btnGroup">
                            <a class="lui_text_primary lui_hr_btn lui_hr_btn_download" onclick="Com_OpenNewWindow(this)" data-href="${LUI_ContextPath}/hr/ratify/hr_ratify_transfer/hrRatifyTransfer.do?method=downloadTemplate"
                                target="_blank">下载标准模版</a>
                        </div>
                </div>
                <!--步骤1 下载模板End-->
                <!--步骤1 上传表格Starts-->
                <div class="lui_hr_step_list_item">
                    <div class="lui_text_primary lui_hr_step_title">
                        <span class="lui_hr_step_text">步骤2</span>
                    </div>
                    <p class="lui_hr_step_content">
                        <p>上传Excel表格</p>
                        <div class="lui_hr_btnGroup lui_hr_import_btnGroup">
    						<input id="upload_file_temp" type="text">
                            <label class="lui_text_primary lui_hr_btn lui_hr_btn_browse"><input id="upload_file" name="file" accept=".xls,.xlsx" type="file" class="" title="浏览" onchange="change();">浏览</label>
                        </div>
                </div>
                 <div class="lui_hr_step_list_item">
                    <div class="lui_text_primary lui_hr_step_title">
                        <span class="lui_hr_step_text">步骤3</span>
                    </div>
                    <p class="lui_hr_step_content">
                    <p>查看上传结果</p>
                </div>
            </div>
            <div class="lui_hr_btnGroup">
            	<div id="div_errorCell" style="display:none;margin-top: 5px;width:600px;overflow:auto;"></div>
            </div>
            <!--步骤1 上传表格End-->
            <!--步骤Ends-->
            <!--弹框底部按钮 产品标准组件 Starts-->
            <div class="lui_hr_footer_btnGroup">
			   <ui:button text="确认上传" order="5"  onclick="upload();">
			   </ui:button>
			   <ui:button text="${ lfn:message('button.cancel') }" order="5" styleClass="lui_toolbar_btn_gray"  onclick="Com_CloseWindow();">
			   </ui:button>
            </div>
            <!--弹框底部按钮 产品标准组件 Ends-->
        </div>
        </html:form>
	</template:replace>
</template:include>

 <script type="text/javascript">
	//文件上传
	function upload(){
		var file = document.getElementsByName("file");
		if(file[0].value==null || file[0].value.length==0){
			seajs.use(['lui/dialog'],function(dialog){
				dialog.alert("<bean:message bundle='hr-ratify' key='hrRatify.concern.transfer.import.file.required'/>");
			});
			return false;
		}
		else{
			//修改状态为正在执行
			/* var uploadProcess=document.getElementById("div_uploadProcess");
			uploadProcess.innerHTML="<font color='blue'><bean:message bundle='hr-ratify' key='hrRatify.concern.transfer.title.uploadDoing'/></font>"; */
			var form=document.getElementsByName("hrRatifyTransferForm")[0];
			form.target="file_frame";
			form.submit();
		}
	}
	function change(){
		document.getElementById("upload_file_temp").value=document.getElementById("upload_file").value; 
	};
	
	//excel文件上传完毕,显示操作信息
	function callback(result){
		document.getElementById("div_errorCell").innerHTML=createTable(result);
		$('#div_errorCell').show();
	}
	function createTable(json) {
		console.log(json);
		if(json == null) return "";
		if(typeof json !== 'object') return "";
		//if(json.hasError != 1) return "";
		var htmlStr = "<table class='tb_normal' style='width:100%;'>";
		var colLength;
		//标题
		var titles = json.titles;
		if(titles != null) {
			htmlStr += "<thead style='background:#ddd;'><tr>";
			var colLength = titles.length;
			titles.forEach(function(item,index) {
				htmlStr += "<th style='padding:5px 10px;text-align:center;border-left:1px solid #d1d1d1;'>"+item+"</th>"
			});
			htmlStr += "</tr></thead>";
		}
		htmlStr += "<tbody>";
		//内容
		var rows = json.errorRows;
		if(rows != null) {
			rows.forEach(function(item,index) {
				var errColNumbers = item.errColNumbers.split(",").map(function(item,index) {
					return parseInt(item);
				});
				htmlStr += "<tr>";
				htmlStr += "<td>"+item.errRowNumber+"</td>";
				var contents = item.contents;
				contents.forEach(function(it,idx) {
					if($.inArray(idx,errColNumbers) > -1)
						htmlStr += "<td style='color:#FF0000;'>"+formatNull(it)+"</td>";
					else
						htmlStr += "<td>"+formatNull(it)+"</td>";	
				});
				htmlStr += "<td style='color:#FF0000'>"+item.errInfos+"</td>";
				htmlStr += "</tr>";
			});
		}
		//其他错误
		var otherErrors = json.otherErrors;
		if(otherErrors && otherErrors.length>0) {
			var colspanStr = colLength==null?"":"colspan='"+colLength+"'";
			htmlStr += "<tr><td "+colspanStr+"><p><b><bean:message bundle='hr-ratify' key='hrRatify.concern.transfer.import.otherErrors'/></b></p>";
			otherErrors.forEach(function(item,index) {
				htmlStr += "<p style='color:#FF0000;'>"+item+"</p>";
			});
			htmlStr += "</td></tr>";
		}
		//成功
		var importMsg = json.importMsg;
		if(importMsg && importMsg.length>0) {
			htmlStr += "<tr><td  colspan=9><p><b>"+importMsg+"</b></p>";
			htmlStr += "</td></tr>";
		}
		
		htmlStr += "</tbody></table>";
		return htmlStr;
	}
	function formatNull(val) {
		if(val===null||val===undefined||val==="null"||val==="NULL") {
			return "";
		}
		return val;
	}
 </script>