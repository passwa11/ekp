<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<% response.addHeader("X-UA-Compatible", "IE=Edge"); %>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<style>
	.progressBar{width:100%;height:8px;border-radius:5px;margin-top:10px;}
	#bar{width:0px;height:8px;border-radius:5px;background:#5EC4EA;}
</style>
<script>
	var _parentWindow = top;
	
	var dialogObject;
	if(window.showModalDialog && window.dialogArguments){
		dialogObject = window.dialogArguments;
	}else if(opener && opener.Com_Parameter.Dialog){
		dialogObject = opener.Com_Parameter.Dialog;
	}
	// 模板下载
	function downTemplate(){
		var url = Com_Parameter.ContextPath + "sys/lbpmservice/flowchartimport/LbpmFlowchartImportAction.do?method=downloadTemplate";	
		Com_OpenWindow(url,"_parent");
	}
	
	seajs.use(['lui/dialog'], function(dialog) {
		// excel导入
		window.importExcel = function(){
			var file = document.getElementsByName("uploadFile");
			var flag = $("input[name='importType']:checked").val();
			if(file[0].value==null || file[0].value.length==0){
				seajs.use(['lui/dialog'],function(dialog){
					dialog.alert("<bean:message bundle='sys-lbpmservice-support' key='lbpmFlowChartImport.import.isNotNull' />");
				});
				return false;
			}
			if(file[0].value.indexOf(".xls") == -1){
					alert("<bean:message bundle='sys-lbpmservice-support' key='lbpmFlowChartImport.import.errorType' />");
					return false;
			}
			// 若为更新模式，则带上原流程图信息xml
			if(flag==1){
				var flowChartXml = "";
				if(this.$dialog){
					flowChartXml = this.$dialog.flowChartXml;
				}else{
					if(dialogObject && dialogObject.Parameters && dialogObject.Parameters.flowChartXml){
						flowChartXml = dialogObject.Parameters.flowChartXml;
					}
				}
				if(flowChartXml){
					$("input[name='flowChartXml']").val(flowChartXml);
				}
			}
			var form = document.getElementsByName("lbpmFlowchartImportForm")[0];
			var action = form.action;
			var tempTarget = form.target;
			form.target = "file_frame";
			form.action += "&flag=" + flag;
			window.lbpm_import_load = dialog.loading();
			form.submit();
			// 复原target
			form.action = action;
			form.target = tempTarget;
		}
		
		window.loadError = function(rs){
			if(window.lbpm_import_load != null){
				window.lbpm_import_load.hide();
			}
			if(rs){
				rs = eval(rs);
				if(rs.status == '00'){
					if(this.$dialog){
						this.$dialog.hide();
						dialog.success("<bean:message bundle='sys-lbpmservice-support' key='lbpmFlowChartImport.import.success' />");
						parent.LBPM_Template_setFlowChart_${JsParam.fdKey}(rs.process);
					}else{
						dialogObject.rtnData = rs.process;
						setTimeout(function(){
							close()
						},500);
						dialog.success("<bean:message bundle='sys-lbpmservice-support' key='lbpmFlowChartImport.import.success' />");
						dialogObject.AfterShow();
					}
				}else{
					callback(rs.errorlog);
				}
			}
		}
		
	});
	
	//excel文件上传完毕,显示操作信息
	function callback(result){
		result = result.replace(/\r\n/g,"<br/>");
		document.getElementById("div_errorCell").innerHTML=result;
		var uploadProcess=document.getElementById("div_uploadProcess");
		if(result==""){
			//隐藏详情
			document.getElementById("resultTr").style.display = "none";
		}else {
			//显示详情
			document.getElementById("resultTr").style.display = "";
			uploadProcess.innerHTML="<font color='red'><bean:message bundle='sys-transport' key='sysTransport.title.partOfUploadFail'/></font>";
		}
	}

	//展开出错列表
	function showMoreErrInfo(srcImg){
		var obj = document.getElementById("div_errorCell");
		if(obj.style.display=="none"){
			obj.style.display="block";
			srcImg.src = Com_Parameter.StylePath + "msg/minus.gif";
		}else{
			obj.style.display="none";
			srcImg.src = Com_Parameter.StylePath + "msg/plus.gif";
		}
	}
	
	//切换单选的时候，提示也跟着切换
	function toggleTip(type){
		//0 为替换流程图，1 为更新流程图
		var tip = '';
		if(type == '0'){
			tip = "<bean:message bundle='sys-lbpmservice-support' key='lbpmFlowChartImport.importType.replaceMsg' />";
		}else if(type == '1'){
			tip = "<bean:message bundle='sys-lbpmservice-support' key='lbpmFlowChartImport.importType.updateMsg' />";
		}
		$("#importTypeTip").html(tip);
	}
