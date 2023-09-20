<%@page import="com.landray.kmss.sys.remind.forms.SysRemindMainForm"%>
<%@page import="com.landray.kmss.sys.remind.forms.SysRemindTemplateForm"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<c:set var="formName" value="${param.formName}" />
<c:set var="fdKey" value="${param.fdKey}" />
<c:set var="templateModelName" value="${param.templateModelName}" />
<c:set var="modelName" value="${param.modelName}" />
<c:set var="templateProperty" value="${param.templateProperty}" />
<c:set var="moduleUrl" value="${param.moduleUrl}" />

<c:set var="templateForm" value="${requestScope[param.formName]}" />
<c:set var="sysRemind" value="${templateForm.sysRemind}" />

<%
	// 多属性中获取提醒中心配置
	Object _sysRemind = pageContext.getAttribute("sysRemind");
	if(_sysRemind instanceof Map) {
		// 获取提醒中心模板配置
		Object _sysRemindTemplate = ((Map)_sysRemind).get("sysRemindTemplate");
		if(_sysRemindTemplate instanceof SysRemindTemplateForm) {
			// 获取提醒项配置
			List<SysRemindMainForm> fdMains = ((SysRemindTemplateForm)_sysRemindTemplate).getFdMains();
			pageContext.setAttribute("__fdMains__", fdMains);
		}
	}
%>
<script type="text/javascript">
	Com_IncludeFile("formula.js");
	window.sysRemind_MainList = [];
	
	Com_AddEventListener(window, 'load', function() {
		//注册切换页签的事件
		var table = document.getElementById("sysRemind_tab").parentNode;
		while((table!=null) && (table.tagName!="TABLE")) {
			table = table.parentNode;
		}
		if(table != null) {
			//页签切换调用的函数
			Doc_AddLabelSwitchEvent(table, "Remind_OnLabelSwitch_key");
		}
	});
	//切换页签
	window.Remind_OnLabelSwitch_key = function(tableName,index) {
		// 获取表单字段
		try {
			if(typeof XForm_getXFormDesignerObj_${JsParam.fdKey} === "function") {
				window.LBPM_Template_FormFieldList = XForm_getXFormDesignerObj_${JsParam.fdKey}();
			} else {
				window.LBPM_Template_FormFieldList = Formula_GetVarInfoByModelName('${modelName}');
			}
		} catch(e) {
			console.error("提醒中心获取公式表达式时报错：",e);
		}
	}
</script>
<style>
	.btn_txt{
		margin: 0px 2px;
	    color: #2574ad;
	    border-bottom: 1px solid transparent;
	}
	.sysRemind_btnopt {
	    color: #fff;
	    background-color: #47b5ea;
	    height: 25px;
	    line-height: 25px;
	    padding: 0px 5px;
	    margin: 10px 0;
	    border: 0px;
	    letter-spacing: 1px;
	    cursor: pointer;
	}
	.optStyle {
	
	}
