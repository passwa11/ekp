<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="default.edit" sidebar="auto">
	<template:replace name="title">
		${ lfn:message('sys-profile:sys.profile.i18n.reset.details') }
	</template:replace>
	<template:replace name="head">
        <link rel="stylesheet" href="${LUI_ContextPath}/sys/profile/resource/css/i18n.css?s_cache=${LUI_Cache}" />
        <style>
			.lui_handover_searchResult table th {
				text-align: center;
			}
			table td.rd_title {
				font-weight: bold;
				background-color: #f6f6f6;
			}
			.module_title {
				font-size: 16px;
				font-weight: bold;
				background-color: #f6f6f6;
				text-align: left;
			}
			h3 {
				text-align: center;
			}
		</style>
	</template:replace>
	<template:replace name="content"> 
		<script>
			Com_IncludeFile("base64.js");
			seajs.use( [ 'lui/jquery', 'lui/dialog' ], function($, dialog) {				
				// 提交操作
				submitOperation = function() {
					window.del_load = dialog.loading();
					$("input[name=methodType]").val($("input[name=checkType]:checked").val());
					// 针对某些资源信息里包含类似脚本信息，普通的提交会被拦截，所以统一进行Base64编码后再提交，后台接收到数据时进行Base64解码
					var dataArray = $("form[name=resultContentForm]").serializeArray();
					var newDatas = {};
					for(var i=0; i<dataArray.length; i++) {
						var data = dataArray[i];
						newDatas[data['name']] = Base64.encode(data['value']);
					}
					$.post('<c:url value="/sys/profile/i18n/sysProfileI18nConfig.do?method=saveMessage"/>',
							newDatas, function(result) {
						if(window.del_load != null) {
							window.del_load.hide();
						}
						if(result.state) {
							dialog.success("${ lfn:message('sys-profile:sys.profile.i18n.saveMessage.success') }");
						} else {
							dialog.alert(result.message);
						}
					}, "json");
				}
				
				detail = function(val, isDiffer) {
					if(!val || val.length < 1) {
						dialog.alert("${ lfn:message('sys-profile:sys.profile.i18n.langtools.noMsg') }");
						return false;
					}
					dialog.iframe("/sys/profile/i18n/sysProfileI18nConfig.do?method=viewDetailOriginal&messageKey=" + val + "&isDiffer=" + isDiffer,
							"${ lfn:message('sys-profile:sys.profile.i18n.langtools.detailOriginal') }", function(value) {
					}, {
						"width" : 700,
						"height" : 400
					});
				}
			});
		</script>
		<h3>${ lfn:message('sys-profile:sys.profile.i18n.reset.details') }</h3>
		<%-- 结果部分--%>
               <div class="lui_handover_searchResult">
                 	<%--记录显示--%> 
                   <div name="resultContent" class="resultContent" id="resultContent">
                   	<form name="resultContentForm" action="<c:url value="/sys/profile/i18n/sysProfileI18nConfig.do?method=saveMessage"/>" method="post">
                   		<input type="hidden" name="methodType" value="">
                   		<ui:dataview id="resultDataview">
							<ui:source type="AjaxJson">
								{url:'/sys/profile/i18n/sysProfileI18nConfig.do?method=getMessageByModule&urlPrefix=${JsParam.urlPrefix}'}
							</ui:source>
							<ui:render type="Template">
							if($.isEmptyObject(data)) {
								{$
									<div class="module_title">${ lfn:message('sys-profile:sys.profile.i18n.seach.nodata') }</div>
								$}
							} else {
								for(var module in data) {
									var _module = module.split(":");
									{$
										<div class="module_title">{% _module[1] %}</div>
									$}
									var allData = [];
									var index = 1;
									var differ = data[module].differ || {};
									for(var lang in data[module]) {
										if(lang == 'differ') continue;
		               					if(lang.split(":")[0] != 'zh_CN') {
		                					allData[index++] = {name:lang, data:data[module][lang]};
		               					} else {
		               						allData[0] = {name:lang, data:data[module][lang]};
		               					}
		               				}
			                   		var width = (75 / allData.length) + "%";
									{$
									<table class="tb_simple lui_handover_headTb lui_sheet_c_table">
			                   			<tr>
			                   				<th width="20%">Message key</th>
			                   				<th width="{% width %}">简体中文</th>
			                   				$}
			                   				for(var i=1; i<allData.length; i++) {
			                   					if(allData[i].name == 'differ') continue;
			                   				{$
			                   				<th width="{% width %}">{% allData[i].name %}</th>
			                   				$}
			                   				}
			                   				{$
			                   				<th width="5%" style="text-align: center;">${ lfn:message('sys-profile:sys.profile.i18n.langtools.original') }</th>
			                   			</tr>
			                   			$}
			                   			var _default = allData[0].data;
			                   			for(var _k in _default) {
			                   				var _input_name = _module[0] + ":" + _k + ":default";
			                   				var color = differ[_k + ":default"] ? "border:#ff0000 1px solid;" : "";
			                   				var isDiffer = false;;
			                   				if(color.length > 0) isDiffer = true;
			                   			{$
				                   			<tr>
				                   			<td>{% _k %}</td>
				                   			<td>
				                   				<textarea name="{% _input_name %}" style="width:100%;height:18px;{% color %}" data-actor-expand="true">{% Com_HtmlEscape(_default[_k]) %}</textarea>
				                   			</td>
				                   			$}
				                   			for(var i=1; i<allData.length; i++) {
				                   				var _langName = _module[0] + ":" + _k + ":" + allData[i].name.split(":")[0];
				                   				color = differ[_k + ":" + allData[i].name.split(":")[0]] ? "border:#ff0000 1px solid;" : "";
				                   				if(color.length > 0) isDiffer = true;
				                   			{$
				                   				<td>
				                   					<textarea name="{% _langName %}" style="width:100%;height:18px;{% color %}" data-actor-expand="true">{% Com_HtmlEscape(allData[i].data[_k]) %}</textarea>
				                   				</td>
				                   			$}
				                   			}
				                   			{$
				                   			<td style="text-align: center;"><a href="javascript:detail('{% _langName %}', '{% isDiffer %}');">${ lfn:message('sys-profile:sys.profile.i18n.langtools.detail') }</a></td>
				                   			</tr>
			                   			$}
			                   			}
			                   			{$
			                   			
			                   		</table>
								$}
								}
							}
							if(window.del_load != null) {
								window.del_load.hide();
							}
							</ui:render>
						</ui:dataview>
						<script>
							LUI.ready(function() {
								LUI("resultDataview").on("load", function() {
									TextArea_BindEvent();

									seajs.use([ 'lui/jquery' ], function($) {
										$("textarea").keydown(function(event) {
											if (event.keyCode != 13)
												return;
											event.preventDefault();
											// 在光标处增加字符
											var myValue = '<br>';
											var $t = $(this)[0];
											//IE  
											if (document.selection) {
												this.focus();
												sel = document.selection.createRange();
												sel.text = myValue;
												this.focus();
											} else
											//!IE  
											if ($t.selectionStart || $t.selectionStart == "0") {
												var startPos = $t.selectionStart;
												var endPos = $t.selectionEnd;
												var scrollTop = $t.scrollTop;
												$t.value = $t.value.substring(0, startPos)
															+ myValue
															+ $t.value.substring(endPos, $t.value.length);
												this.focus();
												$t.selectionStart = startPos + myValue.length;
												$t.selectionEnd = startPos + myValue.length;
												$t.scrollTop = scrollTop;
											} else {
												this.value += myValue;
												this.focus();
											}
										});
									});
								});
							});
						</script>
					</form>
                   </div>
                   <div class="lui_handover_btn_w">
                  		<ui:button style="width:100px" text="${ lfn:message('sys-profile:sys.profile.i18n.seach.submit') }" onclick="submitOperation();"/>
                   </div>
	</template:replace>
</template:include>