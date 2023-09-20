<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<link type="text/css" rel="stylesheet" href="${LUI_ContextPath }/fssc/common/resource/css/jquery.stepy.css" />
<style>
div#header {
	margin: 0 auto;
	padding-top: 12px;
	width: 90%;
}
div#content {
	background-color: #FFF;
	margin: 0 auto;
	padding: 15px;
	width: 90%;
	min-height: 400px;
}
div#footer {
	clear: both;
	height: 25px;
	margin-top: 5px;
	width: 90%;
}

div#title {
	font: bold 17px verdana;
	color: #269;
	letter-spacing: .7px;
	margin-bottom: 10px;
	text-align: left;
}
div#title span {
	color: #777;
	font: 10px verdana;
}
div.description {
	font: bold 12px verdana;
	padding-left: 15px;
}
div.session-first {
	clear: both;
	font: bold 13px verdana;
	border-bottom: 1px solid #EFEFEF;
	color: #444;
	letter-spacing: .7px;
	margin-bottom: 18px;
	text-align: left;
}

.t-button {
	float: left;
}

.t-button {
	border: 1px solid #C9C4BA;
	color: #7F0055;
	cursor: pointer;
	padding: 5px 15px;
	text-decoration: none;
	-khtml-border-radius: 3px;
	-moz-border-radius: 3px;
	-opera-border-radius: 3px;
	-webkit-border-radius: 3px;
	border-radius: 3px;
}
.t-button:hover {
	border-color: #DFDCD6;
	color: #B07;
}
div.grayScreen{
	width:100;
	height:100;
	position:absolute;
	left:0;
	top:0;
	background:#aaa;
	opacity:0.8;
}
/** tb_simple 一般2列表格表单的样式  无表框线表格 **/
.tb_simple{border-collapse: collapse;background-color: #FFFFFF;margin: 0 auto;}
.tb_simple td {padding: 5px; word-break:break-all;}
.tb_simple .td_normal_title{ text-align:right}       /*字段名称*/
</style>
<script>
Com_IncludeFile("jquery.js");
</script>
<!-- Optionaly -->
<script type="text/javascript" src="${LUI_ContextPath }/fssc/common/resource/js/jquery.validate.min.js"></script>
<script type="text/javascript" src="${LUI_ContextPath }/fssc/common/resource/js/jquery.stepy.js"></script>
<div id="header">
	<div id="title"><bean:message key="message.fssc.transfer" bundle="fssc-common"/> <span>(fssc -> eop)</span></div>
</div>

<div id="content">
	<div class="session-first"><bean:message key="message.fssc.transfer.step" bundle="fssc-common"/></div>
	
	<form id="t_step_form">
		<fieldset title="<bean:message key="message.fssc.transfer.step.one" bundle="fssc-common"/>">
			<legend><bean:message key="message.fssc.transfer.step.one.title" bundle="fssc-common"/></legend>
			<table class="tb_simple" style="width: 90%">
				<tr>
					<td>
						<bean:message key="message.fssc.transfer.step.one.tip" bundle="fssc-common"/>
					</td>
				</tr>
				<tr>
					<td>
						<label><bean:message key="message.fssc.transfer.step.one.label" bundle="fssc-common"/></label>
						<input type="button" class="t-button" value="<bean:message key="message.fssc.transfer.step.one.button" bundle="fssc-common"/>" onclick="t_ckecker('1', this);" />
					</td>
				</tr>
				<tr>
					<td>
						<input type="hidden" name="t_next_1" value="false" />
						<div class="source"><bean:message key="message.fssc.transfer.step.one.result" bundle="fssc-common"/></div>
						<div id="t_result_msg_1" class="source"></div>
					</td>
				</tr>
			</table>
		</fieldset>
		
		<fieldset title="<bean:message key="message.fssc.transfer.step.two" bundle="fssc-common"/>">
			<legend><bean:message key="message.fssc.transfer.step.two.title" bundle="fssc-common"/></legend>
			<table class="tb_simple" style="width: 90%">
				<tr>
					<td>
						<bean:message key="message.fssc.transfer.step.two.tip" bundle="fssc-common"/>
					</td>
				</tr>
				<tr>
					<td>
						<label><bean:message key="message.fssc.transfer.step.two.label" bundle="fssc-common"/></label>
						<input id="t_button_2" type="button" class="t-button" value="<bean:message key="message.fssc.transfer.step.two.button.start" bundle="fssc-common"/>" onclick="t_init('2', this);" />
					</td>
				</tr>
				<tr>
					<td>
						<input type="hidden" name="t_next_2" value="" />
						<div class="source"><bean:message key="message.fssc.transfer.step.two.result" bundle="fssc-common"/></div>
						<div id="t_result_msg_2" class="source"></div>
					</td>
				</tr>
			</table>
		</fieldset>
		
		<fieldset title="<bean:message key="message.fssc.transfer.step.three" bundle="fssc-common"/>">
			<legend><bean:message key="message.fssc.transfer.step.three.title" bundle="fssc-common"/></legend>
			<table class="tb_simple" style="width: 90%">
				<tr>
					<td>
						<bean:message key="message.fssc.transfer.step.three.tip" bundle="fssc-common"/>
					</td>
				</tr>
				<tr>
					<td>
						<label><bean:message key="message.fssc.transfer.step.three.label" bundle="fssc-common"/></label>
						<input id="t_button_3" type="button" class="t-button" value="<bean:message key="message.fssc.transfer.step.three.button" bundle="fssc-common"/>" onclick="t_transfe_start('3', this);" />
					</td>
				</tr>
				<tr>
					<td>
						<input type="hidden" name="t_next_3" value="" />
						<div class="source"><bean:message key="message.fssc.transfer.step.three.result" bundle="fssc-common"/></div>
						<div id="t_result_msg_3" class="source"></div>
					</td>
				</tr>
			</table>
		</fieldset>
		
		<fieldset title="<bean:message key="message.fssc.transfer.step.four" bundle="fssc-common"/>">
			<legend><bean:message key="message.fssc.transfer.step.four.title" bundle="fssc-common"/></legend>
			<table class="tb_simple" style="width: 90%">
				<tr>
					<td>
						<bean:message key="message.fssc.transfer.step.four.tip" bundle="fssc-common"/>
					</td>
				</tr>
				<tr>
					<td>
						&nbsp;
					</td>
				</tr>
				<tr>
					<td>
						&nbsp;
					</td>
				</tr>
			</table>
		</fieldset>
	</form>
</div>

<div id="footer">
</div>
<div class="grayScreen"></div>
<script type="text/javascript">
	var t_step_form = null;
	
	$(document).ajaxComplete(function(event,xhr,settings){
		var regLoginPage = /<input[^>]+type=(\"|\')?password(\"|\')?[^>]*>/gi;
		if (xhr.responseText.search(regLoginPage) > 0) {
			//debugger;
			window.top.location.reload();
		}
	});

	$(function() {

		t_step_form =  $('#t_step_form').stepy({
			finishButton: false,
			back: function(index) {
				t_refresh();
				return true;
			},
			next: function(index) {
				var next = $("input[name='t_next_"+(index-1)+"']").val();
				if(next == "false") {
					alert("请先处理完成当前步骤...");
					return false;
				}
				t_refresh();
				return true;
			}
		});

		t_refresh();

	});
	function t_refresh(obj) {
		$(".button-back").text("< 上一步")
		$(".button-next").text("下一步 >")
	}
	function t_init(index,obj){
		var val = confirm('要开始生成映射吗？\n生成过程耗时较长，可能会造成页面卡顿，开始后请不要关闭本页面')
		if(val){
			$.ajax({
				url:'${LUI_ContextPath}/fssc/common/fssc_common_transfer_field/fsscCommonTransferField.do?method=initTransferFieldData',
				data:{},
				dataType:'JSON',
				async:false,
				type:'POST',
				success:function(data){
					$("#t_result_msg_2").html(data.message)
					$(obj).attr("disabled", false);
					if(data.result=='success'){
						$("input[name='t_next_"+index+"']").val("true");
					}
				}
			})
		}
	}
	function t_reset(index,obj){
		var val = confirm('确认要重置配置吗？')
		if(val){
			$.ajax({
				url:'${LUI_ContextPath}/fssc/common/fssc_common_transfer_field/fsscCommonTransferField.do?method=resetTransferFieldData',
				data:{},
				dataType:'JSON',
				async:false,
				type:'POST',
				success:function(data){
					$("#t_result_msg_2").html(data)
					$(obj).attr("disabled", false);
					if(data.result=='success'){
						$("input[name='t_next_"+index+"']").val("true");
					}
				}
			})
		}
	}
	function t_auto_refresh(obj) {
		t_refresh();
		if(obj.checked) {
			__autoRefresh = window.setInterval("t_refresh()", 12000);
		} else {
			if(__autoRefresh) {
				window.clearInterval(__autoRefresh);
			}
		}
	}
	function t_refreshButton(index, json) {
		if(!json) {
			return;
		}
		if(json.isRunning == "true") {
			$("#t_button_"+index).attr("isRuning", "1").attr("value", "暂停迁移");
			$("input[name='t_next_"+index+"']").val("false");
			$("#t_result_msg_"+index).html("<font color=red>数据迁移后台正在进行中... <br>"+"点击“暂停迁移”按钮立刻停止，点击“刷新”按钮查看最新的迁移结果</font>");
			if(json.isOneKey == 'true'){
				$("#t_checkbox_"+index).attr("checked", true);
			} else {
				$("#t_checkbox_"+index).attr("checked", false);
			}
		} else {
			$("#t_button_"+index).attr("isRuning", "0").attr("value", "开始迁移");
			$("input[name='t_next_"+index+"']").val("");
			$("#t_result_msg_"+index).html("迁移已经停止");
			if(json.isOneKey == 'true'){
				$("#t_checkbox_"+index).attr("checked", true);
			} else {
				$("#t_checkbox_"+index).attr("checked", false);
			}
		}
	}
	function t_convertor(index) {
		var type = "ALL";
		if(!$("#t_checkbox_"+index).is(":checked")) {
			if("2" == index) {
				type = "CONFIG";
			} else if("3" == index) {
				type = "RUNNING";
			} else if("4" == index) {
				type = "ENDED";
			}
		}
		return type;
	}
	function t_ckecker(index, obj) {
		$(obj).attr("disabled", true);
		$("input[name='t_next_"+index+"']").val("false");
		$.ajax({
			url:'${LUI_ContextPath}/fssc/common/fssc_common_transfer_field/fsscCommonTransferField.do?method=checkInit',
			data:{},
			dataType:'JSON',
			async:false,
			type:'POST',
			success:function(data){
				$("#t_result_msg_1").html(data.message)
				$(obj).attr("disabled", false);
				if(data.result=='success'){
					$("input[name='t_next_"+index+"']").val("true");
				}
			}
		})
	}
	function t_transfe_start(index,obj){
		$(obj).attr("disabled", true);
		$("input[name='t_next_"+index+"']").val("false");
		$(".grayScreen").show();
		$.ajax({
			url:'${LUI_ContextPath}/fssc/common/fssc_common_transfer_field/fsscCommonTransferField.do?method=transfer',
			data:{},
			async:false,
			dataType:'JSON',
			type:'POST',
			success:function(data){
				console.log(data)
				$("#t_result_msg_3").html(data.message)
				$(obj).attr("disabled", false);
				$("input[name='t_next_"+index+"']").val("true");
				$(".grayScreen").hide();
			},
			error:function(e){
				console.log(e);
				$(obj).attr("disabled", false);
			}
		});
	}
	function t_transfer(index, obj) {
		var type = t_convertor(index);
		if($(obj).attr("isRuning") == "1") {
			$(obj).attr("value", "正在暂停...");
			$.getJSON(Com_Parameter.ContextPath + "tools/lbpmtransfer/lbpm_transfer/lbpmtransfer.do?method=interrput",
					{d: (new Date().getTime())},
				function(json){
						$(obj).attr("isRuning", "0");
						$("input[name='t_next_"+index+"']").val("");
						$(obj).attr("disabled", false);
						$(obj).attr("value", "开始迁移");
						t_callback(json, index);
	 		});
		} else {
			$.getJSON(Com_Parameter.ContextPath + "tools/lbpmtransfer/lbpm_transfer/lbpmtransfer.do?method=transfer",
					{d: (new Date().getTime()), type: type, index: index},
				function(json){
						if(json && json.isRuning == "true") {
							// 已经正在迁移
							$(obj).attr("disabled", false);
						} else {
							$(obj).attr("isRuning", "1");
							$("input[name='t_next_"+index+"']").val("false");
							$(obj).attr("disabled", false);
							$(obj).attr("value", "暂停迁移");
						}
						t_callback(json, index);
	 		});
		}
		$(obj).attr("disabled", true);
	}
	function t_callback(json, index) {
		if(json && json.error == "false") {
			if(json.msg) {
				$("#t_result_msg_"+index).html("<font color=green>" + json.msg + "</font>");
			}
		} else {
			$("input[name='t_next_"+index+"']").val("false"); // 禁止下一步
			if(json && json.msg) {
				$("#t_result_msg_"+index).html("<font color=red>" + json.msg + "</font>");
			} else {
				$("#t_result_msg_"+index).html("<font color=red>出现未知异常</font>");
			}
		}
	}
	function t_finish() {
		$.ajax({
			type: "get",
			dataType: "text",
			url: Com_Parameter.ContextPath + "tools/lbpmtransfer/lbpm_transfer/lbpmtransfer.do?method=finish",
			success: function(text){
				Com_CloseWindow();
			},
			error: function() {
				alert("error!");
			}
		});
	}
</script>
<%@ include file="/resource/jsp/view_down.jsp"%>