</script>
<html:form action="/sys/lbpmservice/flowchartimport/LbpmFlowchartImportAction.do?method=loadExcel" 
	enctype="multipart/form-data"	onsubmit="return validateForm(this);">
	<iframe name="file_frame" style="display:none;"></iframe>
<div id="optBarDiv">
	<input type="button" value="<bean:message bundle='sys-lbpmservice-support' key='lbpmFlowChartImport.downTemplate' />" onclick="downTemplate();">
	<input type="button" value="<bean:message bundle='sys-lbpmservice-support' key='lbpmFlowChartImport.import' />" onclick="importExcel();">
	<input type="button" value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
</div>

<p class="txttitle"><bean:message  bundle="sys-transport" key="sysTransport.button.dataImport"/></p>

<center>
<table class="tb_normal" width="500px">
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle='sys-lbpmservice-support' key='lbpmFlowChartImport.file' />
		</td>
		<td width=85%>
			<html:file property="uploadFile" accept=".xls,.xlsx" styleClass="upload" style="width:90%;height:25px;"/>
			<input type="hidden" name="flowChartXml" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle='sys-lbpmservice-support' key='lbpmFlowChartImport.importType' />
		</td>
		<td width="85%">
			<label><input type="radio" name="importType" value="0" checked onclick="toggleTip('0');"/>
				<span><bean:message bundle='sys-lbpmservice-support' key='lbpmFlowChartImport.importType.replace' /></span>
			</label>
			<c:if test="${param.method=='edit'}">
				<label><input type="radio" name="importType" value="1" onclick="toggleTip('1');"/>
					<span><bean:message bundle='sys-lbpmservice-support' key='lbpmFlowChartImport.importType.update' /></span>
				</label>
			</c:if>
			<div>
				<span id="importTypeTip" style="color:red;font-size:12px;"><bean:message bundle='sys-lbpmservice-support' key='lbpmFlowChartImport.importType.replaceMsg' /></span>
			</div>
		</td>
	</tr>
	<tr id="resultTr" style="display:none;">
		<td colspan="2">
			<bean:message bundle="sys-transport" key="sysTransport.tip.uploadResult" /><br />
			<table class="tb_noborder" width="100%">
				<tr height="25px">
					<td class="msglist" width="15%">
						<bean:message bundle="sys-transport" key="sysTransport.title.uploadProcess"/>
					</td>
					<td width="85%">
						<span id="div_uploadProcess">
								<bean:message bundle="sys-transport" key="sysTransport.title.uploadNotDo"/>
						</span>
					</td>
				</tr>
					
				<tr height="25px">
					<td class="msglist" colspan="2" id="msgDetails">
						<image src='${KMSS_Parameter_StylePath}msg/plus.gif' onclick='showMoreErrInfo(this);' style='cursor:pointer'>
						<bean:message bundle="sys-transport" key="sysTransport.title.detail"/>
						<div id="div_errorCell" style="display:none;margin-left: 20px;font-size:12px;"></div>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<br/>
</center>
</html:form>
<%@ include file="/resource/jsp/edit_down.jsp"%>