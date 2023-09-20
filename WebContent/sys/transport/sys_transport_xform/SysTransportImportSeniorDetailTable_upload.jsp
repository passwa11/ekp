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
<script type="text/javascript">
	Com_IncludeFile("jquery.form.js");
</script>
<script type="text/javascript">
	var importExcelStatus = "unUpload";
	var importData = '';
	top._uploadNewProgress=true;	
	//父窗口存储的信息
	var _paramJsonExcelUpload = top._paramJsonExcelUpload;
	
	function setImportDataAndXform(data){
		importData = data;
	}
	
	function importDatasToTable(){
		top.DocList_importData(_paramJsonExcelUpload.optTBId,importData,_paramJsonExcelUpload.isXform,_paramJsonExcelUpload.maxLimitedNum);
	}

	//展示继续导入的框
	function showContinueImportData(){
		showContiuneImportButton();
	}

	function showContiuneImportButton(){
		$("#continueImport").show();
	}

	function validateForm() {
		if (document.sysTransportImportUploadForm.file.value == "") {
			alert('<bean:message bundle="sys-transport" key="sysTransport.error.upload.fileEmpty"/>');
			return false;
		}
		else return true;
	}
	//文件上传
	function upload(){
		var dataJson = JSON.parse(_paramJsonExcelUpload.field);
		var expControl = ['costCenter','area','vehicle','project','expenseItem','currency'];
		$("[name=templateId]").val(top.$("[name=docTemplateId]").val());
		for(var i=0;i<dataJson.length;i++){
			if($.inArray(dataJson[i].fieldType,expControl)>-1||dataJson[i].fieldType=='area'){
				var div = top.$("[name$='"+dataJson[i].fieldId+")']").parent();
				var func = div[0].onclick||div.find('.selectitem')[0].onclick;
				console.log(func)
				var fdCompanyId = (func||'').toString().replace(/[\s\S]+fdCompanyId\:[\'\"]([^\'^\"]+)[\'\"][\s\S]+/,'$1')
				if(dataJson[i].fieldType=='area'){
					fdCompanyId = fdCompanyId.replace(/[\s\S]+[\"\']([^\'^\"]+)[\"\']\,[\s\S]+/,'$1');
				}
				fdCompanyId = top.$("[name*="+fdCompanyId+"]").val();
				$("[name=fdCompanyId]").val(fdCompanyId);
				break;
			}
		}
		document.getElementById("progress").style.display = "none";
		document.getElementById("importSuccessed").style.display = "none";
		document.getElementById("progressBar").style.display = "none";
		var file = document.getElementsByName("file");
		if(file[0].value==null || file[0].value.length==0){
			seajs.use(['lui/dialog'],function(dialog){
				dialog.alert("<bean:message bundle='sys-transport' key='sysTransport.import.file.required'/>");
			});
			return false;
		}
		if(file[0].value.indexOf(".xls") == -1){
				alert("<bean:message bundle='sys-transport' key='sysTransport.import.file.fileFormatNotRight'/>");
				return false;
		}	
		else if(importExcelStatus && importExcelStatus == "uploading"){
				confirm("<bean:message bundle='sys-transport' key='sysTransport.title.dataImportingWarning'/>");
				return false;
		}else{
			//修改状态为正在执行
			importExcelStatus = "uploading";
			var uploadProcess=document.getElementById("div_uploadProcess");
			document.getElementById("progress").style.display="none";
			document.getElementById("importSuccessed").style.display="none";
			uploadProcess.innerHTML="<font color='red'><bean:message bundle='sys-transport' key='sysTransport.title.dataValidated'/></font>";
			var form=document.getElementsByName("sysTransportImportUploadForm")[0];
			form.target="file_frame";
			var div = buildFormElementByParam(_paramJsonExcelUpload);
			form.appendChild(div);
			top.window.seajs.use(['lui/dialog'],function (dialog){
				top.window.loadProcess = dialog.loading();
			});
			form.submit();
			$(div).remove();
		}
	}
	
	// 以post方式提交，用该方式提交
	function buildFormElementByParam(){
		var div = document.createElement("div");
		if(arguments.length == 0){
			return;
		}
		//如果参数只有一个，表示把传进来的json的全部属性转换
		if(arguments.length == 1){
			for(var proName in arguments[0]){
				var opt = document.createElement("textarea");
				opt.style.display = 'none';
		        opt.name = proName;
		        opt.value = arguments[0][proName];
		        div.appendChild(opt);
			}
		}else{
			var parseJson = arguments[arguments.length - 1];
			if(typeof(parseJson) == 'object'){
				for(var i = 0;i < arguments.length - 1; i++){
					if(parseJson.hasOwnProperty(arguments[i])){
						var opt = document.createElement("textarea");
						opt.style.display = 'none';
				        opt.name = arguments[i];
				        opt.value = parseJson[arguments[i]];
				        div.appendChild(opt);
					}		
				}	
			}
		}
		return div;
	}
	
	//解析json到url的参数里面 用get方式提交采用该拼接方法
	function parseJsonToUrlParam(){
		var urlParam='';
		if(arguments.length == 0){
			return;
		}
		//如果参数只有一个，表示把传进来的json的全部属性转换为url拼接的参数
		if(arguments.length == 1){
			for(var proName in arguments[0]){
				urlParam += "&" + proName + "=" + arguments[0][proName];
			}
		}else{
			var parseJson = arguments[arguments.length - 1];
			if(typeof(parseJson) == 'object'){
				for(var i = 0;i < arguments.length - 1; i++){
					if(parseJson.hasOwnProperty(arguments[i])){
						urlParam += "&" + arguments[i] + "=" + parseJson[arguments[i]];
					}		
				}	
			}
		}
		return urlParam;
	}
	
	//excel文件上传完毕,显示操作信息
	function callback(result){
		result = result.replace(/\r\n/g,"<br/>");
		document.getElementById("div_errorCell").innerHTML=result;
		var uploadProcess=document.getElementById("div_uploadProcess");
		if(result==""){
			//隐藏详情
			document.getElementById("msgDetails").style.display = "none";
		}else {
			//显示详情
			document.getElementById("msgDetails").style.display = "";
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

	//改变上传附件,重置导出结果
	function resetResult(){
		var uploadProcess=document.getElementById("div_uploadProcess");
		uploadProcess.innerHTML="<bean:message bundle='sys-transport' key='sysTransport.title.uploadNotDo'/>";
		document.getElementById("div_errorCell").innerHTML="";
	} 

	function changeImportStatus(status){
		importExcelStatus = status;
		if(status === 'uploadFailure'){
			if(top && top.window && top.window.loadProcess){
				top.window.loadProcess.hide();
			}
		}
	}

	//展示结果区域
	function showResultTr(){
		$("#resultTr").show();
	}
	
	//切换单选的时候，提示也跟着切换
	function toggleTip(type){
		//1 为追加数据，2 为替换数据
		var tip = '';
		if(type == '1'){
			tip = "${lfn:message('sys-transport:sysTransport.upload.appendDataTip')}";
		}else if(type == '2'){
			tip = "${lfn:message('sys-transport:sysTransport.upload.uploadDataTip')}";
		}
		$("#importTypeTip").html(tip);
	}
	
	function sysTransImportCloseWindow(){
		 if(importExcelStatus && importExcelStatus == "uploading"){			   
				if(confirm("<bean:message bundle='sys-transport' key='sysTransport.import.warnCloseWindow'/>")){
					Com_Parameter.CloseInfo = null;//清空关闭信息
					Com_CloseWindow();
				};		
		   }else if(importExcelStatus && importExcelStatus == "unUpload"){
			   Com_Parameter.CloseInfo = null;//清空关闭信息
			   Com_CloseWindow();   	
			}else if(importExcelStatus && importExcelStatus == "uploadFailure" || importExcelStatus == "finishImported"){
				Com_Parameter.CloseInfo = null;//清空关闭信息
				Com_CloseWindow();
			}					
		} 

	//模板下载
	function downTemplate(){
		//把modelName、明细表字段名、需要下载的字段的name传到后台处理
		var url = Com_Parameter.ContextPath + "sys/transport/detailTableSeniorExport.do?method=deTableExportTemplate" + parseJsonToUrlParam("modelName","itemName","propertyName","isXform",_paramJsonExcelUpload);
		Com_OpenWindow(url,"_parent");
	}
</script>
<html:form action="/sys/transport/sys_transport_xform/SysTransportSeniorImportUpload.do?method=detailTableUpload"
	enctype="multipart/form-data"	onsubmit="return validateForm(this);">
	<iframe name="file_frame" style="display:none;"></iframe>
<div id="optBarDiv">
	<input type="button" value="<bean:message bundle="sys-transport" key="sysTransport.button.download.templet"/>" onclick="downTemplate();">
	<input type="button" value="<bean:message bundle="sys-transport" key="sysTransport.button.upload"/>" onclick="upload();">
	<input type="button" value="<bean:message key="button.close"/>" onclick="sysTransImportCloseWindow();">
</div>

<p class="txttitle"><bean:message  bundle="sys-transport" key="sysTransport.button.dataImport"/></p>

<center>
<table class="tb_normal" width="500">
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="sys-transport" key="sysTransport.upload.file"/>
		</td>
		<td width=85% colspan="3">
			<html:file property="file" accept=".xls,.xlsx" styleClass="upload" style="width:90%;height:25px;" onchange="resetResult();"/>
			<input type="hidden" name="fdCompanyId" value=""/>
			<input type="hidden" name="templateId" value=""/>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="sys-transport" key="sysTransport.upload.uploadType"/>
		</td>
		<td>
			<label><input type="radio" name="importType" value="append" checked onclick="toggleTip('1');"/>
				<span><bean:message  bundle="sys-transport" key="sysTransport.upload.appendData"/></span>
			</label>
			<label><input type="radio" name="importType" value="update" onclick="toggleTip('2');"/>
				<span><bean:message  bundle="sys-transport" key="sysTransport.upload.uploadData"/></span>
			</label>
			<div>
				<span id="importTypeTip" style="color:red;font-size:12px;"><bean:message  bundle="sys-transport" key="sysTransport.upload.appendDataTip"/></span><br/><br/>
				<span style="color:red;font-size:12px;"><bean:message  bundle="sys-transport" key="sysTransport.upload.uploadMaxTip"/></span>
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
					<td colspan="2">
						<span id="div_uploadProcess">
								<bean:message bundle="sys-transport" key="sysTransport.title.uploadNotDo"/>
						</span>
					</td>
				</tr>
				<tr>
					<td width="15%">
					</td>
					<td colspan="2">
						<div id="progress" style="display: none">
							<span style="color:red;font-size:12px;">
								<bean:message bundle="sys-transport" key="sysTransport.title.dataImporting"/>
							</span>																
						</div>
						<div id="importSuccessed" style="display: none">
							<span style="color:red;font-size:12px;">
								<bean:message bundle="sys-transport" key="sysTransport.title.uploadFinish"/>							
							</span>																
						</div>
					</td>
				</tr>
				<tr>
					<td colspan="3">					
						<div class="progressBar" id="progressBar" style="display:none">							
						</div>
					</td>
				</tr>
					
				<tr height="25px">
					<td class="msglist" colspan="3" id="msgDetails">
						<image src='${KMSS_Parameter_StylePath}msg/plus.gif' onclick='showMoreErrInfo(this);' style='cursor:pointer'>
						<bean:message bundle="sys-transport" key="sysTransport.title.detail"/>
						<div id="div_errorCell" style="display:none;margin-left: 20px;font-size:12px;"></div>
					</td>
				</tr>
				<tr id="continueImport" style="display:none;">
					<td colspan="3">
						<div style="text-align:center;margin-top:10px;">
							<ui:button onclick="importDatasToTable();" text='${lfn:message("sys-transport:sysTransport.import.file.continueImport")}' style="height:25px;padding:5px 10px;cursor:pointer;"></ui:button>
						</div>
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