</style>
<tr id="sysRemind_tab" LKS_LabelName="${lfn:message('sys-remind:module.sys.remind')}" LKS_LabelEnable="${JsParam.enable eq 'false' ? 'false' : 'true'}">
	<input type="hidden" id="sysRemind_sysRemindTemplate" name="sysRemind(sysRemindTemplate)">

	<td>
		<div  style="float: right;padding-right: 50px;">
			<input type="button" class="sysRemind_btnopt" value="${lfn:message('sys-remind:sysRemindMain.btn.add')}" onclick="addRemind();">
		</div>
		<br>

 		<table id="sysRemind_Table" class="tb_normal" style="width: 95%;text-align: center;">
			<tr>
				<td class="td_normal_title" width=5%>
					${lfn:message('page.serial')}
				</td>
				<td class="td_normal_title" width=30%>
					${lfn:message('sys-remind:sysRemindMain.fdName')}
				</td>
				<td class="td_normal_title" width=15%>
					${lfn:message('sys-remind:sysRemindMain.fdNotifyType')}
				</td>
				<td class="td_normal_title" width=20%>
					${lfn:message('sys-remind:sysRemindMain.fdReceivers')}
				</td>
				<td class="td_normal_title" width=10%>
					${lfn:message('sys-remind:sysRemindMain.fdIsEnable')}
				</td>
				<td class="td_normal_title" width=20%>
					${lfn:message('list.operation')}
				</td>
			</tr>
			<c:if test="${__fdMains__ != null}">
			<c:forEach var="remind" items="${__fdMains__}" varStatus="status">
				<tr id="${remind.fdId}">
					<td name="rowNum">${status.index + 1}</td>
					<td>${remind.fdName}</td>
					<td><kmss:showNotifyType value="${remind.fdNotifyType}"/></td>
					<td>
						<c:forEach var="receiver" items="${remind.fdReceivers}" varStatus="status1">
							<c:if test="${status1.index > 0}">;</c:if>
							<c:choose>
								<c:when test="${'xform' eq receiver.fdType}">
									${receiver.fdReceiverName}
								</c:when>
								<c:when test="${'address' eq receiver.fdType}">
									${receiver.fdReceiverOrgNames}
								</c:when>
							</c:choose>
						</c:forEach>
					</td>
					<td><sunbor:enumsShow value="${remind.fdIsEnable}" enumsType="sys_remind_main_enable" /></td>
					<td>
						<c:if test="${'edit' eq param.method}">
						<a class="btn_txt" href="javascript:showTask('${remind.fdId}');">${lfn:message('sys-remind:sysRemindMain.showTask')}</a>
						<a class="btn_txt" href="javascript:showLog('${remind.fdId}');">${lfn:message('sys-remind:sysRemindMain.showLog')}</a>
						</c:if>
						<a class="btn_txt" href="javascript:editRemind('${remind.fdId}');">${lfn:message('button.edit')}</a>
						<a class="btn_txt" href="javascript:delToTable('${remind.fdId}');">${lfn:message('button.delete')}</a>
						<script type="text/javascript">
						seajs.use(['lui/jquery','lui/dialog'], function($, dialog) {
							$.post('<c:url value="/sys/remind/sys_remind_main/sysRemindMain.do?method=getById&fdId=${remind.fdId}&cloneId=${remind.cloneId}" />', function(res) {
								sysRemind_MainList.push(res);
							}, "json");
						});
						</script>
					</td>
				</tr>
			</c:forEach>
			</c:if>
		</table>

		<div id="NotifyType_Tpl" style="display: none;">
			<kmss:showNotifyType value=""/>
		</div>
		<script type="text/javascript">
		seajs.use(['lui/jquery','lui/dialog'], function($, dialog) {
			window.isNew = function(fdId) {
				if(fdId) {
					if(fdId.match(new RegExp("^new_.*$"))) {
						return true;
					} else {
						return false;
					}
				}
				return true;
			}
			
			// 构建行
			window.getRow = function(data, rowNum) {
				var tr = $('<tr id="' + data.fdId + '"></tr>'), tab = $("#sysRemind_Table"), rows = tab.find("tr"), content = [];
				if(rowNum) {
					content.push('	<td name="rowNum">' + rowNum + '</td>');
				} else {
					content.push('	<td name="rowNum">' + rows.length + '</td>');
				}
				content.push('	<td>' + data.fdName + '</td>');
				var NotifyType_Tpl = $("#NotifyType_Tpl");
				NotifyType_Tpl.find("[type=checkbox]").removeAttr("checked");
				var NotifyTypes = data.fdNotifyType.split(";");
				for(var i=0; i<NotifyTypes.length; i++) {
					NotifyType_Tpl.find("[value="+NotifyTypes[i]+"]").attr("checked", true);
				}
				content.push('	<td>' + NotifyType_Tpl.html() + '</td>');
				var receiverNames = [];
				for(idx in data.fdReceivers) {
					var _name = data.fdReceivers[idx].fdReceiverName;
					if(_name && _name.length > 0) {
						receiverNames.push(_name);
					}
					_name = data.fdReceivers[idx].fdReceiverOrgNames;
					if(_name && _name.length > 0) {
						receiverNames.push(_name);
					}
				}
				content.push('	<td>' + receiverNames.join(";") + '</td>');
				if(data.fdIsEnable == "true") {
					content.push("	<td>${lfn:message('sys-remind:sysRemindMain.fdIsEnable.enable')}</td>");
				} else {
					content.push("	<td>${lfn:message('sys-remind:sysRemindMain.fdIsEnable.disable')}</td>");
				}
				var options = [];
				if(!isNew(data.fdId)) {
					options.push("<a class=\"btn_txt\" href=\"javascript:showTask('" + data.fdId + "');\">${lfn:message('sys-remind:sysRemindMain.showTask')}</a> ");
					options.push("<a class=\"btn_txt\" href=\"javascript:showLog('" + data.fdId + "');\">${lfn:message('sys-remind:sysRemindMain.showLog')}</a> ");
				}
				options.push("<a class=\"btn_txt\" href=\"javascript:editRemind('" + data.fdId + "');\">${lfn:message('button.edit')}</a> ");
				options.push("<a class=\"btn_txt\" href=\"javascript:delToTable('" + data.fdId + "');\">${lfn:message('button.delete')}</a> ");
				content.push('	<td>' + options.join("") + '</td>');
				tr.append(content);
				return tr;
			}
			
			// 增加提醒行
			window.addToTable = function(data) {
				var tab = $("#sysRemind_Table");
				sysRemind_MainList.push(data);
				tab.append(getRow(data));
			}
			
			// 修改提醒行
			window.updateToTable = function(data) {
				var row = $("#sysRemind_Table").find("#" + data.fdId);
				for(idx in sysRemind_MainList) {
					if(data.fdId == sysRemind_MainList[idx].fdId) {
						sysRemind_MainList[idx] = data;
						break;
					}
				}
				var rowNum = row.find("[name=rowNum]").text();
				row.empty();
				row.append(getRow(data, rowNum).html());
			}
			
			// 删除提醒行
			window.delToTable = function(fdId) {
				dialog.confirm("${lfn:message('sys-remind:sysRemindMain.confirm.delete')}", function(value) {
					if(value == true) {
						var row = $("#sysRemind_Table").find("#" + fdId);
						for(idx in sysRemind_MainList) {
							if(fdId == sysRemind_MainList[idx].fdId) {
								//加上删除标志
								sysRemind_MainList[idx].deleteFlag = "1";
								break;
							}
						}
						row.remove();
					}
				});
			}
			
			// 查看任务
			window.showTask = function(fdId) {
				dialog.iframe("/sys/remind/sys_remind_main_task/index.jsp?remindId=" + fdId, "${lfn:message('sys-remind:table.sysRemindMainTask')}", null, 
						{width:1200, height:500, topWin:window, close:true});
			}
			
			// 查看日志
			window.showLog = function(fdId) {
				dialog.iframe("/sys/remind/sys_remind_main_task_log/index.jsp?remindId=" + fdId, "${lfn:message('sys-remind:table.sysRemindMainTaskLog')}", null, 
						{width:1200, height:500, topWin:window, close:true});
			}
			
			// 增加提醒
			window.addRemind = function() {
				editRemind();
			}
			
			// 编辑提醒
			window.editRemind = function(fdId) {
				fdId = fdId || "";
				var url = "/sys/remind/sys_remind_main/sysRemindMain_edit.jsp?fdKey=${JsParam.fdKey}&formName=${JsParam.formName}&fdId=" + fdId;
				var title = "${lfn:message('sys-remind:sysRemindMain.title.add')}";
				if(fdId) {
					title = "${lfn:message('sys-remind:sysRemindMain.title.update')}";
				}
				dialog.iframe(url, title, function(res) {
					if(res) {
						if(fdId) {
							res["fdId"] = fdId;
							updateToTable(res);
						} else {
							if(fdId.length < 1) {
								fdId = "new_" + new Date().getTime();
							}
							res["fdId"] = fdId;
							addToTable(res);
						}
					}
				}, {
					width:900,
					height:600,
					buttons:[{
						name : '<bean:message key="button.ok"/>',
						value : true,
						focus : true,
						fn : function(value, _dialog) {
							var frame = _dialog.frame[0];
							var contentWin = $(frame).find("iframe")[0].contentWindow;
							var data = contentWin.remind_submit();
							if(data) {
								_dialog.hide(data);
							}
						}
					}, {
						name : '<bean:message key="button.cancel"/>',
						styleClass:"lui_toolbar_btn_gray",
						value : false,
						fn : function(value, _dialog) {
							_dialog.hide();
						}
					}]
				});
			}
			
			// 提交前处理
			Com_Parameter.event["submit"][Com_Parameter.event["submit"].length] = function() {
				if(sysRemind_MainList.length > 0) {
					// 过滤新增的ID
					for(var i in sysRemind_MainList) {
						if(isNew(sysRemind_MainList[i].fdId)){
							sysRemind_MainList[i].fdId = "";
						}
					}
					var data = {
							"fdModuleUrl": "${moduleUrl}",
							"fdKey": "${fdKey}",
							"fdModelName": "${modelName}",
							"fdTemplateName": "${templateModelName}",
							"fdTemplateProperty": "${templateProperty}",
							"fdMains": sysRemind_MainList
					};
					$("#sysRemind_sysRemindTemplate").val(JSON.stringify(data));
				}
				return true;
			}
		});
		</script>
	</td>
</tr>
