<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple">

	<template:replace name="body">
   		<link rel="stylesheet" href="${LUI_ContextPath}/hr/organization/resource/css/organization.css">
   		<html:form action="/hr/organization/hr_organization_staffing_level/hrOrganizationStaffingLevel.do?method=fileUpload"  enctype="multipart/form-data" styleClass="lui_hr_import_form">
   			<iframe name="file_frame" style="display:none;"></iframe>	
        <div class="lui_hr_import_wrap">
            <!--提示文字Starts-->
            <div class="lui_hr_validation_msg msg_danger">
                <i class="lui_hr_validation_icon lui_hr_icon_danger_warning">
                </i>
                ${ lfn:message('hr-organization:hr.organization.info.batch.import') } 
            </div>
            <!--提示文字Ends-->

            <!--步骤Starts-->
            <div class="lui_hr_step_list">
                <!--步骤1 下载模板Starts-->
                <div class="lui_hr_step_list_item">
                    <div class="lui_text_primary lui_hr_step_title">
                        <span class="lui_hr_step_text">${ lfn:message('hr-organization:hr.organization.info.batch.import.step') } 1</span>
                    </div>
                    <div class="lui_hr_step_content">
                        <p>${ lfn:message('hr-organization:hr.organization.info.batch.import.step.tip0') }</p>
                        <div class="lui_hr_btnGroup">
                            <a class="lui_text_primary lui_hr_btn lui_hr_btn_download" href="${LUI_ContextPath}/hr/organization/hr_organization_staffing_level/hrOrganizationStaffingLevel.do?method=downloadTemplet"
                                target="_blank">${ lfn:message('hr-organization:hr.organization.info.batch.import.step.tip1') }</a>
                        </div>
                    </div>
                </div>
                <!--步骤1 下载模板End-->
                <!--步骤1 上传表格Starts-->
                <div class="lui_hr_step_list_item">
                    <div class="lui_text_primary lui_hr_step_title">
                        <span class="lui_hr_step_text">${ lfn:message('hr-organization:hr.organization.info.batch.import.step') } 2</span>
                    </div>
                    <div class="lui_hr_step_content">
                        <p>${ lfn:message('hr-organization:hr.organization.info.batch.import.step.tip2') }</p>
                        <div class="lui_hr_btnGroup lui_hr_import_btnGroup">
    						<input id="upload_file_temp" type="text">
                            <label class="lui_text_primary lui_hr_btn lui_hr_btn_browse">
                            <input id="upload_file" name="file" accept=".xls,.xlsx" type="file" class="" title="${ lfn:message('hr-organization:hr.organization.info.batch.import.step.tip4') } " onchange="change();">
                            ${ lfn:message('hr-organization:hr.organization.info.batch.import.step.tip4') } 
                            </label>
                        </div>
                    </div>
                </div>
                 <div class="lui_hr_step_list_item">
                    <div class="lui_text_primary lui_hr_step_title">
                        <span class="lui_hr_step_text">${ lfn:message('hr-organization:hr.organization.info.batch.import.step') } 3</span>
                    </div>
                    <div class="lui_hr_step_content">
                    	<p>${ lfn:message('hr-organization:hr.organization.info.batch.import.step.tip3') } </p>
                    </div>
                </div>
            </div>
            <div class="lui_hr_btnGroup" style="min-height:86px;">
            	<div id="div_errorCell" style="display:none;margin-top: 5px;width:600px;overflow:auto;"></div>
            </div>
            <!--步骤1 上传表格End-->
            <!--步骤Ends-->
            <!--弹框底部按钮 产品标准组件 Starts-->
            <div class="lui_hr_footer_btnGroup">
			   <ui:button text="${ lfn:message('hr-organization:hr.organization.info.batch.import.step.tip5') } " order="5"  onclick="upload();">
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
				dialog.alert("<bean:message bundle='hr-organization' key='hr.organization.import.file.required'/>");
			});
			return false;
		}
		else{
			//修改状态为正在执行
			var form=document.getElementsByName("sysOrganizationStaffingLevelForm")[0];
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
	function createTable(result) {
		var htmlStr = "<table class='tb_normal' style='width:100%;margin-bottom:15px'>";
		if(result && result.length>0) {
			htmlStr += "<tr><td><p><b>"+result+"</b></p>";
			htmlStr += "</td></tr>";
		}
		htmlStr += "</tbody></table>";
		return htmlStr;
	}
	
 </script>