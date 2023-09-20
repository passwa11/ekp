<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<style>
	.error{
		margin: 10px 0;
	}
</style>

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
                        <span class="lui_hr_step_text">
                        ${ lfn:message('hr-organization:hr.organization.info.batch.import.step')}1</span>
                    </div>
                    <div class="lui_hr_step_content">
                        <p>
                         ${ lfn:message('hr-organization:hr.organization.info.batch.import.step.tip0')}
                        </p>
                        <div class="lui_hr_btnGroup">
                            
                            <c:choose>
                            	<c:when test="${param.importByOrg eq 'true' }">
                            		<a class="lui_text_primary lui_hr_btn lui_hr_btn_download" href="${LUI_ContextPath}/hr/organization/resource/file/hr_template_new.xlsx"
                                	target="_blank">
                                	${ lfn:message('hr-organization:hr.organization.info.batch.import.step.tip1')}
                                	</a>
                            	</c:when>
                            	<c:otherwise>
                            		<a class="lui_text_primary lui_hr_btn lui_hr_btn_download" href="${param.downloadTemplateUrl }"
                               	 		target="_blank">${ lfn:message('hr-organization:hr.organization.info.batch.import.step.tip1')}</a>
                            	</c:otherwise>
                            </c:choose>
                        </div>
                      </div>
                </div>
                <!--步骤1 下载模板End-->
                <!--步骤1 上传表格Starts-->
                <div class="lui_hr_step_list_item">
                    <div class="lui_text_primary lui_hr_step_title">
                        <span class="lui_hr_step_text">${ lfn:message('hr-organization:hr.organization.info.batch.import.step')}2</span>
                    </div>
                    <div class="lui_hr_step_content">
                        <p>
                        ${ lfn:message('hr-organization:hr.organization.info.batch.import.step.tip2')}
                        </p>
                        <div class="lui_hr_btnGroup lui_hr_import_btnGroup">
    						<input id="upload_file_temp" type="text">
                            <label class="lui_text_primary lui_hr_btn lui_hr_btn_browse">
                            	<input id="upload_file" name="file" accept=".xls,.xlsx" type="file" class="" title="${ lfn:message('hr-organization:hr.organization.info.batch.import.step.tip4') }" onchange="change();">
              					${ lfn:message('hr-organization:hr.organization.info.batch.import.step.tip4')}
                            </label>
                        </div>
                     </div>
                </div>
                 <div class="lui_hr_step_list_item">
                    <div class="lui_text_primary lui_hr_step_title">
                        <span class="lui_hr_step_text">${ lfn:message('hr-organization:hr.organization.info.batch.import.step')}3</span>
                    </div>
                    <div class="lui_hr_step_content">
                    	<p>${ lfn:message('hr-organization:hr.organization.info.batch.import.step.tip3')}</p>
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
			   <ui:button text="${ lfn:message('hr-organization:hr.organization.info.batch.import.step.tip5')}" order="5"  onclick="upload();">
			   </ui:button>
			   <ui:button text="${ lfn:message('button.cancel') }" order="5" styleClass="lui_toolbar_btn_gray"  onclick="Com_CloseWindow();">
			   </ui:button>
            </div>
            <!--弹框底部按钮 产品标准组件 Ends-->
        </div>
 <script type="text/javascript">
	//文件上传
	var uploadBusy = false;
	var loadingObj=null;
	function upload(){
		seajs.use(['lui/dialog'],function(dialog){
			loadingObj = dialog.loading();
		})
		var file = document.getElementsByName("file");
		if(file[0].value==null || file[0].value.length==0){
			seajs.use(['lui/dialog'],function(dialog){
				dialog.alert("<bean:message bundle='hr-organization' key='hrOrganization.import.file.required'/>");
			});
			return false;
		}
		else{
			var form=document.getElementsByName("${param.formName}")[0];
			form.target="file_frame";
			if(!uploadBusy){
				uploadBusy = true;
				form.submit();
			}else{
				seajs.use(['lui/dialog'],function(dialog){
					dialog.alert("<bean:message bundle='hr-organization' key='hrOrganizationElement.import.not.repeat'/>");
				});
			}
			
		}
	}
	function change(){
		document.getElementById("upload_file_temp").value=document.getElementById("upload_file").value; 
	};
	
	//excel文件上传完毕,显示操作信息
	function callback(result){
		uploadBusy = false;
		document.getElementById("div_errorCell").innerHTML=createTable(result);
		$('#div_errorCell').show();
		loadingObj.hide();
	}
	function createTable(json) {
		if(json == null) return "";
		if(typeof json !== 'object') return "";
		var importType = json.importType;
		var htmlStr = "<table class='tb_normal' style='width:100%;margin-bottom:45px;'>";
		var colLength;
		//标题
		var titles = json.titles;
		var rows = json.errorRows;
		if(titles != null) {
			htmlStr += "<thead style='background:#ddd;'><tr>";
			var colLength = titles.length;
			titles.forEach(function(item,index) {
				var colspan = ""
				if(index==1){
					if(rows&&rows.length>0){
						colspan="colspan='"+(rows[0].contents.length+1)+"'"
					}
				}
				htmlStr += "<th style='padding:5px 10px;text-align:center;border-left:1px solid #d1d1d1;' >"+item+"</th>";
			});
			if(importType){
				//用于组织导入时候的类型区分
				htmlStr += "<th style='padding:5px 10px;text-align:center;border-left:1px solid #d1d1d1;' >导入类型</th>";
			}
			htmlStr += "</tr></thead>";
		}
		htmlStr += "<tbody>";
		//内容
		if(importType){
			if(rows != null&&rows.length>0) {
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
					htmlStr += "<td>"+importType+"</td>";
					htmlStr += "</tr>";
				});
			}
		}else{
			if(rows != null&&rows.length>0) {
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
		}

		//其他错误
		var otherErrors = json.otherErrors;
		var colspanStr = "";
		if(rows&&rows.length>0){
			colspanStr = colLength==null?"":"colspan='"+(rows[0].contents.length+2)+"'";
		}else{
			 colspanStr = "colspan='"+(titles&&titles.length)+"'";
		}
		
		if(otherErrors && otherErrors.length>0) {
			//兼容样式处理
			if(!importType){
				htmlStr += "<tr><td "+colspanStr+"><p><b><bean:message bundle='hr-organization' key='hrOrganization.import.otherErrors'/></b></p>";
			}else{
				htmlStr += "<tr><td "+colspanStr+">";
			}
			otherErrors.forEach(function(item,index) {
				htmlStr += "<p style='color:#FF0000;'>"+item+"</p>";
			});
			htmlStr += "</td>";
			if(importType){
				htmlStr += "<td>"+importType+"</td>";
			}
			htmlStr +="</tr>"
		}
		//导入返回结果
		var importMsg = json.importMsg;
		if(importMsg && importMsg.length>0) {
			
			htmlStr += "<tr><td  "+colspanStr+"><p><b>"+importMsg+"</b></p>";
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