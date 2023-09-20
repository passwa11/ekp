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
	// 模板下载
	function downTemplate(){
		var url = Com_Parameter.ContextPath + "sys/xform/maindata/main_data_custom/sysFormMainDataCustom.do?method=downloadTemplate&hasSuper=${param.isCascade}";	
		Com_OpenWindow(url,"_parent");
	}
	
	seajs.use(['lui/dialog'], function(dialog) {
		// excel导入
		window.importExcel = function(){
			var file = document.getElementsByName("uploadFile");
			var flag = $("input[name='importType']:checked").val();
			if(file[0].value==null || file[0].value.length==0){
				seajs.use(['lui/dialog'],function(dialog){
					dialog.alert('<bean:message bundle="sys-xform-maindata" key="sysFormMainDataCustom.uploadNotEmpty"/>');
				});
				return false;
			}
			if(file[0].value.indexOf(".xls") == -1){
					alert('<bean:message bundle="sys-xform-maindata" key="sysFormMainDataCustom.uploadFileType"/>');
					return false;
			}
			// flag:0---新增，flag：1----覆盖
			var form = document.getElementsByName("sysFormMainDataCustomForm")[0];
			var action = form.action;
			var tempTarget = form.target;
			form.target = "file_frame";
			form.action += "&fdId=${param.fdId}&flag=" + flag;
			window.customImport_load = dialog.loading();
			form.submit();
			// 复原target
			form.action = action;
			form.target = tempTarget;
		}
		
		window.loadError = function(rs){
			if(window.customImport_load != null){
				window.customImport_load.hide();
			}
			if(rs){
				rs = eval(rs);
				if(rs.status == '00'){
					this.$dialog.hide();
					dialog.success('<bean:message bundle="sys-xform-maindata" key="sysFormMainDataCustom.uploadSuccess"/>');
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
		//1 为追加数据，2 为替换数据
		var tip = '';
		if(type == '1'){
			tip = "${lfn:message('sys-transport:sysTransport.upload.appendDataTip')}";
		}else if(type == '2'){
			tip = "${lfn:message('sys-transport:sysTransport.upload.uploadDataTip')}";
		}
		$("#importTypeTip").html(tip);
	}
</script>
<html:form action="/sys/xform/maindata/main_data_custom/sysFormMainDataCustom.do?method=loadExcel" 
	enctype="multipart/form-data"	onsubmit="return validateForm(this);">
	<iframe name="file_frame" style="display:none;"></iframe>
<div id="optBarDiv">
	<input type="button" value='<bean:message bundle="sys-xform-maindata" key="sysFormMainDataCustom.downTemplate"/>' onclick="downTemplate();">
	<input type="button" value='<bean:message bundle="sys-xform-maindata" key="sysFormMainDataCustom.import"/>' onclick="importExcel();">
	<input type="button" value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
</div>

<p class="txttitle"><bean:message  bundle="sys-transport" key="sysTransport.button.dataImport"/></p>

<center>
<!-- 设置分页的最大阈值，当明细表的选项总数大于该值，则用另外一种样式显示，默认100 -->
<input type='hidden' name='maxNum' value='100' />
<table class="tb_normal" width="500px">
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-xform-maindata" key="sysFormMainDataCustom.file"/>
		</td>
		<td width=85%>
			<html:file property="uploadFile" accept=".xls,.xlsx" styleClass="upload" style="width:90%;height:25px;"/>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-xform-maindata" key="sysFormMainDataCustom.importType"/>
		</td>
		<td width="85%">
			<label><input type="radio" name="importType" value="0" checked onclick="toggleTip('1');"/>
				<span><bean:message bundle="sys-transport" key="sysTransport.upload.appendData"/></span>
			</label>
			<label><input type="radio" name="importType" value="1" onclick="toggleTip('2');"/>
				<span><bean:message bundle="sys-transport" key="sysTransport.upload.uploadData"/></span>
			</label>
			<div>
				<span id="importTypeTip" style="color:red;font-size:12px;"><bean:message  bundle="sys-transport" key="sysTransport.upload.appendDataTip"/></span>
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