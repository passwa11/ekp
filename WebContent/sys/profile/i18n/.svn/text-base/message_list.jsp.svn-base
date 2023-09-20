<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String rowStyle = ("N".equalsIgnoreCase(request.getParameter("showRow")) ? "display:none" : "");
%>
<%--记录显示--%> 
<div name="resultContent" class="resultContent" id="resultContent">
	<form name="resultContentForm" action="<c:url value="/sys/profile/i18n/sysProfileI18nConfig.do?method=saveMessage"/>" method="post">
		<input type="hidden" name="methodType" value="">
		<ui:dataview id="resultDataview">
			<ui:source type="AjaxJson">{url:''}</ui:source>
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
									var hasCn = false;
									for(var lang in data[module]) {
										if(lang == 'differ') continue;
		               					if(lang.split(":")[0] != 'zh_CN') {
		                					allData[index++] = {name:lang, data:data[module][lang]};
		               					} else {
		               						hasCn = true;
		               						allData[0] = {name:lang, data:data[module][lang]};
		               					}
		               				}
			                   		var width = (75 / allData.length) + "%";
									{$
									<table id="messageListTable" class="tb_simple lui_handover_headTb lui_sheet_c_table">
										<thead>
											<tr>
				                   				<th width="20%">Message key</th>
				                   				$}
				                   				if (hasCn) {
				                   				{$
				                   				<th width="{% width %}">简体中文</th>
				                   				$}
				                   				}
				                   				for(var i=1; i<allData.length; i++) {
				                   					if(allData[i].name == 'differ') continue;
				                   				{$
				                   				<th width="{% width %}">{% allData[i].name %}</th>
				                   				$}
				                   				}
				                   				{$
				                   				<th width="5%" style="text-align: center;">${ lfn:message('sys-profile:sys.profile.i18n.langtools.original') }</th>
											</tr>
										</thead>
										<tbody>
			                   			$}
			                   			var _default = allData[0] ? allData[0].data : allData[1].data;
			                   			for(var _k in _default) {
			                   				var _input_name = _module[0] + ":" + _k + ":default";
			                   				var color = differ[_k + ":default"] ? "border:#ff0000 1px solid;" : "";
			                   				var isDiffer = false;;
			                   				if(color.length > 0) isDiffer = true;
			                   			{$
				                   			<tr style="<%=rowStyle%>">
					                   			<td>{% _k %}</td>
					                   			$}
				                   				if (hasCn) {
				                   				{$
					                   			<td>
					                   				<textarea name="{% _input_name %}" style="width:100%;height:18px;{% color %}" data-actor-expand="true">{% Com_HtmlEscape(_default[_k]) %}</textarea>
					                   			</td>
					                   			$}
				                   				}
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
					                   			<td style="text-align: center;"><a href="javascript:detail('{% _input_name %}', '{% isDiffer %}');">${ lfn:message('sys-profile:sys.profile.i18n.langtools.detail') }</a></td>
				                   			</tr>
			                   			$}
			                   			}
			                   			{$
										</tbody>
			                   		</table>
								$}
								}
							}
							if(window.del_load != null) {
								window.del_load.hide();
							}
			</ui:render>
		</ui:dataview>
		<script type="text/javascript">
				function loadData(checkType, value){
					var url = "/sys/profile/i18n/sysProfileI18nConfig.do?method=";
					var data = {};
					if("module" == checkType) {
						url += "getMessageByModule";
						data['urlPrefix'] = value;
					} else {
						url += "getMessageByKeyWord";
						data['keyWord'] = value;
					}
					$("#resultDataview").empty();
					var resultDataview = LUI("resultDataview");
					resultDataview.source.commitType = "POST";
					resultDataview.source.params = data;
					resultDataview.source.setUrl(url);
					resultDataview.source.get();
				}
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