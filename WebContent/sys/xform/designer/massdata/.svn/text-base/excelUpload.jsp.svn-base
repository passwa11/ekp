<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<% response.addHeader("X-UA-Compatible", "IE=Edge"); %>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<style>
	.error-info{
		color:#da4e4e;
	}
</style>
<script type="text/javascript">
	Com_IncludeFile("jquery.form.js");
</script>
<script type="text/javascript">
	
	seajs.use(['lui/dialog'],function(dialog){
		window.upload = function(){
			/*********** 前置操作 start ************/
			resetResult();
			var file = document.getElementsByName("file");
			if(file[0].value == null || file[0].value.length == 0){
				dialog.alert("${lfn:message('sys-xform:sysFormMassData.upload.file.required')}");
				return false;
			}
			if(file[0].value.indexOf(".xls") == -1){
				alert("${lfn:message('sys-xform:sysFormMassData.upload.file.fileFormatNotRight')}");
				return false;
			}
			/*********** 前置操作 end ************/
			var columnInfos = window['$dialog'].___params;
			var form = document.getElementsByName("sysFormMassDataUploadForm")[0];
			form.target = "file_frame";
			var div = buildFormElementByParam({columnInfos:JSON.stringify(columnInfos)});
			form.appendChild(div);
			form.submit();
			$(div).remove();
		}
		
		//excel文件上传完毕,显示操作信息  rs: {"excel":{},"status":0(异常)|1(正常),"msg":xxx}
		window.importExcel = function(rs){
			if(rs.status == '1'){
				dialog.success("${lfn:message('sys-xform:sysFormMassData.upload.success')}");
				$dialog.hide(JSON.stringify(rs));
			}else{
				$("#resultTr").show();
				var msg = rs.msg.replace(/\r\n/g,"<br/>");
				document.getElementById("div_errorCell").innerHTML = msg;
				$("#div_uploadStatus").html("<span class='error-info'>${lfn:message('sys-xform:sysFormMassData.upload.details.tip')}</span>");
			}
		}
	});
	
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
			// 否则是最后一个对象的所有属性进行转换
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
	
	// 详情图标，展开出错列表
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
	
	// 重置结果信息
	function resetResult(){
		// 隐藏导入结果，清空记录
		$("#resultTr").hide();
		$("#div_uploadStatus").html("");
		$("#div_errorCell").hide().html("");
		$("#msgDetails").find("img").trigger($.Event("click"));
	} 

	//模板下载
	function downTemplate(){
		var columnInfos = window['$dialog'].___params;		
		var url = Com_Parameter.ContextPath + "sys/xform/massdata/sysFormMassDataUpload.do?method=downloadTemplate";
		var $form = $("<form>");               // 定义一个form表单
		$form.attr("style","display:none");  // form隐藏
		$form.attr("method","post");         // 请求类型（以post方式提交）
		$form.attr("action",url);    		// 模板文件下载请求地址
		$form.append(buildFormElementByParam({columnInfos:JSON.stringify(columnInfos)}));	// 把需要传输的内容放置到form元素里面
		$("body").append($form);             // 将表单放置在页面body中
		$form.submit();                      // 表单提交
		$form.remove();						// 删除form
	}
</script>
<html:form action="/sys/xform/massdata/sysFormMassDataUpload.do?method=uploadExcel" 
	enctype="multipart/form-data">
	<iframe name="file_frame" style="display:none;"></iframe>
<div id="optBarDiv">
	<input type="button" value="<bean:message bundle="sys-xform" key="sysFormMassData.btn.download"/>" onclick="downTemplate();">
	<input type="button" value="<bean:message bundle="sys-xform" key="sysFormMassData.btn.upload"/>" onclick="upload();">
	<input type="button" value="<bean:message key="button.close"/>" onclick="$dialog.hide();">
</div>

<p class="txttitle"><bean:message  bundle="sys-xform" key="sysFormMassData.excel"/></p>

<center>
<table class="tb_normal" width="500">
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="sys-transport" key="sysTransport.upload.file"/>
		</td>
		<td width=85% colspan="3">
			<html:file property="file" accept=".xls,.xlsx" styleClass="upload" style="width:90%;height:25px;" onchange="resetResult();"/>
		</td>
	</tr>
	<!-- 结果详情 -->
	<tr id="resultTr" style="display:none;">
		<td colspan="2">
			<bean:message  bundle="sys-xform" key="sysFormMassData.upload.tip.uploadResult"/><br />
			<table class="tb_noborder" width="100%">
				<tr height="25px">
					<td class="msglist" width="15%">
						<bean:message  bundle="sys-xform" key="sysFormMassData.upload.title.uploadProcess"/>
					</td>
					<td colspan="2">
					<!-- 导入状态：成功|异常 -->
						<span id="div_uploadStatus"></span>
					</td>
				</tr>
				<tr height="25px">
					<td class="msglist" colspan="3" id="msgDetails">
						<image src='${KMSS_Parameter_StylePath}msg/plus.gif' onclick='showMoreErrInfo(this);' style='cursor:pointer'>
						<bean:message  bundle="sys-xform" key="sysFormMassData.upload.title.detail"/>
						<!-- 异常信息 -->
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